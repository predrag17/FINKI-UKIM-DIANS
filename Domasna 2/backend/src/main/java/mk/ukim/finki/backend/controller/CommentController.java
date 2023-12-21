package mk.ukim.finki.backend.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.service.CommentService;
import mk.ukim.finki.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/add-comment")
    public String addComment(@RequestParam("comment") String comment, HttpSession session) {
        Long wineryId = (Long) session.getAttribute("wineryId");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = (User) userService.loadUserByUsername(username);

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
