package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CalculoPlanoAssinaturaCriteriaTest {

    @Test
    void newCalculoPlanoAssinaturaCriteriaHasAllFiltersNullTest() {
        var calculoPlanoAssinaturaCriteria = new CalculoPlanoAssinaturaCriteria();
        assertThat(calculoPlanoAssinaturaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void calculoPlanoAssinaturaCriteriaFluentMethodsCreatesFiltersTest() {
        var calculoPlanoAssinaturaCriteria = new CalculoPlanoAssinaturaCriteria();

        setAllFilters(calculoPlanoAssinaturaCriteria);

        assertThat(calculoPlanoAssinaturaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void calculoPlanoAssinaturaCriteriaCopyCreatesNullFilterTest() {
        var calculoPlanoAssinaturaCriteria = new CalculoPlanoAssinaturaCriteria();
        var copy = calculoPlanoAssinaturaCriteria.copy();

        assertThat(calculoPlanoAssinaturaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(calculoPlanoAssinaturaCriteria)
        );
    }

    @Test
    void calculoPlanoAssinaturaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var calculoPlanoAssinaturaCriteria = new CalculoPlanoAssinaturaCriteria();
        setAllFilters(calculoPlanoAssinaturaCriteria);

        var copy = calculoPlanoAssinaturaCriteria.copy();

        assertThat(calculoPlanoAssinaturaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(calculoPlanoAssinaturaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var calculoPlanoAssinaturaCriteria = new CalculoPlanoAssinaturaCriteria();

        assertThat(calculoPlanoAssinaturaCriteria).hasToString("CalculoPlanoAssinaturaCriteria{}");
    }

    private static void setAllFilters(CalculoPlanoAssinaturaCriteria calculoPlanoAssinaturaCriteria) {
        calculoPlanoAssinaturaCriteria.id();
        calculoPlanoAssinaturaCriteria.codigoAtendimento();
        calculoPlanoAssinaturaCriteria.valorEnquadramento();
        calculoPlanoAssinaturaCriteria.valorTributacao();
        calculoPlanoAssinaturaCriteria.valorRamo();
        calculoPlanoAssinaturaCriteria.valorFuncionarios();
        calculoPlanoAssinaturaCriteria.valorSocios();
        calculoPlanoAssinaturaCriteria.valorFaturamento();
        calculoPlanoAssinaturaCriteria.valorPlanoContabil();
        calculoPlanoAssinaturaCriteria.valorPlanoContabilComDesconto();
        calculoPlanoAssinaturaCriteria.valorMensalidade();
        calculoPlanoAssinaturaCriteria.valorPeriodo();
        calculoPlanoAssinaturaCriteria.valorAno();
        calculoPlanoAssinaturaCriteria.periodoPagamentoId();
        calculoPlanoAssinaturaCriteria.planoContabilId();
        calculoPlanoAssinaturaCriteria.ramoId();
        calculoPlanoAssinaturaCriteria.tributacaoId();
        calculoPlanoAssinaturaCriteria.descontoPlanoContabilId();
        calculoPlanoAssinaturaCriteria.assinaturaEmpresaId();
        calculoPlanoAssinaturaCriteria.distinct();
    }

    private static Condition<CalculoPlanoAssinaturaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCodigoAtendimento()) &&
                condition.apply(criteria.getValorEnquadramento()) &&
                condition.apply(criteria.getValorTributacao()) &&
                condition.apply(criteria.getValorRamo()) &&
                condition.apply(criteria.getValorFuncionarios()) &&
                condition.apply(criteria.getValorSocios()) &&
                condition.apply(criteria.getValorFaturamento()) &&
                condition.apply(criteria.getValorPlanoContabil()) &&
                condition.apply(criteria.getValorPlanoContabilComDesconto()) &&
                condition.apply(criteria.getValorMensalidade()) &&
                condition.apply(criteria.getValorPeriodo()) &&
                condition.apply(criteria.getValorAno()) &&
                condition.apply(criteria.getPeriodoPagamentoId()) &&
                condition.apply(criteria.getPlanoContabilId()) &&
                condition.apply(criteria.getRamoId()) &&
                condition.apply(criteria.getTributacaoId()) &&
                condition.apply(criteria.getDescontoPlanoContabilId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CalculoPlanoAssinaturaCriteria> copyFiltersAre(
        CalculoPlanoAssinaturaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCodigoAtendimento(), copy.getCodigoAtendimento()) &&
                condition.apply(criteria.getValorEnquadramento(), copy.getValorEnquadramento()) &&
                condition.apply(criteria.getValorTributacao(), copy.getValorTributacao()) &&
                condition.apply(criteria.getValorRamo(), copy.getValorRamo()) &&
                condition.apply(criteria.getValorFuncionarios(), copy.getValorFuncionarios()) &&
                condition.apply(criteria.getValorSocios(), copy.getValorSocios()) &&
                condition.apply(criteria.getValorFaturamento(), copy.getValorFaturamento()) &&
                condition.apply(criteria.getValorPlanoContabil(), copy.getValorPlanoContabil()) &&
                condition.apply(criteria.getValorPlanoContabilComDesconto(), copy.getValorPlanoContabilComDesconto()) &&
                condition.apply(criteria.getValorMensalidade(), copy.getValorMensalidade()) &&
                condition.apply(criteria.getValorPeriodo(), copy.getValorPeriodo()) &&
                condition.apply(criteria.getValorAno(), copy.getValorAno()) &&
                condition.apply(criteria.getPeriodoPagamentoId(), copy.getPeriodoPagamentoId()) &&
                condition.apply(criteria.getPlanoContabilId(), copy.getPlanoContabilId()) &&
                condition.apply(criteria.getRamoId(), copy.getRamoId()) &&
                condition.apply(criteria.getTributacaoId(), copy.getTributacaoId()) &&
                condition.apply(criteria.getDescontoPlanoContabilId(), copy.getDescontoPlanoContabilId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId(), copy.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
