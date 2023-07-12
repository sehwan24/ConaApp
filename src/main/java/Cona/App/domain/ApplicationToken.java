package Cona.App.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "application_token")
@Entity
public class ApplicationToken {
    @Id @GeneratedValue
    @Column(name = "token_key")
    private Long key;

    @Column(name = "token_value")
    private String value;    //token_value

    @Builder
    public ApplicationToken(String value) {
        this.value = value;
    }
}
