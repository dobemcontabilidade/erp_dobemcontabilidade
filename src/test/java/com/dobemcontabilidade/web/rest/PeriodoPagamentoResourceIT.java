package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PeriodoPagamentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.repository.PeriodoPagamentoRepository;
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
 * Integration tests for the {@link PeriodoPagamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PeriodoPagamentoResourceIT {

    private static final String DEFAULT_PERIODO = "AAAAAAAAAA";
    private static final String UPDATED_PERIODO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_DIAS = 1;
    private static final Integer UPDATED_NUMERO_DIAS = 2;

    private static final String ENTITY_API_URL = "/api/periodo-pagamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PeriodoPagamentoRepository periodoPagamentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeriodoPagamentoMockMvc;

    private PeriodoPagamento periodoPagamento;

    private PeriodoPagamento insertedPeriodoPagamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodoPagamento createEntity(EntityManager em) {
        PeriodoPagamento periodoPagamento = new PeriodoPagamento().periodo(DEFAULT_PERIODO).numeroDias(DEFAULT_NUMERO_DIAS);
        return periodoPagamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodoPagamento createUpdatedEntity(EntityManager em) {
        PeriodoPagamento periodoPagamento = new PeriodoPagamento().periodo(UPDATED_PERIODO).numeroDias(UPDATED_NUMERO_DIAS);
        return periodoPagamento;
    }

    @BeforeEach
    public void initTest() {
        periodoPagamento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPeriodoPagamento != null) {
            periodoPagamentoRepository.delete(insertedPeriodoPagamento);
            insertedPeriodoPagamento = null;
        }
    }

    @Test
    @Transactional
    void createPeriodoPagamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PeriodoPagamento
        var returnedPeriodoPagamento = om.readValue(
            restPeriodoPagamentoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(periodoPagamento))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PeriodoPagamento.class
        );

        // Validate the PeriodoPagamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPeriodoPagamentoUpdatableFieldsEquals(returnedPeriodoPagamento, getPersistedPeriodoPagamento(returnedPeriodoPagamento));

        insertedPeriodoPagamento = returnedPeriodoPagamento;
    }

    @Test
    @Transactional
    void createPeriodoPagamentoWithExistingId() throws Exception {
        // Create the PeriodoPagamento with an existing ID
        periodoPagamento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoPagamentoMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(periodoPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentos() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList
        restPeriodoPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)))
            .andExpect(jsonPath("$.[*].numeroDias").value(hasItem(DEFAULT_NUMERO_DIAS)));
    }

    @Test
    @Transactional
    void getPeriodoPagamento() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get the periodoPagamento
        restPeriodoPagamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, periodoPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(periodoPagamento.getId().intValue()))
            .andExpect(jsonPath("$.periodo").value(DEFAULT_PERIODO))
            .andExpect(jsonPath("$.numeroDias").value(DEFAULT_NUMERO_DIAS));
    }

    @Test
    @Transactional
    void getNonExistingPeriodoPagamento() throws Exception {
        // Get the periodoPagamento
        restPeriodoPagamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPeriodoPagamento() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the periodoPagamento
        PeriodoPagamento updatedPeriodoPagamento = periodoPagamentoRepository.findById(periodoPagamento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPeriodoPagamento are not directly saved in db
        em.detach(updatedPeriodoPagamento);
        updatedPeriodoPagamento.periodo(UPDATED_PERIODO).numeroDias(UPDATED_NUMERO_DIAS);

        restPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPeriodoPagamento.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPeriodoPagamento))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPeriodoPagamentoToMatchAllProperties(updatedPeriodoPagamento);
    }

    @Test
    @Transactional
    void putNonExistingPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periodoPagamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodoPagamento.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(periodoPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periodoPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(periodoPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periodoPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(periodoPagamento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePeriodoPagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the periodoPagamento using partial update
        PeriodoPagamento partialUpdatedPeriodoPagamento = new PeriodoPagamento();
        partialUpdatedPeriodoPagamento.setId(periodoPagamento.getId());

        restPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodoPagamento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPeriodoPagamento))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoPagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPeriodoPagamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPeriodoPagamento, periodoPagamento),
            getPersistedPeriodoPagamento(periodoPagamento)
        );
    }

    @Test
    @Transactional
    void fullUpdatePeriodoPagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the periodoPagamento using partial update
        PeriodoPagamento partialUpdatedPeriodoPagamento = new PeriodoPagamento();
        partialUpdatedPeriodoPagamento.setId(periodoPagamento.getId());

        partialUpdatedPeriodoPagamento.periodo(UPDATED_PERIODO).numeroDias(UPDATED_NUMERO_DIAS);

        restPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodoPagamento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPeriodoPagamento))
            )
            .andExpect(status().isOk());

        // Validate the PeriodoPagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPeriodoPagamentoUpdatableFieldsEquals(
            partialUpdatedPeriodoPagamento,
            getPersistedPeriodoPagamento(partialUpdatedPeriodoPagamento)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periodoPagamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, periodoPagamento.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(periodoPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periodoPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(periodoPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the PeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPeriodoPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        periodoPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(periodoPagamento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PeriodoPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePeriodoPagamento() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the periodoPagamento
        restPeriodoPagamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, periodoPagamento.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return periodoPagamentoRepository.count();
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

    protected PeriodoPagamento getPersistedPeriodoPagamento(PeriodoPagamento periodoPagamento) {
        return periodoPagamentoRepository.findById(periodoPagamento.getId()).orElseThrow();
    }

    protected void assertPersistedPeriodoPagamentoToMatchAllProperties(PeriodoPagamento expectedPeriodoPagamento) {
        assertPeriodoPagamentoAllPropertiesEquals(expectedPeriodoPagamento, getPersistedPeriodoPagamento(expectedPeriodoPagamento));
    }

    protected void assertPersistedPeriodoPagamentoToMatchUpdatableProperties(PeriodoPagamento expectedPeriodoPagamento) {
        assertPeriodoPagamentoAllUpdatablePropertiesEquals(
            expectedPeriodoPagamento,
            getPersistedPeriodoPagamento(expectedPeriodoPagamento)
        );
    }
}
