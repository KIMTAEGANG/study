package com.study.module.user.application.port.input;

import com.study.module.user.domain.QrResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface QrCodeUseCase {
    SseEmitter newSseEmitterForRedisChannel(String sessionId);
    QrResult pingCheck(String sessionId);
    QrResult sendTokenToSseEmitter(String sessionId, String token);

}
