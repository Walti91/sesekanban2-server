package sese.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sese.entities.Reminder;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}
