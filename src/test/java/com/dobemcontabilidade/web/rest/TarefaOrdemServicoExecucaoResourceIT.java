package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TarefaOrdemServicoExecucaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.TarefaOrdemServico;
import com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao;
import com.dobemcontabilidade.domain.enumeration.MesCompetenciaEnum;
import com.dobemcontabilidade.domain.enumeration.SituacaoTarefaEnum;
import com.dobemcontabilidade.repository.TarefaOrdemServicoExecucaoRepository;
import com.dobemcontabilidade.service.TarefaOrdemServicoExecucaoService;
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
 * Integration tests for the {@link TarefaOrdemServicoExecucaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TarefaOrdemServicoExecucaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    private static final Instant DEFAULT_DATA_ENTREGA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ENTREGA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_AGENDADA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_AGENDADA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_CONCLUIDA = false;
    private static final Boolean UPDATED_CONCLUIDA = true;

    private static final Boolean DEFAULT_NOTIFICAR_CLIENTE = false;
    private static final Boolean UPDATED_NOTIFICAR_CLIENTE = true;

    private static final MesCompetenciaEnum DEFAULT_MES = MesCompetenciaEnum.JANEIRO;
    private static final MesCompetenciaEnum UPDATED_MES = MesCompetenciaEnum.FEVEREIRO;

    private static final SituacaoTarefaEnum DEFAULT_SITUACAO_TAREFA = SituacaoTarefaEnum.AGENDADA;
    private static final SituacaoTarefaEnum UPDATED_SITUACAO_TAREFA = SituacaoTarefaEnum.CANCELADA;

    private static final String ENTITY_API_URL = "/api/tarefa-ordem-servico-execucaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TarefaOrdemServicoExecucaoRepository tarefaOrdemServicoExecucaoRepository;

    @Mock
    private TarefaOrdemServicoExecucaoRepository tarefaOrdemServicoExecucaoRepositoryMock;

    @Mock
    private TarefaOrdemServicoExecucaoService tarefaOrdemServicoExecucaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarefaOrdemServicoExecucaoMockMvc;

    private TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;

    private TarefaOrdemServicoExecucao insertedTarefaOrdemServicoExecucao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaOrdemServicoExecucao createEntity(EntityManager em) {
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao = new TarefaOrdemServicoExecucao()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .ordem(DEFAULT_ORDEM)
            .dataEntrega(DEFAULT_DATA_ENTREGA)
            .dataAgendada(DEFAULT_DATA_AGENDADA)
            .concluida(DEFAULT_CONCLUIDA)
            .notificarCliente(DEFAULT_NOTIFICAR_CLIENTE)
            .mes(DEFAULT_MES)
            .situacaoTarefa(DEFAULT_SITUACAO_TAREFA);
        // Add required entity
        TarefaOrdemServico tarefaOrdemServico;
        if (TestUtil.findAll(em, TarefaOrdemServico.class).isEmpty()) {
            tarefaOrdemServico = TarefaOrdemServicoResourceIT.createEntity(em);
            em.persist(tarefaOrdemServico);
            em.flush();
        } else {
            tarefaOrdemServico = TestUtil.findAll(em, TarefaOrdemServico.class).get(0);
        }
        tarefaOrdemServicoExecucao.setTarefaOrdemServico(tarefaOrdemServico);
        return tarefaOrdemServicoExecucao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaOrdemServicoExecucao createUpdatedEntity(EntityManager em) {
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao = new TarefaOrdemServicoExecucao()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .ordem(UPDATED_ORDEM)
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .dataAgendada(UPDATED_DATA_AGENDADA)
            .concluida(UPDATED_CONCLUIDA)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .mes(UPDATED_MES)
            .situacaoTarefa(UPDATED_SITUACAO_TAREFA);
        // Add required entity
        TarefaOrdemServico tarefaOrdemServico;
        if (TestUtil.findAll(em, TarefaOrdemServico.class).isEmpty()) {
            tarefaOrdemServico = TarefaOrdemServicoResourceIT.createUpdatedEntity(em);
            em.persist(tarefaOrdemServico);
            em.flush();
        } else {
            tarefaOrdemServico = TestUtil.findAll(em, TarefaOrdemServico.class).get(0);
        }
        tarefaOrdemServicoExecucao.setTarefaOrdemServico(tarefaOrdemServico);
        return tarefaOrdemServicoExecucao;
    }

    @BeforeEach
    public void initTest() {
        tarefaOrdemServicoExecucao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTarefaOrdemServicoExecucao != null) {
            tarefaOrdemServicoExecucaoRepository.delete(insertedTarefaOrdemServicoExecucao);
            insertedTarefaOrdemServicoExecucao = null;
        }
    }

    @Test
    @Transactional
    void createTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TarefaOrdemServicoExecucao
        var returnedTarefaOrdemServicoExecucao = om.readValue(
            restTarefaOrdemServicoExecucaoMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaOrdemServicoExecucao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TarefaOrdemServicoExecucao.class
        );

        // Validate the TarefaOrdemServicoExecucao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTarefaOrdemServicoExecucaoUpdatableFieldsEquals(
            returnedTarefaOrdemServicoExecucao,
            getPersistedTarefaOrdemServicoExecucao(returnedTarefaOrdemServicoExecucao)
        );

        insertedTarefaOrdemServicoExecucao = returnedTarefaOrdemServicoExecucao;
    }

    @Test
    @Transactional
    void createTarefaOrdemServicoExecucaoWithExistingId() throws Exception {
        // Create the TarefaOrdemServicoExecucao with an existing ID
        tarefaOrdemServicoExecucao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarefaOrdemServicoExecucaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaOrdemServicoExecucao)))
            .andExpect(status().isBadRequest());

        // Validate the TarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTarefaOrdemServicoExecucaos() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServicoExecucao = tarefaOrdemServicoExecucaoRepository.saveAndFlush(tarefaOrdemServicoExecucao);

        // Get all the tarefaOrdemServicoExecucaoList
        restTarefaOrdemServicoExecucaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefaOrdemServicoExecucao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].dataEntrega").value(hasItem(DEFAULT_DATA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].dataAgendada").value(hasItem(DEFAULT_DATA_AGENDADA.toString())))
            .andExpect(jsonPath("$.[*].concluida").value(hasItem(DEFAULT_CONCLUIDA.booleanValue())))
            .andExpect(jsonPath("$.[*].notificarCliente").value(hasItem(DEFAULT_NOTIFICAR_CLIENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES.toString())))
            .andExpect(jsonPath("$.[*].situacaoTarefa").value(hasItem(DEFAULT_SITUACAO_TAREFA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTarefaOrdemServicoExecucaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(tarefaOrdemServicoExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTarefaOrdemServicoExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tarefaOrdemServicoExecucaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTarefaOrdemServicoExecucaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tarefaOrdemServicoExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTarefaOrdemServicoExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tarefaOrdemServicoExecucaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTarefaOrdemServicoExecucao() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServicoExecucao = tarefaOrdemServicoExecucaoRepository.saveAndFlush(tarefaOrdemServicoExecucao);

        // Get the tarefaOrdemServicoExecucao
        restTarefaOrdemServicoExecucaoMockMvc
            .perform(get(ENTITY_API_URL_ID, tarefaOrdemServicoExecucao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarefaOrdemServicoExecucao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.dataEntrega").value(DEFAULT_DATA_ENTREGA.toString()))
            .andExpect(jsonPath("$.dataAgendada").value(DEFAULT_DATA_AGENDADA.toString()))
            .andExpect(jsonPath("$.concluida").value(DEFAULT_CONCLUIDA.booleanValue()))
            .andExpect(jsonPath("$.notificarCliente").value(DEFAULT_NOTIFICAR_CLIENTE.booleanValue()))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES.toString()))
            .andExpect(jsonPath("$.situacaoTarefa").value(DEFAULT_SITUACAO_TAREFA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTarefaOrdemServicoExecucao() throws Exception {
        // Get the tarefaOrdemServicoExecucao
        restTarefaOrdemServicoExecucaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarefaOrdemServicoExecucao() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServicoExecucao = tarefaOrdemServicoExecucaoRepository.saveAndFlush(tarefaOrdemServicoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaOrdemServicoExecucao
        TarefaOrdemServicoExecucao updatedTarefaOrdemServicoExecucao = tarefaOrdemServicoExecucaoRepository
            .findById(tarefaOrdemServicoExecucao.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTarefaOrdemServicoExecucao are not directly saved in db
        em.detach(updatedTarefaOrdemServicoExecucao);
        updatedTarefaOrdemServicoExecucao
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .ordem(UPDATED_ORDEM)
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .dataAgendada(UPDATED_DATA_AGENDADA)
            .concluida(UPDATED_CONCLUIDA)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .mes(UPDATED_MES)
            .situacaoTarefa(UPDATED_SITUACAO_TAREFA);

        restTarefaOrdemServicoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTarefaOrdemServicoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the TarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTarefaOrdemServicoExecucaoToMatchAllProperties(updatedTarefaOrdemServicoExecucao);
    }

    @Test
    @Transactional
    void putNonExistingTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarefaOrdemServicoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoExecucaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaOrdemServicoExecucao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarefaOrdemServicoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServicoExecucao = tarefaOrdemServicoExecucaoRepository.saveAndFlush(tarefaOrdemServicoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaOrdemServicoExecucao using partial update
        TarefaOrdemServicoExecucao partialUpdatedTarefaOrdemServicoExecucao = new TarefaOrdemServicoExecucao();
        partialUpdatedTarefaOrdemServicoExecucao.setId(tarefaOrdemServicoExecucao.getId());

        partialUpdatedTarefaOrdemServicoExecucao
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .ordem(UPDATED_ORDEM)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .situacaoTarefa(UPDATED_SITUACAO_TAREFA);

        restTarefaOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaOrdemServicoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the TarefaOrdemServicoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaOrdemServicoExecucaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTarefaOrdemServicoExecucao, tarefaOrdemServicoExecucao),
            getPersistedTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao)
        );
    }

    @Test
    @Transactional
    void fullUpdateTarefaOrdemServicoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServicoExecucao = tarefaOrdemServicoExecucaoRepository.saveAndFlush(tarefaOrdemServicoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaOrdemServicoExecucao using partial update
        TarefaOrdemServicoExecucao partialUpdatedTarefaOrdemServicoExecucao = new TarefaOrdemServicoExecucao();
        partialUpdatedTarefaOrdemServicoExecucao.setId(tarefaOrdemServicoExecucao.getId());

        partialUpdatedTarefaOrdemServicoExecucao
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .ordem(UPDATED_ORDEM)
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .dataAgendada(UPDATED_DATA_AGENDADA)
            .concluida(UPDATED_CONCLUIDA)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .mes(UPDATED_MES)
            .situacaoTarefa(UPDATED_SITUACAO_TAREFA);

        restTarefaOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaOrdemServicoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the TarefaOrdemServicoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaOrdemServicoExecucaoUpdatableFieldsEquals(
            partialUpdatedTarefaOrdemServicoExecucao,
            getPersistedTarefaOrdemServicoExecucao(partialUpdatedTarefaOrdemServicoExecucao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarefaOrdemServicoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tarefaOrdemServicoExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarefaOrdemServicoExecucao() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServicoExecucao = tarefaOrdemServicoExecucaoRepository.saveAndFlush(tarefaOrdemServicoExecucao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tarefaOrdemServicoExecucao
        restTarefaOrdemServicoExecucaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarefaOrdemServicoExecucao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tarefaOrdemServicoExecucaoRepository.count();
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

    protected TarefaOrdemServicoExecucao getPersistedTarefaOrdemServicoExecucao(TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao) {
        return tarefaOrdemServicoExecucaoRepository.findById(tarefaOrdemServicoExecucao.getId()).orElseThrow();
    }

    protected void assertPersistedTarefaOrdemServicoExecucaoToMatchAllProperties(
        TarefaOrdemServicoExecucao expectedTarefaOrdemServicoExecucao
    ) {
        assertTarefaOrdemServicoExecucaoAllPropertiesEquals(
            expectedTarefaOrdemServicoExecucao,
            getPersistedTarefaOrdemServicoExecucao(expectedTarefaOrdemServicoExecucao)
        );
    }

    protected void assertPersistedTarefaOrdemServicoExecucaoToMatchUpdatableProperties(
        TarefaOrdemServicoExecucao expectedTarefaOrdemServicoExecucao
    ) {
        assertTarefaOrdemServicoExecucaoAllUpdatablePropertiesEquals(
            expectedTarefaOrdemServicoExecucao,
            getPersistedTarefaOrdemServicoExecucao(expectedTarefaOrdemServicoExecucao)
        );
    }
}
