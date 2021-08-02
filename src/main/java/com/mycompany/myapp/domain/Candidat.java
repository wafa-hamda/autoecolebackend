package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cin", nullable = false)
    private String cin;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "situation")
    private String situation;

    @Column(name = "age")
    private Integer age;

    @ManyToOne
    @JsonIgnoreProperties("candidats")
    private Ecole ecole;

    @OneToOne
    @JoinColumn(unique = true)
    private Payement payement;

    @OneToOne
    @JoinColumn(unique = true)
    private Formation formation;

    @OneToMany(mappedBy = "candidat")
    private Set<Examen> examen = new HashSet<>();

    @ManyToMany(mappedBy = "candidats")
    @JsonIgnore
    private Set<Moniteur> moniteurs = new HashSet<>();

    @ManyToMany(mappedBy = "candidats")
    @JsonIgnore
    private Set<Vehicule> vehicules = new HashSet<>();

    @OneToMany(mappedBy = "candidat")
    private Set<Seance> seances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public String getNom() {
        return nom;
    }

    public Candidat nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Candidat prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public Candidat telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getSituation() {
        return situation;
    }

    public Candidat situation(String situation) {
        this.situation = situation;
        return this;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Integer getAge() {
        return age;
    }

    public Candidat age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Ecole getEcole() {
        return ecole;
    }

    public Candidat ecole(Ecole ecole) {
        this.ecole = ecole;
        return this;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
    }

    public Payement getPayement() {
        return payement;
    }

    public Candidat payement(Payement payement) {
        this.payement = payement;
        return this;
    }

    public void setPayement(Payement payement) {
        this.payement = payement;
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

    public Set<Examen> getExamen() {
        return examen;
    }

    public Candidat examen(Set<Examen> examen) {
        this.examen = examen;
        return this;
    }

    public Candidat addExamen(Examen examen) {
        this.examen.add(examen);
        examen.setCandidat(this);
        return this;
    }

    public Candidat removeExamen(Examen examen) {
        this.examen.remove(examen);
        examen.setCandidat(null);
        return this;
    }

    public void setExamen(Set<Examen> examen) {
        this.examen = examen;
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

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public Candidat vehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
        return this;
    }

    public Candidat addVehicule(Vehicule vehicule) {
        this.vehicules.add(vehicule);
        vehicule.getCandidats().add(this);
        return this;
    }

    public Candidat removeVehicule(Vehicule vehicule) {
        this.vehicules.remove(vehicule);
        vehicule.getCandidats().remove(this);
        return this;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public Set<Seance> getSeances() {
        return seances;
    }

    public Candidat seances(Set<Seance> seances) {
        this.seances = seances;
        return this;
    }

    public Candidat addSeance(Seance seance) {
        this.seances.add(seance);
        seance.setCandidat(this);
        return this;
    }

    public Candidat removeSeance(Seance seance) {
        this.seances.remove(seance);
        seance.setCandidat(null);
        return this;
    }

    public void setSeances(Set<Seance> seances) {
        this.seances = seances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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

    @Override
    public String toString() {
        return "Candidat{" +
            "id=" + getId() +
            ", cin='" + getCin() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", situation='" + getSituation() + "'" +
            ", age=" + getAge() +
            "}";
    }
}
