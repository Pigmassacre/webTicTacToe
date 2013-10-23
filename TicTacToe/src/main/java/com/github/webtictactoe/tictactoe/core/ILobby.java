package com.github.webtictactoe.tictactoe.core;

import java.util.List;

/**
 * Public interface for the Lobby.
 * @author hajo (from shop model)
 */
public interface ILobby {
    
    public Boolean register(String name, String password);
    
    public Boolean login(String name, String password);
    
    public Boolean logout(String name);
    
    public List<Player> getOnlinePlayers();
    
    public List<GameSession> getActiveGames();
    
    public PlayerRegistry getPlayerRegistry();
    
    public GameSession findGame(Player p1, int size);
  
}
