package sese.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sese.entities.Systemuser;

@Repository
public interface SystemuserRepository extends JpaRepository<Systemuser, Long> {
}
