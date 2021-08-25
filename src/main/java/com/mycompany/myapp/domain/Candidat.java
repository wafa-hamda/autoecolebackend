package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Candidat.
 */
@Entity
@Table(name = "candidat")
public class Candidat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(name = "cin", nullable = false)
    private String cin;

    @Column(name = "date_naisssance")
    private LocalDate dateNaisssance;

    @Column(name = "num_tel")
    private String num_tel;

    @Column(name = "adresse")
    private String adresse;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Formation formation;

    @ManyToMany(mappedBy = "candidats")
    @JsonIgnore
    private Set<Moniteur> moniteurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public Candidat cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getDateNaisssance() {
        return dateNaisssance;
    }

    public Candidat dateNaisssance(LocalDate dateNaisssance) {
        this.dateNaisssance = dateNaisssance;
        return this;
    }

    public void setDateNaisssance(LocalDate dateNaisssance) {
        this.dateNaisssance = dateNaisssance;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public Candidat num_tel(String num_tel) {
        this.num_tel = num_tel;
        return this;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public Candidat adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public User getUser() {
        return user;
    }

    public Candidat user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Formation getFormation() {
        return formation;
    }

    public Candidat formation(Formation formation) {
        this.formation = formation;
        return this;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Set<Moniteur> getMoniteurs() {
        return moniteurs;
    }

    public Candidat moniteurs(Set<Moniteur> moniteurs) {
        this.moniteurs = moniteurs;
        return this;
    }

    public Candidat addMoniteur(Moniteur moniteur) {
        this.moniteurs.add(moniteur);
        moniteur.getCandidats().add(this);
        return this;
    }

    public Candidat removeMoniteur(Moniteur moniteur) {
        this.moniteurs.remove(moniteur);
        moniteur.getCandidats().remove(this);
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
        if (!(o instanceof Candidat)) {
            return false;
        }
        return id != null && id.equals(((Candidat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidat{" +
            "id=" + getId() +
            ", cin='" + getCin() + "'" +
            ", dateNaisssance='" + getDateNaisssance() + "'" +
            ", num_tel='" + getNum_tel() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
