package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TarefaRecorrenteAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa;
import com.dobemcontabilidade.domain.TarefaRecorrente;
import com.dobemcontabilidade.domain.enumeration.TipoRecorrenciaEnum;
import com.dobemcontabilidade.repository.TarefaRecorrenteRepository;
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
 * Integration tests for the {@link TarefaRecorrenteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TarefaRecorrenteResourceIT {

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

    private static final TipoRecorrenciaEnum DEFAULT_RECORENCIA = TipoRecorrenciaEnum.DIARIO;
    private static final TipoRecorrenciaEnum UPDATED_RECORENCIA = TipoRecorrenciaEnum.SEMANAL;

    private static final String ENTITY_API_URL = "/api/tarefa-recorrentes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TarefaRecorrenteRepository tarefaRecorrenteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarefaRecorrenteMockMvc;

    private TarefaRecorrente tarefaRecorrente;

    private TarefaRecorrente insertedTarefaRecorrente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaRecorrente createEntity(EntityManager em) {
        TarefaRecorrente tarefaRecorrente = new TarefaRecorrente()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .notificarCliente(DEFAULT_NOTIFICAR_CLIENTE)
            .notificarContador(DEFAULT_NOTIFICAR_CONTADOR)
            .anoReferencia(DEFAULT_ANO_REFERENCIA)
            .dataAdmin(DEFAULT_DATA_ADMIN)
            .recorencia(DEFAULT_RECORENCIA);
        // Add required entity
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa;
        if (TestUtil.findAll(em, ServicoContabilAssinaturaEmpresa.class).isEmpty()) {
            servicoContabilAssinaturaEmpresa = ServicoContabilAssinaturaEmpresaResourceIT.createEntity(em);
            em.persist(servicoContabilAssinaturaEmpresa);
            em.flush();
        } else {
            servicoContabilAssinaturaEmpresa = TestUtil.findAll(em, ServicoContabilAssinaturaEmpresa.class).get(0);
        }
        tarefaRecorrente.setServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresa);
        return tarefaRecorrente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaRecorrente createUpdatedEntity(EntityManager em) {
        TarefaRecorrente tarefaRecorrente = new TarefaRecorrente()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .notificarContador(UPDATED_NOTIFICAR_CONTADOR)
            .anoReferencia(UPDATED_ANO_REFERENCIA)
            .dataAdmin(UPDATED_DATA_ADMIN)
            .recorencia(UPDATED_RECORENCIA);
        // Add required entity
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa;
        if (TestUtil.findAll(em, ServicoContabilAssinaturaEmpresa.class).isEmpty()) {
            servicoContabilAssinaturaEmpresa = ServicoContabilAssinaturaEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabilAssinaturaEmpresa);
            em.flush();
        } else {
            servicoContabilAssinaturaEmpresa = TestUtil.findAll(em, ServicoContabilAssinaturaEmpresa.class).get(0);
        }
        tarefaRecorrente.setServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresa);
        return tarefaRecorrente;
    }

    @BeforeEach
    public void initTest() {
        tarefaRecorrente = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTarefaRecorrente != null) {
            tarefaRecorrenteRepository.delete(insertedTarefaRecorrente);
            insertedTarefaRecorrente = null;
        }
    }

    @Test
    @Transactional
    void createTarefaRecorrente() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TarefaRecorrente
        var returnedTarefaRecorrente = om.readValue(
            restTarefaRecorrenteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaRecorrente)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TarefaRecorrente.class
        );

        // Validate the TarefaRecorrente in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTarefaRecorrenteUpdatableFieldsEquals(returnedTarefaRecorrente, getPersistedTarefaRecorrente(returnedTarefaRecorrente));

        insertedTarefaRecorrente = returnedTarefaRecorrente;
    }

    @Test
    @Transactional
    void createTarefaRecorrenteWithExistingId() throws Exception {
        // Create the TarefaRecorrente with an existing ID
        tarefaRecorrente.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarefaRecorrenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaRecorrente)))
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTarefaRecorrentes() throws Exception {
        // Initialize the database
        insertedTarefaRecorrente = tarefaRecorrenteRepository.saveAndFlush(tarefaRecorrente);

        // Get all the tarefaRecorrenteList
        restTarefaRecorrenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefaRecorrente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].notificarCliente").value(hasItem(DEFAULT_NOTIFICAR_CLIENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].notificarContador").value(hasItem(DEFAULT_NOTIFICAR_CONTADOR.booleanValue())))
            .andExpect(jsonPath("$.[*].anoReferencia").value(hasItem(DEFAULT_ANO_REFERENCIA)))
            .andExpect(jsonPath("$.[*].dataAdmin").value(hasItem(DEFAULT_DATA_ADMIN.toString())))
            .andExpect(jsonPath("$.[*].recorencia").value(hasItem(DEFAULT_RECORENCIA.toString())));
    }

    @Test
    @Transactional
    void getTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedTarefaRecorrente = tarefaRecorrenteRepository.saveAndFlush(tarefaRecorrente);

        // Get the tarefaRecorrente
        restTarefaRecorrenteMockMvc
            .perform(get(ENTITY_API_URL_ID, tarefaRecorrente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarefaRecorrente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.notificarCliente").value(DEFAULT_NOTIFICAR_CLIENTE.booleanValue()))
            .andExpect(jsonPath("$.notificarContador").value(DEFAULT_NOTIFICAR_CONTADOR.booleanValue()))
            .andExpect(jsonPath("$.anoReferencia").value(DEFAULT_ANO_REFERENCIA))
            .andExpect(jsonPath("$.dataAdmin").value(DEFAULT_DATA_ADMIN.toString()))
            .andExpect(jsonPath("$.recorencia").value(DEFAULT_RECORENCIA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTarefaRecorrente() throws Exception {
        // Get the tarefaRecorrente
        restTarefaRecorrenteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedTarefaRecorrente = tarefaRecorrenteRepository.saveAndFlush(tarefaRecorrente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaRecorrente
        TarefaRecorrente updatedTarefaRecorrente = tarefaRecorrenteRepository.findById(tarefaRecorrente.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTarefaRecorrente are not directly saved in db
        em.detach(updatedTarefaRecorrente);
        updatedTarefaRecorrente
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .notificarContador(UPDATED_NOTIFICAR_CONTADOR)
            .anoReferencia(UPDATED_ANO_REFERENCIA)
            .dataAdmin(UPDATED_DATA_ADMIN)
            .recorencia(UPDATED_RECORENCIA);

        restTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTarefaRecorrente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the TarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTarefaRecorrenteToMatchAllProperties(updatedTarefaRecorrente);
    }

    @Test
    @Transactional
    void putNonExistingTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarefaRecorrente.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaRecorrente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarefaRecorrenteWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaRecorrente = tarefaRecorrenteRepository.saveAndFlush(tarefaRecorrente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaRecorrente using partial update
        TarefaRecorrente partialUpdatedTarefaRecorrente = new TarefaRecorrente();
        partialUpdatedTarefaRecorrente.setId(tarefaRecorrente.getId());

        partialUpdatedTarefaRecorrente.descricao(UPDATED_DESCRICAO).notificarContador(UPDATED_NOTIFICAR_CONTADOR);

        restTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the TarefaRecorrente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaRecorrenteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTarefaRecorrente, tarefaRecorrente),
            getPersistedTarefaRecorrente(tarefaRecorrente)
        );
    }

    @Test
    @Transactional
    void fullUpdateTarefaRecorrenteWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaRecorrente = tarefaRecorrenteRepository.saveAndFlush(tarefaRecorrente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaRecorrente using partial update
        TarefaRecorrente partialUpdatedTarefaRecorrente = new TarefaRecorrente();
        partialUpdatedTarefaRecorrente.setId(tarefaRecorrente.getId());

        partialUpdatedTarefaRecorrente
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .notificarContador(UPDATED_NOTIFICAR_CONTADOR)
            .anoReferencia(UPDATED_ANO_REFERENCIA)
            .dataAdmin(UPDATED_DATA_ADMIN)
            .recorencia(UPDATED_RECORENCIA);

        restTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaRecorrente))
            )
            .andExpect(status().isOk());

        // Validate the TarefaRecorrente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaRecorrenteUpdatableFieldsEquals(
            partialUpdatedTarefaRecorrente,
            getPersistedTarefaRecorrente(partialUpdatedTarefaRecorrente)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrente.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarefaRecorrente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaRecorrente))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarefaRecorrente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrente.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tarefaRecorrente)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaRecorrente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarefaRecorrente() throws Exception {
        // Initialize the database
        insertedTarefaRecorrente = tarefaRecorrenteRepository.saveAndFlush(tarefaRecorrente);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tarefaRecorrente
        restTarefaRecorrenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarefaRecorrente.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tarefaRecorrenteRepository.count();
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

    protected TarefaRecorrente getPersistedTarefaRecorrente(TarefaRecorrente tarefaRecorrente) {
        return tarefaRecorrenteRepository.findById(tarefaRecorrente.getId()).orElseThrow();
    }

    protected void assertPersistedTarefaRecorrenteToMatchAllProperties(TarefaRecorrente expectedTarefaRecorrente) {
        assertTarefaRecorrenteAllPropertiesEquals(expectedTarefaRecorrente, getPersistedTarefaRecorrente(expectedTarefaRecorrente));
    }

    protected void assertPersistedTarefaRecorrenteToMatchUpdatableProperties(TarefaRecorrente expectedTarefaRecorrente) {
        assertTarefaRecorrenteAllUpdatablePropertiesEquals(
            expectedTarefaRecorrente,
            getPersistedTarefaRecorrente(expectedTarefaRecorrente)
        );
    }
}
