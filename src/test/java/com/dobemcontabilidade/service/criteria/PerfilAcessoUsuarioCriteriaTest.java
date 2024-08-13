package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PerfilAcessoUsuarioCriteriaTest {

    @Test
    void newPerfilAcessoUsuarioCriteriaHasAllFiltersNullTest() {
        var perfilAcessoUsuarioCriteria = new PerfilAcessoUsuarioCriteria();
        assertThat(perfilAcessoUsuarioCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void perfilAcessoUsuarioCriteriaFluentMethodsCreatesFiltersTest() {
        var perfilAcessoUsuarioCriteria = new PerfilAcessoUsuarioCriteria();

        setAllFilters(perfilAcessoUsuarioCriteria);

        assertThat(perfilAcessoUsuarioCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void perfilAcessoUsuarioCriteriaCopyCreatesNullFilterTest() {
        var perfilAcessoUsuarioCriteria = new PerfilAcessoUsuarioCriteria();
        var copy = perfilAcessoUsuarioCriteria.copy();

        assertThat(perfilAcessoUsuarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(perfilAcessoUsuarioCriteria)
        );
    }

    @Test
    void perfilAcessoUsuarioCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var perfilAcessoUsuarioCriteria = new PerfilAcessoUsuarioCriteria();
        setAllFilters(perfilAcessoUsuarioCriteria);

        var copy = perfilAcessoUsuarioCriteria.copy();

        assertThat(perfilAcessoUsuarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(perfilAcessoUsuarioCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var perfilAcessoUsuarioCriteria = new PerfilAcessoUsuarioCriteria();

        assertThat(perfilAcessoUsuarioCriteria).hasToString("PerfilAcessoUsuarioCriteria{}");
    }

    private static void setAllFilters(PerfilAcessoUsuarioCriteria perfilAcessoUsuarioCriteria) {
        perfilAcessoUsuarioCriteria.id();
        perfilAcessoUsuarioCriteria.nome();
        perfilAcessoUsuarioCriteria.autorizado();
        perfilAcessoUsuarioCriteria.dataExpiracao();
        perfilAcessoUsuarioCriteria.distinct();
    }

    private static Condition<PerfilAcessoUsuarioCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getAutorizado()) &&
                condition.apply(criteria.getDataExpiracao()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PerfilAcessoUsuarioCriteria> copyFiltersAre(
        PerfilAcessoUsuarioCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getAutorizado(), copy.getAutorizado()) &&
                condition.apply(criteria.getDataExpiracao(), copy.getDataExpiracao()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
