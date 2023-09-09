package com.project.poinhanshin.domain.board;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;


@ToString
@EqualsAndHashCode
public class BoardDto {
    private Integer bno;
    private String title;
    private String content;
    private String writer;
    private int view_cnt;
    private int comment_cnt;
    private Date reg_date;

    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");

    public Integer getBno() {
        return bno;
    }

    public void setBno(Integer bno) {
        this.bno = bno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getView_cnt() {
        return view_cnt;
    }

    public void setView_cnt(int view_cnt) {
        this.view_cnt = view_cnt;
    }

    public int getComment_cnt() {
        return comment_cnt;
    }

    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

    public String getReg_date() {
        return sdf.format(reg_date);
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public BoardDto(){}

    public BoardDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}