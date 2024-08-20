import java.util.*;

class Person {
    String name;
    int age;
    String loginID;
    String trainName;
    int bogieNumber;
    int seatNumber;

    public Person(String name, int age, String loginID) {
        this.name = name;
        this.age = age;
        this.loginID = loginID;
        this.trainName = "";
        this.bogieNumber = -1;
        this.seatNumber = -1;
    }
}

class Train {
    String name;
    int[][] seats;

    public Train(String name) {
        this.name = name;
        this.seats = new int[3][2];
    }

    public boolean allocateSeat(Person person) {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 0) {
                    seats[i][j] = 1;
                    person.trainName = this.name;
                    person.bogieNumber = i + 1;
                    person.seatNumber = j + 1;
                    return true;
                }
            }
        }
        return false;
    }

    public int getTotalSeats() {
        return seats.length * seats[0].length;
    }

    public int getBookedSeats() {
        int bookedSeats = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 1) {
                    bookedSeats++;
                }
            }
        }
        return bookedSeats;
    }

    public int getRemainingSeats() {
        return getTotalSeats() - getBookedSeats();
    }
}

public class TrainAllocationSystem {
    private static final Map<String, Person> persons = new HashMap<>();
    private static final Map<String, Train> trains = new HashMap<>();

    public static void main(String[] args) {
        trains.put("Train 1", new Train("Train 1"));
        trains.put("Train 2", new Train("Train 2"));
        trains.put("Train 3", new Train("Train 3"));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your choice:");
            System.out.println("1. Enter Profile");
            System.out.println("2. Enter Train Choice");
            System.out.println("3. Display Person Information");
            System.out.println("4. Display Complete Information");
            System.out.println("5. Display Train Status");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    enterProfile(scanner);
                    break;
                case 2:
                    enterTrainChoice(scanner);
                    break;
                case 3:
                    displayPersonInformation(scanner);
                    break;
                case 4:
                    displayCompleteInformation();
                    break;
                case 5:
                    displayTrainStatus();
                    break;
                case 6:
                    System.out.println("Please Visit Again.......");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void enterProfile(Scanner scanner) {
        System.out.println("Enter your name:");
        String name = scanner.nextLine();

        System.out.println("Enter your age:");
        int age = scanner.nextInt();
        scanner.nextLine();

        String loginID = UUID.randomUUID().toString();
        persons.put(loginID, new Person(name, age, loginID));

        System.out.println("Profile registered successfully.....");
        System.out.println("Your Login ID is: " + loginID);
    }

    private static void enterTrainChoice(Scanner scanner) {
        System.out.println("Enter your Login ID:");
        String loginID = scanner.nextLine();

        Person person = persons.get(loginID);

        if (person == null) {
            System.out.println("Invalid Login ID. Please try again.....");
            return;
        }

        if (!person.trainName.isEmpty()) {
            System.out.println("You have already chosen a train.");
            return;
        }

        System.out.println("Available trains:");
        System.out.println("1. Train 1");
        System.out.println("2. Train 2");
        System.out.println("3. Train 3");

        int trainChoice = scanner.nextInt();
        scanner.nextLine();

        String trainName = "Train " + trainChoice;
        Train train = trains.get(trainName);

        if (train == null) {
            System.out.println("Train is not available. Please try again.....");
            return;
        }

        if (train.allocateSeat(person)) {
            System.out.println("Seat allocated successfully in " + trainName);
        } else {
            System.out.println("No available seats in " + trainName);
        }
    }

    private static void displayPersonInformation(Scanner scanner) {
        System.out.println("Enter your Login ID:");
        String loginID = scanner.nextLine();

        Person person = persons.get(loginID);

        if (person == null) {
            System.out.println("Invalid Login ID. Please try again.");
            return;
        }

        System.out.println("Name: " + person.name);
        System.out.println("Age: " + person.age);
        System.out.println("Train: " + (person.trainName.isEmpty() ? "Not assigned" : person.trainName));
        if (!person.trainName.isEmpty()) {
            System.out.println("Bogie Number: " + person.bogieNumber);
            System.out.println("Seat Number: " + person.seatNumber);
        }
    }

    private static void displayCompleteInformation() {
        if (persons.isEmpty()) {
            System.out.println("No registered users.");
            return;
        }

        System.out.println("Complete Information:");
        for (Person person : persons.values()) {
            System.out.println("Login ID: " + person.loginID);
            System.out.println("Name: " + person.name);
            System.out.println("Age: " + person.age);
            System.out.println("Train: " + (person.trainName.isEmpty() ? "Not assigned" : person.trainName));
            if (!person.trainName.isEmpty()) {
                System.out.println("Bogie Number: " + person.bogieNumber);
                System.out.println("Seat Number: " + person.seatNumber);
            }
            System.out.println();
        }
    }

    private static void displayTrainStatus() {
        System.out.println("Train Status:");

        for (Train train : trains.values()) {
            System.out.println(train.name + ":");
            System.out.println("Total Seats: " + train.getTotalSeats());
            System.out.println("Booked Seats: " + train.getBookedSeats());
            System.out.println("Remaining Seats: " + train.getRemainingSeats());
            System.out.println();
        }
    }
}
