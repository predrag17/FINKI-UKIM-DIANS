package mk.ukim.finki.user.service;


import mk.ukim.finki.user.model.User;
import mk.ukim.finki.user.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User register(String username, String firstName, String lastName, String email, String password, String repeated, Role role);
}
