package mk.ukim.finki.winery.repository;


import mk.ukim.finki.winery.model.Winery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WineryRepository extends JpaRepository<Winery, Long> {
    List<Winery> findAllByTitleContainsIgnoreCase(String search);

    List<Winery> findAllByAddressContainsIgnoreCase(String search);
}
