package Controllers;

import Server.Main;

import java.sql.PreparedStatement;

public class Items {

    public static void New(String ItemName, double Price, String URL ) {
        try {
            PreparedStatement ps = Main.db.prepareStatement(
                    "INSERT INTO Items (ItemName, Price, URL) VALUES (?,?,?)");
            ps.setString(1, ItemName);
            ps.setDouble(2, Price);
            ps.setString(3, URL);

            ps.execute();
        } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void update(String og, String ItemName, double Price, String URL) {
        try {

            PreparedStatement ps = Main.db.prepareStatement(
                    "UPDATE Items SET ItemName = ?, Price = ?, URL = ? WHERE ItemName = ?");
            ps.setString(1, ItemName);
            ps.setDouble(2, Price);
            ps.setString(3, URL);
            ps.setString(4, og);

            ps.execute();
        } catch (Exception exception) {
            System.out.println("Database error: "+ exception.getMessage());
        }
    }

    public static void delete(String name){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Items WHERE ItemName = ?");
            ps.setString(1,name);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

