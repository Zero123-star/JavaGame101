
import java.util.Arrays;

import com.mysql.cj.xdevapi.Table;

public class App {
    public static void main(String[] args) throws Exception {
        String description = "ABCDEFU random stuff in here, Occupied_Slots: head,tail,uwu,owwo,watever,6,7,8,9,10";
        String[] occupied_slots = description.split(",");
        for (String slot : occupied_slots) {
            System.out.println(slot);
        }
        //Stats stat = new Stats();
        //Table_Stats table = Table_Stats.getInstance();
        //table.Insert_Stats(stat);
        //System.out.println("Stats inserted successfully.");
        //table.get_table_stats();
    }

}
