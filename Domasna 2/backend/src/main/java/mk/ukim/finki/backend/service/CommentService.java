package mk.ukim.finki.backend.service;

import mk.ukim.finki.backend.model.User;

public interface CommentService {
    void save(String comment, Long wineryId, User user);

    void delete(Long id, Long wineryId);
}
