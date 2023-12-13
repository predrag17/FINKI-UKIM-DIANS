package mk.ukim.finki.backend.repository;

import mk.ukim.finki.backend.model.Winery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WineryRepository extends JpaRepository<Winery, Long> {
    List<Winery> findByReviews(String filter);

    List<Winery> findByRating(String filter);

    List<Winery> findByTitle(String title);

    List<Winery> findByAddress(String address);

    List<Winery> findAllByTitleLike(String search);

    List<Winery> findAllByAddressContainsIgnoreCase(String search);

    List<Winery> findAllByTitleContainsIgnoreCase(String search);
}
