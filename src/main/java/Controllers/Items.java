package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("Item/")
public class Items {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String ListItems() {
        try {
            System.out.println("thing/list");
            JSONArray list = new JSONArray();
            PreparedStatement ps = Main.db.prepareStatement("SELECT ItemName, Price, URL FROM Items");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("listName",results.getString(1));
                item.put("price",results.getDouble(2));
                item.put("url",results.getString(3));
                list.add(item);
            }
            return list.toString();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to list items, please se server console for more info.\"}";
        }
    }

    @POST
    @Path("add")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String add(@FormDataParam("itemName") String ItemName,@FormDataParam("price") Double Price,@FormDataParam("url") String URL ) {
        try {
            if(ItemName == null || Price == null || URL == null){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Items (ItemName, Price, URL) VALUES (?,?,?)");
            ps.setString(1, ItemName);
            ps.setDouble(2, Price);
            ps.setString(3, URL);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to add new items, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("rename")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Rename(@FormDataParam("itemName") String itemName,@FormDataParam("itemID") Integer itemId) {
        try {
            if(itemName == null || itemId == null){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Items SET ItemName = ? WHERE ItemID = ?");
            ps.setString(1, itemName);
            ps.setInt(2,itemId);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database error: "+ exception.getMessage());
            return "{\"error\": \"Unable to rename the item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("reprice")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String Reprice(@FormDataParam("price") Double price,@FormDataParam("itemID") Integer itemId) {
        try {
            if(price == null || itemId == null){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Items SET Price = ? WHERE ItemID = ?");
            ps.setDouble(1, price);
            ps.setInt(2,itemId);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database error: "+ exception.getMessage());
            return "{\"error\": \"Unable to reprice the item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("url")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String URL(@FormDataParam("url") String url,@FormDataParam("itemId") Integer itemId) {
        try {
            if(url == null || itemId == null){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Items SET URL = ? WHERE ItemID = ?");
            ps.setString(1, url);
            ps.setInt(2,itemId);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database error: "+ exception.getMessage());
            return "{\"error\": \"Unable to change the url of the item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@FormDataParam("itemId") Integer itemId){
        try{
            if(itemId == null){
                throw new Exception("One ore more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Items WHERE ItemID = ?");
            ps.setInt(1,itemId);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println("Database error: "+ e.getMessage());
            return "{\"error\": \"Unable to delete wishList, please see server console for more info.\"}";
        }
    }
}