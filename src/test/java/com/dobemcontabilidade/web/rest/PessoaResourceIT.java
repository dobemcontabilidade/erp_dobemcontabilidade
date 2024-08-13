package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.PessoaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.domain.enumeration.EstadoCivilEnum;
import com.dobemcontabilidade.domain.enumeration.PessoaComDeficienciaEnum;
import com.dobemcontabilidade.domain.enumeration.RacaECorEnum;
import com.dobemcontabilidade.domain.enumeration.SexoEnum;
import com.dobemcontabilidade.repository.PessoaRepository;
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
 * Integration tests for the {@link PessoaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PessoaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_NASCIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_NASCIMENTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TITULO_ELEITOR = 1;
    private static final Integer UPDATED_TITULO_ELEITOR = 2;
    private static final Integer SMALLER_TITULO_ELEITOR = 1 - 1;

    private static final String DEFAULT_RG = "AAAAAAAAAA";
    private static final String UPDATED_RG = "BBBBBBBBBB";

    private static final String DEFAULT_RG_ORGAO_EXPEDITOR = "AAAAAAAAAA";
    private static final String UPDATED_RG_ORGAO_EXPEDITOR = "BBBBBBBBBB";

    private static final String DEFAULT_RG_UF_EXPEDICAO = "AAAAAAAAAA";
    private static final String UPDATED_RG_UF_EXPEDICAO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_MAE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_PAI = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PAI = "BBBBBBBBBB";

    private static final String DEFAULT_LOCAL_NASCIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_NASCIMENTO = "BBBBBBBBBB";

    private static final RacaECorEnum DEFAULT_RACA_E_COR = RacaECorEnum.AMARELA;
    private static final RacaECorEnum UPDATED_RACA_E_COR = RacaECorEnum.BRANCO;

    private static final PessoaComDeficienciaEnum DEFAULT_PESSOA_COM_DEFICIENCIA = PessoaComDeficienciaEnum.DEFICIENCIAMOTORA;
    private static final PessoaComDeficienciaEnum UPDATED_PESSOA_COM_DEFICIENCIA = PessoaComDeficienciaEnum.DEFICIENCIAVISUAL;

    private static final EstadoCivilEnum DEFAULT_ESTADO_CIVIL = EstadoCivilEnum.SOLTEIRO;
    private static final EstadoCivilEnum UPDATED_ESTADO_CIVIL = EstadoCivilEnum.CASADO;

    private static final SexoEnum DEFAULT_SEXO = SexoEnum.MASCULINO;
    private static final SexoEnum UPDATED_SEXO = SexoEnum.FEMININO;

    private static final String DEFAULT_URL_FOTO_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_URL_FOTO_PERFIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    private Pessoa insertedPessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .nome(DEFAULT_NOME)
            .cpf(DEFAULT_CPF)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .tituloEleitor(DEFAULT_TITULO_ELEITOR)
            .rg(DEFAULT_RG)
            .rgOrgaoExpeditor(DEFAULT_RG_ORGAO_EXPEDITOR)
            .rgUfExpedicao(DEFAULT_RG_UF_EXPEDICAO)
            .nomeMae(DEFAULT_NOME_MAE)
            .nomePai(DEFAULT_NOME_PAI)
            .localNascimento(DEFAULT_LOCAL_NASCIMENTO)
            .racaECor(DEFAULT_RACA_E_COR)
            .pessoaComDeficiencia(DEFAULT_PESSOA_COM_DEFICIENCIA)
            .estadoCivil(DEFAULT_ESTADO_CIVIL)
            .sexo(DEFAULT_SEXO)
            .urlFotoPerfil(DEFAULT_URL_FOTO_PERFIL);
        return pessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pessoa createUpdatedEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tituloEleitor(UPDATED_TITULO_ELEITOR)
            .rg(UPDATED_RG)
            .rgOrgaoExpeditor(UPDATED_RG_ORGAO_EXPEDITOR)
            .rgUfExpedicao(UPDATED_RG_UF_EXPEDICAO)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .localNascimento(UPDATED_LOCAL_NASCIMENTO)
            .racaECor(UPDATED_RACA_E_COR)
            .pessoaComDeficiencia(UPDATED_PESSOA_COM_DEFICIENCIA)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .urlFotoPerfil(UPDATED_URL_FOTO_PERFIL);
        return pessoa;
    }

    @BeforeEach
    public void initTest() {
        pessoa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPessoa != null) {
            pessoaRepository.delete(insertedPessoa);
            insertedPessoa = null;
        }
    }

    @Test
    @Transactional
    void createPessoa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Pessoa
        var returnedPessoa = om.readValue(
            restPessoaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Pessoa.class
        );

        // Validate the Pessoa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPessoaUpdatableFieldsEquals(returnedPessoa, getPersistedPessoa(returnedPessoa));

        insertedPessoa = returnedPessoa;
    }

    @Test
    @Transactional
    void createPessoaWithExistingId() throws Exception {
        // Create the Pessoa with an existing ID
        pessoa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoa)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pessoa.setNome(null);

        // Create the Pessoa, which fails.

        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpfIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pessoa.setCpf(null);

        // Create the Pessoa, which fails.

        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRgIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pessoa.setRg(null);

        // Create the Pessoa, which fails.

        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pessoa.setSexo(null);

        // Create the Pessoa, which fails.

        restPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPessoas() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO)))
            .andExpect(jsonPath("$.[*].tituloEleitor").value(hasItem(DEFAULT_TITULO_ELEITOR)))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG)))
            .andExpect(jsonPath("$.[*].rgOrgaoExpeditor").value(hasItem(DEFAULT_RG_ORGAO_EXPEDITOR)))
            .andExpect(jsonPath("$.[*].rgUfExpedicao").value(hasItem(DEFAULT_RG_UF_EXPEDICAO)))
            .andExpect(jsonPath("$.[*].nomeMae").value(hasItem(DEFAULT_NOME_MAE)))
            .andExpect(jsonPath("$.[*].nomePai").value(hasItem(DEFAULT_NOME_PAI)))
            .andExpect(jsonPath("$.[*].localNascimento").value(hasItem(DEFAULT_LOCAL_NASCIMENTO)))
            .andExpect(jsonPath("$.[*].racaECor").value(hasItem(DEFAULT_RACA_E_COR.toString())))
            .andExpect(jsonPath("$.[*].pessoaComDeficiencia").value(hasItem(DEFAULT_PESSOA_COM_DEFICIENCIA.toString())))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].urlFotoPerfil").value(hasItem(DEFAULT_URL_FOTO_PERFIL)));
    }

    @Test
    @Transactional
    void getPessoa() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get the pessoa
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO))
            .andExpect(jsonPath("$.tituloEleitor").value(DEFAULT_TITULO_ELEITOR))
            .andExpect(jsonPath("$.rg").value(DEFAULT_RG))
            .andExpect(jsonPath("$.rgOrgaoExpeditor").value(DEFAULT_RG_ORGAO_EXPEDITOR))
            .andExpect(jsonPath("$.rgUfExpedicao").value(DEFAULT_RG_UF_EXPEDICAO))
            .andExpect(jsonPath("$.nomeMae").value(DEFAULT_NOME_MAE))
            .andExpect(jsonPath("$.nomePai").value(DEFAULT_NOME_PAI))
            .andExpect(jsonPath("$.localNascimento").value(DEFAULT_LOCAL_NASCIMENTO))
            .andExpect(jsonPath("$.racaECor").value(DEFAULT_RACA_E_COR.toString()))
            .andExpect(jsonPath("$.pessoaComDeficiencia").value(DEFAULT_PESSOA_COM_DEFICIENCIA.toString()))
            .andExpect(jsonPath("$.estadoCivil").value(DEFAULT_ESTADO_CIVIL.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.urlFotoPerfil").value(DEFAULT_URL_FOTO_PERFIL));
    }

    @Test
    @Transactional
    void getPessoasByIdFiltering() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        Long id = pessoa.getId();

        defaultPessoaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultPessoaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultPessoaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome equals to
        defaultPessoaFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome in
        defaultPessoaFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome is not null
        defaultPessoaFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome contains
        defaultPessoaFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nome does not contain
        defaultPessoaFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllPessoasByCpfIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where cpf equals to
        defaultPessoaFiltering("cpf.equals=" + DEFAULT_CPF, "cpf.equals=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllPessoasByCpfIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where cpf in
        defaultPessoaFiltering("cpf.in=" + DEFAULT_CPF + "," + UPDATED_CPF, "cpf.in=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllPessoasByCpfIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where cpf is not null
        defaultPessoaFiltering("cpf.specified=true", "cpf.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByCpfContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where cpf contains
        defaultPessoaFiltering("cpf.contains=" + DEFAULT_CPF, "cpf.contains=" + UPDATED_CPF);
    }

    @Test
    @Transactional
    void getAllPessoasByCpfNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where cpf does not contain
        defaultPessoaFiltering("cpf.doesNotContain=" + UPDATED_CPF, "cpf.doesNotContain=" + DEFAULT_CPF);
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento equals to
        defaultPessoaFiltering("dataNascimento.equals=" + DEFAULT_DATA_NASCIMENTO, "dataNascimento.equals=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento in
        defaultPessoaFiltering(
            "dataNascimento.in=" + DEFAULT_DATA_NASCIMENTO + "," + UPDATED_DATA_NASCIMENTO,
            "dataNascimento.in=" + UPDATED_DATA_NASCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento is not null
        defaultPessoaFiltering("dataNascimento.specified=true", "dataNascimento.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento contains
        defaultPessoaFiltering("dataNascimento.contains=" + DEFAULT_DATA_NASCIMENTO, "dataNascimento.contains=" + UPDATED_DATA_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllPessoasByDataNascimentoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where dataNascimento does not contain
        defaultPessoaFiltering(
            "dataNascimento.doesNotContain=" + UPDATED_DATA_NASCIMENTO,
            "dataNascimento.doesNotContain=" + DEFAULT_DATA_NASCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllPessoasByTituloEleitorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tituloEleitor equals to
        defaultPessoaFiltering("tituloEleitor.equals=" + DEFAULT_TITULO_ELEITOR, "tituloEleitor.equals=" + UPDATED_TITULO_ELEITOR);
    }

    @Test
    @Transactional
    void getAllPessoasByTituloEleitorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tituloEleitor in
        defaultPessoaFiltering(
            "tituloEleitor.in=" + DEFAULT_TITULO_ELEITOR + "," + UPDATED_TITULO_ELEITOR,
            "tituloEleitor.in=" + UPDATED_TITULO_ELEITOR
        );
    }

    @Test
    @Transactional
    void getAllPessoasByTituloEleitorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tituloEleitor is not null
        defaultPessoaFiltering("tituloEleitor.specified=true", "tituloEleitor.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByTituloEleitorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tituloEleitor is greater than or equal to
        defaultPessoaFiltering(
            "tituloEleitor.greaterThanOrEqual=" + DEFAULT_TITULO_ELEITOR,
            "tituloEleitor.greaterThanOrEqual=" + UPDATED_TITULO_ELEITOR
        );
    }

    @Test
    @Transactional
    void getAllPessoasByTituloEleitorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tituloEleitor is less than or equal to
        defaultPessoaFiltering(
            "tituloEleitor.lessThanOrEqual=" + DEFAULT_TITULO_ELEITOR,
            "tituloEleitor.lessThanOrEqual=" + SMALLER_TITULO_ELEITOR
        );
    }

    @Test
    @Transactional
    void getAllPessoasByTituloEleitorIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tituloEleitor is less than
        defaultPessoaFiltering("tituloEleitor.lessThan=" + UPDATED_TITULO_ELEITOR, "tituloEleitor.lessThan=" + DEFAULT_TITULO_ELEITOR);
    }

    @Test
    @Transactional
    void getAllPessoasByTituloEleitorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where tituloEleitor is greater than
        defaultPessoaFiltering(
            "tituloEleitor.greaterThan=" + SMALLER_TITULO_ELEITOR,
            "tituloEleitor.greaterThan=" + DEFAULT_TITULO_ELEITOR
        );
    }

    @Test
    @Transactional
    void getAllPessoasByRgIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rg equals to
        defaultPessoaFiltering("rg.equals=" + DEFAULT_RG, "rg.equals=" + UPDATED_RG);
    }

    @Test
    @Transactional
    void getAllPessoasByRgIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rg in
        defaultPessoaFiltering("rg.in=" + DEFAULT_RG + "," + UPDATED_RG, "rg.in=" + UPDATED_RG);
    }

    @Test
    @Transactional
    void getAllPessoasByRgIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rg is not null
        defaultPessoaFiltering("rg.specified=true", "rg.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByRgContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rg contains
        defaultPessoaFiltering("rg.contains=" + DEFAULT_RG, "rg.contains=" + UPDATED_RG);
    }

    @Test
    @Transactional
    void getAllPessoasByRgNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rg does not contain
        defaultPessoaFiltering("rg.doesNotContain=" + UPDATED_RG, "rg.doesNotContain=" + DEFAULT_RG);
    }

    @Test
    @Transactional
    void getAllPessoasByRgOrgaoExpeditorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rgOrgaoExpeditor equals to
        defaultPessoaFiltering(
            "rgOrgaoExpeditor.equals=" + DEFAULT_RG_ORGAO_EXPEDITOR,
            "rgOrgaoExpeditor.equals=" + UPDATED_RG_ORGAO_EXPEDITOR
        );
    }

    @Test
    @Transactional
    void getAllPessoasByRgOrgaoExpeditorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rgOrgaoExpeditor in
        defaultPessoaFiltering(
            "rgOrgaoExpeditor.in=" + DEFAULT_RG_ORGAO_EXPEDITOR + "," + UPDATED_RG_ORGAO_EXPEDITOR,
            "rgOrgaoExpeditor.in=" + UPDATED_RG_ORGAO_EXPEDITOR
        );
    }

    @Test
    @Transactional
    void getAllPessoasByRgOrgaoExpeditorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rgOrgaoExpeditor is not null
        defaultPessoaFiltering("rgOrgaoExpeditor.specified=true", "rgOrgaoExpeditor.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByRgOrgaoExpeditorContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rgOrgaoExpeditor contains
        defaultPessoaFiltering(
            "rgOrgaoExpeditor.contains=" + DEFAULT_RG_ORGAO_EXPEDITOR,
            "rgOrgaoExpeditor.contains=" + UPDATED_RG_ORGAO_EXPEDITOR
        );
    }

    @Test
    @Transactional
    void getAllPessoasByRgOrgaoExpeditorNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rgOrgaoExpeditor does not contain
        defaultPessoaFiltering(
            "rgOrgaoExpeditor.doesNotContain=" + UPDATED_RG_ORGAO_EXPEDITOR,
            "rgOrgaoExpeditor.doesNotContain=" + DEFAULT_RG_ORGAO_EXPEDITOR
        );
    }

    @Test
    @Transactional
    void getAllPessoasByRgUfExpedicaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rgUfExpedicao equals to
        defaultPessoaFiltering("rgUfExpedicao.equals=" + DEFAULT_RG_UF_EXPEDICAO, "rgUfExpedicao.equals=" + UPDATED_RG_UF_EXPEDICAO);
    }

    @Test
    @Transactional
    void getAllPessoasByRgUfExpedicaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rgUfExpedicao in
        defaultPessoaFiltering(
            "rgUfExpedicao.in=" + DEFAULT_RG_UF_EXPEDICAO + "," + UPDATED_RG_UF_EXPEDICAO,
            "rgUfExpedicao.in=" + UPDATED_RG_UF_EXPEDICAO
        );
    }

    @Test
    @Transactional
    void getAllPessoasByRgUfExpedicaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rgUfExpedicao is not null
        defaultPessoaFiltering("rgUfExpedicao.specified=true", "rgUfExpedicao.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByRgUfExpedicaoContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rgUfExpedicao contains
        defaultPessoaFiltering("rgUfExpedicao.contains=" + DEFAULT_RG_UF_EXPEDICAO, "rgUfExpedicao.contains=" + UPDATED_RG_UF_EXPEDICAO);
    }

    @Test
    @Transactional
    void getAllPessoasByRgUfExpedicaoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where rgUfExpedicao does not contain
        defaultPessoaFiltering(
            "rgUfExpedicao.doesNotContain=" + UPDATED_RG_UF_EXPEDICAO,
            "rgUfExpedicao.doesNotContain=" + DEFAULT_RG_UF_EXPEDICAO
        );
    }

    @Test
    @Transactional
    void getAllPessoasByNomeMaeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nomeMae equals to
        defaultPessoaFiltering("nomeMae.equals=" + DEFAULT_NOME_MAE, "nomeMae.equals=" + UPDATED_NOME_MAE);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeMaeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nomeMae in
        defaultPessoaFiltering("nomeMae.in=" + DEFAULT_NOME_MAE + "," + UPDATED_NOME_MAE, "nomeMae.in=" + UPDATED_NOME_MAE);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeMaeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nomeMae is not null
        defaultPessoaFiltering("nomeMae.specified=true", "nomeMae.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByNomeMaeContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nomeMae contains
        defaultPessoaFiltering("nomeMae.contains=" + DEFAULT_NOME_MAE, "nomeMae.contains=" + UPDATED_NOME_MAE);
    }

    @Test
    @Transactional
    void getAllPessoasByNomeMaeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nomeMae does not contain
        defaultPessoaFiltering("nomeMae.doesNotContain=" + UPDATED_NOME_MAE, "nomeMae.doesNotContain=" + DEFAULT_NOME_MAE);
    }

    @Test
    @Transactional
    void getAllPessoasByNomePaiIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nomePai equals to
        defaultPessoaFiltering("nomePai.equals=" + DEFAULT_NOME_PAI, "nomePai.equals=" + UPDATED_NOME_PAI);
    }

    @Test
    @Transactional
    void getAllPessoasByNomePaiIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nomePai in
        defaultPessoaFiltering("nomePai.in=" + DEFAULT_NOME_PAI + "," + UPDATED_NOME_PAI, "nomePai.in=" + UPDATED_NOME_PAI);
    }

    @Test
    @Transactional
    void getAllPessoasByNomePaiIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nomePai is not null
        defaultPessoaFiltering("nomePai.specified=true", "nomePai.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByNomePaiContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nomePai contains
        defaultPessoaFiltering("nomePai.contains=" + DEFAULT_NOME_PAI, "nomePai.contains=" + UPDATED_NOME_PAI);
    }

    @Test
    @Transactional
    void getAllPessoasByNomePaiNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where nomePai does not contain
        defaultPessoaFiltering("nomePai.doesNotContain=" + UPDATED_NOME_PAI, "nomePai.doesNotContain=" + DEFAULT_NOME_PAI);
    }

    @Test
    @Transactional
    void getAllPessoasByLocalNascimentoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where localNascimento equals to
        defaultPessoaFiltering("localNascimento.equals=" + DEFAULT_LOCAL_NASCIMENTO, "localNascimento.equals=" + UPDATED_LOCAL_NASCIMENTO);
    }

    @Test
    @Transactional
    void getAllPessoasByLocalNascimentoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where localNascimento in
        defaultPessoaFiltering(
            "localNascimento.in=" + DEFAULT_LOCAL_NASCIMENTO + "," + UPDATED_LOCAL_NASCIMENTO,
            "localNascimento.in=" + UPDATED_LOCAL_NASCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllPessoasByLocalNascimentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where localNascimento is not null
        defaultPessoaFiltering("localNascimento.specified=true", "localNascimento.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByLocalNascimentoContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where localNascimento contains
        defaultPessoaFiltering(
            "localNascimento.contains=" + DEFAULT_LOCAL_NASCIMENTO,
            "localNascimento.contains=" + UPDATED_LOCAL_NASCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllPessoasByLocalNascimentoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where localNascimento does not contain
        defaultPessoaFiltering(
            "localNascimento.doesNotContain=" + UPDATED_LOCAL_NASCIMENTO,
            "localNascimento.doesNotContain=" + DEFAULT_LOCAL_NASCIMENTO
        );
    }

    @Test
    @Transactional
    void getAllPessoasByRacaECorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where racaECor equals to
        defaultPessoaFiltering("racaECor.equals=" + DEFAULT_RACA_E_COR, "racaECor.equals=" + UPDATED_RACA_E_COR);
    }

    @Test
    @Transactional
    void getAllPessoasByRacaECorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where racaECor in
        defaultPessoaFiltering("racaECor.in=" + DEFAULT_RACA_E_COR + "," + UPDATED_RACA_E_COR, "racaECor.in=" + UPDATED_RACA_E_COR);
    }

    @Test
    @Transactional
    void getAllPessoasByRacaECorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where racaECor is not null
        defaultPessoaFiltering("racaECor.specified=true", "racaECor.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByPessoaComDeficienciaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where pessoaComDeficiencia equals to
        defaultPessoaFiltering(
            "pessoaComDeficiencia.equals=" + DEFAULT_PESSOA_COM_DEFICIENCIA,
            "pessoaComDeficiencia.equals=" + UPDATED_PESSOA_COM_DEFICIENCIA
        );
    }

    @Test
    @Transactional
    void getAllPessoasByPessoaComDeficienciaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where pessoaComDeficiencia in
        defaultPessoaFiltering(
            "pessoaComDeficiencia.in=" + DEFAULT_PESSOA_COM_DEFICIENCIA + "," + UPDATED_PESSOA_COM_DEFICIENCIA,
            "pessoaComDeficiencia.in=" + UPDATED_PESSOA_COM_DEFICIENCIA
        );
    }

    @Test
    @Transactional
    void getAllPessoasByPessoaComDeficienciaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where pessoaComDeficiencia is not null
        defaultPessoaFiltering("pessoaComDeficiencia.specified=true", "pessoaComDeficiencia.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByEstadoCivilIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where estadoCivil equals to
        defaultPessoaFiltering("estadoCivil.equals=" + DEFAULT_ESTADO_CIVIL, "estadoCivil.equals=" + UPDATED_ESTADO_CIVIL);
    }

    @Test
    @Transactional
    void getAllPessoasByEstadoCivilIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where estadoCivil in
        defaultPessoaFiltering(
            "estadoCivil.in=" + DEFAULT_ESTADO_CIVIL + "," + UPDATED_ESTADO_CIVIL,
            "estadoCivil.in=" + UPDATED_ESTADO_CIVIL
        );
    }

    @Test
    @Transactional
    void getAllPessoasByEstadoCivilIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where estadoCivil is not null
        defaultPessoaFiltering("estadoCivil.specified=true", "estadoCivil.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasBySexoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sexo equals to
        defaultPessoaFiltering("sexo.equals=" + DEFAULT_SEXO, "sexo.equals=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllPessoasBySexoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sexo in
        defaultPessoaFiltering("sexo.in=" + DEFAULT_SEXO + "," + UPDATED_SEXO, "sexo.in=" + UPDATED_SEXO);
    }

    @Test
    @Transactional
    void getAllPessoasBySexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where sexo is not null
        defaultPessoaFiltering("sexo.specified=true", "sexo.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByUrlFotoPerfilIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where urlFotoPerfil equals to
        defaultPessoaFiltering("urlFotoPerfil.equals=" + DEFAULT_URL_FOTO_PERFIL, "urlFotoPerfil.equals=" + UPDATED_URL_FOTO_PERFIL);
    }

    @Test
    @Transactional
    void getAllPessoasByUrlFotoPerfilIsInShouldWork() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where urlFotoPerfil in
        defaultPessoaFiltering(
            "urlFotoPerfil.in=" + DEFAULT_URL_FOTO_PERFIL + "," + UPDATED_URL_FOTO_PERFIL,
            "urlFotoPerfil.in=" + UPDATED_URL_FOTO_PERFIL
        );
    }

    @Test
    @Transactional
    void getAllPessoasByUrlFotoPerfilIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where urlFotoPerfil is not null
        defaultPessoaFiltering("urlFotoPerfil.specified=true", "urlFotoPerfil.specified=false");
    }

    @Test
    @Transactional
    void getAllPessoasByUrlFotoPerfilContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where urlFotoPerfil contains
        defaultPessoaFiltering("urlFotoPerfil.contains=" + DEFAULT_URL_FOTO_PERFIL, "urlFotoPerfil.contains=" + UPDATED_URL_FOTO_PERFIL);
    }

    @Test
    @Transactional
    void getAllPessoasByUrlFotoPerfilNotContainsSomething() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList where urlFotoPerfil does not contain
        defaultPessoaFiltering(
            "urlFotoPerfil.doesNotContain=" + UPDATED_URL_FOTO_PERFIL,
            "urlFotoPerfil.doesNotContain=" + DEFAULT_URL_FOTO_PERFIL
        );
    }

    private void defaultPessoaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPessoaShouldBeFound(shouldBeFound);
        defaultPessoaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPessoaShouldBeFound(String filter) throws Exception {
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO)))
            .andExpect(jsonPath("$.[*].tituloEleitor").value(hasItem(DEFAULT_TITULO_ELEITOR)))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG)))
            .andExpect(jsonPath("$.[*].rgOrgaoExpeditor").value(hasItem(DEFAULT_RG_ORGAO_EXPEDITOR)))
            .andExpect(jsonPath("$.[*].rgUfExpedicao").value(hasItem(DEFAULT_RG_UF_EXPEDICAO)))
            .andExpect(jsonPath("$.[*].nomeMae").value(hasItem(DEFAULT_NOME_MAE)))
            .andExpect(jsonPath("$.[*].nomePai").value(hasItem(DEFAULT_NOME_PAI)))
            .andExpect(jsonPath("$.[*].localNascimento").value(hasItem(DEFAULT_LOCAL_NASCIMENTO)))
            .andExpect(jsonPath("$.[*].racaECor").value(hasItem(DEFAULT_RACA_E_COR.toString())))
            .andExpect(jsonPath("$.[*].pessoaComDeficiencia").value(hasItem(DEFAULT_PESSOA_COM_DEFICIENCIA.toString())))
            .andExpect(jsonPath("$.[*].estadoCivil").value(hasItem(DEFAULT_ESTADO_CIVIL.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].urlFotoPerfil").value(hasItem(DEFAULT_URL_FOTO_PERFIL)));

        // Check, that the count call also returns 1
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPessoaShouldNotBeFound(String filter) throws Exception {
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPessoaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPessoa() throws Exception {
        // Get the pessoa
        restPessoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPessoa() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pessoa
        Pessoa updatedPessoa = pessoaRepository.findById(pessoa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPessoa are not directly saved in db
        em.detach(updatedPessoa);
        updatedPessoa
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tituloEleitor(UPDATED_TITULO_ELEITOR)
            .rg(UPDATED_RG)
            .rgOrgaoExpeditor(UPDATED_RG_ORGAO_EXPEDITOR)
            .rgUfExpedicao(UPDATED_RG_UF_EXPEDICAO)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .localNascimento(UPDATED_LOCAL_NASCIMENTO)
            .racaECor(UPDATED_RACA_E_COR)
            .pessoaComDeficiencia(UPDATED_PESSOA_COM_DEFICIENCIA)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .urlFotoPerfil(UPDATED_URL_FOTO_PERFIL);

        restPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPessoa))
            )
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPessoaToMatchAllProperties(updatedPessoa);
    }

    @Test
    @Transactional
    void putNonExistingPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(put(ENTITY_API_URL_ID, pessoa.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoa)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePessoaWithPatch() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pessoa using partial update
        Pessoa partialUpdatedPessoa = new Pessoa();
        partialUpdatedPessoa.setId(pessoa.getId());

        partialUpdatedPessoa
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .rgOrgaoExpeditor(UPDATED_RG_ORGAO_EXPEDITOR)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .racaECor(UPDATED_RACA_E_COR)
            .estadoCivil(UPDATED_ESTADO_CIVIL);

        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPessoa))
            )
            .andExpect(status().isOk());

        // Validate the Pessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPessoaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPessoa, pessoa), getPersistedPessoa(pessoa));
    }

    @Test
    @Transactional
    void fullUpdatePessoaWithPatch() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pessoa using partial update
        Pessoa partialUpdatedPessoa = new Pessoa();
        partialUpdatedPessoa.setId(pessoa.getId());

        partialUpdatedPessoa
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .tituloEleitor(UPDATED_TITULO_ELEITOR)
            .rg(UPDATED_RG)
            .rgOrgaoExpeditor(UPDATED_RG_ORGAO_EXPEDITOR)
            .rgUfExpedicao(UPDATED_RG_UF_EXPEDICAO)
            .nomeMae(UPDATED_NOME_MAE)
            .nomePai(UPDATED_NOME_PAI)
            .localNascimento(UPDATED_LOCAL_NASCIMENTO)
            .racaECor(UPDATED_RACA_E_COR)
            .pessoaComDeficiencia(UPDATED_PESSOA_COM_DEFICIENCIA)
            .estadoCivil(UPDATED_ESTADO_CIVIL)
            .sexo(UPDATED_SEXO)
            .urlFotoPerfil(UPDATED_URL_FOTO_PERFIL);

        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPessoa))
            )
            .andExpect(status().isOk());

        // Validate the Pessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPessoaUpdatableFieldsEquals(partialUpdatedPessoa, getPersistedPessoa(partialUpdatedPessoa));
    }

    @Test
    @Transactional
    void patchNonExistingPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pessoa.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePessoa() throws Exception {
        // Initialize the database
        insertedPessoa = pessoaRepository.saveAndFlush(pessoa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pessoa
        restPessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, pessoa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pessoaRepository.count();
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

    protected Pessoa getPersistedPessoa(Pessoa pessoa) {
        return pessoaRepository.findById(pessoa.getId()).orElseThrow();
    }

    protected void assertPersistedPessoaToMatchAllProperties(Pessoa expectedPessoa) {
        assertPessoaAllPropertiesEquals(expectedPessoa, getPersistedPessoa(expectedPessoa));
    }

    protected void assertPersistedPessoaToMatchUpdatableProperties(Pessoa expectedPessoa) {
        assertPessoaAllUpdatablePropertiesEquals(expectedPessoa, getPersistedPessoa(expectedPessoa));
    }
}
