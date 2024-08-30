package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PlanoAssinaturaContabilAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PlanoAssinaturaContabil;
import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContabilEnum;
import com.dobemcontabilidade.repository.PlanoAssinaturaContabilRepository;
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
 * Integration tests for the {@link PlanoAssinaturaContabilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanoAssinaturaContabilResourceIT {

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

    private static final String ENTITY_API_URL = "/api/plano-assinatura-contabils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlanoAssinaturaContabilRepository planoAssinaturaContabilRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoAssinaturaContabilMockMvc;

    private PlanoAssinaturaContabil planoAssinaturaContabil;

    private PlanoAssinaturaContabil insertedPlanoAssinaturaContabil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoAssinaturaContabil createEntity(EntityManager em) {
        PlanoAssinaturaContabil planoAssinaturaContabil = new PlanoAssinaturaContabil()
            .nome(DEFAULT_NOME)
            .adicionalSocio(DEFAULT_ADICIONAL_SOCIO)
            .adicionalFuncionario(DEFAULT_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(DEFAULT_SOCIOS_ISENTOS)
            .adicionalFaturamento(DEFAULT_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(DEFAULT_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(DEFAULT_VALOR_BASE_ABERTURA)
            .situacao(DEFAULT_SITUACAO);
        return planoAssinaturaContabil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoAssinaturaContabil createUpdatedEntity(EntityManager em) {
        PlanoAssinaturaContabil planoAssinaturaContabil = new PlanoAssinaturaContabil()
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFuncionario(UPDATED_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(UPDATED_SOCIOS_ISENTOS)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(UPDATED_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA)
            .situacao(UPDATED_SITUACAO);
        return planoAssinaturaContabil;
    }

    @BeforeEach
    public void initTest() {
        planoAssinaturaContabil = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPlanoAssinaturaContabil != null) {
            planoAssinaturaContabilRepository.delete(insertedPlanoAssinaturaContabil);
            insertedPlanoAssinaturaContabil = null;
        }
    }

    @Test
    @Transactional
    void createPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PlanoAssinaturaContabil
        var returnedPlanoAssinaturaContabil = om.readValue(
            restPlanoAssinaturaContabilMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(planoAssinaturaContabil))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlanoAssinaturaContabil.class
        );

        // Validate the PlanoAssinaturaContabil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlanoAssinaturaContabilUpdatableFieldsEquals(
            returnedPlanoAssinaturaContabil,
            getPersistedPlanoAssinaturaContabil(returnedPlanoAssinaturaContabil)
        );

        insertedPlanoAssinaturaContabil = returnedPlanoAssinaturaContabil;
    }

    @Test
    @Transactional
    void createPlanoAssinaturaContabilWithExistingId() throws Exception {
        // Create the PlanoAssinaturaContabil with an existing ID
        planoAssinaturaContabil.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoAssinaturaContabilMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabils() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList
        restPlanoAssinaturaContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoAssinaturaContabil.getId().intValue())))
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
    void getPlanoAssinaturaContabil() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get the planoAssinaturaContabil
        restPlanoAssinaturaContabilMockMvc
            .perform(get(ENTITY_API_URL_ID, planoAssinaturaContabil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoAssinaturaContabil.getId().intValue()))
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
    void getPlanoAssinaturaContabilsByIdFiltering() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        Long id = planoAssinaturaContabil.getId();

        defaultPlanoAssinaturaContabilFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPlanoAssinaturaContabilFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPlanoAssinaturaContabilFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where nome equals to
        defaultPlanoAssinaturaContabilFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where nome in
        defaultPlanoAssinaturaContabilFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where nome is not null
        defaultPlanoAssinaturaContabilFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where nome contains
        defaultPlanoAssinaturaContabilFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where nome does not contain
        defaultPlanoAssinaturaContabilFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalSocioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalSocio equals to
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalSocio.equals=" + DEFAULT_ADICIONAL_SOCIO,
            "adicionalSocio.equals=" + UPDATED_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalSocioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalSocio in
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalSocio.in=" + DEFAULT_ADICIONAL_SOCIO + "," + UPDATED_ADICIONAL_SOCIO,
            "adicionalSocio.in=" + UPDATED_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalSocioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalSocio is not null
        defaultPlanoAssinaturaContabilFiltering("adicionalSocio.specified=true", "adicionalSocio.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalSocioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalSocio is greater than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalSocio.greaterThanOrEqual=" + DEFAULT_ADICIONAL_SOCIO,
            "adicionalSocio.greaterThanOrEqual=" + UPDATED_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalSocioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalSocio is less than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalSocio.lessThanOrEqual=" + DEFAULT_ADICIONAL_SOCIO,
            "adicionalSocio.lessThanOrEqual=" + SMALLER_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalSocioIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalSocio is less than
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalSocio.lessThan=" + UPDATED_ADICIONAL_SOCIO,
            "adicionalSocio.lessThan=" + DEFAULT_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalSocioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalSocio is greater than
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalSocio.greaterThan=" + SMALLER_ADICIONAL_SOCIO,
            "adicionalSocio.greaterThan=" + DEFAULT_ADICIONAL_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFuncionarioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFuncionario equals to
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFuncionario.equals=" + DEFAULT_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.equals=" + UPDATED_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFuncionarioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFuncionario in
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFuncionario.in=" + DEFAULT_ADICIONAL_FUNCIONARIO + "," + UPDATED_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.in=" + UPDATED_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFuncionarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFuncionario is not null
        defaultPlanoAssinaturaContabilFiltering("adicionalFuncionario.specified=true", "adicionalFuncionario.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFuncionarioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFuncionario is greater than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFuncionario.greaterThanOrEqual=" + DEFAULT_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.greaterThanOrEqual=" + UPDATED_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFuncionarioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFuncionario is less than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFuncionario.lessThanOrEqual=" + DEFAULT_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.lessThanOrEqual=" + SMALLER_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFuncionarioIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFuncionario is less than
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFuncionario.lessThan=" + UPDATED_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.lessThan=" + DEFAULT_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFuncionarioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFuncionario is greater than
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFuncionario.greaterThan=" + SMALLER_ADICIONAL_FUNCIONARIO,
            "adicionalFuncionario.greaterThan=" + DEFAULT_ADICIONAL_FUNCIONARIO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsBySociosIsentosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where sociosIsentos equals to
        defaultPlanoAssinaturaContabilFiltering(
            "sociosIsentos.equals=" + DEFAULT_SOCIOS_ISENTOS,
            "sociosIsentos.equals=" + UPDATED_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsBySociosIsentosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where sociosIsentos in
        defaultPlanoAssinaturaContabilFiltering(
            "sociosIsentos.in=" + DEFAULT_SOCIOS_ISENTOS + "," + UPDATED_SOCIOS_ISENTOS,
            "sociosIsentos.in=" + UPDATED_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsBySociosIsentosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where sociosIsentos is not null
        defaultPlanoAssinaturaContabilFiltering("sociosIsentos.specified=true", "sociosIsentos.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsBySociosIsentosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where sociosIsentos is greater than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "sociosIsentos.greaterThanOrEqual=" + DEFAULT_SOCIOS_ISENTOS,
            "sociosIsentos.greaterThanOrEqual=" + UPDATED_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsBySociosIsentosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where sociosIsentos is less than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "sociosIsentos.lessThanOrEqual=" + DEFAULT_SOCIOS_ISENTOS,
            "sociosIsentos.lessThanOrEqual=" + SMALLER_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsBySociosIsentosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where sociosIsentos is less than
        defaultPlanoAssinaturaContabilFiltering(
            "sociosIsentos.lessThan=" + UPDATED_SOCIOS_ISENTOS,
            "sociosIsentos.lessThan=" + DEFAULT_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsBySociosIsentosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where sociosIsentos is greater than
        defaultPlanoAssinaturaContabilFiltering(
            "sociosIsentos.greaterThan=" + SMALLER_SOCIOS_ISENTOS,
            "sociosIsentos.greaterThan=" + DEFAULT_SOCIOS_ISENTOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFaturamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFaturamento equals to
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFaturamento.equals=" + DEFAULT_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.equals=" + UPDATED_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFaturamentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFaturamento in
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFaturamento.in=" + DEFAULT_ADICIONAL_FATURAMENTO + "," + UPDATED_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.in=" + UPDATED_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFaturamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFaturamento is not null
        defaultPlanoAssinaturaContabilFiltering("adicionalFaturamento.specified=true", "adicionalFaturamento.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFaturamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFaturamento is greater than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFaturamento.greaterThanOrEqual=" + DEFAULT_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.greaterThanOrEqual=" + UPDATED_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFaturamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFaturamento is less than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFaturamento.lessThanOrEqual=" + DEFAULT_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.lessThanOrEqual=" + SMALLER_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFaturamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFaturamento is less than
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFaturamento.lessThan=" + UPDATED_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.lessThan=" + DEFAULT_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByAdicionalFaturamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where adicionalFaturamento is greater than
        defaultPlanoAssinaturaContabilFiltering(
            "adicionalFaturamento.greaterThan=" + SMALLER_ADICIONAL_FATURAMENTO,
            "adicionalFaturamento.greaterThan=" + DEFAULT_ADICIONAL_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseFaturamentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseFaturamento equals to
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseFaturamento.equals=" + DEFAULT_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.equals=" + UPDATED_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseFaturamentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseFaturamento in
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseFaturamento.in=" + DEFAULT_VALOR_BASE_FATURAMENTO + "," + UPDATED_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.in=" + UPDATED_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseFaturamentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseFaturamento is not null
        defaultPlanoAssinaturaContabilFiltering("valorBaseFaturamento.specified=true", "valorBaseFaturamento.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseFaturamentoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseFaturamento is greater than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseFaturamento.greaterThanOrEqual=" + DEFAULT_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.greaterThanOrEqual=" + UPDATED_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseFaturamentoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseFaturamento is less than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseFaturamento.lessThanOrEqual=" + DEFAULT_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.lessThanOrEqual=" + SMALLER_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseFaturamentoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseFaturamento is less than
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseFaturamento.lessThan=" + UPDATED_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.lessThan=" + DEFAULT_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseFaturamentoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseFaturamento is greater than
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseFaturamento.greaterThan=" + SMALLER_VALOR_BASE_FATURAMENTO,
            "valorBaseFaturamento.greaterThan=" + DEFAULT_VALOR_BASE_FATURAMENTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseAberturaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseAbertura equals to
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseAbertura.equals=" + DEFAULT_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.equals=" + UPDATED_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseAberturaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseAbertura in
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseAbertura.in=" + DEFAULT_VALOR_BASE_ABERTURA + "," + UPDATED_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.in=" + UPDATED_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseAberturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseAbertura is not null
        defaultPlanoAssinaturaContabilFiltering("valorBaseAbertura.specified=true", "valorBaseAbertura.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseAberturaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseAbertura is greater than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseAbertura.greaterThanOrEqual=" + DEFAULT_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.greaterThanOrEqual=" + UPDATED_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseAberturaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseAbertura is less than or equal to
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseAbertura.lessThanOrEqual=" + DEFAULT_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.lessThanOrEqual=" + SMALLER_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseAberturaIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseAbertura is less than
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseAbertura.lessThan=" + UPDATED_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.lessThan=" + DEFAULT_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsByValorBaseAberturaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where valorBaseAbertura is greater than
        defaultPlanoAssinaturaContabilFiltering(
            "valorBaseAbertura.greaterThan=" + SMALLER_VALOR_BASE_ABERTURA,
            "valorBaseAbertura.greaterThan=" + DEFAULT_VALOR_BASE_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where situacao equals to
        defaultPlanoAssinaturaContabilFiltering("situacao.equals=" + DEFAULT_SITUACAO, "situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where situacao in
        defaultPlanoAssinaturaContabilFiltering(
            "situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO,
            "situacao.in=" + UPDATED_SITUACAO
        );
    }

    @Test
    @Transactional
    void getAllPlanoAssinaturaContabilsBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        // Get all the planoAssinaturaContabilList where situacao is not null
        defaultPlanoAssinaturaContabilFiltering("situacao.specified=true", "situacao.specified=false");
    }

    private void defaultPlanoAssinaturaContabilFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPlanoAssinaturaContabilShouldBeFound(shouldBeFound);
        defaultPlanoAssinaturaContabilShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoAssinaturaContabilShouldBeFound(String filter) throws Exception {
        restPlanoAssinaturaContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoAssinaturaContabil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].adicionalSocio").value(hasItem(DEFAULT_ADICIONAL_SOCIO.doubleValue())))
            .andExpect(jsonPath("$.[*].adicionalFuncionario").value(hasItem(DEFAULT_ADICIONAL_FUNCIONARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].sociosIsentos").value(hasItem(DEFAULT_SOCIOS_ISENTOS)))
            .andExpect(jsonPath("$.[*].adicionalFaturamento").value(hasItem(DEFAULT_ADICIONAL_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorBaseFaturamento").value(hasItem(DEFAULT_VALOR_BASE_FATURAMENTO.doubleValue())))
            .andExpect(jsonPath("$.[*].valorBaseAbertura").value(hasItem(DEFAULT_VALOR_BASE_ABERTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));

        // Check, that the count call also returns 1
        restPlanoAssinaturaContabilMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoAssinaturaContabilShouldNotBeFound(String filter) throws Exception {
        restPlanoAssinaturaContabilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoAssinaturaContabilMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPlanoAssinaturaContabil() throws Exception {
        // Get the planoAssinaturaContabil
        restPlanoAssinaturaContabilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanoAssinaturaContabil() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoAssinaturaContabil
        PlanoAssinaturaContabil updatedPlanoAssinaturaContabil = planoAssinaturaContabilRepository
            .findById(planoAssinaturaContabil.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedPlanoAssinaturaContabil are not directly saved in db
        em.detach(updatedPlanoAssinaturaContabil);
        updatedPlanoAssinaturaContabil
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFuncionario(UPDATED_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(UPDATED_SOCIOS_ISENTOS)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(UPDATED_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA)
            .situacao(UPDATED_SITUACAO);

        restPlanoAssinaturaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanoAssinaturaContabil.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlanoAssinaturaContabil))
            )
            .andExpect(status().isOk());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlanoAssinaturaContabilToMatchAllProperties(updatedPlanoAssinaturaContabil);
    }

    @Test
    @Transactional
    void putNonExistingPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoAssinaturaContabil.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanoAssinaturaContabilWithPatch() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoAssinaturaContabil using partial update
        PlanoAssinaturaContabil partialUpdatedPlanoAssinaturaContabil = new PlanoAssinaturaContabil();
        partialUpdatedPlanoAssinaturaContabil.setId(planoAssinaturaContabil.getId());

        partialUpdatedPlanoAssinaturaContabil
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA);

        restPlanoAssinaturaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoAssinaturaContabil.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanoAssinaturaContabil))
            )
            .andExpect(status().isOk());

        // Validate the PlanoAssinaturaContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanoAssinaturaContabilUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlanoAssinaturaContabil, planoAssinaturaContabil),
            getPersistedPlanoAssinaturaContabil(planoAssinaturaContabil)
        );
    }

    @Test
    @Transactional
    void fullUpdatePlanoAssinaturaContabilWithPatch() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoAssinaturaContabil using partial update
        PlanoAssinaturaContabil partialUpdatedPlanoAssinaturaContabil = new PlanoAssinaturaContabil();
        partialUpdatedPlanoAssinaturaContabil.setId(planoAssinaturaContabil.getId());

        partialUpdatedPlanoAssinaturaContabil
            .nome(UPDATED_NOME)
            .adicionalSocio(UPDATED_ADICIONAL_SOCIO)
            .adicionalFuncionario(UPDATED_ADICIONAL_FUNCIONARIO)
            .sociosIsentos(UPDATED_SOCIOS_ISENTOS)
            .adicionalFaturamento(UPDATED_ADICIONAL_FATURAMENTO)
            .valorBaseFaturamento(UPDATED_VALOR_BASE_FATURAMENTO)
            .valorBaseAbertura(UPDATED_VALOR_BASE_ABERTURA)
            .situacao(UPDATED_SITUACAO);

        restPlanoAssinaturaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoAssinaturaContabil.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanoAssinaturaContabil))
            )
            .andExpect(status().isOk());

        // Validate the PlanoAssinaturaContabil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanoAssinaturaContabilUpdatableFieldsEquals(
            partialUpdatedPlanoAssinaturaContabil,
            getPersistedPlanoAssinaturaContabil(partialUpdatedPlanoAssinaturaContabil)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planoAssinaturaContabil.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanoAssinaturaContabil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoAssinaturaContabil.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoAssinaturaContabilMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoAssinaturaContabil))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoAssinaturaContabil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanoAssinaturaContabil() throws Exception {
        // Initialize the database
        insertedPlanoAssinaturaContabil = planoAssinaturaContabilRepository.saveAndFlush(planoAssinaturaContabil);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the planoAssinaturaContabil
        restPlanoAssinaturaContabilMockMvc
            .perform(delete(ENTITY_API_URL_ID, planoAssinaturaContabil.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return planoAssinaturaContabilRepository.count();
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

    protected PlanoAssinaturaContabil getPersistedPlanoAssinaturaContabil(PlanoAssinaturaContabil planoAssinaturaContabil) {
        return planoAssinaturaContabilRepository.findById(planoAssinaturaContabil.getId()).orElseThrow();
    }

    protected void assertPersistedPlanoAssinaturaContabilToMatchAllProperties(PlanoAssinaturaContabil expectedPlanoAssinaturaContabil) {
        assertPlanoAssinaturaContabilAllPropertiesEquals(
            expectedPlanoAssinaturaContabil,
            getPersistedPlanoAssinaturaContabil(expectedPlanoAssinaturaContabil)
        );
    }

    protected void assertPersistedPlanoAssinaturaContabilToMatchUpdatableProperties(
        PlanoAssinaturaContabil expectedPlanoAssinaturaContabil
    ) {
        assertPlanoAssinaturaContabilAllUpdatablePropertiesEquals(
            expectedPlanoAssinaturaContabil,
            getPersistedPlanoAssinaturaContabil(expectedPlanoAssinaturaContabil)
        );
    }
}
