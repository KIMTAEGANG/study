package com.study.module.user.adapter.input;


import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.domain.UserDomain;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserApiController {
    private final UserFindQuery userFindQuery;

    @PostMapping("/signUp")
    public ResponseEntity<Void> register(HttpServletRequest request) {
        return ResponseEntity.created(URI.create(request.getRequestURI())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDomain> findOne(@PathVariable String userId) {
        return ResponseEntity.ok(userFindQuery.findOne(userId));
    }

}
