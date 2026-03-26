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