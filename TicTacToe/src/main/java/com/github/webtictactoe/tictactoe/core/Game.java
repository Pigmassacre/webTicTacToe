package com.github.webtictactoe.tictactoe.core;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Shop is a container for other containers
 * NOTE: Uses Java 1.7
 *
 * @author hajo
 */


public class Game implements IGame {



    public enum Mark {
        EMPTY, CROSS, CIRCLE
    }
    
    private Mark[][] board;
    
    private IPlayerRegistry playerRegistry;    

    public Game(String persistenceUnitName, int size) {
        board = new Mark[size][size];
        playerRegistry = new PlayerRegistry(persistenceUnitName);
        Logger.getAnonymousLogger().log(Level.INFO, "Game alive: {0}", this.hashCode());
    }

    @Override
    public void move(Player p, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getActivePlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mark[][] getBoard() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public IPlayerRegistry getPlayerRegistry() {
        return playerRegistry;
    }
    
    private boolean gameWon(){
        Mark mark;
        boolean won = true;
        //Check Rows
        for(int i = 0; i < board[0].length; i++){
            mark = board[i][0];
            for (int j = 0; j < board[0].length; j++) {
                
                if(!mark.equals(board[i][j]) || mark.equals(Mark.EMPTY)){
                    won = false;
                    break;
                }
            }
            if(won)
                return true;
        }
        
        won = true;
        //Check Columns;
        for(int i = 0; i < board[0].length; i++){
            mark = board[0][i];
            for (int j = 0; j < board[0].length; j++) {
                
                if(!mark.equals(board[j][i]) || mark.equals(Mark.EMPTY)){
                    won = false;
                    break;
                }
            }
            if(won)
                return true;
        }
        
        
        // Check the diagonal
        mark = board[0][0];
        won = true;
        for(int i = 0; i < board[0].length; i++){
            if(!mark.equals(board[i][i]) || mark.equals(Mark.EMPTY)){
                won = false;
                break;
            }
            
        }
        if(won)
           return true;
        
        // Check the other diagonal
        mark = board[0][0];
        won = true;
        for(int i = 0; i < board[0].length; i++){
            if(!mark.equals(board[i][(board[0].length - 1) - i]) 
                    || mark.equals(Mark.EMPTY)){
                won = false;
                break;
            }
            
        }
        
        return won;
        
    }
    
}
