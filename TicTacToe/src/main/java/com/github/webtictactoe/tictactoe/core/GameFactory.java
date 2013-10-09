package com.github.webtictactoe.tictactoe.core;

/**
 * Static factory for Shops
 *
 * @author hajo
 */
public class GameFactory {

    private GameFactory() {
    }

    // If initTestData there will be some data to use
    public static IGame getGame(String persistenceUnitName, int size) {
        return new Game(persistenceUnitName, size);
    }

}
