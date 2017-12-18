package jjwt;

import com.google.common.io.BaseEncoding;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

public class JwtsTest {
    private static final Logger logger = LoggerFactory.getLogger(JwtsTest.class);
    private static final DateTime now = DateTime.now();
    private static final String iss = "http://local.com/api";
    private static final String sub = "admin";
    private static final String aud = "http://static.local.com/jeasyui";
    private static final Date iat = now.toDate();
    private static final Date exp = now.plusHours(2).toDate();
    private static final Date nbf = null;
    private static final String jti = UUID.randomUUID().toString();
    private static final String passwordSalt = "CLN41KXp";

    @Test
    public void encode() {
        // logger.info("{}",MacProvider.generateKey().toString());
        // long now = DateTime.now().toDate().getTime();


        String compactJws = Jwts.builder()
                .setIssuer(iss).setSubject(sub).setAudience(aud).setIssuedAt(iat)
                .setExpiration(exp).setNotBefore(nbf).setId(jti)
                .signWith(SignatureAlgorithm.HS256, BaseEncoding.base64().encode(passwordSalt.getBytes()))
                .compact();

        logger.info("compactJws: {}", compactJws);

    }

    @Test
    public void decode() {

        String compactJws = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWwuY29tL2FwaSIsInN1YiI6ImFkbWluIiwiYXVkIjoiaHR0cDovL3N0YXRpYy5sb2NhbC5jb20vamVhc3l1aSIsImlhdCI6MTUwNDM1NzE1MCwiZXhwIjoxNTA0MzY0MzUwLCJqdGkiOiIzN2UwMjZjMC0wYmY4LTQ0ZGQtYmJiMi1lZWJmNzRjNjQ3M2EifQ.Y2u9BikaKgdVrLVqR4Vci-HmhTgUWFHlXdfywoo-pmI";

        String jti = Jwts.parser().requireSubject(sub)
//                .requireIssuer(iss).requireSubject(sub).requireAudience(aud).requireIssuedAt(iat)
//                .requireExpiration(exp).requireNotBefore(nbf).requireId(jti)
                .setSigningKey(BaseEncoding.base64().encode(passwordSalt.getBytes()))
                .parseClaimsJws(compactJws).getBody().getId();

        // ExpiredJwtException, UnsupportedJwtException, MalformedJwtException,
        // SignatureException, IllegalArgumentException

        logger.info("jti: {}", jti);

    }

    //
}
