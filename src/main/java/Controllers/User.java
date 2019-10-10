package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    //This method selects a row of data
    public static void ListUsers() {
        try
        {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Password, EmailAddress FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int userID = results.getInt(1);
                String username = results.getString(2);
                String password = results.getString(3);
                String emailAddress = results.getString(4);
                System.out.println(userID + " " + username + " " + password + " " + emailAddress);
            }

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }

    }

    //This method a pre-existing data in the database
    public static void Update(int userID, String username, String password, String emailAddress){
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET Username = ?, Password = ?, EmailAddress = ? WHERE UserID = ?");
            ps.setInt(1, userID);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, emailAddress);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //This method inserts data in the database
    public static void Add(String username, String password, String emailAddress){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (Username, Password, EmailAddress) Values(?,?,?)");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,emailAddress);
            ps.executeUpdate();
            System.out.println("Record add to Users table");
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error");
        }
    }

    //This method deletes a record in the table
    public static void Delete(String username){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE Username = ?");
            ps.setString(1,username);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
