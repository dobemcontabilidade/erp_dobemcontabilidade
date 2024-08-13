package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ImpostoEmpresaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertImpostoEmpresaAllPropertiesEquals(ImpostoEmpresa expected, ImpostoEmpresa actual) {
        assertImpostoEmpresaAutoGeneratedPropertiesEquals(expected, actual);
        assertImpostoEmpresaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertImpostoEmpresaAllUpdatablePropertiesEquals(ImpostoEmpresa expected, ImpostoEmpresa actual) {
        assertImpostoEmpresaUpdatableFieldsEquals(expected, actual);
        assertImpostoEmpresaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertImpostoEmpresaAutoGeneratedPropertiesEquals(ImpostoEmpresa expected, ImpostoEmpresa actual) {
        assertThat(expected)
            .as("Verify ImpostoEmpresa auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertImpostoEmpresaUpdatableFieldsEquals(ImpostoEmpresa expected, ImpostoEmpresa actual) {
        assertThat(expected)
            .as("Verify ImpostoEmpresa relevant properties")
            .satisfies(e -> assertThat(e.getDiaVencimento()).as("check diaVencimento").isEqualTo(actual.getDiaVencimento()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertImpostoEmpresaUpdatableRelationshipsEquals(ImpostoEmpresa expected, ImpostoEmpresa actual) {
        assertThat(expected)
            .as("Verify ImpostoEmpresa relationships")
            .satisfies(e -> assertThat(e.getEmpresa()).as("check empresa").isEqualTo(actual.getEmpresa()))
            .satisfies(e -> assertThat(e.getImposto()).as("check imposto").isEqualTo(actual.getImposto()));
    }
}
