package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PerfilContadorDepartamentoCriteriaTest {

    @Test
    void newPerfilContadorDepartamentoCriteriaHasAllFiltersNullTest() {
        var perfilContadorDepartamentoCriteria = new PerfilContadorDepartamentoCriteria();
        assertThat(perfilContadorDepartamentoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void perfilContadorDepartamentoCriteriaFluentMethodsCreatesFiltersTest() {
        var perfilContadorDepartamentoCriteria = new PerfilContadorDepartamentoCriteria();

        setAllFilters(perfilContadorDepartamentoCriteria);

        assertThat(perfilContadorDepartamentoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void perfilContadorDepartamentoCriteriaCopyCreatesNullFilterTest() {
        var perfilContadorDepartamentoCriteria = new PerfilContadorDepartamentoCriteria();
        var copy = perfilContadorDepartamentoCriteria.copy();

        assertThat(perfilContadorDepartamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(perfilContadorDepartamentoCriteria)
        );
    }

    @Test
    void perfilContadorDepartamentoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var perfilContadorDepartamentoCriteria = new PerfilContadorDepartamentoCriteria();
        setAllFilters(perfilContadorDepartamentoCriteria);

        var copy = perfilContadorDepartamentoCriteria.copy();

        assertThat(perfilContadorDepartamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(perfilContadorDepartamentoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var perfilContadorDepartamentoCriteria = new PerfilContadorDepartamentoCriteria();

        assertThat(perfilContadorDepartamentoCriteria).hasToString("PerfilContadorDepartamentoCriteria{}");
    }

    private static void setAllFilters(PerfilContadorDepartamentoCriteria perfilContadorDepartamentoCriteria) {
        perfilContadorDepartamentoCriteria.id();
        perfilContadorDepartamentoCriteria.quantidadeEmpresas();
        perfilContadorDepartamentoCriteria.percentualExperiencia();
        perfilContadorDepartamentoCriteria.departamentoId();
        perfilContadorDepartamentoCriteria.perfilContadorId();
        perfilContadorDepartamentoCriteria.distinct();
    }

    private static Condition<PerfilContadorDepartamentoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getQuantidadeEmpresas()) &&
                condition.apply(criteria.getPercentualExperiencia()) &&
                condition.apply(criteria.getDepartamentoId()) &&
                condition.apply(criteria.getPerfilContadorId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PerfilContadorDepartamentoCriteria> copyFiltersAre(
        PerfilContadorDepartamentoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getQuantidadeEmpresas(), copy.getQuantidadeEmpresas()) &&
                condition.apply(criteria.getPercentualExperiencia(), copy.getPercentualExperiencia()) &&
                condition.apply(criteria.getDepartamentoId(), copy.getDepartamentoId()) &&
                condition.apply(criteria.getPerfilContadorId(), copy.getPerfilContadorId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
