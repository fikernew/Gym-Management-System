import java.util.ArrayList;
/**
 * MemberService handles the core business logic for managing gym members,
 * including trainers and customers, utilizing DBManager for persistence.
 */
public class MemberService {

    // ─── ADD TRAINER ──────────────────────────────────────────────────────────
    /**
     * Handles the registration process for new gym trainers.
     * Collects personal details, specialty, and saves the record to the database.
     */
    static void addTrainer() {
        System.out.println("\n-- Add Trainer --");
        try {
            String name      = GymSystem.getStringInput("Name: ");
            int age          = GymSystem.getIntInput("Age: ");
            String phone     = GymSystem.getStringInput("Phone: ");
            String specialty = GymSystem.getStringInput("Specialty (e.g. Cardio, Weightlifting): ");

            // GymException thrown inside Trainer constructor via Person.validate()
            Trainer t = new Trainer(name, age, phone, specialty);
            DBManager.saveTrainer(t);

            System.out.println("\nTrainer registered successfully!");
            System.out.println("Your Login ID is: " + t.getId() + " (use this to login)");

        } catch (GymException e) {
            // Catching custom exception - from Exception Handling PDF
            System.out.println("Registration failed: " + e.getMessage());
        } finally {
            // finally always runs - from Exception Handling PDF
            System.out.println("--- Registration process complete ---");
        }
    }

    // ─── ADD CUSTOMER ─────────────────────────────────────────────────────────
    /**
     * Handles the registration process for new gym customers.
     * Selects membership tiers and persists data via DBManager.
     */
    static void addCustomer() {
        System.out.println("\n-- Add Customer --");
        try {
            String name  = GymSystem.getStringInput("Name: ");
            int age      = GymSystem.getIntInput("Age: ");
            String phone = GymSystem.getStringInput("Phone: ");

            System.out.println("\n-- Select Membership --");
            System.out.println("1. Normal  - $20/month  (Basic gym access)");
            System.out.println("2. VIP     - $50/month  (Gym access + Personal Trainer)");
            System.out.println("3. Premium - $100/month (Gym access + Personal Trainer + Nutrition Plan)");

            int mChoice = GymSystem.getIntInput("Enter choice (1-3): ");

            String membershipType;
            double price;

            switch (mChoice) {
                case 1  -> { membershipType = "Normal";  price = 20.0;  }
                case 2  -> { membershipType = "VIP";     price = 50.0;  }
                case 3  -> { membershipType = "Premium"; price = 100.0; }
                default -> { membershipType = "Normal";  price = 20.0;
                    System.out.println("Invalid choice, defaulting to Normal."); }
            }

            Customer c = new Customer(name, age, phone, membershipType, price);
            DBManager.saveCustomer(c);

            System.out.println("\nCustomer registered successfully!");
            System.out.println("Your Login ID is: " + c.getId() + " (use this to login)");

        } catch (GymException e) {
            System.out.println("Registration failed: " + e.getMessage());
        } finally {
            System.out.println("--- Registration process complete ---");
        }
    }

    // ─── VIEW MEMBERS MENU ────────────────────────────────────────────────────
    /**
     * Displays the administrative portal for viewing registered members.
     * Requires secure admin password authentication.
     */
    static void viewMembersMenu() {
        System.out.println("\n-- View Members --");
        System.out.println("1. List All Members");
        System.out.println("2. List Trainers Only");
        System.out.println("3. List Customers Only");
        System.out.println("4. Back");

        int choice = GymSystem.getIntInput("Enter choice: ");
        if (choice == 4) return;

        if (!authenticateAdmin()) return;

        switch (choice) {
            case 1 -> listAll();
            case 2 -> listTrainers();
            case 3 -> listCustomers();
            default -> System.out.println("Invalid choice.");
        }
    }

    // ─── LIST METHODS ─────────────────────────────────────────────────────────
    /**
     * Retrieves and displays all registered trainers and customers
     * currently persisted in the system.
     */
    static void listAll() {
        ArrayList<Trainer>  trainers  = DBManager.loadTrainers();
        ArrayList<Customer> customers = DBManager.loadCustomers();

        System.out.println("\n====== All Members ======");
        System.out.println("Trainers:  " + trainers.size());
        System.out.println("Customers: " + customers.size());
        System.out.println("=========================");

        // Polymorphism - Person reference calls correct toString()
        for (Person p : trainers)  { System.out.println("\n" + p); System.out.println("-------------------------"); }
        for (Person p : customers) { System.out.println("\n" + p); System.out.println("-------------------------"); }
    }

    static void listTrainers() {
        ArrayList<Trainer> trainers = DBManager.loadTrainers();
        System.out.println("\n====== Trainers (" + trainers.size() + ") ======");
        if (trainers.isEmpty()) { System.out.println("No trainers found."); return; }
        for (Person p : trainers) { System.out.println("\n" + p); System.out.println("-------------------------"); }
    }

    static void listCustomers() {
        ArrayList<Customer> customers = DBManager.loadCustomers();
        System.out.println("\n====== Customers (" + customers.size() + ") ======");
        if (customers.isEmpty()) { System.out.println("No customers found."); return; }
        for (Person p : customers) { System.out.println("\n" + p); System.out.println("-------------------------"); }
    }
    private static boolean authenticateAdmin() {
        String password = GymSystem.getStringInput("Enter Admin Password to View Records: ");
        if (!password.equals(GymSystem.VIEW_PASSWORD)) {
            System.out.println("Access Denied! Incorrect Password.");
            return false;
        }
        return true;
    }
}
