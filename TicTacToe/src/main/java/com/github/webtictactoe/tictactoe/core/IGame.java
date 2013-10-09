package com.github.webtictactoe.tictactoe.core;

/**
 * Public interface for the shop
 * @author hajo
 */
public interface IGame {

    public IPlayerRegistry getPlayerRegistry();
    
    public void move(Player p, int x, int y);
    
    public Player getActivePlayer();
    
    public Game.Mark[][] getBoard();
    
    

}
