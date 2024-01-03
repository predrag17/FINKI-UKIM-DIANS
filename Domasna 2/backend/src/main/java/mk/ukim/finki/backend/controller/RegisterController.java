package mk.ukim.finki.backend.controller;

import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.model.enumerations.Role;
import mk.ukim.finki.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage() {
        return "register";
    }


    @PostMapping
    public String register(
            @RequestParam("username") String username,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("repeatedPw") String repeated, Model model) {


        User user = userService.register(username, firstName, lastName, email, password, repeated, Role.valueOf("ROLE_USER"));


        if (user == null) {
            model.addAttribute("invalid", true);
            return "register";
        }

        if (user.getUsername() == null) {
            model.addAttribute("alreadyExist", true);
            return "register";
        }

        return "redirect:/login";


    }
}