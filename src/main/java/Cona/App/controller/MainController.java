package Cona.App.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class MainController {
    @GetMapping("/hello")
    public String Hello(){
        return "/boards/hello";
    }
}

//8c90c9b0-9adf-44c6-a2be-3f1c14ad2076