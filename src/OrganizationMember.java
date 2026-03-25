public class OrganizationMember extends User {
    private String organizationName;

    public OrganizationMember(String userId, String name, String faculty, String organizationName) {
        super(userId, name, faculty);
        this.organizationName = organizationName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

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
        return 4;
    }

    @Override
    public int getNoShowPenalty() {
        return 1;
    }
}