import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static Connection db = null; //behaves like a global variable

    //This is the method that opens the database
    private static void openDatabase(String dbFile) {
        try  {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties()); //This opens the database file
            System.out.println("Database connection successfully established.");
        } catch (Exception exception) {
            System.out.println("Database connection error: " + exception.getMessage());
        }

    }

    //This is the method that closes the database
    private static void closeDatabase(){
        try {
            db.close(); //closes the database
            System.out.println("Disconnected from database.");
        } catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }

    //This method selects a row of data
    public static void Select() {
        try
        {
            PreparedStatement ps = db.prepareStatement("SELECT UserID, Username, DateOfBirth FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int userID = results.getInt(1);
                String username = results.getString(2);
                String dateOfBirth = results.getString(3);
                System.out.println(userID + " " + username + " " + dateOfBirth);
            }

        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }

    }

    //This method a pre-existing data in the database
    public static void Update(int userID, String username, String dob){
        try{
            PreparedStatement ps = db.prepareStatement("UPDATE Users SET Username = ?, DateOfBirth = ? WHERE UserID = ?");
            ps.setInt(1, userID);
            ps.setString(2, username);
            ps.setString(3, dob);
            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }

    //This method inserts data in the database
    public static void Insert(int userID, String username, String dob){
    }

    //This is the main method
    public static void main(String[] args) {
        openDatabase("Database.db");
        closeDatabase();
    }

}

