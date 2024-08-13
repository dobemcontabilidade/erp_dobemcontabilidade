package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AgendaTarefaRecorrenteExecucaoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAgendaTarefaRecorrenteExecucaoAllPropertiesEquals(
        AgendaTarefaRecorrenteExecucao expected,
        AgendaTarefaRecorrenteExecucao actual
    ) {
        assertAgendaTarefaRecorrenteExecucaoAutoGeneratedPropertiesEquals(expected, actual);
        assertAgendaTarefaRecorrenteExecucaoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAgendaTarefaRecorrenteExecucaoAllUpdatablePropertiesEquals(
        AgendaTarefaRecorrenteExecucao expected,
        AgendaTarefaRecorrenteExecucao actual
    ) {
        assertAgendaTarefaRecorrenteExecucaoUpdatableFieldsEquals(expected, actual);
        assertAgendaTarefaRecorrenteExecucaoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAgendaTarefaRecorrenteExecucaoAutoGeneratedPropertiesEquals(
        AgendaTarefaRecorrenteExecucao expected,
        AgendaTarefaRecorrenteExecucao actual
    ) {
        assertThat(expected)
            .as("Verify AgendaTarefaRecorrenteExecucao auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAgendaTarefaRecorrenteExecucaoUpdatableFieldsEquals(
        AgendaTarefaRecorrenteExecucao expected,
        AgendaTarefaRecorrenteExecucao actual
    ) {
        assertThat(expected)
            .as("Verify AgendaTarefaRecorrenteExecucao relevant properties")
            .satisfies(e -> assertThat(e.getAtivo()).as("check ativo").isEqualTo(actual.getAtivo()))
            .satisfies(e -> assertThat(e.getHoraInicio()).as("check horaInicio").isEqualTo(actual.getHoraInicio()))
            .satisfies(e -> assertThat(e.getHoraFim()).as("check horaFim").isEqualTo(actual.getHoraFim()))
            .satisfies(e -> assertThat(e.getDiaInteiro()).as("check diaInteiro").isEqualTo(actual.getDiaInteiro()))
            .satisfies(e -> assertThat(e.getComentario()).as("check comentario").isEqualTo(actual.getComentario()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAgendaTarefaRecorrenteExecucaoUpdatableRelationshipsEquals(
        AgendaTarefaRecorrenteExecucao expected,
        AgendaTarefaRecorrenteExecucao actual
    ) {
        assertThat(expected)
            .as("Verify AgendaTarefaRecorrenteExecucao relationships")
            .satisfies(
                e ->
                    assertThat(e.getTarefaRecorrenteExecucao())
                        .as("check tarefaRecorrenteExecucao")
                        .isEqualTo(actual.getTarefaRecorrenteExecucao())
            );
    }
}
