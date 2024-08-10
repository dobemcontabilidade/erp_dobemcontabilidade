package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ObservacaoCnaeCriteriaTest {

    @Test
    void newObservacaoCnaeCriteriaHasAllFiltersNullTest() {
        var observacaoCnaeCriteria = new ObservacaoCnaeCriteria();
        assertThat(observacaoCnaeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void observacaoCnaeCriteriaFluentMethodsCreatesFiltersTest() {
        var observacaoCnaeCriteria = new ObservacaoCnaeCriteria();

        setAllFilters(observacaoCnaeCriteria);

        assertThat(observacaoCnaeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void observacaoCnaeCriteriaCopyCreatesNullFilterTest() {
        var observacaoCnaeCriteria = new ObservacaoCnaeCriteria();
        var copy = observacaoCnaeCriteria.copy();

        assertThat(observacaoCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(observacaoCnaeCriteria)
        );
    }

    @Test
    void observacaoCnaeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var observacaoCnaeCriteria = new ObservacaoCnaeCriteria();
        setAllFilters(observacaoCnaeCriteria);

        var copy = observacaoCnaeCriteria.copy();

        assertThat(observacaoCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(observacaoCnaeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var observacaoCnaeCriteria = new ObservacaoCnaeCriteria();

        assertThat(observacaoCnaeCriteria).hasToString("ObservacaoCnaeCriteria{}");
    }

    private static void setAllFilters(ObservacaoCnaeCriteria observacaoCnaeCriteria) {
        observacaoCnaeCriteria.id();
        observacaoCnaeCriteria.subclasseId();
        observacaoCnaeCriteria.distinct();
    }

    private static Condition<ObservacaoCnaeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) && condition.apply(criteria.getSubclasseId()) && condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ObservacaoCnaeCriteria> copyFiltersAre(
        ObservacaoCnaeCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getSubclasseId(), copy.getSubclasseId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
