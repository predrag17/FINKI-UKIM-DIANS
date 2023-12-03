package mk.ukim.finki.backend.service;

import mk.ukim.finki.backend.model.Winery;

import java.util.List;
import java.util.Optional;

public interface WineryService {

    List<Winery> findAll();

    List<Winery> findBy(String filter);

    Optional<Winery> findById(Long id);

    List<Winery> search(String search);
}