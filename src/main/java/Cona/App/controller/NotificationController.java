package Cona.App.controller;

import Cona.App.domain.Notification;
import Cona.App.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/notification")
@RequiredArgsConstructor    //final이 붙은 속성을 포함하는 생성자 자동 생성, lombok의 @게터 세터의 메서드 생성기능과 유사함.
@Controller
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Notification> notificationList = this.notificationService.getList();
        model.addAttribute("notificationList", notificationList);
        //Model 객체는 자바클래스와 템플릿 간의 연결고리 역할. Model 객체에 값을 담아두면 템플릿에서 그 값을 사용할 수 있음.
        return "notification_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Notification notification = this.notificationService.getNotification(id);
        model.addAttribute("notification", notification);
        return "notification_detail";
    }

    @GetMapping("/create")
    public String notificationCreate() {
        return "notification_form";
    }

    @PostMapping("/create") //GetMapping과 함수명 **매개변수의 형태가 다를 때** 동일하게 사용가능 = 메서드 오버로딩
    public String notificationCreate(@RequestParam String subject, @RequestParam String content) {
        this.notificationService.create(subject, content);
        return "redirect:/notification/list";
    }


}
