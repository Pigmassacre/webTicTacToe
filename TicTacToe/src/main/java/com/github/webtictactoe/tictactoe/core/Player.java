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
@Table(name="PLAYER")
public class Player extends AbstractEntity {

    @Column
    private Integer score;
    
    private Game.Mark mark;
    
    public Player() {
    }

    public Player(String name) {
        super(name);
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public Game.Mark getMark(){
        return mark;
    }
    
    public void setMark(Game.Mark mark){
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Player{" + "name=" + getName() + "}";
    }
}
