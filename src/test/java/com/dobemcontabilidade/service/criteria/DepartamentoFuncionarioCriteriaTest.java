package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DepartamentoFuncionarioCriteriaTest {

    @Test
    void newDepartamentoFuncionarioCriteriaHasAllFiltersNullTest() {
        var departamentoFuncionarioCriteria = new DepartamentoFuncionarioCriteria();
        assertThat(departamentoFuncionarioCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void departamentoFuncionarioCriteriaFluentMethodsCreatesFiltersTest() {
        var departamentoFuncionarioCriteria = new DepartamentoFuncionarioCriteria();

        setAllFilters(departamentoFuncionarioCriteria);

        assertThat(departamentoFuncionarioCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void departamentoFuncionarioCriteriaCopyCreatesNullFilterTest() {
        var departamentoFuncionarioCriteria = new DepartamentoFuncionarioCriteria();
        var copy = departamentoFuncionarioCriteria.copy();

        assertThat(departamentoFuncionarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(departamentoFuncionarioCriteria)
        );
    }

    @Test
    void departamentoFuncionarioCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var departamentoFuncionarioCriteria = new DepartamentoFuncionarioCriteria();
        setAllFilters(departamentoFuncionarioCriteria);

        var copy = departamentoFuncionarioCriteria.copy();

        assertThat(departamentoFuncionarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(departamentoFuncionarioCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var departamentoFuncionarioCriteria = new DepartamentoFuncionarioCriteria();

        assertThat(departamentoFuncionarioCriteria).hasToString("DepartamentoFuncionarioCriteria{}");
    }

    private static void setAllFilters(DepartamentoFuncionarioCriteria departamentoFuncionarioCriteria) {
        departamentoFuncionarioCriteria.id();
        departamentoFuncionarioCriteria.cargo();
        departamentoFuncionarioCriteria.funcionarioId();
        departamentoFuncionarioCriteria.departamentoId();
        departamentoFuncionarioCriteria.distinct();
    }

    private static Condition<DepartamentoFuncionarioCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCargo()) &&
                condition.apply(criteria.getFuncionarioId()) &&
                condition.apply(criteria.getDepartamentoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DepartamentoFuncionarioCriteria> copyFiltersAre(
        DepartamentoFuncionarioCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCargo(), copy.getCargo()) &&
                condition.apply(criteria.getFuncionarioId(), copy.getFuncionarioId()) &&
                condition.apply(criteria.getDepartamentoId(), copy.getDepartamentoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
