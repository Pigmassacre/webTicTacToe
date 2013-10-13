
package com.github.webtictactoe.tictactoe.core;

/**
 * We're using the factory pattern for if we ever want to do something more advanced here.
 * 
 * @author pigmassacre
 */
public class LobbyFactory {
    
    // Lobby should be a singleton
    // I agree, but it is accessed as a singleton in the webapp, so no need for it here! :)
    
    private LobbyFactory() {
        
    }
    
    public static Lobby getLobby(String persistenceUnitName) {
        return new Lobby(persistenceUnitName);
    }
    
}
