package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.Player;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.Cookie;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.HeaderConfig;

@Path("/")
@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class LobbyResource {
    
    private static ILobby lobby = Lobby.INSTANCE.getLobby();
    private @CookieParam(value = "name") String name;
    
    @GET
    @Suspend(contentType = "application/json", listeners = {OnDisconnect.class})
    @Path("/playerlist")
    public String suspend() {
        System.out.println("Suspending playerlist connection for " + name);
        return "";
    }
    
    @POST
    @Broadcast(writeEntity = false)
    @Produces("application/json")
    @Path("/playerlist")
    public Playerlist broadcastPlayerlist() {
        System.out.println("broadcastPlayerlist() called by " + name);
        return getPlayerlist();
    }
    
    private static Playerlist getPlayerlist() {
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
    public Response logout() {
        Boolean success = lobby.logout(name);

        System.out.println("Logout() called by " + name);
        
        if (success) {
            System.out.println("Logout succeded for " + name);
            return Response
                    .ok()
                    .entity(new LogoutResponse("You have been successfully logged out!"))
                    .cookie(new NewCookie("name", "", "/", "", "", 0, false))
                    .cookie(new NewCookie("password", "", "/", "", "", 0, false))
                    .build();
        } else {
            System.out.println("Logout failed for " + name);
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
            String transport = event.getResource().getRequest().getHeader(HeaderConfig.X_ATMOSPHERE_TRANSPORT);
            /*if (transport != null && transport.equalsIgnoreCase(HeaderConfig.DISCONNECT)) {*/
            // Scenario 2: Browser closed the connection.
            String nameFromCookie = "";
            for (Cookie cookie: event.getResource().getRequest().getCookies()) {
                if (cookie.getName().equals("name")) {
                    nameFromCookie = cookie.getValue();
                }
            }

            lobby.logout(nameFromCookie);            
            event.broadcaster().broadcast(getPlayerlist());
            System.out.println(nameFromCookie + " was automatically logged out!");
            /*} else {
                 // Scenario 1: Long-Polling Connection resumed.
                System.out.println("Long-polling connection resumed.");
            }*/
        }
        
    }
    
}
