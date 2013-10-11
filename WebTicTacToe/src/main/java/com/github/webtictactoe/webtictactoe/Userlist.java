package com.github.webtictactoe.webtictactoe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Userlist {

    public List<String> names;

    public Userlist() {
        this.names = new ArrayList<String>();
    }
    
    public void addUsername(String name) {
        this.names.add(name);
    }

}