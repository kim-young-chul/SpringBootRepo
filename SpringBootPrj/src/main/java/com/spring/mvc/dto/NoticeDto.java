package com.spring.mvc.dto;

import org.apache.ibatis.type.Alias;
import org.json.simple.JSONObject;

import lombok.Data;

/**
 * @프로젝트명 : AIBK-Security
 * @패키지명 : com.spring.mvc.dto
 * @파일명 : NoticeDto.java
 * @작성일 : 2023. 2. 27.
 * @작성자 : 김영철
 */
@Data
@Alias("noticeDto")
public class NoticeDto {

    /**
     * @필드타입 : int
     * @필드명 : idnotice
     */
    private int idnotice;

    /**
     * @필드타입 : String
     * @필드명 : subject
     */
    private String subject;

    /**
     * @필드타입 : String
     * @필드명 : content
     */
    private String content;

    /**
     * @메소드타입 : NoticeDto
     * @메소드명 : getJsonObj
     * @return : JSONObject
     * @return
     */
    @SuppressWarnings("unchecked")
    public JSONObject getJsonObj() {
        JSONObject obj = new JSONObject();
        obj.put("idnotice", this.idnotice);
        obj.put("subject", this.subject);
        obj.put("content", this.content);
        return obj;
    }
}
