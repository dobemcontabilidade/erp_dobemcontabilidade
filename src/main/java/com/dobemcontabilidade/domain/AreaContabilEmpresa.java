package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AreaContabilEmpresa.
 */
@Entity
@Table(name = "area_contabil_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaContabilEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "pontuacao")
    private Double pontuacao;

    @Column(name = "depoimento")
    private String depoimento;

    @Column(name = "reclamacao")
    private String reclamacao;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AreaContabilEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPontuacao() {
        return this.pontuacao;
    }

    public AreaContabilEmpresa pontuacao(Double pontuacao) {
        this.setPontuacao(pontuacao);
        return this;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getDepoimento() {
        return this.depoimento;
    }

    public AreaContabilEmpresa depoimento(String depoimento) {
        this.setDepoimento(depoimento);
        return this;
    }

    public void setDepoimento(String depoimento) {
        this.depoimento = depoimento;
    }

    public String getReclamacao() {
        return this.reclamacao;
    }

    public AreaContabilEmpresa reclamacao(String reclamacao) {
        this.setReclamacao(reclamacao);
        return this;
    }

    public void setReclamacao(String reclamacao) {
        this.reclamacao = reclamacao;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public AreaContabilEmpresa contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaContabilEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((AreaContabilEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaContabilEmpresa{" +
            "id=" + getId() +
            ", pontuacao=" + getPontuacao() +
            ", depoimento='" + getDepoimento() + "'" +
            ", reclamacao='" + getReclamacao() + "'" +
            "}";
    }
}
