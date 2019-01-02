package sese.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sese.entities.Bill;
import sese.entities.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

}
