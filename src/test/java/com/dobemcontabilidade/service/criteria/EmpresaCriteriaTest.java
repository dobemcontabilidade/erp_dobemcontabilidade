package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class EmpresaCriteriaTest {

    @Test
    void newEmpresaCriteriaHasAllFiltersNullTest() {
        var empresaCriteria = new EmpresaCriteria();
        assertThat(empresaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void empresaCriteriaFluentMethodsCreatesFiltersTest() {
        var empresaCriteria = new EmpresaCriteria();

        setAllFilters(empresaCriteria);

        assertThat(empresaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void empresaCriteriaCopyCreatesNullFilterTest() {
        var empresaCriteria = new EmpresaCriteria();
        var copy = empresaCriteria.copy();

        assertThat(empresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(empresaCriteria)
        );
    }

    @Test
    void empresaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var empresaCriteria = new EmpresaCriteria();
        setAllFilters(empresaCriteria);

        var copy = empresaCriteria.copy();

        assertThat(empresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(empresaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var empresaCriteria = new EmpresaCriteria();

        assertThat(empresaCriteria).hasToString("EmpresaCriteria{}");
    }

    private static void setAllFilters(EmpresaCriteria empresaCriteria) {
        empresaCriteria.id();
        empresaCriteria.razaoSocial();
        empresaCriteria.dataAbertura();
        empresaCriteria.urlContratoSocial();
        empresaCriteria.capitalSocial();
        empresaCriteria.cnae();
        empresaCriteria.pessoaJuridicaId();
        empresaCriteria.socioId();
        empresaCriteria.assinaturaEmpresaId();
        empresaCriteria.tributacaoId();
        empresaCriteria.ramoId();
        empresaCriteria.enquadramentoId();
        empresaCriteria.distinct();
    }

    private static Condition<EmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRazaoSocial()) &&
                condition.apply(criteria.getDataAbertura()) &&
                condition.apply(criteria.getUrlContratoSocial()) &&
                condition.apply(criteria.getCapitalSocial()) &&
                condition.apply(criteria.getCnae()) &&
                condition.apply(criteria.getPessoaJuridicaId()) &&
                condition.apply(criteria.getSocioId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getTributacaoId()) &&
                condition.apply(criteria.getRamoId()) &&
                condition.apply(criteria.getEnquadramentoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<EmpresaCriteria> copyFiltersAre(EmpresaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRazaoSocial(), copy.getRazaoSocial()) &&
                condition.apply(criteria.getDataAbertura(), copy.getDataAbertura()) &&
                condition.apply(criteria.getUrlContratoSocial(), copy.getUrlContratoSocial()) &&
                condition.apply(criteria.getCapitalSocial(), copy.getCapitalSocial()) &&
                condition.apply(criteria.getCnae(), copy.getCnae()) &&
                condition.apply(criteria.getPessoaJuridicaId(), copy.getPessoaJuridicaId()) &&
                condition.apply(criteria.getSocioId(), copy.getSocioId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId(), copy.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getTributacaoId(), copy.getTributacaoId()) &&
                condition.apply(criteria.getRamoId(), copy.getRamoId()) &&
                condition.apply(criteria.getEnquadramentoId(), copy.getEnquadramentoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
