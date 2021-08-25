package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.myapp.domain.enumeration.TypeMoniteur;

/**
 * A Moniteur.
 */
@Entity
@Table(name = "moniteur")
public class Moniteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(name = "cin", nullable = false)
    private String cin;

    @Column(name = "date_naissance")
    private LocalDate date_naissance;

    @Column(name = "num_tel")
    private String num_tel;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeMoniteur type;

    @Column(name = "salaire_heure")
    private Double salaire_heure;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToMany
    @JoinTable(name = "moniteur_vehicule",
               joinColumns = @JoinColumn(name = "moniteur_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "vehicule_id", referencedColumnName = "id"))
    private Set<Vehicule> vehicules = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "moniteur_candidat",
               joinColumns = @JoinColumn(name = "moniteur_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "candidat_id", referencedColumnName = "id"))
    private Set<Candidat> candidats = new HashSet<>();

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

    public Moniteur cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public Moniteur date_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
        return this;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getNum_tel() {
        return num_tel;
    }

    public Moniteur num_tel(String num_tel) {
        this.num_tel = num_tel;
        return this;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public TypeMoniteur getType() {
        return type;
    }

    public Moniteur type(TypeMoniteur type) {
        this.type = type;
        return this;
    }

    public void setType(TypeMoniteur type) {
        this.type = type;
    }

    public Double getSalaire_heure() {
        return salaire_heure;
    }

    public Moniteur salaire_heure(Double salaire_heure) {
        this.salaire_heure = salaire_heure;
        return this;
    }

    public void setSalaire_heure(Double salaire_heure) {
        this.salaire_heure = salaire_heure;
    }

    public User getUser() {
        return user;
    }

    public Moniteur user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public Moniteur vehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
        return this;
    }

    public Moniteur addVehicule(Vehicule vehicule) {
        this.vehicules.add(vehicule);
        vehicule.getMoniteurs().add(this);
        return this;
    }

    public Moniteur removeVehicule(Vehicule vehicule) {
        this.vehicules.remove(vehicule);
        vehicule.getMoniteurs().remove(this);
        return this;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Set<Candidat> getCandidats() {
        return candidats;
    }

    public Moniteur candidats(Set<Candidat> candidats) {
        this.candidats = candidats;
        return this;
    }

    public Moniteur addCandidat(Candidat candidat) {
        this.candidats.add(candidat);
        candidat.getMoniteurs().add(this);
        return this;
    }

    public Moniteur removeCandidat(Candidat candidat) {
        this.candidats.remove(candidat);
        candidat.getMoniteurs().remove(this);
        return this;
    }

    public void setCandidats(Set<Candidat> candidats) {
        this.candidats = candidats;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Moniteur)) {
            return false;
        }
        return id != null && id.equals(((Moniteur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Moniteur{" +
            "id=" + getId() +
            ", cin='" + getCin() + "'" +
            ", date_naissance='" + getDate_naissance() + "'" +
            ", num_tel='" + getNum_tel() + "'" +
            ", type='" + getType() + "'" +
            ", salaire_heure=" + getSalaire_heure() +
            "}";
    }
}
