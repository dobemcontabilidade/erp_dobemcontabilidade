package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.SistemaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Sistema;
import com.dobemcontabilidade.repository.SistemaRepository;
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
 * Integration tests for the {@link SistemaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SistemaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sistemas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SistemaRepository sistemaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSistemaMockMvc;

    private Sistema sistema;

    private Sistema insertedSistema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sistema createEntity(EntityManager em) {
        Sistema sistema = new Sistema().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return sistema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sistema createUpdatedEntity(EntityManager em) {
        Sistema sistema = new Sistema().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return sistema;
    }

    @BeforeEach
    public void initTest() {
        sistema = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSistema != null) {
            sistemaRepository.delete(insertedSistema);
            insertedSistema = null;
        }
    }

    @Test
    @Transactional
    void createSistema() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sistema
        var returnedSistema = om.readValue(
            restSistemaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sistema)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sistema.class
        );

        // Validate the Sistema in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSistemaUpdatableFieldsEquals(returnedSistema, getPersistedSistema(returnedSistema));

        insertedSistema = returnedSistema;
    }

    @Test
    @Transactional
    void createSistemaWithExistingId() throws Exception {
        // Create the Sistema with an existing ID
        sistema.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSistemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sistema)))
            .andExpect(status().isBadRequest());

        // Validate the Sistema in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSistemas() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList
        restSistemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sistema.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getSistema() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get the sistema
        restSistemaMockMvc
            .perform(get(ENTITY_API_URL_ID, sistema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sistema.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getSistemasByIdFiltering() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        Long id = sistema.getId();

        defaultSistemaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSistemaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSistemaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSistemasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList where nome equals to
        defaultSistemaFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllSistemasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList where nome in
        defaultSistemaFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllSistemasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList where nome is not null
        defaultSistemaFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllSistemasByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList where nome contains
        defaultSistemaFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllSistemasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList where nome does not contain
        defaultSistemaFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllSistemasByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList where descricao equals to
        defaultSistemaFiltering("descricao.equals=" + DEFAULT_DESCRICAO, "descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllSistemasByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList where descricao in
        defaultSistemaFiltering("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO, "descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllSistemasByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList where descricao is not null
        defaultSistemaFiltering("descricao.specified=true", "descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllSistemasByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList where descricao contains
        defaultSistemaFiltering("descricao.contains=" + DEFAULT_DESCRICAO, "descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllSistemasByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        // Get all the sistemaList where descricao does not contain
        defaultSistemaFiltering("descricao.doesNotContain=" + UPDATED_DESCRICAO, "descricao.doesNotContain=" + DEFAULT_DESCRICAO);
    }

    private void defaultSistemaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSistemaShouldBeFound(shouldBeFound);
        defaultSistemaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSistemaShouldBeFound(String filter) throws Exception {
        restSistemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sistema.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));

        // Check, that the count call also returns 1
        restSistemaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSistemaShouldNotBeFound(String filter) throws Exception {
        restSistemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSistemaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSistema() throws Exception {
        // Get the sistema
        restSistemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSistema() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sistema
        Sistema updatedSistema = sistemaRepository.findById(sistema.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSistema are not directly saved in db
        em.detach(updatedSistema);
        updatedSistema.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restSistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSistema.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSistema))
            )
            .andExpect(status().isOk());

        // Validate the Sistema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSistemaToMatchAllProperties(updatedSistema);
    }

    @Test
    @Transactional
    void putNonExistingSistema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sistema.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(put(ENTITY_API_URL_ID, sistema.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sistema)))
            .andExpect(status().isBadRequest());

        // Validate the Sistema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSistema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sistema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sistema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSistema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sistema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sistema)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sistema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSistemaWithPatch() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sistema using partial update
        Sistema partialUpdatedSistema = new Sistema();
        partialUpdatedSistema.setId(sistema.getId());

        restSistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSistema))
            )
            .andExpect(status().isOk());

        // Validate the Sistema in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSistemaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSistema, sistema), getPersistedSistema(sistema));
    }

    @Test
    @Transactional
    void fullUpdateSistemaWithPatch() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sistema using partial update
        Sistema partialUpdatedSistema = new Sistema();
        partialUpdatedSistema.setId(sistema.getId());

        partialUpdatedSistema.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restSistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSistema.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSistema))
            )
            .andExpect(status().isOk());

        // Validate the Sistema in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSistemaUpdatableFieldsEquals(partialUpdatedSistema, getPersistedSistema(partialUpdatedSistema));
    }

    @Test
    @Transactional
    void patchNonExistingSistema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sistema.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sistema.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sistema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSistema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sistema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sistema))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sistema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSistema() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sistema.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSistemaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sistema)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sistema in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSistema() throws Exception {
        // Initialize the database
        insertedSistema = sistemaRepository.saveAndFlush(sistema);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sistema
        restSistemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, sistema.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sistemaRepository.count();
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

    protected Sistema getPersistedSistema(Sistema sistema) {
        return sistemaRepository.findById(sistema.getId()).orElseThrow();
    }

    protected void assertPersistedSistemaToMatchAllProperties(Sistema expectedSistema) {
        assertSistemaAllPropertiesEquals(expectedSistema, getPersistedSistema(expectedSistema));
    }

    protected void assertPersistedSistemaToMatchUpdatableProperties(Sistema expectedSistema) {
        assertSistemaAllUpdatablePropertiesEquals(expectedSistema, getPersistedSistema(expectedSistema));
    }
}
