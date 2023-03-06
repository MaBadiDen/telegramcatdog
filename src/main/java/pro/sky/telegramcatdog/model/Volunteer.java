package pro.sky.telegramcatdog.model;

import jakarta.persistence.*;

import java.util.Objects;

import static pro.sky.telegramcatdog.constants.Constants.VOLUNTEER_CHAT_ID;

@Entity
@Table(name = "volunteers")
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "telegram")
    private String telegram;
    private byte[] picture;
    public Volunteer() {
    }

    public Volunteer(long id, String name, String telegram, byte[] picture) {
        this.id = id;
        this.name = name;
        this.telegram = telegram;
        this.picture = picture;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public long getTelegramChatId() {
        return VOLUNTEER_CHAT_ID;
    }
}
