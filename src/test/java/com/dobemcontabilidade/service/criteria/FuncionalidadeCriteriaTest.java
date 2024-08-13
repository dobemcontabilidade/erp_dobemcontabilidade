package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FuncionalidadeCriteriaTest {

    @Test
    void newFuncionalidadeCriteriaHasAllFiltersNullTest() {
        var funcionalidadeCriteria = new FuncionalidadeCriteria();
        assertThat(funcionalidadeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void funcionalidadeCriteriaFluentMethodsCreatesFiltersTest() {
        var funcionalidadeCriteria = new FuncionalidadeCriteria();

        setAllFilters(funcionalidadeCriteria);

        assertThat(funcionalidadeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void funcionalidadeCriteriaCopyCreatesNullFilterTest() {
        var funcionalidadeCriteria = new FuncionalidadeCriteria();
        var copy = funcionalidadeCriteria.copy();

        assertThat(funcionalidadeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(funcionalidadeCriteria)
        );
    }

    @Test
    void funcionalidadeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var funcionalidadeCriteria = new FuncionalidadeCriteria();
        setAllFilters(funcionalidadeCriteria);

        var copy = funcionalidadeCriteria.copy();

        assertThat(funcionalidadeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(funcionalidadeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var funcionalidadeCriteria = new FuncionalidadeCriteria();

        assertThat(funcionalidadeCriteria).hasToString("FuncionalidadeCriteria{}");
    }

    private static void setAllFilters(FuncionalidadeCriteria funcionalidadeCriteria) {
        funcionalidadeCriteria.id();
        funcionalidadeCriteria.nome();
        funcionalidadeCriteria.ativa();
        funcionalidadeCriteria.funcionalidadeGrupoAcessoPadraoId();
        funcionalidadeCriteria.funcionalidadeGrupoAcessoEmpresaId();
        funcionalidadeCriteria.moduloId();
        funcionalidadeCriteria.distinct();
    }

    private static Condition<FuncionalidadeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getAtiva()) &&
                condition.apply(criteria.getFuncionalidadeGrupoAcessoPadraoId()) &&
                condition.apply(criteria.getFuncionalidadeGrupoAcessoEmpresaId()) &&
                condition.apply(criteria.getModuloId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FuncionalidadeCriteria> copyFiltersAre(
        FuncionalidadeCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getAtiva(), copy.getAtiva()) &&
                condition.apply(criteria.getFuncionalidadeGrupoAcessoPadraoId(), copy.getFuncionalidadeGrupoAcessoPadraoId()) &&
                condition.apply(criteria.getFuncionalidadeGrupoAcessoEmpresaId(), copy.getFuncionalidadeGrupoAcessoEmpresaId()) &&
                condition.apply(criteria.getModuloId(), copy.getModuloId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
