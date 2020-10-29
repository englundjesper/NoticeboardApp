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
import se.experis.academy.noticeboard.utils.Command;
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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;
        Optional<User> optionalUser = userRepository.findByUserName(loginRequest.getUserName());

        if (optionalUser.isPresent()) {
            resp = HttpStatus.CREATED;
            cr.data = optionalUser.get();
            cr.message = "Login by user: " + optionalUser.get().getUserName();
            if (passwordEncoder.matches(loginRequest.getPassword(), optionalUser.get().getPassword())) {
                request.getSession().setAttribute("userId", optionalUser.get().getId());
            }else{
                cr.message = "Wrong password entered";
                resp = HttpStatus.UNAUTHORIZED;
            }
        }else{
            cr.message = "No such user";
            resp = HttpStatus.NOT_FOUND;
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(optionalUser.get(), resp);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout( HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp = HttpStatus.NO_CONTENT;
        if(session!=null){
            session.removeAttribute("userId");
            cr.message = "Deleted user session";
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr,resp);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;
        if (session != null) {
           int  loggedInUserId = 0;
            if(session.getAttribute("userId")!=null){
                loggedInUserId = (int) session.getAttribute("userId");
            }
            Optional<User> optionalUser = userRepository.findById(loggedInUserId);
            if (optionalUser.isPresent()) {
                cr.data = optionalUser.get();
                cr.message = "User logged in ";
                resp = HttpStatus.ACCEPTED;
            } else {
                cr.message = "User not logged in ";
                resp = HttpStatus.NOT_FOUND;
            }
        } else {
            resp = HttpStatus.NOT_FOUND;
            cr.message = "User not logged in";
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
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
