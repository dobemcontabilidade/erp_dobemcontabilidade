package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AdicionalTributacaoCriteriaTest {

    @Test
    void newAdicionalTributacaoCriteriaHasAllFiltersNullTest() {
        var adicionalTributacaoCriteria = new AdicionalTributacaoCriteria();
        assertThat(adicionalTributacaoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void adicionalTributacaoCriteriaFluentMethodsCreatesFiltersTest() {
        var adicionalTributacaoCriteria = new AdicionalTributacaoCriteria();

        setAllFilters(adicionalTributacaoCriteria);

        assertThat(adicionalTributacaoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void adicionalTributacaoCriteriaCopyCreatesNullFilterTest() {
        var adicionalTributacaoCriteria = new AdicionalTributacaoCriteria();
        var copy = adicionalTributacaoCriteria.copy();

        assertThat(adicionalTributacaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(adicionalTributacaoCriteria)
        );
    }

    @Test
    void adicionalTributacaoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var adicionalTributacaoCriteria = new AdicionalTributacaoCriteria();
        setAllFilters(adicionalTributacaoCriteria);

        var copy = adicionalTributacaoCriteria.copy();

        assertThat(adicionalTributacaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(adicionalTributacaoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var adicionalTributacaoCriteria = new AdicionalTributacaoCriteria();

        assertThat(adicionalTributacaoCriteria).hasToString("AdicionalTributacaoCriteria{}");
    }

    private static void setAllFilters(AdicionalTributacaoCriteria adicionalTributacaoCriteria) {
        adicionalTributacaoCriteria.id();
        adicionalTributacaoCriteria.valor();
        adicionalTributacaoCriteria.tributacaoId();
        adicionalTributacaoCriteria.planoAssinaturaContabilId();
        adicionalTributacaoCriteria.distinct();
    }

    private static Condition<AdicionalTributacaoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getValor()) &&
                condition.apply(criteria.getTributacaoId()) &&
                condition.apply(criteria.getPlanoAssinaturaContabilId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AdicionalTributacaoCriteria> copyFiltersAre(
        AdicionalTributacaoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getValor(), copy.getValor()) &&
                condition.apply(criteria.getTributacaoId(), copy.getTributacaoId()) &&
                condition.apply(criteria.getPlanoAssinaturaContabilId(), copy.getPlanoAssinaturaContabilId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
