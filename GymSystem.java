import java.util.Scanner;

public class GymSystem {

    static final String ADMIN_ID = "ADMIN001";
    static final String VIEW_PASSWORD = "ADMIN001";
    static Scanner sc  = new Scanner(System.in);

    public static void main(String[] args) {

        // Load ID counters from DB so new IDs continue correctly
        DBManager.loadCounts();

        printWelcome();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> memberLogin();
                case 3 -> memberRegister();
                case 4 -> MemberService.viewMembersMenu();
                case 5 -> {
                    System.out.println("\nGoodbye! See you at the gym!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        sc.close();
    }

    // ─── WELCOME ──────────────────────────────────────────────────────────────

    static void printWelcome() {
        System.out.println("\n========================================");
        System.out.println("     Welcome to FitLife Gym System      ");
        System.out.println("========================================");
    }

    static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Admin Login");
        System.out.println("2. Member Login");
        System.out.println("3. Member Register");
        System.out.println("4. View Members");
        System.out.println("5. Exit");
    }

    // ─── ADMIN LOGIN ──────────────────────────────────────────────────────────

    static void adminLogin() {
        System.out.println("\n-- Admin Login --");
        String id = getStringInput("Enter Admin ID: ");

        if (!id.equals(ADMIN_ID)) {
            System.out.println("Access Denied. Invalid Admin ID.");
            return;
        }

        System.out.println("Welcome, Admin!");
        adminMenu();
    }

    static void adminMenu() {
        boolean stay = true;
        while (stay) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Trainer");
            System.out.println("2. Add Customer");
            System.out.println("3. Back to Main Menu");

            int choice = getIntInput("Enter choice: ");
            switch (choice) {
                case 1 -> MemberService.addTrainer();
                case 2 -> MemberService.addCustomer();
                case 3 -> stay = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ─── MEMBER LOGIN ─────────────────────────────────────────────────────────

    static void memberLogin() {
        System.out.println("\n-- Member Login --");
        String id = getStringInput("Enter your ID (e.g. T001 or C001): ");

        Person member = DBManager.findById(id);

        if (member == null) {
            System.out.println("No member found with ID: " + id);
            return;
        }

        System.out.println("\nWelcome, " + member.getName() + "!");
        System.out.println("\n-- Your Profile --");
        System.out.println(member);  // Polymorphism - calls correct toString()
    }

    // ─── MEMBER REGISTER ──────────────────────────────────────────────────────

    static void memberRegister() {
        System.out.println("\n-- Member Register --");
        System.out.println("1. Register as Trainer");
        System.out.println("2. Register as Customer");

        int choice = getIntInput("Enter choice: ");
        switch (choice) {
            case 1 -> MemberService.addTrainer();
            case 2 -> MemberService.addCustomer();
            default -> System.out.println("Invalid choice.");
        }
    }

    // ─── INPUT HELPERS ────────────────────────────────────────────────────────

    static String getStringInput(String prompt) {
        String input = "";
        while (input.isEmpty()) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.isEmpty()) System.out.println("Field cannot be empty.");
        }
        return input;
    }

    static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
