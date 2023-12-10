package mk.ukim.finki.backend.service;

import mk.ukim.finki.backend.model.User;

import java.util.Optional;

public interface AuthService {

    Optional<User> login(String username, String password);

    User register(String username, String name, String surname, String password, String repeated);
}
