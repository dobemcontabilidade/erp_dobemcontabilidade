package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AdicionalEnquadramentoCriteriaTest {

    @Test
    void newAdicionalEnquadramentoCriteriaHasAllFiltersNullTest() {
        var adicionalEnquadramentoCriteria = new AdicionalEnquadramentoCriteria();
        assertThat(adicionalEnquadramentoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void adicionalEnquadramentoCriteriaFluentMethodsCreatesFiltersTest() {
        var adicionalEnquadramentoCriteria = new AdicionalEnquadramentoCriteria();

        setAllFilters(adicionalEnquadramentoCriteria);

        assertThat(adicionalEnquadramentoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void adicionalEnquadramentoCriteriaCopyCreatesNullFilterTest() {
        var adicionalEnquadramentoCriteria = new AdicionalEnquadramentoCriteria();
        var copy = adicionalEnquadramentoCriteria.copy();

        assertThat(adicionalEnquadramentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(adicionalEnquadramentoCriteria)
        );
    }

    @Test
    void adicionalEnquadramentoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var adicionalEnquadramentoCriteria = new AdicionalEnquadramentoCriteria();
        setAllFilters(adicionalEnquadramentoCriteria);

        var copy = adicionalEnquadramentoCriteria.copy();

        assertThat(adicionalEnquadramentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(adicionalEnquadramentoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var adicionalEnquadramentoCriteria = new AdicionalEnquadramentoCriteria();

        assertThat(adicionalEnquadramentoCriteria).hasToString("AdicionalEnquadramentoCriteria{}");
    }

    private static void setAllFilters(AdicionalEnquadramentoCriteria adicionalEnquadramentoCriteria) {
        adicionalEnquadramentoCriteria.id();
        adicionalEnquadramentoCriteria.valor();
        adicionalEnquadramentoCriteria.enquadramentoId();
        adicionalEnquadramentoCriteria.planoContabilId();
        adicionalEnquadramentoCriteria.distinct();
    }

    private static Condition<AdicionalEnquadramentoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getValor()) &&
                condition.apply(criteria.getEnquadramentoId()) &&
                condition.apply(criteria.getPlanoContabilId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AdicionalEnquadramentoCriteria> copyFiltersAre(
        AdicionalEnquadramentoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getValor(), copy.getValor()) &&
                condition.apply(criteria.getEnquadramentoId(), copy.getEnquadramentoId()) &&
                condition.apply(criteria.getPlanoContabilId(), copy.getPlanoContabilId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
