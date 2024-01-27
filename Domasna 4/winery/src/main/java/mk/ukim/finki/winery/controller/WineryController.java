package mk.ukim.finki.winery.controller;


import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.winery.model.Winery;
import mk.ukim.finki.winery.service.WineryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class WineryController {

    private final WineryService wineryService;


    public WineryController(WineryService wineryService) {
        this.wineryService = wineryService;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/wineries")
    public String listWineries(@RequestParam(required = false) String filter,
                               @RequestParam(required = false) String error, Model model) {
        if (filter != null && filter.equals("ChooseFilter")) {
            model.addAttribute("chooseFilter", true);
        }

        if (error != null && error.equals("WineryNotFound")) {
            model.addAttribute("wineryNotFound", true);
        }

        List<Winery> wineries = wineryService.findAll();


        List<Double> latitudes = new ArrayList<>();
        List<Double> longitudes = new ArrayList<>();

        lats(wineries, latitudes);
        lons(wineries, longitudes);

        model.addAttribute("wineries", wineries);
        model.addAttribute("latitudes", latitudes);
        model.addAttribute("longitudes", longitudes);
        return "winery";
    }

    @PostMapping("/wineries")
    public String filterWineries(@RequestParam(value = "filter", required = false) String filter, Model model) {
        if (filter == null) {
            return "redirect:/wineries";
        }
        List<Winery> wineries = wineryService.findBy(filter);

        List<Double> latitudes = new ArrayList<>();
        List<Double> longitudes = new ArrayList<>();


        lats(wineries, latitudes);
        lons(wineries, longitudes);

        model.addAttribute("wines", wineries);
        model.addAttribute("filteredLatitudes", latitudes);
        model.addAttribute("filteredLongitudes", longitudes);
        return "winery";
    }

    // Земање на географските должини на винариите
    private void lons(List<Winery> wineries, List<Double> longitudes) {
        for (Winery winery : wineries) {
            String[] parts = winery.getLink().split("/");

            double longitude = 0;
            String[] lons = parts[6].split("!");

            for (int i = 0; i < lons.length; i++) {
                if (lons[i].startsWith("4d")) {
                    longitude = Double.parseDouble(lons[i].substring(2));
                }
            }

            longitudes.add(longitude);
        }
    }

    // Земање на географските ширини на винариите
    private void lats(List<Winery> wineries, List<Double> latitudes) {
        for (Winery winery : wineries) {
            String[] parts = winery.getLink().split("/");

            double latitude = 0;
            String[] lats = parts[6].split("!");

            for (int i = 0; i < lats.length; i++) {
                if (lats[i].startsWith("3d")) {
                    latitude = Double.parseDouble(lats[i].substring(2));
                }
            }

            latitudes.add(latitude);
        }
    }

    @GetMapping("/winery/info/{id}")
    public String info(@PathVariable Long id, Model model, HttpSession session) {
        Optional<Winery> winery = wineryService.findById(id);
        if (winery.isEmpty()) {
            return "redirect:/wineries?error=WineryNotFound";
        }

        session.setAttribute("wineryId", id);
        String[] parts = winery.get().getLink().split("/");

        double latitude = 0;
        double longitude = 0;

        String[] lats = parts[6].split("!");

        for (int i = 0; i < lats.length; i++) {
            if (lats[i].startsWith("3d")) {
                latitude = Double.parseDouble(lats[i].substring(2));
            }
        }

        String[] lons = parts[6].split("!");

        for (int i = 0; i < lons.length; i++) {
            if (lons[i].startsWith("4d")) {
                longitude = Double.parseDouble(lons[i].substring(2));
            }
        }

        //TODO: get comments and ratings from users
        //TODO: average rating and num of comments for that winery
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        model.addAttribute("winery", winery.get());

        return "info";
    }

    @PostMapping("/wineries/search")
    public String search(@RequestParam(value = "filter", required = false) String filter,
                         @RequestParam("search") String search, Model model) {

        if (filter == null) {
            return "redirect:/wineries?filter=ChooseFilter";
        }

        if (search.equals("")) {
            return "redirect:/wineries?error=WineryNotFound";
        }

        List<Winery> wineries = wineryService.search(search, filter);
        if (wineries == null) {
            return "redirect:/wineries";
        }

        List<Double> latitudes = new ArrayList<>();
        List<Double> longitudes = new ArrayList<>();

        lats(wineries, latitudes);
        lons(wineries, longitudes);

        model.addAttribute("searchedWines", wineries);
        model.addAttribute("searchedLatitudes", latitudes);
        model.addAttribute("searchedLongitudes", longitudes);
        return "winery";
    }

    @GetMapping("/winery/aboutUs")
    public String about() {
        return "about";
    }

    @GetMapping("/winery/delete/{id}")
    public String deleteWinery(@PathVariable Long id) {
        boolean check = wineryService.deleteById(id);
        if (check) {
            return "redirect:/wineries";
        }

        return "redirect:/wineries?error=WineryNotFound";
    }

    @GetMapping("/winery/add-winery")
    public String addNewWinery(Model model, HttpSession session) {
        session.removeAttribute("wineryId");
        //TODO: get all users from user microservice
        return "addWinery";
    }

    @GetMapping("/winery/edit/{id}")
    public String editWinery(@PathVariable Long id, Model model, HttpSession httpSession) {
        Optional<Winery> winery = wineryService.findById(id);
        if (winery.isEmpty()) {
            return "redirect:/wineries?error=WineryNotFound";
        }
        httpSession.setAttribute("wineryId", id);
        model.addAttribute("winery", winery.get());
        //TODO: get all users from user microservice
        return "addWinery";
    }

    @PostMapping("/winery/add")
    public String saveWinery(@RequestParam("title") String title,
                             @RequestParam("link") String link,
                             @RequestParam("main-category") String category,
                             @RequestParam("rating") String rating,
                             @RequestParam("reviews") String reviews,
                             @RequestParam("address") String address,
                             @RequestParam("userId") Long userId, HttpSession httpSession) {
        Long id = (Long) httpSession.getAttribute("wineryId");
        Winery winery = wineryService.save(title, link, category, rating, reviews, address, id, userId);
        if (winery == null) {
            return "redirect:/wineries?error=WineryNotFound";
        }
        httpSession.removeAttribute("wineryId");
        return "redirect:/wineries";
    }
}
