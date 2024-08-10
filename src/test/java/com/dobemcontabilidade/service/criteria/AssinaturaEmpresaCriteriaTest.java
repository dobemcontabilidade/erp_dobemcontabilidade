package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AssinaturaEmpresaCriteriaTest {

    @Test
    void newAssinaturaEmpresaCriteriaHasAllFiltersNullTest() {
        var assinaturaEmpresaCriteria = new AssinaturaEmpresaCriteria();
        assertThat(assinaturaEmpresaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void assinaturaEmpresaCriteriaFluentMethodsCreatesFiltersTest() {
        var assinaturaEmpresaCriteria = new AssinaturaEmpresaCriteria();

        setAllFilters(assinaturaEmpresaCriteria);

        assertThat(assinaturaEmpresaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void assinaturaEmpresaCriteriaCopyCreatesNullFilterTest() {
        var assinaturaEmpresaCriteria = new AssinaturaEmpresaCriteria();
        var copy = assinaturaEmpresaCriteria.copy();

        assertThat(assinaturaEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(assinaturaEmpresaCriteria)
        );
    }

    @Test
    void assinaturaEmpresaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var assinaturaEmpresaCriteria = new AssinaturaEmpresaCriteria();
        setAllFilters(assinaturaEmpresaCriteria);

        var copy = assinaturaEmpresaCriteria.copy();

        assertThat(assinaturaEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(assinaturaEmpresaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var assinaturaEmpresaCriteria = new AssinaturaEmpresaCriteria();

        assertThat(assinaturaEmpresaCriteria).hasToString("AssinaturaEmpresaCriteria{}");
    }

    private static void setAllFilters(AssinaturaEmpresaCriteria assinaturaEmpresaCriteria) {
        assinaturaEmpresaCriteria.id();
        assinaturaEmpresaCriteria.codigoAssinatura();
        assinaturaEmpresaCriteria.valorEnquadramento();
        assinaturaEmpresaCriteria.valorTributacao();
        assinaturaEmpresaCriteria.valorRamo();
        assinaturaEmpresaCriteria.valorFuncionarios();
        assinaturaEmpresaCriteria.valorSocios();
        assinaturaEmpresaCriteria.valorFaturamento();
        assinaturaEmpresaCriteria.valorPlanoContabil();
        assinaturaEmpresaCriteria.valorPlanoContabilComDesconto();
        assinaturaEmpresaCriteria.valorMensalidade();
        assinaturaEmpresaCriteria.valorPeriodo();
        assinaturaEmpresaCriteria.valorAno();
        assinaturaEmpresaCriteria.dataContratacao();
        assinaturaEmpresaCriteria.dataEncerramento();
        assinaturaEmpresaCriteria.diaVencimento();
        assinaturaEmpresaCriteria.situacao();
        assinaturaEmpresaCriteria.tipoContrato();
        assinaturaEmpresaCriteria.calculoPlanoAssinaturaId();
        assinaturaEmpresaCriteria.pagamentoId();
        assinaturaEmpresaCriteria.periodoPagamentoId();
        assinaturaEmpresaCriteria.formaDePagamentoId();
        assinaturaEmpresaCriteria.planoContabilId();
        assinaturaEmpresaCriteria.empresaId();
        assinaturaEmpresaCriteria.planoContaAzulId();
        assinaturaEmpresaCriteria.distinct();
    }

    private static Condition<AssinaturaEmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCodigoAssinatura()) &&
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
                condition.apply(criteria.getDataContratacao()) &&
                condition.apply(criteria.getDataEncerramento()) &&
                condition.apply(criteria.getDiaVencimento()) &&
                condition.apply(criteria.getSituacao()) &&
                condition.apply(criteria.getTipoContrato()) &&
                condition.apply(criteria.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getPagamentoId()) &&
                condition.apply(criteria.getPeriodoPagamentoId()) &&
                condition.apply(criteria.getFormaDePagamentoId()) &&
                condition.apply(criteria.getPlanoContabilId()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getPlanoContaAzulId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AssinaturaEmpresaCriteria> copyFiltersAre(
        AssinaturaEmpresaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCodigoAssinatura(), copy.getCodigoAssinatura()) &&
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
                condition.apply(criteria.getDataContratacao(), copy.getDataContratacao()) &&
                condition.apply(criteria.getDataEncerramento(), copy.getDataEncerramento()) &&
                condition.apply(criteria.getDiaVencimento(), copy.getDiaVencimento()) &&
                condition.apply(criteria.getSituacao(), copy.getSituacao()) &&
                condition.apply(criteria.getTipoContrato(), copy.getTipoContrato()) &&
                condition.apply(criteria.getCalculoPlanoAssinaturaId(), copy.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getPagamentoId(), copy.getPagamentoId()) &&
                condition.apply(criteria.getPeriodoPagamentoId(), copy.getPeriodoPagamentoId()) &&
                condition.apply(criteria.getFormaDePagamentoId(), copy.getFormaDePagamentoId()) &&
                condition.apply(criteria.getPlanoContabilId(), copy.getPlanoContabilId()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getPlanoContaAzulId(), copy.getPlanoContaAzulId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
