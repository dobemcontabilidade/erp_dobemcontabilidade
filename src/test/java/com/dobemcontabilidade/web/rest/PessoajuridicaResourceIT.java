package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PessoajuridicaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Pessoajuridica;
import com.dobemcontabilidade.repository.PessoajuridicaRepository;
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
 * Integration tests for the {@link PessoajuridicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PessoajuridicaResourceIT {

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_FANTASIA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_FANTASIA = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pessoajuridicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PessoajuridicaRepository pessoajuridicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoajuridicaMockMvc;

    private Pessoajuridica pessoajuridica;

    private Pessoajuridica insertedPessoajuridica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoajuridica createEntity(EntityManager em) {
        Pessoajuridica pessoajuridica = new Pessoajuridica()
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .nomeFantasia(DEFAULT_NOME_FANTASIA)
            .cnpj(DEFAULT_CNPJ);
        return pessoajuridica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoajuridica createUpdatedEntity(EntityManager em) {
        Pessoajuridica pessoajuridica = new Pessoajuridica()
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeFantasia(UPDATED_NOME_FANTASIA)
            .cnpj(UPDATED_CNPJ);
        return pessoajuridica;
    }

    @BeforeEach
    public void initTest() {
        pessoajuridica = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPessoajuridica != null) {
            pessoajuridicaRepository.delete(insertedPessoajuridica);
            insertedPessoajuridica = null;
        }
    }

    @Test
    @Transactional
    void createPessoajuridica() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pessoajuridica
        var returnedPessoajuridica = om.readValue(
            restPessoajuridicaMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoajuridica))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pessoajuridica.class
        );

        // Validate the Pessoajuridica in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPessoajuridicaUpdatableFieldsEquals(returnedPessoajuridica, getPersistedPessoajuridica(returnedPessoajuridica));

        insertedPessoajuridica = returnedPessoajuridica;
    }

    @Test
    @Transactional
    void createPessoajuridicaWithExistingId() throws Exception {
        // Create the Pessoajuridica with an existing ID
        pessoajuridica.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoajuridicaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoajuridica))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoajuridica in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRazaoSocialIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pessoajuridica.setRazaoSocial(null);

        // Create the Pessoajuridica, which fails.

        restPessoajuridicaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoajuridica))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNomeFantasiaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pessoajuridica.setNomeFantasia(null);

        // Create the Pessoajuridica, which fails.

        restPessoajuridicaMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoajuridica))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPessoajuridicas() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList
        restPessoajuridicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoajuridica.getId().intValue())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeFantasia").value(hasItem(DEFAULT_NOME_FANTASIA)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)));
    }

    @Test
    @Transactional
    void getPessoajuridica() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get the pessoajuridica
        restPessoajuridicaMockMvc
            .perform(get(ENTITY_API_URL_ID, pessoajuridica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoajuridica.getId().intValue()))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.nomeFantasia").value(DEFAULT_NOME_FANTASIA))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ));
    }

    @Test
    @Transactional
    void getPessoajuridicasByIdFiltering() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        Long id = pessoajuridica.getId();

        defaultPessoajuridicaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPessoajuridicaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPessoajuridicaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByRazaoSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where razaoSocial equals to
        defaultPessoajuridicaFiltering("razaoSocial.equals=" + DEFAULT_RAZAO_SOCIAL, "razaoSocial.equals=" + UPDATED_RAZAO_SOCIAL);
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByRazaoSocialIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where razaoSocial in
        defaultPessoajuridicaFiltering(
            "razaoSocial.in=" + DEFAULT_RAZAO_SOCIAL + "," + UPDATED_RAZAO_SOCIAL,
            "razaoSocial.in=" + UPDATED_RAZAO_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByRazaoSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where razaoSocial is not null
        defaultPessoajuridicaFiltering("razaoSocial.specified=true", "razaoSocial.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByRazaoSocialContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where razaoSocial contains
        defaultPessoajuridicaFiltering("razaoSocial.contains=" + DEFAULT_RAZAO_SOCIAL, "razaoSocial.contains=" + UPDATED_RAZAO_SOCIAL);
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByRazaoSocialNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where razaoSocial does not contain
        defaultPessoajuridicaFiltering(
            "razaoSocial.doesNotContain=" + UPDATED_RAZAO_SOCIAL,
            "razaoSocial.doesNotContain=" + DEFAULT_RAZAO_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByNomeFantasiaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where nomeFantasia equals to
        defaultPessoajuridicaFiltering("nomeFantasia.equals=" + DEFAULT_NOME_FANTASIA, "nomeFantasia.equals=" + UPDATED_NOME_FANTASIA);
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByNomeFantasiaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where nomeFantasia in
        defaultPessoajuridicaFiltering(
            "nomeFantasia.in=" + DEFAULT_NOME_FANTASIA + "," + UPDATED_NOME_FANTASIA,
            "nomeFantasia.in=" + UPDATED_NOME_FANTASIA
        );
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByNomeFantasiaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where nomeFantasia is not null
        defaultPessoajuridicaFiltering("nomeFantasia.specified=true", "nomeFantasia.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByNomeFantasiaContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where nomeFantasia contains
        defaultPessoajuridicaFiltering("nomeFantasia.contains=" + DEFAULT_NOME_FANTASIA, "nomeFantasia.contains=" + UPDATED_NOME_FANTASIA);
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByNomeFantasiaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where nomeFantasia does not contain
        defaultPessoajuridicaFiltering(
            "nomeFantasia.doesNotContain=" + UPDATED_NOME_FANTASIA,
            "nomeFantasia.doesNotContain=" + DEFAULT_NOME_FANTASIA
        );
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByCnpjIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where cnpj equals to
        defaultPessoajuridicaFiltering("cnpj.equals=" + DEFAULT_CNPJ, "cnpj.equals=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByCnpjIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where cnpj in
        defaultPessoajuridicaFiltering("cnpj.in=" + DEFAULT_CNPJ + "," + UPDATED_CNPJ, "cnpj.in=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByCnpjIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where cnpj is not null
        defaultPessoajuridicaFiltering("cnpj.specified=true", "cnpj.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByCnpjContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where cnpj contains
        defaultPessoajuridicaFiltering("cnpj.contains=" + DEFAULT_CNPJ, "cnpj.contains=" + UPDATED_CNPJ);
    }

    @Test
    @Transactional
    void getAllPessoajuridicasByCnpjNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        // Get all the pessoajuridicaList where cnpj does not contain
        defaultPessoajuridicaFiltering("cnpj.doesNotContain=" + UPDATED_CNPJ, "cnpj.doesNotContain=" + DEFAULT_CNPJ);
    }

    private void defaultPessoajuridicaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPessoajuridicaShouldBeFound(shouldBeFound);
        defaultPessoajuridicaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPessoajuridicaShouldBeFound(String filter) throws Exception {
        restPessoajuridicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoajuridica.getId().intValue())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeFantasia").value(hasItem(DEFAULT_NOME_FANTASIA)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)));

        // Check, that the count call also returns 1
        restPessoajuridicaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPessoajuridicaShouldNotBeFound(String filter) throws Exception {
        restPessoajuridicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPessoajuridicaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPessoajuridica() throws Exception {
        // Get the pessoajuridica
        restPessoajuridicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPessoajuridica() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pessoajuridica
        Pessoajuridica updatedPessoajuridica = pessoajuridicaRepository.findById(pessoajuridica.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPessoajuridica are not directly saved in db
        em.detach(updatedPessoajuridica);
        updatedPessoajuridica.razaoSocial(UPDATED_RAZAO_SOCIAL).nomeFantasia(UPDATED_NOME_FANTASIA).cnpj(UPDATED_CNPJ);

        restPessoajuridicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPessoajuridica.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPessoajuridica))
            )
            .andExpect(status().isOk());

        // Validate the Pessoajuridica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPessoajuridicaToMatchAllProperties(updatedPessoajuridica);
    }

    @Test
    @Transactional
    void putNonExistingPessoajuridica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoajuridica.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoajuridicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoajuridica.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pessoajuridica))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoajuridica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPessoajuridica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoajuridica.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoajuridicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pessoajuridica))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoajuridica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPessoajuridica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoajuridica.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoajuridicaMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoajuridica)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pessoajuridica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePessoajuridicaWithPatch() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pessoajuridica using partial update
        Pessoajuridica partialUpdatedPessoajuridica = new Pessoajuridica();
        partialUpdatedPessoajuridica.setId(pessoajuridica.getId());

        partialUpdatedPessoajuridica.cnpj(UPDATED_CNPJ);

        restPessoajuridicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoajuridica.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPessoajuridica))
            )
            .andExpect(status().isOk());

        // Validate the Pessoajuridica in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPessoajuridicaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPessoajuridica, pessoajuridica),
            getPersistedPessoajuridica(pessoajuridica)
        );
    }

    @Test
    @Transactional
    void fullUpdatePessoajuridicaWithPatch() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pessoajuridica using partial update
        Pessoajuridica partialUpdatedPessoajuridica = new Pessoajuridica();
        partialUpdatedPessoajuridica.setId(pessoajuridica.getId());

        partialUpdatedPessoajuridica.razaoSocial(UPDATED_RAZAO_SOCIAL).nomeFantasia(UPDATED_NOME_FANTASIA).cnpj(UPDATED_CNPJ);

        restPessoajuridicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoajuridica.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPessoajuridica))
            )
            .andExpect(status().isOk());

        // Validate the Pessoajuridica in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPessoajuridicaUpdatableFieldsEquals(partialUpdatedPessoajuridica, getPersistedPessoajuridica(partialUpdatedPessoajuridica));
    }

    @Test
    @Transactional
    void patchNonExistingPessoajuridica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoajuridica.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoajuridicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pessoajuridica.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pessoajuridica))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoajuridica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPessoajuridica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoajuridica.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoajuridicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pessoajuridica))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoajuridica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPessoajuridica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoajuridica.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoajuridicaMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pessoajuridica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pessoajuridica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePessoajuridica() throws Exception {
        // Initialize the database
        insertedPessoajuridica = pessoajuridicaRepository.saveAndFlush(pessoajuridica);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pessoajuridica
        restPessoajuridicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, pessoajuridica.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pessoajuridicaRepository.count();
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

    protected Pessoajuridica getPersistedPessoajuridica(Pessoajuridica pessoajuridica) {
        return pessoajuridicaRepository.findById(pessoajuridica.getId()).orElseThrow();
    }

    protected void assertPersistedPessoajuridicaToMatchAllProperties(Pessoajuridica expectedPessoajuridica) {
        assertPessoajuridicaAllPropertiesEquals(expectedPessoajuridica, getPersistedPessoajuridica(expectedPessoajuridica));
    }

    protected void assertPersistedPessoajuridicaToMatchUpdatableProperties(Pessoajuridica expectedPessoajuridica) {
        assertPessoajuridicaAllUpdatablePropertiesEquals(expectedPessoajuridica, getPersistedPessoajuridica(expectedPessoajuridica));
    }
}
