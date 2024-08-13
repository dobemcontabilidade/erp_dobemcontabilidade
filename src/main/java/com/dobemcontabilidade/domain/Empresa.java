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
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @NotNull
    @Column(name = "nome_fantasia", nullable = false)
    private String nomeFantasia;

    @Lob
    @Column(name = "descricao_do_negocio")
    private String descricaoDoNegocio;

    @Size(max = 20)
    @Column(name = "cnpj", length = 20)
    private String cnpj;

    @Column(name = "data_abertura")
    private Instant dataAbertura;

    @Column(name = "url_contrato_social")
    private String urlContratoSocial;

    @Column(name = "capital_social")
    private Double capitalSocial;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "usuarioEmpresa",
            "estrangeiros",
            "contratoFuncionarios",
            "demissaoFuncionarios",
            "dependentesFuncionarios",
            "empresaVinculadas",
            "departamentoFuncionarios",
            "pessoa",
            "empresa",
            "profissao",
        },
        allowSetters = true
    )
    private Set<Funcionario> funcionarios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "anexoRequeridoEmpresa" }, allowSetters = true)
    private Set<AnexoEmpresa> anexoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anexoEmpresas", "anexoRequerido", "enquadramento", "tributacao", "ramo", "empresa", "empresaModelo" },
        allowSetters = true
    )
    private Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "impostoAPagarEmpresas", "empresa", "imposto" }, allowSetters = true)
    private Set<ImpostoEmpresa> impostoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parcelaImpostoAPagars", "impostoParcelados", "imposto", "empresa" }, allowSetters = true)
    private Set<ParcelamentoImposto> parcelamentoImpostos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "grupoAcessoEmpresas",
            "areaContabilAssinaturaEmpresas",
            "servicoContabilAssinaturaEmpresas",
            "gatewayAssinaturaEmpresas",
            "calculoPlanoAssinaturas",
            "pagamentos",
            "cobrancaEmpresas",
            "usuarioEmpresas",
            "periodoPagamento",
            "formaDePagamento",
            "planoContaAzul",
            "planoContabil",
            "empresa",
        },
        allowSetters = true
    )
    private Set<AssinaturaEmpresa> assinaturaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "departamento", "empresa", "contador" }, allowSetters = true)
    private Set<DepartamentoEmpresa> departamentoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "contador", "tarefa" }, allowSetters = true)
    private Set<TarefaEmpresa> tarefaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "cidade" }, allowSetters = true)
    private Set<EnderecoEmpresa> enderecoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cnae", "empresa" }, allowSetters = true)
    private Set<AtividadeEmpresa> atividadeEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "usuarioEmpresa", "empresa", "profissao" }, allowSetters = true)
    private Set<Socio> socios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa" }, allowSetters = true)
    private Set<CertificadoDigital> certificadoDigitals = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa" }, allowSetters = true)
    private Set<OpcaoRazaoSocialEmpresa> opcaoRazaoSocialEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa" }, allowSetters = true)
    private Set<OpcaoNomeFantasiaEmpresa> opcaoNomeFantasiaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "planoContabil" }, allowSetters = true)
    private Set<TermoAdesaoEmpresa> termoAdesaoEmpresas = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_empresa__segmento_cnae",
        joinColumns = @JoinColumn(name = "empresa_id"),
        inverseJoinColumns = @JoinColumn(name = "segmento_cnae_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subclasseCnaes", "ramo", "empresas", "empresaModelos" }, allowSetters = true)
    private Set<SegmentoCnae> segmentoCnaes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "servicoContabilEmpresaModelos",
            "impostoEmpresaModelos",
            "anexoRequeridoEmpresas",
            "tarefaEmpresaModelos",
            "empresas",
            "segmentoCnaes",
            "ramo",
            "enquadramento",
            "tributacao",
            "cidade",
        },
        allowSetters = true
    )
    private EmpresaModelo empresaModelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Empresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public Empresa razaoSocial(String razaoSocial) {
        this.setRazaoSocial(razaoSocial);
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    public Empresa nomeFantasia(String nomeFantasia) {
        this.setNomeFantasia(nomeFantasia);
        return this;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getDescricaoDoNegocio() {
        return this.descricaoDoNegocio;
    }

    public Empresa descricaoDoNegocio(String descricaoDoNegocio) {
        this.setDescricaoDoNegocio(descricaoDoNegocio);
        return this;
    }

    public void setDescricaoDoNegocio(String descricaoDoNegocio) {
        this.descricaoDoNegocio = descricaoDoNegocio;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public Empresa cnpj(String cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Instant getDataAbertura() {
        return this.dataAbertura;
    }

    public Empresa dataAbertura(Instant dataAbertura) {
        this.setDataAbertura(dataAbertura);
        return this;
    }

    public void setDataAbertura(Instant dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getUrlContratoSocial() {
        return this.urlContratoSocial;
    }

    public Empresa urlContratoSocial(String urlContratoSocial) {
        this.setUrlContratoSocial(urlContratoSocial);
        return this;
    }

    public void setUrlContratoSocial(String urlContratoSocial) {
        this.urlContratoSocial = urlContratoSocial;
    }

    public Double getCapitalSocial() {
        return this.capitalSocial;
    }

    public Empresa capitalSocial(Double capitalSocial) {
        this.setCapitalSocial(capitalSocial);
        return this;
    }

    public void setCapitalSocial(Double capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public Set<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        if (this.funcionarios != null) {
            this.funcionarios.forEach(i -> i.setEmpresa(null));
        }
        if (funcionarios != null) {
            funcionarios.forEach(i -> i.setEmpresa(this));
        }
        this.funcionarios = funcionarios;
    }

    public Empresa funcionarios(Set<Funcionario> funcionarios) {
        this.setFuncionarios(funcionarios);
        return this;
    }

    public Empresa addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.setEmpresa(this);
        return this;
    }

    public Empresa removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        funcionario.setEmpresa(null);
        return this;
    }

    public Set<AnexoEmpresa> getAnexoEmpresas() {
        return this.anexoEmpresas;
    }

    public void setAnexoEmpresas(Set<AnexoEmpresa> anexoEmpresas) {
        if (this.anexoEmpresas != null) {
            this.anexoEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (anexoEmpresas != null) {
            anexoEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.anexoEmpresas = anexoEmpresas;
    }

    public Empresa anexoEmpresas(Set<AnexoEmpresa> anexoEmpresas) {
        this.setAnexoEmpresas(anexoEmpresas);
        return this;
    }

    public Empresa addAnexoEmpresa(AnexoEmpresa anexoEmpresa) {
        this.anexoEmpresas.add(anexoEmpresa);
        anexoEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeAnexoEmpresa(AnexoEmpresa anexoEmpresa) {
        this.anexoEmpresas.remove(anexoEmpresa);
        anexoEmpresa.setEmpresa(null);
        return this;
    }

    public Set<OrdemServico> getOrdemServicos() {
        return this.ordemServicos;
    }

    public void setOrdemServicos(Set<OrdemServico> ordemServicos) {
        if (this.ordemServicos != null) {
            this.ordemServicos.forEach(i -> i.setEmpresa(null));
        }
        if (ordemServicos != null) {
            ordemServicos.forEach(i -> i.setEmpresa(this));
        }
        this.ordemServicos = ordemServicos;
    }

    public Empresa ordemServicos(Set<OrdemServico> ordemServicos) {
        this.setOrdemServicos(ordemServicos);
        return this;
    }

    public Empresa addOrdemServico(OrdemServico ordemServico) {
        this.ordemServicos.add(ordemServico);
        ordemServico.setEmpresa(this);
        return this;
    }

    public Empresa removeOrdemServico(OrdemServico ordemServico) {
        this.ordemServicos.remove(ordemServico);
        ordemServico.setEmpresa(null);
        return this;
    }

    public Set<AnexoRequeridoEmpresa> getAnexoRequeridoEmpresas() {
        return this.anexoRequeridoEmpresas;
    }

    public void setAnexoRequeridoEmpresas(Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas) {
        if (this.anexoRequeridoEmpresas != null) {
            this.anexoRequeridoEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (anexoRequeridoEmpresas != null) {
            anexoRequeridoEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.anexoRequeridoEmpresas = anexoRequeridoEmpresas;
    }

    public Empresa anexoRequeridoEmpresas(Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas) {
        this.setAnexoRequeridoEmpresas(anexoRequeridoEmpresas);
        return this;
    }

    public Empresa addAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresas.add(anexoRequeridoEmpresa);
        anexoRequeridoEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresas.remove(anexoRequeridoEmpresa);
        anexoRequeridoEmpresa.setEmpresa(null);
        return this;
    }

    public Set<ImpostoEmpresa> getImpostoEmpresas() {
        return this.impostoEmpresas;
    }

    public void setImpostoEmpresas(Set<ImpostoEmpresa> impostoEmpresas) {
        if (this.impostoEmpresas != null) {
            this.impostoEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (impostoEmpresas != null) {
            impostoEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.impostoEmpresas = impostoEmpresas;
    }

    public Empresa impostoEmpresas(Set<ImpostoEmpresa> impostoEmpresas) {
        this.setImpostoEmpresas(impostoEmpresas);
        return this;
    }

    public Empresa addImpostoEmpresa(ImpostoEmpresa impostoEmpresa) {
        this.impostoEmpresas.add(impostoEmpresa);
        impostoEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeImpostoEmpresa(ImpostoEmpresa impostoEmpresa) {
        this.impostoEmpresas.remove(impostoEmpresa);
        impostoEmpresa.setEmpresa(null);
        return this;
    }

    public Set<ParcelamentoImposto> getParcelamentoImpostos() {
        return this.parcelamentoImpostos;
    }

    public void setParcelamentoImpostos(Set<ParcelamentoImposto> parcelamentoImpostos) {
        if (this.parcelamentoImpostos != null) {
            this.parcelamentoImpostos.forEach(i -> i.setEmpresa(null));
        }
        if (parcelamentoImpostos != null) {
            parcelamentoImpostos.forEach(i -> i.setEmpresa(this));
        }
        this.parcelamentoImpostos = parcelamentoImpostos;
    }

    public Empresa parcelamentoImpostos(Set<ParcelamentoImposto> parcelamentoImpostos) {
        this.setParcelamentoImpostos(parcelamentoImpostos);
        return this;
    }

    public Empresa addParcelamentoImposto(ParcelamentoImposto parcelamentoImposto) {
        this.parcelamentoImpostos.add(parcelamentoImposto);
        parcelamentoImposto.setEmpresa(this);
        return this;
    }

    public Empresa removeParcelamentoImposto(ParcelamentoImposto parcelamentoImposto) {
        this.parcelamentoImpostos.remove(parcelamentoImposto);
        parcelamentoImposto.setEmpresa(null);
        return this;
    }

    public Set<AssinaturaEmpresa> getAssinaturaEmpresas() {
        return this.assinaturaEmpresas;
    }

    public void setAssinaturaEmpresas(Set<AssinaturaEmpresa> assinaturaEmpresas) {
        if (this.assinaturaEmpresas != null) {
            this.assinaturaEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (assinaturaEmpresas != null) {
            assinaturaEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.assinaturaEmpresas = assinaturaEmpresas;
    }

    public Empresa assinaturaEmpresas(Set<AssinaturaEmpresa> assinaturaEmpresas) {
        this.setAssinaturaEmpresas(assinaturaEmpresas);
        return this;
    }

    public Empresa addAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresas.add(assinaturaEmpresa);
        assinaturaEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresas.remove(assinaturaEmpresa);
        assinaturaEmpresa.setEmpresa(null);
        return this;
    }

    public Set<DepartamentoEmpresa> getDepartamentoEmpresas() {
        return this.departamentoEmpresas;
    }

    public void setDepartamentoEmpresas(Set<DepartamentoEmpresa> departamentoEmpresas) {
        if (this.departamentoEmpresas != null) {
            this.departamentoEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (departamentoEmpresas != null) {
            departamentoEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.departamentoEmpresas = departamentoEmpresas;
    }

    public Empresa departamentoEmpresas(Set<DepartamentoEmpresa> departamentoEmpresas) {
        this.setDepartamentoEmpresas(departamentoEmpresas);
        return this;
    }

    public Empresa addDepartamentoEmpresa(DepartamentoEmpresa departamentoEmpresa) {
        this.departamentoEmpresas.add(departamentoEmpresa);
        departamentoEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeDepartamentoEmpresa(DepartamentoEmpresa departamentoEmpresa) {
        this.departamentoEmpresas.remove(departamentoEmpresa);
        departamentoEmpresa.setEmpresa(null);
        return this;
    }

    public Set<TarefaEmpresa> getTarefaEmpresas() {
        return this.tarefaEmpresas;
    }

    public void setTarefaEmpresas(Set<TarefaEmpresa> tarefaEmpresas) {
        if (this.tarefaEmpresas != null) {
            this.tarefaEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (tarefaEmpresas != null) {
            tarefaEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.tarefaEmpresas = tarefaEmpresas;
    }

    public Empresa tarefaEmpresas(Set<TarefaEmpresa> tarefaEmpresas) {
        this.setTarefaEmpresas(tarefaEmpresas);
        return this;
    }

    public Empresa addTarefaEmpresa(TarefaEmpresa tarefaEmpresa) {
        this.tarefaEmpresas.add(tarefaEmpresa);
        tarefaEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeTarefaEmpresa(TarefaEmpresa tarefaEmpresa) {
        this.tarefaEmpresas.remove(tarefaEmpresa);
        tarefaEmpresa.setEmpresa(null);
        return this;
    }

    public Set<EnderecoEmpresa> getEnderecoEmpresas() {
        return this.enderecoEmpresas;
    }

    public void setEnderecoEmpresas(Set<EnderecoEmpresa> enderecoEmpresas) {
        if (this.enderecoEmpresas != null) {
            this.enderecoEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (enderecoEmpresas != null) {
            enderecoEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.enderecoEmpresas = enderecoEmpresas;
    }

    public Empresa enderecoEmpresas(Set<EnderecoEmpresa> enderecoEmpresas) {
        this.setEnderecoEmpresas(enderecoEmpresas);
        return this;
    }

    public Empresa addEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
        this.enderecoEmpresas.add(enderecoEmpresa);
        enderecoEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
        this.enderecoEmpresas.remove(enderecoEmpresa);
        enderecoEmpresa.setEmpresa(null);
        return this;
    }

    public Set<AtividadeEmpresa> getAtividadeEmpresas() {
        return this.atividadeEmpresas;
    }

    public void setAtividadeEmpresas(Set<AtividadeEmpresa> atividadeEmpresas) {
        if (this.atividadeEmpresas != null) {
            this.atividadeEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (atividadeEmpresas != null) {
            atividadeEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.atividadeEmpresas = atividadeEmpresas;
    }

    public Empresa atividadeEmpresas(Set<AtividadeEmpresa> atividadeEmpresas) {
        this.setAtividadeEmpresas(atividadeEmpresas);
        return this;
    }

    public Empresa addAtividadeEmpresa(AtividadeEmpresa atividadeEmpresa) {
        this.atividadeEmpresas.add(atividadeEmpresa);
        atividadeEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeAtividadeEmpresa(AtividadeEmpresa atividadeEmpresa) {
        this.atividadeEmpresas.remove(atividadeEmpresa);
        atividadeEmpresa.setEmpresa(null);
        return this;
    }

    public Set<Socio> getSocios() {
        return this.socios;
    }

    public void setSocios(Set<Socio> socios) {
        if (this.socios != null) {
            this.socios.forEach(i -> i.setEmpresa(null));
        }
        if (socios != null) {
            socios.forEach(i -> i.setEmpresa(this));
        }
        this.socios = socios;
    }

    public Empresa socios(Set<Socio> socios) {
        this.setSocios(socios);
        return this;
    }

    public Empresa addSocio(Socio socio) {
        this.socios.add(socio);
        socio.setEmpresa(this);
        return this;
    }

    public Empresa removeSocio(Socio socio) {
        this.socios.remove(socio);
        socio.setEmpresa(null);
        return this;
    }

    public Set<CertificadoDigital> getCertificadoDigitals() {
        return this.certificadoDigitals;
    }

    public void setCertificadoDigitals(Set<CertificadoDigital> certificadoDigitals) {
        if (this.certificadoDigitals != null) {
            this.certificadoDigitals.forEach(i -> i.setEmpresa(null));
        }
        if (certificadoDigitals != null) {
            certificadoDigitals.forEach(i -> i.setEmpresa(this));
        }
        this.certificadoDigitals = certificadoDigitals;
    }

    public Empresa certificadoDigitals(Set<CertificadoDigital> certificadoDigitals) {
        this.setCertificadoDigitals(certificadoDigitals);
        return this;
    }

    public Empresa addCertificadoDigital(CertificadoDigital certificadoDigital) {
        this.certificadoDigitals.add(certificadoDigital);
        certificadoDigital.setEmpresa(this);
        return this;
    }

    public Empresa removeCertificadoDigital(CertificadoDigital certificadoDigital) {
        this.certificadoDigitals.remove(certificadoDigital);
        certificadoDigital.setEmpresa(null);
        return this;
    }

    public Set<OpcaoRazaoSocialEmpresa> getOpcaoRazaoSocialEmpresas() {
        return this.opcaoRazaoSocialEmpresas;
    }

    public void setOpcaoRazaoSocialEmpresas(Set<OpcaoRazaoSocialEmpresa> opcaoRazaoSocialEmpresas) {
        if (this.opcaoRazaoSocialEmpresas != null) {
            this.opcaoRazaoSocialEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (opcaoRazaoSocialEmpresas != null) {
            opcaoRazaoSocialEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.opcaoRazaoSocialEmpresas = opcaoRazaoSocialEmpresas;
    }

    public Empresa opcaoRazaoSocialEmpresas(Set<OpcaoRazaoSocialEmpresa> opcaoRazaoSocialEmpresas) {
        this.setOpcaoRazaoSocialEmpresas(opcaoRazaoSocialEmpresas);
        return this;
    }

    public Empresa addOpcaoRazaoSocialEmpresa(OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa) {
        this.opcaoRazaoSocialEmpresas.add(opcaoRazaoSocialEmpresa);
        opcaoRazaoSocialEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeOpcaoRazaoSocialEmpresa(OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa) {
        this.opcaoRazaoSocialEmpresas.remove(opcaoRazaoSocialEmpresa);
        opcaoRazaoSocialEmpresa.setEmpresa(null);
        return this;
    }

    public Set<OpcaoNomeFantasiaEmpresa> getOpcaoNomeFantasiaEmpresas() {
        return this.opcaoNomeFantasiaEmpresas;
    }

    public void setOpcaoNomeFantasiaEmpresas(Set<OpcaoNomeFantasiaEmpresa> opcaoNomeFantasiaEmpresas) {
        if (this.opcaoNomeFantasiaEmpresas != null) {
            this.opcaoNomeFantasiaEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (opcaoNomeFantasiaEmpresas != null) {
            opcaoNomeFantasiaEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.opcaoNomeFantasiaEmpresas = opcaoNomeFantasiaEmpresas;
    }

    public Empresa opcaoNomeFantasiaEmpresas(Set<OpcaoNomeFantasiaEmpresa> opcaoNomeFantasiaEmpresas) {
        this.setOpcaoNomeFantasiaEmpresas(opcaoNomeFantasiaEmpresas);
        return this;
    }

    public Empresa addOpcaoNomeFantasiaEmpresa(OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa) {
        this.opcaoNomeFantasiaEmpresas.add(opcaoNomeFantasiaEmpresa);
        opcaoNomeFantasiaEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeOpcaoNomeFantasiaEmpresa(OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa) {
        this.opcaoNomeFantasiaEmpresas.remove(opcaoNomeFantasiaEmpresa);
        opcaoNomeFantasiaEmpresa.setEmpresa(null);
        return this;
    }

    public Set<TermoAdesaoEmpresa> getTermoAdesaoEmpresas() {
        return this.termoAdesaoEmpresas;
    }

    public void setTermoAdesaoEmpresas(Set<TermoAdesaoEmpresa> termoAdesaoEmpresas) {
        if (this.termoAdesaoEmpresas != null) {
            this.termoAdesaoEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (termoAdesaoEmpresas != null) {
            termoAdesaoEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.termoAdesaoEmpresas = termoAdesaoEmpresas;
    }

    public Empresa termoAdesaoEmpresas(Set<TermoAdesaoEmpresa> termoAdesaoEmpresas) {
        this.setTermoAdesaoEmpresas(termoAdesaoEmpresas);
        return this;
    }

    public Empresa addTermoAdesaoEmpresa(TermoAdesaoEmpresa termoAdesaoEmpresa) {
        this.termoAdesaoEmpresas.add(termoAdesaoEmpresa);
        termoAdesaoEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeTermoAdesaoEmpresa(TermoAdesaoEmpresa termoAdesaoEmpresa) {
        this.termoAdesaoEmpresas.remove(termoAdesaoEmpresa);
        termoAdesaoEmpresa.setEmpresa(null);
        return this;
    }

    public Set<SegmentoCnae> getSegmentoCnaes() {
        return this.segmentoCnaes;
    }

    public void setSegmentoCnaes(Set<SegmentoCnae> segmentoCnaes) {
        this.segmentoCnaes = segmentoCnaes;
    }

    public Empresa segmentoCnaes(Set<SegmentoCnae> segmentoCnaes) {
        this.setSegmentoCnaes(segmentoCnaes);
        return this;
    }

    public Empresa addSegmentoCnae(SegmentoCnae segmentoCnae) {
        this.segmentoCnaes.add(segmentoCnae);
        return this;
    }

    public Empresa removeSegmentoCnae(SegmentoCnae segmentoCnae) {
        this.segmentoCnaes.remove(segmentoCnae);
        return this;
    }

    public EmpresaModelo getEmpresaModelo() {
        return this.empresaModelo;
    }

    public void setEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelo = empresaModelo;
    }

    public Empresa empresaModelo(EmpresaModelo empresaModelo) {
        this.setEmpresaModelo(empresaModelo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empresa)) {
            return false;
        }
        return getId() != null && getId().equals(((Empresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", nomeFantasia='" + getNomeFantasia() + "'" +
            ", descricaoDoNegocio='" + getDescricaoDoNegocio() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", dataAbertura='" + getDataAbertura() + "'" +
            ", urlContratoSocial='" + getUrlContratoSocial() + "'" +
            ", capitalSocial=" + getCapitalSocial() +
            "}";
    }
}
