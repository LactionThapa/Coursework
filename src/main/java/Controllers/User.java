package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("Users/")
public class User {
    //This method selects a row of data
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String ListUsers() {
        System.out.println("Users/list");
        JSONArray list = new JSONArray();
        try
        {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Password, EmailAddress FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                int userID = results.getInt(1);
                String username = results.getString(2);
                String password = results.getString(3);
                String emailAddress = results.getString(4);
                System.out.println(userID + " " + username + " " + password + " " + emailAddress);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";
        }

    }

    //This method a pre-existing data in the database
    @POST
    @Path("reset")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public static String Reset(@FormDataParam("username") String username, @FormDataParam("password") String password)
    {
        try{
            if(username == null || password == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Password = ? WHERE Username = ?");
            ps.setString(2, username);
            ps.setString(1, password);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to reset password, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("rename")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public static String Rename(@FormDataParam("oldUsername") String oldUsername, @FormDataParam("newUsername") String newUsername)
    {
        try{
            if(oldUsername == null || newUsername == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Username = ? WHERE Username = ?");
            ps.setString(1, newUsername);
            ps.setString(2, oldUsername);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to rename username, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("email")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public static String Email(@FormDataParam("username") String username, @FormDataParam("emailaddress") String emailaddress)
    {
        try{
            if(username == null || emailaddress == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET EmailAddress = ? WHERE Username = ?");
            ps.setString(2, username);
            ps.setString(1, emailaddress);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to change email, please see server console for more info.\"}";
        }
    }

    //This method inserts data in the database
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String New(@FormDataParam("username") String username, @FormDataParam("password") String password, @FormDataParam("emailaddress") String emailaddress)
    {
        try{
            if(username == null || password == null || emailaddress == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (Username, Password, EmailAddress) Values(?,?,?)");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,emailaddress);
            ps.executeUpdate();
            System.out.println("Record add to Users table");
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to create new user, please see server console for more info.\"}";
        }
    }

    //This method deletes a record in the table
    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Delete(@FormDataParam("username") String username){
        try{
            if(username == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Users/delete username=" + username);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE Username = ?");
            ps.setString(1,username);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to delete user, please see server console for more info.\"}";
        }
    }
}
