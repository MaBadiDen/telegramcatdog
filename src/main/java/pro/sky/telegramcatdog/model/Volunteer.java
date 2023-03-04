package pro.sky.telegramcatdog.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "volunteers")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "telegram_chat_id")
    private long telegramChatId;
    @Column(name = "telegram_username")
    private String telegramUsername;
    private byte[] picture;

    public Volunteer(int id, String name, long telegramChatId, String telegramUsername) {
        this.id = id;
        this.name = name;
        this.telegramChatId = telegramChatId;
        this.telegramUsername = telegramUsername;
    }

    public Volunteer() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getTelegramChatId() {
        return telegramChatId;
    }

    public String getTelegramUsername() {
        return telegramUsername;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTelegramChatId(long telegramChatId) {
        this.telegramChatId = telegramChatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return id == volunteer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
