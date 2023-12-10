package mk.ukim.finki.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model, HttpSession session) {
        Optional<User> user = Optional.empty();

        user = authService.login(request.getParameter("email"), request.getParameter("password"));

        if (user.isEmpty()) {
            model.addAttribute("invalid", true);
            return "login";

        }


        session.setAttribute("user", user.get());
        return "redirect:/home";
    }
}

