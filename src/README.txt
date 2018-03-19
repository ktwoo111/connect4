1. Taewoo Kim, 10696249
2. Java
3. Windows 10 and command prompt

4. 
The maxconnect4.java is the main frame calling AiPlayer class which contains the logic for minimax. The herustic for evaluation function is counting the number of four, three, and two streaks, giving them different weights. Then, it was (player's value) - (opponent's value).The GameBoard.java is mainly for display purposes only. maxconnect4Test.java is a JunitTest code that tests out the first step of how the AI would react to a given setup.

5. 
Make sure on Windows 10, go to your control panel and check that your environment variable's PATH has a pathway to Java\jdk1.8.0_111\bin.

Go to command prompt and change directory to the \src directory in the java project.
NOTE: make sure the input files are in the same directory as maxconnect4.java

type in: javac maxconnect4.java

type in: java maxconnect4 [interative/one-move] [input_file] [outputfile/human-first/computer-first] [depth]
