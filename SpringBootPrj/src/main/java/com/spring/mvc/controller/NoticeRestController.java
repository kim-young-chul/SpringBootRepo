/**
  * @파일명 : NoticeRestController.java
  * @작성일 : 2023. 3. 13.
  * @작성자 : 김영철
  */
package com.spring.mvc.controller;

import java.text.ParseException;
import java.util.List;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.spring.mvc.dto.NoticeDto;
import com.spring.mvc.service.NoticeService;
import lombok.extern.slf4j.Slf4j;

/**
 * @프로젝트명 : SpringBootPrj
 * @패키지명 : com.spring.mvc.controller
 * @파일명 : NoticeRestController.java
 * @작성일 : 2023. 3. 13.
 * @작성자 : 김영철
 */

@Slf4j
@RestController
@RequestMapping("/api")
public class NoticeRestController {

    /**
     * @필드타입 : NoticeService
     * @필드명 : noticeService
     */
    @Autowired
    private NoticeService noticeService;

    /**
     * @메소드타입 : NoticeRestController
     * @메소드명 : noticeList
     * @return : ResponseEntity<String>
     * @return
     * @throws ParseException
     * @throws JOSEException
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/notice_list")
    public ResponseEntity<String> noticeList(@CookieValue(name = "jwt", defaultValue = "default-jwt") String jwtString)
            throws JOSEException, ParseException {
        log.info("jwtString ... {}", jwtString);
        JSONArray arr = new JSONArray();
        String responseData = null;
        List<NoticeDto> noticeDtoLst = noticeService.selectNotice();
        for (NoticeDto noticeDto : noticeDtoLst) {
            arr.add(noticeDto.getJsonObj());
        }
        log.info("response ... {}", arr.toString());
        responseData = arr.toString();
        return ResponseEntity.ok(responseData);
    }

    /**
     * @메소드타입 : NoticeRestController
     * @메소드명 : noticeUpdate
     * @return : ResponseEntity<String>
     * @param noticeDto
     * @return
     */
    @GetMapping("/notice_update")
    public ResponseEntity<String> noticeUpdate(final NoticeDto noticeDto) {
        final NoticeDto noticeDtoOut = noticeService.selectOneNotice(noticeDto);
        return ResponseEntity.ok(noticeDtoOut.getJsonObj().toString());
    }

    /**
     * @메소드타입 : NoticeRestController
     * @메소드명 : updateNotice
     * @return : ResponseEntity<String>
     * @param noticeDto
     * @return
     */
    @PostMapping("/update_notice")
    public ResponseEntity<String> updateNotice(final NoticeDto noticeDto) {
        String response;
        int rows = noticeService.updateNotice(noticeDto);
        if (rows > 0) {
            response = "updateSuccess";
        } else {
            response = "updateFailed";
        }
        return ResponseEntity.ok(response);
    }

    /**
     * @메소드타입 : NoticeRestController
     * @메소드명 : noticeDelete
     * @return : ResponseEntity<String>
     * @param noticeDto
     * @return
     */
    @GetMapping("/notice_delete")
    public ResponseEntity<String> noticeDelete(final NoticeDto noticeDto) {
        String response;
        int rows = noticeService.deleteNotice(noticeDto);
        if (rows > 0) {
            response = "deleteSuccess";
        } else {
            response = "deleteFailed";
        }
        return ResponseEntity.ok(response);
    }

    /**
     * @메소드타입 : NoticeRestController
     * @메소드명 : insertNotice
     * @return : ResponseEntity<String>
     * @param noticeDto
     * @return
     */
    @PostMapping("/insert_notice")
    public ResponseEntity<String> insertNotice(final NoticeDto noticeDto) {
        String response;
        int rows = noticeService.insertNotice(noticeDto);
        if (rows > 0) {
            response = "insertSuccess";
        } else {
            response = "insertFailed";
        }
        return ResponseEntity.ok(response);
    }
}
