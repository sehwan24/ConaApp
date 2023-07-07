package Cona.App.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    //응답 결과가 여러 건일 경우에는 메서드 리턴 타입을 Notification이 아닌 List<notification>으로 해야함.
    Notification findBySubject(String subject);

    Notification findBySubjectAndContent(String subject, String content);

    List<Notification> findBySubjectLike(String subject);

}
