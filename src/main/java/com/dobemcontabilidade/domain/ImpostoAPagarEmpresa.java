package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoPagamentoImpostoEnum;
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
 * A ImpostoAPagarEmpresa.
 */
@Entity
@Table(name = "imposto_a_pagar_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImpostoAPagarEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data_vencimento", nullable = false)
    private Instant dataVencimento;

    @NotNull
    @Column(name = "data_pagamento", nullable = false)
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
    @Column(name = "situacao_pagamento_imposto_enum")
    private SituacaoPagamentoImpostoEnum situacaoPagamentoImpostoEnum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "impostoAPagarEmpresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parcelamentoImposto", "impostoAPagarEmpresa" }, allowSetters = true)
    private Set<ImpostoParcelado> impostoParcelados = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "impostoAPagarEmpresas", "empresa", "imposto" }, allowSetters = true)
    private ImpostoEmpresa imposto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ImpostoAPagarEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataVencimento() {
        return this.dataVencimento;
    }

    public ImpostoAPagarEmpresa dataVencimento(Instant dataVencimento) {
        this.setDataVencimento(dataVencimento);
        return this;
    }

    public void setDataVencimento(Instant dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Instant getDataPagamento() {
        return this.dataPagamento;
    }

    public ImpostoAPagarEmpresa dataPagamento(Instant dataPagamento) {
        this.setDataPagamento(dataPagamento);
        return this;
    }

    public void setDataPagamento(Instant dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getValor() {
        return this.valor;
    }

    public ImpostoAPagarEmpresa valor(Double valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getValorMulta() {
        return this.valorMulta;
    }

    public ImpostoAPagarEmpresa valorMulta(Integer valorMulta) {
        this.setValorMulta(valorMulta);
        return this;
    }

    public void setValorMulta(Integer valorMulta) {
        this.valorMulta = valorMulta;
    }

    public String getUrlArquivoPagamento() {
        return this.urlArquivoPagamento;
    }

    public ImpostoAPagarEmpresa urlArquivoPagamento(String urlArquivoPagamento) {
        this.setUrlArquivoPagamento(urlArquivoPagamento);
        return this;
    }

    public void setUrlArquivoPagamento(String urlArquivoPagamento) {
        this.urlArquivoPagamento = urlArquivoPagamento;
    }

    public String getUrlArquivoComprovante() {
        return this.urlArquivoComprovante;
    }

    public ImpostoAPagarEmpresa urlArquivoComprovante(String urlArquivoComprovante) {
        this.setUrlArquivoComprovante(urlArquivoComprovante);
        return this;
    }

    public void setUrlArquivoComprovante(String urlArquivoComprovante) {
        this.urlArquivoComprovante = urlArquivoComprovante;
    }

    public SituacaoPagamentoImpostoEnum getSituacaoPagamentoImpostoEnum() {
        return this.situacaoPagamentoImpostoEnum;
    }

    public ImpostoAPagarEmpresa situacaoPagamentoImpostoEnum(SituacaoPagamentoImpostoEnum situacaoPagamentoImpostoEnum) {
        this.setSituacaoPagamentoImpostoEnum(situacaoPagamentoImpostoEnum);
        return this;
    }

    public void setSituacaoPagamentoImpostoEnum(SituacaoPagamentoImpostoEnum situacaoPagamentoImpostoEnum) {
        this.situacaoPagamentoImpostoEnum = situacaoPagamentoImpostoEnum;
    }

    public Set<ImpostoParcelado> getImpostoParcelados() {
        return this.impostoParcelados;
    }

    public void setImpostoParcelados(Set<ImpostoParcelado> impostoParcelados) {
        if (this.impostoParcelados != null) {
            this.impostoParcelados.forEach(i -> i.setImpostoAPagarEmpresa(null));
        }
        if (impostoParcelados != null) {
            impostoParcelados.forEach(i -> i.setImpostoAPagarEmpresa(this));
        }
        this.impostoParcelados = impostoParcelados;
    }

    public ImpostoAPagarEmpresa impostoParcelados(Set<ImpostoParcelado> impostoParcelados) {
        this.setImpostoParcelados(impostoParcelados);
        return this;
    }

    public ImpostoAPagarEmpresa addImpostoParcelado(ImpostoParcelado impostoParcelado) {
        this.impostoParcelados.add(impostoParcelado);
        impostoParcelado.setImpostoAPagarEmpresa(this);
        return this;
    }

    public ImpostoAPagarEmpresa removeImpostoParcelado(ImpostoParcelado impostoParcelado) {
        this.impostoParcelados.remove(impostoParcelado);
        impostoParcelado.setImpostoAPagarEmpresa(null);
        return this;
    }

    public ImpostoEmpresa getImposto() {
        return this.imposto;
    }

    public void setImposto(ImpostoEmpresa impostoEmpresa) {
        this.imposto = impostoEmpresa;
    }

    public ImpostoAPagarEmpresa imposto(ImpostoEmpresa impostoEmpresa) {
        this.setImposto(impostoEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImpostoAPagarEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((ImpostoAPagarEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImpostoAPagarEmpresa{" +
            "id=" + getId() +
            ", dataVencimento='" + getDataVencimento() + "'" +
            ", dataPagamento='" + getDataPagamento() + "'" +
            ", valor=" + getValor() +
            ", valorMulta=" + getValorMulta() +
            ", urlArquivoPagamento='" + getUrlArquivoPagamento() + "'" +
            ", urlArquivoComprovante='" + getUrlArquivoComprovante() + "'" +
            ", situacaoPagamentoImpostoEnum='" + getSituacaoPagamentoImpostoEnum() + "'" +
            "}";
    }
}
