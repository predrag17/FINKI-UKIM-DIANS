package mk.ukim.finki.user.controller;

import mk.ukim.finki.user.model.User;
import mk.ukim.finki.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/session/user")
    public ResponseEntity<String> sessionUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        User user = (User) userService.loadUserByUsername(username);

        return ResponseEntity.ok(String.valueOf(user.getId()));
    }
}
