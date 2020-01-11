package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@Path("user/")
public class User {

    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormDataParam("username") String username,
                            @FormDataParam("password") String password){
        try{
            if (username == null ||
                    password == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement statement1 = Main.db.prepareStatement(
                    "SELECT Username, Password, SessionToken FROM Users WHERE Username = ? "
            );
            statement1.setString(1,username);
            ResultSet results = statement1.executeQuery();

            if(results != null && results.next()) {
                if(!password.equals(results.getString("Password"))) {
                    return "{\"error\": \"Incorrect password\"}";
                }

                String token = UUID.randomUUID().toString();
                PreparedStatement ps2 = Main.db.prepareStatement(
                        "UPDATE Users SET SessionToken = ? WHERE Username = ?"
                );
                ps2.setString(1,token);
                ps2.setString(2,username);
                ps2.executeUpdate();

                return "{\"token\": \""+token+"\",\"username\": \""+username+"\"}";
            } else {
                return "{\"error\": \"Unknown User\"}";
                }
        } catch(Exception exception){
            System.out.println("Database error during /user/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }

    @POST
    @Path("logout")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String logoutUser(@CookieParam("token") String token) {

        try {

            System.out.println("user/logout");

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT UserID FROM Users WHERE SessionToken = ?");
            ps1.setString(1, token);
            ResultSet logoutResults = ps1.executeQuery();
            if (logoutResults.next()) {

                int id = logoutResults.getInt(1);

                PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET SessionToken = NULL WHERE UserID = ?");
                ps2.setInt(1, id);
                ps2.executeUpdate();

                return "{\"status\": \"OK\"}";
            } else {

                return "{\"error\": \"Invalid token!\"}";

            }

        } catch (Exception exception){
            System.out.println("Database error during /user/logout: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }

    }

    public static boolean validToken(String token) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID FROM Users WHERE SessionToken = ?");
            ps.setString(1, token);
            ResultSet logoutResults = ps.executeQuery();
            return logoutResults.next();
        } catch (Exception exception) {
            System.out.println("Database error during /user/logout: " + exception.getMessage());
            return false;
        }
    }


    @GET
    @Path("check")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public static String validateToken(String token) {
        try {
            PreparedStatement statement = Main.db.prepareStatement("SELECT Username FROM Users WHERE SessionToken = ?");
            statement.setString(1, token);
            ResultSet results = statement.executeQuery();
            if (results != null && results.next()) {
                return results.getString("Username");
            }
        } catch (Exception resultsException) {
            String error = "Database error - can't select by id from 'Users' table: " + resultsException.getMessage();

            System.out.println(error);
        }
        return null;
    }

    public static Integer validateTokenv2(String token) {
        try {
            PreparedStatement statement = Main.db.prepareStatement("SELECT UserID FROM Users WHERE SessionToken = ?");
            statement.setString(1, token);
            ResultSet results = statement.executeQuery();
            if (results != null && results.next()) {
                return results.getInt("UserID");
            }
        } catch (Exception resultsException) {
            String error = "Database error - can't select by id from 'Users' table: " + resultsException.getMessage();

            System.out.println(error);
        }
        return null;
    }

    @POST
    @Path("reset")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public static String Reset(@FormDataParam("token") String token, @FormDataParam("password") String password)
    {
        try{
            if(password == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            String username = validateToken(token);
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
    public static String Rename(@FormDataParam("token") String token, @FormDataParam("newUsername") String newUsername)
    {
        try{
            if(token == null || newUsername == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            String currentUsername = User.validateToken(token);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Username = ? WHERE Username = ?");
            ps.setString(1, newUsername);
            ps.setString(2, currentUsername);
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
    public static String Email(@FormDataParam("token") String token, @FormDataParam("emailAddress") String emailAddress)
    {
        try{
            if(token == null || emailAddress == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            String username = User.validateToken(token);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET EmailAddress = ? WHERE Username = ?");
            ps.setString(2, username);
            ps.setString(1, emailAddress);
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
    public String New(@FormDataParam("email") String emailAddress, @FormDataParam("username") String username, @FormDataParam("password") String password)
    {
        try{
            if(username == null || password == null || emailAddress == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (Username, Password, EmailAddress) Values(?,?,?)");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,emailAddress);
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
    public String Delete(@FormDataParam("token") String token){
        try{
            if(token == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            String username = User.validateToken(token);
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
