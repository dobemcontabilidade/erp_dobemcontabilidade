package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.CertificadoDigitalAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.CertificadoDigital;
import com.dobemcontabilidade.domain.enumeration.TipoCertificadoEnum;
import com.dobemcontabilidade.repository.CertificadoDigitalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CertificadoDigitalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CertificadoDigitalResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final TipoCertificadoEnum DEFAULT_TIPO_CERTIFICADO = TipoCertificadoEnum.A;
    private static final TipoCertificadoEnum UPDATED_TIPO_CERTIFICADO = TipoCertificadoEnum.S;

    private static final String ENTITY_API_URL = "/api/certificado-digitals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CertificadoDigitalRepository certificadoDigitalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCertificadoDigitalMockMvc;

    private CertificadoDigital certificadoDigital;

    private CertificadoDigital insertedCertificadoDigital;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CertificadoDigital createEntity(EntityManager em) {
        CertificadoDigital certificadoDigital = new CertificadoDigital()
            .nome(DEFAULT_NOME)
            .sigla(DEFAULT_SIGLA)
            .descricao(DEFAULT_DESCRICAO)
            .tipoCertificado(DEFAULT_TIPO_CERTIFICADO);
        return certificadoDigital;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CertificadoDigital createUpdatedEntity(EntityManager em) {
        CertificadoDigital certificadoDigital = new CertificadoDigital()
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .descricao(UPDATED_DESCRICAO)
            .tipoCertificado(UPDATED_TIPO_CERTIFICADO);
        return certificadoDigital;
    }

    @BeforeEach
    public void initTest() {
        certificadoDigital = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCertificadoDigital != null) {
            certificadoDigitalRepository.delete(insertedCertificadoDigital);
            insertedCertificadoDigital = null;
        }
    }

    @Test
    @Transactional
    void createCertificadoDigital() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CertificadoDigital
        var returnedCertificadoDigital = om.readValue(
            restCertificadoDigitalMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(certificadoDigital))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CertificadoDigital.class
        );

        // Validate the CertificadoDigital in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCertificadoDigitalUpdatableFieldsEquals(
            returnedCertificadoDigital,
            getPersistedCertificadoDigital(returnedCertificadoDigital)
        );

        insertedCertificadoDigital = returnedCertificadoDigital;
    }

    @Test
    @Transactional
    void createCertificadoDigitalWithExistingId() throws Exception {
        // Create the CertificadoDigital with an existing ID
        certificadoDigital.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificadoDigitalMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCertificadoDigitals() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get all the certificadoDigitalList
        restCertificadoDigitalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificadoDigital.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].tipoCertificado").value(hasItem(DEFAULT_TIPO_CERTIFICADO.toString())));
    }

    @Test
    @Transactional
    void getCertificadoDigital() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        // Get the certificadoDigital
        restCertificadoDigitalMockMvc
            .perform(get(ENTITY_API_URL_ID, certificadoDigital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(certificadoDigital.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.tipoCertificado").value(DEFAULT_TIPO_CERTIFICADO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCertificadoDigital() throws Exception {
        // Get the certificadoDigital
        restCertificadoDigitalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCertificadoDigital() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the certificadoDigital
        CertificadoDigital updatedCertificadoDigital = certificadoDigitalRepository.findById(certificadoDigital.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCertificadoDigital are not directly saved in db
        em.detach(updatedCertificadoDigital);
        updatedCertificadoDigital
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .descricao(UPDATED_DESCRICAO)
            .tipoCertificado(UPDATED_TIPO_CERTIFICADO);

        restCertificadoDigitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCertificadoDigital.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCertificadoDigital))
            )
            .andExpect(status().isOk());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCertificadoDigitalToMatchAllProperties(updatedCertificadoDigital);
    }

    @Test
    @Transactional
    void putNonExistingCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, certificadoDigital.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCertificadoDigitalWithPatch() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the certificadoDigital using partial update
        CertificadoDigital partialUpdatedCertificadoDigital = new CertificadoDigital();
        partialUpdatedCertificadoDigital.setId(certificadoDigital.getId());

        partialUpdatedCertificadoDigital.sigla(UPDATED_SIGLA).tipoCertificado(UPDATED_TIPO_CERTIFICADO);

        restCertificadoDigitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertificadoDigital.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCertificadoDigital))
            )
            .andExpect(status().isOk());

        // Validate the CertificadoDigital in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCertificadoDigitalUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCertificadoDigital, certificadoDigital),
            getPersistedCertificadoDigital(certificadoDigital)
        );
    }

    @Test
    @Transactional
    void fullUpdateCertificadoDigitalWithPatch() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the certificadoDigital using partial update
        CertificadoDigital partialUpdatedCertificadoDigital = new CertificadoDigital();
        partialUpdatedCertificadoDigital.setId(certificadoDigital.getId());

        partialUpdatedCertificadoDigital
            .nome(UPDATED_NOME)
            .sigla(UPDATED_SIGLA)
            .descricao(UPDATED_DESCRICAO)
            .tipoCertificado(UPDATED_TIPO_CERTIFICADO);

        restCertificadoDigitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCertificadoDigital.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCertificadoDigital))
            )
            .andExpect(status().isOk());

        // Validate the CertificadoDigital in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCertificadoDigitalUpdatableFieldsEquals(
            partialUpdatedCertificadoDigital,
            getPersistedCertificadoDigital(partialUpdatedCertificadoDigital)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, certificadoDigital.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCertificadoDigital() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        certificadoDigital.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCertificadoDigitalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(certificadoDigital))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CertificadoDigital in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCertificadoDigital() throws Exception {
        // Initialize the database
        insertedCertificadoDigital = certificadoDigitalRepository.saveAndFlush(certificadoDigital);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the certificadoDigital
        restCertificadoDigitalMockMvc
            .perform(delete(ENTITY_API_URL_ID, certificadoDigital.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return certificadoDigitalRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected CertificadoDigital getPersistedCertificadoDigital(CertificadoDigital certificadoDigital) {
        return certificadoDigitalRepository.findById(certificadoDigital.getId()).orElseThrow();
    }

    protected void assertPersistedCertificadoDigitalToMatchAllProperties(CertificadoDigital expectedCertificadoDigital) {
        assertCertificadoDigitalAllPropertiesEquals(expectedCertificadoDigital, getPersistedCertificadoDigital(expectedCertificadoDigital));
    }

    protected void assertPersistedCertificadoDigitalToMatchUpdatableProperties(CertificadoDigital expectedCertificadoDigital) {
        assertCertificadoDigitalAllUpdatablePropertiesEquals(
            expectedCertificadoDigital,
            getPersistedCertificadoDigital(expectedCertificadoDigital)
        );
    }
}
