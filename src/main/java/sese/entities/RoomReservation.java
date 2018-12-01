package sese.entities;

import javax.persistence.*;

@Entity
public class RoomReservation {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Reservation reservation;

    @ManyToOne(cascade = CascadeType.ALL)
    private Room room;

    private Integer adults;

    private Integer children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
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

    @Override
    public String toString() {
        return "RoomReservation{" +
                "id=" + id +
                ", room=" + room +
                ", adults=" + adults +
                ", children=" + children +
                '}';
    }
}
