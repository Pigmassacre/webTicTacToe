package com.github.webtictactoe.tictactoe.core;

import com.github.webtictactoe.tictactoe.utils.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A player. Name is the ID in the database, as seen in AbstractEntity.
 * 
 * @author hajo
 * @author pigmassacre
 */
@Entity
@Table
public class Player extends AbstractEntity {

    @Column
    private Integer score;
    
    @Column
    private String password;

    public Player() {
    }

    public Player(String name, String password) {
        super(name);
        this.password = password;
        this.score = 1500;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Player{" + "name=" + getName() + ", password=" + getPassword() + ", score=" + getScore() + "}";
    }

}
