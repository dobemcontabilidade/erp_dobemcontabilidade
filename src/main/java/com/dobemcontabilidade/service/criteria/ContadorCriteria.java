package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Contador} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.ContadorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contadors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContadorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter crc;

    private IntegerFilter limiteEmpresas;

    private IntegerFilter limiteAreaContabils;

    private DoubleFilter limiteFaturamento;

    private IntegerFilter limiteDepartamentos;

    private LongFilter pessoaId;

    private LongFilter areaContabilEmpresaId;

    private LongFilter areaContabilContadorId;

    private LongFilter departamentoEmpresaId;

    private LongFilter departamentoContadorId;

    private LongFilter termoAdesaoContadorId;

    private LongFilter bancoContadorId;

    private LongFilter avaliacaoContadorId;

    private LongFilter tarefaEmpresaId;

    private LongFilter perfilContadorId;

    private LongFilter usuarioContadorId;

    private Boolean distinct;

    public ContadorCriteria() {}

    public ContadorCriteria(ContadorCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.crc = other.optionalCrc().map(StringFilter::copy).orElse(null);
        this.limiteEmpresas = other.optionalLimiteEmpresas().map(IntegerFilter::copy).orElse(null);
        this.limiteAreaContabils = other.optionalLimiteAreaContabils().map(IntegerFilter::copy).orElse(null);
        this.limiteFaturamento = other.optionalLimiteFaturamento().map(DoubleFilter::copy).orElse(null);
        this.limiteDepartamentos = other.optionalLimiteDepartamentos().map(IntegerFilter::copy).orElse(null);
        this.pessoaId = other.optionalPessoaId().map(LongFilter::copy).orElse(null);
        this.areaContabilEmpresaId = other.optionalAreaContabilEmpresaId().map(LongFilter::copy).orElse(null);
        this.areaContabilContadorId = other.optionalAreaContabilContadorId().map(LongFilter::copy).orElse(null);
        this.departamentoEmpresaId = other.optionalDepartamentoEmpresaId().map(LongFilter::copy).orElse(null);
        this.departamentoContadorId = other.optionalDepartamentoContadorId().map(LongFilter::copy).orElse(null);
        this.termoAdesaoContadorId = other.optionalTermoAdesaoContadorId().map(LongFilter::copy).orElse(null);
        this.bancoContadorId = other.optionalBancoContadorId().map(LongFilter::copy).orElse(null);
        this.avaliacaoContadorId = other.optionalAvaliacaoContadorId().map(LongFilter::copy).orElse(null);
        this.tarefaEmpresaId = other.optionalTarefaEmpresaId().map(LongFilter::copy).orElse(null);
        this.perfilContadorId = other.optionalPerfilContadorId().map(LongFilter::copy).orElse(null);
        this.usuarioContadorId = other.optionalUsuarioContadorId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ContadorCriteria copy() {
        return new ContadorCriteria(this);
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

    public StringFilter getCrc() {
        return crc;
    }

    public Optional<StringFilter> optionalCrc() {
        return Optional.ofNullable(crc);
    }

    public StringFilter crc() {
        if (crc == null) {
            setCrc(new StringFilter());
        }
        return crc;
    }

    public void setCrc(StringFilter crc) {
        this.crc = crc;
    }

    public IntegerFilter getLimiteEmpresas() {
        return limiteEmpresas;
    }

    public Optional<IntegerFilter> optionalLimiteEmpresas() {
        return Optional.ofNullable(limiteEmpresas);
    }

    public IntegerFilter limiteEmpresas() {
        if (limiteEmpresas == null) {
            setLimiteEmpresas(new IntegerFilter());
        }
        return limiteEmpresas;
    }

    public void setLimiteEmpresas(IntegerFilter limiteEmpresas) {
        this.limiteEmpresas = limiteEmpresas;
    }

    public IntegerFilter getLimiteAreaContabils() {
        return limiteAreaContabils;
    }

    public Optional<IntegerFilter> optionalLimiteAreaContabils() {
        return Optional.ofNullable(limiteAreaContabils);
    }

    public IntegerFilter limiteAreaContabils() {
        if (limiteAreaContabils == null) {
            setLimiteAreaContabils(new IntegerFilter());
        }
        return limiteAreaContabils;
    }

    public void setLimiteAreaContabils(IntegerFilter limiteAreaContabils) {
        this.limiteAreaContabils = limiteAreaContabils;
    }

    public DoubleFilter getLimiteFaturamento() {
        return limiteFaturamento;
    }

    public Optional<DoubleFilter> optionalLimiteFaturamento() {
        return Optional.ofNullable(limiteFaturamento);
    }

    public DoubleFilter limiteFaturamento() {
        if (limiteFaturamento == null) {
            setLimiteFaturamento(new DoubleFilter());
        }
        return limiteFaturamento;
    }

    public void setLimiteFaturamento(DoubleFilter limiteFaturamento) {
        this.limiteFaturamento = limiteFaturamento;
    }

    public IntegerFilter getLimiteDepartamentos() {
        return limiteDepartamentos;
    }

    public Optional<IntegerFilter> optionalLimiteDepartamentos() {
        return Optional.ofNullable(limiteDepartamentos);
    }

    public IntegerFilter limiteDepartamentos() {
        if (limiteDepartamentos == null) {
            setLimiteDepartamentos(new IntegerFilter());
        }
        return limiteDepartamentos;
    }

    public void setLimiteDepartamentos(IntegerFilter limiteDepartamentos) {
        this.limiteDepartamentos = limiteDepartamentos;
    }

    public LongFilter getPessoaId() {
        return pessoaId;
    }

    public Optional<LongFilter> optionalPessoaId() {
        return Optional.ofNullable(pessoaId);
    }

    public LongFilter pessoaId() {
        if (pessoaId == null) {
            setPessoaId(new LongFilter());
        }
        return pessoaId;
    }

    public void setPessoaId(LongFilter pessoaId) {
        this.pessoaId = pessoaId;
    }

    public LongFilter getAreaContabilEmpresaId() {
        return areaContabilEmpresaId;
    }

    public Optional<LongFilter> optionalAreaContabilEmpresaId() {
        return Optional.ofNullable(areaContabilEmpresaId);
    }

    public LongFilter areaContabilEmpresaId() {
        if (areaContabilEmpresaId == null) {
            setAreaContabilEmpresaId(new LongFilter());
        }
        return areaContabilEmpresaId;
    }

    public void setAreaContabilEmpresaId(LongFilter areaContabilEmpresaId) {
        this.areaContabilEmpresaId = areaContabilEmpresaId;
    }

    public LongFilter getAreaContabilContadorId() {
        return areaContabilContadorId;
    }

    public Optional<LongFilter> optionalAreaContabilContadorId() {
        return Optional.ofNullable(areaContabilContadorId);
    }

    public LongFilter areaContabilContadorId() {
        if (areaContabilContadorId == null) {
            setAreaContabilContadorId(new LongFilter());
        }
        return areaContabilContadorId;
    }

    public void setAreaContabilContadorId(LongFilter areaContabilContadorId) {
        this.areaContabilContadorId = areaContabilContadorId;
    }

    public LongFilter getDepartamentoEmpresaId() {
        return departamentoEmpresaId;
    }

    public Optional<LongFilter> optionalDepartamentoEmpresaId() {
        return Optional.ofNullable(departamentoEmpresaId);
    }

    public LongFilter departamentoEmpresaId() {
        if (departamentoEmpresaId == null) {
            setDepartamentoEmpresaId(new LongFilter());
        }
        return departamentoEmpresaId;
    }

    public void setDepartamentoEmpresaId(LongFilter departamentoEmpresaId) {
        this.departamentoEmpresaId = departamentoEmpresaId;
    }

    public LongFilter getDepartamentoContadorId() {
        return departamentoContadorId;
    }

    public Optional<LongFilter> optionalDepartamentoContadorId() {
        return Optional.ofNullable(departamentoContadorId);
    }

    public LongFilter departamentoContadorId() {
        if (departamentoContadorId == null) {
            setDepartamentoContadorId(new LongFilter());
        }
        return departamentoContadorId;
    }

    public void setDepartamentoContadorId(LongFilter departamentoContadorId) {
        this.departamentoContadorId = departamentoContadorId;
    }

    public LongFilter getTermoAdesaoContadorId() {
        return termoAdesaoContadorId;
    }

    public Optional<LongFilter> optionalTermoAdesaoContadorId() {
        return Optional.ofNullable(termoAdesaoContadorId);
    }

    public LongFilter termoAdesaoContadorId() {
        if (termoAdesaoContadorId == null) {
            setTermoAdesaoContadorId(new LongFilter());
        }
        return termoAdesaoContadorId;
    }

    public void setTermoAdesaoContadorId(LongFilter termoAdesaoContadorId) {
        this.termoAdesaoContadorId = termoAdesaoContadorId;
    }

    public LongFilter getBancoContadorId() {
        return bancoContadorId;
    }

    public Optional<LongFilter> optionalBancoContadorId() {
        return Optional.ofNullable(bancoContadorId);
    }

    public LongFilter bancoContadorId() {
        if (bancoContadorId == null) {
            setBancoContadorId(new LongFilter());
        }
        return bancoContadorId;
    }

    public void setBancoContadorId(LongFilter bancoContadorId) {
        this.bancoContadorId = bancoContadorId;
    }

    public LongFilter getAvaliacaoContadorId() {
        return avaliacaoContadorId;
    }

    public Optional<LongFilter> optionalAvaliacaoContadorId() {
        return Optional.ofNullable(avaliacaoContadorId);
    }

    public LongFilter avaliacaoContadorId() {
        if (avaliacaoContadorId == null) {
            setAvaliacaoContadorId(new LongFilter());
        }
        return avaliacaoContadorId;
    }

    public void setAvaliacaoContadorId(LongFilter avaliacaoContadorId) {
        this.avaliacaoContadorId = avaliacaoContadorId;
    }

    public LongFilter getTarefaEmpresaId() {
        return tarefaEmpresaId;
    }

    public Optional<LongFilter> optionalTarefaEmpresaId() {
        return Optional.ofNullable(tarefaEmpresaId);
    }

    public LongFilter tarefaEmpresaId() {
        if (tarefaEmpresaId == null) {
            setTarefaEmpresaId(new LongFilter());
        }
        return tarefaEmpresaId;
    }

    public void setTarefaEmpresaId(LongFilter tarefaEmpresaId) {
        this.tarefaEmpresaId = tarefaEmpresaId;
    }

    public LongFilter getPerfilContadorId() {
        return perfilContadorId;
    }

    public Optional<LongFilter> optionalPerfilContadorId() {
        return Optional.ofNullable(perfilContadorId);
    }

    public LongFilter perfilContadorId() {
        if (perfilContadorId == null) {
            setPerfilContadorId(new LongFilter());
        }
        return perfilContadorId;
    }

    public void setPerfilContadorId(LongFilter perfilContadorId) {
        this.perfilContadorId = perfilContadorId;
    }

    public LongFilter getUsuarioContadorId() {
        return usuarioContadorId;
    }

    public Optional<LongFilter> optionalUsuarioContadorId() {
        return Optional.ofNullable(usuarioContadorId);
    }

    public LongFilter usuarioContadorId() {
        if (usuarioContadorId == null) {
            setUsuarioContadorId(new LongFilter());
        }
        return usuarioContadorId;
    }

    public void setUsuarioContadorId(LongFilter usuarioContadorId) {
        this.usuarioContadorId = usuarioContadorId;
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
        final ContadorCriteria that = (ContadorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(crc, that.crc) &&
            Objects.equals(limiteEmpresas, that.limiteEmpresas) &&
            Objects.equals(limiteAreaContabils, that.limiteAreaContabils) &&
            Objects.equals(limiteFaturamento, that.limiteFaturamento) &&
            Objects.equals(limiteDepartamentos, that.limiteDepartamentos) &&
            Objects.equals(pessoaId, that.pessoaId) &&
            Objects.equals(areaContabilEmpresaId, that.areaContabilEmpresaId) &&
            Objects.equals(areaContabilContadorId, that.areaContabilContadorId) &&
            Objects.equals(departamentoEmpresaId, that.departamentoEmpresaId) &&
            Objects.equals(departamentoContadorId, that.departamentoContadorId) &&
            Objects.equals(termoAdesaoContadorId, that.termoAdesaoContadorId) &&
            Objects.equals(bancoContadorId, that.bancoContadorId) &&
            Objects.equals(avaliacaoContadorId, that.avaliacaoContadorId) &&
            Objects.equals(tarefaEmpresaId, that.tarefaEmpresaId) &&
            Objects.equals(perfilContadorId, that.perfilContadorId) &&
            Objects.equals(usuarioContadorId, that.usuarioContadorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            crc,
            limiteEmpresas,
            limiteAreaContabils,
            limiteFaturamento,
            limiteDepartamentos,
            pessoaId,
            areaContabilEmpresaId,
            areaContabilContadorId,
            departamentoEmpresaId,
            departamentoContadorId,
            termoAdesaoContadorId,
            bancoContadorId,
            avaliacaoContadorId,
            tarefaEmpresaId,
            perfilContadorId,
            usuarioContadorId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContadorCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalCrc().map(f -> "crc=" + f + ", ").orElse("") +
            optionalLimiteEmpresas().map(f -> "limiteEmpresas=" + f + ", ").orElse("") +
            optionalLimiteAreaContabils().map(f -> "limiteAreaContabils=" + f + ", ").orElse("") +
            optionalLimiteFaturamento().map(f -> "limiteFaturamento=" + f + ", ").orElse("") +
            optionalLimiteDepartamentos().map(f -> "limiteDepartamentos=" + f + ", ").orElse("") +
            optionalPessoaId().map(f -> "pessoaId=" + f + ", ").orElse("") +
            optionalAreaContabilEmpresaId().map(f -> "areaContabilEmpresaId=" + f + ", ").orElse("") +
            optionalAreaContabilContadorId().map(f -> "areaContabilContadorId=" + f + ", ").orElse("") +
            optionalDepartamentoEmpresaId().map(f -> "departamentoEmpresaId=" + f + ", ").orElse("") +
            optionalDepartamentoContadorId().map(f -> "departamentoContadorId=" + f + ", ").orElse("") +
            optionalTermoAdesaoContadorId().map(f -> "termoAdesaoContadorId=" + f + ", ").orElse("") +
            optionalBancoContadorId().map(f -> "bancoContadorId=" + f + ", ").orElse("") +
            optionalAvaliacaoContadorId().map(f -> "avaliacaoContadorId=" + f + ", ").orElse("") +
            optionalTarefaEmpresaId().map(f -> "tarefaEmpresaId=" + f + ", ").orElse("") +
            optionalPerfilContadorId().map(f -> "perfilContadorId=" + f + ", ").orElse("") +
            optionalUsuarioContadorId().map(f -> "usuarioContadorId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
