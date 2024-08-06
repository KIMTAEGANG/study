package com.study.module.user.application.service;

import com.study.module.user.application.port.input.QrCodeUseCase;
import com.study.module.user.domain.QrResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class QrCodeService implements QrCodeUseCase {
    @Override
    public SseEmitter newSseEmitterForRedisChannel(String sessionId) {
        return null;
    }

    @Override
    public QrResult pingCheck(String sessionId) {
        return null;
    }

    @Override
    public QrResult sendTokenToSseEmitter(String sessionId, String token) {
        return null;
    }
}
