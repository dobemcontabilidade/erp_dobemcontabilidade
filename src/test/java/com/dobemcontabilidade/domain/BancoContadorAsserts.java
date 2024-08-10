package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BancoContadorAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBancoContadorAllPropertiesEquals(BancoContador expected, BancoContador actual) {
        assertBancoContadorAutoGeneratedPropertiesEquals(expected, actual);
        assertBancoContadorAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBancoContadorAllUpdatablePropertiesEquals(BancoContador expected, BancoContador actual) {
        assertBancoContadorUpdatableFieldsEquals(expected, actual);
        assertBancoContadorUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBancoContadorAutoGeneratedPropertiesEquals(BancoContador expected, BancoContador actual) {
        assertThat(expected)
            .as("Verify BancoContador auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBancoContadorUpdatableFieldsEquals(BancoContador expected, BancoContador actual) {
        assertThat(expected)
            .as("Verify BancoContador relevant properties")
            .satisfies(e -> assertThat(e.getAgencia()).as("check agencia").isEqualTo(actual.getAgencia()))
            .satisfies(e -> assertThat(e.getConta()).as("check conta").isEqualTo(actual.getConta()))
            .satisfies(e -> assertThat(e.getDigitoAgencia()).as("check digitoAgencia").isEqualTo(actual.getDigitoAgencia()))
            .satisfies(e -> assertThat(e.getDigitoConta()).as("check digitoConta").isEqualTo(actual.getDigitoConta()))
            .satisfies(e -> assertThat(e.getPrincipal()).as("check principal").isEqualTo(actual.getPrincipal()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBancoContadorUpdatableRelationshipsEquals(BancoContador expected, BancoContador actual) {
        assertThat(expected)
            .as("Verify BancoContador relationships")
            .satisfies(e -> assertThat(e.getContador()).as("check contador").isEqualTo(actual.getContador()))
            .satisfies(e -> assertThat(e.getBanco()).as("check banco").isEqualTo(actual.getBanco()));
    }
}
