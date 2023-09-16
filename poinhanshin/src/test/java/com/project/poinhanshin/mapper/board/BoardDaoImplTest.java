package com.project.poinhanshin.mapper.board;

import com.project.poinhanshin.domain.board.BoardDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BoardDaoImplTest {

    @Autowired
    BoardMapper boardMapper;

    @Test
    public void test() {

    }

    @Test
    public void delete() {
        boardMapper.deleteAll();
    }
}