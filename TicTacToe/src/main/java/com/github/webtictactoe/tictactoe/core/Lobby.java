
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

            playerRegistry.add(new Player(name, password));
            return true;
        }
        // Cannot register a new player because a player with that name already exists.
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
        // Name and / or password didn't match any player.
        return false;
    }
    
    @Override
    public Boolean logout(String name) {
        Player p = playerRegistry.find(name);
        
        if(p != null) {
            onlinePlayerList.remove(p);
            return true;
        }
        // Cannot logout a player that isn't in the onlinePlayerList.
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
     * Creates a game, then a GameSession that maps
     * that game to two players.
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