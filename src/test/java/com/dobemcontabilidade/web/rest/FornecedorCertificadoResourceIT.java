package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FornecedorCertificadoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.FornecedorCertificado;
import com.dobemcontabilidade.repository.FornecedorCertificadoRepository;
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
 * Integration tests for the {@link FornecedorCertificadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FornecedorCertificadoResourceIT {

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fornecedor-certificados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FornecedorCertificadoRepository fornecedorCertificadoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFornecedorCertificadoMockMvc;

    private FornecedorCertificado fornecedorCertificado;

    private FornecedorCertificado insertedFornecedorCertificado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FornecedorCertificado createEntity(EntityManager em) {
        FornecedorCertificado fornecedorCertificado = new FornecedorCertificado()
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .sigla(DEFAULT_SIGLA)
            .descricao(DEFAULT_DESCRICAO);
        return fornecedorCertificado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FornecedorCertificado createUpdatedEntity(EntityManager em) {
        FornecedorCertificado fornecedorCertificado = new FornecedorCertificado()
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .sigla(UPDATED_SIGLA)
            .descricao(UPDATED_DESCRICAO);
        return fornecedorCertificado;
    }

    @BeforeEach
    public void initTest() {
        fornecedorCertificado = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFornecedorCertificado != null) {
            fornecedorCertificadoRepository.delete(insertedFornecedorCertificado);
            insertedFornecedorCertificado = null;
        }
    }

    @Test
    @Transactional
    void createFornecedorCertificado() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FornecedorCertificado
        var returnedFornecedorCertificado = om.readValue(
            restFornecedorCertificadoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(fornecedorCertificado))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FornecedorCertificado.class
        );

        // Validate the FornecedorCertificado in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFornecedorCertificadoUpdatableFieldsEquals(
            returnedFornecedorCertificado,
            getPersistedFornecedorCertificado(returnedFornecedorCertificado)
        );

        insertedFornecedorCertificado = returnedFornecedorCertificado;
    }

    @Test
    @Transactional
    void createFornecedorCertificadoWithExistingId() throws Exception {
        // Create the FornecedorCertificado with an existing ID
        fornecedorCertificado.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorCertificadoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fornecedorCertificado))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorCertificado in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRazaoSocialIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fornecedorCertificado.setRazaoSocial(null);

        // Create the FornecedorCertificado, which fails.

        restFornecedorCertificadoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fornecedorCertificado))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFornecedorCertificados() throws Exception {
        // Initialize the database
        insertedFornecedorCertificado = fornecedorCertificadoRepository.saveAndFlush(fornecedorCertificado);

        // Get all the fornecedorCertificadoList
        restFornecedorCertificadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedorCertificado.getId().intValue())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getFornecedorCertificado() throws Exception {
        // Initialize the database
        insertedFornecedorCertificado = fornecedorCertificadoRepository.saveAndFlush(fornecedorCertificado);

        // Get the fornecedorCertificado
        restFornecedorCertificadoMockMvc
            .perform(get(ENTITY_API_URL_ID, fornecedorCertificado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedorCertificado.getId().intValue()))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFornecedorCertificado() throws Exception {
        // Get the fornecedorCertificado
        restFornecedorCertificadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFornecedorCertificado() throws Exception {
        // Initialize the database
        insertedFornecedorCertificado = fornecedorCertificadoRepository.saveAndFlush(fornecedorCertificado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fornecedorCertificado
        FornecedorCertificado updatedFornecedorCertificado = fornecedorCertificadoRepository
            .findById(fornecedorCertificado.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFornecedorCertificado are not directly saved in db
        em.detach(updatedFornecedorCertificado);
        updatedFornecedorCertificado.razaoSocial(UPDATED_RAZAO_SOCIAL).sigla(UPDATED_SIGLA).descricao(UPDATED_DESCRICAO);

        restFornecedorCertificadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFornecedorCertificado.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFornecedorCertificado))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorCertificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFornecedorCertificadoToMatchAllProperties(updatedFornecedorCertificado);
    }

    @Test
    @Transactional
    void putNonExistingFornecedorCertificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedorCertificado.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorCertificadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorCertificado.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fornecedorCertificado))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorCertificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFornecedorCertificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedorCertificado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorCertificadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fornecedorCertificado))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorCertificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFornecedorCertificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedorCertificado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorCertificadoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fornecedorCertificado))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FornecedorCertificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFornecedorCertificadoWithPatch() throws Exception {
        // Initialize the database
        insertedFornecedorCertificado = fornecedorCertificadoRepository.saveAndFlush(fornecedorCertificado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fornecedorCertificado using partial update
        FornecedorCertificado partialUpdatedFornecedorCertificado = new FornecedorCertificado();
        partialUpdatedFornecedorCertificado.setId(fornecedorCertificado.getId());

        partialUpdatedFornecedorCertificado.sigla(UPDATED_SIGLA);

        restFornecedorCertificadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedorCertificado.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFornecedorCertificado))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorCertificado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFornecedorCertificadoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFornecedorCertificado, fornecedorCertificado),
            getPersistedFornecedorCertificado(fornecedorCertificado)
        );
    }

    @Test
    @Transactional
    void fullUpdateFornecedorCertificadoWithPatch() throws Exception {
        // Initialize the database
        insertedFornecedorCertificado = fornecedorCertificadoRepository.saveAndFlush(fornecedorCertificado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fornecedorCertificado using partial update
        FornecedorCertificado partialUpdatedFornecedorCertificado = new FornecedorCertificado();
        partialUpdatedFornecedorCertificado.setId(fornecedorCertificado.getId());

        partialUpdatedFornecedorCertificado.razaoSocial(UPDATED_RAZAO_SOCIAL).sigla(UPDATED_SIGLA).descricao(UPDATED_DESCRICAO);

        restFornecedorCertificadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedorCertificado.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFornecedorCertificado))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorCertificado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFornecedorCertificadoUpdatableFieldsEquals(
            partialUpdatedFornecedorCertificado,
            getPersistedFornecedorCertificado(partialUpdatedFornecedorCertificado)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFornecedorCertificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedorCertificado.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorCertificadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fornecedorCertificado.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fornecedorCertificado))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorCertificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFornecedorCertificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedorCertificado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorCertificadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fornecedorCertificado))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorCertificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFornecedorCertificado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fornecedorCertificado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorCertificadoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fornecedorCertificado))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FornecedorCertificado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFornecedorCertificado() throws Exception {
        // Initialize the database
        insertedFornecedorCertificado = fornecedorCertificadoRepository.saveAndFlush(fornecedorCertificado);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fornecedorCertificado
        restFornecedorCertificadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fornecedorCertificado.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fornecedorCertificadoRepository.count();
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

    protected FornecedorCertificado getPersistedFornecedorCertificado(FornecedorCertificado fornecedorCertificado) {
        return fornecedorCertificadoRepository.findById(fornecedorCertificado.getId()).orElseThrow();
    }

    protected void assertPersistedFornecedorCertificadoToMatchAllProperties(FornecedorCertificado expectedFornecedorCertificado) {
        assertFornecedorCertificadoAllPropertiesEquals(
            expectedFornecedorCertificado,
            getPersistedFornecedorCertificado(expectedFornecedorCertificado)
        );
    }

    protected void assertPersistedFornecedorCertificadoToMatchUpdatableProperties(FornecedorCertificado expectedFornecedorCertificado) {
        assertFornecedorCertificadoAllUpdatablePropertiesEquals(
            expectedFornecedorCertificado,
            getPersistedFornecedorCertificado(expectedFornecedorCertificado)
        );
    }
}
