package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ValorBaseRamoCriteriaTest {

    @Test
    void newValorBaseRamoCriteriaHasAllFiltersNullTest() {
        var valorBaseRamoCriteria = new ValorBaseRamoCriteria();
        assertThat(valorBaseRamoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void valorBaseRamoCriteriaFluentMethodsCreatesFiltersTest() {
        var valorBaseRamoCriteria = new ValorBaseRamoCriteria();

        setAllFilters(valorBaseRamoCriteria);

        assertThat(valorBaseRamoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void valorBaseRamoCriteriaCopyCreatesNullFilterTest() {
        var valorBaseRamoCriteria = new ValorBaseRamoCriteria();
        var copy = valorBaseRamoCriteria.copy();

        assertThat(valorBaseRamoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(valorBaseRamoCriteria)
        );
    }

    @Test
    void valorBaseRamoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var valorBaseRamoCriteria = new ValorBaseRamoCriteria();
        setAllFilters(valorBaseRamoCriteria);

        var copy = valorBaseRamoCriteria.copy();

        assertThat(valorBaseRamoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(valorBaseRamoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var valorBaseRamoCriteria = new ValorBaseRamoCriteria();

        assertThat(valorBaseRamoCriteria).hasToString("ValorBaseRamoCriteria{}");
    }

    private static void setAllFilters(ValorBaseRamoCriteria valorBaseRamoCriteria) {
        valorBaseRamoCriteria.id();
        valorBaseRamoCriteria.valorBase();
        valorBaseRamoCriteria.ramoId();
        valorBaseRamoCriteria.planoContabilId();
        valorBaseRamoCriteria.distinct();
    }

    private static Condition<ValorBaseRamoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getValorBase()) &&
                condition.apply(criteria.getRamoId()) &&
                condition.apply(criteria.getPlanoContabilId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ValorBaseRamoCriteria> copyFiltersAre(
        ValorBaseRamoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getValorBase(), copy.getValorBase()) &&
                condition.apply(criteria.getRamoId(), copy.getRamoId()) &&
                condition.apply(criteria.getPlanoContabilId(), copy.getPlanoContabilId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
