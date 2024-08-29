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

    @Lob
    @Column(name = "descricao_do_negocio")
    private String descricaoDoNegocio;

    @Column(name = "data_abertura")
    private Instant dataAbertura;

    @Column(name = "url_contrato_social")
    private String urlContratoSocial;

    @Column(name = "capital_social")
    private Double capitalSocial;

    @NotNull
    @Column(name = "cnae", nullable = false)
    private String cnae;

    @JsonIgnoreProperties(
        value = { "redeSocialEmpresas", "certificadoDigitalEmpresas", "docsEmpresas", "enderecoEmpresas", "empresa" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoajuridica pessoaJuridica;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoaFisica", "empresa" }, allowSetters = true)
    private Set<Socio> socios = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "termoContratoAssinaturaEmpresas", "periodoPagamento", "formaDePagamento", "empresa" },
        allowSetters = true
    )
    private Set<AssinaturaEmpresa> assinaturaEmpresas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "empresas", "adicionalTributacaos" }, allowSetters = true)
    private Tributacao tributacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "empresas", "adicionalRamos" }, allowSetters = true)
    private Ramo ramo;

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

    public String getCnae() {
        return this.cnae;
    }

    public Empresa cnae(String cnae) {
        this.setCnae(cnae);
        return this;
    }

    public void setCnae(String cnae) {
        this.cnae = cnae;
    }

    public Pessoajuridica getPessoaJuridica() {
        return this.pessoaJuridica;
    }

    public void setPessoaJuridica(Pessoajuridica pessoajuridica) {
        this.pessoaJuridica = pessoajuridica;
    }

    public Empresa pessoaJuridica(Pessoajuridica pessoajuridica) {
        this.setPessoaJuridica(pessoajuridica);
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
            ", descricaoDoNegocio='" + getDescricaoDoNegocio() + "'" +
            ", dataAbertura='" + getDataAbertura() + "'" +
            ", urlContratoSocial='" + getUrlContratoSocial() + "'" +
            ", capitalSocial=" + getCapitalSocial() +
            ", cnae='" + getCnae() + "'" +
            "}";
    }
}
