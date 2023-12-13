package mk.ukim.finki.backend.service.impl;

import mk.ukim.finki.backend.model.Comment;
import mk.ukim.finki.backend.model.User;
import mk.ukim.finki.backend.model.Winery;
import mk.ukim.finki.backend.repository.CommentRepository;
import mk.ukim.finki.backend.repository.WineryRepository;
import mk.ukim.finki.backend.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final WineryRepository wineryRepository;

    public CommentServiceImpl(CommentRepository commentRepository, WineryRepository wineryRepository) {
        this.commentRepository = commentRepository;
        this.wineryRepository = wineryRepository;
    }


    @Override
    public void save(String comment, Long wineryId, User user) {
        Optional<Winery> winery = wineryRepository.findById(wineryId);

        Comment c = new Comment(comment, user, winery.get());
        commentRepository.save(c);
        int size = winery.get().getComments().size();
        winery.get().setReviews(String.valueOf(size));
        wineryRepository.save(winery.get());
    }

    @Override
    public void delete(Long id, Long wineryId) {
        Optional<Winery> winery = wineryRepository.findById(wineryId);
        commentRepository.deleteById(id);

        winery.get().setReviews(String.valueOf(winery.get().getComments().size()));
        wineryRepository.save(winery.get());
    }
}
