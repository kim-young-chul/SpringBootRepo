/**
  * @파일명 : NoticeServiceTest.java
  * @작성일 : 2023. 2. 28.
  * @작성자 : 김영철
  */
package com.spring.mvc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.spring.mvc.dao.NoticeDao;
import com.spring.mvc.dto.NoticeDto;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.service
 * @파일명 : NoticeServiceTest.java
 * @작성일 : 2023. 2. 28.
 * @작성자 : 김영철
 */
@SpringBootTest
class NoticeServiceTest {

    @MockBean
    private NoticeDao noticeDao;

    @Autowired
    private NoticeServiceImpl noticeService;

    /**
     * Test method for
     * {@link com.spring.mvc.service.NoticeServiceImpl#NoticeServiceImpl()}.
     */
    @Test
    final void testNoticeServiceImpl() {
        assertNotNull(noticeService);
    }

    /**
     * Test method for
     * {@link com.spring.mvc.service.NoticeServiceImpl#selectNotice()}.
     */
    @Test
    final void testSelectNotice() {
        List<NoticeDto> noticeDtoLst = null;
        when(noticeDao.selectNotice()).thenReturn(noticeDtoLst);
        noticeService.selectNotice();
        verify(noticeDao).selectNotice();
    }

    /**
     * Test method for
     * {@link com.spring.mvc.service.NoticeServiceImpl#insertNotice(com.spring.mvc.dto.NoticeDto)}.
     */
    @Test
    final void testInsertNotice() {
        assertNotNull(noticeService);
    }

    /**
     * Test method for
     * {@link com.spring.mvc.service.NoticeServiceImpl#deleteNotice(com.spring.mvc.dto.NoticeDto)}.
     */
    @Test
    final void testDeleteNotice() {
        assertNotNull(noticeService);
    }

    /**
     * Test method for
     * {@link com.spring.mvc.service.NoticeServiceImpl#selectOneNotice(com.spring.mvc.dto.NoticeDto)}.
     */
    @Test
    final void testSelectOneNotice() {
        assertNotNull(noticeService);
    }

    /**
     * Test method for
     * {@link com.spring.mvc.service.NoticeServiceImpl#updateNotice(com.spring.mvc.dto.NoticeDto)}.
     */
    @Test
    final void testUpdateNotice() {
        assertNotNull(noticeService);
    }
}
