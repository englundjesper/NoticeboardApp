package se.experis.academy.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import se.experis.academy.noticeboard.services.PostService;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
public class View {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() { return "/index.html"; }

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

    @RequestMapping(value = "post/{id}/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, @PathVariable Integer id) {
        return "/edit.html";
    }

    // not working...
    @RequestMapping(value = "post/{id}", method = RequestMethod.GET)
    public String singlePost(@PathVariable Integer id) {
        System.out.println("here");
        return "/post.html";
    }


}
