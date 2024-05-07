package com.study.module.user.adapter.input;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {

    @PostMapping("/signUp")
    public ResponseEntity<Void> register(HttpServletRequest request) {
        return ResponseEntity.created(URI.create(request.getRequestURI())).build();
    }

}
