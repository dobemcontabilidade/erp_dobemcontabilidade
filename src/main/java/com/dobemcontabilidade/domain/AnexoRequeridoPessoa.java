package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoAnexoPessoaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnexoRequeridoPessoa.
 */
@Entity
@Table(name = "anexo_requerido_pessoa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoRequeridoPessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "obrigatorio")
    private Boolean obrigatorio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoAnexoPessoaEnum tipo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "anexoRequeridoPessoas", "pessoa" }, allowSetters = true)
    private AnexoPessoa anexoPessoa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "anexoRequeridoPessoas",
            "anexoRequeridoEmpresas",
            "anexoRequeridoServicoContabils",
            "anexoServicoContabilEmpresas",
            "anexoRequeridoTarefaOrdemServicos",
            "anexoRequeridoTarefaRecorrentes",
        },
        allowSetters = true
    )
    private AnexoRequerido anexoRequerido;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnexoRequeridoPessoa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getObrigatorio() {
        return this.obrigatorio;
    }

    public AnexoRequeridoPessoa obrigatorio(Boolean obrigatorio) {
        this.setObrigatorio(obrigatorio);
        return this;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public TipoAnexoPessoaEnum getTipo() {
        return this.tipo;
    }

    public AnexoRequeridoPessoa tipo(TipoAnexoPessoaEnum tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoAnexoPessoaEnum tipo) {
        this.tipo = tipo;
    }

    public AnexoPessoa getAnexoPessoa() {
        return this.anexoPessoa;
    }

    public void setAnexoPessoa(AnexoPessoa anexoPessoa) {
        this.anexoPessoa = anexoPessoa;
    }

    public AnexoRequeridoPessoa anexoPessoa(AnexoPessoa anexoPessoa) {
        this.setAnexoPessoa(anexoPessoa);
        return this;
    }

    public AnexoRequerido getAnexoRequerido() {
        return this.anexoRequerido;
    }

    public void setAnexoRequerido(AnexoRequerido anexoRequerido) {
        this.anexoRequerido = anexoRequerido;
    }

    public AnexoRequeridoPessoa anexoRequerido(AnexoRequerido anexoRequerido) {
        this.setAnexoRequerido(anexoRequerido);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoRequeridoPessoa)) {
            return false;
        }
        return getId() != null && getId().equals(((AnexoRequeridoPessoa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoRequeridoPessoa{" +
            "id=" + getId() +
            ", obrigatorio='" + getObrigatorio() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
