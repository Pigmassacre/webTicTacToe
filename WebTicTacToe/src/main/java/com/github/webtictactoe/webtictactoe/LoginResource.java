package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.jersey.JerseyBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;

@Path("/login/")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class LoginResource {
    
    private ILobby lobby = Lobby.INSTANCE.getLobby();
    
    @Suspend(contentType = "application/json", listeners = {OnDisconnect.class})
    @GET
    public String suspend() {
        return "";
    }
    
    @Broadcast(writeEntity = false)
    @POST
    @Produces("application/json")
    public LoginResponse broadcast(LoginMessage loginMessage) {
        String message;
        Boolean success = lobby.login(loginMessage.name, loginMessage.password);
        
        System.out.println("login() called with data: " + loginMessage.name + " / " + loginMessage.password);
        
        if(success) {
            message = "Login succeeded!";
        } else {
            // Login failed, so we try to register a new user with these credentials instead.
            success = lobby.register(loginMessage.name, loginMessage.password);
            
            if (success) {
                message = "Registration succeeded!";
            } else {
                message = "That password doesn't match that username, try again!";
            }
        }
        
        return new LoginResponse(message, success);
    }
    
    public static final class OnDisconnect extends AtmosphereResourceEventListenerAdapter {
        /**
         * {@inheritDoc}
         */
        @Override
        public void onDisconnect(AtmosphereResourceEvent event) {
            System.out.println(event);
        }
    }
 
}
