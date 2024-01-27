package mk.ukim.finki.comment.service.impl;


import mk.ukim.finki.comment.repository.CommentRepository;
import mk.ukim.finki.comment.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Override
    public void save(String comment, Long wineryId, Long userId) {
        //TODO: save comment
    }

    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
