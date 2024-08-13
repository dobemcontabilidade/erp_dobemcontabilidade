package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class OpcaoRazaoSocialEmpresaCriteriaTest {

    @Test
    void newOpcaoRazaoSocialEmpresaCriteriaHasAllFiltersNullTest() {
        var opcaoRazaoSocialEmpresaCriteria = new OpcaoRazaoSocialEmpresaCriteria();
        assertThat(opcaoRazaoSocialEmpresaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void opcaoRazaoSocialEmpresaCriteriaFluentMethodsCreatesFiltersTest() {
        var opcaoRazaoSocialEmpresaCriteria = new OpcaoRazaoSocialEmpresaCriteria();

        setAllFilters(opcaoRazaoSocialEmpresaCriteria);

        assertThat(opcaoRazaoSocialEmpresaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void opcaoRazaoSocialEmpresaCriteriaCopyCreatesNullFilterTest() {
        var opcaoRazaoSocialEmpresaCriteria = new OpcaoRazaoSocialEmpresaCriteria();
        var copy = opcaoRazaoSocialEmpresaCriteria.copy();

        assertThat(opcaoRazaoSocialEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(opcaoRazaoSocialEmpresaCriteria)
        );
    }

    @Test
    void opcaoRazaoSocialEmpresaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var opcaoRazaoSocialEmpresaCriteria = new OpcaoRazaoSocialEmpresaCriteria();
        setAllFilters(opcaoRazaoSocialEmpresaCriteria);

        var copy = opcaoRazaoSocialEmpresaCriteria.copy();

        assertThat(opcaoRazaoSocialEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(opcaoRazaoSocialEmpresaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var opcaoRazaoSocialEmpresaCriteria = new OpcaoRazaoSocialEmpresaCriteria();

        assertThat(opcaoRazaoSocialEmpresaCriteria).hasToString("OpcaoRazaoSocialEmpresaCriteria{}");
    }

    private static void setAllFilters(OpcaoRazaoSocialEmpresaCriteria opcaoRazaoSocialEmpresaCriteria) {
        opcaoRazaoSocialEmpresaCriteria.id();
        opcaoRazaoSocialEmpresaCriteria.nome();
        opcaoRazaoSocialEmpresaCriteria.ordem();
        opcaoRazaoSocialEmpresaCriteria.selecionado();
        opcaoRazaoSocialEmpresaCriteria.empresaId();
        opcaoRazaoSocialEmpresaCriteria.distinct();
    }

    private static Condition<OpcaoRazaoSocialEmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
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

    private static Condition<OpcaoRazaoSocialEmpresaCriteria> copyFiltersAre(
        OpcaoRazaoSocialEmpresaCriteria copy,
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
