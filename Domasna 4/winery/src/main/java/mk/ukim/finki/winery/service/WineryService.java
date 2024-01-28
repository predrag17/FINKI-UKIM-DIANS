package mk.ukim.finki.winery.service;

import mk.ukim.finki.winery.model.Winery;

import java.util.List;
import java.util.Optional;

public interface WineryService {

    List<Winery> findAll();

    List<Winery> findBy(String filter);

    Optional<Winery> findById(Long id);

    List<Winery> search(String search, String filter);

    boolean deleteById(Long id);

    Winery save(String title, String link, String category, String rating, String reviews, String address, Long id, Long userId);

    boolean isLogged();
}
