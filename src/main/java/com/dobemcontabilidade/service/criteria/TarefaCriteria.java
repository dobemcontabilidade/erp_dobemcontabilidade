package com.dobemcontabilidade.service.criteria;

import com.dobemcontabilidade.domain.enumeration.TipoTarefaEnum;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Tarefa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.TarefaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tarefas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TipoTarefaEnum
     */
    public static class TipoTarefaEnumFilter extends Filter<TipoTarefaEnum> {

        public TipoTarefaEnumFilter() {}

        public TipoTarefaEnumFilter(TipoTarefaEnumFilter filter) {
            super(filter);
        }

        @Override
        public TipoTarefaEnumFilter copy() {
            return new TipoTarefaEnumFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titulo;

    private IntegerFilter numeroDias;

    private BooleanFilter diaUtil;

    private DoubleFilter valor;

    private BooleanFilter notificarCliente;

    private BooleanFilter geraMulta;

    private BooleanFilter exibirEmpresa;

    private InstantFilter dataLegal;

    private IntegerFilter pontos;

    private TipoTarefaEnumFilter tipoTarefa;

    private LongFilter tarefaEmpresaId;

    private LongFilter subtarefaId;

    private LongFilter documentoTarefaId;

    private LongFilter esferaId;

    private LongFilter frequenciaId;

    private LongFilter competenciaId;

    private Boolean distinct;

    public TarefaCriteria() {}

    public TarefaCriteria(TarefaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.titulo = other.optionalTitulo().map(StringFilter::copy).orElse(null);
        this.numeroDias = other.optionalNumeroDias().map(IntegerFilter::copy).orElse(null);
        this.diaUtil = other.optionalDiaUtil().map(BooleanFilter::copy).orElse(null);
        this.valor = other.optionalValor().map(DoubleFilter::copy).orElse(null);
        this.notificarCliente = other.optionalNotificarCliente().map(BooleanFilter::copy).orElse(null);
        this.geraMulta = other.optionalGeraMulta().map(BooleanFilter::copy).orElse(null);
        this.exibirEmpresa = other.optionalExibirEmpresa().map(BooleanFilter::copy).orElse(null);
        this.dataLegal = other.optionalDataLegal().map(InstantFilter::copy).orElse(null);
        this.pontos = other.optionalPontos().map(IntegerFilter::copy).orElse(null);
        this.tipoTarefa = other.optionalTipoTarefa().map(TipoTarefaEnumFilter::copy).orElse(null);
        this.tarefaEmpresaId = other.optionalTarefaEmpresaId().map(LongFilter::copy).orElse(null);
        this.subtarefaId = other.optionalSubtarefaId().map(LongFilter::copy).orElse(null);
        this.documentoTarefaId = other.optionalDocumentoTarefaId().map(LongFilter::copy).orElse(null);
        this.esferaId = other.optionalEsferaId().map(LongFilter::copy).orElse(null);
        this.frequenciaId = other.optionalFrequenciaId().map(LongFilter::copy).orElse(null);
        this.competenciaId = other.optionalCompetenciaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TarefaCriteria copy() {
        return new TarefaCriteria(this);
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

    public StringFilter getTitulo() {
        return titulo;
    }

    public Optional<StringFilter> optionalTitulo() {
        return Optional.ofNullable(titulo);
    }

    public StringFilter titulo() {
        if (titulo == null) {
            setTitulo(new StringFilter());
        }
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public IntegerFilter getNumeroDias() {
        return numeroDias;
    }

    public Optional<IntegerFilter> optionalNumeroDias() {
        return Optional.ofNullable(numeroDias);
    }

    public IntegerFilter numeroDias() {
        if (numeroDias == null) {
            setNumeroDias(new IntegerFilter());
        }
        return numeroDias;
    }

    public void setNumeroDias(IntegerFilter numeroDias) {
        this.numeroDias = numeroDias;
    }

    public BooleanFilter getDiaUtil() {
        return diaUtil;
    }

    public Optional<BooleanFilter> optionalDiaUtil() {
        return Optional.ofNullable(diaUtil);
    }

    public BooleanFilter diaUtil() {
        if (diaUtil == null) {
            setDiaUtil(new BooleanFilter());
        }
        return diaUtil;
    }

    public void setDiaUtil(BooleanFilter diaUtil) {
        this.diaUtil = diaUtil;
    }

    public DoubleFilter getValor() {
        return valor;
    }

    public Optional<DoubleFilter> optionalValor() {
        return Optional.ofNullable(valor);
    }

    public DoubleFilter valor() {
        if (valor == null) {
            setValor(new DoubleFilter());
        }
        return valor;
    }

    public void setValor(DoubleFilter valor) {
        this.valor = valor;
    }

    public BooleanFilter getNotificarCliente() {
        return notificarCliente;
    }

    public Optional<BooleanFilter> optionalNotificarCliente() {
        return Optional.ofNullable(notificarCliente);
    }

    public BooleanFilter notificarCliente() {
        if (notificarCliente == null) {
            setNotificarCliente(new BooleanFilter());
        }
        return notificarCliente;
    }

    public void setNotificarCliente(BooleanFilter notificarCliente) {
        this.notificarCliente = notificarCliente;
    }

    public BooleanFilter getGeraMulta() {
        return geraMulta;
    }

    public Optional<BooleanFilter> optionalGeraMulta() {
        return Optional.ofNullable(geraMulta);
    }

    public BooleanFilter geraMulta() {
        if (geraMulta == null) {
            setGeraMulta(new BooleanFilter());
        }
        return geraMulta;
    }

    public void setGeraMulta(BooleanFilter geraMulta) {
        this.geraMulta = geraMulta;
    }

    public BooleanFilter getExibirEmpresa() {
        return exibirEmpresa;
    }

    public Optional<BooleanFilter> optionalExibirEmpresa() {
        return Optional.ofNullable(exibirEmpresa);
    }

    public BooleanFilter exibirEmpresa() {
        if (exibirEmpresa == null) {
            setExibirEmpresa(new BooleanFilter());
        }
        return exibirEmpresa;
    }

    public void setExibirEmpresa(BooleanFilter exibirEmpresa) {
        this.exibirEmpresa = exibirEmpresa;
    }

    public InstantFilter getDataLegal() {
        return dataLegal;
    }

    public Optional<InstantFilter> optionalDataLegal() {
        return Optional.ofNullable(dataLegal);
    }

    public InstantFilter dataLegal() {
        if (dataLegal == null) {
            setDataLegal(new InstantFilter());
        }
        return dataLegal;
    }

    public void setDataLegal(InstantFilter dataLegal) {
        this.dataLegal = dataLegal;
    }

    public IntegerFilter getPontos() {
        return pontos;
    }

    public Optional<IntegerFilter> optionalPontos() {
        return Optional.ofNullable(pontos);
    }

    public IntegerFilter pontos() {
        if (pontos == null) {
            setPontos(new IntegerFilter());
        }
        return pontos;
    }

    public void setPontos(IntegerFilter pontos) {
        this.pontos = pontos;
    }

    public TipoTarefaEnumFilter getTipoTarefa() {
        return tipoTarefa;
    }

    public Optional<TipoTarefaEnumFilter> optionalTipoTarefa() {
        return Optional.ofNullable(tipoTarefa);
    }

    public TipoTarefaEnumFilter tipoTarefa() {
        if (tipoTarefa == null) {
            setTipoTarefa(new TipoTarefaEnumFilter());
        }
        return tipoTarefa;
    }

    public void setTipoTarefa(TipoTarefaEnumFilter tipoTarefa) {
        this.tipoTarefa = tipoTarefa;
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

    public LongFilter getSubtarefaId() {
        return subtarefaId;
    }

    public Optional<LongFilter> optionalSubtarefaId() {
        return Optional.ofNullable(subtarefaId);
    }

    public LongFilter subtarefaId() {
        if (subtarefaId == null) {
            setSubtarefaId(new LongFilter());
        }
        return subtarefaId;
    }

    public void setSubtarefaId(LongFilter subtarefaId) {
        this.subtarefaId = subtarefaId;
    }

    public LongFilter getDocumentoTarefaId() {
        return documentoTarefaId;
    }

    public Optional<LongFilter> optionalDocumentoTarefaId() {
        return Optional.ofNullable(documentoTarefaId);
    }

    public LongFilter documentoTarefaId() {
        if (documentoTarefaId == null) {
            setDocumentoTarefaId(new LongFilter());
        }
        return documentoTarefaId;
    }

    public void setDocumentoTarefaId(LongFilter documentoTarefaId) {
        this.documentoTarefaId = documentoTarefaId;
    }

    public LongFilter getEsferaId() {
        return esferaId;
    }

    public Optional<LongFilter> optionalEsferaId() {
        return Optional.ofNullable(esferaId);
    }

    public LongFilter esferaId() {
        if (esferaId == null) {
            setEsferaId(new LongFilter());
        }
        return esferaId;
    }

    public void setEsferaId(LongFilter esferaId) {
        this.esferaId = esferaId;
    }

    public LongFilter getFrequenciaId() {
        return frequenciaId;
    }

    public Optional<LongFilter> optionalFrequenciaId() {
        return Optional.ofNullable(frequenciaId);
    }

    public LongFilter frequenciaId() {
        if (frequenciaId == null) {
            setFrequenciaId(new LongFilter());
        }
        return frequenciaId;
    }

    public void setFrequenciaId(LongFilter frequenciaId) {
        this.frequenciaId = frequenciaId;
    }

    public LongFilter getCompetenciaId() {
        return competenciaId;
    }

    public Optional<LongFilter> optionalCompetenciaId() {
        return Optional.ofNullable(competenciaId);
    }

    public LongFilter competenciaId() {
        if (competenciaId == null) {
            setCompetenciaId(new LongFilter());
        }
        return competenciaId;
    }

    public void setCompetenciaId(LongFilter competenciaId) {
        this.competenciaId = competenciaId;
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
        final TarefaCriteria that = (TarefaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(numeroDias, that.numeroDias) &&
            Objects.equals(diaUtil, that.diaUtil) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(notificarCliente, that.notificarCliente) &&
            Objects.equals(geraMulta, that.geraMulta) &&
            Objects.equals(exibirEmpresa, that.exibirEmpresa) &&
            Objects.equals(dataLegal, that.dataLegal) &&
            Objects.equals(pontos, that.pontos) &&
            Objects.equals(tipoTarefa, that.tipoTarefa) &&
            Objects.equals(tarefaEmpresaId, that.tarefaEmpresaId) &&
            Objects.equals(subtarefaId, that.subtarefaId) &&
            Objects.equals(documentoTarefaId, that.documentoTarefaId) &&
            Objects.equals(esferaId, that.esferaId) &&
            Objects.equals(frequenciaId, that.frequenciaId) &&
            Objects.equals(competenciaId, that.competenciaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            titulo,
            numeroDias,
            diaUtil,
            valor,
            notificarCliente,
            geraMulta,
            exibirEmpresa,
            dataLegal,
            pontos,
            tipoTarefa,
            tarefaEmpresaId,
            subtarefaId,
            documentoTarefaId,
            esferaId,
            frequenciaId,
            competenciaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalTitulo().map(f -> "titulo=" + f + ", ").orElse("") +
            optionalNumeroDias().map(f -> "numeroDias=" + f + ", ").orElse("") +
            optionalDiaUtil().map(f -> "diaUtil=" + f + ", ").orElse("") +
            optionalValor().map(f -> "valor=" + f + ", ").orElse("") +
            optionalNotificarCliente().map(f -> "notificarCliente=" + f + ", ").orElse("") +
            optionalGeraMulta().map(f -> "geraMulta=" + f + ", ").orElse("") +
            optionalExibirEmpresa().map(f -> "exibirEmpresa=" + f + ", ").orElse("") +
            optionalDataLegal().map(f -> "dataLegal=" + f + ", ").orElse("") +
            optionalPontos().map(f -> "pontos=" + f + ", ").orElse("") +
            optionalTipoTarefa().map(f -> "tipoTarefa=" + f + ", ").orElse("") +
            optionalTarefaEmpresaId().map(f -> "tarefaEmpresaId=" + f + ", ").orElse("") +
            optionalSubtarefaId().map(f -> "subtarefaId=" + f + ", ").orElse("") +
            optionalDocumentoTarefaId().map(f -> "documentoTarefaId=" + f + ", ").orElse("") +
            optionalEsferaId().map(f -> "esferaId=" + f + ", ").orElse("") +
            optionalFrequenciaId().map(f -> "frequenciaId=" + f + ", ").orElse("") +
            optionalCompetenciaId().map(f -> "competenciaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
