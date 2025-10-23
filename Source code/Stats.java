import java.util.*;

class Value_and_Turns {
    // Struct to store value and how many turns it will be applied
    // Used in the STATS
    public Integer value;
    public Integer remaining_turns;
    public Boolean permanent;
    public String source;
    // source => Where does this value come from? Armor? Strength Potion? Etc.
    // Returns name.
    // permanent => Is a value and turn "permanent"? eg: an armor equiped shouldn't
    // have a remaining turns value
    // But a player can still unequip it, so therefore its stat values should no
    // longer be given.

    Value_and_Turns() {
        this.value = 0;
        this.remaining_turns = 0;
        this.permanent = false;
        source = null;
    }

    Value_and_Turns(int value, int turns, Boolean permanent, String source) {
        this.value = value;
        this.remaining_turns = turns;
        this.permanent = permanent;
        this.source = source;
    }

    Value_and_Turns(Value_and_Turns number) {
        this.value = number.value;
        this.remaining_turns = number.remaining_turns;
        this.permanent = number.permanent;
        this.source = number.source;
    }
}

public class Stats {
    protected Map<String, Integer> PERMANENT_STATS;
    // Not coming from any equipment, accesories. Base stats of a character, can be
    // permanently improved through level ups
    private static String[] STATS_NAMES;
    protected Map<String, ArrayList<Value_and_Turns>> TEMPORARY_STATS;

    // Stats coming purely from equipment, accesories, etc.
    // Using an arraylist, so that if a player chugs a dozen of regeneration potions
    // at different turns, we can track them all separately and remove them
    // efficiently
    // Temporary stat has permanent=true if it is a piece of equipment, as it is
    // "permanent" until unequiped.
    Stats() {
        PERMANENT_STATS = new HashMap<>();
        PERMANENT_STATS.put("Strength", 10);
        PERMANENT_STATS.put("Dexterity", 10);
        PERMANENT_STATS.put("Constitution", 10);
        PERMANENT_STATS.put("Magic_Strength", 10);
        PERMANENT_STATS.put("Magic_Reserves", 10);
        PERMANENT_STATS.put("Health_Regeneration", 0);
        PERMANENT_STATS.put("Mana_Regeneration", 0);
        PERMANENT_STATS.put("Defense", 10);
        PERMANENT_STATS.put("Life_Steal", 0);

        TEMPORARY_STATS = new HashMap<>();
        TEMPORARY_STATS.put("Strength", new ArrayList<>());
        TEMPORARY_STATS.put("Dexterity", new ArrayList<>());
        TEMPORARY_STATS.put("Constitution", new ArrayList<>());
        TEMPORARY_STATS.put("Magic_Strength", new ArrayList<>());
        TEMPORARY_STATS.put("Magic_Reserves", new ArrayList<>());
        TEMPORARY_STATS.put("Health_Regeneration", new ArrayList<>());
        TEMPORARY_STATS.put("Mana_Regeneration", new ArrayList<>());
        TEMPORARY_STATS.put("Defense", new ArrayList<>());
        TEMPORARY_STATS.put("Life_Steal", new ArrayList<>());
    }

    /**
     * Returns hashmap with the stat values from the statsheet.
     * The key value will contain the sum of the PERMANENT aswell as Valid temporary
     * stats
     * 
     * @param stat_names = Array containing all the stats we are searching for
     * @return Hashmap containing the key value of all requested
     *         stats(Temporary+Permanent)
     */
    public Map<String, Integer> Get_Requested_Stats(String[] stat_names) {

        Map<String, Integer> stat_and_values = new HashMap<>();
        for (String stat_name : stat_names) {
            Integer value = PERMANENT_STATS.get(stat_name);
            if (value == null) {
                System.out.println("ERROR, tried to access nonexistent stat:" + stat_name);
                return null;
            }
            ArrayList<Value_and_Turns> Temporary_Stat_List = TEMPORARY_STATS.get(stat_name);
            for (Value_and_Turns temporary_stat_value : Temporary_Stat_List) {
                value += temporary_stat_value.value;
            }
            ;
            stat_and_values.put(stat_name, value);
            // System.out.println("Added for " + stat_name + " value " + value);
        }
        ;

        return stat_and_values;
    };

    /**
     * Function to add another temporary stat value to a given key(statname)
     * 
     * @param statName          name of stat we want to add another value
     * @param Stat_Value_To_Add the value we want to add, it contains its integer
     *                          value as well as the turn duration and source
     */
    public void Add_Temporary_Stat(String statName, Value_and_Turns Stat_Value_To_Add) {
        ArrayList<Value_and_Turns> list = TEMPORARY_STATS.get(statName);
        if (list != null) {
            if (Stat_Value_To_Add.permanent == false)
                list.add(Stat_Value_To_Add);
            else {
                for (Value_and_Turns value : list) {
                    if (value.source == Stat_Value_To_Add.source) {
                        System.out.println("DEBUG:Tried to add an equipment value twice!(" + value.source + ")");
                        return;
                    }
                }
                list.add(Stat_Value_To_Add);
            }
        } else {
            System.err.println("Stat name not found: " + statName);
        }
    }

    /**
     * Primarily used when removing equipment. For removing temporary stats whose
     * cooldown has ended, check Pass_Turn_And_Check
     * 
     * @param stat_source the source of a Stat_Value objects. Basically used to know
     *                    exactly what equipment we want removed
     */
    public void Remove_Temporary_Stat(String stat_source) {
        // This function is primarily for unequiping items
        // As they have their status .permanent=true
        ArrayList<Value_and_Turns> stat_list = TEMPORARY_STATS.get("Defense");
        // For now, all equiped stats can only give defense.
        // TODO: Equipment which modify stats, have a return of affected stats to check.
        if (stat_list != null) {
            Iterator<Value_and_Turns> statList_iterator = stat_list.iterator();
            while (statList_iterator.hasNext()) {
                Value_and_Turns status = statList_iterator.next();
                if (status.source == stat_source)
                    statList_iterator.remove();
                System.out.println("REMOVED STATUS!");
            }
        }
    };

    /**
     * Function to increase a permanent stat(you level up and want to increase
     * strength by 1 point)
     * 
     * @param statName name of the permanent stat
     * @param value    how much you increase it
     */
    public void Increase_Permanent_Stat(String statName, Integer value) {
        // You level up and want to increase your strength.
        // This functions increases that stat

        Integer val = PERMANENT_STATS.get(statName);
        if (val != null) {
            val += value;
            PERMANENT_STATS.put(statName, val);
            return;
        }
        System.out.println("Couldnt find a stat for " + statName);
    }

    /**
     * Print the name of all stats and their values.
     * The value consists of the sum of permanent and all temporary stats of the
     * given key
     * 
     */
    public void Print_Stats_Values() {
        for (String stat_name : STATS_NAMES) {
            System.out.print(stat_name + ": ");
            Integer stat_value = PERMANENT_STATS.get(stat_name);// Add the permanent stat value
            ArrayList<Value_and_Turns> temporary_stat_list = TEMPORARY_STATS.get(stat_name);
            if (temporary_stat_list != null) {
                for (Value_and_Turns temporary_stat : temporary_stat_list) {
                    stat_value += temporary_stat.value;// add any temporary stat value to the current stat
                }
                ;
            }
            System.out.println(stat_value);// print the stat value(permanent stat + all temp stat values)
        }
    }

    /**
     * Passes a "turn" and removes all temporary stats that have had their duration
     * end.
     * Decreases the remaining duration of all temporary stats by 1. "Permanent"
     * temporary stats unaffected.
     * 
     */
    public void Pass_Turn_And_Check() {// Passes one turn
                                       // Will remove any temporary stats that are not permanent and their time
                                       // ended(turn=0)
        for (String stat_name : STATS_NAMES) {
            ArrayList<Value_and_Turns> temporary_stat_list = TEMPORARY_STATS.get(stat_name);
            if (temporary_stat_list != null) {
                // Use an iterator to safely remove elements during iteration
                Iterator<Value_and_Turns> iterator = temporary_stat_list.iterator();
                while (iterator.hasNext()) {
                    Value_and_Turns temporary_stat = iterator.next();
                    if (temporary_stat.permanent == false) {
                        temporary_stat.remaining_turns -= 1; // decrease turn by 1
                        if (temporary_stat.remaining_turns <= 0) {
                            iterator.remove(); // if remaining turns <=0 remove the stat effect
                        }
                    }
                }
            }
        }
    }

    static {
        STATS_NAMES = new String[] { "Strength", "Dexterity", "Constitution", "Magic_Strength", "Magic_Reserves",
                "Health_Regeneration", "Mana_Regeneration", "Defense", "Life_Steal" };
    };

    /**
     * This functions adds a given statsheet to the temporary stats.
     * Note: statsheet as in Map<String,Value_and_Turns>, not as in a Stats object.
     * 
     * @param Statsheet statsheet to be added to Temporary Stats
     */
    public void Add_Temporary_Statsheet(Map<String, Value_and_Turns> Statsheet) {
        Set<String> keys = Statsheet.keySet();
        for (String stat_name : keys) {
            this.Add_Temporary_Stat(stat_name, Statsheet.get(stat_name));
        }

    }

};
