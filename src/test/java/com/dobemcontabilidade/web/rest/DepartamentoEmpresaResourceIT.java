package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DepartamentoEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.domain.DepartamentoEmpresa;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.repository.DepartamentoEmpresaRepository;
import com.dobemcontabilidade.service.DepartamentoEmpresaService;
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
 * Integration tests for the {@link DepartamentoEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DepartamentoEmpresaResourceIT {

    private static final Double DEFAULT_PONTUACAO = 1D;
    private static final Double UPDATED_PONTUACAO = 2D;
    private static final Double SMALLER_PONTUACAO = 1D - 1D;

    private static final String DEFAULT_DEPOIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPOIMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_RECLAMACAO = "AAAAAAAAAA";
    private static final String UPDATED_RECLAMACAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/departamento-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DepartamentoEmpresaRepository departamentoEmpresaRepository;

    @Mock
    private DepartamentoEmpresaRepository departamentoEmpresaRepositoryMock;

    @Mock
    private DepartamentoEmpresaService departamentoEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartamentoEmpresaMockMvc;

    private DepartamentoEmpresa departamentoEmpresa;

    private DepartamentoEmpresa insertedDepartamentoEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartamentoEmpresa createEntity(EntityManager em) {
        DepartamentoEmpresa departamentoEmpresa = new DepartamentoEmpresa()
            .pontuacao(DEFAULT_PONTUACAO)
            .depoimento(DEFAULT_DEPOIMENTO)
            .reclamacao(DEFAULT_RECLAMACAO);
        // Add required entity
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamento = DepartamentoResourceIT.createEntity(em);
            em.persist(departamento);
            em.flush();
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        departamentoEmpresa.setDepartamento(departamento);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        departamentoEmpresa.setEmpresa(empresa);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        departamentoEmpresa.setContador(contador);
        return departamentoEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartamentoEmpresa createUpdatedEntity(EntityManager em) {
        DepartamentoEmpresa departamentoEmpresa = new DepartamentoEmpresa()
            .pontuacao(UPDATED_PONTUACAO)
            .depoimento(UPDATED_DEPOIMENTO)
            .reclamacao(UPDATED_RECLAMACAO);
        // Add required entity
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamento = DepartamentoResourceIT.createUpdatedEntity(em);
            em.persist(departamento);
            em.flush();
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        departamentoEmpresa.setDepartamento(departamento);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        departamentoEmpresa.setEmpresa(empresa);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        departamentoEmpresa.setContador(contador);
        return departamentoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        departamentoEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDepartamentoEmpresa != null) {
            departamentoEmpresaRepository.delete(insertedDepartamentoEmpresa);
            insertedDepartamentoEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createDepartamentoEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DepartamentoEmpresa
        var returnedDepartamentoEmpresa = om.readValue(
            restDepartamentoEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamentoEmpresa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DepartamentoEmpresa.class
        );

        // Validate the DepartamentoEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertDepartamentoEmpresaUpdatableFieldsEquals(
            returnedDepartamentoEmpresa,
            getPersistedDepartamentoEmpresa(returnedDepartamentoEmpresa)
        );

        insertedDepartamentoEmpresa = returnedDepartamentoEmpresa;
    }

    @Test
    @Transactional
    void createDepartamentoEmpresaWithExistingId() throws Exception {
        // Create the DepartamentoEmpresa with an existing ID
        departamentoEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentoEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamentoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresas() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList
        restDepartamentoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamentoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].pontuacao").value(hasItem(DEFAULT_PONTUACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].depoimento").value(hasItem(DEFAULT_DEPOIMENTO)))
            .andExpect(jsonPath("$.[*].reclamacao").value(hasItem(DEFAULT_RECLAMACAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDepartamentoEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(departamentoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDepartamentoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(departamentoEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDepartamentoEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(departamentoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDepartamentoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(departamentoEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDepartamentoEmpresa() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get the departamentoEmpresa
        restDepartamentoEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, departamentoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departamentoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.pontuacao").value(DEFAULT_PONTUACAO.doubleValue()))
            .andExpect(jsonPath("$.depoimento").value(DEFAULT_DEPOIMENTO))
            .andExpect(jsonPath("$.reclamacao").value(DEFAULT_RECLAMACAO));
    }

    @Test
    @Transactional
    void getDepartamentoEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        Long id = departamentoEmpresa.getId();

        defaultDepartamentoEmpresaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDepartamentoEmpresaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDepartamentoEmpresaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByPontuacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where pontuacao equals to
        defaultDepartamentoEmpresaFiltering("pontuacao.equals=" + DEFAULT_PONTUACAO, "pontuacao.equals=" + UPDATED_PONTUACAO);
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByPontuacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where pontuacao in
        defaultDepartamentoEmpresaFiltering(
            "pontuacao.in=" + DEFAULT_PONTUACAO + "," + UPDATED_PONTUACAO,
            "pontuacao.in=" + UPDATED_PONTUACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByPontuacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where pontuacao is not null
        defaultDepartamentoEmpresaFiltering("pontuacao.specified=true", "pontuacao.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByPontuacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where pontuacao is greater than or equal to
        defaultDepartamentoEmpresaFiltering(
            "pontuacao.greaterThanOrEqual=" + DEFAULT_PONTUACAO,
            "pontuacao.greaterThanOrEqual=" + UPDATED_PONTUACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByPontuacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where pontuacao is less than or equal to
        defaultDepartamentoEmpresaFiltering(
            "pontuacao.lessThanOrEqual=" + DEFAULT_PONTUACAO,
            "pontuacao.lessThanOrEqual=" + SMALLER_PONTUACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByPontuacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where pontuacao is less than
        defaultDepartamentoEmpresaFiltering("pontuacao.lessThan=" + UPDATED_PONTUACAO, "pontuacao.lessThan=" + DEFAULT_PONTUACAO);
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByPontuacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where pontuacao is greater than
        defaultDepartamentoEmpresaFiltering("pontuacao.greaterThan=" + SMALLER_PONTUACAO, "pontuacao.greaterThan=" + DEFAULT_PONTUACAO);
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByDepoimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where depoimento equals to
        defaultDepartamentoEmpresaFiltering("depoimento.equals=" + DEFAULT_DEPOIMENTO, "depoimento.equals=" + UPDATED_DEPOIMENTO);
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByDepoimentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where depoimento in
        defaultDepartamentoEmpresaFiltering(
            "depoimento.in=" + DEFAULT_DEPOIMENTO + "," + UPDATED_DEPOIMENTO,
            "depoimento.in=" + UPDATED_DEPOIMENTO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByDepoimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where depoimento is not null
        defaultDepartamentoEmpresaFiltering("depoimento.specified=true", "depoimento.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByDepoimentoContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where depoimento contains
        defaultDepartamentoEmpresaFiltering("depoimento.contains=" + DEFAULT_DEPOIMENTO, "depoimento.contains=" + UPDATED_DEPOIMENTO);
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByDepoimentoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where depoimento does not contain
        defaultDepartamentoEmpresaFiltering(
            "depoimento.doesNotContain=" + UPDATED_DEPOIMENTO,
            "depoimento.doesNotContain=" + DEFAULT_DEPOIMENTO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByReclamacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where reclamacao equals to
        defaultDepartamentoEmpresaFiltering("reclamacao.equals=" + DEFAULT_RECLAMACAO, "reclamacao.equals=" + UPDATED_RECLAMACAO);
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByReclamacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where reclamacao in
        defaultDepartamentoEmpresaFiltering(
            "reclamacao.in=" + DEFAULT_RECLAMACAO + "," + UPDATED_RECLAMACAO,
            "reclamacao.in=" + UPDATED_RECLAMACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByReclamacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where reclamacao is not null
        defaultDepartamentoEmpresaFiltering("reclamacao.specified=true", "reclamacao.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByReclamacaoContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where reclamacao contains
        defaultDepartamentoEmpresaFiltering("reclamacao.contains=" + DEFAULT_RECLAMACAO, "reclamacao.contains=" + UPDATED_RECLAMACAO);
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByReclamacaoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        // Get all the departamentoEmpresaList where reclamacao does not contain
        defaultDepartamentoEmpresaFiltering(
            "reclamacao.doesNotContain=" + UPDATED_RECLAMACAO,
            "reclamacao.doesNotContain=" + DEFAULT_RECLAMACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByDepartamentoIsEqualToSomething() throws Exception {
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);
            departamento = DepartamentoResourceIT.createEntity(em);
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        em.persist(departamento);
        em.flush();
        departamentoEmpresa.setDepartamento(departamento);
        departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);
        Long departamentoId = departamento.getId();
        // Get all the departamentoEmpresaList where departamento equals to departamentoId
        defaultDepartamentoEmpresaShouldBeFound("departamentoId.equals=" + departamentoId);

        // Get all the departamentoEmpresaList where departamento equals to (departamentoId + 1)
        defaultDepartamentoEmpresaShouldNotBeFound("departamentoId.equals=" + (departamentoId + 1));
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        departamentoEmpresa.setEmpresa(empresa);
        departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);
        Long empresaId = empresa.getId();
        // Get all the departamentoEmpresaList where empresa equals to empresaId
        defaultDepartamentoEmpresaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the departamentoEmpresaList where empresa equals to (empresaId + 1)
        defaultDepartamentoEmpresaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllDepartamentoEmpresasByContadorIsEqualToSomething() throws Exception {
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);
            contador = ContadorResourceIT.createEntity(em);
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        em.persist(contador);
        em.flush();
        departamentoEmpresa.setContador(contador);
        departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);
        Long contadorId = contador.getId();
        // Get all the departamentoEmpresaList where contador equals to contadorId
        defaultDepartamentoEmpresaShouldBeFound("contadorId.equals=" + contadorId);

        // Get all the departamentoEmpresaList where contador equals to (contadorId + 1)
        defaultDepartamentoEmpresaShouldNotBeFound("contadorId.equals=" + (contadorId + 1));
    }

    private void defaultDepartamentoEmpresaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDepartamentoEmpresaShouldBeFound(shouldBeFound);
        defaultDepartamentoEmpresaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartamentoEmpresaShouldBeFound(String filter) throws Exception {
        restDepartamentoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamentoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].pontuacao").value(hasItem(DEFAULT_PONTUACAO.doubleValue())))
            .andExpect(jsonPath("$.[*].depoimento").value(hasItem(DEFAULT_DEPOIMENTO)))
            .andExpect(jsonPath("$.[*].reclamacao").value(hasItem(DEFAULT_RECLAMACAO)));

        // Check, that the count call also returns 1
        restDepartamentoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepartamentoEmpresaShouldNotBeFound(String filter) throws Exception {
        restDepartamentoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartamentoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDepartamentoEmpresa() throws Exception {
        // Get the departamentoEmpresa
        restDepartamentoEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDepartamentoEmpresa() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamentoEmpresa
        DepartamentoEmpresa updatedDepartamentoEmpresa = departamentoEmpresaRepository.findById(departamentoEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDepartamentoEmpresa are not directly saved in db
        em.detach(updatedDepartamentoEmpresa);
        updatedDepartamentoEmpresa.pontuacao(UPDATED_PONTUACAO).depoimento(UPDATED_DEPOIMENTO).reclamacao(UPDATED_RECLAMACAO);

        restDepartamentoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDepartamentoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedDepartamentoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the DepartamentoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDepartamentoEmpresaToMatchAllProperties(updatedDepartamentoEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingDepartamentoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departamentoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamentoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepartamentoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamentoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepartamentoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamentoEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DepartamentoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepartamentoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamentoEmpresa using partial update
        DepartamentoEmpresa partialUpdatedDepartamentoEmpresa = new DepartamentoEmpresa();
        partialUpdatedDepartamentoEmpresa.setId(departamentoEmpresa.getId());

        partialUpdatedDepartamentoEmpresa.pontuacao(UPDATED_PONTUACAO).depoimento(UPDATED_DEPOIMENTO).reclamacao(UPDATED_RECLAMACAO);

        restDepartamentoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamentoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartamentoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the DepartamentoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartamentoEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDepartamentoEmpresa, departamentoEmpresa),
            getPersistedDepartamentoEmpresa(departamentoEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateDepartamentoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamentoEmpresa using partial update
        DepartamentoEmpresa partialUpdatedDepartamentoEmpresa = new DepartamentoEmpresa();
        partialUpdatedDepartamentoEmpresa.setId(departamentoEmpresa.getId());

        partialUpdatedDepartamentoEmpresa.pontuacao(UPDATED_PONTUACAO).depoimento(UPDATED_DEPOIMENTO).reclamacao(UPDATED_RECLAMACAO);

        restDepartamentoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamentoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartamentoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the DepartamentoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartamentoEmpresaUpdatableFieldsEquals(
            partialUpdatedDepartamentoEmpresa,
            getPersistedDepartamentoEmpresa(partialUpdatedDepartamentoEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDepartamentoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, departamentoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departamentoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepartamentoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departamentoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepartamentoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(departamentoEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DepartamentoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepartamentoEmpresa() throws Exception {
        // Initialize the database
        insertedDepartamentoEmpresa = departamentoEmpresaRepository.saveAndFlush(departamentoEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the departamentoEmpresa
        restDepartamentoEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, departamentoEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return departamentoEmpresaRepository.count();
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

    protected DepartamentoEmpresa getPersistedDepartamentoEmpresa(DepartamentoEmpresa departamentoEmpresa) {
        return departamentoEmpresaRepository.findById(departamentoEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedDepartamentoEmpresaToMatchAllProperties(DepartamentoEmpresa expectedDepartamentoEmpresa) {
        assertDepartamentoEmpresaAllPropertiesEquals(
            expectedDepartamentoEmpresa,
            getPersistedDepartamentoEmpresa(expectedDepartamentoEmpresa)
        );
    }

    protected void assertPersistedDepartamentoEmpresaToMatchUpdatableProperties(DepartamentoEmpresa expectedDepartamentoEmpresa) {
        assertDepartamentoEmpresaAllUpdatablePropertiesEquals(
            expectedDepartamentoEmpresa,
            getPersistedDepartamentoEmpresa(expectedDepartamentoEmpresa)
        );
    }
}
