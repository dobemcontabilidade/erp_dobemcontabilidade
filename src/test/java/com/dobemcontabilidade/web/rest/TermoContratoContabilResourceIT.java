package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TermoContratoContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.TermoContratoContabil;
import com.dobemcontabilidade.repository.TermoContratoContabilRepository;
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
 * Integration tests for the {@link TermoContratoContabilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TermoContratoContabilResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_URL_DOCUMENTO_FONTE = "AAAAAAAAAA";
    private static final String UPDATED_URL_DOCUMENTO_FONTE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIVEL = false;
    private static final Boolean UPDATED_DISPONIVEL = true;

    private static final Instant DEFAULT_DATA_CRIACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CRIACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/termo-contrato-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TermoContratoContabilRepository termoContratoContabilRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTermoContratoContabilMockMvc;

    private TermoContratoContabil termoContratoContabil;

    private TermoContratoContabil insertedTermoContratoContabil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoContratoContabil createEntity(EntityManager em) {
        TermoContratoContabil termoContratoContabil = new TermoContratoContabil()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .urlDocumentoFonte(DEFAULT_URL_DOCUMENTO_FONTE)
            .documento(DEFAULT_DOCUMENTO)
            .disponivel(DEFAULT_DISPONIVEL)
            .dataCriacao(DEFAULT_DATA_CRIACAO);
        return termoContratoContabil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoContratoContabil createUpdatedEntity(EntityManager em) {
        TermoContratoContabil termoContratoContabil = new TermoContratoContabil()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .urlDocumentoFonte(UPDATED_URL_DOCUMENTO_FONTE)
            .documento(UPDATED_DOCUMENTO)
            .disponivel(UPDATED_DISPONIVEL)
            .dataCriacao(UPDATED_DATA_CRIACAO);
        return termoContratoContabil;
    }

    @BeforeEach
    public void initTest() {
        termoContratoContabil = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTermoContratoContabil != null) {
            termoContratoContabilRepository.delete(insertedTermoContratoContabil);
            insertedTermoContratoContabil = null;
        }
    }

    @Test
    @Transactional
    void createTermoContratoContabil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TermoContratoContabil
        var returnedTermoContratoContabil = om.readValue(
            restTermoContratoContabilMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(termoContratoContabil))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TermoContratoContabil.class
        );

        // Validate the TermoContratoContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTermoContratoContabilUpdatableFieldsEquals(
            returnedTermoContratoContabil,
            getPersistedTermoContratoContabil(returnedTermoContratoContabil)
        );

        insertedTermoContratoContabil = returnedTermoContratoContabil;
    }

    @Test
    @Transactional
    void createTermoContratoContabilWithExistingId() throws Exception {
        // Create the TermoContratoContabil with an existing ID
        termoContratoContabil.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermoContratoContabilMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTermoContratoContabils() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList
        restTermoContratoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termoContratoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].urlDocumentoFonte").value(hasItem(DEFAULT_URL_DOCUMENTO_FONTE.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].disponivel").value(hasItem(DEFAULT_DISPONIVEL.booleanValue())))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())));
    }

    @Test
    @Transactional
    void getTermoContratoContabil() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get the termoContratoContabil
        restTermoContratoContabilMockMvc
            .perform(get(ENTITY_API_URL_ID, termoContratoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(termoContratoContabil.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.urlDocumentoFonte").value(DEFAULT_URL_DOCUMENTO_FONTE.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.disponivel").value(DEFAULT_DISPONIVEL.booleanValue()))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTermoContratoContabil() throws Exception {
        // Get the termoContratoContabil
        restTermoContratoContabilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTermoContratoContabil() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoContratoContabil
        TermoContratoContabil updatedTermoContratoContabil = termoContratoContabilRepository
            .findById(termoContratoContabil.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTermoContratoContabil are not directly saved in db
        em.detach(updatedTermoContratoContabil);
        updatedTermoContratoContabil
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .urlDocumentoFonte(UPDATED_URL_DOCUMENTO_FONTE)
            .documento(UPDATED_DOCUMENTO)
            .disponivel(UPDATED_DISPONIVEL)
            .dataCriacao(UPDATED_DATA_CRIACAO);

        restTermoContratoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTermoContratoContabil.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTermoContratoContabil))
            )
            .andExpect(status().isOk());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTermoContratoContabilToMatchAllProperties(updatedTermoContratoContabil);
    }

    @Test
    @Transactional
    void putNonExistingTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, termoContratoContabil.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTermoContratoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoContratoContabil using partial update
        TermoContratoContabil partialUpdatedTermoContratoContabil = new TermoContratoContabil();
        partialUpdatedTermoContratoContabil.setId(termoContratoContabil.getId());

        partialUpdatedTermoContratoContabil.titulo(UPDATED_TITULO).descricao(UPDATED_DESCRICAO).dataCriacao(UPDATED_DATA_CRIACAO);

        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoContratoContabil.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoContratoContabil))
            )
            .andExpect(status().isOk());

        // Validate the TermoContratoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoContratoContabilUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTermoContratoContabil, termoContratoContabil),
            getPersistedTermoContratoContabil(termoContratoContabil)
        );
    }

    @Test
    @Transactional
    void fullUpdateTermoContratoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoContratoContabil using partial update
        TermoContratoContabil partialUpdatedTermoContratoContabil = new TermoContratoContabil();
        partialUpdatedTermoContratoContabil.setId(termoContratoContabil.getId());

        partialUpdatedTermoContratoContabil
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .urlDocumentoFonte(UPDATED_URL_DOCUMENTO_FONTE)
            .documento(UPDATED_DOCUMENTO)
            .disponivel(UPDATED_DISPONIVEL)
            .dataCriacao(UPDATED_DATA_CRIACAO);

        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoContratoContabil.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoContratoContabil))
            )
            .andExpect(status().isOk());

        // Validate the TermoContratoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoContratoContabilUpdatableFieldsEquals(
            partialUpdatedTermoContratoContabil,
            getPersistedTermoContratoContabil(partialUpdatedTermoContratoContabil)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, termoContratoContabil.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTermoContratoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoContratoContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoContratoContabil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoContratoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTermoContratoContabil() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the termoContratoContabil
        restTermoContratoContabilMockMvc
            .perform(delete(ENTITY_API_URL_ID, termoContratoContabil.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return termoContratoContabilRepository.count();
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

    protected TermoContratoContabil getPersistedTermoContratoContabil(TermoContratoContabil termoContratoContabil) {
        return termoContratoContabilRepository.findById(termoContratoContabil.getId()).orElseThrow();
    }

    protected void assertPersistedTermoContratoContabilToMatchAllProperties(TermoContratoContabil expectedTermoContratoContabil) {
        assertTermoContratoContabilAllPropertiesEquals(
            expectedTermoContratoContabil,
            getPersistedTermoContratoContabil(expectedTermoContratoContabil)
        );
    }

    protected void assertPersistedTermoContratoContabilToMatchUpdatableProperties(TermoContratoContabil expectedTermoContratoContabil) {
        assertTermoContratoContabilAllUpdatablePropertiesEquals(
            expectedTermoContratoContabil,
            getPersistedTermoContratoContabil(expectedTermoContratoContabil)
        );
    }
}
