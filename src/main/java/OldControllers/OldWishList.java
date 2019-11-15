package OldControllers;

import OldServer.OldMain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OldWishList {
    //This method inserts data in the database
    public static void Add(int userID, String listname, Boolean status){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("INSERT INTO WishLists (UserID, ListName, Status) Values(?,?,?)");
            ps.setInt(1,userID);
            ps.setString(2,listname);
            ps.setBoolean(3,status);
            ps.executeUpdate();
            System.out.println("The wish list has been added to the database");
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error");
        }
    }

    //This method a pre-existing data in the database
    public static void Rename(int listID, String listname){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE WishLists SET ListName = ? WHERE ListID = ?");
            ps.setString(1, listname);
            ps.setInt(2, listID);
            ps.executeUpdate();
            System.out.println("The name of the list has been changed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void Status(int listID, Boolean status){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE WishLists SET Status = ? WHERE ListID = ?");
            ps.setBoolean(1, status);
            ps.setInt(2,listID);
            ps.executeUpdate();
            System.out.println("The status of the list has been updated");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //This method selects a row of data
    public static void ListWishLists() {
        try
        {
            PreparedStatement ps = OldMain.db.prepareStatement("SELECT ListName, Status FROM WishLists");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String listName = results.getString(1);
                Boolean status = results.getBoolean(2);
                System.out.println(listName + " " + status);
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }

    }
    public static void Delete(int listId){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("DELETE FROM WishLists WHERE ListID = ?");
            ps.setInt(1,listId);
            ps.executeUpdate();
            System.out.println("The wish list has been removed from the WishList table in the database");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
