import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
class Table_Inventory {
    static public Table_Inventory instance = null;
    static public Connection connection = null;


    /**
     * Singleton pattern to get the instance of Table_Inventory
     * @return the instance of Table_Inventory
     */
    public static Table_Inventory getInstance() {
        if (instance == null) {
            instance = new Table_Inventory();
        }
        return instance;
    }
    private Table_Inventory() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectjava", "root", "");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }


    /**
     * Function to insert a new inventory into the database
     * @param inventory the inventory to be inserted
     * @return the ID of the inserted inventory
     */
    public Integer Insert_Inventory(Inventory inventory) {
        ArrayList<Item> unequipped_inventory = inventory.Get_Inventory_Items();
        ArrayList<Item> equiped_items= inventory.Get_Equipped_Items();
        unequipped_inventory.addAll(equiped_items); // Add equipped items to the inventory map
        //Create Map<String,Integer> to store item names and their quantities
        Map<String, Integer> inventoryMap = new HashMap<>();
        for (Item item : unequipped_inventory) {
            String itemName = item.NAME;
            Integer itemQuantity = inventoryMap.getOrDefault(itemName, 0);
            inventoryMap.put(itemName, itemQuantity + 1); // Increment the quantity for the item
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(inventoryMap);
            String insertNewInventory = "INSERT INTO Inventory (Inventory) VALUES ('" + jsonString + "')";
            System.out.println("JSON String: " + jsonString);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(insertNewInventory);
            String query = "SELECT MAX(ID) AS last_id from Inventory";
            ResultSet res = stmt.executeQuery(query);
            res.next();
            int lastId= res.getInt("last_id");
            csv.log_to_csv("Insert_Inventory");
            return lastId;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Function to retrieve an inventory from the database
     * @param id ID of the inventory to be retrieved, if null retrieves all inventories
     * @return an Inventory object containing the inventory details
     */
    public Inventory get_inventory(Integer id) {
        try {
            String query = "SELECT Inventory AS inventory FROM Inventory";
            if (id != null) {
                query = "SELECT Inventory AS inventory FROM Inventory WHERE ID = " + id;
            }
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                String totalItems = res.getString("inventory");
                System.out.println("Inventory: " + totalItems);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Integer> inventoryMap = objectMapper.readValue(totalItems, Map.class);
                Inventory inventory = Inventory_Factory.Create_Inventory(inventoryMap);
                csv.log_to_csv("get_inventory");
                return inventory;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null; // Return an empty map if no inventory found or an error occurs
    }

    /**
     * Function to replace an inventory in the database
     * @param inventory the inventory to be replaced
     * @param id the ID of the inventory to be replaced
     */
    public void replace_inventory(Inventory inventory, Integer id) {
        ArrayList<Item> unequipped_inventory = inventory.Get_Inventory_Items();
        ArrayList<Item> equiped_items= inventory.Get_Equipped_Items();
        unequipped_inventory.addAll(equiped_items); // Add equipped items to the inventory map
        //Create Map<String,Integer> to store item names and their quantities
        Map<String, Integer> inventoryMap = new HashMap<>();
        for (Item item : unequipped_inventory) {
            String itemName = item.NAME;
            Integer itemQuantity = inventoryMap.getOrDefault(itemName, 0);
            inventoryMap.put(itemName, itemQuantity + 1); // Increment the quantity for the item
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(inventoryMap);
            String replaceNewInventory = "UPDATE Inventory SET Inventory = '" + jsonString + "' WHERE ID = " + id;
            System.out.println("JSON String: " + jsonString);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(replaceNewInventory);
            csv.log_to_csv("replace_inventory");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }


}

public class DB_Inventory {
    public static void main(String[] args) {
        // Example usage
        Table_Inventory inventoryTable = Table_Inventory.getInstance();
        /*Inventory inventory = new Inventory();
        
        // Add items to the inventory (example)
        String[] st={"Head"};
        Item item3= (Item) new Equipment_Item(100,st,2,"Chelmet","No one reads the description");
        Item item1 = (Item) new Healing_Item(100);
        Item item2 = (Item) new Healing_Item(200,200,200);
        inventory.Add_Item(item1);
        inventory.Add_Item(item1);
        inventory.Add_Item(item2);
        inventory.Add_Item(item3);
        inventory.Equip_Item(3);//Index
        
        // Insert the inventory into the database
        //inventoryTable.Insert_Inventory(inventory);
        //System.out.println("Inventory inserted successfully.");
        */
        Inventory retrievedInventory = inventoryTable.get_inventory(4); // Retrieve the inventory without specifying an ID
        Inventory inventory2=inventoryTable.get_inventory(5); // Retrieve the inventory with ID 1
        inventoryTable.replace_inventory(inventory2, 4); // Replace the inventory with ID 1
        inventoryTable.replace_inventory(retrievedInventory, 5);
        System.out.println("Swapped inventories successfully.");
        // Retrieve the inventory from the database
        //System.out.println("Retrieved Inventory: " + retrievedInventory);
    }
    
}
