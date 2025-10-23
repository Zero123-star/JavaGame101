import java.util.HashMap;
import java.util.Scanner;

public class PlayerTools {
    Npcd player;
    int TotalPoints;
    Service service;
    Scanner myObj;

    PlayerTools() {
        this.player = new Npcd();
        player.NPC_IDENTIFIER="PLAYER";
        this.TotalPoints = 100;
        service = new Service();
        myObj = new Scanner(System.in);
    }

    public void Game() {
        System.out.println("Create character!");
        while (TotalPoints > 0) {
            System.out.println("Remaining points:" + TotalPoints);
            System.out.println("Press the following keys:");
            System.out.println("1:Add healing potion(100 health) -10 Points");
            System.out.println("2:Increase base stat(+5) -10 Points");
            System.out.println("3:Buy helmet(+25 defense) -25 Points");
            System.out.println("4:Buy chainmail(+50 defense) - 75 Points");
            System.out.println("5:Finish character creation");
            System.out.println("6:Add big healing potion(200 health) -20 Points");
            System.out.println("7:Add buffing potion(+10 strength + 10 defense 5 turns) -20 Points");
            int choice = myObj.nextInt(); // Read user input
            switch (choice) {
                case 1: {
                    TotalPoints -= 10;
                    Item potion = new Healing_Item(100);
                    player.Get_Inventory().Add_Item(potion);
                    System.out.println("Added a potion!");
                    break;
                }
                case 2: {
                    TotalPoints -= 10;
                    System.out.println("Type the statname to be increased:");
                    System.out.println("Valid statenames");
                    String[] STATS_NAMES = new String[] { "Strength", "Dexterity", "Constitution", "Magic_Strength",
                            "Magic_Reserves",
                            "Health_Regeneration", "Mana_Regeneration", "Defense", "Life_Steal" };
                    for (String name : STATS_NAMES)
                        System.out.print(name + " ");
                    System.out.println();
                    String choice2 = myObj.next();
                    player.Get_Stats().Increase_Permanent_Stat(choice2, 5);
                    break;
                }
                case 3: {
                    TotalPoints -= 25;
                    String[] slots = { "Head" };
                    Item helmet = new Equipment_Item(25, slots, 0, "Iron Helmet", "Simple helmet");
                    player.Get_Inventory().Add_Item(helmet);
                    break;
                }
                case 4: {
                    TotalPoints -= 75;
                    String[] slots = { "Chest" };
                    Item chainmail = new Equipment_Item(50, slots, 0, "chainmail", null);
                    player.Get_Inventory().Add_Item(chainmail);
                    break;

                }
                case 5: {
                    TotalPoints = 0;
                    break;
                }
                case 6: {
                    TotalPoints -= 20;
                    Item potion = new Healing_Item(200);
                    potion.NAME = "Big_Potion"; // Yeah if you want PURE encapsulation getter is in order.
                    player.Get_Inventory().Add_Item(potion);
                    System.out.println("Added a big potion!");
                    break;
                }
                case 7: {
                    TotalPoints -= 20;
                    HashMap<String, Integer> values = new HashMap<>();
                    values.put("Strength", 10);
                    values.put("Defense", 10);
                    Item potion = new Buffing_Item(values, 5, "Buffing potion", "desc");
                    player.Get_Inventory().Add_Item(potion);
                    System.out.println("Added a buffing potion!");
                    break;
                }
            }
        }
        Game2();
    };

    public void Game2() {
        while (1 > 0) {
            System.out.println("1. Show inventory");
            System.out.println("2.Equip item");
            System.out.println("3.Unequip item");
            System.out.println("4.Use item");
            System.out.println("5.(Debug)Restore health to maximum");
            System.out.println("6.Sort inventory");
            System.out.println("7.Fight an npc!");
            System.out.println("8.Show current stats");
            System.out.println("9.(Debug)Damage health by 10 points");
            // Scanner myObj = new Scanner(System.in);
            Integer choice = myObj.nextInt();
            switch (choice) {
                case 1: {
                    Inventory inv = this.player.Get_Inventory();
                    System.out.println("Inventory:");
                    inv.Print_Inventory();
                    System.out.println("Equipped items:");
                    inv.Print_Equipment();
                    System.out.println("\n\n");
                    break;
                }
                case 2: {
                    System.out.println("Please specify index to be equipped:");
                    Inventory inv = this.player.Get_Inventory();
                    inv.Print_Inventory();
                    int auxiliary = myObj.nextInt();
                    inv.Equip_Item(auxiliary);
                    this.service.Actualize_Equipped_Items(player);
                    System.out.println("\n\n");
                    break;
                }
                case 3: {
                    System.out.println("Please specify index to be equipped:");
                    Inventory inv = this.player.Get_Inventory();
                    inv.Print_Equipment();
                    int auxiliary = myObj.nextInt();
                    Item item = inv.Unequip_Equipment_Item(auxiliary);
                    if (item != null)
                        this.service.Unequip_Item(item, player);
                    System.out.println("\n\n");
                    break;
                }
                case 4: {
                    System.out.println("Specift index of item to be used:");
                    Inventory inv = this.player.Get_Inventory();
                    inv.Print_Inventory();
                    int auxiliary = myObj.nextInt();
                    Item item = inv.Remove_Inventory_Item(auxiliary);
                    if (item.Get_Type() == "Equipment_Item") {
                        inv.Add_Item(item);
                        System.out.println("Please use equip command instead!");
                    } else {
                        service.Apply_Item(item, player);
                    }
                    System.out.println("\n\n");
                    break;
                }
                case 5: {
                    player.restore_health(555555);
                    System.out.println("\n\n");
                    break;
                }
                case 6: {
                    System.out.println("Type value or price for sorting method");
                    String sort = myObj.next();
                    player.Get_Inventory().Sort_Inventory(sort);
                    System.out.println("\n\n");
                    break;
                }
                case 7: {
                    Fight();
                    return;
                }
                case 8: {
                    player.Get_Stats().Print_Stats_Values();
                    System.out.println("\n\n");
                    break;
                }
                case 9: {
                    player.damage_health(10);
                    System.out.println("\n\n");
                    break;
                }
            }
        }
    }

    public void Fight() {
        Npcd enemy = new Npcd();
        double value = service.getRandomDouble(1, 15);
        Integer aux = (int) value;
        enemy.Get_Stats().Increase_Permanent_Stat("Strength", aux);
        Npcd[] entities={player,enemy};
        while (player.get_health() > 0 && enemy.get_health() > 0) {
            System.out.println("Your health: " + player.get_health() + "Your stamina: " + player.get_stamina());
            System.out.println("Enemy health: " + enemy.get_health());
            System.out.println("1: Basic melee attack");
            System.out.println("2: Use inventory item");
            System.out.println("3: Bleeding melee attack(-30 stamina)");
            System.out.println("4: Pass turn and check ur stats");
            int choice = this.myObj.nextInt();
            switch (choice) {
                case 1: {
                    service.NPC1_Attacks_NPC2(player, enemy, "Physical");
                    break;
                }
                case 2: {
                    System.out.println("Specift index of item to be used:");
                    Inventory inv = this.player.Get_Inventory();
                    inv.Print_Inventory();
                    int auxiliary = myObj.nextInt();
                    Item item = inv.Remove_Inventory_Item(auxiliary);
                    if (item.Get_Type() == "Equipment_Item") {
                        inv.Add_Item(item);
                        System.out.println("Please use equip command instead!");
                    } else {
                        service.Apply_Item(item, player);
                    }
                    System.out.println("\n\n");
                    break;
                }
                case 3: {
                    service.NPC1_Attacks_NPC2(player, enemy, "Bleeding");
                    break;
                }
                case 4: {
                    player.Get_Stats().Print_Stats_Values();
                    break;
                }
            }
        double rng=service.getRandomDouble(1, 100);
        if(rng<20)
        {
            service.NPC1_Attacks_NPC2(enemy, player, "Bleeding");
            System.out.println("Enemy decided to strike you with bleeding strike!");
        }
        else 
        {
            service.NPC1_Attacks_NPC2(enemy, player, "Physical");
            System.out.println("Enemy decided to strike you with basic strike");
        }
        service.Pass_Turn(entities);
        }

    };
}