package Server;

import Controllers.User;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static Connection db = null; //behaves like a global variable
    //This is the method that opens the database

    //this for test
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
    //This is the main method
    public static void main(String[] args) {

        openDatabase("database.db");
        User.Delete("Sbeve");
        User.ListUsers();
        closeDatabase();
    }
}
