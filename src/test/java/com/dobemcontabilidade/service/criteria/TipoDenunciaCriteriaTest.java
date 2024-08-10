package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TipoDenunciaCriteriaTest {

    @Test
    void newTipoDenunciaCriteriaHasAllFiltersNullTest() {
        var tipoDenunciaCriteria = new TipoDenunciaCriteria();
        assertThat(tipoDenunciaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void tipoDenunciaCriteriaFluentMethodsCreatesFiltersTest() {
        var tipoDenunciaCriteria = new TipoDenunciaCriteria();

        setAllFilters(tipoDenunciaCriteria);

        assertThat(tipoDenunciaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void tipoDenunciaCriteriaCopyCreatesNullFilterTest() {
        var tipoDenunciaCriteria = new TipoDenunciaCriteria();
        var copy = tipoDenunciaCriteria.copy();

        assertThat(tipoDenunciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(tipoDenunciaCriteria)
        );
    }

    @Test
    void tipoDenunciaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var tipoDenunciaCriteria = new TipoDenunciaCriteria();
        setAllFilters(tipoDenunciaCriteria);

        var copy = tipoDenunciaCriteria.copy();

        assertThat(tipoDenunciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(tipoDenunciaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var tipoDenunciaCriteria = new TipoDenunciaCriteria();

        assertThat(tipoDenunciaCriteria).hasToString("TipoDenunciaCriteria{}");
    }

    private static void setAllFilters(TipoDenunciaCriteria tipoDenunciaCriteria) {
        tipoDenunciaCriteria.id();
        tipoDenunciaCriteria.tipo();
        tipoDenunciaCriteria.distinct();
    }

    private static Condition<TipoDenunciaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria -> condition.apply(criteria.getId()) && condition.apply(criteria.getTipo()) && condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TipoDenunciaCriteria> copyFiltersAre(
        TipoDenunciaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTipo(), copy.getTipo()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
