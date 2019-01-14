package sese.responses;

import sese.entities.Pension;
import sese.entities.RoomReservation;

public class RoomReservationResponse {

    private RoomResponse room;

    private Integer adults;

    private Integer children;

    private Pension pension;

    public RoomReservationResponse(RoomReservation roomReservation) {
        room = new RoomResponse(roomReservation.getRoom());
        adults = roomReservation.getAdults();
        children = roomReservation.getChildren();
        pension = roomReservation.getPension();
    }

    public RoomResponse getRoom() {
        return room;
    }

    public void setRoom(RoomResponse room) {
        this.room = room;
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
