package Cona.App;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/sbb")
    @ResponseBody //URL 요청에 대한 응답으로 문자열을 리턴하라는 의미
    public String index() {
        return "스프링으로 새롭게 시작해보자";
    }
}

//8c90c9b0-9adf-44c6-a2be-3f1c14ad2076