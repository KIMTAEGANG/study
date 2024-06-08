package com.study.common.exception;
public class NotFoundUserException extends ClientException {
    public NotFoundUserException(){super();};

    public NotFoundUserException(String userId){super("사용자 아이디(" + userId + ")가 없습니다.");}
}
