package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class FuncionarioCriteriaTest {

    @Test
    void newFuncionarioCriteriaHasAllFiltersNullTest() {
        var funcionarioCriteria = new FuncionarioCriteria();
        assertThat(funcionarioCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void funcionarioCriteriaFluentMethodsCreatesFiltersTest() {
        var funcionarioCriteria = new FuncionarioCriteria();

        setAllFilters(funcionarioCriteria);

        assertThat(funcionarioCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void funcionarioCriteriaCopyCreatesNullFilterTest() {
        var funcionarioCriteria = new FuncionarioCriteria();
        var copy = funcionarioCriteria.copy();

        assertThat(funcionarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(funcionarioCriteria)
        );
    }

    @Test
    void funcionarioCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var funcionarioCriteria = new FuncionarioCriteria();
        setAllFilters(funcionarioCriteria);

        var copy = funcionarioCriteria.copy();

        assertThat(funcionarioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(funcionarioCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var funcionarioCriteria = new FuncionarioCriteria();

        assertThat(funcionarioCriteria).hasToString("FuncionarioCriteria{}");
    }

    private static void setAllFilters(FuncionarioCriteria funcionarioCriteria) {
        funcionarioCriteria.id();
        funcionarioCriteria.nome();
        funcionarioCriteria.salario();
        funcionarioCriteria.ctps();
        funcionarioCriteria.cargo();
        funcionarioCriteria.situacao();
        funcionarioCriteria.pessoaId();
        funcionarioCriteria.departamentoFuncionarioId();
        funcionarioCriteria.empresaId();
        funcionarioCriteria.distinct();
    }

    private static Condition<FuncionarioCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getSalario()) &&
                condition.apply(criteria.getCtps()) &&
                condition.apply(criteria.getCargo()) &&
                condition.apply(criteria.getSituacao()) &&
                condition.apply(criteria.getPessoaId()) &&
                condition.apply(criteria.getDepartamentoFuncionarioId()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FuncionarioCriteria> copyFiltersAre(FuncionarioCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getSalario(), copy.getSalario()) &&
                condition.apply(criteria.getCtps(), copy.getCtps()) &&
                condition.apply(criteria.getCargo(), copy.getCargo()) &&
                condition.apply(criteria.getSituacao(), copy.getSituacao()) &&
                condition.apply(criteria.getPessoaId(), copy.getPessoaId()) &&
                condition.apply(criteria.getDepartamentoFuncionarioId(), copy.getDepartamentoFuncionarioId()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
