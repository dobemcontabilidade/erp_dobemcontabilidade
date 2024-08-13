package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TermoDeAdesaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.TermoDeAdesao;
import com.dobemcontabilidade.repository.TermoDeAdesaoRepository;
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
 * Integration tests for the {@link TermoDeAdesaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TermoDeAdesaoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/termo-de-adesaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TermoDeAdesaoRepository termoDeAdesaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTermoDeAdesaoMockMvc;

    private TermoDeAdesao termoDeAdesao;

    private TermoDeAdesao insertedTermoDeAdesao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoDeAdesao createEntity(EntityManager em) {
        TermoDeAdesao termoDeAdesao = new TermoDeAdesao().titulo(DEFAULT_TITULO).descricao(DEFAULT_DESCRICAO);
        return termoDeAdesao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoDeAdesao createUpdatedEntity(EntityManager em) {
        TermoDeAdesao termoDeAdesao = new TermoDeAdesao().titulo(UPDATED_TITULO).descricao(UPDATED_DESCRICAO);
        return termoDeAdesao;
    }

    @BeforeEach
    public void initTest() {
        termoDeAdesao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTermoDeAdesao != null) {
            termoDeAdesaoRepository.delete(insertedTermoDeAdesao);
            insertedTermoDeAdesao = null;
        }
    }

    @Test
    @Transactional
    void createTermoDeAdesao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TermoDeAdesao
        var returnedTermoDeAdesao = om.readValue(
            restTermoDeAdesaoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoDeAdesao)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TermoDeAdesao.class
        );

        // Validate the TermoDeAdesao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTermoDeAdesaoUpdatableFieldsEquals(returnedTermoDeAdesao, getPersistedTermoDeAdesao(returnedTermoDeAdesao));

        insertedTermoDeAdesao = returnedTermoDeAdesao;
    }

    @Test
    @Transactional
    void createTermoDeAdesaoWithExistingId() throws Exception {
        // Create the TermoDeAdesao with an existing ID
        termoDeAdesao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermoDeAdesaoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoDeAdesao)))
            .andExpect(status().isBadRequest());

        // Validate the TermoDeAdesao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTermoDeAdesaos() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        // Get all the termoDeAdesaoList
        restTermoDeAdesaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termoDeAdesao.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getTermoDeAdesao() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        // Get the termoDeAdesao
        restTermoDeAdesaoMockMvc
            .perform(get(ENTITY_API_URL_ID, termoDeAdesao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(termoDeAdesao.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getTermoDeAdesaosByIdFiltering() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        Long id = termoDeAdesao.getId();

        defaultTermoDeAdesaoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTermoDeAdesaoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTermoDeAdesaoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTermoDeAdesaosByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        // Get all the termoDeAdesaoList where titulo equals to
        defaultTermoDeAdesaoFiltering("titulo.equals=" + DEFAULT_TITULO, "titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllTermoDeAdesaosByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        // Get all the termoDeAdesaoList where titulo in
        defaultTermoDeAdesaoFiltering("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO, "titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllTermoDeAdesaosByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        // Get all the termoDeAdesaoList where titulo is not null
        defaultTermoDeAdesaoFiltering("titulo.specified=true", "titulo.specified=false");
    }

    @Test
    @Transactional
    void getAllTermoDeAdesaosByTituloContainsSomething() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        // Get all the termoDeAdesaoList where titulo contains
        defaultTermoDeAdesaoFiltering("titulo.contains=" + DEFAULT_TITULO, "titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllTermoDeAdesaosByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        // Get all the termoDeAdesaoList where titulo does not contain
        defaultTermoDeAdesaoFiltering("titulo.doesNotContain=" + UPDATED_TITULO, "titulo.doesNotContain=" + DEFAULT_TITULO);
    }

    private void defaultTermoDeAdesaoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTermoDeAdesaoShouldBeFound(shouldBeFound);
        defaultTermoDeAdesaoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTermoDeAdesaoShouldBeFound(String filter) throws Exception {
        restTermoDeAdesaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termoDeAdesao.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restTermoDeAdesaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTermoDeAdesaoShouldNotBeFound(String filter) throws Exception {
        restTermoDeAdesaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTermoDeAdesaoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTermoDeAdesao() throws Exception {
        // Get the termoDeAdesao
        restTermoDeAdesaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTermoDeAdesao() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoDeAdesao
        TermoDeAdesao updatedTermoDeAdesao = termoDeAdesaoRepository.findById(termoDeAdesao.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTermoDeAdesao are not directly saved in db
        em.detach(updatedTermoDeAdesao);
        updatedTermoDeAdesao.titulo(UPDATED_TITULO).descricao(UPDATED_DESCRICAO);

        restTermoDeAdesaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTermoDeAdesao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTermoDeAdesao))
            )
            .andExpect(status().isOk());

        // Validate the TermoDeAdesao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTermoDeAdesaoToMatchAllProperties(updatedTermoDeAdesao);
    }

    @Test
    @Transactional
    void putNonExistingTermoDeAdesao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoDeAdesao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoDeAdesaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, termoDeAdesao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoDeAdesao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoDeAdesao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTermoDeAdesao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoDeAdesao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoDeAdesaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoDeAdesao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoDeAdesao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTermoDeAdesao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoDeAdesao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoDeAdesaoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoDeAdesao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoDeAdesao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTermoDeAdesaoWithPatch() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoDeAdesao using partial update
        TermoDeAdesao partialUpdatedTermoDeAdesao = new TermoDeAdesao();
        partialUpdatedTermoDeAdesao.setId(termoDeAdesao.getId());

        partialUpdatedTermoDeAdesao.descricao(UPDATED_DESCRICAO);

        restTermoDeAdesaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoDeAdesao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoDeAdesao))
            )
            .andExpect(status().isOk());

        // Validate the TermoDeAdesao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoDeAdesaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTermoDeAdesao, termoDeAdesao),
            getPersistedTermoDeAdesao(termoDeAdesao)
        );
    }

    @Test
    @Transactional
    void fullUpdateTermoDeAdesaoWithPatch() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoDeAdesao using partial update
        TermoDeAdesao partialUpdatedTermoDeAdesao = new TermoDeAdesao();
        partialUpdatedTermoDeAdesao.setId(termoDeAdesao.getId());

        partialUpdatedTermoDeAdesao.titulo(UPDATED_TITULO).descricao(UPDATED_DESCRICAO);

        restTermoDeAdesaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoDeAdesao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoDeAdesao))
            )
            .andExpect(status().isOk());

        // Validate the TermoDeAdesao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoDeAdesaoUpdatableFieldsEquals(partialUpdatedTermoDeAdesao, getPersistedTermoDeAdesao(partialUpdatedTermoDeAdesao));
    }

    @Test
    @Transactional
    void patchNonExistingTermoDeAdesao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoDeAdesao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoDeAdesaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, termoDeAdesao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoDeAdesao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoDeAdesao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTermoDeAdesao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoDeAdesao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoDeAdesaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoDeAdesao))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoDeAdesao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTermoDeAdesao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoDeAdesao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoDeAdesaoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(termoDeAdesao)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoDeAdesao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTermoDeAdesao() throws Exception {
        // Initialize the database
        insertedTermoDeAdesao = termoDeAdesaoRepository.saveAndFlush(termoDeAdesao);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the termoDeAdesao
        restTermoDeAdesaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, termoDeAdesao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return termoDeAdesaoRepository.count();
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

    protected TermoDeAdesao getPersistedTermoDeAdesao(TermoDeAdesao termoDeAdesao) {
        return termoDeAdesaoRepository.findById(termoDeAdesao.getId()).orElseThrow();
    }

    protected void assertPersistedTermoDeAdesaoToMatchAllProperties(TermoDeAdesao expectedTermoDeAdesao) {
        assertTermoDeAdesaoAllPropertiesEquals(expectedTermoDeAdesao, getPersistedTermoDeAdesao(expectedTermoDeAdesao));
    }

    protected void assertPersistedTermoDeAdesaoToMatchUpdatableProperties(TermoDeAdesao expectedTermoDeAdesao) {
        assertTermoDeAdesaoAllUpdatablePropertiesEquals(expectedTermoDeAdesao, getPersistedTermoDeAdesao(expectedTermoDeAdesao));
    }
}
