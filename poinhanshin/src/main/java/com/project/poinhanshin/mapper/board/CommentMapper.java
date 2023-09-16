package com.project.poinhanshin.mapper.board;

import com.project.poinhanshin.domain.board.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(CommentDto commentDto);
    List<CommentDto> getComments();
}
