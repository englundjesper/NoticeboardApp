package se.experis.academy.noticeboard.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.experis.academy.noticeboard.models.User;
import se.experis.academy.noticeboard.models.web.LoginRequest;
import se.experis.academy.noticeboard.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request ){

       Optional<User> optionalUser= userRepository.findByUserName(loginRequest.getUserName());
       if(optionalUser.isPresent()){
           User user = optionalUser.get();
           if(user.getPassword().equals(loginRequest.getPassword())){
               System.out.println("Id Login "+request.getSession().getId());
               request.getSession().setAttribute("userId",user.getId());
               return ResponseEntity.ok("Login Successful");
           }
       }
        return ResponseEntity.ok().build();
    }
}
