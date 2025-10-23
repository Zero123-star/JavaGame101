/* 
 * Definește două clase care modelează o relație HAS_A:
Clasa Room:
Atribute: double width, double length.
Un constructor parametrizat și un constructor de copiere.
Clasa House:
Atribute: String address, Room diningRoom, Owner owner.
În relația cu Room, folosește compoziția: inițializează intern o copie a obiectului primit (prin constructor de copiere).
În relația cu Owner (o altă clasă pe care o definești simplu cu atribute precum String name), folosește agregarea: stochează referința directă, fără a crea o copie.
În metoda main, demonstrează diferența:
Dacă se modifică obiectul Room trecut ca argument, observați că House păstrează o copie proprie (compoziție).
Dacă se modifică obiectul Owner, modificarea se reflectă și în casă (agregare).
 * 
 * 
 * 
 * 
*/

class Room {
    public double width;
    public double length;

    Room() {
        this.width = 2;
        this.length = 2;
    }

    Room(Room b) {
        this.width = b.width;
        this.length = b.length;
    }

}

class Owner {
    String Nume;

    Owner() {
        this.Nume = "Gigel";
    }

    Owner(Owner c) {
        this.Nume = c.Nume;
    }
}

class House {
    String address;
    Room diningRoom;
    Owner owner;

    House(Room a, Owner b, String ad) {
        this.owner = b;
        this.diningRoom = new Room(a);
        this.address = ad;
    }

    public void describeHome() {
        System.out.println("La casa data avem adresa " + address + " dining room " + diningRoom.width + " si length "
                + diningRoom.length + " cu owner " + owner.Nume);

    }

}

public class Lab4 {
    public static void main(String[] args) throws Exception {
        System.out.println("Test");
        Room camera = new Room();
        Owner stapan = new Owner();
        // System.out.println(camera.length);
        // Room d=new Room(camera);
        // System.out.println(d.length);
        House casa = new House(camera, stapan, "asddssaasd");
        casa.describeHome();
        stapan.Nume = "ASD";
        camera.length = 323;
        casa.describeHome();
    }
}