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

    @GetMapping("/")
    public String root() {
        return "redirect:/notification/list";
        //redirect: 클라요청 URL에 대한 응답으로 다른 URL로 다시 요청하라고 응답하는것. 클라에서 리다이렉션 인지 가능. 클라 요청에 의해 서버의 DB에 변화가 생기는 작업에 사용. ex)db의 유저 테이블 변경 = 회원가입
        //forward: 위와 다르게 서버 내부에서 다른 URL로 포워딩 후 클라로 응답. 클라의 요청정보 변함없음. 특정 URL에 대해 외부에 공개되지 말아야 할 부분을 숨기고 조회하는데 사용.
    }
}

//8c90c9b0-9adf-44c6-a2be-3f1c14ad2076