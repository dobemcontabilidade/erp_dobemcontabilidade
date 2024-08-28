package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TributacaoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTributacaoAllPropertiesEquals(Tributacao expected, Tributacao actual) {
        assertTributacaoAutoGeneratedPropertiesEquals(expected, actual);
        assertTributacaoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTributacaoAllUpdatablePropertiesEquals(Tributacao expected, Tributacao actual) {
        assertTributacaoUpdatableFieldsEquals(expected, actual);
        assertTributacaoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTributacaoAutoGeneratedPropertiesEquals(Tributacao expected, Tributacao actual) {
        assertThat(expected)
            .as("Verify Tributacao auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTributacaoUpdatableFieldsEquals(Tributacao expected, Tributacao actual) {
        assertThat(expected)
            .as("Verify Tributacao relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()))
            .satisfies(e -> assertThat(e.getDescricao()).as("check descricao").isEqualTo(actual.getDescricao()))
            .satisfies(e -> assertThat(e.getSituacao()).as("check situacao").isEqualTo(actual.getSituacao()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTributacaoUpdatableRelationshipsEquals(Tributacao expected, Tributacao actual) {}
}
