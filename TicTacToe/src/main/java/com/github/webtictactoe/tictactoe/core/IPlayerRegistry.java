package com.github.webtictactoe.tictactoe.core;

import com.github.webtictactoe.tictactoe.utils.IDAO;
import java.util.List;

/**
 * Interface to player registry
 * @author hajo
 * @author pigmassacre (still mostly hajo :P)
 */
public interface IPlayerRegistry extends IDAO<Player, String> {

    List<Player> getAll();
    
    List<Player> getByName(String name);
    
}
