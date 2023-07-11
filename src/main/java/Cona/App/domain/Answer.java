package Cona.App.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne //없으면 에러 발생, 질문 엔터티와 연결된 속성이란 것을 명시적으로 표시해야하므로. -> 실제 db에서 FK관계 생성
    private Notification notification;

    private LocalDateTime modifyDate;

    @ManyToOne
    private AppUser author;
}
