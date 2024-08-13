package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EnderecoPessoaCriteriaTest {

    @Test
    void newEnderecoPessoaCriteriaHasAllFiltersNullTest() {
        var enderecoPessoaCriteria = new EnderecoPessoaCriteria();
        assertThat(enderecoPessoaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void enderecoPessoaCriteriaFluentMethodsCreatesFiltersTest() {
        var enderecoPessoaCriteria = new EnderecoPessoaCriteria();

        setAllFilters(enderecoPessoaCriteria);

        assertThat(enderecoPessoaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void enderecoPessoaCriteriaCopyCreatesNullFilterTest() {
        var enderecoPessoaCriteria = new EnderecoPessoaCriteria();
        var copy = enderecoPessoaCriteria.copy();

        assertThat(enderecoPessoaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(enderecoPessoaCriteria)
        );
    }

    @Test
    void enderecoPessoaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var enderecoPessoaCriteria = new EnderecoPessoaCriteria();
        setAllFilters(enderecoPessoaCriteria);

        var copy = enderecoPessoaCriteria.copy();

        assertThat(enderecoPessoaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(enderecoPessoaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var enderecoPessoaCriteria = new EnderecoPessoaCriteria();

        assertThat(enderecoPessoaCriteria).hasToString("EnderecoPessoaCriteria{}");
    }

    private static void setAllFilters(EnderecoPessoaCriteria enderecoPessoaCriteria) {
        enderecoPessoaCriteria.id();
        enderecoPessoaCriteria.logradouro();
        enderecoPessoaCriteria.numero();
        enderecoPessoaCriteria.complemento();
        enderecoPessoaCriteria.bairro();
        enderecoPessoaCriteria.cep();
        enderecoPessoaCriteria.principal();
        enderecoPessoaCriteria.residenciaPropria();
        enderecoPessoaCriteria.pessoaId();
        enderecoPessoaCriteria.cidadeId();
        enderecoPessoaCriteria.distinct();
    }

    private static Condition<EnderecoPessoaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLogradouro()) &&
                condition.apply(criteria.getNumero()) &&
                condition.apply(criteria.getComplemento()) &&
                condition.apply(criteria.getBairro()) &&
                condition.apply(criteria.getCep()) &&
                condition.apply(criteria.getPrincipal()) &&
                condition.apply(criteria.getResidenciaPropria()) &&
                condition.apply(criteria.getPessoaId()) &&
                condition.apply(criteria.getCidadeId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EnderecoPessoaCriteria> copyFiltersAre(
        EnderecoPessoaCriteria copy,
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
                condition.apply(criteria.getResidenciaPropria(), copy.getResidenciaPropria()) &&
                condition.apply(criteria.getPessoaId(), copy.getPessoaId()) &&
                condition.apply(criteria.getCidadeId(), copy.getCidadeId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
