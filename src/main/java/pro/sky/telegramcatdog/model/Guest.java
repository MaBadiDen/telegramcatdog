package pro.sky.telegramcatdog.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String telegramId;
    private Timestamp lastVisit;
    private int lastMenu;

    public Guest() {

    }

    public Guest( String telegramId, Timestamp lastVisit, int lastMenu) {

        this.telegramId = telegramId;
        this.lastVisit = lastVisit;
        this.lastMenu = lastMenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return getId() == guest.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }

    public int getLastMenu() {
        return lastMenu;
    }

    public void setLastMenu(int lastMenu) {
        this.lastMenu = lastMenu;
    }
}
