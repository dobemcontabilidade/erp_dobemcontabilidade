package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SubtarefaCriteriaTest {

    @Test
    void newSubtarefaCriteriaHasAllFiltersNullTest() {
        var subtarefaCriteria = new SubtarefaCriteria();
        assertThat(subtarefaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void subtarefaCriteriaFluentMethodsCreatesFiltersTest() {
        var subtarefaCriteria = new SubtarefaCriteria();

        setAllFilters(subtarefaCriteria);

        assertThat(subtarefaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void subtarefaCriteriaCopyCreatesNullFilterTest() {
        var subtarefaCriteria = new SubtarefaCriteria();
        var copy = subtarefaCriteria.copy();

        assertThat(subtarefaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(subtarefaCriteria)
        );
    }

    @Test
    void subtarefaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var subtarefaCriteria = new SubtarefaCriteria();
        setAllFilters(subtarefaCriteria);

        var copy = subtarefaCriteria.copy();

        assertThat(subtarefaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(subtarefaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var subtarefaCriteria = new SubtarefaCriteria();

        assertThat(subtarefaCriteria).hasToString("SubtarefaCriteria{}");
    }

    private static void setAllFilters(SubtarefaCriteria subtarefaCriteria) {
        subtarefaCriteria.id();
        subtarefaCriteria.ordem();
        subtarefaCriteria.item();
        subtarefaCriteria.tarefaId();
        subtarefaCriteria.distinct();
    }

    private static Condition<SubtarefaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getOrdem()) &&
                condition.apply(criteria.getItem()) &&
                condition.apply(criteria.getTarefaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SubtarefaCriteria> copyFiltersAre(SubtarefaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getOrdem(), copy.getOrdem()) &&
                condition.apply(criteria.getItem(), copy.getItem()) &&
                condition.apply(criteria.getTarefaId(), copy.getTarefaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
