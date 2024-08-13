package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TarefaRecorrenteExecucaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.TarefaRecorrente;
import com.dobemcontabilidade.domain.TarefaRecorrenteExecucao;
import com.dobemcontabilidade.domain.enumeration.MesCompetenciaEnum;
import com.dobemcontabilidade.domain.enumeration.SituacaoTarefaEnum;
import com.dobemcontabilidade.repository.TarefaRecorrenteExecucaoRepository;
import com.dobemcontabilidade.service.TarefaRecorrenteExecucaoService;
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
 * Integration tests for the {@link TarefaRecorrenteExecucaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TarefaRecorrenteExecucaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_ENTREGA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ENTREGA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_AGENDADA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_AGENDADA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    private static final Boolean DEFAULT_CONCLUIDA = false;
    private static final Boolean UPDATED_CONCLUIDA = true;

    private static final MesCompetenciaEnum DEFAULT_MES = MesCompetenciaEnum.JANEIRO;
    private static final MesCompetenciaEnum UPDATED_MES = MesCompetenciaEnum.FEVEREIRO;

    private static final SituacaoTarefaEnum DEFAULT_SITUACAO_TAREFA = SituacaoTarefaEnum.AGENDADA;
    private static final SituacaoTarefaEnum UPDATED_SITUACAO_TAREFA = SituacaoTarefaEnum.CANCELADA;

    private static final String ENTITY_API_URL = "/api/tarefa-recorrente-execucaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TarefaRecorrenteExecucaoRepository tarefaRecorrenteExecucaoRepository;

    @Mock
    private TarefaRecorrenteExecucaoRepository tarefaRecorrenteExecucaoRepositoryMock;

    @Mock
    private TarefaRecorrenteExecucaoService tarefaRecorrenteExecucaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarefaRecorrenteExecucaoMockMvc;

    private TarefaRecorrenteExecucao tarefaRecorrenteExecucao;

    private TarefaRecorrenteExecucao insertedTarefaRecorrenteExecucao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaRecorrenteExecucao createEntity(EntityManager em) {
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao = new TarefaRecorrenteExecucao()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .dataEntrega(DEFAULT_DATA_ENTREGA)
            .dataAgendada(DEFAULT_DATA_AGENDADA)
            .ordem(DEFAULT_ORDEM)
            .concluida(DEFAULT_CONCLUIDA)
            .mes(DEFAULT_MES)
            .situacaoTarefa(DEFAULT_SITUACAO_TAREFA);
        // Add required entity
        TarefaRecorrente tarefaRecorrente;
        if (TestUtil.findAll(em, TarefaRecorrente.class).isEmpty()) {
            tarefaRecorrente = TarefaRecorrenteResourceIT.createEntity(em);
            em.persist(tarefaRecorrente);
            em.flush();
        } else {
            tarefaRecorrente = TestUtil.findAll(em, TarefaRecorrente.class).get(0);
        }
        tarefaRecorrenteExecucao.setTarefaRecorrente(tarefaRecorrente);
        return tarefaRecorrenteExecucao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaRecorrenteExecucao createUpdatedEntity(EntityManager em) {
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao = new TarefaRecorrenteExecucao()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .dataAgendada(UPDATED_DATA_AGENDADA)
            .ordem(UPDATED_ORDEM)
            .concluida(UPDATED_CONCLUIDA)
            .mes(UPDATED_MES)
            .situacaoTarefa(UPDATED_SITUACAO_TAREFA);
        // Add required entity
        TarefaRecorrente tarefaRecorrente;
        if (TestUtil.findAll(em, TarefaRecorrente.class).isEmpty()) {
            tarefaRecorrente = TarefaRecorrenteResourceIT.createUpdatedEntity(em);
            em.persist(tarefaRecorrente);
            em.flush();
        } else {
            tarefaRecorrente = TestUtil.findAll(em, TarefaRecorrente.class).get(0);
        }
        tarefaRecorrenteExecucao.setTarefaRecorrente(tarefaRecorrente);
        return tarefaRecorrenteExecucao;
    }

    @BeforeEach
    public void initTest() {
        tarefaRecorrenteExecucao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTarefaRecorrenteExecucao != null) {
            tarefaRecorrenteExecucaoRepository.delete(insertedTarefaRecorrenteExecucao);
            insertedTarefaRecorrenteExecucao = null;
        }
    }

    @Test
    @Transactional
    void createTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TarefaRecorrenteExecucao
        var returnedTarefaRecorrenteExecucao = om.readValue(
            restTarefaRecorrenteExecucaoMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaRecorrenteExecucao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TarefaRecorrenteExecucao.class
        );

        // Validate the TarefaRecorrenteExecucao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTarefaRecorrenteExecucaoUpdatableFieldsEquals(
            returnedTarefaRecorrenteExecucao,
            getPersistedTarefaRecorrenteExecucao(returnedTarefaRecorrenteExecucao)
        );

        insertedTarefaRecorrenteExecucao = returnedTarefaRecorrenteExecucao;
    }

    @Test
    @Transactional
    void createTarefaRecorrenteExecucaoWithExistingId() throws Exception {
        // Create the TarefaRecorrenteExecucao with an existing ID
        tarefaRecorrenteExecucao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarefaRecorrenteExecucaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaRecorrenteExecucao)))
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTarefaRecorrenteExecucaos() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteExecucao = tarefaRecorrenteExecucaoRepository.saveAndFlush(tarefaRecorrenteExecucao);

        // Get all the tarefaRecorrenteExecucaoList
        restTarefaRecorrenteExecucaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefaRecorrenteExecucao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].dataEntrega").value(hasItem(DEFAULT_DATA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].dataAgendada").value(hasItem(DEFAULT_DATA_AGENDADA.toString())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].concluida").value(hasItem(DEFAULT_CONCLUIDA.booleanValue())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.toString())))
            .andExpect(jsonPath("$.[*].situacaoTarefa").value(hasItem(DEFAULT_SITUACAO_TAREFA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTarefaRecorrenteExecucaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(tarefaRecorrenteExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTarefaRecorrenteExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tarefaRecorrenteExecucaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTarefaRecorrenteExecucaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tarefaRecorrenteExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTarefaRecorrenteExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tarefaRecorrenteExecucaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTarefaRecorrenteExecucao() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteExecucao = tarefaRecorrenteExecucaoRepository.saveAndFlush(tarefaRecorrenteExecucao);

        // Get the tarefaRecorrenteExecucao
        restTarefaRecorrenteExecucaoMockMvc
            .perform(get(ENTITY_API_URL_ID, tarefaRecorrenteExecucao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarefaRecorrenteExecucao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.dataEntrega").value(DEFAULT_DATA_ENTREGA.toString()))
            .andExpect(jsonPath("$.dataAgendada").value(DEFAULT_DATA_AGENDADA.toString()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.concluida").value(DEFAULT_CONCLUIDA.booleanValue()))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES.toString()))
            .andExpect(jsonPath("$.situacaoTarefa").value(DEFAULT_SITUACAO_TAREFA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTarefaRecorrenteExecucao() throws Exception {
        // Get the tarefaRecorrenteExecucao
        restTarefaRecorrenteExecucaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarefaRecorrenteExecucao() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteExecucao = tarefaRecorrenteExecucaoRepository.saveAndFlush(tarefaRecorrenteExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaRecorrenteExecucao
        TarefaRecorrenteExecucao updatedTarefaRecorrenteExecucao = tarefaRecorrenteExecucaoRepository
            .findById(tarefaRecorrenteExecucao.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTarefaRecorrenteExecucao are not directly saved in db
        em.detach(updatedTarefaRecorrenteExecucao);
        updatedTarefaRecorrenteExecucao
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .dataAgendada(UPDATED_DATA_AGENDADA)
            .ordem(UPDATED_ORDEM)
            .concluida(UPDATED_CONCLUIDA)
            .mes(UPDATED_MES)
            .situacaoTarefa(UPDATED_SITUACAO_TAREFA);

        restTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTarefaRecorrenteExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTarefaRecorrenteExecucao))
            )
            .andExpect(status().isOk());

        // Validate the TarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTarefaRecorrenteExecucaoToMatchAllProperties(updatedTarefaRecorrenteExecucao);
    }

    @Test
    @Transactional
    void putNonExistingTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarefaRecorrenteExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteExecucaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaRecorrenteExecucao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarefaRecorrenteExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteExecucao = tarefaRecorrenteExecucaoRepository.saveAndFlush(tarefaRecorrenteExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaRecorrenteExecucao using partial update
        TarefaRecorrenteExecucao partialUpdatedTarefaRecorrenteExecucao = new TarefaRecorrenteExecucao();
        partialUpdatedTarefaRecorrenteExecucao.setId(tarefaRecorrenteExecucao.getId());

        partialUpdatedTarefaRecorrenteExecucao
            .nome(UPDATED_NOME)
            .dataAgendada(UPDATED_DATA_AGENDADA)
            .ordem(UPDATED_ORDEM)
            .concluida(UPDATED_CONCLUIDA)
            .mes(UPDATED_MES)
            .situacaoTarefa(UPDATED_SITUACAO_TAREFA);

        restTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaRecorrenteExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaRecorrenteExecucao))
            )
            .andExpect(status().isOk());

        // Validate the TarefaRecorrenteExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaRecorrenteExecucaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTarefaRecorrenteExecucao, tarefaRecorrenteExecucao),
            getPersistedTarefaRecorrenteExecucao(tarefaRecorrenteExecucao)
        );
    }

    @Test
    @Transactional
    void fullUpdateTarefaRecorrenteExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteExecucao = tarefaRecorrenteExecucaoRepository.saveAndFlush(tarefaRecorrenteExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaRecorrenteExecucao using partial update
        TarefaRecorrenteExecucao partialUpdatedTarefaRecorrenteExecucao = new TarefaRecorrenteExecucao();
        partialUpdatedTarefaRecorrenteExecucao.setId(tarefaRecorrenteExecucao.getId());

        partialUpdatedTarefaRecorrenteExecucao
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .dataAgendada(UPDATED_DATA_AGENDADA)
            .ordem(UPDATED_ORDEM)
            .concluida(UPDATED_CONCLUIDA)
            .mes(UPDATED_MES)
            .situacaoTarefa(UPDATED_SITUACAO_TAREFA);

        restTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaRecorrenteExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaRecorrenteExecucao))
            )
            .andExpect(status().isOk());

        // Validate the TarefaRecorrenteExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaRecorrenteExecucaoUpdatableFieldsEquals(
            partialUpdatedTarefaRecorrenteExecucao,
            getPersistedTarefaRecorrenteExecucao(partialUpdatedTarefaRecorrenteExecucao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarefaRecorrenteExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tarefaRecorrenteExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarefaRecorrenteExecucao() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteExecucao = tarefaRecorrenteExecucaoRepository.saveAndFlush(tarefaRecorrenteExecucao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tarefaRecorrenteExecucao
        restTarefaRecorrenteExecucaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarefaRecorrenteExecucao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tarefaRecorrenteExecucaoRepository.count();
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

    protected TarefaRecorrenteExecucao getPersistedTarefaRecorrenteExecucao(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        return tarefaRecorrenteExecucaoRepository.findById(tarefaRecorrenteExecucao.getId()).orElseThrow();
    }

    protected void assertPersistedTarefaRecorrenteExecucaoToMatchAllProperties(TarefaRecorrenteExecucao expectedTarefaRecorrenteExecucao) {
        assertTarefaRecorrenteExecucaoAllPropertiesEquals(
            expectedTarefaRecorrenteExecucao,
            getPersistedTarefaRecorrenteExecucao(expectedTarefaRecorrenteExecucao)
        );
    }

    protected void assertPersistedTarefaRecorrenteExecucaoToMatchUpdatableProperties(
        TarefaRecorrenteExecucao expectedTarefaRecorrenteExecucao
    ) {
        assertTarefaRecorrenteExecucaoAllUpdatablePropertiesEquals(
            expectedTarefaRecorrenteExecucao,
            getPersistedTarefaRecorrenteExecucao(expectedTarefaRecorrenteExecucao)
        );
    }
}
