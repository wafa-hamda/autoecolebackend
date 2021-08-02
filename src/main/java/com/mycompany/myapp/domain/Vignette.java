package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Vignette.
 */
@Entity
@Table(name = "vignette")
public class Vignette implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_debut")
    private String dateDebut;

    @Column(name = "date_fin")
    private String dateFin;

    @Column(name = "prix")
    private Integer prix;

    @ManyToOne
    @JsonIgnoreProperties("vignettes")
    private Vehicule vehicule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public Vignette dateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public Vignette dateFin(String dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getPrix() {
        return prix;
    }

    public Vignette prix(Integer prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public Vignette vehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
        return this;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vignette)) {
            return false;
        }
        return id != null && id.equals(((Vignette) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vignette{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
