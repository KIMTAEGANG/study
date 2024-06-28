package com.study.common.handler.exception;

public class LoginCheckFailException extends ClientException{
    public LoginCheckFailException() {super("비밀번호가 일치하지 않습니다.");};
}
