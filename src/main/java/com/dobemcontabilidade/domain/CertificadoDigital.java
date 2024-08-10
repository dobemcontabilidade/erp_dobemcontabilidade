package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoCertificadoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
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

    @Lob
    @Column(name = "url_certificado", nullable = false)
    private String urlCertificado;

    @Column(name = "data_contratacao")
    private Instant dataContratacao;

    @Column(name = "validade")
    private Integer validade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_certificado")
    private TipoCertificadoEnum tipoCertificado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "assinaturaEmpresas",
            "funcionarios",
            "departamentoEmpresas",
            "tarefaEmpresas",
            "enderecoEmpresas",
            "atividadeEmpresas",
            "socios",
            "anexoEmpresas",
            "certificadoDigitals",
            "usuarioEmpresas",
            "opcaoRazaoSocialEmpresas",
            "opcaoNomeFantasiaEmpresas",
            "segmentoCnaes",
            "ramo",
            "tributacao",
            "enquadramento",
        },
        allowSetters = true
    )
    private Empresa empresa;

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

    public String getUrlCertificado() {
        return this.urlCertificado;
    }

    public CertificadoDigital urlCertificado(String urlCertificado) {
        this.setUrlCertificado(urlCertificado);
        return this;
    }

    public void setUrlCertificado(String urlCertificado) {
        this.urlCertificado = urlCertificado;
    }

    public Instant getDataContratacao() {
        return this.dataContratacao;
    }

    public CertificadoDigital dataContratacao(Instant dataContratacao) {
        this.setDataContratacao(dataContratacao);
        return this;
    }

    public void setDataContratacao(Instant dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Integer getValidade() {
        return this.validade;
    }

    public CertificadoDigital validade(Integer validade) {
        this.setValidade(validade);
        return this;
    }

    public void setValidade(Integer validade) {
        this.validade = validade;
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

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public CertificadoDigital empresa(Empresa empresa) {
        this.setEmpresa(empresa);
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
            ", urlCertificado='" + getUrlCertificado() + "'" +
            ", dataContratacao='" + getDataContratacao() + "'" +
            ", validade=" + getValidade() +
            ", tipoCertificado='" + getTipoCertificado() + "'" +
            "}";
    }
}
