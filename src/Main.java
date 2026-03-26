import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        // inisiasi bookingManager
        BookingManager manager = new BookingManager();

        // inisiasi room untuk user
        Room room1 = new Room("R001", "CCWS 001", 8, true);
        Room room2 = new Room("R002", "CCWS 002", 6, false);

        // inisiasi user
        User user1 = new Student("U001", "Hendra", "Teknologi Informasi");
        User user2 = new Student("U002", "Zaki", "Sistem Informasi");
        User user3 = new OrganizationMember("U003", "Raka", "Teknik Informatika", "BEM-F");

        // bikin slot waktu
        TimeSlot slot1 = new TimeSlot(
                LocalDate.of(2026, 3, 26),
                LocalTime.of(9, 0),
                LocalTime.of(11, 0)
        );

        TimeSlot slot2 = new TimeSlot(
                LocalDate.of(2026, 3, 26),
                LocalTime.of(10, 0),
                LocalTime.of(12, 0)
        );

        TimeSlot slot3 = new TimeSlot(
                LocalDate.of(2026, 3, 26),
                LocalTime.of(13, 0),
                LocalTime.of(15, 0)
        );

        // membuat booking
        manager.createBooking(
                "B001",
                user1,
                room1,
                slot1,
                6,
                "Diskusi kelompok strukdat"
        );

        manager.createBooking(
                "B002",
                user2,
                room1,
                slot1,
                4,
                "Belajar ETS OOP"
        );

        manager.createBooking(
                "B003",
                user3,
                room1,
                slot2,
                8,
                "Rapat koordinasi acara BEM"
        );

        manager.createBooking(
                "B004",
                user2,
                room2,
                slot3,
                8,
                "Latihan presentasi"
        );

        manager.createBooking(
                "B005",
                user2,
                room2,
                slot3,
                5,
                "Latihan presentasi final"
        );

        // memproses status booking
        manager.checkInBooking("B003");
        manager.completeBooking("B003");
        manager.markNoShowBooking("B005");

        // tampilin semua booking
        manager.printAllBookings();

        // tampilin detail satu booking
        System.out.println();
        Booking booking = manager.findBookingById("B003");
        if (booking != null) {
            booking.printDetail();
        }

        System.out.println();
        System.out.println("=============== RINGKASAN USER ===============");
        System.out.println(user1.getName() + " | " + user1.getUserType() + " | penalty: " + user1.getPenaltyPoints());
        System.out.println(user2.getName() + " | " + user2.getUserType() + " | penalty: " + user2.getPenaltyPoints());
        System.out.println(user3.getName() + " | " + user3.getUserType() + " | penalty: " + user3.getPenaltyPoints());
        System.out.println("==============================================");
    }
}