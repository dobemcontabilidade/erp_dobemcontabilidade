package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoFuncionarioEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Funcionario.
 */
@Entity
@Table(name = "funcionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_pis_nis_pasep")
    private Integer numeroPisNisPasep;

    @Column(name = "reintegrado")
    private Boolean reintegrado;

    @Column(name = "primeiro_emprego")
    private Boolean primeiroEmprego;

    @Column(name = "multiplo_vinculos")
    private Boolean multiploVinculos;

    @Column(name = "data_opcao_fgts")
    private String dataOpcaoFgts;

    @Column(name = "filiacao_sindical")
    private Boolean filiacaoSindical;

    @Size(max = 20)
    @Column(name = "cnpj_sindicato", length = 20)
    private String cnpjSindicato;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_funcionario_enum")
    private TipoFuncionarioEnum tipoFuncionarioEnum;

    @JsonIgnoreProperties(
        value = {
            "grupoAcessoUsuarioEmpresas",
            "feedBackUsuarioParaContadors",
            "feedBackContadorParaUsuarios",
            "assinaturaEmpresa",
            "funcionario",
            "socio",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private UsuarioEmpresa usuarioEmpresa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario" }, allowSetters = true)
    private Set<Estrangeiro> estrangeiros = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario", "agenteIntegracaoEstagio", "instituicaoEnsino" }, allowSetters = true)
    private Set<ContratoFuncionario> contratoFuncionarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario" }, allowSetters = true)
    private Set<DemissaoFuncionario> demissaoFuncionarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "funcionario" }, allowSetters = true)
    private Set<DependentesFuncionario> dependentesFuncionarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario" }, allowSetters = true)
    private Set<EmpresaVinculada> empresaVinculadas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario", "departamento" }, allowSetters = true)
    private Set<DepartamentoFuncionario> departamentoFuncionarios = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
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
    private Pessoa pessoa;

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
    @JsonIgnoreProperties(value = { "funcionarios", "socios" }, allowSetters = true)
    private Profissao profissao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Funcionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroPisNisPasep() {
        return this.numeroPisNisPasep;
    }

    public Funcionario numeroPisNisPasep(Integer numeroPisNisPasep) {
        this.setNumeroPisNisPasep(numeroPisNisPasep);
        return this;
    }

    public void setNumeroPisNisPasep(Integer numeroPisNisPasep) {
        this.numeroPisNisPasep = numeroPisNisPasep;
    }

    public Boolean getReintegrado() {
        return this.reintegrado;
    }

    public Funcionario reintegrado(Boolean reintegrado) {
        this.setReintegrado(reintegrado);
        return this;
    }

    public void setReintegrado(Boolean reintegrado) {
        this.reintegrado = reintegrado;
    }

    public Boolean getPrimeiroEmprego() {
        return this.primeiroEmprego;
    }

    public Funcionario primeiroEmprego(Boolean primeiroEmprego) {
        this.setPrimeiroEmprego(primeiroEmprego);
        return this;
    }

    public void setPrimeiroEmprego(Boolean primeiroEmprego) {
        this.primeiroEmprego = primeiroEmprego;
    }

    public Boolean getMultiploVinculos() {
        return this.multiploVinculos;
    }

    public Funcionario multiploVinculos(Boolean multiploVinculos) {
        this.setMultiploVinculos(multiploVinculos);
        return this;
    }

    public void setMultiploVinculos(Boolean multiploVinculos) {
        this.multiploVinculos = multiploVinculos;
    }

    public String getDataOpcaoFgts() {
        return this.dataOpcaoFgts;
    }

    public Funcionario dataOpcaoFgts(String dataOpcaoFgts) {
        this.setDataOpcaoFgts(dataOpcaoFgts);
        return this;
    }

    public void setDataOpcaoFgts(String dataOpcaoFgts) {
        this.dataOpcaoFgts = dataOpcaoFgts;
    }

    public Boolean getFiliacaoSindical() {
        return this.filiacaoSindical;
    }

    public Funcionario filiacaoSindical(Boolean filiacaoSindical) {
        this.setFiliacaoSindical(filiacaoSindical);
        return this;
    }

    public void setFiliacaoSindical(Boolean filiacaoSindical) {
        this.filiacaoSindical = filiacaoSindical;
    }

    public String getCnpjSindicato() {
        return this.cnpjSindicato;
    }

    public Funcionario cnpjSindicato(String cnpjSindicato) {
        this.setCnpjSindicato(cnpjSindicato);
        return this;
    }

    public void setCnpjSindicato(String cnpjSindicato) {
        this.cnpjSindicato = cnpjSindicato;
    }

    public TipoFuncionarioEnum getTipoFuncionarioEnum() {
        return this.tipoFuncionarioEnum;
    }

    public Funcionario tipoFuncionarioEnum(TipoFuncionarioEnum tipoFuncionarioEnum) {
        this.setTipoFuncionarioEnum(tipoFuncionarioEnum);
        return this;
    }

    public void setTipoFuncionarioEnum(TipoFuncionarioEnum tipoFuncionarioEnum) {
        this.tipoFuncionarioEnum = tipoFuncionarioEnum;
    }

    public UsuarioEmpresa getUsuarioEmpresa() {
        return this.usuarioEmpresa;
    }

    public void setUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.usuarioEmpresa = usuarioEmpresa;
    }

    public Funcionario usuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.setUsuarioEmpresa(usuarioEmpresa);
        return this;
    }

    public Set<Estrangeiro> getEstrangeiros() {
        return this.estrangeiros;
    }

    public void setEstrangeiros(Set<Estrangeiro> estrangeiros) {
        if (this.estrangeiros != null) {
            this.estrangeiros.forEach(i -> i.setFuncionario(null));
        }
        if (estrangeiros != null) {
            estrangeiros.forEach(i -> i.setFuncionario(this));
        }
        this.estrangeiros = estrangeiros;
    }

    public Funcionario estrangeiros(Set<Estrangeiro> estrangeiros) {
        this.setEstrangeiros(estrangeiros);
        return this;
    }

    public Funcionario addEstrangeiro(Estrangeiro estrangeiro) {
        this.estrangeiros.add(estrangeiro);
        estrangeiro.setFuncionario(this);
        return this;
    }

    public Funcionario removeEstrangeiro(Estrangeiro estrangeiro) {
        this.estrangeiros.remove(estrangeiro);
        estrangeiro.setFuncionario(null);
        return this;
    }

    public Set<ContratoFuncionario> getContratoFuncionarios() {
        return this.contratoFuncionarios;
    }

    public void setContratoFuncionarios(Set<ContratoFuncionario> contratoFuncionarios) {
        if (this.contratoFuncionarios != null) {
            this.contratoFuncionarios.forEach(i -> i.setFuncionario(null));
        }
        if (contratoFuncionarios != null) {
            contratoFuncionarios.forEach(i -> i.setFuncionario(this));
        }
        this.contratoFuncionarios = contratoFuncionarios;
    }

    public Funcionario contratoFuncionarios(Set<ContratoFuncionario> contratoFuncionarios) {
        this.setContratoFuncionarios(contratoFuncionarios);
        return this;
    }

    public Funcionario addContratoFuncionario(ContratoFuncionario contratoFuncionario) {
        this.contratoFuncionarios.add(contratoFuncionario);
        contratoFuncionario.setFuncionario(this);
        return this;
    }

    public Funcionario removeContratoFuncionario(ContratoFuncionario contratoFuncionario) {
        this.contratoFuncionarios.remove(contratoFuncionario);
        contratoFuncionario.setFuncionario(null);
        return this;
    }

    public Set<DemissaoFuncionario> getDemissaoFuncionarios() {
        return this.demissaoFuncionarios;
    }

    public void setDemissaoFuncionarios(Set<DemissaoFuncionario> demissaoFuncionarios) {
        if (this.demissaoFuncionarios != null) {
            this.demissaoFuncionarios.forEach(i -> i.setFuncionario(null));
        }
        if (demissaoFuncionarios != null) {
            demissaoFuncionarios.forEach(i -> i.setFuncionario(this));
        }
        this.demissaoFuncionarios = demissaoFuncionarios;
    }

    public Funcionario demissaoFuncionarios(Set<DemissaoFuncionario> demissaoFuncionarios) {
        this.setDemissaoFuncionarios(demissaoFuncionarios);
        return this;
    }

    public Funcionario addDemissaoFuncionario(DemissaoFuncionario demissaoFuncionario) {
        this.demissaoFuncionarios.add(demissaoFuncionario);
        demissaoFuncionario.setFuncionario(this);
        return this;
    }

    public Funcionario removeDemissaoFuncionario(DemissaoFuncionario demissaoFuncionario) {
        this.demissaoFuncionarios.remove(demissaoFuncionario);
        demissaoFuncionario.setFuncionario(null);
        return this;
    }

    public Set<DependentesFuncionario> getDependentesFuncionarios() {
        return this.dependentesFuncionarios;
    }

    public void setDependentesFuncionarios(Set<DependentesFuncionario> dependentesFuncionarios) {
        if (this.dependentesFuncionarios != null) {
            this.dependentesFuncionarios.forEach(i -> i.setFuncionario(null));
        }
        if (dependentesFuncionarios != null) {
            dependentesFuncionarios.forEach(i -> i.setFuncionario(this));
        }
        this.dependentesFuncionarios = dependentesFuncionarios;
    }

    public Funcionario dependentesFuncionarios(Set<DependentesFuncionario> dependentesFuncionarios) {
        this.setDependentesFuncionarios(dependentesFuncionarios);
        return this;
    }

    public Funcionario addDependentesFuncionario(DependentesFuncionario dependentesFuncionario) {
        this.dependentesFuncionarios.add(dependentesFuncionario);
        dependentesFuncionario.setFuncionario(this);
        return this;
    }

    public Funcionario removeDependentesFuncionario(DependentesFuncionario dependentesFuncionario) {
        this.dependentesFuncionarios.remove(dependentesFuncionario);
        dependentesFuncionario.setFuncionario(null);
        return this;
    }

    public Set<EmpresaVinculada> getEmpresaVinculadas() {
        return this.empresaVinculadas;
    }

    public void setEmpresaVinculadas(Set<EmpresaVinculada> empresaVinculadas) {
        if (this.empresaVinculadas != null) {
            this.empresaVinculadas.forEach(i -> i.setFuncionario(null));
        }
        if (empresaVinculadas != null) {
            empresaVinculadas.forEach(i -> i.setFuncionario(this));
        }
        this.empresaVinculadas = empresaVinculadas;
    }

    public Funcionario empresaVinculadas(Set<EmpresaVinculada> empresaVinculadas) {
        this.setEmpresaVinculadas(empresaVinculadas);
        return this;
    }

    public Funcionario addEmpresaVinculada(EmpresaVinculada empresaVinculada) {
        this.empresaVinculadas.add(empresaVinculada);
        empresaVinculada.setFuncionario(this);
        return this;
    }

    public Funcionario removeEmpresaVinculada(EmpresaVinculada empresaVinculada) {
        this.empresaVinculadas.remove(empresaVinculada);
        empresaVinculada.setFuncionario(null);
        return this;
    }

    public Set<DepartamentoFuncionario> getDepartamentoFuncionarios() {
        return this.departamentoFuncionarios;
    }

    public void setDepartamentoFuncionarios(Set<DepartamentoFuncionario> departamentoFuncionarios) {
        if (this.departamentoFuncionarios != null) {
            this.departamentoFuncionarios.forEach(i -> i.setFuncionario(null));
        }
        if (departamentoFuncionarios != null) {
            departamentoFuncionarios.forEach(i -> i.setFuncionario(this));
        }
        this.departamentoFuncionarios = departamentoFuncionarios;
    }

    public Funcionario departamentoFuncionarios(Set<DepartamentoFuncionario> departamentoFuncionarios) {
        this.setDepartamentoFuncionarios(departamentoFuncionarios);
        return this;
    }

    public Funcionario addDepartamentoFuncionario(DepartamentoFuncionario departamentoFuncionario) {
        this.departamentoFuncionarios.add(departamentoFuncionario);
        departamentoFuncionario.setFuncionario(this);
        return this;
    }

    public Funcionario removeDepartamentoFuncionario(DepartamentoFuncionario departamentoFuncionario) {
        this.departamentoFuncionarios.remove(departamentoFuncionario);
        departamentoFuncionario.setFuncionario(null);
        return this;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Funcionario pessoa(Pessoa pessoa) {
        this.setPessoa(pessoa);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Funcionario empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Profissao getProfissao() {
        return this.profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public Funcionario profissao(Profissao profissao) {
        this.setProfissao(profissao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funcionario)) {
            return false;
        }
        return getId() != null && getId().equals(((Funcionario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Funcionario{" +
            "id=" + getId() +
            ", numeroPisNisPasep=" + getNumeroPisNisPasep() +
            ", reintegrado='" + getReintegrado() + "'" +
            ", primeiroEmprego='" + getPrimeiroEmprego() + "'" +
            ", multiploVinculos='" + getMultiploVinculos() + "'" +
            ", dataOpcaoFgts='" + getDataOpcaoFgts() + "'" +
            ", filiacaoSindical='" + getFiliacaoSindical() + "'" +
            ", cnpjSindicato='" + getCnpjSindicato() + "'" +
            ", tipoFuncionarioEnum='" + getTipoFuncionarioEnum() + "'" +
            "}";
    }
}
