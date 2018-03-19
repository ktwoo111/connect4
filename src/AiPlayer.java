import java.util.*;

/**
 * This is the AiPlayer class.  
 * 
 * It simulates a minimax player for the max
 * connect four game.
 * 
 * Implementing Alpha Beta Pruning with Depth Limited Search
 * 
 * @author Taewoo Kim
 *
 */

public class AiPlayer 
{
	private int depthLimit;
	private int playerNum;
	private int oppNum;
    /**
     * The constructor essentially does nothing except instantiate an
     * AiPlayer object.
     *
     */
    public AiPlayer(int depthLimit, int playerNum) 
    {
    	this.depthLimit = depthLimit;
    	this.playerNum = playerNum;
    	SetNums(playerNum);
    }
    
    
    public void SetNums(int player){
    	this.playerNum = player;
    	if(playerNum == 1){
    		this.oppNum = 2;
    	}
    	else{
    		this.oppNum = 1;  		
    	}
    	
    }
    public int Evaluation(GameBoard board, int depth){
    	int current = board.getCurrentTurn();
    	int opp = -1;
    	if(current == 1){
    		opp = 2;
    	}
    	else{
    		opp = 1;  		
    	}
    	
    	/*
    	int fours = NumStreaks(board,playerNum,4);
    	int threes = NumStreaks(board,playerNum,3);
    	int twos = NumStreaks(board,playerNum,2);
    	
    	int oFours = NumStreaks(board,oppNum,4);
    	int oThrees = NumStreaks(board,oppNum,3);
    	int oTwos = NumStreaks(board,oppNum,2);
    	*/
    	
    	
    	int fours = NumStreaks(board,current,4);
    	int threes = NumStreaks(board,current,3);
    	int twos = NumStreaks(board,current,2);
    	
    	int oFours = NumStreaks(board,opp,4);
    	int oThrees = NumStreaks(board,opp,3);
    	int oTwos = NumStreaks(board,opp,2);
    	
    	
    	if(oFours > 0){
    		return -10000 - depth;
    		
    	}
    	
    	return (fours*1000 + threes*100 + twos) - (oThrees*100 +oTwos) + depth;
    	
    	//return board.getScore(current) - board.getScore(opp);
    }
    
    public int NumStreaks(GameBoard board,int playerNumber, int streak){
    	int count = 0;
    	for(int i = 0; i < 6; i++){
    		for (int j = 0; j < 7; j++){
    			if(board.getGameBoard()[i][j] == playerNumber){
    				count += VerticalStreak(i,j,board,streak);
    				count += HorizontalStreak(i,j,board,streak);
    				count+= DiagonalStreak(i,j,board,streak);
    			}  			
    			
    		}
    		
    	}
    	return count;
    }
    
    public int VerticalStreak(int row, int col, GameBoard board, int streak){
    	int count = 0;
    	for (int i = row; i <6; i++){
    		if(board.getGameBoard()[i][col] == board.getGameBoard()[row][col]){
    			count++;			
    		}
    		else{
    			break;
    		}
    		
    	}
    	if(count >= streak){
    		return 1;
    	}
    	else{
    		return 0;
    		
    	}
    }
    
    public int HorizontalStreak(int row, int col, GameBoard board, int streak){
    	int count = 0;
    	for (int i = col; i <7; i++){
    		if(board.getGameBoard()[row][i] == board.getGameBoard()[row][col]){
    			count++;			
    		}
    		else{
    			break;
    		}
    		
    	}
    	if(count >= streak){
    		return 1;
    	}
    	else{
    		return 0;
    		
    	}
    }
    
    public int DiagonalStreak(int row, int col, GameBoard board, int streak){
    	int totalDiagonal = 0;
    	int count = 0;
    	
    	int column = col;
    	for(int i = row; i < 6; i++){
    		if(column > 6){
    			break;
    		}
    		else if(board.getGameBoard()[i][column] == board.getGameBoard()[row][col]){
    			count++;
    		}
    		else{
    			break;
    		}
    		column++;
    		
    	}
    	
    	if( count >= streak){
    		totalDiagonal++;	
    	}
    	column = col;
    	for(int i = row; i > 0; i--){
    		if(column > 6){
    			break;
    		}
    		else if(board.getGameBoard()[i][column] == board.getGameBoard()[row][col]){
    			count++;
    		}
    		else{
    			break;
    		}
    		column++;
    	}
    	
    	if( count >= streak){
    		totalDiagonal++;	
    	}
    	
    	return totalDiagonal;  	
    }
   
    
    
    
    
    
    
    /**
     * This method plays a piece randomly on the board
     * @param currentGame The GameBoard object that is currently being used to
     * play the game.
     * @return an integer indicating which column the AiPlayer would like
     * to play in.
     */
public int[] DepthAlphaBeta(int currentDepth, GameBoard boardSim, int alpha, int beta, int currentPlayer){
		
    	
    	if(currentDepth == 0 || boardSim.getPieceCount() == 42){
			int[] returnValue = new int[2];
			returnValue[0] = Evaluation(boardSim, currentDepth);
			returnValue[1] = -5;
    		return returnValue;		
		}
    	
    	
    	if(boardSim.getCurrentTurn() == currentPlayer){
    	int[] returnValue = new int[2];
    	int bestValue = -999999;
    	int bestMove = -10;
    	for(int i = 0; i < 7; i++){ //putting a piece in each column
			GameBoard modified = new GameBoard(boardSim.getGameBoard());
			if(modified.isValidPlay(i)){
			modified.playPiece(i);
			
			int[] value = DepthAlphaBeta(currentDepth-1, modified, alpha, beta, modified.getCurrentTurn());
			//System.out.print("col: " + i + "  ");
			//System.out.println(value[0]);
			int previous = bestValue;
			bestValue = Math.max(bestValue, value[0]);
			if(previous != bestValue){
				bestMove = i;
			}
			//alpha = Math.max(alpha, bestValue);
			if(bestValue > alpha){
				alpha = bestValue;
				bestMove = i;
			}
			if (beta <= alpha){
				break;
			}
			}
		}
    	returnValue[0] = bestValue;
    	returnValue[1] = bestMove;
		return returnValue;
    	}
    	else{
    		int[] returnValue = new int[2];
        	int bestValue = 999999;
        	int bestMove = -10;
        	for(int i = 0; i < 7; i++){ //putting a piece in each column
    			GameBoard modified = new GameBoard(boardSim.getGameBoard());
    			if(modified.isValidPlay(i)){
    			modified.playPiece(i);
    			int[] value = DepthAlphaBeta(currentDepth-1, modified, alpha, beta, modified.getCurrentTurn()); 
    			int previous = bestValue;
    			bestValue = Math.min(bestValue, value[0]);
    			if(previous != bestValue){
    				bestMove = i;
    			}
    			//beta = Math.min(beta, bestValue);
    			if(bestValue < beta){
    				beta = bestValue;
    				bestMove = i;
    			}
    			if (beta <= alpha){
    				break;
    			}
    			}
    		}
        	returnValue[0] = bestValue;
        	returnValue[1] = bestMove;
    		return returnValue;
    	}
    }
    
    /*
    public int[] DepthAlphaBeta(int currentDepth, GameBoard boardSim, int alpha, int beta, boolean isMaxPlayer){
		
    	
    	if(currentDepth == 0 || boardSim.getPieceCount() == 42){
			int[] returnValue = new int[2];
			returnValue[0] = Evaluation(boardSim, currentDepth);
			returnValue[1] = -5;
    		return returnValue;		
		}
    	
    	   	
    	if(isMaxPlayer){
    	int[] returnValue = new int[2];
    	int bestValue = -999999;
    	int bestMove = -10;
    	for(int i = 0; i < 7; i++){ //putting a piece in each column
			GameBoard modified = new GameBoard(boardSim.getGameBoard());
			if(modified.isValidPlay(i)){
			modified.playPiece(i);
			int[] value = DepthAlphaBeta(currentDepth-1, modified, alpha, beta, false ); 
			//System.out.print("col: " + i + "  ");
			//System.out.println(value[0]);
			int previous = bestValue;
			bestValue = Math.max(bestValue, value[0]);
			if(previous != bestValue){
				bestMove = i;
			}
			//alpha = Math.max(alpha, bestValue);
			if(bestValue > alpha){
				alpha = bestValue;
				bestMove = i;
			}
			if (beta <= alpha){
				break;
			}
			}
		}
    	returnValue[0] = bestValue;
    	returnValue[1] = bestMove;
		return returnValue;
    	}
    	else{
    		int[] returnValue = new int[2];
        	int bestValue = 999999;
        	int bestMove = -10;
        	for(int i = 0; i < 7; i++){ //putting a piece in each column
    			GameBoard modified = new GameBoard(boardSim.getGameBoard());
    			if(modified.isValidPlay(i)){
    			modified.playPiece(i);
    			int[] value = DepthAlphaBeta(currentDepth-1, modified, alpha, beta, true ); 
    			int previous = bestValue;
    			bestValue = Math.min(bestValue, value[0]);
    			if(previous != bestValue){
    				bestMove = i;
    			}
    			beta = Math.min(beta, bestValue);
    			if (beta <= alpha){
    				break;
    			}
    			}
    		}
        	returnValue[0] = bestValue;
        	returnValue[1] = bestMove;
    		return returnValue;
    	}
    }
    */
    public int getDepthLimit() {
		return depthLimit;
	}


	public int getPlayerNum() {
		return playerNum;
	}


	public int getOpponNum() {
		return oppNum;
	}


	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}


	public void setOpponNum(int opponNum) {
		this.oppNum = opponNum;
	}


	public int findBestPlay(GameBoard boardSim) 
    {
    	//depth will always be 0, currentPlayer is whatever player number the AI will be.
    	
		//return DepthAlphaBeta(depthLimit, boardSim,-99999,99999,true)[1];
    	return DepthAlphaBeta(depthLimit, boardSim,-99999,99999,boardSim.getCurrentTurn())[1];
    }
}
