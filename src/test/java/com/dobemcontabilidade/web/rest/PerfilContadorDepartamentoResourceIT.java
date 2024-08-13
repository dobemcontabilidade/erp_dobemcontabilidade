package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PerfilContadorDepartamentoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.domain.PerfilContadorDepartamento;
import com.dobemcontabilidade.repository.PerfilContadorDepartamentoRepository;
import com.dobemcontabilidade.service.PerfilContadorDepartamentoService;
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
 * Integration tests for the {@link PerfilContadorDepartamentoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PerfilContadorDepartamentoResourceIT {

    private static final Integer DEFAULT_QUANTIDADE_EMPRESAS = 1;
    private static final Integer UPDATED_QUANTIDADE_EMPRESAS = 2;
    private static final Integer SMALLER_QUANTIDADE_EMPRESAS = 1 - 1;

    private static final Double DEFAULT_PERCENTUAL_EXPERIENCIA = 1D;
    private static final Double UPDATED_PERCENTUAL_EXPERIENCIA = 2D;
    private static final Double SMALLER_PERCENTUAL_EXPERIENCIA = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/perfil-contador-departamentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PerfilContadorDepartamentoRepository perfilContadorDepartamentoRepository;

    @Mock
    private PerfilContadorDepartamentoRepository perfilContadorDepartamentoRepositoryMock;

    @Mock
    private PerfilContadorDepartamentoService perfilContadorDepartamentoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfilContadorDepartamentoMockMvc;

    private PerfilContadorDepartamento perfilContadorDepartamento;

    private PerfilContadorDepartamento insertedPerfilContadorDepartamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilContadorDepartamento createEntity(EntityManager em) {
        PerfilContadorDepartamento perfilContadorDepartamento = new PerfilContadorDepartamento()
            .quantidadeEmpresas(DEFAULT_QUANTIDADE_EMPRESAS)
            .percentualExperiencia(DEFAULT_PERCENTUAL_EXPERIENCIA);
        // Add required entity
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamento = DepartamentoResourceIT.createEntity(em);
            em.persist(departamento);
            em.flush();
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        perfilContadorDepartamento.setDepartamento(departamento);
        // Add required entity
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            perfilContador = PerfilContadorResourceIT.createEntity(em);
            em.persist(perfilContador);
            em.flush();
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        perfilContadorDepartamento.setPerfilContador(perfilContador);
        return perfilContadorDepartamento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfilContadorDepartamento createUpdatedEntity(EntityManager em) {
        PerfilContadorDepartamento perfilContadorDepartamento = new PerfilContadorDepartamento()
            .quantidadeEmpresas(UPDATED_QUANTIDADE_EMPRESAS)
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA);
        // Add required entity
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamento = DepartamentoResourceIT.createUpdatedEntity(em);
            em.persist(departamento);
            em.flush();
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        perfilContadorDepartamento.setDepartamento(departamento);
        // Add required entity
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            perfilContador = PerfilContadorResourceIT.createUpdatedEntity(em);
            em.persist(perfilContador);
            em.flush();
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        perfilContadorDepartamento.setPerfilContador(perfilContador);
        return perfilContadorDepartamento;
    }

    @BeforeEach
    public void initTest() {
        perfilContadorDepartamento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPerfilContadorDepartamento != null) {
            perfilContadorDepartamentoRepository.delete(insertedPerfilContadorDepartamento);
            insertedPerfilContadorDepartamento = null;
        }
    }

    @Test
    @Transactional
    void createPerfilContadorDepartamento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PerfilContadorDepartamento
        var returnedPerfilContadorDepartamento = om.readValue(
            restPerfilContadorDepartamentoMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilContadorDepartamento))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PerfilContadorDepartamento.class
        );

        // Validate the PerfilContadorDepartamento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPerfilContadorDepartamentoUpdatableFieldsEquals(
            returnedPerfilContadorDepartamento,
            getPersistedPerfilContadorDepartamento(returnedPerfilContadorDepartamento)
        );

        insertedPerfilContadorDepartamento = returnedPerfilContadorDepartamento;
    }

    @Test
    @Transactional
    void createPerfilContadorDepartamentoWithExistingId() throws Exception {
        // Create the PerfilContadorDepartamento with an existing ID
        perfilContadorDepartamento.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilContadorDepartamentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilContadorDepartamento)))
            .andExpect(status().isBadRequest());

        // Validate the PerfilContadorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentos() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList
        restPerfilContadorDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilContadorDepartamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeEmpresas").value(hasItem(DEFAULT_QUANTIDADE_EMPRESAS)))
            .andExpect(jsonPath("$.[*].percentualExperiencia").value(hasItem(DEFAULT_PERCENTUAL_EXPERIENCIA.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPerfilContadorDepartamentosWithEagerRelationshipsIsEnabled() throws Exception {
        when(perfilContadorDepartamentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPerfilContadorDepartamentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(perfilContadorDepartamentoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPerfilContadorDepartamentosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(perfilContadorDepartamentoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPerfilContadorDepartamentoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(perfilContadorDepartamentoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPerfilContadorDepartamento() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get the perfilContadorDepartamento
        restPerfilContadorDepartamentoMockMvc
            .perform(get(ENTITY_API_URL_ID, perfilContadorDepartamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfilContadorDepartamento.getId().intValue()))
            .andExpect(jsonPath("$.quantidadeEmpresas").value(DEFAULT_QUANTIDADE_EMPRESAS))
            .andExpect(jsonPath("$.percentualExperiencia").value(DEFAULT_PERCENTUAL_EXPERIENCIA.doubleValue()));
    }

    @Test
    @Transactional
    void getPerfilContadorDepartamentosByIdFiltering() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        Long id = perfilContadorDepartamento.getId();

        defaultPerfilContadorDepartamentoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPerfilContadorDepartamentoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPerfilContadorDepartamentoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByQuantidadeEmpresasIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where quantidadeEmpresas equals to
        defaultPerfilContadorDepartamentoFiltering(
            "quantidadeEmpresas.equals=" + DEFAULT_QUANTIDADE_EMPRESAS,
            "quantidadeEmpresas.equals=" + UPDATED_QUANTIDADE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByQuantidadeEmpresasIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where quantidadeEmpresas in
        defaultPerfilContadorDepartamentoFiltering(
            "quantidadeEmpresas.in=" + DEFAULT_QUANTIDADE_EMPRESAS + "," + UPDATED_QUANTIDADE_EMPRESAS,
            "quantidadeEmpresas.in=" + UPDATED_QUANTIDADE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByQuantidadeEmpresasIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where quantidadeEmpresas is not null
        defaultPerfilContadorDepartamentoFiltering("quantidadeEmpresas.specified=true", "quantidadeEmpresas.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByQuantidadeEmpresasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where quantidadeEmpresas is greater than or equal to
        defaultPerfilContadorDepartamentoFiltering(
            "quantidadeEmpresas.greaterThanOrEqual=" + DEFAULT_QUANTIDADE_EMPRESAS,
            "quantidadeEmpresas.greaterThanOrEqual=" + UPDATED_QUANTIDADE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByQuantidadeEmpresasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where quantidadeEmpresas is less than or equal to
        defaultPerfilContadorDepartamentoFiltering(
            "quantidadeEmpresas.lessThanOrEqual=" + DEFAULT_QUANTIDADE_EMPRESAS,
            "quantidadeEmpresas.lessThanOrEqual=" + SMALLER_QUANTIDADE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByQuantidadeEmpresasIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where quantidadeEmpresas is less than
        defaultPerfilContadorDepartamentoFiltering(
            "quantidadeEmpresas.lessThan=" + UPDATED_QUANTIDADE_EMPRESAS,
            "quantidadeEmpresas.lessThan=" + DEFAULT_QUANTIDADE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByQuantidadeEmpresasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where quantidadeEmpresas is greater than
        defaultPerfilContadorDepartamentoFiltering(
            "quantidadeEmpresas.greaterThan=" + SMALLER_QUANTIDADE_EMPRESAS,
            "quantidadeEmpresas.greaterThan=" + DEFAULT_QUANTIDADE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByPercentualExperienciaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where percentualExperiencia equals to
        defaultPerfilContadorDepartamentoFiltering(
            "percentualExperiencia.equals=" + DEFAULT_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.equals=" + UPDATED_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByPercentualExperienciaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where percentualExperiencia in
        defaultPerfilContadorDepartamentoFiltering(
            "percentualExperiencia.in=" + DEFAULT_PERCENTUAL_EXPERIENCIA + "," + UPDATED_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.in=" + UPDATED_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByPercentualExperienciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where percentualExperiencia is not null
        defaultPerfilContadorDepartamentoFiltering("percentualExperiencia.specified=true", "percentualExperiencia.specified=false");
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByPercentualExperienciaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where percentualExperiencia is greater than or equal to
        defaultPerfilContadorDepartamentoFiltering(
            "percentualExperiencia.greaterThanOrEqual=" + DEFAULT_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.greaterThanOrEqual=" + UPDATED_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByPercentualExperienciaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where percentualExperiencia is less than or equal to
        defaultPerfilContadorDepartamentoFiltering(
            "percentualExperiencia.lessThanOrEqual=" + DEFAULT_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.lessThanOrEqual=" + SMALLER_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByPercentualExperienciaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where percentualExperiencia is less than
        defaultPerfilContadorDepartamentoFiltering(
            "percentualExperiencia.lessThan=" + UPDATED_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.lessThan=" + DEFAULT_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByPercentualExperienciaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        // Get all the perfilContadorDepartamentoList where percentualExperiencia is greater than
        defaultPerfilContadorDepartamentoFiltering(
            "percentualExperiencia.greaterThan=" + SMALLER_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.greaterThan=" + DEFAULT_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByDepartamentoIsEqualToSomething() throws Exception {
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);
            departamento = DepartamentoResourceIT.createEntity(em);
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        em.persist(departamento);
        em.flush();
        perfilContadorDepartamento.setDepartamento(departamento);
        perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);
        Long departamentoId = departamento.getId();
        // Get all the perfilContadorDepartamentoList where departamento equals to departamentoId
        defaultPerfilContadorDepartamentoShouldBeFound("departamentoId.equals=" + departamentoId);

        // Get all the perfilContadorDepartamentoList where departamento equals to (departamentoId + 1)
        defaultPerfilContadorDepartamentoShouldNotBeFound("departamentoId.equals=" + (departamentoId + 1));
    }

    @Test
    @Transactional
    void getAllPerfilContadorDepartamentosByPerfilContadorIsEqualToSomething() throws Exception {
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);
            perfilContador = PerfilContadorResourceIT.createEntity(em);
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        em.persist(perfilContador);
        em.flush();
        perfilContadorDepartamento.setPerfilContador(perfilContador);
        perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);
        Long perfilContadorId = perfilContador.getId();
        // Get all the perfilContadorDepartamentoList where perfilContador equals to perfilContadorId
        defaultPerfilContadorDepartamentoShouldBeFound("perfilContadorId.equals=" + perfilContadorId);

        // Get all the perfilContadorDepartamentoList where perfilContador equals to (perfilContadorId + 1)
        defaultPerfilContadorDepartamentoShouldNotBeFound("perfilContadorId.equals=" + (perfilContadorId + 1));
    }

    private void defaultPerfilContadorDepartamentoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPerfilContadorDepartamentoShouldBeFound(shouldBeFound);
        defaultPerfilContadorDepartamentoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPerfilContadorDepartamentoShouldBeFound(String filter) throws Exception {
        restPerfilContadorDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfilContadorDepartamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidadeEmpresas").value(hasItem(DEFAULT_QUANTIDADE_EMPRESAS)))
            .andExpect(jsonPath("$.[*].percentualExperiencia").value(hasItem(DEFAULT_PERCENTUAL_EXPERIENCIA.doubleValue())));

        // Check, that the count call also returns 1
        restPerfilContadorDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPerfilContadorDepartamentoShouldNotBeFound(String filter) throws Exception {
        restPerfilContadorDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPerfilContadorDepartamentoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPerfilContadorDepartamento() throws Exception {
        // Get the perfilContadorDepartamento
        restPerfilContadorDepartamentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPerfilContadorDepartamento() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilContadorDepartamento
        PerfilContadorDepartamento updatedPerfilContadorDepartamento = perfilContadorDepartamentoRepository
            .findById(perfilContadorDepartamento.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedPerfilContadorDepartamento are not directly saved in db
        em.detach(updatedPerfilContadorDepartamento);
        updatedPerfilContadorDepartamento
            .quantidadeEmpresas(UPDATED_QUANTIDADE_EMPRESAS)
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA);

        restPerfilContadorDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPerfilContadorDepartamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPerfilContadorDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the PerfilContadorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPerfilContadorDepartamentoToMatchAllProperties(updatedPerfilContadorDepartamento);
    }

    @Test
    @Transactional
    void putNonExistingPerfilContadorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorDepartamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilContadorDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, perfilContadorDepartamento.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilContadorDepartamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContadorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerfilContadorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorDepartamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorDepartamentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(perfilContadorDepartamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContadorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerfilContadorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorDepartamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorDepartamentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(perfilContadorDepartamento)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilContadorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePerfilContadorDepartamentoWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilContadorDepartamento using partial update
        PerfilContadorDepartamento partialUpdatedPerfilContadorDepartamento = new PerfilContadorDepartamento();
        partialUpdatedPerfilContadorDepartamento.setId(perfilContadorDepartamento.getId());

        partialUpdatedPerfilContadorDepartamento.quantidadeEmpresas(UPDATED_QUANTIDADE_EMPRESAS);

        restPerfilContadorDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilContadorDepartamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilContadorDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the PerfilContadorDepartamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilContadorDepartamentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPerfilContadorDepartamento, perfilContadorDepartamento),
            getPersistedPerfilContadorDepartamento(perfilContadorDepartamento)
        );
    }

    @Test
    @Transactional
    void fullUpdatePerfilContadorDepartamentoWithPatch() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the perfilContadorDepartamento using partial update
        PerfilContadorDepartamento partialUpdatedPerfilContadorDepartamento = new PerfilContadorDepartamento();
        partialUpdatedPerfilContadorDepartamento.setId(perfilContadorDepartamento.getId());

        partialUpdatedPerfilContadorDepartamento
            .quantidadeEmpresas(UPDATED_QUANTIDADE_EMPRESAS)
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA);

        restPerfilContadorDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerfilContadorDepartamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPerfilContadorDepartamento))
            )
            .andExpect(status().isOk());

        // Validate the PerfilContadorDepartamento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPerfilContadorDepartamentoUpdatableFieldsEquals(
            partialUpdatedPerfilContadorDepartamento,
            getPersistedPerfilContadorDepartamento(partialUpdatedPerfilContadorDepartamento)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPerfilContadorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorDepartamento.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfilContadorDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, perfilContadorDepartamento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilContadorDepartamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContadorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerfilContadorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorDepartamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(perfilContadorDepartamento))
            )
            .andExpect(status().isBadRequest());

        // Validate the PerfilContadorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerfilContadorDepartamento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        perfilContadorDepartamento.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPerfilContadorDepartamentoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(perfilContadorDepartamento))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PerfilContadorDepartamento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerfilContadorDepartamento() throws Exception {
        // Initialize the database
        insertedPerfilContadorDepartamento = perfilContadorDepartamentoRepository.saveAndFlush(perfilContadorDepartamento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the perfilContadorDepartamento
        restPerfilContadorDepartamentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, perfilContadorDepartamento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return perfilContadorDepartamentoRepository.count();
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

    protected PerfilContadorDepartamento getPersistedPerfilContadorDepartamento(PerfilContadorDepartamento perfilContadorDepartamento) {
        return perfilContadorDepartamentoRepository.findById(perfilContadorDepartamento.getId()).orElseThrow();
    }

    protected void assertPersistedPerfilContadorDepartamentoToMatchAllProperties(
        PerfilContadorDepartamento expectedPerfilContadorDepartamento
    ) {
        assertPerfilContadorDepartamentoAllPropertiesEquals(
            expectedPerfilContadorDepartamento,
            getPersistedPerfilContadorDepartamento(expectedPerfilContadorDepartamento)
        );
    }

    protected void assertPersistedPerfilContadorDepartamentoToMatchUpdatableProperties(
        PerfilContadorDepartamento expectedPerfilContadorDepartamento
    ) {
        assertPerfilContadorDepartamentoAllUpdatablePropertiesEquals(
            expectedPerfilContadorDepartamento,
            getPersistedPerfilContadorDepartamento(expectedPerfilContadorDepartamento)
        );
    }
}
