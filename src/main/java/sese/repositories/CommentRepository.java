package sese.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sese.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
