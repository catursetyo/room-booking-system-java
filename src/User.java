public abstract class User {
    private String userId;
    private String name;
    private String major;
    private int penaltyPoints;

    public User(String userId, String name, String major) {
        this.userId = userId;
        this.name = name;
        this.major = major;
        this.penaltyPoints = 0;
    }

    public String getUserId() {
        return userId;
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

    public void addPenalty(int points) {
        if (points > 0) {
            penaltyPoints += points;
        }
    }

    public boolean isSuspended() {
        return penaltyPoints >= 6;
    }

    public abstract String getUserType();

    public abstract int getBookingPriority();

    public abstract int getMaxActiveBookings();

    public abstract int getNoShowPenalty();
}