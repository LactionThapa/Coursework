package Controllers;

import Server.OldMain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OldItems {

    public static void ListItems() {
        try {
            PreparedStatement ps = OldMain.db.prepareStatement("SELECT ItemName, Price, URL FROM Items");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String listName = results.getString(1);
                double price = results.getDouble(2);
                String url = results.getString(3);
                System.out.println("Item: " + listName + "\n Price: £" + price + "\n URL: " + url);
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static void add(String ItemName, double Price, String URL ) {
        try {
            PreparedStatement ps = OldMain.db.prepareStatement("INSERT INTO Items (ItemName, Price, URL) VALUES (?,?,?)");
            ps.setString(1, ItemName);
            ps.setDouble(2, Price);
            ps.setString(3, URL);
            ps.execute();
            System.out.println("A new item has been added to the database");
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void Rename(String itemName,int itemId) {
        try {

            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE Items SET ItemName = ? WHERE ItemID = ?");
            ps.setString(1, itemName);
            ps.setInt(2,itemId);
            ps.execute();
            System.out.println("The item\'s name has been updated");
        } catch (Exception exception) {
            System.out.println("Database error: "+ exception.getMessage());
        }
    }

    public static void Reprice(double price, int itemId) {
        try {

            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE Items SET Price = ? WHERE ItemID = ?");
            ps.setDouble(1, price);
            ps.setInt(2,itemId);
            ps.execute();
            System.out.println("The item\'s price has been updated");
        } catch (Exception exception) {
            System.out.println("Database error: "+ exception.getMessage());
        }
    }

    public static void URL(String url, int itemId) {
        try {

            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE Items SET URL = ? WHERE ItemID = ?");
            ps.setString(1, url);
            ps.setInt(2,itemId);
            ps.execute();
            System.out.println("The item\'s URL has been updated");
        } catch (Exception exception) {
            System.out.println("Database error: "+ exception.getMessage());
        }
    }

    public static void delete(int itemId){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("DELETE FROM Items WHERE ItemID = ?");
            ps.setInt(1,itemId);
            ps.executeUpdate();
            System.out.println("The item has been deleted");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}