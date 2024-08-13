package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.CriterioAvaliacaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.CriterioAvaliacao;
import com.dobemcontabilidade.repository.CriterioAvaliacaoRepository;
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
 * Integration tests for the {@link CriterioAvaliacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CriterioAvaliacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/criterio-avaliacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CriterioAvaliacaoRepository criterioAvaliacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCriterioAvaliacaoMockMvc;

    private CriterioAvaliacao criterioAvaliacao;

    private CriterioAvaliacao insertedCriterioAvaliacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CriterioAvaliacao createEntity(EntityManager em) {
        CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return criterioAvaliacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CriterioAvaliacao createUpdatedEntity(EntityManager em) {
        CriterioAvaliacao criterioAvaliacao = new CriterioAvaliacao().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return criterioAvaliacao;
    }

    @BeforeEach
    public void initTest() {
        criterioAvaliacao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCriterioAvaliacao != null) {
            criterioAvaliacaoRepository.delete(insertedCriterioAvaliacao);
            insertedCriterioAvaliacao = null;
        }
    }

    @Test
    @Transactional
    void createCriterioAvaliacao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CriterioAvaliacao
        var returnedCriterioAvaliacao = om.readValue(
            restCriterioAvaliacaoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(criterioAvaliacao)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CriterioAvaliacao.class
        );

        // Validate the CriterioAvaliacao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCriterioAvaliacaoUpdatableFieldsEquals(returnedCriterioAvaliacao, getPersistedCriterioAvaliacao(returnedCriterioAvaliacao));

        insertedCriterioAvaliacao = returnedCriterioAvaliacao;
    }

    @Test
    @Transactional
    void createCriterioAvaliacaoWithExistingId() throws Exception {
        // Create the CriterioAvaliacao with an existing ID
        criterioAvaliacao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCriterioAvaliacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(criterioAvaliacao)))
            .andExpect(status().isBadRequest());

        // Validate the CriterioAvaliacao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        criterioAvaliacao.setNome(null);

        // Create the CriterioAvaliacao, which fails.

        restCriterioAvaliacaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(criterioAvaliacao)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCriterioAvaliacaos() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacao = criterioAvaliacaoRepository.saveAndFlush(criterioAvaliacao);

        // Get all the criterioAvaliacaoList
        restCriterioAvaliacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(criterioAvaliacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getCriterioAvaliacao() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacao = criterioAvaliacaoRepository.saveAndFlush(criterioAvaliacao);

        // Get the criterioAvaliacao
        restCriterioAvaliacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, criterioAvaliacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(criterioAvaliacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingCriterioAvaliacao() throws Exception {
        // Get the criterioAvaliacao
        restCriterioAvaliacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCriterioAvaliacao() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacao = criterioAvaliacaoRepository.saveAndFlush(criterioAvaliacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the criterioAvaliacao
        CriterioAvaliacao updatedCriterioAvaliacao = criterioAvaliacaoRepository.findById(criterioAvaliacao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCriterioAvaliacao are not directly saved in db
        em.detach(updatedCriterioAvaliacao);
        updatedCriterioAvaliacao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restCriterioAvaliacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCriterioAvaliacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCriterioAvaliacao))
            )
            .andExpect(status().isOk());

        // Validate the CriterioAvaliacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCriterioAvaliacaoToMatchAllProperties(updatedCriterioAvaliacao);
    }

    @Test
    @Transactional
    void putNonExistingCriterioAvaliacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, criterioAvaliacao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(criterioAvaliacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CriterioAvaliacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCriterioAvaliacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(criterioAvaliacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CriterioAvaliacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCriterioAvaliacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(criterioAvaliacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CriterioAvaliacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCriterioAvaliacaoWithPatch() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacao = criterioAvaliacaoRepository.saveAndFlush(criterioAvaliacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the criterioAvaliacao using partial update
        CriterioAvaliacao partialUpdatedCriterioAvaliacao = new CriterioAvaliacao();
        partialUpdatedCriterioAvaliacao.setId(criterioAvaliacao.getId());

        restCriterioAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCriterioAvaliacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCriterioAvaliacao))
            )
            .andExpect(status().isOk());

        // Validate the CriterioAvaliacao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCriterioAvaliacaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCriterioAvaliacao, criterioAvaliacao),
            getPersistedCriterioAvaliacao(criterioAvaliacao)
        );
    }

    @Test
    @Transactional
    void fullUpdateCriterioAvaliacaoWithPatch() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacao = criterioAvaliacaoRepository.saveAndFlush(criterioAvaliacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the criterioAvaliacao using partial update
        CriterioAvaliacao partialUpdatedCriterioAvaliacao = new CriterioAvaliacao();
        partialUpdatedCriterioAvaliacao.setId(criterioAvaliacao.getId());

        partialUpdatedCriterioAvaliacao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restCriterioAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCriterioAvaliacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCriterioAvaliacao))
            )
            .andExpect(status().isOk());

        // Validate the CriterioAvaliacao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCriterioAvaliacaoUpdatableFieldsEquals(
            partialUpdatedCriterioAvaliacao,
            getPersistedCriterioAvaliacao(partialUpdatedCriterioAvaliacao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCriterioAvaliacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, criterioAvaliacao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(criterioAvaliacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CriterioAvaliacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCriterioAvaliacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(criterioAvaliacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the CriterioAvaliacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCriterioAvaliacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(criterioAvaliacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CriterioAvaliacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCriterioAvaliacao() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacao = criterioAvaliacaoRepository.saveAndFlush(criterioAvaliacao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the criterioAvaliacao
        restCriterioAvaliacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, criterioAvaliacao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return criterioAvaliacaoRepository.count();
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

    protected CriterioAvaliacao getPersistedCriterioAvaliacao(CriterioAvaliacao criterioAvaliacao) {
        return criterioAvaliacaoRepository.findById(criterioAvaliacao.getId()).orElseThrow();
    }

    protected void assertPersistedCriterioAvaliacaoToMatchAllProperties(CriterioAvaliacao expectedCriterioAvaliacao) {
        assertCriterioAvaliacaoAllPropertiesEquals(expectedCriterioAvaliacao, getPersistedCriterioAvaliacao(expectedCriterioAvaliacao));
    }

    protected void assertPersistedCriterioAvaliacaoToMatchUpdatableProperties(CriterioAvaliacao expectedCriterioAvaliacao) {
        assertCriterioAvaliacaoAllUpdatablePropertiesEquals(
            expectedCriterioAvaliacao,
            getPersistedCriterioAvaliacao(expectedCriterioAvaliacao)
        );
    }
}
