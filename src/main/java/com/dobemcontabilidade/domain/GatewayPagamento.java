package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GatewayPagamento.
 */
@Entity
@Table(name = "gateway_pagamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GatewayPagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Size(max = 200)
    @Column(name = "descricao", length = 200)
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gatewayPagamento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assinaturaEmpresa", "gatewayPagamento" }, allowSetters = true)
    private Set<GatewayAssinaturaEmpresa> gatewayAssinaturaEmpresas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GatewayPagamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public GatewayPagamento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public GatewayPagamento descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<GatewayAssinaturaEmpresa> getGatewayAssinaturaEmpresas() {
        return this.gatewayAssinaturaEmpresas;
    }

    public void setGatewayAssinaturaEmpresas(Set<GatewayAssinaturaEmpresa> gatewayAssinaturaEmpresas) {
        if (this.gatewayAssinaturaEmpresas != null) {
            this.gatewayAssinaturaEmpresas.forEach(i -> i.setGatewayPagamento(null));
        }
        if (gatewayAssinaturaEmpresas != null) {
            gatewayAssinaturaEmpresas.forEach(i -> i.setGatewayPagamento(this));
        }
        this.gatewayAssinaturaEmpresas = gatewayAssinaturaEmpresas;
    }

    public GatewayPagamento gatewayAssinaturaEmpresas(Set<GatewayAssinaturaEmpresa> gatewayAssinaturaEmpresas) {
        this.setGatewayAssinaturaEmpresas(gatewayAssinaturaEmpresas);
        return this;
    }

    public GatewayPagamento addGatewayAssinaturaEmpresa(GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa) {
        this.gatewayAssinaturaEmpresas.add(gatewayAssinaturaEmpresa);
        gatewayAssinaturaEmpresa.setGatewayPagamento(this);
        return this;
    }

    public GatewayPagamento removeGatewayAssinaturaEmpresa(GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa) {
        this.gatewayAssinaturaEmpresas.remove(gatewayAssinaturaEmpresa);
        gatewayAssinaturaEmpresa.setGatewayPagamento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GatewayPagamento)) {
            return false;
        }
        return getId() != null && getId().equals(((GatewayPagamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GatewayPagamento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
