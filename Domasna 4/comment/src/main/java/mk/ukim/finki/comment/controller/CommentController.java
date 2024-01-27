package mk.ukim.finki.comment.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.comment.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add-comment")
    public String addComment(@RequestParam("comment") String comment) {
        //TODO: get the wineryId and userId
        Long wineryId = null;
        Long userId = null;
        commentService.save(comment, wineryId, userId);
        //TODO: return response ok
        return "redirect:/winery/info/" + wineryId;
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.delete(id);
        Long wineryId = null;
        //TODO: return response ok
        return "redirect:/winery/info/" + wineryId;
    }
}
