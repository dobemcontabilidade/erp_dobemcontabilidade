package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PaisAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Pais;
import com.dobemcontabilidade.repository.PaisRepository;
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
 * Integration tests for the {@link PaisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaisResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NACIONALIDADE = "AAAAAAAAAA";
    private static final String UPDATED_NACIONALIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pais";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaisMockMvc;

    private Pais pais;

    private Pais insertedPais;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pais createEntity(EntityManager em) {
        Pais pais = new Pais().nome(DEFAULT_NOME).nacionalidade(DEFAULT_NACIONALIDADE).sigla(DEFAULT_SIGLA);
        return pais;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pais createUpdatedEntity(EntityManager em) {
        Pais pais = new Pais().nome(UPDATED_NOME).nacionalidade(UPDATED_NACIONALIDADE).sigla(UPDATED_SIGLA);
        return pais;
    }

    @BeforeEach
    public void initTest() {
        pais = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPais != null) {
            paisRepository.delete(insertedPais);
            insertedPais = null;
        }
    }

    @Test
    @Transactional
    void createPais() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pais
        var returnedPais = om.readValue(
            restPaisMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pais)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pais.class
        );

        // Validate the Pais in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPaisUpdatableFieldsEquals(returnedPais, getPersistedPais(returnedPais));

        insertedPais = returnedPais;
    }

    @Test
    @Transactional
    void createPaisWithExistingId() throws Exception {
        // Create the Pais with an existing ID
        pais.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pais)))
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPais() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList
        restPaisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pais.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nacionalidade").value(hasItem(DEFAULT_NACIONALIDADE)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));
    }

    @Test
    @Transactional
    void getPais() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get the pais
        restPaisMockMvc
            .perform(get(ENTITY_API_URL_ID, pais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pais.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.nacionalidade").value(DEFAULT_NACIONALIDADE))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA));
    }

    @Test
    @Transactional
    void getPaisByIdFiltering() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        Long id = pais.getId();

        defaultPaisFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPaisFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPaisFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaisByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where nome equals to
        defaultPaisFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPaisByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where nome in
        defaultPaisFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPaisByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where nome is not null
        defaultPaisFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllPaisByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where nome contains
        defaultPaisFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPaisByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where nome does not contain
        defaultPaisFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllPaisByNacionalidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where nacionalidade equals to
        defaultPaisFiltering("nacionalidade.equals=" + DEFAULT_NACIONALIDADE, "nacionalidade.equals=" + UPDATED_NACIONALIDADE);
    }

    @Test
    @Transactional
    void getAllPaisByNacionalidadeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where nacionalidade in
        defaultPaisFiltering(
            "nacionalidade.in=" + DEFAULT_NACIONALIDADE + "," + UPDATED_NACIONALIDADE,
            "nacionalidade.in=" + UPDATED_NACIONALIDADE
        );
    }

    @Test
    @Transactional
    void getAllPaisByNacionalidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where nacionalidade is not null
        defaultPaisFiltering("nacionalidade.specified=true", "nacionalidade.specified=false");
    }

    @Test
    @Transactional
    void getAllPaisByNacionalidadeContainsSomething() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where nacionalidade contains
        defaultPaisFiltering("nacionalidade.contains=" + DEFAULT_NACIONALIDADE, "nacionalidade.contains=" + UPDATED_NACIONALIDADE);
    }

    @Test
    @Transactional
    void getAllPaisByNacionalidadeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where nacionalidade does not contain
        defaultPaisFiltering(
            "nacionalidade.doesNotContain=" + UPDATED_NACIONALIDADE,
            "nacionalidade.doesNotContain=" + DEFAULT_NACIONALIDADE
        );
    }

    @Test
    @Transactional
    void getAllPaisBySiglaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where sigla equals to
        defaultPaisFiltering("sigla.equals=" + DEFAULT_SIGLA, "sigla.equals=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void getAllPaisBySiglaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where sigla in
        defaultPaisFiltering("sigla.in=" + DEFAULT_SIGLA + "," + UPDATED_SIGLA, "sigla.in=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void getAllPaisBySiglaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where sigla is not null
        defaultPaisFiltering("sigla.specified=true", "sigla.specified=false");
    }

    @Test
    @Transactional
    void getAllPaisBySiglaContainsSomething() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where sigla contains
        defaultPaisFiltering("sigla.contains=" + DEFAULT_SIGLA, "sigla.contains=" + UPDATED_SIGLA);
    }

    @Test
    @Transactional
    void getAllPaisBySiglaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        // Get all the paisList where sigla does not contain
        defaultPaisFiltering("sigla.doesNotContain=" + UPDATED_SIGLA, "sigla.doesNotContain=" + DEFAULT_SIGLA);
    }

    private void defaultPaisFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPaisShouldBeFound(shouldBeFound);
        defaultPaisShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaisShouldBeFound(String filter) throws Exception {
        restPaisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pais.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].nacionalidade").value(hasItem(DEFAULT_NACIONALIDADE)))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA)));

        // Check, that the count call also returns 1
        restPaisMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaisShouldNotBeFound(String filter) throws Exception {
        restPaisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaisMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPais() throws Exception {
        // Get the pais
        restPaisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPais() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pais
        Pais updatedPais = paisRepository.findById(pais.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPais are not directly saved in db
        em.detach(updatedPais);
        updatedPais.nome(UPDATED_NOME).nacionalidade(UPDATED_NACIONALIDADE).sigla(UPDATED_SIGLA);

        restPaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPais.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPais))
            )
            .andExpect(status().isOk());

        // Validate the Pais in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPaisToMatchAllProperties(updatedPais);
    }

    @Test
    @Transactional
    void putNonExistingPais() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pais.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(put(ENTITY_API_URL_ID, pais.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pais)))
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPais() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pais.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pais))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPais() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pais.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pais)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pais in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaisWithPatch() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pais using partial update
        Pais partialUpdatedPais = new Pais();
        partialUpdatedPais.setId(pais.getId());

        partialUpdatedPais.nome(UPDATED_NOME).nacionalidade(UPDATED_NACIONALIDADE);

        restPaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPais.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPais))
            )
            .andExpect(status().isOk());

        // Validate the Pais in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaisUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPais, pais), getPersistedPais(pais));
    }

    @Test
    @Transactional
    void fullUpdatePaisWithPatch() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pais using partial update
        Pais partialUpdatedPais = new Pais();
        partialUpdatedPais.setId(pais.getId());

        partialUpdatedPais.nome(UPDATED_NOME).nacionalidade(UPDATED_NACIONALIDADE).sigla(UPDATED_SIGLA);

        restPaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPais.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPais))
            )
            .andExpect(status().isOk());

        // Validate the Pais in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaisUpdatableFieldsEquals(partialUpdatedPais, getPersistedPais(partialUpdatedPais));
    }

    @Test
    @Transactional
    void patchNonExistingPais() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pais.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(patch(ENTITY_API_URL_ID, pais.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pais)))
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPais() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pais.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pais))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPais() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pais.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaisMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pais)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pais in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePais() throws Exception {
        // Initialize the database
        insertedPais = paisRepository.saveAndFlush(pais);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pais
        restPaisMockMvc
            .perform(delete(ENTITY_API_URL_ID, pais.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return paisRepository.count();
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

    protected Pais getPersistedPais(Pais pais) {
        return paisRepository.findById(pais.getId()).orElseThrow();
    }

    protected void assertPersistedPaisToMatchAllProperties(Pais expectedPais) {
        assertPaisAllPropertiesEquals(expectedPais, getPersistedPais(expectedPais));
    }

    protected void assertPersistedPaisToMatchUpdatableProperties(Pais expectedPais) {
        assertPaisAllUpdatablePropertiesEquals(expectedPais, getPersistedPais(expectedPais));
    }
}
