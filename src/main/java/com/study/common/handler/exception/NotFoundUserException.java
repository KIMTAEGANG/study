package com.study.common.handler.exception;


public class NotFoundUserException extends ClientException {
    public NotFoundUserException(){super();};

    public NotFoundUserException(String userId){super("사용자 아이디(" + userId + ")가 없습니다.");}
}
