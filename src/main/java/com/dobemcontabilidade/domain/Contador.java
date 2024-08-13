package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoContadorEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Contador.
 */
@Entity
@Table(name = "contador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "crc")
    private String crc;

    @Column(name = "limite_empresas")
    private Integer limiteEmpresas;

    @Column(name = "limite_departamentos")
    private Integer limiteDepartamentos;

    @Column(name = "limite_faturamento")
    private Double limiteFaturamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_contador")
    private SituacaoContadorEnum situacaoContador;

    @JsonIgnoreProperties(
        value = {
            "funcionarios",
            "anexoPessoas",
            "escolaridadePessoas",
            "bancoPessoas",
            "dependentesFuncionarios",
            "enderecoPessoas",
            "emails",
            "telefones",
            "administrador",
            "contador",
            "socio",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @JsonIgnoreProperties(
        value = { "grupoAcessoEmpresaUsuarioContadors", "grupoAcessoUsuarioContadors", "feedBackUsuarioParaContadors", "contador" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private UsuarioContador usuarioContador;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "areaContabil", "assinaturaEmpresa", "contador" }, allowSetters = true)
    private Set<AreaContabilAssinaturaEmpresa> areaContabilAssinaturaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "criterioAvaliacaoAtor", "usuarioEmpresa", "contador", "ordemServico" }, allowSetters = true)
    private Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "feedBackUsuarioParaContadors",
            "feedBackContadorParaUsuarios",
            "etapaFluxoExecucaos",
            "servicoContabilOrdemServicos",
            "empresa",
            "contador",
            "fluxoModelo",
        },
        allowSetters = true
    )
    private Set<OrdemServico> ordemServicos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contador", "areaContabil" }, allowSetters = true)
    private Set<AreaContabilContador> areaContabilContadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaOrdemServicoExecucao", "contador" }, allowSetters = true)
    private Set<ContadorResponsavelOrdemServico> contadorResponsavelOrdemServicos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaRecorrenteExecucao", "contador" }, allowSetters = true)
    private Set<ContadorResponsavelTarefaRecorrente> contadorResponsavelTarefaRecorrentes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "departamento", "empresa", "contador" }, allowSetters = true)
    private Set<DepartamentoEmpresa> departamentoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "departamento", "contador" }, allowSetters = true)
    private Set<DepartamentoContador> departamentoContadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contador", "termoDeAdesao" }, allowSetters = true)
    private Set<TermoAdesaoContador> termoAdesaoContadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contador", "avaliacao" }, allowSetters = true)
    private Set<AvaliacaoContador> avaliacaoContadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "contador", "tarefa" }, allowSetters = true)
    private Set<TarefaEmpresa> tarefaEmpresas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "contadors", "perfilContadorDepartamentos" }, allowSetters = true)
    private PerfilContador perfilContador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrc() {
        return this.crc;
    }

    public Contador crc(String crc) {
        this.setCrc(crc);
        return this;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public Integer getLimiteEmpresas() {
        return this.limiteEmpresas;
    }

    public Contador limiteEmpresas(Integer limiteEmpresas) {
        this.setLimiteEmpresas(limiteEmpresas);
        return this;
    }

    public void setLimiteEmpresas(Integer limiteEmpresas) {
        this.limiteEmpresas = limiteEmpresas;
    }

    public Integer getLimiteDepartamentos() {
        return this.limiteDepartamentos;
    }

    public Contador limiteDepartamentos(Integer limiteDepartamentos) {
        this.setLimiteDepartamentos(limiteDepartamentos);
        return this;
    }

    public void setLimiteDepartamentos(Integer limiteDepartamentos) {
        this.limiteDepartamentos = limiteDepartamentos;
    }

    public Double getLimiteFaturamento() {
        return this.limiteFaturamento;
    }

    public Contador limiteFaturamento(Double limiteFaturamento) {
        this.setLimiteFaturamento(limiteFaturamento);
        return this;
    }

    public void setLimiteFaturamento(Double limiteFaturamento) {
        this.limiteFaturamento = limiteFaturamento;
    }

    public SituacaoContadorEnum getSituacaoContador() {
        return this.situacaoContador;
    }

    public Contador situacaoContador(SituacaoContadorEnum situacaoContador) {
        this.setSituacaoContador(situacaoContador);
        return this;
    }

    public void setSituacaoContador(SituacaoContadorEnum situacaoContador) {
        this.situacaoContador = situacaoContador;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Contador pessoa(Pessoa pessoa) {
        this.setPessoa(pessoa);
        return this;
    }

    public UsuarioContador getUsuarioContador() {
        return this.usuarioContador;
    }

    public void setUsuarioContador(UsuarioContador usuarioContador) {
        this.usuarioContador = usuarioContador;
    }

    public Contador usuarioContador(UsuarioContador usuarioContador) {
        this.setUsuarioContador(usuarioContador);
        return this;
    }

    public Set<AreaContabilAssinaturaEmpresa> getAreaContabilAssinaturaEmpresas() {
        return this.areaContabilAssinaturaEmpresas;
    }

    public void setAreaContabilAssinaturaEmpresas(Set<AreaContabilAssinaturaEmpresa> areaContabilAssinaturaEmpresas) {
        if (this.areaContabilAssinaturaEmpresas != null) {
            this.areaContabilAssinaturaEmpresas.forEach(i -> i.setContador(null));
        }
        if (areaContabilAssinaturaEmpresas != null) {
            areaContabilAssinaturaEmpresas.forEach(i -> i.setContador(this));
        }
        this.areaContabilAssinaturaEmpresas = areaContabilAssinaturaEmpresas;
    }

    public Contador areaContabilAssinaturaEmpresas(Set<AreaContabilAssinaturaEmpresa> areaContabilAssinaturaEmpresas) {
        this.setAreaContabilAssinaturaEmpresas(areaContabilAssinaturaEmpresas);
        return this;
    }

    public Contador addAreaContabilAssinaturaEmpresa(AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa) {
        this.areaContabilAssinaturaEmpresas.add(areaContabilAssinaturaEmpresa);
        areaContabilAssinaturaEmpresa.setContador(this);
        return this;
    }

    public Contador removeAreaContabilAssinaturaEmpresa(AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa) {
        this.areaContabilAssinaturaEmpresas.remove(areaContabilAssinaturaEmpresa);
        areaContabilAssinaturaEmpresa.setContador(null);
        return this;
    }

    public Set<FeedBackContadorParaUsuario> getFeedBackContadorParaUsuarios() {
        return this.feedBackContadorParaUsuarios;
    }

    public void setFeedBackContadorParaUsuarios(Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios) {
        if (this.feedBackContadorParaUsuarios != null) {
            this.feedBackContadorParaUsuarios.forEach(i -> i.setContador(null));
        }
        if (feedBackContadorParaUsuarios != null) {
            feedBackContadorParaUsuarios.forEach(i -> i.setContador(this));
        }
        this.feedBackContadorParaUsuarios = feedBackContadorParaUsuarios;
    }

    public Contador feedBackContadorParaUsuarios(Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios) {
        this.setFeedBackContadorParaUsuarios(feedBackContadorParaUsuarios);
        return this;
    }

    public Contador addFeedBackContadorParaUsuario(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        this.feedBackContadorParaUsuarios.add(feedBackContadorParaUsuario);
        feedBackContadorParaUsuario.setContador(this);
        return this;
    }

    public Contador removeFeedBackContadorParaUsuario(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        this.feedBackContadorParaUsuarios.remove(feedBackContadorParaUsuario);
        feedBackContadorParaUsuario.setContador(null);
        return this;
    }

    public Set<OrdemServico> getOrdemServicos() {
        return this.ordemServicos;
    }

    public void setOrdemServicos(Set<OrdemServico> ordemServicos) {
        if (this.ordemServicos != null) {
            this.ordemServicos.forEach(i -> i.setContador(null));
        }
        if (ordemServicos != null) {
            ordemServicos.forEach(i -> i.setContador(this));
        }
        this.ordemServicos = ordemServicos;
    }

    public Contador ordemServicos(Set<OrdemServico> ordemServicos) {
        this.setOrdemServicos(ordemServicos);
        return this;
    }

    public Contador addOrdemServico(OrdemServico ordemServico) {
        this.ordemServicos.add(ordemServico);
        ordemServico.setContador(this);
        return this;
    }

    public Contador removeOrdemServico(OrdemServico ordemServico) {
        this.ordemServicos.remove(ordemServico);
        ordemServico.setContador(null);
        return this;
    }

    public Set<AreaContabilContador> getAreaContabilContadors() {
        return this.areaContabilContadors;
    }

    public void setAreaContabilContadors(Set<AreaContabilContador> areaContabilContadors) {
        if (this.areaContabilContadors != null) {
            this.areaContabilContadors.forEach(i -> i.setContador(null));
        }
        if (areaContabilContadors != null) {
            areaContabilContadors.forEach(i -> i.setContador(this));
        }
        this.areaContabilContadors = areaContabilContadors;
    }

    public Contador areaContabilContadors(Set<AreaContabilContador> areaContabilContadors) {
        this.setAreaContabilContadors(areaContabilContadors);
        return this;
    }

    public Contador addAreaContabilContador(AreaContabilContador areaContabilContador) {
        this.areaContabilContadors.add(areaContabilContador);
        areaContabilContador.setContador(this);
        return this;
    }

    public Contador removeAreaContabilContador(AreaContabilContador areaContabilContador) {
        this.areaContabilContadors.remove(areaContabilContador);
        areaContabilContador.setContador(null);
        return this;
    }

    public Set<ContadorResponsavelOrdemServico> getContadorResponsavelOrdemServicos() {
        return this.contadorResponsavelOrdemServicos;
    }

    public void setContadorResponsavelOrdemServicos(Set<ContadorResponsavelOrdemServico> contadorResponsavelOrdemServicos) {
        if (this.contadorResponsavelOrdemServicos != null) {
            this.contadorResponsavelOrdemServicos.forEach(i -> i.setContador(null));
        }
        if (contadorResponsavelOrdemServicos != null) {
            contadorResponsavelOrdemServicos.forEach(i -> i.setContador(this));
        }
        this.contadorResponsavelOrdemServicos = contadorResponsavelOrdemServicos;
    }

    public Contador contadorResponsavelOrdemServicos(Set<ContadorResponsavelOrdemServico> contadorResponsavelOrdemServicos) {
        this.setContadorResponsavelOrdemServicos(contadorResponsavelOrdemServicos);
        return this;
    }

    public Contador addContadorResponsavelOrdemServico(ContadorResponsavelOrdemServico contadorResponsavelOrdemServico) {
        this.contadorResponsavelOrdemServicos.add(contadorResponsavelOrdemServico);
        contadorResponsavelOrdemServico.setContador(this);
        return this;
    }

    public Contador removeContadorResponsavelOrdemServico(ContadorResponsavelOrdemServico contadorResponsavelOrdemServico) {
        this.contadorResponsavelOrdemServicos.remove(contadorResponsavelOrdemServico);
        contadorResponsavelOrdemServico.setContador(null);
        return this;
    }

    public Set<ContadorResponsavelTarefaRecorrente> getContadorResponsavelTarefaRecorrentes() {
        return this.contadorResponsavelTarefaRecorrentes;
    }

    public void setContadorResponsavelTarefaRecorrentes(Set<ContadorResponsavelTarefaRecorrente> contadorResponsavelTarefaRecorrentes) {
        if (this.contadorResponsavelTarefaRecorrentes != null) {
            this.contadorResponsavelTarefaRecorrentes.forEach(i -> i.setContador(null));
        }
        if (contadorResponsavelTarefaRecorrentes != null) {
            contadorResponsavelTarefaRecorrentes.forEach(i -> i.setContador(this));
        }
        this.contadorResponsavelTarefaRecorrentes = contadorResponsavelTarefaRecorrentes;
    }

    public Contador contadorResponsavelTarefaRecorrentes(Set<ContadorResponsavelTarefaRecorrente> contadorResponsavelTarefaRecorrentes) {
        this.setContadorResponsavelTarefaRecorrentes(contadorResponsavelTarefaRecorrentes);
        return this;
    }

    public Contador addContadorResponsavelTarefaRecorrente(ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente) {
        this.contadorResponsavelTarefaRecorrentes.add(contadorResponsavelTarefaRecorrente);
        contadorResponsavelTarefaRecorrente.setContador(this);
        return this;
    }

    public Contador removeContadorResponsavelTarefaRecorrente(ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente) {
        this.contadorResponsavelTarefaRecorrentes.remove(contadorResponsavelTarefaRecorrente);
        contadorResponsavelTarefaRecorrente.setContador(null);
        return this;
    }

    public Set<DepartamentoEmpresa> getDepartamentoEmpresas() {
        return this.departamentoEmpresas;
    }

    public void setDepartamentoEmpresas(Set<DepartamentoEmpresa> departamentoEmpresas) {
        if (this.departamentoEmpresas != null) {
            this.departamentoEmpresas.forEach(i -> i.setContador(null));
        }
        if (departamentoEmpresas != null) {
            departamentoEmpresas.forEach(i -> i.setContador(this));
        }
        this.departamentoEmpresas = departamentoEmpresas;
    }

    public Contador departamentoEmpresas(Set<DepartamentoEmpresa> departamentoEmpresas) {
        this.setDepartamentoEmpresas(departamentoEmpresas);
        return this;
    }

    public Contador addDepartamentoEmpresa(DepartamentoEmpresa departamentoEmpresa) {
        this.departamentoEmpresas.add(departamentoEmpresa);
        departamentoEmpresa.setContador(this);
        return this;
    }

    public Contador removeDepartamentoEmpresa(DepartamentoEmpresa departamentoEmpresa) {
        this.departamentoEmpresas.remove(departamentoEmpresa);
        departamentoEmpresa.setContador(null);
        return this;
    }

    public Set<DepartamentoContador> getDepartamentoContadors() {
        return this.departamentoContadors;
    }

    public void setDepartamentoContadors(Set<DepartamentoContador> departamentoContadors) {
        if (this.departamentoContadors != null) {
            this.departamentoContadors.forEach(i -> i.setContador(null));
        }
        if (departamentoContadors != null) {
            departamentoContadors.forEach(i -> i.setContador(this));
        }
        this.departamentoContadors = departamentoContadors;
    }

    public Contador departamentoContadors(Set<DepartamentoContador> departamentoContadors) {
        this.setDepartamentoContadors(departamentoContadors);
        return this;
    }

    public Contador addDepartamentoContador(DepartamentoContador departamentoContador) {
        this.departamentoContadors.add(departamentoContador);
        departamentoContador.setContador(this);
        return this;
    }

    public Contador removeDepartamentoContador(DepartamentoContador departamentoContador) {
        this.departamentoContadors.remove(departamentoContador);
        departamentoContador.setContador(null);
        return this;
    }

    public Set<TermoAdesaoContador> getTermoAdesaoContadors() {
        return this.termoAdesaoContadors;
    }

    public void setTermoAdesaoContadors(Set<TermoAdesaoContador> termoAdesaoContadors) {
        if (this.termoAdesaoContadors != null) {
            this.termoAdesaoContadors.forEach(i -> i.setContador(null));
        }
        if (termoAdesaoContadors != null) {
            termoAdesaoContadors.forEach(i -> i.setContador(this));
        }
        this.termoAdesaoContadors = termoAdesaoContadors;
    }

    public Contador termoAdesaoContadors(Set<TermoAdesaoContador> termoAdesaoContadors) {
        this.setTermoAdesaoContadors(termoAdesaoContadors);
        return this;
    }

    public Contador addTermoAdesaoContador(TermoAdesaoContador termoAdesaoContador) {
        this.termoAdesaoContadors.add(termoAdesaoContador);
        termoAdesaoContador.setContador(this);
        return this;
    }

    public Contador removeTermoAdesaoContador(TermoAdesaoContador termoAdesaoContador) {
        this.termoAdesaoContadors.remove(termoAdesaoContador);
        termoAdesaoContador.setContador(null);
        return this;
    }

    public Set<AvaliacaoContador> getAvaliacaoContadors() {
        return this.avaliacaoContadors;
    }

    public void setAvaliacaoContadors(Set<AvaliacaoContador> avaliacaoContadors) {
        if (this.avaliacaoContadors != null) {
            this.avaliacaoContadors.forEach(i -> i.setContador(null));
        }
        if (avaliacaoContadors != null) {
            avaliacaoContadors.forEach(i -> i.setContador(this));
        }
        this.avaliacaoContadors = avaliacaoContadors;
    }

    public Contador avaliacaoContadors(Set<AvaliacaoContador> avaliacaoContadors) {
        this.setAvaliacaoContadors(avaliacaoContadors);
        return this;
    }

    public Contador addAvaliacaoContador(AvaliacaoContador avaliacaoContador) {
        this.avaliacaoContadors.add(avaliacaoContador);
        avaliacaoContador.setContador(this);
        return this;
    }

    public Contador removeAvaliacaoContador(AvaliacaoContador avaliacaoContador) {
        this.avaliacaoContadors.remove(avaliacaoContador);
        avaliacaoContador.setContador(null);
        return this;
    }

    public Set<TarefaEmpresa> getTarefaEmpresas() {
        return this.tarefaEmpresas;
    }

    public void setTarefaEmpresas(Set<TarefaEmpresa> tarefaEmpresas) {
        if (this.tarefaEmpresas != null) {
            this.tarefaEmpresas.forEach(i -> i.setContador(null));
        }
        if (tarefaEmpresas != null) {
            tarefaEmpresas.forEach(i -> i.setContador(this));
        }
        this.tarefaEmpresas = tarefaEmpresas;
    }

    public Contador tarefaEmpresas(Set<TarefaEmpresa> tarefaEmpresas) {
        this.setTarefaEmpresas(tarefaEmpresas);
        return this;
    }

    public Contador addTarefaEmpresa(TarefaEmpresa tarefaEmpresa) {
        this.tarefaEmpresas.add(tarefaEmpresa);
        tarefaEmpresa.setContador(this);
        return this;
    }

    public Contador removeTarefaEmpresa(TarefaEmpresa tarefaEmpresa) {
        this.tarefaEmpresas.remove(tarefaEmpresa);
        tarefaEmpresa.setContador(null);
        return this;
    }

    public PerfilContador getPerfilContador() {
        return this.perfilContador;
    }

    public void setPerfilContador(PerfilContador perfilContador) {
        this.perfilContador = perfilContador;
    }

    public Contador perfilContador(PerfilContador perfilContador) {
        this.setPerfilContador(perfilContador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contador)) {
            return false;
        }
        return getId() != null && getId().equals(((Contador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contador{" +
            "id=" + getId() +
            ", crc='" + getCrc() + "'" +
            ", limiteEmpresas=" + getLimiteEmpresas() +
            ", limiteDepartamentos=" + getLimiteDepartamentos() +
            ", limiteFaturamento=" + getLimiteFaturamento() +
            ", situacaoContador='" + getSituacaoContador() + "'" +
            "}";
    }
}
