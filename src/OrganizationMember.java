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