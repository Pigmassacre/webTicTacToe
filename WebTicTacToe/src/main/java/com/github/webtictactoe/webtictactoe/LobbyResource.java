package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.Player;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
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
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
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
    public Response logout(@CookieParam(value = "name") String name) {
        Boolean success = lobby.logout(name);
        
        if (success) {
            return Response
                    .ok()
                    .entity(new LogoutResponse("You have been successfully logged out!"))
                    .cookie(new NewCookie(name, "", "", "", "", 0, false))
                    .build();
        } else {
            return Response
                    .status(400)
                    .entity(new LogoutResponse("Logout failed, try again!"))
                    .build();
        }
    }
    
    public static final class OnDisconnect extends AtmosphereResourceEventListenerAdapter {
        /**
         * {@inheritDoc}
         */
        @Override
        public void onDisconnect(AtmosphereResourceEvent event) {
            System.out.println(event.broadcaster().getAtmosphereResources());
        }
    }
    
}
