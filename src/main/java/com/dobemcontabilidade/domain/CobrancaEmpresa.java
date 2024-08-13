package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoCobrancaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CobrancaEmpresa.
 */
@Entity
@Table(name = "cobranca_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CobrancaEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_cobranca")
    private Instant dataCobranca;

    @Column(name = "valor_pago")
    private Double valorPago;

    @Column(name = "url_cobranca")
    private String urlCobranca;

    @Column(name = "url_arquivo")
    private String urlArquivo;

    @Column(name = "valor_cobrado")
    private Double valorCobrado;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_cobranca")
    private SituacaoCobrancaEnum situacaoCobranca;

    @ManyToOne(optional = false)
    @NotNull
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
    private AssinaturaEmpresa assinaturaEmpresa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "assinaturaEmpresas", "cobrancaEmpresas" }, allowSetters = true)
    private FormaDePagamento formaDePagamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CobrancaEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataCobranca() {
        return this.dataCobranca;
    }

    public CobrancaEmpresa dataCobranca(Instant dataCobranca) {
        this.setDataCobranca(dataCobranca);
        return this;
    }

    public void setDataCobranca(Instant dataCobranca) {
        this.dataCobranca = dataCobranca;
    }

    public Double getValorPago() {
        return this.valorPago;
    }

    public CobrancaEmpresa valorPago(Double valorPago) {
        this.setValorPago(valorPago);
        return this;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public String getUrlCobranca() {
        return this.urlCobranca;
    }

    public CobrancaEmpresa urlCobranca(String urlCobranca) {
        this.setUrlCobranca(urlCobranca);
        return this;
    }

    public void setUrlCobranca(String urlCobranca) {
        this.urlCobranca = urlCobranca;
    }

    public String getUrlArquivo() {
        return this.urlArquivo;
    }

    public CobrancaEmpresa urlArquivo(String urlArquivo) {
        this.setUrlArquivo(urlArquivo);
        return this;
    }

    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }

    public Double getValorCobrado() {
        return this.valorCobrado;
    }

    public CobrancaEmpresa valorCobrado(Double valorCobrado) {
        this.setValorCobrado(valorCobrado);
        return this;
    }

    public void setValorCobrado(Double valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public SituacaoCobrancaEnum getSituacaoCobranca() {
        return this.situacaoCobranca;
    }

    public CobrancaEmpresa situacaoCobranca(SituacaoCobrancaEnum situacaoCobranca) {
        this.setSituacaoCobranca(situacaoCobranca);
        return this;
    }

    public void setSituacaoCobranca(SituacaoCobrancaEnum situacaoCobranca) {
        this.situacaoCobranca = situacaoCobranca;
    }

    public AssinaturaEmpresa getAssinaturaEmpresa() {
        return this.assinaturaEmpresa;
    }

    public void setAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresa = assinaturaEmpresa;
    }

    public CobrancaEmpresa assinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.setAssinaturaEmpresa(assinaturaEmpresa);
        return this;
    }

    public FormaDePagamento getFormaDePagamento() {
        return this.formaDePagamento;
    }

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public CobrancaEmpresa formaDePagamento(FormaDePagamento formaDePagamento) {
        this.setFormaDePagamento(formaDePagamento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CobrancaEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((CobrancaEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CobrancaEmpresa{" +
            "id=" + getId() +
            ", dataCobranca='" + getDataCobranca() + "'" +
            ", valorPago=" + getValorPago() +
            ", urlCobranca='" + getUrlCobranca() + "'" +
            ", urlArquivo='" + getUrlArquivo() + "'" +
            ", valorCobrado=" + getValorCobrado() +
            ", situacaoCobranca='" + getSituacaoCobranca() + "'" +
            "}";
    }
}
