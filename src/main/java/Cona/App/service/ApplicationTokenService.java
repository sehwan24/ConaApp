package Cona.App.service;

import Cona.App.controller.dto.ApplicationTokenRequestDto;
import Cona.App.controller.dto.ApplicationTokenResponseDto;
import Cona.App.domain.ApplicationToken;
import Cona.App.repository.ApplicationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationTokenService {
    private final ApplicationTokenRepository applicationTokenRepository;

    @Transactional
    public ApplicationTokenResponseDto saveToken(ApplicationTokenRequestDto applicationTokenRequestDto) {
        ApplicationToken applicationToken = applicationTokenRequestDto.toApplicationToken();
        return ApplicationTokenResponseDto.of(applicationTokenRepository.save(applicationToken));
    }
}
