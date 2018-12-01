package sese.responses;

import sese.entities.Room;

public class RoomResponse {

    private Long id;

    private String name;

    private Integer capacity;

    private Integer capacityAdults;

    private Double priceAdult;

    private Double priceChild;

    public RoomResponse(Room room) {
        id = room.getId();
        name = room.getName();
        capacity = room.getCapacity();
        capacityAdults = room.getCapacityAdults();
        priceAdult = room.getPriceAdult();
        priceChild = room.getPriceChild();
    }

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
}
