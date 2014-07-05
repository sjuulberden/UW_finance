<?php

/*
Your database has been setup. Use the following values:

Database:	onewardr_sjuul
Host:		localhost
Username:	onewardr_sjuul
Password:	**** 
*/


class Api {
	
	public $data;
	public $db;
	
	/**
	 * Constructor that checks the validity of the request and sets up the database connection
	 */
	function __construct() {
		
		// Get request data
		$this->data = $_POST;
		
		// Check if the correct API key is set
		if(!isset($_POST['api_key']) || $_POST['api_key'] != "*********") {
			
			// Don't handle this request
			die('access denied');
			
		} else {
			$this->db = new PDO("mysql:host=localhost;dbname=onewardr_sjuul", "onewardr_sjuul", "****");
		}
		
	}
	
	/**
	 * Add a cost to the database
	 */
	public function add() {
		
		// Check if required fields are set
		if(isset($this->data['description']) && isset($this->data['amount']) && isset($this->data['from']) && isset($this->data['to'])) {
			
			// Query
			$query = "INSERT INTO `costs` (`description`, `amount`, `from`, `to`) VALUES (%s, %s, %s, %s)";
			
			// Insert values
			$query = sprintf(
					$query,
					$this->db->quote($this->data['description']),
					$this->db->quote($this->data['amount']),
					$this->db->quote($this->data['from']),
					$this->db->quote($this->data['to'])
			);
			
			// Run query
			$result = $this->db->query($query);
			
			// Check if query was successful
			if($result) {
				$this->success("Cost was added successfully.");
			} else {
				$this->fail("Could not add cost to database.");
			}
			
		} else {
			$this->fail("Not all fields were filled in.");
		}
		
	}
	
	/**
	 * Get costs from the database
	 */
	public function get() {
		
		// Check if required fields are set
		if(isset($this->data['from']) && isset($this->data['to'])) {
			
			// Initialize result set
			$results = array();
			
			// Loop over all rows
			foreach($this->db->query("SELECT `description`, `amount`, `from`, `to` FROM `costs`") as $row) {
				
				//var_dump($row);
				//var_dump($_POST);
				
				// Convert dates to timestamps for easier calculations
				$fromdb = strtotime($row['from']);
				$from = strtotime($this->data['from']);
				$todb = strtotime($row['to']);
				$to = strtotime($this->data['to']);
				
				// Check if row should be included or that it falls outside the given scope
				if($todb < $from) { continue; }
				if($fromdb > $to) { continue; }
				
				// Calculate costs for given timespan
				$diffdb = $todb - $fromdb;
				$diff = $to - $from;
				
				// Date falls inside given scope and is 1 day
				if($diffdb == 0) {
					// Full costs
					$amount = $row['amount'];
				
				// One day requested
				} elseif($diff == 0) {
					
					$diff = 60 * 60 * 24;
					$amount = ($diff / $diffdb) * $row['amount'];
				
				// Requested from-date is earlier than database from-date, but the to-date falls inside requested timespan
				} elseif ($from < $fromdb && $to <= $todb) {
					$diff = $to - $fromdb;
					$amount = ($diff / $diffdb) * $row['amount'];
				
				// Requested to-date is later than database to-date, but the from-date falls inside the requested scope
				} elseif ($to > $todb && $from >= $fromdb) {
					$diff = $todb - $from;
					$amount = ($diff / $diffdb) * $row['amount'];
				
				// The entire requsted timespan falls inside the database timespan
				} elseif($diff >= $diffdb) {
					// Full costs
					$amount = $row['amount'];
					
				// Default action
				} else {
					// Part of the costs
					$amount = ($diff / $diffdb) * $row['amount'];
				}
				
				// If amount is zero, skip this row
				if($amount == 0) { continue; }
				
				// Add result to result set
				$results[] = array(
					'description' => $row['description'],
					'amount' => $amount
				);
				
			}
			
			$this->success('Success', $results);
			
		} else {
			$this->fail("Not all fields were filled in.");
		}
	}
	
	/**
	 * Output 501 (not implemented) error with a message to the client
	 */
	private function fail($msg) {
		
		echo json_encode(array(
			'success' => false,
			'msg' => $msg
		));
		
	}
	
	/**
	 * Output results to the client
	 */
	private function success($msg, $results = null) {
		
		$output = array(
			'success' => true,
			'msg' => $msg
		);
		
		if($results != null) {
			$output['results'] = $results;
		}
		
		echo json_encode($output);
		
	}
	
}
