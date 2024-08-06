package com.study.module.user.adapter.input;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
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
