package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CompetenciaCriteriaTest {

    @Test
    void newCompetenciaCriteriaHasAllFiltersNullTest() {
        var competenciaCriteria = new CompetenciaCriteria();
        assertThat(competenciaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void competenciaCriteriaFluentMethodsCreatesFiltersTest() {
        var competenciaCriteria = new CompetenciaCriteria();

        setAllFilters(competenciaCriteria);

        assertThat(competenciaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void competenciaCriteriaCopyCreatesNullFilterTest() {
        var competenciaCriteria = new CompetenciaCriteria();
        var copy = competenciaCriteria.copy();

        assertThat(competenciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(competenciaCriteria)
        );
    }

    @Test
    void competenciaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var competenciaCriteria = new CompetenciaCriteria();
        setAllFilters(competenciaCriteria);

        var copy = competenciaCriteria.copy();

        assertThat(competenciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(competenciaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var competenciaCriteria = new CompetenciaCriteria();

        assertThat(competenciaCriteria).hasToString("CompetenciaCriteria{}");
    }

    private static void setAllFilters(CompetenciaCriteria competenciaCriteria) {
        competenciaCriteria.id();
        competenciaCriteria.nome();
        competenciaCriteria.numero();
        competenciaCriteria.tarefaId();
        competenciaCriteria.distinct();
    }

    private static Condition<CompetenciaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getNumero()) &&
                condition.apply(criteria.getTarefaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CompetenciaCriteria> copyFiltersAre(CompetenciaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getNumero(), copy.getNumero()) &&
                condition.apply(criteria.getTarefaId(), copy.getTarefaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
