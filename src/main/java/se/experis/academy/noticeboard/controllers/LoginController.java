package se.experis.academy.noticeboard.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.noticeboard.models.CommonResponse;
import se.experis.academy.noticeboard.models.User;
import se.experis.academy.noticeboard.models.web.LoginRequest;
import se.experis.academy.noticeboard.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        Optional<User> optionalUser = userRepository.findByUserName(loginRequest.getUserName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                request.getSession().setAttribute("userId", user.getId());

                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout( HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        CommonResponse cm = new CommonResponse();

        if(session!=null){


            session.removeAttribute("userId");
            cm.message = "Deleted user session";

        }

        return new ResponseEntity<>(cm,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        CommonResponse cm = new CommonResponse();
        HttpSession session = request.getSession(false);

        if (session != null) {
           int  loggedInUserId = 0;
            if(session.getAttribute("userId")!=null){
                loggedInUserId = (int) session.getAttribute("userId");
            }
            Optional<User> optionalUser = userRepository.findById(loggedInUserId);
            if (optionalUser.isPresent()) {
                cm.data = optionalUser.get();
                cm.message = "User logged in ";
            } else {
                cm.message = "User not logged in ";

            }
        } else {
            cm.message = "User not logged in";
        }

        return new ResponseEntity<>(cm, HttpStatus.OK);
    }

    @GetMapping("/status")
    public Boolean getStatus(HttpServletRequest request) {
        HttpSession session = request.getSession(false);


        return session != null && session.getAttribute("userId")!=null;
    }

    @GetMapping("/userId")
    public Integer getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        int loggedInUserId = -1;

        if (session != null) {
            loggedInUserId = (int) session.getAttribute("userId");

        }

        return loggedInUserId;
    }
}
