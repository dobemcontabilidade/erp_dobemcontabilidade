package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.RamoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.repository.RamoRepository;
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
 * Integration tests for the {@link RamoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RamoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ramos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RamoRepository ramoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRamoMockMvc;

    private Ramo ramo;

    private Ramo insertedRamo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ramo createEntity(EntityManager em) {
        Ramo ramo = new Ramo().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return ramo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ramo createUpdatedEntity(EntityManager em) {
        Ramo ramo = new Ramo().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return ramo;
    }

    @BeforeEach
    public void initTest() {
        ramo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRamo != null) {
            ramoRepository.delete(insertedRamo);
            insertedRamo = null;
        }
    }

    @Test
    @Transactional
    void createRamo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Ramo
        var returnedRamo = om.readValue(
            restRamoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ramo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Ramo.class
        );

        // Validate the Ramo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRamoUpdatableFieldsEquals(returnedRamo, getPersistedRamo(returnedRamo));

        insertedRamo = returnedRamo;
    }

    @Test
    @Transactional
    void createRamoWithExistingId() throws Exception {
        // Create the Ramo with an existing ID
        ramo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRamoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ramo)))
            .andExpect(status().isBadRequest());

        // Validate the Ramo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRamos() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        // Get all the ramoList
        restRamoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ramo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getRamo() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        // Get the ramo
        restRamoMockMvc
            .perform(get(ENTITY_API_URL_ID, ramo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ramo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getRamosByIdFiltering() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        Long id = ramo.getId();

        defaultRamoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultRamoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultRamoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRamosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        // Get all the ramoList where nome equals to
        defaultRamoFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRamosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        // Get all the ramoList where nome in
        defaultRamoFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRamosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        // Get all the ramoList where nome is not null
        defaultRamoFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllRamosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        // Get all the ramoList where nome contains
        defaultRamoFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllRamosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        // Get all the ramoList where nome does not contain
        defaultRamoFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    private void defaultRamoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultRamoShouldBeFound(shouldBeFound);
        defaultRamoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRamoShouldBeFound(String filter) throws Exception {
        restRamoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ramo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restRamoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRamoShouldNotBeFound(String filter) throws Exception {
        restRamoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRamoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRamo() throws Exception {
        // Get the ramo
        restRamoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRamo() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ramo
        Ramo updatedRamo = ramoRepository.findById(ramo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRamo are not directly saved in db
        em.detach(updatedRamo);
        updatedRamo.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restRamoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRamo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRamo))
            )
            .andExpect(status().isOk());

        // Validate the Ramo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRamoToMatchAllProperties(updatedRamo);
    }

    @Test
    @Transactional
    void putNonExistingRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ramo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRamoMockMvc
            .perform(put(ENTITY_API_URL_ID, ramo.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ramo)))
            .andExpect(status().isBadRequest());

        // Validate the Ramo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ramo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRamoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ramo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ramo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ramo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRamoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ramo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ramo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRamoWithPatch() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ramo using partial update
        Ramo partialUpdatedRamo = new Ramo();
        partialUpdatedRamo.setId(ramo.getId());

        partialUpdatedRamo.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRamo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRamo))
            )
            .andExpect(status().isOk());

        // Validate the Ramo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRamoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRamo, ramo), getPersistedRamo(ramo));
    }

    @Test
    @Transactional
    void fullUpdateRamoWithPatch() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ramo using partial update
        Ramo partialUpdatedRamo = new Ramo();
        partialUpdatedRamo.setId(ramo.getId());

        partialUpdatedRamo.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRamo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRamo))
            )
            .andExpect(status().isOk());

        // Validate the Ramo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRamoUpdatableFieldsEquals(partialUpdatedRamo, getPersistedRamo(partialUpdatedRamo));
    }

    @Test
    @Transactional
    void patchNonExistingRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ramo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRamoMockMvc
            .perform(patch(ENTITY_API_URL_ID, ramo.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ramo)))
            .andExpect(status().isBadRequest());

        // Validate the Ramo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ramo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ramo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ramo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ramo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRamoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ramo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ramo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRamo() throws Exception {
        // Initialize the database
        insertedRamo = ramoRepository.saveAndFlush(ramo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ramo
        restRamoMockMvc
            .perform(delete(ENTITY_API_URL_ID, ramo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ramoRepository.count();
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

    protected Ramo getPersistedRamo(Ramo ramo) {
        return ramoRepository.findById(ramo.getId()).orElseThrow();
    }

    protected void assertPersistedRamoToMatchAllProperties(Ramo expectedRamo) {
        assertRamoAllPropertiesEquals(expectedRamo, getPersistedRamo(expectedRamo));
    }

    protected void assertPersistedRamoToMatchUpdatableProperties(Ramo expectedRamo) {
        assertRamoAllUpdatablePropertiesEquals(expectedRamo, getPersistedRamo(expectedRamo));
    }
}
