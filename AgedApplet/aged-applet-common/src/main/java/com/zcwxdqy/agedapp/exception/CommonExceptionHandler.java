package com.zcwxdqy.agedapp.exception;

import com.zcwxdqy.agedapp.pojo.vo.result.JsonVo;
import com.zcwxdqy.agedapp.util.JsonVos;
import com.zcwxdqy.agedapp.util.Streams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    // 拦截所有异常，判断是什么异常
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public JsonVo handle(Throwable t) {
       // 先打印日志
        log.error("handle", t);

        // 一些可以直接处理的异常
        if (t instanceof CommonException) {
            return handle((CommonException) t);
        } else if (t instanceof BindException) {
            return handle((BindException) t);
        } else if (t instanceof ConstraintViolationException) {
            return handle((ConstraintViolationException) t);
        } else if (t instanceof MethodArgumentNotValidException) {
            return handle((MethodArgumentNotValidException) t);
        } else if (t instanceof ServletException) {

        }

        // 处理cause异常（导致产生t的异常）
        Throwable cause = t.getCause();
        if (cause != null) {
            return handle(cause);
        }

        // 其他异常（没有cause的异常）
        return JsonVos.error();
    }

    // 处理一般性异常CommonException：返回前台的是错误码和异常信息
    private JsonVo handle(CommonException ce) {
        return JsonVos.error(ce.getCode(), ce.getMessage());
    }

    // 处理后端验证框架的异常BindException和ConstraintViolationException：返回前台的是错误码和异常信息
    private JsonVo handle(BindException be) {
        List<ObjectError> errors = be.getBindingResult().getAllErrors();
        /*
        1、函数式编程的方式：stream -> 【将后一类型的集合，转换成前一类型的集合】
        2、lambda表达式的简化方法引用 ObjectError::getDefaultMessage
        3、返回的是将错误信息拼接装在一个数组里
        4、StringUtils：spring boot的工具类，将数组里的元素用逗号拼接成字符串
         */
        List<String> defaultMsgs = Streams.map(errors, ObjectError::getDefaultMessage);
        String msg = StringUtils.collectionToDelimitedString(defaultMsgs, ", ");
        return JsonVos.error(msg);
    }
    private JsonVo handle(ConstraintViolationException cve) {
        List<String> msgs = Streams.map(cve.getConstraintViolations(), ConstraintViolation::getMessage);
        String msg = StringUtils.collectionToDelimitedString(msgs, ", ");
        return JsonVos.error(msg);
    }
    private JsonVo handle(MethodArgumentNotValidException mae) {
        List<String> msgs = Streams.map(mae.getBindingResult().getAllErrors(), ObjectError::getDefaultMessage);
        String msg = StringUtils.collectionToDelimitedString(msgs, ", ");
        return JsonVos.error(msg);
    }


}
