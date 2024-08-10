package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DepartamentoContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.domain.DepartamentoContador;
import com.dobemcontabilidade.repository.DepartamentoContadorRepository;
import com.dobemcontabilidade.service.DepartamentoContadorService;
import com.dobemcontabilidade.service.dto.DepartamentoContadorDTO;
import com.dobemcontabilidade.service.mapper.DepartamentoContadorMapper;
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
 * Integration tests for the {@link DepartamentoContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DepartamentoContadorResourceIT {

    private static final Double DEFAULT_PERCENTUAL_EXPERIENCIA = 1D;
    private static final Double UPDATED_PERCENTUAL_EXPERIENCIA = 2D;
    private static final Double SMALLER_PERCENTUAL_EXPERIENCIA = 1D - 1D;

    private static final String DEFAULT_DESCRICAO_EXPERIENCIA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_EXPERIENCIA = "BBBBBBBBBB";

    private static final Double DEFAULT_PONTUACAO_ENTREVISTA = 1D;
    private static final Double UPDATED_PONTUACAO_ENTREVISTA = 2D;
    private static final Double SMALLER_PONTUACAO_ENTREVISTA = 1D - 1D;

    private static final Double DEFAULT_PONTUACAO_AVALIACAO = 1D;
    private static final Double UPDATED_PONTUACAO_AVALIACAO = 2D;
    private static final Double SMALLER_PONTUACAO_AVALIACAO = 1D - 1D;

    private static final String ENTITY_API_URL = "/api/departamento-contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DepartamentoContadorRepository departamentoContadorRepository;

    @Mock
    private DepartamentoContadorRepository departamentoContadorRepositoryMock;

    @Autowired
    private DepartamentoContadorMapper departamentoContadorMapper;

    @Mock
    private DepartamentoContadorService departamentoContadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartamentoContadorMockMvc;

    private DepartamentoContador departamentoContador;

    private DepartamentoContador insertedDepartamentoContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartamentoContador createEntity(EntityManager em) {
        DepartamentoContador departamentoContador = new DepartamentoContador()
            .percentualExperiencia(DEFAULT_PERCENTUAL_EXPERIENCIA)
            .descricaoExperiencia(DEFAULT_DESCRICAO_EXPERIENCIA)
            .pontuacaoEntrevista(DEFAULT_PONTUACAO_ENTREVISTA)
            .pontuacaoAvaliacao(DEFAULT_PONTUACAO_AVALIACAO);
        // Add required entity
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamento = DepartamentoResourceIT.createEntity(em);
            em.persist(departamento);
            em.flush();
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        departamentoContador.setDepartamento(departamento);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        departamentoContador.setContador(contador);
        return departamentoContador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartamentoContador createUpdatedEntity(EntityManager em) {
        DepartamentoContador departamentoContador = new DepartamentoContador()
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA)
            .descricaoExperiencia(UPDATED_DESCRICAO_EXPERIENCIA)
            .pontuacaoEntrevista(UPDATED_PONTUACAO_ENTREVISTA)
            .pontuacaoAvaliacao(UPDATED_PONTUACAO_AVALIACAO);
        // Add required entity
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamento = DepartamentoResourceIT.createUpdatedEntity(em);
            em.persist(departamento);
            em.flush();
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        departamentoContador.setDepartamento(departamento);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        departamentoContador.setContador(contador);
        return departamentoContador;
    }

    @BeforeEach
    public void initTest() {
        departamentoContador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDepartamentoContador != null) {
            departamentoContadorRepository.delete(insertedDepartamentoContador);
            insertedDepartamentoContador = null;
        }
    }

    @Test
    @Transactional
    void createDepartamentoContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DepartamentoContador
        DepartamentoContadorDTO departamentoContadorDTO = departamentoContadorMapper.toDto(departamentoContador);
        var returnedDepartamentoContadorDTO = om.readValue(
            restDepartamentoContadorMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamentoContadorDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DepartamentoContadorDTO.class
        );

        // Validate the DepartamentoContador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDepartamentoContador = departamentoContadorMapper.toEntity(returnedDepartamentoContadorDTO);
        assertDepartamentoContadorUpdatableFieldsEquals(
            returnedDepartamentoContador,
            getPersistedDepartamentoContador(returnedDepartamentoContador)
        );

        insertedDepartamentoContador = returnedDepartamentoContador;
    }

    @Test
    @Transactional
    void createDepartamentoContadorWithExistingId() throws Exception {
        // Create the DepartamentoContador with an existing ID
        departamentoContador.setId(1L);
        DepartamentoContadorDTO departamentoContadorDTO = departamentoContadorMapper.toDto(departamentoContador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentoContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamentoContadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDepartamentoContadors() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList
        restDepartamentoContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamentoContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentualExperiencia").value(hasItem(DEFAULT_PERCENTUAL_EXPERIENCIA.doubleValue())))
            .andExpect(jsonPath("$.[*].descricaoExperiencia").value(hasItem(DEFAULT_DESCRICAO_EXPERIENCIA)))
            .andExpect(jsonPath("$.[*].pontuacaoEntrevista").value(hasItem(DEFAULT_PONTUACAO_ENTREVISTA.doubleValue())))
            .andExpect(jsonPath("$.[*].pontuacaoAvaliacao").value(hasItem(DEFAULT_PONTUACAO_AVALIACAO.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDepartamentoContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(departamentoContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDepartamentoContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(departamentoContadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDepartamentoContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(departamentoContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDepartamentoContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(departamentoContadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDepartamentoContador() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get the departamentoContador
        restDepartamentoContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, departamentoContador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departamentoContador.getId().intValue()))
            .andExpect(jsonPath("$.percentualExperiencia").value(DEFAULT_PERCENTUAL_EXPERIENCIA.doubleValue()))
            .andExpect(jsonPath("$.descricaoExperiencia").value(DEFAULT_DESCRICAO_EXPERIENCIA))
            .andExpect(jsonPath("$.pontuacaoEntrevista").value(DEFAULT_PONTUACAO_ENTREVISTA.doubleValue()))
            .andExpect(jsonPath("$.pontuacaoAvaliacao").value(DEFAULT_PONTUACAO_AVALIACAO.doubleValue()));
    }

    @Test
    @Transactional
    void getDepartamentoContadorsByIdFiltering() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        Long id = departamentoContador.getId();

        defaultDepartamentoContadorFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDepartamentoContadorFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDepartamentoContadorFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPercentualExperienciaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where percentualExperiencia equals to
        defaultDepartamentoContadorFiltering(
            "percentualExperiencia.equals=" + DEFAULT_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.equals=" + UPDATED_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPercentualExperienciaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where percentualExperiencia in
        defaultDepartamentoContadorFiltering(
            "percentualExperiencia.in=" + DEFAULT_PERCENTUAL_EXPERIENCIA + "," + UPDATED_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.in=" + UPDATED_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPercentualExperienciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where percentualExperiencia is not null
        defaultDepartamentoContadorFiltering("percentualExperiencia.specified=true", "percentualExperiencia.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPercentualExperienciaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where percentualExperiencia is greater than or equal to
        defaultDepartamentoContadorFiltering(
            "percentualExperiencia.greaterThanOrEqual=" + DEFAULT_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.greaterThanOrEqual=" + UPDATED_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPercentualExperienciaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where percentualExperiencia is less than or equal to
        defaultDepartamentoContadorFiltering(
            "percentualExperiencia.lessThanOrEqual=" + DEFAULT_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.lessThanOrEqual=" + SMALLER_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPercentualExperienciaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where percentualExperiencia is less than
        defaultDepartamentoContadorFiltering(
            "percentualExperiencia.lessThan=" + UPDATED_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.lessThan=" + DEFAULT_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPercentualExperienciaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where percentualExperiencia is greater than
        defaultDepartamentoContadorFiltering(
            "percentualExperiencia.greaterThan=" + SMALLER_PERCENTUAL_EXPERIENCIA,
            "percentualExperiencia.greaterThan=" + DEFAULT_PERCENTUAL_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByDescricaoExperienciaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where descricaoExperiencia equals to
        defaultDepartamentoContadorFiltering(
            "descricaoExperiencia.equals=" + DEFAULT_DESCRICAO_EXPERIENCIA,
            "descricaoExperiencia.equals=" + UPDATED_DESCRICAO_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByDescricaoExperienciaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where descricaoExperiencia in
        defaultDepartamentoContadorFiltering(
            "descricaoExperiencia.in=" + DEFAULT_DESCRICAO_EXPERIENCIA + "," + UPDATED_DESCRICAO_EXPERIENCIA,
            "descricaoExperiencia.in=" + UPDATED_DESCRICAO_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByDescricaoExperienciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where descricaoExperiencia is not null
        defaultDepartamentoContadorFiltering("descricaoExperiencia.specified=true", "descricaoExperiencia.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByDescricaoExperienciaContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where descricaoExperiencia contains
        defaultDepartamentoContadorFiltering(
            "descricaoExperiencia.contains=" + DEFAULT_DESCRICAO_EXPERIENCIA,
            "descricaoExperiencia.contains=" + UPDATED_DESCRICAO_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByDescricaoExperienciaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where descricaoExperiencia does not contain
        defaultDepartamentoContadorFiltering(
            "descricaoExperiencia.doesNotContain=" + UPDATED_DESCRICAO_EXPERIENCIA,
            "descricaoExperiencia.doesNotContain=" + DEFAULT_DESCRICAO_EXPERIENCIA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoEntrevistaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoEntrevista equals to
        defaultDepartamentoContadorFiltering(
            "pontuacaoEntrevista.equals=" + DEFAULT_PONTUACAO_ENTREVISTA,
            "pontuacaoEntrevista.equals=" + UPDATED_PONTUACAO_ENTREVISTA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoEntrevistaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoEntrevista in
        defaultDepartamentoContadorFiltering(
            "pontuacaoEntrevista.in=" + DEFAULT_PONTUACAO_ENTREVISTA + "," + UPDATED_PONTUACAO_ENTREVISTA,
            "pontuacaoEntrevista.in=" + UPDATED_PONTUACAO_ENTREVISTA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoEntrevistaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoEntrevista is not null
        defaultDepartamentoContadorFiltering("pontuacaoEntrevista.specified=true", "pontuacaoEntrevista.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoEntrevistaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoEntrevista is greater than or equal to
        defaultDepartamentoContadorFiltering(
            "pontuacaoEntrevista.greaterThanOrEqual=" + DEFAULT_PONTUACAO_ENTREVISTA,
            "pontuacaoEntrevista.greaterThanOrEqual=" + UPDATED_PONTUACAO_ENTREVISTA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoEntrevistaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoEntrevista is less than or equal to
        defaultDepartamentoContadorFiltering(
            "pontuacaoEntrevista.lessThanOrEqual=" + DEFAULT_PONTUACAO_ENTREVISTA,
            "pontuacaoEntrevista.lessThanOrEqual=" + SMALLER_PONTUACAO_ENTREVISTA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoEntrevistaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoEntrevista is less than
        defaultDepartamentoContadorFiltering(
            "pontuacaoEntrevista.lessThan=" + UPDATED_PONTUACAO_ENTREVISTA,
            "pontuacaoEntrevista.lessThan=" + DEFAULT_PONTUACAO_ENTREVISTA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoEntrevistaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoEntrevista is greater than
        defaultDepartamentoContadorFiltering(
            "pontuacaoEntrevista.greaterThan=" + SMALLER_PONTUACAO_ENTREVISTA,
            "pontuacaoEntrevista.greaterThan=" + DEFAULT_PONTUACAO_ENTREVISTA
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoAvaliacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoAvaliacao equals to
        defaultDepartamentoContadorFiltering(
            "pontuacaoAvaliacao.equals=" + DEFAULT_PONTUACAO_AVALIACAO,
            "pontuacaoAvaliacao.equals=" + UPDATED_PONTUACAO_AVALIACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoAvaliacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoAvaliacao in
        defaultDepartamentoContadorFiltering(
            "pontuacaoAvaliacao.in=" + DEFAULT_PONTUACAO_AVALIACAO + "," + UPDATED_PONTUACAO_AVALIACAO,
            "pontuacaoAvaliacao.in=" + UPDATED_PONTUACAO_AVALIACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoAvaliacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoAvaliacao is not null
        defaultDepartamentoContadorFiltering("pontuacaoAvaliacao.specified=true", "pontuacaoAvaliacao.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoAvaliacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoAvaliacao is greater than or equal to
        defaultDepartamentoContadorFiltering(
            "pontuacaoAvaliacao.greaterThanOrEqual=" + DEFAULT_PONTUACAO_AVALIACAO,
            "pontuacaoAvaliacao.greaterThanOrEqual=" + UPDATED_PONTUACAO_AVALIACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoAvaliacaoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoAvaliacao is less than or equal to
        defaultDepartamentoContadorFiltering(
            "pontuacaoAvaliacao.lessThanOrEqual=" + DEFAULT_PONTUACAO_AVALIACAO,
            "pontuacaoAvaliacao.lessThanOrEqual=" + SMALLER_PONTUACAO_AVALIACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoAvaliacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoAvaliacao is less than
        defaultDepartamentoContadorFiltering(
            "pontuacaoAvaliacao.lessThan=" + UPDATED_PONTUACAO_AVALIACAO,
            "pontuacaoAvaliacao.lessThan=" + DEFAULT_PONTUACAO_AVALIACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByPontuacaoAvaliacaoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        // Get all the departamentoContadorList where pontuacaoAvaliacao is greater than
        defaultDepartamentoContadorFiltering(
            "pontuacaoAvaliacao.greaterThan=" + SMALLER_PONTUACAO_AVALIACAO,
            "pontuacaoAvaliacao.greaterThan=" + DEFAULT_PONTUACAO_AVALIACAO
        );
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByDepartamentoIsEqualToSomething() throws Exception {
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamentoContadorRepository.saveAndFlush(departamentoContador);
            departamento = DepartamentoResourceIT.createEntity(em);
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        em.persist(departamento);
        em.flush();
        departamentoContador.setDepartamento(departamento);
        departamentoContadorRepository.saveAndFlush(departamentoContador);
        Long departamentoId = departamento.getId();
        // Get all the departamentoContadorList where departamento equals to departamentoId
        defaultDepartamentoContadorShouldBeFound("departamentoId.equals=" + departamentoId);

        // Get all the departamentoContadorList where departamento equals to (departamentoId + 1)
        defaultDepartamentoContadorShouldNotBeFound("departamentoId.equals=" + (departamentoId + 1));
    }

    @Test
    @Transactional
    void getAllDepartamentoContadorsByContadorIsEqualToSomething() throws Exception {
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            departamentoContadorRepository.saveAndFlush(departamentoContador);
            contador = ContadorResourceIT.createEntity(em);
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        em.persist(contador);
        em.flush();
        departamentoContador.setContador(contador);
        departamentoContadorRepository.saveAndFlush(departamentoContador);
        Long contadorId = contador.getId();
        // Get all the departamentoContadorList where contador equals to contadorId
        defaultDepartamentoContadorShouldBeFound("contadorId.equals=" + contadorId);

        // Get all the departamentoContadorList where contador equals to (contadorId + 1)
        defaultDepartamentoContadorShouldNotBeFound("contadorId.equals=" + (contadorId + 1));
    }

    private void defaultDepartamentoContadorFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDepartamentoContadorShouldBeFound(shouldBeFound);
        defaultDepartamentoContadorShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartamentoContadorShouldBeFound(String filter) throws Exception {
        restDepartamentoContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamentoContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentualExperiencia").value(hasItem(DEFAULT_PERCENTUAL_EXPERIENCIA.doubleValue())))
            .andExpect(jsonPath("$.[*].descricaoExperiencia").value(hasItem(DEFAULT_DESCRICAO_EXPERIENCIA)))
            .andExpect(jsonPath("$.[*].pontuacaoEntrevista").value(hasItem(DEFAULT_PONTUACAO_ENTREVISTA.doubleValue())))
            .andExpect(jsonPath("$.[*].pontuacaoAvaliacao").value(hasItem(DEFAULT_PONTUACAO_AVALIACAO.doubleValue())));

        // Check, that the count call also returns 1
        restDepartamentoContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepartamentoContadorShouldNotBeFound(String filter) throws Exception {
        restDepartamentoContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartamentoContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDepartamentoContador() throws Exception {
        // Get the departamentoContador
        restDepartamentoContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDepartamentoContador() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamentoContador
        DepartamentoContador updatedDepartamentoContador = departamentoContadorRepository
            .findById(departamentoContador.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDepartamentoContador are not directly saved in db
        em.detach(updatedDepartamentoContador);
        updatedDepartamentoContador
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA)
            .descricaoExperiencia(UPDATED_DESCRICAO_EXPERIENCIA)
            .pontuacaoEntrevista(UPDATED_PONTUACAO_ENTREVISTA)
            .pontuacaoAvaliacao(UPDATED_PONTUACAO_AVALIACAO);
        DepartamentoContadorDTO departamentoContadorDTO = departamentoContadorMapper.toDto(updatedDepartamentoContador);

        restDepartamentoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departamentoContadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamentoContadorDTO))
            )
            .andExpect(status().isOk());

        // Validate the DepartamentoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDepartamentoContadorToMatchAllProperties(updatedDepartamentoContador);
    }

    @Test
    @Transactional
    void putNonExistingDepartamentoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoContador.setId(longCount.incrementAndGet());

        // Create the DepartamentoContador
        DepartamentoContadorDTO departamentoContadorDTO = departamentoContadorMapper.toDto(departamentoContador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departamentoContadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamentoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepartamentoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoContador.setId(longCount.incrementAndGet());

        // Create the DepartamentoContador
        DepartamentoContadorDTO departamentoContadorDTO = departamentoContadorMapper.toDto(departamentoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamentoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepartamentoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoContador.setId(longCount.incrementAndGet());

        // Create the DepartamentoContador
        DepartamentoContadorDTO departamentoContadorDTO = departamentoContadorMapper.toDto(departamentoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamentoContadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DepartamentoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepartamentoContadorWithPatch() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamentoContador using partial update
        DepartamentoContador partialUpdatedDepartamentoContador = new DepartamentoContador();
        partialUpdatedDepartamentoContador.setId(departamentoContador.getId());

        partialUpdatedDepartamentoContador.pontuacaoEntrevista(UPDATED_PONTUACAO_ENTREVISTA);

        restDepartamentoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamentoContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartamentoContador))
            )
            .andExpect(status().isOk());

        // Validate the DepartamentoContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartamentoContadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDepartamentoContador, departamentoContador),
            getPersistedDepartamentoContador(departamentoContador)
        );
    }

    @Test
    @Transactional
    void fullUpdateDepartamentoContadorWithPatch() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamentoContador using partial update
        DepartamentoContador partialUpdatedDepartamentoContador = new DepartamentoContador();
        partialUpdatedDepartamentoContador.setId(departamentoContador.getId());

        partialUpdatedDepartamentoContador
            .percentualExperiencia(UPDATED_PERCENTUAL_EXPERIENCIA)
            .descricaoExperiencia(UPDATED_DESCRICAO_EXPERIENCIA)
            .pontuacaoEntrevista(UPDATED_PONTUACAO_ENTREVISTA)
            .pontuacaoAvaliacao(UPDATED_PONTUACAO_AVALIACAO);

        restDepartamentoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamentoContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartamentoContador))
            )
            .andExpect(status().isOk());

        // Validate the DepartamentoContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartamentoContadorUpdatableFieldsEquals(
            partialUpdatedDepartamentoContador,
            getPersistedDepartamentoContador(partialUpdatedDepartamentoContador)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDepartamentoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoContador.setId(longCount.incrementAndGet());

        // Create the DepartamentoContador
        DepartamentoContadorDTO departamentoContadorDTO = departamentoContadorMapper.toDto(departamentoContador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, departamentoContadorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departamentoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepartamentoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoContador.setId(longCount.incrementAndGet());

        // Create the DepartamentoContador
        DepartamentoContadorDTO departamentoContadorDTO = departamentoContadorMapper.toDto(departamentoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departamentoContadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepartamentoContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoContador.setId(longCount.incrementAndGet());

        // Create the DepartamentoContador
        DepartamentoContadorDTO departamentoContadorDTO = departamentoContadorMapper.toDto(departamentoContador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoContadorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(departamentoContadorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DepartamentoContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepartamentoContador() throws Exception {
        // Initialize the database
        insertedDepartamentoContador = departamentoContadorRepository.saveAndFlush(departamentoContador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the departamentoContador
        restDepartamentoContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, departamentoContador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return departamentoContadorRepository.count();
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

    protected DepartamentoContador getPersistedDepartamentoContador(DepartamentoContador departamentoContador) {
        return departamentoContadorRepository.findById(departamentoContador.getId()).orElseThrow();
    }

    protected void assertPersistedDepartamentoContadorToMatchAllProperties(DepartamentoContador expectedDepartamentoContador) {
        assertDepartamentoContadorAllPropertiesEquals(
            expectedDepartamentoContador,
            getPersistedDepartamentoContador(expectedDepartamentoContador)
        );
    }

    protected void assertPersistedDepartamentoContadorToMatchUpdatableProperties(DepartamentoContador expectedDepartamentoContador) {
        assertDepartamentoContadorAllUpdatablePropertiesEquals(
            expectedDepartamentoContador,
            getPersistedDepartamentoContador(expectedDepartamentoContador)
        );
    }
}
