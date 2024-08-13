package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.CriterioAvaliacaoAtorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AtorAvaliado;
import com.dobemcontabilidade.domain.CriterioAvaliacao;
import com.dobemcontabilidade.domain.CriterioAvaliacaoAtor;
import com.dobemcontabilidade.repository.CriterioAvaliacaoAtorRepository;
import com.dobemcontabilidade.service.CriterioAvaliacaoAtorService;
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
 * Integration tests for the {@link CriterioAvaliacaoAtorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CriterioAvaliacaoAtorResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String ENTITY_API_URL = "/api/criterio-avaliacao-ators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CriterioAvaliacaoAtorRepository criterioAvaliacaoAtorRepository;

    @Mock
    private CriterioAvaliacaoAtorRepository criterioAvaliacaoAtorRepositoryMock;

    @Mock
    private CriterioAvaliacaoAtorService criterioAvaliacaoAtorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCriterioAvaliacaoAtorMockMvc;

    private CriterioAvaliacaoAtor criterioAvaliacaoAtor;

    private CriterioAvaliacaoAtor insertedCriterioAvaliacaoAtor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CriterioAvaliacaoAtor createEntity(EntityManager em) {
        CriterioAvaliacaoAtor criterioAvaliacaoAtor = new CriterioAvaliacaoAtor().descricao(DEFAULT_DESCRICAO).ativo(DEFAULT_ATIVO);
        // Add required entity
        CriterioAvaliacao criterioAvaliacao;
        if (TestUtil.findAll(em, CriterioAvaliacao.class).isEmpty()) {
            criterioAvaliacao = CriterioAvaliacaoResourceIT.createEntity(em);
            em.persist(criterioAvaliacao);
            em.flush();
        } else {
            criterioAvaliacao = TestUtil.findAll(em, CriterioAvaliacao.class).get(0);
        }
        criterioAvaliacaoAtor.setCriterioAvaliacao(criterioAvaliacao);
        // Add required entity
        AtorAvaliado atorAvaliado;
        if (TestUtil.findAll(em, AtorAvaliado.class).isEmpty()) {
            atorAvaliado = AtorAvaliadoResourceIT.createEntity(em);
            em.persist(atorAvaliado);
            em.flush();
        } else {
            atorAvaliado = TestUtil.findAll(em, AtorAvaliado.class).get(0);
        }
        criterioAvaliacaoAtor.setAtorAvaliado(atorAvaliado);
        return criterioAvaliacaoAtor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CriterioAvaliacaoAtor createUpdatedEntity(EntityManager em) {
        CriterioAvaliacaoAtor criterioAvaliacaoAtor = new CriterioAvaliacaoAtor().descricao(UPDATED_DESCRICAO).ativo(UPDATED_ATIVO);
        // Add required entity
        CriterioAvaliacao criterioAvaliacao;
        if (TestUtil.findAll(em, CriterioAvaliacao.class).isEmpty()) {
            criterioAvaliacao = CriterioAvaliacaoResourceIT.createUpdatedEntity(em);
            em.persist(criterioAvaliacao);
            em.flush();
        } else {
            criterioAvaliacao = TestUtil.findAll(em, CriterioAvaliacao.class).get(0);
        }
        criterioAvaliacaoAtor.setCriterioAvaliacao(criterioAvaliacao);
        // Add required entity
        AtorAvaliado atorAvaliado;
        if (TestUtil.findAll(em, AtorAvaliado.class).isEmpty()) {
            atorAvaliado = AtorAvaliadoResourceIT.createUpdatedEntity(em);
            em.persist(atorAvaliado);
            em.flush();
        } else {
            atorAvaliado = TestUtil.findAll(em, AtorAvaliado.class).get(0);
        }
        criterioAvaliacaoAtor.setAtorAvaliado(atorAvaliado);
        return criterioAvaliacaoAtor;
    }

    @BeforeEach
    public void initTest() {
        criterioAvaliacaoAtor = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCriterioAvaliacaoAtor != null) {
            criterioAvaliacaoAtorRepository.delete(insertedCriterioAvaliacaoAtor);
            insertedCriterioAvaliacaoAtor = null;
        }
    }

    @Test
    @Transactional
    void createCriterioAvaliacaoAtor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CriterioAvaliacaoAtor
        var returnedCriterioAvaliacaoAtor = om.readValue(
            restCriterioAvaliacaoAtorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(criterioAvaliacaoAtor)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CriterioAvaliacaoAtor.class
        );

        // Validate the CriterioAvaliacaoAtor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCriterioAvaliacaoAtorUpdatableFieldsEquals(
            returnedCriterioAvaliacaoAtor,
            getPersistedCriterioAvaliacaoAtor(returnedCriterioAvaliacaoAtor)
        );

        insertedCriterioAvaliacaoAtor = returnedCriterioAvaliacaoAtor;
    }

    @Test
    @Transactional
    void createCriterioAvaliacaoAtorWithExistingId() throws Exception {
        // Create the CriterioAvaliacaoAtor with an existing ID
        criterioAvaliacaoAtor.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCriterioAvaliacaoAtorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(criterioAvaliacaoAtor)))
            .andExpect(status().isBadRequest());

        // Validate the CriterioAvaliacaoAtor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCriterioAvaliacaoAtors() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacaoAtor = criterioAvaliacaoAtorRepository.saveAndFlush(criterioAvaliacaoAtor);

        // Get all the criterioAvaliacaoAtorList
        restCriterioAvaliacaoAtorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(criterioAvaliacaoAtor.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCriterioAvaliacaoAtorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(criterioAvaliacaoAtorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCriterioAvaliacaoAtorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(criterioAvaliacaoAtorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCriterioAvaliacaoAtorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(criterioAvaliacaoAtorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCriterioAvaliacaoAtorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(criterioAvaliacaoAtorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCriterioAvaliacaoAtor() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacaoAtor = criterioAvaliacaoAtorRepository.saveAndFlush(criterioAvaliacaoAtor);

        // Get the criterioAvaliacaoAtor
        restCriterioAvaliacaoAtorMockMvc
            .perform(get(ENTITY_API_URL_ID, criterioAvaliacaoAtor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(criterioAvaliacaoAtor.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCriterioAvaliacaoAtor() throws Exception {
        // Get the criterioAvaliacaoAtor
        restCriterioAvaliacaoAtorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCriterioAvaliacaoAtor() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacaoAtor = criterioAvaliacaoAtorRepository.saveAndFlush(criterioAvaliacaoAtor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the criterioAvaliacaoAtor
        CriterioAvaliacaoAtor updatedCriterioAvaliacaoAtor = criterioAvaliacaoAtorRepository
            .findById(criterioAvaliacaoAtor.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedCriterioAvaliacaoAtor are not directly saved in db
        em.detach(updatedCriterioAvaliacaoAtor);
        updatedCriterioAvaliacaoAtor.descricao(UPDATED_DESCRICAO).ativo(UPDATED_ATIVO);

        restCriterioAvaliacaoAtorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCriterioAvaliacaoAtor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCriterioAvaliacaoAtor))
            )
            .andExpect(status().isOk());

        // Validate the CriterioAvaliacaoAtor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCriterioAvaliacaoAtorToMatchAllProperties(updatedCriterioAvaliacaoAtor);
    }

    @Test
    @Transactional
    void putNonExistingCriterioAvaliacaoAtor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacaoAtor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoAtorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, criterioAvaliacaoAtor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(criterioAvaliacaoAtor))
            )
            .andExpect(status().isBadRequest());

        // Validate the CriterioAvaliacaoAtor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCriterioAvaliacaoAtor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacaoAtor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoAtorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(criterioAvaliacaoAtor))
            )
            .andExpect(status().isBadRequest());

        // Validate the CriterioAvaliacaoAtor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCriterioAvaliacaoAtor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacaoAtor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoAtorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(criterioAvaliacaoAtor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CriterioAvaliacaoAtor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCriterioAvaliacaoAtorWithPatch() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacaoAtor = criterioAvaliacaoAtorRepository.saveAndFlush(criterioAvaliacaoAtor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the criterioAvaliacaoAtor using partial update
        CriterioAvaliacaoAtor partialUpdatedCriterioAvaliacaoAtor = new CriterioAvaliacaoAtor();
        partialUpdatedCriterioAvaliacaoAtor.setId(criterioAvaliacaoAtor.getId());

        partialUpdatedCriterioAvaliacaoAtor.descricao(UPDATED_DESCRICAO);

        restCriterioAvaliacaoAtorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCriterioAvaliacaoAtor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCriterioAvaliacaoAtor))
            )
            .andExpect(status().isOk());

        // Validate the CriterioAvaliacaoAtor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCriterioAvaliacaoAtorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCriterioAvaliacaoAtor, criterioAvaliacaoAtor),
            getPersistedCriterioAvaliacaoAtor(criterioAvaliacaoAtor)
        );
    }

    @Test
    @Transactional
    void fullUpdateCriterioAvaliacaoAtorWithPatch() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacaoAtor = criterioAvaliacaoAtorRepository.saveAndFlush(criterioAvaliacaoAtor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the criterioAvaliacaoAtor using partial update
        CriterioAvaliacaoAtor partialUpdatedCriterioAvaliacaoAtor = new CriterioAvaliacaoAtor();
        partialUpdatedCriterioAvaliacaoAtor.setId(criterioAvaliacaoAtor.getId());

        partialUpdatedCriterioAvaliacaoAtor.descricao(UPDATED_DESCRICAO).ativo(UPDATED_ATIVO);

        restCriterioAvaliacaoAtorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCriterioAvaliacaoAtor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCriterioAvaliacaoAtor))
            )
            .andExpect(status().isOk());

        // Validate the CriterioAvaliacaoAtor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCriterioAvaliacaoAtorUpdatableFieldsEquals(
            partialUpdatedCriterioAvaliacaoAtor,
            getPersistedCriterioAvaliacaoAtor(partialUpdatedCriterioAvaliacaoAtor)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCriterioAvaliacaoAtor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacaoAtor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoAtorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, criterioAvaliacaoAtor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(criterioAvaliacaoAtor))
            )
            .andExpect(status().isBadRequest());

        // Validate the CriterioAvaliacaoAtor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCriterioAvaliacaoAtor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacaoAtor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoAtorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(criterioAvaliacaoAtor))
            )
            .andExpect(status().isBadRequest());

        // Validate the CriterioAvaliacaoAtor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCriterioAvaliacaoAtor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        criterioAvaliacaoAtor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCriterioAvaliacaoAtorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(criterioAvaliacaoAtor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CriterioAvaliacaoAtor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCriterioAvaliacaoAtor() throws Exception {
        // Initialize the database
        insertedCriterioAvaliacaoAtor = criterioAvaliacaoAtorRepository.saveAndFlush(criterioAvaliacaoAtor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the criterioAvaliacaoAtor
        restCriterioAvaliacaoAtorMockMvc
            .perform(delete(ENTITY_API_URL_ID, criterioAvaliacaoAtor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return criterioAvaliacaoAtorRepository.count();
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

    protected CriterioAvaliacaoAtor getPersistedCriterioAvaliacaoAtor(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        return criterioAvaliacaoAtorRepository.findById(criterioAvaliacaoAtor.getId()).orElseThrow();
    }

    protected void assertPersistedCriterioAvaliacaoAtorToMatchAllProperties(CriterioAvaliacaoAtor expectedCriterioAvaliacaoAtor) {
        assertCriterioAvaliacaoAtorAllPropertiesEquals(
            expectedCriterioAvaliacaoAtor,
            getPersistedCriterioAvaliacaoAtor(expectedCriterioAvaliacaoAtor)
        );
    }

    protected void assertPersistedCriterioAvaliacaoAtorToMatchUpdatableProperties(CriterioAvaliacaoAtor expectedCriterioAvaliacaoAtor) {
        assertCriterioAvaliacaoAtorAllUpdatablePropertiesEquals(
            expectedCriterioAvaliacaoAtor,
            getPersistedCriterioAvaliacaoAtor(expectedCriterioAvaliacaoAtor)
        );
    }
}
