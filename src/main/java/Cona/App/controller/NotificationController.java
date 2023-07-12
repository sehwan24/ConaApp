package Cona.App.controller;

import Cona.App.domain.AppUser;
import Cona.App.domain.Notification;
import Cona.App.service.NotificationService;
import Cona.App.service.UserService;
import Cona.App.utility.AnswerForm;
import Cona.App.utility.NotificationForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/notification")
@RequiredArgsConstructor    //final이 붙은 속성을 포함하는 생성자 자동 생성, lombok의 @게터 세터의 메서드 생성기능과 유사함.
@Controller
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Notification> paging = this.notificationService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "notification_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Notification notification = this.notificationService.getNotification(id);
        model.addAttribute("notification", notification);
        return "notification_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String notificationCreate(NotificationForm notificationForm) {
        return "notification_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create") //GetMapping과 함수명 **매개변수의 형태가 다를 때** 동일하게 사용가능 = 메서드 오버로딩
    public String notificationCreate(@Valid NotificationForm notificationForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "notification_form";
        }
        AppUser appUser = this.userService.getUser(principal.getName());
        this.notificationService.create(notificationForm.getSubject(), notificationForm.getContent(), appUser);
        return "redirect:/notification/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String notificationModify(NotificationForm notificationForm, @PathVariable("id") Integer id, Principal principal) {
        Notification notification = this.notificationService.getNotification(id);
        if (!notification.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        notificationForm.setSubject(notification.getSubject());
        notificationForm.setContent(notification.getContent());
        return "notification_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("modify/{id}")
    public String notificationModify(@Valid NotificationForm notificationForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "notification_form";
        }
        Notification notification = this.notificationService.getNotification(id);
        if (!notification.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.notificationService.modify(notification, notificationForm.getSubject(), notificationForm.getContent());
        return String.format("redirect:/notification/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String notificationDelete(Principal principal, @PathVariable("id") Integer id) {
        Notification notification = this.notificationService.getNotification(id);
        if (!notification.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.notificationService.delete(notification);
        return "redirect:/";
    }
}
