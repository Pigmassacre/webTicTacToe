package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.Player;
import java.util.List;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;

@Path("/")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class LobbyResource {
    
    private ILobby lobby = Lobby.INSTANCE.getLobby();
    
    @GET
    @Suspend(contentType = "application/json", listeners = {OnDisconnect.class})
    @Path("/playerlist")
    public String suspend() {
        System.out.println("Suspending connection!");
        return "";
    }
    
    @POST
    @Broadcast(writeEntity = false)
    @Produces("application/json")
    @Path("/playerlist")
    public Playerlist updatePlayerlist() {
        Playerlist playerlist = new Playerlist();
        
        System.out.println("updatePlayerlist() called");
        
        List<Player> onlinePlayers = lobby.getOnlinePlayers();
        
        // Populate the userlist with the name of each online player.
        for (Player player : onlinePlayers) {
            playerlist.addUsername(player.getName());
        }
        
        return playerlist;
    }
    
    @POST
    @Produces("application/json")
    @Path("/logout/")
    public LogoutResponse logout(LogoutMessage logoutMessage) {
        String message;
        Boolean success = lobby.logout(logoutMessage.name);
        
        // TODO: figure out how to properly handle logging in/out. As it works right now
        // the server logs in to the model correctly, and then the client side simply
        // subscribes to the lobbyresource and recieves broadcasts from that.
        // However, if the client reloads the page all context is lost and the client has to
        // login again. Do we save information in a cookie, clientside, or do we do
        // something more advanced? This logout function here needs the name, but that
        // is where I got stuck. Currently there's no way each client knows what user it is signed in as.
        
        if (success) {
            message = "You have been successfully logged out!";
        } else {
            message = "Failed to log out, try again!";
        }
        
        return new LogoutResponse(message, success);
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
