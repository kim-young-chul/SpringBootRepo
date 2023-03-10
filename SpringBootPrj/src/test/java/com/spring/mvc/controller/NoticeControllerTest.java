package com.spring.mvc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.ModelAndView;

import com.spring.mvc.dto.NoticeDto;
import com.spring.mvc.service.NoticeService;

@SpringBootTest
class NoticeControllerTest {

    @MockBean
    private NoticeService mockNoticeService;

    @Autowired
    private NoticeController mockNoticeController;

    @Test
    void testNoticeList() throws Exception {

        ModelAndView mav;
        List<NoticeDto> noticeDtoLst = null;
        when(mockNoticeService.selectNotice()).thenReturn(noticeDtoLst);
        mav = mockNoticeController.noticeList();
        assertEquals("notice_list", mav.getViewName());
        assertEquals(noticeDtoLst, mav.getModel().get("noticeList"));
    }

    @Test
    void testNoticeUpdate() throws Exception {

        ModelAndView mav;
        // 해당 번호의 게시물이 없는 경우
        mav = mockNoticeController.noticeUpdate(new NoticeDto());
        assertEquals("redirect:notice_list", mav.getViewName());

        // 해당 번호의 게시룸이 있는 경우
        when(mockNoticeService.selectOneNotice(new NoticeDto())).thenReturn(new NoticeDto());
        mav = mockNoticeController.noticeUpdate(new NoticeDto());
        assertEquals("notice_update", mav.getViewName());
        assertEquals(new NoticeDto(), (NoticeDto) mav.getModel().get("notice"));
    }

    @Test
    void testNoticeWrite() throws Exception {
        String view;
        view = mockNoticeController.noticeWrite();
        assertEquals("notice_write", view);
    }

    @Test
    void testNoticeDelete() throws Exception {

        String view;
        // 게시물이 정상 삭제된 경우
        when(mockNoticeService.deleteNotice(new NoticeDto())).thenReturn(1);
        view = mockNoticeController.noticeDelete(new NoticeDto());
        assertEquals("redirect:notice_list", view);

        // 게시물 번호가 잘못 된 경우
        when(mockNoticeService.deleteNotice(new NoticeDto())).thenReturn(0);
        view = mockNoticeController.noticeDelete(new NoticeDto());
        assertEquals("error", view);
    }

    @Test
    void testInsertNotice() throws Exception {

        String view;
        view = mockNoticeController.insertNotice(new NoticeDto());
        assertEquals("redirect:notice_list", view);
    }

    @Test
    void testUpdateNotice() throws Exception {

        String view;
        // 게시물이 정상 수정 된 경우
        when(mockNoticeService.updateNotice(new NoticeDto())).thenReturn(1);
        view = mockNoticeController.updateNotice(new NoticeDto());
        assertEquals("redirect:notice_list", view);

        // 게시물 수정에 실패 한 경우
        when(mockNoticeService.updateNotice(new NoticeDto())).thenReturn(0);
        view = mockNoticeController.updateNotice(new NoticeDto());
        assertEquals("error", view);
    }
}
