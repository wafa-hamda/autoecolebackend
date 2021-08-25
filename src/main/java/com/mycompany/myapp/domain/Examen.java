package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.mycompany.myapp.domain.enumeration.Resultat;

import com.mycompany.myapp.domain.enumeration.TypeMoniteur;

/**
 * A Examen.
 */
@Entity
@Table(name = "examen")
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "resultat")
    private Resultat resultat;

    @Column(name = "date_examen")
    private Instant dateExamen;

    @Column(name = "point_depart")
    private String pointDepart;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_examen")
    private TypeMoniteur typeExamen;

    @ManyToOne
    @JsonIgnoreProperties(value = "examen", allowSetters = true)
    private Candidat candidat;

    @ManyToOne
    @JsonIgnoreProperties(value = "examen", allowSetters = true)
    private Moniteur moniteur;

    @ManyToOne
    @JsonIgnoreProperties(value = "examen", allowSetters = true)
    private Vehicule vehicule;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Examen description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Resultat getResultat() {
        return resultat;
    }

    public Examen resultat(Resultat resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(Resultat resultat) {
        this.resultat = resultat;
    }

    public Instant getDateExamen() {
        return dateExamen;
    }

    public Examen dateExamen(Instant dateExamen) {
        this.dateExamen = dateExamen;
        return this;
    }

    public void setDateExamen(Instant dateExamen) {
        this.dateExamen = dateExamen;
    }

    public String getPointDepart() {
        return pointDepart;
    }

    public Examen pointDepart(String pointDepart) {
        this.pointDepart = pointDepart;
        return this;
    }

    public void setPointDepart(String pointDepart) {
        this.pointDepart = pointDepart;
    }

    public TypeMoniteur getTypeExamen() {
        return typeExamen;
    }

    public Examen typeExamen(TypeMoniteur typeExamen) {
        this.typeExamen = typeExamen;
        return this;
    }

    public void setTypeExamen(TypeMoniteur typeExamen) {
        this.typeExamen = typeExamen;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Examen candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Moniteur getMoniteur() {
        return moniteur;
    }

    public Examen moniteur(Moniteur moniteur) {
        this.moniteur = moniteur;
        return this;
    }

    public void setMoniteur(Moniteur moniteur) {
        this.moniteur = moniteur;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public Examen vehicule(Vehicule vehicule) {
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
        if (!(o instanceof Examen)) {
            return false;
        }
        return id != null && id.equals(((Examen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Examen{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", resultat='" + getResultat() + "'" +
            ", dateExamen='" + getDateExamen() + "'" +
            ", pointDepart='" + getPointDepart() + "'" +
            ", typeExamen='" + getTypeExamen() + "'" +
            "}";
    }
}
