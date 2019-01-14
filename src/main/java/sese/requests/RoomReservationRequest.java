package sese.requests;

import sese.entities.Pension;

public class RoomReservationRequest {

    private Long roomId;
    private Integer adults;
    private Integer children;
    private Pension pension;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public Pension getPension() {
        return pension;
    }

    public void setPension(Pension pension) {
        this.pension = pension;
    }
}
