package sese.entities;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private double value;
    private OffsetDateTime timestamp;
    private boolean isEmailSent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Bill bill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isEmailSent() {
        return isEmailSent;
    }

    public void setEmailSent(boolean emailSent) {
        isEmailSent = emailSent;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

}
