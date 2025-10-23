
//import java.math.*;
import java.util.*;
public class Service {
    public static double getRandomDouble(int lowerBound, int upperBound) 
    {   //Returns value between [lowerBound,upperBound)
        Random rand = new Random();
        return lowerBound + (upperBound - lowerBound) * rand.nextDouble();
    }
    public int NPC1_BasicMelee_NPC2(Npcd player1, Npcd player2)
    {
        //Handles damage logic between p1 and p2, returns the damage dealt
        
        Stats Stat1=player1.Get_Stats();
        Stats Stat2=player2.Get_Stats();
        String[] To_Check={"Defense","Strength","Life_Steal"};
        double rng=(int)(Math.random()*100);
        rng=rng%30+1;
        double damage=Math.max(1,
        Stat1.Get_Requested_Stats(To_Check).get("Strength")-
        Stat2.Get_Requested_Stats(To_Check).get("Defense"));
        rng/=10;
        damage=damage+damage*rng;
        int aux=(int) damage;
        player2.damage_health(aux);
        int attacker_lifesteal=Stat1.Get_Requested_Stats(To_Check).get("Life_Steal");
        if(attacker_lifesteal>0)
        {
            int restored_health=(aux*attacker_lifesteal)/100;
            player1.restore_health(restored_health);
        };
        return aux;
    };

    public void NPC1_Attacks_NPC2(Npcd player1, Npcd player2, String Attack_Type) {    
        //Attack logic for different skills
        //Will call attack functions
        switch (Attack_Type){
            case "Physical": { 
                int value=NPC1_BasicMelee_NPC2(player1, player2);
                System.out.println(player1.NPC_IDENTIFIER + " dealt " + value + " damage to " + player2.NPC_IDENTIFIER);
                break;}
            case "Bleeding":{
                double rng=getRandomDouble(0, 101);
                String[] st={"Dexterity"};
                Integer dex_value=player1.Get_Stats().Get_Requested_Stats(st).get("Dexterity");
                int uhitbro=0;
                if(rng<=5+dex_value && player1.get_stamina()>=30)
                {
                uhitbro=1;
                int val2=-30;
                player1.restore_stamina(val2);
                Value_and_Turns v=new Value_and_Turns(-14,3,false,"bleeding");
                System.out.println(player1.NPC_IDENTIFIER + "succesfully caused " + player2.NPC_IDENTIFIER +" to bleed! ");
                player2.Get_Stats().Add_Temporary_Stat("Health_Regeneration", v);
                }
                if(uhitbro==0)
                {System.out.println(player1.NPC_IDENTIFIER + "failed to inflict bleeding strike! lost half the normal stamina");
                if(player1.get_stamina()>30)
                    player1.restore_stamina(-15);
                }
                int value=NPC1_BasicMelee_NPC2(player1, player2);    
                System.out.println(player1.NPC_IDENTIFIER + " dealt " + value + " damage to " + player2.NPC_IDENTIFIER);
            }
        
        }
    };
    
    public void Pass_Turn(Npcd[] entities)
    {
        //Passes a turn
        //Applies any end of turn effects(health regeneration, mana regeneration, stats, etc)
        //Does this to ALL entities
    String[] To_Check={"Mana_Regeneration","Health_Regeneration","Constitution"};
    for (Npcd player:entities)
    {
        Map<String,Integer>player_stats=player.Get_Stats().Get_Requested_Stats(To_Check);
        Integer health=player_stats.get("Health_Regeneration");
        Integer mana=player_stats.get("Mana_Regeneration");
        Integer constitution=player_stats.get("Constitution");
        if(mana>0)
        {
            player.restore_mana(mana);
        }
        if(health!=0)
        {
            player.restore_health(health);
        }
        player.restore_stamina(constitution/2);
        if(player.NPC_IDENTIFIER=="PLAYER")
        {System.out.println("Restored health:" + health + " stamina " + constitution);
        System.out.println("Current relevant stats:");
        String[] Stats={"Strength","Defense","Health_Regeneration"};
        Map<String,Integer> val=player.Get_Stats().Get_Requested_Stats(Stats);
        System.out.println("(Strength,Defense,HealthReg)(" + val.get("Strength") + ","+val.get("Defense")+","+val.get("Health_Regeneration")+")");
    }
        
        player.Get_Stats().Pass_Turn_And_Check();
    }
    };

    /** Applies an item to an entity: you drink a healing potion, buffing potion, that sort of thing
     * Also works for equipment(applying its stats)
     * @param item item that will be applied
     * @param entity entity that will be affected by the item's effects
     */
    public void Apply_Item(Item item, Npcd entity)
    {
        Map<String,Integer>item_values=item.Get_Value();
        String item_type=item.Get_Type();
        switch(item_type)
        {
        case "Healing_Item":
            {
                String[] Check={"Restored_Health","Restored_Mana","Restored_Stamina"};
                entity.restore_health(item_values.get("Restored_Health"));
                entity.restore_mana(item_values.get("Restored_Mana"));
                entity.restore_stamina(item_values.get("Restored_Stamina"));
                break;    
            }
        case "Buffing_Item":
        {
            Set<String> Check = item_values.keySet();
            int duration=item_values.get("Duration");
            Map<String,Value_and_Turns>updated_item_values=new HashMap<>();
            for(String stat_name:Check)
            {
                if(stat_name!="Duration")
                {
                    Value_and_Turns ob =new Value_and_Turns(item_values.get(stat_name), duration, false, item_type);
                    updated_item_values.put(stat_name,ob);
                }
            }
            entity.Get_Stats().Add_Temporary_Statsheet(updated_item_values);
            break;
        }
        case "Equipment_Item":
        {
            Set<String> Check = item_values.keySet();
            int duration=5;
            Map<String,Value_and_Turns>updated_item_values=new HashMap<>();
            for(String stat_name:Check)
            {
                if(stat_name!="Duration")
                {
                    Value_and_Turns ob =new Value_and_Turns(item_values.get(stat_name), duration, true, item.NAME);
                    updated_item_values.put(stat_name,ob);
                }
            }
            entity.Get_Stats().Add_Temporary_Statsheet(updated_item_values);
            break;
        }
        }
        
    }
    public void Unequip_Item(Item item,Npcd entity)
    {
        //Removes the items effect from the statsheet
        entity.Get_Stats().Remove_Temporary_Stat(item.NAME);
    };
    public void Actualize_Equipped_Items(Npcd entity)
    {
        //Re-applies the equipped item effects to the statsheets. Should NOT cause duplicates.
        for(Item equipped_item :entity.Get_Inventory().Get_Equipped_Items())
        this.Apply_Item(equipped_item, entity);
    }
};