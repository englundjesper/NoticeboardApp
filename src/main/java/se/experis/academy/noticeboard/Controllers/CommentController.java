package se.experis.academy.noticeboard.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.noticeboard.Models.Comment;
import se.experis.academy.noticeboard.Models.CommonResponse;
import se.experis.academy.noticeboard.Repositories.CommentRepository;
import se.experis.academy.noticeboard.Utils.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentRepository repository;

    @PostMapping("/")
    public ResponseEntity<CommonResponse> addComment(HttpServletRequest request, HttpServletResponse response, @RequestBody Comment comment) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();

        comment = repository.save(comment);
        cr.data = comment;
        cr.message = "New comment with id: " + comment.getId();

        HttpStatus resp = HttpStatus.CREATED;
        response.addHeader("Location", "/comment/" + comment.getId());

        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateComment(HttpServletRequest request, @RequestBody Comment newComment, @PathVariable Integer id) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if (repository.existsById(id)) {
            Optional<Comment> actorRepo = repository.findById(id);
            Comment comment = actorRepo.get();

            if (newComment.getDescription() != null) {
                comment.setDescription(newComment.getDescription());
            }

            repository.save(comment);

            cr.data = comment;
            cr.message = "Updated comment with id: " + comment.getId();
            resp = HttpStatus.OK;
        } else {
            cr.message = "Comment not found with id: " + id;
            resp = HttpStatus.NOT_FOUND;
        }
        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteComment(HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);
        CommonResponse cr = new CommonResponse();
        HttpStatus resp;

        if(repository.existsById(id)) {

            repository.deleteById(id);
            cr.message = "Deleted comment with id: " + id;
            resp = HttpStatus.OK;
        } else {
            cr.message = "Comment not found with id: " + id;
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        return new ResponseEntity<>(cr, resp);
    }

}
