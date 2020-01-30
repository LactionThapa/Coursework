package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("WishList/")
public class WishList {

    @GET
    @Path("list")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String ListWishLists(@CookieParam("token") String token) {
        JSONArray list = new JSONArray();
        int userID = User.validateTokenv2(token);
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ListID,ListName, Status  FROM WishLists WHERE UserID = ?");
            ps.setInt(1,userID);
            ResultSet results = ps.executeQuery();
            while (results != null && results.next()) {
                JSONObject item = new JSONObject();
                item.put("ListID", results.getString(1));
                item.put("ListName", results.getString(2));
                item.put("Status", results.getBoolean(3));
                list.add(item);
            }
            return list.toString();

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please se server console for more info.\"}";
        }

    }

    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getThing(@PathParam("id") Integer id) {

        JSONObject item = new JSONObject();
        try {
            if (id == null) {
                throw new Exception("Thing's 'id' is missing in the HTTP request's URL.");
            }
            PreparedStatement ps = Main.db.prepareStatement("SELECT ListName, Status FROM WishLists WHERE ListID = ?");
            ps.setInt(1, id);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("ListID", id);
                item.put("ListName", results.getString(1));
                item.put("Status", results.getBoolean(2));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }


    @POST
    @Path("add")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String New(@FormDataParam("ListName") String listName,
                      @FormDataParam("Status") Boolean status,
                      @CookieParam("token") String token) {

        try {
            if (listName == null || status == null || token == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            int userID = User.validateTokenv2(token);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO WishLists (UserID, ListName, Status) Values(?,?,?)");
            ps.setInt(1, userID);
            ps.setString(2, listName);
            ps.setBoolean(3, status);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to create new list, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateThing(@FormDataParam("ListID") Integer ListID,
                              @FormDataParam("ListName") String ListName,
                              @FormDataParam("Status") Boolean Status) {

        try {
            if (ListID == null || ListName == null || Status == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement ps = Main.db.prepareStatement("UPDATE WishLists SET ListName = ?, Status = ? WHERE ListID = ?");
            ps.setString(1, ListName);
            ps.setBoolean(2, Status);
            ps.setInt(3, ListID);
            ps.execute();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }


    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Delete(@FormDataParam("ListID") Integer ListID) {

        try {
            if (ListID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement ps = Main.db.prepareStatement("Delete FROM WishLists WHERE ListID = ?");
            ps.setInt(1, ListID);
            ps.executeUpdate();

            return "{\"status\": \"OK\"}";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to delete wishList, please see server console for more info.\"}";
        }
    }


}