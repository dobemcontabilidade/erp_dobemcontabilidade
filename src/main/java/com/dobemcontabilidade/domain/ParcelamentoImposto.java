package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoSolicitacaoParcelamentoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ParcelamentoImposto.
 */
@Entity
@Table(name = "parcelamento_imposto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ParcelamentoImposto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dia_vencimento")
    private Integer diaVencimento;

    @Column(name = "numero_parcelas")
    private Integer numeroParcelas;

    @Column(name = "url_arquivo_negociacao")
    private String urlArquivoNegociacao;

    @Column(name = "numero_parcelas_pagas")
    private Integer numeroParcelasPagas;

    @Column(name = "numero_parcelas_regatantes")
    private Integer numeroParcelasRegatantes;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_solicitacao_parcelamento_enum")
    private SituacaoSolicitacaoParcelamentoEnum situacaoSolicitacaoParcelamentoEnum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parcelamentoImposto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parcelamentoImposto" }, allowSetters = true)
    private Set<ParcelaImpostoAPagar> parcelaImpostoAPagars = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parcelamentoImposto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parcelamentoImposto", "impostoAPagarEmpresa" }, allowSetters = true)
    private Set<ImpostoParcelado> impostoParcelados = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "impostoEmpresas", "parcelamentoImpostos", "impostoEmpresaModelos", "esfera" }, allowSetters = true)
    private Imposto imposto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "funcionarios",
            "anexoEmpresas",
            "ordemServicos",
            "anexoRequeridoEmpresas",
            "impostoEmpresas",
            "parcelamentoImpostos",
            "assinaturaEmpresas",
            "departamentoEmpresas",
            "tarefaEmpresas",
            "enderecoEmpresas",
            "atividadeEmpresas",
            "socios",
            "certificadoDigitals",
            "opcaoRazaoSocialEmpresas",
            "opcaoNomeFantasiaEmpresas",
            "termoAdesaoEmpresas",
            "segmentoCnaes",
            "empresaModelo",
        },
        allowSetters = true
    )
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ParcelamentoImposto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiaVencimento() {
        return this.diaVencimento;
    }

    public ParcelamentoImposto diaVencimento(Integer diaVencimento) {
        this.setDiaVencimento(diaVencimento);
        return this;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public Integer getNumeroParcelas() {
        return this.numeroParcelas;
    }

    public ParcelamentoImposto numeroParcelas(Integer numeroParcelas) {
        this.setNumeroParcelas(numeroParcelas);
        return this;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public String getUrlArquivoNegociacao() {
        return this.urlArquivoNegociacao;
    }

    public ParcelamentoImposto urlArquivoNegociacao(String urlArquivoNegociacao) {
        this.setUrlArquivoNegociacao(urlArquivoNegociacao);
        return this;
    }

    public void setUrlArquivoNegociacao(String urlArquivoNegociacao) {
        this.urlArquivoNegociacao = urlArquivoNegociacao;
    }

    public Integer getNumeroParcelasPagas() {
        return this.numeroParcelasPagas;
    }

    public ParcelamentoImposto numeroParcelasPagas(Integer numeroParcelasPagas) {
        this.setNumeroParcelasPagas(numeroParcelasPagas);
        return this;
    }

    public void setNumeroParcelasPagas(Integer numeroParcelasPagas) {
        this.numeroParcelasPagas = numeroParcelasPagas;
    }

    public Integer getNumeroParcelasRegatantes() {
        return this.numeroParcelasRegatantes;
    }

    public ParcelamentoImposto numeroParcelasRegatantes(Integer numeroParcelasRegatantes) {
        this.setNumeroParcelasRegatantes(numeroParcelasRegatantes);
        return this;
    }

    public void setNumeroParcelasRegatantes(Integer numeroParcelasRegatantes) {
        this.numeroParcelasRegatantes = numeroParcelasRegatantes;
    }

    public SituacaoSolicitacaoParcelamentoEnum getSituacaoSolicitacaoParcelamentoEnum() {
        return this.situacaoSolicitacaoParcelamentoEnum;
    }

    public ParcelamentoImposto situacaoSolicitacaoParcelamentoEnum(
        SituacaoSolicitacaoParcelamentoEnum situacaoSolicitacaoParcelamentoEnum
    ) {
        this.setSituacaoSolicitacaoParcelamentoEnum(situacaoSolicitacaoParcelamentoEnum);
        return this;
    }

    public void setSituacaoSolicitacaoParcelamentoEnum(SituacaoSolicitacaoParcelamentoEnum situacaoSolicitacaoParcelamentoEnum) {
        this.situacaoSolicitacaoParcelamentoEnum = situacaoSolicitacaoParcelamentoEnum;
    }

    public Set<ParcelaImpostoAPagar> getParcelaImpostoAPagars() {
        return this.parcelaImpostoAPagars;
    }

    public void setParcelaImpostoAPagars(Set<ParcelaImpostoAPagar> parcelaImpostoAPagars) {
        if (this.parcelaImpostoAPagars != null) {
            this.parcelaImpostoAPagars.forEach(i -> i.setParcelamentoImposto(null));
        }
        if (parcelaImpostoAPagars != null) {
            parcelaImpostoAPagars.forEach(i -> i.setParcelamentoImposto(this));
        }
        this.parcelaImpostoAPagars = parcelaImpostoAPagars;
    }

    public ParcelamentoImposto parcelaImpostoAPagars(Set<ParcelaImpostoAPagar> parcelaImpostoAPagars) {
        this.setParcelaImpostoAPagars(parcelaImpostoAPagars);
        return this;
    }

    public ParcelamentoImposto addParcelaImpostoAPagar(ParcelaImpostoAPagar parcelaImpostoAPagar) {
        this.parcelaImpostoAPagars.add(parcelaImpostoAPagar);
        parcelaImpostoAPagar.setParcelamentoImposto(this);
        return this;
    }

    public ParcelamentoImposto removeParcelaImpostoAPagar(ParcelaImpostoAPagar parcelaImpostoAPagar) {
        this.parcelaImpostoAPagars.remove(parcelaImpostoAPagar);
        parcelaImpostoAPagar.setParcelamentoImposto(null);
        return this;
    }

    public Set<ImpostoParcelado> getImpostoParcelados() {
        return this.impostoParcelados;
    }

    public void setImpostoParcelados(Set<ImpostoParcelado> impostoParcelados) {
        if (this.impostoParcelados != null) {
            this.impostoParcelados.forEach(i -> i.setParcelamentoImposto(null));
        }
        if (impostoParcelados != null) {
            impostoParcelados.forEach(i -> i.setParcelamentoImposto(this));
        }
        this.impostoParcelados = impostoParcelados;
    }

    public ParcelamentoImposto impostoParcelados(Set<ImpostoParcelado> impostoParcelados) {
        this.setImpostoParcelados(impostoParcelados);
        return this;
    }

    public ParcelamentoImposto addImpostoParcelado(ImpostoParcelado impostoParcelado) {
        this.impostoParcelados.add(impostoParcelado);
        impostoParcelado.setParcelamentoImposto(this);
        return this;
    }

    public ParcelamentoImposto removeImpostoParcelado(ImpostoParcelado impostoParcelado) {
        this.impostoParcelados.remove(impostoParcelado);
        impostoParcelado.setParcelamentoImposto(null);
        return this;
    }

    public Imposto getImposto() {
        return this.imposto;
    }

    public void setImposto(Imposto imposto) {
        this.imposto = imposto;
    }

    public ParcelamentoImposto imposto(Imposto imposto) {
        this.setImposto(imposto);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ParcelamentoImposto empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParcelamentoImposto)) {
            return false;
        }
        return getId() != null && getId().equals(((ParcelamentoImposto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParcelamentoImposto{" +
            "id=" + getId() +
            ", diaVencimento=" + getDiaVencimento() +
            ", numeroParcelas=" + getNumeroParcelas() +
            ", urlArquivoNegociacao='" + getUrlArquivoNegociacao() + "'" +
            ", numeroParcelasPagas=" + getNumeroParcelasPagas() +
            ", numeroParcelasRegatantes=" + getNumeroParcelasRegatantes() +
            ", situacaoSolicitacaoParcelamentoEnum='" + getSituacaoSolicitacaoParcelamentoEnum() + "'" +
            "}";
    }
}
