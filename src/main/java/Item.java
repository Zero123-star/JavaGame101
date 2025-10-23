
import java.util.*;
import java.math.*;
class Item_Factory
{
    public static Item Create_Item(String item_type, Map<String,Integer> item_stats, String name, String description)
    {
        if(item_type.equals("Healing_Item"))
        {
            return new Healing_Item(item_stats.get("Restored_Health"),item_stats.get("Restored_Mana"),item_stats.get("Restored_Stamina"));
        }
        else if(item_type.equals("Buffing_Item"))
        {
            return new Buffing_Item(item_stats,item_stats.get("Duration"),name,description);
        }
        else if(item_type.equals("Equipment_Item"))
        {
            //String[] occupied_slots=item_stats.get("Occupied_Slots").toString().split(",");
            //Occupied slots is found in description, following the format : Description="Equipment_Item, Occupied_Slots: head,arm,....,"
            System.out.println("Description: " + description);
            String[] occupied_slots = description.split("Occupied_Slots:")[1].split(",");
            for(String slot : occupied_slots)
            {
                System.out.println(slot); //Remove any leading or trailing whitespace
            }
            return new Equipment_Item(item_stats.get("Defense"),occupied_slots,20,name,description);
        }
        return null;
    }
}




public abstract class Item {
    // TODO: Make it abstract maybe? Further divide it into consumable item and non
    // consumable item, make those classes abstract too?(armor vs potions) Potions
    // further subdivided into (healing/mana/stamina/stat boosting potions?)
    // separate classes?
    public String NAME; //Let it be public. FFs I dont want to set up another getter for name
    protected String TYPE;
    protected String DESCRIPTION;
    protected Integer VALUE;
    public abstract void Print_Description();
    /** Will return a Map<String,Integer> of given values
     * Values that may be returned: Restored_Health,Restored_Mana,Restored_Stamina, Value(cost of item), Simple statsheet stats
     * @return
     */
    public abstract Map<String,Integer> Get_Value();
    public abstract String Get_Type();
}
interface Equipment_With_Slots {
    List<String> Get_Slots();
}
abstract class Consumable_Item extends Item {//Meh, might delete it. 
}

class Healing_Item extends Consumable_Item {
    // Healing potions, mana potions, stamina potions, that short of thing. Might
    // include poisons even, and they give negative health
    protected int RESTORED_MANA;
    protected int RESTORED_HEALTH;
    protected int RESTORED_STAMINA;
    Healing_Item(int restored_health, int restored_mana, int restored_stamina)
    {   this.TYPE="Healing_Item";
        if(restored_health>=200)
        this.NAME="Big healing potion";
        else if(restored_health>=100)
        this.NAME="Healing potion";
        else
        this.NAME="Small healing potion";
        this.RESTORED_HEALTH=restored_health;
        this.RESTORED_MANA=restored_mana;
        this.RESTORED_STAMINA=restored_stamina;
        this.VALUE=Math.abs(restored_health)+Math.abs(restored_stamina)+Math.abs(restored_mana);
    };
    Healing_Item(int restored_health)
    {
        this.TYPE="Healing_Item";
        this.NAME="Healing potion";
        this.RESTORED_HEALTH=restored_health;
        this.RESTORED_MANA=0;
        this.RESTORED_STAMINA=0;
        this.VALUE=Math.abs(RESTORED_HEALTH)+Math.abs(RESTORED_STAMINA)+Math.abs(RESTORED_MANA);
    };
    @Override
    public Map<String,Integer> Get_Value()
    {
        Map<String,Integer>Potion_Values=new HashMap<>();
        Potion_Values.put("Restored_Health",this.RESTORED_HEALTH);
        Potion_Values.put("Restored_Mana",this.RESTORED_MANA);
        Potion_Values.put("Restored_Stamina",this.RESTORED_STAMINA);
        Potion_Values.put("Value",this.VALUE);
        
        return Potion_Values;
    }
    public String Get_Type()
    {
        return this.TYPE;
    }
    public void Print_Description(){
        System.out.println("This is a Healing_Item, it restores " + this.RESTORED_HEALTH +"hp "
        +this.RESTORED_MANA+"mana "+this.RESTORED_STAMINA+"stamina"+" total value=" + this.VALUE);
    };
}

class Buffing_Item extends Consumable_Item {
    protected Map<String, Integer> CHANGED_STATS;
    private static String[] STATS_NAMES;
    protected Integer DURATION;
    Buffing_Item(Map<String,Integer> map_of_BuffedStats,Integer duration_of_buffs, String name, String description)
    {   CHANGED_STATS=new HashMap <>();
        this.NAME=name;
        this.TYPE="Buffing_Item";
        this.DESCRIPTION=description;
        this.DURATION=duration_of_buffs;
        this.VALUE=100;
        CHANGED_STATS.put("Duration",this.DURATION);
        for(String stat_name: STATS_NAMES)
        {
            Integer stat_value=map_of_BuffedStats.get(stat_name);
            if(stat_value!=null)
            {
                CHANGED_STATS.put(stat_name,stat_value);
            }
            else
            {
                CHANGED_STATS.put(stat_name,0);
            }
        };
    }
    @Override
    public Map<String, Integer> Get_Value()
    {   
        return CHANGED_STATS;
    }
    public String Get_Type()
    {return this.TYPE;}
    public void Print_Description(){
        System.out.println("This is a buffing item, it changes the following stats:");
        for(String stat_name:STATS_NAMES)
        {
            if(CHANGED_STATS.get(stat_name)!=null)
            {
                System.out.println(stat_name + ":" + CHANGED_STATS.get(stat_name));
            }
        }
        System.out.println("It lasts " + this.DURATION + "has the following description: \n" + this.DESCRIPTION);
    };
    static 
    {
    STATS_NAMES=new String[]{"Strength","Dexterity","Constitution","Magic_Strength","Magic_Reserves","Health_Regeneration","Mana_Regeneration","Defense"};
    }
}

class Equipment_Item extends Item implements Equipment_With_Slots{
    protected int DEFENSE_STAT;
    protected List<String> OCCUPIED_SLOTS; // head/body/legs/hands/mask/special(neck or fingers)
    protected int WEIGHT;
    Equipment_Item(int defense, String[] Occupied_Slots, int weight, String name, String description) {
        this.NAME = name;
        this.TYPE = "Equipment_Item";
        this.DESCRIPTION = description;
        this.DEFENSE_STAT = defense;
        this.WEIGHT = weight;
        this.VALUE=defense*10-weight;
        OCCUPIED_SLOTS=new ArrayList<>();
        for(String occupied_slot : Occupied_Slots)
        {
            OCCUPIED_SLOTS.add(occupied_slot);
        }
    }
    public Map<String,Integer> Get_Value()
    {
        Map<String,Integer> stats=new HashMap<>();
        stats.put("Defense",this.DEFENSE_STAT);
        stats.put("Value",this.VALUE);
        //stats.put("Type",this.TYPE);
        return stats;

    };
    public List<String> Get_Slots()
    {
        return OCCUPIED_SLOTS;
    }
    public String Get_Type()
    {return this.TYPE;}
    public void Print_Description() {
        System.out.print("This equipment has the following stats: ");
        System.out.println("Ttotal value: " + this.VALUE);
    }
}