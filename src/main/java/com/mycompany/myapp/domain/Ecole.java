package com.mycompany.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;

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

    @Column(name = "description")
    private String description;

    @Column(name = "email")
    private String email;

    @Column(name = "num_tel")
    private String numTel;

    @Column(name = "url_face_book")
    private String urlFaceBook;

    @Column(name = "url_instagram")
    private String urlInstagram;

    @Column(name = "url_youtube")
    private String urlYoutube;

    // jhipster-needle-entity-add-field - JHipster will add fields here
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

    public String getDescription() {
        return description;
    }

    public Ecole description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public Ecole email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTel() {
        return numTel;
    }

    public Ecole numTel(String numTel) {
        this.numTel = numTel;
        return this;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getUrlFaceBook() {
        return urlFaceBook;
    }

    public Ecole urlFaceBook(String urlFaceBook) {
        this.urlFaceBook = urlFaceBook;
        return this;
    }

    public void setUrlFaceBook(String urlFaceBook) {
        this.urlFaceBook = urlFaceBook;
    }

    public String getUrlInstagram() {
        return urlInstagram;
    }

    public Ecole urlInstagram(String urlInstagram) {
        this.urlInstagram = urlInstagram;
        return this;
    }

    public void setUrlInstagram(String urlInstagram) {
        this.urlInstagram = urlInstagram;
    }

    public String getUrlYoutube() {
        return urlYoutube;
    }

    public Ecole urlYoutube(String urlYoutube) {
        this.urlYoutube = urlYoutube;
        return this;
    }

    public void setUrlYoutube(String urlYoutube) {
        this.urlYoutube = urlYoutube;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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

    // prettier-ignore
    @Override
    public String toString() {
        return "Ecole{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", description='" + getDescription() + "'" +
            ", email='" + getEmail() + "'" +
            ", numTel='" + getNumTel() + "'" +
            ", urlFaceBook='" + getUrlFaceBook() + "'" +
            ", urlInstagram='" + getUrlInstagram() + "'" +
            ", urlYoutube='" + getUrlYoutube() + "'" +
            "}";
    }
}
