package security.boot.first.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import security.boot.first.entities.Role;
import security.boot.first.entities.User;
import security.boot.first.repositories.UsersRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {    //задача по имени пользователя предоставить самого юзера

    private UsersRepository usersRepository;    //доступ к репозиторию юзеров

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }
    public User findByUsername(String username){        //делаем обёртку над методом репозитория, чтоб напрямую не обращаться
        return usersRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {   //альт+инсер и переписываем метод сервиса, который по имени пользователя из базы вернёт нам самого юзера
        User user = findByUsername(username); //пробуем достать пользователя из базы данных по пришедшему имени
        if (user == null){      //если в базе не нашли этого юзера
            throw new UsernameNotFoundException(String.format("User '%s' not found ", username));   //исключение и пишем, что не найдет пользователь
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRolesCollection()));
        //возвращаем Юзера (не нашего, а спринг ворк секьюрити, у которого пользователь(наш юзер.гетНэйм,наш юзер гет пароль и берем коллекцию ролей и оттуда вытаскиваем роль нашего юзера
    }

    //метод, который из коллекции ролей получает коллекцию прав доступа для loadUserByUsername (выше)
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roleCollection){
        return roleCollection.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        //роли в стрим, приводим их к нью гранд авторити(каждую роль с именем). на вход коллекция ролей, на выходе коллекция из имен с такими же строками

    }

}
