package com.project.poinhanshin.service.board;

import com.project.poinhanshin.domain.board.CommentDto;
import com.project.poinhanshin.mapper.board.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentMapper commentMapper;
    @Autowired
    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public void addComment(CommentDto commentDto) {
        commentMapper.insertComment(commentDto);
    }

    public List<CommentDto> getComments() {
        return commentMapper.getComments();
    }
}
