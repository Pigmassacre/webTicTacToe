package com.github.webtictactoe.webtictactoe.lobby;

import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.LobbyFactory;

/**
 * We use this to access the lobby in the web app. This keeps the 
 * lobby a singleton.
 * 
 * Usage: Lobby.INSTANCE.getLobby() returns an ILobby object that allows
 * access to the model-Lobby methods.
 * 
 * @author pigmassacre
 */
public enum Lobby {
    INSTANCE;
    
    private final ILobby lobby;
    
    private static final String persistenceUnitName = "game_pu";
    
    private Lobby() {
        lobby = LobbyFactory.getLobby(persistenceUnitName);
    } 
    
    public ILobby getLobby() {
        return lobby;
    }
    
}