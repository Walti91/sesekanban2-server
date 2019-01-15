package sese.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sese.entities.Bill;

import java.time.OffsetDateTime;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByCreatedLessThanEqual(OffsetDateTime date);

}
