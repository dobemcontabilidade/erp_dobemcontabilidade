package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FluxoExecucaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.FluxoExecucao;
import com.dobemcontabilidade.repository.FluxoExecucaoRepository;
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
 * Integration tests for the {@link FluxoExecucaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FluxoExecucaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fluxo-execucaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FluxoExecucaoRepository fluxoExecucaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFluxoExecucaoMockMvc;

    private FluxoExecucao fluxoExecucao;

    private FluxoExecucao insertedFluxoExecucao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FluxoExecucao createEntity(EntityManager em) {
        FluxoExecucao fluxoExecucao = new FluxoExecucao().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return fluxoExecucao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FluxoExecucao createUpdatedEntity(EntityManager em) {
        FluxoExecucao fluxoExecucao = new FluxoExecucao().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return fluxoExecucao;
    }

    @BeforeEach
    public void initTest() {
        fluxoExecucao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFluxoExecucao != null) {
            fluxoExecucaoRepository.delete(insertedFluxoExecucao);
            insertedFluxoExecucao = null;
        }
    }

    @Test
    @Transactional
    void createFluxoExecucao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FluxoExecucao
        var returnedFluxoExecucao = om.readValue(
            restFluxoExecucaoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fluxoExecucao)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FluxoExecucao.class
        );

        // Validate the FluxoExecucao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFluxoExecucaoUpdatableFieldsEquals(returnedFluxoExecucao, getPersistedFluxoExecucao(returnedFluxoExecucao));

        insertedFluxoExecucao = returnedFluxoExecucao;
    }

    @Test
    @Transactional
    void createFluxoExecucaoWithExistingId() throws Exception {
        // Create the FluxoExecucao with an existing ID
        fluxoExecucao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFluxoExecucaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fluxoExecucao)))
            .andExpect(status().isBadRequest());

        // Validate the FluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFluxoExecucaos() throws Exception {
        // Initialize the database
        insertedFluxoExecucao = fluxoExecucaoRepository.saveAndFlush(fluxoExecucao);

        // Get all the fluxoExecucaoList
        restFluxoExecucaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fluxoExecucao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getFluxoExecucao() throws Exception {
        // Initialize the database
        insertedFluxoExecucao = fluxoExecucaoRepository.saveAndFlush(fluxoExecucao);

        // Get the fluxoExecucao
        restFluxoExecucaoMockMvc
            .perform(get(ENTITY_API_URL_ID, fluxoExecucao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fluxoExecucao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingFluxoExecucao() throws Exception {
        // Get the fluxoExecucao
        restFluxoExecucaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFluxoExecucao() throws Exception {
        // Initialize the database
        insertedFluxoExecucao = fluxoExecucaoRepository.saveAndFlush(fluxoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fluxoExecucao
        FluxoExecucao updatedFluxoExecucao = fluxoExecucaoRepository.findById(fluxoExecucao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFluxoExecucao are not directly saved in db
        em.detach(updatedFluxoExecucao);
        updatedFluxoExecucao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restFluxoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFluxoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFluxoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the FluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFluxoExecucaoToMatchAllProperties(updatedFluxoExecucao);
    }

    @Test
    @Transactional
    void putNonExistingFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFluxoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fluxoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoExecucaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fluxoExecucao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFluxoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedFluxoExecucao = fluxoExecucaoRepository.saveAndFlush(fluxoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fluxoExecucao using partial update
        FluxoExecucao partialUpdatedFluxoExecucao = new FluxoExecucao();
        partialUpdatedFluxoExecucao.setId(fluxoExecucao.getId());

        partialUpdatedFluxoExecucao.descricao(UPDATED_DESCRICAO);

        restFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFluxoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFluxoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the FluxoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFluxoExecucaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFluxoExecucao, fluxoExecucao),
            getPersistedFluxoExecucao(fluxoExecucao)
        );
    }

    @Test
    @Transactional
    void fullUpdateFluxoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedFluxoExecucao = fluxoExecucaoRepository.saveAndFlush(fluxoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fluxoExecucao using partial update
        FluxoExecucao partialUpdatedFluxoExecucao = new FluxoExecucao();
        partialUpdatedFluxoExecucao.setId(fluxoExecucao.getId());

        partialUpdatedFluxoExecucao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFluxoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFluxoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the FluxoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFluxoExecucaoUpdatableFieldsEquals(partialUpdatedFluxoExecucao, getPersistedFluxoExecucao(partialUpdatedFluxoExecucao));
    }

    @Test
    @Transactional
    void patchNonExistingFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fluxoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoExecucaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fluxoExecucao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFluxoExecucao() throws Exception {
        // Initialize the database
        insertedFluxoExecucao = fluxoExecucaoRepository.saveAndFlush(fluxoExecucao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fluxoExecucao
        restFluxoExecucaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fluxoExecucao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fluxoExecucaoRepository.count();
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

    protected FluxoExecucao getPersistedFluxoExecucao(FluxoExecucao fluxoExecucao) {
        return fluxoExecucaoRepository.findById(fluxoExecucao.getId()).orElseThrow();
    }

    protected void assertPersistedFluxoExecucaoToMatchAllProperties(FluxoExecucao expectedFluxoExecucao) {
        assertFluxoExecucaoAllPropertiesEquals(expectedFluxoExecucao, getPersistedFluxoExecucao(expectedFluxoExecucao));
    }

    protected void assertPersistedFluxoExecucaoToMatchUpdatableProperties(FluxoExecucao expectedFluxoExecucao) {
        assertFluxoExecucaoAllUpdatablePropertiesEquals(expectedFluxoExecucao, getPersistedFluxoExecucao(expectedFluxoExecucao));
    }
}
