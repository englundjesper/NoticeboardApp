package se.experis.academy.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.noticeboard.models.CommonResponse;
import se.experis.academy.noticeboard.models.web.LoginRequest;
import se.experis.academy.noticeboard.services.LoginService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return loginService.login(loginRequest, request);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse> logout(HttpServletRequest request) {
        return loginService.logout(request);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        return loginService.getUser(request);
    }

    @GetMapping("/status")
    public Boolean getStatus(HttpServletRequest request) {
        return loginService.getStatus(request);
    }

    @GetMapping("/userId")
    public Integer getUserId(HttpServletRequest request) {
        return loginService.getUserId(request);
    }
}
