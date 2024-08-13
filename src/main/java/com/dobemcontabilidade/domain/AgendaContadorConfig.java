package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoVisualizacaoAgendaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AgendaContadorConfig.
 */
@Entity
@Table(name = "agenda_contador_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgendaContadorConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_visualizacao_agenda_enum")
    private TipoVisualizacaoAgendaEnum tipoVisualizacaoAgendaEnum;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "agendaContadorConfigs", "tarefaRecorrenteExecucao" }, allowSetters = true)
    private AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "agendaContadorConfigs", "tarefaOrdemServicoExecucao" }, allowSetters = true)
    private AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AgendaContadorConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public AgendaContadorConfig ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public TipoVisualizacaoAgendaEnum getTipoVisualizacaoAgendaEnum() {
        return this.tipoVisualizacaoAgendaEnum;
    }

    public AgendaContadorConfig tipoVisualizacaoAgendaEnum(TipoVisualizacaoAgendaEnum tipoVisualizacaoAgendaEnum) {
        this.setTipoVisualizacaoAgendaEnum(tipoVisualizacaoAgendaEnum);
        return this;
    }

    public void setTipoVisualizacaoAgendaEnum(TipoVisualizacaoAgendaEnum tipoVisualizacaoAgendaEnum) {
        this.tipoVisualizacaoAgendaEnum = tipoVisualizacaoAgendaEnum;
    }

    public AgendaTarefaRecorrenteExecucao getAgendaTarefaRecorrenteExecucao() {
        return this.agendaTarefaRecorrenteExecucao;
    }

    public void setAgendaTarefaRecorrenteExecucao(AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao) {
        this.agendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucao;
    }

    public AgendaContadorConfig agendaTarefaRecorrenteExecucao(AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao) {
        this.setAgendaTarefaRecorrenteExecucao(agendaTarefaRecorrenteExecucao);
        return this;
    }

    public AgendaTarefaOrdemServicoExecucao getAgendaTarefaOrdemServicoExecucao() {
        return this.agendaTarefaOrdemServicoExecucao;
    }

    public void setAgendaTarefaOrdemServicoExecucao(AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao) {
        this.agendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucao;
    }

    public AgendaContadorConfig agendaTarefaOrdemServicoExecucao(AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao) {
        this.setAgendaTarefaOrdemServicoExecucao(agendaTarefaOrdemServicoExecucao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendaContadorConfig)) {
            return false;
        }
        return getId() != null && getId().equals(((AgendaContadorConfig) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgendaContadorConfig{" +
            "id=" + getId() +
            ", ativo='" + getAtivo() + "'" +
            ", tipoVisualizacaoAgendaEnum='" + getTipoVisualizacaoAgendaEnum() + "'" +
            "}";
    }
}
