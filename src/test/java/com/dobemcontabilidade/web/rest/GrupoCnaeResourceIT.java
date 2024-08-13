package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.GrupoCnaeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.DivisaoCnae;
import com.dobemcontabilidade.domain.GrupoCnae;
import com.dobemcontabilidade.repository.GrupoCnaeRepository;
import com.dobemcontabilidade.service.GrupoCnaeService;
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
 * Integration tests for the {@link GrupoCnaeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GrupoCnaeResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/grupo-cnaes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GrupoCnaeRepository grupoCnaeRepository;

    @Mock
    private GrupoCnaeRepository grupoCnaeRepositoryMock;

    @Mock
    private GrupoCnaeService grupoCnaeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoCnaeMockMvc;

    private GrupoCnae grupoCnae;

    private GrupoCnae insertedGrupoCnae;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoCnae createEntity(EntityManager em) {
        GrupoCnae grupoCnae = new GrupoCnae().codigo(DEFAULT_CODIGO).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        DivisaoCnae divisaoCnae;
        if (TestUtil.findAll(em, DivisaoCnae.class).isEmpty()) {
            divisaoCnae = DivisaoCnaeResourceIT.createEntity(em);
            em.persist(divisaoCnae);
            em.flush();
        } else {
            divisaoCnae = TestUtil.findAll(em, DivisaoCnae.class).get(0);
        }
        grupoCnae.setDivisao(divisaoCnae);
        return grupoCnae;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoCnae createUpdatedEntity(EntityManager em) {
        GrupoCnae grupoCnae = new GrupoCnae().codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);
        // Add required entity
        DivisaoCnae divisaoCnae;
        if (TestUtil.findAll(em, DivisaoCnae.class).isEmpty()) {
            divisaoCnae = DivisaoCnaeResourceIT.createUpdatedEntity(em);
            em.persist(divisaoCnae);
            em.flush();
        } else {
            divisaoCnae = TestUtil.findAll(em, DivisaoCnae.class).get(0);
        }
        grupoCnae.setDivisao(divisaoCnae);
        return grupoCnae;
    }

    @BeforeEach
    public void initTest() {
        grupoCnae = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGrupoCnae != null) {
            grupoCnaeRepository.delete(insertedGrupoCnae);
            insertedGrupoCnae = null;
        }
    }

    @Test
    @Transactional
    void createGrupoCnae() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GrupoCnae
        var returnedGrupoCnae = om.readValue(
            restGrupoCnaeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoCnae)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GrupoCnae.class
        );

        // Validate the GrupoCnae in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGrupoCnaeUpdatableFieldsEquals(returnedGrupoCnae, getPersistedGrupoCnae(returnedGrupoCnae));

        insertedGrupoCnae = returnedGrupoCnae;
    }

    @Test
    @Transactional
    void createGrupoCnaeWithExistingId() throws Exception {
        // Create the GrupoCnae with an existing ID
        grupoCnae.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoCnae)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrupoCnaes() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        // Get all the grupoCnaeList
        restGrupoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrupoCnaesWithEagerRelationshipsIsEnabled() throws Exception {
        when(grupoCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(grupoCnaeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrupoCnaesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(grupoCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(grupoCnaeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGrupoCnae() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        // Get the grupoCnae
        restGrupoCnaeMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoCnae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoCnae.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getGrupoCnaesByIdFiltering() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        Long id = grupoCnae.getId();

        defaultGrupoCnaeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultGrupoCnaeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultGrupoCnaeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGrupoCnaesByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        // Get all the grupoCnaeList where codigo equals to
        defaultGrupoCnaeFiltering("codigo.equals=" + DEFAULT_CODIGO, "codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllGrupoCnaesByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        // Get all the grupoCnaeList where codigo in
        defaultGrupoCnaeFiltering("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO, "codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllGrupoCnaesByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        // Get all the grupoCnaeList where codigo is not null
        defaultGrupoCnaeFiltering("codigo.specified=true", "codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllGrupoCnaesByCodigoContainsSomething() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        // Get all the grupoCnaeList where codigo contains
        defaultGrupoCnaeFiltering("codigo.contains=" + DEFAULT_CODIGO, "codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllGrupoCnaesByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        // Get all the grupoCnaeList where codigo does not contain
        defaultGrupoCnaeFiltering("codigo.doesNotContain=" + UPDATED_CODIGO, "codigo.doesNotContain=" + DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    void getAllGrupoCnaesByDivisaoIsEqualToSomething() throws Exception {
        DivisaoCnae divisao;
        if (TestUtil.findAll(em, DivisaoCnae.class).isEmpty()) {
            grupoCnaeRepository.saveAndFlush(grupoCnae);
            divisao = DivisaoCnaeResourceIT.createEntity(em);
        } else {
            divisao = TestUtil.findAll(em, DivisaoCnae.class).get(0);
        }
        em.persist(divisao);
        em.flush();
        grupoCnae.setDivisao(divisao);
        grupoCnaeRepository.saveAndFlush(grupoCnae);
        Long divisaoId = divisao.getId();
        // Get all the grupoCnaeList where divisao equals to divisaoId
        defaultGrupoCnaeShouldBeFound("divisaoId.equals=" + divisaoId);

        // Get all the grupoCnaeList where divisao equals to (divisaoId + 1)
        defaultGrupoCnaeShouldNotBeFound("divisaoId.equals=" + (divisaoId + 1));
    }

    private void defaultGrupoCnaeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultGrupoCnaeShouldBeFound(shouldBeFound);
        defaultGrupoCnaeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGrupoCnaeShouldBeFound(String filter) throws Exception {
        restGrupoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restGrupoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGrupoCnaeShouldNotBeFound(String filter) throws Exception {
        restGrupoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGrupoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGrupoCnae() throws Exception {
        // Get the grupoCnae
        restGrupoCnaeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrupoCnae() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoCnae
        GrupoCnae updatedGrupoCnae = grupoCnaeRepository.findById(grupoCnae.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGrupoCnae are not directly saved in db
        em.detach(updatedGrupoCnae);
        updatedGrupoCnae.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restGrupoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoCnae.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGrupoCnae))
            )
            .andExpect(status().isOk());

        // Validate the GrupoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGrupoCnaeToMatchAllProperties(updatedGrupoCnae);
    }

    @Test
    @Transactional
    void putNonExistingGrupoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoCnae.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoCnae.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoCnae))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoCnae.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoCnae))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoCnae.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoCnaeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoCnae)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoCnae using partial update
        GrupoCnae partialUpdatedGrupoCnae = new GrupoCnae();
        partialUpdatedGrupoCnae.setId(grupoCnae.getId());

        restGrupoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoCnae))
            )
            .andExpect(status().isOk());

        // Validate the GrupoCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoCnaeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGrupoCnae, grupoCnae),
            getPersistedGrupoCnae(grupoCnae)
        );
    }

    @Test
    @Transactional
    void fullUpdateGrupoCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoCnae using partial update
        GrupoCnae partialUpdatedGrupoCnae = new GrupoCnae();
        partialUpdatedGrupoCnae.setId(grupoCnae.getId());

        partialUpdatedGrupoCnae.codigo(UPDATED_CODIGO).descricao(UPDATED_DESCRICAO);

        restGrupoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoCnae))
            )
            .andExpect(status().isOk());

        // Validate the GrupoCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoCnaeUpdatableFieldsEquals(partialUpdatedGrupoCnae, getPersistedGrupoCnae(partialUpdatedGrupoCnae));
    }

    @Test
    @Transactional
    void patchNonExistingGrupoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoCnae.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoCnae))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoCnae.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoCnae))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoCnae.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoCnaeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(grupoCnae)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoCnae() throws Exception {
        // Initialize the database
        insertedGrupoCnae = grupoCnaeRepository.saveAndFlush(grupoCnae);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grupoCnae
        restGrupoCnaeMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoCnae.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return grupoCnaeRepository.count();
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

    protected GrupoCnae getPersistedGrupoCnae(GrupoCnae grupoCnae) {
        return grupoCnaeRepository.findById(grupoCnae.getId()).orElseThrow();
    }

    protected void assertPersistedGrupoCnaeToMatchAllProperties(GrupoCnae expectedGrupoCnae) {
        assertGrupoCnaeAllPropertiesEquals(expectedGrupoCnae, getPersistedGrupoCnae(expectedGrupoCnae));
    }

    protected void assertPersistedGrupoCnaeToMatchUpdatableProperties(GrupoCnae expectedGrupoCnae) {
        assertGrupoCnaeAllUpdatablePropertiesEquals(expectedGrupoCnae, getPersistedGrupoCnae(expectedGrupoCnae));
    }
}
