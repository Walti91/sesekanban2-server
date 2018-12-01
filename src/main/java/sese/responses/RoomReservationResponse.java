package sese.responses;

import sese.entities.RoomReservation;

public class RoomReservationResponse {

    private RoomResponse room;

    private Integer adults;

    private Integer children;

    public RoomReservationResponse(RoomReservation roomReservation) {
        room = new RoomResponse(roomReservation.getRoom());
        adults = roomReservation.getAdults();
        children = roomReservation.getChildren();
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
}
