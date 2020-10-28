package se.experis.academy.noticeboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.academy.noticeboard.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUserName(String userName);

}
