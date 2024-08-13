package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EnderecoPessoaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.EnderecoPessoa;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.repository.EnderecoPessoaRepository;
import com.dobemcontabilidade.service.EnderecoPessoaService;
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
 * Integration tests for the {@link EnderecoPessoaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EnderecoPessoaResourceIT {

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAA";
    private static final String UPDATED_NUMERO = "BBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBB";

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final Boolean DEFAULT_RESIDENCIA_PROPRIA = false;
    private static final Boolean UPDATED_RESIDENCIA_PROPRIA = true;

    private static final String ENTITY_API_URL = "/api/endereco-pessoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EnderecoPessoaRepository enderecoPessoaRepository;

    @Mock
    private EnderecoPessoaRepository enderecoPessoaRepositoryMock;

    @Mock
    private EnderecoPessoaService enderecoPessoaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnderecoPessoaMockMvc;

    private EnderecoPessoa enderecoPessoa;

    private EnderecoPessoa insertedEnderecoPessoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoPessoa createEntity(EntityManager em) {
        EnderecoPessoa enderecoPessoa = new EnderecoPessoa()
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .bairro(DEFAULT_BAIRRO)
            .cep(DEFAULT_CEP)
            .principal(DEFAULT_PRINCIPAL)
            .residenciaPropria(DEFAULT_RESIDENCIA_PROPRIA);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        enderecoPessoa.setPessoa(pessoa);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        enderecoPessoa.setCidade(cidade);
        return enderecoPessoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoPessoa createUpdatedEntity(EntityManager em) {
        EnderecoPessoa enderecoPessoa = new EnderecoPessoa()
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL)
            .residenciaPropria(UPDATED_RESIDENCIA_PROPRIA);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        enderecoPessoa.setPessoa(pessoa);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createUpdatedEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        enderecoPessoa.setCidade(cidade);
        return enderecoPessoa;
    }

    @BeforeEach
    public void initTest() {
        enderecoPessoa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEnderecoPessoa != null) {
            enderecoPessoaRepository.delete(insertedEnderecoPessoa);
            insertedEnderecoPessoa = null;
        }
    }

    @Test
    @Transactional
    void createEnderecoPessoa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EnderecoPessoa
        var returnedEnderecoPessoa = om.readValue(
            restEnderecoPessoaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoPessoa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EnderecoPessoa.class
        );

        // Validate the EnderecoPessoa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEnderecoPessoaUpdatableFieldsEquals(returnedEnderecoPessoa, getPersistedEnderecoPessoa(returnedEnderecoPessoa));

        insertedEnderecoPessoa = returnedEnderecoPessoa;
    }

    @Test
    @Transactional
    void createEnderecoPessoaWithExistingId() throws Exception {
        // Create the EnderecoPessoa with an existing ID
        enderecoPessoa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnderecoPessoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoPessoa)))
            .andExpect(status().isBadRequest());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoas() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList
        restEnderecoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enderecoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())))
            .andExpect(jsonPath("$.[*].residenciaPropria").value(hasItem(DEFAULT_RESIDENCIA_PROPRIA.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEnderecoPessoasWithEagerRelationshipsIsEnabled() throws Exception {
        when(enderecoPessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEnderecoPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(enderecoPessoaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEnderecoPessoasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(enderecoPessoaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEnderecoPessoaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(enderecoPessoaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEnderecoPessoa() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get the enderecoPessoa
        restEnderecoPessoaMockMvc
            .perform(get(ENTITY_API_URL_ID, enderecoPessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enderecoPessoa.getId().intValue()))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()))
            .andExpect(jsonPath("$.residenciaPropria").value(DEFAULT_RESIDENCIA_PROPRIA.booleanValue()));
    }

    @Test
    @Transactional
    void getEnderecoPessoasByIdFiltering() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        Long id = enderecoPessoa.getId();

        defaultEnderecoPessoaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEnderecoPessoaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEnderecoPessoaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByLogradouroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where logradouro equals to
        defaultEnderecoPessoaFiltering("logradouro.equals=" + DEFAULT_LOGRADOURO, "logradouro.equals=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByLogradouroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where logradouro in
        defaultEnderecoPessoaFiltering(
            "logradouro.in=" + DEFAULT_LOGRADOURO + "," + UPDATED_LOGRADOURO,
            "logradouro.in=" + UPDATED_LOGRADOURO
        );
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByLogradouroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where logradouro is not null
        defaultEnderecoPessoaFiltering("logradouro.specified=true", "logradouro.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByLogradouroContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where logradouro contains
        defaultEnderecoPessoaFiltering("logradouro.contains=" + DEFAULT_LOGRADOURO, "logradouro.contains=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByLogradouroNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where logradouro does not contain
        defaultEnderecoPessoaFiltering(
            "logradouro.doesNotContain=" + UPDATED_LOGRADOURO,
            "logradouro.doesNotContain=" + DEFAULT_LOGRADOURO
        );
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where numero equals to
        defaultEnderecoPessoaFiltering("numero.equals=" + DEFAULT_NUMERO, "numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where numero in
        defaultEnderecoPessoaFiltering("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO, "numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where numero is not null
        defaultEnderecoPessoaFiltering("numero.specified=true", "numero.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByNumeroContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where numero contains
        defaultEnderecoPessoaFiltering("numero.contains=" + DEFAULT_NUMERO, "numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where numero does not contain
        defaultEnderecoPessoaFiltering("numero.doesNotContain=" + UPDATED_NUMERO, "numero.doesNotContain=" + DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByComplementoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where complemento equals to
        defaultEnderecoPessoaFiltering("complemento.equals=" + DEFAULT_COMPLEMENTO, "complemento.equals=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByComplementoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where complemento in
        defaultEnderecoPessoaFiltering(
            "complemento.in=" + DEFAULT_COMPLEMENTO + "," + UPDATED_COMPLEMENTO,
            "complemento.in=" + UPDATED_COMPLEMENTO
        );
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByComplementoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where complemento is not null
        defaultEnderecoPessoaFiltering("complemento.specified=true", "complemento.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByComplementoContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where complemento contains
        defaultEnderecoPessoaFiltering("complemento.contains=" + DEFAULT_COMPLEMENTO, "complemento.contains=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByComplementoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where complemento does not contain
        defaultEnderecoPessoaFiltering(
            "complemento.doesNotContain=" + UPDATED_COMPLEMENTO,
            "complemento.doesNotContain=" + DEFAULT_COMPLEMENTO
        );
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByBairroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where bairro equals to
        defaultEnderecoPessoaFiltering("bairro.equals=" + DEFAULT_BAIRRO, "bairro.equals=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByBairroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where bairro in
        defaultEnderecoPessoaFiltering("bairro.in=" + DEFAULT_BAIRRO + "," + UPDATED_BAIRRO, "bairro.in=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByBairroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where bairro is not null
        defaultEnderecoPessoaFiltering("bairro.specified=true", "bairro.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByBairroContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where bairro contains
        defaultEnderecoPessoaFiltering("bairro.contains=" + DEFAULT_BAIRRO, "bairro.contains=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByBairroNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where bairro does not contain
        defaultEnderecoPessoaFiltering("bairro.doesNotContain=" + UPDATED_BAIRRO, "bairro.doesNotContain=" + DEFAULT_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByCepIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where cep equals to
        defaultEnderecoPessoaFiltering("cep.equals=" + DEFAULT_CEP, "cep.equals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByCepIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where cep in
        defaultEnderecoPessoaFiltering("cep.in=" + DEFAULT_CEP + "," + UPDATED_CEP, "cep.in=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByCepIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where cep is not null
        defaultEnderecoPessoaFiltering("cep.specified=true", "cep.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByCepContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where cep contains
        defaultEnderecoPessoaFiltering("cep.contains=" + DEFAULT_CEP, "cep.contains=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByCepNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where cep does not contain
        defaultEnderecoPessoaFiltering("cep.doesNotContain=" + UPDATED_CEP, "cep.doesNotContain=" + DEFAULT_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByPrincipalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where principal equals to
        defaultEnderecoPessoaFiltering("principal.equals=" + DEFAULT_PRINCIPAL, "principal.equals=" + UPDATED_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByPrincipalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where principal in
        defaultEnderecoPessoaFiltering("principal.in=" + DEFAULT_PRINCIPAL + "," + UPDATED_PRINCIPAL, "principal.in=" + UPDATED_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByPrincipalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where principal is not null
        defaultEnderecoPessoaFiltering("principal.specified=true", "principal.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByResidenciaPropriaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where residenciaPropria equals to
        defaultEnderecoPessoaFiltering(
            "residenciaPropria.equals=" + DEFAULT_RESIDENCIA_PROPRIA,
            "residenciaPropria.equals=" + UPDATED_RESIDENCIA_PROPRIA
        );
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByResidenciaPropriaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where residenciaPropria in
        defaultEnderecoPessoaFiltering(
            "residenciaPropria.in=" + DEFAULT_RESIDENCIA_PROPRIA + "," + UPDATED_RESIDENCIA_PROPRIA,
            "residenciaPropria.in=" + UPDATED_RESIDENCIA_PROPRIA
        );
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByResidenciaPropriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        // Get all the enderecoPessoaList where residenciaPropria is not null
        defaultEnderecoPessoaFiltering("residenciaPropria.specified=true", "residenciaPropria.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByPessoaIsEqualToSomething() throws Exception {
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            enderecoPessoaRepository.saveAndFlush(enderecoPessoa);
            pessoa = PessoaResourceIT.createEntity(em);
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        em.persist(pessoa);
        em.flush();
        enderecoPessoa.setPessoa(pessoa);
        enderecoPessoaRepository.saveAndFlush(enderecoPessoa);
        Long pessoaId = pessoa.getId();
        // Get all the enderecoPessoaList where pessoa equals to pessoaId
        defaultEnderecoPessoaShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the enderecoPessoaList where pessoa equals to (pessoaId + 1)
        defaultEnderecoPessoaShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    @Test
    @Transactional
    void getAllEnderecoPessoasByCidadeIsEqualToSomething() throws Exception {
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            enderecoPessoaRepository.saveAndFlush(enderecoPessoa);
            cidade = CidadeResourceIT.createEntity(em);
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        em.persist(cidade);
        em.flush();
        enderecoPessoa.setCidade(cidade);
        enderecoPessoaRepository.saveAndFlush(enderecoPessoa);
        Long cidadeId = cidade.getId();
        // Get all the enderecoPessoaList where cidade equals to cidadeId
        defaultEnderecoPessoaShouldBeFound("cidadeId.equals=" + cidadeId);

        // Get all the enderecoPessoaList where cidade equals to (cidadeId + 1)
        defaultEnderecoPessoaShouldNotBeFound("cidadeId.equals=" + (cidadeId + 1));
    }

    private void defaultEnderecoPessoaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEnderecoPessoaShouldBeFound(shouldBeFound);
        defaultEnderecoPessoaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnderecoPessoaShouldBeFound(String filter) throws Exception {
        restEnderecoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enderecoPessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())))
            .andExpect(jsonPath("$.[*].residenciaPropria").value(hasItem(DEFAULT_RESIDENCIA_PROPRIA.booleanValue())));

        // Check, that the count call also returns 1
        restEnderecoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnderecoPessoaShouldNotBeFound(String filter) throws Exception {
        restEnderecoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnderecoPessoaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEnderecoPessoa() throws Exception {
        // Get the enderecoPessoa
        restEnderecoPessoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnderecoPessoa() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoPessoa
        EnderecoPessoa updatedEnderecoPessoa = enderecoPessoaRepository.findById(enderecoPessoa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEnderecoPessoa are not directly saved in db
        em.detach(updatedEnderecoPessoa);
        updatedEnderecoPessoa
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL)
            .residenciaPropria(UPDATED_RESIDENCIA_PROPRIA);

        restEnderecoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEnderecoPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEnderecoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEnderecoPessoaToMatchAllProperties(updatedEnderecoPessoa);
    }

    @Test
    @Transactional
    void putNonExistingEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enderecoPessoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enderecoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enderecoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnderecoPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoPessoa using partial update
        EnderecoPessoa partialUpdatedEnderecoPessoa = new EnderecoPessoa();
        partialUpdatedEnderecoPessoa.setId(enderecoPessoa.getId());

        partialUpdatedEnderecoPessoa
            .logradouro(UPDATED_LOGRADOURO)
            .principal(UPDATED_PRINCIPAL)
            .residenciaPropria(UPDATED_RESIDENCIA_PROPRIA);

        restEnderecoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnderecoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnderecoPessoaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEnderecoPessoa, enderecoPessoa),
            getPersistedEnderecoPessoa(enderecoPessoa)
        );
    }

    @Test
    @Transactional
    void fullUpdateEnderecoPessoaWithPatch() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoPessoa using partial update
        EnderecoPessoa partialUpdatedEnderecoPessoa = new EnderecoPessoa();
        partialUpdatedEnderecoPessoa.setId(enderecoPessoa.getId());

        partialUpdatedEnderecoPessoa
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL)
            .residenciaPropria(UPDATED_RESIDENCIA_PROPRIA);

        restEnderecoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnderecoPessoa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoPessoa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnderecoPessoaUpdatableFieldsEquals(partialUpdatedEnderecoPessoa, getPersistedEnderecoPessoa(partialUpdatedEnderecoPessoa));
    }

    @Test
    @Transactional
    void patchNonExistingEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enderecoPessoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enderecoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enderecoPessoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnderecoPessoa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoPessoa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoPessoaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(enderecoPessoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoPessoa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnderecoPessoa() throws Exception {
        // Initialize the database
        insertedEnderecoPessoa = enderecoPessoaRepository.saveAndFlush(enderecoPessoa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the enderecoPessoa
        restEnderecoPessoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, enderecoPessoa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return enderecoPessoaRepository.count();
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

    protected EnderecoPessoa getPersistedEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        return enderecoPessoaRepository.findById(enderecoPessoa.getId()).orElseThrow();
    }

    protected void assertPersistedEnderecoPessoaToMatchAllProperties(EnderecoPessoa expectedEnderecoPessoa) {
        assertEnderecoPessoaAllPropertiesEquals(expectedEnderecoPessoa, getPersistedEnderecoPessoa(expectedEnderecoPessoa));
    }

    protected void assertPersistedEnderecoPessoaToMatchUpdatableProperties(EnderecoPessoa expectedEnderecoPessoa) {
        assertEnderecoPessoaAllUpdatablePropertiesEquals(expectedEnderecoPessoa, getPersistedEnderecoPessoa(expectedEnderecoPessoa));
    }
}
