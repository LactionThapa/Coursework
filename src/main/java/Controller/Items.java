package Controller;
import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Items {

    public static void addItems(String ItemName, double Price, String URL ) {
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

    public static void updateItems(String og, String ItemName, double Price, String URL) {
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
}

