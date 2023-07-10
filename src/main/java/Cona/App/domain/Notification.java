package Cona.App.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter // 추후에 @Builder로 변경하여 대대적인 수정이 필요할 수도 있음. 데이터를 자유롭게 변경 가능한 Setter는 안전하지 않다고 판단됨.
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    //질문-답변 1:N 관계이므로 공지 엔티티의 답변 속성은 List형태임.
    @OneToMany(mappedBy = "notification", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}
