package pro.sky.telegramcatdog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class ShelterParams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int branch;
    private String name;
    private String country;
    private String city;
    private String zip;
    private String address;
    private String workHours;
    private byte[] map;
    private String info;
    private int probPeriod;
    private int probExtend;

    public ShelterParams(int branch, String name) {
        this.branch = branch;
        this.name = name;
    }

    public ShelterParams() {

    }

    public int getBranch() {
        return branch;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkHours() {
        return workHours;
    }

    public byte[] getMap() {
        return map;
    }

    public String getInfo() {
        return info;
    }

    public int getProbPeriod() {
        return probPeriod;
    }

    public int getProbExtend() {
        return probExtend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelterParams that = (ShelterParams) o;
        return branch == that.branch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(branch);
    }
}
