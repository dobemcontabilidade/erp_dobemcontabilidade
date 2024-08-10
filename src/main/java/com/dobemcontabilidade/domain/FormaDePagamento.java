package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FormaDePagamento.
 */
@Entity
@Table(name = "forma_de_pagamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormaDePagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "forma")
    private String forma;

    @Column(name = "disponivel")
    private Boolean disponivel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formaDePagamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "calculoPlanoAssinaturas", "pagamentos", "periodoPagamento", "formaDePagamento", "planoContabil", "empresa" },
        allowSetters = true
    )
    private Set<AssinaturaEmpresa> assinaturaEmpresas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FormaDePagamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForma() {
        return this.forma;
    }

    public FormaDePagamento forma(String forma) {
        this.setForma(forma);
        return this;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public Boolean getDisponivel() {
        return this.disponivel;
    }

    public FormaDePagamento disponivel(Boolean disponivel) {
        this.setDisponivel(disponivel);
        return this;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Set<AssinaturaEmpresa> getAssinaturaEmpresas() {
        return this.assinaturaEmpresas;
    }

    public void setAssinaturaEmpresas(Set<AssinaturaEmpresa> assinaturaEmpresas) {
        if (this.assinaturaEmpresas != null) {
            this.assinaturaEmpresas.forEach(i -> i.setFormaDePagamento(null));
        }
        if (assinaturaEmpresas != null) {
            assinaturaEmpresas.forEach(i -> i.setFormaDePagamento(this));
        }
        this.assinaturaEmpresas = assinaturaEmpresas;
    }

    public FormaDePagamento assinaturaEmpresas(Set<AssinaturaEmpresa> assinaturaEmpresas) {
        this.setAssinaturaEmpresas(assinaturaEmpresas);
        return this;
    }

    public FormaDePagamento addAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresas.add(assinaturaEmpresa);
        assinaturaEmpresa.setFormaDePagamento(this);
        return this;
    }

    public FormaDePagamento removeAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresas.remove(assinaturaEmpresa);
        assinaturaEmpresa.setFormaDePagamento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormaDePagamento)) {
            return false;
        }
        return getId() != null && getId().equals(((FormaDePagamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormaDePagamento{" +
            "id=" + getId() +
            ", forma='" + getForma() + "'" +
            ", disponivel='" + getDisponivel() + "'" +
            "}";
    }
}
