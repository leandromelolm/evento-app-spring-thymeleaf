package com.eventoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FaviconController {
 
    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }
}

//https://www.baeldung.com/spring-boot-favicon