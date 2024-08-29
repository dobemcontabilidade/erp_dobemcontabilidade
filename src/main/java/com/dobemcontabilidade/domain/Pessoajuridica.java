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
 * A Pessoajuridica.
 */
@Entity
@Table(name = "pessoajuridica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pessoajuridica implements Serializable {

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

    @Size(max = 20)
    @Column(name = "cnpj", length = 20)
    private String cnpj;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoajuridica")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "redeSocial", "pessoajuridica" }, allowSetters = true)
    private Set<RedeSocialEmpresa> redeSocialEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoaJuridica")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoaJuridica", "certificadoDigital", "fornecedorCertificado" }, allowSetters = true)
    private Set<CertificadoDigitalEmpresa> certificadoDigitalEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoaJuridica")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoaJuridica" }, allowSetters = true)
    private Set<DocsEmpresa> docsEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoaJuridica")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cidade", "pessoaJuridica" }, allowSetters = true)
    private Set<EnderecoEmpresa> enderecoEmpresas = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "pessoaJuridica", "socios", "assinaturaEmpresas", "tributacao", "ramo", "enquadramento" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pessoaJuridica")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pessoajuridica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public Pessoajuridica razaoSocial(String razaoSocial) {
        this.setRazaoSocial(razaoSocial);
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return this.nomeFantasia;
    }

    public Pessoajuridica nomeFantasia(String nomeFantasia) {
        this.setNomeFantasia(nomeFantasia);
        return this;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public Pessoajuridica cnpj(String cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Set<RedeSocialEmpresa> getRedeSocialEmpresas() {
        return this.redeSocialEmpresas;
    }

    public void setRedeSocialEmpresas(Set<RedeSocialEmpresa> redeSocialEmpresas) {
        if (this.redeSocialEmpresas != null) {
            this.redeSocialEmpresas.forEach(i -> i.setPessoajuridica(null));
        }
        if (redeSocialEmpresas != null) {
            redeSocialEmpresas.forEach(i -> i.setPessoajuridica(this));
        }
        this.redeSocialEmpresas = redeSocialEmpresas;
    }

    public Pessoajuridica redeSocialEmpresas(Set<RedeSocialEmpresa> redeSocialEmpresas) {
        this.setRedeSocialEmpresas(redeSocialEmpresas);
        return this;
    }

    public Pessoajuridica addRedeSocialEmpresa(RedeSocialEmpresa redeSocialEmpresa) {
        this.redeSocialEmpresas.add(redeSocialEmpresa);
        redeSocialEmpresa.setPessoajuridica(this);
        return this;
    }

    public Pessoajuridica removeRedeSocialEmpresa(RedeSocialEmpresa redeSocialEmpresa) {
        this.redeSocialEmpresas.remove(redeSocialEmpresa);
        redeSocialEmpresa.setPessoajuridica(null);
        return this;
    }

    public Set<CertificadoDigitalEmpresa> getCertificadoDigitalEmpresas() {
        return this.certificadoDigitalEmpresas;
    }

    public void setCertificadoDigitalEmpresas(Set<CertificadoDigitalEmpresa> certificadoDigitalEmpresas) {
        if (this.certificadoDigitalEmpresas != null) {
            this.certificadoDigitalEmpresas.forEach(i -> i.setPessoaJuridica(null));
        }
        if (certificadoDigitalEmpresas != null) {
            certificadoDigitalEmpresas.forEach(i -> i.setPessoaJuridica(this));
        }
        this.certificadoDigitalEmpresas = certificadoDigitalEmpresas;
    }

    public Pessoajuridica certificadoDigitalEmpresas(Set<CertificadoDigitalEmpresa> certificadoDigitalEmpresas) {
        this.setCertificadoDigitalEmpresas(certificadoDigitalEmpresas);
        return this;
    }

    public Pessoajuridica addCertificadoDigitalEmpresa(CertificadoDigitalEmpresa certificadoDigitalEmpresa) {
        this.certificadoDigitalEmpresas.add(certificadoDigitalEmpresa);
        certificadoDigitalEmpresa.setPessoaJuridica(this);
        return this;
    }

    public Pessoajuridica removeCertificadoDigitalEmpresa(CertificadoDigitalEmpresa certificadoDigitalEmpresa) {
        this.certificadoDigitalEmpresas.remove(certificadoDigitalEmpresa);
        certificadoDigitalEmpresa.setPessoaJuridica(null);
        return this;
    }

    public Set<DocsEmpresa> getDocsEmpresas() {
        return this.docsEmpresas;
    }

    public void setDocsEmpresas(Set<DocsEmpresa> docsEmpresas) {
        if (this.docsEmpresas != null) {
            this.docsEmpresas.forEach(i -> i.setPessoaJuridica(null));
        }
        if (docsEmpresas != null) {
            docsEmpresas.forEach(i -> i.setPessoaJuridica(this));
        }
        this.docsEmpresas = docsEmpresas;
    }

    public Pessoajuridica docsEmpresas(Set<DocsEmpresa> docsEmpresas) {
        this.setDocsEmpresas(docsEmpresas);
        return this;
    }

    public Pessoajuridica addDocsEmpresa(DocsEmpresa docsEmpresa) {
        this.docsEmpresas.add(docsEmpresa);
        docsEmpresa.setPessoaJuridica(this);
        return this;
    }

    public Pessoajuridica removeDocsEmpresa(DocsEmpresa docsEmpresa) {
        this.docsEmpresas.remove(docsEmpresa);
        docsEmpresa.setPessoaJuridica(null);
        return this;
    }

    public Set<EnderecoEmpresa> getEnderecoEmpresas() {
        return this.enderecoEmpresas;
    }

    public void setEnderecoEmpresas(Set<EnderecoEmpresa> enderecoEmpresas) {
        if (this.enderecoEmpresas != null) {
            this.enderecoEmpresas.forEach(i -> i.setPessoaJuridica(null));
        }
        if (enderecoEmpresas != null) {
            enderecoEmpresas.forEach(i -> i.setPessoaJuridica(this));
        }
        this.enderecoEmpresas = enderecoEmpresas;
    }

    public Pessoajuridica enderecoEmpresas(Set<EnderecoEmpresa> enderecoEmpresas) {
        this.setEnderecoEmpresas(enderecoEmpresas);
        return this;
    }

    public Pessoajuridica addEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
        this.enderecoEmpresas.add(enderecoEmpresa);
        enderecoEmpresa.setPessoaJuridica(this);
        return this;
    }

    public Pessoajuridica removeEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
        this.enderecoEmpresas.remove(enderecoEmpresa);
        enderecoEmpresa.setPessoaJuridica(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        if (this.empresa != null) {
            this.empresa.setPessoaJuridica(null);
        }
        if (empresa != null) {
            empresa.setPessoaJuridica(this);
        }
        this.empresa = empresa;
    }

    public Pessoajuridica empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pessoajuridica)) {
            return false;
        }
        return getId() != null && getId().equals(((Pessoajuridica) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pessoajuridica{" +
            "id=" + getId() +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", nomeFantasia='" + getNomeFantasia() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            "}";
    }
}
