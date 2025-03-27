import java.util.Scanner;
import java.io.File;
import java.io.FileReader;

public class App
{
	public static void main(String[] args) throws Exception {
        String user, pass;
        System.out.println("Enter your username");
        Scanner scanner = new Scanner(System.in);
        
                user = scanner.nextLine();
        
        System.out.println("Enter your password");

                pass = scanner.nextLine();

        System.out.println("Your username is:" + user);
        System.out.println("Your password is:" + pass);

        User one = new User(user, pass);

        File myfile = new File("accounts.txt");
        
        if (myfile.exists()) {
                System.out.println("File Exists");
        }
        

        Scanner fileScanner = new Scanner(myfile);

        Boolean accountexists = false;
        while (fileScanner.hasNextLine()) {
                String filedata = fileScanner.nextLine();
                
                String username = filedata.split(",")[0];

                String password = filedata.split(",")[1];
        
                if (user.equals(username) && pass.equals(password)) {
                        accountexists = true;
                        break;
                }
                
        } 
        if(accountexists){
                System.out.println("Login Success, Hi " + one.getUsername());
        }
        else {
                System.out.println("Login Error");
        }



                fileScanner.close();


        }

}