package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginResponse {

    public String message;

    public LoginResponse(String message) {
        this.message = message;
    }

    public LoginResponse() {
        
    }
    
}