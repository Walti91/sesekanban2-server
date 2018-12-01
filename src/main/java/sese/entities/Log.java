package sese.entities;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Log {

    @Id
    @GeneratedValue
    private Long id;
    private OffsetDateTime timestamp;
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Systemuser systemuser;

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

    public Systemuser getSystemuser() {
        return systemuser;
    }

    public void setSystemuser(Systemuser systemuser) {
        this.systemuser = systemuser;
    }
}
