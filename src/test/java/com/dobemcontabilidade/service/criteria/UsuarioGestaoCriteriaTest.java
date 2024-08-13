package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class UsuarioGestaoCriteriaTest {

    @Test
    void newUsuarioGestaoCriteriaHasAllFiltersNullTest() {
        var usuarioGestaoCriteria = new UsuarioGestaoCriteria();
        assertThat(usuarioGestaoCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void usuarioGestaoCriteriaFluentMethodsCreatesFiltersTest() {
        var usuarioGestaoCriteria = new UsuarioGestaoCriteria();

        setAllFilters(usuarioGestaoCriteria);

        assertThat(usuarioGestaoCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void usuarioGestaoCriteriaCopyCreatesNullFilterTest() {
        var usuarioGestaoCriteria = new UsuarioGestaoCriteria();
        var copy = usuarioGestaoCriteria.copy();

        assertThat(usuarioGestaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(usuarioGestaoCriteria)
        );
    }

    @Test
    void usuarioGestaoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var usuarioGestaoCriteria = new UsuarioGestaoCriteria();
        setAllFilters(usuarioGestaoCriteria);

        var copy = usuarioGestaoCriteria.copy();

        assertThat(usuarioGestaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(usuarioGestaoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var usuarioGestaoCriteria = new UsuarioGestaoCriteria();

        assertThat(usuarioGestaoCriteria).hasToString("UsuarioGestaoCriteria{}");
    }

    private static void setAllFilters(UsuarioGestaoCriteria usuarioGestaoCriteria) {
        usuarioGestaoCriteria.id();
        usuarioGestaoCriteria.email();
        usuarioGestaoCriteria.ativo();
        usuarioGestaoCriteria.dataHoraAtivacao();
        usuarioGestaoCriteria.dataLimiteAcesso();
        usuarioGestaoCriteria.situacaoUsuarioGestao();
        usuarioGestaoCriteria.administradorId();
        usuarioGestaoCriteria.distinct();
    }

    private static Condition<UsuarioGestaoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getAtivo()) &&
                condition.apply(criteria.getDataHoraAtivacao()) &&
                condition.apply(criteria.getDataLimiteAcesso()) &&
                condition.apply(criteria.getSituacaoUsuarioGestao()) &&
                condition.apply(criteria.getAdministradorId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<UsuarioGestaoCriteria> copyFiltersAre(
        UsuarioGestaoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getAtivo(), copy.getAtivo()) &&
                condition.apply(criteria.getDataHoraAtivacao(), copy.getDataHoraAtivacao()) &&
                condition.apply(criteria.getDataLimiteAcesso(), copy.getDataLimiteAcesso()) &&
                condition.apply(criteria.getSituacaoUsuarioGestao(), copy.getSituacaoUsuarioGestao()) &&
                condition.apply(criteria.getAdministradorId(), copy.getAdministradorId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
