package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TarefaOrdemServicoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.ServicoContabilOrdemServico;
import com.dobemcontabilidade.domain.TarefaOrdemServico;
import com.dobemcontabilidade.repository.TarefaOrdemServicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link TarefaOrdemServicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TarefaOrdemServicoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NOTIFICAR_CLIENTE = false;
    private static final Boolean UPDATED_NOTIFICAR_CLIENTE = true;

    private static final Boolean DEFAULT_NOTIFICAR_CONTADOR = false;
    private static final Boolean UPDATED_NOTIFICAR_CONTADOR = true;

    private static final Integer DEFAULT_ANO_REFERENCIA = 1;
    private static final Integer UPDATED_ANO_REFERENCIA = 2;

    private static final Instant DEFAULT_DATA_ADMIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ADMIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tarefa-ordem-servicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TarefaOrdemServicoRepository tarefaOrdemServicoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarefaOrdemServicoMockMvc;

    private TarefaOrdemServico tarefaOrdemServico;

    private TarefaOrdemServico insertedTarefaOrdemServico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaOrdemServico createEntity(EntityManager em) {
        TarefaOrdemServico tarefaOrdemServico = new TarefaOrdemServico()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .notificarCliente(DEFAULT_NOTIFICAR_CLIENTE)
            .notificarContador(DEFAULT_NOTIFICAR_CONTADOR)
            .anoReferencia(DEFAULT_ANO_REFERENCIA)
            .dataAdmin(DEFAULT_DATA_ADMIN);
        // Add required entity
        ServicoContabilOrdemServico servicoContabilOrdemServico;
        if (TestUtil.findAll(em, ServicoContabilOrdemServico.class).isEmpty()) {
            servicoContabilOrdemServico = ServicoContabilOrdemServicoResourceIT.createEntity(em);
            em.persist(servicoContabilOrdemServico);
            em.flush();
        } else {
            servicoContabilOrdemServico = TestUtil.findAll(em, ServicoContabilOrdemServico.class).get(0);
        }
        tarefaOrdemServico.setServicoContabilOrdemServico(servicoContabilOrdemServico);
        return tarefaOrdemServico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaOrdemServico createUpdatedEntity(EntityManager em) {
        TarefaOrdemServico tarefaOrdemServico = new TarefaOrdemServico()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .notificarContador(UPDATED_NOTIFICAR_CONTADOR)
            .anoReferencia(UPDATED_ANO_REFERENCIA)
            .dataAdmin(UPDATED_DATA_ADMIN);
        // Add required entity
        ServicoContabilOrdemServico servicoContabilOrdemServico;
        if (TestUtil.findAll(em, ServicoContabilOrdemServico.class).isEmpty()) {
            servicoContabilOrdemServico = ServicoContabilOrdemServicoResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabilOrdemServico);
            em.flush();
        } else {
            servicoContabilOrdemServico = TestUtil.findAll(em, ServicoContabilOrdemServico.class).get(0);
        }
        tarefaOrdemServico.setServicoContabilOrdemServico(servicoContabilOrdemServico);
        return tarefaOrdemServico;
    }

    @BeforeEach
    public void initTest() {
        tarefaOrdemServico = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTarefaOrdemServico != null) {
            tarefaOrdemServicoRepository.delete(insertedTarefaOrdemServico);
            insertedTarefaOrdemServico = null;
        }
    }

    @Test
    @Transactional
    void createTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TarefaOrdemServico
        var returnedTarefaOrdemServico = om.readValue(
            restTarefaOrdemServicoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaOrdemServico)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TarefaOrdemServico.class
        );

        // Validate the TarefaOrdemServico in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTarefaOrdemServicoUpdatableFieldsEquals(
            returnedTarefaOrdemServico,
            getPersistedTarefaOrdemServico(returnedTarefaOrdemServico)
        );

        insertedTarefaOrdemServico = returnedTarefaOrdemServico;
    }

    @Test
    @Transactional
    void createTarefaOrdemServicoWithExistingId() throws Exception {
        // Create the TarefaOrdemServico with an existing ID
        tarefaOrdemServico.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarefaOrdemServicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaOrdemServico)))
            .andExpect(status().isBadRequest());

        // Validate the TarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTarefaOrdemServicos() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServico = tarefaOrdemServicoRepository.saveAndFlush(tarefaOrdemServico);

        // Get all the tarefaOrdemServicoList
        restTarefaOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefaOrdemServico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].notificarCliente").value(hasItem(DEFAULT_NOTIFICAR_CLIENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].notificarContador").value(hasItem(DEFAULT_NOTIFICAR_CONTADOR.booleanValue())))
            .andExpect(jsonPath("$.[*].anoReferencia").value(hasItem(DEFAULT_ANO_REFERENCIA)))
            .andExpect(jsonPath("$.[*].dataAdmin").value(hasItem(DEFAULT_DATA_ADMIN.toString())));
    }

    @Test
    @Transactional
    void getTarefaOrdemServico() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServico = tarefaOrdemServicoRepository.saveAndFlush(tarefaOrdemServico);

        // Get the tarefaOrdemServico
        restTarefaOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL_ID, tarefaOrdemServico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarefaOrdemServico.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.notificarCliente").value(DEFAULT_NOTIFICAR_CLIENTE.booleanValue()))
            .andExpect(jsonPath("$.notificarContador").value(DEFAULT_NOTIFICAR_CONTADOR.booleanValue()))
            .andExpect(jsonPath("$.anoReferencia").value(DEFAULT_ANO_REFERENCIA))
            .andExpect(jsonPath("$.dataAdmin").value(DEFAULT_DATA_ADMIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTarefaOrdemServico() throws Exception {
        // Get the tarefaOrdemServico
        restTarefaOrdemServicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarefaOrdemServico() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServico = tarefaOrdemServicoRepository.saveAndFlush(tarefaOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaOrdemServico
        TarefaOrdemServico updatedTarefaOrdemServico = tarefaOrdemServicoRepository.findById(tarefaOrdemServico.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTarefaOrdemServico are not directly saved in db
        em.detach(updatedTarefaOrdemServico);
        updatedTarefaOrdemServico
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .notificarContador(UPDATED_NOTIFICAR_CONTADOR)
            .anoReferencia(UPDATED_ANO_REFERENCIA)
            .dataAdmin(UPDATED_DATA_ADMIN);

        restTarefaOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTarefaOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTarefaOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the TarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTarefaOrdemServicoToMatchAllProperties(updatedTarefaOrdemServico);
    }

    @Test
    @Transactional
    void putNonExistingTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarefaOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaOrdemServico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarefaOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServico = tarefaOrdemServicoRepository.saveAndFlush(tarefaOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaOrdemServico using partial update
        TarefaOrdemServico partialUpdatedTarefaOrdemServico = new TarefaOrdemServico();
        partialUpdatedTarefaOrdemServico.setId(tarefaOrdemServico.getId());

        partialUpdatedTarefaOrdemServico
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .anoReferencia(UPDATED_ANO_REFERENCIA);

        restTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the TarefaOrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaOrdemServicoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTarefaOrdemServico, tarefaOrdemServico),
            getPersistedTarefaOrdemServico(tarefaOrdemServico)
        );
    }

    @Test
    @Transactional
    void fullUpdateTarefaOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServico = tarefaOrdemServicoRepository.saveAndFlush(tarefaOrdemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaOrdemServico using partial update
        TarefaOrdemServico partialUpdatedTarefaOrdemServico = new TarefaOrdemServico();
        partialUpdatedTarefaOrdemServico.setId(tarefaOrdemServico.getId());

        partialUpdatedTarefaOrdemServico
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .notificarContador(UPDATED_NOTIFICAR_CONTADOR)
            .anoReferencia(UPDATED_ANO_REFERENCIA)
            .dataAdmin(UPDATED_DATA_ADMIN);

        restTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the TarefaOrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaOrdemServicoUpdatableFieldsEquals(
            partialUpdatedTarefaOrdemServico,
            getPersistedTarefaOrdemServico(partialUpdatedTarefaOrdemServico)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarefaOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaOrdemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarefaOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaOrdemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaOrdemServicoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tarefaOrdemServico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaOrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarefaOrdemServico() throws Exception {
        // Initialize the database
        insertedTarefaOrdemServico = tarefaOrdemServicoRepository.saveAndFlush(tarefaOrdemServico);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tarefaOrdemServico
        restTarefaOrdemServicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarefaOrdemServico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tarefaOrdemServicoRepository.count();
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

    protected TarefaOrdemServico getPersistedTarefaOrdemServico(TarefaOrdemServico tarefaOrdemServico) {
        return tarefaOrdemServicoRepository.findById(tarefaOrdemServico.getId()).orElseThrow();
    }

    protected void assertPersistedTarefaOrdemServicoToMatchAllProperties(TarefaOrdemServico expectedTarefaOrdemServico) {
        assertTarefaOrdemServicoAllPropertiesEquals(expectedTarefaOrdemServico, getPersistedTarefaOrdemServico(expectedTarefaOrdemServico));
    }

    protected void assertPersistedTarefaOrdemServicoToMatchUpdatableProperties(TarefaOrdemServico expectedTarefaOrdemServico) {
        assertTarefaOrdemServicoAllUpdatablePropertiesEquals(
            expectedTarefaOrdemServico,
            getPersistedTarefaOrdemServico(expectedTarefaOrdemServico)
        );
    }
}
