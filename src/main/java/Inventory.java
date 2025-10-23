
import java.util.*;

class Inventory_Factory 
{
public static Inventory Create_Inventory(Map<String,Integer> inventory_map) 
{

    Inventory inventory = new Inventory();
    for (Map.Entry<String, Integer> entry : inventory_map.entrySet()) {
        String itemName = entry.getKey();
        Integer itemQuantity = entry.getValue();
        Item item = Table_Item.getInstance().get_item(itemName);
        if (item != null) {
            for (int i = 0; i < itemQuantity; i++) {
                inventory.Add_Item(item);
            }
        } else {
            System.out.println("Item not found: " + itemName);
        }
    }
    return inventory;
}    
};


////
////SORTING CLASSES
////
class Sort_Health implements Comparator {
    public int compare(Object obj1, Object obj2) {
        Item a = (Item) obj1;
        Item b = (Item) obj2;
        if (!(b instanceof Healing_Item) && (a instanceof Healing_Item)) {
            return 1;
        }
        if (!(a instanceof Healing_Item) && (b instanceof Healing_Item)) {
            return -1;
        }
        if (!(a instanceof Healing_Item) && !(b instanceof Healing_Item)) {
            return 0;
        }
        if (a.Get_Value().get("Restored_Health") > b.Get_Value().get("Restored_Health"))
            return 1;
        return -1;
    }
}

class Sort_Value implements Comparator {
    public int compare(Object item1, Object item2) {
        Item aux1 = (Item) item1;
        Item aux2 = (Item) item2;
        // aux1.Print_Description();
        // aux2.Print_Description();
        Integer value_aux1 = aux1.Get_Value().get("Value");
        Integer value_aux2 = aux2.Get_Value().get("Value");
        System.out.println("Comparing " + aux1.Get_Type() + "(" + value_aux1 + ") with " + aux2.Get_Type() + "("
                + value_aux2 + ")");
        if (value_aux1 > value_aux2)
            return 1;
        if (aux1 == aux2)
            return 0;
        return -1;
    }

};

////
//// SORTING CLASSES 
//// 

public class Inventory {
    // TODO: add equipped items too
    // Separate inventory_items and equipped_items?
    // Hopefully won't cause a headache.
    private ArrayList<Item> inventory_items;
    private ArrayList<Item> equipped_items;
    private ArrayList<String> occupied_slots;

    Inventory() {
        inventory_items = new ArrayList<>();
        equipped_items = new ArrayList<>();
        occupied_slots = new ArrayList<>();
    }

    /** Function checks if an item can be equipped, and then it moves it from inventory_items to equipped items
     * Occupied_slots value from the class is used to keep track of all current occupied slots.
     * Will be dynamically modified as new slots might be added.
     * You might after all start only wearing a helmet, but maybe you will wear a cuirass and some pants too.
     * It checks: if item is equipable, if it does not try to occupy an already occupied slot
     * @param index index of the item to be equipped
     * 
     */
    public void Equip_Item(int index) {
        Item item = Remove_Inventory_Item(index);

        if (item.Get_Type() != "Equipment_Item") {
            inventory_items.add(item);
            System.out.println("Tried to equip unequipable item!");
            return;
        }

        
        //Checking to see if we have the slots to equip the item
        //We cant really wear 2 helmets at once, now can we?
        Equipment_With_Slots Slots = (Equipment_With_Slots) item;
        int current_list_size = occupied_slots.size();
        Boolean found_duplicate_slots = false;
        for (String occupied_slot : Slots.Get_Slots()) {
            if (occupied_slots.indexOf(occupied_slot) != -1) {
                found_duplicate_slots = true;
                break;
            }
            occupied_slots.add(occupied_slot);
        }
        if (found_duplicate_slots == true) {
            while (occupied_slots.size() != current_list_size)
               {//int last_index = occupied_slots.size() - 1;
                occupied_slots.removeLast();//To remove the added slots
                //NOTE: before we had occupied_slots.removeLast(), however this is not available in Java 8
               }
            System.out.println("Tried to use an already occupied slot!");
            inventory_items.add(item);
            return;
        }

        //We have equipped the item. Yay!
        equipped_items.add(item);
    }
    
    
    public ArrayList<Item> Get_Equipped_Items()
    {
        return this.equipped_items;
    }
    public ArrayList<Item> Get_Inventory_Items()
    {
        return this.inventory_items;
    }
    
    
    
    
    /**
     * Unequips an item and moves it from the equipped_items to inventory_items
     * @param index index of item to be unequipped
     * @return the removed unequipped item or null otherwise
     */
    public Item Unequip_Equipment_Item(int index) {
        if (index >= 0 && index < equipped_items.size()) {
            Item item = equipped_items.remove(index);
            inventory_items.add(item);
            return item;
        } else {
            System.out.println("Invalid index");
            return null;
        }
    }

    /**
     * Adds the item to inventory_items
     * 
     * @param item_to_add the item object to be added
     */
    public void Add_Item(Item item_to_add) {
        inventory_items.add(item_to_add);
    }

    /**
     * Function to remove a given item
     * 
     * @param index index of the removed item(so we know what item to remove)
     * @return the item that was removed
     */
    public Item Remove_Inventory_Item(int index) {
        // WARNING int vs Integer
        // Removes the item from the specified index and returns item removed
        if (index >= 0 && index < inventory_items.size()) {
            return inventory_items.remove(index);
        } else {
            System.out.println("Invalid index");
            return null;
        }
    }

    /** Function to sort the inventory
     * @param sorting_method how to sort inventory. It can currently be price(cost of item) or value(stats of items)
     */
    public void Sort_Inventory(String sorting_method) {
        switch (sorting_method) {
            case "price": {
                Comparator compare = new Sort_Value();
                Collections.sort(inventory_items, compare);
                break;
            }
            case "value": {
                Comparator v = new Sort_Health();
                Collections.sort(inventory_items, v);
                break;
            }
            case "Type": {
                break;
            }
        }
    }

    public void Print_Inventory() {
        int cnt=0;
        for (Item item_to_print : inventory_items) {
            System.out.println(cnt +":"+item_to_print.NAME);
            cnt+=1;
        }
    }
    public void Print_Equipment() {
        int cnt=0;
        for (Item item_to_print : equipped_items) {
            System.out.println(cnt +":"+item_to_print.NAME);
            cnt+=1;
        }
    }    
};