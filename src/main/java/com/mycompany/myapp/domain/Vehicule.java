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
 * A Vehicule.
 */
@Entity
@Table(name = "vehicule")
public class Vehicule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false)
    private String matricule;

    @Column(name = "marque")
    private String marque;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JsonIgnoreProperties("vehicules")
    private Ecole ecole;

    @ManyToMany
    @JsonIgnore
    private Set<Candidat> candidats = new HashSet<>();

    @OneToMany(mappedBy = "vehicule")
    private Set<Seance> seances = new HashSet<>();

    @OneToMany(mappedBy = "vehicule")
    private Set<Charge> charges = new HashSet<>();

    @OneToMany(mappedBy = "vehicule")
    private Set<Assurance> assurances = new HashSet<>();

    @OneToMany(mappedBy = "vehicule")
    private Set<Vignette> vignettes = new HashSet<>();

    @OneToMany(mappedBy = "vehicule")
    private Set<Entretien> entretiens = new HashSet<>();

    @ManyToMany(mappedBy = "vehicules")
    @JsonIgnore
    private Set<Moniteur> moniteurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public Vehicule matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMarque() {
        return marque;
    }

    public Vehicule marque(String marque) {
        this.marque = marque;
        return this;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getType() {
        return type;
    }

    public Vehicule type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Ecole getEcole() {
        return ecole;
    }

    public Vehicule ecole(Ecole ecole) {
        this.ecole = ecole;
        return this;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
    }

    public Set<Candidat> getCandidats() {
        return candidats;
    }

    public Vehicule candidats(Set<Candidat> candidats) {
        this.candidats = candidats;
        return this;
    }

    public Vehicule addCandidat(Candidat candidat) {
        this.candidats.add(candidat);
        candidat.getVehicules().add(this);
        return this;
    }

    public Vehicule removeCandidat(Candidat candidat) {
        this.candidats.remove(candidat);
        candidat.getVehicules().remove(this);
        return this;
    }

    public void setCandidats(Set<Candidat> candidats) {
        this.candidats = candidats;
    }

    public Set<Seance> getSeances() {
        return seances;
    }

    public Vehicule seances(Set<Seance> seances) {
        this.seances = seances;
        return this;
    }

    public Vehicule addSeance(Seance seance) {
        this.seances.add(seance);
        seance.setVehicule(this);
        return this;
    }

    public Vehicule removeSeance(Seance seance) {
        this.seances.remove(seance);
        seance.setVehicule(null);
        return this;
    }

    public void setSeances(Set<Seance> seances) {
        this.seances = seances;
    }

    public Set<Charge> getCharges() {
        return charges;
    }

    public Vehicule charges(Set<Charge> charges) {
        this.charges = charges;
        return this;
    }

    public Vehicule addCharge(Charge charge) {
        this.charges.add(charge);
        charge.setVehicule(this);
        return this;
    }

    public Vehicule removeCharge(Charge charge) {
        this.charges.remove(charge);
        charge.setVehicule(null);
        return this;
    }

    public void setCharges(Set<Charge> charges) {
        this.charges = charges;
    }

    public Set<Assurance> getAssurances() {
        return assurances;
    }

    public Vehicule assurances(Set<Assurance> assurances) {
        this.assurances = assurances;
        return this;
    }

    public Vehicule addAssurance(Assurance assurance) {
        this.assurances.add(assurance);
        assurance.setVehicule(this);
        return this;
    }

    public Vehicule removeAssurance(Assurance assurance) {
        this.assurances.remove(assurance);
        assurance.setVehicule(null);
        return this;
    }

    public void setAssurances(Set<Assurance> assurances) {
        this.assurances = assurances;
    }

    public Set<Vignette> getVignettes() {
        return vignettes;
    }

    public Vehicule vignettes(Set<Vignette> vignettes) {
        this.vignettes = vignettes;
        return this;
    }

    public Vehicule addVignette(Vignette vignette) {
        this.vignettes.add(vignette);
        vignette.setVehicule(this);
        return this;
    }

    public Vehicule removeVignette(Vignette vignette) {
        this.vignettes.remove(vignette);
        vignette.setVehicule(null);
        return this;
    }

    public void setVignettes(Set<Vignette> vignettes) {
        this.vignettes = vignettes;
    }

    public Set<Entretien> getEntretiens() {
        return entretiens;
    }

    public Vehicule entretiens(Set<Entretien> entretiens) {
        this.entretiens = entretiens;
        return this;
    }

    public Vehicule addEntretien(Entretien entretien) {
        this.entretiens.add(entretien);
        entretien.setVehicule(this);
        return this;
    }

    public Vehicule removeEntretien(Entretien entretien) {
        this.entretiens.remove(entretien);
        entretien.setVehicule(null);
        return this;
    }

    public void setEntretiens(Set<Entretien> entretiens) {
        this.entretiens = entretiens;
    }

    public Set<Moniteur> getMoniteurs() {
        return moniteurs;
    }

    public Vehicule moniteurs(Set<Moniteur> moniteurs) {
        this.moniteurs = moniteurs;
        return this;
    }

    public Vehicule addMoniteur(Moniteur moniteur) {
        this.moniteurs.add(moniteur);
        moniteur.getVehicules().add(this);
        return this;
    }

    public Vehicule removeMoniteur(Moniteur moniteur) {
        this.moniteurs.remove(moniteur);
        moniteur.getVehicules().remove(this);
        return this;
    }

    public void setMoniteurs(Set<Moniteur> moniteurs) {
        this.moniteurs = moniteurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicule)) {
            return false;
        }
        return id != null && id.equals(((Vehicule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", marque='" + getMarque() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
