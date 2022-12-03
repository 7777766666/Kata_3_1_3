package security.boot.first.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    @NotNull(message = "username Empty")
    private String username;

    @Column(name = "password")
    @NotNull(message = "password Empty")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "age")
    @NotNull
    private Integer age;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roleCollection;

    public Integer getId() {
        return id;
    }

    public User(){

    }

    public User(Integer id, String username, String password, String name, String lastName, Integer age, Collection<Role> roleCollection) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.roleCollection = roleCollection;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Collection<Role> getRolesCollection() {
        return roleCollection;
    }

    public void setRolesCollection(Collection<Role> roleCollection) {
        this.roleCollection = roleCollection;
    }
}
