package com.study.module.user.adapter.output;

import com.study.module.user.application.port.output.JwtModifyPort;
import com.study.module.user.application.port.output.JwtRegisterPort;
import org.springframework.stereotype.Component;

@Component
public class JwtPersistenceAdapter implements JwtRegisterPort, JwtModifyPort {
}
