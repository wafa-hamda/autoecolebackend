package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Moniteur.
 */
@Entity
@Table(name = "moniteur")
public class Moniteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cin", nullable = false)
    private String cin;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "type")
    private String type;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "datenais")
    private String datenais;

    @ManyToOne
    @JsonIgnoreProperties("moniteurs")
    private Ecole ecole;

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

    @OneToMany(mappedBy = "moniteur")
    private Set<Examen> examen = new HashSet<>();

    @OneToMany(mappedBy = "moniteur")
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

    public Moniteur cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getLogin() {
        return login;
    }

    public Moniteur login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public Moniteur password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public Moniteur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Moniteur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public Moniteur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getType() {
        return type;
    }

    public Moniteur type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdresse() {
        return adresse;
    }

    public Moniteur adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDatenais() {
        return datenais;
    }

    public Moniteur datenais(String datenais) {
        this.datenais = datenais;
        return this;
    }

    public void setDatenais(String datenais) {
        this.datenais = datenais;
    }

    public Ecole getEcole() {
        return ecole;
    }

    public Moniteur ecole(Ecole ecole) {
        this.ecole = ecole;
        return this;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
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

    public Set<Examen> getExamen() {
        return examen;
    }

    public Moniteur examen(Set<Examen> examen) {
        this.examen = examen;
        return this;
    }

    public Moniteur addExamen(Examen examen) {
        this.examen.add(examen);
        examen.setMoniteur(this);
        return this;
    }

    public Moniteur removeExamen(Examen examen) {
        this.examen.remove(examen);
        examen.setMoniteur(null);
        return this;
    }

    public void setExamen(Set<Examen> examen) {
        this.examen = examen;
    }

    public Set<Seance> getSeances() {
        return seances;
    }

    public Moniteur seances(Set<Seance> seances) {
        this.seances = seances;
        return this;
    }

    public Moniteur addSeance(Seance seance) {
        this.seances.add(seance);
        seance.setMoniteur(this);
        return this;
    }

    public Moniteur removeSeance(Seance seance) {
        this.seances.remove(seance);
        seance.setMoniteur(null);
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
        if (!(o instanceof Moniteur)) {
            return false;
        }
        return id != null && id.equals(((Moniteur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Moniteur{" +
            "id=" + getId() +
            ", cin='" + getCin() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", type='" + getType() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", datenais='" + getDatenais() + "'" +
            "}";
    }
}
