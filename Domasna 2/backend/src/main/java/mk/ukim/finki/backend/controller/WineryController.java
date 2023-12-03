package mk.ukim.finki.backend.controller;

import mk.ukim.finki.backend.model.Winery;
import mk.ukim.finki.backend.service.WineryService;
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
    public String listWineries(@RequestParam(required = false) String error, Model model) {
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
    public String filterWineries(@RequestParam("filter") String filter, Model model) {
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
    public String info(@PathVariable Long id, Model model) {
        Optional<Winery> winery = wineryService.findById(id);
        if (winery.isEmpty()) {
            return "redirect:/wineries?error=WineryNotFound";
        }

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

        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        model.addAttribute("winery", winery.get());

        return "info";
    }

    @PostMapping("/wineries/search")
    public String search(@RequestParam("search") String search, Model model) {
        if (search.equals("")) {
            return "redirect:/wineries?error=WineryNotFound";
        }

        List<Winery> wineries = wineryService.search(search);
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

}
