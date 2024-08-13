package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PlanoContaAzulAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContaAzul;
import com.dobemcontabilidade.repository.PlanoContaAzulRepository;
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
 * Integration tests for the {@link PlanoContaAzulResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanoContaAzulResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Double DEFAULT_VALOR_BASE = 1D;
    private static final Double UPDATED_VALOR_BASE = 2D;
    private static final Double SMALLER_VALOR_BASE = 1D - 1D;

    private static final Integer DEFAULT_USUARIOS = 1;
    private static final Integer UPDATED_USUARIOS = 2;
    private static final Integer SMALLER_USUARIOS = 1 - 1;

    private static final Integer DEFAULT_BOLETOS = 1;
    private static final Integer UPDATED_BOLETOS = 2;
    private static final Integer SMALLER_BOLETOS = 1 - 1;

    private static final Integer DEFAULT_NOTA_FISCAL_PRODUTO = 1;
    private static final Integer UPDATED_NOTA_FISCAL_PRODUTO = 2;
    private static final Integer SMALLER_NOTA_FISCAL_PRODUTO = 1 - 1;

    private static final Integer DEFAULT_NOTA_FISCAL_SERVICO = 1;
    private static final Integer UPDATED_NOTA_FISCAL_SERVICO = 2;
    private static final Integer SMALLER_NOTA_FISCAL_SERVICO = 1 - 1;

    private static final Integer DEFAULT_NOTA_FISCAL_CE = 1;
    private static final Integer UPDATED_NOTA_FISCAL_CE = 2;
    private static final Integer SMALLER_NOTA_FISCAL_CE = 1 - 1;

    private static final Boolean DEFAULT_SUPORTE = false;
    private static final Boolean UPDATED_SUPORTE = true;

    private static final SituacaoPlanoContaAzul DEFAULT_SITUACAO = SituacaoPlanoContaAzul.ATIVO;
    private static final SituacaoPlanoContaAzul UPDATED_SITUACAO = SituacaoPlanoContaAzul.INATIVO;

    private static final String ENTITY_API_URL = "/api/plano-conta-azuls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlanoContaAzulRepository planoContaAzulRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoContaAzulMockMvc;

    private PlanoContaAzul planoContaAzul;

    private PlanoContaAzul insertedPlanoContaAzul;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoContaAzul createEntity(EntityManager em) {
        PlanoContaAzul planoContaAzul = new PlanoContaAzul()
            .nome(DEFAULT_NOME)
            .valorBase(DEFAULT_VALOR_BASE)
            .usuarios(DEFAULT_USUARIOS)
            .boletos(DEFAULT_BOLETOS)
            .notaFiscalProduto(DEFAULT_NOTA_FISCAL_PRODUTO)
            .notaFiscalServico(DEFAULT_NOTA_FISCAL_SERVICO)
            .notaFiscalCe(DEFAULT_NOTA_FISCAL_CE)
            .suporte(DEFAULT_SUPORTE)
            .situacao(DEFAULT_SITUACAO);
        return planoContaAzul;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoContaAzul createUpdatedEntity(EntityManager em) {
        PlanoContaAzul planoContaAzul = new PlanoContaAzul()
            .nome(UPDATED_NOME)
            .valorBase(UPDATED_VALOR_BASE)
            .usuarios(UPDATED_USUARIOS)
            .boletos(UPDATED_BOLETOS)
            .notaFiscalProduto(UPDATED_NOTA_FISCAL_PRODUTO)
            .notaFiscalServico(UPDATED_NOTA_FISCAL_SERVICO)
            .notaFiscalCe(UPDATED_NOTA_FISCAL_CE)
            .suporte(UPDATED_SUPORTE)
            .situacao(UPDATED_SITUACAO);
        return planoContaAzul;
    }

    @BeforeEach
    public void initTest() {
        planoContaAzul = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPlanoContaAzul != null) {
            planoContaAzulRepository.delete(insertedPlanoContaAzul);
            insertedPlanoContaAzul = null;
        }
    }

    @Test
    @Transactional
    void createPlanoContaAzul() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PlanoContaAzul
        var returnedPlanoContaAzul = om.readValue(
            restPlanoContaAzulMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoContaAzul)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlanoContaAzul.class
        );

        // Validate the PlanoContaAzul in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlanoContaAzulUpdatableFieldsEquals(returnedPlanoContaAzul, getPersistedPlanoContaAzul(returnedPlanoContaAzul));

        insertedPlanoContaAzul = returnedPlanoContaAzul;
    }

    @Test
    @Transactional
    void createPlanoContaAzulWithExistingId() throws Exception {
        // Create the PlanoContaAzul with an existing ID
        planoContaAzul.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoContaAzulMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoContaAzul)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzuls() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList
        restPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoContaAzul.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].valorBase").value(hasItem(DEFAULT_VALOR_BASE.doubleValue())))
            .andExpect(jsonPath("$.[*].usuarios").value(hasItem(DEFAULT_USUARIOS)))
            .andExpect(jsonPath("$.[*].boletos").value(hasItem(DEFAULT_BOLETOS)))
            .andExpect(jsonPath("$.[*].notaFiscalProduto").value(hasItem(DEFAULT_NOTA_FISCAL_PRODUTO)))
            .andExpect(jsonPath("$.[*].notaFiscalServico").value(hasItem(DEFAULT_NOTA_FISCAL_SERVICO)))
            .andExpect(jsonPath("$.[*].notaFiscalCe").value(hasItem(DEFAULT_NOTA_FISCAL_CE)))
            .andExpect(jsonPath("$.[*].suporte").value(hasItem(DEFAULT_SUPORTE.booleanValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @Test
    @Transactional
    void getPlanoContaAzul() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get the planoContaAzul
        restPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL_ID, planoContaAzul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoContaAzul.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.valorBase").value(DEFAULT_VALOR_BASE.doubleValue()))
            .andExpect(jsonPath("$.usuarios").value(DEFAULT_USUARIOS))
            .andExpect(jsonPath("$.boletos").value(DEFAULT_BOLETOS))
            .andExpect(jsonPath("$.notaFiscalProduto").value(DEFAULT_NOTA_FISCAL_PRODUTO))
            .andExpect(jsonPath("$.notaFiscalServico").value(DEFAULT_NOTA_FISCAL_SERVICO))
            .andExpect(jsonPath("$.notaFiscalCe").value(DEFAULT_NOTA_FISCAL_CE))
            .andExpect(jsonPath("$.suporte").value(DEFAULT_SUPORTE.booleanValue()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getPlanoContaAzulsByIdFiltering() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        Long id = planoContaAzul.getId();

        defaultPlanoContaAzulFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPlanoContaAzulFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPlanoContaAzulFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where nome equals to
        defaultPlanoContaAzulFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where nome in
        defaultPlanoContaAzulFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where nome is not null
        defaultPlanoContaAzulFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where nome contains
        defaultPlanoContaAzulFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where nome does not contain
        defaultPlanoContaAzulFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByValorBaseIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where valorBase equals to
        defaultPlanoContaAzulFiltering("valorBase.equals=" + DEFAULT_VALOR_BASE, "valorBase.equals=" + UPDATED_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByValorBaseIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where valorBase in
        defaultPlanoContaAzulFiltering(
            "valorBase.in=" + DEFAULT_VALOR_BASE + "," + UPDATED_VALOR_BASE,
            "valorBase.in=" + UPDATED_VALOR_BASE
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByValorBaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where valorBase is not null
        defaultPlanoContaAzulFiltering("valorBase.specified=true", "valorBase.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByValorBaseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where valorBase is greater than or equal to
        defaultPlanoContaAzulFiltering(
            "valorBase.greaterThanOrEqual=" + DEFAULT_VALOR_BASE,
            "valorBase.greaterThanOrEqual=" + UPDATED_VALOR_BASE
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByValorBaseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where valorBase is less than or equal to
        defaultPlanoContaAzulFiltering(
            "valorBase.lessThanOrEqual=" + DEFAULT_VALOR_BASE,
            "valorBase.lessThanOrEqual=" + SMALLER_VALOR_BASE
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByValorBaseIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where valorBase is less than
        defaultPlanoContaAzulFiltering("valorBase.lessThan=" + UPDATED_VALOR_BASE, "valorBase.lessThan=" + DEFAULT_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByValorBaseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where valorBase is greater than
        defaultPlanoContaAzulFiltering("valorBase.greaterThan=" + SMALLER_VALOR_BASE, "valorBase.greaterThan=" + DEFAULT_VALOR_BASE);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByUsuariosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where usuarios equals to
        defaultPlanoContaAzulFiltering("usuarios.equals=" + DEFAULT_USUARIOS, "usuarios.equals=" + UPDATED_USUARIOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByUsuariosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where usuarios in
        defaultPlanoContaAzulFiltering("usuarios.in=" + DEFAULT_USUARIOS + "," + UPDATED_USUARIOS, "usuarios.in=" + UPDATED_USUARIOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByUsuariosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where usuarios is not null
        defaultPlanoContaAzulFiltering("usuarios.specified=true", "usuarios.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByUsuariosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where usuarios is greater than or equal to
        defaultPlanoContaAzulFiltering(
            "usuarios.greaterThanOrEqual=" + DEFAULT_USUARIOS,
            "usuarios.greaterThanOrEqual=" + UPDATED_USUARIOS
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByUsuariosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where usuarios is less than or equal to
        defaultPlanoContaAzulFiltering("usuarios.lessThanOrEqual=" + DEFAULT_USUARIOS, "usuarios.lessThanOrEqual=" + SMALLER_USUARIOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByUsuariosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where usuarios is less than
        defaultPlanoContaAzulFiltering("usuarios.lessThan=" + UPDATED_USUARIOS, "usuarios.lessThan=" + DEFAULT_USUARIOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByUsuariosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where usuarios is greater than
        defaultPlanoContaAzulFiltering("usuarios.greaterThan=" + SMALLER_USUARIOS, "usuarios.greaterThan=" + DEFAULT_USUARIOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByBoletosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where boletos equals to
        defaultPlanoContaAzulFiltering("boletos.equals=" + DEFAULT_BOLETOS, "boletos.equals=" + UPDATED_BOLETOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByBoletosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where boletos in
        defaultPlanoContaAzulFiltering("boletos.in=" + DEFAULT_BOLETOS + "," + UPDATED_BOLETOS, "boletos.in=" + UPDATED_BOLETOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByBoletosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where boletos is not null
        defaultPlanoContaAzulFiltering("boletos.specified=true", "boletos.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByBoletosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where boletos is greater than or equal to
        defaultPlanoContaAzulFiltering("boletos.greaterThanOrEqual=" + DEFAULT_BOLETOS, "boletos.greaterThanOrEqual=" + UPDATED_BOLETOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByBoletosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where boletos is less than or equal to
        defaultPlanoContaAzulFiltering("boletos.lessThanOrEqual=" + DEFAULT_BOLETOS, "boletos.lessThanOrEqual=" + SMALLER_BOLETOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByBoletosIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where boletos is less than
        defaultPlanoContaAzulFiltering("boletos.lessThan=" + UPDATED_BOLETOS, "boletos.lessThan=" + DEFAULT_BOLETOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByBoletosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where boletos is greater than
        defaultPlanoContaAzulFiltering("boletos.greaterThan=" + SMALLER_BOLETOS, "boletos.greaterThan=" + DEFAULT_BOLETOS);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalProduto equals to
        defaultPlanoContaAzulFiltering(
            "notaFiscalProduto.equals=" + DEFAULT_NOTA_FISCAL_PRODUTO,
            "notaFiscalProduto.equals=" + UPDATED_NOTA_FISCAL_PRODUTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalProdutoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalProduto in
        defaultPlanoContaAzulFiltering(
            "notaFiscalProduto.in=" + DEFAULT_NOTA_FISCAL_PRODUTO + "," + UPDATED_NOTA_FISCAL_PRODUTO,
            "notaFiscalProduto.in=" + UPDATED_NOTA_FISCAL_PRODUTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalProdutoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalProduto is not null
        defaultPlanoContaAzulFiltering("notaFiscalProduto.specified=true", "notaFiscalProduto.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalProdutoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalProduto is greater than or equal to
        defaultPlanoContaAzulFiltering(
            "notaFiscalProduto.greaterThanOrEqual=" + DEFAULT_NOTA_FISCAL_PRODUTO,
            "notaFiscalProduto.greaterThanOrEqual=" + UPDATED_NOTA_FISCAL_PRODUTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalProdutoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalProduto is less than or equal to
        defaultPlanoContaAzulFiltering(
            "notaFiscalProduto.lessThanOrEqual=" + DEFAULT_NOTA_FISCAL_PRODUTO,
            "notaFiscalProduto.lessThanOrEqual=" + SMALLER_NOTA_FISCAL_PRODUTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalProdutoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalProduto is less than
        defaultPlanoContaAzulFiltering(
            "notaFiscalProduto.lessThan=" + UPDATED_NOTA_FISCAL_PRODUTO,
            "notaFiscalProduto.lessThan=" + DEFAULT_NOTA_FISCAL_PRODUTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalProdutoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalProduto is greater than
        defaultPlanoContaAzulFiltering(
            "notaFiscalProduto.greaterThan=" + SMALLER_NOTA_FISCAL_PRODUTO,
            "notaFiscalProduto.greaterThan=" + DEFAULT_NOTA_FISCAL_PRODUTO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalServicoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalServico equals to
        defaultPlanoContaAzulFiltering(
            "notaFiscalServico.equals=" + DEFAULT_NOTA_FISCAL_SERVICO,
            "notaFiscalServico.equals=" + UPDATED_NOTA_FISCAL_SERVICO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalServicoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalServico in
        defaultPlanoContaAzulFiltering(
            "notaFiscalServico.in=" + DEFAULT_NOTA_FISCAL_SERVICO + "," + UPDATED_NOTA_FISCAL_SERVICO,
            "notaFiscalServico.in=" + UPDATED_NOTA_FISCAL_SERVICO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalServicoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalServico is not null
        defaultPlanoContaAzulFiltering("notaFiscalServico.specified=true", "notaFiscalServico.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalServicoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalServico is greater than or equal to
        defaultPlanoContaAzulFiltering(
            "notaFiscalServico.greaterThanOrEqual=" + DEFAULT_NOTA_FISCAL_SERVICO,
            "notaFiscalServico.greaterThanOrEqual=" + UPDATED_NOTA_FISCAL_SERVICO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalServicoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalServico is less than or equal to
        defaultPlanoContaAzulFiltering(
            "notaFiscalServico.lessThanOrEqual=" + DEFAULT_NOTA_FISCAL_SERVICO,
            "notaFiscalServico.lessThanOrEqual=" + SMALLER_NOTA_FISCAL_SERVICO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalServicoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalServico is less than
        defaultPlanoContaAzulFiltering(
            "notaFiscalServico.lessThan=" + UPDATED_NOTA_FISCAL_SERVICO,
            "notaFiscalServico.lessThan=" + DEFAULT_NOTA_FISCAL_SERVICO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalServicoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalServico is greater than
        defaultPlanoContaAzulFiltering(
            "notaFiscalServico.greaterThan=" + SMALLER_NOTA_FISCAL_SERVICO,
            "notaFiscalServico.greaterThan=" + DEFAULT_NOTA_FISCAL_SERVICO
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalCeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalCe equals to
        defaultPlanoContaAzulFiltering("notaFiscalCe.equals=" + DEFAULT_NOTA_FISCAL_CE, "notaFiscalCe.equals=" + UPDATED_NOTA_FISCAL_CE);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalCeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalCe in
        defaultPlanoContaAzulFiltering(
            "notaFiscalCe.in=" + DEFAULT_NOTA_FISCAL_CE + "," + UPDATED_NOTA_FISCAL_CE,
            "notaFiscalCe.in=" + UPDATED_NOTA_FISCAL_CE
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalCeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalCe is not null
        defaultPlanoContaAzulFiltering("notaFiscalCe.specified=true", "notaFiscalCe.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalCeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalCe is greater than or equal to
        defaultPlanoContaAzulFiltering(
            "notaFiscalCe.greaterThanOrEqual=" + DEFAULT_NOTA_FISCAL_CE,
            "notaFiscalCe.greaterThanOrEqual=" + UPDATED_NOTA_FISCAL_CE
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalCeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalCe is less than or equal to
        defaultPlanoContaAzulFiltering(
            "notaFiscalCe.lessThanOrEqual=" + DEFAULT_NOTA_FISCAL_CE,
            "notaFiscalCe.lessThanOrEqual=" + SMALLER_NOTA_FISCAL_CE
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalCeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalCe is less than
        defaultPlanoContaAzulFiltering(
            "notaFiscalCe.lessThan=" + UPDATED_NOTA_FISCAL_CE,
            "notaFiscalCe.lessThan=" + DEFAULT_NOTA_FISCAL_CE
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsByNotaFiscalCeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where notaFiscalCe is greater than
        defaultPlanoContaAzulFiltering(
            "notaFiscalCe.greaterThan=" + SMALLER_NOTA_FISCAL_CE,
            "notaFiscalCe.greaterThan=" + DEFAULT_NOTA_FISCAL_CE
        );
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsBySuporteIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where suporte equals to
        defaultPlanoContaAzulFiltering("suporte.equals=" + DEFAULT_SUPORTE, "suporte.equals=" + UPDATED_SUPORTE);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsBySuporteIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where suporte in
        defaultPlanoContaAzulFiltering("suporte.in=" + DEFAULT_SUPORTE + "," + UPDATED_SUPORTE, "suporte.in=" + UPDATED_SUPORTE);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsBySuporteIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where suporte is not null
        defaultPlanoContaAzulFiltering("suporte.specified=true", "suporte.specified=false");
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where situacao equals to
        defaultPlanoContaAzulFiltering("situacao.equals=" + DEFAULT_SITUACAO, "situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where situacao in
        defaultPlanoContaAzulFiltering("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO, "situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllPlanoContaAzulsBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        // Get all the planoContaAzulList where situacao is not null
        defaultPlanoContaAzulFiltering("situacao.specified=true", "situacao.specified=false");
    }

    private void defaultPlanoContaAzulFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPlanoContaAzulShouldBeFound(shouldBeFound);
        defaultPlanoContaAzulShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlanoContaAzulShouldBeFound(String filter) throws Exception {
        restPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoContaAzul.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].valorBase").value(hasItem(DEFAULT_VALOR_BASE.doubleValue())))
            .andExpect(jsonPath("$.[*].usuarios").value(hasItem(DEFAULT_USUARIOS)))
            .andExpect(jsonPath("$.[*].boletos").value(hasItem(DEFAULT_BOLETOS)))
            .andExpect(jsonPath("$.[*].notaFiscalProduto").value(hasItem(DEFAULT_NOTA_FISCAL_PRODUTO)))
            .andExpect(jsonPath("$.[*].notaFiscalServico").value(hasItem(DEFAULT_NOTA_FISCAL_SERVICO)))
            .andExpect(jsonPath("$.[*].notaFiscalCe").value(hasItem(DEFAULT_NOTA_FISCAL_CE)))
            .andExpect(jsonPath("$.[*].suporte").value(hasItem(DEFAULT_SUPORTE.booleanValue())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));

        // Check, that the count call also returns 1
        restPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlanoContaAzulShouldNotBeFound(String filter) throws Exception {
        restPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlanoContaAzulMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPlanoContaAzul() throws Exception {
        // Get the planoContaAzul
        restPlanoContaAzulMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanoContaAzul() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoContaAzul
        PlanoContaAzul updatedPlanoContaAzul = planoContaAzulRepository.findById(planoContaAzul.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlanoContaAzul are not directly saved in db
        em.detach(updatedPlanoContaAzul);
        updatedPlanoContaAzul
            .nome(UPDATED_NOME)
            .valorBase(UPDATED_VALOR_BASE)
            .usuarios(UPDATED_USUARIOS)
            .boletos(UPDATED_BOLETOS)
            .notaFiscalProduto(UPDATED_NOTA_FISCAL_PRODUTO)
            .notaFiscalServico(UPDATED_NOTA_FISCAL_SERVICO)
            .notaFiscalCe(UPDATED_NOTA_FISCAL_CE)
            .suporte(UPDATED_SUPORTE)
            .situacao(UPDATED_SITUACAO);

        restPlanoContaAzulMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanoContaAzul.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlanoContaAzul))
            )
            .andExpect(status().isOk());

        // Validate the PlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlanoContaAzulToMatchAllProperties(updatedPlanoContaAzul);
    }

    @Test
    @Transactional
    void putNonExistingPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContaAzul.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoContaAzulMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoContaAzul.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoContaAzul))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContaAzul.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoContaAzulMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoContaAzul))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContaAzul.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoContaAzulMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoContaAzul)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanoContaAzulWithPatch() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoContaAzul using partial update
        PlanoContaAzul partialUpdatedPlanoContaAzul = new PlanoContaAzul();
        partialUpdatedPlanoContaAzul.setId(planoContaAzul.getId());

        partialUpdatedPlanoContaAzul
            .nome(UPDATED_NOME)
            .notaFiscalCe(UPDATED_NOTA_FISCAL_CE)
            .suporte(UPDATED_SUPORTE)
            .situacao(UPDATED_SITUACAO);

        restPlanoContaAzulMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoContaAzul.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanoContaAzul))
            )
            .andExpect(status().isOk());

        // Validate the PlanoContaAzul in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanoContaAzulUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlanoContaAzul, planoContaAzul),
            getPersistedPlanoContaAzul(planoContaAzul)
        );
    }

    @Test
    @Transactional
    void fullUpdatePlanoContaAzulWithPatch() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoContaAzul using partial update
        PlanoContaAzul partialUpdatedPlanoContaAzul = new PlanoContaAzul();
        partialUpdatedPlanoContaAzul.setId(planoContaAzul.getId());

        partialUpdatedPlanoContaAzul
            .nome(UPDATED_NOME)
            .valorBase(UPDATED_VALOR_BASE)
            .usuarios(UPDATED_USUARIOS)
            .boletos(UPDATED_BOLETOS)
            .notaFiscalProduto(UPDATED_NOTA_FISCAL_PRODUTO)
            .notaFiscalServico(UPDATED_NOTA_FISCAL_SERVICO)
            .notaFiscalCe(UPDATED_NOTA_FISCAL_CE)
            .suporte(UPDATED_SUPORTE)
            .situacao(UPDATED_SITUACAO);

        restPlanoContaAzulMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoContaAzul.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanoContaAzul))
            )
            .andExpect(status().isOk());

        // Validate the PlanoContaAzul in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanoContaAzulUpdatableFieldsEquals(partialUpdatedPlanoContaAzul, getPersistedPlanoContaAzul(partialUpdatedPlanoContaAzul));
    }

    @Test
    @Transactional
    void patchNonExistingPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContaAzul.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoContaAzulMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planoContaAzul.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoContaAzul))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContaAzul.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoContaAzulMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoContaAzul))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanoContaAzul() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoContaAzul.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoContaAzulMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(planoContaAzul)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoContaAzul in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanoContaAzul() throws Exception {
        // Initialize the database
        insertedPlanoContaAzul = planoContaAzulRepository.saveAndFlush(planoContaAzul);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the planoContaAzul
        restPlanoContaAzulMockMvc
            .perform(delete(ENTITY_API_URL_ID, planoContaAzul.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return planoContaAzulRepository.count();
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

    protected PlanoContaAzul getPersistedPlanoContaAzul(PlanoContaAzul planoContaAzul) {
        return planoContaAzulRepository.findById(planoContaAzul.getId()).orElseThrow();
    }

    protected void assertPersistedPlanoContaAzulToMatchAllProperties(PlanoContaAzul expectedPlanoContaAzul) {
        assertPlanoContaAzulAllPropertiesEquals(expectedPlanoContaAzul, getPersistedPlanoContaAzul(expectedPlanoContaAzul));
    }

    protected void assertPersistedPlanoContaAzulToMatchUpdatableProperties(PlanoContaAzul expectedPlanoContaAzul) {
        assertPlanoContaAzulAllUpdatablePropertiesEquals(expectedPlanoContaAzul, getPersistedPlanoContaAzul(expectedPlanoContaAzul));
    }
}
