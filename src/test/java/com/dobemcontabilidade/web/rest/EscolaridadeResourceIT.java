package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EscolaridadeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Escolaridade;
import com.dobemcontabilidade.repository.EscolaridadeRepository;
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
 * Integration tests for the {@link EscolaridadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EscolaridadeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/escolaridades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EscolaridadeRepository escolaridadeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEscolaridadeMockMvc;

    private Escolaridade escolaridade;

    private Escolaridade insertedEscolaridade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Escolaridade createEntity(EntityManager em) {
        Escolaridade escolaridade = new Escolaridade().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return escolaridade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Escolaridade createUpdatedEntity(EntityManager em) {
        Escolaridade escolaridade = new Escolaridade().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return escolaridade;
    }

    @BeforeEach
    public void initTest() {
        escolaridade = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEscolaridade != null) {
            escolaridadeRepository.delete(insertedEscolaridade);
            insertedEscolaridade = null;
        }
    }

    @Test
    @Transactional
    void createEscolaridade() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Escolaridade
        var returnedEscolaridade = om.readValue(
            restEscolaridadeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escolaridade)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Escolaridade.class
        );

        // Validate the Escolaridade in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEscolaridadeUpdatableFieldsEquals(returnedEscolaridade, getPersistedEscolaridade(returnedEscolaridade));

        insertedEscolaridade = returnedEscolaridade;
    }

    @Test
    @Transactional
    void createEscolaridadeWithExistingId() throws Exception {
        // Create the Escolaridade with an existing ID
        escolaridade.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscolaridadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escolaridade)))
            .andExpect(status().isBadRequest());

        // Validate the Escolaridade in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        escolaridade.setNome(null);

        // Create the Escolaridade, which fails.

        restEscolaridadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escolaridade)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEscolaridades() throws Exception {
        // Initialize the database
        insertedEscolaridade = escolaridadeRepository.saveAndFlush(escolaridade);

        // Get all the escolaridadeList
        restEscolaridadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escolaridade.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getEscolaridade() throws Exception {
        // Initialize the database
        insertedEscolaridade = escolaridadeRepository.saveAndFlush(escolaridade);

        // Get the escolaridade
        restEscolaridadeMockMvc
            .perform(get(ENTITY_API_URL_ID, escolaridade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(escolaridade.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEscolaridade() throws Exception {
        // Get the escolaridade
        restEscolaridadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEscolaridade() throws Exception {
        // Initialize the database
        insertedEscolaridade = escolaridadeRepository.saveAndFlush(escolaridade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the escolaridade
        Escolaridade updatedEscolaridade = escolaridadeRepository.findById(escolaridade.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEscolaridade are not directly saved in db
        em.detach(updatedEscolaridade);
        updatedEscolaridade.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restEscolaridadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEscolaridade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEscolaridade))
            )
            .andExpect(status().isOk());

        // Validate the Escolaridade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEscolaridadeToMatchAllProperties(updatedEscolaridade);
    }

    @Test
    @Transactional
    void putNonExistingEscolaridade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridade.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEscolaridadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, escolaridade.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(escolaridade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Escolaridade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEscolaridade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscolaridadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(escolaridade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Escolaridade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEscolaridade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscolaridadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(escolaridade)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Escolaridade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEscolaridadeWithPatch() throws Exception {
        // Initialize the database
        insertedEscolaridade = escolaridadeRepository.saveAndFlush(escolaridade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the escolaridade using partial update
        Escolaridade partialUpdatedEscolaridade = new Escolaridade();
        partialUpdatedEscolaridade.setId(escolaridade.getId());

        partialUpdatedEscolaridade.nome(UPDATED_NOME);

        restEscolaridadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEscolaridade.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEscolaridade))
            )
            .andExpect(status().isOk());

        // Validate the Escolaridade in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEscolaridadeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEscolaridade, escolaridade),
            getPersistedEscolaridade(escolaridade)
        );
    }

    @Test
    @Transactional
    void fullUpdateEscolaridadeWithPatch() throws Exception {
        // Initialize the database
        insertedEscolaridade = escolaridadeRepository.saveAndFlush(escolaridade);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the escolaridade using partial update
        Escolaridade partialUpdatedEscolaridade = new Escolaridade();
        partialUpdatedEscolaridade.setId(escolaridade.getId());

        partialUpdatedEscolaridade.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restEscolaridadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEscolaridade.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEscolaridade))
            )
            .andExpect(status().isOk());

        // Validate the Escolaridade in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEscolaridadeUpdatableFieldsEquals(partialUpdatedEscolaridade, getPersistedEscolaridade(partialUpdatedEscolaridade));
    }

    @Test
    @Transactional
    void patchNonExistingEscolaridade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridade.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEscolaridadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, escolaridade.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(escolaridade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Escolaridade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEscolaridade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscolaridadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(escolaridade))
            )
            .andExpect(status().isBadRequest());

        // Validate the Escolaridade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEscolaridade() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        escolaridade.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEscolaridadeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(escolaridade)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Escolaridade in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEscolaridade() throws Exception {
        // Initialize the database
        insertedEscolaridade = escolaridadeRepository.saveAndFlush(escolaridade);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the escolaridade
        restEscolaridadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, escolaridade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return escolaridadeRepository.count();
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

    protected Escolaridade getPersistedEscolaridade(Escolaridade escolaridade) {
        return escolaridadeRepository.findById(escolaridade.getId()).orElseThrow();
    }

    protected void assertPersistedEscolaridadeToMatchAllProperties(Escolaridade expectedEscolaridade) {
        assertEscolaridadeAllPropertiesEquals(expectedEscolaridade, getPersistedEscolaridade(expectedEscolaridade));
    }

    protected void assertPersistedEscolaridadeToMatchUpdatableProperties(Escolaridade expectedEscolaridade) {
        assertEscolaridadeAllUpdatablePropertiesEquals(expectedEscolaridade, getPersistedEscolaridade(expectedEscolaridade));
    }
}
