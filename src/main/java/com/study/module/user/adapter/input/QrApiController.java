package com.study.module.user.adapter.input;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.study.module.user.application.port.input.QrCodeUseCase;
import com.study.module.user.domain.QrResult;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.ByteArrayOutputStream;

@RestController
@RequiredArgsConstructor
public class QrApiController {
    private final QrCodeUseCase qrCodeUseCase;

    @GetMapping("/api/v1/qr")
    public ResponseEntity<byte[]> create() throws WriterException {
        int width = 200;
        int height = 200;
        String url = "https://localhost:8080";

        BitMatrix encode = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(encode, "PNG", out);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(out.toByteArray());
        } catch (Exception e) {

        }
        return null;
    }

    @GetMapping("/api/v1/qr-req")
    public ResponseEntity<SseEmitter> qrCodeReq(@RequestParam String sessionId) {
        return ResponseEntity.ok(qrCodeUseCase.newSseEmitterForRedisChannel(sessionId));
    }

    @GetMapping("/api/v1/qr-req/ping")
    public ResponseEntity<QrResult> ping(@RequestParam String sessionId) {
        return ResponseEntity.ok(qrCodeUseCase.pingCheck(sessionId));
    }

    @GetMapping("/api/v1/qr-res")
    public QrResult qrCodeRes(@RequestParam String sessionId,
                              @RequestHeader(value = "Authorization", required = false, defaultValue = "") String token) {
        return qrCodeUseCase.sendTokenToSseEmitter(sessionId, token.substring(7));
    }

}
