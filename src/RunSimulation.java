import java.util.Scanner;

public class RunSimulation {
    public static void main(String[] args) throws Exception {
        Administrator admin = new Administrator();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while(!exit) {
            System.out.println("\n========== Administrator Console ==========");
            System.out.println("1. Create User");
            System.out.println("2. Manage User");
            System.out.println("3. Delete User");
            System.out.println("4. EXIT");
            System.out.println("Select an option (e.g., 1):");

            int choice;

            try{
                choice = Integer.parseInt(scanner.nextLine());
            }

            catch(NumberFormatException e) {
                System.out.println("Invalid input, choose a number between 1 and 4.");
                continue;
            }

            switch(choice) {
                case 1:
                    System.out.print("Create your Unique ID: ");
                    String idIn = scanner.nextLine();
                    System.out.print("Enter User Type: [Scientist, Space Agency Representative, Policy Maker]: ");
                    String typeIn = scanner.nextLine();
                    admin.createUser(idIn, typeIn);
                    break;
                case 2:
                    System.out.print("Enter the User ID to manage: ");
                    String manageId = scanner.nextLine();
                    admin.manageUser(manageId);
                    break;
                case 3:
                    System.out.print("Enter the User ID to delete: ");
                    String deleteId = scanner.nextLine();
                    admin.deleteUser(deleteId);
                    break;
                case 4:
                    exit = true;
                    System.out.println("You are now exiting the simulation. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option, choose a number between 1 and 4.");
            }
        }
        scanner.close();
    }
}
