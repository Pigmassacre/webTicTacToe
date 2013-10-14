package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import javax.ws.rs.Consumes;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.jersey.JerseyBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;

@Path("/login/")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class LoginResource {
    
    private ILobby lobby = Lobby.INSTANCE.getLobby();
    
    @GET
    @Suspend(contentType = "application/json", listeners = {OnDisconnect.class})
    public String suspend() {
        return "";
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginMessage loginMessage) {
        Boolean success;
        
        System.out.println("login() called with data: " + loginMessage.name + " / " + loginMessage.password);
        
        if (!loginMessage.name.isEmpty() && !loginMessage.password.isEmpty()) {
            // As long as the name and password aren't empty, we try to login.
            success = lobby.login(loginMessage.name, loginMessage.password);
            if(success) {
                //message = "Login succeeded!";
                return Response.ok().cookie(new NewCookie("name", loginMessage.name)).build();
            } else {
                // Login failed, so we try to register a new user with these credentials instead.
                success = lobby.register(loginMessage.name, loginMessage.password);
                
                if (success) {
                    lobby.login(loginMessage.name, loginMessage.password);
                    System.out.println("Registered!");
                    return Response.ok().entity(new LoginResponse("You have been registered!")).cookie(new NewCookie("name", loginMessage.name)).build();
                } else {
                    return Response.status(400).entity(new LoginResponse("That password doesn't match that username, try again!")).build();
                }
            }
        } else {
            // If the name and / or password is empty, the user is denied registration.
            System.out.println("Reached end of function!");
            return Response.status(400).entity(new LoginResponse("Name and / or password cannot be empty!")).build();
        }
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
