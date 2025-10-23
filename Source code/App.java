import java.util.Arrays;

class FigureShitOut {
    protected int x, y;

    public FigureShitOut(int x, int y) {
        System.out.println("Apelez constructor neimplicit figureshitout");
        this.x = x;
        this.y = y;
    }

    FigureShitOut() {
        System.out.println("Apelez constructor implicit figureshitout");
        this.x = 0;
        this.y = 0;
    }

    int sum() {
        return 5;
    }

    int sum(int x) {
        return x + 4;
    }

    int sum(int x, int y) {
        return x + y;
    }
};

class Icant extends FigureShitOut {
    int m, n;

    public Icant(int x1, int x2) {
        System.out.println("Apelez constructor Icant");
        // FigureShitOut(3,4);
        this.m = x1;
        this.n = x2;
    }

    @Override
    int sum() {
        System.out.println("Apelez override din Icant");
        return this.x + this.y + 3;

    }
}

abstract class NPC {
    abstract int gethp();

    abstract int getdmg();
}

class player extends NPC {
    private int hp;
    int speed;

    public player() {
        this.hp = 100;
        this.speed = 10;
    }

    int gethp() {
        return hp;
    }

    int getdmg() {
        return 10;
    }

}
class person
{
    private String nume;
    private int varsta;
    public int id;
    private double[] venit; 
    private static int nrPersoane;
    person()
    {
        this.nume="Necunoscut";
        this.varsta=0;
        venit=new double[12];
    }
    person(int varsta2,String nume2,double[] valori)
    {
        this.varsta=varsta2;
        this.nume=nume2;
        this.venit=valori.clone();
    }
    person(person b)
    {
        this.nume=b.nume;
        this.varsta=b.varsta;
        this.venit=b.venit.clone();
    }
    public String getNume() {
        return nume;
    }
    public static int getNrPersoane() {
        return nrPersoane;
    }
    public int getVarsta() {
        return varsta;
    }
    public int getId() {
        return id;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }
    public void setVenit(double[] venit) {
        this.venit = venit;
    }
    public String toString()
    {
    String s;
    s="Persoana curenta are numele " + this.nume + " cu varsta " + this.varsta + " si venitul " + venit.toString();
    return s;
    }

    static
    {
        person.nrPersoane=0;
    }
    {
        this.id=++nrPersoane;


    }

}
class MathUtil
{
    public MathUtil(){}
    public int multiply(int a, int b){return a*b;}
    public double multiply(double a, double b, double c){return a*b*c;}
    public int multiply(int []values){
        int m=values[0];
        for(int i=1;i<values.length;i+=1)
        {
            m*=values[i];
        }
        return m;
    }
}
class Vehicle
{   
   private String brand;
   private int year;
   Vehicle(String mn,int p){this.brand=mn;this.year=p;}
    public String displayInfo(){
        String s="Masina are brandul " + this.brand + " si a fost fabricata in anul " + year;
        return s;
    }


}
class Car extends Vehicle
{
    private int number;
    Car(int number, String mn, int p){
        super(mn,p);
        this.number=number;}
    @Override
    public String displayInfo() {
        // TODO Auto-generated method stub
        String s=super.displayInfo()+" si cu numarul de usi " + number;
        return s;
    }
}
abstract class Animal
{
abstract void makesound();
}
class Dog extends Animal 
{
@Override
void makesound() {
    System.out.println("Woof");
}
}
class Cat extends Animal 
{
@Override
void makesound() {
    System.out.println("Meow");
}
}

interface Insurable {
double getinsurancecost();
    
}
abstract class Vehicul{
    void drive(){};
}
class motorcycle extends Vehicul
{   
    private int engine;
    private String brand;
    motorcycle(int eng,String brand){this.engine=eng; this.brand=brand;}
    void drive(){System.out.println("Vehicul is driving brand "+brand);}
    double getinsurancecost(){return 3.14159*this.engine;}

}
public class App {
    public static void main(String[] args) throws Exception {

        MathUtil e=new MathUtil();
        int[] bd2=new int[5];
        for(int i=0;i<5;i++)
        bd2[i]=i+1;
        System.out.println(e.multiply(3,4 ));
        System.out.println(e.multiply(3,4 ,5));
        System.out.println(e.multiply(bd2));
        Vehicle ef=new Vehicle("asd",1);
        System.out.println(ef.displayInfo());
        
        Animal[] animal=new Animal[2];
        animal[0]=new Cat();
        animal[1]=new Dog();
        animal[1].makesound();
        animal[0].makesound();
        
        ////////// EX1
        
        int[] v=new int[10];
        for(int i=0;i<10;i++)
        v[i]=i;
        for(int i=0;i<10;i++)
        {System.out.print(v[i]);
        System.out.print(" ");
        }

        for (int i:v)
        {
        System.out.print(i);
        System.out.print(" ");
        }
        System.out.print(Arrays.toString(v));

        /////////
        //////// EX2
        System.out.println("EX@");
        int[][] arr2={{1,2,3},{2,3,4,5},{8,2},{11}};
        for(int i=0;i<arr2.length;i++)
        {
        for(int j=0;j<arr2[i].length;j++)
        {
        System.out.print(arr2[i][j]);
        System.out.print(" ");
        }
        System.out.println("");
        };
        System.out.println(Arrays.deepToString(arr2));

        //////////EX3
        person bd= new person();
        System.out.print(bd.toString());
    }

    static public int dosomething(int x, int y) {
        System.out.println("Doing something bruh");
        return x + y;

    }

}
