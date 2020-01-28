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
    @Path("listItem/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String ListWishLists(@PathParam("id") Integer ListID) {
        JSONArray list = new JSONArray();

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ItemID,ItemName, Price  FROM Items WHERE ListID = ?");
            ps.setInt(1,ListID);
            ResultSet results = ps.executeQuery();
            while (results != null && results.next()) {
                JSONObject item = new JSONObject();
                item.put("ItemID", results.getString(1));
                item.put("ItemName", results.getString(2));
                item.put("Price", results.getDouble(3));
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
    public String add(@FormDataParam("ItemName") String ItemName,
                      @FormDataParam("Price") Double Price,
                      @FormDataParam("URL") String URL,
                      @FormDataParam("Quantity") Integer Quantity,
                      @FormDataParam("ListID") Integer ListID) {
        try {
            if(ItemName == null || Price == null || URL == null || Quantity == null || ListID == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Items (ItemName, ListID, Price, Quantity, URL, Marker) VALUES (?,?,?,?,?,null)");
            ps.setString(1, ItemName);
            ps.setInt(2, ListID);
            ps.setDouble(3, Price);
            ps.setInt(4, Quantity);
            ps.setString(5, URL);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to add new items, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@FormDataParam("ItemName") String ItemName,
                      @FormDataParam("Price") Double Price,
                      @FormDataParam("URL") String URL,
                      @FormDataParam("Quantity") Integer Quantity,
                      @FormDataParam("ItemID") Integer ItemID) {
        try {
            if(ItemName == null || Price == null || URL == null || Quantity == null || ItemID == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Items SET ItemName = ?, Price =?, Quantity = ?, URL = ? WHERE ItemID = ?");
            ps.setString(1, ItemName);
            ps.setDouble(2, Price);
            ps.setInt(3, Quantity);
            ps.setString(4, URL);
            ps.setInt(5,ItemID);
            ps.execute();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to add new items, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItem(@PathParam("id") Integer ItemID) {
        JSONArray list = new JSONArray();

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT ItemName, Price, Quantity, URL  FROM Items WHERE ItemID = ?");
            ps.setInt(1,ItemID);
            ResultSet results = ps.executeQuery();
            while (results != null && results.next()) {
                JSONObject item = new JSONObject();
                item.put("ItemID", ItemID);
                item.put("ItemName", results.getString(1));
                item.put("Price", results.getDouble(2));
                item.put("Quantity", results.getInt(3));
                item.put("URL", results.getString(4));
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
    public String getThing(@PathParam("id") Integer ItemID) {

        JSONObject item = new JSONObject();
        try {
            if (ItemID == null) {
                throw new Exception("Thing's 'id' is missing in the HTTP request's URL.");
            }
            PreparedStatement ps = Main.db.prepareStatement("SELECT ItemName, Price, Quantity, URL FROM Items WHERE ItemID = ?");
            ps.setInt(1, ItemID);
            ResultSet results = ps.executeQuery();
            if (results.next()) {
                item.put("ItemID", ItemID);
                item.put("ItemName", results.getString(1));
                item.put("Price", results.getDouble(2));
                item.put("Quantity", results.getInt(3));
                item.put("URL", results.getString(4));
            }
            return item.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
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
    public String delete(@FormDataParam("id") Integer itemId){
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