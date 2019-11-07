package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;

public class WishList {

    @POST
    @Path("New")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String New(@FormDataParam("userID") int userID, @FormDataParam("listname") String listname, @FormDataParam("status") Boolean status)
    {
        try{
            if(userID == 0 || listname == null || status == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO WishLists (UserID, ListName, Status) Values(?,?,?)");
            ps.setInt(1,userID);
            ps.setString(2,listname);
            ps.setBoolean(3,status);
            ps.executeUpdate();
            System.out.println("Record added to WishLists table");
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable add items, please see server console for more info.\"}";
        }
    }


}
