package Cona.App.service;

import Cona.App.domain.Answer;
import Cona.App.domain.AppUser;
import Cona.App.utility.DataNotFoundException;
import Cona.App.domain.Notification;
import Cona.App.repository.NotificationRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private Specification<Notification> search(String kw) {
        return new Specification<>() {
            @Serial
            private static final long serialVersionUID =1L;
            @Override
            public Predicate toPredicate(Root<Notification> n, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); //중복 제거
                Join<Notification, AppUser> u1 = n.join("author", JoinType.LEFT);
                Join<Notification, Answer> a = n.join("answerList", JoinType.LEFT);
                Join<Answer, AppUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(n.get("subject"), "%" + kw + "%"), //제목
                        cb.like(n.get("content"), "%" + kw + "%"),                  //내용
                        cb.like(u1.get("username"), "%" + kw + "%"),                //질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),                  //답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));               //답변 작성자
            }
        };
    }

    public Page<Notification> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Notification> spec = search(kw);
        return this.notificationRepository.findAll(spec, pageable);
//        return this.notificationRepository.findAllByKeyword(kw, pageable); 쿼리 직접 작성
    }


    public Notification getNotification(Integer id) {
        Optional<Notification> notification = this.notificationRepository.findById(id);
        if (notification.isPresent()) {
            return notification.get();
        } else {
            throw new DataNotFoundException("notification not found");
        }
    }

    public void create(String subject, String content, AppUser user) {
        Notification n = new Notification();
        n.setSubject(subject);
        n.setContent(content);
        n.setCreateDate(LocalDateTime.now());
        n.setAuthor(user);
        this.notificationRepository.save(n);
    }

    public void modify(Notification notification, String subject, String content) {
        notification.setSubject(subject);
        notification.setContent(content);
        notification.setModifyDate(LocalDateTime.now());
        this.notificationRepository.save(notification);
    }

    public void delete(Notification notification) {
        this.notificationRepository.delete(notification);
    }
}
