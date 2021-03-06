package sese.responses;

import sese.entities.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BillResponse {

    private Long id;
    private boolean isCancelled;
    private List<ReservationResponse> reservations;
    private List<PaymentResponse> payments;
    private List<ReminderResponse> reminders;
    private Double amount;
    private int discount;
    private OffsetDateTime created;

    public BillResponse(Bill bill) {
        id = bill.getId();
        isCancelled = bill.isCancelled();
        reservations = bill.getReservations().stream().map(ReservationResponse::new).collect(Collectors.toList());
        payments = bill.getPayments().stream().map(PaymentResponse::new).collect(Collectors.toList());
        reminders = bill.getReminders().stream().map(ReminderResponse::new).collect(Collectors.toList());
        amount = bill.getAmount();
        discount = bill.getDiscount();
        created = bill.getCreated();
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public List<ReservationResponse> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationResponse> reservations) {
        this.reservations = reservations;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PaymentResponse> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResponse> payments) {
        this.payments = payments;
    }

    public List<ReminderResponse> getReminders() {
        return reminders;
    }

    public void setReminders(List<ReminderResponse> reminders) {
        this.reminders = reminders;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "BillResponse{" +
                "id=" + id +
                ", isCancelled=" + isCancelled +
                ", reservations=" + reservations +
                ", payments=" + payments +
                ", reminders=" + reminders +
                ", amount=" + amount +
                ", discount=" + discount +
                ", created=" + created +
                '}';
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
