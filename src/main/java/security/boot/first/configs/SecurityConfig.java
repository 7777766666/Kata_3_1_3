package security.boot.first.configs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import security.boot.first.services.UserService;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;    //нужен чтоб вставить в daoAuthenticationProvider.setUserDetailsService(userService);

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()    //начало авторизации
                .antMatchers("/auth/**").authenticated()    //если адрес люой по /auth/**, то доступ только аутентифицированным
                .antMatchers("/user/**").hasAnyRole("ADMIN","USER")  //только с любым из прав юзер или админ
                .antMatchers("/user/**").authenticated()     //на эти страницы только аутефинцированные
                .antMatchers("/admin/**").hasAuthority("ADMIN")     //доступ только с ролью админ
                .and()  //разделитель и дальше указываем седующую область настройки
//                .httpBasic()        //бэйсик аутентификация
                .formLogin()    //стандартная форма для ввода логина
//                .loginProcessingUrl("/super") //можно указать другую страницу авторизации
//                .successHandler("куда направить ссылку, не урл") //внутрь добавить настройку, куда перенаправитть в случае успешной аудентификации
                .and()
                .logout().logoutSuccessUrl("/");    //куда перенаправлять после лог аута (корневая страница)
        //http.csrf().disable(); - если надо отключить csrf секьюрити

    }


    @Bean
    public PasswordEncoder passwordEncoder(){       //декодирует введенные пользователем данные в хэш, который в базе
        return new BCryptPasswordEncoder();         //возвращаем Хэш (из введенного пароля)
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){   //дает олтвет, существует или нет такой пользователь
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();  //
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());    //используй пасворд энкодер уже декодированный из метода выше (ХЭШ)
        daoAuthenticationProvider.setUserDetailsService(userService);

        return daoAuthenticationProvider;
    }



//InMemory
//    @Bean
//    public UserDetailsService userDetailsService(){     //создаем бин по UserDetailsService
//        UserDetails userDetails = User.builder()    //минимальная информация о пользователях спринга в секьюрити
//                .username("user")       //https://bcrypt-generator.com/  тут генерить пароли по bcrypt
//                .password("{bcrypt}$2a$12$.0M1iH9zNNbIILLCF3pfYe80HJmMbeqCFgtNPfjzr2lUxEyoMwEuC")
//                .roles("USER")      //одна или больше ролей через запятую
//                .build();       //пользователь юзер готов
//
//        UserDetails adminDatails = User.builder()
//                .username("admin")      //https://bcrypt-generator.com/  тут генерить пароли по bcrypt
//                .password("{bcrypt}$2a$12$qH/.wVKjSkudadIpq9JMXeYmHiWw8BezMXrMtKuBuHHzbLUJM4lpC")
//                .roles("USER", "ADMIN")     //одна или больше ролей через запятую
//                .build();       //пользователь админ готов
//        return new InMemoryUserDetailsManager(userDetails, adminDatails);
//    }
//JDBC Autentification
//    @Bean
//    public JdbcUserDetailsManager jdbcUserDetailsManager (DataSource dataSource){   //берем данные из базы данных
//        UserDetails userDetails = User.builder()    //минимальная информация о пользователях спринга в секьюрити
//                .username("user")       //https://bcrypt-generator.com/  тут генерить пароли по bcrypt
//                .password("{bcrypt}$2a$12$.0M1iH9zNNbIILLCF3pfYe80HJmMbeqCFgtNPfjzr2lUxEyoMwEuC")
//                .roles("USER")      //одна или больше ролей через запятую
//                .build();       //пользователь юзер готов
//
//        UserDetails adminDatails = User.builder()
//                .username("admin")      //https://bcrypt-generator.com/  тут генерить пароли по bcrypt
//                .password("{bcrypt}$2a$12$qH/.wVKjSkudadIpq9JMXeYmHiWw8BezMXrMtKuBuHHzbLUJM4lpC")
//                .roles("USER", "ADMIN")     //одна или больше ролей через запятую
//                .build();       //пользователь админ готов
//        JdbcUserDetailsManager jdbcUserDetailsManagerAll = new JdbcUserDetailsManager(dataSource);
//        if (jdbcUserDetailsManagerAll.userExists(userDetails.getUsername())){
//            jdbcUserDetailsManagerAll.deleteUser((userDetails.getUsername()));
//        }
//        if (jdbcUserDetailsManagerAll.userExists(adminDatails.getUsername())){
//            jdbcUserDetailsManagerAll.deleteUser(adminDatails.getUsername());
//        }
//        jdbcUserDetailsManagerAll.createUser(userDetails);  //создаем пользователя юзер
//        jdbcUserDetailsManagerAll.createUser(adminDatails);  //создаем пользователя Админ
//        return jdbcUserDetailsManagerAll;
//    }


}
