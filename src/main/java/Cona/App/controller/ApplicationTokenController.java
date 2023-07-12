package Cona.App.controller;

import Cona.App.controller.dto.ApplicationTokenRequestDto;
import Cona.App.controller.dto.ApplicationTokenResponseDto;
import Cona.App.domain.ApplicationToken;
import Cona.App.service.ApplicationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/application")
public class ApplicationTokenController {
    private final ApplicationTokenService applicationTokenService;

    @PostMapping("/token")
    public ResponseEntity<ApplicationTokenResponseDto> saveToken(@RequestBody ApplicationTokenRequestDto applicationTokenRequestDto) {
        return ResponseEntity.ok(applicationTokenService.saveToken(applicationTokenRequestDto));
    }
}
