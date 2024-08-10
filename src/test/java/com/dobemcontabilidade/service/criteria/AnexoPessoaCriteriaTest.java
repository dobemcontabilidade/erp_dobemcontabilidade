package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AnexoPessoaCriteriaTest {

    @Test
    void newAnexoPessoaCriteriaHasAllFiltersNullTest() {
        var anexoPessoaCriteria = new AnexoPessoaCriteria();
        assertThat(anexoPessoaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void anexoPessoaCriteriaFluentMethodsCreatesFiltersTest() {
        var anexoPessoaCriteria = new AnexoPessoaCriteria();

        setAllFilters(anexoPessoaCriteria);

        assertThat(anexoPessoaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void anexoPessoaCriteriaCopyCreatesNullFilterTest() {
        var anexoPessoaCriteria = new AnexoPessoaCriteria();
        var copy = anexoPessoaCriteria.copy();

        assertThat(anexoPessoaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(anexoPessoaCriteria)
        );
    }

    @Test
    void anexoPessoaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var anexoPessoaCriteria = new AnexoPessoaCriteria();
        setAllFilters(anexoPessoaCriteria);

        var copy = anexoPessoaCriteria.copy();

        assertThat(anexoPessoaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(anexoPessoaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var anexoPessoaCriteria = new AnexoPessoaCriteria();

        assertThat(anexoPessoaCriteria).hasToString("AnexoPessoaCriteria{}");
    }

    private static void setAllFilters(AnexoPessoaCriteria anexoPessoaCriteria) {
        anexoPessoaCriteria.id();
        anexoPessoaCriteria.tipo();
        anexoPessoaCriteria.pessoaId();
        anexoPessoaCriteria.distinct();
    }

    private static Condition<AnexoPessoaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTipo()) &&
                condition.apply(criteria.getPessoaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AnexoPessoaCriteria> copyFiltersAre(AnexoPessoaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTipo(), copy.getTipo()) &&
                condition.apply(criteria.getPessoaId(), copy.getPessoaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
