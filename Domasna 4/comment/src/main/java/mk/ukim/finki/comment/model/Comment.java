package mk.ukim.finki.comment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    private Long id;
    private String comment;
    private Long wineryId;
    private Long userId;

    public Comment(String comment, Long userId, Long wineryId) {
        this.comment = comment;
        this.userId = userId;
        this.wineryId = wineryId;
    }

    @Override
    public String toString() {
        return String.format("%s", comment);
    }
}
