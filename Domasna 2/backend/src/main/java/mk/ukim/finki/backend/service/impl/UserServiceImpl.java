package mk.ukim.finki.backend.service.impl;

import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.repository.UserRepository;
import mk.ukim.finki.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
