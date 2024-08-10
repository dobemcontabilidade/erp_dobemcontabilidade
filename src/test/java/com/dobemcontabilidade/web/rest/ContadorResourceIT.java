package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.repository.ContadorRepository;
import com.dobemcontabilidade.service.ContadorService;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import com.dobemcontabilidade.service.mapper.ContadorMapper;
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
 * Integration tests for the {@link ContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContadorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CRC = "AAAAAAAAAA";
    private static final String UPDATED_CRC = "BBBBBBBBBB";

    private static final Integer DEFAULT_LIMITE_EMPRESAS = 1;
    private static final Integer UPDATED_LIMITE_EMPRESAS = 2;
    private static final Integer SMALLER_LIMITE_EMPRESAS = 1 - 1;

    private static final Integer DEFAULT_LIMITE_AREA_CONTABILS = 1;
    private static final Integer UPDATED_LIMITE_AREA_CONTABILS = 2;
    private static final Integer SMALLER_LIMITE_AREA_CONTABILS = 1 - 1;

    private static final Double DEFAULT_LIMITE_FATURAMENTO = 1D;
    private static final Double UPDATED_LIMITE_FATURAMENTO = 2D;
    private static final Double SMALLER_LIMITE_FATURAMENTO = 1D - 1D;

    private static final Integer DEFAULT_LIMITE_DEPARTAMENTOS = 1;
    private static final Integer UPDATED_LIMITE_DEPARTAMENTOS = 2;
    private static final Integer SMALLER_LIMITE_DEPARTAMENTOS = 1 - 1;

    private static final String ENTITY_API_URL = "/api/contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContadorRepository contadorRepository;

    @Mock
    private ContadorRepository contadorRepositoryMock;

    @Autowired
    private ContadorMapper contadorMapper;

    @Mock
    private ContadorService contadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContadorMockMvc;

    private Contador contador;

    private Contador insertedContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contador createEntity(EntityManager em) {
        Contador contador = new Contador()
            .nome(DEFAULT_NOME)
            .crc(DEFAULT_CRC)
            .limiteEmpresas(DEFAULT_LIMITE_EMPRESAS)
            .limiteAreaContabils(DEFAULT_LIMITE_AREA_CONTABILS)
            .limiteFaturamento(DEFAULT_LIMITE_FATURAMENTO)
            .limiteDepartamentos(DEFAULT_LIMITE_DEPARTAMENTOS);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        contador.setPessoa(pessoa);
        // Add required entity
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            perfilContador = PerfilContadorResourceIT.createEntity(em);
            em.persist(perfilContador);
            em.flush();
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        contador.setPerfilContador(perfilContador);
        return contador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contador createUpdatedEntity(EntityManager em) {
        Contador contador = new Contador()
            .nome(UPDATED_NOME)
            .crc(UPDATED_CRC)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteAreaContabils(UPDATED_LIMITE_AREA_CONTABILS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        contador.setPessoa(pessoa);
        // Add required entity
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            perfilContador = PerfilContadorResourceIT.createUpdatedEntity(em);
            em.persist(perfilContador);
            em.flush();
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        contador.setPerfilContador(perfilContador);
        return contador;
    }

    @BeforeEach
    public void initTest() {
        contador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedContador != null) {
            contadorRepository.delete(insertedContador);
            insertedContador = null;
        }
    }

    @Test
    @Transactional
    void createContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Contador
        ContadorDTO contadorDTO = contadorMapper.toDto(contador);
        var returnedContadorDTO = om.readValue(
            restContadorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contadorDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContadorDTO.class
        );

        // Validate the Contador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedContador = contadorMapper.toEntity(returnedContadorDTO);
        assertContadorUpdatableFieldsEquals(returnedContador, getPersistedContador(returnedContador));

        insertedContador = returnedContador;
    }

    @Test
    @Transactional
    void createContadorWithExistingId() throws Exception {
        // Create the Contador with an existing ID
        contador.setId(1L);
        ContadorDTO contadorDTO = contadorMapper.toDto(contador);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contadorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCrcIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        contador.setCrc(null);

        // Create the Contador, which fails.
        ContadorDTO contadorDTO = contadorMapper.toDto(contador);

        restContadorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contadorDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContadors() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList
        restContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].crc").value(hasItem(DEFAULT_CRC)))
            .andExpect(jsonPath("$.[*].limiteEmpresas").value(hasItem(DEFAULT_LIMITE_EMPRESAS)))
            .andExpect(jsonPath("$.[*].limiteAreaContabils").value(hasItem(DEFAULT_LIMITE_AREA_CONTABILS)))
            .andExpect(jsonPath("$.[*].limiteFaturamento").value(hasItem(DEFAULT_LIMITE_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].limiteDepartamentos").value(hasItem(DEFAULT_LIMITE_DEPARTAMENTOS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(contadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getContador() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get the contador
        restContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, contador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contador.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.crc").value(DEFAULT_CRC))
            .andExpect(jsonPath("$.limiteEmpresas").value(DEFAULT_LIMITE_EMPRESAS))
            .andExpect(jsonPath("$.limiteAreaContabils").value(DEFAULT_LIMITE_AREA_CONTABILS))
            .andExpect(jsonPath("$.limiteFaturamento").value(DEFAULT_LIMITE_FATURAMENTO.doubleValue()))
            .andExpect(jsonPath("$.limiteDepartamentos").value(DEFAULT_LIMITE_DEPARTAMENTOS));
    }

    @Test
    @Transactional
    void getContadorsByIdFiltering() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        Long id = contador.getId();

        defaultContadorFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultContadorFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultContadorFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContadorsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where nome equals to
        defaultContadorFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllContadorsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where nome in
        defaultContadorFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllContadorsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where nome is not null
        defaultContadorFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where nome contains
        defaultContadorFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllContadorsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where nome does not contain
        defaultContadorFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllContadorsByCrcIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where crc equals to
        defaultContadorFiltering("crc.equals=" + DEFAULT_CRC, "crc.equals=" + UPDATED_CRC);
    }

    @Test
    @Transactional
    void getAllContadorsByCrcIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where crc in
        defaultContadorFiltering("crc.in=" + DEFAULT_CRC + "," + UPDATED_CRC, "crc.in=" + UPDATED_CRC);
    }

    @Test
    @Transactional
    void getAllContadorsByCrcIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where crc is not null
        defaultContadorFiltering("crc.specified=true", "crc.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByCrcContainsSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where crc contains
        defaultContadorFiltering("crc.contains=" + DEFAULT_CRC, "crc.contains=" + UPDATED_CRC);
    }

    @Test
    @Transactional
    void getAllContadorsByCrcNotContainsSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where crc does not contain
        defaultContadorFiltering("crc.doesNotContain=" + UPDATED_CRC, "crc.doesNotContain=" + DEFAULT_CRC);
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas equals to
        defaultContadorFiltering("limiteEmpresas.equals=" + DEFAULT_LIMITE_EMPRESAS, "limiteEmpresas.equals=" + UPDATED_LIMITE_EMPRESAS);
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas in
        defaultContadorFiltering(
            "limiteEmpresas.in=" + DEFAULT_LIMITE_EMPRESAS + "," + UPDATED_LIMITE_EMPRESAS,
            "limiteEmpresas.in=" + UPDATED_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas is not null
        defaultContadorFiltering("limiteEmpresas.specified=true", "limiteEmpresas.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas is greater than or equal to
        defaultContadorFiltering(
            "limiteEmpresas.greaterThanOrEqual=" + DEFAULT_LIMITE_EMPRESAS,
            "limiteEmpresas.greaterThanOrEqual=" + UPDATED_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas is less than or equal to
        defaultContadorFiltering(
            "limiteEmpresas.lessThanOrEqual=" + DEFAULT_LIMITE_EMPRESAS,
            "limiteEmpresas.lessThanOrEqual=" + SMALLER_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas is less than
        defaultContadorFiltering(
            "limiteEmpresas.lessThan=" + UPDATED_LIMITE_EMPRESAS,
            "limiteEmpresas.lessThan=" + DEFAULT_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteEmpresasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteEmpresas is greater than
        defaultContadorFiltering(
            "limiteEmpresas.greaterThan=" + SMALLER_LIMITE_EMPRESAS,
            "limiteEmpresas.greaterThan=" + DEFAULT_LIMITE_EMPRESAS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteAreaContabilsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteAreaContabils equals to
        defaultContadorFiltering(
            "limiteAreaContabils.equals=" + DEFAULT_LIMITE_AREA_CONTABILS,
            "limiteAreaContabils.equals=" + UPDATED_LIMITE_AREA_CONTABILS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteAreaContabilsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteAreaContabils in
        defaultContadorFiltering(
            "limiteAreaContabils.in=" + DEFAULT_LIMITE_AREA_CONTABILS + "," + UPDATED_LIMITE_AREA_CONTABILS,
            "limiteAreaContabils.in=" + UPDATED_LIMITE_AREA_CONTABILS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteAreaContabilsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteAreaContabils is not null
        defaultContadorFiltering("limiteAreaContabils.specified=true", "limiteAreaContabils.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteAreaContabilsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteAreaContabils is greater than or equal to
        defaultContadorFiltering(
            "limiteAreaContabils.greaterThanOrEqual=" + DEFAULT_LIMITE_AREA_CONTABILS,
            "limiteAreaContabils.greaterThanOrEqual=" + UPDATED_LIMITE_AREA_CONTABILS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteAreaContabilsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteAreaContabils is less than or equal to
        defaultContadorFiltering(
            "limiteAreaContabils.lessThanOrEqual=" + DEFAULT_LIMITE_AREA_CONTABILS,
            "limiteAreaContabils.lessThanOrEqual=" + SMALLER_LIMITE_AREA_CONTABILS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteAreaContabilsIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteAreaContabils is less than
        defaultContadorFiltering(
            "limiteAreaContabils.lessThan=" + UPDATED_LIMITE_AREA_CONTABILS,
            "limiteAreaContabils.lessThan=" + DEFAULT_LIMITE_AREA_CONTABILS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteAreaContabilsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteAreaContabils is greater than
        defaultContadorFiltering(
            "limiteAreaContabils.greaterThan=" + SMALLER_LIMITE_AREA_CONTABILS,
            "limiteAreaContabils.greaterThan=" + DEFAULT_LIMITE_AREA_CONTABILS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento equals to
        defaultContadorFiltering(
            "limiteFaturamento.equals=" + DEFAULT_LIMITE_FATURAMENTO,
            "limiteFaturamento.equals=" + UPDATED_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento in
        defaultContadorFiltering(
            "limiteFaturamento.in=" + DEFAULT_LIMITE_FATURAMENTO + "," + UPDATED_LIMITE_FATURAMENTO,
            "limiteFaturamento.in=" + UPDATED_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento is not null
        defaultContadorFiltering("limiteFaturamento.specified=true", "limiteFaturamento.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento is greater than or equal to
        defaultContadorFiltering(
            "limiteFaturamento.greaterThanOrEqual=" + DEFAULT_LIMITE_FATURAMENTO,
            "limiteFaturamento.greaterThanOrEqual=" + UPDATED_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento is less than or equal to
        defaultContadorFiltering(
            "limiteFaturamento.lessThanOrEqual=" + DEFAULT_LIMITE_FATURAMENTO,
            "limiteFaturamento.lessThanOrEqual=" + SMALLER_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento is less than
        defaultContadorFiltering(
            "limiteFaturamento.lessThan=" + UPDATED_LIMITE_FATURAMENTO,
            "limiteFaturamento.lessThan=" + DEFAULT_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteFaturamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteFaturamento is greater than
        defaultContadorFiltering(
            "limiteFaturamento.greaterThan=" + SMALLER_LIMITE_FATURAMENTO,
            "limiteFaturamento.greaterThan=" + DEFAULT_LIMITE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos equals to
        defaultContadorFiltering(
            "limiteDepartamentos.equals=" + DEFAULT_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.equals=" + UPDATED_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos in
        defaultContadorFiltering(
            "limiteDepartamentos.in=" + DEFAULT_LIMITE_DEPARTAMENTOS + "," + UPDATED_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.in=" + UPDATED_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos is not null
        defaultContadorFiltering("limiteDepartamentos.specified=true", "limiteDepartamentos.specified=false");
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos is greater than or equal to
        defaultContadorFiltering(
            "limiteDepartamentos.greaterThanOrEqual=" + DEFAULT_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.greaterThanOrEqual=" + UPDATED_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos is less than or equal to
        defaultContadorFiltering(
            "limiteDepartamentos.lessThanOrEqual=" + DEFAULT_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.lessThanOrEqual=" + SMALLER_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos is less than
        defaultContadorFiltering(
            "limiteDepartamentos.lessThan=" + UPDATED_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.lessThan=" + DEFAULT_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByLimiteDepartamentosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        // Get all the contadorList where limiteDepartamentos is greater than
        defaultContadorFiltering(
            "limiteDepartamentos.greaterThan=" + SMALLER_LIMITE_DEPARTAMENTOS,
            "limiteDepartamentos.greaterThan=" + DEFAULT_LIMITE_DEPARTAMENTOS
        );
    }

    @Test
    @Transactional
    void getAllContadorsByPessoaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Pessoa pessoa = contador.getPessoa();
        contadorRepository.saveAndFlush(contador);
        Long pessoaId = pessoa.getId();
        // Get all the contadorList where pessoa equals to pessoaId
        defaultContadorShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the contadorList where pessoa equals to (pessoaId + 1)
        defaultContadorShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    @Test
    @Transactional
    void getAllContadorsByPerfilContadorIsEqualToSomething() throws Exception {
        PerfilContador perfilContador;
        if (TestUtil.findAll(em, PerfilContador.class).isEmpty()) {
            contadorRepository.saveAndFlush(contador);
            perfilContador = PerfilContadorResourceIT.createEntity(em);
        } else {
            perfilContador = TestUtil.findAll(em, PerfilContador.class).get(0);
        }
        em.persist(perfilContador);
        em.flush();
        contador.setPerfilContador(perfilContador);
        contadorRepository.saveAndFlush(contador);
        Long perfilContadorId = perfilContador.getId();
        // Get all the contadorList where perfilContador equals to perfilContadorId
        defaultContadorShouldBeFound("perfilContadorId.equals=" + perfilContadorId);

        // Get all the contadorList where perfilContador equals to (perfilContadorId + 1)
        defaultContadorShouldNotBeFound("perfilContadorId.equals=" + (perfilContadorId + 1));
    }

    private void defaultContadorFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultContadorShouldBeFound(shouldBeFound);
        defaultContadorShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContadorShouldBeFound(String filter) throws Exception {
        restContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].crc").value(hasItem(DEFAULT_CRC)))
            .andExpect(jsonPath("$.[*].limiteEmpresas").value(hasItem(DEFAULT_LIMITE_EMPRESAS)))
            .andExpect(jsonPath("$.[*].limiteAreaContabils").value(hasItem(DEFAULT_LIMITE_AREA_CONTABILS)))
            .andExpect(jsonPath("$.[*].limiteFaturamento").value(hasItem(DEFAULT_LIMITE_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].limiteDepartamentos").value(hasItem(DEFAULT_LIMITE_DEPARTAMENTOS)));

        // Check, that the count call also returns 1
        restContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContadorShouldNotBeFound(String filter) throws Exception {
        restContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContadorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContador() throws Exception {
        // Get the contador
        restContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContador() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contador
        Contador updatedContador = contadorRepository.findById(contador.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContador are not directly saved in db
        em.detach(updatedContador);
        updatedContador
            .nome(UPDATED_NOME)
            .crc(UPDATED_CRC)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteAreaContabils(UPDATED_LIMITE_AREA_CONTABILS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS);
        ContadorDTO contadorDTO = contadorMapper.toDto(updatedContador);

        restContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contadorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContadorToMatchAllProperties(updatedContador);
    }

    @Test
    @Transactional
    void putNonExistingContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // Create the Contador
        ContadorDTO contadorDTO = contadorMapper.toDto(contador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contadorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // Create the Contador
        ContadorDTO contadorDTO = contadorMapper.toDto(contador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // Create the Contador
        ContadorDTO contadorDTO = contadorMapper.toDto(contador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContadorWithPatch() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contador using partial update
        Contador partialUpdatedContador = new Contador();
        partialUpdatedContador.setId(contador.getId());

        partialUpdatedContador
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS);

        restContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContador))
            )
            .andExpect(status().isOk());

        // Validate the Contador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContadorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedContador, contador), getPersistedContador(contador));
    }

    @Test
    @Transactional
    void fullUpdateContadorWithPatch() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contador using partial update
        Contador partialUpdatedContador = new Contador();
        partialUpdatedContador.setId(contador.getId());

        partialUpdatedContador
            .nome(UPDATED_NOME)
            .crc(UPDATED_CRC)
            .limiteEmpresas(UPDATED_LIMITE_EMPRESAS)
            .limiteAreaContabils(UPDATED_LIMITE_AREA_CONTABILS)
            .limiteFaturamento(UPDATED_LIMITE_FATURAMENTO)
            .limiteDepartamentos(UPDATED_LIMITE_DEPARTAMENTOS);

        restContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContador))
            )
            .andExpect(status().isOk());

        // Validate the Contador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContadorUpdatableFieldsEquals(partialUpdatedContador, getPersistedContador(partialUpdatedContador));
    }

    @Test
    @Transactional
    void patchNonExistingContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // Create the Contador
        ContadorDTO contadorDTO = contadorMapper.toDto(contador);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contadorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // Create the Contador
        ContadorDTO contadorDTO = contadorMapper.toDto(contador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contadorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contador.setId(longCount.incrementAndGet());

        // Create the Contador
        ContadorDTO contadorDTO = contadorMapper.toDto(contador);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContadorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(contadorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContador() throws Exception {
        // Initialize the database
        insertedContador = contadorRepository.saveAndFlush(contador);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contador
        restContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, contador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contadorRepository.count();
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

    protected Contador getPersistedContador(Contador contador) {
        return contadorRepository.findById(contador.getId()).orElseThrow();
    }

    protected void assertPersistedContadorToMatchAllProperties(Contador expectedContador) {
        assertContadorAllPropertiesEquals(expectedContador, getPersistedContador(expectedContador));
    }

    protected void assertPersistedContadorToMatchUpdatableProperties(Contador expectedContador) {
        assertContadorAllUpdatablePropertiesEquals(expectedContador, getPersistedContador(expectedContador));
    }
}
