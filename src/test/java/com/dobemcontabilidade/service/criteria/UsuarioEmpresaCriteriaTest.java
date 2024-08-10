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
        usuarioEmpresaCriteria.dataHoraAtivacao();
        usuarioEmpresaCriteria.dataLimiteAcesso();
        usuarioEmpresaCriteria.situacao();
        usuarioEmpresaCriteria.pessoaId();
        usuarioEmpresaCriteria.empresaId();
        usuarioEmpresaCriteria.distinct();
    }

    private static Condition<UsuarioEmpresaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getDataHoraAtivacao()) &&
                condition.apply(criteria.getDataLimiteAcesso()) &&
                condition.apply(criteria.getSituacao()) &&
                condition.apply(criteria.getPessoaId()) &&
                condition.apply(criteria.getEmpresaId()) &&
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
                condition.apply(criteria.getDataHoraAtivacao(), copy.getDataHoraAtivacao()) &&
                condition.apply(criteria.getDataLimiteAcesso(), copy.getDataLimiteAcesso()) &&
                condition.apply(criteria.getSituacao(), copy.getSituacao()) &&
                condition.apply(criteria.getPessoaId(), copy.getPessoaId()) &&
                condition.apply(criteria.getEmpresaId(), copy.getEmpresaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
