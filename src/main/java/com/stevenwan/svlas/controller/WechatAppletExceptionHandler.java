package com.stevenwan.svlas.controller;

import com.stevenwan.svlas.common.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-04 10:52
 */

@Slf4j
@ControllerAdvice(basePackages = {"com.stevenwan.svlas.controller"})
public class WechatAppletExceptionHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return o;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultData exception(Exception e) {
        log.error("错误信息", e);
        return ResultData.error(e.getMessage());
    }
}
