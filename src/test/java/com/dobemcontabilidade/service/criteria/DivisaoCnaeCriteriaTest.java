package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DivisaoCnaeCriteriaTest {

    @Test
    void newDivisaoCnaeCriteriaHasAllFiltersNullTest() {
        var divisaoCnaeCriteria = new DivisaoCnaeCriteria();
        assertThat(divisaoCnaeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void divisaoCnaeCriteriaFluentMethodsCreatesFiltersTest() {
        var divisaoCnaeCriteria = new DivisaoCnaeCriteria();

        setAllFilters(divisaoCnaeCriteria);

        assertThat(divisaoCnaeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void divisaoCnaeCriteriaCopyCreatesNullFilterTest() {
        var divisaoCnaeCriteria = new DivisaoCnaeCriteria();
        var copy = divisaoCnaeCriteria.copy();

        assertThat(divisaoCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(divisaoCnaeCriteria)
        );
    }

    @Test
    void divisaoCnaeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var divisaoCnaeCriteria = new DivisaoCnaeCriteria();
        setAllFilters(divisaoCnaeCriteria);

        var copy = divisaoCnaeCriteria.copy();

        assertThat(divisaoCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(divisaoCnaeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var divisaoCnaeCriteria = new DivisaoCnaeCriteria();

        assertThat(divisaoCnaeCriteria).hasToString("DivisaoCnaeCriteria{}");
    }

    private static void setAllFilters(DivisaoCnaeCriteria divisaoCnaeCriteria) {
        divisaoCnaeCriteria.id();
        divisaoCnaeCriteria.codigo();
        divisaoCnaeCriteria.grupoCnaeId();
        divisaoCnaeCriteria.secaoId();
        divisaoCnaeCriteria.distinct();
    }

    private static Condition<DivisaoCnaeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCodigo()) &&
                condition.apply(criteria.getGrupoCnaeId()) &&
                condition.apply(criteria.getSecaoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DivisaoCnaeCriteria> copyFiltersAre(DivisaoCnaeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCodigo(), copy.getCodigo()) &&
                condition.apply(criteria.getGrupoCnaeId(), copy.getGrupoCnaeId()) &&
                condition.apply(criteria.getSecaoId(), copy.getSecaoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
