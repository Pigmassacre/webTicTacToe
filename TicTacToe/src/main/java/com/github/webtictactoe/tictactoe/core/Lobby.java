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
public class Lobby implements ILobby {
    
    private List<Player> onlinePlayerList = new ArrayList();
    private List<GameSession> activeGames = new ArrayList();
    
    PlayerRegistry playerRegistry;
    
    public Lobby(String persistenceUnitName){
        playerRegistry = new PlayerRegistry(persistenceUnitName);
    }
    
    @Override
    public Boolean register(String name, String password){
        if (playerRegistry.find(name) == null) {
            // If the name doesn't already exist, we create a new player and add it to the registry.
            playerRegistry.add(new Player(name, password));
            return true;
        }
        // Else, if the name already exists, we return false.
        return false;           
    }
    
    @Override
    public Boolean login(String name, String password){
        Player p = playerRegistry.find(name);
        
        if(p != null &&
           password.equals(p.getPassword()) &&
           !onlinePlayerList.contains(p)) {
           onlinePlayerList.add(p);
           return true;
        }
        // We return false if the login fails for any reason.
        return false;
    }
    
    @Override
    public Boolean logout(String name) {
        Player p = playerRegistry.find(name);
        
        if(p != null) {
            onlinePlayerList.remove(p);
            return true;
        }
        // We return false if the logout fails for any reason.
        return false;
    }
    
    @Override
    public List<Player> getOnlinePlayers(){
        return onlinePlayerList;
    }
    
    public List<GameSession> getActiveGames(){
        return activeGames;
    }
    
    
    /**
     * findGame creates a game and 
     * forces another player to join p1 and something
     * @param p1 player that want to join a game
     * @param size Size of the board
     */
    @Override
    public void findGame(Player p1, int size) {
        
        onlinePlayerList.remove(p1);
        
        if(!onlinePlayerList.isEmpty()){
            Player p2 = onlinePlayerList.remove(0);
            activeGames.add(new GameSession(new Game(size),p1, p2));
        }
    }
    
}