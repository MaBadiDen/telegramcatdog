package pro.sky.telegramcatdog.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "adoption_docs")
public class AdoptionDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "short_desc")
    private String shortDesc;
    private String description;

    public AdoptionDoc() {
    }

    public AdoptionDoc(int id, String shortDesc, String description) {
        this.id = id;
        this.shortDesc = shortDesc;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdoptionDoc that = (AdoptionDoc) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
