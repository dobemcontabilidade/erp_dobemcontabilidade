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
        funcionarioCriteria.numeroPisNisPasep();
        funcionarioCriteria.reintegrado();
        funcionarioCriteria.primeiroEmprego();
        funcionarioCriteria.multiploVinculos();
        funcionarioCriteria.dataOpcaoFgts();
        funcionarioCriteria.filiacaoSindical();
        funcionarioCriteria.cnpjSindicato();
        funcionarioCriteria.tipoFuncionarioEnum();
        funcionarioCriteria.usuarioEmpresaId();
        funcionarioCriteria.estrangeiroId();
        funcionarioCriteria.contratoFuncionarioId();
        funcionarioCriteria.demissaoFuncionarioId();
        funcionarioCriteria.dependentesFuncionarioId();
        funcionarioCriteria.empresaVinculadaId();
        funcionarioCriteria.departamentoFuncionarioId();
        funcionarioCriteria.pessoaId();
        funcionarioCriteria.empresaId();
        funcionarioCriteria.profissaoId();
        funcionarioCriteria.distinct();
    }

    private static Condition<FuncionarioCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNumeroPisNisPasep()) &&
                condition.apply(criteria.getReintegrado()) &&
                condition.apply(criteria.getPrimeiroEmprego()) &&
                condition.apply(criteria.getMultiploVinculos()) &&
                condition.apply(criteria.getDataOpcaoFgts()) &&
                condition.apply(criteria.getFiliacaoSindical()) &&
                condition.apply(criteria.getCnpjSindicato()) &&
                condition.apply(criteria.getTipoFuncionarioEnum()) &&
                condition.apply(criteria.getUsuarioEmpresaId()) &&
                condition.apply(criteria.getEstrangeiroId()) &&
                condition.apply(criteria.getContratoFuncionarioId()) &&
                condition.apply(criteria.getDemissaoFuncionarioId()) &&
                condition.apply(criteria.getDependentesFuncionarioId()) &&
                condition.apply(criteria.getEmpresaVinculadaId()) &&
                condition.apply(criteria.getDepartamentoFuncionarioId()) &&
                condition.apply(criteria.getPessoaId()) &&
                condition.apply(criteria.getEmpresaId()) &&
                condition.apply(criteria.getProfissaoId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<FuncionarioCriteria> copyFiltersAre(FuncionarioCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNumeroPisNisPasep(), copy.getNumeroPisNisPasep()) &&
                condition.apply(criteria.getReintegrado(), copy.getReintegrado()) &&
                condition.apply(criteria.getPrimeiroEmprego(), copy.getPrimeiroEmprego()) &&
                condition.apply(criteria.getMultiploVinculos(), copy.getMultiploVinculos()) &&
                condition.apply(criteria.getDataOpcaoFgts(), copy.getDataOpcaoFgts()) &&
                condition.apply(criteria.getFiliacaoSindical(), copy.getFiliacaoSindical()) &&
                condition.apply(criteria.getCnpjSindicato(), copy.getCnpjSindicato()) &&
                condition.apply(criteria.getTipoFuncionarioEnum(), copy.getTipoFuncionarioEnum()) &&
                condition.apply(criteria.getUsuarioEmpresaId(), copy.getUsuarioEmpresaId()) &&
                condition.apply(criteria.getEstrangeiroId(), copy.getEstrangeiroId()) &&
                condition.apply(criteria.getContratoFuncionarioId(), copy.getContratoFuncionarioId()) &&
                condition.apply(criteria.getDemissaoFuncionarioId(), copy.getDemissaoFuncionarioId()) &&
                condition.apply(criteria.getDependentesFuncionarioId(), copy.getDependentesFuncionarioId()) &&
                condition.apply(criteria.getEmpresaVinculadaId(), copy.getEmpresaVinculadaId()) &&
                condition.apply(criteria.getDepartamentoFuncionarioId(), copy.getDepartamentoFuncionarioId()) &&
                condition.apply(criteria.getPessoaId(), copy.getPessoaId()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getProfissaoId(), copy.getProfissaoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
