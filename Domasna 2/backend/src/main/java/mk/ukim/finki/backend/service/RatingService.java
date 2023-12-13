package mk.ukim.finki.backend.service;

import mk.ukim.finki.backend.model.User;

public interface RatingService {
    void saveRating(String rating, Long wineryId, User user);

    void delete(Long id);
}
