package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class CidadeCriteriaTest {

    @Test
    void newCidadeCriteriaHasAllFiltersNullTest() {
        var cidadeCriteria = new CidadeCriteria();
        assertThat(cidadeCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void cidadeCriteriaFluentMethodsCreatesFiltersTest() {
        var cidadeCriteria = new CidadeCriteria();

        setAllFilters(cidadeCriteria);

        assertThat(cidadeCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void cidadeCriteriaCopyCreatesNullFilterTest() {
        var cidadeCriteria = new CidadeCriteria();
        var copy = cidadeCriteria.copy();

        assertThat(cidadeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(cidadeCriteria)
        );
    }

    @Test
    void cidadeCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var cidadeCriteria = new CidadeCriteria();
        setAllFilters(cidadeCriteria);

        var copy = cidadeCriteria.copy();

        assertThat(cidadeCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(cidadeCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var cidadeCriteria = new CidadeCriteria();

        assertThat(cidadeCriteria).hasToString("CidadeCriteria{}");
    }

    private static void setAllFilters(CidadeCriteria cidadeCriteria) {
        cidadeCriteria.id();
        cidadeCriteria.nome();
        cidadeCriteria.contratacao();
        cidadeCriteria.abertura();
        cidadeCriteria.enderecoPessoaId();
        cidadeCriteria.enderecoEmpresaId();
        cidadeCriteria.estadoId();
        cidadeCriteria.distinct();
    }

    private static Condition<CidadeCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getContratacao()) &&
                condition.apply(criteria.getAbertura()) &&
                condition.apply(criteria.getEnderecoPessoaId()) &&
                condition.apply(criteria.getEnderecoEmpresaId()) &&
                condition.apply(criteria.getEstadoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<CidadeCriteria> copyFiltersAre(CidadeCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getContratacao(), copy.getContratacao()) &&
                condition.apply(criteria.getAbertura(), copy.getAbertura()) &&
                condition.apply(criteria.getEnderecoPessoaId(), copy.getEnderecoPessoaId()) &&
                condition.apply(criteria.getEnderecoEmpresaId(), copy.getEnderecoEmpresaId()) &&
                condition.apply(criteria.getEstadoId(), copy.getEstadoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
