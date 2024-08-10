package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ValorBaseRamoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.ValorBaseRamo;
import com.dobemcontabilidade.repository.ValorBaseRamoRepository;
import com.dobemcontabilidade.service.ValorBaseRamoService;
import com.dobemcontabilidade.service.dto.ValorBaseRamoDTO;
import com.dobemcontabilidade.service.mapper.ValorBaseRamoMapper;
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
 * Integration tests for the {@link ValorBaseRamoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ValorBaseRamoResourceIT {

    private static final Double DEFAULT_VALOR_BASE = 1D;
    private static final Double UPDATED_VALOR_BASE = 2D;
    private static final Double SMALLER_VALOR_BASE = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/valor-base-ramos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ValorBaseRamoRepository valorBaseRamoRepository;

    @Mock
    private ValorBaseRamoRepository valorBaseRamoRepositoryMock;

    @Autowired
    private ValorBaseRamoMapper valorBaseRamoMapper;

    @Mock
    private ValorBaseRamoService valorBaseRamoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restValorBaseRamoMockMvc;

    private ValorBaseRamo valorBaseRamo;

    private ValorBaseRamo insertedValorBaseRamo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValorBaseRamo createEntity(EntityManager em) {
        ValorBaseRamo valorBaseRamo = new ValorBaseRamo().valorBase(DEFAULT_VALOR_BASE);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        valorBaseRamo.setRamo(ramo);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        valorBaseRamo.setPlanoContabil(planoContabil);
        return valorBaseRamo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValorBaseRamo createUpdatedEntity(EntityManager em) {
        ValorBaseRamo valorBaseRamo = new ValorBaseRamo().valorBase(UPDATED_VALOR_BASE);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createUpdatedEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        valorBaseRamo.setRamo(ramo);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        valorBaseRamo.setPlanoContabil(planoContabil);
        return valorBaseRamo;
    }

    @BeforeEach
    public void initTest() {
        valorBaseRamo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedValorBaseRamo != null) {
            valorBaseRamoRepository.delete(insertedValorBaseRamo);
            insertedValorBaseRamo = null;
        }
    }

    @Test
    @Transactional
    void createValorBaseRamo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ValorBaseRamo
        ValorBaseRamoDTO valorBaseRamoDTO = valorBaseRamoMapper.toDto(valorBaseRamo);
        var returnedValorBaseRamoDTO = om.readValue(
            restValorBaseRamoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valorBaseRamoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ValorBaseRamoDTO.class
        );

        // Validate the ValorBaseRamo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedValorBaseRamo = valorBaseRamoMapper.toEntity(returnedValorBaseRamoDTO);
        assertValorBaseRamoUpdatableFieldsEquals(returnedValorBaseRamo, getPersistedValorBaseRamo(returnedValorBaseRamo));

        insertedValorBaseRamo = returnedValorBaseRamo;
    }

    @Test
    @Transactional
    void createValorBaseRamoWithExistingId() throws Exception {
        // Create the ValorBaseRamo with an existing ID
        valorBaseRamo.setId(1L);
        ValorBaseRamoDTO valorBaseRamoDTO = valorBaseRamoMapper.toDto(valorBaseRamo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restValorBaseRamoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valorBaseRamoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ValorBaseRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValorBaseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        valorBaseRamo.setValorBase(null);

        // Create the ValorBaseRamo, which fails.
        ValorBaseRamoDTO valorBaseRamoDTO = valorBaseRamoMapper.toDto(valorBaseRamo);

        restValorBaseRamoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valorBaseRamoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllValorBaseRamos() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        // Get all the valorBaseRamoList
        restValorBaseRamoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valorBaseRamo.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorBase").value(hasItem(DEFAULT_VALOR_BASE.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllValorBaseRamosWithEagerRelationshipsIsEnabled() throws Exception {
        when(valorBaseRamoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restValorBaseRamoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(valorBaseRamoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllValorBaseRamosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(valorBaseRamoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restValorBaseRamoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(valorBaseRamoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getValorBaseRamo() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        // Get the valorBaseRamo
        restValorBaseRamoMockMvc
            .perform(get(ENTITY_API_URL_ID, valorBaseRamo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(valorBaseRamo.getId().intValue()))
            .andExpect(jsonPath("$.valorBase").value(DEFAULT_VALOR_BASE.doubleValue()));
    }

    @Test
    @Transactional
    void getValorBaseRamosByIdFiltering() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        Long id = valorBaseRamo.getId();

        defaultValorBaseRamoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultValorBaseRamoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultValorBaseRamoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllValorBaseRamosByValorBaseIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        // Get all the valorBaseRamoList where valorBase equals to
        defaultValorBaseRamoFiltering("valorBase.equals=" + DEFAULT_VALOR_BASE, "valorBase.equals=" + UPDATED_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllValorBaseRamosByValorBaseIsInShouldWork() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        // Get all the valorBaseRamoList where valorBase in
        defaultValorBaseRamoFiltering(
            "valorBase.in=" + DEFAULT_VALOR_BASE + "," + UPDATED_VALOR_BASE,
            "valorBase.in=" + UPDATED_VALOR_BASE
        );
    }

    @Test
    @Transactional
    void getAllValorBaseRamosByValorBaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        // Get all the valorBaseRamoList where valorBase is not null
        defaultValorBaseRamoFiltering("valorBase.specified=true", "valorBase.specified=false");
    }

    @Test
    @Transactional
    void getAllValorBaseRamosByValorBaseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        // Get all the valorBaseRamoList where valorBase is greater than or equal to
        defaultValorBaseRamoFiltering(
            "valorBase.greaterThanOrEqual=" + DEFAULT_VALOR_BASE,
            "valorBase.greaterThanOrEqual=" + UPDATED_VALOR_BASE
        );
    }

    @Test
    @Transactional
    void getAllValorBaseRamosByValorBaseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        // Get all the valorBaseRamoList where valorBase is less than or equal to
        defaultValorBaseRamoFiltering("valorBase.lessThanOrEqual=" + DEFAULT_VALOR_BASE, "valorBase.lessThanOrEqual=" + SMALLER_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllValorBaseRamosByValorBaseIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        // Get all the valorBaseRamoList where valorBase is less than
        defaultValorBaseRamoFiltering("valorBase.lessThan=" + UPDATED_VALOR_BASE, "valorBase.lessThan=" + DEFAULT_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllValorBaseRamosByValorBaseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        // Get all the valorBaseRamoList where valorBase is greater than
        defaultValorBaseRamoFiltering("valorBase.greaterThan=" + SMALLER_VALOR_BASE, "valorBase.greaterThan=" + DEFAULT_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllValorBaseRamosByRamoIsEqualToSomething() throws Exception {
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            valorBaseRamoRepository.saveAndFlush(valorBaseRamo);
            ramo = RamoResourceIT.createEntity(em);
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        em.persist(ramo);
        em.flush();
        valorBaseRamo.setRamo(ramo);
        valorBaseRamoRepository.saveAndFlush(valorBaseRamo);
        Long ramoId = ramo.getId();
        // Get all the valorBaseRamoList where ramo equals to ramoId
        defaultValorBaseRamoShouldBeFound("ramoId.equals=" + ramoId);

        // Get all the valorBaseRamoList where ramo equals to (ramoId + 1)
        defaultValorBaseRamoShouldNotBeFound("ramoId.equals=" + (ramoId + 1));
    }

    @Test
    @Transactional
    void getAllValorBaseRamosByPlanoContabilIsEqualToSomething() throws Exception {
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            valorBaseRamoRepository.saveAndFlush(valorBaseRamo);
            planoContabil = PlanoContabilResourceIT.createEntity(em);
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        em.persist(planoContabil);
        em.flush();
        valorBaseRamo.setPlanoContabil(planoContabil);
        valorBaseRamoRepository.saveAndFlush(valorBaseRamo);
        Long planoContabilId = planoContabil.getId();
        // Get all the valorBaseRamoList where planoContabil equals to planoContabilId
        defaultValorBaseRamoShouldBeFound("planoContabilId.equals=" + planoContabilId);

        // Get all the valorBaseRamoList where planoContabil equals to (planoContabilId + 1)
        defaultValorBaseRamoShouldNotBeFound("planoContabilId.equals=" + (planoContabilId + 1));
    }

    private void defaultValorBaseRamoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultValorBaseRamoShouldBeFound(shouldBeFound);
        defaultValorBaseRamoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultValorBaseRamoShouldBeFound(String filter) throws Exception {
        restValorBaseRamoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valorBaseRamo.getId().intValue())))
            .andExpect(jsonPath("$.[*].valorBase").value(hasItem(DEFAULT_VALOR_BASE.doubleValue())));

        // Check, that the count call also returns 1
        restValorBaseRamoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultValorBaseRamoShouldNotBeFound(String filter) throws Exception {
        restValorBaseRamoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restValorBaseRamoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingValorBaseRamo() throws Exception {
        // Get the valorBaseRamo
        restValorBaseRamoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingValorBaseRamo() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the valorBaseRamo
        ValorBaseRamo updatedValorBaseRamo = valorBaseRamoRepository.findById(valorBaseRamo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedValorBaseRamo are not directly saved in db
        em.detach(updatedValorBaseRamo);
        updatedValorBaseRamo.valorBase(UPDATED_VALOR_BASE);
        ValorBaseRamoDTO valorBaseRamoDTO = valorBaseRamoMapper.toDto(updatedValorBaseRamo);

        restValorBaseRamoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, valorBaseRamoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(valorBaseRamoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ValorBaseRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedValorBaseRamoToMatchAllProperties(updatedValorBaseRamo);
    }

    @Test
    @Transactional
    void putNonExistingValorBaseRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valorBaseRamo.setId(longCount.incrementAndGet());

        // Create the ValorBaseRamo
        ValorBaseRamoDTO valorBaseRamoDTO = valorBaseRamoMapper.toDto(valorBaseRamo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValorBaseRamoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, valorBaseRamoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(valorBaseRamoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValorBaseRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchValorBaseRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valorBaseRamo.setId(longCount.incrementAndGet());

        // Create the ValorBaseRamo
        ValorBaseRamoDTO valorBaseRamoDTO = valorBaseRamoMapper.toDto(valorBaseRamo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValorBaseRamoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(valorBaseRamoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValorBaseRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamValorBaseRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valorBaseRamo.setId(longCount.incrementAndGet());

        // Create the ValorBaseRamo
        ValorBaseRamoDTO valorBaseRamoDTO = valorBaseRamoMapper.toDto(valorBaseRamo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValorBaseRamoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(valorBaseRamoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ValorBaseRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateValorBaseRamoWithPatch() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the valorBaseRamo using partial update
        ValorBaseRamo partialUpdatedValorBaseRamo = new ValorBaseRamo();
        partialUpdatedValorBaseRamo.setId(valorBaseRamo.getId());

        partialUpdatedValorBaseRamo.valorBase(UPDATED_VALOR_BASE);

        restValorBaseRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValorBaseRamo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedValorBaseRamo))
            )
            .andExpect(status().isOk());

        // Validate the ValorBaseRamo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertValorBaseRamoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedValorBaseRamo, valorBaseRamo),
            getPersistedValorBaseRamo(valorBaseRamo)
        );
    }

    @Test
    @Transactional
    void fullUpdateValorBaseRamoWithPatch() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the valorBaseRamo using partial update
        ValorBaseRamo partialUpdatedValorBaseRamo = new ValorBaseRamo();
        partialUpdatedValorBaseRamo.setId(valorBaseRamo.getId());

        partialUpdatedValorBaseRamo.valorBase(UPDATED_VALOR_BASE);

        restValorBaseRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValorBaseRamo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedValorBaseRamo))
            )
            .andExpect(status().isOk());

        // Validate the ValorBaseRamo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertValorBaseRamoUpdatableFieldsEquals(partialUpdatedValorBaseRamo, getPersistedValorBaseRamo(partialUpdatedValorBaseRamo));
    }

    @Test
    @Transactional
    void patchNonExistingValorBaseRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valorBaseRamo.setId(longCount.incrementAndGet());

        // Create the ValorBaseRamo
        ValorBaseRamoDTO valorBaseRamoDTO = valorBaseRamoMapper.toDto(valorBaseRamo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValorBaseRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, valorBaseRamoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(valorBaseRamoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValorBaseRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchValorBaseRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valorBaseRamo.setId(longCount.incrementAndGet());

        // Create the ValorBaseRamo
        ValorBaseRamoDTO valorBaseRamoDTO = valorBaseRamoMapper.toDto(valorBaseRamo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValorBaseRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(valorBaseRamoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValorBaseRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamValorBaseRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        valorBaseRamo.setId(longCount.incrementAndGet());

        // Create the ValorBaseRamo
        ValorBaseRamoDTO valorBaseRamoDTO = valorBaseRamoMapper.toDto(valorBaseRamo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValorBaseRamoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(valorBaseRamoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ValorBaseRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteValorBaseRamo() throws Exception {
        // Initialize the database
        insertedValorBaseRamo = valorBaseRamoRepository.saveAndFlush(valorBaseRamo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the valorBaseRamo
        restValorBaseRamoMockMvc
            .perform(delete(ENTITY_API_URL_ID, valorBaseRamo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return valorBaseRamoRepository.count();
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

    protected ValorBaseRamo getPersistedValorBaseRamo(ValorBaseRamo valorBaseRamo) {
        return valorBaseRamoRepository.findById(valorBaseRamo.getId()).orElseThrow();
    }

    protected void assertPersistedValorBaseRamoToMatchAllProperties(ValorBaseRamo expectedValorBaseRamo) {
        assertValorBaseRamoAllPropertiesEquals(expectedValorBaseRamo, getPersistedValorBaseRamo(expectedValorBaseRamo));
    }

    protected void assertPersistedValorBaseRamoToMatchUpdatableProperties(ValorBaseRamo expectedValorBaseRamo) {
        assertValorBaseRamoAllUpdatablePropertiesEquals(expectedValorBaseRamo, getPersistedValorBaseRamo(expectedValorBaseRamo));
    }
}
