import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
class Table_Npcd {
    static public Table_Npcd instance = null;
    static public Connection connection = null;
    public static Table_Npcd getInstance() {
        if (instance == null) {
            instance = new Table_Npcd();
        }
        return instance;
    }
    private Table_Npcd() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectjava", "root", "");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }


    /**
     * Function to insert a new NPC into the database
     * @param npc the NPC to be inserted
     * @return the ID of the inserted NPC
     */
    public Integer Insert_Npcd(Npcd npc) {
        
        Stats Statsheet = npc.Get_Stats();
        Inventory inventory = npc.Get_Inventory();
        ObjectMapper objectMapper = new ObjectMapper();
        int npc_maxhp=npc.get_max_health();
        int npc_curhp=npc.get_health();
        int npc_maxmana=npc.get_max_mana();
        int npc_curmana=npc.get_mana();
        int npc_maxstamina=npc.get_max_stamina();
        int npc_curstamina=npc.get_stamina();
        try {
/*
CREATE TABLE IF NOT EXISTS NPC ("
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
            + "FOREIGN KEY (INVENTORY_ID) REFERENCES Inventory(ID))
 */

            Table_Inventory inventoryTable = Table_Inventory.getInstance();
            Table_Stats statsTable = Table_Stats.getInstance();
            Integer inventoryId = inventoryTable.Insert_Inventory(inventory);
            Integer statsId = statsTable.Insert_Stats(Statsheet);
            String insertNewNpcd="INSERT INTO NPC (MAX_STAMINA, MAX_HEALTH, MAX_MANA, CURRENT_HEALTH, CURRENT_STAMINA, CURRENT_MANA, STATSHEET_ID, INVENTORY_ID) VALUES ("
                    + npc_maxstamina + ", "
                    + npc_maxhp + ", "
                    + npc_maxmana + ", "
                    + npc_curhp + ", "
                    + npc_curstamina + ", "
                    + npc_curmana + ", "
                    + statsId + ", "
                    + inventoryId + ")";
            System.out.println("Insert NPC Query: " + insertNewNpcd);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(insertNewNpcd);
            csv.log_to_csv("Insert_Npcd");
            String query = "SELECT MAX(NPC_IDENTIFIER) AS last_id FROM NPC";
            ResultSet res = stmt.executeQuery(query);
            res.next();
            int lastId = res.getInt("last_id");
            return lastId;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Function to retrieve an NPC from the database
     * @param id ID of the NPC to be retrieved
     * @return an Npcd object containing the NPC details
     */
    public Npcd get_npcd(Integer id) {
        try {
            String query = "SELECT * FROM NPC WHERE NPC_IDENTIFIER = " + id;
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                int npc_maxstamina = res.getInt("MAX_STAMINA");
                int npc_maxhp = res.getInt("MAX_HEALTH");
                int npc_maxmana = res.getInt("MAX_MANA");
                int npc_curhp = res.getInt("CURRENT_HEALTH");
                int npc_curstamina = res.getInt("CURRENT_STAMINA");
                int npc_curmana = res.getInt("CURRENT_MANA");
                int statsheetId = res.getInt("STATSHEET_ID");
                int inventoryId = res.getInt("INVENTORY_ID");

                Table_Stats statsTable = Table_Stats.getInstance();
                Table_Inventory inventoryTable = Table_Inventory.getInstance();
                
                Stats statsheet = statsTable.get_table_stats(statsheetId);
                Inventory inventory = inventoryTable.get_inventory(inventoryId);
                //Npcd(int max_stamina, int max_health, int max_mana, int current_health, int current_stamina, int current_mana, Stats statsheet, Inventory inventory) 
                Npcd npc = new Npcd(npc_maxstamina, npc_maxhp, npc_maxmana, npc_curhp, npc_curstamina, npc_curmana, statsheet, inventory);
                npc.NPC_IDENTIFIER="Player"+id;
                csv.log_to_csv("get_npcd");
                return npc;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null; // Return null if no NPC found or an error occurs
    }

    /**
     * Function to retrieve the statsheet and inventory IDs for a given NPC_IDENTIFIER
     * @param NPC_IDENTIFIER the identifier of the NPC
     * @return an array containing the statsheet ID and inventory ID, [statsheetId, inventoryId]
     */
    public Integer[] get_stat_inventory_ids(String NPC_IDENTIFIER)
    {
        try {
            System.out.println("DB_NPCD"+NPC_IDENTIFIER);
            String id=NPC_IDENTIFIER.replace("Player", "");
            int npcId = Integer.parseInt(id);
            String query = "SELECT STATSHEET_ID, INVENTORY_ID FROM NPC WHERE NPC_IDENTIFIER = '" + npcId + "'";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                int statsheetId = res.getInt("STATSHEET_ID");
                int inventoryId = res.getInt("INVENTORY_ID");
                csv.log_to_csv("get_stat_inventory_ids");
                return new Integer[]{statsheetId, inventoryId};
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null; // Return null if no IDs found or an error occurs
    }

    /**
     * Function to retrieve all NPC identifiers from the database
     * @return a string array containing all NPC identifiers
     */
    public String[] get_all_npc()//Returns a string array containing all the npc ids
    {
        try {
            String query = "SELECT NPC_IDENTIFIER FROM NPC";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            List<String> npcList = new ArrayList<>();
            while (res.next()) {
                String npcIdentifier = res.getString("NPC_IDENTIFIER");
                npcList.add(npcIdentifier);
            }
            csv.log_to_csv("get_all_npc");
            return npcList.toArray(new String[0]);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return new String[0]; 
    }
}



public class DB_Npcd {
    public static void main(String[] args) {
        // Example usage
        Table_Npcd npcTable = Table_Npcd.getInstance();
      /*  Npcd npc = new Npcd();
        
        Stats stats = npc.Get_Stats();
        stats.Increase_Permanent_Stat("Strength", 10);
        stats.Increase_Permanent_Stat("Dexterity", 5);
        
        Inventory inventory = npc.Get_Inventory();
        Item item1 = (Item) new Healing_Item(300);
        inventory.Add_Item(item1);
        npcTable.Insert_Npcd(npc);*/
        String npcIdentifier = "Player2";
        //Npcd npc2 = npcTable.get_npcd(2);
        Integer[] x=npcTable.get_stat_inventory_ids(npcIdentifier);
        System.out.println(x[0] + " " + x[1]);
    }
    
}
