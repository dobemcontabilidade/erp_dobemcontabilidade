package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SecaoCnaeCriteriaTest {

    @Test
    void newSecaoCnaeCriteriaHasAllFiltersNullTest() {
        var secaoCnaeCriteria = new SecaoCnaeCriteria();
        assertThat(secaoCnaeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void secaoCnaeCriteriaFluentMethodsCreatesFiltersTest() {
        var secaoCnaeCriteria = new SecaoCnaeCriteria();

        setAllFilters(secaoCnaeCriteria);

        assertThat(secaoCnaeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void secaoCnaeCriteriaCopyCreatesNullFilterTest() {
        var secaoCnaeCriteria = new SecaoCnaeCriteria();
        var copy = secaoCnaeCriteria.copy();

        assertThat(secaoCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(secaoCnaeCriteria)
        );
    }

    @Test
    void secaoCnaeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var secaoCnaeCriteria = new SecaoCnaeCriteria();
        setAllFilters(secaoCnaeCriteria);

        var copy = secaoCnaeCriteria.copy();

        assertThat(secaoCnaeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(secaoCnaeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var secaoCnaeCriteria = new SecaoCnaeCriteria();

        assertThat(secaoCnaeCriteria).hasToString("SecaoCnaeCriteria{}");
    }

    private static void setAllFilters(SecaoCnaeCriteria secaoCnaeCriteria) {
        secaoCnaeCriteria.id();
        secaoCnaeCriteria.codigo();
        secaoCnaeCriteria.divisaoCnaeId();
        secaoCnaeCriteria.distinct();
    }

    private static Condition<SecaoCnaeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCodigo()) &&
                condition.apply(criteria.getDivisaoCnaeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SecaoCnaeCriteria> copyFiltersAre(SecaoCnaeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCodigo(), copy.getCodigo()) &&
                condition.apply(criteria.getDivisaoCnaeId(), copy.getDivisaoCnaeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
