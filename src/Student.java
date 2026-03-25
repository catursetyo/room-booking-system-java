public class Student extends User {

    public Student(String userId, String name, String faculty) {
        super(userId, name, faculty);
    }

    @Override
    public String getUserType() {
        return "Mahasiswa";
    }

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