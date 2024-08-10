package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DepartamentoContador.
 */
@Entity
@Table(name = "departamento_contador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DepartamentoContador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "percentual_experiencia")
    private Double percentualExperiencia;

    @Column(name = "descricao_experiencia")
    private String descricaoExperiencia;

    @Column(name = "pontuacao_entrevista")
    private Double pontuacaoEntrevista;

    @Column(name = "pontuacao_avaliacao")
    private Double pontuacaoAvaliacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "departamentoEmpresas", "perfilContadorDepartamentos", "departamentoContadors", "departamentoFuncionarios" },
        allowSetters = true
    )
    private Departamento departamento;

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

    public DepartamentoContador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPercentualExperiencia() {
        return this.percentualExperiencia;
    }

    public DepartamentoContador percentualExperiencia(Double percentualExperiencia) {
        this.setPercentualExperiencia(percentualExperiencia);
        return this;
    }

    public void setPercentualExperiencia(Double percentualExperiencia) {
        this.percentualExperiencia = percentualExperiencia;
    }

    public String getDescricaoExperiencia() {
        return this.descricaoExperiencia;
    }

    public DepartamentoContador descricaoExperiencia(String descricaoExperiencia) {
        this.setDescricaoExperiencia(descricaoExperiencia);
        return this;
    }

    public void setDescricaoExperiencia(String descricaoExperiencia) {
        this.descricaoExperiencia = descricaoExperiencia;
    }

    public Double getPontuacaoEntrevista() {
        return this.pontuacaoEntrevista;
    }

    public DepartamentoContador pontuacaoEntrevista(Double pontuacaoEntrevista) {
        this.setPontuacaoEntrevista(pontuacaoEntrevista);
        return this;
    }

    public void setPontuacaoEntrevista(Double pontuacaoEntrevista) {
        this.pontuacaoEntrevista = pontuacaoEntrevista;
    }

    public Double getPontuacaoAvaliacao() {
        return this.pontuacaoAvaliacao;
    }

    public DepartamentoContador pontuacaoAvaliacao(Double pontuacaoAvaliacao) {
        this.setPontuacaoAvaliacao(pontuacaoAvaliacao);
        return this;
    }

    public void setPontuacaoAvaliacao(Double pontuacaoAvaliacao) {
        this.pontuacaoAvaliacao = pontuacaoAvaliacao;
    }

    public Departamento getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public DepartamentoContador departamento(Departamento departamento) {
        this.setDepartamento(departamento);
        return this;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public DepartamentoContador contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartamentoContador)) {
            return false;
        }
        return getId() != null && getId().equals(((DepartamentoContador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoContador{" +
            "id=" + getId() +
            ", percentualExperiencia=" + getPercentualExperiencia() +
            ", descricaoExperiencia='" + getDescricaoExperiencia() + "'" +
            ", pontuacaoEntrevista=" + getPontuacaoEntrevista() +
            ", pontuacaoAvaliacao=" + getPontuacaoAvaliacao() +
            "}";
    }
}
