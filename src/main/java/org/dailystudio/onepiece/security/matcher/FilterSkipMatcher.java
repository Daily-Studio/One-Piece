package org.dailystudio.onepiece.security.matcher;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 적용하지 않을 주소와
 * 적용할 주소를 받아야한다.
 */
public class FilterSkipMatcher implements RequestMatcher {

    private OrRequestMatcher orRequestMatcher;
    private RequestMatcher processingMatcher;

    public FilterSkipMatcher(List<String> pathToSkip, String processingPath) {
        this.orRequestMatcher = new OrRequestMatcher(getAntPathRequestMatchers(pathToSkip));
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest req) {
        return isProcessPath(req);
    }

    private boolean isProcessPath(HttpServletRequest req) {
        return !orRequestMatcher.matches(req) && processingMatcher.matches(req);
    }

    private List<RequestMatcher> getAntPathRequestMatchers(List<String> pathToSkip) {
        return pathToSkip.stream()
                .map(eachPath -> new AntPathRequestMatcher(eachPath))
                .collect(Collectors.toList());
    }
}
