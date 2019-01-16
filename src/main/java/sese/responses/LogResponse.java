package sese.responses;

import sese.entities.Log;
import sese.entities.Systemuser;

import java.time.OffsetDateTime;

public class LogResponse {

    private Long id;
    private OffsetDateTime timestamp;
    private String text;
    private SystemuserResponse systemuser;

    public LogResponse(Log log){
        this.id = log.getId();
        this.timestamp = log.getTimestamp();
        this.text = log.getText();
        this.systemuser = new SystemuserResponse(log.getSystemuser());
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SystemuserResponse getSystemuser() {
        return systemuser;
    }

    public void setSystemuser(SystemuserResponse systemuser) {
        this.systemuser = systemuser;
    }

    @Override
    public String toString() {
        return "LogResponse{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", text='" + text + '\'' +
                ", systemuser=" + systemuser +
                '}';
    }
}
