package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.EstadoCivilEnum;
import com.dobemcontabilidade.domain.enumeration.PessoaComDeficienciaEnum;
import com.dobemcontabilidade.domain.enumeration.RacaECorEnum;
import com.dobemcontabilidade.domain.enumeration.SexoEnum;
import com.dobemcontabilidade.domain.enumeration.SituacaoContadorEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * ENTITIES
 */
@Schema(description = "ENTITIES")
@Entity
@Table(name = "contador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @NotNull
    @Size(max = 200)
    @Column(name = "cpf", length = 200, nullable = false)
    private String cpf;

    @Size(max = 15)
    @Column(name = "data_nascimento", length = 15)
    private String dataNascimento;

    @Column(name = "titulo_eleitor")
    private Integer tituloEleitor;

    @NotNull
    @Size(max = 10)
    @Column(name = "rg", length = 10, nullable = false)
    private String rg;

    @Column(name = "rg_orgao_expeditor")
    private String rgOrgaoExpeditor;

    @Column(name = "rg_uf_expedicao")
    private String rgUfExpedicao;

    @Size(max = 200)
    @Column(name = "nome_mae", length = 200)
    private String nomeMae;

    @Size(max = 200)
    @Column(name = "nome_pai", length = 200)
    private String nomePai;

    @Column(name = "local_nascimento")
    private String localNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "raca_e_cor")
    private RacaECorEnum racaECor;

    @Enumerated(EnumType.STRING)
    @Column(name = "pessoa_com_deficiencia")
    private PessoaComDeficienciaEnum pessoaComDeficiencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivilEnum estadoCivil;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private SexoEnum sexo;

    @Column(name = "url_foto_perfil")
    private String urlFotoPerfil;

    @Column(name = "rg_orgao_expditor")
    private String rgOrgaoExpditor;

    @NotNull
    @Column(name = "crc", nullable = false)
    private String crc;

    @Column(name = "limite_empresas")
    private Integer limiteEmpresas;

    @Column(name = "limite_area_contabils")
    private Integer limiteAreaContabils;

    @Column(name = "limite_faturamento")
    private Double limiteFaturamento;

    @Column(name = "limite_departamentos")
    private Integer limiteDepartamentos;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_contador")
    private SituacaoContadorEnum situacaoContador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Contador nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public Contador cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return this.dataNascimento;
    }

    public Contador dataNascimento(String dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getTituloEleitor() {
        return this.tituloEleitor;
    }

    public Contador tituloEleitor(Integer tituloEleitor) {
        this.setTituloEleitor(tituloEleitor);
        return this;
    }

    public void setTituloEleitor(Integer tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    public String getRg() {
        return this.rg;
    }

    public Contador rg(String rg) {
        this.setRg(rg);
        return this;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getRgOrgaoExpeditor() {
        return this.rgOrgaoExpeditor;
    }

    public Contador rgOrgaoExpeditor(String rgOrgaoExpeditor) {
        this.setRgOrgaoExpeditor(rgOrgaoExpeditor);
        return this;
    }

    public void setRgOrgaoExpeditor(String rgOrgaoExpeditor) {
        this.rgOrgaoExpeditor = rgOrgaoExpeditor;
    }

    public String getRgUfExpedicao() {
        return this.rgUfExpedicao;
    }

    public Contador rgUfExpedicao(String rgUfExpedicao) {
        this.setRgUfExpedicao(rgUfExpedicao);
        return this;
    }

    public void setRgUfExpedicao(String rgUfExpedicao) {
        this.rgUfExpedicao = rgUfExpedicao;
    }

    public String getNomeMae() {
        return this.nomeMae;
    }

    public Contador nomeMae(String nomeMae) {
        this.setNomeMae(nomeMae);
        return this;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return this.nomePai;
    }

    public Contador nomePai(String nomePai) {
        this.setNomePai(nomePai);
        return this;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getLocalNascimento() {
        return this.localNascimento;
    }

    public Contador localNascimento(String localNascimento) {
        this.setLocalNascimento(localNascimento);
        return this;
    }

    public void setLocalNascimento(String localNascimento) {
        this.localNascimento = localNascimento;
    }

    public RacaECorEnum getRacaECor() {
        return this.racaECor;
    }

    public Contador racaECor(RacaECorEnum racaECor) {
        this.setRacaECor(racaECor);
        return this;
    }

    public void setRacaECor(RacaECorEnum racaECor) {
        this.racaECor = racaECor;
    }

    public PessoaComDeficienciaEnum getPessoaComDeficiencia() {
        return this.pessoaComDeficiencia;
    }

    public Contador pessoaComDeficiencia(PessoaComDeficienciaEnum pessoaComDeficiencia) {
        this.setPessoaComDeficiencia(pessoaComDeficiencia);
        return this;
    }

    public void setPessoaComDeficiencia(PessoaComDeficienciaEnum pessoaComDeficiencia) {
        this.pessoaComDeficiencia = pessoaComDeficiencia;
    }

    public EstadoCivilEnum getEstadoCivil() {
        return this.estadoCivil;
    }

    public Contador estadoCivil(EstadoCivilEnum estadoCivil) {
        this.setEstadoCivil(estadoCivil);
        return this;
    }

    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public SexoEnum getSexo() {
        return this.sexo;
    }

    public Contador sexo(SexoEnum sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public String getUrlFotoPerfil() {
        return this.urlFotoPerfil;
    }

    public Contador urlFotoPerfil(String urlFotoPerfil) {
        this.setUrlFotoPerfil(urlFotoPerfil);
        return this;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public String getRgOrgaoExpditor() {
        return this.rgOrgaoExpditor;
    }

    public Contador rgOrgaoExpditor(String rgOrgaoExpditor) {
        this.setRgOrgaoExpditor(rgOrgaoExpditor);
        return this;
    }

    public void setRgOrgaoExpditor(String rgOrgaoExpditor) {
        this.rgOrgaoExpditor = rgOrgaoExpditor;
    }

    public String getCrc() {
        return this.crc;
    }

    public Contador crc(String crc) {
        this.setCrc(crc);
        return this;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public Integer getLimiteEmpresas() {
        return this.limiteEmpresas;
    }

    public Contador limiteEmpresas(Integer limiteEmpresas) {
        this.setLimiteEmpresas(limiteEmpresas);
        return this;
    }

    public void setLimiteEmpresas(Integer limiteEmpresas) {
        this.limiteEmpresas = limiteEmpresas;
    }

    public Integer getLimiteAreaContabils() {
        return this.limiteAreaContabils;
    }

    public Contador limiteAreaContabils(Integer limiteAreaContabils) {
        this.setLimiteAreaContabils(limiteAreaContabils);
        return this;
    }

    public void setLimiteAreaContabils(Integer limiteAreaContabils) {
        this.limiteAreaContabils = limiteAreaContabils;
    }

    public Double getLimiteFaturamento() {
        return this.limiteFaturamento;
    }

    public Contador limiteFaturamento(Double limiteFaturamento) {
        this.setLimiteFaturamento(limiteFaturamento);
        return this;
    }

    public void setLimiteFaturamento(Double limiteFaturamento) {
        this.limiteFaturamento = limiteFaturamento;
    }

    public Integer getLimiteDepartamentos() {
        return this.limiteDepartamentos;
    }

    public Contador limiteDepartamentos(Integer limiteDepartamentos) {
        this.setLimiteDepartamentos(limiteDepartamentos);
        return this;
    }

    public void setLimiteDepartamentos(Integer limiteDepartamentos) {
        this.limiteDepartamentos = limiteDepartamentos;
    }

    public SituacaoContadorEnum getSituacaoContador() {
        return this.situacaoContador;
    }

    public Contador situacaoContador(SituacaoContadorEnum situacaoContador) {
        this.setSituacaoContador(situacaoContador);
        return this;
    }

    public void setSituacaoContador(SituacaoContadorEnum situacaoContador) {
        this.situacaoContador = situacaoContador;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contador)) {
            return false;
        }
        return getId() != null && getId().equals(((Contador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contador{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", tituloEleitor=" + getTituloEleitor() +
            ", rg='" + getRg() + "'" +
            ", rgOrgaoExpeditor='" + getRgOrgaoExpeditor() + "'" +
            ", rgUfExpedicao='" + getRgUfExpedicao() + "'" +
            ", nomeMae='" + getNomeMae() + "'" +
            ", nomePai='" + getNomePai() + "'" +
            ", localNascimento='" + getLocalNascimento() + "'" +
            ", racaECor='" + getRacaECor() + "'" +
            ", pessoaComDeficiencia='" + getPessoaComDeficiencia() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", urlFotoPerfil='" + getUrlFotoPerfil() + "'" +
            ", rgOrgaoExpditor='" + getRgOrgaoExpditor() + "'" +
            ", crc='" + getCrc() + "'" +
            ", limiteEmpresas=" + getLimiteEmpresas() +
            ", limiteAreaContabils=" + getLimiteAreaContabils() +
            ", limiteFaturamento=" + getLimiteFaturamento() +
            ", limiteDepartamentos=" + getLimiteDepartamentos() +
            ", situacaoContador='" + getSituacaoContador() + "'" +
            "}";
    }
}
