package se.experis.academy.noticeboard.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.academy.noticeboard.Models.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
