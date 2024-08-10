package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DescontoPlanoContaAzulAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.DescontoPlanoContaAzul;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.repository.DescontoPlanoContaAzulRepository;
import com.dobemcontabilidade.service.DescontoPlanoContaAzulService;
import com.dobemcontabilidade.service.dto.DescontoPlanoContaAzulDTO;
import com.dobemcontabilidade.service.mapper.DescontoPlanoContaAzulMapper;
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
 * Integration tests for the {@link DescontoPlanoContaAzulResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DescontoPlanoContaAzulResourceIT {

    private static final Double DEFAULT_PERCENTUAL = 1D;
    private static final Double UPDATED_PERCENTUAL = 2D;
    private static final Double SMALLER_PERCENTUAL = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/desconto-plano-conta-azuls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DescontoPlanoContaAzulRepository descontoPlanoContaAzulRepository;

    @Mock
    private DescontoPlanoContaAzulRepository descontoPlanoContaAzulRepositoryMock;

    @Autowired
    private DescontoPlanoContaAzulMapper descontoPlanoContaAzulMapper;

    @Mock
    private DescontoPlanoContaAzulService descontoPlanoContaAzulServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDescontoPlanoContaAzulMockMvc;

    private DescontoPlanoContaAzul descontoPlanoContaAzul;

    private DescontoPlanoContaAzul insertedDescontoPlanoContaAzul;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescontoPlanoContaAzul createEntity(EntityManager em) {
        DescontoPlanoContaAzul descontoPlanoContaAzul = new DescontoPlanoContaAzul().percentual(DEFAULT_PERCENTUAL);
        // Add required entity
        PlanoContaAzul planoContaAzul;
        if (TestUtil.findAll(em, PlanoContaAzul.class).isEmpty()) {
            planoContaAzul = PlanoContaAzulResourceIT.createEntity(em);
            em.persist(planoContaAzul);
            em.flush();
        } else {
            planoContaAzul = TestUtil.findAll(em, PlanoContaAzul.class).get(0);
        }
        descontoPlanoContaAzul.setPlanoContaAzul(planoContaAzul);
        // Add required entity
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            periodoPagamento = PeriodoPagamentoResourceIT.createEntity(em);
            em.persist(periodoPagamento);
            em.flush();
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        descontoPlanoContaAzul.setPeriodoPagamento(periodoPagamento);
        return descontoPlanoContaAzul;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DescontoPlanoContaAzul createUpdatedEntity(EntityManager em) {
        DescontoPlanoContaAzul descontoPlanoContaAzul = new DescontoPlanoContaAzul().percentual(UPDATED_PERCENTUAL);
        // Add required entity
        PlanoContaAzul planoContaAzul;
        if (TestUtil.findAll(em, PlanoContaAzul.class).isEmpty()) {
            planoContaAzul = PlanoContaAzulResourceIT.createUpdatedEntity(em);
            em.persist(planoContaAzul);
            em.flush();
        } else {
            planoContaAzul = TestUtil.findAll(em, PlanoContaAzul.class).get(0);
        }
        descontoPlanoContaAzul.setPlanoContaAzul(planoContaAzul);
        // Add required entity
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            periodoPagamento = PeriodoPagamentoResourceIT.createUpdatedEntity(em);
            em.persist(periodoPagamento);
            em.flush();
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        descontoPlanoContaAzul.setPeriodoPagamento(periodoPagamento);
        return descontoPlanoContaAzul;
    }

    @BeforeEach
    public void initTest() {
        descontoPlanoContaAzul = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDescontoPlanoContaAzul != null) {
            descontoPlanoContaAzulRepository.delete(insertedDescontoPlanoContaAzul);
            insertedDescontoPlanoContaAzul = null;
        }
    }

    @Test
    @Transactional
    void createDescontoPlanoContaAzul() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DescontoPlanoContaAzul
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO = descontoPlanoContaAzulMapper.toDto(descontoPlanoContaAzul);
        var returnedDescontoPlanoContaAzulDTO = om.readValue(
            restDescontoPlanoContaAzulMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(descontoPlanoContaAzulDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DescontoPlanoContaAzulDTO.class
        );

        // Validate the DescontoPlanoContaAzul in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDescontoPlanoContaAzul = descontoPlanoContaAzulMapper.toEntity(returnedDescontoPlanoContaAzulDTO);
        assertDescontoPlanoContaAzulUpdatableFieldsEquals(
            returnedDescontoPlanoContaAzul,
            getPersistedDescontoPlanoContaAzul(returnedDescontoPlanoContaAzul)
        );

        insertedDescontoPlanoContaAzul = returnedDescontoPlanoContaAzul;
    }

    @Test
    @Transactional
    void createDescontoPlanoContaAzulWithExistingId() throws Exception {
        // Create the DescontoPlanoContaAzul with an existing ID
        descontoPlanoContaAzul.setId(1L);
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO = descontoPlanoContaAzulMapper.toDto(descontoPlanoContaAzul);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDescontoPlanoContaAzulMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(descontoPlanoContaAzulDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContaAzuls() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        // Get all the descontoPlanoContaAzulList
        restDescontoPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descontoPlanoContaAzul.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentual").value(hasItem(DEFAULT_PERCENTUAL.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDescontoPlanoContaAzulsWithEagerRelationshipsIsEnabled() throws Exception {
        when(descontoPlanoContaAzulServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDescontoPlanoContaAzulMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(descontoPlanoContaAzulServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDescontoPlanoContaAzulsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(descontoPlanoContaAzulServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDescontoPlanoContaAzulMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(descontoPlanoContaAzulRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDescontoPlanoContaAzul() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        // Get the descontoPlanoContaAzul
        restDescontoPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL_ID, descontoPlanoContaAzul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(descontoPlanoContaAzul.getId().intValue()))
            .andExpect(jsonPath("$.percentual").value(DEFAULT_PERCENTUAL.doubleValue()));
    }

    @Test
    @Transactional
    void getDescontoPlanoContaAzulsByIdFiltering() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        Long id = descontoPlanoContaAzul.getId();

        defaultDescontoPlanoContaAzulFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDescontoPlanoContaAzulFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDescontoPlanoContaAzulFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContaAzulsByPercentualIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        // Get all the descontoPlanoContaAzulList where percentual equals to
        defaultDescontoPlanoContaAzulFiltering("percentual.equals=" + DEFAULT_PERCENTUAL, "percentual.equals=" + UPDATED_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContaAzulsByPercentualIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        // Get all the descontoPlanoContaAzulList where percentual in
        defaultDescontoPlanoContaAzulFiltering(
            "percentual.in=" + DEFAULT_PERCENTUAL + "," + UPDATED_PERCENTUAL,
            "percentual.in=" + UPDATED_PERCENTUAL
        );
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContaAzulsByPercentualIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        // Get all the descontoPlanoContaAzulList where percentual is not null
        defaultDescontoPlanoContaAzulFiltering("percentual.specified=true", "percentual.specified=false");
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContaAzulsByPercentualIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        // Get all the descontoPlanoContaAzulList where percentual is greater than or equal to
        defaultDescontoPlanoContaAzulFiltering(
            "percentual.greaterThanOrEqual=" + DEFAULT_PERCENTUAL,
            "percentual.greaterThanOrEqual=" + UPDATED_PERCENTUAL
        );
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContaAzulsByPercentualIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        // Get all the descontoPlanoContaAzulList where percentual is less than or equal to
        defaultDescontoPlanoContaAzulFiltering(
            "percentual.lessThanOrEqual=" + DEFAULT_PERCENTUAL,
            "percentual.lessThanOrEqual=" + SMALLER_PERCENTUAL
        );
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContaAzulsByPercentualIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        // Get all the descontoPlanoContaAzulList where percentual is less than
        defaultDescontoPlanoContaAzulFiltering("percentual.lessThan=" + UPDATED_PERCENTUAL, "percentual.lessThan=" + DEFAULT_PERCENTUAL);
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContaAzulsByPercentualIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        // Get all the descontoPlanoContaAzulList where percentual is greater than
        defaultDescontoPlanoContaAzulFiltering(
            "percentual.greaterThan=" + SMALLER_PERCENTUAL,
            "percentual.greaterThan=" + DEFAULT_PERCENTUAL
        );
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContaAzulsByPlanoContaAzulIsEqualToSomething() throws Exception {
        PlanoContaAzul planoContaAzul;
        if (TestUtil.findAll(em, PlanoContaAzul.class).isEmpty()) {
            descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);
            planoContaAzul = PlanoContaAzulResourceIT.createEntity(em);
        } else {
            planoContaAzul = TestUtil.findAll(em, PlanoContaAzul.class).get(0);
        }
        em.persist(planoContaAzul);
        em.flush();
        descontoPlanoContaAzul.setPlanoContaAzul(planoContaAzul);
        descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);
        Long planoContaAzulId = planoContaAzul.getId();
        // Get all the descontoPlanoContaAzulList where planoContaAzul equals to planoContaAzulId
        defaultDescontoPlanoContaAzulShouldBeFound("planoContaAzulId.equals=" + planoContaAzulId);

        // Get all the descontoPlanoContaAzulList where planoContaAzul equals to (planoContaAzulId + 1)
        defaultDescontoPlanoContaAzulShouldNotBeFound("planoContaAzulId.equals=" + (planoContaAzulId + 1));
    }

    @Test
    @Transactional
    void getAllDescontoPlanoContaAzulsByPeriodoPagamentoIsEqualToSomething() throws Exception {
        PeriodoPagamento periodoPagamento;
        if (TestUtil.findAll(em, PeriodoPagamento.class).isEmpty()) {
            descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);
            periodoPagamento = PeriodoPagamentoResourceIT.createEntity(em);
        } else {
            periodoPagamento = TestUtil.findAll(em, PeriodoPagamento.class).get(0);
        }
        em.persist(periodoPagamento);
        em.flush();
        descontoPlanoContaAzul.setPeriodoPagamento(periodoPagamento);
        descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);
        Long periodoPagamentoId = periodoPagamento.getId();
        // Get all the descontoPlanoContaAzulList where periodoPagamento equals to periodoPagamentoId
        defaultDescontoPlanoContaAzulShouldBeFound("periodoPagamentoId.equals=" + periodoPagamentoId);

        // Get all the descontoPlanoContaAzulList where periodoPagamento equals to (periodoPagamentoId + 1)
        defaultDescontoPlanoContaAzulShouldNotBeFound("periodoPagamentoId.equals=" + (periodoPagamentoId + 1));
    }

    private void defaultDescontoPlanoContaAzulFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDescontoPlanoContaAzulShouldBeFound(shouldBeFound);
        defaultDescontoPlanoContaAzulShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDescontoPlanoContaAzulShouldBeFound(String filter) throws Exception {
        restDescontoPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(descontoPlanoContaAzul.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentual").value(hasItem(DEFAULT_PERCENTUAL.doubleValue())));

        // Check, that the count call also returns 1
        restDescontoPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDescontoPlanoContaAzulShouldNotBeFound(String filter) throws Exception {
        restDescontoPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDescontoPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDescontoPlanoContaAzul() throws Exception {
        // Get the descontoPlanoContaAzul
        restDescontoPlanoContaAzulMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDescontoPlanoContaAzul() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the descontoPlanoContaAzul
        DescontoPlanoContaAzul updatedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository
            .findById(descontoPlanoContaAzul.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDescontoPlanoContaAzul are not directly saved in db
        em.detach(updatedDescontoPlanoContaAzul);
        updatedDescontoPlanoContaAzul.percentual(UPDATED_PERCENTUAL);
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO = descontoPlanoContaAzulMapper.toDto(updatedDescontoPlanoContaAzul);

        restDescontoPlanoContaAzulMockMvc
            .perform(
                put(ENTITY_API_URL_ID, descontoPlanoContaAzulDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(descontoPlanoContaAzulDTO))
            )
            .andExpect(status().isOk());

        // Validate the DescontoPlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDescontoPlanoContaAzulToMatchAllProperties(updatedDescontoPlanoContaAzul);
    }

    @Test
    @Transactional
    void putNonExistingDescontoPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContaAzul.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContaAzul
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO = descontoPlanoContaAzulMapper.toDto(descontoPlanoContaAzul);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescontoPlanoContaAzulMockMvc
            .perform(
                put(ENTITY_API_URL_ID, descontoPlanoContaAzulDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(descontoPlanoContaAzulDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDescontoPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContaAzul.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContaAzul
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO = descontoPlanoContaAzulMapper.toDto(descontoPlanoContaAzul);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPlanoContaAzulMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(descontoPlanoContaAzulDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDescontoPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContaAzul.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContaAzul
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO = descontoPlanoContaAzulMapper.toDto(descontoPlanoContaAzul);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPlanoContaAzulMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(descontoPlanoContaAzulDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DescontoPlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDescontoPlanoContaAzulWithPatch() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the descontoPlanoContaAzul using partial update
        DescontoPlanoContaAzul partialUpdatedDescontoPlanoContaAzul = new DescontoPlanoContaAzul();
        partialUpdatedDescontoPlanoContaAzul.setId(descontoPlanoContaAzul.getId());

        partialUpdatedDescontoPlanoContaAzul.percentual(UPDATED_PERCENTUAL);

        restDescontoPlanoContaAzulMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDescontoPlanoContaAzul.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDescontoPlanoContaAzul))
            )
            .andExpect(status().isOk());

        // Validate the DescontoPlanoContaAzul in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDescontoPlanoContaAzulUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDescontoPlanoContaAzul, descontoPlanoContaAzul),
            getPersistedDescontoPlanoContaAzul(descontoPlanoContaAzul)
        );
    }

    @Test
    @Transactional
    void fullUpdateDescontoPlanoContaAzulWithPatch() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the descontoPlanoContaAzul using partial update
        DescontoPlanoContaAzul partialUpdatedDescontoPlanoContaAzul = new DescontoPlanoContaAzul();
        partialUpdatedDescontoPlanoContaAzul.setId(descontoPlanoContaAzul.getId());

        partialUpdatedDescontoPlanoContaAzul.percentual(UPDATED_PERCENTUAL);

        restDescontoPlanoContaAzulMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDescontoPlanoContaAzul.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDescontoPlanoContaAzul))
            )
            .andExpect(status().isOk());

        // Validate the DescontoPlanoContaAzul in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDescontoPlanoContaAzulUpdatableFieldsEquals(
            partialUpdatedDescontoPlanoContaAzul,
            getPersistedDescontoPlanoContaAzul(partialUpdatedDescontoPlanoContaAzul)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDescontoPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContaAzul.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContaAzul
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO = descontoPlanoContaAzulMapper.toDto(descontoPlanoContaAzul);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDescontoPlanoContaAzulMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, descontoPlanoContaAzulDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(descontoPlanoContaAzulDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDescontoPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContaAzul.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContaAzul
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO = descontoPlanoContaAzulMapper.toDto(descontoPlanoContaAzul);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPlanoContaAzulMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(descontoPlanoContaAzulDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DescontoPlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDescontoPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        descontoPlanoContaAzul.setId(longCount.incrementAndGet());

        // Create the DescontoPlanoContaAzul
        DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO = descontoPlanoContaAzulMapper.toDto(descontoPlanoContaAzul);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDescontoPlanoContaAzulMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(descontoPlanoContaAzulDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DescontoPlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDescontoPlanoContaAzul() throws Exception {
        // Initialize the database
        insertedDescontoPlanoContaAzul = descontoPlanoContaAzulRepository.saveAndFlush(descontoPlanoContaAzul);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the descontoPlanoContaAzul
        restDescontoPlanoContaAzulMockMvc
            .perform(delete(ENTITY_API_URL_ID, descontoPlanoContaAzul.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return descontoPlanoContaAzulRepository.count();
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

    protected DescontoPlanoContaAzul getPersistedDescontoPlanoContaAzul(DescontoPlanoContaAzul descontoPlanoContaAzul) {
        return descontoPlanoContaAzulRepository.findById(descontoPlanoContaAzul.getId()).orElseThrow();
    }

    protected void assertPersistedDescontoPlanoContaAzulToMatchAllProperties(DescontoPlanoContaAzul expectedDescontoPlanoContaAzul) {
        assertDescontoPlanoContaAzulAllPropertiesEquals(
            expectedDescontoPlanoContaAzul,
            getPersistedDescontoPlanoContaAzul(expectedDescontoPlanoContaAzul)
        );
    }

    protected void assertPersistedDescontoPlanoContaAzulToMatchUpdatableProperties(DescontoPlanoContaAzul expectedDescontoPlanoContaAzul) {
        assertDescontoPlanoContaAzulAllUpdatablePropertiesEquals(
            expectedDescontoPlanoContaAzul,
            getPersistedDescontoPlanoContaAzul(expectedDescontoPlanoContaAzul)
        );
    }
}
