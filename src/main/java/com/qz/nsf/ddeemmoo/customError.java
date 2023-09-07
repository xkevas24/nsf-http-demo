package com.qz.nsf.ddeemmoo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("${server.error.path:${error.path:/error}}")
public class customError {
    @GetMapping
    public String handleError(HttpServletRequest request) {
        // 获取请求的状态码
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // 获取请求的 URL
                String requestUrl = (String)request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
                // 处理 404 错误页面
                return "error/404";
            }
        }
        // 处理其他错误页面
        return "error/error";
    }
}
