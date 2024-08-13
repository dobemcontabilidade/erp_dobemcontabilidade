package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AgendaTarefaOrdemServicoExecucao.
 */
@Entity
@Table(name = "agen_tafe_ordem_serv_exec")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgendaTarefaOrdemServicoExecucao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "hora_inicio")
    private Instant horaInicio;

    @Column(name = "hora_fim")
    private Instant horaFim;

    @Column(name = "dia_inteiro")
    private Boolean diaInteiro;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agendaTarefaOrdemServicoExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "agendaTarefaRecorrenteExecucao", "agendaTarefaOrdemServicoExecucao" }, allowSetters = true)
    private Set<AgendaContadorConfig> agendaContadorConfigs = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "agendaTarefaOrdemServicoExecucaos",
            "anexoOrdemServicoExecucaos",
            "subTarefaOrdemServicos",
            "contadorResponsavelOrdemServicos",
            "tarefaOrdemServico",
        },
        allowSetters = true
    )
    private TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AgendaTarefaOrdemServicoExecucao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHoraInicio() {
        return this.horaInicio;
    }

    public AgendaTarefaOrdemServicoExecucao horaInicio(Instant horaInicio) {
        this.setHoraInicio(horaInicio);
        return this;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFim() {
        return this.horaFim;
    }

    public AgendaTarefaOrdemServicoExecucao horaFim(Instant horaFim) {
        this.setHoraFim(horaFim);
        return this;
    }

    public void setHoraFim(Instant horaFim) {
        this.horaFim = horaFim;
    }

    public Boolean getDiaInteiro() {
        return this.diaInteiro;
    }

    public AgendaTarefaOrdemServicoExecucao diaInteiro(Boolean diaInteiro) {
        this.setDiaInteiro(diaInteiro);
        return this;
    }

    public void setDiaInteiro(Boolean diaInteiro) {
        this.diaInteiro = diaInteiro;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public AgendaTarefaOrdemServicoExecucao ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Set<AgendaContadorConfig> getAgendaContadorConfigs() {
        return this.agendaContadorConfigs;
    }

    public void setAgendaContadorConfigs(Set<AgendaContadorConfig> agendaContadorConfigs) {
        if (this.agendaContadorConfigs != null) {
            this.agendaContadorConfigs.forEach(i -> i.setAgendaTarefaOrdemServicoExecucao(null));
        }
        if (agendaContadorConfigs != null) {
            agendaContadorConfigs.forEach(i -> i.setAgendaTarefaOrdemServicoExecucao(this));
        }
        this.agendaContadorConfigs = agendaContadorConfigs;
    }

    public AgendaTarefaOrdemServicoExecucao agendaContadorConfigs(Set<AgendaContadorConfig> agendaContadorConfigs) {
        this.setAgendaContadorConfigs(agendaContadorConfigs);
        return this;
    }

    public AgendaTarefaOrdemServicoExecucao addAgendaContadorConfig(AgendaContadorConfig agendaContadorConfig) {
        this.agendaContadorConfigs.add(agendaContadorConfig);
        agendaContadorConfig.setAgendaTarefaOrdemServicoExecucao(this);
        return this;
    }

    public AgendaTarefaOrdemServicoExecucao removeAgendaContadorConfig(AgendaContadorConfig agendaContadorConfig) {
        this.agendaContadorConfigs.remove(agendaContadorConfig);
        agendaContadorConfig.setAgendaTarefaOrdemServicoExecucao(null);
        return this;
    }

    public TarefaOrdemServicoExecucao getTarefaOrdemServicoExecucao() {
        return this.tarefaOrdemServicoExecucao;
    }

    public void setTarefaOrdemServicoExecucao(TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao) {
        this.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;
    }

    public AgendaTarefaOrdemServicoExecucao tarefaOrdemServicoExecucao(TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao) {
        this.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendaTarefaOrdemServicoExecucao)) {
            return false;
        }
        return getId() != null && getId().equals(((AgendaTarefaOrdemServicoExecucao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgendaTarefaOrdemServicoExecucao{" +
            "id=" + getId() +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            ", diaInteiro='" + getDiaInteiro() + "'" +
            ", ativo='" + getAtivo() + "'" +
            "}";
    }
}
