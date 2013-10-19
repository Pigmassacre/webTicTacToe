package com.github.webtictactoe.tictactoe.core;

/**
 * Public interface for the shop
 * @author hajo
 */
public interface IGame {
    
    public Game.Mark move(int x, int y, Game.Mark mark);
    
    public boolean isFree(int x, int y);
    
    public Game.Mark[][] getBoard();

}
