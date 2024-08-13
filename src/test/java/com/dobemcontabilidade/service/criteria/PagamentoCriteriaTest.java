package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PagamentoCriteriaTest {

    @Test
    void newPagamentoCriteriaHasAllFiltersNullTest() {
        var pagamentoCriteria = new PagamentoCriteria();
        assertThat(pagamentoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void pagamentoCriteriaFluentMethodsCreatesFiltersTest() {
        var pagamentoCriteria = new PagamentoCriteria();

        setAllFilters(pagamentoCriteria);

        assertThat(pagamentoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void pagamentoCriteriaCopyCreatesNullFilterTest() {
        var pagamentoCriteria = new PagamentoCriteria();
        var copy = pagamentoCriteria.copy();

        assertThat(pagamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(pagamentoCriteria)
        );
    }

    @Test
    void pagamentoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var pagamentoCriteria = new PagamentoCriteria();
        setAllFilters(pagamentoCriteria);

        var copy = pagamentoCriteria.copy();

        assertThat(pagamentoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(pagamentoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var pagamentoCriteria = new PagamentoCriteria();

        assertThat(pagamentoCriteria).hasToString("PagamentoCriteria{}");
    }

    private static void setAllFilters(PagamentoCriteria pagamentoCriteria) {
        pagamentoCriteria.id();
        pagamentoCriteria.dataCobranca();
        pagamentoCriteria.dataVencimento();
        pagamentoCriteria.dataPagamento();
        pagamentoCriteria.valorPago();
        pagamentoCriteria.valorCobrado();
        pagamentoCriteria.acrescimo();
        pagamentoCriteria.multa();
        pagamentoCriteria.juros();
        pagamentoCriteria.situacao();
        pagamentoCriteria.assinaturaEmpresaId();
        pagamentoCriteria.distinct();
    }

    private static Condition<PagamentoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getDataCobranca()) &&
                condition.apply(criteria.getDataVencimento()) &&
                condition.apply(criteria.getDataPagamento()) &&
                condition.apply(criteria.getValorPago()) &&
                condition.apply(criteria.getValorCobrado()) &&
                condition.apply(criteria.getAcrescimo()) &&
                condition.apply(criteria.getMulta()) &&
                condition.apply(criteria.getJuros()) &&
                condition.apply(criteria.getSituacao()) &&
                condition.apply(criteria.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PagamentoCriteria> copyFiltersAre(PagamentoCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getDataCobranca(), copy.getDataCobranca()) &&
                condition.apply(criteria.getDataVencimento(), copy.getDataVencimento()) &&
                condition.apply(criteria.getDataPagamento(), copy.getDataPagamento()) &&
                condition.apply(criteria.getValorPago(), copy.getValorPago()) &&
                condition.apply(criteria.getValorCobrado(), copy.getValorCobrado()) &&
                condition.apply(criteria.getAcrescimo(), copy.getAcrescimo()) &&
                condition.apply(criteria.getMulta(), copy.getMulta()) &&
                condition.apply(criteria.getJuros(), copy.getJuros()) &&
                condition.apply(criteria.getSituacao(), copy.getSituacao()) &&
                condition.apply(criteria.getAssinaturaEmpresaId(), copy.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
