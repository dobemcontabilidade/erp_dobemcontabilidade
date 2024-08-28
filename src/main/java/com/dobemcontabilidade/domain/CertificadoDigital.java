package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoCertificadoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CertificadoDigital.
 */
@Entity
@Table(name = "certificado_digital")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CertificadoDigital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 300)
    @Column(name = "nome", length = 300)
    private String nome;

    @Size(max = 20)
    @Column(name = "sigla", length = 20)
    private String sigla;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_certificado")
    private TipoCertificadoEnum tipoCertificado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "certificadoDigital")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoaJuridica", "certificadoDigital", "fornecedorCertificado" }, allowSetters = true)
    private Set<CertificadoDigitalEmpresa> certificadoDigitalEmpresas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CertificadoDigital id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public CertificadoDigital nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public CertificadoDigital sigla(String sigla) {
        this.setSigla(sigla);
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public CertificadoDigital descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoCertificadoEnum getTipoCertificado() {
        return this.tipoCertificado;
    }

    public CertificadoDigital tipoCertificado(TipoCertificadoEnum tipoCertificado) {
        this.setTipoCertificado(tipoCertificado);
        return this;
    }

    public void setTipoCertificado(TipoCertificadoEnum tipoCertificado) {
        this.tipoCertificado = tipoCertificado;
    }

    public Set<CertificadoDigitalEmpresa> getCertificadoDigitalEmpresas() {
        return this.certificadoDigitalEmpresas;
    }

    public void setCertificadoDigitalEmpresas(Set<CertificadoDigitalEmpresa> certificadoDigitalEmpresas) {
        if (this.certificadoDigitalEmpresas != null) {
            this.certificadoDigitalEmpresas.forEach(i -> i.setCertificadoDigital(null));
        }
        if (certificadoDigitalEmpresas != null) {
            certificadoDigitalEmpresas.forEach(i -> i.setCertificadoDigital(this));
        }
        this.certificadoDigitalEmpresas = certificadoDigitalEmpresas;
    }

    public CertificadoDigital certificadoDigitalEmpresas(Set<CertificadoDigitalEmpresa> certificadoDigitalEmpresas) {
        this.setCertificadoDigitalEmpresas(certificadoDigitalEmpresas);
        return this;
    }

    public CertificadoDigital addCertificadoDigitalEmpresa(CertificadoDigitalEmpresa certificadoDigitalEmpresa) {
        this.certificadoDigitalEmpresas.add(certificadoDigitalEmpresa);
        certificadoDigitalEmpresa.setCertificadoDigital(this);
        return this;
    }

    public CertificadoDigital removeCertificadoDigitalEmpresa(CertificadoDigitalEmpresa certificadoDigitalEmpresa) {
        this.certificadoDigitalEmpresas.remove(certificadoDigitalEmpresa);
        certificadoDigitalEmpresa.setCertificadoDigital(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CertificadoDigital)) {
            return false;
        }
        return getId() != null && getId().equals(((CertificadoDigital) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CertificadoDigital{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", tipoCertificado='" + getTipoCertificado() + "'" +
            "}";
    }
}
