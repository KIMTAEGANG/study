package com.study.module.web.adapter.input;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebApiController {

    @GetMapping("/index")
    public String main(Model model) {
        return "html/main.html";
    }
}
