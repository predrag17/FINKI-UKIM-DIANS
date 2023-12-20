package mk.ukim.finki.backend.service;

import mk.ukim.finki.backend.model.User;

import java.util.Optional;

public interface AuthService {

    Optional<User> login(String username, String password);


}
