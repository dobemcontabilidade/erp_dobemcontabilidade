package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EnderecoEmpresaCriteriaTest {

    @Test
    void newEnderecoEmpresaCriteriaHasAllFiltersNullTest() {
        var enderecoEmpresaCriteria = new EnderecoEmpresaCriteria();
        assertThat(enderecoEmpresaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void enderecoEmpresaCriteriaFluentMethodsCreatesFiltersTest() {
        var enderecoEmpresaCriteria = new EnderecoEmpresaCriteria();

        setAllFilters(enderecoEmpresaCriteria);

        assertThat(enderecoEmpresaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void enderecoEmpresaCriteriaCopyCreatesNullFilterTest() {
        var enderecoEmpresaCriteria = new EnderecoEmpresaCriteria();
        var copy = enderecoEmpresaCriteria.copy();

        assertThat(enderecoEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(enderecoEmpresaCriteria)
        );
    }

    @Test
    void enderecoEmpresaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var enderecoEmpresaCriteria = new EnderecoEmpresaCriteria();
        setAllFilters(enderecoEmpresaCriteria);

        var copy = enderecoEmpresaCriteria.copy();

        assertThat(enderecoEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(enderecoEmpresaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var enderecoEmpresaCriteria = new EnderecoEmpresaCriteria();

        assertThat(enderecoEmpresaCriteria).hasToString("EnderecoEmpresaCriteria{}");
    }

    private static void setAllFilters(EnderecoEmpresaCriteria enderecoEmpresaCriteria) {
        enderecoEmpresaCriteria.id();
        enderecoEmpresaCriteria.logradouro();
        enderecoEmpresaCriteria.numero();
        enderecoEmpresaCriteria.complemento();
        enderecoEmpresaCriteria.bairro();
        enderecoEmpresaCriteria.cep();
        enderecoEmpresaCriteria.principal();
        enderecoEmpresaCriteria.filial();
        enderecoEmpresaCriteria.enderecoFiscal();
        enderecoEmpresaCriteria.empresaId();
        enderecoEmpresaCriteria.cidadeId();
        enderecoEmpresaCriteria.distinct();
    }

    private static Condition<EnderecoEmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLogradouro()) &&
                condition.apply(criteria.getNumero()) &&
                condition.apply(criteria.getComplemento()) &&
                condition.apply(criteria.getBairro()) &&
                condition.apply(criteria.getCep()) &&
                condition.apply(criteria.getPrincipal()) &&
                condition.apply(criteria.getFilial()) &&
                condition.apply(criteria.getEnderecoFiscal()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getCidadeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EnderecoEmpresaCriteria> copyFiltersAre(
        EnderecoEmpresaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLogradouro(), copy.getLogradouro()) &&
                condition.apply(criteria.getNumero(), copy.getNumero()) &&
                condition.apply(criteria.getComplemento(), copy.getComplemento()) &&
                condition.apply(criteria.getBairro(), copy.getBairro()) &&
                condition.apply(criteria.getCep(), copy.getCep()) &&
                condition.apply(criteria.getPrincipal(), copy.getPrincipal()) &&
                condition.apply(criteria.getFilial(), copy.getFilial()) &&
                condition.apply(criteria.getEnderecoFiscal(), copy.getEnderecoFiscal()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getCidadeId(), copy.getCidadeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
