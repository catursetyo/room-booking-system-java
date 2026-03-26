// dependency untuk akses local time and date
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeSlot {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    // mengecek apakah start time < end time
    public TimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time harus lebih kecil dari end time.");
        }
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // getter waktu
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }

    // method untuk mendeteksi bentrok jadwal
    // apakah ada 2 waktu bentrok atau beririsan
    // apabila waktu sama tetapi tanggal berbeda, maka =  tidak bentrok
    public boolean conflictsWith(TimeSlot other) {
        if (!this.date.equals(other.date)) {
            return false;
        }
        return this.startTime.isBefore(other.endTime) && other.startTime.isBefore(this.endTime);
    }

    // format date and time
    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return date.format(dateFormatter) + " | " +
                startTime.format(timeFormatter) + " - " +
                endTime.format(timeFormatter);
    }
}