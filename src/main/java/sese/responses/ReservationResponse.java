package sese.responses;

import sese.entities.Comment;
import sese.entities.Reservation;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationResponse {

    private Long id;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private CustomerResponse customer;
    private Set<RoomReservationResponse> roomReservations;
    private List<String> comments;

    public ReservationResponse(Reservation reservation) {
        id = reservation.getId();
        startDate = reservation.getStartDate();
        endDate = reservation.getEndDate();
        customer = new CustomerResponse(reservation.getCustomer());
        roomReservations = reservation.getRoomReservations().stream().map(RoomReservationResponse::new).collect(Collectors.toSet());
        comments = reservation.getComments().stream().map(Comment::getText).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public Set<RoomReservationResponse> getRoomReservations() {
        return roomReservations;
    }

    public void setRoomReservations(Set<RoomReservationResponse> roomReservations) {
        this.roomReservations = roomReservations;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ReservationResponse{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", customer=" + customer +
                ", roomReservations=" + roomReservations +
                ", comments=" + comments +
                '}';
    }
}
