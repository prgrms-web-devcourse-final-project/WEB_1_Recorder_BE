package com.revup.config;

import com.revup.jwt.filter.RevUpJwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class SecurityFilterConfiguration {

//    // WebSecurityCustomizer를 쓰기 위해서는 Servlet Filter에서도 수행되지 않아야 함.
//    // 이를 비활성화 해주는 코드
//    @Bean
//    public FilterRegistrationBean<RevUpJwtFilter> registrationBean(
//            RevUpJwtFilter revUpJwtFilter) {
//        FilterRegistrationBean<RevUpJwtFilter> registrationBean =
//                new FilterRegistrationBean<>(revUpJwtFilter);
//        registrationBean.setEnabled(false);
//        return registrationBean;
//    }
}
