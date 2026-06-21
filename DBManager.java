import java.sql.*;
import java.util.ArrayList;

// JDBC - from Database Programming PDF
public class DBManager {

    private static final String URL      = "jdbc:postgresql://localhost:5432/gym_db";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "08080808";

    // Establish connection - from JDBC PDF
    private static Connection getConnection() throws SQLException {
        try {
            // Manually load the PostgreSQL driver - from JDBC PDF
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found. Make sure the JAR is included.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ─── SETUP ────────────────────────────────────────────────────────────────

    // Load counts from DB so generated IDs continue from where they left off
    public static void loadCounts() {
        Connection conn = null;
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();

            ResultSet rsT = stmt.executeQuery("SELECT COUNT(*) FROM trainers");
            if (rsT.next()) Person.setTrainerCount(rsT.getInt(1));

            ResultSet rsC = stmt.executeQuery("SELECT COUNT(*) FROM customers");
            if (rsC.next()) Person.setCustomerCount(rsC.getInt(1));

        } catch (SQLException e) {
            System.out.println("Error loading counts: " + e.getMessage());
        } finally {
            // finally block - from Exception Handling PDF
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
        }
    }

    // ─── TRAINER OPERATIONS ───────────────────────────────────────────────────



    public static ArrayList<Trainer> loadTrainers() {
        ArrayList<Trainer> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();

            // ResultSet - from JDBC PDF
            ResultSet rs = stmt.executeQuery("SELECT * FROM trainers ORDER BY id");
            while (rs.next()) {
                list.add(new Trainer(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt   ("age"),
                        rs.getString("phone"),
                        rs.getString("specialty")
                ));
            }
        } catch (SQLException | GymException e) {
            System.out.println("Error loading trainers: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
        }
        return list;
    }

    // ─── CUSTOMER OPERATIONS ──────────────────────────────────────────────────

    public static void saveCustomer(Customer c) {
        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO customers (id, name, age, phone, membership_type, price) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, c.getId());
            ps.setString(2, c.getName());
            ps.setInt   (3, c.getAge());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getMembershipType());
            ps.setDouble(6, c.getPrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saving customer: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
        }
    }

    public static ArrayList<Customer> loadCustomers() {
        ArrayList<Customer> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs   = stmt.executeQuery("SELECT * FROM customers ORDER BY id");

            while (rs.next()) {
                list.add(new Customer(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt   ("age"),
                        rs.getString("phone"),
                        rs.getString("membership_type"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException | GymException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
        }
        return list;
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────

    public static Person findById(String id) {
        Connection conn = null;
        try {
            conn = getConnection();

            if (id.startsWith("T")) {
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT * FROM trainers WHERE id = ?"
                );
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Trainer(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getInt   ("age"),
                            rs.getString("phone"),
                            rs.getString("specialty")
                    );
                }

            } else if (id.startsWith("C")) {
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT * FROM customers WHERE id = ?"
                );
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Customer(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getInt   ("age"),
                            rs.getString("phone"),
                            rs.getString("membership_type"),
                            rs.getDouble("price")
                    );
                }
            }

        } catch (SQLException | GymException e) {
            System.out.println("Error finding member: " + e.getMessage());
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
        }
        return null;
    }
}
