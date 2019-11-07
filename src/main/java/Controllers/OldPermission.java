package Controllers;
import Server.OldMain;

import java.sql.PreparedStatement;

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
}
