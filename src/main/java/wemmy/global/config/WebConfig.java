package wemmy.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wemmy.global.interceptor.AdminAuthenticationInterceptor;
import wemmy.global.interceptor.AuthenticationInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    // accessToken 검증
    private final AuthenticationInterceptor authenticationInterceptor;
    private final AdminAuthenticationInterceptor adminAuthenticationInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)  // accessToken 검증 우선
                .order(1)
                .addPathPatterns("/wemmy/**")
                .excludePathPatterns(
                        "/wemmy/user/sign-up",
                        "/wemmy/user/validate/**",
                        "/wemmy/user/login",
                        "/wemmy/access-token/reissue",
                        "/wemmy/user/oauth/**"
                );

        registry.addInterceptor(adminAuthenticationInterceptor)
                .order(2)
                .addPathPatterns("/wemmy/admin/**");
    }
}
