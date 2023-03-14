package pro.sky.telegramcatdog.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "adoption_reports")
public class AdoptionReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private Adopter adopterId;
    private Timestamp reportDate;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet petId;
    private byte[] picture;
    private String diet;
    @Column(name = "wellbeing")
    private String wellBeing;
    private String behaviorChange;

    public AdoptionReport() {
    }

    public AdoptionReport(Long id, byte[] picture, String diet, String wellBeing, String behaviorChange) {
        this.id = id;

        this.picture = picture;
        this.diet = diet;
        this.wellBeing = wellBeing;
        this.behaviorChange = behaviorChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdoptionReport that = (AdoptionReport) o;
        return Objects.equals(adopterId, that.adopterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adopterId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Adopter getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(Adopter adopterId) {
        this.adopterId = adopterId;
    }

    public Timestamp getReportDate() {
        return reportDate;
    }

    public void setReportDate(Timestamp reportDate) {
        this.reportDate = reportDate;
    }

    public Pet getPetId() {
        return petId;
    }

    public void setPetId(Pet petId) {
        this.petId = petId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getWellBeing() {
        return wellBeing;
    }

    public void setWellBeing(String wellBeing) {
        this.wellBeing = wellBeing;
    }

    public String getBehaviorChange() {
        return behaviorChange;
    }

    public void setBehaviorChange(String behaviorChange) {
        this.behaviorChange = behaviorChange;
    }
}


