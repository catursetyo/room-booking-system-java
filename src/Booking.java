import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {
    private String bookingId;
    private User user;
    private Room room;
    private TimeSlot timeSlot;
    private int participantCount;
    private String purpose;
    private BookingStatus status;
    private LocalDateTime createdAt;

    public Booking(String bookingId, User user, Room room, TimeSlot timeSlot, int participantCount, String purpose) {
        this.bookingId = bookingId;
        this.user = user;
        this.room = room;
        this.timeSlot = timeSlot;
        this.participantCount = participantCount;
        this.purpose = purpose;
        this.status = BookingStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public String getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public String getPurpose() {
        return purpose;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void confirmBooking() {
        if (status == BookingStatus.PENDING) {
            status = BookingStatus.CONFIRMED;
        }
    }

    public void cancelBooking() {
        if (status == BookingStatus.PENDING || status == BookingStatus.CONFIRMED) {
            status = BookingStatus.CANCELLED;
        }
    }

    public void checkIn() {
        if (status == BookingStatus.CONFIRMED) {
            status = BookingStatus.CHECKED_IN;
        }
    }

    public void complete() {
        if (status == BookingStatus.CHECKED_IN) {
            status = BookingStatus.COMPLETED;
        }
    }

    public void markNoShow() {
        if (status == BookingStatus.CONFIRMED) {
            status = BookingStatus.NO_SHOW;
            user.addPenalty(user.getNoShowPenalty());
        }
    }

    public boolean isActive() {
        return status == BookingStatus.PENDING ||
                status == BookingStatus.CONFIRMED ||
                status == BookingStatus.CHECKED_IN;
    }

    public void printDetail() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println("===== DETAIL BOOKING =====");
        System.out.println("ID Booking        : " + bookingId);
        System.out.println("Nama User         : " + user.getName());
        System.out.println("Tipe User         : " + user.getUserType());
        System.out.println("Fakultas          : " + user.getFaculty());
        System.out.println("Ruangan           : " + room.getRoomName());
        System.out.println("Slot Waktu        : " + timeSlot);
        System.out.println("Jumlah Peserta    : " + participantCount);
        System.out.println("Tujuan            : " + purpose);
        System.out.println("Status            : " + status);
        System.out.println("Priority User     : " + user.getBookingPriority());
        System.out.println("Waktu Dibuat      : " + createdAt.format(dtf));
        System.out.println("==========================");
    }
}