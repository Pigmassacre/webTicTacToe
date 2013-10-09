/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.webtictactoe.tictactoe.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emileriksson
 */
public class Lobby {
    
    public List<Player> playerList = new ArrayList();
    
    
    public List<Player> getPlayerList() {
        return playerList;
    }
    
    public void addPlayer(Player p) {
        playerList.add(p);
    }
    
    public void removePlayer(Player p) {
        playerList.remove(p);
    }
    
    /**
     * findGame creates a game and 
     * forces another player to join p1
     * @param p1 player that want to join a game
     * @param size Size of the board
     */
    public void findGame(Player p1, int size) {
        
        playerList.remove(p1);
        
        if(!playerList.isEmpty()){
            Player p2 = playerList.get(0);
            IGame game = GameFactory.getGame(null, size);
            
            p1.join(game);
            p2.join(game);
        }
    }
    
}
