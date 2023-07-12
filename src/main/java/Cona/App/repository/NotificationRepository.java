package Cona.App.repository;

import Cona.App.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    //응답 결과가 여러 건일 경우에는 메서드 리턴 타입을 Notification이 아닌 List<notification>으로 해야함.
    Notification findBySubject(String subject);

    Notification findBySubjectAndContent(String subject, String content);

    List<Notification> findBySubjectLike(String subject);

    Page<Notification> findAll(Pageable pageable);

    Page<Notification> findAll(Specification<Notification> spec, Pageable pageable);

    //쿼리 사용 시 테이블 기준이 아닌 엔티티 기준으로 appUser -> AppUser, n.author_id=u1.id -> n.author=u1
    @Query("select "
            + "distinct n "
            + "from Notification n "
            + "left outer join AppUser u1 on n.author=u1 "
            + "left outer join Answer a on a.notification=n "
            + "left outer join AppUser u2 on a.author=u2 "
            + "where "
            + "   n.subject like %:kw% "
            + "   or n.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<Notification> findAllByKeyword(@Param("kw") String kw, Pageable pageable); //@Query에 전달할 문자열은 @Param 어노테이션 사용. 쿼리 안에서는 :kw로 참조됨
}
