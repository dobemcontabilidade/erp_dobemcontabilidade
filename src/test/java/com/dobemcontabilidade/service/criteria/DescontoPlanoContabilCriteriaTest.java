package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DescontoPlanoContabilCriteriaTest {

    @Test
    void newDescontoPlanoContabilCriteriaHasAllFiltersNullTest() {
        var descontoPlanoContabilCriteria = new DescontoPlanoContabilCriteria();
        assertThat(descontoPlanoContabilCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void descontoPlanoContabilCriteriaFluentMethodsCreatesFiltersTest() {
        var descontoPlanoContabilCriteria = new DescontoPlanoContabilCriteria();

        setAllFilters(descontoPlanoContabilCriteria);

        assertThat(descontoPlanoContabilCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void descontoPlanoContabilCriteriaCopyCreatesNullFilterTest() {
        var descontoPlanoContabilCriteria = new DescontoPlanoContabilCriteria();
        var copy = descontoPlanoContabilCriteria.copy();

        assertThat(descontoPlanoContabilCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(descontoPlanoContabilCriteria)
        );
    }

    @Test
    void descontoPlanoContabilCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var descontoPlanoContabilCriteria = new DescontoPlanoContabilCriteria();
        setAllFilters(descontoPlanoContabilCriteria);

        var copy = descontoPlanoContabilCriteria.copy();

        assertThat(descontoPlanoContabilCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(descontoPlanoContabilCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var descontoPlanoContabilCriteria = new DescontoPlanoContabilCriteria();

        assertThat(descontoPlanoContabilCriteria).hasToString("DescontoPlanoContabilCriteria{}");
    }

    private static void setAllFilters(DescontoPlanoContabilCriteria descontoPlanoContabilCriteria) {
        descontoPlanoContabilCriteria.id();
        descontoPlanoContabilCriteria.percentual();
        descontoPlanoContabilCriteria.calculoPlanoAssinaturaId();
        descontoPlanoContabilCriteria.periodoPagamentoId();
        descontoPlanoContabilCriteria.planoContabilId();
        descontoPlanoContabilCriteria.distinct();
    }

    private static Condition<DescontoPlanoContabilCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPercentual()) &&
                condition.apply(criteria.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getPeriodoPagamentoId()) &&
                condition.apply(criteria.getPlanoContabilId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DescontoPlanoContabilCriteria> copyFiltersAre(
        DescontoPlanoContabilCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPercentual(), copy.getPercentual()) &&
                condition.apply(criteria.getCalculoPlanoAssinaturaId(), copy.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getPeriodoPagamentoId(), copy.getPeriodoPagamentoId()) &&
                condition.apply(criteria.getPlanoContabilId(), copy.getPlanoContabilId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
