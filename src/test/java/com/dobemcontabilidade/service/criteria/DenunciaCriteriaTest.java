package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DenunciaCriteriaTest {

    @Test
    void newDenunciaCriteriaHasAllFiltersNullTest() {
        var denunciaCriteria = new DenunciaCriteria();
        assertThat(denunciaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void denunciaCriteriaFluentMethodsCreatesFiltersTest() {
        var denunciaCriteria = new DenunciaCriteria();

        setAllFilters(denunciaCriteria);

        assertThat(denunciaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void denunciaCriteriaCopyCreatesNullFilterTest() {
        var denunciaCriteria = new DenunciaCriteria();
        var copy = denunciaCriteria.copy();

        assertThat(denunciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(denunciaCriteria)
        );
    }

    @Test
    void denunciaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var denunciaCriteria = new DenunciaCriteria();
        setAllFilters(denunciaCriteria);

        var copy = denunciaCriteria.copy();

        assertThat(denunciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(denunciaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var denunciaCriteria = new DenunciaCriteria();

        assertThat(denunciaCriteria).hasToString("DenunciaCriteria{}");
    }

    private static void setAllFilters(DenunciaCriteria denunciaCriteria) {
        denunciaCriteria.id();
        denunciaCriteria.titulo();
        denunciaCriteria.mensagem();
        denunciaCriteria.distinct();
    }

    private static Condition<DenunciaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTitulo()) &&
                condition.apply(criteria.getMensagem()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DenunciaCriteria> copyFiltersAre(DenunciaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTitulo(), copy.getTitulo()) &&
                condition.apply(criteria.getMensagem(), copy.getMensagem()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
