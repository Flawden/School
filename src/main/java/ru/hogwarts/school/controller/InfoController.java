package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/info")
public class InfoController {

    private final ServletWebServerApplicationContext webServerAppCtxt;

    public InfoController(ServletWebServerApplicationContext webServerAppCtxt) {
        this.webServerAppCtxt = webServerAppCtxt;
    }

    @GetMapping("/getPort")
    public String getPort() {
        return "Ваше приложение работает на следующем порту: " + webServerAppCtxt.getWebServer().getPort();
    }

}
