package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.SubTarefaRecorrenteAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.SubTarefaRecorrente;
import com.dobemcontabilidade.domain.TarefaRecorrenteExecucao;
import com.dobemcontabilidade.repository.SubTarefaRecorrenteRepository;
import com.dobemcontabilidade.service.SubTarefaRecorrenteService;
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
 * Integration tests for the {@link SubTarefaRecorrenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SubTarefaRecorrenteResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    private static final Boolean DEFAULT_CONCLUIDA = false;
    private static final Boolean UPDATED_CONCLUIDA = true;

    private static final String ENTITY_API_URL = "/api/sub-tarefa-recorrentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubTarefaRecorrenteRepository subTarefaRecorrenteRepository;

    @Mock
    private SubTarefaRecorrenteRepository subTarefaRecorrenteRepositoryMock;

    @Mock
    private SubTarefaRecorrenteService subTarefaRecorrenteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubTarefaRecorrenteMockMvc;

    private SubTarefaRecorrente subTarefaRecorrente;

    private SubTarefaRecorrente insertedSubTarefaRecorrente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubTarefaRecorrente createEntity(EntityManager em) {
        SubTarefaRecorrente subTarefaRecorrente = new SubTarefaRecorrente()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .ordem(DEFAULT_ORDEM)
            .concluida(DEFAULT_CONCLUIDA);
        // Add required entity
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao;
        if (TestUtil.findAll(em, TarefaRecorrenteExecucao.class).isEmpty()) {
            tarefaRecorrenteExecucao = TarefaRecorrenteExecucaoResourceIT.createEntity(em);
            em.persist(tarefaRecorrenteExecucao);
            em.flush();
        } else {
            tarefaRecorrenteExecucao = TestUtil.findAll(em, TarefaRecorrenteExecucao.class).get(0);
        }
        subTarefaRecorrente.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        return subTarefaRecorrente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubTarefaRecorrente createUpdatedEntity(EntityManager em) {
        SubTarefaRecorrente subTarefaRecorrente = new SubTarefaRecorrente()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .ordem(UPDATED_ORDEM)
            .concluida(UPDATED_CONCLUIDA);
        // Add required entity
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao;
        if (TestUtil.findAll(em, TarefaRecorrenteExecucao.class).isEmpty()) {
            tarefaRecorrenteExecucao = TarefaRecorrenteExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(tarefaRecorrenteExecucao);
            em.flush();
        } else {
            tarefaRecorrenteExecucao = TestUtil.findAll(em, TarefaRecorrenteExecucao.class).get(0);
        }
        subTarefaRecorrente.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        return subTarefaRecorrente;
    }

    @BeforeEach
    public void initTest() {
        subTarefaRecorrente = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSubTarefaRecorrente != null) {
            subTarefaRecorrenteRepository.delete(insertedSubTarefaRecorrente);
            insertedSubTarefaRecorrente = null;
        }
    }

    @Test
    @Transactional
    void createSubTarefaRecorrente() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SubTarefaRecorrente
        var returnedSubTarefaRecorrente = om.readValue(
            restSubTarefaRecorrenteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subTarefaRecorrente)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SubTarefaRecorrente.class
        );

        // Validate the SubTarefaRecorrente in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSubTarefaRecorrenteUpdatableFieldsEquals(
            returnedSubTarefaRecorrente,
            getPersistedSubTarefaRecorrente(returnedSubTarefaRecorrente)
        );

        insertedSubTarefaRecorrente = returnedSubTarefaRecorrente;
    }

    @Test
    @Transactional
    void createSubTarefaRecorrenteWithExistingId() throws Exception {
        // Create the SubTarefaRecorrente with an existing ID
        subTarefaRecorrente.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubTarefaRecorrenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subTarefaRecorrente)))
            .andExpect(status().isBadRequest());

        // Validate the SubTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubTarefaRecorrentes() throws Exception {
        // Initialize the database
        insertedSubTarefaRecorrente = subTarefaRecorrenteRepository.saveAndFlush(subTarefaRecorrente);

        // Get all the subTarefaRecorrenteList
        restSubTarefaRecorrenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subTarefaRecorrente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].concluida").value(hasItem(DEFAULT_CONCLUIDA.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubTarefaRecorrentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(subTarefaRecorrenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubTarefaRecorrenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(subTarefaRecorrenteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubTarefaRecorrentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(subTarefaRecorrenteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubTarefaRecorrenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(subTarefaRecorrenteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSubTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedSubTarefaRecorrente = subTarefaRecorrenteRepository.saveAndFlush(subTarefaRecorrente);

        // Get the subTarefaRecorrente
        restSubTarefaRecorrenteMockMvc
            .perform(get(ENTITY_API_URL_ID, subTarefaRecorrente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subTarefaRecorrente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.concluida").value(DEFAULT_CONCLUIDA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSubTarefaRecorrente() throws Exception {
        // Get the subTarefaRecorrente
        restSubTarefaRecorrenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedSubTarefaRecorrente = subTarefaRecorrenteRepository.saveAndFlush(subTarefaRecorrente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subTarefaRecorrente
        SubTarefaRecorrente updatedSubTarefaRecorrente = subTarefaRecorrenteRepository.findById(subTarefaRecorrente.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubTarefaRecorrente are not directly saved in db
        em.detach(updatedSubTarefaRecorrente);
        updatedSubTarefaRecorrente.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).ordem(UPDATED_ORDEM).concluida(UPDATED_CONCLUIDA);

        restSubTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSubTarefaRecorrente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSubTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the SubTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubTarefaRecorrenteToMatchAllProperties(updatedSubTarefaRecorrente);
    }

    @Test
    @Transactional
    void putNonExistingSubTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaRecorrente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subTarefaRecorrente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTarefaRecorrenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subTarefaRecorrente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubTarefaRecorrenteWithPatch() throws Exception {
        // Initialize the database
        insertedSubTarefaRecorrente = subTarefaRecorrenteRepository.saveAndFlush(subTarefaRecorrente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subTarefaRecorrente using partial update
        SubTarefaRecorrente partialUpdatedSubTarefaRecorrente = new SubTarefaRecorrente();
        partialUpdatedSubTarefaRecorrente.setId(subTarefaRecorrente.getId());

        partialUpdatedSubTarefaRecorrente.nome(UPDATED_NOME);

        restSubTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the SubTarefaRecorrente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubTarefaRecorrenteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubTarefaRecorrente, subTarefaRecorrente),
            getPersistedSubTarefaRecorrente(subTarefaRecorrente)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubTarefaRecorrenteWithPatch() throws Exception {
        // Initialize the database
        insertedSubTarefaRecorrente = subTarefaRecorrenteRepository.saveAndFlush(subTarefaRecorrente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subTarefaRecorrente using partial update
        SubTarefaRecorrente partialUpdatedSubTarefaRecorrente = new SubTarefaRecorrente();
        partialUpdatedSubTarefaRecorrente.setId(subTarefaRecorrente.getId());

        partialUpdatedSubTarefaRecorrente.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).ordem(UPDATED_ORDEM).concluida(UPDATED_CONCLUIDA);

        restSubTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the SubTarefaRecorrente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubTarefaRecorrenteUpdatableFieldsEquals(
            partialUpdatedSubTarefaRecorrente,
            getPersistedSubTarefaRecorrente(partialUpdatedSubTarefaRecorrente)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSubTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaRecorrente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subTarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subTarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubTarefaRecorrenteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subTarefaRecorrente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubTarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedSubTarefaRecorrente = subTarefaRecorrenteRepository.saveAndFlush(subTarefaRecorrente);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subTarefaRecorrente
        restSubTarefaRecorrenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, subTarefaRecorrente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subTarefaRecorrenteRepository.count();
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

    protected SubTarefaRecorrente getPersistedSubTarefaRecorrente(SubTarefaRecorrente subTarefaRecorrente) {
        return subTarefaRecorrenteRepository.findById(subTarefaRecorrente.getId()).orElseThrow();
    }

    protected void assertPersistedSubTarefaRecorrenteToMatchAllProperties(SubTarefaRecorrente expectedSubTarefaRecorrente) {
        assertSubTarefaRecorrenteAllPropertiesEquals(
            expectedSubTarefaRecorrente,
            getPersistedSubTarefaRecorrente(expectedSubTarefaRecorrente)
        );
    }

    protected void assertPersistedSubTarefaRecorrenteToMatchUpdatableProperties(SubTarefaRecorrente expectedSubTarefaRecorrente) {
        assertSubTarefaRecorrenteAllUpdatablePropertiesEquals(
            expectedSubTarefaRecorrente,
            getPersistedSubTarefaRecorrente(expectedSubTarefaRecorrente)
        );
    }
}
