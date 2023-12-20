package mk.ukim.finki.backend.service.impl;

import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.repository.UserRepository;
import mk.ukim.finki.backend.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

//    @Override
//    public User register(String firstName, String lastName, String email, String password, String repeated) {
//        if (!password.equals(repeated)) {
//            return null;
//        }
//
//        User user = new User(firstName, lastName, email, password);
//        return userRepository.save(user);
//    }
}
