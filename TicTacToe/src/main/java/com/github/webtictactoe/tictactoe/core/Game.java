package com.github.webtictactoe.tictactoe.core;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Shop is a container for other containers
 * NOTE: Uses Java 1.7
 *
 * @author hajo
 */
public class Game implements IGame {

    private IPlayerRegistry playerRegistry;    

    public Game(String persistenceUnitName) {
        playerRegistry = new PlayerRegistry(persistenceUnitName);
        Logger.getAnonymousLogger().log(Level.INFO, "Game alive: {0}", this.hashCode());
    }

    @Override
    public IPlayerRegistry getPlayerRegistry() {
        return playerRegistry;
    }
    
}
