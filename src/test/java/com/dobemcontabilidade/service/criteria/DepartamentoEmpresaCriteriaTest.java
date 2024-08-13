package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DepartamentoEmpresaCriteriaTest {

    @Test
    void newDepartamentoEmpresaCriteriaHasAllFiltersNullTest() {
        var departamentoEmpresaCriteria = new DepartamentoEmpresaCriteria();
        assertThat(departamentoEmpresaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void departamentoEmpresaCriteriaFluentMethodsCreatesFiltersTest() {
        var departamentoEmpresaCriteria = new DepartamentoEmpresaCriteria();

        setAllFilters(departamentoEmpresaCriteria);

        assertThat(departamentoEmpresaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void departamentoEmpresaCriteriaCopyCreatesNullFilterTest() {
        var departamentoEmpresaCriteria = new DepartamentoEmpresaCriteria();
        var copy = departamentoEmpresaCriteria.copy();

        assertThat(departamentoEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(departamentoEmpresaCriteria)
        );
    }

    @Test
    void departamentoEmpresaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var departamentoEmpresaCriteria = new DepartamentoEmpresaCriteria();
        setAllFilters(departamentoEmpresaCriteria);

        var copy = departamentoEmpresaCriteria.copy();

        assertThat(departamentoEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(departamentoEmpresaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var departamentoEmpresaCriteria = new DepartamentoEmpresaCriteria();

        assertThat(departamentoEmpresaCriteria).hasToString("DepartamentoEmpresaCriteria{}");
    }

    private static void setAllFilters(DepartamentoEmpresaCriteria departamentoEmpresaCriteria) {
        departamentoEmpresaCriteria.id();
        departamentoEmpresaCriteria.pontuacao();
        departamentoEmpresaCriteria.depoimento();
        departamentoEmpresaCriteria.reclamacao();
        departamentoEmpresaCriteria.departamentoId();
        departamentoEmpresaCriteria.empresaId();
        departamentoEmpresaCriteria.contadorId();
        departamentoEmpresaCriteria.distinct();
    }

    private static Condition<DepartamentoEmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPontuacao()) &&
                condition.apply(criteria.getDepoimento()) &&
                condition.apply(criteria.getReclamacao()) &&
                condition.apply(criteria.getDepartamentoId()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getContadorId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DepartamentoEmpresaCriteria> copyFiltersAre(
        DepartamentoEmpresaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPontuacao(), copy.getPontuacao()) &&
                condition.apply(criteria.getDepoimento(), copy.getDepoimento()) &&
                condition.apply(criteria.getReclamacao(), copy.getReclamacao()) &&
                condition.apply(criteria.getDepartamentoId(), copy.getDepartamentoId()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getContadorId(), copy.getContadorId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
