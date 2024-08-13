package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PrazoAssinaturaCriteriaTest {

    @Test
    void newPrazoAssinaturaCriteriaHasAllFiltersNullTest() {
        var prazoAssinaturaCriteria = new PrazoAssinaturaCriteria();
        assertThat(prazoAssinaturaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void prazoAssinaturaCriteriaFluentMethodsCreatesFiltersTest() {
        var prazoAssinaturaCriteria = new PrazoAssinaturaCriteria();

        setAllFilters(prazoAssinaturaCriteria);

        assertThat(prazoAssinaturaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void prazoAssinaturaCriteriaCopyCreatesNullFilterTest() {
        var prazoAssinaturaCriteria = new PrazoAssinaturaCriteria();
        var copy = prazoAssinaturaCriteria.copy();

        assertThat(prazoAssinaturaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(prazoAssinaturaCriteria)
        );
    }

    @Test
    void prazoAssinaturaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var prazoAssinaturaCriteria = new PrazoAssinaturaCriteria();
        setAllFilters(prazoAssinaturaCriteria);

        var copy = prazoAssinaturaCriteria.copy();

        assertThat(prazoAssinaturaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(prazoAssinaturaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var prazoAssinaturaCriteria = new PrazoAssinaturaCriteria();

        assertThat(prazoAssinaturaCriteria).hasToString("PrazoAssinaturaCriteria{}");
    }

    private static void setAllFilters(PrazoAssinaturaCriteria prazoAssinaturaCriteria) {
        prazoAssinaturaCriteria.id();
        prazoAssinaturaCriteria.prazo();
        prazoAssinaturaCriteria.meses();
        prazoAssinaturaCriteria.distinct();
    }

    private static Condition<PrazoAssinaturaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPrazo()) &&
                condition.apply(criteria.getMeses()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PrazoAssinaturaCriteria> copyFiltersAre(
        PrazoAssinaturaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPrazo(), copy.getPrazo()) &&
                condition.apply(criteria.getMeses(), copy.getMeses()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
