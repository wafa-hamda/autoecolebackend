package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.mycompany.myapp.domain.enumeration.TypeMoniteur;

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

    @Column(name = "date_debut")
    private Instant dateDebut;

    @Column(name = "duree")
    private Integer duree;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_seance")
    private TypeMoniteur typeSeance;

    @Column(name = "note")
    private String note;

    @Column(name = "point_depart")
    private String point_depart;

    @Column(name = "number_seance")
    private Integer numberSeance;

    @ManyToOne
    @JsonIgnoreProperties(value = "seances", allowSetters = true)
    private Candidat candidat;

    @ManyToOne
    @JsonIgnoreProperties(value = "seances", allowSetters = true)
    private Moniteur moniteur;

    @ManyToOne
    @JsonIgnoreProperties(value = "seances", allowSetters = true)
    private Vehicule vehicule;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public Seance dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Integer getDuree() {
        return duree;
    }

    public Seance duree(Integer duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public TypeMoniteur getTypeSeance() {
        return typeSeance;
    }

    public Seance typeSeance(TypeMoniteur typeSeance) {
        this.typeSeance = typeSeance;
        return this;
    }

    public void setTypeSeance(TypeMoniteur typeSeance) {
        this.typeSeance = typeSeance;
    }

    public String getNote() {
        return note;
    }

    public Seance note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPoint_depart() {
        return point_depart;
    }

    public Seance point_depart(String point_depart) {
        this.point_depart = point_depart;
        return this;
    }

    public void setPoint_depart(String point_depart) {
        this.point_depart = point_depart;
    }

    public Integer getNumberSeance() {
        return numberSeance;
    }

    public Seance numberSeance(Integer numberSeance) {
        this.numberSeance = numberSeance;
        return this;
    }

    public void setNumberSeance(Integer numberSeance) {
        this.numberSeance = numberSeance;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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

    // prettier-ignore
    @Override
    public String toString() {
        return "Seance{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", duree=" + getDuree() +
            ", typeSeance='" + getTypeSeance() + "'" +
            ", note='" + getNote() + "'" +
            ", point_depart='" + getPoint_depart() + "'" +
            ", numberSeance=" + getNumberSeance() +
            "}";
    }
}
