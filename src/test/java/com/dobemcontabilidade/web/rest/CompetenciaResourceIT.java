package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.CompetenciaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Competencia;
import com.dobemcontabilidade.repository.CompetenciaRepository;
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
 * Integration tests for the {@link CompetenciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetenciaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;
    private static final Integer SMALLER_NUMERO = 1 - 1;

    private static final String ENTITY_API_URL = "/api/competencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CompetenciaRepository competenciaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetenciaMockMvc;

    private Competencia competencia;

    private Competencia insertedCompetencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competencia createEntity(EntityManager em) {
        Competencia competencia = new Competencia().nome(DEFAULT_NOME).numero(DEFAULT_NUMERO);
        return competencia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competencia createUpdatedEntity(EntityManager em) {
        Competencia competencia = new Competencia().nome(UPDATED_NOME).numero(UPDATED_NUMERO);
        return competencia;
    }

    @BeforeEach
    public void initTest() {
        competencia = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCompetencia != null) {
            competenciaRepository.delete(insertedCompetencia);
            insertedCompetencia = null;
        }
    }

    @Test
    @Transactional
    void createCompetencia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Competencia
        var returnedCompetencia = om.readValue(
            restCompetenciaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competencia)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Competencia.class
        );

        // Validate the Competencia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCompetenciaUpdatableFieldsEquals(returnedCompetencia, getPersistedCompetencia(returnedCompetencia));

        insertedCompetencia = returnedCompetencia;
    }

    @Test
    @Transactional
    void createCompetenciaWithExistingId() throws Exception {
        // Create the Competencia with an existing ID
        competencia.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competencia)))
            .andExpect(status().isBadRequest());

        // Validate the Competencia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompetencias() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList
        restCompetenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }

    @Test
    @Transactional
    void getCompetencia() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get the competencia
        restCompetenciaMockMvc
            .perform(get(ENTITY_API_URL_ID, competencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competencia.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    void getCompetenciasByIdFiltering() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        Long id = competencia.getId();

        defaultCompetenciaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCompetenciaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCompetenciaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompetenciasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where nome equals to
        defaultCompetenciaFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCompetenciasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where nome in
        defaultCompetenciaFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCompetenciasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where nome is not null
        defaultCompetenciaFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciasByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where nome contains
        defaultCompetenciaFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllCompetenciasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where nome does not contain
        defaultCompetenciaFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllCompetenciasByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where numero equals to
        defaultCompetenciaFiltering("numero.equals=" + DEFAULT_NUMERO, "numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllCompetenciasByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where numero in
        defaultCompetenciaFiltering("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO, "numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllCompetenciasByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where numero is not null
        defaultCompetenciaFiltering("numero.specified=true", "numero.specified=false");
    }

    @Test
    @Transactional
    void getAllCompetenciasByNumeroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where numero is greater than or equal to
        defaultCompetenciaFiltering("numero.greaterThanOrEqual=" + DEFAULT_NUMERO, "numero.greaterThanOrEqual=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllCompetenciasByNumeroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where numero is less than or equal to
        defaultCompetenciaFiltering("numero.lessThanOrEqual=" + DEFAULT_NUMERO, "numero.lessThanOrEqual=" + SMALLER_NUMERO);
    }

    @Test
    @Transactional
    void getAllCompetenciasByNumeroIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where numero is less than
        defaultCompetenciaFiltering("numero.lessThan=" + UPDATED_NUMERO, "numero.lessThan=" + DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    void getAllCompetenciasByNumeroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        // Get all the competenciaList where numero is greater than
        defaultCompetenciaFiltering("numero.greaterThan=" + SMALLER_NUMERO, "numero.greaterThan=" + DEFAULT_NUMERO);
    }

    private void defaultCompetenciaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultCompetenciaShouldBeFound(shouldBeFound);
        defaultCompetenciaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompetenciaShouldBeFound(String filter) throws Exception {
        restCompetenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));

        // Check, that the count call also returns 1
        restCompetenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompetenciaShouldNotBeFound(String filter) throws Exception {
        restCompetenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompetenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompetencia() throws Exception {
        // Get the competencia
        restCompetenciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompetencia() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the competencia
        Competencia updatedCompetencia = competenciaRepository.findById(competencia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCompetencia are not directly saved in db
        em.detach(updatedCompetencia);
        updatedCompetencia.nome(UPDATED_NOME).numero(UPDATED_NUMERO);

        restCompetenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCompetencia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCompetencia))
            )
            .andExpect(status().isOk());

        // Validate the Competencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCompetenciaToMatchAllProperties(updatedCompetencia);
    }

    @Test
    @Transactional
    void putNonExistingCompetencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competencia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competencia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(competencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(competencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(competencia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetenciaWithPatch() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the competencia using partial update
        Competencia partialUpdatedCompetencia = new Competencia();
        partialUpdatedCompetencia.setId(competencia.getId());

        partialUpdatedCompetencia.nome(UPDATED_NOME).numero(UPDATED_NUMERO);

        restCompetenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCompetencia))
            )
            .andExpect(status().isOk());

        // Validate the Competencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompetenciaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCompetencia, competencia),
            getPersistedCompetencia(competencia)
        );
    }

    @Test
    @Transactional
    void fullUpdateCompetenciaWithPatch() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the competencia using partial update
        Competencia partialUpdatedCompetencia = new Competencia();
        partialUpdatedCompetencia.setId(competencia.getId());

        partialUpdatedCompetencia.nome(UPDATED_NOME).numero(UPDATED_NUMERO);

        restCompetenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCompetencia))
            )
            .andExpect(status().isOk());

        // Validate the Competencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompetenciaUpdatableFieldsEquals(partialUpdatedCompetencia, getPersistedCompetencia(partialUpdatedCompetencia));
    }

    @Test
    @Transactional
    void patchNonExistingCompetencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competencia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(competencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(competencia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        competencia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetenciaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(competencia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetencia() throws Exception {
        // Initialize the database
        insertedCompetencia = competenciaRepository.saveAndFlush(competencia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the competencia
        restCompetenciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, competencia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return competenciaRepository.count();
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

    protected Competencia getPersistedCompetencia(Competencia competencia) {
        return competenciaRepository.findById(competencia.getId()).orElseThrow();
    }

    protected void assertPersistedCompetenciaToMatchAllProperties(Competencia expectedCompetencia) {
        assertCompetenciaAllPropertiesEquals(expectedCompetencia, getPersistedCompetencia(expectedCompetencia));
    }

    protected void assertPersistedCompetenciaToMatchUpdatableProperties(Competencia expectedCompetencia) {
        assertCompetenciaAllUpdatablePropertiesEquals(expectedCompetencia, getPersistedCompetencia(expectedCompetencia));
    }
}
