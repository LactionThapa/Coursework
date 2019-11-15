package OldControllers;

import OldServer.OldMain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OldUser {
    //This method selects a row of data
    public static void ListUsers() {
        try
        {
            PreparedStatement ps = OldMain.db.prepareStatement("SELECT Username, Password, EmailAddress FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                String username = results.getString(1);
                String password = results.getString(2);
                String emailAddress = results.getString(3);
                System.out.println(username + " " + password + " " + emailAddress);
            }

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }

    }

    //This method a pre-existing data in the database
    public static void Reset(String username, String password){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE Users SET Password = ? WHERE Username = ? ");
            ps.setString(2, username);
            ps.setString(1, password);
            ps.executeUpdate();
            System.out.println("Your password has been reset");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void Rename(String newUsername, String oldUsername){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE Users SET Username = ? WHERE Username = ? ");
            ps.setString(1, newUsername);
            ps.setString(2,oldUsername);
            ps.executeUpdate();
            System.out.println("Your username has been changed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void Email(String email, String username){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE Users SET EmailAddress = ? WHERE Username = ? ");
            ps.setString(1, email);
            ps.setString(2, username);
            ps.executeUpdate();
            System.out.println("Your email has been changed");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //This method inserts data in the database
    public static void New(String username, String password, String emailAddress){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("INSERT INTO Users (Username, Password, EmailAddress) Values(?,?,?)");
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
            PreparedStatement ps = OldMain.db.prepareStatement("DELETE FROM Users WHERE Username = ?");
            ps.setString(1,username);
            ps.executeUpdate();
            System.out.println("The record has been removed from the database");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
