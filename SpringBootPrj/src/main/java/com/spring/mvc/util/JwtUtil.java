/**
  * @파일명 : JwtUtil.java
  * @작성일 : 2023. 3. 19.
  * @작성자 : 김영철
  */
package com.spring.mvc.util;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObject;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;

/**
 * @프로젝트명 : SpringBootPrj
 * @패키지명 : com.spring.mvc.util
 * @파일명 : JwtUtil.java
 * @작성일 : 2023. 3. 19.
 * @작성자 : 김영철
 */
@Slf4j
public class JwtUtil {

    private static final String SECRET_KEY = "fTjWnZq4t7w!z%C*F-JaNdRgUkXp2s5u";

    /**
     * @메소드타입 : JwtUtil
     * @메소드명 : createToken
     * @return : String
     * @param userid
     * @return
     * @throws JOSEException
     */
    public String createToken(final String userid) throws JOSEException {
        // 현재 시간
        long longIat = new Date().getTime();
        // 만료 시간
        long longExp = new Date().getTime() + 1000 * 60L * 10L; // 10분
        // 정보 (payload)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                // registered claims
                .issuer("aihub.com")
                .subject("certification")
                .audience("avengers")
                .expirationTime(new Date(longExp))
                .notBeforeTime(new Date(longIat))
                .issueTime(new Date(longIat))
                .jwtID("endgame")
                // public claims
                .claim("https://aihub.com/jwt_claims/is_member", true)
                // private claims
                .claim("userid", userid)
                .claim("username", "ironman")
                .build();
        log.info("clamsSet ... {}", claimsSet.toString());
        // 헤더 (header) HMAC SHA256
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        log.info("JWSAlgorithm ... {}", JWSAlgorithm.HS256);
        log.info("JWSHeader ... {}", new JWSHeader(JWSAlgorithm.HS256));
        // 서명 (signature) key is 256bit(32bytes) over ...
        JWSSigner signer = new MACSigner(SECRET_KEY);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    /**
     * @메소드타입 : JwtUtil
     * @메소드명 : verifyToken
     * @return : String
     * @param jwtString
     * @return
     * @throws JOSEException
     * @throws ParseException
     */
    public String verifyToken(String jwtString) throws JOSEException, ParseException {
        JWT signedJWT = SignedJWT.parse(jwtString);
        // check payload and applied algorithm in header
        Map<String, Object> map = ((JOSEObject) signedJWT).getPayload().toJSONObject();
        log.info("getPayload ... {}", map);
        log.info("getAlgorithm ... {}", signedJWT.getHeader().getAlgorithm());
        // verification with signature
        JWSVerifier verifier = new MACVerifier(SECRET_KEY);
        boolean isValid = SignedJWT.parse(jwtString).verify(verifier);
        log.info("isValid ... {}", isValid);
        String userid = null;
        if (isValid) {
            userid = (String) map.get("userid");
            log.info("userid ... {}", userid);
        }
        return userid;
    }
}
