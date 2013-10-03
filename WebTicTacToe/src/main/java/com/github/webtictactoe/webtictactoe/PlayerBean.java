/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.webtictactoe.webtictactoe;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.validation.constraints.NotNull;

/**
 *
 * @author pigmassacre
 */
@SessionScoped
public class PlayerBean implements Serializable {
    
    @NotNull
    private String name;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
