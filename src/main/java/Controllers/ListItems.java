package Controllers;
import Server.Main;
import java.sql.PreparedStatement;

public class ListItems {
    public static void Delete(int listid, int itemid){
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM ListItems WHERE ListID = ? AND ItemID = ?");
            ps.setInt(1,listid);
            ps.setInt(2,itemid);
            ps.executeUpdate();
            System.out.println("Item has been deleted from list");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void Add(int listid, int itemid, int amount, int userid ){
        try{
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Permission (ListID, ItemID, Quantity, MarkedUserID) Values(?,?,?,?)");
            ps.setInt(1,listid);
            ps.setInt(2,itemid);
            ps.setInt(3, amount);
            ps.setInt(4,userid);
            ps.executeUpdate();
            System.out.println("Item has been added into the list");
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error");
        }
    }

    public static void Update(int listid, int itemid, int amount, int userid ){
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE ListItems SET Quantity = ?, MarkedUserID = ?, WHERE ListID = ? AND ItemID = ?");
            ps.setInt(1,amount);
            ps.setInt(2, userid);
            ps.setInt(3, listid);
            ps.setInt(4,itemid);
            ps.executeUpdate();
            System.out.println("The item in the list has been updated");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
