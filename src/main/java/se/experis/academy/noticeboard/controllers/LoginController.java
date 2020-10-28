package se.experis.academy.noticeboard.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.noticeboard.models.User;
import se.experis.academy.noticeboard.models.web.LoginRequest;
import se.experis.academy.noticeboard.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request ){

       Optional<User> optionalUser= userRepository.findByUserName(loginRequest.getUserName());
       if(optionalUser.isPresent()){
           User user = optionalUser.get();

           if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
               System.out.println("Id Login "+request.getSession().getId());
               request.getSession().setAttribute("userId",user.getId());
               return new ResponseEntity<>(user,HttpStatus.OK);
           }
       }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public Boolean getStatus(HttpServletRequest request ){
        HttpSession session = request.getSession(false);

       return session !=null;
    }
}
