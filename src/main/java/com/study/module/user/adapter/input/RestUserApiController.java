package com.study.module.user.adapter.input;

import com.study.module.auth.domain.JwtToken;
import com.study.module.user.application.port.input.MailSendUseCase;
import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.application.port.input.UserRegisterUseCase;
import com.study.module.user.application.port.input.command.MailSendCommand;
import com.study.module.user.application.port.input.command.UserLoginCommand;
import com.study.module.user.application.port.input.command.UserRegisterCommand;
import com.study.module.user.domain.ExternalUserDomain;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestUserApiController {

    private final MailSendUseCase mailSendUseCase;
    private final UserRegisterUseCase userRegisterUseCase;
    private final UserFindQuery userFindQuery;

    @PostMapping("/signUp")
    public ResponseEntity<Void> register(HttpServletRequest request, @RequestBody UserRegisterCommand command) {

        userRegisterUseCase.save(command);
        return ResponseEntity.created(URI.create(request.getRequestURI())).build();
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtToken> signIn(@RequestBody UserLoginCommand command) {
        return ResponseEntity.ok(userFindQuery.findLoginIinfo(command));
    }
    @PostMapping("test")
    public String test() {
        return "success";
    }

    @PostMapping("/signUp/send-mail")
    public ResponseEntity<String> sendMail(@RequestBody MailSendCommand command) {
        return ResponseEntity.ok(mailSendUseCase.sendAuthCode(command.email()));
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
