package com.green.greengram3.common.my;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String ip = Inet4Address.getLocalHost().getHostAddress();

        String ip = request.getRemoteHost();

        log.info("ip = {}", ip);
        if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
            return true;
        } else {
            return false;
        }
    }
}
