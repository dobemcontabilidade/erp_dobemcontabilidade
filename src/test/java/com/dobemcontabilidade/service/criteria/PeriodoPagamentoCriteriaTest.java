package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PeriodoPagamentoCriteriaTest {

    @Test
    void newPeriodoPagamentoCriteriaHasAllFiltersNullTest() {
        var periodoPagamentoCriteria = new PeriodoPagamentoCriteria();
        assertThat(periodoPagamentoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void periodoPagamentoCriteriaFluentMethodsCreatesFiltersTest() {
        var periodoPagamentoCriteria = new PeriodoPagamentoCriteria();

        setAllFilters(periodoPagamentoCriteria);

        assertThat(periodoPagamentoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void periodoPagamentoCriteriaCopyCreatesNullFilterTest() {
        var periodoPagamentoCriteria = new PeriodoPagamentoCriteria();
        var copy = periodoPagamentoCriteria.copy();

        assertThat(periodoPagamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(periodoPagamentoCriteria)
        );
    }

    @Test
    void periodoPagamentoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var periodoPagamentoCriteria = new PeriodoPagamentoCriteria();
        setAllFilters(periodoPagamentoCriteria);

        var copy = periodoPagamentoCriteria.copy();

        assertThat(periodoPagamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(periodoPagamentoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var periodoPagamentoCriteria = new PeriodoPagamentoCriteria();

        assertThat(periodoPagamentoCriteria).hasToString("PeriodoPagamentoCriteria{}");
    }

    private static void setAllFilters(PeriodoPagamentoCriteria periodoPagamentoCriteria) {
        periodoPagamentoCriteria.id();
        periodoPagamentoCriteria.periodo();
        periodoPagamentoCriteria.numeroDias();
        periodoPagamentoCriteria.idPlanGnet();
        periodoPagamentoCriteria.calculoPlanoAssinaturaId();
        periodoPagamentoCriteria.assinaturaEmpresaId();
        periodoPagamentoCriteria.descontoPlanoContaAzulId();
        periodoPagamentoCriteria.descontoPlanoContabilId();
        periodoPagamentoCriteria.distinct();
    }

    private static Condition<PeriodoPagamentoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPeriodo()) &&
                condition.apply(criteria.getNumeroDias()) &&
                condition.apply(criteria.getIdPlanGnet()) &&
                condition.apply(criteria.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDescontoPlanoContaAzulId()) &&
                condition.apply(criteria.getDescontoPlanoContabilId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PeriodoPagamentoCriteria> copyFiltersAre(
        PeriodoPagamentoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPeriodo(), copy.getPeriodo()) &&
                condition.apply(criteria.getNumeroDias(), copy.getNumeroDias()) &&
                condition.apply(criteria.getIdPlanGnet(), copy.getIdPlanGnet()) &&
                condition.apply(criteria.getCalculoPlanoAssinaturaId(), copy.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId(), copy.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDescontoPlanoContaAzulId(), copy.getDescontoPlanoContaAzulId()) &&
                condition.apply(criteria.getDescontoPlanoContabilId(), copy.getDescontoPlanoContabilId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
