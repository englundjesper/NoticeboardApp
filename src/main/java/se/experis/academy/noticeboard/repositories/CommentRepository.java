package se.experis.academy.noticeboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.academy.noticeboard.models.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
