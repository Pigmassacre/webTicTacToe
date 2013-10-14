package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.Player;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.jersey.JerseyBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;

@Path("/")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class LobbyResource {
    
    private static ILobby lobby = Lobby.INSTANCE.getLobby();
    private @CookieParam(value = "name") String name;
    private static HashMap<String, String> idMap = new HashMap<String, String>();
    private @Context AtmosphereResource resource;
    
    @GET
    @Suspend(contentType = "application/json", listeners = {OnDisconnect.class})
    @Path("/playerlist")
    public String suspend() {
        System.out.println(resource.getRequest().getHeader("X-Atmosphere-tracking-id"));
        idMap.put(resource.getRequest().getHeader("X-Atmosphere-tracking-id"), name);
        System.out.println("Suspending connection!");
        return "";
    }
    
    @POST
    @Broadcast(writeEntity = false)
    @Produces("application/json")
    @Path("/playerlist")
    public Playerlist broadcastPlayerlist() {
        System.out.println("updatePlayerlist() called by " + name);
        return getPlayerlist();
    }
    
    public static Playerlist getPlayerlist() {
        Playerlist playerlist = new Playerlist();
        
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
                    .cookie(new NewCookie("name", "", "/", "", "", 0, false))
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
            // Gets the name from the idMap, logs out that user and then broadcasts the new playerlist.
            String nameFromId = idMap.get(event.getResource().getRequest().getHeader("X-Atmosphere-tracking-id"));
            lobby.logout(nameFromId);
            event.broadcaster().broadcast(getPlayerlist());
            System.out.println(nameFromId + " was automatically logged out!");
        }
    }
    
}
