package com.study.module.user.adapter.input;


import com.study.module.user.application.port.input.MailSendUseCase;
import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.application.port.input.UserRegisterUseCase;
import com.study.module.user.application.port.input.command.MailSendCommand;
import com.study.module.user.application.port.input.command.UserRegisterCommand;
import com.study.module.user.domain.ExternalUserDomain;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserApiController {
    private final UserFindQuery userFindQuery;
    private final UserRegisterUseCase userRegisterUseCase;
    private final MailSendUseCase mailSendUseCase;

    @GetMapping("/index")
    public String main() {
        return "/html/main.html";
    }

    @GetMapping("/login")
    public String login() {
        return "/html/login.html";
    }

    @GetMapping("/signUp")
    public String join() {
        return "/html/join.html";
    }

    @PostMapping("/signUp")
    public ResponseEntity<Void> register(HttpServletRequest request, @RequestBody UserRegisterCommand command) {

        userRegisterUseCase.save(command);
        return ResponseEntity.created(URI.create(request.getRequestURI())).build();
    }

    @PostMapping("/signUp/send-mail")
    public String sendMail(@RequestBody MailSendCommand command) {
        return mailSendUseCase.sendAuthCode(command.email());
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
