package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PerfilAcessoCriteriaTest {

    @Test
    void newPerfilAcessoCriteriaHasAllFiltersNullTest() {
        var perfilAcessoCriteria = new PerfilAcessoCriteria();
        assertThat(perfilAcessoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void perfilAcessoCriteriaFluentMethodsCreatesFiltersTest() {
        var perfilAcessoCriteria = new PerfilAcessoCriteria();

        setAllFilters(perfilAcessoCriteria);

        assertThat(perfilAcessoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void perfilAcessoCriteriaCopyCreatesNullFilterTest() {
        var perfilAcessoCriteria = new PerfilAcessoCriteria();
        var copy = perfilAcessoCriteria.copy();

        assertThat(perfilAcessoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(perfilAcessoCriteria)
        );
    }

    @Test
    void perfilAcessoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var perfilAcessoCriteria = new PerfilAcessoCriteria();
        setAllFilters(perfilAcessoCriteria);

        var copy = perfilAcessoCriteria.copy();

        assertThat(perfilAcessoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(perfilAcessoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var perfilAcessoCriteria = new PerfilAcessoCriteria();

        assertThat(perfilAcessoCriteria).hasToString("PerfilAcessoCriteria{}");
    }

    private static void setAllFilters(PerfilAcessoCriteria perfilAcessoCriteria) {
        perfilAcessoCriteria.id();
        perfilAcessoCriteria.nome();
        perfilAcessoCriteria.descricao();
        perfilAcessoCriteria.distinct();
    }

    private static Condition<PerfilAcessoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getDescricao()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PerfilAcessoCriteria> copyFiltersAre(
        PerfilAcessoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getDescricao(), copy.getDescricao()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
