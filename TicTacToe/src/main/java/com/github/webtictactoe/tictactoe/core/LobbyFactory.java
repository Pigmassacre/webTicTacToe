
package com.github.webtictactoe.tictactoe.core;

/**
 * We're using the factory pattern for if we ever want to do something more advanced here.
 * Accessed as a singleton in the WebApp.
 * 
 * @author pigmassacre
 */
public class LobbyFactory {
    
    private LobbyFactory() {
        
    }
    
    public static Lobby getLobby(String persistenceUnitName) {
        return new Lobby(persistenceUnitName);
    }
    
}
