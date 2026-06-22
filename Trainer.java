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
// Loading from database — ID is provided
    public Trainer(String id, String name, int age, String phone, String specialty) throws GymException {
        super(id, name, age, phone, ROLE);
        this.specialty = specialty;
    }

    public String getSpecialty() { return specialty; }

    @Override
    public String getRole() { return ROLE; }

    @Override
    public String getDetails() {
        return toString();
    }

    @Override
    public String toString() {
        return super.toString()        + "\n" +
               "Specialty: " + specialty;
    }
}


