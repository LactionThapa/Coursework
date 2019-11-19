package Controllers;
import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("ListItem/")
public class ListItems {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String List() {
        try
        {
            System.out.println("thing/list");
            JSONArray list = new JSONArray();
            PreparedStatement ps = Main.db.prepareStatement("SELECT ListID,ItemID, Quantity, MarkedUserID FROM ListItems");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("listID",results.getInt(1));
                item.put("userID",results.getInt(2));
                item.put("quantity",results.getInt(3));
                item.put("marker",results.getInt(4));
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list listItems, please se server console for more info.\"}";
        }

    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Delete(@FormDataParam("listID") Integer listID,@FormDataParam("itemID") Integer itemID){
        try{
            if(listID == null || itemID == null){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM ListItems WHERE ListID = ? AND ItemID = ?");
            ps.setInt(1,listID);
            ps.setInt(2,itemID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to delete listItems, please se server console for more info.\"}";
        }
    }

    @POST
    @Path("add")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Add(@FormDataParam("listID") Integer listID, @FormDataParam("itemID") Integer itemID, @FormDataParam("quantity") Integer quantity, @FormDataParam("markerID") Integer markerID){
        try{
            if(listID == null || itemID == null || quantity == null || markerID == null ){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO ListItems(ListID, ItemID, Quantity, MarkedUserID) Values(?,?,?,?)");
            ps.setInt(1,listID);
            ps.setInt(2,itemID);
            ps.setInt(3, quantity);
            if(markerID == 0){
                ps.setNull(4,markerID);
            } else {
                ps.setInt(4,markerID);
            }
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to add items to a list, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("quantity")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Quantity(@FormDataParam("listID") Integer listID,@FormDataParam("itemID") Integer itemID,@FormDataParam("quantity") Integer quantity ){
        try{
            if(listID == null || itemID == null || quantity == null){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE ListItems SET Quantity = ? WHERE ListID = ? AND ItemID = ?");
            ps.setInt(1,quantity);
            ps.setInt(2, listID);
            ps.setInt(3,itemID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to change the quantity of an item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("mark")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Mark(@FormDataParam("listID") Integer listID,@FormDataParam("itemID") Integer itemID,@FormDataParam("markerID") Integer markerID ){
        try{
            if(listID == null || itemID == null || markerID == null){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE ListItems SET MarkedUserID = ? WHERE ListID = ? AND ItemID = ?");
            ps.setInt(1, markerID);
            ps.setInt(2, listID);
            ps.setInt(3,itemID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to mark an item, please see server console for more info.\"}";
        }
    }


}
