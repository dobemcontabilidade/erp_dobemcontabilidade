package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class PessoaCriteriaTest {

    @Test
    void newPessoaCriteriaHasAllFiltersNullTest() {
        var pessoaCriteria = new PessoaCriteria();
        assertThat(pessoaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void pessoaCriteriaFluentMethodsCreatesFiltersTest() {
        var pessoaCriteria = new PessoaCriteria();

        setAllFilters(pessoaCriteria);

        assertThat(pessoaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void pessoaCriteriaCopyCreatesNullFilterTest() {
        var pessoaCriteria = new PessoaCriteria();
        var copy = pessoaCriteria.copy();

        assertThat(pessoaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(pessoaCriteria)
        );
    }

    @Test
    void pessoaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var pessoaCriteria = new PessoaCriteria();
        setAllFilters(pessoaCriteria);

        var copy = pessoaCriteria.copy();

        assertThat(pessoaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(pessoaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var pessoaCriteria = new PessoaCriteria();

        assertThat(pessoaCriteria).hasToString("PessoaCriteria{}");
    }

    private static void setAllFilters(PessoaCriteria pessoaCriteria) {
        pessoaCriteria.id();
        pessoaCriteria.nome();
        pessoaCriteria.cpf();
        pessoaCriteria.dataNascimento();
        pessoaCriteria.tituloEleitor();
        pessoaCriteria.rg();
        pessoaCriteria.rgOrgaoExpditor();
        pessoaCriteria.rgUfExpedicao();
        pessoaCriteria.estadoCivil();
        pessoaCriteria.sexo();
        pessoaCriteria.urlFotoPerfil();
        pessoaCriteria.enderecoPessoaId();
        pessoaCriteria.anexoPessoaId();
        pessoaCriteria.emailId();
        pessoaCriteria.telefoneId();
        pessoaCriteria.administradorId();
        pessoaCriteria.contadorId();
        pessoaCriteria.funcionarioId();
        pessoaCriteria.socioId();
        pessoaCriteria.usuarioEmpresaId();
        pessoaCriteria.distinct();
    }

    private static Condition<PessoaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getNome()) &&
                condition.apply(criteria.getCpf()) &&
                condition.apply(criteria.getDataNascimento()) &&
                condition.apply(criteria.getTituloEleitor()) &&
                condition.apply(criteria.getRg()) &&
                condition.apply(criteria.getRgOrgaoExpditor()) &&
                condition.apply(criteria.getRgUfExpedicao()) &&
                condition.apply(criteria.getEstadoCivil()) &&
                condition.apply(criteria.getSexo()) &&
                condition.apply(criteria.getUrlFotoPerfil()) &&
                condition.apply(criteria.getEnderecoPessoaId()) &&
                condition.apply(criteria.getAnexoPessoaId()) &&
                condition.apply(criteria.getEmailId()) &&
                condition.apply(criteria.getTelefoneId()) &&
                condition.apply(criteria.getAdministradorId()) &&
                condition.apply(criteria.getContadorId()) &&
                condition.apply(criteria.getFuncionarioId()) &&
                condition.apply(criteria.getSocioId()) &&
                condition.apply(criteria.getUsuarioEmpresaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<PessoaCriteria> copyFiltersAre(PessoaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getNome(), copy.getNome()) &&
                condition.apply(criteria.getCpf(), copy.getCpf()) &&
                condition.apply(criteria.getDataNascimento(), copy.getDataNascimento()) &&
                condition.apply(criteria.getTituloEleitor(), copy.getTituloEleitor()) &&
                condition.apply(criteria.getRg(), copy.getRg()) &&
                condition.apply(criteria.getRgOrgaoExpditor(), copy.getRgOrgaoExpditor()) &&
                condition.apply(criteria.getRgUfExpedicao(), copy.getRgUfExpedicao()) &&
                condition.apply(criteria.getEstadoCivil(), copy.getEstadoCivil()) &&
                condition.apply(criteria.getSexo(), copy.getSexo()) &&
                condition.apply(criteria.getUrlFotoPerfil(), copy.getUrlFotoPerfil()) &&
                condition.apply(criteria.getEnderecoPessoaId(), copy.getEnderecoPessoaId()) &&
                condition.apply(criteria.getAnexoPessoaId(), copy.getAnexoPessoaId()) &&
                condition.apply(criteria.getEmailId(), copy.getEmailId()) &&
                condition.apply(criteria.getTelefoneId(), copy.getTelefoneId()) &&
                condition.apply(criteria.getAdministradorId(), copy.getAdministradorId()) &&
                condition.apply(criteria.getContadorId(), copy.getContadorId()) &&
                condition.apply(criteria.getFuncionarioId(), copy.getFuncionarioId()) &&
                condition.apply(criteria.getSocioId(), copy.getSocioId()) &&
                condition.apply(criteria.getUsuarioEmpresaId(), copy.getUsuarioEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
