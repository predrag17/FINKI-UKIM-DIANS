package mk.ukim.finki.rating.service;


public interface RatingService {
    void saveRating(String rating, Long wineryId, Long userId);

    void delete(Long id);
}
