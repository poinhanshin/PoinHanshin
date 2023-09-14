package com.project.poinhanshin.domain.board;

import lombok.Data;

@Data
public class CommentDto {
    private Integer id;
    private Integer bno;
    private String writer;
    private String content;
}
