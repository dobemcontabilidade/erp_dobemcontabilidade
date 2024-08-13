package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.GatewayPagamentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.GatewayPagamento;
import com.dobemcontabilidade.repository.GatewayPagamentoRepository;
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
 * Integration tests for the {@link GatewayPagamentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GatewayPagamentoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gateway-pagamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GatewayPagamentoRepository gatewayPagamentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGatewayPagamentoMockMvc;

    private GatewayPagamento gatewayPagamento;

    private GatewayPagamento insertedGatewayPagamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GatewayPagamento createEntity(EntityManager em) {
        GatewayPagamento gatewayPagamento = new GatewayPagamento().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        return gatewayPagamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GatewayPagamento createUpdatedEntity(EntityManager em) {
        GatewayPagamento gatewayPagamento = new GatewayPagamento().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        return gatewayPagamento;
    }

    @BeforeEach
    public void initTest() {
        gatewayPagamento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGatewayPagamento != null) {
            gatewayPagamentoRepository.delete(insertedGatewayPagamento);
            insertedGatewayPagamento = null;
        }
    }

    @Test
    @Transactional
    void createGatewayPagamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GatewayPagamento
        var returnedGatewayPagamento = om.readValue(
            restGatewayPagamentoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayPagamento)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GatewayPagamento.class
        );

        // Validate the GatewayPagamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGatewayPagamentoUpdatableFieldsEquals(returnedGatewayPagamento, getPersistedGatewayPagamento(returnedGatewayPagamento));

        insertedGatewayPagamento = returnedGatewayPagamento;
    }

    @Test
    @Transactional
    void createGatewayPagamentoWithExistingId() throws Exception {
        // Create the GatewayPagamento with an existing ID
        gatewayPagamento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGatewayPagamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayPagamento)))
            .andExpect(status().isBadRequest());

        // Validate the GatewayPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGatewayPagamentos() throws Exception {
        // Initialize the database
        insertedGatewayPagamento = gatewayPagamentoRepository.saveAndFlush(gatewayPagamento);

        // Get all the gatewayPagamentoList
        restGatewayPagamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gatewayPagamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @Test
    @Transactional
    void getGatewayPagamento() throws Exception {
        // Initialize the database
        insertedGatewayPagamento = gatewayPagamentoRepository.saveAndFlush(gatewayPagamento);

        // Get the gatewayPagamento
        restGatewayPagamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, gatewayPagamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gatewayPagamento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingGatewayPagamento() throws Exception {
        // Get the gatewayPagamento
        restGatewayPagamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGatewayPagamento() throws Exception {
        // Initialize the database
        insertedGatewayPagamento = gatewayPagamentoRepository.saveAndFlush(gatewayPagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gatewayPagamento
        GatewayPagamento updatedGatewayPagamento = gatewayPagamentoRepository.findById(gatewayPagamento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGatewayPagamento are not directly saved in db
        em.detach(updatedGatewayPagamento);
        updatedGatewayPagamento.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restGatewayPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGatewayPagamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGatewayPagamento))
            )
            .andExpect(status().isOk());

        // Validate the GatewayPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGatewayPagamentoToMatchAllProperties(updatedGatewayPagamento);
    }

    @Test
    @Transactional
    void putNonExistingGatewayPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayPagamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGatewayPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gatewayPagamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gatewayPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the GatewayPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGatewayPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayPagamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(gatewayPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the GatewayPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGatewayPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayPagamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(gatewayPagamento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GatewayPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGatewayPagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedGatewayPagamento = gatewayPagamentoRepository.saveAndFlush(gatewayPagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gatewayPagamento using partial update
        GatewayPagamento partialUpdatedGatewayPagamento = new GatewayPagamento();
        partialUpdatedGatewayPagamento.setId(gatewayPagamento.getId());

        partialUpdatedGatewayPagamento.nome(UPDATED_NOME);

        restGatewayPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGatewayPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGatewayPagamento))
            )
            .andExpect(status().isOk());

        // Validate the GatewayPagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGatewayPagamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGatewayPagamento, gatewayPagamento),
            getPersistedGatewayPagamento(gatewayPagamento)
        );
    }

    @Test
    @Transactional
    void fullUpdateGatewayPagamentoWithPatch() throws Exception {
        // Initialize the database
        insertedGatewayPagamento = gatewayPagamentoRepository.saveAndFlush(gatewayPagamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the gatewayPagamento using partial update
        GatewayPagamento partialUpdatedGatewayPagamento = new GatewayPagamento();
        partialUpdatedGatewayPagamento.setId(gatewayPagamento.getId());

        partialUpdatedGatewayPagamento.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restGatewayPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGatewayPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGatewayPagamento))
            )
            .andExpect(status().isOk());

        // Validate the GatewayPagamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGatewayPagamentoUpdatableFieldsEquals(
            partialUpdatedGatewayPagamento,
            getPersistedGatewayPagamento(partialUpdatedGatewayPagamento)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGatewayPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayPagamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGatewayPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gatewayPagamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gatewayPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the GatewayPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGatewayPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayPagamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(gatewayPagamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the GatewayPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGatewayPagamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        gatewayPagamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGatewayPagamentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(gatewayPagamento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GatewayPagamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGatewayPagamento() throws Exception {
        // Initialize the database
        insertedGatewayPagamento = gatewayPagamentoRepository.saveAndFlush(gatewayPagamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the gatewayPagamento
        restGatewayPagamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, gatewayPagamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return gatewayPagamentoRepository.count();
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

    protected GatewayPagamento getPersistedGatewayPagamento(GatewayPagamento gatewayPagamento) {
        return gatewayPagamentoRepository.findById(gatewayPagamento.getId()).orElseThrow();
    }

    protected void assertPersistedGatewayPagamentoToMatchAllProperties(GatewayPagamento expectedGatewayPagamento) {
        assertGatewayPagamentoAllPropertiesEquals(expectedGatewayPagamento, getPersistedGatewayPagamento(expectedGatewayPagamento));
    }

    protected void assertPersistedGatewayPagamentoToMatchUpdatableProperties(GatewayPagamento expectedGatewayPagamento) {
        assertGatewayPagamentoAllUpdatablePropertiesEquals(
            expectedGatewayPagamento,
            getPersistedGatewayPagamento(expectedGatewayPagamento)
        );
    }
}
