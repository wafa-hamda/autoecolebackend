package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Seance.
 */
@Entity
@Table(name = "seance")
public class Seance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_heure_prevu")
    private String dateHeurePrevu;

    @Column(name = "date_heure_reel")
    private String dateHeureReel;

    @Column(name = "nbr_heure")
    private Integer nbrHeure;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JsonIgnoreProperties("seances")
    private Moniteur moniteur;

    @ManyToOne
    @JsonIgnoreProperties("seances")
    private Candidat candidat;

    @ManyToOne
    @JsonIgnoreProperties("seances")
    private Vehicule vehicule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateHeurePrevu() {
        return dateHeurePrevu;
    }

    public Seance dateHeurePrevu(String dateHeurePrevu) {
        this.dateHeurePrevu = dateHeurePrevu;
        return this;
    }

    public void setDateHeurePrevu(String dateHeurePrevu) {
        this.dateHeurePrevu = dateHeurePrevu;
    }

    public String getDateHeureReel() {
        return dateHeureReel;
    }

    public Seance dateHeureReel(String dateHeureReel) {
        this.dateHeureReel = dateHeureReel;
        return this;
    }

    public void setDateHeureReel(String dateHeureReel) {
        this.dateHeureReel = dateHeureReel;
    }

    public Integer getNbrHeure() {
        return nbrHeure;
    }

    public Seance nbrHeure(Integer nbrHeure) {
        this.nbrHeure = nbrHeure;
        return this;
    }

    public void setNbrHeure(Integer nbrHeure) {
        this.nbrHeure = nbrHeure;
    }

    public String getType() {
        return type;
    }

    public Seance type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Moniteur getMoniteur() {
        return moniteur;
    }

    public Seance moniteur(Moniteur moniteur) {
        this.moniteur = moniteur;
        return this;
    }

    public void setMoniteur(Moniteur moniteur) {
        this.moniteur = moniteur;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Seance candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public Seance vehicule(Vehicule vehicule) {
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
        if (!(o instanceof Seance)) {
            return false;
        }
        return id != null && id.equals(((Seance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Seance{" +
            "id=" + getId() +
            ", dateHeurePrevu='" + getDateHeurePrevu() + "'" +
            ", dateHeureReel='" + getDateHeureReel() + "'" +
            ", nbrHeure=" + getNbrHeure() +
            ", type='" + getType() + "'" +
            "}";
    }
}
