package com.spring.mvc.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.mvc.dto.NoticeDto;
import com.spring.mvc.service.NoticeService;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.controller
 * @파일명 : NoticeController.java
 * @작성일 : 2023. 2. 27.
 * @작성자 : 김영철
 */
@Controller
public class NoticeController {

    /**
     * @필드타입 : String
     * @필드명 : RE_NOTICE_LIST
     */
    private static final String RE_NOTICE_LIST = "redirect:notice_list";

    /**
     * @필드타입 : Logger
     * @필드명 : LOG
     */
    private static final Logger LOG = LogManager.getLogger(NoticeController.class);

    /**
     * @필드타입 : NoticeService
     * @필드명 : noticeService
     */
    @Autowired
    private NoticeService noticeService;

    /**
     * @메소드타입 : NoticeController
     */
    public NoticeController() {
        LOG.debug("NoticeController constructor");
    }

    /**
     * @메소드타입 : NoticeController
     * @메소드명 : noticeList
     * @return : ModelAndView
     */
    @GetMapping("/servlet/notice_list")
    public ModelAndView noticeList() {
        List<NoticeDto> noticeDtoLst = noticeService.selectNotice();
        final ModelAndView mav = new ModelAndView();
        mav.setViewName("notice_list");
        mav.addObject("noticeList", noticeDtoLst);
        return mav;
    }

    /**
     * @메소드타입 : NoticeController
     * @메소드명 : noticeUpdate
     * @return : ModelAndView
     * @param noticeDto
     * @return
     */
    @GetMapping("/servlet/notice_update")
    public ModelAndView noticeUpdate(final NoticeDto noticeDto) {
        final NoticeDto noticeDtoOut = noticeService.selectOneNotice(noticeDto);
        final ModelAndView mav = new ModelAndView();
        if (noticeDtoOut != null) {
            mav.setViewName("notice_update");
            mav.addObject("notice", noticeDtoOut);
        } else {
            mav.setViewName(RE_NOTICE_LIST);
        }
        return mav;
    }

    /**
     * @메소드타입 : NoticeController
     * @메소드명 : noticeWrite
     * @return : String
     */
    @GetMapping("/servlet/notice_write")
    public String noticeWrite() {
        return "notice_write";
    }

    /**
     * @메소드타입 : NoticeController
     * @메소드명 : noticeDelete
     * @return : String
     * @param noticeDto
     * @return
     */
    @GetMapping("/servlet/notice_delete")
    public String noticeDelete(final NoticeDto noticeDto) {
        String view;
        int rows = noticeService.deleteNotice(noticeDto);
        if (rows > 0) {
            view = RE_NOTICE_LIST;
        } else {
            view = "error";
        }
        return view;
    }

    /**
     * @메소드타입 : NoticeController
     * @메소드명 : insertNotice
     * @return : String
     * @param noticeDto
     * @return
     */
    @PostMapping("/servlet/insert_notice")
    public String insertNotice(final NoticeDto noticeDto) {
        noticeService.insertNotice(noticeDto);
        return RE_NOTICE_LIST;
    }

    /**
     * @메소드타입 : NoticeController
     * @메소드명 : updateNotice
     * @return : String
     * @param noticeDto
     * @return
     */
    @PostMapping("/servlet/update_notice")
    public String updateNotice(final NoticeDto noticeDto) {
        String view;
        int rows = noticeService.updateNotice(noticeDto);
        if (rows > 0) {
            view = RE_NOTICE_LIST;
        } else {
            view = "error";
        }
        return view;
    }
}
