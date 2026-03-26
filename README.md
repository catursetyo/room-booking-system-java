# Sistem Booking Ruang Belajar Kelompok (CCWS) di Kampus

- **Nama:** Catur Setyo Ragil
- **NRP:** 5027251066
- **Kelas:** Struktur Data dan OOP (B)

## Deskripsi

Di lingkungan kampus, mahasiswa sering membutuhkan ruang belajar untuk diskusi kelompok, latihan presentasi, rapat organisasi, atau persiapan ujian. Permasalahan yang sering terjadi adalah bentrok jadwal, kapasitas ruangan yang tidak sesuai dengan jumlah peserta, tidak adanya prioritas untuk kebutuhan organisasi, serta adanya pengguna yang sudah booking tetapi tidak datang.

Untuk membantu menyelesaikan masalah tersebut, dibuatlah program **Sistem Booking Ruang Belajar Kelompok** berbasis **Object-Oriented Programming (OOP)** dengan `Java`. Program ini dapat mengelola data pengguna, ruangan, jadwal booking, status booking, prioritas booking, serta penalti untuk pengguna yang melakukan `no-show`.

## Fitur Utama

- Mendukung dua jenis user: **Mahasiswa** dan **Pengurus Organisasi**
- Mendeteksi **bentrok jadwal** booking
- Memvalidasi **kapasitas ruangan**
- Memberikan **prioritas booking** berdasarkan jenis user
- Memberikan **penalty point** untuk user yang tidak hadir setelah melakukan booking
- Membatasi jumlah **booking aktif** per user
- Menampilkan daftar booking dan detail booking

## Struktur Repository

```
room-booking-system-java/
├── README.md
└── assets/
    ├── class-diagram.mmd
    ├── class-diagram.png
    └── output.png
└── src/
    ├── User.java
    ├── Student.java
    ├── OrganizationMember.java
    ├── Room.java
    ├── TimeSlot.java
    ├── BookingStatus.java
    ├── Booking.java
    ├── BookingManager.java
    └── Main.java
```

## Class Diagram

<img src="/assets/class-diagram.png">

## Class yang Diimplementasikan

### 1. `User`
Class abstract yang menjadi parent class untuk semua jenis user. Menyimpan data umum seperti `studentId`, `name`, `major`, dan `penaltyPoints`.

### 2. `Student`
Child class dari `User` untuk mahasiswa biasa. Memiliki prioritas booking lebih rendah dibanding pengurus organisasi.

### 3. `OrganizationMember`
Child class dari `User` untuk pengguna yang juga menjadi pengurus organisasi. Memiliki prioritas booking lebih tinggi.

### 4. `Room`
Merepresentasikan ruangan yang dapat dibooking, lengkap dengan kapasitas dan informasi projector.

### 5. `TimeSlot`
Merepresentasikan slot waktu booking. Digunakan untuk mengecek apakah dua jadwal saling bentrok.

### 6. `BookingStatus`
Enum yang menyimpan status booking agar lebih aman dan konsisten.

### 7. `Booking`
Merepresentasikan satu transaksi booking. Menyimpan user, room, timeslot, jumlah peserta, tujuan, status, dan waktu dibuat.

### 8. `BookingManager`
Class pengelola seluruh booking. Berisi logika validasi booking, check-in, complete, no-show, pencarian booking, dan pencetakan data booking.

### 9. `Main`
Class utama yang menjalankan simulasi program.

## Prinsip OOP yang Diterapkan

### 1. Encapsulation
Data penting seperti `studentId`, `major`, `capacity`, `status`, dan `penaltyPoints` disimpan dalam atribut `private`. Akses ke data dilakukan melalui getter atau method tertentu agar lebih aman.

### 2. Abstraction
Class `User` dibuat sebagai **abstract class** karena hanya merepresentasikan konsep umum user. Implementasi detail diturunkan ke class `Student` dan `OrganizationMember`.

### 3. Inheritance
Program menggunakan pewarisan:
- `Student` mewarisi `User`
- `OrganizationMember` mewarisi `User`

Dengan inheritance, atribut dan method umum tidak perlu ditulis ulang.

### 4. Polymorphism
Method abstract pada `User` seperti:
- `getUserType()`
- `getBookingPriority()`
- `getMaxActiveBookings()`
- `getNoShowPenalty()`

diimplementasikan secara berbeda pada `Student` dan `OrganizationMember`. Ini menunjukkan bahwa method yang sama dapat memiliki perilaku berbeda tergantung objeknya.

## Kode Program Java
Berikut source code program Java yang digunakan pada project ini.

<details>
<summary><strong>User.java</strong></summary>

```java
public abstract class User {
    private String studentId;
    private String name;
    private String major;
    private int penaltyPoints;

    // constructor
    public User(String studentId, String name, String major) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.penaltyPoints = 0;
    }

    // getter untuk ID, name, major, dan penaltyPoints
    public String getStudentId() {
        return studentId;
    }
    public String getName() {
        return name;
    }
    public String getMajor() {
        return major;
    }
    public int getPenaltyPoints() {
        return penaltyPoints;
    }

    // menambah penaltyPoints
    public void addPenalty(int points) {
        if (points > 0) {
            penaltyPoints += points;
        }
    }

    // buat ngecek apakah user terkena suspend
    public boolean isSuspended() {
        return penaltyPoints >= 6;
    }

    // abstract method untuk class turunan Student() dan OrganizationMember()
    public abstract String getUserType();
    public abstract int getBookingPriority();
    public abstract int getMaxActiveBookings();
    // kondisi penalty untuk menentukan berapa poin penalty yang akan diterima user ketika tidak datang saat booking sudah terkonfirmasi
    public abstract int getNoShowPenalty();
}
```
</details>

<details>
<summary><strong>Student.java</strong></summary>

```java
public class Student extends User {
    public Student(String studentId, String name, String major) {
        super(studentId, name, major);
    }

    // implementasi abstract methods dari parent class User()
    // set tipe user, yaitu Mahasiswa biasa
    @Override
    public String getUserType() {
        return "Mahasiswa";
    }
    // set poin prioritas, poin penalty ketika tidak datang, dan maksimal booking aktif
    @Override
    public int getBookingPriority() {
        return 1;
    }
    @Override
    public int getMaxActiveBookings() {
        return 2;
    }
    @Override
    public int getNoShowPenalty() {
        return 2;
    }
}
```
</details>

<details>
<summary><strong>OrganizationMember.java</strong></summary>

```java
public class OrganizationMember extends User {
    private String organizationName;

    // constructor
    public OrganizationMember(String studentId, String name, String major, String organizationName) {
        super(studentId, name, major);
        this.organizationName = organizationName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    // aplikasi abstract methods
    @Override
    public String getUserType() {
        return "Pengurus Organisasi - " + organizationName;
    }
    @Override
    public int getBookingPriority() {
        return 2;
    }
    @Override
    public int getMaxActiveBookings() {
        return 3;
    }
    @Override
    public int getNoShowPenalty() {
        return 3;
    }
}
```
</details>

<details>
<summary><strong>Room.java</strong></summary>

```java
public class Room {
    private String roomId;
    private String roomName;
    private int capacity;
    private boolean hasProjector;

    // constructor
    public Room(String roomId, String roomName, int capacity, boolean hasProjector) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
    }

    // getter
    public String getRoomId() {
        return roomId;
    }
    public String getRoomName() {
        return roomName;
    }
    public int getCapacity() {
        return capacity;
    }
    public boolean hasProjector() {
        return hasProjector;
    }

    // override dari default parent class Object
    @Override
    public String toString() {
        return roomName + " (kapasitas: " + capacity + ", projector: " + (hasProjector ? "Ya" : "Tidak") + ")";
    }
}
```
</details>

<details>
<summary><strong>TimeSlot.java</strong></summary>

```java
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
```
</details>

<details>
<summary><strong>BookingStatus.java</strong></summary>

```java
// enum untuk menyimpan status booking, agar status tidak rawan typo dibanding pakai string biasa
public enum BookingStatus {
    PENDING,
    CONFIRMED,
    CHECKED_IN,
    CANCELLED,
    NO_SHOW,
    COMPLETED
}
```
</details>

<details>
<summary><strong>Booking.java</strong></summary>

```java
// dependency
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

    // constructor
    public Booking(String bookingId, User user, Room room, TimeSlot timeSlot, int participantCount, String purpose) {
        this.bookingId = bookingId;
        this.user = user;
        this.room = room;
        this.timeSlot = timeSlot;
        this.participantCount = participantCount;
        this.purpose = purpose;
        this.status = BookingStatus.PENDING; // saat booking dibuat, status otomatis PENDING
        this.createdAt = LocalDateTime.now();
    }

    // getter
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

    // update status booking
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
            user.addPenalty(user.getNoShowPenalty()); // penaltyPoints bertambah sesuai aturan
        }
    }
    public boolean isActive() {
        return status == BookingStatus.PENDING ||
                status == BookingStatus.CONFIRMED ||
                status == BookingStatus.CHECKED_IN;
    }

    // detail booking
    public void printDetail() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println("=============== DETAIL BOOKING ===============");
        System.out.println("ID Booking        : " + bookingId);
        System.out.println("Nama User         : " + user.getName());
        System.out.println("Tipe User         : " + user.getUserType());
        System.out.println("Program Studi     : " + user.getMajor());
        System.out.println("Ruangan           : " + room.getRoomName());
        System.out.println("Slot Waktu        : " + timeSlot);
        System.out.println("Jumlah Peserta    : " + participantCount);
        System.out.println("Tujuan            : " + purpose);
        System.out.println("Status            : " + status);
        System.out.println("Priority User     : " + user.getBookingPriority());
        System.out.println("Waktu Dibuat      : " + createdAt.format(dtf));
        System.out.println("==============================================");
    }
}
```
</details>

<details>
<summary><strong>BookingManager.java</strong></summary>

```java
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
```
</details>

<details>
<summary><strong>Main.java</strong></summary>

```java
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
```
</details>

## How To Run
1. Pastikan semua file `.java` berada pada folder yang sama.
2. Buka terminal atau command prompt pada folder project.
3. Compile semua file Java:
   ```bash
   javac *.java
   ```
4. Jalankan program:
   ```bash
   java Main.java
   ```

## Contoh Output
Berikut screenshot output program saat dijalankan:

<img src="/assets/output.png">
