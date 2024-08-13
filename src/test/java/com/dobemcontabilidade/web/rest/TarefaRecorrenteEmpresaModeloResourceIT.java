package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TarefaRecorrenteEmpresaModeloAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.ServicoContabilEmpresaModelo;
import com.dobemcontabilidade.domain.TarefaRecorrenteEmpresaModelo;
import com.dobemcontabilidade.domain.enumeration.MesCompetenciaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoRecorrenciaEnum;
import com.dobemcontabilidade.repository.TarefaRecorrenteEmpresaModeloRepository;
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
 * Integration tests for the {@link TarefaRecorrenteEmpresaModeloResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TarefaRecorrenteEmpresaModeloResourceIT {

    private static final Integer DEFAULT_DIA_ADMIN = 1;
    private static final Integer UPDATED_DIA_ADMIN = 2;

    private static final MesCompetenciaEnum DEFAULT_MES_LEGAL = MesCompetenciaEnum.JANEIRO;
    private static final MesCompetenciaEnum UPDATED_MES_LEGAL = MesCompetenciaEnum.FEVEREIRO;

    private static final TipoRecorrenciaEnum DEFAULT_RECORRENCIA = TipoRecorrenciaEnum.DIARIO;
    private static final TipoRecorrenciaEnum UPDATED_RECORRENCIA = TipoRecorrenciaEnum.SEMANAL;

    private static final String ENTITY_API_URL = "/api/tarefa-recorrente-empresa-modelos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TarefaRecorrenteEmpresaModeloRepository tarefaRecorrenteEmpresaModeloRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarefaRecorrenteEmpresaModeloMockMvc;

    private TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo;

    private TarefaRecorrenteEmpresaModelo insertedTarefaRecorrenteEmpresaModelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaRecorrenteEmpresaModelo createEntity(EntityManager em) {
        TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo = new TarefaRecorrenteEmpresaModelo()
            .diaAdmin(DEFAULT_DIA_ADMIN)
            .mesLegal(DEFAULT_MES_LEGAL)
            .recorrencia(DEFAULT_RECORRENCIA);
        // Add required entity
        ServicoContabilEmpresaModelo servicoContabilEmpresaModelo;
        if (TestUtil.findAll(em, ServicoContabilEmpresaModelo.class).isEmpty()) {
            servicoContabilEmpresaModelo = ServicoContabilEmpresaModeloResourceIT.createEntity(em);
            em.persist(servicoContabilEmpresaModelo);
            em.flush();
        } else {
            servicoContabilEmpresaModelo = TestUtil.findAll(em, ServicoContabilEmpresaModelo.class).get(0);
        }
        tarefaRecorrenteEmpresaModelo.setServicoContabilEmpresaModelo(servicoContabilEmpresaModelo);
        return tarefaRecorrenteEmpresaModelo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaRecorrenteEmpresaModelo createUpdatedEntity(EntityManager em) {
        TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo = new TarefaRecorrenteEmpresaModelo()
            .diaAdmin(UPDATED_DIA_ADMIN)
            .mesLegal(UPDATED_MES_LEGAL)
            .recorrencia(UPDATED_RECORRENCIA);
        // Add required entity
        ServicoContabilEmpresaModelo servicoContabilEmpresaModelo;
        if (TestUtil.findAll(em, ServicoContabilEmpresaModelo.class).isEmpty()) {
            servicoContabilEmpresaModelo = ServicoContabilEmpresaModeloResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabilEmpresaModelo);
            em.flush();
        } else {
            servicoContabilEmpresaModelo = TestUtil.findAll(em, ServicoContabilEmpresaModelo.class).get(0);
        }
        tarefaRecorrenteEmpresaModelo.setServicoContabilEmpresaModelo(servicoContabilEmpresaModelo);
        return tarefaRecorrenteEmpresaModelo;
    }

    @BeforeEach
    public void initTest() {
        tarefaRecorrenteEmpresaModelo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTarefaRecorrenteEmpresaModelo != null) {
            tarefaRecorrenteEmpresaModeloRepository.delete(insertedTarefaRecorrenteEmpresaModelo);
            insertedTarefaRecorrenteEmpresaModelo = null;
        }
    }

    @Test
    @Transactional
    void createTarefaRecorrenteEmpresaModelo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TarefaRecorrenteEmpresaModelo
        var returnedTarefaRecorrenteEmpresaModelo = om.readValue(
            restTarefaRecorrenteEmpresaModeloMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(tarefaRecorrenteEmpresaModelo))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TarefaRecorrenteEmpresaModelo.class
        );

        // Validate the TarefaRecorrenteEmpresaModelo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTarefaRecorrenteEmpresaModeloUpdatableFieldsEquals(
            returnedTarefaRecorrenteEmpresaModelo,
            getPersistedTarefaRecorrenteEmpresaModelo(returnedTarefaRecorrenteEmpresaModelo)
        );

        insertedTarefaRecorrenteEmpresaModelo = returnedTarefaRecorrenteEmpresaModelo;
    }

    @Test
    @Transactional
    void createTarefaRecorrenteEmpresaModeloWithExistingId() throws Exception {
        // Create the TarefaRecorrenteEmpresaModelo with an existing ID
        tarefaRecorrenteEmpresaModelo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaRecorrenteEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrenteEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTarefaRecorrenteEmpresaModelos() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModeloRepository.saveAndFlush(tarefaRecorrenteEmpresaModelo);

        // Get all the tarefaRecorrenteEmpresaModeloList
        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefaRecorrenteEmpresaModelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].diaAdmin").value(hasItem(DEFAULT_DIA_ADMIN)))
            .andExpect(jsonPath("$.[*].mesLegal").value(hasItem(DEFAULT_MES_LEGAL.toString())))
            .andExpect(jsonPath("$.[*].recorrencia").value(hasItem(DEFAULT_RECORRENCIA.toString())));
    }

    @Test
    @Transactional
    void getTarefaRecorrenteEmpresaModelo() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModeloRepository.saveAndFlush(tarefaRecorrenteEmpresaModelo);

        // Get the tarefaRecorrenteEmpresaModelo
        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(get(ENTITY_API_URL_ID, tarefaRecorrenteEmpresaModelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarefaRecorrenteEmpresaModelo.getId().intValue()))
            .andExpect(jsonPath("$.diaAdmin").value(DEFAULT_DIA_ADMIN))
            .andExpect(jsonPath("$.mesLegal").value(DEFAULT_MES_LEGAL.toString()))
            .andExpect(jsonPath("$.recorrencia").value(DEFAULT_RECORRENCIA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTarefaRecorrenteEmpresaModelo() throws Exception {
        // Get the tarefaRecorrenteEmpresaModelo
        restTarefaRecorrenteEmpresaModeloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarefaRecorrenteEmpresaModelo() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModeloRepository.saveAndFlush(tarefaRecorrenteEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaRecorrenteEmpresaModelo
        TarefaRecorrenteEmpresaModelo updatedTarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModeloRepository
            .findById(tarefaRecorrenteEmpresaModelo.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTarefaRecorrenteEmpresaModelo are not directly saved in db
        em.detach(updatedTarefaRecorrenteEmpresaModelo);
        updatedTarefaRecorrenteEmpresaModelo.diaAdmin(UPDATED_DIA_ADMIN).mesLegal(UPDATED_MES_LEGAL).recorrencia(UPDATED_RECORRENCIA);

        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTarefaRecorrenteEmpresaModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTarefaRecorrenteEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the TarefaRecorrenteEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTarefaRecorrenteEmpresaModeloToMatchAllProperties(updatedTarefaRecorrenteEmpresaModelo);
    }

    @Test
    @Transactional
    void putNonExistingTarefaRecorrenteEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteEmpresaModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarefaRecorrenteEmpresaModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaRecorrenteEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrenteEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarefaRecorrenteEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaRecorrenteEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrenteEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarefaRecorrenteEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaRecorrenteEmpresaModelo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaRecorrenteEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarefaRecorrenteEmpresaModeloWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModeloRepository.saveAndFlush(tarefaRecorrenteEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaRecorrenteEmpresaModelo using partial update
        TarefaRecorrenteEmpresaModelo partialUpdatedTarefaRecorrenteEmpresaModelo = new TarefaRecorrenteEmpresaModelo();
        partialUpdatedTarefaRecorrenteEmpresaModelo.setId(tarefaRecorrenteEmpresaModelo.getId());

        partialUpdatedTarefaRecorrenteEmpresaModelo
            .diaAdmin(UPDATED_DIA_ADMIN)
            .mesLegal(UPDATED_MES_LEGAL)
            .recorrencia(UPDATED_RECORRENCIA);

        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaRecorrenteEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaRecorrenteEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the TarefaRecorrenteEmpresaModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaRecorrenteEmpresaModeloUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTarefaRecorrenteEmpresaModelo, tarefaRecorrenteEmpresaModelo),
            getPersistedTarefaRecorrenteEmpresaModelo(tarefaRecorrenteEmpresaModelo)
        );
    }

    @Test
    @Transactional
    void fullUpdateTarefaRecorrenteEmpresaModeloWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModeloRepository.saveAndFlush(tarefaRecorrenteEmpresaModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaRecorrenteEmpresaModelo using partial update
        TarefaRecorrenteEmpresaModelo partialUpdatedTarefaRecorrenteEmpresaModelo = new TarefaRecorrenteEmpresaModelo();
        partialUpdatedTarefaRecorrenteEmpresaModelo.setId(tarefaRecorrenteEmpresaModelo.getId());

        partialUpdatedTarefaRecorrenteEmpresaModelo
            .diaAdmin(UPDATED_DIA_ADMIN)
            .mesLegal(UPDATED_MES_LEGAL)
            .recorrencia(UPDATED_RECORRENCIA);

        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaRecorrenteEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaRecorrenteEmpresaModelo))
            )
            .andExpect(status().isOk());

        // Validate the TarefaRecorrenteEmpresaModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaRecorrenteEmpresaModeloUpdatableFieldsEquals(
            partialUpdatedTarefaRecorrenteEmpresaModelo,
            getPersistedTarefaRecorrenteEmpresaModelo(partialUpdatedTarefaRecorrenteEmpresaModelo)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTarefaRecorrenteEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteEmpresaModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarefaRecorrenteEmpresaModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaRecorrenteEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrenteEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarefaRecorrenteEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaRecorrenteEmpresaModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaRecorrenteEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarefaRecorrenteEmpresaModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaRecorrenteEmpresaModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaRecorrenteEmpresaModelo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaRecorrenteEmpresaModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarefaRecorrenteEmpresaModelo() throws Exception {
        // Initialize the database
        insertedTarefaRecorrenteEmpresaModelo = tarefaRecorrenteEmpresaModeloRepository.saveAndFlush(tarefaRecorrenteEmpresaModelo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tarefaRecorrenteEmpresaModelo
        restTarefaRecorrenteEmpresaModeloMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarefaRecorrenteEmpresaModelo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tarefaRecorrenteEmpresaModeloRepository.count();
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

    protected TarefaRecorrenteEmpresaModelo getPersistedTarefaRecorrenteEmpresaModelo(
        TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo
    ) {
        return tarefaRecorrenteEmpresaModeloRepository.findById(tarefaRecorrenteEmpresaModelo.getId()).orElseThrow();
    }

    protected void assertPersistedTarefaRecorrenteEmpresaModeloToMatchAllProperties(
        TarefaRecorrenteEmpresaModelo expectedTarefaRecorrenteEmpresaModelo
    ) {
        assertTarefaRecorrenteEmpresaModeloAllPropertiesEquals(
            expectedTarefaRecorrenteEmpresaModelo,
            getPersistedTarefaRecorrenteEmpresaModelo(expectedTarefaRecorrenteEmpresaModelo)
        );
    }

    protected void assertPersistedTarefaRecorrenteEmpresaModeloToMatchUpdatableProperties(
        TarefaRecorrenteEmpresaModelo expectedTarefaRecorrenteEmpresaModelo
    ) {
        assertTarefaRecorrenteEmpresaModeloAllUpdatablePropertiesEquals(
            expectedTarefaRecorrenteEmpresaModelo,
            getPersistedTarefaRecorrenteEmpresaModelo(expectedTarefaRecorrenteEmpresaModelo)
        );
    }
}
