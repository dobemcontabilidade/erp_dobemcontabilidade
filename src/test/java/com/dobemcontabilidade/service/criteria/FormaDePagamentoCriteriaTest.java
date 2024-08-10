package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FormaDePagamentoCriteriaTest {

    @Test
    void newFormaDePagamentoCriteriaHasAllFiltersNullTest() {
        var formaDePagamentoCriteria = new FormaDePagamentoCriteria();
        assertThat(formaDePagamentoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void formaDePagamentoCriteriaFluentMethodsCreatesFiltersTest() {
        var formaDePagamentoCriteria = new FormaDePagamentoCriteria();

        setAllFilters(formaDePagamentoCriteria);

        assertThat(formaDePagamentoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void formaDePagamentoCriteriaCopyCreatesNullFilterTest() {
        var formaDePagamentoCriteria = new FormaDePagamentoCriteria();
        var copy = formaDePagamentoCriteria.copy();

        assertThat(formaDePagamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(formaDePagamentoCriteria)
        );
    }

    @Test
    void formaDePagamentoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var formaDePagamentoCriteria = new FormaDePagamentoCriteria();
        setAllFilters(formaDePagamentoCriteria);

        var copy = formaDePagamentoCriteria.copy();

        assertThat(formaDePagamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(formaDePagamentoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var formaDePagamentoCriteria = new FormaDePagamentoCriteria();

        assertThat(formaDePagamentoCriteria).hasToString("FormaDePagamentoCriteria{}");
    }

    private static void setAllFilters(FormaDePagamentoCriteria formaDePagamentoCriteria) {
        formaDePagamentoCriteria.id();
        formaDePagamentoCriteria.forma();
        formaDePagamentoCriteria.disponivel();
        formaDePagamentoCriteria.assinaturaEmpresaId();
        formaDePagamentoCriteria.distinct();
    }

    private static Condition<FormaDePagamentoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getForma()) &&
                condition.apply(criteria.getDisponivel()) &&
                condition.apply(criteria.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FormaDePagamentoCriteria> copyFiltersAre(
        FormaDePagamentoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getForma(), copy.getForma()) &&
                condition.apply(criteria.getDisponivel(), copy.getDisponivel()) &&
                condition.apply(criteria.getAssinaturaEmpresaId(), copy.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
