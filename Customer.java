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
} 
