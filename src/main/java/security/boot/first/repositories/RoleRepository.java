package security.boot.first.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.boot.first.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
