public abstract class Person {

    private String id;
    private String name;
    private int age;
    private String phone;
    private String role;

    
    private static int trainerCount  = 0;
    private static int customerCount = 0;

    
    public Person(String id, String name, int age, String phone, String role) throws GymException {
        validate(name, age, phone);
        this.id    = id;
        this.name  = name;
        this.age   = age;
        this.phone = phone;
        this.role  = role;
    }

    
    private void validate(String name, int age, String phone) throws GymException {
        if (name == null || name.trim().isEmpty()) {
            throw new GymException("Name cannot be empty.");
        }
        if (age < 16 || age > 80) {
            throw new GymException("Age must be between 16 and 80.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new GymException("Phone cannot be empty.");
        }
    }

    
    public static String generateTrainerId() {
        trainerCount++;
        return String.format("T%03d", trainerCount);
    }

    public static String generateCustomerId() {
        customerCount++;
        return String.format("C%03d", customerCount);
    }

    public static void setTrainerCount(int count)  { trainerCount  = count; }
    public static void setCustomerCount(int count) { customerCount = count; }

    // Getters
    public String getId()    { return id;    }
    public String getName()  { return name;  }
    public int    getAge()   { return age;   }
    public String getPhone() { return phone; }
    public String getRole()  { return role;  }

    
    public abstract String getDetails();

    @Override
    public String toString() {
        return "Role:  " + role  + "\n" +
               "ID:    " + id    + "\n" +
               "Name:  " + name  + "\n" +
               "Age:   " + age   + "\n" +
               "Phone: " + phone;
    }
}
