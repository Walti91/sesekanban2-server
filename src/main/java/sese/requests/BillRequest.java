package sese.requests;

import java.util.ArrayList;
import java.util.List;

public class BillRequest {
    private Long id;

    private List<Long> reservationIds = new ArrayList<>();

    @Override
    public String toString() {
        return "BillRequest{" +
                "id=" + id +
                ", reservationIds=" + reservationIds +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getReservationIds() {
        return reservationIds;
    }

    public void setReservationIds(List<Long> reservationIds) {
        this.reservationIds = reservationIds;
    }
}
