package sese.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RoomReservation> reservations;

    private String name;

    private Integer capacity;

    private Integer capacityAdults;

    private Double priceAdult;

    private Double priceChild;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacityAdults() {
        return capacityAdults;
    }

    public void setCapacityAdults(Integer capacityAdults) {
        this.capacityAdults = capacityAdults;
    }

    public Double getPriceAdult() {
        return priceAdult;
    }

    public void setPriceAdult(Double priceAdult) {
        this.priceAdult = priceAdult;
    }

    public Double getPriceChild() {
        return priceChild;
    }

    public void setPriceChild(Double priceChild) {
        this.priceChild = priceChild;
    }

    public List<RoomReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<RoomReservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", capacityAdults=" + capacityAdults +
                ", priceAdult=" + priceAdult +
                ", priceChild=" + priceChild +
                '}';
    }
}
