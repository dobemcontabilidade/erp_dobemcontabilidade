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
 * A TarefaOrdemServicoExecucao.
 */
@Entity
@Table(name = "taf_ordem_serv_exec")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaOrdemServicoExecucao implements Serializable {

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

    @Column(name = "ordem")
    private Integer ordem;

    @Column(name = "data_entrega")
    private Instant dataEntrega;

    @Column(name = "data_agendada")
    private Instant dataAgendada;

    @Column(name = "concluida")
    private Boolean concluida;

    @Column(name = "notificar_cliente")
    private Boolean notificarCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "mes")
    private MesCompetenciaEnum mes;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_tarefa")
    private SituacaoTarefaEnum situacaoTarefa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaOrdemServicoExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "agendaContadorConfigs", "tarefaOrdemServicoExecucao" }, allowSetters = true)
    private Set<AgendaTarefaOrdemServicoExecucao> agendaTarefaOrdemServicoExecucaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaOrdemServicoExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaOrdemServicoExecucao" }, allowSetters = true)
    private Set<AnexoOrdemServicoExecucao> anexoOrdemServicoExecucaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaOrdemServicoExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaOrdemServicoExecucao" }, allowSetters = true)
    private Set<SubTarefaOrdemServico> subTarefaOrdemServicos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaOrdemServicoExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaOrdemServicoExecucao", "contador" }, allowSetters = true)
    private Set<ContadorResponsavelOrdemServico> contadorResponsavelOrdemServicos = new HashSet<>();

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

    public TarefaOrdemServicoExecucao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public TarefaOrdemServicoExecucao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public TarefaOrdemServicoExecucao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public TarefaOrdemServicoExecucao ordem(Integer ordem) {
        this.setOrdem(ordem);
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Instant getDataEntrega() {
        return this.dataEntrega;
    }

    public TarefaOrdemServicoExecucao dataEntrega(Instant dataEntrega) {
        this.setDataEntrega(dataEntrega);
        return this;
    }

    public void setDataEntrega(Instant dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Instant getDataAgendada() {
        return this.dataAgendada;
    }

    public TarefaOrdemServicoExecucao dataAgendada(Instant dataAgendada) {
        this.setDataAgendada(dataAgendada);
        return this;
    }

    public void setDataAgendada(Instant dataAgendada) {
        this.dataAgendada = dataAgendada;
    }

    public Boolean getConcluida() {
        return this.concluida;
    }

    public TarefaOrdemServicoExecucao concluida(Boolean concluida) {
        this.setConcluida(concluida);
        return this;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public Boolean getNotificarCliente() {
        return this.notificarCliente;
    }

    public TarefaOrdemServicoExecucao notificarCliente(Boolean notificarCliente) {
        this.setNotificarCliente(notificarCliente);
        return this;
    }

    public void setNotificarCliente(Boolean notificarCliente) {
        this.notificarCliente = notificarCliente;
    }

    public MesCompetenciaEnum getMes() {
        return this.mes;
    }

    public TarefaOrdemServicoExecucao mes(MesCompetenciaEnum mes) {
        this.setMes(mes);
        return this;
    }

    public void setMes(MesCompetenciaEnum mes) {
        this.mes = mes;
    }

    public SituacaoTarefaEnum getSituacaoTarefa() {
        return this.situacaoTarefa;
    }

    public TarefaOrdemServicoExecucao situacaoTarefa(SituacaoTarefaEnum situacaoTarefa) {
        this.setSituacaoTarefa(situacaoTarefa);
        return this;
    }

    public void setSituacaoTarefa(SituacaoTarefaEnum situacaoTarefa) {
        this.situacaoTarefa = situacaoTarefa;
    }

    public Set<AgendaTarefaOrdemServicoExecucao> getAgendaTarefaOrdemServicoExecucaos() {
        return this.agendaTarefaOrdemServicoExecucaos;
    }

    public void setAgendaTarefaOrdemServicoExecucaos(Set<AgendaTarefaOrdemServicoExecucao> agendaTarefaOrdemServicoExecucaos) {
        if (this.agendaTarefaOrdemServicoExecucaos != null) {
            this.agendaTarefaOrdemServicoExecucaos.forEach(i -> i.setTarefaOrdemServicoExecucao(null));
        }
        if (agendaTarefaOrdemServicoExecucaos != null) {
            agendaTarefaOrdemServicoExecucaos.forEach(i -> i.setTarefaOrdemServicoExecucao(this));
        }
        this.agendaTarefaOrdemServicoExecucaos = agendaTarefaOrdemServicoExecucaos;
    }

    public TarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucaos(
        Set<AgendaTarefaOrdemServicoExecucao> agendaTarefaOrdemServicoExecucaos
    ) {
        this.setAgendaTarefaOrdemServicoExecucaos(agendaTarefaOrdemServicoExecucaos);
        return this;
    }

    public TarefaOrdemServicoExecucao addAgendaTarefaOrdemServicoExecucao(
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao
    ) {
        this.agendaTarefaOrdemServicoExecucaos.add(agendaTarefaOrdemServicoExecucao);
        agendaTarefaOrdemServicoExecucao.setTarefaOrdemServicoExecucao(this);
        return this;
    }

    public TarefaOrdemServicoExecucao removeAgendaTarefaOrdemServicoExecucao(
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao
    ) {
        this.agendaTarefaOrdemServicoExecucaos.remove(agendaTarefaOrdemServicoExecucao);
        agendaTarefaOrdemServicoExecucao.setTarefaOrdemServicoExecucao(null);
        return this;
    }

    public Set<AnexoOrdemServicoExecucao> getAnexoOrdemServicoExecucaos() {
        return this.anexoOrdemServicoExecucaos;
    }

    public void setAnexoOrdemServicoExecucaos(Set<AnexoOrdemServicoExecucao> anexoOrdemServicoExecucaos) {
        if (this.anexoOrdemServicoExecucaos != null) {
            this.anexoOrdemServicoExecucaos.forEach(i -> i.setTarefaOrdemServicoExecucao(null));
        }
        if (anexoOrdemServicoExecucaos != null) {
            anexoOrdemServicoExecucaos.forEach(i -> i.setTarefaOrdemServicoExecucao(this));
        }
        this.anexoOrdemServicoExecucaos = anexoOrdemServicoExecucaos;
    }

    public TarefaOrdemServicoExecucao anexoOrdemServicoExecucaos(Set<AnexoOrdemServicoExecucao> anexoOrdemServicoExecucaos) {
        this.setAnexoOrdemServicoExecucaos(anexoOrdemServicoExecucaos);
        return this;
    }

    public TarefaOrdemServicoExecucao addAnexoOrdemServicoExecucao(AnexoOrdemServicoExecucao anexoOrdemServicoExecucao) {
        this.anexoOrdemServicoExecucaos.add(anexoOrdemServicoExecucao);
        anexoOrdemServicoExecucao.setTarefaOrdemServicoExecucao(this);
        return this;
    }

    public TarefaOrdemServicoExecucao removeAnexoOrdemServicoExecucao(AnexoOrdemServicoExecucao anexoOrdemServicoExecucao) {
        this.anexoOrdemServicoExecucaos.remove(anexoOrdemServicoExecucao);
        anexoOrdemServicoExecucao.setTarefaOrdemServicoExecucao(null);
        return this;
    }

    public Set<SubTarefaOrdemServico> getSubTarefaOrdemServicos() {
        return this.subTarefaOrdemServicos;
    }

    public void setSubTarefaOrdemServicos(Set<SubTarefaOrdemServico> subTarefaOrdemServicos) {
        if (this.subTarefaOrdemServicos != null) {
            this.subTarefaOrdemServicos.forEach(i -> i.setTarefaOrdemServicoExecucao(null));
        }
        if (subTarefaOrdemServicos != null) {
            subTarefaOrdemServicos.forEach(i -> i.setTarefaOrdemServicoExecucao(this));
        }
        this.subTarefaOrdemServicos = subTarefaOrdemServicos;
    }

    public TarefaOrdemServicoExecucao subTarefaOrdemServicos(Set<SubTarefaOrdemServico> subTarefaOrdemServicos) {
        this.setSubTarefaOrdemServicos(subTarefaOrdemServicos);
        return this;
    }

    public TarefaOrdemServicoExecucao addSubTarefaOrdemServico(SubTarefaOrdemServico subTarefaOrdemServico) {
        this.subTarefaOrdemServicos.add(subTarefaOrdemServico);
        subTarefaOrdemServico.setTarefaOrdemServicoExecucao(this);
        return this;
    }

    public TarefaOrdemServicoExecucao removeSubTarefaOrdemServico(SubTarefaOrdemServico subTarefaOrdemServico) {
        this.subTarefaOrdemServicos.remove(subTarefaOrdemServico);
        subTarefaOrdemServico.setTarefaOrdemServicoExecucao(null);
        return this;
    }

    public Set<ContadorResponsavelOrdemServico> getContadorResponsavelOrdemServicos() {
        return this.contadorResponsavelOrdemServicos;
    }

    public void setContadorResponsavelOrdemServicos(Set<ContadorResponsavelOrdemServico> contadorResponsavelOrdemServicos) {
        if (this.contadorResponsavelOrdemServicos != null) {
            this.contadorResponsavelOrdemServicos.forEach(i -> i.setTarefaOrdemServicoExecucao(null));
        }
        if (contadorResponsavelOrdemServicos != null) {
            contadorResponsavelOrdemServicos.forEach(i -> i.setTarefaOrdemServicoExecucao(this));
        }
        this.contadorResponsavelOrdemServicos = contadorResponsavelOrdemServicos;
    }

    public TarefaOrdemServicoExecucao contadorResponsavelOrdemServicos(
        Set<ContadorResponsavelOrdemServico> contadorResponsavelOrdemServicos
    ) {
        this.setContadorResponsavelOrdemServicos(contadorResponsavelOrdemServicos);
        return this;
    }

    public TarefaOrdemServicoExecucao addContadorResponsavelOrdemServico(ContadorResponsavelOrdemServico contadorResponsavelOrdemServico) {
        this.contadorResponsavelOrdemServicos.add(contadorResponsavelOrdemServico);
        contadorResponsavelOrdemServico.setTarefaOrdemServicoExecucao(this);
        return this;
    }

    public TarefaOrdemServicoExecucao removeContadorResponsavelOrdemServico(
        ContadorResponsavelOrdemServico contadorResponsavelOrdemServico
    ) {
        this.contadorResponsavelOrdemServicos.remove(contadorResponsavelOrdemServico);
        contadorResponsavelOrdemServico.setTarefaOrdemServicoExecucao(null);
        return this;
    }

    public TarefaOrdemServico getTarefaOrdemServico() {
        return this.tarefaOrdemServico;
    }

    public void setTarefaOrdemServico(TarefaOrdemServico tarefaOrdemServico) {
        this.tarefaOrdemServico = tarefaOrdemServico;
    }

    public TarefaOrdemServicoExecucao tarefaOrdemServico(TarefaOrdemServico tarefaOrdemServico) {
        this.setTarefaOrdemServico(tarefaOrdemServico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarefaOrdemServicoExecucao)) {
            return false;
        }
        return getId() != null && getId().equals(((TarefaOrdemServicoExecucao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaOrdemServicoExecucao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ordem=" + getOrdem() +
            ", dataEntrega='" + getDataEntrega() + "'" +
            ", dataAgendada='" + getDataAgendada() + "'" +
            ", concluida='" + getConcluida() + "'" +
            ", notificarCliente='" + getNotificarCliente() + "'" +
            ", mes='" + getMes() + "'" +
            ", situacaoTarefa='" + getSituacaoTarefa() + "'" +
            "}";
    }
}
