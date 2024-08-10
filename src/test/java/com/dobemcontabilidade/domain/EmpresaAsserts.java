package com.dobemcontabilidade.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class EmpresaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEmpresaAllPropertiesEquals(Empresa expected, Empresa actual) {
        assertEmpresaAutoGeneratedPropertiesEquals(expected, actual);
        assertEmpresaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEmpresaAllUpdatablePropertiesEquals(Empresa expected, Empresa actual) {
        assertEmpresaUpdatableFieldsEquals(expected, actual);
        assertEmpresaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEmpresaAutoGeneratedPropertiesEquals(Empresa expected, Empresa actual) {
        assertThat(expected)
            .as("Verify Empresa auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEmpresaUpdatableFieldsEquals(Empresa expected, Empresa actual) {
        assertThat(expected)
            .as("Verify Empresa relevant properties")
            .satisfies(e -> assertThat(e.getRazaoSocial()).as("check razaoSocial").isEqualTo(actual.getRazaoSocial()))
            .satisfies(e -> assertThat(e.getNomeFantasia()).as("check nomeFantasia").isEqualTo(actual.getNomeFantasia()))
            .satisfies(e -> assertThat(e.getDescricaoDoNegocio()).as("check descricaoDoNegocio").isEqualTo(actual.getDescricaoDoNegocio()))
            .satisfies(e -> assertThat(e.getCnpj()).as("check cnpj").isEqualTo(actual.getCnpj()))
            .satisfies(e -> assertThat(e.getDataAbertura()).as("check dataAbertura").isEqualTo(actual.getDataAbertura()))
            .satisfies(e -> assertThat(e.getUrlContratoSocial()).as("check urlContratoSocial").isEqualTo(actual.getUrlContratoSocial()))
            .satisfies(e -> assertThat(e.getCapitalSocial()).as("check capitalSocial").isEqualTo(actual.getCapitalSocial()))
            .satisfies(e -> assertThat(e.getTipoSegmento()).as("check tipoSegmento").isEqualTo(actual.getTipoSegmento()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertEmpresaUpdatableRelationshipsEquals(Empresa expected, Empresa actual) {
        assertThat(expected)
            .as("Verify Empresa relationships")
            .satisfies(e -> assertThat(e.getRamo()).as("check ramo").isEqualTo(actual.getRamo()))
            .satisfies(e -> assertThat(e.getTributacao()).as("check tributacao").isEqualTo(actual.getTributacao()))
            .satisfies(e -> assertThat(e.getEnquadramento()).as("check enquadramento").isEqualTo(actual.getEnquadramento()));
    }
}
