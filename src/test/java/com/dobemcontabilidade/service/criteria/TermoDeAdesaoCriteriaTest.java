package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TermoDeAdesaoCriteriaTest {

    @Test
    void newTermoDeAdesaoCriteriaHasAllFiltersNullTest() {
        var termoDeAdesaoCriteria = new TermoDeAdesaoCriteria();
        assertThat(termoDeAdesaoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void termoDeAdesaoCriteriaFluentMethodsCreatesFiltersTest() {
        var termoDeAdesaoCriteria = new TermoDeAdesaoCriteria();

        setAllFilters(termoDeAdesaoCriteria);

        assertThat(termoDeAdesaoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void termoDeAdesaoCriteriaCopyCreatesNullFilterTest() {
        var termoDeAdesaoCriteria = new TermoDeAdesaoCriteria();
        var copy = termoDeAdesaoCriteria.copy();

        assertThat(termoDeAdesaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(termoDeAdesaoCriteria)
        );
    }

    @Test
    void termoDeAdesaoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var termoDeAdesaoCriteria = new TermoDeAdesaoCriteria();
        setAllFilters(termoDeAdesaoCriteria);

        var copy = termoDeAdesaoCriteria.copy();

        assertThat(termoDeAdesaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(termoDeAdesaoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var termoDeAdesaoCriteria = new TermoDeAdesaoCriteria();

        assertThat(termoDeAdesaoCriteria).hasToString("TermoDeAdesaoCriteria{}");
    }

    private static void setAllFilters(TermoDeAdesaoCriteria termoDeAdesaoCriteria) {
        termoDeAdesaoCriteria.id();
        termoDeAdesaoCriteria.titulo();
        termoDeAdesaoCriteria.urlDoc();
        termoDeAdesaoCriteria.termoAdesaoContadorId();
        termoDeAdesaoCriteria.distinct();
    }

    private static Condition<TermoDeAdesaoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTitulo()) &&
                condition.apply(criteria.getUrlDoc()) &&
                condition.apply(criteria.getTermoAdesaoContadorId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TermoDeAdesaoCriteria> copyFiltersAre(
        TermoDeAdesaoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTitulo(), copy.getTitulo()) &&
                condition.apply(criteria.getUrlDoc(), copy.getUrlDoc()) &&
                condition.apply(criteria.getTermoAdesaoContadorId(), copy.getTermoAdesaoContadorId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
