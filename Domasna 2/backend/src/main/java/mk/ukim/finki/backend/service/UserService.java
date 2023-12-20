package mk.ukim.finki.backend.service;

import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User register(String username, String firstName, String lastName, String email, String password, String repeated, Role role);
}
