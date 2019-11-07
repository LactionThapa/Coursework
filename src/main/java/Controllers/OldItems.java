package Controllers;

import Server.OldMain;

import java.sql.PreparedStatement;

public class OldItems {

    public static void New(String itemName, double price, String url ) {
        try {
            PreparedStatement ps = OldMain.db.prepareStatement(
                    "INSERT INTO Items (ItemName, Price, URL) VALUES (?,?,?)");
            ps.setString(1, itemName);
            ps.setDouble(2, price);
            ps.setString(3, url);
            ps.execute();
            System.out.println("New item has been added to the database");
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void rename(int userId, String itemName) {
        try {

            PreparedStatement ps = OldMain.db.prepareStatement(
                    "UPDATE Items SET ItemName = ? WHERE ItemID = ?");
            ps.setString(1, itemName);
            ps.setInt(2,userId);
            ps.execute();
            System.out.println("Item name has been updated");
        } catch (Exception exception) {
            System.out.println("Database error: "+ exception.getMessage());
        }
    }

    public static void price(int userId, double price) {
        try {

            PreparedStatement ps = OldMain.db.prepareStatement(
                    "UPDATE Items SET Price = ? WHERE ItemID = ?");
            ps.setDouble(1, price);
            ps.setInt(2,userId);
            ps.execute();
            System.out.println("Item price has been updated");
        } catch (Exception exception) {
            System.out.println("Database error: "+ exception.getMessage());
        }
    }

    public static void url(int userId, String url) {
        try {

            PreparedStatement ps = OldMain.db.prepareStatement(
                    "UPDATE Items SET URL = ? WHERE ItemID = ?");
            ps.setString(1, url);
            ps.setInt(2,userId);
            ps.execute();
            System.out.println("Item url has been updated");
        } catch (Exception exception) {
            System.out.println("Database error: "+ exception.getMessage());
        }
    }

    public static void delete(int itemId){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("DELETE FROM Items WHERE ItemID = ?");
            ps.setInt(1,itemId);
            ps.executeUpdate();
            System.out.println("The item has been deleted from the database");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

