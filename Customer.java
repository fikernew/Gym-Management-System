// Inheritance - from Inheritance PDF
public class Customer extends Person {

    // final field - from Inheritance PDF
    private static final String ROLE = "Customer";
    private String membershipType;
    private double price;

    // New Customer — ID is auto generated
    public Customer(String name, int age, String phone, String membershipType, double price) throws GymException {
        super(generateCustomerId(), name, age, phone, ROLE);
        this.membershipType = membershipType;
        this.price          = price;
    }
    // Loading from database — ID is provided
    public Customer(String id, String name, int age, String phone, String membershipType, double price) throws GymException {
        super(id, name, age, phone, ROLE);
        this.membershipType = membershipType;
        this.price          = price;
    }

    public String getMembershipType() { return membershipType; }
    public double getPrice()          { return price;          }

    @Override
    public String getRole() { return ROLE; }

    @Override
    public String getDetails() {
        return toString();
    }

    public String getBenefits() {
        return switch (membershipType) {
            case "Normal"  -> "Basic gym access";
            case "VIP"     -> "Gym access + Personal Trainer";
            case "Premium" -> "Gym access + Personal Trainer + Nutrition Plan";
            default        -> "N/A";
        };
    }

    @Override
    public String toString() {
        return super.toString()                   + "\n" +
               "Membership: " + membershipType    + "\n" +
               "Price:      $" + price + "/month" + "\n" +
               "Benefits:   " + getBenefits();
    }
}
