package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DescontoPeriodoPagamentoCriteriaTest {

    @Test
    void newDescontoPeriodoPagamentoCriteriaHasAllFiltersNullTest() {
        var descontoPeriodoPagamentoCriteria = new DescontoPeriodoPagamentoCriteria();
        assertThat(descontoPeriodoPagamentoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void descontoPeriodoPagamentoCriteriaFluentMethodsCreatesFiltersTest() {
        var descontoPeriodoPagamentoCriteria = new DescontoPeriodoPagamentoCriteria();

        setAllFilters(descontoPeriodoPagamentoCriteria);

        assertThat(descontoPeriodoPagamentoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void descontoPeriodoPagamentoCriteriaCopyCreatesNullFilterTest() {
        var descontoPeriodoPagamentoCriteria = new DescontoPeriodoPagamentoCriteria();
        var copy = descontoPeriodoPagamentoCriteria.copy();

        assertThat(descontoPeriodoPagamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(descontoPeriodoPagamentoCriteria)
        );
    }

    @Test
    void descontoPeriodoPagamentoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var descontoPeriodoPagamentoCriteria = new DescontoPeriodoPagamentoCriteria();
        setAllFilters(descontoPeriodoPagamentoCriteria);

        var copy = descontoPeriodoPagamentoCriteria.copy();

        assertThat(descontoPeriodoPagamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(descontoPeriodoPagamentoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var descontoPeriodoPagamentoCriteria = new DescontoPeriodoPagamentoCriteria();

        assertThat(descontoPeriodoPagamentoCriteria).hasToString("DescontoPeriodoPagamentoCriteria{}");
    }

    private static void setAllFilters(DescontoPeriodoPagamentoCriteria descontoPeriodoPagamentoCriteria) {
        descontoPeriodoPagamentoCriteria.id();
        descontoPeriodoPagamentoCriteria.percentual();
        descontoPeriodoPagamentoCriteria.periodoPagamentoId();
        descontoPeriodoPagamentoCriteria.planoAssinaturaContabilId();
        descontoPeriodoPagamentoCriteria.distinct();
    }

    private static Condition<DescontoPeriodoPagamentoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPercentual()) &&
                condition.apply(criteria.getPeriodoPagamentoId()) &&
                condition.apply(criteria.getPlanoAssinaturaContabilId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DescontoPeriodoPagamentoCriteria> copyFiltersAre(
        DescontoPeriodoPagamentoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPercentual(), copy.getPercentual()) &&
                condition.apply(criteria.getPeriodoPagamentoId(), copy.getPeriodoPagamentoId()) &&
                condition.apply(criteria.getPlanoAssinaturaContabilId(), copy.getPlanoAssinaturaContabilId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
