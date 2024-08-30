package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TributacaoCriteriaTest {

    @Test
    void newTributacaoCriteriaHasAllFiltersNullTest() {
        var tributacaoCriteria = new TributacaoCriteria();
        assertThat(tributacaoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void tributacaoCriteriaFluentMethodsCreatesFiltersTest() {
        var tributacaoCriteria = new TributacaoCriteria();

        setAllFilters(tributacaoCriteria);

        assertThat(tributacaoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void tributacaoCriteriaCopyCreatesNullFilterTest() {
        var tributacaoCriteria = new TributacaoCriteria();
        var copy = tributacaoCriteria.copy();

        assertThat(tributacaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(tributacaoCriteria)
        );
    }

    @Test
    void tributacaoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var tributacaoCriteria = new TributacaoCriteria();
        setAllFilters(tributacaoCriteria);

        var copy = tributacaoCriteria.copy();

        assertThat(tributacaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(tributacaoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var tributacaoCriteria = new TributacaoCriteria();

        assertThat(tributacaoCriteria).hasToString("TributacaoCriteria{}");
    }

    private static void setAllFilters(TributacaoCriteria tributacaoCriteria) {
        tributacaoCriteria.id();
        tributacaoCriteria.nome();
        tributacaoCriteria.situacao();
        tributacaoCriteria.empresaId();
        tributacaoCriteria.adicionalTributacaoId();
        tributacaoCriteria.distinct();
    }

    private static Condition<TributacaoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getSituacao()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getAdicionalTributacaoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TributacaoCriteria> copyFiltersAre(TributacaoCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getSituacao(), copy.getSituacao()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getAdicionalTributacaoId(), copy.getAdicionalTributacaoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
