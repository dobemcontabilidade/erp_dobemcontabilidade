package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EsferaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Esfera;
import com.dobemcontabilidade.repository.EsferaRepository;
import com.dobemcontabilidade.service.dto.EsferaDTO;
import com.dobemcontabilidade.service.mapper.EsferaMapper;
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
 * Integration tests for the {@link EsferaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EsferaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/esferas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EsferaRepository esferaRepository;

    @Autowired
    private EsferaMapper esferaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEsferaMockMvc;

    private Esfera esfera;

    private Esfera insertedEsfera;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Esfera createEntity(EntityManager em) {
        Esfera esfera = new Esfera().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return esfera;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Esfera createUpdatedEntity(EntityManager em) {
        Esfera esfera = new Esfera().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return esfera;
    }

    @BeforeEach
    public void initTest() {
        esfera = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEsfera != null) {
            esferaRepository.delete(insertedEsfera);
            insertedEsfera = null;
        }
    }

    @Test
    @Transactional
    void createEsfera() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Esfera
        EsferaDTO esferaDTO = esferaMapper.toDto(esfera);
        var returnedEsferaDTO = om.readValue(
            restEsferaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(esferaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EsferaDTO.class
        );

        // Validate the Esfera in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEsfera = esferaMapper.toEntity(returnedEsferaDTO);
        assertEsferaUpdatableFieldsEquals(returnedEsfera, getPersistedEsfera(returnedEsfera));

        insertedEsfera = returnedEsfera;
    }

    @Test
    @Transactional
    void createEsferaWithExistingId() throws Exception {
        // Create the Esfera with an existing ID
        esfera.setId(1L);
        EsferaDTO esferaDTO = esferaMapper.toDto(esfera);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEsferaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(esferaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Esfera in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEsferas() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        // Get all the esferaList
        restEsferaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esfera.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getEsfera() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        // Get the esfera
        restEsferaMockMvc
            .perform(get(ENTITY_API_URL_ID, esfera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(esfera.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getEsferasByIdFiltering() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        Long id = esfera.getId();

        defaultEsferaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEsferaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEsferaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEsferasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        // Get all the esferaList where nome equals to
        defaultEsferaFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEsferasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        // Get all the esferaList where nome in
        defaultEsferaFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEsferasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        // Get all the esferaList where nome is not null
        defaultEsferaFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllEsferasByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        // Get all the esferaList where nome contains
        defaultEsferaFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllEsferasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        // Get all the esferaList where nome does not contain
        defaultEsferaFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    private void defaultEsferaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEsferaShouldBeFound(shouldBeFound);
        defaultEsferaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEsferaShouldBeFound(String filter) throws Exception {
        restEsferaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esfera.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restEsferaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEsferaShouldNotBeFound(String filter) throws Exception {
        restEsferaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEsferaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEsfera() throws Exception {
        // Get the esfera
        restEsferaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEsfera() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the esfera
        Esfera updatedEsfera = esferaRepository.findById(esfera.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEsfera are not directly saved in db
        em.detach(updatedEsfera);
        updatedEsfera.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        EsferaDTO esferaDTO = esferaMapper.toDto(updatedEsfera);

        restEsferaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, esferaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(esferaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Esfera in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEsferaToMatchAllProperties(updatedEsfera);
    }

    @Test
    @Transactional
    void putNonExistingEsfera() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        esfera.setId(longCount.incrementAndGet());

        // Create the Esfera
        EsferaDTO esferaDTO = esferaMapper.toDto(esfera);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEsferaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, esferaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(esferaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Esfera in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEsfera() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        esfera.setId(longCount.incrementAndGet());

        // Create the Esfera
        EsferaDTO esferaDTO = esferaMapper.toDto(esfera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsferaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(esferaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Esfera in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEsfera() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        esfera.setId(longCount.incrementAndGet());

        // Create the Esfera
        EsferaDTO esferaDTO = esferaMapper.toDto(esfera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsferaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(esferaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Esfera in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEsferaWithPatch() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the esfera using partial update
        Esfera partialUpdatedEsfera = new Esfera();
        partialUpdatedEsfera.setId(esfera.getId());

        partialUpdatedEsfera.nome(UPDATED_NOME);

        restEsferaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEsfera.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEsfera))
            )
            .andExpect(status().isOk());

        // Validate the Esfera in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEsferaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEsfera, esfera), getPersistedEsfera(esfera));
    }

    @Test
    @Transactional
    void fullUpdateEsferaWithPatch() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the esfera using partial update
        Esfera partialUpdatedEsfera = new Esfera();
        partialUpdatedEsfera.setId(esfera.getId());

        partialUpdatedEsfera.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restEsferaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEsfera.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEsfera))
            )
            .andExpect(status().isOk());

        // Validate the Esfera in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEsferaUpdatableFieldsEquals(partialUpdatedEsfera, getPersistedEsfera(partialUpdatedEsfera));
    }

    @Test
    @Transactional
    void patchNonExistingEsfera() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        esfera.setId(longCount.incrementAndGet());

        // Create the Esfera
        EsferaDTO esferaDTO = esferaMapper.toDto(esfera);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEsferaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, esferaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(esferaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Esfera in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEsfera() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        esfera.setId(longCount.incrementAndGet());

        // Create the Esfera
        EsferaDTO esferaDTO = esferaMapper.toDto(esfera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsferaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(esferaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Esfera in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEsfera() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        esfera.setId(longCount.incrementAndGet());

        // Create the Esfera
        EsferaDTO esferaDTO = esferaMapper.toDto(esfera);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsferaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(esferaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Esfera in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEsfera() throws Exception {
        // Initialize the database
        insertedEsfera = esferaRepository.saveAndFlush(esfera);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the esfera
        restEsferaMockMvc
            .perform(delete(ENTITY_API_URL_ID, esfera.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return esferaRepository.count();
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

    protected Esfera getPersistedEsfera(Esfera esfera) {
        return esferaRepository.findById(esfera.getId()).orElseThrow();
    }

    protected void assertPersistedEsferaToMatchAllProperties(Esfera expectedEsfera) {
        assertEsferaAllPropertiesEquals(expectedEsfera, getPersistedEsfera(expectedEsfera));
    }

    protected void assertPersistedEsferaToMatchUpdatableProperties(Esfera expectedEsfera) {
        assertEsferaAllUpdatablePropertiesEquals(expectedEsfera, getPersistedEsfera(expectedEsfera));
    }
}
