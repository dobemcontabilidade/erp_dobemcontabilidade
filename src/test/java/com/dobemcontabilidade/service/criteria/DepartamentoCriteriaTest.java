package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DepartamentoCriteriaTest {

    @Test
    void newDepartamentoCriteriaHasAllFiltersNullTest() {
        var departamentoCriteria = new DepartamentoCriteria();
        assertThat(departamentoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void departamentoCriteriaFluentMethodsCreatesFiltersTest() {
        var departamentoCriteria = new DepartamentoCriteria();

        setAllFilters(departamentoCriteria);

        assertThat(departamentoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void departamentoCriteriaCopyCreatesNullFilterTest() {
        var departamentoCriteria = new DepartamentoCriteria();
        var copy = departamentoCriteria.copy();

        assertThat(departamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(departamentoCriteria)
        );
    }

    @Test
    void departamentoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var departamentoCriteria = new DepartamentoCriteria();
        setAllFilters(departamentoCriteria);

        var copy = departamentoCriteria.copy();

        assertThat(departamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(departamentoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var departamentoCriteria = new DepartamentoCriteria();

        assertThat(departamentoCriteria).hasToString("DepartamentoCriteria{}");
    }

    private static void setAllFilters(DepartamentoCriteria departamentoCriteria) {
        departamentoCriteria.id();
        departamentoCriteria.nome();
        departamentoCriteria.departamentoEmpresaId();
        departamentoCriteria.perfilContadorDepartamentoId();
        departamentoCriteria.departamentoContadorId();
        departamentoCriteria.departamentoFuncionarioId();
        departamentoCriteria.distinct();
    }

    private static Condition<DepartamentoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getDepartamentoEmpresaId()) &&
                condition.apply(criteria.getPerfilContadorDepartamentoId()) &&
                condition.apply(criteria.getDepartamentoContadorId()) &&
                condition.apply(criteria.getDepartamentoFuncionarioId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DepartamentoCriteria> copyFiltersAre(
        DepartamentoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getDepartamentoEmpresaId(), copy.getDepartamentoEmpresaId()) &&
                condition.apply(criteria.getPerfilContadorDepartamentoId(), copy.getPerfilContadorDepartamentoId()) &&
                condition.apply(criteria.getDepartamentoContadorId(), copy.getDepartamentoContadorId()) &&
                condition.apply(criteria.getDepartamentoFuncionarioId(), copy.getDepartamentoFuncionarioId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
