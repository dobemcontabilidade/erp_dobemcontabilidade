package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AdicionalRamoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AdicionalRamo;
import com.dobemcontabilidade.domain.PlanoAssinaturaContabil;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.repository.AdicionalRamoRepository;
import com.dobemcontabilidade.service.AdicionalRamoService;
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
 * Integration tests for the {@link AdicionalRamoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AdicionalRamoResourceIT {

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;
    private static final Double SMALLER_VALOR = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/adicional-ramos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AdicionalRamoRepository adicionalRamoRepository;

    @Mock
    private AdicionalRamoRepository adicionalRamoRepositoryMock;

    @Mock
    private AdicionalRamoService adicionalRamoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdicionalRamoMockMvc;

    private AdicionalRamo adicionalRamo;

    private AdicionalRamo insertedAdicionalRamo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdicionalRamo createEntity(EntityManager em) {
        AdicionalRamo adicionalRamo = new AdicionalRamo().valor(DEFAULT_VALOR);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        adicionalRamo.setRamo(ramo);
        // Add required entity
        PlanoAssinaturaContabil planoAssinaturaContabil;
        if (TestUtil.findAll(em, PlanoAssinaturaContabil.class).isEmpty()) {
            planoAssinaturaContabil = PlanoAssinaturaContabilResourceIT.createEntity(em);
            em.persist(planoAssinaturaContabil);
            em.flush();
        } else {
            planoAssinaturaContabil = TestUtil.findAll(em, PlanoAssinaturaContabil.class).get(0);
        }
        adicionalRamo.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        return adicionalRamo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdicionalRamo createUpdatedEntity(EntityManager em) {
        AdicionalRamo adicionalRamo = new AdicionalRamo().valor(UPDATED_VALOR);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createUpdatedEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        adicionalRamo.setRamo(ramo);
        // Add required entity
        PlanoAssinaturaContabil planoAssinaturaContabil;
        if (TestUtil.findAll(em, PlanoAssinaturaContabil.class).isEmpty()) {
            planoAssinaturaContabil = PlanoAssinaturaContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoAssinaturaContabil);
            em.flush();
        } else {
            planoAssinaturaContabil = TestUtil.findAll(em, PlanoAssinaturaContabil.class).get(0);
        }
        adicionalRamo.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        return adicionalRamo;
    }

    @BeforeEach
    public void initTest() {
        adicionalRamo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAdicionalRamo != null) {
            adicionalRamoRepository.delete(insertedAdicionalRamo);
            insertedAdicionalRamo = null;
        }
    }

    @Test
    @Transactional
    void createAdicionalRamo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AdicionalRamo
        var returnedAdicionalRamo = om.readValue(
            restAdicionalRamoMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalRamo))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AdicionalRamo.class
        );

        // Validate the AdicionalRamo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAdicionalRamoUpdatableFieldsEquals(returnedAdicionalRamo, getPersistedAdicionalRamo(returnedAdicionalRamo));

        insertedAdicionalRamo = returnedAdicionalRamo;
    }

    @Test
    @Transactional
    void createAdicionalRamoWithExistingId() throws Exception {
        // Create the AdicionalRamo with an existing ID
        adicionalRamo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdicionalRamoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalRamo)))
            .andExpect(status().isBadRequest());

        // Validate the AdicionalRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        adicionalRamo.setValor(null);

        // Create the AdicionalRamo, which fails.

        restAdicionalRamoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalRamo)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdicionalRamos() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        // Get all the adicionalRamoList
        restAdicionalRamoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adicionalRamo.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAdicionalRamosWithEagerRelationshipsIsEnabled() throws Exception {
        when(adicionalRamoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdicionalRamoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(adicionalRamoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAdicionalRamosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(adicionalRamoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAdicionalRamoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(adicionalRamoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAdicionalRamo() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        // Get the adicionalRamo
        restAdicionalRamoMockMvc
            .perform(get(ENTITY_API_URL_ID, adicionalRamo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adicionalRamo.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    void getAdicionalRamosByIdFiltering() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        Long id = adicionalRamo.getId();

        defaultAdicionalRamoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAdicionalRamoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAdicionalRamoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAdicionalRamosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        // Get all the adicionalRamoList where valor equals to
        defaultAdicionalRamoFiltering("valor.equals=" + DEFAULT_VALOR, "valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllAdicionalRamosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        // Get all the adicionalRamoList where valor in
        defaultAdicionalRamoFiltering("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR, "valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllAdicionalRamosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        // Get all the adicionalRamoList where valor is not null
        defaultAdicionalRamoFiltering("valor.specified=true", "valor.specified=false");
    }

    @Test
    @Transactional
    void getAllAdicionalRamosByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        // Get all the adicionalRamoList where valor is greater than or equal to
        defaultAdicionalRamoFiltering("valor.greaterThanOrEqual=" + DEFAULT_VALOR, "valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllAdicionalRamosByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        // Get all the adicionalRamoList where valor is less than or equal to
        defaultAdicionalRamoFiltering("valor.lessThanOrEqual=" + DEFAULT_VALOR, "valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    void getAllAdicionalRamosByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        // Get all the adicionalRamoList where valor is less than
        defaultAdicionalRamoFiltering("valor.lessThan=" + UPDATED_VALOR, "valor.lessThan=" + DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void getAllAdicionalRamosByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        // Get all the adicionalRamoList where valor is greater than
        defaultAdicionalRamoFiltering("valor.greaterThan=" + SMALLER_VALOR, "valor.greaterThan=" + DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void getAllAdicionalRamosByRamoIsEqualToSomething() throws Exception {
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            adicionalRamoRepository.saveAndFlush(adicionalRamo);
            ramo = RamoResourceIT.createEntity(em);
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        em.persist(ramo);
        em.flush();
        adicionalRamo.setRamo(ramo);
        adicionalRamoRepository.saveAndFlush(adicionalRamo);
        Long ramoId = ramo.getId();
        // Get all the adicionalRamoList where ramo equals to ramoId
        defaultAdicionalRamoShouldBeFound("ramoId.equals=" + ramoId);

        // Get all the adicionalRamoList where ramo equals to (ramoId + 1)
        defaultAdicionalRamoShouldNotBeFound("ramoId.equals=" + (ramoId + 1));
    }

    @Test
    @Transactional
    void getAllAdicionalRamosByPlanoAssinaturaContabilIsEqualToSomething() throws Exception {
        PlanoAssinaturaContabil planoAssinaturaContabil;
        if (TestUtil.findAll(em, PlanoAssinaturaContabil.class).isEmpty()) {
            adicionalRamoRepository.saveAndFlush(adicionalRamo);
            planoAssinaturaContabil = PlanoAssinaturaContabilResourceIT.createEntity(em);
        } else {
            planoAssinaturaContabil = TestUtil.findAll(em, PlanoAssinaturaContabil.class).get(0);
        }
        em.persist(planoAssinaturaContabil);
        em.flush();
        adicionalRamo.setPlanoAssinaturaContabil(planoAssinaturaContabil);
        adicionalRamoRepository.saveAndFlush(adicionalRamo);
        Long planoAssinaturaContabilId = planoAssinaturaContabil.getId();
        // Get all the adicionalRamoList where planoAssinaturaContabil equals to planoAssinaturaContabilId
        defaultAdicionalRamoShouldBeFound("planoAssinaturaContabilId.equals=" + planoAssinaturaContabilId);

        // Get all the adicionalRamoList where planoAssinaturaContabil equals to (planoAssinaturaContabilId + 1)
        defaultAdicionalRamoShouldNotBeFound("planoAssinaturaContabilId.equals=" + (planoAssinaturaContabilId + 1));
    }

    private void defaultAdicionalRamoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAdicionalRamoShouldBeFound(shouldBeFound);
        defaultAdicionalRamoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdicionalRamoShouldBeFound(String filter) throws Exception {
        restAdicionalRamoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adicionalRamo.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));

        // Check, that the count call also returns 1
        restAdicionalRamoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdicionalRamoShouldNotBeFound(String filter) throws Exception {
        restAdicionalRamoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdicionalRamoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAdicionalRamo() throws Exception {
        // Get the adicionalRamo
        restAdicionalRamoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdicionalRamo() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionalRamo
        AdicionalRamo updatedAdicionalRamo = adicionalRamoRepository.findById(adicionalRamo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAdicionalRamo are not directly saved in db
        em.detach(updatedAdicionalRamo);
        updatedAdicionalRamo.valor(UPDATED_VALOR);

        restAdicionalRamoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAdicionalRamo.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAdicionalRamo))
            )
            .andExpect(status().isOk());

        // Validate the AdicionalRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAdicionalRamoToMatchAllProperties(updatedAdicionalRamo);
    }

    @Test
    @Transactional
    void putNonExistingAdicionalRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalRamo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdicionalRamoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adicionalRamo.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalRamo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdicionalRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalRamo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalRamoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(adicionalRamo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdicionalRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalRamo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalRamoMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(adicionalRamo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdicionalRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdicionalRamoWithPatch() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionalRamo using partial update
        AdicionalRamo partialUpdatedAdicionalRamo = new AdicionalRamo();
        partialUpdatedAdicionalRamo.setId(adicionalRamo.getId());

        partialUpdatedAdicionalRamo.valor(UPDATED_VALOR);

        restAdicionalRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdicionalRamo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdicionalRamo))
            )
            .andExpect(status().isOk());

        // Validate the AdicionalRamo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdicionalRamoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAdicionalRamo, adicionalRamo),
            getPersistedAdicionalRamo(adicionalRamo)
        );
    }

    @Test
    @Transactional
    void fullUpdateAdicionalRamoWithPatch() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the adicionalRamo using partial update
        AdicionalRamo partialUpdatedAdicionalRamo = new AdicionalRamo();
        partialUpdatedAdicionalRamo.setId(adicionalRamo.getId());

        partialUpdatedAdicionalRamo.valor(UPDATED_VALOR);

        restAdicionalRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdicionalRamo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAdicionalRamo))
            )
            .andExpect(status().isOk());

        // Validate the AdicionalRamo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAdicionalRamoUpdatableFieldsEquals(partialUpdatedAdicionalRamo, getPersistedAdicionalRamo(partialUpdatedAdicionalRamo));
    }

    @Test
    @Transactional
    void patchNonExistingAdicionalRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalRamo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdicionalRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adicionalRamo.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adicionalRamo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdicionalRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalRamo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalRamoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(adicionalRamo))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdicionalRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdicionalRamo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        adicionalRamo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdicionalRamoMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(adicionalRamo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AdicionalRamo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdicionalRamo() throws Exception {
        // Initialize the database
        insertedAdicionalRamo = adicionalRamoRepository.saveAndFlush(adicionalRamo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the adicionalRamo
        restAdicionalRamoMockMvc
            .perform(delete(ENTITY_API_URL_ID, adicionalRamo.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return adicionalRamoRepository.count();
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

    protected AdicionalRamo getPersistedAdicionalRamo(AdicionalRamo adicionalRamo) {
        return adicionalRamoRepository.findById(adicionalRamo.getId()).orElseThrow();
    }

    protected void assertPersistedAdicionalRamoToMatchAllProperties(AdicionalRamo expectedAdicionalRamo) {
        assertAdicionalRamoAllPropertiesEquals(expectedAdicionalRamo, getPersistedAdicionalRamo(expectedAdicionalRamo));
    }

    protected void assertPersistedAdicionalRamoToMatchUpdatableProperties(AdicionalRamo expectedAdicionalRamo) {
        assertAdicionalRamoAllUpdatablePropertiesEquals(expectedAdicionalRamo, getPersistedAdicionalRamo(expectedAdicionalRamo));
    }
}
