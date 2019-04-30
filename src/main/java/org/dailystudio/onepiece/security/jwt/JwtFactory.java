package org.dailystudio.onepiece.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.dailystudio.onepiece.security.context.AccountContext;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class JwtFactory {

    private static final LocalDateTime NOW = LocalDateTime.now();

    public static String create(final AccountContext context) {
        final Date expiredDate = Date.from(NOW.plusDays(JwtInfo.EXPIRES_LIMIT).toInstant(ZoneOffset.ofHours(9)));
        return create(context, expiredDate);
    }

    public static String create(final AccountContext context, final Date expiredDate) throws JWTCreationException {
        return JWT.create()
                .withClaim(JwtInfo.USER_IDX, context.getUsername())
                .withClaim(JwtInfo.USER_AUTHORITY, context.getAuthorities().toArray()[0].toString())
                .withIssuer(JwtInfo.ISSUER)
                .withExpiresAt(expiredDate)
                .sign(JwtInfo.getAlgorithm());
    }


    public static Boolean isValid(final String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(JwtInfo.getAlgorithm()).build();
            jwtVerifier.verify(token);

            return true;
        } catch (JWTVerificationException verificationException) {

            return false;
        }
    }

    public static DecodedJWT decode(final String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            if (decodedJWT == null) {
                throw new BadCredentialsException("토큰 해독 오류.");
            }
            return decodedJWT;
        } catch (JWTDecodeException decodeException) {
            return null;
        }
    }

    public static AccountContext getAccountContext(final String token) {
        DecodedJWT decodedJWT = decode(token);
        return new AccountContext(decodedJWT);
    }
}
