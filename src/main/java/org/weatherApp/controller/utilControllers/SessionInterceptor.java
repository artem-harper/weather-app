package org.weatherApp.controller.utilControllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Controller
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("NO SESSION COOKIE FOUND");
        if (request.getCookies() == null){
            response.sendRedirect("/sign-in");
            return false;
        }
        return true;
    }
}
