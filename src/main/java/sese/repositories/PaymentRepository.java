package sese.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sese.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
