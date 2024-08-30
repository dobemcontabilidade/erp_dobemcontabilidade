package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PlanoAssinaturaContabilCriteriaTest {

    @Test
    void newPlanoAssinaturaContabilCriteriaHasAllFiltersNullTest() {
        var planoAssinaturaContabilCriteria = new PlanoAssinaturaContabilCriteria();
        assertThat(planoAssinaturaContabilCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void planoAssinaturaContabilCriteriaFluentMethodsCreatesFiltersTest() {
        var planoAssinaturaContabilCriteria = new PlanoAssinaturaContabilCriteria();

        setAllFilters(planoAssinaturaContabilCriteria);

        assertThat(planoAssinaturaContabilCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void planoAssinaturaContabilCriteriaCopyCreatesNullFilterTest() {
        var planoAssinaturaContabilCriteria = new PlanoAssinaturaContabilCriteria();
        var copy = planoAssinaturaContabilCriteria.copy();

        assertThat(planoAssinaturaContabilCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(planoAssinaturaContabilCriteria)
        );
    }

    @Test
    void planoAssinaturaContabilCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var planoAssinaturaContabilCriteria = new PlanoAssinaturaContabilCriteria();
        setAllFilters(planoAssinaturaContabilCriteria);

        var copy = planoAssinaturaContabilCriteria.copy();

        assertThat(planoAssinaturaContabilCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(planoAssinaturaContabilCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var planoAssinaturaContabilCriteria = new PlanoAssinaturaContabilCriteria();

        assertThat(planoAssinaturaContabilCriteria).hasToString("PlanoAssinaturaContabilCriteria{}");
    }

    private static void setAllFilters(PlanoAssinaturaContabilCriteria planoAssinaturaContabilCriteria) {
        planoAssinaturaContabilCriteria.id();
        planoAssinaturaContabilCriteria.nome();
        planoAssinaturaContabilCriteria.adicionalSocio();
        planoAssinaturaContabilCriteria.adicionalFuncionario();
        planoAssinaturaContabilCriteria.sociosIsentos();
        planoAssinaturaContabilCriteria.adicionalFaturamento();
        planoAssinaturaContabilCriteria.valorBaseFaturamento();
        planoAssinaturaContabilCriteria.valorBaseAbertura();
        planoAssinaturaContabilCriteria.situacao();
        planoAssinaturaContabilCriteria.descontoPeriodoPagamentoId();
        planoAssinaturaContabilCriteria.adicionalRamoId();
        planoAssinaturaContabilCriteria.adicionalTributacaoId();
        planoAssinaturaContabilCriteria.adicionalEnquadramentoId();
        planoAssinaturaContabilCriteria.distinct();
    }

    private static Condition<PlanoAssinaturaContabilCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getAdicionalSocio()) &&
                condition.apply(criteria.getAdicionalFuncionario()) &&
                condition.apply(criteria.getSociosIsentos()) &&
                condition.apply(criteria.getAdicionalFaturamento()) &&
                condition.apply(criteria.getValorBaseFaturamento()) &&
                condition.apply(criteria.getValorBaseAbertura()) &&
                condition.apply(criteria.getSituacao()) &&
                condition.apply(criteria.getDescontoPeriodoPagamentoId()) &&
                condition.apply(criteria.getAdicionalRamoId()) &&
                condition.apply(criteria.getAdicionalTributacaoId()) &&
                condition.apply(criteria.getAdicionalEnquadramentoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PlanoAssinaturaContabilCriteria> copyFiltersAre(
        PlanoAssinaturaContabilCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getAdicionalSocio(), copy.getAdicionalSocio()) &&
                condition.apply(criteria.getAdicionalFuncionario(), copy.getAdicionalFuncionario()) &&
                condition.apply(criteria.getSociosIsentos(), copy.getSociosIsentos()) &&
                condition.apply(criteria.getAdicionalFaturamento(), copy.getAdicionalFaturamento()) &&
                condition.apply(criteria.getValorBaseFaturamento(), copy.getValorBaseFaturamento()) &&
                condition.apply(criteria.getValorBaseAbertura(), copy.getValorBaseAbertura()) &&
                condition.apply(criteria.getSituacao(), copy.getSituacao()) &&
                condition.apply(criteria.getDescontoPeriodoPagamentoId(), copy.getDescontoPeriodoPagamentoId()) &&
                condition.apply(criteria.getAdicionalRamoId(), copy.getAdicionalRamoId()) &&
                condition.apply(criteria.getAdicionalTributacaoId(), copy.getAdicionalTributacaoId()) &&
                condition.apply(criteria.getAdicionalEnquadramentoId(), copy.getAdicionalEnquadramentoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
