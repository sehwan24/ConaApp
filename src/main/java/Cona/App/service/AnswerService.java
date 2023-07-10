package Cona.App.service;

import Cona.App.domain.Answer;
import Cona.App.domain.Notification;
import Cona.App.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Notification notification, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setNotification(notification);
        this.answerRepository.save(answer);
    }
}
