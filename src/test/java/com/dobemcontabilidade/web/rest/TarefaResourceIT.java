package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TarefaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Competencia;
import com.dobemcontabilidade.domain.Esfera;
import com.dobemcontabilidade.domain.Frequencia;
import com.dobemcontabilidade.domain.Tarefa;
import com.dobemcontabilidade.domain.enumeration.TipoTarefaEnum;
import com.dobemcontabilidade.repository.TarefaRepository;
import com.dobemcontabilidade.service.TarefaService;
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
 * Integration tests for the {@link TarefaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TarefaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_DIAS = 1;
    private static final Integer UPDATED_NUMERO_DIAS = 2;
    private static final Integer SMALLER_NUMERO_DIAS = 1 - 1;

    private static final Boolean DEFAULT_DIA_UTIL = false;
    private static final Boolean UPDATED_DIA_UTIL = true;

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;
    private static final Double SMALLER_VALOR = 1D - 1D;

    private static final Boolean DEFAULT_NOTIFICAR_CLIENTE = false;
    private static final Boolean UPDATED_NOTIFICAR_CLIENTE = true;

    private static final Boolean DEFAULT_GERA_MULTA = false;
    private static final Boolean UPDATED_GERA_MULTA = true;

    private static final Boolean DEFAULT_EXIBIR_EMPRESA = false;
    private static final Boolean UPDATED_EXIBIR_EMPRESA = true;

    private static final Instant DEFAULT_DATA_LEGAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_LEGAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PONTOS = 1;
    private static final Integer UPDATED_PONTOS = 2;
    private static final Integer SMALLER_PONTOS = 1 - 1;

    private static final TipoTarefaEnum DEFAULT_TIPO_TAREFA = TipoTarefaEnum.RECORRENTE;
    private static final TipoTarefaEnum UPDATED_TIPO_TAREFA = TipoTarefaEnum.ORDEMSERVICO;

    private static final String ENTITY_API_URL = "/api/tarefas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Mock
    private TarefaRepository tarefaRepositoryMock;

    @Mock
    private TarefaService tarefaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarefaMockMvc;

    private Tarefa tarefa;

    private Tarefa insertedTarefa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarefa createEntity(EntityManager em) {
        Tarefa tarefa = new Tarefa()
            .titulo(DEFAULT_TITULO)
            .numeroDias(DEFAULT_NUMERO_DIAS)
            .diaUtil(DEFAULT_DIA_UTIL)
            .valor(DEFAULT_VALOR)
            .notificarCliente(DEFAULT_NOTIFICAR_CLIENTE)
            .geraMulta(DEFAULT_GERA_MULTA)
            .exibirEmpresa(DEFAULT_EXIBIR_EMPRESA)
            .dataLegal(DEFAULT_DATA_LEGAL)
            .pontos(DEFAULT_PONTOS)
            .tipoTarefa(DEFAULT_TIPO_TAREFA);
        // Add required entity
        Esfera esfera;
        if (TestUtil.findAll(em, Esfera.class).isEmpty()) {
            esfera = EsferaResourceIT.createEntity(em);
            em.persist(esfera);
            em.flush();
        } else {
            esfera = TestUtil.findAll(em, Esfera.class).get(0);
        }
        tarefa.setEsfera(esfera);
        // Add required entity
        Frequencia frequencia;
        if (TestUtil.findAll(em, Frequencia.class).isEmpty()) {
            frequencia = FrequenciaResourceIT.createEntity(em);
            em.persist(frequencia);
            em.flush();
        } else {
            frequencia = TestUtil.findAll(em, Frequencia.class).get(0);
        }
        tarefa.setFrequencia(frequencia);
        // Add required entity
        Competencia competencia;
        if (TestUtil.findAll(em, Competencia.class).isEmpty()) {
            competencia = CompetenciaResourceIT.createEntity(em);
            em.persist(competencia);
            em.flush();
        } else {
            competencia = TestUtil.findAll(em, Competencia.class).get(0);
        }
        tarefa.setCompetencia(competencia);
        return tarefa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarefa createUpdatedEntity(EntityManager em) {
        Tarefa tarefa = new Tarefa()
            .titulo(UPDATED_TITULO)
            .numeroDias(UPDATED_NUMERO_DIAS)
            .diaUtil(UPDATED_DIA_UTIL)
            .valor(UPDATED_VALOR)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .geraMulta(UPDATED_GERA_MULTA)
            .exibirEmpresa(UPDATED_EXIBIR_EMPRESA)
            .dataLegal(UPDATED_DATA_LEGAL)
            .pontos(UPDATED_PONTOS)
            .tipoTarefa(UPDATED_TIPO_TAREFA);
        // Add required entity
        Esfera esfera;
        if (TestUtil.findAll(em, Esfera.class).isEmpty()) {
            esfera = EsferaResourceIT.createUpdatedEntity(em);
            em.persist(esfera);
            em.flush();
        } else {
            esfera = TestUtil.findAll(em, Esfera.class).get(0);
        }
        tarefa.setEsfera(esfera);
        // Add required entity
        Frequencia frequencia;
        if (TestUtil.findAll(em, Frequencia.class).isEmpty()) {
            frequencia = FrequenciaResourceIT.createUpdatedEntity(em);
            em.persist(frequencia);
            em.flush();
        } else {
            frequencia = TestUtil.findAll(em, Frequencia.class).get(0);
        }
        tarefa.setFrequencia(frequencia);
        // Add required entity
        Competencia competencia;
        if (TestUtil.findAll(em, Competencia.class).isEmpty()) {
            competencia = CompetenciaResourceIT.createUpdatedEntity(em);
            em.persist(competencia);
            em.flush();
        } else {
            competencia = TestUtil.findAll(em, Competencia.class).get(0);
        }
        tarefa.setCompetencia(competencia);
        return tarefa;
    }

    @BeforeEach
    public void initTest() {
        tarefa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTarefa != null) {
            tarefaRepository.delete(insertedTarefa);
            insertedTarefa = null;
        }
    }

    @Test
    @Transactional
    void createTarefa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tarefa
        var returnedTarefa = om.readValue(
            restTarefaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Tarefa.class
        );

        // Validate the Tarefa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTarefaUpdatableFieldsEquals(returnedTarefa, getPersistedTarefa(returnedTarefa));

        insertedTarefa = returnedTarefa;
    }

    @Test
    @Transactional
    void createTarefaWithExistingId() throws Exception {
        // Create the Tarefa with an existing ID
        tarefa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarefaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefa)))
            .andExpect(status().isBadRequest());

        // Validate the Tarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTarefas() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList
        restTarefaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefa.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].numeroDias").value(hasItem(DEFAULT_NUMERO_DIAS)))
            .andExpect(jsonPath("$.[*].diaUtil").value(hasItem(DEFAULT_DIA_UTIL.booleanValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].notificarCliente").value(hasItem(DEFAULT_NOTIFICAR_CLIENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].geraMulta").value(hasItem(DEFAULT_GERA_MULTA.booleanValue())))
            .andExpect(jsonPath("$.[*].exibirEmpresa").value(hasItem(DEFAULT_EXIBIR_EMPRESA.booleanValue())))
            .andExpect(jsonPath("$.[*].dataLegal").value(hasItem(DEFAULT_DATA_LEGAL.toString())))
            .andExpect(jsonPath("$.[*].pontos").value(hasItem(DEFAULT_PONTOS)))
            .andExpect(jsonPath("$.[*].tipoTarefa").value(hasItem(DEFAULT_TIPO_TAREFA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTarefasWithEagerRelationshipsIsEnabled() throws Exception {
        when(tarefaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTarefaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tarefaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTarefasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tarefaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTarefaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tarefaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTarefa() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get the tarefa
        restTarefaMockMvc
            .perform(get(ENTITY_API_URL_ID, tarefa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarefa.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.numeroDias").value(DEFAULT_NUMERO_DIAS))
            .andExpect(jsonPath("$.diaUtil").value(DEFAULT_DIA_UTIL.booleanValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.notificarCliente").value(DEFAULT_NOTIFICAR_CLIENTE.booleanValue()))
            .andExpect(jsonPath("$.geraMulta").value(DEFAULT_GERA_MULTA.booleanValue()))
            .andExpect(jsonPath("$.exibirEmpresa").value(DEFAULT_EXIBIR_EMPRESA.booleanValue()))
            .andExpect(jsonPath("$.dataLegal").value(DEFAULT_DATA_LEGAL.toString()))
            .andExpect(jsonPath("$.pontos").value(DEFAULT_PONTOS))
            .andExpect(jsonPath("$.tipoTarefa").value(DEFAULT_TIPO_TAREFA.toString()));
    }

    @Test
    @Transactional
    void getTarefasByIdFiltering() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        Long id = tarefa.getId();

        defaultTarefaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTarefaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTarefaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTarefasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where titulo equals to
        defaultTarefaFiltering("titulo.equals=" + DEFAULT_TITULO, "titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllTarefasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where titulo in
        defaultTarefaFiltering("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO, "titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllTarefasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where titulo is not null
        defaultTarefaFiltering("titulo.specified=true", "titulo.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefasByTituloContainsSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where titulo contains
        defaultTarefaFiltering("titulo.contains=" + DEFAULT_TITULO, "titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    void getAllTarefasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where titulo does not contain
        defaultTarefaFiltering("titulo.doesNotContain=" + UPDATED_TITULO, "titulo.doesNotContain=" + DEFAULT_TITULO);
    }

    @Test
    @Transactional
    void getAllTarefasByNumeroDiasIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where numeroDias equals to
        defaultTarefaFiltering("numeroDias.equals=" + DEFAULT_NUMERO_DIAS, "numeroDias.equals=" + UPDATED_NUMERO_DIAS);
    }

    @Test
    @Transactional
    void getAllTarefasByNumeroDiasIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where numeroDias in
        defaultTarefaFiltering("numeroDias.in=" + DEFAULT_NUMERO_DIAS + "," + UPDATED_NUMERO_DIAS, "numeroDias.in=" + UPDATED_NUMERO_DIAS);
    }

    @Test
    @Transactional
    void getAllTarefasByNumeroDiasIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where numeroDias is not null
        defaultTarefaFiltering("numeroDias.specified=true", "numeroDias.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefasByNumeroDiasIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where numeroDias is greater than or equal to
        defaultTarefaFiltering(
            "numeroDias.greaterThanOrEqual=" + DEFAULT_NUMERO_DIAS,
            "numeroDias.greaterThanOrEqual=" + UPDATED_NUMERO_DIAS
        );
    }

    @Test
    @Transactional
    void getAllTarefasByNumeroDiasIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where numeroDias is less than or equal to
        defaultTarefaFiltering("numeroDias.lessThanOrEqual=" + DEFAULT_NUMERO_DIAS, "numeroDias.lessThanOrEqual=" + SMALLER_NUMERO_DIAS);
    }

    @Test
    @Transactional
    void getAllTarefasByNumeroDiasIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where numeroDias is less than
        defaultTarefaFiltering("numeroDias.lessThan=" + UPDATED_NUMERO_DIAS, "numeroDias.lessThan=" + DEFAULT_NUMERO_DIAS);
    }

    @Test
    @Transactional
    void getAllTarefasByNumeroDiasIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where numeroDias is greater than
        defaultTarefaFiltering("numeroDias.greaterThan=" + SMALLER_NUMERO_DIAS, "numeroDias.greaterThan=" + DEFAULT_NUMERO_DIAS);
    }

    @Test
    @Transactional
    void getAllTarefasByDiaUtilIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where diaUtil equals to
        defaultTarefaFiltering("diaUtil.equals=" + DEFAULT_DIA_UTIL, "diaUtil.equals=" + UPDATED_DIA_UTIL);
    }

    @Test
    @Transactional
    void getAllTarefasByDiaUtilIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where diaUtil in
        defaultTarefaFiltering("diaUtil.in=" + DEFAULT_DIA_UTIL + "," + UPDATED_DIA_UTIL, "diaUtil.in=" + UPDATED_DIA_UTIL);
    }

    @Test
    @Transactional
    void getAllTarefasByDiaUtilIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where diaUtil is not null
        defaultTarefaFiltering("diaUtil.specified=true", "diaUtil.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where valor equals to
        defaultTarefaFiltering("valor.equals=" + DEFAULT_VALOR, "valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllTarefasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where valor in
        defaultTarefaFiltering("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR, "valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllTarefasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where valor is not null
        defaultTarefaFiltering("valor.specified=true", "valor.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefasByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where valor is greater than or equal to
        defaultTarefaFiltering("valor.greaterThanOrEqual=" + DEFAULT_VALOR, "valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    void getAllTarefasByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where valor is less than or equal to
        defaultTarefaFiltering("valor.lessThanOrEqual=" + DEFAULT_VALOR, "valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    void getAllTarefasByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where valor is less than
        defaultTarefaFiltering("valor.lessThan=" + UPDATED_VALOR, "valor.lessThan=" + DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void getAllTarefasByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where valor is greater than
        defaultTarefaFiltering("valor.greaterThan=" + SMALLER_VALOR, "valor.greaterThan=" + DEFAULT_VALOR);
    }

    @Test
    @Transactional
    void getAllTarefasByNotificarClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where notificarCliente equals to
        defaultTarefaFiltering(
            "notificarCliente.equals=" + DEFAULT_NOTIFICAR_CLIENTE,
            "notificarCliente.equals=" + UPDATED_NOTIFICAR_CLIENTE
        );
    }

    @Test
    @Transactional
    void getAllTarefasByNotificarClienteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where notificarCliente in
        defaultTarefaFiltering(
            "notificarCliente.in=" + DEFAULT_NOTIFICAR_CLIENTE + "," + UPDATED_NOTIFICAR_CLIENTE,
            "notificarCliente.in=" + UPDATED_NOTIFICAR_CLIENTE
        );
    }

    @Test
    @Transactional
    void getAllTarefasByNotificarClienteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where notificarCliente is not null
        defaultTarefaFiltering("notificarCliente.specified=true", "notificarCliente.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefasByGeraMultaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where geraMulta equals to
        defaultTarefaFiltering("geraMulta.equals=" + DEFAULT_GERA_MULTA, "geraMulta.equals=" + UPDATED_GERA_MULTA);
    }

    @Test
    @Transactional
    void getAllTarefasByGeraMultaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where geraMulta in
        defaultTarefaFiltering("geraMulta.in=" + DEFAULT_GERA_MULTA + "," + UPDATED_GERA_MULTA, "geraMulta.in=" + UPDATED_GERA_MULTA);
    }

    @Test
    @Transactional
    void getAllTarefasByGeraMultaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where geraMulta is not null
        defaultTarefaFiltering("geraMulta.specified=true", "geraMulta.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefasByExibirEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where exibirEmpresa equals to
        defaultTarefaFiltering("exibirEmpresa.equals=" + DEFAULT_EXIBIR_EMPRESA, "exibirEmpresa.equals=" + UPDATED_EXIBIR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllTarefasByExibirEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where exibirEmpresa in
        defaultTarefaFiltering(
            "exibirEmpresa.in=" + DEFAULT_EXIBIR_EMPRESA + "," + UPDATED_EXIBIR_EMPRESA,
            "exibirEmpresa.in=" + UPDATED_EXIBIR_EMPRESA
        );
    }

    @Test
    @Transactional
    void getAllTarefasByExibirEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where exibirEmpresa is not null
        defaultTarefaFiltering("exibirEmpresa.specified=true", "exibirEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefasByDataLegalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where dataLegal equals to
        defaultTarefaFiltering("dataLegal.equals=" + DEFAULT_DATA_LEGAL, "dataLegal.equals=" + UPDATED_DATA_LEGAL);
    }

    @Test
    @Transactional
    void getAllTarefasByDataLegalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where dataLegal in
        defaultTarefaFiltering("dataLegal.in=" + DEFAULT_DATA_LEGAL + "," + UPDATED_DATA_LEGAL, "dataLegal.in=" + UPDATED_DATA_LEGAL);
    }

    @Test
    @Transactional
    void getAllTarefasByDataLegalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where dataLegal is not null
        defaultTarefaFiltering("dataLegal.specified=true", "dataLegal.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefasByPontosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where pontos equals to
        defaultTarefaFiltering("pontos.equals=" + DEFAULT_PONTOS, "pontos.equals=" + UPDATED_PONTOS);
    }

    @Test
    @Transactional
    void getAllTarefasByPontosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where pontos in
        defaultTarefaFiltering("pontos.in=" + DEFAULT_PONTOS + "," + UPDATED_PONTOS, "pontos.in=" + UPDATED_PONTOS);
    }

    @Test
    @Transactional
    void getAllTarefasByPontosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where pontos is not null
        defaultTarefaFiltering("pontos.specified=true", "pontos.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefasByPontosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where pontos is greater than or equal to
        defaultTarefaFiltering("pontos.greaterThanOrEqual=" + DEFAULT_PONTOS, "pontos.greaterThanOrEqual=" + UPDATED_PONTOS);
    }

    @Test
    @Transactional
    void getAllTarefasByPontosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where pontos is less than or equal to
        defaultTarefaFiltering("pontos.lessThanOrEqual=" + DEFAULT_PONTOS, "pontos.lessThanOrEqual=" + SMALLER_PONTOS);
    }

    @Test
    @Transactional
    void getAllTarefasByPontosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where pontos is less than
        defaultTarefaFiltering("pontos.lessThan=" + UPDATED_PONTOS, "pontos.lessThan=" + DEFAULT_PONTOS);
    }

    @Test
    @Transactional
    void getAllTarefasByPontosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where pontos is greater than
        defaultTarefaFiltering("pontos.greaterThan=" + SMALLER_PONTOS, "pontos.greaterThan=" + DEFAULT_PONTOS);
    }

    @Test
    @Transactional
    void getAllTarefasByTipoTarefaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where tipoTarefa equals to
        defaultTarefaFiltering("tipoTarefa.equals=" + DEFAULT_TIPO_TAREFA, "tipoTarefa.equals=" + UPDATED_TIPO_TAREFA);
    }

    @Test
    @Transactional
    void getAllTarefasByTipoTarefaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where tipoTarefa in
        defaultTarefaFiltering("tipoTarefa.in=" + DEFAULT_TIPO_TAREFA + "," + UPDATED_TIPO_TAREFA, "tipoTarefa.in=" + UPDATED_TIPO_TAREFA);
    }

    @Test
    @Transactional
    void getAllTarefasByTipoTarefaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        // Get all the tarefaList where tipoTarefa is not null
        defaultTarefaFiltering("tipoTarefa.specified=true", "tipoTarefa.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefasByEsferaIsEqualToSomething() throws Exception {
        Esfera esfera;
        if (TestUtil.findAll(em, Esfera.class).isEmpty()) {
            tarefaRepository.saveAndFlush(tarefa);
            esfera = EsferaResourceIT.createEntity(em);
        } else {
            esfera = TestUtil.findAll(em, Esfera.class).get(0);
        }
        em.persist(esfera);
        em.flush();
        tarefa.setEsfera(esfera);
        tarefaRepository.saveAndFlush(tarefa);
        Long esferaId = esfera.getId();
        // Get all the tarefaList where esfera equals to esferaId
        defaultTarefaShouldBeFound("esferaId.equals=" + esferaId);

        // Get all the tarefaList where esfera equals to (esferaId + 1)
        defaultTarefaShouldNotBeFound("esferaId.equals=" + (esferaId + 1));
    }

    @Test
    @Transactional
    void getAllTarefasByFrequenciaIsEqualToSomething() throws Exception {
        Frequencia frequencia;
        if (TestUtil.findAll(em, Frequencia.class).isEmpty()) {
            tarefaRepository.saveAndFlush(tarefa);
            frequencia = FrequenciaResourceIT.createEntity(em);
        } else {
            frequencia = TestUtil.findAll(em, Frequencia.class).get(0);
        }
        em.persist(frequencia);
        em.flush();
        tarefa.setFrequencia(frequencia);
        tarefaRepository.saveAndFlush(tarefa);
        Long frequenciaId = frequencia.getId();
        // Get all the tarefaList where frequencia equals to frequenciaId
        defaultTarefaShouldBeFound("frequenciaId.equals=" + frequenciaId);

        // Get all the tarefaList where frequencia equals to (frequenciaId + 1)
        defaultTarefaShouldNotBeFound("frequenciaId.equals=" + (frequenciaId + 1));
    }

    @Test
    @Transactional
    void getAllTarefasByCompetenciaIsEqualToSomething() throws Exception {
        Competencia competencia;
        if (TestUtil.findAll(em, Competencia.class).isEmpty()) {
            tarefaRepository.saveAndFlush(tarefa);
            competencia = CompetenciaResourceIT.createEntity(em);
        } else {
            competencia = TestUtil.findAll(em, Competencia.class).get(0);
        }
        em.persist(competencia);
        em.flush();
        tarefa.setCompetencia(competencia);
        tarefaRepository.saveAndFlush(tarefa);
        Long competenciaId = competencia.getId();
        // Get all the tarefaList where competencia equals to competenciaId
        defaultTarefaShouldBeFound("competenciaId.equals=" + competenciaId);

        // Get all the tarefaList where competencia equals to (competenciaId + 1)
        defaultTarefaShouldNotBeFound("competenciaId.equals=" + (competenciaId + 1));
    }

    private void defaultTarefaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTarefaShouldBeFound(shouldBeFound);
        defaultTarefaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTarefaShouldBeFound(String filter) throws Exception {
        restTarefaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefa.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].numeroDias").value(hasItem(DEFAULT_NUMERO_DIAS)))
            .andExpect(jsonPath("$.[*].diaUtil").value(hasItem(DEFAULT_DIA_UTIL.booleanValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].notificarCliente").value(hasItem(DEFAULT_NOTIFICAR_CLIENTE.booleanValue())))
            .andExpect(jsonPath("$.[*].geraMulta").value(hasItem(DEFAULT_GERA_MULTA.booleanValue())))
            .andExpect(jsonPath("$.[*].exibirEmpresa").value(hasItem(DEFAULT_EXIBIR_EMPRESA.booleanValue())))
            .andExpect(jsonPath("$.[*].dataLegal").value(hasItem(DEFAULT_DATA_LEGAL.toString())))
            .andExpect(jsonPath("$.[*].pontos").value(hasItem(DEFAULT_PONTOS)))
            .andExpect(jsonPath("$.[*].tipoTarefa").value(hasItem(DEFAULT_TIPO_TAREFA.toString())));

        // Check, that the count call also returns 1
        restTarefaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTarefaShouldNotBeFound(String filter) throws Exception {
        restTarefaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTarefaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTarefa() throws Exception {
        // Get the tarefa
        restTarefaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarefa() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefa
        Tarefa updatedTarefa = tarefaRepository.findById(tarefa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTarefa are not directly saved in db
        em.detach(updatedTarefa);
        updatedTarefa
            .titulo(UPDATED_TITULO)
            .numeroDias(UPDATED_NUMERO_DIAS)
            .diaUtil(UPDATED_DIA_UTIL)
            .valor(UPDATED_VALOR)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .geraMulta(UPDATED_GERA_MULTA)
            .exibirEmpresa(UPDATED_EXIBIR_EMPRESA)
            .dataLegal(UPDATED_DATA_LEGAL)
            .pontos(UPDATED_PONTOS)
            .tipoTarefa(UPDATED_TIPO_TAREFA);

        restTarefaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTarefa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTarefa))
            )
            .andExpect(status().isOk());

        // Validate the Tarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTarefaToMatchAllProperties(updatedTarefa);
    }

    @Test
    @Transactional
    void putNonExistingTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaMockMvc
            .perform(put(ENTITY_API_URL_ID, tarefa.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefa)))
            .andExpect(status().isBadRequest());

        // Validate the Tarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarefaWithPatch() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefa using partial update
        Tarefa partialUpdatedTarefa = new Tarefa();
        partialUpdatedTarefa.setId(tarefa.getId());

        partialUpdatedTarefa
            .titulo(UPDATED_TITULO)
            .numeroDias(UPDATED_NUMERO_DIAS)
            .diaUtil(UPDATED_DIA_UTIL)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .exibirEmpresa(UPDATED_EXIBIR_EMPRESA)
            .dataLegal(UPDATED_DATA_LEGAL)
            .pontos(UPDATED_PONTOS)
            .tipoTarefa(UPDATED_TIPO_TAREFA);

        restTarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefa))
            )
            .andExpect(status().isOk());

        // Validate the Tarefa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTarefa, tarefa), getPersistedTarefa(tarefa));
    }

    @Test
    @Transactional
    void fullUpdateTarefaWithPatch() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefa using partial update
        Tarefa partialUpdatedTarefa = new Tarefa();
        partialUpdatedTarefa.setId(tarefa.getId());

        partialUpdatedTarefa
            .titulo(UPDATED_TITULO)
            .numeroDias(UPDATED_NUMERO_DIAS)
            .diaUtil(UPDATED_DIA_UTIL)
            .valor(UPDATED_VALOR)
            .notificarCliente(UPDATED_NOTIFICAR_CLIENTE)
            .geraMulta(UPDATED_GERA_MULTA)
            .exibirEmpresa(UPDATED_EXIBIR_EMPRESA)
            .dataLegal(UPDATED_DATA_LEGAL)
            .pontos(UPDATED_PONTOS)
            .tipoTarefa(UPDATED_TIPO_TAREFA);

        restTarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefa))
            )
            .andExpect(status().isOk());

        // Validate the Tarefa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaUpdatableFieldsEquals(partialUpdatedTarefa, getPersistedTarefa(partialUpdatedTarefa));
    }

    @Test
    @Transactional
    void patchNonExistingTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarefa.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tarefa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarefa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tarefa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tarefa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarefa() throws Exception {
        // Initialize the database
        insertedTarefa = tarefaRepository.saveAndFlush(tarefa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tarefa
        restTarefaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarefa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tarefaRepository.count();
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

    protected Tarefa getPersistedTarefa(Tarefa tarefa) {
        return tarefaRepository.findById(tarefa.getId()).orElseThrow();
    }

    protected void assertPersistedTarefaToMatchAllProperties(Tarefa expectedTarefa) {
        assertTarefaAllPropertiesEquals(expectedTarefa, getPersistedTarefa(expectedTarefa));
    }

    protected void assertPersistedTarefaToMatchUpdatableProperties(Tarefa expectedTarefa) {
        assertTarefaAllUpdatablePropertiesEquals(expectedTarefa, getPersistedTarefa(expectedTarefa));
    }
}
