# Design Document UW_finance (version 19-06-2014)

General Description:
When growing an online start-up there are many aspects to take into account. How to manage growth technically, how to reach more people out of the chosen target audience etc. etc. Due to this many startups lose sight of their costs and get into problems. UW_finance is an native Android app that enables the three starters to always accesses the cost of United Wardrobe, prohibiting them from making the same mistake that many start-ups make.

### Begin Screen



### Game View



### Settings



### High Scores

Button 3 bring the user to the high score page where the 10 highest scores are displayed together with the name of the person who scored that high score. The highscores are displayed from highest to lowest. 

### Technical description

The game consists of several classes. Firstly there is MainActivity. MainActivity determines what happens when the app is openend or what to do when one of the three buttons is clicked. secondly there is GameActivity which takes care of the general game activities like setting the gallow and choosing a (randow) word. GameActivity also checks wether the user has won or lost. Lastly, Gameactivity determines gameplay by its extention in GoodGamePlayActivity and EvilGamePlayActivity, the return values depend on the settings the user has set. Thirdly there is LetterAdapter witch takes care of setting the buttons and giving them the the right (alphabetical) value. The last class regulates the highscores. The highscores are supported by a helper class and a adapter class to retreive the values properly.

As for showning the gallow, it is choosen to cut the picture in 7 parts. This means that when the first wrong guess is made 1/7th of hangman is shown. When the user lost and there are no more guesses left the whole hangman is shown. As for showing the letter buttons it is chosen to create a function LetterPressed() which changes the aesthetics so the user is aware of witch buttons he pressed.  

For the layout there are serveral layout files. For every main screen (see design's UI) there is a layout file. Moreover there is an aditional layout file for the letter buttons. 

There are two lists used in the hangman app. Firstly there is the wordlist witch contains strings of words, this file is called words.xml, located in the values folder. Secondly there is the highscorelist, also located in the values folder. In this values folder there is a second file, the string.xml file. This file contains the hardcoded strings.

### Advanced sketches of UIs

See the pictures in the doc directory.

### Acknowledgement

This app is developed and copyrighted by Thijs Verheul (10003265) and Sjuul Berden (10694498).
