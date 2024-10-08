package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SubtarefaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubtarefaAllPropertiesEquals(Subtarefa expected, Subtarefa actual) {
        assertSubtarefaAutoGeneratedPropertiesEquals(expected, actual);
        assertSubtarefaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubtarefaAllUpdatablePropertiesEquals(Subtarefa expected, Subtarefa actual) {
        assertSubtarefaUpdatableFieldsEquals(expected, actual);
        assertSubtarefaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubtarefaAutoGeneratedPropertiesEquals(Subtarefa expected, Subtarefa actual) {
        assertThat(expected)
            .as("Verify Subtarefa auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubtarefaUpdatableFieldsEquals(Subtarefa expected, Subtarefa actual) {
        assertThat(expected)
            .as("Verify Subtarefa relevant properties")
            .satisfies(e -> assertThat(e.getOrdem()).as("check ordem").isEqualTo(actual.getOrdem()))
            .satisfies(e -> assertThat(e.getItem()).as("check item").isEqualTo(actual.getItem()))
            .satisfies(e -> assertThat(e.getDescricao()).as("check descricao").isEqualTo(actual.getDescricao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSubtarefaUpdatableRelationshipsEquals(Subtarefa expected, Subtarefa actual) {
        assertThat(expected)
            .as("Verify Subtarefa relationships")
            .satisfies(e -> assertThat(e.getTarefa()).as("check tarefa").isEqualTo(actual.getTarefa()));
    }
}
