package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DepartamentoContadorCriteriaTest {

    @Test
    void newDepartamentoContadorCriteriaHasAllFiltersNullTest() {
        var departamentoContadorCriteria = new DepartamentoContadorCriteria();
        assertThat(departamentoContadorCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void departamentoContadorCriteriaFluentMethodsCreatesFiltersTest() {
        var departamentoContadorCriteria = new DepartamentoContadorCriteria();

        setAllFilters(departamentoContadorCriteria);

        assertThat(departamentoContadorCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void departamentoContadorCriteriaCopyCreatesNullFilterTest() {
        var departamentoContadorCriteria = new DepartamentoContadorCriteria();
        var copy = departamentoContadorCriteria.copy();

        assertThat(departamentoContadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(departamentoContadorCriteria)
        );
    }

    @Test
    void departamentoContadorCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var departamentoContadorCriteria = new DepartamentoContadorCriteria();
        setAllFilters(departamentoContadorCriteria);

        var copy = departamentoContadorCriteria.copy();

        assertThat(departamentoContadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(departamentoContadorCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var departamentoContadorCriteria = new DepartamentoContadorCriteria();

        assertThat(departamentoContadorCriteria).hasToString("DepartamentoContadorCriteria{}");
    }

    private static void setAllFilters(DepartamentoContadorCriteria departamentoContadorCriteria) {
        departamentoContadorCriteria.id();
        departamentoContadorCriteria.percentualExperiencia();
        departamentoContadorCriteria.descricaoExperiencia();
        departamentoContadorCriteria.pontuacaoEntrevista();
        departamentoContadorCriteria.pontuacaoAvaliacao();
        departamentoContadorCriteria.departamentoId();
        departamentoContadorCriteria.contadorId();
        departamentoContadorCriteria.distinct();
    }

    private static Condition<DepartamentoContadorCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPercentualExperiencia()) &&
                condition.apply(criteria.getDescricaoExperiencia()) &&
                condition.apply(criteria.getPontuacaoEntrevista()) &&
                condition.apply(criteria.getPontuacaoAvaliacao()) &&
                condition.apply(criteria.getDepartamentoId()) &&
                condition.apply(criteria.getContadorId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DepartamentoContadorCriteria> copyFiltersAre(
        DepartamentoContadorCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPercentualExperiencia(), copy.getPercentualExperiencia()) &&
                condition.apply(criteria.getDescricaoExperiencia(), copy.getDescricaoExperiencia()) &&
                condition.apply(criteria.getPontuacaoEntrevista(), copy.getPontuacaoEntrevista()) &&
                condition.apply(criteria.getPontuacaoAvaliacao(), copy.getPontuacaoAvaliacao()) &&
                condition.apply(criteria.getDepartamentoId(), copy.getDepartamentoId()) &&
                condition.apply(criteria.getContadorId(), copy.getContadorId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
