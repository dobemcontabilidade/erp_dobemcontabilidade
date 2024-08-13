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
 * A AgendaTarefaRecorrenteExecucao.
 */
@Entity
@Table(name = "agen_tafe_reco_exec")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AgendaTarefaRecorrenteExecucao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "hora_inicio")
    private Instant horaInicio;

    @Column(name = "hora_fim")
    private Instant horaFim;

    @Column(name = "dia_inteiro")
    private Boolean diaInteiro;

    @Column(name = "comentario")
    private String comentario;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "agendaTarefaRecorrenteExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "agendaTarefaRecorrenteExecucao", "agendaTarefaOrdemServicoExecucao" }, allowSetters = true)
    private Set<AgendaContadorConfig> agendaContadorConfigs = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "agendaTarefaRecorrenteExecucaos",
            "anexoTarefaRecorrenteExecucaos",
            "subTarefaRecorrentes",
            "contadorResponsavelTarefaRecorrentes",
            "tarefaRecorrente",
        },
        allowSetters = true
    )
    private TarefaRecorrenteExecucao tarefaRecorrenteExecucao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AgendaTarefaRecorrenteExecucao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public AgendaTarefaRecorrenteExecucao ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Instant getHoraInicio() {
        return this.horaInicio;
    }

    public AgendaTarefaRecorrenteExecucao horaInicio(Instant horaInicio) {
        this.setHoraInicio(horaInicio);
        return this;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFim() {
        return this.horaFim;
    }

    public AgendaTarefaRecorrenteExecucao horaFim(Instant horaFim) {
        this.setHoraFim(horaFim);
        return this;
    }

    public void setHoraFim(Instant horaFim) {
        this.horaFim = horaFim;
    }

    public Boolean getDiaInteiro() {
        return this.diaInteiro;
    }

    public AgendaTarefaRecorrenteExecucao diaInteiro(Boolean diaInteiro) {
        this.setDiaInteiro(diaInteiro);
        return this;
    }

    public void setDiaInteiro(Boolean diaInteiro) {
        this.diaInteiro = diaInteiro;
    }

    public String getComentario() {
        return this.comentario;
    }

    public AgendaTarefaRecorrenteExecucao comentario(String comentario) {
        this.setComentario(comentario);
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Set<AgendaContadorConfig> getAgendaContadorConfigs() {
        return this.agendaContadorConfigs;
    }

    public void setAgendaContadorConfigs(Set<AgendaContadorConfig> agendaContadorConfigs) {
        if (this.agendaContadorConfigs != null) {
            this.agendaContadorConfigs.forEach(i -> i.setAgendaTarefaRecorrenteExecucao(null));
        }
        if (agendaContadorConfigs != null) {
            agendaContadorConfigs.forEach(i -> i.setAgendaTarefaRecorrenteExecucao(this));
        }
        this.agendaContadorConfigs = agendaContadorConfigs;
    }

    public AgendaTarefaRecorrenteExecucao agendaContadorConfigs(Set<AgendaContadorConfig> agendaContadorConfigs) {
        this.setAgendaContadorConfigs(agendaContadorConfigs);
        return this;
    }

    public AgendaTarefaRecorrenteExecucao addAgendaContadorConfig(AgendaContadorConfig agendaContadorConfig) {
        this.agendaContadorConfigs.add(agendaContadorConfig);
        agendaContadorConfig.setAgendaTarefaRecorrenteExecucao(this);
        return this;
    }

    public AgendaTarefaRecorrenteExecucao removeAgendaContadorConfig(AgendaContadorConfig agendaContadorConfig) {
        this.agendaContadorConfigs.remove(agendaContadorConfig);
        agendaContadorConfig.setAgendaTarefaRecorrenteExecucao(null);
        return this;
    }

    public TarefaRecorrenteExecucao getTarefaRecorrenteExecucao() {
        return this.tarefaRecorrenteExecucao;
    }

    public void setTarefaRecorrenteExecucao(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        this.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;
    }

    public AgendaTarefaRecorrenteExecucao tarefaRecorrenteExecucao(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        this.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgendaTarefaRecorrenteExecucao)) {
            return false;
        }
        return getId() != null && getId().equals(((AgendaTarefaRecorrenteExecucao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgendaTarefaRecorrenteExecucao{" +
            "id=" + getId() +
            ", ativo='" + getAtivo() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            ", diaInteiro='" + getDiaInteiro() + "'" +
            ", comentario='" + getComentario() + "'" +
            "}";
    }
}
