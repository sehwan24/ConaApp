package Cona.App.controller.dto;

import Cona.App.domain.ApplicationToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.AnyKeyJavaClass;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationTokenResponseDto {
    private String value;

    public static ApplicationTokenResponseDto of(ApplicationToken applicationToken) {
        return new ApplicationTokenResponseDto(applicationToken.getValue());
    }
}
