/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.webtictactoe.tictactoe.core;

import com.github.webtictactoe.tictactoe.core.Game.Mark;
import java.util.HashMap;

/**
 * This wraps two Player objects (player one and player two) to
 * a Game object, and provides methods to perform game moves
 * and access relevant information (the winner, the active player
 * the mark of each player etc).
 * 
 * @author emileriksson
 * @author pigmassacre
 */
public class GameSession {
    private Player p1;
    private Player p2;
    private Player winner;
    
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
                Mark winningMark = game.move(x, y, markMap.get(p));
                
                if (winningMark.equals(markMap.get(p))) {
                    winner = p;
                    
                    // A dumb version of ELO rating... :P
                    p.setScore(p.getScore() + 15);
                    if (!p.equals(getPlayerOne())) {
                        getPlayerOne().setScore(getPlayerOne().getScore() - 15);
                    } else {
                        getPlayerTwo().setScore(getPlayerTwo().getScore() - 15);
                    }
                }
                
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
    
    public Player getWinner() {
        return winner;
    }
    
    public Player getActivePlayer() {
        return activePlayer;
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
