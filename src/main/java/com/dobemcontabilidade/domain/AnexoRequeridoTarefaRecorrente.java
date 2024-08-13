package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnexoRequeridoTarefaRecorrente.
 */
@Entity
@Table(name = "anexo_req_taf_recorrente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoRequeridoTarefaRecorrente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "obrigatorio")
    private Boolean obrigatorio;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "tarefaRecorrenteExecucaos", "anexoRequeridoTarefaRecorrentes", "servicoContabilAssinaturaEmpresa" },
        allowSetters = true
    )
    private TarefaRecorrente tarefaRecorrente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnexoRequeridoTarefaRecorrente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getObrigatorio() {
        return this.obrigatorio;
    }

    public AnexoRequeridoTarefaRecorrente obrigatorio(Boolean obrigatorio) {
        this.setObrigatorio(obrigatorio);
        return this;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public AnexoRequerido getAnexoRequerido() {
        return this.anexoRequerido;
    }

    public void setAnexoRequerido(AnexoRequerido anexoRequerido) {
        this.anexoRequerido = anexoRequerido;
    }

    public AnexoRequeridoTarefaRecorrente anexoRequerido(AnexoRequerido anexoRequerido) {
        this.setAnexoRequerido(anexoRequerido);
        return this;
    }

    public TarefaRecorrente getTarefaRecorrente() {
        return this.tarefaRecorrente;
    }

    public void setTarefaRecorrente(TarefaRecorrente tarefaRecorrente) {
        this.tarefaRecorrente = tarefaRecorrente;
    }

    public AnexoRequeridoTarefaRecorrente tarefaRecorrente(TarefaRecorrente tarefaRecorrente) {
        this.setTarefaRecorrente(tarefaRecorrente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoRequeridoTarefaRecorrente)) {
            return false;
        }
        return getId() != null && getId().equals(((AnexoRequeridoTarefaRecorrente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoRequeridoTarefaRecorrente{" +
            "id=" + getId() +
            ", obrigatorio='" + getObrigatorio() + "'" +
            "}";
    }
}
