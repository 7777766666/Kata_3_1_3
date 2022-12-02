package security.boot.first.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/")    //гетмаппинг в корень приложения
    public String homePage(){
        return "home";
    }

    @GetMapping("/auth")
    public String pageForAufUsers(Principal principal){    //Принципал = сжатая информация о текущем пользователя на входе, хранящаяяся в контексте спринг секьюрити
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  // аунтефикация через получения данных из сукьюрити контекст холдера
        return "authenticate  success!!! " + principal.getName() ;  //приветствуем + геттер с именем пользователя
    }
}
