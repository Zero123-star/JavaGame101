

class Npcd {
    public static int npc_counter;
    public String NPC_IDENTIFIER;
    private int MAX_STAMINA;
    private int MAX_HEALTH;
    private int MAX_MANA;
    private Stats STATSHEET;
    private int current_health;
    private int current_stamina;
    private int current_mana;
    private Inventory INVENTORY; //Here I will add sorting options 101.
    Npcd(int health, int mana, int stamina) {
        this.STATSHEET=new Stats();
        this.MAX_STAMINA = stamina;
        this.MAX_HEALTH = health;
        this.MAX_MANA = mana;
        this.NPC_IDENTIFIER="NPC"+npc_counter;
        this.INVENTORY=new Inventory();
        this.current_health = health;
        this.current_mana = mana;
        this.current_stamina = stamina;

    }

    Npcd() {
        this.INVENTORY=new Inventory();
        this.STATSHEET=new Stats();
        this.MAX_STAMINA = 100;
        this.MAX_HEALTH = 100;
        this.MAX_MANA = 100;
        this.NPC_IDENTIFIER="NPC"+npc_counter;
        this.current_health = 100;
        this.current_mana = 100;
        this.current_stamina = 100;

    }

    Npcd(int max_stamina, int max_health, int max_mana, int current_health, int current_stamina, int current_mana, Stats statsheet, Inventory inventory) 
    {
        //this.NPC_IDENTIFIER = npc_identifier;
        this.MAX_STAMINA = max_stamina;
        this.MAX_HEALTH = max_health;
        this.MAX_MANA = max_mana;
        this.current_health = current_health;
        this.current_stamina = current_stamina;
        this.current_mana = current_mana;
        this.STATSHEET = statsheet;
        this.INVENTORY = inventory;
    }

    public Stats Get_Stats()
    {
        return this.STATSHEET;
    }

    public Inventory Get_Inventory()
    {
        return this.INVENTORY;
    }

    public void restore_health(int value) {
    //Can also be negative values, and acts as a damage_health method
            this.current_health = Math.min(MAX_HEALTH, this.current_health + value);
    }

    public void restore_mana(int value) {
        this.current_mana = Math.min(MAX_MANA, this.current_mana + value);

    }

    public void restore_stamina(int value) {
        this.current_stamina = Math.min(MAX_STAMINA, this.current_stamina + value);
    }

    public void damage_health(int value) {

        this.current_health = Math.max(0, this.current_health - value);
    }
    public int get_max_health() {
        return this.MAX_HEALTH;
    }
    public int get_max_stamina() {
        return this.MAX_STAMINA;
    }
    public int get_max_mana() {
        return this.MAX_MANA;
    }

    public int get_health() {
        return this.current_health;
    }

    public int get_stamina() {
        return this.current_stamina;
    }

    public int get_mana() {
        return this.current_mana;
    }
    static 
    {
        npc_counter=0;
    }
    {
        npc_counter+=1;
    }

}