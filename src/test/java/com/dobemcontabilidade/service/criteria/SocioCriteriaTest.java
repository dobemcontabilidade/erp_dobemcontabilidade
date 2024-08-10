package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SocioCriteriaTest {

    @Test
    void newSocioCriteriaHasAllFiltersNullTest() {
        var socioCriteria = new SocioCriteria();
        assertThat(socioCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void socioCriteriaFluentMethodsCreatesFiltersTest() {
        var socioCriteria = new SocioCriteria();

        setAllFilters(socioCriteria);

        assertThat(socioCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void socioCriteriaCopyCreatesNullFilterTest() {
        var socioCriteria = new SocioCriteria();
        var copy = socioCriteria.copy();

        assertThat(socioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(socioCriteria)
        );
    }

    @Test
    void socioCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var socioCriteria = new SocioCriteria();
        setAllFilters(socioCriteria);

        var copy = socioCriteria.copy();

        assertThat(socioCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(socioCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var socioCriteria = new SocioCriteria();

        assertThat(socioCriteria).hasToString("SocioCriteria{}");
    }

    private static void setAllFilters(SocioCriteria socioCriteria) {
        socioCriteria.id();
        socioCriteria.nome();
        socioCriteria.prolabore();
        socioCriteria.percentualSociedade();
        socioCriteria.adminstrador();
        socioCriteria.distribuicaoLucro();
        socioCriteria.responsavelReceita();
        socioCriteria.percentualDistribuicaoLucro();
        socioCriteria.funcaoSocio();
        socioCriteria.pessoaId();
        socioCriteria.profissaoId();
        socioCriteria.empresaId();
        socioCriteria.distinct();
    }

    private static Condition<SocioCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getProlabore()) &&
                condition.apply(criteria.getPercentualSociedade()) &&
                condition.apply(criteria.getAdminstrador()) &&
                condition.apply(criteria.getDistribuicaoLucro()) &&
                condition.apply(criteria.getResponsavelReceita()) &&
                condition.apply(criteria.getPercentualDistribuicaoLucro()) &&
                condition.apply(criteria.getFuncaoSocio()) &&
                condition.apply(criteria.getPessoaId()) &&
                condition.apply(criteria.getProfissaoId()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SocioCriteria> copyFiltersAre(SocioCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getProlabore(), copy.getProlabore()) &&
                condition.apply(criteria.getPercentualSociedade(), copy.getPercentualSociedade()) &&
                condition.apply(criteria.getAdminstrador(), copy.getAdminstrador()) &&
                condition.apply(criteria.getDistribuicaoLucro(), copy.getDistribuicaoLucro()) &&
                condition.apply(criteria.getResponsavelReceita(), copy.getResponsavelReceita()) &&
                condition.apply(criteria.getPercentualDistribuicaoLucro(), copy.getPercentualDistribuicaoLucro()) &&
                condition.apply(criteria.getFuncaoSocio(), copy.getFuncaoSocio()) &&
                condition.apply(criteria.getPessoaId(), copy.getPessoaId()) &&
                condition.apply(criteria.getProfissaoId(), copy.getProfissaoId()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
