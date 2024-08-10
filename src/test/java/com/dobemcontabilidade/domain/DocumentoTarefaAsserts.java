package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DocumentoTarefaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentoTarefaAllPropertiesEquals(DocumentoTarefa expected, DocumentoTarefa actual) {
        assertDocumentoTarefaAutoGeneratedPropertiesEquals(expected, actual);
        assertDocumentoTarefaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentoTarefaAllUpdatablePropertiesEquals(DocumentoTarefa expected, DocumentoTarefa actual) {
        assertDocumentoTarefaUpdatableFieldsEquals(expected, actual);
        assertDocumentoTarefaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentoTarefaAutoGeneratedPropertiesEquals(DocumentoTarefa expected, DocumentoTarefa actual) {
        assertThat(expected)
            .as("Verify DocumentoTarefa auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentoTarefaUpdatableFieldsEquals(DocumentoTarefa expected, DocumentoTarefa actual) {
        assertThat(expected)
            .as("Verify DocumentoTarefa relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDocumentoTarefaUpdatableRelationshipsEquals(DocumentoTarefa expected, DocumentoTarefa actual) {
        assertThat(expected)
            .as("Verify DocumentoTarefa relationships")
            .satisfies(e -> assertThat(e.getTarefa()).as("check tarefa").isEqualTo(actual.getTarefa()));
    }
}
