package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Payement.
 */
@Entity
@Table(name = "payement")
public class Payement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "total")
    private Integer total;

    @Column(name = "reste")
    private Integer reste;

    @Column(name = "vendu")
    private Integer vendu;

    @Column(name = "remarque")
    private String remarque;

    @OneToOne(mappedBy = "payement")
    @JsonIgnore
    private Candidat candidat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public Payement total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getReste() {
        return reste;
    }

    public Payement reste(Integer reste) {
        this.reste = reste;
        return this;
    }

    public void setReste(Integer reste) {
        this.reste = reste;
    }

    public Integer getVendu() {
        return vendu;
    }

    public Payement vendu(Integer vendu) {
        this.vendu = vendu;
        return this;
    }

    public void setVendu(Integer vendu) {
        this.vendu = vendu;
    }

    public String getRemarque() {
        return remarque;
    }

    public Payement remarque(String remarque) {
        this.remarque = remarque;
        return this;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public Payement candidat(Candidat candidat) {
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
        if (!(o instanceof Payement)) {
            return false;
        }
        return id != null && id.equals(((Payement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Payement{" +
            "id=" + getId() +
            ", total=" + getTotal() +
            ", reste=" + getReste() +
            ", vendu=" + getVendu() +
            ", remarque='" + getRemarque() + "'" +
            "}";
    }
}
