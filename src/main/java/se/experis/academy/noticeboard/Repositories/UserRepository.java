package se.experis.academy.noticeboard.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.academy.noticeboard.Models.User;

public interface UserRepository extends JpaRepository<User,Integer> {

}
