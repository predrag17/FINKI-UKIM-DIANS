package mk.ukim.finki.comment.service;


public interface CommentService {
    void save(String comment, Long wineryId, Long userId);

    void delete(Long id);
}
