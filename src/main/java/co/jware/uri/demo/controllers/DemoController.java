package co.jware.uri.demo.controllers;


import co.jware.uri.demo.annotation.ApiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ApiController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public String handle() {
        return "demo";
    }
}
