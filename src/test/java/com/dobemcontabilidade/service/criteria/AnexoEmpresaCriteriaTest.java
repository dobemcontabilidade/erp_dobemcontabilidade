package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AnexoEmpresaCriteriaTest {

    @Test
    void newAnexoEmpresaCriteriaHasAllFiltersNullTest() {
        var anexoEmpresaCriteria = new AnexoEmpresaCriteria();
        assertThat(anexoEmpresaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void anexoEmpresaCriteriaFluentMethodsCreatesFiltersTest() {
        var anexoEmpresaCriteria = new AnexoEmpresaCriteria();

        setAllFilters(anexoEmpresaCriteria);

        assertThat(anexoEmpresaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void anexoEmpresaCriteriaCopyCreatesNullFilterTest() {
        var anexoEmpresaCriteria = new AnexoEmpresaCriteria();
        var copy = anexoEmpresaCriteria.copy();

        assertThat(anexoEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(anexoEmpresaCriteria)
        );
    }

    @Test
    void anexoEmpresaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var anexoEmpresaCriteria = new AnexoEmpresaCriteria();
        setAllFilters(anexoEmpresaCriteria);

        var copy = anexoEmpresaCriteria.copy();

        assertThat(anexoEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(anexoEmpresaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var anexoEmpresaCriteria = new AnexoEmpresaCriteria();

        assertThat(anexoEmpresaCriteria).hasToString("AnexoEmpresaCriteria{}");
    }

    private static void setAllFilters(AnexoEmpresaCriteria anexoEmpresaCriteria) {
        anexoEmpresaCriteria.id();
        anexoEmpresaCriteria.tipo();
        anexoEmpresaCriteria.empresaId();
        anexoEmpresaCriteria.distinct();
    }

    private static Condition<AnexoEmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTipo()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AnexoEmpresaCriteria> copyFiltersAre(
        AnexoEmpresaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTipo(), copy.getTipo()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
