import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class maxconnect4Test {
	
	
	@Test
	public void test1() {
		String game_mode = "one-move";				// the game mode
	    String input = "test1.txt";					// the input game file
	    String output = "output1.txt";				    // the output game file
	    int depthLevel =5;  		// the depth level of the ai search
			
	    
	    // create and initialize the game board
	    GameBoard currentGame = new GameBoard(input);
	    int playColumn = 99;				//  the players choice of column to play
	    boolean playMade = false;			//  set to true once a play has been made
	    
	 // create the Ai Player
        AiPlayer player1 = new AiPlayer(depthLevel,1);
        AiPlayer player2 = new AiPlayer(depthLevel,2);
        
        ArrayList<AiPlayer> players = new ArrayList<AiPlayer>();
        players.add(player1);
        players.add(player2);
        
            int current_player = currentGame.getCurrentTurn();
            // AI play
            playColumn = players.get(currentGame.getCurrentTurn()-1).findBestPlay(currentGame);
            assertEquals(playColumn,0);
	}

	@Test
	public void test2() {
		String game_mode = "one-move";				// the game mode
	    String input = "test2.txt";					// the input game file
	    String output = "output2.txt";				    // the output game file
	    int depthLevel =5;  		// the depth level of the ai search
			
	    
	    // create and initialize the game board
	    GameBoard currentGame = new GameBoard(input);
	    int playColumn = 99;				//  the players choice of column to play
	    boolean playMade = false;			//  set to true once a play has been made
	    
	 // create the Ai Player
        AiPlayer player1 = new AiPlayer(depthLevel,1);
        AiPlayer player2 = new AiPlayer(depthLevel,2);
        
        ArrayList<AiPlayer> players = new ArrayList<AiPlayer>();
        players.add(player1);
        players.add(player2);
        
            int current_player = currentGame.getCurrentTurn();
            // AI play
            playColumn = players.get(currentGame.getCurrentTurn()-1).findBestPlay(currentGame);
            assertEquals(5,playColumn);
	}
	
}
