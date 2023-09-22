package com.project.poinhanshin.mapper.member;

import com.project.poinhanshin.domain.board.BoardDto;
import com.project.poinhanshin.domain.member.User;
import com.project.poinhanshin.domain.protectboard.ProtectBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    void updateUser(User user);
    void dismissUser(Long userno);
    List<BoardDto> writeMyBoard(Integer userno);
    List<BoardDto> selectMyBoard(Integer userno);
    List<ProtectBoardDto> selectMyprotectboard(Integer userno);
}
