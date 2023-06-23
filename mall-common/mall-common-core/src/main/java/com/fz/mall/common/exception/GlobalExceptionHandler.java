package com.fz.mall.common.exception;


import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.common.resp.ServerResponseEntity;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = {"com.fz"})
public class GlobalExceptionHandler {


    /**
     * 全局异常处理   用于检测参数是否合法
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ServerResponseEntity<?> methodArgumentNotValidExceptionHandler(Exception e) {
        log.error("methodArgumentNotValidExceptionHandler", e);
        List<FieldError> fieldErrors = null;
        if (e instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }
        if (e instanceof BindException) {
            fieldErrors = ((BindException) e).getBindingResult().getFieldErrors();
        }
        if (fieldErrors == null) {
            return ServerResponseEntity.fail(ResponseEnum.REQUEST_PARAMETER_ERROR);
        }

//        List<String> defaultMessages = new ArrayList<>(fieldErrors.size());
//        for (FieldError fieldError : fieldErrors) {
//            defaultMessages.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
//        }
        Map<String, String> fieldErrorMap = fieldErrors.stream().collect(Collectors.toMap(FieldError::getField, fieldError -> fieldError.getDefaultMessage() == null ? "参数校验异常" : fieldError.getDefaultMessage()));
        return ServerResponseEntity.fail(ResponseEnum.REQUEST_PARAMETER_ERROR, fieldErrorMap);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({FeignException.class})
    public ServerResponseEntity feignExceptionHandler(FeignException e) {
        log.error("feignExceptionHandler", e);
        return ServerResponseEntity.fail(ResponseEnum.SERVER_INTERNAL_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ServerResponseEntity<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error("constraintViolationExceptionHandler", e);
        List<String> defaultMessages = new ArrayList<>();
        if (e != null) {
            Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : constraintViolations) {
                defaultMessages.add(violation.getPropertyPath() + ":" + violation.getMessage());
            }
        }
        return ServerResponseEntity.fail(ResponseEnum.REQUEST_PARAMETER_ERROR, defaultMessages);
    }


    /**
     * 如果是feign调用 HTTP状态码异常时 将会抛出FeignException 所以考虑对于自定义异常 状态码都为200
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler
    public ServerResponseEntity mallServerExceptionHandler(MallServerException e) {
        log.error("mallServerExceptionHandler", e);
        if (e.getMessage() != null)
            return ServerResponseEntity.fail(ResponseEnum.SERVER_INTERNAL_ERROR.getCode(), e.getMessage());
        if (e.getServerResponseEnum() != null)
            return ServerResponseEntity.fail(e.getServerResponseEnum());
        return ServerResponseEntity.fail(ResponseEnum.SERVER_INTERNAL_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ServerResponseEntity methodArgumentNotValidExceptionHandler(HttpMessageNotReadableException e) {
        log.error("methodArgumentNotValidExceptionHandler", e);
        return ServerResponseEntity.fail(ResponseEnum.REQUEST_PARAMETER_ERROR);
    }


}
