package sese.responses;


public class RoomFreeResponse {

    boolean isFree;

    public RoomFreeResponse(boolean isFree) {
        this.isFree = isFree;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public String toString() {
        return "RoomFreeResponse{" +
                "isFree=" + isFree +
                '}';
    }
}
