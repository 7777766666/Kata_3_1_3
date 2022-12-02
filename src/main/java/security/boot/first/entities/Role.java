package security.boot.first.entities;


import org.springframework.lang.NonNull;

import javax.persistence.*;


@Entity //сущности
@Table(name = "roles")  //название таблицы в БД
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Integer id;

    @NonNull
    private String name;

    public Role(){

    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
