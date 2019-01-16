package sese.entities;

import javax.persistence.*;
import java.sql.Blob;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bill {

    @Id
    @GeneratedValue
    private Long id;
    private boolean isCancelled = false;

    @OneToMany(mappedBy = "bill")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "bill", cascade = CascadeType.PERSIST)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "bill")
    private List<Reminder> reminders = new ArrayList<>();

    @Lob
    private Blob billPdf;

    private boolean paid;

    private double amount;

    private int discount;

    private OffsetDateTime created;

    public Bill() {
        paid = false;
        created = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setBill(this);
    }

    public void addReminder(Reminder reminder) {
        this.reminders.add(reminder);
        reminder.setBill(this);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    public Blob getBillPdf() {
        return billPdf;
    }

    public void setBillPdf(Blob billPdf) {
        this.billPdf = billPdf;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", isCancelled=" + isCancelled +
                ", reservations=" + reservations +
                ", payments=" + payments +
                ", reminders=" + reminders +
                ", content=" + billPdf +
                ", paid=" + paid +
                ", amount=" + amount +
                ", discount= "+ discount +
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
