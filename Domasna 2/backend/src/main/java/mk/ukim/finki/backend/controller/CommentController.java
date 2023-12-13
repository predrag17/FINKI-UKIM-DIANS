package mk.ukim.finki.backend.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.service.CommentService;
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
    public String addComment(@RequestParam("comment") String comment, HttpSession session) {
        Long wineryId = (Long) session.getAttribute("wineryId");
        User user = (User) session.getAttribute("user");

        commentService.save(comment, wineryId, user);
        return "redirect:/winery/info/" + wineryId;
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable Long id, HttpSession session) {
        Long wineryId = (Long) session.getAttribute("wineryId");

        commentService.delete(id, wineryId);
        return "redirect:/winery/info/" + wineryId;
    }
}
