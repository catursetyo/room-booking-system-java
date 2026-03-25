import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();

        Room room1 = new Room("R001", "CCWS 001", 8, true);
        Room room2 = new Room("R002", "CCWS 002", 6, false);

        User user1 = new Student("U001", "Hendra", "Teknologi Informasi");
        User user2 = new Student("U002", "Zaki", "Sistem Informasi");
        User user3 = new OrganizationMember("U003", "Raka", "Teknik Informatika", "BEM-F");
    }
}