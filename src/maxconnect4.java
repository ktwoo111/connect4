import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author James Spargo
 * This class controls the game play for the Max Connect-Four game. 
 * To compile the program, use the following command from the maxConnectFour directory:
 * javac *.java
 *
 * the usage to run the program is as follows:
 * ( again, from the maxConnectFour directory )
 *
 *  -- for interactive mode:
 * java MaxConnectFour interactive [ input_file ] [ computer-next / human-next ] [ search depth]
 *
 * -- for one move mode
 * java maxConnectFour.MaxConnectFour one-move [ input_file ] [ output_file ] [ search depth]
 * 
 * description of arguments: 
 *  [ input_file ]
 *  -- the path and filename of the input file for the game
 *  
 *  [ computer-next / human-next ]
 *  -- the entity to make the next move. either computer or human. can be abbreviated to either C or H. This is only used in interactive mode
 *  
 *  [ output_file ]
 *  -- the path and filename of the output file for the game.  this is only used in one-move mode
 *  
 *  [ search depth ]
 *  -- the depth of the minimax search algorithm
 * 
 *   
 */

public class maxconnect4
{
  public static void main(String[] args) 
  {
	// check for the correct number of arguments
    
	  /*
	  if( args.length != 4 ) 
    {
      System.out.println("Four command-line arguments are needed:\n"
                         + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                         + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

      exit_function( 0 );
     }
     
    
    // parse the input arguments
	 
    
    String game_mode = args[0].toString();				// the game mode
    String input = args[1].toString();					// the input game file
    String output = args[2].toString();				    // the output game file
    String CorH = args[2].toString();
    int depthLevel = Integer.parseInt( args[3] );  		// the depth level of the ai search
    */
    
	
    String game_mode = "one-move";				// the game mode
    String input = "src/Jake.txt";					// the input game file
    String output = "output1.txt";				    // the output game file
    String CorH = "human-next";       //for interactive, who goes first
    int depthLevel =5;  		// the depth level of the ai search
    
		
    
    // create and initialize the game board
    GameBoard currentGame = new GameBoard(input);
    	
    //  variables to keep up with the game
    int playColumn = 99;				//  the players choice of column to play
    boolean playMade = false;			//  set to true once a play has been made

    if( game_mode.equalsIgnoreCase( "interactive" ) ) 
    {
    	AiPlayer computer = new AiPlayer(depthLevel,1);    
    	if(CorH.equalsIgnoreCase("computer-next")){
    		computer = new AiPlayer(depthLevel,currentGame.getCurrentTurn());
    	}
    	else{
    		if(currentGame.getCurrentTurn() == 1 ){
    		computer = new AiPlayer(depthLevel,2);
    		}
    		else{
    		computer = new AiPlayer(depthLevel,1);		
    		}   		
    	}
    	
    	 System.out.print("\nMaxConnect-4 game\n");
         System.out.print("game state before move:\n");
         //print the current game board
         currentGame.printGameBoard();
         // print the current scores
         System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
     			", Player2 = " + currentGame.getScore( 2 ) + "\n " );
         
         
         
    	 while(currentGame.getPieceCount() < 42 ){
    	        if( currentGame.getPieceCount() < 42 ) 
    	        {
    	            int current_player = currentGame.getCurrentTurn();
					if(current_player == computer.getPlayerNum()){
    	            	playColumn = computer.findBestPlay(currentGame);  	            	
    	            }
					else{
						System.out.print("which column(0-6) do you want to put your piece?: ");
						Scanner in = new Scanner(System.in);
						playColumn = in.nextInt();
						
						if(playColumn < 0 || playColumn > 6) {
							playColumn = 0;						
						}
						
					}  	
    	            
					// play the piece
    	            currentGame.playPiece(playColumn);           	
    	            // display the current game board
    	            
    	            System.out.println("move " + currentGame.getPieceCount() 
    	                               + ": Player " + current_player
    	                               + ", column " + playColumn);
    	            System.out.print("game state after move:\n");
    	            currentGame.printGameBoard();
    	        
    	            // print the current scores
    	            System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
    	                                ", Player2 = " + currentGame.getScore( 2 ) + "\n " );
    	            
    	            currentGame.printGameBoardToFile( output );
    	        } 
    	        else 
    	        {
    	    	System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
    	        }
    	        }
    } 
			
    else if(game_mode.equalsIgnoreCase( "one-move" ) ) 
    {
  
    	// create the Ai Player
        AiPlayer player1 = new AiPlayer(depthLevel,1);
        AiPlayer player2 = new AiPlayer(depthLevel,2);
        
        ArrayList<AiPlayer> players = new ArrayList<AiPlayer>();
        players.add(player1);
        players.add(player2);
        
        System.out.print("\nMaxConnect-4 game\n");
        System.out.print("game state before move:\n");
        //print the current game board
        currentGame.printGameBoard();
        // print the current scores
        System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
    			", Player2 = " + currentGame.getScore( 2 ) + "\n " );
        
        
        
        // ****************** this chunk of code makes the computer play
        while(currentGame.getPieceCount() < 42 ){
        if( currentGame.getPieceCount() < 42 ) 
        {
            int current_player = currentGame.getCurrentTurn();
            // AI play
            playColumn = players.get(currentGame.getCurrentTurn()-1).findBestPlay(currentGame);  	
            // play the piece
            currentGame.playPiece(playColumn);           	
            // display the current game board
            
            System.out.println("move " + currentGame.getPieceCount() 
                               + ": Player " + current_player
                               + ", column " + playColumn);
            System.out.print("game state after move:\n");
            currentGame.printGameBoard();
        
            // print the current scores
            System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
                                ", Player2 = " + currentGame.getScore( 2 ) + "\n " );
            
            currentGame.printGameBoardToFile( output );
        } 
        else 
        {
    	System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
        }
        }
    }
    
    return;
    
} // end of main()
	
  
  
  
  /**
   * This method is used when to exit the program prematurely.
   * @param value an integer that is returned to the system when the program exits.
   */
  private static void exit_function( int value )
  {
      System.out.println("exiting from MaxConnectFour.java!\n\n");
      System.exit( value );
  }
} // end of class connectFour
