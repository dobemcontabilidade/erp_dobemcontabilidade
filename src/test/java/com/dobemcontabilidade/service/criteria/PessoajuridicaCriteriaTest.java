package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PessoajuridicaCriteriaTest {

    @Test
    void newPessoajuridicaCriteriaHasAllFiltersNullTest() {
        var pessoajuridicaCriteria = new PessoajuridicaCriteria();
        assertThat(pessoajuridicaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void pessoajuridicaCriteriaFluentMethodsCreatesFiltersTest() {
        var pessoajuridicaCriteria = new PessoajuridicaCriteria();

        setAllFilters(pessoajuridicaCriteria);

        assertThat(pessoajuridicaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void pessoajuridicaCriteriaCopyCreatesNullFilterTest() {
        var pessoajuridicaCriteria = new PessoajuridicaCriteria();
        var copy = pessoajuridicaCriteria.copy();

        assertThat(pessoajuridicaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(pessoajuridicaCriteria)
        );
    }

    @Test
    void pessoajuridicaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var pessoajuridicaCriteria = new PessoajuridicaCriteria();
        setAllFilters(pessoajuridicaCriteria);

        var copy = pessoajuridicaCriteria.copy();

        assertThat(pessoajuridicaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(pessoajuridicaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var pessoajuridicaCriteria = new PessoajuridicaCriteria();

        assertThat(pessoajuridicaCriteria).hasToString("PessoajuridicaCriteria{}");
    }

    private static void setAllFilters(PessoajuridicaCriteria pessoajuridicaCriteria) {
        pessoajuridicaCriteria.id();
        pessoajuridicaCriteria.razaoSocial();
        pessoajuridicaCriteria.nomeFantasia();
        pessoajuridicaCriteria.cnpj();
        pessoajuridicaCriteria.redeSocialEmpresaId();
        pessoajuridicaCriteria.certificadoDigitalEmpresaId();
        pessoajuridicaCriteria.docsEmpresaId();
        pessoajuridicaCriteria.enderecoEmpresaId();
        pessoajuridicaCriteria.empresaId();
        pessoajuridicaCriteria.distinct();
    }

    private static Condition<PessoajuridicaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRazaoSocial()) &&
                condition.apply(criteria.getNomeFantasia()) &&
                condition.apply(criteria.getCnpj()) &&
                condition.apply(criteria.getRedeSocialEmpresaId()) &&
                condition.apply(criteria.getCertificadoDigitalEmpresaId()) &&
                condition.apply(criteria.getDocsEmpresaId()) &&
                condition.apply(criteria.getEnderecoEmpresaId()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PessoajuridicaCriteria> copyFiltersAre(
        PessoajuridicaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getRazaoSocial(), copy.getRazaoSocial()) &&
                condition.apply(criteria.getNomeFantasia(), copy.getNomeFantasia()) &&
                condition.apply(criteria.getCnpj(), copy.getCnpj()) &&
                condition.apply(criteria.getRedeSocialEmpresaId(), copy.getRedeSocialEmpresaId()) &&
                condition.apply(criteria.getCertificadoDigitalEmpresaId(), copy.getCertificadoDigitalEmpresaId()) &&
                condition.apply(criteria.getDocsEmpresaId(), copy.getDocsEmpresaId()) &&
                condition.apply(criteria.getEnderecoEmpresaId(), copy.getEnderecoEmpresaId()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
