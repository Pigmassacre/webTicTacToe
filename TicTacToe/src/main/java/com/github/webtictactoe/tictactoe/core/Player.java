package com.github.webtictactoe.tictactoe.core;

import com.github.webtictactoe.tictactoe.utils.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A player. Name is the ID in the database, as seen in AbstractEntity.
 * 
 * @author hajo
 * @author pigmassacre
 */
@Entity
@Table(name="PLAYER")
public class Player extends AbstractEntity {

    public Player() {
    }

    public Player(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Player{" + "name=" + getName() + '}';
    }
}
