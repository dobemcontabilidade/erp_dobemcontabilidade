package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ImpostoEmpresaModelo.
 */
@Entity
@Table(name = "imposto_empresa_modelo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImpostoEmpresaModelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Size(max = 200)
    @Column(name = "observacao", length = 200)
    private String observacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "servicoContabilEmpresaModelos",
            "impostoEmpresaModelos",
            "anexoRequeridoEmpresas",
            "tarefaEmpresaModelos",
            "empresas",
            "segmentoCnaes",
            "ramo",
            "enquadramento",
            "tributacao",
            "cidade",
        },
        allowSetters = true
    )
    private EmpresaModelo empresaModelo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "impostoEmpresas", "parcelamentoImpostos", "impostoEmpresaModelos", "esfera" }, allowSetters = true)
    private Imposto imposto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ImpostoEmpresaModelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public ImpostoEmpresaModelo nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public ImpostoEmpresaModelo observacao(String observacao) {
        this.setObservacao(observacao);
        return this;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public EmpresaModelo getEmpresaModelo() {
        return this.empresaModelo;
    }

    public void setEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelo = empresaModelo;
    }

    public ImpostoEmpresaModelo empresaModelo(EmpresaModelo empresaModelo) {
        this.setEmpresaModelo(empresaModelo);
        return this;
    }

    public Imposto getImposto() {
        return this.imposto;
    }

    public void setImposto(Imposto imposto) {
        this.imposto = imposto;
    }

    public ImpostoEmpresaModelo imposto(Imposto imposto) {
        this.setImposto(imposto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImpostoEmpresaModelo)) {
            return false;
        }
        return getId() != null && getId().equals(((ImpostoEmpresaModelo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImpostoEmpresaModelo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
