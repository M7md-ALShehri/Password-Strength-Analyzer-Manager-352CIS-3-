import java.io.*;
import java.util.*;
class PasswordProject {
     public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println("1- Register");
        System.out.println("2- Login");
        System.out.print("Choose: ");
        int choice = scan.nextInt();
        scan.nextLine();
         System.out.print("Username: ");
        String username = scan.nextLine();

        if (choice == 1) {
             System.out.println("\nPassword must follow these rules:");
            System.out.println("  - Length between 8 and 15 characters");
            System.out.println("  - At least one uppercase letter (A-Z)");
            System.out.println("  - At least one lowercase letter (a-z)");
            System.out.println("  - At least one number (0-9)");
            System.out.println("  - At least one special character (!@#$%^&*)");
            System.out.println("  - Must not contain your username");
             String password = "";
            boolean valid = false;
             for (int i = 3; i > 0; i--) {
                System.out.print("Enter password (" + i + " attempts left): ");
                password = scan.nextLine();

                if (!password.toLowerCase().contains(username.toLowerCase()) &&
                    password.length() >= 8 && password.length() <= 15 &&
                    password.matches("(.*[A-Z].*)") &&
                    password.matches("(.*[a-z].*)") &&
                    password.matches("(.*[0-9].*)") &&
                    password.matches("(.*[!@#$%^&*()_+\\-=].*)")) {
                    valid = true;
                    break;
                }
                System.out.println("Password does not meet the requirements.");
            }

            if (!valid) { System.out.println("Too many failed attempts."); return; }

            System.out.println("Password is valid.");

            int score = 0;
            if (password.length() >= 12) score++;
            if (password.matches("(.*[A-Z].*)")) score++;
            if (password.matches("(.*[a-z].*)")) score++;
            if (password.matches("(.*[0-9].*)")) score++;
            if (password.matches("(.*[!@#$%^&*()_+\\-=].*)")) score++;

            if (score <= 3) System.out.println("Strength: Weak");
            else if (score == 4) System.out.println("Strength: Medium");
            else System.out.println("Strength: Strong");

            System.out.print("Confirm password: ");
            String confirm = scan.nextLine();
            if (!confirm.equals(password)) {
                System.out.println("Passwords do not match."); return;
            }
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(username + ":" + password + "\n");
            fw.close();
            System.out.println("Account created successfully!");

        } else if (choice == 2) {

            boolean found = false;

            for (int i = 3; i > 0; i--) {
                System.out.print("Password (" + i + " attempts left): ");
                String password = scan.nextLine();

                File file = new File("users.txt");
                if (!file.exists()) { System.out.println("No users found."); return; }

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts[0].trim().equals(username) && parts[1].trim().equals(password)) {
                        found = true; break;
                    }
                }
                br.close();
                 if (found) break;
                System.out.println("Wrong username or password.");
            }

            if (found) System.out.println("Login successful! Welcome " + username);
            else System.out.println("Too many failed attempts.");
        }

        scan.close();
    }
}
