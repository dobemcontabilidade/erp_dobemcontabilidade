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
        empresaCriteria.nomeFantasia();
        empresaCriteria.cnpj();
        empresaCriteria.dataAbertura();
        empresaCriteria.urlContratoSocial();
        empresaCriteria.capitalSocial();
        empresaCriteria.assinaturaEmpresaId();
        empresaCriteria.funcionarioId();
        empresaCriteria.departamentoEmpresaId();
        empresaCriteria.tarefaEmpresaId();
        empresaCriteria.enderecoEmpresaId();
        empresaCriteria.atividadeEmpresaId();
        empresaCriteria.socioId();
        empresaCriteria.anexoEmpresaId();
        empresaCriteria.certificadoDigitalId();
        empresaCriteria.usuarioEmpresaId();
        empresaCriteria.opcaoRazaoSocialEmpresaId();
        empresaCriteria.opcaoNomeFantasiaEmpresaId();
        empresaCriteria.ramoId();
        empresaCriteria.tributacaoId();
        empresaCriteria.enquadramentoId();
        empresaCriteria.distinct();
    }

    private static Condition<EmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getRazaoSocial()) &&
                condition.apply(criteria.getNomeFantasia()) &&
                condition.apply(criteria.getCnpj()) &&
                condition.apply(criteria.getDataAbertura()) &&
                condition.apply(criteria.getUrlContratoSocial()) &&
                condition.apply(criteria.getCapitalSocial()) &&
                condition.apply(criteria.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getFuncionarioId()) &&
                condition.apply(criteria.getDepartamentoEmpresaId()) &&
                condition.apply(criteria.getTarefaEmpresaId()) &&
                condition.apply(criteria.getEnderecoEmpresaId()) &&
                condition.apply(criteria.getAtividadeEmpresaId()) &&
                condition.apply(criteria.getSocioId()) &&
                condition.apply(criteria.getAnexoEmpresaId()) &&
                condition.apply(criteria.getCertificadoDigitalId()) &&
                condition.apply(criteria.getUsuarioEmpresaId()) &&
                condition.apply(criteria.getOpcaoRazaoSocialEmpresaId()) &&
                condition.apply(criteria.getOpcaoNomeFantasiaEmpresaId()) &&
                condition.apply(criteria.getRamoId()) &&
                condition.apply(criteria.getTributacaoId()) &&
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
                condition.apply(criteria.getNomeFantasia(), copy.getNomeFantasia()) &&
                condition.apply(criteria.getCnpj(), copy.getCnpj()) &&
                condition.apply(criteria.getDataAbertura(), copy.getDataAbertura()) &&
                condition.apply(criteria.getUrlContratoSocial(), copy.getUrlContratoSocial()) &&
                condition.apply(criteria.getCapitalSocial(), copy.getCapitalSocial()) &&
                condition.apply(criteria.getAssinaturaEmpresaId(), copy.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getFuncionarioId(), copy.getFuncionarioId()) &&
                condition.apply(criteria.getDepartamentoEmpresaId(), copy.getDepartamentoEmpresaId()) &&
                condition.apply(criteria.getTarefaEmpresaId(), copy.getTarefaEmpresaId()) &&
                condition.apply(criteria.getEnderecoEmpresaId(), copy.getEnderecoEmpresaId()) &&
                condition.apply(criteria.getAtividadeEmpresaId(), copy.getAtividadeEmpresaId()) &&
                condition.apply(criteria.getSocioId(), copy.getSocioId()) &&
                condition.apply(criteria.getAnexoEmpresaId(), copy.getAnexoEmpresaId()) &&
                condition.apply(criteria.getCertificadoDigitalId(), copy.getCertificadoDigitalId()) &&
                condition.apply(criteria.getUsuarioEmpresaId(), copy.getUsuarioEmpresaId()) &&
                condition.apply(criteria.getOpcaoRazaoSocialEmpresaId(), copy.getOpcaoRazaoSocialEmpresaId()) &&
                condition.apply(criteria.getOpcaoNomeFantasiaEmpresaId(), copy.getOpcaoNomeFantasiaEmpresaId()) &&
                condition.apply(criteria.getRamoId(), copy.getRamoId()) &&
                condition.apply(criteria.getTributacaoId(), copy.getTributacaoId()) &&
                condition.apply(criteria.getEnquadramentoId(), copy.getEnquadramentoId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
