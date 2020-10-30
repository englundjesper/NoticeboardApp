package se.experis.academy.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.academy.noticeboard.models.CommonResponse;
import se.experis.academy.noticeboard.models.web.CommentWeb;
import se.experis.academy.noticeboard.services.CommentService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommonResponse> addComment(HttpServletRequest request, HttpServletResponse response, @RequestBody CommentWeb commentWeb) {
       return commentService.addComment(request,response,commentWeb);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse> updateComment(HttpServletRequest request, @RequestBody CommentWeb commentWeb, @PathVariable Integer id) {
        return commentService.updateComment(request, commentWeb, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> deleteComment(HttpServletRequest request ,@PathVariable Integer id) {
        return commentService.deleteComment(request, id);
    }
}
