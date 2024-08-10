package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class UsuarioContadorCriteriaTest {

    @Test
    void newUsuarioContadorCriteriaHasAllFiltersNullTest() {
        var usuarioContadorCriteria = new UsuarioContadorCriteria();
        assertThat(usuarioContadorCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void usuarioContadorCriteriaFluentMethodsCreatesFiltersTest() {
        var usuarioContadorCriteria = new UsuarioContadorCriteria();

        setAllFilters(usuarioContadorCriteria);

        assertThat(usuarioContadorCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void usuarioContadorCriteriaCopyCreatesNullFilterTest() {
        var usuarioContadorCriteria = new UsuarioContadorCriteria();
        var copy = usuarioContadorCriteria.copy();

        assertThat(usuarioContadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(usuarioContadorCriteria)
        );
    }

    @Test
    void usuarioContadorCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var usuarioContadorCriteria = new UsuarioContadorCriteria();
        setAllFilters(usuarioContadorCriteria);

        var copy = usuarioContadorCriteria.copy();

        assertThat(usuarioContadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(usuarioContadorCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var usuarioContadorCriteria = new UsuarioContadorCriteria();

        assertThat(usuarioContadorCriteria).hasToString("UsuarioContadorCriteria{}");
    }

    private static void setAllFilters(UsuarioContadorCriteria usuarioContadorCriteria) {
        usuarioContadorCriteria.id();
        usuarioContadorCriteria.email();
        usuarioContadorCriteria.dataHoraAtivacao();
        usuarioContadorCriteria.dataLimiteAcesso();
        usuarioContadorCriteria.situacao();
        usuarioContadorCriteria.contadorId();
        usuarioContadorCriteria.administradorId();
        usuarioContadorCriteria.distinct();
    }

    private static Condition<UsuarioContadorCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getEmail()) &&
                condition.apply(criteria.getDataHoraAtivacao()) &&
                condition.apply(criteria.getDataLimiteAcesso()) &&
                condition.apply(criteria.getSituacao()) &&
                condition.apply(criteria.getContadorId()) &&
                condition.apply(criteria.getAdministradorId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<UsuarioContadorCriteria> copyFiltersAre(
        UsuarioContadorCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getEmail(), copy.getEmail()) &&
                condition.apply(criteria.getDataHoraAtivacao(), copy.getDataHoraAtivacao()) &&
                condition.apply(criteria.getDataLimiteAcesso(), copy.getDataLimiteAcesso()) &&
                condition.apply(criteria.getSituacao(), copy.getSituacao()) &&
                condition.apply(criteria.getContadorId(), copy.getContadorId()) &&
                condition.apply(criteria.getAdministradorId(), copy.getAdministradorId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
