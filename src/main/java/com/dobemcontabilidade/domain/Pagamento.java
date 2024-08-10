package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pagamento.
 */
@Entity
@Table(name = "pagamento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_cobranca")
    private Instant dataCobranca;

    @Column(name = "data_vencimento")
    private Instant dataVencimento;

    @Column(name = "data_pagamento")
    private Instant dataPagamento;

    @Column(name = "valor_pago")
    private Double valorPago;

    @Column(name = "valor_cobrado")
    private Double valorCobrado;

    @Column(name = "acrescimo")
    private Double acrescimo;

    @Column(name = "multa")
    private Double multa;

    @Column(name = "juros")
    private Double juros;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private SituacaoPagamentoEnum situacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "calculoPlanoAssinaturas", "pagamentos", "periodoPagamento", "formaDePagamento", "planoContabil", "empresa" },
        allowSetters = true
    )
    private AssinaturaEmpresa assinaturaEmpresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pagamento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataCobranca() {
        return this.dataCobranca;
    }

    public Pagamento dataCobranca(Instant dataCobranca) {
        this.setDataCobranca(dataCobranca);
        return this;
    }

    public void setDataCobranca(Instant dataCobranca) {
        this.dataCobranca = dataCobranca;
    }

    public Instant getDataVencimento() {
        return this.dataVencimento;
    }

    public Pagamento dataVencimento(Instant dataVencimento) {
        this.setDataVencimento(dataVencimento);
        return this;
    }

    public void setDataVencimento(Instant dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Instant getDataPagamento() {
        return this.dataPagamento;
    }

    public Pagamento dataPagamento(Instant dataPagamento) {
        this.setDataPagamento(dataPagamento);
        return this;
    }

    public void setDataPagamento(Instant dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getValorPago() {
        return this.valorPago;
    }

    public Pagamento valorPago(Double valorPago) {
        this.setValorPago(valorPago);
        return this;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public Double getValorCobrado() {
        return this.valorCobrado;
    }

    public Pagamento valorCobrado(Double valorCobrado) {
        this.setValorCobrado(valorCobrado);
        return this;
    }

    public void setValorCobrado(Double valorCobrado) {
        this.valorCobrado = valorCobrado;
    }

    public Double getAcrescimo() {
        return this.acrescimo;
    }

    public Pagamento acrescimo(Double acrescimo) {
        this.setAcrescimo(acrescimo);
        return this;
    }

    public void setAcrescimo(Double acrescimo) {
        this.acrescimo = acrescimo;
    }

    public Double getMulta() {
        return this.multa;
    }

    public Pagamento multa(Double multa) {
        this.setMulta(multa);
        return this;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Double getJuros() {
        return this.juros;
    }

    public Pagamento juros(Double juros) {
        this.setJuros(juros);
        return this;
    }

    public void setJuros(Double juros) {
        this.juros = juros;
    }

    public SituacaoPagamentoEnum getSituacao() {
        return this.situacao;
    }

    public Pagamento situacao(SituacaoPagamentoEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoPagamentoEnum situacao) {
        this.situacao = situacao;
    }

    public AssinaturaEmpresa getAssinaturaEmpresa() {
        return this.assinaturaEmpresa;
    }

    public void setAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresa = assinaturaEmpresa;
    }

    public Pagamento assinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.setAssinaturaEmpresa(assinaturaEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pagamento)) {
            return false;
        }
        return getId() != null && getId().equals(((Pagamento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pagamento{" +
            "id=" + getId() +
            ", dataCobranca='" + getDataCobranca() + "'" +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", dataPagamento='" + getDataPagamento() + "'" +
            ", valorPago=" + getValorPago() +
            ", valorCobrado=" + getValorCobrado() +
            ", acrescimo=" + getAcrescimo() +
            ", multa=" + getMulta() +
            ", juros=" + getJuros() +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
