package se.experis.academy.noticeboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.academy.noticeboard.models.Post;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findAllByUser(Integer userId);
}
