package Cona.App.service;

import Cona.App.utility.DataNotFoundException;
import Cona.App.domain.Notification;
import Cona.App.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> getList() {
        return this.notificationRepository.findAll();
    }

    public Notification getNotification(Integer id) {
        Optional<Notification> notification = this.notificationRepository.findById(id);
        if (notification.isPresent()) {
            return notification.get();
        } else {
            throw new DataNotFoundException("notification not found");
        }
    }

    public void create(String subject, String content) {
        Notification n = new Notification();
        n.setSubject(subject);
        n.setContent(content);
        n.setCreateDate(LocalDateTime.now());
        this.notificationRepository.save(n);
    }
}
