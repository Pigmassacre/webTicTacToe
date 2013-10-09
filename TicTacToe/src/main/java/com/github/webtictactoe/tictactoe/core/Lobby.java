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
    
    public List<Player> onlinePlayerList = new ArrayList();
    
    PlayerRegistry playerRegistry;
    
    public Lobby(String persistenceUnitName){
        playerRegistry = new PlayerRegistry(persistenceUnitName);
    }
    
    public List<Player> getPlayerList() {
        return onlinePlayerList;
    }
    
    public void login(String name){
        Player p = playerRegistry.find(name);
        if(p != null)
            onlinePlayerList.add(p);
    }
    
    public void logout(String name) {
        Player p = playerRegistry.find(name);
        if(p != null)
            onlinePlayerList.remove(p);
    }
    
    public List<Player> getOnlinePlayers(){
        return onlinePlayerList;
    }
    
    
    /**
     * findGame creates a game and 
     * forces another player to join p1
     * @param p1 player that want to join a game
     * @param size Size of the board
     */
    public void findGame(Player p1, int size) {
        
        onlinePlayerList.remove(p1);
        
        if(!onlinePlayerList.isEmpty()){
            Player p2 = onlinePlayerList.get(0);
            
            
        }
    }
    
}
