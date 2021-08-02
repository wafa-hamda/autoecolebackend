package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

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

    @NotNull
    @Column(name = "date", nullable = false)
    private String date;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "result")
    private String result;

    @Column(name = "remarque")
    private String remarque;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Candidat candidat;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Moniteur moniteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public Examen date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public Examen type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public Examen result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemarque() {
        return remarque;
    }

    public Examen remarque(String remarque) {
        this.remarque = remarque;
        return this;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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

    @Override
    public String toString() {
        return "Examen{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            ", result='" + getResult() + "'" +
            ", remarque='" + getRemarque() + "'" +
            "}";
    }
}
