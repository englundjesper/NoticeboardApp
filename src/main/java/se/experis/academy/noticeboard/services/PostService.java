package se.experis.academy.noticeboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.experis.academy.noticeboard.models.CommonResponse;
import se.experis.academy.noticeboard.models.Post;
import se.experis.academy.noticeboard.models.User;
import se.experis.academy.noticeboard.models.web.PostWeb;
import se.experis.academy.noticeboard.repositories.PostRepository;
import se.experis.academy.noticeboard.repositories.UserRepository;
import se.experis.academy.noticeboard.utils.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<CommonResponse> createPost(HttpServletRequest request, HttpServletResponse response, PostWeb postWeb) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp = HttpStatus.METHOD_NOT_ALLOWED;
        Post post = new Post();
        HttpSession session = request.getSession(false);
        if (session != null) {
            int loggedInUserId = (int) session.getAttribute("userId");
            Optional<User> optionalUser = userRepository.findById(loggedInUserId);

            if (optionalUser.isPresent()) {
                post.setTitle(postWeb.getTitle());
                post.setDescription(postWeb.getDescription());
                post.setUser(optionalUser.get());
                post.setCreatedAt(LocalDateTime.now().withNano(0));
                postRepository.save(post);
                cr.message = "New post with id: " + post.getId();
                resp = HttpStatus.CREATED;
                response.addHeader("Location", "/post/" + post.getId());
            } else {
                cr.message = "Wrong userId";
            }
        } else {
            cr.message = "Not logged in";
        }
        cr.data = post;
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    public ResponseEntity<CommonResponse> getPostById(HttpServletRequest request, Integer id) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            cr.data = post;
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

    public ResponseEntity<CommonResponse> getAllPosts(HttpServletRequest request) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        List<Post> posts = postRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
        cr.data = posts;

        if (postRepository.count() > 0)
            cr.message = "All posts";
        else {
            cr.message = "No posts found";
        }
        HttpStatus resp = HttpStatus.OK;
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    public ResponseEntity<CommonResponse> updatePost(HttpServletRequest request, PostWeb postWeb, Integer id) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;
        HttpSession session = request.getSession(false);
        if (session != null) {
            int loggedInUserId = (int) session.getAttribute("userId");
            Optional<User> optionalUser = userRepository.findById(loggedInUserId);

            if (optionalUser.isPresent()) {
                if (postRepository.existsById(id)) {
                    if ((postRepository.findById(id).get().getUser().getId()) == loggedInUserId) {
                        Optional<Post> postRepo = postRepository.findById(id);
                        Post post = postRepo.get();

                        if (postWeb.getDescription() != null) {
                            post.setDescription(postWeb.getDescription());
                        }
                        if (postWeb.getTitle() != null) {
                            post.setTitle(postWeb.getTitle());
                        }
                        postRepository.save(post);
                        cr.data = post;
                        cr.message = "Updated post with id: " + post.getId();
                        resp = HttpStatus.OK;
                    } else {
                        cr.message = "You can only update your own posts";
                        resp = HttpStatus.METHOD_NOT_ALLOWED;
                    }

                } else {
                    cr.message = "Post not found with id: " + id;
                    resp = HttpStatus.NOT_FOUND;
                }
            } else {
                cr.message = "Need to be logged in to update post";
                resp = HttpStatus.METHOD_NOT_ALLOWED;
            }
        } else {
            cr.message = "Need to be logged in to update post";
            resp = HttpStatus.METHOD_NOT_ALLOWED;
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    public ResponseEntity<CommonResponse> deletePost(HttpServletRequest request, Integer id) {
        HttpSession session = request.getSession(false);
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (session != null) {
            int loggedInUserId = (int) session.getAttribute("userId");
            Optional<User> optionalUser = userRepository.findById(loggedInUserId);

            if (optionalUser.isPresent()) {
                if (postRepository.existsById(id)) {
                    if ((postRepository.findById(id).get().getUser().getId()) == loggedInUserId) {
                        postRepository.deleteById(id);
                        cr.message = "Deleted post with id: " + id;
                        resp = HttpStatus.OK;
                    } else {
                        cr.message = "You can only delete your own posts";
                        resp = HttpStatus.METHOD_NOT_ALLOWED;
                    }
                } else {
                    cr.message = "Post not found with id: " + id;
                    resp = HttpStatus.NOT_FOUND;
                }
            } else {
                cr.message = "Need to be logged in to delete post";
                resp = HttpStatus.METHOD_NOT_ALLOWED;
            }
        } else {
            cr.message = "Need to be logged in to delete post";
            resp = HttpStatus.METHOD_NOT_ALLOWED;
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    public ResponseEntity<CommonResponse> getUser(HttpServletRequest request, Integer postId) {
        HttpSession session = request.getSession(false);
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (session != null) {
            int loggedInUserId = (int) session.getAttribute("userId");
            Optional<User> optionalUser = userRepository.findById(loggedInUserId);

            if (optionalUser.isPresent()) {
                if (postRepository.existsById(postId)) {
                    if ((postRepository.findById(postId).get().getUser().getId()) == loggedInUserId) {
                        cr.data = optionalUser.get();
                        cr.message = "User with id: " + loggedInUserId +" is owner of post with id: " + postId;
                        resp = HttpStatus.OK;
                    } else {
                        cr.message = "User with id " + loggedInUserId + " not owner of post with id " + postId;
                        resp = HttpStatus.METHOD_NOT_ALLOWED;
                    }
                } else {
                    cr.message = "Post not found with id: " + postId;
                    resp = HttpStatus.NOT_FOUND;
                }
            } else {
                cr.message = "User not logged in";
                resp = HttpStatus.METHOD_NOT_ALLOWED;
            }
        } else {
            cr.message = "User not logged in";
            resp = HttpStatus.METHOD_NOT_ALLOWED;
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }
}
