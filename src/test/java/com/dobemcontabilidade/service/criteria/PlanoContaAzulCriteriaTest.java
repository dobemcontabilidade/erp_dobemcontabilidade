package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PlanoContaAzulCriteriaTest {

    @Test
    void newPlanoContaAzulCriteriaHasAllFiltersNullTest() {
        var planoContaAzulCriteria = new PlanoContaAzulCriteria();
        assertThat(planoContaAzulCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void planoContaAzulCriteriaFluentMethodsCreatesFiltersTest() {
        var planoContaAzulCriteria = new PlanoContaAzulCriteria();

        setAllFilters(planoContaAzulCriteria);

        assertThat(planoContaAzulCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void planoContaAzulCriteriaCopyCreatesNullFilterTest() {
        var planoContaAzulCriteria = new PlanoContaAzulCriteria();
        var copy = planoContaAzulCriteria.copy();

        assertThat(planoContaAzulCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(planoContaAzulCriteria)
        );
    }

    @Test
    void planoContaAzulCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var planoContaAzulCriteria = new PlanoContaAzulCriteria();
        setAllFilters(planoContaAzulCriteria);

        var copy = planoContaAzulCriteria.copy();

        assertThat(planoContaAzulCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(planoContaAzulCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var planoContaAzulCriteria = new PlanoContaAzulCriteria();

        assertThat(planoContaAzulCriteria).hasToString("PlanoContaAzulCriteria{}");
    }

    private static void setAllFilters(PlanoContaAzulCriteria planoContaAzulCriteria) {
        planoContaAzulCriteria.id();
        planoContaAzulCriteria.nome();
        planoContaAzulCriteria.valorBase();
        planoContaAzulCriteria.usuarios();
        planoContaAzulCriteria.boletos();
        planoContaAzulCriteria.notaFiscalProduto();
        planoContaAzulCriteria.notaFiscalServico();
        planoContaAzulCriteria.notaFiscalCe();
        planoContaAzulCriteria.suporte();
        planoContaAzulCriteria.situacao();
        planoContaAzulCriteria.calculoPlanoAssinaturaId();
        planoContaAzulCriteria.assinaturaEmpresaId();
        planoContaAzulCriteria.descontoPlanoContaAzulId();
        planoContaAzulCriteria.distinct();
    }

    private static Condition<PlanoContaAzulCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getValorBase()) &&
                condition.apply(criteria.getUsuarios()) &&
                condition.apply(criteria.getBoletos()) &&
                condition.apply(criteria.getNotaFiscalProduto()) &&
                condition.apply(criteria.getNotaFiscalServico()) &&
                condition.apply(criteria.getNotaFiscalCe()) &&
                condition.apply(criteria.getSuporte()) &&
                condition.apply(criteria.getSituacao()) &&
                condition.apply(criteria.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDescontoPlanoContaAzulId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PlanoContaAzulCriteria> copyFiltersAre(
        PlanoContaAzulCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getValorBase(), copy.getValorBase()) &&
                condition.apply(criteria.getUsuarios(), copy.getUsuarios()) &&
                condition.apply(criteria.getBoletos(), copy.getBoletos()) &&
                condition.apply(criteria.getNotaFiscalProduto(), copy.getNotaFiscalProduto()) &&
                condition.apply(criteria.getNotaFiscalServico(), copy.getNotaFiscalServico()) &&
                condition.apply(criteria.getNotaFiscalCe(), copy.getNotaFiscalCe()) &&
                condition.apply(criteria.getSuporte(), copy.getSuporte()) &&
                condition.apply(criteria.getSituacao(), copy.getSituacao()) &&
                condition.apply(criteria.getCalculoPlanoAssinaturaId(), copy.getCalculoPlanoAssinaturaId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId(), copy.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDescontoPlanoContaAzulId(), copy.getDescontoPlanoContaAzulId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
