package OldControllers;
import OldServer.OldMain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OldPermission {
    public static void Add(int listid, int userid){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("INSERT INTO Permission (ListID, UserID) Values(?,?)");
            ps.setInt(1,listid);
            ps.setInt(2,userid);
            ps.executeUpdate();
            System.out.println("User has been given access");
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error");
        }
    }

    public static void Delete(int listid, int userid){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("DELETE FROM Permission WHERE ListID = ? AND UserID = ?");
            ps.setInt(1,listid);
            ps.setInt(2,userid);
            ps.executeUpdate();
            System.out.println("User\'s access has been revoked");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void List() {
        try
        {
            PreparedStatement ps = OldMain.db.prepareStatement("SELECT ListID, UserID FROM Permission");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int listId = results.getInt(1);
                int userId = results.getInt(2);
                System.out.println("ListID: "+ listId + " UserID: " + userId);
            }

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }

    }
}
