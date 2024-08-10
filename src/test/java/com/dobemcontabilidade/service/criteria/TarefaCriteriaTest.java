package com.dobemcontabilidade.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class TarefaCriteriaTest {

    @Test
    void newTarefaCriteriaHasAllFiltersNullTest() {
        var tarefaCriteria = new TarefaCriteria();
        assertThat(tarefaCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void tarefaCriteriaFluentMethodsCreatesFiltersTest() {
        var tarefaCriteria = new TarefaCriteria();

        setAllFilters(tarefaCriteria);

        assertThat(tarefaCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void tarefaCriteriaCopyCreatesNullFilterTest() {
        var tarefaCriteria = new TarefaCriteria();
        var copy = tarefaCriteria.copy();

        assertThat(tarefaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(tarefaCriteria)
        );
    }

    @Test
    void tarefaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var tarefaCriteria = new TarefaCriteria();
        setAllFilters(tarefaCriteria);

        var copy = tarefaCriteria.copy();

        assertThat(tarefaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(tarefaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var tarefaCriteria = new TarefaCriteria();

        assertThat(tarefaCriteria).hasToString("TarefaCriteria{}");
    }

    private static void setAllFilters(TarefaCriteria tarefaCriteria) {
        tarefaCriteria.id();
        tarefaCriteria.titulo();
        tarefaCriteria.numeroDias();
        tarefaCriteria.diaUtil();
        tarefaCriteria.valor();
        tarefaCriteria.notificarCliente();
        tarefaCriteria.geraMulta();
        tarefaCriteria.exibirEmpresa();
        tarefaCriteria.dataLegal();
        tarefaCriteria.pontos();
        tarefaCriteria.tipoTarefa();
        tarefaCriteria.tarefaEmpresaId();
        tarefaCriteria.subtarefaId();
        tarefaCriteria.documentoTarefaId();
        tarefaCriteria.esferaId();
        tarefaCriteria.frequenciaId();
        tarefaCriteria.competenciaId();
        tarefaCriteria.distinct();
    }

    private static Condition<TarefaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getTitulo()) &&
                condition.apply(criteria.getNumeroDias()) &&
                condition.apply(criteria.getDiaUtil()) &&
                condition.apply(criteria.getValor()) &&
                condition.apply(criteria.getNotificarCliente()) &&
                condition.apply(criteria.getGeraMulta()) &&
                condition.apply(criteria.getExibirEmpresa()) &&
                condition.apply(criteria.getDataLegal()) &&
                condition.apply(criteria.getPontos()) &&
                condition.apply(criteria.getTipoTarefa()) &&
                condition.apply(criteria.getTarefaEmpresaId()) &&
                condition.apply(criteria.getSubtarefaId()) &&
                condition.apply(criteria.getDocumentoTarefaId()) &&
                condition.apply(criteria.getEsferaId()) &&
                condition.apply(criteria.getFrequenciaId()) &&
                condition.apply(criteria.getCompetenciaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<TarefaCriteria> copyFiltersAre(TarefaCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getTitulo(), copy.getTitulo()) &&
                condition.apply(criteria.getNumeroDias(), copy.getNumeroDias()) &&
                condition.apply(criteria.getDiaUtil(), copy.getDiaUtil()) &&
                condition.apply(criteria.getValor(), copy.getValor()) &&
                condition.apply(criteria.getNotificarCliente(), copy.getNotificarCliente()) &&
                condition.apply(criteria.getGeraMulta(), copy.getGeraMulta()) &&
                condition.apply(criteria.getExibirEmpresa(), copy.getExibirEmpresa()) &&
                condition.apply(criteria.getDataLegal(), copy.getDataLegal()) &&
                condition.apply(criteria.getPontos(), copy.getPontos()) &&
                condition.apply(criteria.getTipoTarefa(), copy.getTipoTarefa()) &&
                condition.apply(criteria.getTarefaEmpresaId(), copy.getTarefaEmpresaId()) &&
                condition.apply(criteria.getSubtarefaId(), copy.getSubtarefaId()) &&
                condition.apply(criteria.getDocumentoTarefaId(), copy.getDocumentoTarefaId()) &&
                condition.apply(criteria.getEsferaId(), copy.getEsferaId()) &&
                condition.apply(criteria.getFrequenciaId(), copy.getFrequenciaId()) &&
                condition.apply(criteria.getCompetenciaId(), copy.getCompetenciaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
