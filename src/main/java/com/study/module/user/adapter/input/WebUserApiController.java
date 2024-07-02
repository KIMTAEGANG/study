package com.study.module.user.adapter.input;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebUserApiController {
    @GetMapping("/index")
    public String main() {
        return "/html/main.html";
    }

    @GetMapping("/login")
    public String login() {
        return "/html/user/login.html";
    }

    @GetMapping("/signUp")
    public String join() {
        return "/html/user/join.html";
    }

    @GetMapping("/find/id")
    public String findId() {
        return "/html/user/findId.html";
    }

    @GetMapping("/find/password")
    public String findPassword() {
        return "/html/user/findPassword.html";
    }

}
