package se.experis.academy.noticeboard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.academy.noticeboard.models.User;

public interface UserRepository extends JpaRepository<User,Integer> {

}
