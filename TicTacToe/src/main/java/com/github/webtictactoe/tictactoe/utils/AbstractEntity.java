
package com.github.webtictactoe.tictactoe.utils;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Originally was AbstractEntity from Hajos shop model.
 * Base class for all entities (later to be stored in database), 
 * Product, Order, etc
 * @author hajo
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Column(nullable = false)
    private String name;
   
    protected AbstractEntity(){
    }
    
    protected AbstractEntity(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
