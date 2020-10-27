package se.experis.academy.noticeboard.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.noticeboard.models.Comment;
import se.experis.academy.noticeboard.models.CommonResponse;
import se.experis.academy.noticeboard.models.Post;
import se.experis.academy.noticeboard.models.User;
import se.experis.academy.noticeboard.models.web.PostWeb;
import se.experis.academy.noticeboard.repositories.PostRepository;
import se.experis.academy.noticeboard.repositories.UserRepository;
import se.experis.academy.noticeboard.utils.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/api/v1/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")

    public ResponseEntity<CommonResponse> addPost(HttpServletRequest request, HttpServletResponse response, @RequestBody PostWeb newPost) {

        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();

        Post post = new Post();
        post.setTitle(newPost.getTitle());
        post.setDescription(newPost.getDescription());
        Optional<User> userOptional = userRepository.findById(newPost.getUserId());
        if (userOptional.isPresent())
            
            post.setUser(userOptional.get());
        post.setUser(userRepository.getOne(newPost.getUserId()));

        System.out.println(newPost.getUserId());
        post.setCreatedAt(LocalDateTime.now());

        String firstName = userOptional.get().getFirstName();
        String lastName = userOptional.get().getLastName();
        int id = newPost.getUserId();

        postRepository.save(post);

        cr.data = post;
        cr.message = "New post with id: " + post.getId();

        HttpStatus resp = HttpStatus.CREATED;
        response.addHeader("Location", "/post/" + post.getId());

        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    @GetMapping("/all")
    public ResponseEntity<CommonResponse> getAllPosts(HttpServletRequest request) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();

        cr.data = postRepository.findAll();
        List<Post> posts = postRepository.findAll();
        posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());

        if(postRepository.count()>0)
            cr.message = "All posts";
        else{
            cr.message = "No posts found";
        }

        HttpStatus resp = HttpStatus.OK;

        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getPostById(HttpServletRequest request, @PathVariable("id") Integer id) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();

        HttpStatus resp;

        if (postRepository.existsById(id)) {
            cr.data = postRepository.findById(id);
            cr.message = "Post with id: " + id;
            resp = HttpStatus.OK;
        } else {
            cr.data = null;
            cr.message = "Post not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<CommonResponse> updatePost(HttpServletRequest request, @RequestBody PostWeb newPost, @PathVariable Integer id) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (postRepository.existsById(id)) {
            Optional<Post> postRepo = postRepository.findById(id);
            Post post = postRepo.get();

            if (newPost.getDescription() != null) {
                post.setDescription(newPost.getDescription());
            }
            if (newPost.getTitle() != null) {
                post.setTitle(newPost.getTitle());
            }

            postRepository.save(post);

            cr.data = post;
            cr.message = "Updated post with id: " + post.getId();
            resp = HttpStatus.OK;
        } else {
            cr.message = "Post not found with id: " + id;
            resp = HttpStatus.NOT_FOUND;
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deletePost(HttpServletRequest request, @RequestBody PostWeb newPost, @PathVariable Integer id) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;
        // check session

        if(postRepository.existsById(id) && (postRepository.findById(id).get().getUser().getId()) == newPost.getUserId()) {

            postRepository.deleteById(id);
            cr.message = "Deleted post with id: " + id;
            resp = HttpStatus.OK;
        } else {
            cr.message = "Post not found with id: " + id;
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }
}
