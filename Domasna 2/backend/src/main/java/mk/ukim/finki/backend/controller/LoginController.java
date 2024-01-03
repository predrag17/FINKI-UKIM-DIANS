package mk.ukim.finki.backend.controller;

import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(@RequestParam("username") String username, Model model, HttpSession session) {
        UserDetails user = null;

        user = userService.loadUserByUsername(username);

        if (user == null) {
            model.addAttribute("invalid", true);
            return "login";

        }

        User user1 = (User) user;

        session.setAttribute("user", user1);
        return "redirect:/home";
    }
}

