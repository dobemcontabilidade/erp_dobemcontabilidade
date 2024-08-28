package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TributacaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.domain.enumeration.SituacaoTributacaoEnum;
import com.dobemcontabilidade.repository.TributacaoRepository;
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
 * Integration tests for the {@link TributacaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TributacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final SituacaoTributacaoEnum DEFAULT_SITUACAO = SituacaoTributacaoEnum.ATIVO;
    private static final SituacaoTributacaoEnum UPDATED_SITUACAO = SituacaoTributacaoEnum.INATIVO;

    private static final String ENTITY_API_URL = "/api/tributacaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TributacaoRepository tributacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTributacaoMockMvc;

    private Tributacao tributacao;

    private Tributacao insertedTributacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tributacao createEntity(EntityManager em) {
        Tributacao tributacao = new Tributacao().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO).situacao(DEFAULT_SITUACAO);
        return tributacao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tributacao createUpdatedEntity(EntityManager em) {
        Tributacao tributacao = new Tributacao().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).situacao(UPDATED_SITUACAO);
        return tributacao;
    }

    @BeforeEach
    public void initTest() {
        tributacao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTributacao != null) {
            tributacaoRepository.delete(insertedTributacao);
            insertedTributacao = null;
        }
    }

    @Test
    @Transactional
    void createTributacao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tributacao
        var returnedTributacao = om.readValue(
            restTributacaoMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tributacao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Tributacao.class
        );

        // Validate the Tributacao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTributacaoUpdatableFieldsEquals(returnedTributacao, getPersistedTributacao(returnedTributacao));

        insertedTributacao = returnedTributacao;
    }

    @Test
    @Transactional
    void createTributacaoWithExistingId() throws Exception {
        // Create the Tributacao with an existing ID
        tributacao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTributacaoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tributacao)))
            .andExpect(status().isBadRequest());

        // Validate the Tributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTributacaos() throws Exception {
        // Initialize the database
        insertedTributacao = tributacaoRepository.saveAndFlush(tributacao);

        // Get all the tributacaoList
        restTributacaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tributacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @Test
    @Transactional
    void getTributacao() throws Exception {
        // Initialize the database
        insertedTributacao = tributacaoRepository.saveAndFlush(tributacao);

        // Get the tributacao
        restTributacaoMockMvc
            .perform(get(ENTITY_API_URL_ID, tributacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tributacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTributacao() throws Exception {
        // Get the tributacao
        restTributacaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTributacao() throws Exception {
        // Initialize the database
        insertedTributacao = tributacaoRepository.saveAndFlush(tributacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tributacao
        Tributacao updatedTributacao = tributacaoRepository.findById(tributacao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTributacao are not directly saved in db
        em.detach(updatedTributacao);
        updatedTributacao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).situacao(UPDATED_SITUACAO);

        restTributacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTributacao.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTributacao))
            )
            .andExpect(status().isOk());

        // Validate the Tributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTributacaoToMatchAllProperties(updatedTributacao);
    }

    @Test
    @Transactional
    void putNonExistingTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tributacao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTributacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tributacao.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tributacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tributacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTributacaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tributacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tributacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTributacaoMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tributacao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTributacaoWithPatch() throws Exception {
        // Initialize the database
        insertedTributacao = tributacaoRepository.saveAndFlush(tributacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tributacao using partial update
        Tributacao partialUpdatedTributacao = new Tributacao();
        partialUpdatedTributacao.setId(tributacao.getId());

        partialUpdatedTributacao.nome(UPDATED_NOME).situacao(UPDATED_SITUACAO);

        restTributacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTributacao.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTributacao))
            )
            .andExpect(status().isOk());

        // Validate the Tributacao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTributacaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTributacao, tributacao),
            getPersistedTributacao(tributacao)
        );
    }

    @Test
    @Transactional
    void fullUpdateTributacaoWithPatch() throws Exception {
        // Initialize the database
        insertedTributacao = tributacaoRepository.saveAndFlush(tributacao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tributacao using partial update
        Tributacao partialUpdatedTributacao = new Tributacao();
        partialUpdatedTributacao.setId(tributacao.getId());

        partialUpdatedTributacao.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO).situacao(UPDATED_SITUACAO);

        restTributacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTributacao.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTributacao))
            )
            .andExpect(status().isOk());

        // Validate the Tributacao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTributacaoUpdatableFieldsEquals(partialUpdatedTributacao, getPersistedTributacao(partialUpdatedTributacao));
    }

    @Test
    @Transactional
    void patchNonExistingTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tributacao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTributacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tributacao.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tributacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tributacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTributacaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tributacao))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTributacao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tributacao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTributacaoMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tributacao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tributacao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTributacao() throws Exception {
        // Initialize the database
        insertedTributacao = tributacaoRepository.saveAndFlush(tributacao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tributacao
        restTributacaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tributacao.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tributacaoRepository.count();
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

    protected Tributacao getPersistedTributacao(Tributacao tributacao) {
        return tributacaoRepository.findById(tributacao.getId()).orElseThrow();
    }

    protected void assertPersistedTributacaoToMatchAllProperties(Tributacao expectedTributacao) {
        assertTributacaoAllPropertiesEquals(expectedTributacao, getPersistedTributacao(expectedTributacao));
    }

    protected void assertPersistedTributacaoToMatchUpdatableProperties(Tributacao expectedTributacao) {
        assertTributacaoAllUpdatablePropertiesEquals(expectedTributacao, getPersistedTributacao(expectedTributacao));
    }
}
