package sese.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Systemuser extends Person {

    @Id
    @GeneratedValue
    private Long id;
    private EnumPosition position;

    @OneToMany(mappedBy = "systemuser")
    private List<Log> logs = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumPosition getPosition() {
        return position;
    }

    public void setPosition(EnumPosition position) {
        this.position = position;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }
}
