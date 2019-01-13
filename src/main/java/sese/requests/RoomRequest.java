package sese.requests;

import java.time.OffsetDateTime;

public class RoomRequest {

    private OffsetDateTime startDate;
    private OffsetDateTime endDate;

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "RoomRequest{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
