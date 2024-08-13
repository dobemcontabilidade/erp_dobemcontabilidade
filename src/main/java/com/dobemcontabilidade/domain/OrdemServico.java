package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.StatusDaOSEnum;
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
 * A OrdemServico.
 */
@Entity
@Table(name = "ordem_servico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrdemServico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "valor")
    private Float valor;

    @Column(name = "prazo")
    private Instant prazo;

    @Column(name = "data_criacao")
    private Instant dataCriacao;

    @Column(name = "data_hora_cancelamento")
    private Instant dataHoraCancelamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_da_os")
    private StatusDaOSEnum statusDaOS;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ordemServico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuarioEmpresa", "usuarioContador", "criterioAvaliacaoAtor", "ordemServico" }, allowSetters = true)
    private Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ordemServico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "criterioAvaliacaoAtor", "usuarioEmpresa", "contador", "ordemServico" }, allowSetters = true)
    private Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ordemServico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "servicoContabilEtapaFluxoExecucaos", "ordemServico" }, allowSetters = true)
    private Set<EtapaFluxoExecucao> etapaFluxoExecucaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ordemServico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaOrdemServicos", "servicoContabil", "ordemServico" }, allowSetters = true)
    private Set<ServicoContabilOrdemServico> servicoContabilOrdemServicos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "funcionarios",
            "anexoEmpresas",
            "ordemServicos",
            "anexoRequeridoEmpresas",
            "impostoEmpresas",
            "parcelamentoImpostos",
            "assinaturaEmpresas",
            "departamentoEmpresas",
            "tarefaEmpresas",
            "enderecoEmpresas",
            "atividadeEmpresas",
            "socios",
            "certificadoDigitals",
            "opcaoRazaoSocialEmpresas",
            "opcaoNomeFantasiaEmpresas",
            "termoAdesaoEmpresas",
            "segmentoCnaes",
            "empresaModelo",
        },
        allowSetters = true
    )
    private Empresa empresa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "pessoa",
            "usuarioContador",
            "areaContabilAssinaturaEmpresas",
            "feedBackContadorParaUsuarios",
            "ordemServicos",
            "areaContabilContadors",
            "contadorResponsavelOrdemServicos",
            "contadorResponsavelTarefaRecorrentes",
            "departamentoEmpresas",
            "departamentoContadors",
            "termoAdesaoContadors",
            "avaliacaoContadors",
            "tarefaEmpresas",
            "perfilContador",
        },
        allowSetters = true
    )
    private Contador contador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ordemServicos", "etapaFluxoModelos", "cidade" }, allowSetters = true)
    private FluxoModelo fluxoModelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrdemServico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValor() {
        return this.valor;
    }

    public OrdemServico valor(Float valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Instant getPrazo() {
        return this.prazo;
    }

    public OrdemServico prazo(Instant prazo) {
        this.setPrazo(prazo);
        return this;
    }

    public void setPrazo(Instant prazo) {
        this.prazo = prazo;
    }

    public Instant getDataCriacao() {
        return this.dataCriacao;
    }

    public OrdemServico dataCriacao(Instant dataCriacao) {
        this.setDataCriacao(dataCriacao);
        return this;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Instant getDataHoraCancelamento() {
        return this.dataHoraCancelamento;
    }

    public OrdemServico dataHoraCancelamento(Instant dataHoraCancelamento) {
        this.setDataHoraCancelamento(dataHoraCancelamento);
        return this;
    }

    public void setDataHoraCancelamento(Instant dataHoraCancelamento) {
        this.dataHoraCancelamento = dataHoraCancelamento;
    }

    public StatusDaOSEnum getStatusDaOS() {
        return this.statusDaOS;
    }

    public OrdemServico statusDaOS(StatusDaOSEnum statusDaOS) {
        this.setStatusDaOS(statusDaOS);
        return this;
    }

    public void setStatusDaOS(StatusDaOSEnum statusDaOS) {
        this.statusDaOS = statusDaOS;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public OrdemServico descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<FeedBackUsuarioParaContador> getFeedBackUsuarioParaContadors() {
        return this.feedBackUsuarioParaContadors;
    }

    public void setFeedBackUsuarioParaContadors(Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors) {
        if (this.feedBackUsuarioParaContadors != null) {
            this.feedBackUsuarioParaContadors.forEach(i -> i.setOrdemServico(null));
        }
        if (feedBackUsuarioParaContadors != null) {
            feedBackUsuarioParaContadors.forEach(i -> i.setOrdemServico(this));
        }
        this.feedBackUsuarioParaContadors = feedBackUsuarioParaContadors;
    }

    public OrdemServico feedBackUsuarioParaContadors(Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors) {
        this.setFeedBackUsuarioParaContadors(feedBackUsuarioParaContadors);
        return this;
    }

    public OrdemServico addFeedBackUsuarioParaContador(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        this.feedBackUsuarioParaContadors.add(feedBackUsuarioParaContador);
        feedBackUsuarioParaContador.setOrdemServico(this);
        return this;
    }

    public OrdemServico removeFeedBackUsuarioParaContador(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        this.feedBackUsuarioParaContadors.remove(feedBackUsuarioParaContador);
        feedBackUsuarioParaContador.setOrdemServico(null);
        return this;
    }

    public Set<FeedBackContadorParaUsuario> getFeedBackContadorParaUsuarios() {
        return this.feedBackContadorParaUsuarios;
    }

    public void setFeedBackContadorParaUsuarios(Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios) {
        if (this.feedBackContadorParaUsuarios != null) {
            this.feedBackContadorParaUsuarios.forEach(i -> i.setOrdemServico(null));
        }
        if (feedBackContadorParaUsuarios != null) {
            feedBackContadorParaUsuarios.forEach(i -> i.setOrdemServico(this));
        }
        this.feedBackContadorParaUsuarios = feedBackContadorParaUsuarios;
    }

    public OrdemServico feedBackContadorParaUsuarios(Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios) {
        this.setFeedBackContadorParaUsuarios(feedBackContadorParaUsuarios);
        return this;
    }

    public OrdemServico addFeedBackContadorParaUsuario(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        this.feedBackContadorParaUsuarios.add(feedBackContadorParaUsuario);
        feedBackContadorParaUsuario.setOrdemServico(this);
        return this;
    }

    public OrdemServico removeFeedBackContadorParaUsuario(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        this.feedBackContadorParaUsuarios.remove(feedBackContadorParaUsuario);
        feedBackContadorParaUsuario.setOrdemServico(null);
        return this;
    }

    public Set<EtapaFluxoExecucao> getEtapaFluxoExecucaos() {
        return this.etapaFluxoExecucaos;
    }

    public void setEtapaFluxoExecucaos(Set<EtapaFluxoExecucao> etapaFluxoExecucaos) {
        if (this.etapaFluxoExecucaos != null) {
            this.etapaFluxoExecucaos.forEach(i -> i.setOrdemServico(null));
        }
        if (etapaFluxoExecucaos != null) {
            etapaFluxoExecucaos.forEach(i -> i.setOrdemServico(this));
        }
        this.etapaFluxoExecucaos = etapaFluxoExecucaos;
    }

    public OrdemServico etapaFluxoExecucaos(Set<EtapaFluxoExecucao> etapaFluxoExecucaos) {
        this.setEtapaFluxoExecucaos(etapaFluxoExecucaos);
        return this;
    }

    public OrdemServico addEtapaFluxoExecucao(EtapaFluxoExecucao etapaFluxoExecucao) {
        this.etapaFluxoExecucaos.add(etapaFluxoExecucao);
        etapaFluxoExecucao.setOrdemServico(this);
        return this;
    }

    public OrdemServico removeEtapaFluxoExecucao(EtapaFluxoExecucao etapaFluxoExecucao) {
        this.etapaFluxoExecucaos.remove(etapaFluxoExecucao);
        etapaFluxoExecucao.setOrdemServico(null);
        return this;
    }

    public Set<ServicoContabilOrdemServico> getServicoContabilOrdemServicos() {
        return this.servicoContabilOrdemServicos;
    }

    public void setServicoContabilOrdemServicos(Set<ServicoContabilOrdemServico> servicoContabilOrdemServicos) {
        if (this.servicoContabilOrdemServicos != null) {
            this.servicoContabilOrdemServicos.forEach(i -> i.setOrdemServico(null));
        }
        if (servicoContabilOrdemServicos != null) {
            servicoContabilOrdemServicos.forEach(i -> i.setOrdemServico(this));
        }
        this.servicoContabilOrdemServicos = servicoContabilOrdemServicos;
    }

    public OrdemServico servicoContabilOrdemServicos(Set<ServicoContabilOrdemServico> servicoContabilOrdemServicos) {
        this.setServicoContabilOrdemServicos(servicoContabilOrdemServicos);
        return this;
    }

    public OrdemServico addServicoContabilOrdemServico(ServicoContabilOrdemServico servicoContabilOrdemServico) {
        this.servicoContabilOrdemServicos.add(servicoContabilOrdemServico);
        servicoContabilOrdemServico.setOrdemServico(this);
        return this;
    }

    public OrdemServico removeServicoContabilOrdemServico(ServicoContabilOrdemServico servicoContabilOrdemServico) {
        this.servicoContabilOrdemServicos.remove(servicoContabilOrdemServico);
        servicoContabilOrdemServico.setOrdemServico(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public OrdemServico empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public OrdemServico contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    public FluxoModelo getFluxoModelo() {
        return this.fluxoModelo;
    }

    public void setFluxoModelo(FluxoModelo fluxoModelo) {
        this.fluxoModelo = fluxoModelo;
    }

    public OrdemServico fluxoModelo(FluxoModelo fluxoModelo) {
        this.setFluxoModelo(fluxoModelo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrdemServico)) {
            return false;
        }
        return getId() != null && getId().equals(((OrdemServico) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrdemServico{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", prazo='" + getPrazo() + "'" +
            ", dataCriacao='" + getDataCriacao() + "'" +
            ", dataHoraCancelamento='" + getDataHoraCancelamento() + "'" +
            ", statusDaOS='" + getStatusDaOS() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
