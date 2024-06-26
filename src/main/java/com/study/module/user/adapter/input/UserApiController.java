package com.study.module.user.adapter.input;


import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.domain.ExternalUserDomain;
import com.study.module.user.domain.UserDomain;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserApiController {
    private final UserFindQuery userFindQuery;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("html/login.html");
        return mv;
    }

    @PostMapping("/login")
    public String loginConfirm(HttpServletRequest request) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        URI uri = URI.create(request.getRequestURI());
        if(userId == null) {
//            throw new Exception();
        }


        log.info("userId : {}, password : {}", userId, password);
        return "html/login.html";
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> register(HttpServletRequest request) {
        return ResponseEntity.created(URI.create(request.getRequestURI())).build();
    }

    @GetMapping("/find")
    public ResponseEntity<ExternalUserDomain> findOne(@RequestParam String userId) {
        return ResponseEntity.ok(userFindQuery.findOne(userId));
    }

    @GetMapping("/findId")
    public ResponseEntity<List<String>> findUserId(@RequestParam String email) {
        return ResponseEntity.ok(userFindQuery.findUserIdByEmail(email));
    }

}
