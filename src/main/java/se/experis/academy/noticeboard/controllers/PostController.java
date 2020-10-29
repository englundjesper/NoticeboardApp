package se.experis.academy.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.noticeboard.models.CommonResponse;
import se.experis.academy.noticeboard.models.web.PostWeb;
import se.experis.academy.noticeboard.services.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;


    @PostMapping("/create")

    public ResponseEntity<CommonResponse> addPost(HttpServletRequest request, HttpServletResponse response, @RequestBody PostWeb newPost) {
        return postService.createPost(request, response, newPost);
    }

    @GetMapping("/all")
    public ResponseEntity<CommonResponse> getAllPosts(HttpServletRequest request) {
        return postService.getAllPosts(request);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getPostById(HttpServletRequest request, @PathVariable("id") Integer id) {
        return postService.getPostById(request, id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<CommonResponse> updatePost(HttpServletRequest request, @RequestBody PostWeb postWeb, @PathVariable Integer id) {
        return postService.updatePost(request, postWeb, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deletePost(HttpServletRequest request, @PathVariable Integer id) {
        return postService.deletePost(request, id);

    }

    @GetMapping("/{postId}/session")
    public ResponseEntity<CommonResponse> getPostOwnerBySession(HttpServletRequest request, @PathVariable Integer postId) {
        return postService.getUser(request, postId);
    }
}
