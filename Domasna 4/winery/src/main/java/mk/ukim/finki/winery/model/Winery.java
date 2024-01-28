package mk.ukim.finki.winery.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity(name = "wineries")
@Table
public class Winery {

    @Id
    @SequenceGenerator(name = "winery_sequence", sequenceName = "winery_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "winery_sequence")
    private Long id;
    private String title;
    private String link;

    @Column(name = "main_category")
    private String mainCategory;
    private String rating;
    private String reviews;
    private String address;
    private Long userId;


    public Winery(String title, String link, String mainCategory, String rating, String reviews, String address, Long userId) {
        this.title = title;
        this.link = link;
        this.mainCategory = mainCategory;
        this.rating = rating;
        this.reviews = reviews;
        this.address = address;
        this.userId = userId;
    }


    @Override
    public String toString() {
        return String.format("Име: %s, Рејтинг: %s, Коментари: %s, Адреса: %s", title, rating, reviews, address);
    }
}
