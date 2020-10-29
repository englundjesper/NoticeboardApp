package se.experis.academy.noticeboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.academy.noticeboard.models.Post;
import se.experis.academy.noticeboard.models.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findAllByUser(Integer userId);

}
