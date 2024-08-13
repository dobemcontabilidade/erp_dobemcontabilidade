package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoOrdemServicoExecucaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoOrdemServicoExecucao;
import com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao;
import com.dobemcontabilidade.repository.AnexoOrdemServicoExecucaoRepository;
import com.dobemcontabilidade.service.AnexoOrdemServicoExecucaoService;
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
 * Integration tests for the {@link AnexoOrdemServicoExecucaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AnexoOrdemServicoExecucaoResourceIT {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_HORA_UPLOAD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_UPLOAD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/anexo-ordem-servico-execucaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoOrdemServicoExecucaoRepository anexoOrdemServicoExecucaoRepository;

    @Mock
    private AnexoOrdemServicoExecucaoRepository anexoOrdemServicoExecucaoRepositoryMock;

    @Mock
    private AnexoOrdemServicoExecucaoService anexoOrdemServicoExecucaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoOrdemServicoExecucaoMockMvc;

    private AnexoOrdemServicoExecucao anexoOrdemServicoExecucao;

    private AnexoOrdemServicoExecucao insertedAnexoOrdemServicoExecucao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoOrdemServicoExecucao createEntity(EntityManager em) {
        AnexoOrdemServicoExecucao anexoOrdemServicoExecucao = new AnexoOrdemServicoExecucao()
            .url(DEFAULT_URL)
            .descricao(DEFAULT_DESCRICAO)
            .dataHoraUpload(DEFAULT_DATA_HORA_UPLOAD);
        // Add required entity
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;
        if (TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).isEmpty()) {
            tarefaOrdemServicoExecucao = TarefaOrdemServicoExecucaoResourceIT.createEntity(em);
            em.persist(tarefaOrdemServicoExecucao);
            em.flush();
        } else {
            tarefaOrdemServicoExecucao = TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).get(0);
        }
        anexoOrdemServicoExecucao.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao);
        return anexoOrdemServicoExecucao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoOrdemServicoExecucao createUpdatedEntity(EntityManager em) {
        AnexoOrdemServicoExecucao anexoOrdemServicoExecucao = new AnexoOrdemServicoExecucao()
            .url(UPDATED_URL)
            .descricao(UPDATED_DESCRICAO)
            .dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);
        // Add required entity
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;
        if (TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).isEmpty()) {
            tarefaOrdemServicoExecucao = TarefaOrdemServicoExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(tarefaOrdemServicoExecucao);
            em.flush();
        } else {
            tarefaOrdemServicoExecucao = TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).get(0);
        }
        anexoOrdemServicoExecucao.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao);
        return anexoOrdemServicoExecucao;
    }

    @BeforeEach
    public void initTest() {
        anexoOrdemServicoExecucao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoOrdemServicoExecucao != null) {
            anexoOrdemServicoExecucaoRepository.delete(insertedAnexoOrdemServicoExecucao);
            insertedAnexoOrdemServicoExecucao = null;
        }
    }

    @Test
    @Transactional
    void createAnexoOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoOrdemServicoExecucao
        var returnedAnexoOrdemServicoExecucao = om.readValue(
            restAnexoOrdemServicoExecucaoMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoOrdemServicoExecucao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoOrdemServicoExecucao.class
        );

        // Validate the AnexoOrdemServicoExecucao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnexoOrdemServicoExecucaoUpdatableFieldsEquals(
            returnedAnexoOrdemServicoExecucao,
            getPersistedAnexoOrdemServicoExecucao(returnedAnexoOrdemServicoExecucao)
        );

        insertedAnexoOrdemServicoExecucao = returnedAnexoOrdemServicoExecucao;
    }

    @Test
    @Transactional
    void createAnexoOrdemServicoExecucaoWithExistingId() throws Exception {
        // Create the AnexoOrdemServicoExecucao with an existing ID
        anexoOrdemServicoExecucao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoOrdemServicoExecucaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoOrdemServicoExecucao)))
            .andExpect(status().isBadRequest());

        // Validate the AnexoOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnexoOrdemServicoExecucaos() throws Exception {
        // Initialize the database
        insertedAnexoOrdemServicoExecucao = anexoOrdemServicoExecucaoRepository.saveAndFlush(anexoOrdemServicoExecucao);

        // Get all the anexoOrdemServicoExecucaoList
        restAnexoOrdemServicoExecucaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoOrdemServicoExecucao.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].dataHoraUpload").value(hasItem(DEFAULT_DATA_HORA_UPLOAD.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoOrdemServicoExecucaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(anexoOrdemServicoExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoOrdemServicoExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(anexoOrdemServicoExecucaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAnexoOrdemServicoExecucaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(anexoOrdemServicoExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAnexoOrdemServicoExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(anexoOrdemServicoExecucaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAnexoOrdemServicoExecucao() throws Exception {
        // Initialize the database
        insertedAnexoOrdemServicoExecucao = anexoOrdemServicoExecucaoRepository.saveAndFlush(anexoOrdemServicoExecucao);

        // Get the anexoOrdemServicoExecucao
        restAnexoOrdemServicoExecucaoMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoOrdemServicoExecucao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoOrdemServicoExecucao.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.dataHoraUpload").value(DEFAULT_DATA_HORA_UPLOAD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnexoOrdemServicoExecucao() throws Exception {
        // Get the anexoOrdemServicoExecucao
        restAnexoOrdemServicoExecucaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoOrdemServicoExecucao() throws Exception {
        // Initialize the database
        insertedAnexoOrdemServicoExecucao = anexoOrdemServicoExecucaoRepository.saveAndFlush(anexoOrdemServicoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoOrdemServicoExecucao
        AnexoOrdemServicoExecucao updatedAnexoOrdemServicoExecucao = anexoOrdemServicoExecucaoRepository
            .findById(anexoOrdemServicoExecucao.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoOrdemServicoExecucao are not directly saved in db
        em.detach(updatedAnexoOrdemServicoExecucao);
        updatedAnexoOrdemServicoExecucao.url(UPDATED_URL).descricao(UPDATED_DESCRICAO).dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);

        restAnexoOrdemServicoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnexoOrdemServicoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnexoOrdemServicoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AnexoOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoOrdemServicoExecucaoToMatchAllProperties(updatedAnexoOrdemServicoExecucao);
    }

    @Test
    @Transactional
    void putNonExistingAnexoOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoOrdemServicoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoOrdemServicoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoOrdemServicoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoOrdemServicoExecucaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoOrdemServicoExecucao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoOrdemServicoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoOrdemServicoExecucao = anexoOrdemServicoExecucaoRepository.saveAndFlush(anexoOrdemServicoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoOrdemServicoExecucao using partial update
        AnexoOrdemServicoExecucao partialUpdatedAnexoOrdemServicoExecucao = new AnexoOrdemServicoExecucao();
        partialUpdatedAnexoOrdemServicoExecucao.setId(anexoOrdemServicoExecucao.getId());

        partialUpdatedAnexoOrdemServicoExecucao.dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);

        restAnexoOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoOrdemServicoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoOrdemServicoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AnexoOrdemServicoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoOrdemServicoExecucaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoOrdemServicoExecucao, anexoOrdemServicoExecucao),
            getPersistedAnexoOrdemServicoExecucao(anexoOrdemServicoExecucao)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoOrdemServicoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoOrdemServicoExecucao = anexoOrdemServicoExecucaoRepository.saveAndFlush(anexoOrdemServicoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoOrdemServicoExecucao using partial update
        AnexoOrdemServicoExecucao partialUpdatedAnexoOrdemServicoExecucao = new AnexoOrdemServicoExecucao();
        partialUpdatedAnexoOrdemServicoExecucao.setId(anexoOrdemServicoExecucao.getId());

        partialUpdatedAnexoOrdemServicoExecucao.url(UPDATED_URL).descricao(UPDATED_DESCRICAO).dataHoraUpload(UPDATED_DATA_HORA_UPLOAD);

        restAnexoOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoOrdemServicoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoOrdemServicoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AnexoOrdemServicoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoOrdemServicoExecucaoUpdatableFieldsEquals(
            partialUpdatedAnexoOrdemServicoExecucao,
            getPersistedAnexoOrdemServicoExecucao(partialUpdatedAnexoOrdemServicoExecucao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAnexoOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoOrdemServicoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anexoOrdemServicoExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoOrdemServicoExecucao() throws Exception {
        // Initialize the database
        insertedAnexoOrdemServicoExecucao = anexoOrdemServicoExecucaoRepository.saveAndFlush(anexoOrdemServicoExecucao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoOrdemServicoExecucao
        restAnexoOrdemServicoExecucaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoOrdemServicoExecucao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoOrdemServicoExecucaoRepository.count();
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

    protected AnexoOrdemServicoExecucao getPersistedAnexoOrdemServicoExecucao(AnexoOrdemServicoExecucao anexoOrdemServicoExecucao) {
        return anexoOrdemServicoExecucaoRepository.findById(anexoOrdemServicoExecucao.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoOrdemServicoExecucaoToMatchAllProperties(
        AnexoOrdemServicoExecucao expectedAnexoOrdemServicoExecucao
    ) {
        assertAnexoOrdemServicoExecucaoAllPropertiesEquals(
            expectedAnexoOrdemServicoExecucao,
            getPersistedAnexoOrdemServicoExecucao(expectedAnexoOrdemServicoExecucao)
        );
    }

    protected void assertPersistedAnexoOrdemServicoExecucaoToMatchUpdatableProperties(
        AnexoOrdemServicoExecucao expectedAnexoOrdemServicoExecucao
    ) {
        assertAnexoOrdemServicoExecucaoAllUpdatablePropertiesEquals(
            expectedAnexoOrdemServicoExecucao,
            getPersistedAnexoOrdemServicoExecucao(expectedAnexoOrdemServicoExecucao)
        );
    }
}
