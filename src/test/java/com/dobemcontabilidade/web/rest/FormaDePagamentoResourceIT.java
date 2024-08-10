package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FormaDePagamentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.repository.FormaDePagamentoRepository;
import com.dobemcontabilidade.service.dto.FormaDePagamentoDTO;
import com.dobemcontabilidade.service.mapper.FormaDePagamentoMapper;
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
 * Integration tests for the {@link FormaDePagamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormaDePagamentoResourceIT {

    private static final String DEFAULT_FORMA = "AAAAAAAAAA";
    private static final String UPDATED_FORMA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIVEL = false;
    private static final Boolean UPDATED_DISPONIVEL = true;

    private static final String ENTITY_API_URL = "/api/forma-de-pagamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FormaDePagamentoRepository formaDePagamentoRepository;

    @Autowired
    private FormaDePagamentoMapper formaDePagamentoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormaDePagamentoMockMvc;

    private FormaDePagamento formaDePagamento;

    private FormaDePagamento insertedFormaDePagamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaDePagamento createEntity(EntityManager em) {
        FormaDePagamento formaDePagamento = new FormaDePagamento().forma(DEFAULT_FORMA).disponivel(DEFAULT_DISPONIVEL);
        return formaDePagamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormaDePagamento createUpdatedEntity(EntityManager em) {
        FormaDePagamento formaDePagamento = new FormaDePagamento().forma(UPDATED_FORMA).disponivel(UPDATED_DISPONIVEL);
        return formaDePagamento;
    }

    @BeforeEach
    public void initTest() {
        formaDePagamento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFormaDePagamento != null) {
            formaDePagamentoRepository.delete(insertedFormaDePagamento);
            insertedFormaDePagamento = null;
        }
    }

    @Test
    @Transactional
    void createFormaDePagamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FormaDePagamento
        FormaDePagamentoDTO formaDePagamentoDTO = formaDePagamentoMapper.toDto(formaDePagamento);
        var returnedFormaDePagamentoDTO = om.readValue(
            restFormaDePagamentoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formaDePagamentoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FormaDePagamentoDTO.class
        );

        // Validate the FormaDePagamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFormaDePagamento = formaDePagamentoMapper.toEntity(returnedFormaDePagamentoDTO);
        assertFormaDePagamentoUpdatableFieldsEquals(returnedFormaDePagamento, getPersistedFormaDePagamento(returnedFormaDePagamento));

        insertedFormaDePagamento = returnedFormaDePagamento;
    }

    @Test
    @Transactional
    void createFormaDePagamentoWithExistingId() throws Exception {
        // Create the FormaDePagamento with an existing ID
        formaDePagamento.setId(1L);
        FormaDePagamentoDTO formaDePagamentoDTO = formaDePagamentoMapper.toDto(formaDePagamento);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormaDePagamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formaDePagamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFormaDePagamentos() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get all the formaDePagamentoList
        restFormaDePagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaDePagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].forma").value(hasItem(DEFAULT_FORMA)))
            .andExpect(jsonPath("$.[*].disponivel").value(hasItem(DEFAULT_DISPONIVEL.booleanValue())));
    }

    @Test
    @Transactional
    void getFormaDePagamento() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get the formaDePagamento
        restFormaDePagamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, formaDePagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formaDePagamento.getId().intValue()))
            .andExpect(jsonPath("$.forma").value(DEFAULT_FORMA))
            .andExpect(jsonPath("$.disponivel").value(DEFAULT_DISPONIVEL.booleanValue()));
    }

    @Test
    @Transactional
    void getFormaDePagamentosByIdFiltering() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        Long id = formaDePagamento.getId();

        defaultFormaDePagamentoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFormaDePagamentoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFormaDePagamentoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFormaDePagamentosByFormaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get all the formaDePagamentoList where forma equals to
        defaultFormaDePagamentoFiltering("forma.equals=" + DEFAULT_FORMA, "forma.equals=" + UPDATED_FORMA);
    }

    @Test
    @Transactional
    void getAllFormaDePagamentosByFormaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get all the formaDePagamentoList where forma in
        defaultFormaDePagamentoFiltering("forma.in=" + DEFAULT_FORMA + "," + UPDATED_FORMA, "forma.in=" + UPDATED_FORMA);
    }

    @Test
    @Transactional
    void getAllFormaDePagamentosByFormaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get all the formaDePagamentoList where forma is not null
        defaultFormaDePagamentoFiltering("forma.specified=true", "forma.specified=false");
    }

    @Test
    @Transactional
    void getAllFormaDePagamentosByFormaContainsSomething() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get all the formaDePagamentoList where forma contains
        defaultFormaDePagamentoFiltering("forma.contains=" + DEFAULT_FORMA, "forma.contains=" + UPDATED_FORMA);
    }

    @Test
    @Transactional
    void getAllFormaDePagamentosByFormaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get all the formaDePagamentoList where forma does not contain
        defaultFormaDePagamentoFiltering("forma.doesNotContain=" + UPDATED_FORMA, "forma.doesNotContain=" + DEFAULT_FORMA);
    }

    @Test
    @Transactional
    void getAllFormaDePagamentosByDisponivelIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get all the formaDePagamentoList where disponivel equals to
        defaultFormaDePagamentoFiltering("disponivel.equals=" + DEFAULT_DISPONIVEL, "disponivel.equals=" + UPDATED_DISPONIVEL);
    }

    @Test
    @Transactional
    void getAllFormaDePagamentosByDisponivelIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get all the formaDePagamentoList where disponivel in
        defaultFormaDePagamentoFiltering(
            "disponivel.in=" + DEFAULT_DISPONIVEL + "," + UPDATED_DISPONIVEL,
            "disponivel.in=" + UPDATED_DISPONIVEL
        );
    }

    @Test
    @Transactional
    void getAllFormaDePagamentosByDisponivelIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        // Get all the formaDePagamentoList where disponivel is not null
        defaultFormaDePagamentoFiltering("disponivel.specified=true", "disponivel.specified=false");
    }

    private void defaultFormaDePagamentoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFormaDePagamentoShouldBeFound(shouldBeFound);
        defaultFormaDePagamentoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFormaDePagamentoShouldBeFound(String filter) throws Exception {
        restFormaDePagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaDePagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].forma").value(hasItem(DEFAULT_FORMA)))
            .andExpect(jsonPath("$.[*].disponivel").value(hasItem(DEFAULT_DISPONIVEL.booleanValue())));

        // Check, that the count call also returns 1
        restFormaDePagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFormaDePagamentoShouldNotBeFound(String filter) throws Exception {
        restFormaDePagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFormaDePagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFormaDePagamento() throws Exception {
        // Get the formaDePagamento
        restFormaDePagamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFormaDePagamento() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formaDePagamento
        FormaDePagamento updatedFormaDePagamento = formaDePagamentoRepository.findById(formaDePagamento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFormaDePagamento are not directly saved in db
        em.detach(updatedFormaDePagamento);
        updatedFormaDePagamento.forma(UPDATED_FORMA).disponivel(UPDATED_DISPONIVEL);
        FormaDePagamentoDTO formaDePagamentoDTO = formaDePagamentoMapper.toDto(updatedFormaDePagamento);

        restFormaDePagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formaDePagamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formaDePagamentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFormaDePagamentoToMatchAllProperties(updatedFormaDePagamento);
    }

    @Test
    @Transactional
    void putNonExistingFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // Create the FormaDePagamento
        FormaDePagamentoDTO formaDePagamentoDTO = formaDePagamentoMapper.toDto(formaDePagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formaDePagamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formaDePagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // Create the FormaDePagamento
        FormaDePagamentoDTO formaDePagamentoDTO = formaDePagamentoMapper.toDto(formaDePagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(formaDePagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // Create the FormaDePagamento
        FormaDePagamentoDTO formaDePagamentoDTO = formaDePagamentoMapper.toDto(formaDePagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(formaDePagamentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFormaDePagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formaDePagamento using partial update
        FormaDePagamento partialUpdatedFormaDePagamento = new FormaDePagamento();
        partialUpdatedFormaDePagamento.setId(formaDePagamento.getId());

        restFormaDePagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormaDePagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormaDePagamento))
            )
            .andExpect(status().isOk());

        // Validate the FormaDePagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormaDePagamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFormaDePagamento, formaDePagamento),
            getPersistedFormaDePagamento(formaDePagamento)
        );
    }

    @Test
    @Transactional
    void fullUpdateFormaDePagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the formaDePagamento using partial update
        FormaDePagamento partialUpdatedFormaDePagamento = new FormaDePagamento();
        partialUpdatedFormaDePagamento.setId(formaDePagamento.getId());

        partialUpdatedFormaDePagamento.forma(UPDATED_FORMA).disponivel(UPDATED_DISPONIVEL);

        restFormaDePagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormaDePagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFormaDePagamento))
            )
            .andExpect(status().isOk());

        // Validate the FormaDePagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFormaDePagamentoUpdatableFieldsEquals(
            partialUpdatedFormaDePagamento,
            getPersistedFormaDePagamento(partialUpdatedFormaDePagamento)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // Create the FormaDePagamento
        FormaDePagamentoDTO formaDePagamentoDTO = formaDePagamentoMapper.toDto(formaDePagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formaDePagamentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formaDePagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // Create the FormaDePagamento
        FormaDePagamentoDTO formaDePagamentoDTO = formaDePagamentoMapper.toDto(formaDePagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(formaDePagamentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFormaDePagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        formaDePagamento.setId(longCount.incrementAndGet());

        // Create the FormaDePagamento
        FormaDePagamentoDTO formaDePagamentoDTO = formaDePagamentoMapper.toDto(formaDePagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormaDePagamentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(formaDePagamentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormaDePagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFormaDePagamento() throws Exception {
        // Initialize the database
        insertedFormaDePagamento = formaDePagamentoRepository.saveAndFlush(formaDePagamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the formaDePagamento
        restFormaDePagamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, formaDePagamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return formaDePagamentoRepository.count();
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

    protected FormaDePagamento getPersistedFormaDePagamento(FormaDePagamento formaDePagamento) {
        return formaDePagamentoRepository.findById(formaDePagamento.getId()).orElseThrow();
    }

    protected void assertPersistedFormaDePagamentoToMatchAllProperties(FormaDePagamento expectedFormaDePagamento) {
        assertFormaDePagamentoAllPropertiesEquals(expectedFormaDePagamento, getPersistedFormaDePagamento(expectedFormaDePagamento));
    }

    protected void assertPersistedFormaDePagamentoToMatchUpdatableProperties(FormaDePagamento expectedFormaDePagamento) {
        assertFormaDePagamentoAllUpdatablePropertiesEquals(
            expectedFormaDePagamento,
            getPersistedFormaDePagamento(expectedFormaDePagamento)
        );
    }
}
