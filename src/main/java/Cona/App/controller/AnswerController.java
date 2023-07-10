package Cona.App.controller;

import Cona.App.domain.Notification;
import Cona.App.service.AnswerService;
import Cona.App.service.NotificationService;
import Cona.App.utility.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final NotificationService notificationService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult) {
        Notification notification = this.notificationService.getNotification(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("notification", notification);
            return "notification_detail";
        }
        this.answerService.create(notification, answerForm.getContent());
        return String.format("redirect:/notification/detail/%s", id);
    }

}
