package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FrequenciaCriteriaTest {

    @Test
    void newFrequenciaCriteriaHasAllFiltersNullTest() {
        var frequenciaCriteria = new FrequenciaCriteria();
        assertThat(frequenciaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void frequenciaCriteriaFluentMethodsCreatesFiltersTest() {
        var frequenciaCriteria = new FrequenciaCriteria();

        setAllFilters(frequenciaCriteria);

        assertThat(frequenciaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void frequenciaCriteriaCopyCreatesNullFilterTest() {
        var frequenciaCriteria = new FrequenciaCriteria();
        var copy = frequenciaCriteria.copy();

        assertThat(frequenciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(frequenciaCriteria)
        );
    }

    @Test
    void frequenciaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var frequenciaCriteria = new FrequenciaCriteria();
        setAllFilters(frequenciaCriteria);

        var copy = frequenciaCriteria.copy();

        assertThat(frequenciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(frequenciaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var frequenciaCriteria = new FrequenciaCriteria();

        assertThat(frequenciaCriteria).hasToString("FrequenciaCriteria{}");
    }

    private static void setAllFilters(FrequenciaCriteria frequenciaCriteria) {
        frequenciaCriteria.id();
        frequenciaCriteria.nome();
        frequenciaCriteria.numeroDias();
        frequenciaCriteria.tarefaId();
        frequenciaCriteria.distinct();
    }

    private static Condition<FrequenciaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getNumeroDias()) &&
                condition.apply(criteria.getTarefaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FrequenciaCriteria> copyFiltersAre(FrequenciaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getNumeroDias(), copy.getNumeroDias()) &&
                condition.apply(criteria.getTarefaId(), copy.getTarefaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
