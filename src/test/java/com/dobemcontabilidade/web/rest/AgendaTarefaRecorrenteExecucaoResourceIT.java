package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucao;
import com.dobemcontabilidade.domain.TarefaRecorrenteExecucao;
import com.dobemcontabilidade.repository.AgendaTarefaRecorrenteExecucaoRepository;
import com.dobemcontabilidade.service.AgendaTarefaRecorrenteExecucaoService;
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
 * Integration tests for the {@link AgendaTarefaRecorrenteExecucaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AgendaTarefaRecorrenteExecucaoResourceIT {

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Instant DEFAULT_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HORA_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DIA_INTEIRO = false;
    private static final Boolean UPDATED_DIA_INTEIRO = true;

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/agenda-tarefa-recorrente-execucaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AgendaTarefaRecorrenteExecucaoRepository agendaTarefaRecorrenteExecucaoRepository;

    @Mock
    private AgendaTarefaRecorrenteExecucaoRepository agendaTarefaRecorrenteExecucaoRepositoryMock;

    @Mock
    private AgendaTarefaRecorrenteExecucaoService agendaTarefaRecorrenteExecucaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgendaTarefaRecorrenteExecucaoMockMvc;

    private AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao;

    private AgendaTarefaRecorrenteExecucao insertedAgendaTarefaRecorrenteExecucao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaTarefaRecorrenteExecucao createEntity(EntityManager em) {
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao = new AgendaTarefaRecorrenteExecucao()
            .ativo(DEFAULT_ATIVO)
            .horaInicio(DEFAULT_HORA_INICIO)
            .horaFim(DEFAULT_HORA_FIM)
            .diaInteiro(DEFAULT_DIA_INTEIRO)
            .comentario(DEFAULT_COMENTARIO);
        // Add required entity
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao;
        if (TestUtil.findAll(em, TarefaRecorrenteExecucao.class).isEmpty()) {
            tarefaRecorrenteExecucao = TarefaRecorrenteExecucaoResourceIT.createEntity(em);
            em.persist(tarefaRecorrenteExecucao);
            em.flush();
        } else {
            tarefaRecorrenteExecucao = TestUtil.findAll(em, TarefaRecorrenteExecucao.class).get(0);
        }
        agendaTarefaRecorrenteExecucao.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        return agendaTarefaRecorrenteExecucao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaTarefaRecorrenteExecucao createUpdatedEntity(EntityManager em) {
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao = new AgendaTarefaRecorrenteExecucao()
            .ativo(UPDATED_ATIVO)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .diaInteiro(UPDATED_DIA_INTEIRO)
            .comentario(UPDATED_COMENTARIO);
        // Add required entity
        TarefaRecorrenteExecucao tarefaRecorrenteExecucao;
        if (TestUtil.findAll(em, TarefaRecorrenteExecucao.class).isEmpty()) {
            tarefaRecorrenteExecucao = TarefaRecorrenteExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(tarefaRecorrenteExecucao);
            em.flush();
        } else {
            tarefaRecorrenteExecucao = TestUtil.findAll(em, TarefaRecorrenteExecucao.class).get(0);
        }
        agendaTarefaRecorrenteExecucao.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        return agendaTarefaRecorrenteExecucao;
    }

    @BeforeEach
    public void initTest() {
        agendaTarefaRecorrenteExecucao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAgendaTarefaRecorrenteExecucao != null) {
            agendaTarefaRecorrenteExecucaoRepository.delete(insertedAgendaTarefaRecorrenteExecucao);
            insertedAgendaTarefaRecorrenteExecucao = null;
        }
    }

    @Test
    @Transactional
    void createAgendaTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AgendaTarefaRecorrenteExecucao
        var returnedAgendaTarefaRecorrenteExecucao = om.readValue(
            restAgendaTarefaRecorrenteExecucaoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(agendaTarefaRecorrenteExecucao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AgendaTarefaRecorrenteExecucao.class
        );

        // Validate the AgendaTarefaRecorrenteExecucao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAgendaTarefaRecorrenteExecucaoUpdatableFieldsEquals(
            returnedAgendaTarefaRecorrenteExecucao,
            getPersistedAgendaTarefaRecorrenteExecucao(returnedAgendaTarefaRecorrenteExecucao)
        );

        insertedAgendaTarefaRecorrenteExecucao = returnedAgendaTarefaRecorrenteExecucao;
    }

    @Test
    @Transactional
    void createAgendaTarefaRecorrenteExecucaoWithExistingId() throws Exception {
        // Create the AgendaTarefaRecorrenteExecucao with an existing ID
        agendaTarefaRecorrenteExecucao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaTarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgendaTarefaRecorrenteExecucaos() throws Exception {
        // Initialize the database
        insertedAgendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucaoRepository.saveAndFlush(agendaTarefaRecorrenteExecucao);

        // Get all the agendaTarefaRecorrenteExecucaoList
        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaTarefaRecorrenteExecucao.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFim").value(hasItem(DEFAULT_HORA_FIM.toString())))
            .andExpect(jsonPath("$.[*].diaInteiro").value(hasItem(DEFAULT_DIA_INTEIRO.booleanValue())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAgendaTarefaRecorrenteExecucaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(agendaTarefaRecorrenteExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgendaTarefaRecorrenteExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(agendaTarefaRecorrenteExecucaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAgendaTarefaRecorrenteExecucaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(agendaTarefaRecorrenteExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAgendaTarefaRecorrenteExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(agendaTarefaRecorrenteExecucaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAgendaTarefaRecorrenteExecucao() throws Exception {
        // Initialize the database
        insertedAgendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucaoRepository.saveAndFlush(agendaTarefaRecorrenteExecucao);

        // Get the agendaTarefaRecorrenteExecucao
        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(get(ENTITY_API_URL_ID, agendaTarefaRecorrenteExecucao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agendaTarefaRecorrenteExecucao.getId().intValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFim").value(DEFAULT_HORA_FIM.toString()))
            .andExpect(jsonPath("$.diaInteiro").value(DEFAULT_DIA_INTEIRO.booleanValue()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO));
    }

    @Test
    @Transactional
    void getNonExistingAgendaTarefaRecorrenteExecucao() throws Exception {
        // Get the agendaTarefaRecorrenteExecucao
        restAgendaTarefaRecorrenteExecucaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgendaTarefaRecorrenteExecucao() throws Exception {
        // Initialize the database
        insertedAgendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucaoRepository.saveAndFlush(agendaTarefaRecorrenteExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendaTarefaRecorrenteExecucao
        AgendaTarefaRecorrenteExecucao updatedAgendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucaoRepository
            .findById(agendaTarefaRecorrenteExecucao.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAgendaTarefaRecorrenteExecucao are not directly saved in db
        em.detach(updatedAgendaTarefaRecorrenteExecucao);
        updatedAgendaTarefaRecorrenteExecucao
            .ativo(UPDATED_ATIVO)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .diaInteiro(UPDATED_DIA_INTEIRO)
            .comentario(UPDATED_COMENTARIO);

        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgendaTarefaRecorrenteExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAgendaTarefaRecorrenteExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AgendaTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAgendaTarefaRecorrenteExecucaoToMatchAllProperties(updatedAgendaTarefaRecorrenteExecucao);
    }

    @Test
    @Transactional
    void putNonExistingAgendaTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendaTarefaRecorrenteExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendaTarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgendaTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendaTarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgendaTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaTarefaRecorrenteExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgendaTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgendaTarefaRecorrenteExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedAgendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucaoRepository.saveAndFlush(agendaTarefaRecorrenteExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendaTarefaRecorrenteExecucao using partial update
        AgendaTarefaRecorrenteExecucao partialUpdatedAgendaTarefaRecorrenteExecucao = new AgendaTarefaRecorrenteExecucao();
        partialUpdatedAgendaTarefaRecorrenteExecucao.setId(agendaTarefaRecorrenteExecucao.getId());

        partialUpdatedAgendaTarefaRecorrenteExecucao.horaFim(UPDATED_HORA_FIM).diaInteiro(UPDATED_DIA_INTEIRO);

        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendaTarefaRecorrenteExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendaTarefaRecorrenteExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AgendaTarefaRecorrenteExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendaTarefaRecorrenteExecucaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAgendaTarefaRecorrenteExecucao, agendaTarefaRecorrenteExecucao),
            getPersistedAgendaTarefaRecorrenteExecucao(agendaTarefaRecorrenteExecucao)
        );
    }

    @Test
    @Transactional
    void fullUpdateAgendaTarefaRecorrenteExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedAgendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucaoRepository.saveAndFlush(agendaTarefaRecorrenteExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendaTarefaRecorrenteExecucao using partial update
        AgendaTarefaRecorrenteExecucao partialUpdatedAgendaTarefaRecorrenteExecucao = new AgendaTarefaRecorrenteExecucao();
        partialUpdatedAgendaTarefaRecorrenteExecucao.setId(agendaTarefaRecorrenteExecucao.getId());

        partialUpdatedAgendaTarefaRecorrenteExecucao
            .ativo(UPDATED_ATIVO)
            .horaInicio(UPDATED_HORA_INICIO)
            .horaFim(UPDATED_HORA_FIM)
            .diaInteiro(UPDATED_DIA_INTEIRO)
            .comentario(UPDATED_COMENTARIO);

        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendaTarefaRecorrenteExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendaTarefaRecorrenteExecucao))
            )
            .andExpect(status().isOk());

        // Validate the AgendaTarefaRecorrenteExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendaTarefaRecorrenteExecucaoUpdatableFieldsEquals(
            partialUpdatedAgendaTarefaRecorrenteExecucao,
            getPersistedAgendaTarefaRecorrenteExecucao(partialUpdatedAgendaTarefaRecorrenteExecucao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAgendaTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agendaTarefaRecorrenteExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendaTarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgendaTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendaTarefaRecorrenteExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgendaTarefaRecorrenteExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaTarefaRecorrenteExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendaTarefaRecorrenteExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgendaTarefaRecorrenteExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgendaTarefaRecorrenteExecucao() throws Exception {
        // Initialize the database
        insertedAgendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucaoRepository.saveAndFlush(agendaTarefaRecorrenteExecucao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the agendaTarefaRecorrenteExecucao
        restAgendaTarefaRecorrenteExecucaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, agendaTarefaRecorrenteExecucao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return agendaTarefaRecorrenteExecucaoRepository.count();
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

    protected AgendaTarefaRecorrenteExecucao getPersistedAgendaTarefaRecorrenteExecucao(
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao
    ) {
        return agendaTarefaRecorrenteExecucaoRepository.findById(agendaTarefaRecorrenteExecucao.getId()).orElseThrow();
    }

    protected void assertPersistedAgendaTarefaRecorrenteExecucaoToMatchAllProperties(
        AgendaTarefaRecorrenteExecucao expectedAgendaTarefaRecorrenteExecucao
    ) {
        assertAgendaTarefaRecorrenteExecucaoAllPropertiesEquals(
            expectedAgendaTarefaRecorrenteExecucao,
            getPersistedAgendaTarefaRecorrenteExecucao(expectedAgendaTarefaRecorrenteExecucao)
        );
    }

    protected void assertPersistedAgendaTarefaRecorrenteExecucaoToMatchUpdatableProperties(
        AgendaTarefaRecorrenteExecucao expectedAgendaTarefaRecorrenteExecucao
    ) {
        assertAgendaTarefaRecorrenteExecucaoAllUpdatablePropertiesEquals(
            expectedAgendaTarefaRecorrenteExecucao,
            getPersistedAgendaTarefaRecorrenteExecucao(expectedAgendaTarefaRecorrenteExecucao)
        );
    }
}
