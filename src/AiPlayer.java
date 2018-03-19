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
    	return board.getScore(playerNum) - board.getScore(oppNum);
    }
    
   
         
    /**
     * This method plays a piece randomly on the board
     * @param currentGame The GameBoard object that is currently being used to
     * play the game.
     * @return an integer indicating which column the AiPlayer would like
     * to play in.
     */
    
    public int DepthAlphaBeta(int currentDepth, GameBoard boardSim, int alpha, int beta, boolean isMaxPlayer){
    	if(currentDepth == 0 || boardSim.getPieceCount() == 42){
			return Evaluation(boardSim, currentDepth);
	
		}
    	
    	   	
    	if(isMaxPlayer){
    	int bestValue = -999999;
    	for(int i = 0; i < 7; i++){ //putting a piece in each column
			GameBoard modified = new GameBoard(boardSim.getGameBoard());
			if(modified.isValidPlay(i)){
			modified.playPiece(i);
			int value = DepthAlphaBeta(currentDepth-1, modified, alpha, beta, false ); 
			bestValue = Math.max(bestValue, value);
			alpha = Math.max(alpha, bestValue);
			if (beta <= alpha){
				break;
			}
			}
		}
		return bestValue;
    	}
    	else{
        	int bestValue = 9999999;
        	for(int i = 0; i < 7; i++){ //putting a piece in each column
    			GameBoard modified = new GameBoard(boardSim.getGameBoard());
    			if(modified.isValidPlay(i)){
    			modified.playPiece(i);
    			int value = DepthAlphaBeta(currentDepth-1, modified, alpha, beta, true); 
    			bestValue = Math.min(bestValue, value);
    			beta = Math.min(beta, bestValue);
    			if (beta <= alpha){
    				break;
    			}
    			}
    		}
    		return bestValue;
    	}
    }
    
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
		int bestMove = 8;
		int maxVal = -9999999;
		for (int i = 0; i < 7; i++) {
			GameBoard hi = new GameBoard(boardSim.getGameBoard());
			hi.playPiece(i);
			
			int val = DepthAlphaBeta(depthLimit-1, hi,-9999,9999,false);			
			System.out.print("col: " + i + " ");
			System.out.println(val);
			if (val > maxVal) {
				maxVal = val;
				if(boardSim.isValidPlay(i)) {
				bestMove = i;			
				}
			}
			
		}
		return bestMove;
    }
}
