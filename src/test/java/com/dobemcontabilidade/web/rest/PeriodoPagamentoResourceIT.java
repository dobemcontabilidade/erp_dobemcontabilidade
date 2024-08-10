package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PeriodoPagamentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.repository.PeriodoPagamentoRepository;
import com.dobemcontabilidade.service.dto.PeriodoPagamentoDTO;
import com.dobemcontabilidade.service.mapper.PeriodoPagamentoMapper;
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
    private static final Integer SMALLER_NUMERO_DIAS = 1 - 1;

    private static final String DEFAULT_ID_PLAN_GNET = "AAAAAAAAAA";
    private static final String UPDATED_ID_PLAN_GNET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/periodo-pagamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PeriodoPagamentoRepository periodoPagamentoRepository;

    @Autowired
    private PeriodoPagamentoMapper periodoPagamentoMapper;

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
        PeriodoPagamento periodoPagamento = new PeriodoPagamento()
            .periodo(DEFAULT_PERIODO)
            .numeroDias(DEFAULT_NUMERO_DIAS)
            .idPlanGnet(DEFAULT_ID_PLAN_GNET);
        return periodoPagamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeriodoPagamento createUpdatedEntity(EntityManager em) {
        PeriodoPagamento periodoPagamento = new PeriodoPagamento()
            .periodo(UPDATED_PERIODO)
            .numeroDias(UPDATED_NUMERO_DIAS)
            .idPlanGnet(UPDATED_ID_PLAN_GNET);
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
        PeriodoPagamentoDTO periodoPagamentoDTO = periodoPagamentoMapper.toDto(periodoPagamento);
        var returnedPeriodoPagamentoDTO = om.readValue(
            restPeriodoPagamentoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(periodoPagamentoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PeriodoPagamentoDTO.class
        );

        // Validate the PeriodoPagamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPeriodoPagamento = periodoPagamentoMapper.toEntity(returnedPeriodoPagamentoDTO);
        assertPeriodoPagamentoUpdatableFieldsEquals(returnedPeriodoPagamento, getPersistedPeriodoPagamento(returnedPeriodoPagamento));

        insertedPeriodoPagamento = returnedPeriodoPagamento;
    }

    @Test
    @Transactional
    void createPeriodoPagamentoWithExistingId() throws Exception {
        // Create the PeriodoPagamento with an existing ID
        periodoPagamento.setId(1L);
        PeriodoPagamentoDTO periodoPagamentoDTO = periodoPagamentoMapper.toDto(periodoPagamento);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodoPagamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(periodoPagamentoDTO)))
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
            .andExpect(jsonPath("$.[*].numeroDias").value(hasItem(DEFAULT_NUMERO_DIAS)))
            .andExpect(jsonPath("$.[*].idPlanGnet").value(hasItem(DEFAULT_ID_PLAN_GNET)));
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
            .andExpect(jsonPath("$.numeroDias").value(DEFAULT_NUMERO_DIAS))
            .andExpect(jsonPath("$.idPlanGnet").value(DEFAULT_ID_PLAN_GNET));
    }

    @Test
    @Transactional
    void getPeriodoPagamentosByIdFiltering() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        Long id = periodoPagamento.getId();

        defaultPeriodoPagamentoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPeriodoPagamentoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPeriodoPagamentoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByPeriodoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where periodo equals to
        defaultPeriodoPagamentoFiltering("periodo.equals=" + DEFAULT_PERIODO, "periodo.equals=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByPeriodoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where periodo in
        defaultPeriodoPagamentoFiltering("periodo.in=" + DEFAULT_PERIODO + "," + UPDATED_PERIODO, "periodo.in=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByPeriodoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where periodo is not null
        defaultPeriodoPagamentoFiltering("periodo.specified=true", "periodo.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByPeriodoContainsSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where periodo contains
        defaultPeriodoPagamentoFiltering("periodo.contains=" + DEFAULT_PERIODO, "periodo.contains=" + UPDATED_PERIODO);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByPeriodoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where periodo does not contain
        defaultPeriodoPagamentoFiltering("periodo.doesNotContain=" + UPDATED_PERIODO, "periodo.doesNotContain=" + DEFAULT_PERIODO);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByNumeroDiasIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where numeroDias equals to
        defaultPeriodoPagamentoFiltering("numeroDias.equals=" + DEFAULT_NUMERO_DIAS, "numeroDias.equals=" + UPDATED_NUMERO_DIAS);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByNumeroDiasIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where numeroDias in
        defaultPeriodoPagamentoFiltering(
            "numeroDias.in=" + DEFAULT_NUMERO_DIAS + "," + UPDATED_NUMERO_DIAS,
            "numeroDias.in=" + UPDATED_NUMERO_DIAS
        );
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByNumeroDiasIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where numeroDias is not null
        defaultPeriodoPagamentoFiltering("numeroDias.specified=true", "numeroDias.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByNumeroDiasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where numeroDias is greater than or equal to
        defaultPeriodoPagamentoFiltering(
            "numeroDias.greaterThanOrEqual=" + DEFAULT_NUMERO_DIAS,
            "numeroDias.greaterThanOrEqual=" + UPDATED_NUMERO_DIAS
        );
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByNumeroDiasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where numeroDias is less than or equal to
        defaultPeriodoPagamentoFiltering(
            "numeroDias.lessThanOrEqual=" + DEFAULT_NUMERO_DIAS,
            "numeroDias.lessThanOrEqual=" + SMALLER_NUMERO_DIAS
        );
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByNumeroDiasIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where numeroDias is less than
        defaultPeriodoPagamentoFiltering("numeroDias.lessThan=" + UPDATED_NUMERO_DIAS, "numeroDias.lessThan=" + DEFAULT_NUMERO_DIAS);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByNumeroDiasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where numeroDias is greater than
        defaultPeriodoPagamentoFiltering("numeroDias.greaterThan=" + SMALLER_NUMERO_DIAS, "numeroDias.greaterThan=" + DEFAULT_NUMERO_DIAS);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByIdPlanGnetIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where idPlanGnet equals to
        defaultPeriodoPagamentoFiltering("idPlanGnet.equals=" + DEFAULT_ID_PLAN_GNET, "idPlanGnet.equals=" + UPDATED_ID_PLAN_GNET);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByIdPlanGnetIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where idPlanGnet in
        defaultPeriodoPagamentoFiltering(
            "idPlanGnet.in=" + DEFAULT_ID_PLAN_GNET + "," + UPDATED_ID_PLAN_GNET,
            "idPlanGnet.in=" + UPDATED_ID_PLAN_GNET
        );
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByIdPlanGnetIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where idPlanGnet is not null
        defaultPeriodoPagamentoFiltering("idPlanGnet.specified=true", "idPlanGnet.specified=false");
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByIdPlanGnetContainsSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where idPlanGnet contains
        defaultPeriodoPagamentoFiltering("idPlanGnet.contains=" + DEFAULT_ID_PLAN_GNET, "idPlanGnet.contains=" + UPDATED_ID_PLAN_GNET);
    }

    @Test
    @Transactional
    void getAllPeriodoPagamentosByIdPlanGnetNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPeriodoPagamento = periodoPagamentoRepository.saveAndFlush(periodoPagamento);

        // Get all the periodoPagamentoList where idPlanGnet does not contain
        defaultPeriodoPagamentoFiltering(
            "idPlanGnet.doesNotContain=" + UPDATED_ID_PLAN_GNET,
            "idPlanGnet.doesNotContain=" + DEFAULT_ID_PLAN_GNET
        );
    }

    private void defaultPeriodoPagamentoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPeriodoPagamentoShouldBeFound(shouldBeFound);
        defaultPeriodoPagamentoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPeriodoPagamentoShouldBeFound(String filter) throws Exception {
        restPeriodoPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodoPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodo").value(hasItem(DEFAULT_PERIODO)))
            .andExpect(jsonPath("$.[*].numeroDias").value(hasItem(DEFAULT_NUMERO_DIAS)))
            .andExpect(jsonPath("$.[*].idPlanGnet").value(hasItem(DEFAULT_ID_PLAN_GNET)));

        // Check, that the count call also returns 1
        restPeriodoPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPeriodoPagamentoShouldNotBeFound(String filter) throws Exception {
        restPeriodoPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPeriodoPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        updatedPeriodoPagamento.periodo(UPDATED_PERIODO).numeroDias(UPDATED_NUMERO_DIAS).idPlanGnet(UPDATED_ID_PLAN_GNET);
        PeriodoPagamentoDTO periodoPagamentoDTO = periodoPagamentoMapper.toDto(updatedPeriodoPagamento);

        restPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodoPagamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(periodoPagamentoDTO))
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

        // Create the PeriodoPagamento
        PeriodoPagamentoDTO periodoPagamentoDTO = periodoPagamentoMapper.toDto(periodoPagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, periodoPagamentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(periodoPagamentoDTO))
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

        // Create the PeriodoPagamento
        PeriodoPagamentoDTO periodoPagamentoDTO = periodoPagamentoMapper.toDto(periodoPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(periodoPagamentoDTO))
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

        // Create the PeriodoPagamento
        PeriodoPagamentoDTO periodoPagamentoDTO = periodoPagamentoMapper.toDto(periodoPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(periodoPagamentoDTO)))
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

        partialUpdatedPeriodoPagamento.periodo(UPDATED_PERIODO).numeroDias(UPDATED_NUMERO_DIAS);

        restPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodoPagamento.getId())
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

        partialUpdatedPeriodoPagamento.periodo(UPDATED_PERIODO).numeroDias(UPDATED_NUMERO_DIAS).idPlanGnet(UPDATED_ID_PLAN_GNET);

        restPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPeriodoPagamento.getId())
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

        // Create the PeriodoPagamento
        PeriodoPagamentoDTO periodoPagamentoDTO = periodoPagamentoMapper.toDto(periodoPagamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, periodoPagamentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(periodoPagamentoDTO))
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

        // Create the PeriodoPagamento
        PeriodoPagamentoDTO periodoPagamentoDTO = periodoPagamentoMapper.toDto(periodoPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(periodoPagamentoDTO))
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

        // Create the PeriodoPagamento
        PeriodoPagamentoDTO periodoPagamentoDTO = periodoPagamentoMapper.toDto(periodoPagamento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPeriodoPagamentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(periodoPagamentoDTO)))
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
            .perform(delete(ENTITY_API_URL_ID, periodoPagamento.getId()).accept(MediaType.APPLICATION_JSON))
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
