package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.TipoAnexoRequeridoEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.AnexoRequerido} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.AnexoRequeridoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /anexo-requeridos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoRequeridoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoAnexoRequeridoEnum
     */
    public static class TipoAnexoRequeridoEnumFilter extends Filter<TipoAnexoRequeridoEnum> {

        public TipoAnexoRequeridoEnumFilter() {}

        public TipoAnexoRequeridoEnumFilter(TipoAnexoRequeridoEnumFilter filter) {
            super(filter);
        }

        @Override
        public TipoAnexoRequeridoEnumFilter copy() {
            return new TipoAnexoRequeridoEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private TipoAnexoRequeridoEnumFilter tipo;

    private LongFilter anexoRequeridoPessoaId;

    private LongFilter anexoRequeridoEmpresaId;

    private LongFilter anexoRequeridoServicoContabilId;

    private LongFilter anexoServicoContabilEmpresaId;

    private LongFilter anexoRequeridoTarefaOrdemServicoId;

    private LongFilter anexoRequeridoTarefaRecorrenteId;

    private Boolean distinct;

    public AnexoRequeridoCriteria() {}

    public AnexoRequeridoCriteria(AnexoRequeridoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.nome = other.optionalNome().map(StringFilter::copy).orElse(null);
        this.tipo = other.optionalTipo().map(TipoAnexoRequeridoEnumFilter::copy).orElse(null);
        this.anexoRequeridoPessoaId = other.optionalAnexoRequeridoPessoaId().map(LongFilter::copy).orElse(null);
        this.anexoRequeridoEmpresaId = other.optionalAnexoRequeridoEmpresaId().map(LongFilter::copy).orElse(null);
        this.anexoRequeridoServicoContabilId = other.optionalAnexoRequeridoServicoContabilId().map(LongFilter::copy).orElse(null);
        this.anexoServicoContabilEmpresaId = other.optionalAnexoServicoContabilEmpresaId().map(LongFilter::copy).orElse(null);
        this.anexoRequeridoTarefaOrdemServicoId = other.optionalAnexoRequeridoTarefaOrdemServicoId().map(LongFilter::copy).orElse(null);
        this.anexoRequeridoTarefaRecorrenteId = other.optionalAnexoRequeridoTarefaRecorrenteId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AnexoRequeridoCriteria copy() {
        return new AnexoRequeridoCriteria(this);
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

    public TipoAnexoRequeridoEnumFilter getTipo() {
        return tipo;
    }

    public Optional<TipoAnexoRequeridoEnumFilter> optionalTipo() {
        return Optional.ofNullable(tipo);
    }

    public TipoAnexoRequeridoEnumFilter tipo() {
        if (tipo == null) {
            setTipo(new TipoAnexoRequeridoEnumFilter());
        }
        return tipo;
    }

    public void setTipo(TipoAnexoRequeridoEnumFilter tipo) {
        this.tipo = tipo;
    }

    public LongFilter getAnexoRequeridoPessoaId() {
        return anexoRequeridoPessoaId;
    }

    public Optional<LongFilter> optionalAnexoRequeridoPessoaId() {
        return Optional.ofNullable(anexoRequeridoPessoaId);
    }

    public LongFilter anexoRequeridoPessoaId() {
        if (anexoRequeridoPessoaId == null) {
            setAnexoRequeridoPessoaId(new LongFilter());
        }
        return anexoRequeridoPessoaId;
    }

    public void setAnexoRequeridoPessoaId(LongFilter anexoRequeridoPessoaId) {
        this.anexoRequeridoPessoaId = anexoRequeridoPessoaId;
    }

    public LongFilter getAnexoRequeridoEmpresaId() {
        return anexoRequeridoEmpresaId;
    }

    public Optional<LongFilter> optionalAnexoRequeridoEmpresaId() {
        return Optional.ofNullable(anexoRequeridoEmpresaId);
    }

    public LongFilter anexoRequeridoEmpresaId() {
        if (anexoRequeridoEmpresaId == null) {
            setAnexoRequeridoEmpresaId(new LongFilter());
        }
        return anexoRequeridoEmpresaId;
    }

    public void setAnexoRequeridoEmpresaId(LongFilter anexoRequeridoEmpresaId) {
        this.anexoRequeridoEmpresaId = anexoRequeridoEmpresaId;
    }

    public LongFilter getAnexoRequeridoServicoContabilId() {
        return anexoRequeridoServicoContabilId;
    }

    public Optional<LongFilter> optionalAnexoRequeridoServicoContabilId() {
        return Optional.ofNullable(anexoRequeridoServicoContabilId);
    }

    public LongFilter anexoRequeridoServicoContabilId() {
        if (anexoRequeridoServicoContabilId == null) {
            setAnexoRequeridoServicoContabilId(new LongFilter());
        }
        return anexoRequeridoServicoContabilId;
    }

    public void setAnexoRequeridoServicoContabilId(LongFilter anexoRequeridoServicoContabilId) {
        this.anexoRequeridoServicoContabilId = anexoRequeridoServicoContabilId;
    }

    public LongFilter getAnexoServicoContabilEmpresaId() {
        return anexoServicoContabilEmpresaId;
    }

    public Optional<LongFilter> optionalAnexoServicoContabilEmpresaId() {
        return Optional.ofNullable(anexoServicoContabilEmpresaId);
    }

    public LongFilter anexoServicoContabilEmpresaId() {
        if (anexoServicoContabilEmpresaId == null) {
            setAnexoServicoContabilEmpresaId(new LongFilter());
        }
        return anexoServicoContabilEmpresaId;
    }

    public void setAnexoServicoContabilEmpresaId(LongFilter anexoServicoContabilEmpresaId) {
        this.anexoServicoContabilEmpresaId = anexoServicoContabilEmpresaId;
    }

    public LongFilter getAnexoRequeridoTarefaOrdemServicoId() {
        return anexoRequeridoTarefaOrdemServicoId;
    }

    public Optional<LongFilter> optionalAnexoRequeridoTarefaOrdemServicoId() {
        return Optional.ofNullable(anexoRequeridoTarefaOrdemServicoId);
    }

    public LongFilter anexoRequeridoTarefaOrdemServicoId() {
        if (anexoRequeridoTarefaOrdemServicoId == null) {
            setAnexoRequeridoTarefaOrdemServicoId(new LongFilter());
        }
        return anexoRequeridoTarefaOrdemServicoId;
    }

    public void setAnexoRequeridoTarefaOrdemServicoId(LongFilter anexoRequeridoTarefaOrdemServicoId) {
        this.anexoRequeridoTarefaOrdemServicoId = anexoRequeridoTarefaOrdemServicoId;
    }

    public LongFilter getAnexoRequeridoTarefaRecorrenteId() {
        return anexoRequeridoTarefaRecorrenteId;
    }

    public Optional<LongFilter> optionalAnexoRequeridoTarefaRecorrenteId() {
        return Optional.ofNullable(anexoRequeridoTarefaRecorrenteId);
    }

    public LongFilter anexoRequeridoTarefaRecorrenteId() {
        if (anexoRequeridoTarefaRecorrenteId == null) {
            setAnexoRequeridoTarefaRecorrenteId(new LongFilter());
        }
        return anexoRequeridoTarefaRecorrenteId;
    }

    public void setAnexoRequeridoTarefaRecorrenteId(LongFilter anexoRequeridoTarefaRecorrenteId) {
        this.anexoRequeridoTarefaRecorrenteId = anexoRequeridoTarefaRecorrenteId;
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
        final AnexoRequeridoCriteria that = (AnexoRequeridoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(tipo, that.tipo) &&
            Objects.equals(anexoRequeridoPessoaId, that.anexoRequeridoPessoaId) &&
            Objects.equals(anexoRequeridoEmpresaId, that.anexoRequeridoEmpresaId) &&
            Objects.equals(anexoRequeridoServicoContabilId, that.anexoRequeridoServicoContabilId) &&
            Objects.equals(anexoServicoContabilEmpresaId, that.anexoServicoContabilEmpresaId) &&
            Objects.equals(anexoRequeridoTarefaOrdemServicoId, that.anexoRequeridoTarefaOrdemServicoId) &&
            Objects.equals(anexoRequeridoTarefaRecorrenteId, that.anexoRequeridoTarefaRecorrenteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nome,
            tipo,
            anexoRequeridoPessoaId,
            anexoRequeridoEmpresaId,
            anexoRequeridoServicoContabilId,
            anexoServicoContabilEmpresaId,
            anexoRequeridoTarefaOrdemServicoId,
            anexoRequeridoTarefaRecorrenteId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoRequeridoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalNome().map(f -> "nome=" + f + ", ").orElse("") +
            optionalTipo().map(f -> "tipo=" + f + ", ").orElse("") +
            optionalAnexoRequeridoPessoaId().map(f -> "anexoRequeridoPessoaId=" + f + ", ").orElse("") +
            optionalAnexoRequeridoEmpresaId().map(f -> "anexoRequeridoEmpresaId=" + f + ", ").orElse("") +
            optionalAnexoRequeridoServicoContabilId().map(f -> "anexoRequeridoServicoContabilId=" + f + ", ").orElse("") +
            optionalAnexoServicoContabilEmpresaId().map(f -> "anexoServicoContabilEmpresaId=" + f + ", ").orElse("") +
            optionalAnexoRequeridoTarefaOrdemServicoId().map(f -> "anexoRequeridoTarefaOrdemServicoId=" + f + ", ").orElse("") +
            optionalAnexoRequeridoTarefaRecorrenteId().map(f -> "anexoRequeridoTarefaRecorrenteId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
