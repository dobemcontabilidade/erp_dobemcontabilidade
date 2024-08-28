package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoTermoContratoAssinadoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TermoContratoAssinaturaEmpresa.
 */
@Entity
@Table(name = "termo_contrato_assinatura_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoContratoAssinaturaEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_assinatura")
    private Instant dataAssinatura;

    @Column(name = "data_envio_email")
    private Instant dataEnvioEmail;

    @Lob
    @Column(name = "url_documento_assinado")
    private String urlDocumentoAssinado;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoTermoContratoAssinadoEnum situacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "termoContratoAssinaturaEmpresas" }, allowSetters = true)
    private TermoContratoContabil termoContratoContabil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "termoContratoAssinaturaEmpresas", "periodoPagamento", "formaDePagamento", "empresa" },
        allowSetters = true
    )
    private AssinaturaEmpresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TermoContratoAssinaturaEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAssinatura() {
        return this.dataAssinatura;
    }

    public TermoContratoAssinaturaEmpresa dataAssinatura(Instant dataAssinatura) {
        this.setDataAssinatura(dataAssinatura);
        return this;
    }

    public void setDataAssinatura(Instant dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public Instant getDataEnvioEmail() {
        return this.dataEnvioEmail;
    }

    public TermoContratoAssinaturaEmpresa dataEnvioEmail(Instant dataEnvioEmail) {
        this.setDataEnvioEmail(dataEnvioEmail);
        return this;
    }

    public void setDataEnvioEmail(Instant dataEnvioEmail) {
        this.dataEnvioEmail = dataEnvioEmail;
    }

    public String getUrlDocumentoAssinado() {
        return this.urlDocumentoAssinado;
    }

    public TermoContratoAssinaturaEmpresa urlDocumentoAssinado(String urlDocumentoAssinado) {
        this.setUrlDocumentoAssinado(urlDocumentoAssinado);
        return this;
    }

    public void setUrlDocumentoAssinado(String urlDocumentoAssinado) {
        this.urlDocumentoAssinado = urlDocumentoAssinado;
    }

    public SituacaoTermoContratoAssinadoEnum getSituacao() {
        return this.situacao;
    }

    public TermoContratoAssinaturaEmpresa situacao(SituacaoTermoContratoAssinadoEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoTermoContratoAssinadoEnum situacao) {
        this.situacao = situacao;
    }

    public TermoContratoContabil getTermoContratoContabil() {
        return this.termoContratoContabil;
    }

    public void setTermoContratoContabil(TermoContratoContabil termoContratoContabil) {
        this.termoContratoContabil = termoContratoContabil;
    }

    public TermoContratoAssinaturaEmpresa termoContratoContabil(TermoContratoContabil termoContratoContabil) {
        this.setTermoContratoContabil(termoContratoContabil);
        return this;
    }

    public AssinaturaEmpresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.empresa = assinaturaEmpresa;
    }

    public TermoContratoAssinaturaEmpresa empresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.setEmpresa(assinaturaEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermoContratoAssinaturaEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((TermoContratoAssinaturaEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoContratoAssinaturaEmpresa{" +
            "id=" + getId() +
            ", dataAssinatura='" + getDataAssinatura() + "'" +
            ", dataEnvioEmail='" + getDataEnvioEmail() + "'" +
            ", urlDocumentoAssinado='" + getUrlDocumentoAssinado() + "'" +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
