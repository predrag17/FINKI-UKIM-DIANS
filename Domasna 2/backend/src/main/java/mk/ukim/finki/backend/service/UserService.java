package mk.ukim.finki.backend.service;

import mk.ukim.finki.backend.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
}
