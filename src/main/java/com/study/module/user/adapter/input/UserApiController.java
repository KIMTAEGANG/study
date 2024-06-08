package com.study.module.user.adapter.input;


import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.domain.ExternalUserDomain;
import com.study.module.user.domain.UserDomain;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserApiController {
    private final UserFindQuery userFindQuery;

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
