package sese.requests;

import java.time.OffsetDateTime;
import java.util.List;

public class ReservationRequest {

    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Long customerId;
    private CustomerRequest customer;
    private List<RoomReservationRequest> roomReservations;

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public CustomerRequest getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerRequest customer) {
        this.customer = customer;
    }

    public List<RoomReservationRequest> getRoomReservations() {
        return roomReservations;
    }

    public void setRoomReservations(List<RoomReservationRequest> roomReservations) {
        this.roomReservations = roomReservations;
    }
}
