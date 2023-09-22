package com.project.poinhanshin.service.map;

import com.project.poinhanshin.domain.board.BoardFileDto;
import com.project.poinhanshin.domain.etc.SearchCondition;
import com.project.poinhanshin.domain.map.MapBoardDto;
import com.project.poinhanshin.domain.map.MapBoardFileDto;
import com.project.poinhanshin.mapper.map.MapFileMapper;
import com.project.poinhanshin.mapper.map.MapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapBoardServicelmpl implements MapBoardService{
    MapMapper mapMapper;
    MapFileMapper mapFileMapper;

    @Autowired
    public MapBoardServicelmpl(MapMapper mapMapper, MapFileMapper mapFileMapper) {
        this.mapMapper = mapMapper;
        this.mapFileMapper = mapFileMapper;
    }

    // 검색된 게시물 리스트를 가져온다.
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MapBoardDto> getMapBoardListService(SearchCondition sc) {

        List<MapBoardDto> mapBoardDtoList = mapMapper.selectMapList(sc);

        for(MapBoardDto mapBoardDto : mapBoardDtoList){
            List<String> storedFileName = mapFileMapper.MapBoardSelectFileName(mapBoardDto.getMapboardno());
            mapBoardDto.setStoredFileName(storedFileName);
        }

        return mapBoardDtoList;
    }

    // 검색된 게시물 리스트 개수를 반환
    @Override
    public int getMapBoardListCnt(SearchCondition sc) {



        return mapMapper.selectMapCnt(sc);
    }

    // 게시물을 하나 가져오기
    @Override
    @Transactional(rollbackFor = Exception.class)
    public MapBoardDto bringMapBoardOne(Integer mapboardno) {

        MapBoardDto mapBoardDto = mapMapper.selectMapBoardOne(mapboardno);
        if(mapBoardDto.getFileAttached() == 0){
            // 파일 없음
            return mapBoardDto;
        } else {
            // 파일 있음
            List<MapBoardFileDto> mapBoardFileDtoList = mapFileMapper.MapBoardSelectFile(mapboardno);

            // 원본 파일 이름 저장하기 위한 리스트
            List<String> originalFileNameList = new ArrayList<>();

            // 서버에 저장된 이름을 저장하기 위한 리스트
            List<String> storedFileNameList = new ArrayList<>();

            // DB에서 게시물 이미지 파일들을 읽어 각각 mapBoardDto에 저장함
            for(MapBoardFileDto mapBoardFileDto : mapBoardFileDtoList){
                originalFileNameList.add(mapBoardFileDto.getOriginal_file_name());
                storedFileNameList.add(mapBoardFileDto.getStored_file_name());
            }

            mapBoardDto.setOriginalFileName(originalFileNameList);
            mapBoardDto.setStoredFileName(storedFileNameList);

        }
        return mapBoardDto;
    }

    // 게시물 등록
    @Override
    @Transactional( rollbackFor = Exception.class)
    public int writeMapBoard(MapBoardDto mapBoardDto) throws IOException {
        // 게시물 등록
        mapMapper.insertMapBoard(mapBoardDto);

        Integer currentMapBoardNo = mapMapper.selectRecentMapBoardNo(mapBoardDto.getMapboardno());

        mapBoardDto.setMapboardno(currentMapBoardNo);

        // 첨부 파일 있음
        if(mapBoardDto.getFileAttached() != 0){
            addImgFiles(mapBoardDto);
        }
        return currentMapBoardNo;
    }

    // 게시물 수정
    @Override
    public int modifyMapBoard(MapBoardDto mapBoardDto, Integer loginUser) throws IOException {

        int imgCnt = mapFileMapper.MapBoardSelectCnt(mapBoardDto.getMapboardno());

        // 이미지가 있는 게시물을 수정할 때
        if(mapBoardDto.getFileAttached() == 1){
            System.out.println("이미지가 있는 게시물 수정");
            if(imgCnt == 0){
                // 기존 이미지를 모두 삭제하고 이미지를 추가하지 않은 경우
                System.out.println("기존 이미지 X , 이미지 추가 X");
                mapBoardDto.setFileAttached(0);
            } else {
                // 이미지가 하나라도 남아 있는 경우
                System.out.println("이미지가 하나라도 존재하는 경우");
                if(mapBoardDto.getMapBoardFile() != null){
                    // 추가 이미지가 있는 경우
                    System.out.println("이미지를 추가하는 경우");
                    // 이미지 저장
                    addImgFiles(mapBoardDto);
                } else {
                    // 추가 이미지가 없는 경우
                    System.out.println("이미지를 추가하지 않는 경우");
                }
            }
        } else {
            // 이미지가 없는 게시물을 수정할 때
            System.out.println("이미지가 없는 게시물 수정시");
            if(mapBoardDto.getMapBoardFile() != null){
                // 이미지를 추가하는 경우
                System.out.println("이미지를 추가하는 경우");
                mapBoardDto.setFileAttached(1);
                // 이미지 저장
                addImgFiles(mapBoardDto);
            } else {
                // 이미지를 추가하지 않는 경우
                System.out.println("이미지를 추가하지 않는 경우");
            }
        }

        // 게시물 내용 업데이트
        mapMapper.updateMapBoard(mapBoardDto);

        return mapBoardDto.getMapboardno();
    }

    // 게시물 삭제
    @Override
    public int removeMapBoard(Integer mapboardno, Long loginUser) throws IOException {
        return 0;
    }

    // 최근 등록 게시물 번호 반환
    @Override
    public int bringRecentContentNo(Integer mapboard_userno) {
        return mapMapper.selectRecentMapBoardNo(mapboard_userno);
    }

    // 파일 저장 여부 값 수정
    @Override
    public int updateFileAttached(Integer mapboardno, Integer fileAttached) {
        return 0;
    }

    // 이미지 저장을 위해 사용하는 메서드
    @Override
    public void addImgFiles(MapBoardDto mapBoardDto) throws IOException {
        for (MultipartFile mapBoardFile : mapBoardDto.getMapBoardFile()) {

            // 파일 이름 저장 (서버 이름 X)
            String originalFileName = mapBoardFile.getOriginalFilename();

            // 서버 저장용 이름 // System.currentTimeMillis - 현재 몇 밀리초가 지났는지 - 겹치면 안 되기 때문
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
            // 서버 컴퓨터 파일 저장 위치
            String savePath = "/Users/yuyeong-u/fileStorage/mapBoard/" + storedFileName;
            // java.io.File; // 지정된 경로로 파일을 넘긴다.
            mapBoardFile.transferTo(new File(savePath));

            // 파일 테이블에 데이터 저장 ( 원본 파일 , 서버에 저장할 이름 , 부모 게시물 번호 )
            MapBoardFileDto mapBoardFileDto = new MapBoardFileDto(mapBoardDto.getMapboardno(), null , null , originalFileName ,storedFileName , mapBoardFile.getSize() );
            mapFileMapper.MapBoardInsertFile(mapBoardFileDto);

        }
    }

    // 마이페이지 - 자신의 게시판 즐겨찾기 리스트 불러오기
    @Override
    public List<MapBoardDto> SelectMyMapService(Integer userno) {
        List<MapBoardDto> SelectMyMapService = mapMapper.SelectMyMap(userno);
        return SelectMyMapService;
    }


    // 마이페이지 - 자신이 작성한 실종/발견 지도 게시글 불러오기
    @Override
    public List<MapBoardDto> WriteMyMapService(Integer userno) {
        List<MapBoardDto> WriteMyMapService = mapMapper.WriteMyMap(userno);
        return WriteMyMapService;
    }
}
