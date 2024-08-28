package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class SocioAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSocioAllPropertiesEquals(Socio expected, Socio actual) {
        assertSocioAutoGeneratedPropertiesEquals(expected, actual);
        assertSocioAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSocioAllUpdatablePropertiesEquals(Socio expected, Socio actual) {
        assertSocioUpdatableFieldsEquals(expected, actual);
        assertSocioUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSocioAutoGeneratedPropertiesEquals(Socio expected, Socio actual) {
        assertThat(expected)
            .as("Verify Socio auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSocioUpdatableFieldsEquals(Socio expected, Socio actual) {
        assertThat(expected)
            .as("Verify Socio relevant properties")
            .satisfies(e -> assertThat(e.getNome()).as("check nome").isEqualTo(actual.getNome()))
            .satisfies(e -> assertThat(e.getProlabore()).as("check prolabore").isEqualTo(actual.getProlabore()))
            .satisfies(
                e -> assertThat(e.getPercentualSociedade()).as("check percentualSociedade").isEqualTo(actual.getPercentualSociedade())
            )
            .satisfies(e -> assertThat(e.getAdminstrador()).as("check adminstrador").isEqualTo(actual.getAdminstrador()))
            .satisfies(e -> assertThat(e.getDistribuicaoLucro()).as("check distribuicaoLucro").isEqualTo(actual.getDistribuicaoLucro()))
            .satisfies(e -> assertThat(e.getResponsavelReceita()).as("check responsavelReceita").isEqualTo(actual.getResponsavelReceita()))
            .satisfies(
                e ->
                    assertThat(e.getPercentualDistribuicaoLucro())
                        .as("check percentualDistribuicaoLucro")
                        .isEqualTo(actual.getPercentualDistribuicaoLucro())
            )
            .satisfies(e -> assertThat(e.getFuncaoSocio()).as("check funcaoSocio").isEqualTo(actual.getFuncaoSocio()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertSocioUpdatableRelationshipsEquals(Socio expected, Socio actual) {
        assertThat(expected)
            .as("Verify Socio relationships")
            .satisfies(e -> assertThat(e.getPessoaFisica()).as("check pessoaFisica").isEqualTo(actual.getPessoaFisica()))
            .satisfies(e -> assertThat(e.getEmpresa()).as("check empresa").isEqualTo(actual.getEmpresa()));
    }
}
