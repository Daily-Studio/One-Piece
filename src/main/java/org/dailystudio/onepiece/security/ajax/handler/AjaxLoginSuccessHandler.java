package org.dailystudio.onepiece.security.ajax.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dailystudio.onepiece.dto.jwt.JwtResDto;
import org.dailystudio.onepiece.security.context.AccountContext;
import org.dailystudio.onepiece.security.jwt.JwtFactory;
import org.dailystudio.onepiece.security.jwt.JwtInfo;
import org.dailystudio.onepiece.security.token.PostAuthorizationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AjaxLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory jwtFactory;
    private final ObjectMapper objectMapper;

    // 정상적으로 인증이 이루어 졌다면 auth 는 PostAuthorizationToken이라고 생각하면된다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req,
                                        HttpServletResponse res,
                                        Authentication auth) throws IOException, ServletException {
        PostAuthorizationToken token = (PostAuthorizationToken) auth;
        AccountContext context = (AccountContext) token.getPrincipal();
        JwtResDto jwtResDto = new JwtResDto(jwtFactory.create(context));

        processResponse(res, jwtResDto);

        log.info("[request end] -> {}", req.getRequestURI());
    }

    public void processResponse(HttpServletResponse res, final JwtResDto jwt) throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        res.setStatus(HttpStatus.CREATED.value());
        res.setHeader(JwtInfo.HEADER_NAME, jwt.getJwt());
        res.setCharacterEncoding("UTF-8");

//        res.getWriter().print(objectMapper.writeValueAsString(jwt));
    }
}
