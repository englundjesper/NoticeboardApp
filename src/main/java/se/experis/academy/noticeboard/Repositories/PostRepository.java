package se.experis.academy.noticeboard.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.academy.noticeboard.Models.Post;

public interface PostRepository extends JpaRepository<Post,Integer> {
}
