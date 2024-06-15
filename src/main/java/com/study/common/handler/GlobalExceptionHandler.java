package com.study.common.handler;

import com.study.common.handler.exception.ClientException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, ServerException.class})
    protected ResponseEntity<Map<String, Object>> error500(HttpServletRequest request, Exception e) {
        return errorResponse(request, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Map<String, Object>> error400RequestBody(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<String> errList = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errList.add(field + " : " + message);
        });
        return errorResponse(request, errList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ClientException.class, MissingServletRequestParameterException.class})
    protected ResponseEntity<Map<String, Object>> error400(HttpServletRequest request, Exception e) {
        return errorResponse(request, e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, Object>> errorResponse(HttpServletRequest request, Exception e, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", status);
        map.put("error", status.getReasonPhrase());
        map.put("path", request.getRequestURI());
        map.put("message", e.getMessage());
        return ResponseEntity.status(status.value()).body(map);
    }

    private ResponseEntity<Map<String, Object>> errorResponse(HttpServletRequest request, Object message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status", status);
        map.put("error", status.getReasonPhrase());
        map.put("path", request.getRequestURI());
        map.put("message", message);
        return ResponseEntity.status(status.value()).body(map);
    }
}
