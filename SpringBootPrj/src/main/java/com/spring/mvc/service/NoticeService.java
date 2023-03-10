package com.spring.mvc.service;

import java.util.List;
import com.spring.mvc.dto.NoticeDto;

/**
 * @프로젝트명 : AIBK
 * @패키지명 : com.spring.mvc.service
 * @파일명 : NoticeService.java
 * @작성일 : 2023. 2. 20.
 * @작성자 : 김영철
 */
public interface NoticeService {

    /**
     * @메소드타입 : NoticeService
     * @메소드명 : selectNotice
     * @return : List<NoticeDto>
     */
    List<NoticeDto> selectNotice();

    /**
     * @메소드타입 : NoticeService
     * @메소드명 : selectOneNotice
     * @return : NoticeDto
     * @param noticeDto
     * @return
     */
    NoticeDto selectOneNotice(NoticeDto noticeDto);

    /**
     * @메소드타입 : NoticeService
     * @메소드명 : insertNotice
     * @return : int
     * @param noticeDto
     * @return
     */
    int insertNotice(NoticeDto noticeDto);

    /**
     * @메소드타입 : NoticeService
     * @메소드명 : deleteNotice
     * @return : int
     * @param noticeDto
     * @return
     */
    int deleteNotice(NoticeDto noticeDto);

    /**
     * @메소드타입 : NoticeService
     * @메소드명 : updateNotice
     * @return : int
     * @param noticeDto
     * @return
     */
    int updateNotice(NoticeDto noticeDto);
}
