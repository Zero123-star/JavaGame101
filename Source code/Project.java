
import java.util.*;
/*  
 * Shift+Alt+F
 * Idea:
 * Simple "combat" text based game
 * Fight enemy 1 on 1. Attack/Defend/Skills/Items/Etc  //Mostly implemented, more skills, more entertainment
 * After you defeat, you can fight another enemy or go to shop/training grounds/etc spend money earned for better equipment //todo
 * Skills can be chosen from experience points //skill system
 * Creation system. You get fixed points to create a character: health,skills,mana,gear and you fight against another created character //mostly implemetned
 */

public class Project {
    public static void main(String[] args) throws Exception {
        

        PlayerTools p=new PlayerTools();
        p.Game();
        /*
            DEBUG STUFF BELOW, IGNORE
        
        
        Npcd testing= new Npcd();
        Map<String,Value_and_Turns>mp=new HashMap<>();
        Value_and_Turns aux=new Value_and_Turns(100,0,false,"testing");
        Inventory inv = testing.Get_Inventory();//testing.;
        String[][] slots={{"Head","Tail"},{"Head"}};
        Item ep = new Equipment_Item(100, slots[0], 0, "Desu", null);
        inv.Add_Item(ep);
        ep = new Healing_Item(100);
        inv.Add_Item(ep);
        ep=new Equipment_Item(200, slots[1], 0, "Desu2", null);
        inv.Add_Item(ep);
        inv.Sort_Inventory("Price_Value");
        inv.Print_Inventory();
        inv.Equip_Item(1);
        inv.Print_Inventory();
        inv.Equip_Item(1);
        inv.Print_Inventory();
        Service service=new Service();
        testing.damage_health(30);
        System.out.println(testing.get_health());
        service.Apply_Item(inv.Remove_Inventory_Item(0), testing);
        System.out.println(testing.get_health());
        service.Actualize_Equipped_Items(testing);
        testing.Get_Stats().Print_Stats_Values();
        service.Actualize_Equipped_Items(testing);
        System.out.println(inv.Get_Equipped_Items().size());
        //inv.Unequip_Item(0);
        service.Unequip_Item(inv.Unequip_Equipment_Item(0), testing);
        testing.Get_Stats().Print_Stats_Values();
        */
        /*System.out.println("Test");
        String[][] val={{"Head","Tail","Eye"},{"Fluffy","Tail"}};
        Item v= new Equipment_Item(30, val[0], 30, "Something_name", "Placeholder");
        Inventory p;
        p=new Inventory();
        p.Add_Item(v);
        v=new Healing_Item(30);
        p.Add_Item(v);
        v=new Healing_Item(100);
        p.Add_Item(v);
        //p.Remove_Item(1);
        //p.Print_Inventory();
        Healing_Item[] hp={new Healing_Item(123),new Healing_Item(-34)};
        for(Healing_Item m : hp)
        {p.Add_Item(m);}
        p.Sort_Inventory("Price_Value");
        p.Print_Inventory();
        //v.Print_Statements();
        //test.Show_Stats();
        int cnt=0;
        int p1wins=0;
        int p2wins=0;
        ArrayList<String> Occ;
        Occ= new ArrayList<>();
        Occ.add("ASD");
        if(Occ.indexOf("ASD")!=-1)
        Occ.add("AS");
        System.out.println(Occ.size());
        Occ.remove("ASD");
        System.out.println(Occ.size());
*/
/* /
        while(cnt<1000){
            cnt++;
            Npcd player1= new Npcd();
            Npcd player2= new Npcd();
            Npcd[] entity_array={player1,player2};
            Service service = new Service();
            player2.Get_Stats().Increase_Permanent_Stat("Life_Steal", 100);
            while (player1.get_health()>0 && player2.get_health()>0)
            {
                if(player1.get_health()>0)
                {
                service.NPC1_Attacks_NPC2(player1, player2, "Bleeding");
                }
                if(player2.get_health()>0)
                {
                    service.NPC1_Attacks_NPC2(player2, player1, "Physical");
                }
                service.Pass_Turn(entity_array);

            }
            if(player1.get_health()>0)
                p1wins++;
            else
                p2wins++;
                //player1.Get_Stats().Print_Stats_Values();
            //m.Print_Stats_Values();
        }
        System.out.println("Player1 won: "+ p1wins+ " times");
        System.out.println("Player2 won: "+ p2wins+ " times");
*/
        }
}