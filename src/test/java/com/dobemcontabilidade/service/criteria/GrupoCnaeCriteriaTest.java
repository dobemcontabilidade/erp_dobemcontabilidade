package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class GrupoCnaeCriteriaTest {

    @Test
    void newGrupoCnaeCriteriaHasAllFiltersNullTest() {
        var grupoCnaeCriteria = new GrupoCnaeCriteria();
        assertThat(grupoCnaeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void grupoCnaeCriteriaFluentMethodsCreatesFiltersTest() {
        var grupoCnaeCriteria = new GrupoCnaeCriteria();

        setAllFilters(grupoCnaeCriteria);

        assertThat(grupoCnaeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void grupoCnaeCriteriaCopyCreatesNullFilterTest() {
        var grupoCnaeCriteria = new GrupoCnaeCriteria();
        var copy = grupoCnaeCriteria.copy();

        assertThat(grupoCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(grupoCnaeCriteria)
        );
    }

    @Test
    void grupoCnaeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var grupoCnaeCriteria = new GrupoCnaeCriteria();
        setAllFilters(grupoCnaeCriteria);

        var copy = grupoCnaeCriteria.copy();

        assertThat(grupoCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(grupoCnaeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var grupoCnaeCriteria = new GrupoCnaeCriteria();

        assertThat(grupoCnaeCriteria).hasToString("GrupoCnaeCriteria{}");
    }

    private static void setAllFilters(GrupoCnaeCriteria grupoCnaeCriteria) {
        grupoCnaeCriteria.id();
        grupoCnaeCriteria.codigo();
        grupoCnaeCriteria.classeCnaeId();
        grupoCnaeCriteria.divisaoId();
        grupoCnaeCriteria.distinct();
    }

    private static Condition<GrupoCnaeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCodigo()) &&
                condition.apply(criteria.getClasseCnaeId()) &&
                condition.apply(criteria.getDivisaoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<GrupoCnaeCriteria> copyFiltersAre(GrupoCnaeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCodigo(), copy.getCodigo()) &&
                condition.apply(criteria.getClasseCnaeId(), copy.getClasseCnaeId()) &&
                condition.apply(criteria.getDivisaoId(), copy.getDivisaoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
