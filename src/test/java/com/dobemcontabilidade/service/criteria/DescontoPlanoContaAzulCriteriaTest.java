package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DescontoPlanoContaAzulCriteriaTest {

    @Test
    void newDescontoPlanoContaAzulCriteriaHasAllFiltersNullTest() {
        var descontoPlanoContaAzulCriteria = new DescontoPlanoContaAzulCriteria();
        assertThat(descontoPlanoContaAzulCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void descontoPlanoContaAzulCriteriaFluentMethodsCreatesFiltersTest() {
        var descontoPlanoContaAzulCriteria = new DescontoPlanoContaAzulCriteria();

        setAllFilters(descontoPlanoContaAzulCriteria);

        assertThat(descontoPlanoContaAzulCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void descontoPlanoContaAzulCriteriaCopyCreatesNullFilterTest() {
        var descontoPlanoContaAzulCriteria = new DescontoPlanoContaAzulCriteria();
        var copy = descontoPlanoContaAzulCriteria.copy();

        assertThat(descontoPlanoContaAzulCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(descontoPlanoContaAzulCriteria)
        );
    }

    @Test
    void descontoPlanoContaAzulCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var descontoPlanoContaAzulCriteria = new DescontoPlanoContaAzulCriteria();
        setAllFilters(descontoPlanoContaAzulCriteria);

        var copy = descontoPlanoContaAzulCriteria.copy();

        assertThat(descontoPlanoContaAzulCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(descontoPlanoContaAzulCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var descontoPlanoContaAzulCriteria = new DescontoPlanoContaAzulCriteria();

        assertThat(descontoPlanoContaAzulCriteria).hasToString("DescontoPlanoContaAzulCriteria{}");
    }

    private static void setAllFilters(DescontoPlanoContaAzulCriteria descontoPlanoContaAzulCriteria) {
        descontoPlanoContaAzulCriteria.id();
        descontoPlanoContaAzulCriteria.percentual();
        descontoPlanoContaAzulCriteria.calculoPlanoAssinaturaId();
        descontoPlanoContaAzulCriteria.planoContaAzulId();
        descontoPlanoContaAzulCriteria.periodoPagamentoId();
        descontoPlanoContaAzulCriteria.distinct();
    }

    private static Condition<DescontoPlanoContaAzulCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPercentual()) &&
                condition.apply(criteria.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getPlanoContaAzulId()) &&
                condition.apply(criteria.getPeriodoPagamentoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DescontoPlanoContaAzulCriteria> copyFiltersAre(
        DescontoPlanoContaAzulCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPercentual(), copy.getPercentual()) &&
                condition.apply(criteria.getCalculoPlanoAssinaturaId(), copy.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getPlanoContaAzulId(), copy.getPlanoContaAzulId()) &&
                condition.apply(criteria.getPeriodoPagamentoId(), copy.getPeriodoPagamentoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
