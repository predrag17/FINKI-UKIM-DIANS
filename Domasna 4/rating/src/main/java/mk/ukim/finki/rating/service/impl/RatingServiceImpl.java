package mk.ukim.finki.rating.service.impl;

import mk.ukim.finki.rating.repository.RatingRepository;
import mk.ukim.finki.rating.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void saveRating(String rating, Long wineryId, Long userId) {
        //TODO: save rating
    }

    @Override
    public void delete(Long id) {
        ratingRepository.deleteById(id);
    }
}
