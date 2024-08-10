package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PerfilContadorCriteriaTest {

    @Test
    void newPerfilContadorCriteriaHasAllFiltersNullTest() {
        var perfilContadorCriteria = new PerfilContadorCriteria();
        assertThat(perfilContadorCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void perfilContadorCriteriaFluentMethodsCreatesFiltersTest() {
        var perfilContadorCriteria = new PerfilContadorCriteria();

        setAllFilters(perfilContadorCriteria);

        assertThat(perfilContadorCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void perfilContadorCriteriaCopyCreatesNullFilterTest() {
        var perfilContadorCriteria = new PerfilContadorCriteria();
        var copy = perfilContadorCriteria.copy();

        assertThat(perfilContadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(perfilContadorCriteria)
        );
    }

    @Test
    void perfilContadorCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var perfilContadorCriteria = new PerfilContadorCriteria();
        setAllFilters(perfilContadorCriteria);

        var copy = perfilContadorCriteria.copy();

        assertThat(perfilContadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(perfilContadorCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var perfilContadorCriteria = new PerfilContadorCriteria();

        assertThat(perfilContadorCriteria).hasToString("PerfilContadorCriteria{}");
    }

    private static void setAllFilters(PerfilContadorCriteria perfilContadorCriteria) {
        perfilContadorCriteria.id();
        perfilContadorCriteria.perfil();
        perfilContadorCriteria.descricao();
        perfilContadorCriteria.limiteEmpresas();
        perfilContadorCriteria.limiteDepartamentos();
        perfilContadorCriteria.limiteAreaContabils();
        perfilContadorCriteria.limiteFaturamento();
        perfilContadorCriteria.perfilContadorAreaContabilId();
        perfilContadorCriteria.contadorId();
        perfilContadorCriteria.perfilContadorDepartamentoId();
        perfilContadorCriteria.distinct();
    }

    private static Condition<PerfilContadorCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPerfil()) &&
                condition.apply(criteria.getDescricao()) &&
                condition.apply(criteria.getLimiteEmpresas()) &&
                condition.apply(criteria.getLimiteDepartamentos()) &&
                condition.apply(criteria.getLimiteAreaContabils()) &&
                condition.apply(criteria.getLimiteFaturamento()) &&
                condition.apply(criteria.getPerfilContadorAreaContabilId()) &&
                condition.apply(criteria.getContadorId()) &&
                condition.apply(criteria.getPerfilContadorDepartamentoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PerfilContadorCriteria> copyFiltersAre(
        PerfilContadorCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPerfil(), copy.getPerfil()) &&
                condition.apply(criteria.getDescricao(), copy.getDescricao()) &&
                condition.apply(criteria.getLimiteEmpresas(), copy.getLimiteEmpresas()) &&
                condition.apply(criteria.getLimiteDepartamentos(), copy.getLimiteDepartamentos()) &&
                condition.apply(criteria.getLimiteAreaContabils(), copy.getLimiteAreaContabils()) &&
                condition.apply(criteria.getLimiteFaturamento(), copy.getLimiteFaturamento()) &&
                condition.apply(criteria.getPerfilContadorAreaContabilId(), copy.getPerfilContadorAreaContabilId()) &&
                condition.apply(criteria.getContadorId(), copy.getContadorId()) &&
                condition.apply(criteria.getPerfilContadorDepartamentoId(), copy.getPerfilContadorDepartamentoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
