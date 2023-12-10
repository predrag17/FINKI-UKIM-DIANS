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

    @Query(value = "SELECT w FROM mk.ukim.finki.backend.model.Winery w WHERE LOWER(w.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(w.address) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Winery> findBySearchTextContains(@Param("search") String search);
}
