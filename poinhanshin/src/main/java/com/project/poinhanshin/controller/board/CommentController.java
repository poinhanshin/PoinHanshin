package com.project.poinhanshin.controller.board;

import com.project.poinhanshin.domain.board.BoardDto;
import com.project.poinhanshin.domain.board.CommentDto;
import com.project.poinhanshin.domain.member.User;
import com.project.poinhanshin.service.board.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController (CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    @ResponseBody
    public String addComment( @RequestBody CommentDto commentDto) {

        commentService.addComment(commentDto);
        return "success";
    }

    @GetMapping("/getComments")
    @ResponseBody
    public List<CommentDto> getComments() {
        return commentService.getComments();
    }
}
