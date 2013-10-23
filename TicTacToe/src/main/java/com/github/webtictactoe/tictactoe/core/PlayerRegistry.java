package com.github.webtictactoe.tictactoe.core;

import com.github.webtictactoe.tictactoe.utils.AbstractDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * All players. Provides some useful methods to access Players from the database.
 *
 * @author hajo
 * @author pigmassacre
 */
public final class PlayerRegistry extends AbstractDAO<Player, String>
        implements IPlayerRegistry {

    public PlayerRegistry(String puName) {
        super(Player.class, puName);
    }
    
    @Override
    public List<Player> getAll() {
        return getRange(0, getCount());
    }

    @Override
    public List<Player> getByName(String name) {
        List<Player> found = new ArrayList<>();
        for (Player c : getRange(0, getCount())) {
            if (c.getName().equals(name)) {
                found.add(c);
            }
        }
        return found;
    }
}
