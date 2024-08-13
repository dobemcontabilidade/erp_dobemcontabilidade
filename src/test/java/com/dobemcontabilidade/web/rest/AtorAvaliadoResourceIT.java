package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AtorAvaliadoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AtorAvaliado;
import com.dobemcontabilidade.repository.AtorAvaliadoRepository;
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
 * Integration tests for the {@link AtorAvaliadoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AtorAvaliadoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String ENTITY_API_URL = "/api/ator-avaliados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AtorAvaliadoRepository atorAvaliadoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAtorAvaliadoMockMvc;

    private AtorAvaliado atorAvaliado;

    private AtorAvaliado insertedAtorAvaliado;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtorAvaliado createEntity(EntityManager em) {
        AtorAvaliado atorAvaliado = new AtorAvaliado().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO).ativo(DEFAULT_ATIVO);
        return atorAvaliado;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AtorAvaliado createUpdatedEntity(EntityManager em) {
        AtorAvaliado atorAvaliado = new AtorAvaliado().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).ativo(UPDATED_ATIVO);
        return atorAvaliado;
    }

    @BeforeEach
    public void initTest() {
        atorAvaliado = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAtorAvaliado != null) {
            atorAvaliadoRepository.delete(insertedAtorAvaliado);
            insertedAtorAvaliado = null;
        }
    }

    @Test
    @Transactional
    void createAtorAvaliado() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AtorAvaliado
        var returnedAtorAvaliado = om.readValue(
            restAtorAvaliadoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atorAvaliado)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AtorAvaliado.class
        );

        // Validate the AtorAvaliado in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAtorAvaliadoUpdatableFieldsEquals(returnedAtorAvaliado, getPersistedAtorAvaliado(returnedAtorAvaliado));

        insertedAtorAvaliado = returnedAtorAvaliado;
    }

    @Test
    @Transactional
    void createAtorAvaliadoWithExistingId() throws Exception {
        // Create the AtorAvaliado with an existing ID
        atorAvaliado.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtorAvaliadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atorAvaliado)))
            .andExpect(status().isBadRequest());

        // Validate the AtorAvaliado in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        atorAvaliado.setNome(null);

        // Create the AtorAvaliado, which fails.

        restAtorAvaliadoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atorAvaliado)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAtorAvaliados() throws Exception {
        // Initialize the database
        insertedAtorAvaliado = atorAvaliadoRepository.saveAndFlush(atorAvaliado);

        // Get all the atorAvaliadoList
        restAtorAvaliadoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atorAvaliado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @Test
    @Transactional
    void getAtorAvaliado() throws Exception {
        // Initialize the database
        insertedAtorAvaliado = atorAvaliadoRepository.saveAndFlush(atorAvaliado);

        // Get the atorAvaliado
        restAtorAvaliadoMockMvc
            .perform(get(ENTITY_API_URL_ID, atorAvaliado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(atorAvaliado.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAtorAvaliado() throws Exception {
        // Get the atorAvaliado
        restAtorAvaliadoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAtorAvaliado() throws Exception {
        // Initialize the database
        insertedAtorAvaliado = atorAvaliadoRepository.saveAndFlush(atorAvaliado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the atorAvaliado
        AtorAvaliado updatedAtorAvaliado = atorAvaliadoRepository.findById(atorAvaliado.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAtorAvaliado are not directly saved in db
        em.detach(updatedAtorAvaliado);
        updatedAtorAvaliado.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).ativo(UPDATED_ATIVO);

        restAtorAvaliadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAtorAvaliado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAtorAvaliado))
            )
            .andExpect(status().isOk());

        // Validate the AtorAvaliado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAtorAvaliadoToMatchAllProperties(updatedAtorAvaliado);
    }

    @Test
    @Transactional
    void putNonExistingAtorAvaliado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atorAvaliado.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtorAvaliadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, atorAvaliado.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(atorAvaliado))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtorAvaliado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAtorAvaliado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atorAvaliado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtorAvaliadoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(atorAvaliado))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtorAvaliado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAtorAvaliado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atorAvaliado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtorAvaliadoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(atorAvaliado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AtorAvaliado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAtorAvaliadoWithPatch() throws Exception {
        // Initialize the database
        insertedAtorAvaliado = atorAvaliadoRepository.saveAndFlush(atorAvaliado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the atorAvaliado using partial update
        AtorAvaliado partialUpdatedAtorAvaliado = new AtorAvaliado();
        partialUpdatedAtorAvaliado.setId(atorAvaliado.getId());

        partialUpdatedAtorAvaliado.nome(UPDATED_NOME).ativo(UPDATED_ATIVO);

        restAtorAvaliadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtorAvaliado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAtorAvaliado))
            )
            .andExpect(status().isOk());

        // Validate the AtorAvaliado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAtorAvaliadoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAtorAvaliado, atorAvaliado),
            getPersistedAtorAvaliado(atorAvaliado)
        );
    }

    @Test
    @Transactional
    void fullUpdateAtorAvaliadoWithPatch() throws Exception {
        // Initialize the database
        insertedAtorAvaliado = atorAvaliadoRepository.saveAndFlush(atorAvaliado);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the atorAvaliado using partial update
        AtorAvaliado partialUpdatedAtorAvaliado = new AtorAvaliado();
        partialUpdatedAtorAvaliado.setId(atorAvaliado.getId());

        partialUpdatedAtorAvaliado.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).ativo(UPDATED_ATIVO);

        restAtorAvaliadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAtorAvaliado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAtorAvaliado))
            )
            .andExpect(status().isOk());

        // Validate the AtorAvaliado in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAtorAvaliadoUpdatableFieldsEquals(partialUpdatedAtorAvaliado, getPersistedAtorAvaliado(partialUpdatedAtorAvaliado));
    }

    @Test
    @Transactional
    void patchNonExistingAtorAvaliado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atorAvaliado.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAtorAvaliadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, atorAvaliado.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(atorAvaliado))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtorAvaliado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAtorAvaliado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atorAvaliado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtorAvaliadoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(atorAvaliado))
            )
            .andExpect(status().isBadRequest());

        // Validate the AtorAvaliado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAtorAvaliado() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        atorAvaliado.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAtorAvaliadoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(atorAvaliado)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AtorAvaliado in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAtorAvaliado() throws Exception {
        // Initialize the database
        insertedAtorAvaliado = atorAvaliadoRepository.saveAndFlush(atorAvaliado);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the atorAvaliado
        restAtorAvaliadoMockMvc
            .perform(delete(ENTITY_API_URL_ID, atorAvaliado.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return atorAvaliadoRepository.count();
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

    protected AtorAvaliado getPersistedAtorAvaliado(AtorAvaliado atorAvaliado) {
        return atorAvaliadoRepository.findById(atorAvaliado.getId()).orElseThrow();
    }

    protected void assertPersistedAtorAvaliadoToMatchAllProperties(AtorAvaliado expectedAtorAvaliado) {
        assertAtorAvaliadoAllPropertiesEquals(expectedAtorAvaliado, getPersistedAtorAvaliado(expectedAtorAvaliado));
    }

    protected void assertPersistedAtorAvaliadoToMatchUpdatableProperties(AtorAvaliado expectedAtorAvaliado) {
        assertAtorAvaliadoAllUpdatablePropertiesEquals(expectedAtorAvaliado, getPersistedAtorAvaliado(expectedAtorAvaliado));
    }
}
