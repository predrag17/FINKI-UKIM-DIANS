package mk.ukim.finki.rating.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.rating.service.RatingService;
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
    public String addRating(@RequestParam("rating") String rating) {
        //TODO: get the wineryId and userId
        Long wineryId = null;
        Long userId = null;
        ratingService.saveRating(rating, wineryId, userId);

        //TODO: send response ok
        return "redirect:/winery/info/" + wineryId;
    }

    @GetMapping("/rating/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        ratingService.delete(id);
        return "redirect:/winery/info/" + (Long) session.getAttribute("wineryId");
    }
}
