
package com.github.webtictactoe.tictactoe.core;

/**
 * We're using the factory pattern for if we ever want to do something more advanced here.
 * 
 * @author pigmassacre
 */
public class LobbyFactory {
    
    // Lobby should be a singleton
    private static Lobby INSTANCE;
    
    private LobbyFactory() {
        
    }
    
    public static Lobby getLobby(String persistenceUnitName) {
        if(INSTANCE == null )
            INSTANCE = new Lobby(persistenceUnitName);
        return INSTANCE;
    }
    
}
