package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PerfilAcessoUsuarioAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PerfilAcessoUsuario;
import com.dobemcontabilidade.repository.PerfilAcessoUsuarioRepository;
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
 * Integration tests for the {@link PerfilAcessoUsuarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PerfilAcessoUsuarioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTORIZADO = false;
    private static final Boolean UPDATED_AUTORIZADO = true;

    private static final Instant DEFAULT_DATA_EXPIRACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EXPIRACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/perfil-acesso-usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PerfilAcessoUsuarioRepository perfilAcessoUsuarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfilAcessoUsuarioMockMvc;

    private PerfilAcessoUsuario perfilAcessoUsuario;

    private PerfilAcessoUsuario insertedPerfilAcessoUsuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilAcessoUsuario createEntity(EntityManager em) {
        PerfilAcessoUsuario perfilAcessoUsuario = new PerfilAcessoUsuario()
            .nome(DEFAULT_NOME)
            .autorizado(DEFAULT_AUTORIZADO)
            .dataExpiracao(DEFAULT_DATA_EXPIRACAO);
        return perfilAcessoUsuario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilAcessoUsuario createUpdatedEntity(EntityManager em) {
        PerfilAcessoUsuario perfilAcessoUsuario = new PerfilAcessoUsuario()
            .nome(UPDATED_NOME)
            .autorizado(UPDATED_AUTORIZADO)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO);
        return perfilAcessoUsuario;
    }

    @BeforeEach
    public void initTest() {
        perfilAcessoUsuario = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPerfilAcessoUsuario != null) {
            perfilAcessoUsuarioRepository.delete(insertedPerfilAcessoUsuario);
            insertedPerfilAcessoUsuario = null;
        }
    }

    @Test
    @Transactional
    void createPerfilAcessoUsuario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PerfilAcessoUsuario
        var returnedPerfilAcessoUsuario = om.readValue(
            restPerfilAcessoUsuarioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilAcessoUsuario)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PerfilAcessoUsuario.class
        );

        // Validate the PerfilAcessoUsuario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPerfilAcessoUsuarioUpdatableFieldsEquals(
            returnedPerfilAcessoUsuario,
            getPersistedPerfilAcessoUsuario(returnedPerfilAcessoUsuario)
        );

        insertedPerfilAcessoUsuario = returnedPerfilAcessoUsuario;
    }

    @Test
    @Transactional
    void createPerfilAcessoUsuarioWithExistingId() throws Exception {
        // Create the PerfilAcessoUsuario with an existing ID
        perfilAcessoUsuario.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilAcessoUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilAcessoUsuario)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilAcessoUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuarios() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList
        restPerfilAcessoUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilAcessoUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].autorizado").value(hasItem(DEFAULT_AUTORIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataExpiracao").value(hasItem(DEFAULT_DATA_EXPIRACAO.toString())));
    }

    @Test
    @Transactional
    void getPerfilAcessoUsuario() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get the perfilAcessoUsuario
        restPerfilAcessoUsuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, perfilAcessoUsuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfilAcessoUsuario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.autorizado").value(DEFAULT_AUTORIZADO.booleanValue()))
            .andExpect(jsonPath("$.dataExpiracao").value(DEFAULT_DATA_EXPIRACAO.toString()));
    }

    @Test
    @Transactional
    void getPerfilAcessoUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        Long id = perfilAcessoUsuario.getId();

        defaultPerfilAcessoUsuarioFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPerfilAcessoUsuarioFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPerfilAcessoUsuarioFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where nome equals to
        defaultPerfilAcessoUsuarioFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where nome in
        defaultPerfilAcessoUsuarioFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where nome is not null
        defaultPerfilAcessoUsuarioFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where nome contains
        defaultPerfilAcessoUsuarioFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where nome does not contain
        defaultPerfilAcessoUsuarioFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByAutorizadoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where autorizado equals to
        defaultPerfilAcessoUsuarioFiltering("autorizado.equals=" + DEFAULT_AUTORIZADO, "autorizado.equals=" + UPDATED_AUTORIZADO);
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByAutorizadoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where autorizado in
        defaultPerfilAcessoUsuarioFiltering(
            "autorizado.in=" + DEFAULT_AUTORIZADO + "," + UPDATED_AUTORIZADO,
            "autorizado.in=" + UPDATED_AUTORIZADO
        );
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByAutorizadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where autorizado is not null
        defaultPerfilAcessoUsuarioFiltering("autorizado.specified=true", "autorizado.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByDataExpiracaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where dataExpiracao equals to
        defaultPerfilAcessoUsuarioFiltering(
            "dataExpiracao.equals=" + DEFAULT_DATA_EXPIRACAO,
            "dataExpiracao.equals=" + UPDATED_DATA_EXPIRACAO
        );
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByDataExpiracaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where dataExpiracao in
        defaultPerfilAcessoUsuarioFiltering(
            "dataExpiracao.in=" + DEFAULT_DATA_EXPIRACAO + "," + UPDATED_DATA_EXPIRACAO,
            "dataExpiracao.in=" + UPDATED_DATA_EXPIRACAO
        );
    }

    @Test
    @Transactional
    void getAllPerfilAcessoUsuariosByDataExpiracaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        // Get all the perfilAcessoUsuarioList where dataExpiracao is not null
        defaultPerfilAcessoUsuarioFiltering("dataExpiracao.specified=true", "dataExpiracao.specified=false");
    }

    private void defaultPerfilAcessoUsuarioFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPerfilAcessoUsuarioShouldBeFound(shouldBeFound);
        defaultPerfilAcessoUsuarioShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerfilAcessoUsuarioShouldBeFound(String filter) throws Exception {
        restPerfilAcessoUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilAcessoUsuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].autorizado").value(hasItem(DEFAULT_AUTORIZADO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataExpiracao").value(hasItem(DEFAULT_DATA_EXPIRACAO.toString())));

        // Check, that the count call also returns 1
        restPerfilAcessoUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerfilAcessoUsuarioShouldNotBeFound(String filter) throws Exception {
        restPerfilAcessoUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerfilAcessoUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerfilAcessoUsuario() throws Exception {
        // Get the perfilAcessoUsuario
        restPerfilAcessoUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerfilAcessoUsuario() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilAcessoUsuario
        PerfilAcessoUsuario updatedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.findById(perfilAcessoUsuario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPerfilAcessoUsuario are not directly saved in db
        em.detach(updatedPerfilAcessoUsuario);
        updatedPerfilAcessoUsuario.nome(UPDATED_NOME).autorizado(UPDATED_AUTORIZADO).dataExpiracao(UPDATED_DATA_EXPIRACAO);

        restPerfilAcessoUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerfilAcessoUsuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPerfilAcessoUsuario))
            )
            .andExpect(status().isOk());

        // Validate the PerfilAcessoUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPerfilAcessoUsuarioToMatchAllProperties(updatedPerfilAcessoUsuario);
    }

    @Test
    @Transactional
    void putNonExistingPerfilAcessoUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcessoUsuario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilAcessoUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, perfilAcessoUsuario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilAcessoUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilAcessoUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerfilAcessoUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcessoUsuario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilAcessoUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilAcessoUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilAcessoUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerfilAcessoUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcessoUsuario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilAcessoUsuarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilAcessoUsuario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilAcessoUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerfilAcessoUsuarioWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilAcessoUsuario using partial update
        PerfilAcessoUsuario partialUpdatedPerfilAcessoUsuario = new PerfilAcessoUsuario();
        partialUpdatedPerfilAcessoUsuario.setId(perfilAcessoUsuario.getId());

        restPerfilAcessoUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilAcessoUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilAcessoUsuario))
            )
            .andExpect(status().isOk());

        // Validate the PerfilAcessoUsuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilAcessoUsuarioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPerfilAcessoUsuario, perfilAcessoUsuario),
            getPersistedPerfilAcessoUsuario(perfilAcessoUsuario)
        );
    }

    @Test
    @Transactional
    void fullUpdatePerfilAcessoUsuarioWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilAcessoUsuario using partial update
        PerfilAcessoUsuario partialUpdatedPerfilAcessoUsuario = new PerfilAcessoUsuario();
        partialUpdatedPerfilAcessoUsuario.setId(perfilAcessoUsuario.getId());

        partialUpdatedPerfilAcessoUsuario.nome(UPDATED_NOME).autorizado(UPDATED_AUTORIZADO).dataExpiracao(UPDATED_DATA_EXPIRACAO);

        restPerfilAcessoUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilAcessoUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilAcessoUsuario))
            )
            .andExpect(status().isOk());

        // Validate the PerfilAcessoUsuario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilAcessoUsuarioUpdatableFieldsEquals(
            partialUpdatedPerfilAcessoUsuario,
            getPersistedPerfilAcessoUsuario(partialUpdatedPerfilAcessoUsuario)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPerfilAcessoUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcessoUsuario.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilAcessoUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, perfilAcessoUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilAcessoUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilAcessoUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerfilAcessoUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcessoUsuario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilAcessoUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilAcessoUsuario))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilAcessoUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerfilAcessoUsuario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilAcessoUsuario.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilAcessoUsuarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(perfilAcessoUsuario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilAcessoUsuario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerfilAcessoUsuario() throws Exception {
        // Initialize the database
        insertedPerfilAcessoUsuario = perfilAcessoUsuarioRepository.saveAndFlush(perfilAcessoUsuario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the perfilAcessoUsuario
        restPerfilAcessoUsuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, perfilAcessoUsuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return perfilAcessoUsuarioRepository.count();
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

    protected PerfilAcessoUsuario getPersistedPerfilAcessoUsuario(PerfilAcessoUsuario perfilAcessoUsuario) {
        return perfilAcessoUsuarioRepository.findById(perfilAcessoUsuario.getId()).orElseThrow();
    }

    protected void assertPersistedPerfilAcessoUsuarioToMatchAllProperties(PerfilAcessoUsuario expectedPerfilAcessoUsuario) {
        assertPerfilAcessoUsuarioAllPropertiesEquals(
            expectedPerfilAcessoUsuario,
            getPersistedPerfilAcessoUsuario(expectedPerfilAcessoUsuario)
        );
    }

    protected void assertPersistedPerfilAcessoUsuarioToMatchUpdatableProperties(PerfilAcessoUsuario expectedPerfilAcessoUsuario) {
        assertPerfilAcessoUsuarioAllUpdatablePropertiesEquals(
            expectedPerfilAcessoUsuario,
            getPersistedPerfilAcessoUsuario(expectedPerfilAcessoUsuario)
        );
    }
}
