package com.spring.mvc.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.mvc.dao.NoticeDao;
import com.spring.mvc.dto.NoticeDto;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.service
 * @파일명 : NoticeServiceImpl.java
 * @작성일 : 2023. 2. 28.
 * @작성자 : 김영철
 */
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

    /**
     * @필드타입 : Logger
     * @필드명 : LOG
     */
    private static final Logger LOG = LogManager.getLogger(NoticeServiceImpl.class);

    /**
     * @필드타입 : NoticeDao
     * @필드명 : noticeDao
     */
    @Autowired
    private NoticeDao noticeDao;

    /**
     * @메소드타입 : NoticeServiceImpl
     */
    public NoticeServiceImpl() {
        LOG.debug("NoticeServiceImpl constructor");
    }

    /**
     * @메소드타입 : NoticeServiceImpl
     * @메소드명 : selectNotice
     * @return
     */
    @Transactional
    @Override
    public List<NoticeDto> selectNotice() {
        return noticeDao.selectNotice();
    }

    /**
     * @메소드타입 : NoticeServiceImpl
     * @메소드명 : insertNotice
     * @param noticeDt
     * @return
     */
    @Transactional
    @Override
    public int insertNotice(final NoticeDto noticeDt) {
        return noticeDao.insertNotice(noticeDt);
    }

    /**
     * @메소드타입 : NoticeServiceImpl
     * @메소드명 : deleteNotice
     * @param noticeDto
     * @return
     */
    @Override
    public int deleteNotice(final NoticeDto noticeDto) {
        return noticeDao.deleteNotice(noticeDto);
    }

    /**
     * @메소드타입 : NoticeServiceImpl
     * @메소드명 : selectOneNotice
     * @param noticeDto
     * @return
     */
    @Override
    public NoticeDto selectOneNotice(final NoticeDto noticeDto) {
        return noticeDao.selectOneNotice(noticeDto);
    }

    /**
     * @메소드타입 : NoticeServiceImpl
     * @메소드명 : updateNotice
     * @param noticeDto
     * @return
     */
    @Override
    public int updateNotice(final NoticeDto noticeDto) {
        return noticeDao.updateNotice(noticeDto);
    }
}
