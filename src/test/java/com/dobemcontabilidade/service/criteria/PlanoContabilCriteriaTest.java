package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PlanoContabilCriteriaTest {

    @Test
    void newPlanoContabilCriteriaHasAllFiltersNullTest() {
        var planoContabilCriteria = new PlanoContabilCriteria();
        assertThat(planoContabilCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void planoContabilCriteriaFluentMethodsCreatesFiltersTest() {
        var planoContabilCriteria = new PlanoContabilCriteria();

        setAllFilters(planoContabilCriteria);

        assertThat(planoContabilCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void planoContabilCriteriaCopyCreatesNullFilterTest() {
        var planoContabilCriteria = new PlanoContabilCriteria();
        var copy = planoContabilCriteria.copy();

        assertThat(planoContabilCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(planoContabilCriteria)
        );
    }

    @Test
    void planoContabilCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var planoContabilCriteria = new PlanoContabilCriteria();
        setAllFilters(planoContabilCriteria);

        var copy = planoContabilCriteria.copy();

        assertThat(planoContabilCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(planoContabilCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var planoContabilCriteria = new PlanoContabilCriteria();

        assertThat(planoContabilCriteria).hasToString("PlanoContabilCriteria{}");
    }

    private static void setAllFilters(PlanoContabilCriteria planoContabilCriteria) {
        planoContabilCriteria.id();
        planoContabilCriteria.nome();
        planoContabilCriteria.adicionalSocio();
        planoContabilCriteria.adicionalFuncionario();
        planoContabilCriteria.sociosIsentos();
        planoContabilCriteria.adicionalFaturamento();
        planoContabilCriteria.valorBaseFaturamento();
        planoContabilCriteria.valorBaseAbertura();
        planoContabilCriteria.situacao();
        planoContabilCriteria.calculoPlanoAssinaturaId();
        planoContabilCriteria.assinaturaEmpresaId();
        planoContabilCriteria.descontoPlanoContabilId();
        planoContabilCriteria.adicionalRamoId();
        planoContabilCriteria.adicionalTributacaoId();
        planoContabilCriteria.termoContratoContabilId();
        planoContabilCriteria.adicionalEnquadramentoId();
        planoContabilCriteria.valorBaseRamoId();
        planoContabilCriteria.distinct();
    }

    private static Condition<PlanoContabilCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
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
                condition.apply(criteria.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDescontoPlanoContabilId()) &&
                condition.apply(criteria.getAdicionalRamoId()) &&
                condition.apply(criteria.getAdicionalTributacaoId()) &&
                condition.apply(criteria.getTermoContratoContabilId()) &&
                condition.apply(criteria.getAdicionalEnquadramentoId()) &&
                condition.apply(criteria.getValorBaseRamoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PlanoContabilCriteria> copyFiltersAre(
        PlanoContabilCriteria copy,
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
                condition.apply(criteria.getCalculoPlanoAssinaturaId(), copy.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId(), copy.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDescontoPlanoContabilId(), copy.getDescontoPlanoContabilId()) &&
                condition.apply(criteria.getAdicionalRamoId(), copy.getAdicionalRamoId()) &&
                condition.apply(criteria.getAdicionalTributacaoId(), copy.getAdicionalTributacaoId()) &&
                condition.apply(criteria.getTermoContratoContabilId(), copy.getTermoContratoContabilId()) &&
                condition.apply(criteria.getAdicionalEnquadramentoId(), copy.getAdicionalEnquadramentoId()) &&
                condition.apply(criteria.getValorBaseRamoId(), copy.getValorBaseRamoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
