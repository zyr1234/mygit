package com.geovis.jg.common.exception;

import com.geovis.jg.common.domain.BaseResp;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

/**
 * @Author:Carl Lee 李洪勇
 * @Date: 2020/11/21 17:23
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public BaseResp sqlException(SQLException e){
        if (e instanceof SQLIntegrityConstraintViolationException){
            return new BaseResp(null,"该数据有关联数据，操作失败！",500);
        }
        return new BaseResp(null,"数据库异常，操作失败！",500);
    }
}
