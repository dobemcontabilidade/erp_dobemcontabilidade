package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class UsuarioEmpresaCriteriaTest {

    @Test
    void newUsuarioEmpresaCriteriaHasAllFiltersNullTest() {
        var usuarioEmpresaCriteria = new UsuarioEmpresaCriteria();
        assertThat(usuarioEmpresaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void usuarioEmpresaCriteriaFluentMethodsCreatesFiltersTest() {
        var usuarioEmpresaCriteria = new UsuarioEmpresaCriteria();

        setAllFilters(usuarioEmpresaCriteria);

        assertThat(usuarioEmpresaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void usuarioEmpresaCriteriaCopyCreatesNullFilterTest() {
        var usuarioEmpresaCriteria = new UsuarioEmpresaCriteria();
        var copy = usuarioEmpresaCriteria.copy();

        assertThat(usuarioEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(usuarioEmpresaCriteria)
        );
    }

    @Test
    void usuarioEmpresaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var usuarioEmpresaCriteria = new UsuarioEmpresaCriteria();
        setAllFilters(usuarioEmpresaCriteria);

        var copy = usuarioEmpresaCriteria.copy();

        assertThat(usuarioEmpresaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(usuarioEmpresaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var usuarioEmpresaCriteria = new UsuarioEmpresaCriteria();

        assertThat(usuarioEmpresaCriteria).hasToString("UsuarioEmpresaCriteria{}");
    }

    private static void setAllFilters(UsuarioEmpresaCriteria usuarioEmpresaCriteria) {
        usuarioEmpresaCriteria.id();
        usuarioEmpresaCriteria.email();
        usuarioEmpresaCriteria.ativo();
        usuarioEmpresaCriteria.dataHoraAtivacao();
        usuarioEmpresaCriteria.dataLimiteAcesso();
        usuarioEmpresaCriteria.situacaoUsuarioEmpresa();
        usuarioEmpresaCriteria.tipoUsuarioEmpresaEnum();
        usuarioEmpresaCriteria.grupoAcessoUsuarioEmpresaId();
        usuarioEmpresaCriteria.feedBackUsuarioParaContadorId();
        usuarioEmpresaCriteria.feedBackContadorParaUsuarioId();
        usuarioEmpresaCriteria.assinaturaEmpresaId();
        usuarioEmpresaCriteria.funcionarioId();
        usuarioEmpresaCriteria.socioId();
        usuarioEmpresaCriteria.distinct();
    }

    private static Condition<UsuarioEmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getAtivo()) &&
                condition.apply(criteria.getDataHoraAtivacao()) &&
                condition.apply(criteria.getDataLimiteAcesso()) &&
                condition.apply(criteria.getSituacaoUsuarioEmpresa()) &&
                condition.apply(criteria.getTipoUsuarioEmpresaEnum()) &&
                condition.apply(criteria.getGrupoAcessoUsuarioEmpresaId()) &&
                condition.apply(criteria.getFeedBackUsuarioParaContadorId()) &&
                condition.apply(criteria.getFeedBackContadorParaUsuarioId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getFuncionarioId()) &&
                condition.apply(criteria.getSocioId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<UsuarioEmpresaCriteria> copyFiltersAre(
        UsuarioEmpresaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getAtivo(), copy.getAtivo()) &&
                condition.apply(criteria.getDataHoraAtivacao(), copy.getDataHoraAtivacao()) &&
                condition.apply(criteria.getDataLimiteAcesso(), copy.getDataLimiteAcesso()) &&
                condition.apply(criteria.getSituacaoUsuarioEmpresa(), copy.getSituacaoUsuarioEmpresa()) &&
                condition.apply(criteria.getTipoUsuarioEmpresaEnum(), copy.getTipoUsuarioEmpresaEnum()) &&
                condition.apply(criteria.getGrupoAcessoUsuarioEmpresaId(), copy.getGrupoAcessoUsuarioEmpresaId()) &&
                condition.apply(criteria.getFeedBackUsuarioParaContadorId(), copy.getFeedBackUsuarioParaContadorId()) &&
                condition.apply(criteria.getFeedBackContadorParaUsuarioId(), copy.getFeedBackContadorParaUsuarioId()) &&
                condition.apply(criteria.getAssinaturaEmpresaId(), copy.getAssinaturaEmpresaId()) &&
                condition.apply(criteria.getFuncionarioId(), copy.getFuncionarioId()) &&
                condition.apply(criteria.getSocioId(), copy.getSocioId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
