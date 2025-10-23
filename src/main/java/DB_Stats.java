import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
class Table_Stats {
    static public Table_Stats t = null;
    static public Connection connection = null;

    private Table_Stats() {
        t = this;
    }

    /**
     * Singleton pattern to get the instance of Table_Stats
     * @return the instance of Table_Stats
     */
    public static Table_Stats getInstance() {
        if (t == null) {
            t = new Table_Stats();
        }
        return t;
    }

    /**
     * Function to retrieve stats from the database
     * @param id ID of the statsheet to be retrieved, if null retrieves all stats
     * @return a Stats object containing the stats
     */
    public Stats get_table_stats(Integer id) {
        try {
            String query= "SELECT Stats AS stats FROM Stats";
            if(id!=null){
            query= "SELECT Stats AS stats FROM Stats where ID = " + id;}
            Statement stmt = connection.createStatement();
            ResultSet res= stmt.executeQuery(query);
            while (res.next()) {
                var totalItems = res.getString("stats");
                //Print keys and values from the JSON object
                //System.out.println("Stats: " + totalItems);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Integer> statsMap = objectMapper.readValue(totalItems, Map.class);
                csv.log_to_csv("get_table_stats");
                Stats stats = new Stats();
                stats.Get_Permanent_Stats().putAll(statsMap);

                return stats;
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return null; // Return an empty map if no stats found or an error occurs
    }


    /**
     * Function to insert a new statsheet into the database
     * 
     * @param stat the statsheet to be inserted
     * @return the ID of the newly inserted statsheet
     */
    public Integer Insert_Stats(Stats stat)
    {
        Map<String, Integer> permanent_stats= stat.Get_Permanent_Stats();
        ObjectMapper objectMaper = new ObjectMapper();
        try{String jsoString = objectMaper.writeValueAsString(permanent_stats);
        String insert_new_statsheet= "INSERT INTO Stats (Stats) VALUES" + "('" + jsoString + "')";
        System.out.println("JSON String: " + jsoString);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(insert_new_statsheet);
        csv.log_to_csv("Insert_Stats");
        String query = "SELECT MAX(ID) AS last_id from Stats";
        ResultSet res = stmt.executeQuery(query);
        res.next();
        int lastId=res.getInt("last_id");
        return lastId;
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return 0;
    }


    /**
     * Function to replace a statsheet in the database
     * 
     * @param stat the statsheet to be replaced
     * @param id   the ID of the statsheet to be replaced
     */
    public void Replace_Stats(Stats stat, Integer id)
    {
        Map<String, Integer> permanent_stats= stat.Get_Permanent_Stats();
        ObjectMapper objectMaper = new ObjectMapper();
        try{String jsoString = objectMaper.writeValueAsString(permanent_stats);
        String replace_new_statsheet= "UPDATE Stats SET Stats = '" + jsoString + "' WHERE ID = " + id;
        System.out.println("JSON String: " + jsoString);
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(replace_new_statsheet);
        csv.log_to_csv("Replace_Stats");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * Function to create the Stats table in the database
     */
    public void Create_Stats_Table() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Stats ("
                + "ID INT PRIMARY KEY AUTO_INCREMENT,"
                + "Stats JSON)";
        try {
            connection.createStatement().execute(createTableSQL);
            System.out.println("Stats table created successfully.");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/proiectjava", "root", "");
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }
}

public class DB_Stats {
    public static void main(String[] args) throws Exception {
        Table_Stats table = Table_Stats.getInstance();
        Stats stats = new Stats();
        stats= table.get_table_stats(3);
        stats.Increase_Permanent_Stat("Strength", 5);
        table.Replace_Stats(stats, 3);


        //stats.Increase_Permanent_Stat("Strength", null);
        
        //table.get_table_stats(null);
    }
}
