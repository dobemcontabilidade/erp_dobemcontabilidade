package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SistemaCriteriaTest {

    @Test
    void newSistemaCriteriaHasAllFiltersNullTest() {
        var sistemaCriteria = new SistemaCriteria();
        assertThat(sistemaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void sistemaCriteriaFluentMethodsCreatesFiltersTest() {
        var sistemaCriteria = new SistemaCriteria();

        setAllFilters(sistemaCriteria);

        assertThat(sistemaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void sistemaCriteriaCopyCreatesNullFilterTest() {
        var sistemaCriteria = new SistemaCriteria();
        var copy = sistemaCriteria.copy();

        assertThat(sistemaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(sistemaCriteria)
        );
    }

    @Test
    void sistemaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var sistemaCriteria = new SistemaCriteria();
        setAllFilters(sistemaCriteria);

        var copy = sistemaCriteria.copy();

        assertThat(sistemaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(sistemaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var sistemaCriteria = new SistemaCriteria();

        assertThat(sistemaCriteria).hasToString("SistemaCriteria{}");
    }

    private static void setAllFilters(SistemaCriteria sistemaCriteria) {
        sistemaCriteria.id();
        sistemaCriteria.nome();
        sistemaCriteria.descricao();
        sistemaCriteria.moduloId();
        sistemaCriteria.distinct();
    }

    private static Condition<SistemaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getDescricao()) &&
                condition.apply(criteria.getModuloId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SistemaCriteria> copyFiltersAre(SistemaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getDescricao(), copy.getDescricao()) &&
                condition.apply(criteria.getModuloId(), copy.getModuloId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
