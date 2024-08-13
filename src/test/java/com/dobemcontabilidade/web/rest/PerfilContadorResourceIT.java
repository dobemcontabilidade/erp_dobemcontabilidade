package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PerfilContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.repository.PerfilContadorRepository;
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
 * Integration tests for the {@link PerfilContadorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PerfilContadorResourceIT {

    private static final String DEFAULT_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_PERFIL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIMITE_EMPRESAS = 1;
    private static final Integer UPDATED_LIMITE_EMPRESAS = 2;
    private static final Integer SMALLER_LIMITE_EMPRESAS = 1 - 1;

    private static final Integer DEFAULT_LIMITE_DEPARTAMENTOS = 1;
    private static final Integer UPDATED_LIMITE_DEPARTAMENTOS = 2;
    private static final Integer SMALLER_LIMITE_DEPARTAMENTOS = 1 - 1;

    private static final Double DEFAULT_LIMITE_FATURAMENTO = 1D;
    private static final Double UPDATED_LIMITE_FATURAMENTO = 2D;
    private static final Double SMALLER_LIMITE_FATURAMENTO = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/perfil-contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PerfilContadorRepository perfilContadorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfilContadorMockMvc;

    private PerfilContador perfilContador;

    private PerfilContador insertedPerfilContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilContador createEntity(EntityManager em) {
        PerfilContador perfilContador = new PerfilContador()
            .perfil(DEFAULT_PERFIL)
            .descricao(DEFAULT_DESCRICAO)
            .limiteEmpresas(DEFAULT_LIMITE_EMPRESAS)
            .limiteDepartamentos(DEFAULT_LIMITE_DEPARTAMENTOS)
            .limiteFaturamento(DEFAULT_LIMITE_FATURAMENTO);
        return perfilContador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilContador createUpdatedEntity(EntityManager em) {
        PerfilContador perfilContador = new PerfilContador()
            .perfil(UPDATED_PERFIL)
            .descricao(UPDATED_DESCRICAO)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO);
        return perfilContador;
    }

    @BeforeEach
    public void initTest() {
        perfilContador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPerfilContador != null) {
            perfilContadorRepository.delete(insertedPerfilContador);
            insertedPerfilContador = null;
        }
    }

    @Test
    @Transactional
    void createPerfilContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PerfilContador
        var returnedPerfilContador = om.readValue(
            restPerfilContadorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilContador)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PerfilContador.class
        );

        // Validate the PerfilContador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPerfilContadorUpdatableFieldsEquals(returnedPerfilContador, getPersistedPerfilContador(returnedPerfilContador));

        insertedPerfilContador = returnedPerfilContador;
    }

    @Test
    @Transactional
    void createPerfilContadorWithExistingId() throws Exception {
        // Create the PerfilContador with an existing ID
        perfilContador.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilContador)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPerfilIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        perfilContador.setPerfil(null);

        // Create the PerfilContador, which fails.

        restPerfilContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilContador)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPerfilContadors() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList
        restPerfilContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].perfil").value(hasItem(DEFAULT_PERFIL)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].limiteEmpresas").value(hasItem(DEFAULT_LIMITE_EMPRESAS)))
            .andExpect(jsonPath("$.[*].limiteDepartamentos").value(hasItem(DEFAULT_LIMITE_DEPARTAMENTOS)))
            .andExpect(jsonPath("$.[*].limiteFaturamento").value(hasItem(DEFAULT_LIMITE_FATURAMENTO.doubleValue())));
    }

    @Test
    @Transactional
    void getPerfilContador() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get the perfilContador
        restPerfilContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, perfilContador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfilContador.getId().intValue()))
            .andExpect(jsonPath("$.perfil").value(DEFAULT_PERFIL))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.limiteEmpresas").value(DEFAULT_LIMITE_EMPRESAS))
            .andExpect(jsonPath("$.limiteDepartamentos").value(DEFAULT_LIMITE_DEPARTAMENTOS))
            .andExpect(jsonPath("$.limiteFaturamento").value(DEFAULT_LIMITE_FATURAMENTO.doubleValue()));
    }

    @Test
    @Transactional
    void getPerfilContadorsByIdFiltering() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        Long id = perfilContador.getId();

        defaultPerfilContadorFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPerfilContadorFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPerfilContadorFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByPerfilIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where perfil equals to
        defaultPerfilContadorFiltering("perfil.equals=" + DEFAULT_PERFIL, "perfil.equals=" + UPDATED_PERFIL);
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByPerfilIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where perfil in
        defaultPerfilContadorFiltering("perfil.in=" + DEFAULT_PERFIL + "," + UPDATED_PERFIL, "perfil.in=" + UPDATED_PERFIL);
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByPerfilIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where perfil is not null
        defaultPerfilContadorFiltering("perfil.specified=true", "perfil.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByPerfilContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where perfil contains
        defaultPerfilContadorFiltering("perfil.contains=" + DEFAULT_PERFIL, "perfil.contains=" + UPDATED_PERFIL);
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByPerfilNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where perfil does not contain
        defaultPerfilContadorFiltering("perfil.doesNotContain=" + UPDATED_PERFIL, "perfil.doesNotContain=" + DEFAULT_PERFIL);
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where descricao equals to
        defaultPerfilContadorFiltering("descricao.equals=" + DEFAULT_DESCRICAO, "descricao.equals=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where descricao in
        defaultPerfilContadorFiltering("descricao.in=" + DEFAULT_DESCRICAO + "," + UPDATED_DESCRICAO, "descricao.in=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where descricao is not null
        defaultPerfilContadorFiltering("descricao.specified=true", "descricao.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByDescricaoContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where descricao contains
        defaultPerfilContadorFiltering("descricao.contains=" + DEFAULT_DESCRICAO, "descricao.contains=" + UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where descricao does not contain
        defaultPerfilContadorFiltering("descricao.doesNotContain=" + UPDATED_DESCRICAO, "descricao.doesNotContain=" + DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteEmpresasIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteEmpresas equals to
        defaultPerfilContadorFiltering(
            "limiteEmpresas.equals=" + DEFAULT_LIMITE_EMPRESAS,
            "limiteEmpresas.equals=" + UPDATED_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteEmpresasIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteEmpresas in
        defaultPerfilContadorFiltering(
            "limiteEmpresas.in=" + DEFAULT_LIMITE_EMPRESAS + "," + UPDATED_LIMITE_EMPRESAS,
            "limiteEmpresas.in=" + UPDATED_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteEmpresasIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteEmpresas is not null
        defaultPerfilContadorFiltering("limiteEmpresas.specified=true", "limiteEmpresas.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteEmpresasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteEmpresas is greater than or equal to
        defaultPerfilContadorFiltering(
            "limiteEmpresas.greaterThanOrEqual=" + DEFAULT_LIMITE_EMPRESAS,
            "limiteEmpresas.greaterThanOrEqual=" + UPDATED_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteEmpresasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteEmpresas is less than or equal to
        defaultPerfilContadorFiltering(
            "limiteEmpresas.lessThanOrEqual=" + DEFAULT_LIMITE_EMPRESAS,
            "limiteEmpresas.lessThanOrEqual=" + SMALLER_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteEmpresasIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteEmpresas is less than
        defaultPerfilContadorFiltering(
            "limiteEmpresas.lessThan=" + UPDATED_LIMITE_EMPRESAS,
            "limiteEmpresas.lessThan=" + DEFAULT_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteEmpresasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteEmpresas is greater than
        defaultPerfilContadorFiltering(
            "limiteEmpresas.greaterThan=" + SMALLER_LIMITE_EMPRESAS,
            "limiteEmpresas.greaterThan=" + DEFAULT_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteDepartamentosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteDepartamentos equals to
        defaultPerfilContadorFiltering(
            "limiteDepartamentos.equals=" + DEFAULT_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.equals=" + UPDATED_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteDepartamentosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteDepartamentos in
        defaultPerfilContadorFiltering(
            "limiteDepartamentos.in=" + DEFAULT_LIMITE_DEPARTAMENTOS + "," + UPDATED_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.in=" + UPDATED_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteDepartamentosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteDepartamentos is not null
        defaultPerfilContadorFiltering("limiteDepartamentos.specified=true", "limiteDepartamentos.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteDepartamentosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteDepartamentos is greater than or equal to
        defaultPerfilContadorFiltering(
            "limiteDepartamentos.greaterThanOrEqual=" + DEFAULT_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.greaterThanOrEqual=" + UPDATED_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteDepartamentosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteDepartamentos is less than or equal to
        defaultPerfilContadorFiltering(
            "limiteDepartamentos.lessThanOrEqual=" + DEFAULT_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.lessThanOrEqual=" + SMALLER_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteDepartamentosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteDepartamentos is less than
        defaultPerfilContadorFiltering(
            "limiteDepartamentos.lessThan=" + UPDATED_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.lessThan=" + DEFAULT_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteDepartamentosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteDepartamentos is greater than
        defaultPerfilContadorFiltering(
            "limiteDepartamentos.greaterThan=" + SMALLER_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.greaterThan=" + DEFAULT_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteFaturamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteFaturamento equals to
        defaultPerfilContadorFiltering(
            "limiteFaturamento.equals=" + DEFAULT_LIMITE_FATURAMENTO,
            "limiteFaturamento.equals=" + UPDATED_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteFaturamentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteFaturamento in
        defaultPerfilContadorFiltering(
            "limiteFaturamento.in=" + DEFAULT_LIMITE_FATURAMENTO + "," + UPDATED_LIMITE_FATURAMENTO,
            "limiteFaturamento.in=" + UPDATED_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteFaturamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteFaturamento is not null
        defaultPerfilContadorFiltering("limiteFaturamento.specified=true", "limiteFaturamento.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteFaturamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteFaturamento is greater than or equal to
        defaultPerfilContadorFiltering(
            "limiteFaturamento.greaterThanOrEqual=" + DEFAULT_LIMITE_FATURAMENTO,
            "limiteFaturamento.greaterThanOrEqual=" + UPDATED_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteFaturamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteFaturamento is less than or equal to
        defaultPerfilContadorFiltering(
            "limiteFaturamento.lessThanOrEqual=" + DEFAULT_LIMITE_FATURAMENTO,
            "limiteFaturamento.lessThanOrEqual=" + SMALLER_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteFaturamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteFaturamento is less than
        defaultPerfilContadorFiltering(
            "limiteFaturamento.lessThan=" + UPDATED_LIMITE_FATURAMENTO,
            "limiteFaturamento.lessThan=" + DEFAULT_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorsByLimiteFaturamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        // Get all the perfilContadorList where limiteFaturamento is greater than
        defaultPerfilContadorFiltering(
            "limiteFaturamento.greaterThan=" + SMALLER_LIMITE_FATURAMENTO,
            "limiteFaturamento.greaterThan=" + DEFAULT_LIMITE_FATURAMENTO
        );
    }

    private void defaultPerfilContadorFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPerfilContadorShouldBeFound(shouldBeFound);
        defaultPerfilContadorShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerfilContadorShouldBeFound(String filter) throws Exception {
        restPerfilContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].perfil").value(hasItem(DEFAULT_PERFIL)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].limiteEmpresas").value(hasItem(DEFAULT_LIMITE_EMPRESAS)))
            .andExpect(jsonPath("$.[*].limiteDepartamentos").value(hasItem(DEFAULT_LIMITE_DEPARTAMENTOS)))
            .andExpect(jsonPath("$.[*].limiteFaturamento").value(hasItem(DEFAULT_LIMITE_FATURAMENTO.doubleValue())));

        // Check, that the count call also returns 1
        restPerfilContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerfilContadorShouldNotBeFound(String filter) throws Exception {
        restPerfilContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerfilContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerfilContador() throws Exception {
        // Get the perfilContador
        restPerfilContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerfilContador() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilContador
        PerfilContador updatedPerfilContador = perfilContadorRepository.findById(perfilContador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPerfilContador are not directly saved in db
        em.detach(updatedPerfilContador);
        updatedPerfilContador
            .perfil(UPDATED_PERFIL)
            .descricao(UPDATED_DESCRICAO)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO);

        restPerfilContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerfilContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPerfilContador))
            )
            .andExpect(status().isOk());

        // Validate the PerfilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPerfilContadorToMatchAllProperties(updatedPerfilContador);
    }

    @Test
    @Transactional
    void putNonExistingPerfilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, perfilContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerfilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerfilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilContador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerfilContadorWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilContador using partial update
        PerfilContador partialUpdatedPerfilContador = new PerfilContador();
        partialUpdatedPerfilContador.setId(perfilContador.getId());

        partialUpdatedPerfilContador
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO);

        restPerfilContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilContador))
            )
            .andExpect(status().isOk());

        // Validate the PerfilContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilContadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPerfilContador, perfilContador),
            getPersistedPerfilContador(perfilContador)
        );
    }

    @Test
    @Transactional
    void fullUpdatePerfilContadorWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilContador using partial update
        PerfilContador partialUpdatedPerfilContador = new PerfilContador();
        partialUpdatedPerfilContador.setId(perfilContador.getId());

        partialUpdatedPerfilContador
            .perfil(UPDATED_PERFIL)
            .descricao(UPDATED_DESCRICAO)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO);

        restPerfilContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilContador))
            )
            .andExpect(status().isOk());

        // Validate the PerfilContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilContadorUpdatableFieldsEquals(partialUpdatedPerfilContador, getPersistedPerfilContador(partialUpdatedPerfilContador));
    }

    @Test
    @Transactional
    void patchNonExistingPerfilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, perfilContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerfilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerfilContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(perfilContador)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerfilContador() throws Exception {
        // Initialize the database
        insertedPerfilContador = perfilContadorRepository.saveAndFlush(perfilContador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the perfilContador
        restPerfilContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, perfilContador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return perfilContadorRepository.count();
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

    protected PerfilContador getPersistedPerfilContador(PerfilContador perfilContador) {
        return perfilContadorRepository.findById(perfilContador.getId()).orElseThrow();
    }

    protected void assertPersistedPerfilContadorToMatchAllProperties(PerfilContador expectedPerfilContador) {
        assertPerfilContadorAllPropertiesEquals(expectedPerfilContador, getPersistedPerfilContador(expectedPerfilContador));
    }

    protected void assertPersistedPerfilContadorToMatchUpdatableProperties(PerfilContador expectedPerfilContador) {
        assertPerfilContadorAllUpdatablePropertiesEquals(expectedPerfilContador, getPersistedPerfilContador(expectedPerfilContador));
    }
}
