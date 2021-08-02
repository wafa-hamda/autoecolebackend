package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
public class Formation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private String dateDebut;

    @Column(name = "date_fin")
    private String dateFin;

    @Column(name = "nbr_heure_code")
    private Integer nbrHeureCode;

    @Column(name = "prixheure_code")
    private Integer prixheureCode;

    @Column(name = "nbr_heure_conduit")
    private Integer nbrHeureConduit;

    @Column(name = "prix_heure_conduit")
    private Integer prixHeureConduit;

    @Column(name = "disponobilte")
    private String disponobilte;

    @OneToOne
    @JoinColumn(unique = true)
    private Candidat candidat;

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

    public Formation dateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public Formation dateFin(String dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getNbrHeureCode() {
        return nbrHeureCode;
    }

    public Formation nbrHeureCode(Integer nbrHeureCode) {
        this.nbrHeureCode = nbrHeureCode;
        return this;
    }

    public void setNbrHeureCode(Integer nbrHeureCode) {
        this.nbrHeureCode = nbrHeureCode;
    }

    public Integer getPrixheureCode() {
        return prixheureCode;
    }

    public Formation prixheureCode(Integer prixheureCode) {
        this.prixheureCode = prixheureCode;
        return this;
    }

    public void setPrixheureCode(Integer prixheureCode) {
        this.prixheureCode = prixheureCode;
    }

    public Integer getNbrHeureConduit() {
        return nbrHeureConduit;
    }

    public Formation nbrHeureConduit(Integer nbrHeureConduit) {
        this.nbrHeureConduit = nbrHeureConduit;
        return this;
    }

    public void setNbrHeureConduit(Integer nbrHeureConduit) {
        this.nbrHeureConduit = nbrHeureConduit;
    }

    public Integer getPrixHeureConduit() {
        return prixHeureConduit;
    }

    public Formation prixHeureConduit(Integer prixHeureConduit) {
        this.prixHeureConduit = prixHeureConduit;
        return this;
    }

    public void setPrixHeureConduit(Integer prixHeureConduit) {
        this.prixHeureConduit = prixHeureConduit;
    }

    public String getDisponobilte() {
        return disponobilte;
    }

    public Formation disponobilte(String disponobilte) {
        this.disponobilte = disponobilte;
        return this;
    }

    public void setDisponobilte(String disponobilte) {
        this.disponobilte = disponobilte;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Formation candidat(Candidat candidat) {
        this.candidat = candidat;
        return this;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formation)) {
            return false;
        }
        return id != null && id.equals(((Formation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Formation{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", nbrHeureCode=" + getNbrHeureCode() +
            ", prixheureCode=" + getPrixheureCode() +
            ", nbrHeureConduit=" + getNbrHeureConduit() +
            ", prixHeureConduit=" + getPrixHeureConduit() +
            ", disponobilte='" + getDisponobilte() + "'" +
            "}";
    }
}
