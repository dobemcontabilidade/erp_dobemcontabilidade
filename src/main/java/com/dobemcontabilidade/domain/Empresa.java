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
            "calculoPlanoAssinaturas", "pagamentos", "periodoPagamento", "formaDePagamento", "planoContaAzul", "planoContabil", "empresa",
        },
        allowSetters = true
    )
    private Set<AssinaturaEmpresa> assinaturaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "departamentoFuncionarios", "empresa" }, allowSetters = true)
    private Set<Funcionario> funcionarios = new HashSet<>();

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
    @JsonIgnoreProperties(value = { "empresa" }, allowSetters = true)
    private Set<AtividadeEmpresa> atividadeEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "profissaos", "empresa" }, allowSetters = true)
    private Set<Socio> socios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa" }, allowSetters = true)
    private Set<AnexoEmpresa> anexoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa" }, allowSetters = true)
    private Set<CertificadoDigital> certificadoDigitals = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "empresa" }, allowSetters = true)
    private Set<UsuarioEmpresa> usuarioEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa" }, allowSetters = true)
    private Set<OpcaoRazaoSocialEmpresa> opcaoRazaoSocialEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa" }, allowSetters = true)
    private Set<OpcaoNomeFantasiaEmpresa> opcaoNomeFantasiaEmpresas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "empresas", "adicionalRamos", "valorBaseRamos" }, allowSetters = true)
    private Ramo ramo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "empresas", "adicionalTributacaos" }, allowSetters = true)
    private Tributacao tributacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "empresas", "adicionalEnquadramentos" }, allowSetters = true)
    private Enquadramento enquadramento;

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

    public Set<UsuarioEmpresa> getUsuarioEmpresas() {
        return this.usuarioEmpresas;
    }

    public void setUsuarioEmpresas(Set<UsuarioEmpresa> usuarioEmpresas) {
        if (this.usuarioEmpresas != null) {
            this.usuarioEmpresas.forEach(i -> i.setEmpresa(null));
        }
        if (usuarioEmpresas != null) {
            usuarioEmpresas.forEach(i -> i.setEmpresa(this));
        }
        this.usuarioEmpresas = usuarioEmpresas;
    }

    public Empresa usuarioEmpresas(Set<UsuarioEmpresa> usuarioEmpresas) {
        this.setUsuarioEmpresas(usuarioEmpresas);
        return this;
    }

    public Empresa addUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.usuarioEmpresas.add(usuarioEmpresa);
        usuarioEmpresa.setEmpresa(this);
        return this;
    }

    public Empresa removeUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.usuarioEmpresas.remove(usuarioEmpresa);
        usuarioEmpresa.setEmpresa(null);
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

    public Ramo getRamo() {
        return this.ramo;
    }

    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    public Empresa ramo(Ramo ramo) {
        this.setRamo(ramo);
        return this;
    }

    public Tributacao getTributacao() {
        return this.tributacao;
    }

    public void setTributacao(Tributacao tributacao) {
        this.tributacao = tributacao;
    }

    public Empresa tributacao(Tributacao tributacao) {
        this.setTributacao(tributacao);
        return this;
    }

    public Enquadramento getEnquadramento() {
        return this.enquadramento;
    }

    public void setEnquadramento(Enquadramento enquadramento) {
        this.enquadramento = enquadramento;
    }

    public Empresa enquadramento(Enquadramento enquadramento) {
        this.setEnquadramento(enquadramento);
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
