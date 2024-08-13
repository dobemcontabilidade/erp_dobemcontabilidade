package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EtapaFluxoExecucaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.EtapaFluxoExecucao;
import com.dobemcontabilidade.domain.OrdemServico;
import com.dobemcontabilidade.repository.EtapaFluxoExecucaoRepository;
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
 * Integration tests for the {@link EtapaFluxoExecucaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EtapaFluxoExecucaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FEITO = false;
    private static final Boolean UPDATED_FEITO = true;

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    private static final Boolean DEFAULT_AGENDADA = false;
    private static final Boolean UPDATED_AGENDADA = true;

    private static final String ENTITY_API_URL = "/api/etapa-fluxo-execucaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EtapaFluxoExecucaoRepository etapaFluxoExecucaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtapaFluxoExecucaoMockMvc;

    private EtapaFluxoExecucao etapaFluxoExecucao;

    private EtapaFluxoExecucao insertedEtapaFluxoExecucao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtapaFluxoExecucao createEntity(EntityManager em) {
        EtapaFluxoExecucao etapaFluxoExecucao = new EtapaFluxoExecucao()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .feito(DEFAULT_FEITO)
            .ordem(DEFAULT_ORDEM)
            .agendada(DEFAULT_AGENDADA);
        // Add required entity
        OrdemServico ordemServico;
        if (TestUtil.findAll(em, OrdemServico.class).isEmpty()) {
            ordemServico = OrdemServicoResourceIT.createEntity(em);
            em.persist(ordemServico);
            em.flush();
        } else {
            ordemServico = TestUtil.findAll(em, OrdemServico.class).get(0);
        }
        etapaFluxoExecucao.setOrdemServico(ordemServico);
        return etapaFluxoExecucao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtapaFluxoExecucao createUpdatedEntity(EntityManager em) {
        EtapaFluxoExecucao etapaFluxoExecucao = new EtapaFluxoExecucao()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .feito(UPDATED_FEITO)
            .ordem(UPDATED_ORDEM)
            .agendada(UPDATED_AGENDADA);
        // Add required entity
        OrdemServico ordemServico;
        if (TestUtil.findAll(em, OrdemServico.class).isEmpty()) {
            ordemServico = OrdemServicoResourceIT.createUpdatedEntity(em);
            em.persist(ordemServico);
            em.flush();
        } else {
            ordemServico = TestUtil.findAll(em, OrdemServico.class).get(0);
        }
        etapaFluxoExecucao.setOrdemServico(ordemServico);
        return etapaFluxoExecucao;
    }

    @BeforeEach
    public void initTest() {
        etapaFluxoExecucao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEtapaFluxoExecucao != null) {
            etapaFluxoExecucaoRepository.delete(insertedEtapaFluxoExecucao);
            insertedEtapaFluxoExecucao = null;
        }
    }

    @Test
    @Transactional
    void createEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EtapaFluxoExecucao
        var returnedEtapaFluxoExecucao = om.readValue(
            restEtapaFluxoExecucaoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etapaFluxoExecucao)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EtapaFluxoExecucao.class
        );

        // Validate the EtapaFluxoExecucao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEtapaFluxoExecucaoUpdatableFieldsEquals(
            returnedEtapaFluxoExecucao,
            getPersistedEtapaFluxoExecucao(returnedEtapaFluxoExecucao)
        );

        insertedEtapaFluxoExecucao = returnedEtapaFluxoExecucao;
    }

    @Test
    @Transactional
    void createEtapaFluxoExecucaoWithExistingId() throws Exception {
        // Create the EtapaFluxoExecucao with an existing ID
        etapaFluxoExecucao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapaFluxoExecucaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etapaFluxoExecucao)))
            .andExpect(status().isBadRequest());

        // Validate the EtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEtapaFluxoExecucaos() throws Exception {
        // Initialize the database
        insertedEtapaFluxoExecucao = etapaFluxoExecucaoRepository.saveAndFlush(etapaFluxoExecucao);

        // Get all the etapaFluxoExecucaoList
        restEtapaFluxoExecucaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapaFluxoExecucao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].feito").value(hasItem(DEFAULT_FEITO.booleanValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].agendada").value(hasItem(DEFAULT_AGENDADA.booleanValue())));
    }

    @Test
    @Transactional
    void getEtapaFluxoExecucao() throws Exception {
        // Initialize the database
        insertedEtapaFluxoExecucao = etapaFluxoExecucaoRepository.saveAndFlush(etapaFluxoExecucao);

        // Get the etapaFluxoExecucao
        restEtapaFluxoExecucaoMockMvc
            .perform(get(ENTITY_API_URL_ID, etapaFluxoExecucao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etapaFluxoExecucao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.feito").value(DEFAULT_FEITO.booleanValue()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.agendada").value(DEFAULT_AGENDADA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEtapaFluxoExecucao() throws Exception {
        // Get the etapaFluxoExecucao
        restEtapaFluxoExecucaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEtapaFluxoExecucao() throws Exception {
        // Initialize the database
        insertedEtapaFluxoExecucao = etapaFluxoExecucaoRepository.saveAndFlush(etapaFluxoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etapaFluxoExecucao
        EtapaFluxoExecucao updatedEtapaFluxoExecucao = etapaFluxoExecucaoRepository.findById(etapaFluxoExecucao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEtapaFluxoExecucao are not directly saved in db
        em.detach(updatedEtapaFluxoExecucao);
        updatedEtapaFluxoExecucao
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .feito(UPDATED_FEITO)
            .ordem(UPDATED_ORDEM)
            .agendada(UPDATED_AGENDADA);

        restEtapaFluxoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEtapaFluxoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEtapaFluxoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the EtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEtapaFluxoExecucaoToMatchAllProperties(updatedEtapaFluxoExecucao);
    }

    @Test
    @Transactional
    void putNonExistingEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapaFluxoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, etapaFluxoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(etapaFluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapaFluxoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(etapaFluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapaFluxoExecucaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(etapaFluxoExecucao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEtapaFluxoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedEtapaFluxoExecucao = etapaFluxoExecucaoRepository.saveAndFlush(etapaFluxoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etapaFluxoExecucao using partial update
        EtapaFluxoExecucao partialUpdatedEtapaFluxoExecucao = new EtapaFluxoExecucao();
        partialUpdatedEtapaFluxoExecucao.setId(etapaFluxoExecucao.getId());

        partialUpdatedEtapaFluxoExecucao.nome(UPDATED_NOME).feito(UPDATED_FEITO).agendada(UPDATED_AGENDADA);

        restEtapaFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtapaFluxoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEtapaFluxoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the EtapaFluxoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEtapaFluxoExecucaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEtapaFluxoExecucao, etapaFluxoExecucao),
            getPersistedEtapaFluxoExecucao(etapaFluxoExecucao)
        );
    }

    @Test
    @Transactional
    void fullUpdateEtapaFluxoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedEtapaFluxoExecucao = etapaFluxoExecucaoRepository.saveAndFlush(etapaFluxoExecucao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the etapaFluxoExecucao using partial update
        EtapaFluxoExecucao partialUpdatedEtapaFluxoExecucao = new EtapaFluxoExecucao();
        partialUpdatedEtapaFluxoExecucao.setId(etapaFluxoExecucao.getId());

        partialUpdatedEtapaFluxoExecucao
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .feito(UPDATED_FEITO)
            .ordem(UPDATED_ORDEM)
            .agendada(UPDATED_AGENDADA);

        restEtapaFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEtapaFluxoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEtapaFluxoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the EtapaFluxoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEtapaFluxoExecucaoUpdatableFieldsEquals(
            partialUpdatedEtapaFluxoExecucao,
            getPersistedEtapaFluxoExecucao(partialUpdatedEtapaFluxoExecucao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtapaFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, etapaFluxoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(etapaFluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapaFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(etapaFluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the EtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        etapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEtapaFluxoExecucaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(etapaFluxoExecucao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEtapaFluxoExecucao() throws Exception {
        // Initialize the database
        insertedEtapaFluxoExecucao = etapaFluxoExecucaoRepository.saveAndFlush(etapaFluxoExecucao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the etapaFluxoExecucao
        restEtapaFluxoExecucaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, etapaFluxoExecucao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return etapaFluxoExecucaoRepository.count();
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

    protected EtapaFluxoExecucao getPersistedEtapaFluxoExecucao(EtapaFluxoExecucao etapaFluxoExecucao) {
        return etapaFluxoExecucaoRepository.findById(etapaFluxoExecucao.getId()).orElseThrow();
    }

    protected void assertPersistedEtapaFluxoExecucaoToMatchAllProperties(EtapaFluxoExecucao expectedEtapaFluxoExecucao) {
        assertEtapaFluxoExecucaoAllPropertiesEquals(expectedEtapaFluxoExecucao, getPersistedEtapaFluxoExecucao(expectedEtapaFluxoExecucao));
    }

    protected void assertPersistedEtapaFluxoExecucaoToMatchUpdatableProperties(EtapaFluxoExecucao expectedEtapaFluxoExecucao) {
        assertEtapaFluxoExecucaoAllUpdatablePropertiesEquals(
            expectedEtapaFluxoExecucao,
            getPersistedEtapaFluxoExecucao(expectedEtapaFluxoExecucao)
        );
    }
}
