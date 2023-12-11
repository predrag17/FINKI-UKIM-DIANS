package mk.ukim.finki.backend.service.impl;

import mk.ukim.finki.backend.model.Winery;
import mk.ukim.finki.backend.repository.WineryRepository;
import mk.ukim.finki.backend.service.WineryService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WineryServiceImpl implements WineryService {

    private final WineryRepository wineryRepository;

    public WineryServiceImpl(WineryRepository wineryRepository) {
        this.wineryRepository = wineryRepository;
    }

    @Override
    public List<Winery> findAll() {
        return wineryRepository.findAll();
    }

    @Override
    public List<Winery> findBy(String filter) {
        if (filter.isEmpty()) {
            return findAll();
        }


        List<Winery> wineries = findAll();

        if (filter.equals("Коментар")) {
            return wineries.stream()
                    .filter(winery -> Objects.nonNull(winery.getReviews()))
                    .mapToInt(winery -> Integer.parseInt(winery.getReviews()))
                    .boxed()
                    .sorted(Comparator.reverseOrder())
                    .map(sorted -> wineries.stream()
                            .filter(winery -> Objects.equals(winery.getReviews(), Integer.toString(sorted)))
                            .findFirst().orElse(null))
                    .collect(Collectors.toList());

        }

        if (filter.equals("Рејтинг")) {
            return wineries.stream()
                    .filter(winery -> Objects.nonNull(winery.getRating()))
                    .sorted(Comparator.comparing(Winery::getRating).reversed())
                    .collect(Collectors.toList());
        }


        return wineries;
    }

    @Override
    public Optional<Winery> findById(Long id) {
        if (wineryRepository.existsById(id)) {
            return wineryRepository.findById(id);
        }

        return Optional.empty();
    }

    @Override
    public List<Winery> search(String search) {
        if (search.startsWith(" ")) {
            search = search.substring(1);
        } else if (search.endsWith(" ")) {
            search = search.substring(0, search.length() - 1);
        }

        return wineryRepository.findBySearchTextContains(search);


    }

    @Override
    public void deleteById(Long id) {
        wineryRepository.deleteById(id);
    }

    @Override
    public void save(String title, String link, String category, String rating, String reviews, String address, Long id){
        if(id == null){
            this.wineryRepository.save(new Winery(title, link, category, rating, reviews, address));
            return;
        }
        Optional<Winery> winery = wineryRepository.findById(id);
        if(winery.isPresent()){
            winery.get().setTitle(title);
            winery.get().setLink(link);
            winery.get().setMainCategory(category);
            winery.get().setRating(rating);
            winery.get().setReviews(reviews);
            winery.get().setAddress(address);
            return;
        }
        this.wineryRepository.save(new Winery(title, link, category, rating, reviews, address));
    }
}
