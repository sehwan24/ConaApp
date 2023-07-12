package Cona.App.controller.dto;

import Cona.App.domain.ApplicationToken;
import groovy.beans.Bindable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationTokenRequestDto {
    private String value;

    public ApplicationToken toApplicationToken() {
        System.out.println("ApplicationToken = " + value);
        return ApplicationToken.builder()
                .value(value)
                .build();
    }
}
