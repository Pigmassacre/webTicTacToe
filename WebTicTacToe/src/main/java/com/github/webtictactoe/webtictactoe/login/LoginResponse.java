package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The message the server sends to a client that tries to log in.
 * @author pigmassacre
 */
@XmlRootElement
public class LoginResponse {

    public String message;
    public String name;

    public LoginResponse(String message) {
        this.message = message;
    }
    
    public LoginResponse(String message, String name) {
        this.message = message;
        this.name = name;
    }

    public LoginResponse() {
        
    }
    
}