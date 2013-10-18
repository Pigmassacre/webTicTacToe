/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.webtictactoe.tictactoe.core;

import com.github.webtictactoe.tictactoe.core.Game.Mark;
import java.util.HashMap;

/**
 *
 * @author emileriksson
 * @author pigmassacre
 */
public class GameSession {
    private Player p1;
    private Player p2;
    
    private Player activePlayer;
    
    private HashMap<Player, Mark> markMap = new HashMap<>();
    
    private IGame game;
    
    public GameSession(IGame g, Player p1, Player p2){
        game = g;
        this.p1 = activePlayer = p1;
        markMap.put(p1, Mark.CROSS);
        this.p2 = p2;
        markMap.put(p2, Mark.CIRCLE);
    }
    
    public boolean move(int x, int y, Player p){
        if(p.equals(activePlayer)){
            if(game.isFree(x, y)){
                game.move(x, y, markMap.get(p));
                if(activePlayer == p1)
                    activePlayer = p2;
                else
                    activePlayer = p1;
                return true; // The move was successful.
            }
        }
        return false; // The move was unsuccessful.
    }
    
    public Mark[][] getBoard(){
        return game.getBoard();
    }
    
    public Boolean gameWon() {
        return game.gameWon();
    }
    
    public Player getPlayerOne() {
        return p1;
    }
    
    public Player getPlayerTwo() {
        return p2;
    }
    
    public Mark getMarkForPlayer(Player player) {
        return markMap.get(player);
    }
    
}
