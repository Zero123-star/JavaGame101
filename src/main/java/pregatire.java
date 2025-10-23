import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.lang.*;

interface m {
    abstract void mn();
}
class mica{
public static HashMap<String,Integer> v= new HashMap<>();



}
class vehicle 
{
    public String name;
    public Integer value;
    public String type;
    public vehicle(String name, Integer value, String type)
    {
        this.name=name;
        this.value=value;
        this.type=type;
    }
    public String getName()
    {return this.name;}
    public Integer getValue()
    {
        return this.value;
    }
    public String getType()
    {
        return this.type;
    }
    public String toString()
    {
        return name+" "+type + " " + this.value;
    }
}




abstract class b {}
class v extends b implements m 
{
public void mn(){System.out.println("ASD");}
public void dm(){System.out.println("Hapy");}
}
class book implements Runnable
{
static File m=new File("uwu.txt");
//static Scanner b=new Scanner(m);

public void run(){
System.out.println("I AM RUNNING RUNNING RUNNING");
try {PrintWriter auxi = new PrintWriter(new FileWriter(m, true));
Scanner b= new Scanner(m);
Integer cnt=5;
while(b.hasNextLine() && cnt>=0)
{
    System.out.println("Next line!!!");
    cnt--;
String aux2=b.nextLine();
String[] v=aux2.split("[ ,!]+");

aux2+=" MODIFYING CAUSE WHY NOT!!!!1111 ";
for(String s:v)
System.out.print(s+" ");
//auxi.println(aux2);
}
String aux2=Thread.currentThread().getName();
//auxi.println("Sending love from the current thread " + aux2 );
b.close();
auxi.close();
}
catch(Exception e)
{System.out.println("Error");};
System.out.println("I am done now f of11!!");
return;


}
};


public class pregatire {
    public static void main(String[] args) throws Exception{
        ArrayList<vehicle> v= new ArrayList<>();
        v.add(new vehicle("dacia",123,"logan"));
        v.add(new vehicle("excalibur",444,"logan"));
        v.add(new vehicle("dacia",30,"mik"));
        v.add(new vehicle("lig",123,"v"));
        v.add(new vehicle("dacia",1203,"v"));
    //v.stream()
    //.filter(vehicul->vehicul.getName()=="dacia")
    //.forEach(System.out::println);

    v.stream()
    .sorted((v1,v2)->v1.getValue()>v2.getValue() ? 1 : -1)
    .collect(Collectors.groupingBy(vehicle::getName))
    .forEach((e,p)->{
        System.out.println(e);
        p.forEach((el) -> {System.out.println("Mimi" + el.getType());});
    });

    /* 
    System.out.println(
    v.stream()
    .sorted((v1,v2)->v1.getValue()>v2.getValue() ? 1 : -1)
    .distinct()
    .max((v1,v2)->v1.getValue()>v2.getValue() ? 1 : -1)
    .orElse(null)
    );*/
}
}