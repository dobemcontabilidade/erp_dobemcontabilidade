package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TermoAdesaoContador.
 */
@Entity
@Table(name = "termo_adesao_contador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoAdesaoContador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_adesao")
    private Instant dataAdesao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "pessoa",
            "areaContabilEmpresas",
            "areaContabilContadors",
            "departamentoEmpresas",
            "departamentoContadors",
            "termoAdesaoContadors",
            "bancoContadors",
            "avaliacaoContadors",
            "tarefaEmpresas",
            "perfilContador",
            "usuarioContador",
        },
        allowSetters = true
    )
    private Contador contador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "termoAdesaoContadors" }, allowSetters = true)
    private TermoDeAdesao termoDeAdesao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TermoAdesaoContador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAdesao() {
        return this.dataAdesao;
    }

    public TermoAdesaoContador dataAdesao(Instant dataAdesao) {
        this.setDataAdesao(dataAdesao);
        return this;
    }

    public void setDataAdesao(Instant dataAdesao) {
        this.dataAdesao = dataAdesao;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public TermoAdesaoContador contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    public TermoDeAdesao getTermoDeAdesao() {
        return this.termoDeAdesao;
    }

    public void setTermoDeAdesao(TermoDeAdesao termoDeAdesao) {
        this.termoDeAdesao = termoDeAdesao;
    }

    public TermoAdesaoContador termoDeAdesao(TermoDeAdesao termoDeAdesao) {
        this.setTermoDeAdesao(termoDeAdesao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermoAdesaoContador)) {
            return false;
        }
        return getId() != null && getId().equals(((TermoAdesaoContador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoAdesaoContador{" +
            "id=" + getId() +
            ", dataAdesao='" + getDataAdesao() + "'" +
            "}";
    }
}
