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