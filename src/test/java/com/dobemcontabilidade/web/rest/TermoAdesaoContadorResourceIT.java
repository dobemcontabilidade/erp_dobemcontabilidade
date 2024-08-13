package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TermoAdesaoContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.TermoAdesaoContador;
import com.dobemcontabilidade.domain.TermoDeAdesao;
import com.dobemcontabilidade.repository.TermoAdesaoContadorRepository;
import com.dobemcontabilidade.service.TermoAdesaoContadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link TermoAdesaoContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TermoAdesaoContadorResourceIT {

    private static final Instant DEFAULT_DATA_ADESAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ADESAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_CHECKED = false;
    private static final Boolean UPDATED_CHECKED = true;

    private static final String DEFAULT_URL_DOC = "AAAAAAAAAA";
    private static final String UPDATED_URL_DOC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/termo-adesao-contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TermoAdesaoContadorRepository termoAdesaoContadorRepository;

    @Mock
    private TermoAdesaoContadorRepository termoAdesaoContadorRepositoryMock;

    @Mock
    private TermoAdesaoContadorService termoAdesaoContadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTermoAdesaoContadorMockMvc;

    private TermoAdesaoContador termoAdesaoContador;

    private TermoAdesaoContador insertedTermoAdesaoContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoAdesaoContador createEntity(EntityManager em) {
        TermoAdesaoContador termoAdesaoContador = new TermoAdesaoContador()
            .dataAdesao(DEFAULT_DATA_ADESAO)
            .checked(DEFAULT_CHECKED)
            .urlDoc(DEFAULT_URL_DOC);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        termoAdesaoContador.setContador(contador);
        // Add required entity
        TermoDeAdesao termoDeAdesao;
        if (TestUtil.findAll(em, TermoDeAdesao.class).isEmpty()) {
            termoDeAdesao = TermoDeAdesaoResourceIT.createEntity(em);
            em.persist(termoDeAdesao);
            em.flush();
        } else {
            termoDeAdesao = TestUtil.findAll(em, TermoDeAdesao.class).get(0);
        }
        termoAdesaoContador.setTermoDeAdesao(termoDeAdesao);
        return termoAdesaoContador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermoAdesaoContador createUpdatedEntity(EntityManager em) {
        TermoAdesaoContador termoAdesaoContador = new TermoAdesaoContador()
            .dataAdesao(UPDATED_DATA_ADESAO)
            .checked(UPDATED_CHECKED)
            .urlDoc(UPDATED_URL_DOC);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        termoAdesaoContador.setContador(contador);
        // Add required entity
        TermoDeAdesao termoDeAdesao;
        if (TestUtil.findAll(em, TermoDeAdesao.class).isEmpty()) {
            termoDeAdesao = TermoDeAdesaoResourceIT.createUpdatedEntity(em);
            em.persist(termoDeAdesao);
            em.flush();
        } else {
            termoDeAdesao = TestUtil.findAll(em, TermoDeAdesao.class).get(0);
        }
        termoAdesaoContador.setTermoDeAdesao(termoDeAdesao);
        return termoAdesaoContador;
    }

    @BeforeEach
    public void initTest() {
        termoAdesaoContador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTermoAdesaoContador != null) {
            termoAdesaoContadorRepository.delete(insertedTermoAdesaoContador);
            insertedTermoAdesaoContador = null;
        }
    }

    @Test
    @Transactional
    void createTermoAdesaoContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TermoAdesaoContador
        var returnedTermoAdesaoContador = om.readValue(
            restTermoAdesaoContadorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoAdesaoContador)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TermoAdesaoContador.class
        );

        // Validate the TermoAdesaoContador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTermoAdesaoContadorUpdatableFieldsEquals(
            returnedTermoAdesaoContador,
            getPersistedTermoAdesaoContador(returnedTermoAdesaoContador)
        );

        insertedTermoAdesaoContador = returnedTermoAdesaoContador;
    }

    @Test
    @Transactional
    void createTermoAdesaoContadorWithExistingId() throws Exception {
        // Create the TermoAdesaoContador with an existing ID
        termoAdesaoContador.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermoAdesaoContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoAdesaoContador)))
            .andExpect(status().isBadRequest());

        // Validate the TermoAdesaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadors() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList
        restTermoAdesaoContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termoAdesaoContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAdesao").value(hasItem(DEFAULT_DATA_ADESAO.toString())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].urlDoc").value(hasItem(DEFAULT_URL_DOC)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTermoAdesaoContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(termoAdesaoContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTermoAdesaoContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(termoAdesaoContadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTermoAdesaoContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(termoAdesaoContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTermoAdesaoContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(termoAdesaoContadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTermoAdesaoContador() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get the termoAdesaoContador
        restTermoAdesaoContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, termoAdesaoContador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(termoAdesaoContador.getId().intValue()))
            .andExpect(jsonPath("$.dataAdesao").value(DEFAULT_DATA_ADESAO.toString()))
            .andExpect(jsonPath("$.checked").value(DEFAULT_CHECKED.booleanValue()))
            .andExpect(jsonPath("$.urlDoc").value(DEFAULT_URL_DOC));
    }

    @Test
    @Transactional
    void getTermoAdesaoContadorsByIdFiltering() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        Long id = termoAdesaoContador.getId();

        defaultTermoAdesaoContadorFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTermoAdesaoContadorFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTermoAdesaoContadorFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByDataAdesaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where dataAdesao equals to
        defaultTermoAdesaoContadorFiltering("dataAdesao.equals=" + DEFAULT_DATA_ADESAO, "dataAdesao.equals=" + UPDATED_DATA_ADESAO);
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByDataAdesaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where dataAdesao in
        defaultTermoAdesaoContadorFiltering(
            "dataAdesao.in=" + DEFAULT_DATA_ADESAO + "," + UPDATED_DATA_ADESAO,
            "dataAdesao.in=" + UPDATED_DATA_ADESAO
        );
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByDataAdesaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where dataAdesao is not null
        defaultTermoAdesaoContadorFiltering("dataAdesao.specified=true", "dataAdesao.specified=false");
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByCheckedIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where checked equals to
        defaultTermoAdesaoContadorFiltering("checked.equals=" + DEFAULT_CHECKED, "checked.equals=" + UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByCheckedIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where checked in
        defaultTermoAdesaoContadorFiltering("checked.in=" + DEFAULT_CHECKED + "," + UPDATED_CHECKED, "checked.in=" + UPDATED_CHECKED);
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByCheckedIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where checked is not null
        defaultTermoAdesaoContadorFiltering("checked.specified=true", "checked.specified=false");
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByUrlDocIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where urlDoc equals to
        defaultTermoAdesaoContadorFiltering("urlDoc.equals=" + DEFAULT_URL_DOC, "urlDoc.equals=" + UPDATED_URL_DOC);
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByUrlDocIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where urlDoc in
        defaultTermoAdesaoContadorFiltering("urlDoc.in=" + DEFAULT_URL_DOC + "," + UPDATED_URL_DOC, "urlDoc.in=" + UPDATED_URL_DOC);
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByUrlDocIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where urlDoc is not null
        defaultTermoAdesaoContadorFiltering("urlDoc.specified=true", "urlDoc.specified=false");
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByUrlDocContainsSomething() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where urlDoc contains
        defaultTermoAdesaoContadorFiltering("urlDoc.contains=" + DEFAULT_URL_DOC, "urlDoc.contains=" + UPDATED_URL_DOC);
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByUrlDocNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        // Get all the termoAdesaoContadorList where urlDoc does not contain
        defaultTermoAdesaoContadorFiltering("urlDoc.doesNotContain=" + UPDATED_URL_DOC, "urlDoc.doesNotContain=" + DEFAULT_URL_DOC);
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByContadorIsEqualToSomething() throws Exception {
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);
            contador = ContadorResourceIT.createEntity(em);
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        em.persist(contador);
        em.flush();
        termoAdesaoContador.setContador(contador);
        termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);
        Long contadorId = contador.getId();
        // Get all the termoAdesaoContadorList where contador equals to contadorId
        defaultTermoAdesaoContadorShouldBeFound("contadorId.equals=" + contadorId);

        // Get all the termoAdesaoContadorList where contador equals to (contadorId + 1)
        defaultTermoAdesaoContadorShouldNotBeFound("contadorId.equals=" + (contadorId + 1));
    }

    @Test
    @Transactional
    void getAllTermoAdesaoContadorsByTermoDeAdesaoIsEqualToSomething() throws Exception {
        TermoDeAdesao termoDeAdesao;
        if (TestUtil.findAll(em, TermoDeAdesao.class).isEmpty()) {
            termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);
            termoDeAdesao = TermoDeAdesaoResourceIT.createEntity(em);
        } else {
            termoDeAdesao = TestUtil.findAll(em, TermoDeAdesao.class).get(0);
        }
        em.persist(termoDeAdesao);
        em.flush();
        termoAdesaoContador.setTermoDeAdesao(termoDeAdesao);
        termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);
        Long termoDeAdesaoId = termoDeAdesao.getId();
        // Get all the termoAdesaoContadorList where termoDeAdesao equals to termoDeAdesaoId
        defaultTermoAdesaoContadorShouldBeFound("termoDeAdesaoId.equals=" + termoDeAdesaoId);

        // Get all the termoAdesaoContadorList where termoDeAdesao equals to (termoDeAdesaoId + 1)
        defaultTermoAdesaoContadorShouldNotBeFound("termoDeAdesaoId.equals=" + (termoDeAdesaoId + 1));
    }

    private void defaultTermoAdesaoContadorFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTermoAdesaoContadorShouldBeFound(shouldBeFound);
        defaultTermoAdesaoContadorShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTermoAdesaoContadorShouldBeFound(String filter) throws Exception {
        restTermoAdesaoContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termoAdesaoContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataAdesao").value(hasItem(DEFAULT_DATA_ADESAO.toString())))
            .andExpect(jsonPath("$.[*].checked").value(hasItem(DEFAULT_CHECKED.booleanValue())))
            .andExpect(jsonPath("$.[*].urlDoc").value(hasItem(DEFAULT_URL_DOC)));

        // Check, that the count call also returns 1
        restTermoAdesaoContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTermoAdesaoContadorShouldNotBeFound(String filter) throws Exception {
        restTermoAdesaoContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTermoAdesaoContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTermoAdesaoContador() throws Exception {
        // Get the termoAdesaoContador
        restTermoAdesaoContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTermoAdesaoContador() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoAdesaoContador
        TermoAdesaoContador updatedTermoAdesaoContador = termoAdesaoContadorRepository.findById(termoAdesaoContador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTermoAdesaoContador are not directly saved in db
        em.detach(updatedTermoAdesaoContador);
        updatedTermoAdesaoContador.dataAdesao(UPDATED_DATA_ADESAO).checked(UPDATED_CHECKED).urlDoc(UPDATED_URL_DOC);

        restTermoAdesaoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTermoAdesaoContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTermoAdesaoContador))
            )
            .andExpect(status().isOk());

        // Validate the TermoAdesaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTermoAdesaoContadorToMatchAllProperties(updatedTermoAdesaoContador);
    }

    @Test
    @Transactional
    void putNonExistingTermoAdesaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoContador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoAdesaoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, termoAdesaoContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoAdesaoContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoAdesaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTermoAdesaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoAdesaoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termoAdesaoContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoAdesaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTermoAdesaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoAdesaoContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termoAdesaoContador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoAdesaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTermoAdesaoContadorWithPatch() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoAdesaoContador using partial update
        TermoAdesaoContador partialUpdatedTermoAdesaoContador = new TermoAdesaoContador();
        partialUpdatedTermoAdesaoContador.setId(termoAdesaoContador.getId());

        partialUpdatedTermoAdesaoContador.dataAdesao(UPDATED_DATA_ADESAO);

        restTermoAdesaoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoAdesaoContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoAdesaoContador))
            )
            .andExpect(status().isOk());

        // Validate the TermoAdesaoContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoAdesaoContadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTermoAdesaoContador, termoAdesaoContador),
            getPersistedTermoAdesaoContador(termoAdesaoContador)
        );
    }

    @Test
    @Transactional
    void fullUpdateTermoAdesaoContadorWithPatch() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termoAdesaoContador using partial update
        TermoAdesaoContador partialUpdatedTermoAdesaoContador = new TermoAdesaoContador();
        partialUpdatedTermoAdesaoContador.setId(termoAdesaoContador.getId());

        partialUpdatedTermoAdesaoContador.dataAdesao(UPDATED_DATA_ADESAO).checked(UPDATED_CHECKED).urlDoc(UPDATED_URL_DOC);

        restTermoAdesaoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermoAdesaoContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermoAdesaoContador))
            )
            .andExpect(status().isOk());

        // Validate the TermoAdesaoContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermoAdesaoContadorUpdatableFieldsEquals(
            partialUpdatedTermoAdesaoContador,
            getPersistedTermoAdesaoContador(partialUpdatedTermoAdesaoContador)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTermoAdesaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoContador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermoAdesaoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, termoAdesaoContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoAdesaoContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoAdesaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTermoAdesaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoAdesaoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termoAdesaoContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermoAdesaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTermoAdesaoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termoAdesaoContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermoAdesaoContadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(termoAdesaoContador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermoAdesaoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTermoAdesaoContador() throws Exception {
        // Initialize the database
        insertedTermoAdesaoContador = termoAdesaoContadorRepository.saveAndFlush(termoAdesaoContador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the termoAdesaoContador
        restTermoAdesaoContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, termoAdesaoContador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return termoAdesaoContadorRepository.count();
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

    protected TermoAdesaoContador getPersistedTermoAdesaoContador(TermoAdesaoContador termoAdesaoContador) {
        return termoAdesaoContadorRepository.findById(termoAdesaoContador.getId()).orElseThrow();
    }

    protected void assertPersistedTermoAdesaoContadorToMatchAllProperties(TermoAdesaoContador expectedTermoAdesaoContador) {
        assertTermoAdesaoContadorAllPropertiesEquals(
            expectedTermoAdesaoContador,
            getPersistedTermoAdesaoContador(expectedTermoAdesaoContador)
        );
    }

    protected void assertPersistedTermoAdesaoContadorToMatchUpdatableProperties(TermoAdesaoContador expectedTermoAdesaoContador) {
        assertTermoAdesaoContadorAllUpdatablePropertiesEquals(
            expectedTermoAdesaoContador,
            getPersistedTermoAdesaoContador(expectedTermoAdesaoContador)
        );
    }
}
