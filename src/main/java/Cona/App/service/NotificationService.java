package Cona.App.service;

import Cona.App.domain.AppUser;
import Cona.App.utility.DataNotFoundException;
import Cona.App.domain.Notification;
import Cona.App.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Page<Notification> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.notificationRepository.findAll(pageable);
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
}
