package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TarefaEmpresaCriteriaTest {

    @Test
    void newTarefaEmpresaCriteriaHasAllFiltersNullTest() {
        var tarefaEmpresaCriteria = new TarefaEmpresaCriteria();
        assertThat(tarefaEmpresaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void tarefaEmpresaCriteriaFluentMethodsCreatesFiltersTest() {
        var tarefaEmpresaCriteria = new TarefaEmpresaCriteria();

        setAllFilters(tarefaEmpresaCriteria);

        assertThat(tarefaEmpresaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void tarefaEmpresaCriteriaCopyCreatesNullFilterTest() {
        var tarefaEmpresaCriteria = new TarefaEmpresaCriteria();
        var copy = tarefaEmpresaCriteria.copy();

        assertThat(tarefaEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(tarefaEmpresaCriteria)
        );
    }

    @Test
    void tarefaEmpresaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var tarefaEmpresaCriteria = new TarefaEmpresaCriteria();
        setAllFilters(tarefaEmpresaCriteria);

        var copy = tarefaEmpresaCriteria.copy();

        assertThat(tarefaEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(tarefaEmpresaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var tarefaEmpresaCriteria = new TarefaEmpresaCriteria();

        assertThat(tarefaEmpresaCriteria).hasToString("TarefaEmpresaCriteria{}");
    }

    private static void setAllFilters(TarefaEmpresaCriteria tarefaEmpresaCriteria) {
        tarefaEmpresaCriteria.id();
        tarefaEmpresaCriteria.dataHora();
        tarefaEmpresaCriteria.empresaId();
        tarefaEmpresaCriteria.contadorId();
        tarefaEmpresaCriteria.tarefaId();
        tarefaEmpresaCriteria.distinct();
    }

    private static Condition<TarefaEmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDataHora()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getContadorId()) &&
                condition.apply(criteria.getTarefaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TarefaEmpresaCriteria> copyFiltersAre(
        TarefaEmpresaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDataHora(), copy.getDataHora()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getContadorId(), copy.getContadorId()) &&
                condition.apply(criteria.getTarefaId(), copy.getTarefaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
