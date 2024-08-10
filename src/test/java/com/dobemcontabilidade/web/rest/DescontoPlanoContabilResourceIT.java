package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DescontoPlanoContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.DescontoPlanoContabil;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.repository.DescontoPlanoContabilRepository;
import com.dobemcontabilidade.service.DescontoPlanoContabilService;
import com.dobemcontabilidade.service.dto.DescontoPlanoContabilDTO;
import com.dobemcontabilidade.service.mapper.DescontoPlanoContabilMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DescontoPlanoContabilResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DescontoPlanoContabilResourceIT {

    private static final Double DEFAULT_PERCENTUAL = 1D;
    private static final Double UPDATED_PERCENTUAL = 2D;
    private static final Double SMALLER_PERCENTUAL = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/desconto-plano-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DescontoPlanoContabilRepository descontoPlanoContabilRepository;

    @Mock
    private DescontoPlanoContabilRepository descontoPlanoContabilRepositoryMock;

    @Autowired
    private DescontoPlanoContabilMapper descontoPlanoContabilMapper;

    @Mock
    private DescontoPlanoContabilService descontoPlanoContabilServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDescontoPlanoContabilMockMvc;

    private DescontoPlanoContabil descontoPlanoContabil;

    private DescontoPlanoContabil insertedDescontoPlanoContabil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescontoPlanoContabil createEntity(EntityManager em) {
        DescontoPlanoContabil descontoPlanoContabil = new DescontoPlanoContabil().percentual(DEFAULT_PERCENTUAL);
        // Add required entity
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            periodoPagamento = PeriodoPagamentoResourceIT.createEntity(em);
            em.persist(periodoPagamento);
            em.flush();
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        descontoPlanoContabil.setPeriodoPagamento(periodoPagamento);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        descontoPlanoContabil.setPlanoContabil(planoContabil);
        return descontoPlanoContabil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescontoPlanoContabil createUpdatedEntity(EntityManager em) {
        DescontoPlanoContabil descontoPlanoContabil = new DescontoPlanoContabil().percentual(UPDATED_PERCENTUAL);
        // Add required entity
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            periodoPagamento = PeriodoPagamentoResourceIT.createUpdatedEntity(em);
            em.persist(periodoPagamento);
            em.flush();
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        descontoPlanoContabil.setPeriodoPagamento(periodoPagamento);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        descontoPlanoContabil.setPlanoContabil(planoContabil);
        return descontoPlanoContabil;
    }

    @BeforeEach
    public void initTest() {
        descontoPlanoContabil = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDescontoPlanoContabil != null) {
            descontoPlanoContabilRepository.delete(insertedDescontoPlanoContabil);
            insertedDescontoPlanoContabil = null;
        }
    }

    @Test
    @Transactional
    void createDescontoPlanoContabil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DescontoPlanoContabil
        DescontoPlanoContabilDTO descontoPlanoContabilDTO = descontoPlanoContabilMapper.toDto(descontoPlanoContabil);
        var returnedDescontoPlanoContabilDTO = om.readValue(
            restDescontoPlanoContabilMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(descontoPlanoContabilDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DescontoPlanoContabilDTO.class
        );

        // Validate the DescontoPlanoContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDescontoPlanoContabil = descontoPlanoContabilMapper.toEntity(returnedDescontoPlanoContabilDTO);
        assertDescontoPlanoContabilUpdatableFieldsEquals(
            returnedDescontoPlanoContabil,
            getPersistedDescontoPlanoContabil(returnedDescontoPlanoContabil)
        );

        insertedDescontoPlanoContabil = returnedDescontoPlanoContabil;
    }

    @Test
    @Transactional
    void createDescontoPlanoContabilWithExistingId() throws Exception {
        // Create the DescontoPlanoContabil with an existing ID
        descontoPlanoContabil.setId(1L);
        DescontoPlanoContabilDTO descontoPlanoContabilDTO = descontoPlanoContabilMapper.toDto(descontoPlanoContabil);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDescontoPlanoContabilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(descontoPlanoContabilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPercentualIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        descontoPlanoContabil.setPercentual(null);

        // Create the DescontoPlanoContabil, which fails.
        DescontoPlanoContabilDTO descontoPlanoContabilDTO = descontoPlanoContabilMapper.toDto(descontoPlanoContabil);

        restDescontoPlanoContabilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(descontoPlanoContabilDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContabils() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get all the descontoPlanoContabilList
        restDescontoPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descontoPlanoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentual").value(hasItem(DEFAULT_PERCENTUAL.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDescontoPlanoContabilsWithEagerRelationshipsIsEnabled() throws Exception {
        when(descontoPlanoContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDescontoPlanoContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(descontoPlanoContabilServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDescontoPlanoContabilsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(descontoPlanoContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDescontoPlanoContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(descontoPlanoContabilRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDescontoPlanoContabil() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get the descontoPlanoContabil
        restDescontoPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL_ID, descontoPlanoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(descontoPlanoContabil.getId().intValue()))
            .andExpect(jsonPath("$.percentual").value(DEFAULT_PERCENTUAL.doubleValue()));
    }

    @Test
    @Transactional
    void getDescontoPlanoContabilsByIdFiltering() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        Long id = descontoPlanoContabil.getId();

        defaultDescontoPlanoContabilFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDescontoPlanoContabilFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDescontoPlanoContabilFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContabilsByPercentualIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get all the descontoPlanoContabilList where percentual equals to
        defaultDescontoPlanoContabilFiltering("percentual.equals=" + DEFAULT_PERCENTUAL, "percentual.equals=" + UPDATED_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContabilsByPercentualIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get all the descontoPlanoContabilList where percentual in
        defaultDescontoPlanoContabilFiltering(
            "percentual.in=" + DEFAULT_PERCENTUAL + "," + UPDATED_PERCENTUAL,
            "percentual.in=" + UPDATED_PERCENTUAL
        );
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContabilsByPercentualIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get all the descontoPlanoContabilList where percentual is not null
        defaultDescontoPlanoContabilFiltering("percentual.specified=true", "percentual.specified=false");
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContabilsByPercentualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get all the descontoPlanoContabilList where percentual is greater than or equal to
        defaultDescontoPlanoContabilFiltering(
            "percentual.greaterThanOrEqual=" + DEFAULT_PERCENTUAL,
            "percentual.greaterThanOrEqual=" + UPDATED_PERCENTUAL
        );
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContabilsByPercentualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get all the descontoPlanoContabilList where percentual is less than or equal to
        defaultDescontoPlanoContabilFiltering(
            "percentual.lessThanOrEqual=" + DEFAULT_PERCENTUAL,
            "percentual.lessThanOrEqual=" + SMALLER_PERCENTUAL
        );
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContabilsByPercentualIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get all the descontoPlanoContabilList where percentual is less than
        defaultDescontoPlanoContabilFiltering("percentual.lessThan=" + UPDATED_PERCENTUAL, "percentual.lessThan=" + DEFAULT_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContabilsByPercentualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        // Get all the descontoPlanoContabilList where percentual is greater than
        defaultDescontoPlanoContabilFiltering(
            "percentual.greaterThan=" + SMALLER_PERCENTUAL,
            "percentual.greaterThan=" + DEFAULT_PERCENTUAL
        );
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContabilsByPeriodoPagamentoIsEqualToSomething() throws Exception {
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);
            periodoPagamento = PeriodoPagamentoResourceIT.createEntity(em);
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        em.persist(periodoPagamento);
        em.flush();
        descontoPlanoContabil.setPeriodoPagamento(periodoPagamento);
        descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);
        Long periodoPagamentoId = periodoPagamento.getId();
        // Get all the descontoPlanoContabilList where periodoPagamento equals to periodoPagamentoId
        defaultDescontoPlanoContabilShouldBeFound("periodoPagamentoId.equals=" + periodoPagamentoId);

        // Get all the descontoPlanoContabilList where periodoPagamento equals to (periodoPagamentoId + 1)
        defaultDescontoPlanoContabilShouldNotBeFound("periodoPagamentoId.equals=" + (periodoPagamentoId + 1));
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContabilsByPlanoContabilIsEqualToSomething() throws Exception {
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);
            planoContabil = PlanoContabilResourceIT.createEntity(em);
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        em.persist(planoContabil);
        em.flush();
        descontoPlanoContabil.setPlanoContabil(planoContabil);
        descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);
        Long planoContabilId = planoContabil.getId();
        // Get all the descontoPlanoContabilList where planoContabil equals to planoContabilId
        defaultDescontoPlanoContabilShouldBeFound("planoContabilId.equals=" + planoContabilId);

        // Get all the descontoPlanoContabilList where planoContabil equals to (planoContabilId + 1)
        defaultDescontoPlanoContabilShouldNotBeFound("planoContabilId.equals=" + (planoContabilId + 1));
    }

    private void defaultDescontoPlanoContabilFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDescontoPlanoContabilShouldBeFound(shouldBeFound);
        defaultDescontoPlanoContabilShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDescontoPlanoContabilShouldBeFound(String filter) throws Exception {
        restDescontoPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descontoPlanoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentual").value(hasItem(DEFAULT_PERCENTUAL.doubleValue())));

        // Check, that the count call also returns 1
        restDescontoPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDescontoPlanoContabilShouldNotBeFound(String filter) throws Exception {
        restDescontoPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDescontoPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDescontoPlanoContabil() throws Exception {
        // Get the descontoPlanoContabil
        restDescontoPlanoContabilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDescontoPlanoContabil() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the descontoPlanoContabil
        DescontoPlanoContabil updatedDescontoPlanoContabil = descontoPlanoContabilRepository
            .findById(descontoPlanoContabil.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDescontoPlanoContabil are not directly saved in db
        em.detach(updatedDescontoPlanoContabil);
        updatedDescontoPlanoContabil.percentual(UPDATED_PERCENTUAL);
        DescontoPlanoContabilDTO descontoPlanoContabilDTO = descontoPlanoContabilMapper.toDto(updatedDescontoPlanoContabil);

        restDescontoPlanoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, descontoPlanoContabilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(descontoPlanoContabilDTO))
            )
            .andExpect(status().isOk());

        // Validate the DescontoPlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDescontoPlanoContabilToMatchAllProperties(updatedDescontoPlanoContabil);
    }

    @Test
    @Transactional
    void putNonExistingDescontoPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContabil.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContabil
        DescontoPlanoContabilDTO descontoPlanoContabilDTO = descontoPlanoContabilMapper.toDto(descontoPlanoContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescontoPlanoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, descontoPlanoContabilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(descontoPlanoContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDescontoPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContabil.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContabil
        DescontoPlanoContabilDTO descontoPlanoContabilDTO = descontoPlanoContabilMapper.toDto(descontoPlanoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPlanoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(descontoPlanoContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDescontoPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContabil.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContabil
        DescontoPlanoContabilDTO descontoPlanoContabilDTO = descontoPlanoContabilMapper.toDto(descontoPlanoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPlanoContabilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(descontoPlanoContabilDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DescontoPlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDescontoPlanoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the descontoPlanoContabil using partial update
        DescontoPlanoContabil partialUpdatedDescontoPlanoContabil = new DescontoPlanoContabil();
        partialUpdatedDescontoPlanoContabil.setId(descontoPlanoContabil.getId());

        partialUpdatedDescontoPlanoContabil.percentual(UPDATED_PERCENTUAL);

        restDescontoPlanoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDescontoPlanoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDescontoPlanoContabil))
            )
            .andExpect(status().isOk());

        // Validate the DescontoPlanoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDescontoPlanoContabilUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDescontoPlanoContabil, descontoPlanoContabil),
            getPersistedDescontoPlanoContabil(descontoPlanoContabil)
        );
    }

    @Test
    @Transactional
    void fullUpdateDescontoPlanoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the descontoPlanoContabil using partial update
        DescontoPlanoContabil partialUpdatedDescontoPlanoContabil = new DescontoPlanoContabil();
        partialUpdatedDescontoPlanoContabil.setId(descontoPlanoContabil.getId());

        partialUpdatedDescontoPlanoContabil.percentual(UPDATED_PERCENTUAL);

        restDescontoPlanoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDescontoPlanoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDescontoPlanoContabil))
            )
            .andExpect(status().isOk());

        // Validate the DescontoPlanoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDescontoPlanoContabilUpdatableFieldsEquals(
            partialUpdatedDescontoPlanoContabil,
            getPersistedDescontoPlanoContabil(partialUpdatedDescontoPlanoContabil)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDescontoPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContabil.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContabil
        DescontoPlanoContabilDTO descontoPlanoContabilDTO = descontoPlanoContabilMapper.toDto(descontoPlanoContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescontoPlanoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, descontoPlanoContabilDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(descontoPlanoContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDescontoPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContabil.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContabil
        DescontoPlanoContabilDTO descontoPlanoContabilDTO = descontoPlanoContabilMapper.toDto(descontoPlanoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPlanoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(descontoPlanoContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDescontoPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContabil.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContabil
        DescontoPlanoContabilDTO descontoPlanoContabilDTO = descontoPlanoContabilMapper.toDto(descontoPlanoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPlanoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(descontoPlanoContabilDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DescontoPlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDescontoPlanoContabil() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContabil = descontoPlanoContabilRepository.saveAndFlush(descontoPlanoContabil);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the descontoPlanoContabil
        restDescontoPlanoContabilMockMvc
            .perform(delete(ENTITY_API_URL_ID, descontoPlanoContabil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return descontoPlanoContabilRepository.count();
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

    protected DescontoPlanoContabil getPersistedDescontoPlanoContabil(DescontoPlanoContabil descontoPlanoContabil) {
        return descontoPlanoContabilRepository.findById(descontoPlanoContabil.getId()).orElseThrow();
    }

    protected void assertPersistedDescontoPlanoContabilToMatchAllProperties(DescontoPlanoContabil expectedDescontoPlanoContabil) {
        assertDescontoPlanoContabilAllPropertiesEquals(
            expectedDescontoPlanoContabil,
            getPersistedDescontoPlanoContabil(expectedDescontoPlanoContabil)
        );
    }

    protected void assertPersistedDescontoPlanoContabilToMatchUpdatableProperties(DescontoPlanoContabil expectedDescontoPlanoContabil) {
        assertDescontoPlanoContabilAllUpdatablePropertiesEquals(
            expectedDescontoPlanoContabil,
            getPersistedDescontoPlanoContabil(expectedDescontoPlanoContabil)
        );
    }
}
