package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class OpcaoNomeFantasiaEmpresaCriteriaTest {

    @Test
    void newOpcaoNomeFantasiaEmpresaCriteriaHasAllFiltersNullTest() {
        var opcaoNomeFantasiaEmpresaCriteria = new OpcaoNomeFantasiaEmpresaCriteria();
        assertThat(opcaoNomeFantasiaEmpresaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void opcaoNomeFantasiaEmpresaCriteriaFluentMethodsCreatesFiltersTest() {
        var opcaoNomeFantasiaEmpresaCriteria = new OpcaoNomeFantasiaEmpresaCriteria();

        setAllFilters(opcaoNomeFantasiaEmpresaCriteria);

        assertThat(opcaoNomeFantasiaEmpresaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void opcaoNomeFantasiaEmpresaCriteriaCopyCreatesNullFilterTest() {
        var opcaoNomeFantasiaEmpresaCriteria = new OpcaoNomeFantasiaEmpresaCriteria();
        var copy = opcaoNomeFantasiaEmpresaCriteria.copy();

        assertThat(opcaoNomeFantasiaEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(opcaoNomeFantasiaEmpresaCriteria)
        );
    }

    @Test
    void opcaoNomeFantasiaEmpresaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var opcaoNomeFantasiaEmpresaCriteria = new OpcaoNomeFantasiaEmpresaCriteria();
        setAllFilters(opcaoNomeFantasiaEmpresaCriteria);

        var copy = opcaoNomeFantasiaEmpresaCriteria.copy();

        assertThat(opcaoNomeFantasiaEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(opcaoNomeFantasiaEmpresaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var opcaoNomeFantasiaEmpresaCriteria = new OpcaoNomeFantasiaEmpresaCriteria();

        assertThat(opcaoNomeFantasiaEmpresaCriteria).hasToString("OpcaoNomeFantasiaEmpresaCriteria{}");
    }

    private static void setAllFilters(OpcaoNomeFantasiaEmpresaCriteria opcaoNomeFantasiaEmpresaCriteria) {
        opcaoNomeFantasiaEmpresaCriteria.id();
        opcaoNomeFantasiaEmpresaCriteria.nome();
        opcaoNomeFantasiaEmpresaCriteria.ordem();
        opcaoNomeFantasiaEmpresaCriteria.selecionado();
        opcaoNomeFantasiaEmpresaCriteria.empresaId();
        opcaoNomeFantasiaEmpresaCriteria.distinct();
    }

    private static Condition<OpcaoNomeFantasiaEmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getOrdem()) &&
                condition.apply(criteria.getSelecionado()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<OpcaoNomeFantasiaEmpresaCriteria> copyFiltersAre(
        OpcaoNomeFantasiaEmpresaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getOrdem(), copy.getOrdem()) &&
                condition.apply(criteria.getSelecionado(), copy.getSelecionado()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
