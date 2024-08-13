package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class EscolaridadeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEscolaridadeAllPropertiesEquals(Escolaridade expected, Escolaridade actual) {
        assertEscolaridadeAutoGeneratedPropertiesEquals(expected, actual);
        assertEscolaridadeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEscolaridadeAllUpdatablePropertiesEquals(Escolaridade expected, Escolaridade actual) {
        assertEscolaridadeUpdatableFieldsEquals(expected, actual);
        assertEscolaridadeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEscolaridadeAutoGeneratedPropertiesEquals(Escolaridade expected, Escolaridade actual) {
        assertThat(expected)
            .as("Verify Escolaridade auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEscolaridadeUpdatableFieldsEquals(Escolaridade expected, Escolaridade actual) {
        assertThat(expected)
            .as("Verify Escolaridade relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()))
            .satisfies(e -> assertThat(e.getDescricao()).as("check descricao").isEqualTo(actual.getDescricao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEscolaridadeUpdatableRelationshipsEquals(Escolaridade expected, Escolaridade actual) {}
}
