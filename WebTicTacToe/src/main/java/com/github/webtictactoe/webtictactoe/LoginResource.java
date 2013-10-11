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

@Path("/")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class LoginResource {
    
    private ILobby lobby = Lobby.INSTANCE.getLobby();
    
    /**
     * Suspend the response without writing anything back to the client.
     *
     * @return a white space
     */
    @Suspend(contentType = "application/json", listeners = {OnDisconnect.class})
    @GET
    public String suspend() {

        return "";
    }

    /**
     * Broadcast the received message object to all suspended response. Do not write back the message to the calling connection.
     *
     * @param message a {@link Message}
     * @return a {@link Response}
     */
    @Broadcast(writeEntity = false)
    @POST
    @Produces("application/json")
    public Response broadcast(Message message) {
        return new Response(message.author, message.message);
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
    /*
    @POST
    @Produces("application/json")
    public LoginResponse login(LoginMessage loginMessage) {
        String message;
        Boolean success = lobby.login(loginMessage.name, loginMessage.password);
        
        System.out.println("login() called");
        
        if(success) {
            message = "Login failed, username or password does not match.";
        } else {
            message = "Login succeeded!";
        }
        
        return new LoginResponse(message, success);
    }
    */
}
