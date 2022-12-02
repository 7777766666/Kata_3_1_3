package security.boot.first.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import security.boot.first.entities.User;
import security.boot.first.services.UserService;

import java.security.Principal;

@RestController
public class MainController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {   //для получения экземпляра юзера, так как юзер не равен принципалу в методе pageForAufUsers (ниже)
        this.userService = userService;
    }

    @GetMapping("/")    //гетмаппинг в корень приложения
    public String homePage(){
        return "home page for All!!!";
    }

    @GetMapping("/auth")
    public String pageForAufUsers(Principal principal){    //Принципал = сжатая информация о текущем пользователя на входе, хранящаяяся в контексте спринг секьюрити
        User user = userService.findByUsername(principal.getName());    //получаем из юзерсервиса юзера, по входящему принципалу, вызвав на нем гетНэйм
         return "authenticate  success!!! " +user.getUsername() + ": твой крипто хэш пароль 12 уровня   " + user.getPassword();  //приветствуем + геттерами можем вывести абсолютно любую информацию о юзере, даже хэш пароль
    }

    //Данным способом нельзя получить из принципала юзера(принципал не каститься к юзеру). Поэтому переписываем метод на тот, который выше
//    @GetMapping("/auth")
//    public String pageForAufUsers(Principal principal){    //Принципал = сжатая информация о текущем пользователя на входе, хранящаяяся в контексте спринг секьюрити
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  // аунтефикация через получения данных из сукьюрити контекст холдера
//        return "authenticate  success!!! " + principal.getName() ;  //приветствуем + геттер с именем пользователя
//    }
}
