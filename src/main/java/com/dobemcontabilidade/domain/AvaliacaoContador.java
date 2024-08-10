package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AvaliacaoContador.
 */
@Entity
@Table(name = "avaliacao_contador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AvaliacaoContador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "pontuacao")
    private Double pontuacao;

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
    @JsonIgnoreProperties(value = { "avaliacaoContadors" }, allowSetters = true)
    private Avaliacao avaliacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AvaliacaoContador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPontuacao() {
        return this.pontuacao;
    }

    public AvaliacaoContador pontuacao(Double pontuacao) {
        this.setPontuacao(pontuacao);
        return this;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public AvaliacaoContador contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    public Avaliacao getAvaliacao() {
        return this.avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public AvaliacaoContador avaliacao(Avaliacao avaliacao) {
        this.setAvaliacao(avaliacao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AvaliacaoContador)) {
            return false;
        }
        return getId() != null && getId().equals(((AvaliacaoContador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AvaliacaoContador{" +
            "id=" + getId() +
            ", pontuacao=" + getPontuacao() +
            "}";
    }
}
