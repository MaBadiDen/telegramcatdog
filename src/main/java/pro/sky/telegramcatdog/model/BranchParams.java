package pro.sky.telegramcatdog.model;

import jakarta.persistence.*;
import pro.sky.telegramcatdog.constants.PetType;

import java.util.Objects;

@Entity
public class BranchParams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    @Column(name = "security_info")
    private String securityInfo;

    @Column(name = "security_contact")
    private String securityContact;

    @Column(name = "pet_type")
    private PetType petType;

    public BranchParams(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public BranchParams() {

    }

    public int getId() {
        return id;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getSecurityInfo() {
        return securityInfo;
    }

    public String getSecurityContact() {
        return securityContact;
    }

    public PetType getPetType() {
        return petType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchParams that = (BranchParams) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
