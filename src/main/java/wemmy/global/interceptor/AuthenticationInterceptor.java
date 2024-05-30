package wemmy.global.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import wemmy.global.config.error.ErrorCode;
import wemmy.global.config.error.exception.TokenValidateException;
import wemmy.global.token.jwt.TokenProvider;
import wemmy.global.token.jwt.constant.TokenType;
import wemmy.global.token.jwt.util.AuthorizationHeaderUtils;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. Authorization Header 검증
        String authorization = request.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        // 2. 토큰 검증
        String accessToken = authorization.split(" ")[1];
        tokenProvider.validateToken(accessToken);

        // 3. 토큰 타입
        Claims tokenClaims = tokenProvider.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();
        if(!TokenType.isAccessToken(tokenType))
            // 예외 발생
            throw new TokenValidateException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        return true;
    }
}
