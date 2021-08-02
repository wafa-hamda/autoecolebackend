package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Ecole.
 */
@Entity
@Table(name = "ecole")
public class Ecole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "date_creation")
    private String dateCreation;

    @Column(name = "tarifs")
    private String tarifs;

    @OneToMany(mappedBy = "ecole")
    private Set<Moniteur> moniteurs = new HashSet<>();

    @OneToMany(mappedBy = "ecole")
    private Set<Candidat> candidats = new HashSet<>();

    @OneToMany(mappedBy = "ecole")
    private Set<Vehicule> vehicules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Ecole nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Ecole adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public Ecole dateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTarifs() {
        return tarifs;
    }

    public Ecole tarifs(String tarifs) {
        this.tarifs = tarifs;
        return this;
    }

    public void setTarifs(String tarifs) {
        this.tarifs = tarifs;
    }

    public Set<Moniteur> getMoniteurs() {
        return moniteurs;
    }

    public Ecole moniteurs(Set<Moniteur> moniteurs) {
        this.moniteurs = moniteurs;
        return this;
    }

    public Ecole addMoniteur(Moniteur moniteur) {
        this.moniteurs.add(moniteur);
        moniteur.setEcole(this);
        return this;
    }

    public Ecole removeMoniteur(Moniteur moniteur) {
        this.moniteurs.remove(moniteur);
        moniteur.setEcole(null);
        return this;
    }

    public void setMoniteurs(Set<Moniteur> moniteurs) {
        this.moniteurs = moniteurs;
    }

    public Set<Candidat> getCandidats() {
        return candidats;
    }

    public Ecole candidats(Set<Candidat> candidats) {
        this.candidats = candidats;
        return this;
    }

    public Ecole addCandidat(Candidat candidat) {
        this.candidats.add(candidat);
        candidat.setEcole(this);
        return this;
    }

    public Ecole removeCandidat(Candidat candidat) {
        this.candidats.remove(candidat);
        candidat.setEcole(null);
        return this;
    }

    public void setCandidats(Set<Candidat> candidats) {
        this.candidats = candidats;
    }

    public Set<Vehicule> getVehicules() {
        return vehicules;
    }

    public Ecole vehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
        return this;
    }

    public Ecole addVehicule(Vehicule vehicule) {
        this.vehicules.add(vehicule);
        vehicule.setEcole(this);
        return this;
    }

    public Ecole removeVehicule(Vehicule vehicule) {
        this.vehicules.remove(vehicule);
        vehicule.setEcole(null);
        return this;
    }

    public void setVehicules(Set<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ecole)) {
            return false;
        }
        return id != null && id.equals(((Ecole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ecole{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", tarifs='" + getTarifs() + "'" +
            "}";
    }
}
