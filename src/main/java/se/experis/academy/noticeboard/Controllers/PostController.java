package se.experis.academy.noticeboard.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.noticeboard.Models.CommonResponse;
import se.experis.academy.noticeboard.Models.Post;
import se.experis.academy.noticeboard.Repositories.PostRepository;
import se.experis.academy.noticeboard.Utils.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/post")
public class PostController {

    @Autowired
    private PostRepository repository;

    @PostMapping("/create")

    public ResponseEntity<CommonResponse> addPost(HttpServletRequest request, HttpServletResponse response, @RequestBody Post post) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();

        post = repository.save(post);
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

        cr.data = repository.findAll();
        if(repository.count()>0)
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

        if (repository.existsById(id)) {
            cr.data = repository.findById(id);
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

    @PatchMapping("/actor/{id}")
    public ResponseEntity<CommonResponse> updatePost(HttpServletRequest request, @RequestBody Post newPost, @PathVariable Integer id) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (repository.existsById(id)) {
            Optional<Post> postRepo = repository.findById(id);
            Post post = postRepo.get();

            if (newPost.getDescription() != null) {
                post.setDescription(newPost.getDescription());
            }
            if (newPost.getTitle() != null) {
                post.setTitle(newPost.getTitle());
            }
            if (newPost.getCreatedAt() != null) {
                post.setCreatedAt(newPost.getCreatedAt());
            }

            repository.save(post);

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
    public ResponseEntity<CommonResponse> deletePost(HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if(repository.existsById(id)) {

            repository.deleteById(id);
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
