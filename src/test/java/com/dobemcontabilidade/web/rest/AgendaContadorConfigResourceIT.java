package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AgendaContadorConfigAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AgendaContadorConfig;
import com.dobemcontabilidade.domain.AgendaTarefaOrdemServicoExecucao;
import com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucao;
import com.dobemcontabilidade.domain.enumeration.TipoVisualizacaoAgendaEnum;
import com.dobemcontabilidade.repository.AgendaContadorConfigRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AgendaContadorConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgendaContadorConfigResourceIT {

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final TipoVisualizacaoAgendaEnum DEFAULT_TIPO_VISUALIZACAO_AGENDA_ENUM = TipoVisualizacaoAgendaEnum.RECORRENTE;
    private static final TipoVisualizacaoAgendaEnum UPDATED_TIPO_VISUALIZACAO_AGENDA_ENUM = TipoVisualizacaoAgendaEnum.ORDEMSERVICO;

    private static final String ENTITY_API_URL = "/api/agenda-contador-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AgendaContadorConfigRepository agendaContadorConfigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgendaContadorConfigMockMvc;

    private AgendaContadorConfig agendaContadorConfig;

    private AgendaContadorConfig insertedAgendaContadorConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaContadorConfig createEntity(EntityManager em) {
        AgendaContadorConfig agendaContadorConfig = new AgendaContadorConfig()
            .ativo(DEFAULT_ATIVO)
            .tipoVisualizacaoAgendaEnum(DEFAULT_TIPO_VISUALIZACAO_AGENDA_ENUM);
        // Add required entity
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao;
        if (TestUtil.findAll(em, AgendaTarefaRecorrenteExecucao.class).isEmpty()) {
            agendaTarefaRecorrenteExecucao = AgendaTarefaRecorrenteExecucaoResourceIT.createEntity(em);
            em.persist(agendaTarefaRecorrenteExecucao);
            em.flush();
        } else {
            agendaTarefaRecorrenteExecucao = TestUtil.findAll(em, AgendaTarefaRecorrenteExecucao.class).get(0);
        }
        agendaContadorConfig.setAgendaTarefaRecorrenteExecucao(agendaTarefaRecorrenteExecucao);
        // Add required entity
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao;
        if (TestUtil.findAll(em, AgendaTarefaOrdemServicoExecucao.class).isEmpty()) {
            agendaTarefaOrdemServicoExecucao = AgendaTarefaOrdemServicoExecucaoResourceIT.createEntity(em);
            em.persist(agendaTarefaOrdemServicoExecucao);
            em.flush();
        } else {
            agendaTarefaOrdemServicoExecucao = TestUtil.findAll(em, AgendaTarefaOrdemServicoExecucao.class).get(0);
        }
        agendaContadorConfig.setAgendaTarefaOrdemServicoExecucao(agendaTarefaOrdemServicoExecucao);
        return agendaContadorConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgendaContadorConfig createUpdatedEntity(EntityManager em) {
        AgendaContadorConfig agendaContadorConfig = new AgendaContadorConfig()
            .ativo(UPDATED_ATIVO)
            .tipoVisualizacaoAgendaEnum(UPDATED_TIPO_VISUALIZACAO_AGENDA_ENUM);
        // Add required entity
        AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao;
        if (TestUtil.findAll(em, AgendaTarefaRecorrenteExecucao.class).isEmpty()) {
            agendaTarefaRecorrenteExecucao = AgendaTarefaRecorrenteExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(agendaTarefaRecorrenteExecucao);
            em.flush();
        } else {
            agendaTarefaRecorrenteExecucao = TestUtil.findAll(em, AgendaTarefaRecorrenteExecucao.class).get(0);
        }
        agendaContadorConfig.setAgendaTarefaRecorrenteExecucao(agendaTarefaRecorrenteExecucao);
        // Add required entity
        AgendaTarefaOrdemServicoExecucao agendaTarefaOrdemServicoExecucao;
        if (TestUtil.findAll(em, AgendaTarefaOrdemServicoExecucao.class).isEmpty()) {
            agendaTarefaOrdemServicoExecucao = AgendaTarefaOrdemServicoExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(agendaTarefaOrdemServicoExecucao);
            em.flush();
        } else {
            agendaTarefaOrdemServicoExecucao = TestUtil.findAll(em, AgendaTarefaOrdemServicoExecucao.class).get(0);
        }
        agendaContadorConfig.setAgendaTarefaOrdemServicoExecucao(agendaTarefaOrdemServicoExecucao);
        return agendaContadorConfig;
    }

    @BeforeEach
    public void initTest() {
        agendaContadorConfig = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAgendaContadorConfig != null) {
            agendaContadorConfigRepository.delete(insertedAgendaContadorConfig);
            insertedAgendaContadorConfig = null;
        }
    }

    @Test
    @Transactional
    void createAgendaContadorConfig() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AgendaContadorConfig
        var returnedAgendaContadorConfig = om.readValue(
            restAgendaContadorConfigMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaContadorConfig)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AgendaContadorConfig.class
        );

        // Validate the AgendaContadorConfig in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAgendaContadorConfigUpdatableFieldsEquals(
            returnedAgendaContadorConfig,
            getPersistedAgendaContadorConfig(returnedAgendaContadorConfig)
        );

        insertedAgendaContadorConfig = returnedAgendaContadorConfig;
    }

    @Test
    @Transactional
    void createAgendaContadorConfigWithExistingId() throws Exception {
        // Create the AgendaContadorConfig with an existing ID
        agendaContadorConfig.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaContadorConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaContadorConfig)))
            .andExpect(status().isBadRequest());

        // Validate the AgendaContadorConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgendaContadorConfigs() throws Exception {
        // Initialize the database
        insertedAgendaContadorConfig = agendaContadorConfigRepository.saveAndFlush(agendaContadorConfig);

        // Get all the agendaContadorConfigList
        restAgendaContadorConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendaContadorConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].tipoVisualizacaoAgendaEnum").value(hasItem(DEFAULT_TIPO_VISUALIZACAO_AGENDA_ENUM.toString())));
    }

    @Test
    @Transactional
    void getAgendaContadorConfig() throws Exception {
        // Initialize the database
        insertedAgendaContadorConfig = agendaContadorConfigRepository.saveAndFlush(agendaContadorConfig);

        // Get the agendaContadorConfig
        restAgendaContadorConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, agendaContadorConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agendaContadorConfig.getId().intValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.tipoVisualizacaoAgendaEnum").value(DEFAULT_TIPO_VISUALIZACAO_AGENDA_ENUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAgendaContadorConfig() throws Exception {
        // Get the agendaContadorConfig
        restAgendaContadorConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgendaContadorConfig() throws Exception {
        // Initialize the database
        insertedAgendaContadorConfig = agendaContadorConfigRepository.saveAndFlush(agendaContadorConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendaContadorConfig
        AgendaContadorConfig updatedAgendaContadorConfig = agendaContadorConfigRepository
            .findById(agendaContadorConfig.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAgendaContadorConfig are not directly saved in db
        em.detach(updatedAgendaContadorConfig);
        updatedAgendaContadorConfig.ativo(UPDATED_ATIVO).tipoVisualizacaoAgendaEnum(UPDATED_TIPO_VISUALIZACAO_AGENDA_ENUM);

        restAgendaContadorConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAgendaContadorConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAgendaContadorConfig))
            )
            .andExpect(status().isOk());

        // Validate the AgendaContadorConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAgendaContadorConfigToMatchAllProperties(updatedAgendaContadorConfig);
    }

    @Test
    @Transactional
    void putNonExistingAgendaContadorConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaContadorConfig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaContadorConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agendaContadorConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendaContadorConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaContadorConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgendaContadorConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaContadorConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaContadorConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(agendaContadorConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaContadorConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgendaContadorConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaContadorConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaContadorConfigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(agendaContadorConfig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgendaContadorConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgendaContadorConfigWithPatch() throws Exception {
        // Initialize the database
        insertedAgendaContadorConfig = agendaContadorConfigRepository.saveAndFlush(agendaContadorConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendaContadorConfig using partial update
        AgendaContadorConfig partialUpdatedAgendaContadorConfig = new AgendaContadorConfig();
        partialUpdatedAgendaContadorConfig.setId(agendaContadorConfig.getId());

        partialUpdatedAgendaContadorConfig.tipoVisualizacaoAgendaEnum(UPDATED_TIPO_VISUALIZACAO_AGENDA_ENUM);

        restAgendaContadorConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendaContadorConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendaContadorConfig))
            )
            .andExpect(status().isOk());

        // Validate the AgendaContadorConfig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendaContadorConfigUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAgendaContadorConfig, agendaContadorConfig),
            getPersistedAgendaContadorConfig(agendaContadorConfig)
        );
    }

    @Test
    @Transactional
    void fullUpdateAgendaContadorConfigWithPatch() throws Exception {
        // Initialize the database
        insertedAgendaContadorConfig = agendaContadorConfigRepository.saveAndFlush(agendaContadorConfig);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the agendaContadorConfig using partial update
        AgendaContadorConfig partialUpdatedAgendaContadorConfig = new AgendaContadorConfig();
        partialUpdatedAgendaContadorConfig.setId(agendaContadorConfig.getId());

        partialUpdatedAgendaContadorConfig.ativo(UPDATED_ATIVO).tipoVisualizacaoAgendaEnum(UPDATED_TIPO_VISUALIZACAO_AGENDA_ENUM);

        restAgendaContadorConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgendaContadorConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAgendaContadorConfig))
            )
            .andExpect(status().isOk());

        // Validate the AgendaContadorConfig in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAgendaContadorConfigUpdatableFieldsEquals(
            partialUpdatedAgendaContadorConfig,
            getPersistedAgendaContadorConfig(partialUpdatedAgendaContadorConfig)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAgendaContadorConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaContadorConfig.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaContadorConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agendaContadorConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendaContadorConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaContadorConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgendaContadorConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaContadorConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaContadorConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(agendaContadorConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the AgendaContadorConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgendaContadorConfig() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        agendaContadorConfig.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgendaContadorConfigMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(agendaContadorConfig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AgendaContadorConfig in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgendaContadorConfig() throws Exception {
        // Initialize the database
        insertedAgendaContadorConfig = agendaContadorConfigRepository.saveAndFlush(agendaContadorConfig);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the agendaContadorConfig
        restAgendaContadorConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, agendaContadorConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return agendaContadorConfigRepository.count();
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

    protected AgendaContadorConfig getPersistedAgendaContadorConfig(AgendaContadorConfig agendaContadorConfig) {
        return agendaContadorConfigRepository.findById(agendaContadorConfig.getId()).orElseThrow();
    }

    protected void assertPersistedAgendaContadorConfigToMatchAllProperties(AgendaContadorConfig expectedAgendaContadorConfig) {
        assertAgendaContadorConfigAllPropertiesEquals(
            expectedAgendaContadorConfig,
            getPersistedAgendaContadorConfig(expectedAgendaContadorConfig)
        );
    }

    protected void assertPersistedAgendaContadorConfigToMatchUpdatableProperties(AgendaContadorConfig expectedAgendaContadorConfig) {
        assertAgendaContadorConfigAllUpdatablePropertiesEquals(
            expectedAgendaContadorConfig,
            getPersistedAgendaContadorConfig(expectedAgendaContadorConfig)
        );
    }
}
