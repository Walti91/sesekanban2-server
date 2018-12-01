package sese.responses;

import sese.entities.Payment;

import java.time.OffsetDateTime;

public class PaymentResponse {

    private Long id;

    private double value;
    private OffsetDateTime timestamp;
    private boolean isEmailSent;

    public PaymentResponse(Payment payment) {
        this.id = payment.getId();
        this.value = payment.getValue();
        this.timestamp = payment.getTimestamp();
        this.isEmailSent = payment.isEmailSent();
    }

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
}
