package com.github.webtictactoe.webtictactoe.lobby;

import com.github.webtictactoe.tictactoe.core.ILobby;
import com.github.webtictactoe.tictactoe.core.Player;
import com.github.webtictactoe.webtictactoe.game.Playerlist;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.ws.rs.CookieParam;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.jersey.JerseyBroadcaster;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.HeaderConfig;

/**
 * This is the resource used to handle broadcasting the online playerlist to all
 * connected players and also logging out players who wish to do so.
 * @author pigmassacre
 */
@Path("/")
@AtmosphereService(broadcaster = JerseyBroadcaster.class) // Used when using atmosphere and jersey together.
public class LobbyResource {
    
    private static ILobby lobby = Lobby.INSTANCE.getLobby();
    private @CookieParam(value = "name") String name;
    
    /**
     * Suspends a connection to this broadcaster.
     * @return 
     */
    @GET
    @Suspend(contentType = "application/json", listeners = {OnDisconnect.class})
    @Path("/playerlist")
    public String suspend() {
        System.out.println("Suspending playerlist connection for " + name);
        return "";
    }
    
    /**
     * Broadcasts the playerlist to all suspended connections.
     * @return 
     */
    @POST
    @Broadcast(writeEntity = false)
    @Produces("application/json")
    @Path("/playerlist")
    public Playerlist broadcastPlayerlist() {
        System.out.println("broadcastPlayerlist() called by " + name);
        return getPlayerlist();
    }
    
    /**
     * Simple helpmethod to return a list of online players.
     * @return 
     */
    private static Playerlist getPlayerlist() {
        Playerlist playerlist = new Playerlist();
        
        List<Player> onlinePlayers = lobby.getOnlinePlayers();
        
        // Populate the userlist with the name of each online player.
        for (Player player : onlinePlayers) {
            playerlist.addUsername(player.getName());
        }
        
        return playerlist;
    }
    
    /**
     * Logs out the user matching the name provided by the 'name' cookie.
     * @return 
     */
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
         * This is to make sure that clients who lose their connection to the server are automatically logged out.
         */
        @Override
        public void onDisconnect(AtmosphereResourceEvent event) {
            String transport = event.getResource().getRequest().getHeader(HeaderConfig.X_ATMOSPHERE_TRANSPORT);
            String nameFromCookie = "";
            for (Cookie cookie: event.getResource().getRequest().getCookies()) {
                if (cookie.getName().equals("name")) {
                    nameFromCookie = cookie.getValue();
                }
            }

            lobby.logout(nameFromCookie);            
            event.broadcaster().broadcast(getPlayerlist());
            System.out.println(nameFromCookie + " was automatically logged out!");
        }
        
    }
    
}
