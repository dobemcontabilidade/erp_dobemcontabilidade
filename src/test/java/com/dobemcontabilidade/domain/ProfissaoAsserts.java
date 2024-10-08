package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfissaoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProfissaoAllPropertiesEquals(Profissao expected, Profissao actual) {
        assertProfissaoAutoGeneratedPropertiesEquals(expected, actual);
        assertProfissaoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProfissaoAllUpdatablePropertiesEquals(Profissao expected, Profissao actual) {
        assertProfissaoUpdatableFieldsEquals(expected, actual);
        assertProfissaoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProfissaoAutoGeneratedPropertiesEquals(Profissao expected, Profissao actual) {
        assertThat(expected)
            .as("Verify Profissao auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProfissaoUpdatableFieldsEquals(Profissao expected, Profissao actual) {
        assertThat(expected)
            .as("Verify Profissao relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()))
            .satisfies(e -> assertThat(e.getDescricao()).as("check descricao").isEqualTo(actual.getDescricao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertProfissaoUpdatableRelationshipsEquals(Profissao expected, Profissao actual) {
        assertThat(expected)
            .as("Verify Profissao relationships")
            .satisfies(e -> assertThat(e.getSocio()).as("check socio").isEqualTo(actual.getSocio()));
    }
}
