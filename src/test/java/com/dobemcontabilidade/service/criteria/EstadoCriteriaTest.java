package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EstadoCriteriaTest {

    @Test
    void newEstadoCriteriaHasAllFiltersNullTest() {
        var estadoCriteria = new EstadoCriteria();
        assertThat(estadoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void estadoCriteriaFluentMethodsCreatesFiltersTest() {
        var estadoCriteria = new EstadoCriteria();

        setAllFilters(estadoCriteria);

        assertThat(estadoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void estadoCriteriaCopyCreatesNullFilterTest() {
        var estadoCriteria = new EstadoCriteria();
        var copy = estadoCriteria.copy();

        assertThat(estadoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(estadoCriteria)
        );
    }

    @Test
    void estadoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var estadoCriteria = new EstadoCriteria();
        setAllFilters(estadoCriteria);

        var copy = estadoCriteria.copy();

        assertThat(estadoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(estadoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var estadoCriteria = new EstadoCriteria();

        assertThat(estadoCriteria).hasToString("EstadoCriteria{}");
    }

    private static void setAllFilters(EstadoCriteria estadoCriteria) {
        estadoCriteria.id();
        estadoCriteria.nome();
        estadoCriteria.naturalidade();
        estadoCriteria.sigla();
        estadoCriteria.cidadeId();
        estadoCriteria.distinct();
    }

    private static Condition<EstadoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getNaturalidade()) &&
                condition.apply(criteria.getSigla()) &&
                condition.apply(criteria.getCidadeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EstadoCriteria> copyFiltersAre(EstadoCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getNaturalidade(), copy.getNaturalidade()) &&
                condition.apply(criteria.getSigla(), copy.getSigla()) &&
                condition.apply(criteria.getCidadeId(), copy.getCidadeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
