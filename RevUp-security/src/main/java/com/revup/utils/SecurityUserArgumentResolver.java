package com.revup.utils;


import com.revup.annotation.SecurityUser;
import com.revup.auth.dto.token.TokenInfo;
import com.revup.user.UserUtil;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import com.revup.user.repository.UserRepository;
import com.revup.user.util.UserDomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class SecurityUserArgumentResolver implements HandlerMethodArgumentResolver {
    
    private final UserAdaptor userAdaptor;
    private final UserDomainUtil userUtil;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @SecurityUser 애노테이션이 붙어 있고, 엔티티 타입이 User인지 확인
        return parameter.getParameterAnnotation(SecurityUser.class) != null
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof TokenInfo tokenInfo) {
            Long id = tokenInfo.id();
            return userAdaptor.findById(id);
        }
        return null;
    }
}
