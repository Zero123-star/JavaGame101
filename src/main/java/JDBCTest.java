
//Import necessary packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;





public class JDBCTest {

void Create_Table(){
//Item table: Value, Description, Quantity, Price
    String createTableSQL = "CREATE TABLE IF NOT EXISTS Item ("
            + "Name VARCHAR(255) PRIMARY KEY,"
            + "Description VARCHAR(255),"
            + "Stats JSON,"
            + "Price DECIMAL(10, 2))";
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectjava", "root", "")) {
        connection.createStatement().execute(createTableSQL);
        System.out.println("Item table created successfully.");
    } catch (SQLException e) {
        System.out.println("SQL Exception: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Exception: " + e.getMessage());
    }
}
void Create_Stats_Table()
    {
        //Columns: ID, Stats(json)
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Stats ("
                + "ID INT PRIMARY KEY AUTO_INCREMENT,"
                + "Stats JSON)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectjava", "root", "")) {
            connection.createStatement().execute(createTableSQL);
            System.out.println("Stats table created successfully.");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
void Create_Inventory_Table()
{
    //Columns: ID, Inventory(json)
    String createTableSQL = "CREATE TABLE IF NOT EXISTS Inventory ("
            + "ID INT PRIMARY KEY AUTO_INCREMENT,"
            + "Inventory JSON)"; //Json format: Name_of_item: Quantity. Name_of_item is then used to reference the Item table.
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectjava", "root", "")) {
        connection.createStatement().execute(createTableSQL);
        System.out.println("Inventory table created successfully.");
    } catch (SQLException e) {
        System.out.println("SQL Exception: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Exception: " + e.getMessage());
    }
}
void Create_NPC_Table()
{
    String createTableSQL = "CREATE TABLE IF NOT EXISTS NPC ("
            + "NPC_IDENTIFIER INT PRIMARY KEY AUTO_INCREMENT,"
            + "MAX_STAMINA INT,"
            + "MAX_HEALTH INT,"
            + "MAX_MANA INT,"
            + "CURRENT_HEALTH INT,"
            + "CURRENT_STAMINA INT,"
            + "CURRENT_MANA INT,"
            + "STATSHEET_ID INT,"
            + "INVENTORY_ID INT,"
            + "FOREIGN KEY (STATSHEET_ID) REFERENCES Stats(ID),"
            + "FOREIGN KEY (INVENTORY_ID) REFERENCES Inventory(ID))";
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectjava", "root", "")) {
        connection.createStatement().execute(createTableSQL);
        System.out.println("NPC table created successfully.");
    } catch (SQLException e) {
        System.out.println("SQL Exception: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("Exception: " + e.getMessage());
    }
}


    public static void main(String[] args) throws Exception {
        System.out.println("Test");
        // Database URL
        String url = "jdbc:mysql://localhost:3306/proiectjava"; 
        // Database credentials
        String user = "root";
        String password = ""; //no password
        // Establish a connection
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    System.out.println("Current Java version: " + System.getProperty("java.version"));
        JDBCTest test = new JDBCTest();
        //test.Create_Table();
        test.Create_NPC_Table();
}
}