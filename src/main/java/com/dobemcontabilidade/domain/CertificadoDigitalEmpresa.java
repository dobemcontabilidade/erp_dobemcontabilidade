package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CertificadoDigitalEmpresa.
 */
@Entity
@Table(name = "certificado_digital_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CertificadoDigitalEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "url_certificado", nullable = false)
    private String urlCertificado;

    @Column(name = "data_contratacao")
    private Instant dataContratacao;

    @Column(name = "data_vencimento")
    private Instant dataVencimento;

    @Column(name = "dias_uso")
    private Integer diasUso;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "redeSocialEmpresas", "certificadoDigitalEmpresas", "docsEmpresas", "enderecoEmpresas", "empresa" },
        allowSetters = true
    )
    private Pessoajuridica pessoaJuridica;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "certificadoDigitalEmpresas" }, allowSetters = true)
    private CertificadoDigital certificadoDigital;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "certificadoDigitalEmpresas" }, allowSetters = true)
    private FornecedorCertificado fornecedorCertificado;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CertificadoDigitalEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlCertificado() {
        return this.urlCertificado;
    }

    public CertificadoDigitalEmpresa urlCertificado(String urlCertificado) {
        this.setUrlCertificado(urlCertificado);
        return this;
    }

    public void setUrlCertificado(String urlCertificado) {
        this.urlCertificado = urlCertificado;
    }

    public Instant getDataContratacao() {
        return this.dataContratacao;
    }

    public CertificadoDigitalEmpresa dataContratacao(Instant dataContratacao) {
        this.setDataContratacao(dataContratacao);
        return this;
    }

    public void setDataContratacao(Instant dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Instant getDataVencimento() {
        return this.dataVencimento;
    }

    public CertificadoDigitalEmpresa dataVencimento(Instant dataVencimento) {
        this.setDataVencimento(dataVencimento);
        return this;
    }

    public void setDataVencimento(Instant dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Integer getDiasUso() {
        return this.diasUso;
    }

    public CertificadoDigitalEmpresa diasUso(Integer diasUso) {
        this.setDiasUso(diasUso);
        return this;
    }

    public void setDiasUso(Integer diasUso) {
        this.diasUso = diasUso;
    }

    public Pessoajuridica getPessoaJuridica() {
        return this.pessoaJuridica;
    }

    public void setPessoaJuridica(Pessoajuridica pessoajuridica) {
        this.pessoaJuridica = pessoajuridica;
    }

    public CertificadoDigitalEmpresa pessoaJuridica(Pessoajuridica pessoajuridica) {
        this.setPessoaJuridica(pessoajuridica);
        return this;
    }

    public CertificadoDigital getCertificadoDigital() {
        return this.certificadoDigital;
    }

    public void setCertificadoDigital(CertificadoDigital certificadoDigital) {
        this.certificadoDigital = certificadoDigital;
    }

    public CertificadoDigitalEmpresa certificadoDigital(CertificadoDigital certificadoDigital) {
        this.setCertificadoDigital(certificadoDigital);
        return this;
    }

    public FornecedorCertificado getFornecedorCertificado() {
        return this.fornecedorCertificado;
    }

    public void setFornecedorCertificado(FornecedorCertificado fornecedorCertificado) {
        this.fornecedorCertificado = fornecedorCertificado;
    }

    public CertificadoDigitalEmpresa fornecedorCertificado(FornecedorCertificado fornecedorCertificado) {
        this.setFornecedorCertificado(fornecedorCertificado);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CertificadoDigitalEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((CertificadoDigitalEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CertificadoDigitalEmpresa{" +
            "id=" + getId() +
            ", urlCertificado='" + getUrlCertificado() + "'" +
            ", dataContratacao='" + getDataContratacao() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", diasUso=" + getDiasUso() +
            "}";
    }
}
