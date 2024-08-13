package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.EstadoCivilEnum;
import com.dobemcontabilidade.domain.enumeration.PessoaComDeficienciaEnum;
import com.dobemcontabilidade.domain.enumeration.RacaECorEnum;
import com.dobemcontabilidade.domain.enumeration.SexoEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Pessoa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PessoaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pessoas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PessoaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering RacaECorEnum
     */
    public static class RacaECorEnumFilter extends Filter<RacaECorEnum> {

        public RacaECorEnumFilter() {}

        public RacaECorEnumFilter(RacaECorEnumFilter filter) {
            super(filter);
        }

        @Override
        public RacaECorEnumFilter copy() {
            return new RacaECorEnumFilter(this);
        }
    }

    /**
     * Class for filtering PessoaComDeficienciaEnum
     */
    public static class PessoaComDeficienciaEnumFilter extends Filter<PessoaComDeficienciaEnum> {

        public PessoaComDeficienciaEnumFilter() {}

        public PessoaComDeficienciaEnumFilter(PessoaComDeficienciaEnumFilter filter) {
            super(filter);
        }

        @Override
        public PessoaComDeficienciaEnumFilter copy() {
            return new PessoaComDeficienciaEnumFilter(this);
        }
    }

    /**
     * Class for filtering EstadoCivilEnum
     */
    public static class EstadoCivilEnumFilter extends Filter<EstadoCivilEnum> {

        public EstadoCivilEnumFilter() {}

        public EstadoCivilEnumFilter(EstadoCivilEnumFilter filter) {
            super(filter);
        }

        @Override
        public EstadoCivilEnumFilter copy() {
            return new EstadoCivilEnumFilter(this);
        }
    }

    /**
     * Class for filtering SexoEnum
     */
    public static class SexoEnumFilter extends Filter<SexoEnum> {

        public SexoEnumFilter() {}

        public SexoEnumFilter(SexoEnumFilter filter) {
            super(filter);
        }

        @Override
        public SexoEnumFilter copy() {
            return new SexoEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter cpf;

    private StringFilter dataNascimento;

    private IntegerFilter tituloEleitor;

    private StringFilter rg;

    private StringFilter rgOrgaoExpeditor;

    private StringFilter rgUfExpedicao;

    private StringFilter nomeMae;

    private StringFilter nomePai;

    private StringFilter localNascimento;

    private RacaECorEnumFilter racaECor;

    private PessoaComDeficienciaEnumFilter pessoaComDeficiencia;

    private EstadoCivilEnumFilter estadoCivil;

    private SexoEnumFilter sexo;

    private StringFilter urlFotoPerfil;

    private LongFilter funcionarioId;

    private LongFilter anexoPessoaId;

    private LongFilter escolaridadePessoaId;

    private LongFilter bancoPessoaId;

    private LongFilter dependentesFuncionarioId;

    private LongFilter enderecoPessoaId;

    private LongFilter emailId;

    private LongFilter telefoneId;

    private LongFilter administradorId;

    private LongFilter contadorId;

    private LongFilter socioId;

    private Boolean distinct;

    public PessoaCriteria() {}

    public PessoaCriteria(PessoaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.cpf = other.optionalCpf().map(StringFilter::copy).orElse(null);
        this.dataNascimento = other.optionalDataNascimento().map(StringFilter::copy).orElse(null);
        this.tituloEleitor = other.optionalTituloEleitor().map(IntegerFilter::copy).orElse(null);
        this.rg = other.optionalRg().map(StringFilter::copy).orElse(null);
        this.rgOrgaoExpeditor = other.optionalRgOrgaoExpeditor().map(StringFilter::copy).orElse(null);
        this.rgUfExpedicao = other.optionalRgUfExpedicao().map(StringFilter::copy).orElse(null);
        this.nomeMae = other.optionalNomeMae().map(StringFilter::copy).orElse(null);
        this.nomePai = other.optionalNomePai().map(StringFilter::copy).orElse(null);
        this.localNascimento = other.optionalLocalNascimento().map(StringFilter::copy).orElse(null);
        this.racaECor = other.optionalRacaECor().map(RacaECorEnumFilter::copy).orElse(null);
        this.pessoaComDeficiencia = other.optionalPessoaComDeficiencia().map(PessoaComDeficienciaEnumFilter::copy).orElse(null);
        this.estadoCivil = other.optionalEstadoCivil().map(EstadoCivilEnumFilter::copy).orElse(null);
        this.sexo = other.optionalSexo().map(SexoEnumFilter::copy).orElse(null);
        this.urlFotoPerfil = other.optionalUrlFotoPerfil().map(StringFilter::copy).orElse(null);
        this.funcionarioId = other.optionalFuncionarioId().map(LongFilter::copy).orElse(null);
        this.anexoPessoaId = other.optionalAnexoPessoaId().map(LongFilter::copy).orElse(null);
        this.escolaridadePessoaId = other.optionalEscolaridadePessoaId().map(LongFilter::copy).orElse(null);
        this.bancoPessoaId = other.optionalBancoPessoaId().map(LongFilter::copy).orElse(null);
        this.dependentesFuncionarioId = other.optionalDependentesFuncionarioId().map(LongFilter::copy).orElse(null);
        this.enderecoPessoaId = other.optionalEnderecoPessoaId().map(LongFilter::copy).orElse(null);
        this.emailId = other.optionalEmailId().map(LongFilter::copy).orElse(null);
        this.telefoneId = other.optionalTelefoneId().map(LongFilter::copy).orElse(null);
        this.administradorId = other.optionalAdministradorId().map(LongFilter::copy).orElse(null);
        this.contadorId = other.optionalContadorId().map(LongFilter::copy).orElse(null);
        this.socioId = other.optionalSocioId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PessoaCriteria copy() {
        return new PessoaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public Optional<StringFilter> optionalNome() {
        return Optional.ofNullable(nome);
    }

    public StringFilter nome() {
        if (nome == null) {
            setNome(new StringFilter());
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getCpf() {
        return cpf;
    }

    public Optional<StringFilter> optionalCpf() {
        return Optional.ofNullable(cpf);
    }

    public StringFilter cpf() {
        if (cpf == null) {
            setCpf(new StringFilter());
        }
        return cpf;
    }

    public void setCpf(StringFilter cpf) {
        this.cpf = cpf;
    }

    public StringFilter getDataNascimento() {
        return dataNascimento;
    }

    public Optional<StringFilter> optionalDataNascimento() {
        return Optional.ofNullable(dataNascimento);
    }

    public StringFilter dataNascimento() {
        if (dataNascimento == null) {
            setDataNascimento(new StringFilter());
        }
        return dataNascimento;
    }

    public void setDataNascimento(StringFilter dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public IntegerFilter getTituloEleitor() {
        return tituloEleitor;
    }

    public Optional<IntegerFilter> optionalTituloEleitor() {
        return Optional.ofNullable(tituloEleitor);
    }

    public IntegerFilter tituloEleitor() {
        if (tituloEleitor == null) {
            setTituloEleitor(new IntegerFilter());
        }
        return tituloEleitor;
    }

    public void setTituloEleitor(IntegerFilter tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    public StringFilter getRg() {
        return rg;
    }

    public Optional<StringFilter> optionalRg() {
        return Optional.ofNullable(rg);
    }

    public StringFilter rg() {
        if (rg == null) {
            setRg(new StringFilter());
        }
        return rg;
    }

    public void setRg(StringFilter rg) {
        this.rg = rg;
    }

    public StringFilter getRgOrgaoExpeditor() {
        return rgOrgaoExpeditor;
    }

    public Optional<StringFilter> optionalRgOrgaoExpeditor() {
        return Optional.ofNullable(rgOrgaoExpeditor);
    }

    public StringFilter rgOrgaoExpeditor() {
        if (rgOrgaoExpeditor == null) {
            setRgOrgaoExpeditor(new StringFilter());
        }
        return rgOrgaoExpeditor;
    }

    public void setRgOrgaoExpeditor(StringFilter rgOrgaoExpeditor) {
        this.rgOrgaoExpeditor = rgOrgaoExpeditor;
    }

    public StringFilter getRgUfExpedicao() {
        return rgUfExpedicao;
    }

    public Optional<StringFilter> optionalRgUfExpedicao() {
        return Optional.ofNullable(rgUfExpedicao);
    }

    public StringFilter rgUfExpedicao() {
        if (rgUfExpedicao == null) {
            setRgUfExpedicao(new StringFilter());
        }
        return rgUfExpedicao;
    }

    public void setRgUfExpedicao(StringFilter rgUfExpedicao) {
        this.rgUfExpedicao = rgUfExpedicao;
    }

    public StringFilter getNomeMae() {
        return nomeMae;
    }

    public Optional<StringFilter> optionalNomeMae() {
        return Optional.ofNullable(nomeMae);
    }

    public StringFilter nomeMae() {
        if (nomeMae == null) {
            setNomeMae(new StringFilter());
        }
        return nomeMae;
    }

    public void setNomeMae(StringFilter nomeMae) {
        this.nomeMae = nomeMae;
    }

    public StringFilter getNomePai() {
        return nomePai;
    }

    public Optional<StringFilter> optionalNomePai() {
        return Optional.ofNullable(nomePai);
    }

    public StringFilter nomePai() {
        if (nomePai == null) {
            setNomePai(new StringFilter());
        }
        return nomePai;
    }

    public void setNomePai(StringFilter nomePai) {
        this.nomePai = nomePai;
    }

    public StringFilter getLocalNascimento() {
        return localNascimento;
    }

    public Optional<StringFilter> optionalLocalNascimento() {
        return Optional.ofNullable(localNascimento);
    }

    public StringFilter localNascimento() {
        if (localNascimento == null) {
            setLocalNascimento(new StringFilter());
        }
        return localNascimento;
    }

    public void setLocalNascimento(StringFilter localNascimento) {
        this.localNascimento = localNascimento;
    }

    public RacaECorEnumFilter getRacaECor() {
        return racaECor;
    }

    public Optional<RacaECorEnumFilter> optionalRacaECor() {
        return Optional.ofNullable(racaECor);
    }

    public RacaECorEnumFilter racaECor() {
        if (racaECor == null) {
            setRacaECor(new RacaECorEnumFilter());
        }
        return racaECor;
    }

    public void setRacaECor(RacaECorEnumFilter racaECor) {
        this.racaECor = racaECor;
    }

    public PessoaComDeficienciaEnumFilter getPessoaComDeficiencia() {
        return pessoaComDeficiencia;
    }

    public Optional<PessoaComDeficienciaEnumFilter> optionalPessoaComDeficiencia() {
        return Optional.ofNullable(pessoaComDeficiencia);
    }

    public PessoaComDeficienciaEnumFilter pessoaComDeficiencia() {
        if (pessoaComDeficiencia == null) {
            setPessoaComDeficiencia(new PessoaComDeficienciaEnumFilter());
        }
        return pessoaComDeficiencia;
    }

    public void setPessoaComDeficiencia(PessoaComDeficienciaEnumFilter pessoaComDeficiencia) {
        this.pessoaComDeficiencia = pessoaComDeficiencia;
    }

    public EstadoCivilEnumFilter getEstadoCivil() {
        return estadoCivil;
    }

    public Optional<EstadoCivilEnumFilter> optionalEstadoCivil() {
        return Optional.ofNullable(estadoCivil);
    }

    public EstadoCivilEnumFilter estadoCivil() {
        if (estadoCivil == null) {
            setEstadoCivil(new EstadoCivilEnumFilter());
        }
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivilEnumFilter estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public SexoEnumFilter getSexo() {
        return sexo;
    }

    public Optional<SexoEnumFilter> optionalSexo() {
        return Optional.ofNullable(sexo);
    }

    public SexoEnumFilter sexo() {
        if (sexo == null) {
            setSexo(new SexoEnumFilter());
        }
        return sexo;
    }

    public void setSexo(SexoEnumFilter sexo) {
        this.sexo = sexo;
    }

    public StringFilter getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public Optional<StringFilter> optionalUrlFotoPerfil() {
        return Optional.ofNullable(urlFotoPerfil);
    }

    public StringFilter urlFotoPerfil() {
        if (urlFotoPerfil == null) {
            setUrlFotoPerfil(new StringFilter());
        }
        return urlFotoPerfil;
    }

    public void setUrlFotoPerfil(StringFilter urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public LongFilter getFuncionarioId() {
        return funcionarioId;
    }

    public Optional<LongFilter> optionalFuncionarioId() {
        return Optional.ofNullable(funcionarioId);
    }

    public LongFilter funcionarioId() {
        if (funcionarioId == null) {
            setFuncionarioId(new LongFilter());
        }
        return funcionarioId;
    }

    public void setFuncionarioId(LongFilter funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public LongFilter getAnexoPessoaId() {
        return anexoPessoaId;
    }

    public Optional<LongFilter> optionalAnexoPessoaId() {
        return Optional.ofNullable(anexoPessoaId);
    }

    public LongFilter anexoPessoaId() {
        if (anexoPessoaId == null) {
            setAnexoPessoaId(new LongFilter());
        }
        return anexoPessoaId;
    }

    public void setAnexoPessoaId(LongFilter anexoPessoaId) {
        this.anexoPessoaId = anexoPessoaId;
    }

    public LongFilter getEscolaridadePessoaId() {
        return escolaridadePessoaId;
    }

    public Optional<LongFilter> optionalEscolaridadePessoaId() {
        return Optional.ofNullable(escolaridadePessoaId);
    }

    public LongFilter escolaridadePessoaId() {
        if (escolaridadePessoaId == null) {
            setEscolaridadePessoaId(new LongFilter());
        }
        return escolaridadePessoaId;
    }

    public void setEscolaridadePessoaId(LongFilter escolaridadePessoaId) {
        this.escolaridadePessoaId = escolaridadePessoaId;
    }

    public LongFilter getBancoPessoaId() {
        return bancoPessoaId;
    }

    public Optional<LongFilter> optionalBancoPessoaId() {
        return Optional.ofNullable(bancoPessoaId);
    }

    public LongFilter bancoPessoaId() {
        if (bancoPessoaId == null) {
            setBancoPessoaId(new LongFilter());
        }
        return bancoPessoaId;
    }

    public void setBancoPessoaId(LongFilter bancoPessoaId) {
        this.bancoPessoaId = bancoPessoaId;
    }

    public LongFilter getDependentesFuncionarioId() {
        return dependentesFuncionarioId;
    }

    public Optional<LongFilter> optionalDependentesFuncionarioId() {
        return Optional.ofNullable(dependentesFuncionarioId);
    }

    public LongFilter dependentesFuncionarioId() {
        if (dependentesFuncionarioId == null) {
            setDependentesFuncionarioId(new LongFilter());
        }
        return dependentesFuncionarioId;
    }

    public void setDependentesFuncionarioId(LongFilter dependentesFuncionarioId) {
        this.dependentesFuncionarioId = dependentesFuncionarioId;
    }

    public LongFilter getEnderecoPessoaId() {
        return enderecoPessoaId;
    }

    public Optional<LongFilter> optionalEnderecoPessoaId() {
        return Optional.ofNullable(enderecoPessoaId);
    }

    public LongFilter enderecoPessoaId() {
        if (enderecoPessoaId == null) {
            setEnderecoPessoaId(new LongFilter());
        }
        return enderecoPessoaId;
    }

    public void setEnderecoPessoaId(LongFilter enderecoPessoaId) {
        this.enderecoPessoaId = enderecoPessoaId;
    }

    public LongFilter getEmailId() {
        return emailId;
    }

    public Optional<LongFilter> optionalEmailId() {
        return Optional.ofNullable(emailId);
    }

    public LongFilter emailId() {
        if (emailId == null) {
            setEmailId(new LongFilter());
        }
        return emailId;
    }

    public void setEmailId(LongFilter emailId) {
        this.emailId = emailId;
    }

    public LongFilter getTelefoneId() {
        return telefoneId;
    }

    public Optional<LongFilter> optionalTelefoneId() {
        return Optional.ofNullable(telefoneId);
    }

    public LongFilter telefoneId() {
        if (telefoneId == null) {
            setTelefoneId(new LongFilter());
        }
        return telefoneId;
    }

    public void setTelefoneId(LongFilter telefoneId) {
        this.telefoneId = telefoneId;
    }

    public LongFilter getAdministradorId() {
        return administradorId;
    }

    public Optional<LongFilter> optionalAdministradorId() {
        return Optional.ofNullable(administradorId);
    }

    public LongFilter administradorId() {
        if (administradorId == null) {
            setAdministradorId(new LongFilter());
        }
        return administradorId;
    }

    public void setAdministradorId(LongFilter administradorId) {
        this.administradorId = administradorId;
    }

    public LongFilter getContadorId() {
        return contadorId;
    }

    public Optional<LongFilter> optionalContadorId() {
        return Optional.ofNullable(contadorId);
    }

    public LongFilter contadorId() {
        if (contadorId == null) {
            setContadorId(new LongFilter());
        }
        return contadorId;
    }

    public void setContadorId(LongFilter contadorId) {
        this.contadorId = contadorId;
    }

    public LongFilter getSocioId() {
        return socioId;
    }

    public Optional<LongFilter> optionalSocioId() {
        return Optional.ofNullable(socioId);
    }

    public LongFilter socioId() {
        if (socioId == null) {
            setSocioId(new LongFilter());
        }
        return socioId;
    }

    public void setSocioId(LongFilter socioId) {
        this.socioId = socioId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PessoaCriteria that = (PessoaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(cpf, that.cpf) &&
            Objects.equals(dataNascimento, that.dataNascimento) &&
            Objects.equals(tituloEleitor, that.tituloEleitor) &&
            Objects.equals(rg, that.rg) &&
            Objects.equals(rgOrgaoExpeditor, that.rgOrgaoExpeditor) &&
            Objects.equals(rgUfExpedicao, that.rgUfExpedicao) &&
            Objects.equals(nomeMae, that.nomeMae) &&
            Objects.equals(nomePai, that.nomePai) &&
            Objects.equals(localNascimento, that.localNascimento) &&
            Objects.equals(racaECor, that.racaECor) &&
            Objects.equals(pessoaComDeficiencia, that.pessoaComDeficiencia) &&
            Objects.equals(estadoCivil, that.estadoCivil) &&
            Objects.equals(sexo, that.sexo) &&
            Objects.equals(urlFotoPerfil, that.urlFotoPerfil) &&
            Objects.equals(funcionarioId, that.funcionarioId) &&
            Objects.equals(anexoPessoaId, that.anexoPessoaId) &&
            Objects.equals(escolaridadePessoaId, that.escolaridadePessoaId) &&
            Objects.equals(bancoPessoaId, that.bancoPessoaId) &&
            Objects.equals(dependentesFuncionarioId, that.dependentesFuncionarioId) &&
            Objects.equals(enderecoPessoaId, that.enderecoPessoaId) &&
            Objects.equals(emailId, that.emailId) &&
            Objects.equals(telefoneId, that.telefoneId) &&
            Objects.equals(administradorId, that.administradorId) &&
            Objects.equals(contadorId, that.contadorId) &&
            Objects.equals(socioId, that.socioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            cpf,
            dataNascimento,
            tituloEleitor,
            rg,
            rgOrgaoExpeditor,
            rgUfExpedicao,
            nomeMae,
            nomePai,
            localNascimento,
            racaECor,
            pessoaComDeficiencia,
            estadoCivil,
            sexo,
            urlFotoPerfil,
            funcionarioId,
            anexoPessoaId,
            escolaridadePessoaId,
            bancoPessoaId,
            dependentesFuncionarioId,
            enderecoPessoaId,
            emailId,
            telefoneId,
            administradorId,
            contadorId,
            socioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalCpf().map(f -> "cpf=" + f + ", ").orElse("") +
            optionalDataNascimento().map(f -> "dataNascimento=" + f + ", ").orElse("") +
            optionalTituloEleitor().map(f -> "tituloEleitor=" + f + ", ").orElse("") +
            optionalRg().map(f -> "rg=" + f + ", ").orElse("") +
            optionalRgOrgaoExpeditor().map(f -> "rgOrgaoExpeditor=" + f + ", ").orElse("") +
            optionalRgUfExpedicao().map(f -> "rgUfExpedicao=" + f + ", ").orElse("") +
            optionalNomeMae().map(f -> "nomeMae=" + f + ", ").orElse("") +
            optionalNomePai().map(f -> "nomePai=" + f + ", ").orElse("") +
            optionalLocalNascimento().map(f -> "localNascimento=" + f + ", ").orElse("") +
            optionalRacaECor().map(f -> "racaECor=" + f + ", ").orElse("") +
            optionalPessoaComDeficiencia().map(f -> "pessoaComDeficiencia=" + f + ", ").orElse("") +
            optionalEstadoCivil().map(f -> "estadoCivil=" + f + ", ").orElse("") +
            optionalSexo().map(f -> "sexo=" + f + ", ").orElse("") +
            optionalUrlFotoPerfil().map(f -> "urlFotoPerfil=" + f + ", ").orElse("") +
            optionalFuncionarioId().map(f -> "funcionarioId=" + f + ", ").orElse("") +
            optionalAnexoPessoaId().map(f -> "anexoPessoaId=" + f + ", ").orElse("") +
            optionalEscolaridadePessoaId().map(f -> "escolaridadePessoaId=" + f + ", ").orElse("") +
            optionalBancoPessoaId().map(f -> "bancoPessoaId=" + f + ", ").orElse("") +
            optionalDependentesFuncionarioId().map(f -> "dependentesFuncionarioId=" + f + ", ").orElse("") +
            optionalEnderecoPessoaId().map(f -> "enderecoPessoaId=" + f + ", ").orElse("") +
            optionalEmailId().map(f -> "emailId=" + f + ", ").orElse("") +
            optionalTelefoneId().map(f -> "telefoneId=" + f + ", ").orElse("") +
            optionalAdministradorId().map(f -> "administradorId=" + f + ", ").orElse("") +
            optionalContadorId().map(f -> "contadorId=" + f + ", ").orElse("") +
            optionalSocioId().map(f -> "socioId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
