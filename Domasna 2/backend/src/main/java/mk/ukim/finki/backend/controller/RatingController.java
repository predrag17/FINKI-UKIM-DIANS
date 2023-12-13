package mk.ukim.finki.backend.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.backend.model.Rating;
import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.service.RatingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/rating/add")
    public String addRating(@RequestParam("rating") String rating, HttpSession session) {
        Long wineryId = (Long) session.getAttribute("wineryId");
        User user = (User) session.getAttribute("user");

        ratingService.saveRating(rating, wineryId, user);
        return "redirect:/winery/info/" + wineryId;
    }

    @GetMapping("/rating/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        ratingService.delete(id);


        return "redirect:/winery/info/" + (Long) session.getAttribute("wineryId");
    }
}
