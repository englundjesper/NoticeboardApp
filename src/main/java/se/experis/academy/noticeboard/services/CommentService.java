package se.experis.academy.noticeboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import se.experis.academy.noticeboard.models.*;
import se.experis.academy.noticeboard.repositories.*;
import se.experis.academy.noticeboard.models.web.CommentWeb;
import se.experis.academy.noticeboard.utils.Command;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public ResponseEntity<CommonResponse> addComment(HttpServletRequest request, HttpServletResponse response, @RequestBody CommentWeb commentWeb) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        Comment comment = new Comment();
        HttpStatus resp;
        HttpSession session = request.getSession(false);
        if (session != null) {
            int loggedInUserId = (int) session.getAttribute("userId");
            Optional<User> optionalUser = userRepository.findById(loggedInUserId);

            if (optionalUser.isPresent()) {
                Optional<Post> optionalPost = postRepository.findById(commentWeb.getPostId());
                if (optionalPost.isPresent()) {
                    comment.setPost(optionalPost.get());
                    comment.setUser(optionalUser.get());
                    comment.setDescription(commentWeb.getDescription());
                    comment.setCreatedAt(LocalDateTime.now().withNano(0));
                    comment = commentRepository.save(comment);
                    cr.data = comment;
                    resp = HttpStatus.CREATED;
                    response.addHeader("Location", "/comment/" + comment.getId());
                    cr.message = "New comment with id: " + comment.getId();
                } else {
                    cr.message = !optionalUser.isPresent() && optionalPost.isPresent() ? "User not found with id: " + loggedInUserId
                            : optionalUser.isPresent() && !optionalPost.isPresent() ? "Post not found with id: " + commentWeb.getPostId()
                            : "User and post not found with ids: userId: " + loggedInUserId + " postId: " + commentWeb.getPostId();
                    resp = HttpStatus.NOT_FOUND;
                }
            } else {
                cr.message = "Need to be logged in to create comment";
                resp = HttpStatus.METHOD_NOT_ALLOWED;
            }
        } else {
            cr.message = "Need to be logged in to create comment";
            resp = HttpStatus.METHOD_NOT_ALLOWED;
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    public ResponseEntity<CommonResponse> updateComment(HttpServletRequest request, @RequestBody CommentWeb commentWeb, @PathVariable Integer id) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;
        HttpSession session = request.getSession(false);

        if (session != null) {
            int loggedInUserId = (int) session.getAttribute("userId");
            Optional<User> optionalUser = userRepository.findById(loggedInUserId);

            if (optionalUser.isPresent()) {
                if (commentRepository.existsById(id)) {
                    if (commentRepository.findById(id).get().getUser().getId() == loggedInUserId) {
                        Optional<Comment> commentOptional = commentRepository.findById(id);
                        Comment comment = commentOptional.get();

                        if (commentWeb.getDescription() != null) {
                            comment.setDescription(commentWeb.getDescription());
                        }
                        commentRepository.save(comment);
                        cr.data = comment;
                        cr.message = "Updated comment with id: " + comment.getId();
                        resp = HttpStatus.OK;
                    } else {
                        cr.message = "You can only update your own comments";
                        resp = HttpStatus.METHOD_NOT_ALLOWED;
                    }
                } else {
                    cr.message = "Comment not found with id: " + id;
                    resp = HttpStatus.NOT_FOUND;
                }
            } else {
                cr.message = "Need to be logged in to update comment";
                resp = HttpStatus.METHOD_NOT_ALLOWED;
            }
        } else {
            cr.message = "Need to be logged in to update comment";
            resp = HttpStatus.METHOD_NOT_ALLOWED;
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    public ResponseEntity<CommonResponse> deleteComment(HttpServletRequest request, Integer id) {

        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;
        HttpSession session = request.getSession(false);
        if (session != null) {
            int loggedInUserId = (int) session.getAttribute("userId");
            Optional<User> optionalUser = userRepository.findById(loggedInUserId);

            if (optionalUser.isPresent()) {
                if (commentRepository.existsById(id)) {
                    if (commentRepository.findById(id).get().getUser().getId() == loggedInUserId) {
                        commentRepository.deleteById(id);
                        cr.message = "Deleted comment with id: " + id;
                        resp = HttpStatus.OK;
                    } else {
                        cr.message = "You can only delete your own comments";
                        resp = HttpStatus.METHOD_NOT_ALLOWED;
                    }
                } else {
                    cr.message = "Comment not found with id: " + id;
                    resp = HttpStatus.NOT_FOUND;
                }
            } else {
                cr.message = "Need to be logged in to delete comment";
                resp = HttpStatus.METHOD_NOT_ALLOWED;
            }
        } else {
            cr.message = "Need to be logged in to delete comment";
            resp = HttpStatus.METHOD_NOT_ALLOWED;
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }
}
