package Cona.App.controller;

import Cona.App.domain.Notification;
import Cona.App.service.AnswerService;
import Cona.App.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final NotificationService notificationService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
        Notification notification = this.notificationService.getNotification(id);
        this.answerService.create(notification, content);
        return String.format("redirect:/notification/detail/%s", id);
    }

}
