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
    public List<Player> getPlayerList() {
        return onlinePlayerList;
    }
    
    @Override
    public void register(String name, String password){
        playerRegistry.add(new Player(name, password));
    }
    
    @Override
    public Boolean login(String name, String password){
        Player p = playerRegistry.find(name);
        if(p != null){
            if(password.equals(p.getPassword()))
                onlinePlayerList.add(p);
                return true;
        }
        // If the login fails, we return false.
        return false;
    }
    
    @Override
    public Boolean logout(String name) {
        Player p = playerRegistry.find(name);
        if(p != null) {
            onlinePlayerList.remove(p);
            return true;
        }
        // If the logout fails for some reason, we return false.
        return false;
    }
    
    @Override
    public List<Player> getOnlinePlayers(){
        return onlinePlayerList;
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
            Player p2 = onlinePlayerList.get(0);
            activeGames.add(new GameSession(new Game(size),p1, p2));
        }
    }
    
}
