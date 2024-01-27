package mk.ukim.finki.rating.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @SequenceGenerator(name = "rating_sequence", sequenceName = "rating_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_sequence")
    private Long id;

    private String rating;
    private Long userId;
    private Long wineryId;

    public Rating(String rating, Long userId, Long wineryId) {
        this.rating = rating;
        this.userId = userId;
        this.wineryId = wineryId;
    }
}
