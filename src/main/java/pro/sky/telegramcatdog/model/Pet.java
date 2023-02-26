package pro.sky.telegramcatdog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pet {

    @Id
    private long id;

    public Pet() {

    }

    public Long getId() {
        return id;
    }
}
