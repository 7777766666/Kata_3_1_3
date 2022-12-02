package security.boot.first.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.boot.first.entities.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

}
