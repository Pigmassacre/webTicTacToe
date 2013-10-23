/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.webtictactoe.tictactoe.core;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the main class of the model. The WebApp uses the LobbyFactory
 * to create an instance of the lobby (as a singleton, see Lobby.java in the WebApp).
 * 
 * This class provides methods to access everything the WebApp needs.
 * 
 * @author emileriksson
 */
public class Lobby implements ILobby {
    
    private List<Player> onlinePlayerList = new ArrayList();
    private List<GameSession> activeGames = new ArrayList();
    
    private PlayerRegistry playerRegistry;
    
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
        
        if(p != null && password.equals(p.getPassword())) {
            if (!onlinePlayerList.contains(p)) {
                onlinePlayerList.add(p);
            }
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
    
    @Override
    public List<GameSession> getActiveGames(){
        return activeGames;
    }
    
    @Override
    public PlayerRegistry getPlayerRegistry(){
        return playerRegistry;
    }
    
    /**
     * findGame creates a game and
     * forces another player to join p1 and something
     * @param p1 player that want to join a game
     * @param size Size of the board
     */
    @Override
    public GameSession findGame(Player p1, int size) {
        
        onlinePlayerList.remove(p1);
        
        if(!onlinePlayerList.isEmpty()){
            Player p2 = onlinePlayerList.remove(0);
            Game newGame = new Game(size);
            GameSession newGameSession = new GameSession(newGame, p1, p2);
            activeGames.add(newGameSession);
            
            return newGameSession;
        }
        
        // No game session could be created, therefore we have no real choice but to return null. :/
        return null;
    }
    
}