package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.DiasDaSemanaEnum;
import com.dobemcontabilidade.domain.enumeration.JornadaEspecialEnum;
import com.dobemcontabilidade.domain.enumeration.RegimePrevidenciarioEnum;
import com.dobemcontabilidade.domain.enumeration.TipoContratoTrabalhoEnum;
import com.dobemcontabilidade.domain.enumeration.TipoInscricaoEmpresaVinculadaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoJornadaEmpresaVinculadaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoRegimeTrabalhoEnum;
import com.dobemcontabilidade.domain.enumeration.UnidadePagamentoSalarioEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmpresaVinculada.
 */
@Entity
@Table(name = "empresa_vinculada")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmpresaVinculada implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 200)
    @Column(name = "nome_empresa", length = 200)
    private String nomeEmpresa;

    @Size(max = 14)
    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Column(name = "remuneracao_empresa")
    private String remuneracaoEmpresa;

    @Size(max = 200)
    @Column(name = "observacoes", length = 200)
    private String observacoes;

    @Column(name = "salario_fixo")
    private Boolean salarioFixo;

    @Column(name = "salario_variavel")
    private Boolean salarioVariavel;

    @Column(name = "valor_salario_fixo")
    private String valorSalarioFixo;

    @Column(name = "data_termino_contrato")
    private String dataTerminoContrato;

    @Column(name = "numero_inscricao")
    private Integer numeroInscricao;

    @Column(name = "codigo_lotacao")
    private Integer codigoLotacao;

    @Column(name = "descricao_complementar")
    private String descricaoComplementar;

    @Size(max = 200)
    @Column(name = "descricao_cargo", length = 200)
    private String descricaoCargo;

    @Column(name = "observacao_jornada_trabalho")
    private String observacaoJornadaTrabalho;

    @Column(name = "media_horas_trabalhadas_semana")
    private Integer mediaHorasTrabalhadasSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "regime_previdenciario")
    private RegimePrevidenciarioEnum regimePrevidenciario;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_pagamento_salario")
    private UnidadePagamentoSalarioEnum unidadePagamentoSalario;

    @Enumerated(EnumType.STRING)
    @Column(name = "jornada_especial")
    private JornadaEspecialEnum jornadaEspecial;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_inscricao_empresa_vinculada")
    private TipoInscricaoEmpresaVinculadaEnum tipoInscricaoEmpresaVinculada;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contrato_trabalho")
    private TipoContratoTrabalhoEnum tipoContratoTrabalho;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_regime_trabalho")
    private TipoRegimeTrabalhoEnum tipoRegimeTrabalho;

    @Enumerated(EnumType.STRING)
    @Column(name = "dias_da_semana")
    private DiasDaSemanaEnum diasDaSemana;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_jornada_empresa_vinculada")
    private TipoJornadaEmpresaVinculadaEnum tipoJornadaEmpresaVinculada;

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

    public EmpresaVinculada id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return this.nomeEmpresa;
    }

    public EmpresaVinculada nomeEmpresa(String nomeEmpresa) {
        this.setNomeEmpresa(nomeEmpresa);
        return this;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public EmpresaVinculada cnpj(String cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRemuneracaoEmpresa() {
        return this.remuneracaoEmpresa;
    }

    public EmpresaVinculada remuneracaoEmpresa(String remuneracaoEmpresa) {
        this.setRemuneracaoEmpresa(remuneracaoEmpresa);
        return this;
    }

    public void setRemuneracaoEmpresa(String remuneracaoEmpresa) {
        this.remuneracaoEmpresa = remuneracaoEmpresa;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public EmpresaVinculada observacoes(String observacoes) {
        this.setObservacoes(observacoes);
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Boolean getSalarioFixo() {
        return this.salarioFixo;
    }

    public EmpresaVinculada salarioFixo(Boolean salarioFixo) {
        this.setSalarioFixo(salarioFixo);
        return this;
    }

    public void setSalarioFixo(Boolean salarioFixo) {
        this.salarioFixo = salarioFixo;
    }

    public Boolean getSalarioVariavel() {
        return this.salarioVariavel;
    }

    public EmpresaVinculada salarioVariavel(Boolean salarioVariavel) {
        this.setSalarioVariavel(salarioVariavel);
        return this;
    }

    public void setSalarioVariavel(Boolean salarioVariavel) {
        this.salarioVariavel = salarioVariavel;
    }

    public String getValorSalarioFixo() {
        return this.valorSalarioFixo;
    }

    public EmpresaVinculada valorSalarioFixo(String valorSalarioFixo) {
        this.setValorSalarioFixo(valorSalarioFixo);
        return this;
    }

    public void setValorSalarioFixo(String valorSalarioFixo) {
        this.valorSalarioFixo = valorSalarioFixo;
    }

    public String getDataTerminoContrato() {
        return this.dataTerminoContrato;
    }

    public EmpresaVinculada dataTerminoContrato(String dataTerminoContrato) {
        this.setDataTerminoContrato(dataTerminoContrato);
        return this;
    }

    public void setDataTerminoContrato(String dataTerminoContrato) {
        this.dataTerminoContrato = dataTerminoContrato;
    }

    public Integer getNumeroInscricao() {
        return this.numeroInscricao;
    }

    public EmpresaVinculada numeroInscricao(Integer numeroInscricao) {
        this.setNumeroInscricao(numeroInscricao);
        return this;
    }

    public void setNumeroInscricao(Integer numeroInscricao) {
        this.numeroInscricao = numeroInscricao;
    }

    public Integer getCodigoLotacao() {
        return this.codigoLotacao;
    }

    public EmpresaVinculada codigoLotacao(Integer codigoLotacao) {
        this.setCodigoLotacao(codigoLotacao);
        return this;
    }

    public void setCodigoLotacao(Integer codigoLotacao) {
        this.codigoLotacao = codigoLotacao;
    }

    public String getDescricaoComplementar() {
        return this.descricaoComplementar;
    }

    public EmpresaVinculada descricaoComplementar(String descricaoComplementar) {
        this.setDescricaoComplementar(descricaoComplementar);
        return this;
    }

    public void setDescricaoComplementar(String descricaoComplementar) {
        this.descricaoComplementar = descricaoComplementar;
    }

    public String getDescricaoCargo() {
        return this.descricaoCargo;
    }

    public EmpresaVinculada descricaoCargo(String descricaoCargo) {
        this.setDescricaoCargo(descricaoCargo);
        return this;
    }

    public void setDescricaoCargo(String descricaoCargo) {
        this.descricaoCargo = descricaoCargo;
    }

    public String getObservacaoJornadaTrabalho() {
        return this.observacaoJornadaTrabalho;
    }

    public EmpresaVinculada observacaoJornadaTrabalho(String observacaoJornadaTrabalho) {
        this.setObservacaoJornadaTrabalho(observacaoJornadaTrabalho);
        return this;
    }

    public void setObservacaoJornadaTrabalho(String observacaoJornadaTrabalho) {
        this.observacaoJornadaTrabalho = observacaoJornadaTrabalho;
    }

    public Integer getMediaHorasTrabalhadasSemana() {
        return this.mediaHorasTrabalhadasSemana;
    }

    public EmpresaVinculada mediaHorasTrabalhadasSemana(Integer mediaHorasTrabalhadasSemana) {
        this.setMediaHorasTrabalhadasSemana(mediaHorasTrabalhadasSemana);
        return this;
    }

    public void setMediaHorasTrabalhadasSemana(Integer mediaHorasTrabalhadasSemana) {
        this.mediaHorasTrabalhadasSemana = mediaHorasTrabalhadasSemana;
    }

    public RegimePrevidenciarioEnum getRegimePrevidenciario() {
        return this.regimePrevidenciario;
    }

    public EmpresaVinculada regimePrevidenciario(RegimePrevidenciarioEnum regimePrevidenciario) {
        this.setRegimePrevidenciario(regimePrevidenciario);
        return this;
    }

    public void setRegimePrevidenciario(RegimePrevidenciarioEnum regimePrevidenciario) {
        this.regimePrevidenciario = regimePrevidenciario;
    }

    public UnidadePagamentoSalarioEnum getUnidadePagamentoSalario() {
        return this.unidadePagamentoSalario;
    }

    public EmpresaVinculada unidadePagamentoSalario(UnidadePagamentoSalarioEnum unidadePagamentoSalario) {
        this.setUnidadePagamentoSalario(unidadePagamentoSalario);
        return this;
    }

    public void setUnidadePagamentoSalario(UnidadePagamentoSalarioEnum unidadePagamentoSalario) {
        this.unidadePagamentoSalario = unidadePagamentoSalario;
    }

    public JornadaEspecialEnum getJornadaEspecial() {
        return this.jornadaEspecial;
    }

    public EmpresaVinculada jornadaEspecial(JornadaEspecialEnum jornadaEspecial) {
        this.setJornadaEspecial(jornadaEspecial);
        return this;
    }

    public void setJornadaEspecial(JornadaEspecialEnum jornadaEspecial) {
        this.jornadaEspecial = jornadaEspecial;
    }

    public TipoInscricaoEmpresaVinculadaEnum getTipoInscricaoEmpresaVinculada() {
        return this.tipoInscricaoEmpresaVinculada;
    }

    public EmpresaVinculada tipoInscricaoEmpresaVinculada(TipoInscricaoEmpresaVinculadaEnum tipoInscricaoEmpresaVinculada) {
        this.setTipoInscricaoEmpresaVinculada(tipoInscricaoEmpresaVinculada);
        return this;
    }

    public void setTipoInscricaoEmpresaVinculada(TipoInscricaoEmpresaVinculadaEnum tipoInscricaoEmpresaVinculada) {
        this.tipoInscricaoEmpresaVinculada = tipoInscricaoEmpresaVinculada;
    }

    public TipoContratoTrabalhoEnum getTipoContratoTrabalho() {
        return this.tipoContratoTrabalho;
    }

    public EmpresaVinculada tipoContratoTrabalho(TipoContratoTrabalhoEnum tipoContratoTrabalho) {
        this.setTipoContratoTrabalho(tipoContratoTrabalho);
        return this;
    }

    public void setTipoContratoTrabalho(TipoContratoTrabalhoEnum tipoContratoTrabalho) {
        this.tipoContratoTrabalho = tipoContratoTrabalho;
    }

    public TipoRegimeTrabalhoEnum getTipoRegimeTrabalho() {
        return this.tipoRegimeTrabalho;
    }

    public EmpresaVinculada tipoRegimeTrabalho(TipoRegimeTrabalhoEnum tipoRegimeTrabalho) {
        this.setTipoRegimeTrabalho(tipoRegimeTrabalho);
        return this;
    }

    public void setTipoRegimeTrabalho(TipoRegimeTrabalhoEnum tipoRegimeTrabalho) {
        this.tipoRegimeTrabalho = tipoRegimeTrabalho;
    }

    public DiasDaSemanaEnum getDiasDaSemana() {
        return this.diasDaSemana;
    }

    public EmpresaVinculada diasDaSemana(DiasDaSemanaEnum diasDaSemana) {
        this.setDiasDaSemana(diasDaSemana);
        return this;
    }

    public void setDiasDaSemana(DiasDaSemanaEnum diasDaSemana) {
        this.diasDaSemana = diasDaSemana;
    }

    public TipoJornadaEmpresaVinculadaEnum getTipoJornadaEmpresaVinculada() {
        return this.tipoJornadaEmpresaVinculada;
    }

    public EmpresaVinculada tipoJornadaEmpresaVinculada(TipoJornadaEmpresaVinculadaEnum tipoJornadaEmpresaVinculada) {
        this.setTipoJornadaEmpresaVinculada(tipoJornadaEmpresaVinculada);
        return this;
    }

    public void setTipoJornadaEmpresaVinculada(TipoJornadaEmpresaVinculadaEnum tipoJornadaEmpresaVinculada) {
        this.tipoJornadaEmpresaVinculada = tipoJornadaEmpresaVinculada;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public EmpresaVinculada funcionario(Funcionario funcionario) {
        this.setFuncionario(funcionario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpresaVinculada)) {
            return false;
        }
        return getId() != null && getId().equals(((EmpresaVinculada) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaVinculada{" +
            "id=" + getId() +
            ", nomeEmpresa='" + getNomeEmpresa() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", remuneracaoEmpresa='" + getRemuneracaoEmpresa() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", salarioFixo='" + getSalarioFixo() + "'" +
            ", salarioVariavel='" + getSalarioVariavel() + "'" +
            ", valorSalarioFixo='" + getValorSalarioFixo() + "'" +
            ", dataTerminoContrato='" + getDataTerminoContrato() + "'" +
            ", numeroInscricao=" + getNumeroInscricao() +
            ", codigoLotacao=" + getCodigoLotacao() +
            ", descricaoComplementar='" + getDescricaoComplementar() + "'" +
            ", descricaoCargo='" + getDescricaoCargo() + "'" +
            ", observacaoJornadaTrabalho='" + getObservacaoJornadaTrabalho() + "'" +
            ", mediaHorasTrabalhadasSemana=" + getMediaHorasTrabalhadasSemana() +
            ", regimePrevidenciario='" + getRegimePrevidenciario() + "'" +
            ", unidadePagamentoSalario='" + getUnidadePagamentoSalario() + "'" +
            ", jornadaEspecial='" + getJornadaEspecial() + "'" +
            ", tipoInscricaoEmpresaVinculada='" + getTipoInscricaoEmpresaVinculada() + "'" +
            ", tipoContratoTrabalho='" + getTipoContratoTrabalho() + "'" +
            ", tipoRegimeTrabalho='" + getTipoRegimeTrabalho() + "'" +
            ", diasDaSemana='" + getDiasDaSemana() + "'" +
            ", tipoJornadaEmpresaVinculada='" + getTipoJornadaEmpresaVinculada() + "'" +
            "}";
    }
}
