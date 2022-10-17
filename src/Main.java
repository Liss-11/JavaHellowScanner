import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, String> users = getUsersHashMap();
        demoInput(users);
    }

    private static Map<String, String> getUsersHashMap() throws FileNotFoundException {
        File targetFile = new File("my-db.csv");
        Scanner reader = new Scanner(targetFile);
        String line;

        Map<String, String> users = new HashMap<>();
        while(reader.hasNextLine()){
            line = reader.nextLine();
            String[] user_pass = line.split(", ");
            String user = user_pass[0];
            String password = user_pass[1];
            users.put(user, password);
        }
        reader.close();
        reader.close();
        return users;
    }
    private static void demoFileWriter(String message) throws IOException {

            var writer = new FileWriter("log.txt", true);
            writer.write(message + "\n");
            writer.close();
    }

    private static void demoInput(Map<String, String> users) throws IOException {
        var scanner = new Scanner(System.in);
        int count;

        System.out.println("Enter your UserName: ");
        var user = scanner.nextLine();
        if (!users.containsKey(user)){
            System.out.println("User no valid...");
            demoFileWriter(Instant.now().toString() + " USER: " + user + " doesn't Exist!");
            System.exit(1);
        }else{
            count = 0;
            while(count < 3) {
                System.out.println("Enter your password: ");
                var password = scanner.nextLine();
                if (users.get(user).equals(password)) {
                    demoFileWriter(Instant.now().toString() + " USER: " + user + ", With password: " + password + " - SUCCESS");
                    System.out.println("Access for " + user + " is ALLOWED!");
                    printLogEntries();
                    System.exit(1);
                } else {
                    demoFileWriter(Instant.now().toString() + " USER: " + user + ", With password: " + password + " - FAIL");
                    System.out.println("Incorrect PASSWORD! You have " + (3 - count - 1) + " retries more");
                }
                count++;
            }
            demoFileWriter(Instant.now().toString() + " USER " + user + " is BLOCKED");
            System.out.println("SORRY, the user: " + user + " is temporarily BLOCKED!");
        }
        scanner.close();
    }

    public static void printLogEntries() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("If you want to see all the log entries, write \"yes\"");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")){
            File Logs = new File("log.txt");
            Scanner readEntries = new Scanner(Logs);
            while (readEntries.hasNextLine()){
                System.out.println(readEntries.nextLine());
            }
            readEntries.close();
        }else{
            System.out.println("Thanks for using this program, BYE!");
        }
        scanner.close();

    }

}
