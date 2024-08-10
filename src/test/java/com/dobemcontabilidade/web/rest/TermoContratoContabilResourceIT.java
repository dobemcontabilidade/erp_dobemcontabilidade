package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TermoContratoContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.TermoContratoContabil;
import com.dobemcontabilidade.repository.TermoContratoContabilRepository;
import com.dobemcontabilidade.service.TermoContratoContabilService;
import com.dobemcontabilidade.service.dto.TermoContratoContabilDTO;
import com.dobemcontabilidade.service.mapper.TermoContratoContabilMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TermoContratoContabilResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TermoContratoContabilResourceIT {

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/termo-contrato-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TermoContratoContabilRepository termoContratoContabilRepository;

    @Mock
    private TermoContratoContabilRepository termoContratoContabilRepositoryMock;

    @Autowired
    private TermoContratoContabilMapper termoContratoContabilMapper;

    @Mock
    private TermoContratoContabilService termoContratoContabilServiceMock;

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
            .documento(DEFAULT_DOCUMENTO)
            .descricao(DEFAULT_DESCRICAO)
            .titulo(DEFAULT_TITULO);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        termoContratoContabil.setPlanoContabil(planoContabil);
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
            .documento(UPDATED_DOCUMENTO)
            .descricao(UPDATED_DESCRICAO)
            .titulo(UPDATED_TITULO);
        // Add required entity
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            planoContabil = PlanoContabilResourceIT.createUpdatedEntity(em);
            em.persist(planoContabil);
            em.flush();
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        termoContratoContabil.setPlanoContabil(planoContabil);
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
        TermoContratoContabilDTO termoContratoContabilDTO = termoContratoContabilMapper.toDto(termoContratoContabil);
        var returnedTermoContratoContabilDTO = om.readValue(
            restTermoContratoContabilMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoContratoContabilDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TermoContratoContabilDTO.class
        );

        // Validate the TermoContratoContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTermoContratoContabil = termoContratoContabilMapper.toEntity(returnedTermoContratoContabilDTO);
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
        TermoContratoContabilDTO termoContratoContabilDTO = termoContratoContabilMapper.toDto(termoContratoContabil);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermoContratoContabilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoContratoContabilDTO)))
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
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTermoContratoContabilsWithEagerRelationshipsIsEnabled() throws Exception {
        when(termoContratoContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTermoContratoContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(termoContratoContabilServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTermoContratoContabilsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(termoContratoContabilServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTermoContratoContabilMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(termoContratoContabilRepositoryMock, times(1)).findAll(any(Pageable.class));
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
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO));
    }

    @Test
    @Transactional
    void getTermoContratoContabilsByIdFiltering() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        Long id = termoContratoContabil.getId();

        defaultTermoContratoContabilFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTermoContratoContabilFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTermoContratoContabilFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList where documento equals to
        defaultTermoContratoContabilFiltering("documento.equals=" + DEFAULT_DOCUMENTO, "documento.equals=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList where documento in
        defaultTermoContratoContabilFiltering(
            "documento.in=" + DEFAULT_DOCUMENTO + "," + UPDATED_DOCUMENTO,
            "documento.in=" + UPDATED_DOCUMENTO
        );
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList where documento is not null
        defaultTermoContratoContabilFiltering("documento.specified=true", "documento.specified=false");
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByDocumentoContainsSomething() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList where documento contains
        defaultTermoContratoContabilFiltering("documento.contains=" + DEFAULT_DOCUMENTO, "documento.contains=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByDocumentoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList where documento does not contain
        defaultTermoContratoContabilFiltering(
            "documento.doesNotContain=" + UPDATED_DOCUMENTO,
            "documento.doesNotContain=" + DEFAULT_DOCUMENTO
        );
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList where titulo equals to
        defaultTermoContratoContabilFiltering("titulo.equals=" + DEFAULT_TITULO, "titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList where titulo in
        defaultTermoContratoContabilFiltering("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO, "titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList where titulo is not null
        defaultTermoContratoContabilFiltering("titulo.specified=true", "titulo.specified=false");
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByTituloContainsSomething() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList where titulo contains
        defaultTermoContratoContabilFiltering("titulo.contains=" + DEFAULT_TITULO, "titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTermoContratoContabil = termoContratoContabilRepository.saveAndFlush(termoContratoContabil);

        // Get all the termoContratoContabilList where titulo does not contain
        defaultTermoContratoContabilFiltering("titulo.doesNotContain=" + UPDATED_TITULO, "titulo.doesNotContain=" + DEFAULT_TITULO);
    }

    @Test
    @Transactional
    void getAllTermoContratoContabilsByPlanoContabilIsEqualToSomething() throws Exception {
        PlanoContabil planoContabil;
        if (TestUtil.findAll(em, PlanoContabil.class).isEmpty()) {
            termoContratoContabilRepository.saveAndFlush(termoContratoContabil);
            planoContabil = PlanoContabilResourceIT.createEntity(em);
        } else {
            planoContabil = TestUtil.findAll(em, PlanoContabil.class).get(0);
        }
        em.persist(planoContabil);
        em.flush();
        termoContratoContabil.setPlanoContabil(planoContabil);
        termoContratoContabilRepository.saveAndFlush(termoContratoContabil);
        Long planoContabilId = planoContabil.getId();
        // Get all the termoContratoContabilList where planoContabil equals to planoContabilId
        defaultTermoContratoContabilShouldBeFound("planoContabilId.equals=" + planoContabilId);

        // Get all the termoContratoContabilList where planoContabil equals to (planoContabilId + 1)
        defaultTermoContratoContabilShouldNotBeFound("planoContabilId.equals=" + (planoContabilId + 1));
    }

    private void defaultTermoContratoContabilFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTermoContratoContabilShouldBeFound(shouldBeFound);
        defaultTermoContratoContabilShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTermoContratoContabilShouldBeFound(String filter) throws Exception {
        restTermoContratoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termoContratoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));

        // Check, that the count call also returns 1
        restTermoContratoContabilMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTermoContratoContabilShouldNotBeFound(String filter) throws Exception {
        restTermoContratoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTermoContratoContabilMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        updatedTermoContratoContabil.documento(UPDATED_DOCUMENTO).descricao(UPDATED_DESCRICAO).titulo(UPDATED_TITULO);
        TermoContratoContabilDTO termoContratoContabilDTO = termoContratoContabilMapper.toDto(updatedTermoContratoContabil);

        restTermoContratoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, termoContratoContabilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoContabilDTO))
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

        // Create the TermoContratoContabil
        TermoContratoContabilDTO termoContratoContabilDTO = termoContratoContabilMapper.toDto(termoContratoContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, termoContratoContabilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoContabilDTO))
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

        // Create the TermoContratoContabil
        TermoContratoContabilDTO termoContratoContabilDTO = termoContratoContabilMapper.toDto(termoContratoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoContratoContabilDTO))
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

        // Create the TermoContratoContabil
        TermoContratoContabilDTO termoContratoContabilDTO = termoContratoContabilMapper.toDto(termoContratoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoContratoContabilDTO)))
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

        partialUpdatedTermoContratoContabil.documento(UPDATED_DOCUMENTO).descricao(UPDATED_DESCRICAO);

        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoContratoContabil.getId())
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

        partialUpdatedTermoContratoContabil.documento(UPDATED_DOCUMENTO).descricao(UPDATED_DESCRICAO).titulo(UPDATED_TITULO);

        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoContratoContabil.getId())
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

        // Create the TermoContratoContabil
        TermoContratoContabilDTO termoContratoContabilDTO = termoContratoContabilMapper.toDto(termoContratoContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, termoContratoContabilDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoContratoContabilDTO))
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

        // Create the TermoContratoContabil
        TermoContratoContabilDTO termoContratoContabilDTO = termoContratoContabilMapper.toDto(termoContratoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoContratoContabilDTO))
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

        // Create the TermoContratoContabil
        TermoContratoContabilDTO termoContratoContabilDTO = termoContratoContabilMapper.toDto(termoContratoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoContratoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(termoContratoContabilDTO))
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
            .perform(delete(ENTITY_API_URL_ID, termoContratoContabil.getId()).accept(MediaType.APPLICATION_JSON))
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
