package Controllers;

import Server.OldMain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WishList {
    //This method inserts data in the database
    public static void Add(int userID, String listname, Boolean status){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("INSERT INTO WishLists (UserID, ListName, Public) Values(?,?,?)");
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
    public static void Update(int listID, String listname, Boolean status){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE WishLists SET ListName = ?, Public = ?, WHERE ListID = ?");
            ps.setString(2, listname);
            ps.setBoolean(3, status);
            ps.setInt(1, listID);
            ps.executeUpdate();
            System.out.println("The wish list has been updated");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //This method selects a row of data
    public static void ListWishLists() {
        try
        {
            PreparedStatement ps = OldMain.db.prepareStatement("SELECT * FROM WishLists");
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
    public static void Delete(String listname){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("DELETE FROM WishLists WHERE ListName = ?");
            ps.setString(1,listname);
            ps.executeUpdate();
            System.out.println("The wish list has been removed from the WishList table in the database");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
