package br.com.pradolabs.springboot.thymeleafdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class DemoController {

    //  create a mapping for "/hello"
    @GetMapping("/hello")
    public String sayHello(Model model) {
        // add data to the model
        model.addAttribute("theDate", LocalDateTime.now());

        // return the name of the view
        return "helloworld";
    }
}
