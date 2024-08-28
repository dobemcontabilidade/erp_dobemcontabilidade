package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TermoContratoContabilAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermoContratoContabilAllPropertiesEquals(TermoContratoContabil expected, TermoContratoContabil actual) {
        assertTermoContratoContabilAutoGeneratedPropertiesEquals(expected, actual);
        assertTermoContratoContabilAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermoContratoContabilAllUpdatablePropertiesEquals(
        TermoContratoContabil expected,
        TermoContratoContabil actual
    ) {
        assertTermoContratoContabilUpdatableFieldsEquals(expected, actual);
        assertTermoContratoContabilUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermoContratoContabilAutoGeneratedPropertiesEquals(
        TermoContratoContabil expected,
        TermoContratoContabil actual
    ) {
        assertThat(expected)
            .as("Verify TermoContratoContabil auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermoContratoContabilUpdatableFieldsEquals(TermoContratoContabil expected, TermoContratoContabil actual) {
        assertThat(expected)
            .as("Verify TermoContratoContabil relevant properties")
            .satisfies(e -> assertThat(e.getTitulo()).as("check titulo").isEqualTo(actual.getTitulo()))
            .satisfies(e -> assertThat(e.getDescricao()).as("check descricao").isEqualTo(actual.getDescricao()))
            .satisfies(e -> assertThat(e.getUrlDocumentoFonte()).as("check urlDocumentoFonte").isEqualTo(actual.getUrlDocumentoFonte()))
            .satisfies(e -> assertThat(e.getDocumento()).as("check documento").isEqualTo(actual.getDocumento()))
            .satisfies(e -> assertThat(e.getDisponivel()).as("check disponivel").isEqualTo(actual.getDisponivel()))
            .satisfies(e -> assertThat(e.getDataCriacao()).as("check dataCriacao").isEqualTo(actual.getDataCriacao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermoContratoContabilUpdatableRelationshipsEquals(
        TermoContratoContabil expected,
        TermoContratoContabil actual
    ) {}
}
