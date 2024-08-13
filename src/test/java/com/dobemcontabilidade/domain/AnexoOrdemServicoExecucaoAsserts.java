package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AnexoOrdemServicoExecucaoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAnexoOrdemServicoExecucaoAllPropertiesEquals(
        AnexoOrdemServicoExecucao expected,
        AnexoOrdemServicoExecucao actual
    ) {
        assertAnexoOrdemServicoExecucaoAutoGeneratedPropertiesEquals(expected, actual);
        assertAnexoOrdemServicoExecucaoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAnexoOrdemServicoExecucaoAllUpdatablePropertiesEquals(
        AnexoOrdemServicoExecucao expected,
        AnexoOrdemServicoExecucao actual
    ) {
        assertAnexoOrdemServicoExecucaoUpdatableFieldsEquals(expected, actual);
        assertAnexoOrdemServicoExecucaoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAnexoOrdemServicoExecucaoAutoGeneratedPropertiesEquals(
        AnexoOrdemServicoExecucao expected,
        AnexoOrdemServicoExecucao actual
    ) {
        assertThat(expected)
            .as("Verify AnexoOrdemServicoExecucao auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAnexoOrdemServicoExecucaoUpdatableFieldsEquals(
        AnexoOrdemServicoExecucao expected,
        AnexoOrdemServicoExecucao actual
    ) {
        assertThat(expected)
            .as("Verify AnexoOrdemServicoExecucao relevant properties")
            .satisfies(e -> assertThat(e.getUrl()).as("check url").isEqualTo(actual.getUrl()))
            .satisfies(e -> assertThat(e.getDescricao()).as("check descricao").isEqualTo(actual.getDescricao()))
            .satisfies(e -> assertThat(e.getDataHoraUpload()).as("check dataHoraUpload").isEqualTo(actual.getDataHoraUpload()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAnexoOrdemServicoExecucaoUpdatableRelationshipsEquals(
        AnexoOrdemServicoExecucao expected,
        AnexoOrdemServicoExecucao actual
    ) {
        assertThat(expected)
            .as("Verify AnexoOrdemServicoExecucao relationships")
            .satisfies(
                e ->
                    assertThat(e.getTarefaOrdemServicoExecucao())
                        .as("check tarefaOrdemServicoExecucao")
                        .isEqualTo(actual.getTarefaOrdemServicoExecucao())
            );
    }
}
