package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginResponse {

    public String message;
    public Boolean success;

    public LoginResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public LoginResponse() {
        
    }
    
}