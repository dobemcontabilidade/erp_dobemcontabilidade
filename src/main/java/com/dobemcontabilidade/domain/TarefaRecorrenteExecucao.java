package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.MesCompetenciaEnum;
import com.dobemcontabilidade.domain.enumeration.SituacaoTarefaEnum;
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
 * A TarefaRecorrenteExecucao.
 */
@Entity
@Table(name = "tarefa_recorrente_execucao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaRecorrenteExecucao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_entrega")
    private Instant dataEntrega;

    @Column(name = "data_agendada")
    private Instant dataAgendada;

    @Column(name = "ordem")
    private Integer ordem;

    @Column(name = "concluida")
    private Boolean concluida;

    @Enumerated(EnumType.STRING)
    @Column(name = "mes")
    private MesCompetenciaEnum mes;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_tarefa")
    private SituacaoTarefaEnum situacaoTarefa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaRecorrenteExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "agendaContadorConfigs", "tarefaRecorrenteExecucao" }, allowSetters = true)
    private Set<AgendaTarefaRecorrenteExecucao> agendaTarefaRecorrenteExecucaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaRecorrenteExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaRecorrenteExecucao" }, allowSetters = true)
    private Set<AnexoTarefaRecorrenteExecucao> anexoTarefaRecorrenteExecucaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaRecorrenteExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaRecorrenteExecucao" }, allowSetters = true)
    private Set<SubTarefaRecorrente> subTarefaRecorrentes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaRecorrenteExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaRecorrenteExecucao", "contador" }, allowSetters = true)
    private Set<ContadorResponsavelTarefaRecorrente> contadorResponsavelTarefaRecorrentes = new HashSet<>();

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

    public TarefaRecorrenteExecucao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public TarefaRecorrenteExecucao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public TarefaRecorrenteExecucao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataEntrega() {
        return this.dataEntrega;
    }

    public TarefaRecorrenteExecucao dataEntrega(Instant dataEntrega) {
        this.setDataEntrega(dataEntrega);
        return this;
    }

    public void setDataEntrega(Instant dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Instant getDataAgendada() {
        return this.dataAgendada;
    }

    public TarefaRecorrenteExecucao dataAgendada(Instant dataAgendada) {
        this.setDataAgendada(dataAgendada);
        return this;
    }

    public void setDataAgendada(Instant dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public TarefaRecorrenteExecucao ordem(Integer ordem) {
        this.setOrdem(ordem);
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getConcluida() {
        return this.concluida;
    }

    public TarefaRecorrenteExecucao concluida(Boolean concluida) {
        this.setConcluida(concluida);
        return this;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public MesCompetenciaEnum getMes() {
        return this.mes;
    }

    public TarefaRecorrenteExecucao mes(MesCompetenciaEnum mes) {
        this.setMes(mes);
        return this;
    }

    public void setMes(MesCompetenciaEnum mes) {
        this.mes = mes;
    }

    public SituacaoTarefaEnum getSituacaoTarefa() {
        return this.situacaoTarefa;
    }

    public TarefaRecorrenteExecucao situacaoTarefa(SituacaoTarefaEnum situacaoTarefa) {
        this.setSituacaoTarefa(situacaoTarefa);
        return this;
    }

    public void setSituacaoTarefa(SituacaoTarefaEnum situacaoTarefa) {
        this.situacaoTarefa = situacaoTarefa;
    }

    public Set<AgendaTarefaRecorrenteExecucao> getAgendaTarefaRecorrenteExecucaos() {
        return this.agendaTarefaRecorrenteExecucaos;
    }

    public void setAgendaTarefaRecorrenteExecucaos(Set<AgendaTarefaRecorrenteExecucao> agendaTarefaRecorrenteExecucaos) {
        if (this.agendaTarefaRecorrenteExecucaos != null) {
            this.agendaTarefaRecorrenteExecucaos.forEach(i -> i.setTarefaRecorrenteExecucao(null));
        }
        if (agendaTarefaRecorrenteExecucaos != null) {
            agendaTarefaRecorrenteExecucaos.forEach(i -> i.setTarefaRecorrenteExecucao(this));
        }
        this.agendaTarefaRecorrenteExecucaos = agendaTarefaRecorrenteExecucaos;
    }

    public TarefaRecorrenteExecucao agendaTarefaRecorrenteExecucaos(Set<AgendaTarefaRecorrenteExecucao> agendaTarefaRecorrenteExecucaos) {
        this.setAgendaTarefaRecorrenteExecucaos(agendaTarefaRecorrenteExecucaos);
        return this;
    }

    public TarefaRecorrenteExecucao addAgendaTarefaRecorrenteExecucao(AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao) {
        this.agendaTarefaRecorrenteExecucaos.add(agendaTarefaRecorrenteExecucao);
        agendaTarefaRecorrenteExecucao.setTarefaRecorrenteExecucao(this);
        return this;
    }

    public TarefaRecorrenteExecucao removeAgendaTarefaRecorrenteExecucao(AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao) {
        this.agendaTarefaRecorrenteExecucaos.remove(agendaTarefaRecorrenteExecucao);
        agendaTarefaRecorrenteExecucao.setTarefaRecorrenteExecucao(null);
        return this;
    }

    public Set<AnexoTarefaRecorrenteExecucao> getAnexoTarefaRecorrenteExecucaos() {
        return this.anexoTarefaRecorrenteExecucaos;
    }

    public void setAnexoTarefaRecorrenteExecucaos(Set<AnexoTarefaRecorrenteExecucao> anexoTarefaRecorrenteExecucaos) {
        if (this.anexoTarefaRecorrenteExecucaos != null) {
            this.anexoTarefaRecorrenteExecucaos.forEach(i -> i.setTarefaRecorrenteExecucao(null));
        }
        if (anexoTarefaRecorrenteExecucaos != null) {
            anexoTarefaRecorrenteExecucaos.forEach(i -> i.setTarefaRecorrenteExecucao(this));
        }
        this.anexoTarefaRecorrenteExecucaos = anexoTarefaRecorrenteExecucaos;
    }

    public TarefaRecorrenteExecucao anexoTarefaRecorrenteExecucaos(Set<AnexoTarefaRecorrenteExecucao> anexoTarefaRecorrenteExecucaos) {
        this.setAnexoTarefaRecorrenteExecucaos(anexoTarefaRecorrenteExecucaos);
        return this;
    }

    public TarefaRecorrenteExecucao addAnexoTarefaRecorrenteExecucao(AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao) {
        this.anexoTarefaRecorrenteExecucaos.add(anexoTarefaRecorrenteExecucao);
        anexoTarefaRecorrenteExecucao.setTarefaRecorrenteExecucao(this);
        return this;
    }

    public TarefaRecorrenteExecucao removeAnexoTarefaRecorrenteExecucao(AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao) {
        this.anexoTarefaRecorrenteExecucaos.remove(anexoTarefaRecorrenteExecucao);
        anexoTarefaRecorrenteExecucao.setTarefaRecorrenteExecucao(null);
        return this;
    }

    public Set<SubTarefaRecorrente> getSubTarefaRecorrentes() {
        return this.subTarefaRecorrentes;
    }

    public void setSubTarefaRecorrentes(Set<SubTarefaRecorrente> subTarefaRecorrentes) {
        if (this.subTarefaRecorrentes != null) {
            this.subTarefaRecorrentes.forEach(i -> i.setTarefaRecorrenteExecucao(null));
        }
        if (subTarefaRecorrentes != null) {
            subTarefaRecorrentes.forEach(i -> i.setTarefaRecorrenteExecucao(this));
        }
        this.subTarefaRecorrentes = subTarefaRecorrentes;
    }

    public TarefaRecorrenteExecucao subTarefaRecorrentes(Set<SubTarefaRecorrente> subTarefaRecorrentes) {
        this.setSubTarefaRecorrentes(subTarefaRecorrentes);
        return this;
    }

    public TarefaRecorrenteExecucao addSubTarefaRecorrente(SubTarefaRecorrente subTarefaRecorrente) {
        this.subTarefaRecorrentes.add(subTarefaRecorrente);
        subTarefaRecorrente.setTarefaRecorrenteExecucao(this);
        return this;
    }

    public TarefaRecorrenteExecucao removeSubTarefaRecorrente(SubTarefaRecorrente subTarefaRecorrente) {
        this.subTarefaRecorrentes.remove(subTarefaRecorrente);
        subTarefaRecorrente.setTarefaRecorrenteExecucao(null);
        return this;
    }

    public Set<ContadorResponsavelTarefaRecorrente> getContadorResponsavelTarefaRecorrentes() {
        return this.contadorResponsavelTarefaRecorrentes;
    }

    public void setContadorResponsavelTarefaRecorrentes(Set<ContadorResponsavelTarefaRecorrente> contadorResponsavelTarefaRecorrentes) {
        if (this.contadorResponsavelTarefaRecorrentes != null) {
            this.contadorResponsavelTarefaRecorrentes.forEach(i -> i.setTarefaRecorrenteExecucao(null));
        }
        if (contadorResponsavelTarefaRecorrentes != null) {
            contadorResponsavelTarefaRecorrentes.forEach(i -> i.setTarefaRecorrenteExecucao(this));
        }
        this.contadorResponsavelTarefaRecorrentes = contadorResponsavelTarefaRecorrentes;
    }

    public TarefaRecorrenteExecucao contadorResponsavelTarefaRecorrentes(
        Set<ContadorResponsavelTarefaRecorrente> contadorResponsavelTarefaRecorrentes
    ) {
        this.setContadorResponsavelTarefaRecorrentes(contadorResponsavelTarefaRecorrentes);
        return this;
    }

    public TarefaRecorrenteExecucao addContadorResponsavelTarefaRecorrente(
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente
    ) {
        this.contadorResponsavelTarefaRecorrentes.add(contadorResponsavelTarefaRecorrente);
        contadorResponsavelTarefaRecorrente.setTarefaRecorrenteExecucao(this);
        return this;
    }

    public TarefaRecorrenteExecucao removeContadorResponsavelTarefaRecorrente(
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente
    ) {
        this.contadorResponsavelTarefaRecorrentes.remove(contadorResponsavelTarefaRecorrente);
        contadorResponsavelTarefaRecorrente.setTarefaRecorrenteExecucao(null);
        return this;
    }

    public TarefaRecorrente getTarefaRecorrente() {
        return this.tarefaRecorrente;
    }

    public void setTarefaRecorrente(TarefaRecorrente tarefaRecorrente) {
        this.tarefaRecorrente = tarefaRecorrente;
    }

    public TarefaRecorrenteExecucao tarefaRecorrente(TarefaRecorrente tarefaRecorrente) {
        this.setTarefaRecorrente(tarefaRecorrente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarefaRecorrenteExecucao)) {
            return false;
        }
        return getId() != null && getId().equals(((TarefaRecorrenteExecucao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaRecorrenteExecucao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", dataEntrega='" + getDataEntrega() + "'" +
            ", dataAgendada='" + getDataAgendada() + "'" +
            ", ordem=" + getOrdem() +
            ", concluida='" + getConcluida() + "'" +
            ", mes='" + getMes() + "'" +
            ", situacaoTarefa='" + getSituacaoTarefa() + "'" +
            "}";
    }
}
