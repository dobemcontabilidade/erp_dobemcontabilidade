package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FrequenciaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Frequencia;
import com.dobemcontabilidade.repository.FrequenciaRepository;
import com.dobemcontabilidade.service.dto.FrequenciaDTO;
import com.dobemcontabilidade.service.mapper.FrequenciaMapper;
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
 * Integration tests for the {@link FrequenciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FrequenciaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORIDADE = "AAAAAAAAAA";
    private static final String UPDATED_PRIORIDADE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_DIAS = 1;
    private static final Integer UPDATED_NUMERO_DIAS = 2;
    private static final Integer SMALLER_NUMERO_DIAS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/frequencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FrequenciaRepository frequenciaRepository;

    @Autowired
    private FrequenciaMapper frequenciaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFrequenciaMockMvc;

    private Frequencia frequencia;

    private Frequencia insertedFrequencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frequencia createEntity(EntityManager em) {
        Frequencia frequencia = new Frequencia()
            .nome(DEFAULT_NOME)
            .prioridade(DEFAULT_PRIORIDADE)
            .descricao(DEFAULT_DESCRICAO)
            .numeroDias(DEFAULT_NUMERO_DIAS);
        return frequencia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frequencia createUpdatedEntity(EntityManager em) {
        Frequencia frequencia = new Frequencia()
            .nome(UPDATED_NOME)
            .prioridade(UPDATED_PRIORIDADE)
            .descricao(UPDATED_DESCRICAO)
            .numeroDias(UPDATED_NUMERO_DIAS);
        return frequencia;
    }

    @BeforeEach
    public void initTest() {
        frequencia = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFrequencia != null) {
            frequenciaRepository.delete(insertedFrequencia);
            insertedFrequencia = null;
        }
    }

    @Test
    @Transactional
    void createFrequencia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Frequencia
        FrequenciaDTO frequenciaDTO = frequenciaMapper.toDto(frequencia);
        var returnedFrequenciaDTO = om.readValue(
            restFrequenciaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(frequenciaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FrequenciaDTO.class
        );

        // Validate the Frequencia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFrequencia = frequenciaMapper.toEntity(returnedFrequenciaDTO);
        assertFrequenciaUpdatableFieldsEquals(returnedFrequencia, getPersistedFrequencia(returnedFrequencia));

        insertedFrequencia = returnedFrequencia;
    }

    @Test
    @Transactional
    void createFrequenciaWithExistingId() throws Exception {
        // Create the Frequencia with an existing ID
        frequencia.setId(1L);
        FrequenciaDTO frequenciaDTO = frequenciaMapper.toDto(frequencia);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFrequenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(frequenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Frequencia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFrequencias() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList
        restFrequenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frequencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].numeroDias").value(hasItem(DEFAULT_NUMERO_DIAS)));
    }

    @Test
    @Transactional
    void getFrequencia() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get the frequencia
        restFrequenciaMockMvc
            .perform(get(ENTITY_API_URL_ID, frequencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(frequencia.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.prioridade").value(DEFAULT_PRIORIDADE))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.numeroDias").value(DEFAULT_NUMERO_DIAS));
    }

    @Test
    @Transactional
    void getFrequenciasByIdFiltering() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        Long id = frequencia.getId();

        defaultFrequenciaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFrequenciaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFrequenciaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFrequenciasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where nome equals to
        defaultFrequenciaFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFrequenciasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where nome in
        defaultFrequenciaFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFrequenciasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where nome is not null
        defaultFrequenciaFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllFrequenciasByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where nome contains
        defaultFrequenciaFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFrequenciasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where nome does not contain
        defaultFrequenciaFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllFrequenciasByPrioridadeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where prioridade equals to
        defaultFrequenciaFiltering("prioridade.equals=" + DEFAULT_PRIORIDADE, "prioridade.equals=" + UPDATED_PRIORIDADE);
    }

    @Test
    @Transactional
    void getAllFrequenciasByPrioridadeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where prioridade in
        defaultFrequenciaFiltering("prioridade.in=" + DEFAULT_PRIORIDADE + "," + UPDATED_PRIORIDADE, "prioridade.in=" + UPDATED_PRIORIDADE);
    }

    @Test
    @Transactional
    void getAllFrequenciasByPrioridadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where prioridade is not null
        defaultFrequenciaFiltering("prioridade.specified=true", "prioridade.specified=false");
    }

    @Test
    @Transactional
    void getAllFrequenciasByPrioridadeContainsSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where prioridade contains
        defaultFrequenciaFiltering("prioridade.contains=" + DEFAULT_PRIORIDADE, "prioridade.contains=" + UPDATED_PRIORIDADE);
    }

    @Test
    @Transactional
    void getAllFrequenciasByPrioridadeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where prioridade does not contain
        defaultFrequenciaFiltering("prioridade.doesNotContain=" + UPDATED_PRIORIDADE, "prioridade.doesNotContain=" + DEFAULT_PRIORIDADE);
    }

    @Test
    @Transactional
    void getAllFrequenciasByNumeroDiasIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where numeroDias equals to
        defaultFrequenciaFiltering("numeroDias.equals=" + DEFAULT_NUMERO_DIAS, "numeroDias.equals=" + UPDATED_NUMERO_DIAS);
    }

    @Test
    @Transactional
    void getAllFrequenciasByNumeroDiasIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where numeroDias in
        defaultFrequenciaFiltering(
            "numeroDias.in=" + DEFAULT_NUMERO_DIAS + "," + UPDATED_NUMERO_DIAS,
            "numeroDias.in=" + UPDATED_NUMERO_DIAS
        );
    }

    @Test
    @Transactional
    void getAllFrequenciasByNumeroDiasIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where numeroDias is not null
        defaultFrequenciaFiltering("numeroDias.specified=true", "numeroDias.specified=false");
    }

    @Test
    @Transactional
    void getAllFrequenciasByNumeroDiasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where numeroDias is greater than or equal to
        defaultFrequenciaFiltering(
            "numeroDias.greaterThanOrEqual=" + DEFAULT_NUMERO_DIAS,
            "numeroDias.greaterThanOrEqual=" + UPDATED_NUMERO_DIAS
        );
    }

    @Test
    @Transactional
    void getAllFrequenciasByNumeroDiasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where numeroDias is less than or equal to
        defaultFrequenciaFiltering(
            "numeroDias.lessThanOrEqual=" + DEFAULT_NUMERO_DIAS,
            "numeroDias.lessThanOrEqual=" + SMALLER_NUMERO_DIAS
        );
    }

    @Test
    @Transactional
    void getAllFrequenciasByNumeroDiasIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where numeroDias is less than
        defaultFrequenciaFiltering("numeroDias.lessThan=" + UPDATED_NUMERO_DIAS, "numeroDias.lessThan=" + DEFAULT_NUMERO_DIAS);
    }

    @Test
    @Transactional
    void getAllFrequenciasByNumeroDiasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        // Get all the frequenciaList where numeroDias is greater than
        defaultFrequenciaFiltering("numeroDias.greaterThan=" + SMALLER_NUMERO_DIAS, "numeroDias.greaterThan=" + DEFAULT_NUMERO_DIAS);
    }

    private void defaultFrequenciaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFrequenciaShouldBeFound(shouldBeFound);
        defaultFrequenciaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFrequenciaShouldBeFound(String filter) throws Exception {
        restFrequenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frequencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].prioridade").value(hasItem(DEFAULT_PRIORIDADE)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].numeroDias").value(hasItem(DEFAULT_NUMERO_DIAS)));

        // Check, that the count call also returns 1
        restFrequenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFrequenciaShouldNotBeFound(String filter) throws Exception {
        restFrequenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFrequenciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFrequencia() throws Exception {
        // Get the frequencia
        restFrequenciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFrequencia() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the frequencia
        Frequencia updatedFrequencia = frequenciaRepository.findById(frequencia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFrequencia are not directly saved in db
        em.detach(updatedFrequencia);
        updatedFrequencia.nome(UPDATED_NOME).prioridade(UPDATED_PRIORIDADE).descricao(UPDATED_DESCRICAO).numeroDias(UPDATED_NUMERO_DIAS);
        FrequenciaDTO frequenciaDTO = frequenciaMapper.toDto(updatedFrequencia);

        restFrequenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frequenciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(frequenciaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Frequencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFrequenciaToMatchAllProperties(updatedFrequencia);
    }

    @Test
    @Transactional
    void putNonExistingFrequencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frequencia.setId(longCount.incrementAndGet());

        // Create the Frequencia
        FrequenciaDTO frequenciaDTO = frequenciaMapper.toDto(frequencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrequenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frequenciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(frequenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frequencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFrequencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frequencia.setId(longCount.incrementAndGet());

        // Create the Frequencia
        FrequenciaDTO frequenciaDTO = frequenciaMapper.toDto(frequencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrequenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(frequenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frequencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFrequencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frequencia.setId(longCount.incrementAndGet());

        // Create the Frequencia
        FrequenciaDTO frequenciaDTO = frequenciaMapper.toDto(frequencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrequenciaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(frequenciaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frequencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFrequenciaWithPatch() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the frequencia using partial update
        Frequencia partialUpdatedFrequencia = new Frequencia();
        partialUpdatedFrequencia.setId(frequencia.getId());

        partialUpdatedFrequencia.nome(UPDATED_NOME).numeroDias(UPDATED_NUMERO_DIAS);

        restFrequenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrequencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFrequencia))
            )
            .andExpect(status().isOk());

        // Validate the Frequencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFrequenciaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFrequencia, frequencia),
            getPersistedFrequencia(frequencia)
        );
    }

    @Test
    @Transactional
    void fullUpdateFrequenciaWithPatch() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the frequencia using partial update
        Frequencia partialUpdatedFrequencia = new Frequencia();
        partialUpdatedFrequencia.setId(frequencia.getId());

        partialUpdatedFrequencia
            .nome(UPDATED_NOME)
            .prioridade(UPDATED_PRIORIDADE)
            .descricao(UPDATED_DESCRICAO)
            .numeroDias(UPDATED_NUMERO_DIAS);

        restFrequenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrequencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFrequencia))
            )
            .andExpect(status().isOk());

        // Validate the Frequencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFrequenciaUpdatableFieldsEquals(partialUpdatedFrequencia, getPersistedFrequencia(partialUpdatedFrequencia));
    }

    @Test
    @Transactional
    void patchNonExistingFrequencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frequencia.setId(longCount.incrementAndGet());

        // Create the Frequencia
        FrequenciaDTO frequenciaDTO = frequenciaMapper.toDto(frequencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrequenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, frequenciaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(frequenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frequencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFrequencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frequencia.setId(longCount.incrementAndGet());

        // Create the Frequencia
        FrequenciaDTO frequenciaDTO = frequenciaMapper.toDto(frequencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrequenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(frequenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frequencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFrequencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        frequencia.setId(longCount.incrementAndGet());

        // Create the Frequencia
        FrequenciaDTO frequenciaDTO = frequenciaMapper.toDto(frequencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrequenciaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(frequenciaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frequencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFrequencia() throws Exception {
        // Initialize the database
        insertedFrequencia = frequenciaRepository.saveAndFlush(frequencia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the frequencia
        restFrequenciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, frequencia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return frequenciaRepository.count();
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

    protected Frequencia getPersistedFrequencia(Frequencia frequencia) {
        return frequenciaRepository.findById(frequencia.getId()).orElseThrow();
    }

    protected void assertPersistedFrequenciaToMatchAllProperties(Frequencia expectedFrequencia) {
        assertFrequenciaAllPropertiesEquals(expectedFrequencia, getPersistedFrequencia(expectedFrequencia));
    }

    protected void assertPersistedFrequenciaToMatchUpdatableProperties(Frequencia expectedFrequencia) {
        assertFrequenciaAllUpdatablePropertiesEquals(expectedFrequencia, getPersistedFrequencia(expectedFrequencia));
    }
}
