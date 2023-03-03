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
    private int id;
    private String telegram_id;
    private Timestamp last_visit;
    private int last_menu;

    public Guest() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelegram_id() {
        return telegram_id;
    }

    public void setTelegram_id(String telegram_id) {
        this.telegram_id = telegram_id;
    }

    public Timestamp getLast_visit() {
        return last_visit;
    }

    public void setLast_visit(Timestamp last_visit) {
        this.last_visit = last_visit;
    }

    public int getLast_menu() {
        return last_menu;
    }

    public void setLast_menu(int last_menu) {
        this.last_menu = last_menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return getId() == guest.getId() && getLast_menu() == guest.getLast_menu() && Objects.equals(getTelegram_id(), guest.getTelegram_id()) && Objects.equals(getLast_visit(), guest.getLast_visit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTelegram_id(), getLast_visit(), getLast_menu());
    }
}
