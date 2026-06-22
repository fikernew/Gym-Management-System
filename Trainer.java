// Inheritance - from Inheritance PDF
public class Trainer extends Person {

    // final field - from Inheritance PDF
    private static final String ROLE = "Trainer";
    private String specialty;

    // New Trainer — ID is auto generated
    public Trainer(String name, int age, String phone, String specialty) throws GymException {
        super(generateTrainerId(), name, age, phone, ROLE);
        this.specialty = specialty;
    }
