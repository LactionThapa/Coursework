package Controllers;
import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("Permission/")
public class Permission {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String List() {
        try
        {
            JSONArray list = new JSONArray();
            PreparedStatement ps = Main.db.prepareStatement("SELECT ListID, UserID FROM Permission");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("listID",results.getInt(1));
                item.put("userID",results.getInt(2));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list permissions, please se server console for more info.\"}";
        }

    }

    @POST
    @Path("add")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Add(@FormDataParam("listID") Integer listID,@FormDataParam("userID") Integer userID){
        try{
            if(listID == null || userID == null){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Permission (ListID, UserID) Values(?,?)");
            ps.setInt(1,listID);
            ps.setInt(2,userID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list permissions, please se server console for more info.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Delete(@FormDataParam("listID") Integer listID,@FormDataParam("userID") Integer userID){
        try{
            if(listID == null || userID == null ){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Permission WHERE ListID = ? AND UserID = ?");
            ps.setInt(1,listID);
            ps.setInt(2,userID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list permissions, please se server console for more info.\"}";
        }
    }

}
