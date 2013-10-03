package com.github.webtictactoe.tictactoe.core;

import com.github.webtictactoe.tictactoe.utils.IDAO;
import java.util.List;

/**
 * Interface to customer registry
 * @author hajo
 */
public interface IPlayerRegistry extends IDAO<Player, String> {

    List<Player> getAll();
    
    List<Player> getByName(String name);
    
}
