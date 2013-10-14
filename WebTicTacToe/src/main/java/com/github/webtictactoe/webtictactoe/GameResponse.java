package com.github.webtictactoe.webtictactoe;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GameResponse {

    public String name;
    // Yes we use an enum in the model, but here a simple X for cross and O for circle will suffice, no?
    // X as in big x, O as in big o.
    public Character[][] stateOfBoard;

    public GameResponse(String name, Character[][] stateOfBoard) {
        this.name = name;
        this.stateOfBoard = stateOfBoard;
    }

    public GameResponse() {
        
    }
    
}