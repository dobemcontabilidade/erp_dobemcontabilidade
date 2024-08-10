package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PlanoContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContabilEnum;
import com.dobemcontabilidade.repository.PlanoContabilRepository;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import com.dobemcontabilidade.service.mapper.PlanoContabilMapper;
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
 * Integration tests for the {@link PlanoContabilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanoContabilResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Double DEFAULT_ADICIONAL_SOCIO = 1D;
    private static final Double UPDATED_ADICIONAL_SOCIO = 2D;
    private static final Double SMALLER_ADICIONAL_SOCIO = 1D - 1D;

    private static final Double DEFAULT_ADICIONAL_FUNCIONARIO = 1D;
    private static final Double UPDATED_ADICIONAL_FUNCIONARIO = 2D;
    private static final Double SMALLER_ADICIONAL_FUNCIONARIO = 1D - 1D;

    private static final Integer DEFAULT_SOCIOS_ISENTOS = 1;
    private static final Integer UPDATED_SOCIOS_ISENTOS = 2;
    private static final Integer SMALLER_SOCIOS_ISENTOS = 1 - 1;

    private static final Double DEFAULT_ADICIONAL_FATURAMENTO = 1D;
    private static final Double UPDATED_ADICIONAL_FATURAMENTO = 2D;
    private static final Double SMALLER_ADICIONAL_FATURAMENTO = 1D - 1D;

    private static final Double DEFAULT_VALOR_BASE_FATURAMENTO = 1D;
    private static final Double UPDATED_VALOR_BASE_FATURAMENTO = 2D;
    private static final Double SMALLER_VALOR_BASE_FATURAMENTO = 1D - 1D;

    private static final Double DEFAULT_VALOR_BASE_ABERTURA = 1D;
    private static final Double UPDATED_VALOR_BASE_ABERTURA = 2D;
    private static final Double SMALLER_VALOR_BASE_ABERTURA = 1D - 1D;

    private static final SituacaoPlanoContabilEnum DEFAULT_SITUACAO = SituacaoPlanoContabilEnum.ATIVO;
    private static final SituacaoPlanoContabilEnum UPDATED_SITUACAO = SituacaoPlanoContabilEnum.INATIVO;

    private static final String ENTITY_API_URL = "/api/plano-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlanoContabilRepository planoContabilRepository;

    @Autowired
    private PlanoContabilMapper planoContabilMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoContabilMockMvc;

    private PlanoContabil planoContabil;

    private PlanoContabil insertedPlanoContabil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoContabil createEntity(EntityManager em) {
        PlanoContabil planoContabil = new PlanoContabil()
            .nome(DEFAULT_NOME)
            .adicionalSocio(DEFAULT_ADICIONAL_SOCIO)
            .adicionalFuncionario(DEFAULT_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(DEFAULT_SOCIOS_ISENTOS)
            .adicionalFaturamento(DEFAULT_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(DEFAULT_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(DEFAULT_VALOR_BASE_ABERTURA)
            .situacao(DEFAULT_SITUACAO);
        return planoContabil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoContabil createUpdatedEntity(EntityManager em) {
        PlanoContabil planoContabil = new PlanoContabil()
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFuncionario(UPDATED_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(UPDATED_SOCIOS_ISENTOS)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(UPDATED_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA)
            .situacao(UPDATED_SITUACAO);
        return planoContabil;
    }

    @BeforeEach
    public void initTest() {
        planoContabil = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPlanoContabil != null) {
            planoContabilRepository.delete(insertedPlanoContabil);
            insertedPlanoContabil = null;
        }
    }

    @Test
    @Transactional
    void createPlanoContabil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PlanoContabil
        PlanoContabilDTO planoContabilDTO = planoContabilMapper.toDto(planoContabil);
        var returnedPlanoContabilDTO = om.readValue(
            restPlanoContabilMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoContabilDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlanoContabilDTO.class
        );

        // Validate the PlanoContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPlanoContabil = planoContabilMapper.toEntity(returnedPlanoContabilDTO);
        assertPlanoContabilUpdatableFieldsEquals(returnedPlanoContabil, getPersistedPlanoContabil(returnedPlanoContabil));

        insertedPlanoContabil = returnedPlanoContabil;
    }

    @Test
    @Transactional
    void createPlanoContabilWithExistingId() throws Exception {
        // Create the PlanoContabil with an existing ID
        planoContabil.setId(1L);
        PlanoContabilDTO planoContabilDTO = planoContabilMapper.toDto(planoContabil);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoContabilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoContabilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlanoContabils() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList
        restPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].adicionalSocio").value(hasItem(DEFAULT_ADICIONAL_SOCIO.doubleValue())))
            .andExpect(jsonPath("$.[*].adicionalFuncionario").value(hasItem(DEFAULT_ADICIONAL_FUNCIONARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].sociosIsentos").value(hasItem(DEFAULT_SOCIOS_ISENTOS)))
            .andExpect(jsonPath("$.[*].adicionalFaturamento").value(hasItem(DEFAULT_ADICIONAL_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorBaseFaturamento").value(hasItem(DEFAULT_VALOR_BASE_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorBaseAbertura").value(hasItem(DEFAULT_VALOR_BASE_ABERTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @Test
    @Transactional
    void getPlanoContabil() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get the planoContabil
        restPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL_ID, planoContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoContabil.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.adicionalSocio").value(DEFAULT_ADICIONAL_SOCIO.doubleValue()))
            .andExpect(jsonPath("$.adicionalFuncionario").value(DEFAULT_ADICIONAL_FUNCIONARIO.doubleValue()))
            .andExpect(jsonPath("$.sociosIsentos").value(DEFAULT_SOCIOS_ISENTOS))
            .andExpect(jsonPath("$.adicionalFaturamento").value(DEFAULT_ADICIONAL_FATURAMENTO.doubleValue()))
            .andExpect(jsonPath("$.valorBaseFaturamento").value(DEFAULT_VALOR_BASE_FATURAMENTO.doubleValue()))
            .andExpect(jsonPath("$.valorBaseAbertura").value(DEFAULT_VALOR_BASE_ABERTURA.doubleValue()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getPlanoContabilsByIdFiltering() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        Long id = planoContabil.getId();

        defaultPlanoContabilFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPlanoContabilFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPlanoContabilFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where nome equals to
        defaultPlanoContabilFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where nome in
        defaultPlanoContabilFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where nome is not null
        defaultPlanoContabilFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where nome contains
        defaultPlanoContabilFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where nome does not contain
        defaultPlanoContabilFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalSocioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalSocio equals to
        defaultPlanoContabilFiltering(
            "adicionalSocio.equals=" + DEFAULT_ADICIONAL_SOCIO,
            "adicionalSocio.equals=" + UPDATED_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalSocioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalSocio in
        defaultPlanoContabilFiltering(
            "adicionalSocio.in=" + DEFAULT_ADICIONAL_SOCIO + "," + UPDATED_ADICIONAL_SOCIO,
            "adicionalSocio.in=" + UPDATED_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalSocioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalSocio is not null
        defaultPlanoContabilFiltering("adicionalSocio.specified=true", "adicionalSocio.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalSocioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalSocio is greater than or equal to
        defaultPlanoContabilFiltering(
            "adicionalSocio.greaterThanOrEqual=" + DEFAULT_ADICIONAL_SOCIO,
            "adicionalSocio.greaterThanOrEqual=" + UPDATED_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalSocioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalSocio is less than or equal to
        defaultPlanoContabilFiltering(
            "adicionalSocio.lessThanOrEqual=" + DEFAULT_ADICIONAL_SOCIO,
            "adicionalSocio.lessThanOrEqual=" + SMALLER_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalSocioIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalSocio is less than
        defaultPlanoContabilFiltering(
            "adicionalSocio.lessThan=" + UPDATED_ADICIONAL_SOCIO,
            "adicionalSocio.lessThan=" + DEFAULT_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalSocioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalSocio is greater than
        defaultPlanoContabilFiltering(
            "adicionalSocio.greaterThan=" + SMALLER_ADICIONAL_SOCIO,
            "adicionalSocio.greaterThan=" + DEFAULT_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFuncionarioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFuncionario equals to
        defaultPlanoContabilFiltering(
            "adicionalFuncionario.equals=" + DEFAULT_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.equals=" + UPDATED_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFuncionarioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFuncionario in
        defaultPlanoContabilFiltering(
            "adicionalFuncionario.in=" + DEFAULT_ADICIONAL_FUNCIONARIO + "," + UPDATED_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.in=" + UPDATED_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFuncionarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFuncionario is not null
        defaultPlanoContabilFiltering("adicionalFuncionario.specified=true", "adicionalFuncionario.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFuncionarioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFuncionario is greater than or equal to
        defaultPlanoContabilFiltering(
            "adicionalFuncionario.greaterThanOrEqual=" + DEFAULT_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.greaterThanOrEqual=" + UPDATED_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFuncionarioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFuncionario is less than or equal to
        defaultPlanoContabilFiltering(
            "adicionalFuncionario.lessThanOrEqual=" + DEFAULT_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.lessThanOrEqual=" + SMALLER_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFuncionarioIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFuncionario is less than
        defaultPlanoContabilFiltering(
            "adicionalFuncionario.lessThan=" + UPDATED_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.lessThan=" + DEFAULT_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFuncionarioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFuncionario is greater than
        defaultPlanoContabilFiltering(
            "adicionalFuncionario.greaterThan=" + SMALLER_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.greaterThan=" + DEFAULT_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsBySociosIsentosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where sociosIsentos equals to
        defaultPlanoContabilFiltering("sociosIsentos.equals=" + DEFAULT_SOCIOS_ISENTOS, "sociosIsentos.equals=" + UPDATED_SOCIOS_ISENTOS);
    }

    @Test
    @Transactional
    void getAllPlanoContabilsBySociosIsentosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where sociosIsentos in
        defaultPlanoContabilFiltering(
            "sociosIsentos.in=" + DEFAULT_SOCIOS_ISENTOS + "," + UPDATED_SOCIOS_ISENTOS,
            "sociosIsentos.in=" + UPDATED_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsBySociosIsentosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where sociosIsentos is not null
        defaultPlanoContabilFiltering("sociosIsentos.specified=true", "sociosIsentos.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContabilsBySociosIsentosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where sociosIsentos is greater than or equal to
        defaultPlanoContabilFiltering(
            "sociosIsentos.greaterThanOrEqual=" + DEFAULT_SOCIOS_ISENTOS,
            "sociosIsentos.greaterThanOrEqual=" + UPDATED_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsBySociosIsentosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where sociosIsentos is less than or equal to
        defaultPlanoContabilFiltering(
            "sociosIsentos.lessThanOrEqual=" + DEFAULT_SOCIOS_ISENTOS,
            "sociosIsentos.lessThanOrEqual=" + SMALLER_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsBySociosIsentosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where sociosIsentos is less than
        defaultPlanoContabilFiltering(
            "sociosIsentos.lessThan=" + UPDATED_SOCIOS_ISENTOS,
            "sociosIsentos.lessThan=" + DEFAULT_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsBySociosIsentosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where sociosIsentos is greater than
        defaultPlanoContabilFiltering(
            "sociosIsentos.greaterThan=" + SMALLER_SOCIOS_ISENTOS,
            "sociosIsentos.greaterThan=" + DEFAULT_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFaturamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFaturamento equals to
        defaultPlanoContabilFiltering(
            "adicionalFaturamento.equals=" + DEFAULT_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.equals=" + UPDATED_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFaturamentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFaturamento in
        defaultPlanoContabilFiltering(
            "adicionalFaturamento.in=" + DEFAULT_ADICIONAL_FATURAMENTO + "," + UPDATED_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.in=" + UPDATED_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFaturamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFaturamento is not null
        defaultPlanoContabilFiltering("adicionalFaturamento.specified=true", "adicionalFaturamento.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFaturamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFaturamento is greater than or equal to
        defaultPlanoContabilFiltering(
            "adicionalFaturamento.greaterThanOrEqual=" + DEFAULT_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.greaterThanOrEqual=" + UPDATED_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFaturamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFaturamento is less than or equal to
        defaultPlanoContabilFiltering(
            "adicionalFaturamento.lessThanOrEqual=" + DEFAULT_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.lessThanOrEqual=" + SMALLER_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFaturamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFaturamento is less than
        defaultPlanoContabilFiltering(
            "adicionalFaturamento.lessThan=" + UPDATED_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.lessThan=" + DEFAULT_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByAdicionalFaturamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where adicionalFaturamento is greater than
        defaultPlanoContabilFiltering(
            "adicionalFaturamento.greaterThan=" + SMALLER_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.greaterThan=" + DEFAULT_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseFaturamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseFaturamento equals to
        defaultPlanoContabilFiltering(
            "valorBaseFaturamento.equals=" + DEFAULT_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.equals=" + UPDATED_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseFaturamentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseFaturamento in
        defaultPlanoContabilFiltering(
            "valorBaseFaturamento.in=" + DEFAULT_VALOR_BASE_FATURAMENTO + "," + UPDATED_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.in=" + UPDATED_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseFaturamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseFaturamento is not null
        defaultPlanoContabilFiltering("valorBaseFaturamento.specified=true", "valorBaseFaturamento.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseFaturamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseFaturamento is greater than or equal to
        defaultPlanoContabilFiltering(
            "valorBaseFaturamento.greaterThanOrEqual=" + DEFAULT_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.greaterThanOrEqual=" + UPDATED_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseFaturamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseFaturamento is less than or equal to
        defaultPlanoContabilFiltering(
            "valorBaseFaturamento.lessThanOrEqual=" + DEFAULT_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.lessThanOrEqual=" + SMALLER_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseFaturamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseFaturamento is less than
        defaultPlanoContabilFiltering(
            "valorBaseFaturamento.lessThan=" + UPDATED_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.lessThan=" + DEFAULT_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseFaturamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseFaturamento is greater than
        defaultPlanoContabilFiltering(
            "valorBaseFaturamento.greaterThan=" + SMALLER_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.greaterThan=" + DEFAULT_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseAberturaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseAbertura equals to
        defaultPlanoContabilFiltering(
            "valorBaseAbertura.equals=" + DEFAULT_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.equals=" + UPDATED_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseAberturaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseAbertura in
        defaultPlanoContabilFiltering(
            "valorBaseAbertura.in=" + DEFAULT_VALOR_BASE_ABERTURA + "," + UPDATED_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.in=" + UPDATED_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseAberturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseAbertura is not null
        defaultPlanoContabilFiltering("valorBaseAbertura.specified=true", "valorBaseAbertura.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseAberturaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseAbertura is greater than or equal to
        defaultPlanoContabilFiltering(
            "valorBaseAbertura.greaterThanOrEqual=" + DEFAULT_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.greaterThanOrEqual=" + UPDATED_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseAberturaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseAbertura is less than or equal to
        defaultPlanoContabilFiltering(
            "valorBaseAbertura.lessThanOrEqual=" + DEFAULT_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.lessThanOrEqual=" + SMALLER_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseAberturaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseAbertura is less than
        defaultPlanoContabilFiltering(
            "valorBaseAbertura.lessThan=" + UPDATED_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.lessThan=" + DEFAULT_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsByValorBaseAberturaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where valorBaseAbertura is greater than
        defaultPlanoContabilFiltering(
            "valorBaseAbertura.greaterThan=" + SMALLER_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.greaterThan=" + DEFAULT_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoContabilsBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where situacao equals to
        defaultPlanoContabilFiltering("situacao.equals=" + DEFAULT_SITUACAO, "situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllPlanoContabilsBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where situacao in
        defaultPlanoContabilFiltering("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO, "situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllPlanoContabilsBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        // Get all the planoContabilList where situacao is not null
        defaultPlanoContabilFiltering("situacao.specified=true", "situacao.specified=false");
    }

    private void defaultPlanoContabilFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPlanoContabilShouldBeFound(shouldBeFound);
        defaultPlanoContabilShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoContabilShouldBeFound(String filter) throws Exception {
        restPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].adicionalSocio").value(hasItem(DEFAULT_ADICIONAL_SOCIO.doubleValue())))
            .andExpect(jsonPath("$.[*].adicionalFuncionario").value(hasItem(DEFAULT_ADICIONAL_FUNCIONARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].sociosIsentos").value(hasItem(DEFAULT_SOCIOS_ISENTOS)))
            .andExpect(jsonPath("$.[*].adicionalFaturamento").value(hasItem(DEFAULT_ADICIONAL_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorBaseFaturamento").value(hasItem(DEFAULT_VALOR_BASE_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorBaseAbertura").value(hasItem(DEFAULT_VALOR_BASE_ABERTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));

        // Check, that the count call also returns 1
        restPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoContabilShouldNotBeFound(String filter) throws Exception {
        restPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoContabilMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPlanoContabil() throws Exception {
        // Get the planoContabil
        restPlanoContabilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanoContabil() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoContabil
        PlanoContabil updatedPlanoContabil = planoContabilRepository.findById(planoContabil.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlanoContabil are not directly saved in db
        em.detach(updatedPlanoContabil);
        updatedPlanoContabil
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFuncionario(UPDATED_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(UPDATED_SOCIOS_ISENTOS)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(UPDATED_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA)
            .situacao(UPDATED_SITUACAO);
        PlanoContabilDTO planoContabilDTO = planoContabilMapper.toDto(updatedPlanoContabil);

        restPlanoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoContabilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoContabilDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlanoContabilToMatchAllProperties(updatedPlanoContabil);
    }

    @Test
    @Transactional
    void putNonExistingPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContabil.setId(longCount.incrementAndGet());

        // Create the PlanoContabil
        PlanoContabilDTO planoContabilDTO = planoContabilMapper.toDto(planoContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoContabilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContabil.setId(longCount.incrementAndGet());

        // Create the PlanoContabil
        PlanoContabilDTO planoContabilDTO = planoContabilMapper.toDto(planoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContabil.setId(longCount.incrementAndGet());

        // Create the PlanoContabil
        PlanoContabilDTO planoContabilDTO = planoContabilMapper.toDto(planoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoContabilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoContabilDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoContabil using partial update
        PlanoContabil partialUpdatedPlanoContabil = new PlanoContabil();
        partialUpdatedPlanoContabil.setId(planoContabil.getId());

        partialUpdatedPlanoContabil
            .nome(UPDATED_NOME)
            .sociosIsentos(UPDATED_SOCIOS_ISENTOS)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA);

        restPlanoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanoContabil))
            )
            .andExpect(status().isOk());

        // Validate the PlanoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanoContabilUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlanoContabil, planoContabil),
            getPersistedPlanoContabil(planoContabil)
        );
    }

    @Test
    @Transactional
    void fullUpdatePlanoContabilWithPatch() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoContabil using partial update
        PlanoContabil partialUpdatedPlanoContabil = new PlanoContabil();
        partialUpdatedPlanoContabil.setId(planoContabil.getId());

        partialUpdatedPlanoContabil
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFuncionario(UPDATED_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(UPDATED_SOCIOS_ISENTOS)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(UPDATED_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA)
            .situacao(UPDATED_SITUACAO);

        restPlanoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoContabil.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanoContabil))
            )
            .andExpect(status().isOk());

        // Validate the PlanoContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanoContabilUpdatableFieldsEquals(partialUpdatedPlanoContabil, getPersistedPlanoContabil(partialUpdatedPlanoContabil));
    }

    @Test
    @Transactional
    void patchNonExistingPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContabil.setId(longCount.incrementAndGet());

        // Create the PlanoContabil
        PlanoContabilDTO planoContabilDTO = planoContabilMapper.toDto(planoContabil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planoContabilDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContabil.setId(longCount.incrementAndGet());

        // Create the PlanoContabil
        PlanoContabilDTO planoContabilDTO = planoContabilMapper.toDto(planoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoContabilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanoContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContabil.setId(longCount.incrementAndGet());

        // Create the PlanoContabil
        PlanoContabilDTO planoContabilDTO = planoContabilMapper.toDto(planoContabil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoContabilMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(planoContabilDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanoContabil() throws Exception {
        // Initialize the database
        insertedPlanoContabil = planoContabilRepository.saveAndFlush(planoContabil);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the planoContabil
        restPlanoContabilMockMvc
            .perform(delete(ENTITY_API_URL_ID, planoContabil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return planoContabilRepository.count();
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

    protected PlanoContabil getPersistedPlanoContabil(PlanoContabil planoContabil) {
        return planoContabilRepository.findById(planoContabil.getId()).orElseThrow();
    }

    protected void assertPersistedPlanoContabilToMatchAllProperties(PlanoContabil expectedPlanoContabil) {
        assertPlanoContabilAllPropertiesEquals(expectedPlanoContabil, getPersistedPlanoContabil(expectedPlanoContabil));
    }

    protected void assertPersistedPlanoContabilToMatchUpdatableProperties(PlanoContabil expectedPlanoContabil) {
        assertPlanoContabilAllUpdatablePropertiesEquals(expectedPlanoContabil, getPersistedPlanoContabil(expectedPlanoContabil));
    }
}
