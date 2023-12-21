package mk.ukim.finki.backend.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.service.RatingService;
import mk.ukim.finki.backend.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;

    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @PostMapping("/rating/add")
    public String addRating(@RequestParam("rating") String rating, HttpSession session) {
        Long wineryId = (Long) session.getAttribute("wineryId");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = (User) userService.loadUserByUsername(username);

        ratingService.saveRating(rating, wineryId, user);
        return "redirect:/winery/info/" + wineryId;
    }

    @GetMapping("/rating/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        ratingService.delete(id);


        return "redirect:/winery/info/" + (Long) session.getAttribute("wineryId");
    }
}
