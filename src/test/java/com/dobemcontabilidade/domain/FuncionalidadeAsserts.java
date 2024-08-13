package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class FuncionalidadeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFuncionalidadeAllPropertiesEquals(Funcionalidade expected, Funcionalidade actual) {
        assertFuncionalidadeAutoGeneratedPropertiesEquals(expected, actual);
        assertFuncionalidadeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFuncionalidadeAllUpdatablePropertiesEquals(Funcionalidade expected, Funcionalidade actual) {
        assertFuncionalidadeUpdatableFieldsEquals(expected, actual);
        assertFuncionalidadeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFuncionalidadeAutoGeneratedPropertiesEquals(Funcionalidade expected, Funcionalidade actual) {
        assertThat(expected)
            .as("Verify Funcionalidade auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFuncionalidadeUpdatableFieldsEquals(Funcionalidade expected, Funcionalidade actual) {
        assertThat(expected)
            .as("Verify Funcionalidade relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()))
            .satisfies(e -> assertThat(e.getAtiva()).as("check ativa").isEqualTo(actual.getAtiva()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFuncionalidadeUpdatableRelationshipsEquals(Funcionalidade expected, Funcionalidade actual) {
        assertThat(expected)
            .as("Verify Funcionalidade relationships")
            .satisfies(e -> assertThat(e.getModulo()).as("check modulo").isEqualTo(actual.getModulo()));
    }
}
