package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.GrupoAcessoPadraoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.GrupoAcessoPadrao;
import com.dobemcontabilidade.repository.GrupoAcessoPadraoRepository;
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
 * Integration tests for the {@link GrupoAcessoPadraoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrupoAcessoPadraoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/grupo-acesso-padraos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GrupoAcessoPadraoRepository grupoAcessoPadraoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoAcessoPadraoMockMvc;

    private GrupoAcessoPadrao grupoAcessoPadrao;

    private GrupoAcessoPadrao insertedGrupoAcessoPadrao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAcessoPadrao createEntity(EntityManager em) {
        GrupoAcessoPadrao grupoAcessoPadrao = new GrupoAcessoPadrao().nome(DEFAULT_NOME);
        return grupoAcessoPadrao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAcessoPadrao createUpdatedEntity(EntityManager em) {
        GrupoAcessoPadrao grupoAcessoPadrao = new GrupoAcessoPadrao().nome(UPDATED_NOME);
        return grupoAcessoPadrao;
    }

    @BeforeEach
    public void initTest() {
        grupoAcessoPadrao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGrupoAcessoPadrao != null) {
            grupoAcessoPadraoRepository.delete(insertedGrupoAcessoPadrao);
            insertedGrupoAcessoPadrao = null;
        }
    }

    @Test
    @Transactional
    void createGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GrupoAcessoPadrao
        var returnedGrupoAcessoPadrao = om.readValue(
            restGrupoAcessoPadraoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoPadrao)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GrupoAcessoPadrao.class
        );

        // Validate the GrupoAcessoPadrao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGrupoAcessoPadraoUpdatableFieldsEquals(returnedGrupoAcessoPadrao, getPersistedGrupoAcessoPadrao(returnedGrupoAcessoPadrao));

        insertedGrupoAcessoPadrao = returnedGrupoAcessoPadrao;
    }

    @Test
    @Transactional
    void createGrupoAcessoPadraoWithExistingId() throws Exception {
        // Create the GrupoAcessoPadrao with an existing ID
        grupoAcessoPadrao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoAcessoPadraoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoPadrao)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrupoAcessoPadraos() throws Exception {
        // Initialize the database
        insertedGrupoAcessoPadrao = grupoAcessoPadraoRepository.saveAndFlush(grupoAcessoPadrao);

        // Get all the grupoAcessoPadraoList
        restGrupoAcessoPadraoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoAcessoPadrao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getGrupoAcessoPadrao() throws Exception {
        // Initialize the database
        insertedGrupoAcessoPadrao = grupoAcessoPadraoRepository.saveAndFlush(grupoAcessoPadrao);

        // Get the grupoAcessoPadrao
        restGrupoAcessoPadraoMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoAcessoPadrao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoAcessoPadrao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingGrupoAcessoPadrao() throws Exception {
        // Get the grupoAcessoPadrao
        restGrupoAcessoPadraoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrupoAcessoPadrao() throws Exception {
        // Initialize the database
        insertedGrupoAcessoPadrao = grupoAcessoPadraoRepository.saveAndFlush(grupoAcessoPadrao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoPadrao
        GrupoAcessoPadrao updatedGrupoAcessoPadrao = grupoAcessoPadraoRepository.findById(grupoAcessoPadrao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGrupoAcessoPadrao are not directly saved in db
        em.detach(updatedGrupoAcessoPadrao);
        updatedGrupoAcessoPadrao.nome(UPDATED_NOME);

        restGrupoAcessoPadraoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoAcessoPadrao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGrupoAcessoPadrao))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGrupoAcessoPadraoToMatchAllProperties(updatedGrupoAcessoPadrao);
    }

    @Test
    @Transactional
    void putNonExistingGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAcessoPadraoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoAcessoPadrao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoPadrao))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoPadraoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoPadrao))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoPadraoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoPadrao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoAcessoPadraoWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoAcessoPadrao = grupoAcessoPadraoRepository.saveAndFlush(grupoAcessoPadrao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoPadrao using partial update
        GrupoAcessoPadrao partialUpdatedGrupoAcessoPadrao = new GrupoAcessoPadrao();
        partialUpdatedGrupoAcessoPadrao.setId(grupoAcessoPadrao.getId());

        partialUpdatedGrupoAcessoPadrao.nome(UPDATED_NOME);

        restGrupoAcessoPadraoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAcessoPadrao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoAcessoPadrao))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoPadrao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoAcessoPadraoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGrupoAcessoPadrao, grupoAcessoPadrao),
            getPersistedGrupoAcessoPadrao(grupoAcessoPadrao)
        );
    }

    @Test
    @Transactional
    void fullUpdateGrupoAcessoPadraoWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoAcessoPadrao = grupoAcessoPadraoRepository.saveAndFlush(grupoAcessoPadrao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoPadrao using partial update
        GrupoAcessoPadrao partialUpdatedGrupoAcessoPadrao = new GrupoAcessoPadrao();
        partialUpdatedGrupoAcessoPadrao.setId(grupoAcessoPadrao.getId());

        partialUpdatedGrupoAcessoPadrao.nome(UPDATED_NOME);

        restGrupoAcessoPadraoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAcessoPadrao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoAcessoPadrao))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoPadrao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoAcessoPadraoUpdatableFieldsEquals(
            partialUpdatedGrupoAcessoPadrao,
            getPersistedGrupoAcessoPadrao(partialUpdatedGrupoAcessoPadrao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAcessoPadraoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoAcessoPadrao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoPadrao))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoPadraoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoPadrao))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoAcessoPadrao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoPadrao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoPadraoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(grupoAcessoPadrao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAcessoPadrao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoAcessoPadrao() throws Exception {
        // Initialize the database
        insertedGrupoAcessoPadrao = grupoAcessoPadraoRepository.saveAndFlush(grupoAcessoPadrao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grupoAcessoPadrao
        restGrupoAcessoPadraoMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoAcessoPadrao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return grupoAcessoPadraoRepository.count();
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

    protected GrupoAcessoPadrao getPersistedGrupoAcessoPadrao(GrupoAcessoPadrao grupoAcessoPadrao) {
        return grupoAcessoPadraoRepository.findById(grupoAcessoPadrao.getId()).orElseThrow();
    }

    protected void assertPersistedGrupoAcessoPadraoToMatchAllProperties(GrupoAcessoPadrao expectedGrupoAcessoPadrao) {
        assertGrupoAcessoPadraoAllPropertiesEquals(expectedGrupoAcessoPadrao, getPersistedGrupoAcessoPadrao(expectedGrupoAcessoPadrao));
    }

    protected void assertPersistedGrupoAcessoPadraoToMatchUpdatableProperties(GrupoAcessoPadrao expectedGrupoAcessoPadrao) {
        assertGrupoAcessoPadraoAllUpdatablePropertiesEquals(
            expectedGrupoAcessoPadrao,
            getPersistedGrupoAcessoPadrao(expectedGrupoAcessoPadrao)
        );
    }
}
