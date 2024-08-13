package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Subtarefa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.SubtarefaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /subtarefas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubtarefaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter ordem;

    private StringFilter item;

    private LongFilter tarefaId;

    private Boolean distinct;

    public SubtarefaCriteria() {}

    public SubtarefaCriteria(SubtarefaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.ordem = other.optionalOrdem().map(IntegerFilter::copy).orElse(null);
        this.item = other.optionalItem().map(StringFilter::copy).orElse(null);
        this.tarefaId = other.optionalTarefaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SubtarefaCriteria copy() {
        return new SubtarefaCriteria(this);
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

    public IntegerFilter getOrdem() {
        return ordem;
    }

    public Optional<IntegerFilter> optionalOrdem() {
        return Optional.ofNullable(ordem);
    }

    public IntegerFilter ordem() {
        if (ordem == null) {
            setOrdem(new IntegerFilter());
        }
        return ordem;
    }

    public void setOrdem(IntegerFilter ordem) {
        this.ordem = ordem;
    }

    public StringFilter getItem() {
        return item;
    }

    public Optional<StringFilter> optionalItem() {
        return Optional.ofNullable(item);
    }

    public StringFilter item() {
        if (item == null) {
            setItem(new StringFilter());
        }
        return item;
    }

    public void setItem(StringFilter item) {
        this.item = item;
    }

    public LongFilter getTarefaId() {
        return tarefaId;
    }

    public Optional<LongFilter> optionalTarefaId() {
        return Optional.ofNullable(tarefaId);
    }

    public LongFilter tarefaId() {
        if (tarefaId == null) {
            setTarefaId(new LongFilter());
        }
        return tarefaId;
    }

    public void setTarefaId(LongFilter tarefaId) {
        this.tarefaId = tarefaId;
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
        final SubtarefaCriteria that = (SubtarefaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(ordem, that.ordem) &&
            Objects.equals(item, that.item) &&
            Objects.equals(tarefaId, that.tarefaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ordem, item, tarefaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubtarefaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalOrdem().map(f -> "ordem=" + f + ", ").orElse("") +
            optionalItem().map(f -> "item=" + f + ", ").orElse("") +
            optionalTarefaId().map(f -> "tarefaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
