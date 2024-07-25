package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.annotation.LogNameOfRunningMethod;

@RestController
@RequestMapping("/api/v1/info")
@Tag(name = "info", description = "Предоставляет различную служебную информацию")
@LogNameOfRunningMethod
public class InfoController {

    private final ServletWebServerApplicationContext webServerAppCtxt;

    public InfoController(ServletWebServerApplicationContext webServerAppCtxt) {
        this.webServerAppCtxt = webServerAppCtxt;
    }

    //Я не уверен могу ли я считать это задание выполненным.
    //Технически у меня есть 2 конфигурационных файла.
    //И эндпоинт вернет любое значение порта...
    @GetMapping("/port")
    public String getPort() {
        return "Ваше приложение работает на следующем порту: " + webServerAppCtxt.getWebServer().getPort();
    }

    @GetMapping("/strange-number")
    public Long getStrangeNumber() {
        return 1000000L * (1000000 + 1) / 2;
    }

}
