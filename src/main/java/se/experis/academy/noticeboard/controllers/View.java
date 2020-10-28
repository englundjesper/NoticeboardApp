package se.experis.academy.noticeboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class View {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {

        return "/index.html";
    }

    @RequestMapping(value = "post", method = RequestMethod.GET)
    public String getPost() {
        return "/post.html";
    }

    @RequestMapping(value = "addpost", method = RequestMethod.GET)
    public String addPost() {
        return "/addpost.html";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "/login.html";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register() {
        return "/register.html";
    }

    // not working...
    @RequestMapping(value = "post/{id}", method = RequestMethod.GET)
    public String singlePost(@PathVariable Integer id) {
        System.out.println("here");
        return "/post.html";
    }


}
