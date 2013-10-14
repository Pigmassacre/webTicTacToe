package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

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