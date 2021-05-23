package br.com.mj.resource;

import br.com.mj.domain.request.CaptureRequest;
import br.com.mj.service.PlayerDecisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captures")
@RequiredArgsConstructor
@Slf4j
public class CaptureResource {

    private final PlayerDecisionService playerDecisionService;

    @PostMapping
    public ResponseEntity<?> capture(@RequestBody CaptureRequest captureRequest) {
        try {
            return ResponseEntity.ok(playerDecisionService.findPlayer(captureRequest));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
