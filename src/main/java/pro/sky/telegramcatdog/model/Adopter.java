package pro.sky.telegramcatdog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Adopter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first_name;
    private String last_name;
    private String passport;
    private int age;
    private String phone1;
    private String phone2;
    private String telegram;
    private int volunteer_id;
    private boolean on_probation;
    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adopter)) return false;
        Adopter adopter = (Adopter) o;
        return getAge() == adopter.getAge() && getVolunteer_id() == adopter.getVolunteer_id() && isOn_probation() == adopter.isOn_probation() && isActive() == adopter.isActive() && Objects.equals(getId(), adopter.getId()) && Objects.equals(getFirst_name(), adopter.getFirst_name()) && Objects.equals(getLast_name(), adopter.getLast_name()) && Objects.equals(getPassport(), adopter.getPassport()) && Objects.equals(getPhone1(), adopter.getPhone1()) && Objects.equals(getPhone2(), adopter.getPhone2()) && Objects.equals(getTelegram(), adopter.getTelegram());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirst_name(), getLast_name(), getPassport(), getAge(), getPhone1(), getPhone2(), getTelegram(), getVolunteer_id(), isOn_probation(), isActive());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public int getVolunteer_id() {
        return volunteer_id;
    }

    public void setVolunteer_id(int volunteer_id) {
        this.volunteer_id = volunteer_id;
    }

    public boolean isOn_probation() {
        return on_probation;
    }

    public void setOn_probation(boolean on_probation) {
        this.on_probation = on_probation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
