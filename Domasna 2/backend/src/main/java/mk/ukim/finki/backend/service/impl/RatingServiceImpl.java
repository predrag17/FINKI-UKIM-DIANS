package mk.ukim.finki.backend.service.impl;

import mk.ukim.finki.backend.model.Rating;
import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.model.Winery;
import mk.ukim.finki.backend.repository.RatingRepository;
import mk.ukim.finki.backend.repository.WineryRepository;
import mk.ukim.finki.backend.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final WineryRepository wineryRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, WineryRepository wineryRepository) {
        this.ratingRepository = ratingRepository;
        this.wineryRepository = wineryRepository;
    }

    @Override
    public void saveRating(String rating, Long wineryId, User user) {
        Optional<Winery> winery = wineryRepository.findById(wineryId);

        Rating r = new Rating(rating, user, winery.get());
        ratingRepository.save(r);

        winery.get().setRating(String.valueOf(winery.get().avgRating()));
        wineryRepository.save(winery.get());
    }

    @Override
    public void delete(Long id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        Winery winery = rating.get().getWinery();
        ratingRepository.deleteById(id);

        winery.setRating(String.valueOf(winery.avgRating()));
        wineryRepository.save(winery);
    }
}
