package com.project.poinhanshin.service.protectboard;

import com.project.poinhanshin.domain.protectboard.ProtectBoardFileDto;
import com.project.poinhanshin.mapper.protectboard.ProtectBoardFileMapper;
import com.project.poinhanshin.mapper.protectboard.ProtectBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProtectBoardFileServiceImpl implements ProtectBoardFileService{
    
    ProtectBoardFileMapper protectBoardFileMapper;
    ProtectBoardMapper protectBoardMapper;

    @Autowired
    public ProtectBoardFileServiceImpl(ProtectBoardFileMapper protectBoardFileMapper, ProtectBoardMapper protectBoardMapper) {
        this.protectBoardFileMapper = protectBoardFileMapper;
        this.protectBoardMapper = protectBoardMapper;
    }


    // 이미지 첨부 파일 불러오기
    @Override
    public List<ProtectBoardFileDto> selectFiles(Integer protectboardno) {
        return protectBoardFileMapper.selectFiles(protectboardno);
    }

    // 이미지 첨부 파일 삭제
    @Override
    public int deleteFile(Integer protectboardfileno) {
        return protectBoardFileMapper.deleteFile(protectboardfileno);
    }

    @Override
    public int selectCnt(Integer protectboardfileno) {
        return protectBoardFileMapper.selectCnt(protectboardfileno);
    }

    @Override
    public int insetFile(MultipartFile[] multipartFile, Integer protectboard_userno) throws IOException {
        if(multipartFile == null)
            return 0;
        // 등록한 게시물의 번호 가져오기
        int protectboardno = protectBoardMapper.selectRecentBoardno(protectboard_userno);

        for(int i = 0 ; i < multipartFile.length; i++){

            String originalFileName = multipartFile[i].getOriginalFilename();

            String storedFileName = System.currentTimeMillis() + "_" + originalFileName;

            String savePath = "/Users/yuyeong-u/fileStorage/protectboard/" + storedFileName;

            multipartFile[i].transferTo(new File(savePath));

            ProtectBoardFileDto protectBoardFileDto = new ProtectBoardFileDto(null , null , originalFileName, storedFileName , protectboardno );
            protectBoardFileMapper.insertFiles(protectBoardFileDto);
        }
        return 1;
    }
}
