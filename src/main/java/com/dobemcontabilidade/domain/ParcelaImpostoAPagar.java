package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.MesCompetenciaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ParcelaImpostoAPagar.
 */
@Entity
@Table(name = "parcela_imposto_a_pagar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParcelaImpostoAPagar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_parcela")
    private Integer numeroParcela;

    @Column(name = "data_vencimento")
    private Instant dataVencimento;

    @Column(name = "data_pagamento")
    private Instant dataPagamento;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "valor_multa")
    private Integer valorMulta;

    @Column(name = "url_arquivo_pagamento")
    private String urlArquivoPagamento;

    @Column(name = "url_arquivo_comprovante")
    private String urlArquivoComprovante;

    @Enumerated(EnumType.STRING)
    @Column(name = "mes_competencia")
    private MesCompetenciaEnum mesCompetencia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "parcelaImpostoAPagars", "impostoParcelados", "imposto", "empresa" }, allowSetters = true)
    private ParcelamentoImposto parcelamentoImposto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ParcelaImpostoAPagar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroParcela() {
        return this.numeroParcela;
    }

    public ParcelaImpostoAPagar numeroParcela(Integer numeroParcela) {
        this.setNumeroParcela(numeroParcela);
        return this;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Instant getDataVencimento() {
        return this.dataVencimento;
    }

    public ParcelaImpostoAPagar dataVencimento(Instant dataVencimento) {
        this.setDataVencimento(dataVencimento);
        return this;
    }

    public void setDataVencimento(Instant dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Instant getDataPagamento() {
        return this.dataPagamento;
    }

    public ParcelaImpostoAPagar dataPagamento(Instant dataPagamento) {
        this.setDataPagamento(dataPagamento);
        return this;
    }

    public void setDataPagamento(Instant dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getValor() {
        return this.valor;
    }

    public ParcelaImpostoAPagar valor(Double valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getValorMulta() {
        return this.valorMulta;
    }

    public ParcelaImpostoAPagar valorMulta(Integer valorMulta) {
        this.setValorMulta(valorMulta);
        return this;
    }

    public void setValorMulta(Integer valorMulta) {
        this.valorMulta = valorMulta;
    }

    public String getUrlArquivoPagamento() {
        return this.urlArquivoPagamento;
    }

    public ParcelaImpostoAPagar urlArquivoPagamento(String urlArquivoPagamento) {
        this.setUrlArquivoPagamento(urlArquivoPagamento);
        return this;
    }

    public void setUrlArquivoPagamento(String urlArquivoPagamento) {
        this.urlArquivoPagamento = urlArquivoPagamento;
    }

    public String getUrlArquivoComprovante() {
        return this.urlArquivoComprovante;
    }

    public ParcelaImpostoAPagar urlArquivoComprovante(String urlArquivoComprovante) {
        this.setUrlArquivoComprovante(urlArquivoComprovante);
        return this;
    }

    public void setUrlArquivoComprovante(String urlArquivoComprovante) {
        this.urlArquivoComprovante = urlArquivoComprovante;
    }

    public MesCompetenciaEnum getMesCompetencia() {
        return this.mesCompetencia;
    }

    public ParcelaImpostoAPagar mesCompetencia(MesCompetenciaEnum mesCompetencia) {
        this.setMesCompetencia(mesCompetencia);
        return this;
    }

    public void setMesCompetencia(MesCompetenciaEnum mesCompetencia) {
        this.mesCompetencia = mesCompetencia;
    }

    public ParcelamentoImposto getParcelamentoImposto() {
        return this.parcelamentoImposto;
    }

    public void setParcelamentoImposto(ParcelamentoImposto parcelamentoImposto) {
        this.parcelamentoImposto = parcelamentoImposto;
    }

    public ParcelaImpostoAPagar parcelamentoImposto(ParcelamentoImposto parcelamentoImposto) {
        this.setParcelamentoImposto(parcelamentoImposto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParcelaImpostoAPagar)) {
            return false;
        }
        return getId() != null && getId().equals(((ParcelaImpostoAPagar) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParcelaImpostoAPagar{" +
            "id=" + getId() +
            ", numeroParcela=" + getNumeroParcela() +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", dataPagamento='" + getDataPagamento() + "'" +
            ", valor=" + getValor() +
            ", valorMulta=" + getValorMulta() +
            ", urlArquivoPagamento='" + getUrlArquivoPagamento() + "'" +
            ", urlArquivoComprovante='" + getUrlArquivoComprovante() + "'" +
            ", mesCompetencia='" + getMesCompetencia() + "'" +
            "}";
    }
}
