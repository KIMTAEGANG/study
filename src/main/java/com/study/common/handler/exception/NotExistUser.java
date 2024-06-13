package com.study.common.handler.exception;

public class NotExistUser extends ClientException{
    public NotExistUser() {super("회원 정보가 존재 하지 않습니다.");}
}
