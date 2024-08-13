package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AnexoRequeridoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AnexoRequerido;
import com.dobemcontabilidade.domain.enumeration.TipoAnexoRequeridoEnum;
import com.dobemcontabilidade.repository.AnexoRequeridoRepository;
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
 * Integration tests for the {@link AnexoRequeridoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnexoRequeridoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final TipoAnexoRequeridoEnum DEFAULT_TIPO = TipoAnexoRequeridoEnum.PJ;
    private static final TipoAnexoRequeridoEnum UPDATED_TIPO = TipoAnexoRequeridoEnum.PF;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/anexo-requeridos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnexoRequeridoRepository anexoRequeridoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnexoRequeridoMockMvc;

    private AnexoRequerido anexoRequerido;

    private AnexoRequerido insertedAnexoRequerido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequerido createEntity(EntityManager em) {
        AnexoRequerido anexoRequerido = new AnexoRequerido().nome(DEFAULT_NOME).tipo(DEFAULT_TIPO).descricao(DEFAULT_DESCRICAO);
        return anexoRequerido;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnexoRequerido createUpdatedEntity(EntityManager em) {
        AnexoRequerido anexoRequerido = new AnexoRequerido().nome(UPDATED_NOME).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);
        return anexoRequerido;
    }

    @BeforeEach
    public void initTest() {
        anexoRequerido = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnexoRequerido != null) {
            anexoRequeridoRepository.delete(insertedAnexoRequerido);
            insertedAnexoRequerido = null;
        }
    }

    @Test
    @Transactional
    void createAnexoRequerido() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AnexoRequerido
        var returnedAnexoRequerido = om.readValue(
            restAnexoRequeridoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequerido)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnexoRequerido.class
        );

        // Validate the AnexoRequerido in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAnexoRequeridoUpdatableFieldsEquals(returnedAnexoRequerido, getPersistedAnexoRequerido(returnedAnexoRequerido));

        insertedAnexoRequerido = returnedAnexoRequerido;
    }

    @Test
    @Transactional
    void createAnexoRequeridoWithExistingId() throws Exception {
        // Create the AnexoRequerido with an existing ID
        anexoRequerido.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnexoRequeridoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequerido)))
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequerido in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        anexoRequerido.setNome(null);

        // Create the AnexoRequerido, which fails.

        restAnexoRequeridoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequerido)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridos() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        // Get all the anexoRequeridoList
        restAnexoRequeridoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoRequerido.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getAnexoRequerido() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        // Get the anexoRequerido
        restAnexoRequeridoMockMvc
            .perform(get(ENTITY_API_URL_ID, anexoRequerido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anexoRequerido.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getAnexoRequeridosByIdFiltering() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        Long id = anexoRequerido.getId();

        defaultAnexoRequeridoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAnexoRequeridoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAnexoRequeridoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        // Get all the anexoRequeridoList where nome equals to
        defaultAnexoRequeridoFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        // Get all the anexoRequeridoList where nome in
        defaultAnexoRequeridoFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        // Get all the anexoRequeridoList where nome is not null
        defaultAnexoRequeridoFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllAnexoRequeridosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        // Get all the anexoRequeridoList where nome contains
        defaultAnexoRequeridoFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        // Get all the anexoRequeridoList where nome does not contain
        defaultAnexoRequeridoFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridosByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        // Get all the anexoRequeridoList where tipo equals to
        defaultAnexoRequeridoFiltering("tipo.equals=" + DEFAULT_TIPO, "tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridosByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        // Get all the anexoRequeridoList where tipo in
        defaultAnexoRequeridoFiltering("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO, "tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllAnexoRequeridosByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        // Get all the anexoRequeridoList where tipo is not null
        defaultAnexoRequeridoFiltering("tipo.specified=true", "tipo.specified=false");
    }

    private void defaultAnexoRequeridoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAnexoRequeridoShouldBeFound(shouldBeFound);
        defaultAnexoRequeridoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnexoRequeridoShouldBeFound(String filter) throws Exception {
        restAnexoRequeridoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anexoRequerido.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restAnexoRequeridoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnexoRequeridoShouldNotBeFound(String filter) throws Exception {
        restAnexoRequeridoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnexoRequeridoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAnexoRequerido() throws Exception {
        // Get the anexoRequerido
        restAnexoRequeridoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnexoRequerido() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequerido
        AnexoRequerido updatedAnexoRequerido = anexoRequeridoRepository.findById(anexoRequerido.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAnexoRequerido are not directly saved in db
        em.detach(updatedAnexoRequerido);
        updatedAnexoRequerido.nome(UPDATED_NOME).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);

        restAnexoRequeridoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAnexoRequerido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAnexoRequerido))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequerido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnexoRequeridoToMatchAllProperties(updatedAnexoRequerido);
    }

    @Test
    @Transactional
    void putNonExistingAnexoRequerido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequerido.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anexoRequerido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequerido))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequerido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnexoRequerido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequerido.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anexoRequerido))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequerido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnexoRequerido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequerido.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anexoRequerido)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequerido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnexoRequeridoWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequerido using partial update
        AnexoRequerido partialUpdatedAnexoRequerido = new AnexoRequerido();
        partialUpdatedAnexoRequerido.setId(anexoRequerido.getId());

        partialUpdatedAnexoRequerido.nome(UPDATED_NOME).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);

        restAnexoRequeridoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequerido.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequerido))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequerido in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAnexoRequerido, anexoRequerido),
            getPersistedAnexoRequerido(anexoRequerido)
        );
    }

    @Test
    @Transactional
    void fullUpdateAnexoRequeridoWithPatch() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anexoRequerido using partial update
        AnexoRequerido partialUpdatedAnexoRequerido = new AnexoRequerido();
        partialUpdatedAnexoRequerido.setId(anexoRequerido.getId());

        partialUpdatedAnexoRequerido.nome(UPDATED_NOME).tipo(UPDATED_TIPO).descricao(UPDATED_DESCRICAO);

        restAnexoRequeridoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnexoRequerido.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnexoRequerido))
            )
            .andExpect(status().isOk());

        // Validate the AnexoRequerido in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnexoRequeridoUpdatableFieldsEquals(partialUpdatedAnexoRequerido, getPersistedAnexoRequerido(partialUpdatedAnexoRequerido));
    }

    @Test
    @Transactional
    void patchNonExistingAnexoRequerido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequerido.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnexoRequeridoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anexoRequerido.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequerido))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequerido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnexoRequerido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequerido.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anexoRequerido))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnexoRequerido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnexoRequerido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anexoRequerido.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnexoRequeridoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anexoRequerido)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnexoRequerido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnexoRequerido() throws Exception {
        // Initialize the database
        insertedAnexoRequerido = anexoRequeridoRepository.saveAndFlush(anexoRequerido);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anexoRequerido
        restAnexoRequeridoMockMvc
            .perform(delete(ENTITY_API_URL_ID, anexoRequerido.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anexoRequeridoRepository.count();
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

    protected AnexoRequerido getPersistedAnexoRequerido(AnexoRequerido anexoRequerido) {
        return anexoRequeridoRepository.findById(anexoRequerido.getId()).orElseThrow();
    }

    protected void assertPersistedAnexoRequeridoToMatchAllProperties(AnexoRequerido expectedAnexoRequerido) {
        assertAnexoRequeridoAllPropertiesEquals(expectedAnexoRequerido, getPersistedAnexoRequerido(expectedAnexoRequerido));
    }

    protected void assertPersistedAnexoRequeridoToMatchUpdatableProperties(AnexoRequerido expectedAnexoRequerido) {
        assertAnexoRequeridoAllUpdatablePropertiesEquals(expectedAnexoRequerido, getPersistedAnexoRequerido(expectedAnexoRequerido));
    }
}
