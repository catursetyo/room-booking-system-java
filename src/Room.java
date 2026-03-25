public class Room {
    private String roomId;
    private String roomName;
    private int capacity;
    private boolean hasProjector;

    public Room(String roomId, String roomName, int capacity, boolean hasProjector) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
    }

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

    @Override
    public String toString() {
        return roomName + " (kapasitas: " + capacity + ", projector: " + (hasProjector ? "Ya" : "Tidak") + ")";
    }
}