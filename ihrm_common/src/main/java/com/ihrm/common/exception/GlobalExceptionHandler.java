package com.ihrm.common.exception;

import com.ihrm.common.entity.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常ApiException处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = ApiException.class)
    public CommonResult apiExceptionHandler(ApiException e) {
        log.error("apiExceptionHandler ",e);
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }
    /**
     * 自定义异常ImageCodeException处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = ImageCodeException.class)
    public CommonResult imageCodeException(ImageCodeException e) {
        e.printStackTrace();
//        ResultModel resultModel = new ResultModel(2,e.getMessage());
//        CommonResult commonResult=new CommonResult()
//        return new ResponseEntity<>(resultModel,HttpStatus.OK);
        if (e.getCode() != null) {
            return CommonResult.failed(e.getCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 自定义注解@FlagValidator异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public CommonResult bindExceptionHandler(BindException e) {
        log.error("bindExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return createValidExceptionResp(errors);
    }


    /**
     * 处理validation 框架异常
     *
     * @param e
     * @return com.yingxue.lesson.utils.DataResult<T>
     * @throws
     * @UpdateUser:
     * @Version: 0.0.1
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    <T> CommonResult<T> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return createValidExceptionResp(errors);
    }

    private <T> CommonResult<T> createValidExceptionResp(List<ObjectError> errors) {
        String[] msgs = new String[errors.size()];
        int i = 0;
        for (ObjectError error : errors) {
            msgs[i] = error.getDefaultMessage();
            log.info("msg={}", msgs[i]);
            i++;
        }
        return CommonResult.validateFailed(msgs[0]);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    <T> CommonResult<T> constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> set = e.getConstraintViolations();
        StringBuffer sb = new StringBuffer();
        for (ConstraintViolation s : set) {
            sb.append(s.getMessageTemplate());
            sb.append(",");
        }
        //去掉最后一个","
        String msg = sb.toString().substring(0, sb.length() - 1);
        return CommonResult.validateFailed(msg);
    }
}
