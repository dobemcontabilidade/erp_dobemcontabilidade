package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TermoAdesaoContadorCriteriaTest {

    @Test
    void newTermoAdesaoContadorCriteriaHasAllFiltersNullTest() {
        var termoAdesaoContadorCriteria = new TermoAdesaoContadorCriteria();
        assertThat(termoAdesaoContadorCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void termoAdesaoContadorCriteriaFluentMethodsCreatesFiltersTest() {
        var termoAdesaoContadorCriteria = new TermoAdesaoContadorCriteria();

        setAllFilters(termoAdesaoContadorCriteria);

        assertThat(termoAdesaoContadorCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void termoAdesaoContadorCriteriaCopyCreatesNullFilterTest() {
        var termoAdesaoContadorCriteria = new TermoAdesaoContadorCriteria();
        var copy = termoAdesaoContadorCriteria.copy();

        assertThat(termoAdesaoContadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(termoAdesaoContadorCriteria)
        );
    }

    @Test
    void termoAdesaoContadorCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var termoAdesaoContadorCriteria = new TermoAdesaoContadorCriteria();
        setAllFilters(termoAdesaoContadorCriteria);

        var copy = termoAdesaoContadorCriteria.copy();

        assertThat(termoAdesaoContadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(termoAdesaoContadorCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var termoAdesaoContadorCriteria = new TermoAdesaoContadorCriteria();

        assertThat(termoAdesaoContadorCriteria).hasToString("TermoAdesaoContadorCriteria{}");
    }

    private static void setAllFilters(TermoAdesaoContadorCriteria termoAdesaoContadorCriteria) {
        termoAdesaoContadorCriteria.id();
        termoAdesaoContadorCriteria.dataAdesao();
        termoAdesaoContadorCriteria.checked();
        termoAdesaoContadorCriteria.urlDoc();
        termoAdesaoContadorCriteria.contadorId();
        termoAdesaoContadorCriteria.termoDeAdesaoId();
        termoAdesaoContadorCriteria.distinct();
    }

    private static Condition<TermoAdesaoContadorCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDataAdesao()) &&
                condition.apply(criteria.getChecked()) &&
                condition.apply(criteria.getUrlDoc()) &&
                condition.apply(criteria.getContadorId()) &&
                condition.apply(criteria.getTermoDeAdesaoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TermoAdesaoContadorCriteria> copyFiltersAre(
        TermoAdesaoContadorCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDataAdesao(), copy.getDataAdesao()) &&
                condition.apply(criteria.getChecked(), copy.getChecked()) &&
                condition.apply(criteria.getUrlDoc(), copy.getUrlDoc()) &&
                condition.apply(criteria.getContadorId(), copy.getContadorId()) &&
                condition.apply(criteria.getTermoDeAdesaoId(), copy.getTermoDeAdesaoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
