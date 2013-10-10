package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.LobbyFactory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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