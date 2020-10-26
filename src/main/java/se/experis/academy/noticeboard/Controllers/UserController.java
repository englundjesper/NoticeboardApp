package se.experis.academy.noticeboard.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.experis.academy.noticeboard.Repositories.UserRepository;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/")
    String login(){
        return "login to Notice Board";
    }
}
