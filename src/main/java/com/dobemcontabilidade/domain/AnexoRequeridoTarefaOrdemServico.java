package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnexoRequeridoTarefaOrdemServico.
 */
@Entity
@Table(name = "anexo_req_tare_ordem_serv")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoRequeridoTarefaOrdemServico implements Serializable {

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
        value = { "anexoRequeridoTarefaOrdemServicos", "tarefaOrdemServicoExecucaos", "servicoContabilOrdemServico" },
        allowSetters = true
    )
    private TarefaOrdemServico tarefaOrdemServico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnexoRequeridoTarefaOrdemServico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getObrigatorio() {
        return this.obrigatorio;
    }

    public AnexoRequeridoTarefaOrdemServico obrigatorio(Boolean obrigatorio) {
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

    public AnexoRequeridoTarefaOrdemServico anexoRequerido(AnexoRequerido anexoRequerido) {
        this.setAnexoRequerido(anexoRequerido);
        return this;
    }

    public TarefaOrdemServico getTarefaOrdemServico() {
        return this.tarefaOrdemServico;
    }

    public void setTarefaOrdemServico(TarefaOrdemServico tarefaOrdemServico) {
        this.tarefaOrdemServico = tarefaOrdemServico;
    }

    public AnexoRequeridoTarefaOrdemServico tarefaOrdemServico(TarefaOrdemServico tarefaOrdemServico) {
        this.setTarefaOrdemServico(tarefaOrdemServico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoRequeridoTarefaOrdemServico)) {
            return false;
        }
        return getId() != null && getId().equals(((AnexoRequeridoTarefaOrdemServico) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoRequeridoTarefaOrdemServico{" +
            "id=" + getId() +
            ", obrigatorio='" + getObrigatorio() + "'" +
            "}";
    }
}
