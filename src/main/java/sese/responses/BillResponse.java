package sese.responses;

import sese.entities.*;

import java.util.List;
import java.util.stream.Collectors;

public class BillResponse {

    private Long id;
    private boolean isCancelled;
    private List<ReservationResponse> reservations;
    private List<PaymentResponse> payments;
    private List<ReminderResponse> reminders;
    private Double amount;

    public BillResponse(Bill bill){
        id = bill.getId();
        isCancelled = bill.isCancelled();
        reservations = bill.getReservations().stream().map(ReservationResponse::new).collect(Collectors.toList());
        payments = bill.getPayments().stream().map(PaymentResponse::new).collect(Collectors.toList());
        reminders = bill.getReminders().stream().map(ReminderResponse::new).collect(Collectors.toList());
        amount = bill.getAmount();
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
}
