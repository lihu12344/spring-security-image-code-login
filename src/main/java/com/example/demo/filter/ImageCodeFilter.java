package com.example.demo.filter;

import com.example.demo.util.ImageCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class ImageCodeFilter extends OncePerRequestFilter {

    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/login/image", httpServletRequest.getRequestURI())
                && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "POST")) {
            HttpSession session=httpServletRequest.getSession();
            ImageCode imageCode = (ImageCode) session.getAttribute("image_code");

            try {
                if (imageCode == null) {
                    throw new BadCredentialsException("没有申请验证码");
                }

                if (imageCode.isExpire()) {
                    throw new BadCredentialsException("验证码过期");
                }

                if (!imageCode.getCode().equals(httpServletRequest.getParameter("image_code"))) {
                    throw new BadCredentialsException("验证码出错");
                }
            }catch (AuthenticationException e){
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);

                return;
            }

            session.removeAttribute("image_code");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}