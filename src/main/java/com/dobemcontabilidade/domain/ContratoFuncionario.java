package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.CategoriaTrabalhadorEnum;
import com.dobemcontabilidade.domain.enumeration.FgtsOpcaoEnum;
import com.dobemcontabilidade.domain.enumeration.IndicativoAdmissaoEnum;
import com.dobemcontabilidade.domain.enumeration.NaturezaEstagioEnum;
import com.dobemcontabilidade.domain.enumeration.PeriodoExperienciaEnum;
import com.dobemcontabilidade.domain.enumeration.PeriodoIntermitenteEnum;
import com.dobemcontabilidade.domain.enumeration.SituacaoFuncionarioEnum;
import com.dobemcontabilidade.domain.enumeration.TipoAdmisaoEnum;
import com.dobemcontabilidade.domain.enumeration.TipoDocumentoEnum;
import com.dobemcontabilidade.domain.enumeration.TipoVinculoTrabalhoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContratoFuncionario.
 */
@Entity
@Table(name = "contrato_funcionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContratoFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "salario_fixo")
    private Boolean salarioFixo;

    @Column(name = "salario_variavel")
    private Boolean salarioVariavel;

    @Column(name = "estagio")
    private Boolean estagio;

    @Enumerated(EnumType.STRING)
    @Column(name = "natureza_estagio_enum")
    private NaturezaEstagioEnum naturezaEstagioEnum;

    @Column(name = "ctps")
    private String ctps;

    @Column(name = "serie_ctps")
    private Integer serieCtps;

    @Column(name = "orgao_emissor_documento")
    private String orgaoEmissorDocumento;

    @Column(name = "data_validade_documento")
    private String dataValidadeDocumento;

    @Column(name = "data_admissao")
    private String dataAdmissao;

    @Column(name = "cargo")
    private String cargo;

    @Lob
    @Column(name = "descricao_atividades")
    private String descricaoAtividades;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoFuncionarioEnum situacao;

    @Column(name = "valor_salario_fixo")
    private String valorSalarioFixo;

    @Column(name = "valor_salario_variavel")
    private String valorSalarioVariavel;

    @Column(name = "data_termino_contrato")
    private String dataTerminoContrato;

    @Column(name = "datainicio_contrato")
    private String datainicioContrato;

    @Column(name = "horas_a_trabalhadar")
    private Integer horasATrabalhadar;

    @Size(max = 10)
    @Column(name = "codigo_cargo", length = 10)
    private String codigoCargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_trabalhador")
    private CategoriaTrabalhadorEnum categoriaTrabalhador;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vinculo_trabalho")
    private TipoVinculoTrabalhoEnum tipoVinculoTrabalho;

    @Enumerated(EnumType.STRING)
    @Column(name = "fgts_opcao")
    private FgtsOpcaoEnum fgtsOpcao;

    @Enumerated(EnumType.STRING)
    @Column(name = "t_ipo_documento_enum")
    private TipoDocumentoEnum tIpoDocumentoEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodo_experiencia")
    private PeriodoExperienciaEnum periodoExperiencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_admisao_enum")
    private TipoAdmisaoEnum tipoAdmisaoEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodo_intermitente")
    private PeriodoIntermitenteEnum periodoIntermitente;

    @Enumerated(EnumType.STRING)
    @Column(name = "indicativo_admissao")
    private IndicativoAdmissaoEnum indicativoAdmissao;

    @Column(name = "numero_pis_nis_pasep")
    private Integer numeroPisNisPasep;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "contratoFuncionarios", "cidade" }, allowSetters = true)
    private AgenteIntegracaoEstagio agenteIntegracaoEstagio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "contratoFuncionarios", "cidade" }, allowSetters = true)
    private InstituicaoEnsino instituicaoEnsino;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContratoFuncionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSalarioFixo() {
        return this.salarioFixo;
    }

    public ContratoFuncionario salarioFixo(Boolean salarioFixo) {
        this.setSalarioFixo(salarioFixo);
        return this;
    }

    public void setSalarioFixo(Boolean salarioFixo) {
        this.salarioFixo = salarioFixo;
    }

    public Boolean getSalarioVariavel() {
        return this.salarioVariavel;
    }

    public ContratoFuncionario salarioVariavel(Boolean salarioVariavel) {
        this.setSalarioVariavel(salarioVariavel);
        return this;
    }

    public void setSalarioVariavel(Boolean salarioVariavel) {
        this.salarioVariavel = salarioVariavel;
    }

    public Boolean getEstagio() {
        return this.estagio;
    }

    public ContratoFuncionario estagio(Boolean estagio) {
        this.setEstagio(estagio);
        return this;
    }

    public void setEstagio(Boolean estagio) {
        this.estagio = estagio;
    }

    public NaturezaEstagioEnum getNaturezaEstagioEnum() {
        return this.naturezaEstagioEnum;
    }

    public ContratoFuncionario naturezaEstagioEnum(NaturezaEstagioEnum naturezaEstagioEnum) {
        this.setNaturezaEstagioEnum(naturezaEstagioEnum);
        return this;
    }

    public void setNaturezaEstagioEnum(NaturezaEstagioEnum naturezaEstagioEnum) {
        this.naturezaEstagioEnum = naturezaEstagioEnum;
    }

    public String getCtps() {
        return this.ctps;
    }

    public ContratoFuncionario ctps(String ctps) {
        this.setCtps(ctps);
        return this;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public Integer getSerieCtps() {
        return this.serieCtps;
    }

    public ContratoFuncionario serieCtps(Integer serieCtps) {
        this.setSerieCtps(serieCtps);
        return this;
    }

    public void setSerieCtps(Integer serieCtps) {
        this.serieCtps = serieCtps;
    }

    public String getOrgaoEmissorDocumento() {
        return this.orgaoEmissorDocumento;
    }

    public ContratoFuncionario orgaoEmissorDocumento(String orgaoEmissorDocumento) {
        this.setOrgaoEmissorDocumento(orgaoEmissorDocumento);
        return this;
    }

    public void setOrgaoEmissorDocumento(String orgaoEmissorDocumento) {
        this.orgaoEmissorDocumento = orgaoEmissorDocumento;
    }

    public String getDataValidadeDocumento() {
        return this.dataValidadeDocumento;
    }

    public ContratoFuncionario dataValidadeDocumento(String dataValidadeDocumento) {
        this.setDataValidadeDocumento(dataValidadeDocumento);
        return this;
    }

    public void setDataValidadeDocumento(String dataValidadeDocumento) {
        this.dataValidadeDocumento = dataValidadeDocumento;
    }

    public String getDataAdmissao() {
        return this.dataAdmissao;
    }

    public ContratoFuncionario dataAdmissao(String dataAdmissao) {
        this.setDataAdmissao(dataAdmissao);
        return this;
    }

    public void setDataAdmissao(String dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getCargo() {
        return this.cargo;
    }

    public ContratoFuncionario cargo(String cargo) {
        this.setCargo(cargo);
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDescricaoAtividades() {
        return this.descricaoAtividades;
    }

    public ContratoFuncionario descricaoAtividades(String descricaoAtividades) {
        this.setDescricaoAtividades(descricaoAtividades);
        return this;
    }

    public void setDescricaoAtividades(String descricaoAtividades) {
        this.descricaoAtividades = descricaoAtividades;
    }

    public SituacaoFuncionarioEnum getSituacao() {
        return this.situacao;
    }

    public ContratoFuncionario situacao(SituacaoFuncionarioEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoFuncionarioEnum situacao) {
        this.situacao = situacao;
    }

    public String getValorSalarioFixo() {
        return this.valorSalarioFixo;
    }

    public ContratoFuncionario valorSalarioFixo(String valorSalarioFixo) {
        this.setValorSalarioFixo(valorSalarioFixo);
        return this;
    }

    public void setValorSalarioFixo(String valorSalarioFixo) {
        this.valorSalarioFixo = valorSalarioFixo;
    }

    public String getValorSalarioVariavel() {
        return this.valorSalarioVariavel;
    }

    public ContratoFuncionario valorSalarioVariavel(String valorSalarioVariavel) {
        this.setValorSalarioVariavel(valorSalarioVariavel);
        return this;
    }

    public void setValorSalarioVariavel(String valorSalarioVariavel) {
        this.valorSalarioVariavel = valorSalarioVariavel;
    }

    public String getDataTerminoContrato() {
        return this.dataTerminoContrato;
    }

    public ContratoFuncionario dataTerminoContrato(String dataTerminoContrato) {
        this.setDataTerminoContrato(dataTerminoContrato);
        return this;
    }

    public void setDataTerminoContrato(String dataTerminoContrato) {
        this.dataTerminoContrato = dataTerminoContrato;
    }

    public String getDatainicioContrato() {
        return this.datainicioContrato;
    }

    public ContratoFuncionario datainicioContrato(String datainicioContrato) {
        this.setDatainicioContrato(datainicioContrato);
        return this;
    }

    public void setDatainicioContrato(String datainicioContrato) {
        this.datainicioContrato = datainicioContrato;
    }

    public Integer getHorasATrabalhadar() {
        return this.horasATrabalhadar;
    }

    public ContratoFuncionario horasATrabalhadar(Integer horasATrabalhadar) {
        this.setHorasATrabalhadar(horasATrabalhadar);
        return this;
    }

    public void setHorasATrabalhadar(Integer horasATrabalhadar) {
        this.horasATrabalhadar = horasATrabalhadar;
    }

    public String getCodigoCargo() {
        return this.codigoCargo;
    }

    public ContratoFuncionario codigoCargo(String codigoCargo) {
        this.setCodigoCargo(codigoCargo);
        return this;
    }

    public void setCodigoCargo(String codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    public CategoriaTrabalhadorEnum getCategoriaTrabalhador() {
        return this.categoriaTrabalhador;
    }

    public ContratoFuncionario categoriaTrabalhador(CategoriaTrabalhadorEnum categoriaTrabalhador) {
        this.setCategoriaTrabalhador(categoriaTrabalhador);
        return this;
    }

    public void setCategoriaTrabalhador(CategoriaTrabalhadorEnum categoriaTrabalhador) {
        this.categoriaTrabalhador = categoriaTrabalhador;
    }

    public TipoVinculoTrabalhoEnum getTipoVinculoTrabalho() {
        return this.tipoVinculoTrabalho;
    }

    public ContratoFuncionario tipoVinculoTrabalho(TipoVinculoTrabalhoEnum tipoVinculoTrabalho) {
        this.setTipoVinculoTrabalho(tipoVinculoTrabalho);
        return this;
    }

    public void setTipoVinculoTrabalho(TipoVinculoTrabalhoEnum tipoVinculoTrabalho) {
        this.tipoVinculoTrabalho = tipoVinculoTrabalho;
    }

    public FgtsOpcaoEnum getFgtsOpcao() {
        return this.fgtsOpcao;
    }

    public ContratoFuncionario fgtsOpcao(FgtsOpcaoEnum fgtsOpcao) {
        this.setFgtsOpcao(fgtsOpcao);
        return this;
    }

    public void setFgtsOpcao(FgtsOpcaoEnum fgtsOpcao) {
        this.fgtsOpcao = fgtsOpcao;
    }

    public TipoDocumentoEnum gettIpoDocumentoEnum() {
        return this.tIpoDocumentoEnum;
    }

    public ContratoFuncionario tIpoDocumentoEnum(TipoDocumentoEnum tIpoDocumentoEnum) {
        this.settIpoDocumentoEnum(tIpoDocumentoEnum);
        return this;
    }

    public void settIpoDocumentoEnum(TipoDocumentoEnum tIpoDocumentoEnum) {
        this.tIpoDocumentoEnum = tIpoDocumentoEnum;
    }

    public PeriodoExperienciaEnum getPeriodoExperiencia() {
        return this.periodoExperiencia;
    }

    public ContratoFuncionario periodoExperiencia(PeriodoExperienciaEnum periodoExperiencia) {
        this.setPeriodoExperiencia(periodoExperiencia);
        return this;
    }

    public void setPeriodoExperiencia(PeriodoExperienciaEnum periodoExperiencia) {
        this.periodoExperiencia = periodoExperiencia;
    }

    public TipoAdmisaoEnum getTipoAdmisaoEnum() {
        return this.tipoAdmisaoEnum;
    }

    public ContratoFuncionario tipoAdmisaoEnum(TipoAdmisaoEnum tipoAdmisaoEnum) {
        this.setTipoAdmisaoEnum(tipoAdmisaoEnum);
        return this;
    }

    public void setTipoAdmisaoEnum(TipoAdmisaoEnum tipoAdmisaoEnum) {
        this.tipoAdmisaoEnum = tipoAdmisaoEnum;
    }

    public PeriodoIntermitenteEnum getPeriodoIntermitente() {
        return this.periodoIntermitente;
    }

    public ContratoFuncionario periodoIntermitente(PeriodoIntermitenteEnum periodoIntermitente) {
        this.setPeriodoIntermitente(periodoIntermitente);
        return this;
    }

    public void setPeriodoIntermitente(PeriodoIntermitenteEnum periodoIntermitente) {
        this.periodoIntermitente = periodoIntermitente;
    }

    public IndicativoAdmissaoEnum getIndicativoAdmissao() {
        return this.indicativoAdmissao;
    }

    public ContratoFuncionario indicativoAdmissao(IndicativoAdmissaoEnum indicativoAdmissao) {
        this.setIndicativoAdmissao(indicativoAdmissao);
        return this;
    }

    public void setIndicativoAdmissao(IndicativoAdmissaoEnum indicativoAdmissao) {
        this.indicativoAdmissao = indicativoAdmissao;
    }

    public Integer getNumeroPisNisPasep() {
        return this.numeroPisNisPasep;
    }

    public ContratoFuncionario numeroPisNisPasep(Integer numeroPisNisPasep) {
        this.setNumeroPisNisPasep(numeroPisNisPasep);
        return this;
    }

    public void setNumeroPisNisPasep(Integer numeroPisNisPasep) {
        this.numeroPisNisPasep = numeroPisNisPasep;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public ContratoFuncionario funcionario(Funcionario funcionario) {
        this.setFuncionario(funcionario);
        return this;
    }

    public AgenteIntegracaoEstagio getAgenteIntegracaoEstagio() {
        return this.agenteIntegracaoEstagio;
    }

    public void setAgenteIntegracaoEstagio(AgenteIntegracaoEstagio agenteIntegracaoEstagio) {
        this.agenteIntegracaoEstagio = agenteIntegracaoEstagio;
    }

    public ContratoFuncionario agenteIntegracaoEstagio(AgenteIntegracaoEstagio agenteIntegracaoEstagio) {
        this.setAgenteIntegracaoEstagio(agenteIntegracaoEstagio);
        return this;
    }

    public InstituicaoEnsino getInstituicaoEnsino() {
        return this.instituicaoEnsino;
    }

    public void setInstituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        this.instituicaoEnsino = instituicaoEnsino;
    }

    public ContratoFuncionario instituicaoEnsino(InstituicaoEnsino instituicaoEnsino) {
        this.setInstituicaoEnsino(instituicaoEnsino);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContratoFuncionario)) {
            return false;
        }
        return getId() != null && getId().equals(((ContratoFuncionario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContratoFuncionario{" +
            "id=" + getId() +
            ", salarioFixo='" + getSalarioFixo() + "'" +
            ", salarioVariavel='" + getSalarioVariavel() + "'" +
            ", estagio='" + getEstagio() + "'" +
            ", naturezaEstagioEnum='" + getNaturezaEstagioEnum() + "'" +
            ", ctps='" + getCtps() + "'" +
            ", serieCtps=" + getSerieCtps() +
            ", orgaoEmissorDocumento='" + getOrgaoEmissorDocumento() + "'" +
            ", dataValidadeDocumento='" + getDataValidadeDocumento() + "'" +
            ", dataAdmissao='" + getDataAdmissao() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", descricaoAtividades='" + getDescricaoAtividades() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", valorSalarioFixo='" + getValorSalarioFixo() + "'" +
            ", valorSalarioVariavel='" + getValorSalarioVariavel() + "'" +
            ", dataTerminoContrato='" + getDataTerminoContrato() + "'" +
            ", datainicioContrato='" + getDatainicioContrato() + "'" +
            ", horasATrabalhadar=" + getHorasATrabalhadar() +
            ", codigoCargo='" + getCodigoCargo() + "'" +
            ", categoriaTrabalhador='" + getCategoriaTrabalhador() + "'" +
            ", tipoVinculoTrabalho='" + getTipoVinculoTrabalho() + "'" +
            ", fgtsOpcao='" + getFgtsOpcao() + "'" +
            ", tIpoDocumentoEnum='" + gettIpoDocumentoEnum() + "'" +
            ", periodoExperiencia='" + getPeriodoExperiencia() + "'" +
            ", tipoAdmisaoEnum='" + getTipoAdmisaoEnum() + "'" +
            ", periodoIntermitente='" + getPeriodoIntermitente() + "'" +
            ", indicativoAdmissao='" + getIndicativoAdmissao() + "'" +
            ", numeroPisNisPasep=" + getNumeroPisNisPasep() +
            "}";
    }
}
