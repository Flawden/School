package ru.hogwarts.school.controller;

import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/info")
public class InfoController {

    private final ServletWebServerApplicationContext webServerAppCtxt;

    public InfoController(ServletWebServerApplicationContext webServerAppCtxt) {
        this.webServerAppCtxt = webServerAppCtxt;
    }

    //Я не уверен могу ли я считать это задание выполненным.
    //Технически у меня есть 2 конфигурационных файла.
    //И эндпоинт вернет любое значение порта...
    @GetMapping("/getPort")
    public String getPort() {
        return "Ваше приложение работает на следующем порту: " + webServerAppCtxt.getWebServer().getPort();
    }

    @GetMapping("/getStrangeNumber")
    public Integer getStrangeNumber() {
        return Stream.iterate(1, a -> a +1).limit(1_000_000).parallel().reduce(0, Integer::sum);
    }

}
