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
 * A FornecedorCertificado.
 */
@Entity
@Table(name = "fornecedor_certificado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FornecedorCertificado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Size(max = 20)
    @Column(name = "sigla", length = 20)
    private String sigla;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fornecedorCertificado")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoaJuridica", "certificadoDigital", "fornecedorCertificado" }, allowSetters = true)
    private Set<CertificadoDigitalEmpresa> certificadoDigitalEmpresas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FornecedorCertificado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return this.razaoSocial;
    }

    public FornecedorCertificado razaoSocial(String razaoSocial) {
        this.setRazaoSocial(razaoSocial);
        return this;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getSigla() {
        return this.sigla;
    }

    public FornecedorCertificado sigla(String sigla) {
        this.setSigla(sigla);
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public FornecedorCertificado descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<CertificadoDigitalEmpresa> getCertificadoDigitalEmpresas() {
        return this.certificadoDigitalEmpresas;
    }

    public void setCertificadoDigitalEmpresas(Set<CertificadoDigitalEmpresa> certificadoDigitalEmpresas) {
        if (this.certificadoDigitalEmpresas != null) {
            this.certificadoDigitalEmpresas.forEach(i -> i.setFornecedorCertificado(null));
        }
        if (certificadoDigitalEmpresas != null) {
            certificadoDigitalEmpresas.forEach(i -> i.setFornecedorCertificado(this));
        }
        this.certificadoDigitalEmpresas = certificadoDigitalEmpresas;
    }

    public FornecedorCertificado certificadoDigitalEmpresas(Set<CertificadoDigitalEmpresa> certificadoDigitalEmpresas) {
        this.setCertificadoDigitalEmpresas(certificadoDigitalEmpresas);
        return this;
    }

    public FornecedorCertificado addCertificadoDigitalEmpresa(CertificadoDigitalEmpresa certificadoDigitalEmpresa) {
        this.certificadoDigitalEmpresas.add(certificadoDigitalEmpresa);
        certificadoDigitalEmpresa.setFornecedorCertificado(this);
        return this;
    }

    public FornecedorCertificado removeCertificadoDigitalEmpresa(CertificadoDigitalEmpresa certificadoDigitalEmpresa) {
        this.certificadoDigitalEmpresas.remove(certificadoDigitalEmpresa);
        certificadoDigitalEmpresa.setFornecedorCertificado(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FornecedorCertificado)) {
            return false;
        }
        return getId() != null && getId().equals(((FornecedorCertificado) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FornecedorCertificado{" +
            "id=" + getId() +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
