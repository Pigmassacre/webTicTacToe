package com.github.webtictactoe.tictactoe.core;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is basically a GameBoard, or a GameInstance if you will.
 * Has methods to perform moves, and has the logic for deciding
 * if the game has been won or not (and the Mark that won).
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
    public Mark move(int x, int y, Mark mark) {
        board[x][y] = mark;
        
        // Check if mark has won for the row mark was placed in
        for (int i = 0; i < board.length; i++){
            if (!board[x][i].equals(mark)) {
                break;
            }
            if (i == board.length - 1) {
                return mark;
            }
        }
        
        // Check if mark has won for the column mark was placed in
        for (int i = 0; i < board.length; i++){
            if (!board[i][y].equals(mark)) {
                break;
            }
            if (i == board.length - 1) {
                return mark;
            }
        }
        
        // If we're on the normal diagonal
        if (x == y) {
            // Check if mark has won for the normal diagonal
            for (int i = 0; i < board.length; i++) {
                if (!board[i][i].equals(mark)) {
                    break;
                }
                if (i == board.length - 1) {
                    return mark;
                }
            }
        }
        
        // Check if mark has won for the antidiagonal mark was placed in
        for (int i = 0; i < board.length; i++){
            if (!board[i][(board.length - 1) - i].equals(mark)) {
                break;
            }
            if (i == board.length - 1) {
                return mark;
            }
        }
        
        // If mark didn't win, we return the EMPTY mark.
        return Mark.EMPTY;
    }
    
    @Override
    public Mark[][] getBoard() {
        return board;
    }
    
}
