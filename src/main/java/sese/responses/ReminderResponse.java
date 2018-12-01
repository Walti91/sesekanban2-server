package sese.responses;

import sese.entities.Reminder;

import java.time.OffsetDateTime;

public class ReminderResponse {

    private Long id;
    private OffsetDateTime timestamp;
    private boolean isEmailSent;

    public ReminderResponse(Reminder reminder){
        this.id = reminder.getId();
        this.timestamp = reminder.getTimestamp();
        this.isEmailSent = reminder.isEmailSent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
