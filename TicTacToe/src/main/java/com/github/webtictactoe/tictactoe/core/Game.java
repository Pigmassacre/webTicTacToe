package com.github.webtictactoe.tictactoe.core;

import java.util.Arrays;
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
    public Game(int size) {
        board = new Mark[size][size];
        
        // Fill the board with Mark.EMPTY.
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                board[x][y] = Mark.EMPTY;
            }
        }
        
        Logger.getAnonymousLogger().log(Level.INFO, "Game created with hashCode: {0}", this.hashCode());
    }

    @Override
    public boolean isFree(int x, int y) {
        return board[x][y].equals(Mark.EMPTY);
    }
    
    @Override
    public void move(int x, int y, Mark mark) {
        board[x][y] = mark;
    }

    @Override
    public Mark[][] getBoard() {
        return board;
    }
    
    @Override
    public boolean gameWon(){
        Mark mark;
        boolean won = true;
        //Check Rows
        for(int i = 0; i < board.length; i++){
            for (int j = 0; j < board[j].length; j++) {
                mark = board[i][j];
                System.out.println("mark is: " + mark + " and mark at " + i + "," + j + " is " + board[i][j]);
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
