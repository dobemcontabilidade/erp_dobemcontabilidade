package com.dobemcontabilidade.domain;

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

    @Size(max = 200)
    @Column(name = "nome", length = 200)
    private String nome;

    @NotNull
    @Column(name = "crc", nullable = false)
    private String crc;

    @Column(name = "limite_empresas")
    private Integer limiteEmpresas;

    @Column(name = "limite_area_contabils")
    private Integer limiteAreaContabils;

    @Column(name = "limite_faturamento")
    private Double limiteFaturamento;

    @Column(name = "limite_departamentos")
    private Integer limiteDepartamentos;

    @JsonIgnoreProperties(
        value = {
            "enderecoPessoas", "anexoPessoas", "emails", "telefones", "usuarioEmpresa", "administrador", "contador", "funcionario", "socio",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contador" }, allowSetters = true)
    private Set<AreaContabilEmpresa> areaContabilEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "areaContabil", "contador" }, allowSetters = true)
    private Set<AreaContabilContador> areaContabilContadors = new HashSet<>();

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
    @JsonIgnoreProperties(value = { "contador", "banco" }, allowSetters = true)
    private Set<BancoContador> bancoContadors = new HashSet<>();

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
    @JsonIgnoreProperties(value = { "perfilContadorAreaContabils", "contadors", "perfilContadorDepartamentos" }, allowSetters = true)
    private PerfilContador perfilContador;

    @JsonIgnoreProperties(value = { "contador", "administrador" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "contador")
    private UsuarioContador usuarioContador;

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

    public String getNome() {
        return this.nome;
    }

    public Contador nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Integer getLimiteAreaContabils() {
        return this.limiteAreaContabils;
    }

    public Contador limiteAreaContabils(Integer limiteAreaContabils) {
        this.setLimiteAreaContabils(limiteAreaContabils);
        return this;
    }

    public void setLimiteAreaContabils(Integer limiteAreaContabils) {
        this.limiteAreaContabils = limiteAreaContabils;
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

    public Set<AreaContabilEmpresa> getAreaContabilEmpresas() {
        return this.areaContabilEmpresas;
    }

    public void setAreaContabilEmpresas(Set<AreaContabilEmpresa> areaContabilEmpresas) {
        if (this.areaContabilEmpresas != null) {
            this.areaContabilEmpresas.forEach(i -> i.setContador(null));
        }
        if (areaContabilEmpresas != null) {
            areaContabilEmpresas.forEach(i -> i.setContador(this));
        }
        this.areaContabilEmpresas = areaContabilEmpresas;
    }

    public Contador areaContabilEmpresas(Set<AreaContabilEmpresa> areaContabilEmpresas) {
        this.setAreaContabilEmpresas(areaContabilEmpresas);
        return this;
    }

    public Contador addAreaContabilEmpresa(AreaContabilEmpresa areaContabilEmpresa) {
        this.areaContabilEmpresas.add(areaContabilEmpresa);
        areaContabilEmpresa.setContador(this);
        return this;
    }

    public Contador removeAreaContabilEmpresa(AreaContabilEmpresa areaContabilEmpresa) {
        this.areaContabilEmpresas.remove(areaContabilEmpresa);
        areaContabilEmpresa.setContador(null);
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

    public Set<BancoContador> getBancoContadors() {
        return this.bancoContadors;
    }

    public void setBancoContadors(Set<BancoContador> bancoContadors) {
        if (this.bancoContadors != null) {
            this.bancoContadors.forEach(i -> i.setContador(null));
        }
        if (bancoContadors != null) {
            bancoContadors.forEach(i -> i.setContador(this));
        }
        this.bancoContadors = bancoContadors;
    }

    public Contador bancoContadors(Set<BancoContador> bancoContadors) {
        this.setBancoContadors(bancoContadors);
        return this;
    }

    public Contador addBancoContador(BancoContador bancoContador) {
        this.bancoContadors.add(bancoContador);
        bancoContador.setContador(this);
        return this;
    }

    public Contador removeBancoContador(BancoContador bancoContador) {
        this.bancoContadors.remove(bancoContador);
        bancoContador.setContador(null);
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

    public UsuarioContador getUsuarioContador() {
        return this.usuarioContador;
    }

    public void setUsuarioContador(UsuarioContador usuarioContador) {
        if (this.usuarioContador != null) {
            this.usuarioContador.setContador(null);
        }
        if (usuarioContador != null) {
            usuarioContador.setContador(this);
        }
        this.usuarioContador = usuarioContador;
    }

    public Contador usuarioContador(UsuarioContador usuarioContador) {
        this.setUsuarioContador(usuarioContador);
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
            ", nome='" + getNome() + "'" +
            ", crc='" + getCrc() + "'" +
            ", limiteEmpresas=" + getLimiteEmpresas() +
            ", limiteAreaContabils=" + getLimiteAreaContabils() +
            ", limiteFaturamento=" + getLimiteFaturamento() +
            ", limiteDepartamentos=" + getLimiteDepartamentos() +
            "}";
    }
}
