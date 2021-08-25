package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vehicule.
 */
@Entity
@Table(name = "vehicule")
public class Vehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false)
    private String matricule;

    @Column(name = "marque")
    private String marque;

    @Column(name = "age")
    private Integer age;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "image_url")
    private String image_Url;

    @ManyToMany(mappedBy = "vehicules")
    @JsonIgnore
    private Set<Moniteur> moniteurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public Vehicule matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMarque() {
        return marque;
    }

    public Vehicule marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Integer getAge() {
        return age;
    }

    public Vehicule age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public Vehicule description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public Vehicule color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage_Url() {
        return image_Url;
    }

    public Vehicule image_Url(String image_Url) {
        this.image_Url = image_Url;
        return this;
    }

    public void setImage_Url(String image_Url) {
        this.image_Url = image_Url;
    }

    public Set<Moniteur> getMoniteurs() {
        return moniteurs;
    }

    public Vehicule moniteurs(Set<Moniteur> moniteurs) {
        this.moniteurs = moniteurs;
        return this;
    }

    public Vehicule addMoniteur(Moniteur moniteur) {
        this.moniteurs.add(moniteur);
        moniteur.getVehicules().add(this);
        return this;
    }

    public Vehicule removeMoniteur(Moniteur moniteur) {
        this.moniteurs.remove(moniteur);
        moniteur.getVehicules().remove(this);
        return this;
    }

    public void setMoniteurs(Set<Moniteur> moniteurs) {
        this.moniteurs = moniteurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicule)) {
            return false;
        }
        return id != null && id.equals(((Vehicule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicule{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", marque='" + getMarque() + "'" +
            ", age=" + getAge() +
            ", description='" + getDescription() + "'" +
            ", color='" + getColor() + "'" +
            ", image_Url='" + getImage_Url() + "'" +
            "}";
    }
}
