package com.github.webtictactoe.webtictactoe;

import com.github.webtictactoe.tictactoe.core.ILobby;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/login/")
public class LoginResource {
    
    private ILobby lobby = Lobby.INSTANCE.getLobby();
    
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
                return Response.ok().entity(new LoginResponse("Login succeeded!")).cookie(new NewCookie("name", loginMessage.name, "/", "", "", -1, false)).build();
            } else {
                // Login failed, so we try to register a new user with these credentials instead.
                success = lobby.register(loginMessage.name, loginMessage.password);
                
                if (success) {
                    lobby.login(loginMessage.name, loginMessage.password);
                    System.out.println("Registered!");
                    return Response.ok().entity(new LoginResponse("You have been registered!")).cookie(new NewCookie("name", loginMessage.name, "/", "", "", -1, false)).build();
                } else {
                    System.out.println("Failed to log in!");
                    return Response.status(400).entity(new LoginResponse("That password doesn't match that username, try again!")).build();
                }
            }
        } else {
            // If the name and / or password is empty, the user is denied registration.
            System.out.println("Reached end of function!");
            return Response.status(400).entity(new LoginResponse("Name and / or password cannot be empty!")).build();
        }
    }
    
}
