package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoTarefaRecorrenteExecucaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoTarefaRecorrenteExecucao;
import com.dobemcontabilidade.domain.TarefaRecorrenteExecucao;
import com.dobemcontabilidade.repository.AnexoTarefaRecorrenteExecucaoRepository;
import com.dobemcontabilidade.service.AnexoTarefaRecorrenteExecucaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link AnexoTarefaRecorrenteExecucaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoTarefaRecorrenteExecucaoResourceIT {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA_UPLOAD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_UPLOAD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/anexo-tarefa-recorrente-execucaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoTarefaRecorrenteExecucaoRepository anexoTarefaRecorrenteExecucaoRepository;

    @Mock
    private AnexoTarefaRecorrenteExecucaoRepository anexoTarefaRecorrenteExecucaoRepositoryMock;

    @Mock
    private AnexoTarefaRecorrenteExecucaoService anexoTarefaRecorrenteExecucaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoTarefaRecorrenteExecucaoMockMvc;

    private AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao;

    private AnexoTarefaRecorrenteExecucao insertedAnexoTarefaRecorrenteExecucao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoTarefaRecorrenteExecucao createEntity(EntityManager em) {
        AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao = new AnexoTarefaRecorrenteExecucao()
            .url(DEFAULT_URL)
            .descricao(DEFAULT_DESCRICAO)
            .dataHoraUpload(DEFAULT_DATA_HORA_UPLOAD);
        // Add required entity
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao;
        if (TestUtil.findAll(em, TarefaRecorrenteExecucao.class).isEmpty()) {
            tarefaRecorrenteExecucao = TarefaRecorrenteExecucaoResourceIT.createEntity(em);
            em.persist(tarefaRecorrenteExecucao);
            em.flush();
        } else {
            tarefaRecorrenteExecucao = TestUtil.findAll(em, TarefaRecorrenteExecucao.class).get(0);
        }
        anexoTarefaRecorrenteExecucao.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        return anexoTarefaRecorrenteExecucao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoTarefaRecorrenteExecucao createUpdatedEntity(EntityManager em) {
        AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao = new AnexoTarefaRecorrenteExecucao()
            .url(UPDATED_URL)
            .descricao(UPDATED_DESCRICAO)
            .dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);
        // Add required entity
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao;
        if (TestUtil.findAll(em, TarefaRecorrenteExecucao.class).isEmpty()) {
            tarefaRecorrenteExecucao = TarefaRecorrenteExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(tarefaRecorrenteExecucao);
            em.flush();
        } else {
            tarefaRecorrenteExecucao = TestUtil.findAll(em, TarefaRecorrenteExecucao.class).get(0);
        }
        anexoTarefaRecorrenteExecucao.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        return anexoTarefaRecorrenteExecucao;
    }

    @BeforeEach
    public void initTest() {
        anexoTarefaRecorrenteExecucao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoTarefaRecorrenteExecucao != null) {
            anexoTarefaRecorrenteExecucaoRepository.delete(insertedAnexoTarefaRecorrenteExecucao);
            insertedAnexoTarefaRecorrenteExecucao = null;
        }
    }

    @Test
    @Transactional
    void createAnexoTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoTarefaRecorrenteExecucao
        var returnedAnexoTarefaRecorrenteExecucao = om.readValue(
            restAnexoTarefaRecorrenteExecucaoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(anexoTarefaRecorrenteExecucao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoTarefaRecorrenteExecucao.class
        );

        // Validate the AnexoTarefaRecorrenteExecucao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnexoTarefaRecorrenteExecucaoUpdatableFieldsEquals(
            returnedAnexoTarefaRecorrenteExecucao,
            getPersistedAnexoTarefaRecorrenteExecucao(returnedAnexoTarefaRecorrenteExecucao)
        );

        insertedAnexoTarefaRecorrenteExecucao = returnedAnexoTarefaRecorrenteExecucao;
    }

    @Test
    @Transactional
    void createAnexoTarefaRecorrenteExecucaoWithExistingId() throws Exception {
        // Create the AnexoTarefaRecorrenteExecucao with an existing ID
        anexoTarefaRecorrenteExecucao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoTarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnexoTarefaRecorrenteExecucaos() throws Exception {
        // Initialize the database
        insertedAnexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucaoRepository.saveAndFlush(anexoTarefaRecorrenteExecucao);

        // Get all the anexoTarefaRecorrenteExecucaoList
        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoTarefaRecorrenteExecucao.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].dataHoraUpload").value(hasItem(DEFAULT_DATA_HORA_UPLOAD.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoTarefaRecorrenteExecucaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoTarefaRecorrenteExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoTarefaRecorrenteExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoTarefaRecorrenteExecucaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoTarefaRecorrenteExecucaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoTarefaRecorrenteExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoTarefaRecorrenteExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoTarefaRecorrenteExecucaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoTarefaRecorrenteExecucao() throws Exception {
        // Initialize the database
        insertedAnexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucaoRepository.saveAndFlush(anexoTarefaRecorrenteExecucao);

        // Get the anexoTarefaRecorrenteExecucao
        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoTarefaRecorrenteExecucao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoTarefaRecorrenteExecucao.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.dataHoraUpload").value(DEFAULT_DATA_HORA_UPLOAD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnexoTarefaRecorrenteExecucao() throws Exception {
        // Get the anexoTarefaRecorrenteExecucao
        restAnexoTarefaRecorrenteExecucaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoTarefaRecorrenteExecucao() throws Exception {
        // Initialize the database
        insertedAnexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucaoRepository.saveAndFlush(anexoTarefaRecorrenteExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoTarefaRecorrenteExecucao
        AnexoTarefaRecorrenteExecucao updatedAnexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucaoRepository
            .findById(anexoTarefaRecorrenteExecucao.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoTarefaRecorrenteExecucao are not directly saved in db
        em.detach(updatedAnexoTarefaRecorrenteExecucao);
        updatedAnexoTarefaRecorrenteExecucao.url(UPDATED_URL).descricao(UPDATED_DESCRICAO).dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);

        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnexoTarefaRecorrenteExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnexoTarefaRecorrenteExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AnexoTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoTarefaRecorrenteExecucaoToMatchAllProperties(updatedAnexoTarefaRecorrenteExecucao);
    }

    @Test
    @Transactional
    void putNonExistingAnexoTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoTarefaRecorrenteExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoTarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoTarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoTarefaRecorrenteExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoTarefaRecorrenteExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucaoRepository.saveAndFlush(anexoTarefaRecorrenteExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoTarefaRecorrenteExecucao using partial update
        AnexoTarefaRecorrenteExecucao partialUpdatedAnexoTarefaRecorrenteExecucao = new AnexoTarefaRecorrenteExecucao();
        partialUpdatedAnexoTarefaRecorrenteExecucao.setId(anexoTarefaRecorrenteExecucao.getId());

        partialUpdatedAnexoTarefaRecorrenteExecucao.dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);

        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoTarefaRecorrenteExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoTarefaRecorrenteExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AnexoTarefaRecorrenteExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoTarefaRecorrenteExecucaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoTarefaRecorrenteExecucao, anexoTarefaRecorrenteExecucao),
            getPersistedAnexoTarefaRecorrenteExecucao(anexoTarefaRecorrenteExecucao)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoTarefaRecorrenteExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucaoRepository.saveAndFlush(anexoTarefaRecorrenteExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoTarefaRecorrenteExecucao using partial update
        AnexoTarefaRecorrenteExecucao partialUpdatedAnexoTarefaRecorrenteExecucao = new AnexoTarefaRecorrenteExecucao();
        partialUpdatedAnexoTarefaRecorrenteExecucao.setId(anexoTarefaRecorrenteExecucao.getId());

        partialUpdatedAnexoTarefaRecorrenteExecucao.url(UPDATED_URL).descricao(UPDATED_DESCRICAO).dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);

        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoTarefaRecorrenteExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoTarefaRecorrenteExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AnexoTarefaRecorrenteExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoTarefaRecorrenteExecucaoUpdatableFieldsEquals(
            partialUpdatedAnexoTarefaRecorrenteExecucao,
            getPersistedAnexoTarefaRecorrenteExecucao(partialUpdatedAnexoTarefaRecorrenteExecucao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAnexoTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoTarefaRecorrenteExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoTarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoTarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoTarefaRecorrenteExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoTarefaRecorrenteExecucao() throws Exception {
        // Initialize the database
        insertedAnexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucaoRepository.saveAndFlush(anexoTarefaRecorrenteExecucao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoTarefaRecorrenteExecucao
        restAnexoTarefaRecorrenteExecucaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoTarefaRecorrenteExecucao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoTarefaRecorrenteExecucaoRepository.count();
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

    protected AnexoTarefaRecorrenteExecucao getPersistedAnexoTarefaRecorrenteExecucao(
        AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao
    ) {
        return anexoTarefaRecorrenteExecucaoRepository.findById(anexoTarefaRecorrenteExecucao.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoTarefaRecorrenteExecucaoToMatchAllProperties(
        AnexoTarefaRecorrenteExecucao expectedAnexoTarefaRecorrenteExecucao
    ) {
        assertAnexoTarefaRecorrenteExecucaoAllPropertiesEquals(
            expectedAnexoTarefaRecorrenteExecucao,
            getPersistedAnexoTarefaRecorrenteExecucao(expectedAnexoTarefaRecorrenteExecucao)
        );
    }

    protected void assertPersistedAnexoTarefaRecorrenteExecucaoToMatchUpdatableProperties(
        AnexoTarefaRecorrenteExecucao expectedAnexoTarefaRecorrenteExecucao
    ) {
        assertAnexoTarefaRecorrenteExecucaoAllUpdatablePropertiesEquals(
            expectedAnexoTarefaRecorrenteExecucao,
            getPersistedAnexoTarefaRecorrenteExecucao(expectedAnexoTarefaRecorrenteExecucao)
        );
    }
}
