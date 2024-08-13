package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrenteAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoRequerido;
import com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrente;
import com.dobemcontabilidade.domain.TarefaRecorrente;
import com.dobemcontabilidade.repository.AnexoRequeridoTarefaRecorrenteRepository;
import com.dobemcontabilidade.service.AnexoRequeridoTarefaRecorrenteService;
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
 * Integration tests for the {@link AnexoRequeridoTarefaRecorrenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoRequeridoTarefaRecorrenteResourceIT {

    private static final Boolean DEFAULT_OBRIGATORIO = false;
    private static final Boolean UPDATED_OBRIGATORIO = true;

    private static final String ENTITY_API_URL = "/api/anexo-requerido-tarefa-recorrentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoRequeridoTarefaRecorrenteRepository anexoRequeridoTarefaRecorrenteRepository;

    @Mock
    private AnexoRequeridoTarefaRecorrenteRepository anexoRequeridoTarefaRecorrenteRepositoryMock;

    @Mock
    private AnexoRequeridoTarefaRecorrenteService anexoRequeridoTarefaRecorrenteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoRequeridoTarefaRecorrenteMockMvc;

    private AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente;

    private AnexoRequeridoTarefaRecorrente insertedAnexoRequeridoTarefaRecorrente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequeridoTarefaRecorrente createEntity(EntityManager em) {
        AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente = new AnexoRequeridoTarefaRecorrente()
            .obrigatorio(DEFAULT_OBRIGATORIO);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoRequeridoTarefaRecorrente.setAnexoRequerido(anexoRequerido);
        // Add required entity
        TarefaRecorrente tarefaRecorrente;
        if (TestUtil.findAll(em, TarefaRecorrente.class).isEmpty()) {
            tarefaRecorrente = TarefaRecorrenteResourceIT.createEntity(em);
            em.persist(tarefaRecorrente);
            em.flush();
        } else {
            tarefaRecorrente = TestUtil.findAll(em, TarefaRecorrente.class).get(0);
        }
        anexoRequeridoTarefaRecorrente.setTarefaRecorrente(tarefaRecorrente);
        return anexoRequeridoTarefaRecorrente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequeridoTarefaRecorrente createUpdatedEntity(EntityManager em) {
        AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente = new AnexoRequeridoTarefaRecorrente()
            .obrigatorio(UPDATED_OBRIGATORIO);
        // Add required entity
        AnexoRequerido anexoRequerido;
        if (TestUtil.findAll(em, AnexoRequerido.class).isEmpty()) {
            anexoRequerido = AnexoRequeridoResourceIT.createUpdatedEntity(em);
            em.persist(anexoRequerido);
            em.flush();
        } else {
            anexoRequerido = TestUtil.findAll(em, AnexoRequerido.class).get(0);
        }
        anexoRequeridoTarefaRecorrente.setAnexoRequerido(anexoRequerido);
        // Add required entity
        TarefaRecorrente tarefaRecorrente;
        if (TestUtil.findAll(em, TarefaRecorrente.class).isEmpty()) {
            tarefaRecorrente = TarefaRecorrenteResourceIT.createUpdatedEntity(em);
            em.persist(tarefaRecorrente);
            em.flush();
        } else {
            tarefaRecorrente = TestUtil.findAll(em, TarefaRecorrente.class).get(0);
        }
        anexoRequeridoTarefaRecorrente.setTarefaRecorrente(tarefaRecorrente);
        return anexoRequeridoTarefaRecorrente;
    }

    @BeforeEach
    public void initTest() {
        anexoRequeridoTarefaRecorrente = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoRequeridoTarefaRecorrente != null) {
            anexoRequeridoTarefaRecorrenteRepository.delete(insertedAnexoRequeridoTarefaRecorrente);
            insertedAnexoRequeridoTarefaRecorrente = null;
        }
    }

    @Test
    @Transactional
    void createAnexoRequeridoTarefaRecorrente() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoRequeridoTarefaRecorrente
        var returnedAnexoRequeridoTarefaRecorrente = om.readValue(
            restAnexoRequeridoTarefaRecorrenteMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(anexoRequeridoTarefaRecorrente))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoRequeridoTarefaRecorrente.class
        );

        // Validate the AnexoRequeridoTarefaRecorrente in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnexoRequeridoTarefaRecorrenteUpdatableFieldsEquals(
            returnedAnexoRequeridoTarefaRecorrente,
            getPersistedAnexoRequeridoTarefaRecorrente(returnedAnexoRequeridoTarefaRecorrente)
        );

        insertedAnexoRequeridoTarefaRecorrente = returnedAnexoRequeridoTarefaRecorrente;
    }

    @Test
    @Transactional
    void createAnexoRequeridoTarefaRecorrenteWithExistingId() throws Exception {
        // Create the AnexoRequeridoTarefaRecorrente with an existing ID
        anexoRequeridoTarefaRecorrente.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridoTarefaRecorrentes() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrenteRepository.saveAndFlush(anexoRequeridoTarefaRecorrente);

        // Get all the anexoRequeridoTarefaRecorrenteList
        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoRequeridoTarefaRecorrente.getId().intValue())))
            .andExpect(jsonPath("$.[*].obrigatorio").value(hasItem(DEFAULT_OBRIGATORIO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoRequeridoTarefaRecorrentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoRequeridoTarefaRecorrenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoRequeridoTarefaRecorrenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoRequeridoTarefaRecorrenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoRequeridoTarefaRecorrentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoRequeridoTarefaRecorrenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoRequeridoTarefaRecorrenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoRequeridoTarefaRecorrenteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoRequeridoTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrenteRepository.saveAndFlush(anexoRequeridoTarefaRecorrente);

        // Get the anexoRequeridoTarefaRecorrente
        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoRequeridoTarefaRecorrente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoRequeridoTarefaRecorrente.getId().intValue()))
            .andExpect(jsonPath("$.obrigatorio").value(DEFAULT_OBRIGATORIO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAnexoRequeridoTarefaRecorrente() throws Exception {
        // Get the anexoRequeridoTarefaRecorrente
        restAnexoRequeridoTarefaRecorrenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoRequeridoTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrenteRepository.saveAndFlush(anexoRequeridoTarefaRecorrente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoTarefaRecorrente
        AnexoRequeridoTarefaRecorrente updatedAnexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrenteRepository
            .findById(anexoRequeridoTarefaRecorrente.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoRequeridoTarefaRecorrente are not directly saved in db
        em.detach(updatedAnexoRequeridoTarefaRecorrente);
        updatedAnexoRequeridoTarefaRecorrente.obrigatorio(UPDATED_OBRIGATORIO);

        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnexoRequeridoTarefaRecorrente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnexoRequeridoTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoRequeridoTarefaRecorrenteToMatchAllProperties(updatedAnexoRequeridoTarefaRecorrente);
    }

    @Test
    @Transactional
    void putNonExistingAnexoRequeridoTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaRecorrente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoRequeridoTarefaRecorrente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequeridoTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoRequeridoTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequeridoTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoRequeridoTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequeridoTarefaRecorrente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequeridoTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoRequeridoTarefaRecorrenteWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrenteRepository.saveAndFlush(anexoRequeridoTarefaRecorrente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoTarefaRecorrente using partial update
        AnexoRequeridoTarefaRecorrente partialUpdatedAnexoRequeridoTarefaRecorrente = new AnexoRequeridoTarefaRecorrente();
        partialUpdatedAnexoRequeridoTarefaRecorrente.setId(anexoRequeridoTarefaRecorrente.getId());

        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequeridoTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequeridoTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoTarefaRecorrente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoTarefaRecorrenteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoRequeridoTarefaRecorrente, anexoRequeridoTarefaRecorrente),
            getPersistedAnexoRequeridoTarefaRecorrente(anexoRequeridoTarefaRecorrente)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoRequeridoTarefaRecorrenteWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrenteRepository.saveAndFlush(anexoRequeridoTarefaRecorrente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequeridoTarefaRecorrente using partial update
        AnexoRequeridoTarefaRecorrente partialUpdatedAnexoRequeridoTarefaRecorrente = new AnexoRequeridoTarefaRecorrente();
        partialUpdatedAnexoRequeridoTarefaRecorrente.setId(anexoRequeridoTarefaRecorrente.getId());

        partialUpdatedAnexoRequeridoTarefaRecorrente.obrigatorio(UPDATED_OBRIGATORIO);

        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequeridoTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequeridoTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequeridoTarefaRecorrente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoTarefaRecorrenteUpdatableFieldsEquals(
            partialUpdatedAnexoRequeridoTarefaRecorrente,
            getPersistedAnexoRequeridoTarefaRecorrente(partialUpdatedAnexoRequeridoTarefaRecorrente)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAnexoRequeridoTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaRecorrente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoRequeridoTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoRequeridoTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequeridoTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoRequeridoTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequeridoTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequeridoTarefaRecorrente))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequeridoTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoRequeridoTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedAnexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrenteRepository.saveAndFlush(anexoRequeridoTarefaRecorrente);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoRequeridoTarefaRecorrente
        restAnexoRequeridoTarefaRecorrenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoRequeridoTarefaRecorrente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoRequeridoTarefaRecorrenteRepository.count();
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

    protected AnexoRequeridoTarefaRecorrente getPersistedAnexoRequeridoTarefaRecorrente(
        AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente
    ) {
        return anexoRequeridoTarefaRecorrenteRepository.findById(anexoRequeridoTarefaRecorrente.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoRequeridoTarefaRecorrenteToMatchAllProperties(
        AnexoRequeridoTarefaRecorrente expectedAnexoRequeridoTarefaRecorrente
    ) {
        assertAnexoRequeridoTarefaRecorrenteAllPropertiesEquals(
            expectedAnexoRequeridoTarefaRecorrente,
            getPersistedAnexoRequeridoTarefaRecorrente(expectedAnexoRequeridoTarefaRecorrente)
        );
    }

    protected void assertPersistedAnexoRequeridoTarefaRecorrenteToMatchUpdatableProperties(
        AnexoRequeridoTarefaRecorrente expectedAnexoRequeridoTarefaRecorrente
    ) {
        assertAnexoRequeridoTarefaRecorrenteAllUpdatablePropertiesEquals(
            expectedAnexoRequeridoTarefaRecorrente,
            getPersistedAnexoRequeridoTarefaRecorrente(expectedAnexoRequeridoTarefaRecorrente)
        );
    }
}
