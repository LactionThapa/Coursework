import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WishListsController {
    //This method inserts data in the database
    public static void InsertWishList(int userID, String listname, Boolean status){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO WishLists (UserID, ListName, Public) Values(?,?,?)");
            ps.setInt(1,userID);
            ps.setString(2,listname);
            ps.setBoolean(3,status);
            ps.executeUpdate();
            System.out.println("Record add to Users table");
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error");
        }
    }

    //This method a pre-existing data in the database
    public static void UpdateWishList(int listID, String listname, Boolean status){
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE WishLists SET ListName = ?, Public = ?, WHERE ListID = ?");
            ps.setString(2, listname);
            ps.setBoolean(3, status);
            ps.setInt(1, listID);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //This method selects a row of data
    public static void SelectUser() {
        try
        {
            PreparedStatement ps = Main.db.prepareStatement("SELECT * FROM WishLists");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int listID = results.getInt(1);
                String userID = results.getString(2);
                String listName = results.getString(3);
                Boolean status = results.getBoolean(4);
                System.out.println(listID + " " + userID + " " + listName + " " + status);
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }

    }
}
