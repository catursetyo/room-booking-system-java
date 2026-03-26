import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookingManager {
    // menyimpan data booking dalam list
    private List<Booking> bookings;

    public BookingManager() {
        bookings = new ArrayList<>();
    }

    // logic untuk melakukan booking
    public Booking createBooking(String bookingId, User user, Room room, TimeSlot timeSlot,
                                 int participantCount, String purpose) {

        System.out.println("\nMemproses booking " + bookingId + " ...");

        // validasi berdasarkan aturan
        if (participantCount <= 0) {
            System.out.println("Gagal: jumlah peserta harus lebih dari 0.");
            return null;
        }
        if (participantCount > room.getCapacity()) {
            System.out.println("Gagal: jumlah peserta melebihi kapasitas ruangan.");
            return null;
        }
        if (user.isSuspended()) {
            System.out.println("Gagal: user sedang terkena suspend karena penalty terlalu tinggi.");
            return null;
        }
        if (getActiveBookingCount(user) >= user.getMaxActiveBookings()) {
            System.out.println("Gagal: user sudah mencapai batas booking aktif.");
            return null;
        }

        // jika booking lama sudah CHECKED_IN, booking baru ditolak
        // jika user baru punya prioritas lebih tinggi, booking lama dibatalkan
        // jika prioritas sama atau lebih rendah, booking baru ditolak
        Booking conflictBooking = findConflictingBooking(room, timeSlot);

        if (conflictBooking != null) {
            if (conflictBooking.getStatus() == BookingStatus.CHECKED_IN) {
                System.out.println("Gagal: ruangan sedang dipakai dan sudah check-in.");
                return null;
            }
            if (user.getBookingPriority() > conflictBooking.getUser().getBookingPriority()) {
                conflictBooking.cancelBooking();
                System.out.println("Info: booking " + conflictBooking.getBookingId()
                        + " dibatalkan karena ada user dengan prioritas lebih tinggi.");
            } else {
                System.out.println("Gagal: slot ruangan bentrok dengan booking lain yang prioritasnya sama atau lebih tinggi.");
                return null;
            }
        }

        // kalo lolos validasi, booking statusnya diganti menjadi CONFIRMED
        Booking booking = new Booking(bookingId, user, room, timeSlot, participantCount, purpose);
        booking.confirmBooking();
        bookings.add(booking);

        System.out.println("Sukses: booking berhasil dibuat.");
        return booking;
    }

    // cari booking berdasarkan ID lalu panggil checkIn()
    public boolean checkInBooking(String bookingId) {
        Booking booking = findBookingById(bookingId);

        if (booking == null) {
            System.out.println("Check-in gagal: booking tidak ditemukan.");
            return false;
        }
        booking.checkIn();
        System.out.println("Booking " + bookingId + " berhasil check-in.");
        return true;
    }

    // sama aja kayak yang checkIn
    public boolean completeBooking(String bookingId) {
        Booking booking = findBookingById(bookingId);

        if (booking == null) {
            System.out.println("Complete gagal: booking tidak ditemukan.");
            return false;
        }
        booking.complete();
        System.out.println("Booking " + bookingId + " selesai.");
        return true;
    }

    public boolean markNoShowBooking(String bookingId) {
        Booking booking = findBookingById(bookingId);

        if (booking == null) {
            System.out.println("No-show gagal: booking tidak ditemukan.");
            return false;
        }
        booking.markNoShow();
        System.out.println("Booking " + bookingId + " ditandai no-show.");
        return true;
    }

    public Booking findBookingById(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    // tampilin list booking
    public void printAllBookings() {
        List<Booking> sortedBookings = new ArrayList<>(bookings);

        // urutan berdasarkan tanggal, jam mulai, prioritas
        sortedBookings.sort(
            Comparator.comparing((Booking b) -> b.getTimeSlot().getDate())
                      .thenComparing(b -> b.getTimeSlot().getStartTime())
                      .thenComparing((Booking b) -> b.getUser().getBookingPriority(), Comparator.reverseOrder())
        );

        System.out.println("\n=============== DAFTAR SEMUA BOOKING ===============");
        for (Booking booking : sortedBookings) {
            System.out.println(
                booking.getBookingId() + " | " +
                booking.getUser().getName() + " | " +
                booking.getRoom().getRoomName() + " | " +
                booking.getTimeSlot() + " | " +
                booking.getStatus()
            );
        }
        System.out.println("====================================================");
    }

    // hitung berapa banyak booking aktif yang dimiliki user
    private int getActiveBookingCount(User user) {
        int count = 0;
        for (Booking booking : bookings) {
            if (booking.getUser().getStudentId().equals(user.getStudentId()) && booking.isActive()) {
                count++;
            }
        }
        return count;
    }

    // cari apakah ada booking lain pada ruangan yang sama dan slot waktu yang bentrok
    private Booking findConflictingBooking(Room room, TimeSlot timeSlot) {
        for (Booking booking : bookings) {
            boolean sameRoom = booking.getRoom().getRoomId().equals(room.getRoomId());
            boolean conflictTime = booking.getTimeSlot().conflictsWith(timeSlot);

            if (sameRoom && conflictTime && booking.isActive()) {
                return booking;
            }
        }
        return null;
    }
}