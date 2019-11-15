package OldControllers;
import OldServer.OldMain;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OldListItems {
    public static void Delete(int listid, int itemid){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("DELETE FROM ListItems WHERE ListID = ? AND ItemID = ?");
            ps.setInt(1,listid);
            ps.setInt(2,itemid);
            ps.executeUpdate();
            System.out.println("Item has been deleted from list");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void Add(int listid, int itemid, int amount, Integer userid ){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("INSERT INTO ListItems(ListID, ItemID, Quantity, MarkedUserID) Values(?,?,?,?)");
            ps.setInt(1,listid);
            ps.setInt(2,itemid);
            ps.setInt(3, amount);
            if(userid == 0){
                ps.setNull(4,userid);
            } else {
                ps.setInt(4,userid);
            }
            ps.executeUpdate();
            System.out.println("Item has been added into the list");
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error");
        }
    }

    public static void Quantity(int listid, int itemid, int amount ){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE ListItems SET Quantity = ? WHERE ListID = ? AND ItemID = ?");
            ps.setInt(1,amount);
            ps.setInt(2, listid);
            ps.setInt(3,itemid);
            ps.executeUpdate();
            System.out.println("The quantity of the item has been updated");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void Mark(int listid, int itemid, int userid ){
        try{
            PreparedStatement ps = OldMain.db.prepareStatement("UPDATE ListItems SET MarkedUserID = ? WHERE ListID = ? AND ItemID = ?");
            ps.setInt(1, userid);
            ps.setInt(2, listid);
            ps.setInt(3,itemid);
            ps.executeUpdate();
            System.out.println("A user has marked an item");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void List() {
        try
        {
            PreparedStatement ps = OldMain.db.prepareStatement("SELECT ListID,ItemID, Quantity, MarkedUserID FROM ListItems");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int listId = results.getInt(1);
                int userId = results.getInt(2);
                int quantity = results.getInt(3);
                int marked = results.getInt(4);
                System.out.println("ListID: "+ listId + " UserID: " + userId + " Quantity: " + quantity + " MarkedBy: " + marked);
            }

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }

    }

}
