package sese.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sese.entities.Bill;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

}
