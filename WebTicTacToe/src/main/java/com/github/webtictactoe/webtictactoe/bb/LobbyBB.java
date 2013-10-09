/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.webtictactoe.webtictactoe.bb;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author pigmassacre
 */
@Named("lobby")
@ApplicationScoped
public class LobbyBB implements Serializable {
    
    public LobbyBB() {
        System.out.println("LobbyBB is alive: " + this);
    }
    
    public void login() {
        System.out.println("Logging in!");
    }
    
}
