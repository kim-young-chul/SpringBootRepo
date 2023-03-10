/**
  * @파일명 : NoticeDao.java
  * @작성일 : 2023. 2. 26.
  * @작성자 : 김영철
  */
package com.spring.mvc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.mvc.dto.NoticeDto;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.dao
 * @파일명 : NoticeDao.java
 * @작성일 : 2023. 2. 27.
 * @작성자 : 김영철
 */
@Mapper
public interface NoticeDao {

    /**
     * @메소드타입 : NoticeDao
     * @메소드명 : selectNotice
     * @return : List<NoticeDto>
     */
    List<NoticeDto> selectNotice();

    /**
     * @메소드타입 : NoticeDao
     * @메소드명 : selectOneNotice
     * @return : NoticeDto
     * @param noticeDto
     */
    NoticeDto selectOneNotice(NoticeDto noticeDto);

    /**
     * @메소드타입 : NoticeDao
     * @메소드명 : insertNotice
     * @return : int
     * @param noticeDto
     */
    int insertNotice(NoticeDto noticeDto);

    /**
     * @메소드타입 : NoticeDao
     * @메소드명 : deleteNotice
     * @return : int
     * @param noticeDto
     */
    int deleteNotice(NoticeDto noticeDto);

    /**
     * @메소드타입 : NoticeDao
     * @메소드명 : updateNotice
     * @return : int
     * @param noticeDto
     */
    int updateNotice(NoticeDto noticeDto);
}
