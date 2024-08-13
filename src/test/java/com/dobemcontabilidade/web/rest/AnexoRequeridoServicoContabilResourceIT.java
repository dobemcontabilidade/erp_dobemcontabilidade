package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoRequeridoServicoContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoRequerido;
import com.dobemcontabilidade.domain.AnexoRequeridoServicoContabil;
import com.dobemcontabilidade.domain.ServicoContabil;
import com.dobemcontabilidade.repository.AnexoRequeridoServicoContabilRepository;
import com.dobemcontabilidade.service.AnexoRequeridoServicoContabilService;
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
 * Integration tests for the {@link AnexoRequeridoServicoContabilResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoRequeridoServicoContabilResourceIT {

    private static final Boolean DEFAULT_OBRIGATORIO = false;
    private static final Boolean UPDATED_OBRIGATORIO = true;

    private static final String ENTITY_API_URL = "/api/anexo-requerido-servico-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoRequeridoServicoContabilRepository anexoRequeridoServicoContabilRepository;

    @Mock
    private AnexoRequeridoServicoContabilRepository anexoRequeridoServicoContabilRepositoryMock;

    @Mock
    private AnexoRequeridoServicoContabilService anexoRequeridoServicoContabilServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoRequeridoServicoContabilMockMvc;

    private AnexoRequeridoServicoContabil anexoRequeridoServicoContabil;

    private AnexoRequeridoServicoContabil insertedAnexoRequeridoServicoContabil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequeridoServicoContabil createEntity(EntityManager em) {
        AnexoRequeridoServicoContabil anexoRequeridoServicoContabil = new AnexoRequeridoServicoContabil().obrigatorio(DEFAULT_OBRIGATORIO);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        anexoRequeridoServicoContabil.setServicoContabil(servicoContabil);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoRequeridoServicoContabil.setAnexoRequerido(anexoRequerido);
        return anexoRequeridoServicoContabil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequeridoServicoContabil createUpdatedEntity(EntityManager em) {
        AnexoRequeridoServicoContabil anexoRequeridoServicoContabil = new AnexoRequeridoServicoContabil().obrigatorio(UPDATED_OBRIGATORIO);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        anexoRequeridoServicoContabil.setServicoContabil(servicoContabil);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createUpdatedEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoRequeridoServicoContabil.setAnexoRequerido(anexoRequerido);
        return anexoRequeridoServicoContabil;
    }

    @BeforeEach
    public void initTest() {
        anexoRequeridoServicoContabil = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoRequeridoServicoContabil != null) {
            anexoRequeridoServicoContabilRepository.delete(insertedAnexoRequeridoServicoContabil);
            insertedAnexoRequeridoServicoContabil = null;
        }
    }

    @Test
    @Transactional
    void createAnexoRequeridoServicoContabil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoRequeridoServicoContabil
        var returnedAnexoRequeridoServicoContabil = om.readValue(
            restAnexoRequeridoServicoContabilMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(anexoRequeridoServicoContabil))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoRequeridoServicoContabil.class
        );

        // Validate the AnexoRequeridoServicoContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnexoRequeridoServicoContabilUpdatableFieldsEquals(
            returnedAnexoRequeridoServicoContabil,
            getPersistedAnexoRequeridoServicoContabil(returnedAnexoRequeridoServicoContabil)
        );

        insertedAnexoRequeridoServicoContabil = returnedAnexoRequeridoServicoContabil;
    }

    @Test
    @Transactional
    void createAnexoRequeridoServicoContabilWithExistingId() throws Exception {
        // Create the AnexoRequeridoServicoContabil with an existing ID
        anexoRequeridoServicoContabil.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoRequeridoServicoContabilMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoServicoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridoServicoContabils() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoServicoContabil = anexoRequeridoServicoContabilRepository.saveAndFlush(anexoRequeridoServicoContabil);

        // Get all the anexoRequeridoServicoContabilList
        restAnexoRequeridoServicoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoRequeridoServicoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].obrigatorio").value(hasItem(DEFAULT_OBRIGATORIO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoRequeridoServicoContabilsWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoRequeridoServicoContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoRequeridoServicoContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoRequeridoServicoContabilServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoRequeridoServicoContabilsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoRequeridoServicoContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoRequeridoServicoContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoRequeridoServicoContabilRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoRequeridoServicoContabil() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoServicoContabil = anexoRequeridoServicoContabilRepository.saveAndFlush(anexoRequeridoServicoContabil);

        // Get the anexoRequeridoServicoContabil
        restAnexoRequeridoServicoContabilMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoRequeridoServicoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoRequeridoServicoContabil.getId().intValue()))
            .andExpect(jsonPath("$.obrigatorio").value(DEFAULT_OBRIGATORIO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAnexoRequeridoServicoContabil() throws Exception {
        // Get the anexoRequeridoServicoContabil
        restAnexoRequeridoServicoContabilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoRequeridoServicoContabil() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoServicoContabil = anexoRequeridoServicoContabilRepository.saveAndFlush(anexoRequeridoServicoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoServicoContabil
        AnexoRequeridoServicoContabil updatedAnexoRequeridoServicoContabil = anexoRequeridoServicoContabilRepository
            .findById(anexoRequeridoServicoContabil.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoRequeridoServicoContabil are not directly saved in db
        em.detach(updatedAnexoRequeridoServicoContabil);
        updatedAnexoRequeridoServicoContabil.obrigatorio(UPDATED_OBRIGATORIO);

        restAnexoRequeridoServicoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnexoRequeridoServicoContabil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnexoRequeridoServicoContabil))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoRequeridoServicoContabilToMatchAllProperties(updatedAnexoRequeridoServicoContabil);
    }

    @Test
    @Transactional
    void putNonExistingAnexoRequeridoServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoServicoContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoServicoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoRequeridoServicoContabil.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequeridoServicoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoRequeridoServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoServicoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoServicoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequeridoServicoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoRequeridoServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoServicoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoServicoContabilMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoServicoContabil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequeridoServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoRequeridoServicoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoServicoContabil = anexoRequeridoServicoContabilRepository.saveAndFlush(anexoRequeridoServicoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoServicoContabil using partial update
        AnexoRequeridoServicoContabil partialUpdatedAnexoRequeridoServicoContabil = new AnexoRequeridoServicoContabil();
        partialUpdatedAnexoRequeridoServicoContabil.setId(anexoRequeridoServicoContabil.getId());

        restAnexoRequeridoServicoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequeridoServicoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequeridoServicoContabil))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoServicoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoServicoContabilUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoRequeridoServicoContabil, anexoRequeridoServicoContabil),
            getPersistedAnexoRequeridoServicoContabil(anexoRequeridoServicoContabil)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoRequeridoServicoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoServicoContabil = anexoRequeridoServicoContabilRepository.saveAndFlush(anexoRequeridoServicoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoServicoContabil using partial update
        AnexoRequeridoServicoContabil partialUpdatedAnexoRequeridoServicoContabil = new AnexoRequeridoServicoContabil();
        partialUpdatedAnexoRequeridoServicoContabil.setId(anexoRequeridoServicoContabil.getId());

        partialUpdatedAnexoRequeridoServicoContabil.obrigatorio(UPDATED_OBRIGATORIO);

        restAnexoRequeridoServicoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequeridoServicoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequeridoServicoContabil))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoServicoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoServicoContabilUpdatableFieldsEquals(
            partialUpdatedAnexoRequeridoServicoContabil,
            getPersistedAnexoRequeridoServicoContabil(partialUpdatedAnexoRequeridoServicoContabil)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAnexoRequeridoServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoServicoContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoServicoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoRequeridoServicoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoServicoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoRequeridoServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoServicoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoServicoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoServicoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoRequeridoServicoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoServicoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoServicoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoServicoContabil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequeridoServicoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoRequeridoServicoContabil() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoServicoContabil = anexoRequeridoServicoContabilRepository.saveAndFlush(anexoRequeridoServicoContabil);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoRequeridoServicoContabil
        restAnexoRequeridoServicoContabilMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoRequeridoServicoContabil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoRequeridoServicoContabilRepository.count();
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

    protected AnexoRequeridoServicoContabil getPersistedAnexoRequeridoServicoContabil(
        AnexoRequeridoServicoContabil anexoRequeridoServicoContabil
    ) {
        return anexoRequeridoServicoContabilRepository.findById(anexoRequeridoServicoContabil.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoRequeridoServicoContabilToMatchAllProperties(
        AnexoRequeridoServicoContabil expectedAnexoRequeridoServicoContabil
    ) {
        assertAnexoRequeridoServicoContabilAllPropertiesEquals(
            expectedAnexoRequeridoServicoContabil,
            getPersistedAnexoRequeridoServicoContabil(expectedAnexoRequeridoServicoContabil)
        );
    }

    protected void assertPersistedAnexoRequeridoServicoContabilToMatchUpdatableProperties(
        AnexoRequeridoServicoContabil expectedAnexoRequeridoServicoContabil
    ) {
        assertAnexoRequeridoServicoContabilAllUpdatablePropertiesEquals(
            expectedAnexoRequeridoServicoContabil,
            getPersistedAnexoRequeridoServicoContabil(expectedAnexoRequeridoServicoContabil)
        );
    }
}
