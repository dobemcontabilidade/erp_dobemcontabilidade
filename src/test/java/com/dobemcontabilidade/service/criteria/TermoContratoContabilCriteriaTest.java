package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TermoContratoContabilCriteriaTest {

    @Test
    void newTermoContratoContabilCriteriaHasAllFiltersNullTest() {
        var termoContratoContabilCriteria = new TermoContratoContabilCriteria();
        assertThat(termoContratoContabilCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void termoContratoContabilCriteriaFluentMethodsCreatesFiltersTest() {
        var termoContratoContabilCriteria = new TermoContratoContabilCriteria();

        setAllFilters(termoContratoContabilCriteria);

        assertThat(termoContratoContabilCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void termoContratoContabilCriteriaCopyCreatesNullFilterTest() {
        var termoContratoContabilCriteria = new TermoContratoContabilCriteria();
        var copy = termoContratoContabilCriteria.copy();

        assertThat(termoContratoContabilCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(termoContratoContabilCriteria)
        );
    }

    @Test
    void termoContratoContabilCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var termoContratoContabilCriteria = new TermoContratoContabilCriteria();
        setAllFilters(termoContratoContabilCriteria);

        var copy = termoContratoContabilCriteria.copy();

        assertThat(termoContratoContabilCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(termoContratoContabilCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var termoContratoContabilCriteria = new TermoContratoContabilCriteria();

        assertThat(termoContratoContabilCriteria).hasToString("TermoContratoContabilCriteria{}");
    }

    private static void setAllFilters(TermoContratoContabilCriteria termoContratoContabilCriteria) {
        termoContratoContabilCriteria.id();
        termoContratoContabilCriteria.documento();
        termoContratoContabilCriteria.titulo();
        termoContratoContabilCriteria.planoContabilId();
        termoContratoContabilCriteria.distinct();
    }

    private static Condition<TermoContratoContabilCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDocumento()) &&
                condition.apply(criteria.getTitulo()) &&
                condition.apply(criteria.getPlanoContabilId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TermoContratoContabilCriteria> copyFiltersAre(
        TermoContratoContabilCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDocumento(), copy.getDocumento()) &&
                condition.apply(criteria.getTitulo(), copy.getTitulo()) &&
                condition.apply(criteria.getPlanoContabilId(), copy.getPlanoContabilId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
