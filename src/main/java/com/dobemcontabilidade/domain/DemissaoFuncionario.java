package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.AvisoPrevioEnum;
import com.dobemcontabilidade.domain.enumeration.SituacaoDemissaoEnum;
import com.dobemcontabilidade.domain.enumeration.TipoDemissaoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemissaoFuncionario.
 */
@Entity
@Table(name = "demissao_funcionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemissaoFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_certidao_obito")
    private String numeroCertidaoObito;

    @Column(name = "cnpj_empresa_sucessora")
    private String cnpjEmpresaSucessora;

    @Column(name = "saldo_fgts")
    private String saldoFGTS;

    @Column(name = "valor_pensao")
    private String valorPensao;

    @Column(name = "valor_pensao_fgts")
    private String valorPensaoFgts;

    @Column(name = "percentual_pensao")
    private String percentualPensao;

    @Column(name = "percentual_fgts")
    private String percentualFgts;

    @Column(name = "dias_aviso_previo")
    private Integer diasAvisoPrevio;

    @Column(name = "data_aviso_previo")
    private String dataAvisoPrevio;

    @Column(name = "data_pagamento")
    private String dataPagamento;

    @Column(name = "data_afastamento")
    private String dataAfastamento;

    @Column(name = "url_demissional")
    private String urlDemissional;

    @Column(name = "calcular_recisao")
    private Boolean calcularRecisao;

    @Column(name = "pagar_13_recisao")
    private Boolean pagar13Recisao;

    @Column(name = "jornada_trabalho_cumprida_semana")
    private Boolean jornadaTrabalhoCumpridaSemana;

    @Column(name = "sabado_compesado")
    private Boolean sabadoCompesado;

    @Column(name = "novo_vinculo_comprovado")
    private Boolean novoVinculoComprovado;

    @Column(name = "dispensa_aviso_previo")
    private Boolean dispensaAvisoPrevio;

    @Column(name = "fgts_arrecadado_guia")
    private Boolean fgtsArrecadadoGuia;

    @Column(name = "aviso_previo_trabalhado_recebido")
    private Boolean avisoPrevioTrabalhadoRecebido;

    @Column(name = "recolher_fgts_mes_anterior")
    private Boolean recolherFgtsMesAnterior;

    @Column(name = "aviso_previo_indenizado")
    private Boolean avisoPrevioIndenizado;

    @Column(name = "cumprimento_aviso_previo")
    private Integer cumprimentoAvisoPrevio;

    @Enumerated(EnumType.STRING)
    @Column(name = "aviso_previo")
    private AvisoPrevioEnum avisoPrevio;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_demissao")
    private SituacaoDemissaoEnum situacaoDemissao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_demissao")
    private TipoDemissaoEnum tipoDemissao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "usuarioEmpresa",
            "estrangeiros",
            "contratoFuncionarios",
            "demissaoFuncionarios",
            "dependentesFuncionarios",
            "empresaVinculadas",
            "departamentoFuncionarios",
            "pessoa",
            "empresa",
            "profissao",
        },
        allowSetters = true
    )
    private Funcionario funcionario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemissaoFuncionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCertidaoObito() {
        return this.numeroCertidaoObito;
    }

    public DemissaoFuncionario numeroCertidaoObito(String numeroCertidaoObito) {
        this.setNumeroCertidaoObito(numeroCertidaoObito);
        return this;
    }

    public void setNumeroCertidaoObito(String numeroCertidaoObito) {
        this.numeroCertidaoObito = numeroCertidaoObito;
    }

    public String getCnpjEmpresaSucessora() {
        return this.cnpjEmpresaSucessora;
    }

    public DemissaoFuncionario cnpjEmpresaSucessora(String cnpjEmpresaSucessora) {
        this.setCnpjEmpresaSucessora(cnpjEmpresaSucessora);
        return this;
    }

    public void setCnpjEmpresaSucessora(String cnpjEmpresaSucessora) {
        this.cnpjEmpresaSucessora = cnpjEmpresaSucessora;
    }

    public String getSaldoFGTS() {
        return this.saldoFGTS;
    }

    public DemissaoFuncionario saldoFGTS(String saldoFGTS) {
        this.setSaldoFGTS(saldoFGTS);
        return this;
    }

    public void setSaldoFGTS(String saldoFGTS) {
        this.saldoFGTS = saldoFGTS;
    }

    public String getValorPensao() {
        return this.valorPensao;
    }

    public DemissaoFuncionario valorPensao(String valorPensao) {
        this.setValorPensao(valorPensao);
        return this;
    }

    public void setValorPensao(String valorPensao) {
        this.valorPensao = valorPensao;
    }

    public String getValorPensaoFgts() {
        return this.valorPensaoFgts;
    }

    public DemissaoFuncionario valorPensaoFgts(String valorPensaoFgts) {
        this.setValorPensaoFgts(valorPensaoFgts);
        return this;
    }

    public void setValorPensaoFgts(String valorPensaoFgts) {
        this.valorPensaoFgts = valorPensaoFgts;
    }

    public String getPercentualPensao() {
        return this.percentualPensao;
    }

    public DemissaoFuncionario percentualPensao(String percentualPensao) {
        this.setPercentualPensao(percentualPensao);
        return this;
    }

    public void setPercentualPensao(String percentualPensao) {
        this.percentualPensao = percentualPensao;
    }

    public String getPercentualFgts() {
        return this.percentualFgts;
    }

    public DemissaoFuncionario percentualFgts(String percentualFgts) {
        this.setPercentualFgts(percentualFgts);
        return this;
    }

    public void setPercentualFgts(String percentualFgts) {
        this.percentualFgts = percentualFgts;
    }

    public Integer getDiasAvisoPrevio() {
        return this.diasAvisoPrevio;
    }

    public DemissaoFuncionario diasAvisoPrevio(Integer diasAvisoPrevio) {
        this.setDiasAvisoPrevio(diasAvisoPrevio);
        return this;
    }

    public void setDiasAvisoPrevio(Integer diasAvisoPrevio) {
        this.diasAvisoPrevio = diasAvisoPrevio;
    }

    public String getDataAvisoPrevio() {
        return this.dataAvisoPrevio;
    }

    public DemissaoFuncionario dataAvisoPrevio(String dataAvisoPrevio) {
        this.setDataAvisoPrevio(dataAvisoPrevio);
        return this;
    }

    public void setDataAvisoPrevio(String dataAvisoPrevio) {
        this.dataAvisoPrevio = dataAvisoPrevio;
    }

    public String getDataPagamento() {
        return this.dataPagamento;
    }

    public DemissaoFuncionario dataPagamento(String dataPagamento) {
        this.setDataPagamento(dataPagamento);
        return this;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getDataAfastamento() {
        return this.dataAfastamento;
    }

    public DemissaoFuncionario dataAfastamento(String dataAfastamento) {
        this.setDataAfastamento(dataAfastamento);
        return this;
    }

    public void setDataAfastamento(String dataAfastamento) {
        this.dataAfastamento = dataAfastamento;
    }

    public String getUrlDemissional() {
        return this.urlDemissional;
    }

    public DemissaoFuncionario urlDemissional(String urlDemissional) {
        this.setUrlDemissional(urlDemissional);
        return this;
    }

    public void setUrlDemissional(String urlDemissional) {
        this.urlDemissional = urlDemissional;
    }

    public Boolean getCalcularRecisao() {
        return this.calcularRecisao;
    }

    public DemissaoFuncionario calcularRecisao(Boolean calcularRecisao) {
        this.setCalcularRecisao(calcularRecisao);
        return this;
    }

    public void setCalcularRecisao(Boolean calcularRecisao) {
        this.calcularRecisao = calcularRecisao;
    }

    public Boolean getPagar13Recisao() {
        return this.pagar13Recisao;
    }

    public DemissaoFuncionario pagar13Recisao(Boolean pagar13Recisao) {
        this.setPagar13Recisao(pagar13Recisao);
        return this;
    }

    public void setPagar13Recisao(Boolean pagar13Recisao) {
        this.pagar13Recisao = pagar13Recisao;
    }

    public Boolean getJornadaTrabalhoCumpridaSemana() {
        return this.jornadaTrabalhoCumpridaSemana;
    }

    public DemissaoFuncionario jornadaTrabalhoCumpridaSemana(Boolean jornadaTrabalhoCumpridaSemana) {
        this.setJornadaTrabalhoCumpridaSemana(jornadaTrabalhoCumpridaSemana);
        return this;
    }

    public void setJornadaTrabalhoCumpridaSemana(Boolean jornadaTrabalhoCumpridaSemana) {
        this.jornadaTrabalhoCumpridaSemana = jornadaTrabalhoCumpridaSemana;
    }

    public Boolean getSabadoCompesado() {
        return this.sabadoCompesado;
    }

    public DemissaoFuncionario sabadoCompesado(Boolean sabadoCompesado) {
        this.setSabadoCompesado(sabadoCompesado);
        return this;
    }

    public void setSabadoCompesado(Boolean sabadoCompesado) {
        this.sabadoCompesado = sabadoCompesado;
    }

    public Boolean getNovoVinculoComprovado() {
        return this.novoVinculoComprovado;
    }

    public DemissaoFuncionario novoVinculoComprovado(Boolean novoVinculoComprovado) {
        this.setNovoVinculoComprovado(novoVinculoComprovado);
        return this;
    }

    public void setNovoVinculoComprovado(Boolean novoVinculoComprovado) {
        this.novoVinculoComprovado = novoVinculoComprovado;
    }

    public Boolean getDispensaAvisoPrevio() {
        return this.dispensaAvisoPrevio;
    }

    public DemissaoFuncionario dispensaAvisoPrevio(Boolean dispensaAvisoPrevio) {
        this.setDispensaAvisoPrevio(dispensaAvisoPrevio);
        return this;
    }

    public void setDispensaAvisoPrevio(Boolean dispensaAvisoPrevio) {
        this.dispensaAvisoPrevio = dispensaAvisoPrevio;
    }

    public Boolean getFgtsArrecadadoGuia() {
        return this.fgtsArrecadadoGuia;
    }

    public DemissaoFuncionario fgtsArrecadadoGuia(Boolean fgtsArrecadadoGuia) {
        this.setFgtsArrecadadoGuia(fgtsArrecadadoGuia);
        return this;
    }

    public void setFgtsArrecadadoGuia(Boolean fgtsArrecadadoGuia) {
        this.fgtsArrecadadoGuia = fgtsArrecadadoGuia;
    }

    public Boolean getAvisoPrevioTrabalhadoRecebido() {
        return this.avisoPrevioTrabalhadoRecebido;
    }

    public DemissaoFuncionario avisoPrevioTrabalhadoRecebido(Boolean avisoPrevioTrabalhadoRecebido) {
        this.setAvisoPrevioTrabalhadoRecebido(avisoPrevioTrabalhadoRecebido);
        return this;
    }

    public void setAvisoPrevioTrabalhadoRecebido(Boolean avisoPrevioTrabalhadoRecebido) {
        this.avisoPrevioTrabalhadoRecebido = avisoPrevioTrabalhadoRecebido;
    }

    public Boolean getRecolherFgtsMesAnterior() {
        return this.recolherFgtsMesAnterior;
    }

    public DemissaoFuncionario recolherFgtsMesAnterior(Boolean recolherFgtsMesAnterior) {
        this.setRecolherFgtsMesAnterior(recolherFgtsMesAnterior);
        return this;
    }

    public void setRecolherFgtsMesAnterior(Boolean recolherFgtsMesAnterior) {
        this.recolherFgtsMesAnterior = recolherFgtsMesAnterior;
    }

    public Boolean getAvisoPrevioIndenizado() {
        return this.avisoPrevioIndenizado;
    }

    public DemissaoFuncionario avisoPrevioIndenizado(Boolean avisoPrevioIndenizado) {
        this.setAvisoPrevioIndenizado(avisoPrevioIndenizado);
        return this;
    }

    public void setAvisoPrevioIndenizado(Boolean avisoPrevioIndenizado) {
        this.avisoPrevioIndenizado = avisoPrevioIndenizado;
    }

    public Integer getCumprimentoAvisoPrevio() {
        return this.cumprimentoAvisoPrevio;
    }

    public DemissaoFuncionario cumprimentoAvisoPrevio(Integer cumprimentoAvisoPrevio) {
        this.setCumprimentoAvisoPrevio(cumprimentoAvisoPrevio);
        return this;
    }

    public void setCumprimentoAvisoPrevio(Integer cumprimentoAvisoPrevio) {
        this.cumprimentoAvisoPrevio = cumprimentoAvisoPrevio;
    }

    public AvisoPrevioEnum getAvisoPrevio() {
        return this.avisoPrevio;
    }

    public DemissaoFuncionario avisoPrevio(AvisoPrevioEnum avisoPrevio) {
        this.setAvisoPrevio(avisoPrevio);
        return this;
    }

    public void setAvisoPrevio(AvisoPrevioEnum avisoPrevio) {
        this.avisoPrevio = avisoPrevio;
    }

    public SituacaoDemissaoEnum getSituacaoDemissao() {
        return this.situacaoDemissao;
    }

    public DemissaoFuncionario situacaoDemissao(SituacaoDemissaoEnum situacaoDemissao) {
        this.setSituacaoDemissao(situacaoDemissao);
        return this;
    }

    public void setSituacaoDemissao(SituacaoDemissaoEnum situacaoDemissao) {
        this.situacaoDemissao = situacaoDemissao;
    }

    public TipoDemissaoEnum getTipoDemissao() {
        return this.tipoDemissao;
    }

    public DemissaoFuncionario tipoDemissao(TipoDemissaoEnum tipoDemissao) {
        this.setTipoDemissao(tipoDemissao);
        return this;
    }

    public void setTipoDemissao(TipoDemissaoEnum tipoDemissao) {
        this.tipoDemissao = tipoDemissao;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public DemissaoFuncionario funcionario(Funcionario funcionario) {
        this.setFuncionario(funcionario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemissaoFuncionario)) {
            return false;
        }
        return getId() != null && getId().equals(((DemissaoFuncionario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemissaoFuncionario{" +
            "id=" + getId() +
            ", numeroCertidaoObito='" + getNumeroCertidaoObito() + "'" +
            ", cnpjEmpresaSucessora='" + getCnpjEmpresaSucessora() + "'" +
            ", saldoFGTS='" + getSaldoFGTS() + "'" +
            ", valorPensao='" + getValorPensao() + "'" +
            ", valorPensaoFgts='" + getValorPensaoFgts() + "'" +
            ", percentualPensao='" + getPercentualPensao() + "'" +
            ", percentualFgts='" + getPercentualFgts() + "'" +
            ", diasAvisoPrevio=" + getDiasAvisoPrevio() +
            ", dataAvisoPrevio='" + getDataAvisoPrevio() + "'" +
            ", dataPagamento='" + getDataPagamento() + "'" +
            ", dataAfastamento='" + getDataAfastamento() + "'" +
            ", urlDemissional='" + getUrlDemissional() + "'" +
            ", calcularRecisao='" + getCalcularRecisao() + "'" +
            ", pagar13Recisao='" + getPagar13Recisao() + "'" +
            ", jornadaTrabalhoCumpridaSemana='" + getJornadaTrabalhoCumpridaSemana() + "'" +
            ", sabadoCompesado='" + getSabadoCompesado() + "'" +
            ", novoVinculoComprovado='" + getNovoVinculoComprovado() + "'" +
            ", dispensaAvisoPrevio='" + getDispensaAvisoPrevio() + "'" +
            ", fgtsArrecadadoGuia='" + getFgtsArrecadadoGuia() + "'" +
            ", avisoPrevioTrabalhadoRecebido='" + getAvisoPrevioTrabalhadoRecebido() + "'" +
            ", recolherFgtsMesAnterior='" + getRecolherFgtsMesAnterior() + "'" +
            ", avisoPrevioIndenizado='" + getAvisoPrevioIndenizado() + "'" +
            ", cumprimentoAvisoPrevio=" + getCumprimentoAvisoPrevio() +
            ", avisoPrevio='" + getAvisoPrevio() + "'" +
            ", situacaoDemissao='" + getSituacaoDemissao() + "'" +
            ", tipoDemissao='" + getTipoDemissao() + "'" +
            "}";
    }
}
