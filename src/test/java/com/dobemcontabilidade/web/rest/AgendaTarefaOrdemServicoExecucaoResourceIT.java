package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucao;
import com.dobemcontabilidade.domain.TarefaOrdemServicoExecucao;
import com.dobemcontabilidade.repository.AgendaTarefaOrdemServicoExecucaoRepository;
import com.dobemcontabilidade.service.AgendaTarefaOrdemServicoExecucaoService;
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
 * Integration tests for the {@link AgendaTarefaOrdemServicoExecucaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AgendaTarefaOrdemServicoExecucaoResourceIT {

    private static final Instant DEFAULT_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DIA_INTEIRO = false;
    private static final Boolean UPDATED_DIA_INTEIRO = true;

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final String ENTITY_API_URL = "/api/agenda-tarefa-ordem-servico-execucaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AgendaTarefaOrdemServicoExecucaoRepository agendaTarefaOrdemServicoExecucaoRepository;

    @Mock
    private AgendaTarefaOrdemServicoExecucaoRepository agendaTarefaOrdemServicoExecucaoRepositoryMock;

    @Mock
    private AgendaTarefaOrdemServicoExecucaoService agendaTarefaOrdemServicoExecucaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgendaTarefaOrdemServicoExecucaoMockMvc;

    private AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao;

    private AgendaTarefaOrdemServicoExecucao insertedAgendaTarefaOrdemServicoExecucao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaTarefaOrdemServicoExecucao createEntity(EntityManager em) {
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao = new AgendaTarefaOrdemServicoExecucao()
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFim(DEFAULT_HORA_FIM)
            .diaInteiro(DEFAULT_DIA_INTEIRO)
            .ativo(DEFAULT_ATIVO);
        // Add required entity
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;
        if (TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).isEmpty()) {
            tarefaOrdemServicoExecucao = TarefaOrdemServicoExecucaoResourceIT.createEntity(em);
            em.persist(tarefaOrdemServicoExecucao);
            em.flush();
        } else {
            tarefaOrdemServicoExecucao = TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).get(0);
        }
        agendaTarefaOrdemServicoExecucao.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao);
        return agendaTarefaOrdemServicoExecucao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaTarefaOrdemServicoExecucao createUpdatedEntity(EntityManager em) {
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao = new AgendaTarefaOrdemServicoExecucao()
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .diaInteiro(UPDATED_DIA_INTEIRO)
            .ativo(UPDATED_ATIVO);
        // Add required entity
        TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;
        if (TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).isEmpty()) {
            tarefaOrdemServicoExecucao = TarefaOrdemServicoExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(tarefaOrdemServicoExecucao);
            em.flush();
        } else {
            tarefaOrdemServicoExecucao = TestUtil.findAll(em, TarefaOrdemServicoExecucao.class).get(0);
        }
        agendaTarefaOrdemServicoExecucao.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao);
        return agendaTarefaOrdemServicoExecucao;
    }

    @BeforeEach
    public void initTest() {
        agendaTarefaOrdemServicoExecucao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAgendaTarefaOrdemServicoExecucao != null) {
            agendaTarefaOrdemServicoExecucaoRepository.delete(insertedAgendaTarefaOrdemServicoExecucao);
            insertedAgendaTarefaOrdemServicoExecucao = null;
        }
    }

    @Test
    @Transactional
    void createAgendaTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AgendaTarefaOrdemServicoExecucao
        var returnedAgendaTarefaOrdemServicoExecucao = om.readValue(
            restAgendaTarefaOrdemServicoExecucaoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(agendaTarefaOrdemServicoExecucao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AgendaTarefaOrdemServicoExecucao.class
        );

        // Validate the AgendaTarefaOrdemServicoExecucao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAgendaTarefaOrdemServicoExecucaoUpdatableFieldsEquals(
            returnedAgendaTarefaOrdemServicoExecucao,
            getPersistedAgendaTarefaOrdemServicoExecucao(returnedAgendaTarefaOrdemServicoExecucao)
        );

        insertedAgendaTarefaOrdemServicoExecucao = returnedAgendaTarefaOrdemServicoExecucao;
    }

    @Test
    @Transactional
    void createAgendaTarefaOrdemServicoExecucaoWithExistingId() throws Exception {
        // Create the AgendaTarefaOrdemServicoExecucao with an existing ID
        agendaTarefaOrdemServicoExecucao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaTarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgendaTarefaOrdemServicoExecucaos() throws Exception {
        // Initialize the database
        insertedAgendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucaoRepository.saveAndFlush(
            agendaTarefaOrdemServicoExecucao
        );

        // Get all the agendaTarefaOrdemServicoExecucaoList
        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaTarefaOrdemServicoExecucao.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFim").value(hasItem(DEFAULT_HORA_FIM.toString())))
            .andExpect(jsonPath("$.[*].diaInteiro").value(hasItem(DEFAULT_DIA_INTEIRO.booleanValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAgendaTarefaOrdemServicoExecucaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(agendaTarefaOrdemServicoExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgendaTarefaOrdemServicoExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(agendaTarefaOrdemServicoExecucaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAgendaTarefaOrdemServicoExecucaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(agendaTarefaOrdemServicoExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgendaTarefaOrdemServicoExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(agendaTarefaOrdemServicoExecucaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAgendaTarefaOrdemServicoExecucao() throws Exception {
        // Initialize the database
        insertedAgendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucaoRepository.saveAndFlush(
            agendaTarefaOrdemServicoExecucao
        );

        // Get the agendaTarefaOrdemServicoExecucao
        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(get(ENTITY_API_URL_ID, agendaTarefaOrdemServicoExecucao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agendaTarefaOrdemServicoExecucao.getId().intValue()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFim").value(DEFAULT_HORA_FIM.toString()))
            .andExpect(jsonPath("$.diaInteiro").value(DEFAULT_DIA_INTEIRO.booleanValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAgendaTarefaOrdemServicoExecucao() throws Exception {
        // Get the agendaTarefaOrdemServicoExecucao
        restAgendaTarefaOrdemServicoExecucaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgendaTarefaOrdemServicoExecucao() throws Exception {
        // Initialize the database
        insertedAgendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucaoRepository.saveAndFlush(
            agendaTarefaOrdemServicoExecucao
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendaTarefaOrdemServicoExecucao
        AgendaTarefaOrdemServicoExecucao updatedAgendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucaoRepository
            .findById(agendaTarefaOrdemServicoExecucao.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAgendaTarefaOrdemServicoExecucao are not directly saved in db
        em.detach(updatedAgendaTarefaOrdemServicoExecucao);
        updatedAgendaTarefaOrdemServicoExecucao
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .diaInteiro(UPDATED_DIA_INTEIRO)
            .ativo(UPDATED_ATIVO);

        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgendaTarefaOrdemServicoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAgendaTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AgendaTarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAgendaTarefaOrdemServicoExecucaoToMatchAllProperties(updatedAgendaTarefaOrdemServicoExecucao);
    }

    @Test
    @Transactional
    void putNonExistingAgendaTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendaTarefaOrdemServicoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendaTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaTarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgendaTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendaTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaTarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgendaTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgendaTarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgendaTarefaOrdemServicoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedAgendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucaoRepository.saveAndFlush(
            agendaTarefaOrdemServicoExecucao
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendaTarefaOrdemServicoExecucao using partial update
        AgendaTarefaOrdemServicoExecucao partialUpdatedAgendaTarefaOrdemServicoExecucao = new AgendaTarefaOrdemServicoExecucao();
        partialUpdatedAgendaTarefaOrdemServicoExecucao.setId(agendaTarefaOrdemServicoExecucao.getId());

        partialUpdatedAgendaTarefaOrdemServicoExecucao.horaInicio(UPDATED_HORA_INICIO).horaFim(UPDATED_HORA_FIM).ativo(UPDATED_ATIVO);

        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendaTarefaOrdemServicoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendaTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AgendaTarefaOrdemServicoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendaTarefaOrdemServicoExecucaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAgendaTarefaOrdemServicoExecucao, agendaTarefaOrdemServicoExecucao),
            getPersistedAgendaTarefaOrdemServicoExecucao(agendaTarefaOrdemServicoExecucao)
        );
    }

    @Test
    @Transactional
    void fullUpdateAgendaTarefaOrdemServicoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedAgendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucaoRepository.saveAndFlush(
            agendaTarefaOrdemServicoExecucao
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendaTarefaOrdemServicoExecucao using partial update
        AgendaTarefaOrdemServicoExecucao partialUpdatedAgendaTarefaOrdemServicoExecucao = new AgendaTarefaOrdemServicoExecucao();
        partialUpdatedAgendaTarefaOrdemServicoExecucao.setId(agendaTarefaOrdemServicoExecucao.getId());

        partialUpdatedAgendaTarefaOrdemServicoExecucao
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .diaInteiro(UPDATED_DIA_INTEIRO)
            .ativo(UPDATED_ATIVO);

        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendaTarefaOrdemServicoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendaTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AgendaTarefaOrdemServicoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendaTarefaOrdemServicoExecucaoUpdatableFieldsEquals(
            partialUpdatedAgendaTarefaOrdemServicoExecucao,
            getPersistedAgendaTarefaOrdemServicoExecucao(partialUpdatedAgendaTarefaOrdemServicoExecucao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAgendaTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agendaTarefaOrdemServicoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendaTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaTarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgendaTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendaTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaTarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgendaTarefaOrdemServicoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaOrdemServicoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendaTarefaOrdemServicoExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgendaTarefaOrdemServicoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgendaTarefaOrdemServicoExecucao() throws Exception {
        // Initialize the database
        insertedAgendaTarefaOrdemServicoExecucao = agendaTarefaOrdemServicoExecucaoRepository.saveAndFlush(
            agendaTarefaOrdemServicoExecucao
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the agendaTarefaOrdemServicoExecucao
        restAgendaTarefaOrdemServicoExecucaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, agendaTarefaOrdemServicoExecucao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return agendaTarefaOrdemServicoExecucaoRepository.count();
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

    protected AgendaTarefaOrdemServicoExecucao getPersistedAgendaTarefaOrdemServicoExecucao(
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao
    ) {
        return agendaTarefaOrdemServicoExecucaoRepository.findById(agendaTarefaOrdemServicoExecucao.getId()).orElseThrow();
    }

    protected void assertPersistedAgendaTarefaOrdemServicoExecucaoToMatchAllProperties(
        AgendaTarefaOrdemServicoExecucao expectedAgendaTarefaOrdemServicoExecucao
    ) {
        assertAgendaTarefaOrdemServicoExecucaoAllPropertiesEquals(
            expectedAgendaTarefaOrdemServicoExecucao,
            getPersistedAgendaTarefaOrdemServicoExecucao(expectedAgendaTarefaOrdemServicoExecucao)
        );
    }

    protected void assertPersistedAgendaTarefaOrdemServicoExecucaoToMatchUpdatableProperties(
        AgendaTarefaOrdemServicoExecucao expectedAgendaTarefaOrdemServicoExecucao
    ) {
        assertAgendaTarefaOrdemServicoExecucaoAllUpdatablePropertiesEquals(
            expectedAgendaTarefaOrdemServicoExecucao,
            getPersistedAgendaTarefaOrdemServicoExecucao(expectedAgendaTarefaOrdemServicoExecucao)
        );
    }
}
