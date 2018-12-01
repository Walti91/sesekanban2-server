package sese.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sese.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
