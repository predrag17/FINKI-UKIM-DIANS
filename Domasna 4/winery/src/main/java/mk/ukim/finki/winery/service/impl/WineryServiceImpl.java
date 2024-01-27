package mk.ukim.finki.winery.service.impl;



import mk.ukim.finki.winery.model.Winery;
import mk.ukim.finki.winery.repository.WineryRepository;
import mk.ukim.finki.winery.service.WineryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WineryServiceImpl implements WineryService {

    private final WineryRepository wineryRepository;

//    @Autowired
//    private WineryInterface wineryInterface;

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
                    .sorted(Comparator.comparing((Winery winery) -> Integer.parseInt(winery.getReviews())).reversed())
                    .collect(Collectors.toList());
        }

        if (filter.equals("Рејтинг")) {
            return wineries.stream()
                    .filter(winery -> Objects.nonNull(winery.getRating()))
                    .sorted(Comparator.comparing((Winery winery) -> Double.parseDouble(winery.getRating())).reversed())
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
    public List<Winery> search(String search, String filter) {
        if (search.startsWith(" ")) {
            search = search.substring(1);
        } else if (search.endsWith(" ")) {
            search = search.substring(0, search.length() - 1);
        }

        if (filter.equals("Име")) {
            return wineryRepository.findAllByTitleContainsIgnoreCase(search);
        }

        if (filter.equals("Град")) {
            return wineryRepository.findAllByAddressContainsIgnoreCase(search);
        }

        return wineryRepository.findAll();

    }

    @Override
    public boolean deleteById(Long id) {
        if (!wineryRepository.existsById(id)) {
            return false;
        }

        wineryRepository.deleteById(id);
        return true;
    }

    @Override
    public Winery save(String title, String link, String category, String rating, String reviews, String address, Long id, Long userId) {

        if (id == null) {
            return this.wineryRepository.save(new Winery(title, link, category, rating, reviews, address, userId));
        } else {
            Optional<Winery> winery = wineryRepository.findById(id);
            if (winery.isPresent()) {
                winery.get().setTitle(title);
                winery.get().setLink(link);
                winery.get().setMainCategory(category);
                winery.get().setRating(rating);
                winery.get().setReviews(reviews);
                winery.get().setAddress(address);
                winery.get().setUserId(userId);
                return wineryRepository.save(winery.get());
            } else {
                return null;
            }

        }

    }

//    @Override
//    public boolean isLogged() {
//        return wineryInterface.sessionUser();
//    }
}
