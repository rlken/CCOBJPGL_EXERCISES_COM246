import java.util.Scanner;
import java.io.File;
import java.io.FileReader;

public class App
{
	public static void main(String[] args) throws Exception {
        DataScientist dataScientist1 = new DataScientist("John", null, 0);

                System.out.println("Name: " + dataScientist1.name);
                System.out.println("Work: " + dataScientist1.getWork());
                System.out.println("Salary: " + dataScientist1.getSalary());

        
        Researcher researcher1 = new Researcher("Jane", null, 0);

                System.out.println("Name: " + researcher1.name);
                System.out.println("Work: " + researcher1.getWork());
                System.out.println("Salary: " + researcher1.getSalary());
        }
        Car mycar = new Car(6, "10kwh");

        System.out.println("My car has a " + mycar.getBattery() + "battery");
        System.out.println(" and it also has " + mycar.getCylinder() + "cylinders");
    }