<?php

error_reporting(E_ALL);
ini_set('display_errors', 1);

require_once 'api.php';

$api = new Api();

$api->get();