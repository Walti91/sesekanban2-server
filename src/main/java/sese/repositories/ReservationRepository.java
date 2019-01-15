package sese.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sese.entities.Reservation;

import java.time.OffsetDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerNameContaining(String customerName);
    List<Reservation> findByStartDateBetween(OffsetDateTime first,OffsetDateTime second);
    List<Reservation> findByEndDateBetween(OffsetDateTime first,OffsetDateTime second);
}
