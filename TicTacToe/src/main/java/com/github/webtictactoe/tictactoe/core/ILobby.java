package com.github.webtictactoe.tictactoe.core;

import java.util.List;

/**
 * Public interface for the shop
 * @author hajo
 */
public interface ILobby {
    
    public void register(String name, String password);
    
    public void login(String name, String password);
    
    public void logout(String name);
    
    public List<Player> getOnlinePlayers();
    
    public void findGame(Player p1, int size);
  
}
