import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
class Table_Item {
    static public Table_Item instance = null;
    static public Connection connection = null;

    /**
     * Singleton pattern to get the instance of Table_Item
     * @return the instance of Table_Item
     */
    public static Table_Item getInstance() {
        if (instance == null) {
            instance = new Table_Item();
        }
        return instance;
    }
    private Table_Item() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectjava", "root", "");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }


    /**
     * Function to retrieve an item from the database
     * @param itemName Name of the item to be retrieved
     * @return an Item object containing the item details
     */
    public Item get_item(String itemName) {
        try {
            String query = "SELECT Name, Description, Stats, Price FROM Item WHERE Name = '" + itemName + "'";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {

                String totalItems = res.getString("Stats");
                String itemDescription = res.getString("Description");
                String itemType= itemDescription.split(",")[0];
                System.out.println("Item Type: " + itemType);

                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Integer> itemMap = objectMapper.readValue(totalItems, Map.class);
                //System.out.println("Item Map: " + itemMap);
                //Item_Factory itemFactory = new Item_Factory();
                Item item = Item_Factory.Create_Item(itemType,itemMap,itemName,itemDescription);
                csv.log_to_csv("get_item");
                return item;
                //return itemMap;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        Healing_Item aux=new Healing_Item(0);
        System.out.println("Item not found or an error occurred.");
        return (Item) aux; // Return an empty map if no item found or an error occurs
    }
    
    /**
     * Function to insert a new item into the database
     * @param item the item to be inserted
     */
    public void Insert_Item(Item item) {
        Map<String, Integer> itemStats = item.Get_Value();
        Integer itemPrice=itemStats.get("Value");
        String itemDescription=item.Get_Type()+",";
        if(item.Get_Type()=="Equipment_Item")
        {
            String slots="";
            for (String slot : ((Equipment_Item)item).Get_Slots()) {
                slots += slot + ",";
            }
            slots = slots.substring(0, slots.length() - 1); // Remove the last comma
            itemDescription += "Occupied_Slots:"+slots;
        }
        //System.out.println("Item Description: " + itemDescription);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(itemStats);
            String insertNewItem = "INSERT INTO Item (Name, Description, Stats, Price) VALUES ('"
                    + item.NAME + "', '" + itemDescription + "', '" + jsonString + "', "
                    + itemPrice + ")";
            System.out.println("JSON String: " + jsonString);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(insertNewItem);
            csv.log_to_csv("Insert_Item");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
    /**
     * Function to update an item in the database
     * @param item the item to be updated
     */
    public void Delete_Item(String itemName) {
        try {
            String deleteItem = "DELETE FROM Item WHERE Name = '" + itemName + "'";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(deleteItem);
            csv.log_to_csv("Delete_Item");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}



public class DB_Item {
    public static void main(String[] args) {
    String[] slots= {"Head"};
    Item helmet = new Equipment_Item(25, slots, 0, "Iron Helmet", "Simple helmet");
    Table_Item.getInstance().Insert_Item(helmet);
    slots = new String[] {"Chest"};
    Item chainmail = new Equipment_Item(50, slots, 0, "chainmail", null);
    Table_Item.getInstance().Insert_Item(chainmail);
    HashMap<String, Integer> values = new HashMap<>();
    values.put("Strength", 10);
    values.put("Defense", 10);
    Item potion = new Buffing_Item(values, 5, "Buffing potion", "desc");
    Table_Item.getInstance().Insert_Item(potion);
    Item healingPotion = new Healing_Item(100, 50, 50);
    healingPotion.NAME = "Healing Potion";
    Table_Item.getInstance().Insert_Item(healingPotion);
    Item healingPotion2 = new Healing_Item(200, 100, 100);
    healingPotion2.NAME = "Big Healing Potion";
    Table_Item.getInstance().Insert_Item(healingPotion2);

 }
}
