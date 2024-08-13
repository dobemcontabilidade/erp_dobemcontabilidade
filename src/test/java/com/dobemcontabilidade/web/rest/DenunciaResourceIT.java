package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DenunciaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Denuncia;
import com.dobemcontabilidade.repository.DenunciaRepository;
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
 * Integration tests for the {@link DenunciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DenunciaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_MENSAGEM = "AAAAAAAAAA";
    private static final String UPDATED_MENSAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/denuncias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DenunciaRepository denunciaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDenunciaMockMvc;

    private Denuncia denuncia;

    private Denuncia insertedDenuncia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Denuncia createEntity(EntityManager em) {
        Denuncia denuncia = new Denuncia().titulo(DEFAULT_TITULO).mensagem(DEFAULT_MENSAGEM).descricao(DEFAULT_DESCRICAO);
        return denuncia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Denuncia createUpdatedEntity(EntityManager em) {
        Denuncia denuncia = new Denuncia().titulo(UPDATED_TITULO).mensagem(UPDATED_MENSAGEM).descricao(UPDATED_DESCRICAO);
        return denuncia;
    }

    @BeforeEach
    public void initTest() {
        denuncia = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDenuncia != null) {
            denunciaRepository.delete(insertedDenuncia);
            insertedDenuncia = null;
        }
    }

    @Test
    @Transactional
    void createDenuncia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Denuncia
        var returnedDenuncia = om.readValue(
            restDenunciaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(denuncia)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Denuncia.class
        );

        // Validate the Denuncia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDenunciaUpdatableFieldsEquals(returnedDenuncia, getPersistedDenuncia(returnedDenuncia));

        insertedDenuncia = returnedDenuncia;
    }

    @Test
    @Transactional
    void createDenunciaWithExistingId() throws Exception {
        // Create the Denuncia with an existing ID
        denuncia.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDenunciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(denuncia)))
            .andExpect(status().isBadRequest());

        // Validate the Denuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        denuncia.setTitulo(null);

        // Create the Denuncia, which fails.

        restDenunciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(denuncia)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMensagemIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        denuncia.setMensagem(null);

        // Create the Denuncia, which fails.

        restDenunciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(denuncia)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDenuncias() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList
        restDenunciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(denuncia.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].mensagem").value(hasItem(DEFAULT_MENSAGEM)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    void getDenuncia() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get the denuncia
        restDenunciaMockMvc
            .perform(get(ENTITY_API_URL_ID, denuncia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(denuncia.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.mensagem").value(DEFAULT_MENSAGEM))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    void getDenunciasByIdFiltering() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        Long id = denuncia.getId();

        defaultDenunciaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDenunciaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDenunciaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDenunciasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList where titulo equals to
        defaultDenunciaFiltering("titulo.equals=" + DEFAULT_TITULO, "titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllDenunciasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList where titulo in
        defaultDenunciaFiltering("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO, "titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllDenunciasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList where titulo is not null
        defaultDenunciaFiltering("titulo.specified=true", "titulo.specified=false");
    }

    @Test
    @Transactional
    void getAllDenunciasByTituloContainsSomething() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList where titulo contains
        defaultDenunciaFiltering("titulo.contains=" + DEFAULT_TITULO, "titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllDenunciasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList where titulo does not contain
        defaultDenunciaFiltering("titulo.doesNotContain=" + UPDATED_TITULO, "titulo.doesNotContain=" + DEFAULT_TITULO);
    }

    @Test
    @Transactional
    void getAllDenunciasByMensagemIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList where mensagem equals to
        defaultDenunciaFiltering("mensagem.equals=" + DEFAULT_MENSAGEM, "mensagem.equals=" + UPDATED_MENSAGEM);
    }

    @Test
    @Transactional
    void getAllDenunciasByMensagemIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList where mensagem in
        defaultDenunciaFiltering("mensagem.in=" + DEFAULT_MENSAGEM + "," + UPDATED_MENSAGEM, "mensagem.in=" + UPDATED_MENSAGEM);
    }

    @Test
    @Transactional
    void getAllDenunciasByMensagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList where mensagem is not null
        defaultDenunciaFiltering("mensagem.specified=true", "mensagem.specified=false");
    }

    @Test
    @Transactional
    void getAllDenunciasByMensagemContainsSomething() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList where mensagem contains
        defaultDenunciaFiltering("mensagem.contains=" + DEFAULT_MENSAGEM, "mensagem.contains=" + UPDATED_MENSAGEM);
    }

    @Test
    @Transactional
    void getAllDenunciasByMensagemNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        // Get all the denunciaList where mensagem does not contain
        defaultDenunciaFiltering("mensagem.doesNotContain=" + UPDATED_MENSAGEM, "mensagem.doesNotContain=" + DEFAULT_MENSAGEM);
    }

    private void defaultDenunciaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDenunciaShouldBeFound(shouldBeFound);
        defaultDenunciaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDenunciaShouldBeFound(String filter) throws Exception {
        restDenunciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(denuncia.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].mensagem").value(hasItem(DEFAULT_MENSAGEM)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));

        // Check, that the count call also returns 1
        restDenunciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDenunciaShouldNotBeFound(String filter) throws Exception {
        restDenunciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDenunciaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDenuncia() throws Exception {
        // Get the denuncia
        restDenunciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDenuncia() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the denuncia
        Denuncia updatedDenuncia = denunciaRepository.findById(denuncia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDenuncia are not directly saved in db
        em.detach(updatedDenuncia);
        updatedDenuncia.titulo(UPDATED_TITULO).mensagem(UPDATED_MENSAGEM).descricao(UPDATED_DESCRICAO);

        restDenunciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDenuncia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDenuncia))
            )
            .andExpect(status().isOk());

        // Validate the Denuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDenunciaToMatchAllProperties(updatedDenuncia);
    }

    @Test
    @Transactional
    void putNonExistingDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        denuncia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDenunciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, denuncia.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(denuncia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Denuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        denuncia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDenunciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(denuncia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Denuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        denuncia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDenunciaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(denuncia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Denuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDenunciaWithPatch() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the denuncia using partial update
        Denuncia partialUpdatedDenuncia = new Denuncia();
        partialUpdatedDenuncia.setId(denuncia.getId());

        partialUpdatedDenuncia.mensagem(UPDATED_MENSAGEM);

        restDenunciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDenuncia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDenuncia))
            )
            .andExpect(status().isOk());

        // Validate the Denuncia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDenunciaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDenuncia, denuncia), getPersistedDenuncia(denuncia));
    }

    @Test
    @Transactional
    void fullUpdateDenunciaWithPatch() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the denuncia using partial update
        Denuncia partialUpdatedDenuncia = new Denuncia();
        partialUpdatedDenuncia.setId(denuncia.getId());

        partialUpdatedDenuncia.titulo(UPDATED_TITULO).mensagem(UPDATED_MENSAGEM).descricao(UPDATED_DESCRICAO);

        restDenunciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDenuncia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDenuncia))
            )
            .andExpect(status().isOk());

        // Validate the Denuncia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDenunciaUpdatableFieldsEquals(partialUpdatedDenuncia, getPersistedDenuncia(partialUpdatedDenuncia));
    }

    @Test
    @Transactional
    void patchNonExistingDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        denuncia.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDenunciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, denuncia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(denuncia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Denuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        denuncia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDenunciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(denuncia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Denuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDenuncia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        denuncia.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDenunciaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(denuncia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Denuncia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDenuncia() throws Exception {
        // Initialize the database
        insertedDenuncia = denunciaRepository.saveAndFlush(denuncia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the denuncia
        restDenunciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, denuncia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return denunciaRepository.count();
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

    protected Denuncia getPersistedDenuncia(Denuncia denuncia) {
        return denunciaRepository.findById(denuncia.getId()).orElseThrow();
    }

    protected void assertPersistedDenunciaToMatchAllProperties(Denuncia expectedDenuncia) {
        assertDenunciaAllPropertiesEquals(expectedDenuncia, getPersistedDenuncia(expectedDenuncia));
    }

    protected void assertPersistedDenunciaToMatchUpdatableProperties(Denuncia expectedDenuncia) {
        assertDenunciaAllUpdatablePropertiesEquals(expectedDenuncia, getPersistedDenuncia(expectedDenuncia));
    }
}
