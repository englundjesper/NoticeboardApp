package se.experis.academy.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.noticeboard.models.CommonResponse;
import se.experis.academy.noticeboard.models.User;
import se.experis.academy.noticeboard.repositories.UserRepository;
import se.experis.academy.noticeboard.utils.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create")

    public ResponseEntity<CommonResponse> createUser(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;
        Optional<User> optionalUser = repository.findByUserName(user.getUserName());
        if (!optionalUser.isPresent()) {
            System.out.println("HEREEE");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user = repository.save(user);
            resp = HttpStatus.CREATED;
            cr.data = user;
            cr.message = "New user with id: " + user.getId();
            response.addHeader("Location", "/user/" + user.getId());
        } else {
            resp = HttpStatus.CONFLICT;
            cr.message = user.getUserName() + " already exists";
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }
}
