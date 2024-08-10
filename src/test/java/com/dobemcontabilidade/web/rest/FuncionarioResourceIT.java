package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FuncionarioAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.domain.enumeration.SituacaoFuncionarioEnum;
import com.dobemcontabilidade.repository.FuncionarioRepository;
import com.dobemcontabilidade.service.FuncionarioService;
import com.dobemcontabilidade.service.dto.FuncionarioDTO;
import com.dobemcontabilidade.service.mapper.FuncionarioMapper;
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
 * Integration tests for the {@link FuncionarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FuncionarioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Double DEFAULT_SALARIO = 1D;
    private static final Double UPDATED_SALARIO = 2D;
    private static final Double SMALLER_SALARIO = 1D - 1D;

    private static final String DEFAULT_CTPS = "AAAAAAAAAA";
    private static final String UPDATED_CTPS = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_ATIVIDADES = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_ATIVIDADES = "BBBBBBBBBB";

    private static final SituacaoFuncionarioEnum DEFAULT_SITUACAO = SituacaoFuncionarioEnum.ADMITIDO;
    private static final SituacaoFuncionarioEnum UPDATED_SITUACAO = SituacaoFuncionarioEnum.AFASTADO;

    private static final String ENTITY_API_URL = "/api/funcionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private FuncionarioRepository funcionarioRepositoryMock;

    @Autowired
    private FuncionarioMapper funcionarioMapper;

    @Mock
    private FuncionarioService funcionarioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionarioMockMvc;

    private Funcionario funcionario;

    private Funcionario insertedFuncionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .nome(DEFAULT_NOME)
            .salario(DEFAULT_SALARIO)
            .ctps(DEFAULT_CTPS)
            .cargo(DEFAULT_CARGO)
            .descricaoAtividades(DEFAULT_DESCRICAO_ATIVIDADES)
            .situacao(DEFAULT_SITUACAO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        funcionario.setPessoa(pessoa);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createUpdatedEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .nome(UPDATED_NOME)
            .salario(UPDATED_SALARIO)
            .ctps(UPDATED_CTPS)
            .cargo(UPDATED_CARGO)
            .descricaoAtividades(UPDATED_DESCRICAO_ATIVIDADES)
            .situacao(UPDATED_SITUACAO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        funcionario.setPessoa(pessoa);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        funcionario.setEmpresa(empresa);
        return funcionario;
    }

    @BeforeEach
    public void initTest() {
        funcionario = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFuncionario != null) {
            funcionarioRepository.delete(insertedFuncionario);
            insertedFuncionario = null;
        }
    }

    @Test
    @Transactional
    void createFuncionario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);
        var returnedFuncionarioDTO = om.readValue(
            restFuncionarioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionarioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FuncionarioDTO.class
        );

        // Validate the Funcionario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFuncionario = funcionarioMapper.toEntity(returnedFuncionarioDTO);
        assertFuncionarioUpdatableFieldsEquals(returnedFuncionario, getPersistedFuncionario(returnedFuncionario));

        insertedFuncionario = returnedFuncionario;
    }

    @Test
    @Transactional
    void createFuncionarioWithExistingId() throws Exception {
        // Create the Funcionario with an existing ID
        funcionario.setId(1L);
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuncionarios() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(DEFAULT_SALARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].ctps").value(hasItem(DEFAULT_CTPS)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].descricaoAtividades").value(hasItem(DEFAULT_DESCRICAO_ATIVIDADES.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFuncionariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(funcionarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFuncionarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(funcionarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFuncionariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(funcionarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFuncionarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(funcionarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFuncionario() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get the funcionario
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, funcionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.salario").value(DEFAULT_SALARIO.doubleValue()))
            .andExpect(jsonPath("$.ctps").value(DEFAULT_CTPS))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO))
            .andExpect(jsonPath("$.descricaoAtividades").value(DEFAULT_DESCRICAO_ATIVIDADES.toString()))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    void getFuncionariosByIdFiltering() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        Long id = funcionario.getId();

        defaultFuncionarioFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultFuncionarioFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultFuncionarioFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFuncionariosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nome equals to
        defaultFuncionarioFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nome in
        defaultFuncionarioFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nome is not null
        defaultFuncionarioFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nome contains
        defaultFuncionarioFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nome does not contain
        defaultFuncionarioFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosBySalarioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where salario equals to
        defaultFuncionarioFiltering("salario.equals=" + DEFAULT_SALARIO, "salario.equals=" + UPDATED_SALARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBySalarioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where salario in
        defaultFuncionarioFiltering("salario.in=" + DEFAULT_SALARIO + "," + UPDATED_SALARIO, "salario.in=" + UPDATED_SALARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBySalarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where salario is not null
        defaultFuncionarioFiltering("salario.specified=true", "salario.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosBySalarioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where salario is greater than or equal to
        defaultFuncionarioFiltering("salario.greaterThanOrEqual=" + DEFAULT_SALARIO, "salario.greaterThanOrEqual=" + UPDATED_SALARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBySalarioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where salario is less than or equal to
        defaultFuncionarioFiltering("salario.lessThanOrEqual=" + DEFAULT_SALARIO, "salario.lessThanOrEqual=" + SMALLER_SALARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBySalarioIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where salario is less than
        defaultFuncionarioFiltering("salario.lessThan=" + UPDATED_SALARIO, "salario.lessThan=" + DEFAULT_SALARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBySalarioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where salario is greater than
        defaultFuncionarioFiltering("salario.greaterThan=" + SMALLER_SALARIO, "salario.greaterThan=" + DEFAULT_SALARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByCtpsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where ctps equals to
        defaultFuncionarioFiltering("ctps.equals=" + DEFAULT_CTPS, "ctps.equals=" + UPDATED_CTPS);
    }

    @Test
    @Transactional
    void getAllFuncionariosByCtpsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where ctps in
        defaultFuncionarioFiltering("ctps.in=" + DEFAULT_CTPS + "," + UPDATED_CTPS, "ctps.in=" + UPDATED_CTPS);
    }

    @Test
    @Transactional
    void getAllFuncionariosByCtpsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where ctps is not null
        defaultFuncionarioFiltering("ctps.specified=true", "ctps.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByCtpsContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where ctps contains
        defaultFuncionarioFiltering("ctps.contains=" + DEFAULT_CTPS, "ctps.contains=" + UPDATED_CTPS);
    }

    @Test
    @Transactional
    void getAllFuncionariosByCtpsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where ctps does not contain
        defaultFuncionarioFiltering("ctps.doesNotContain=" + UPDATED_CTPS, "ctps.doesNotContain=" + DEFAULT_CTPS);
    }

    @Test
    @Transactional
    void getAllFuncionariosByCargoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where cargo equals to
        defaultFuncionarioFiltering("cargo.equals=" + DEFAULT_CARGO, "cargo.equals=" + UPDATED_CARGO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByCargoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where cargo in
        defaultFuncionarioFiltering("cargo.in=" + DEFAULT_CARGO + "," + UPDATED_CARGO, "cargo.in=" + UPDATED_CARGO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByCargoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where cargo is not null
        defaultFuncionarioFiltering("cargo.specified=true", "cargo.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByCargoContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where cargo contains
        defaultFuncionarioFiltering("cargo.contains=" + DEFAULT_CARGO, "cargo.contains=" + UPDATED_CARGO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByCargoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where cargo does not contain
        defaultFuncionarioFiltering("cargo.doesNotContain=" + UPDATED_CARGO, "cargo.doesNotContain=" + DEFAULT_CARGO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBySituacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where situacao equals to
        defaultFuncionarioFiltering("situacao.equals=" + DEFAULT_SITUACAO, "situacao.equals=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBySituacaoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where situacao in
        defaultFuncionarioFiltering("situacao.in=" + DEFAULT_SITUACAO + "," + UPDATED_SITUACAO, "situacao.in=" + UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBySituacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where situacao is not null
        defaultFuncionarioFiltering("situacao.specified=true", "situacao.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByPessoaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Pessoa pessoa = funcionario.getPessoa();
        funcionarioRepository.saveAndFlush(funcionario);
        Long pessoaId = pessoa.getId();
        // Get all the funcionarioList where pessoa equals to pessoaId
        defaultFuncionarioShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the funcionarioList where pessoa equals to (pessoaId + 1)
        defaultFuncionarioShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    @Test
    @Transactional
    void getAllFuncionariosByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            funcionarioRepository.saveAndFlush(funcionario);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        funcionario.setEmpresa(empresa);
        funcionarioRepository.saveAndFlush(funcionario);
        Long empresaId = empresa.getId();
        // Get all the funcionarioList where empresa equals to empresaId
        defaultFuncionarioShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the funcionarioList where empresa equals to (empresaId + 1)
        defaultFuncionarioShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    private void defaultFuncionarioFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultFuncionarioShouldBeFound(shouldBeFound);
        defaultFuncionarioShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuncionarioShouldBeFound(String filter) throws Exception {
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].salario").value(hasItem(DEFAULT_SALARIO.doubleValue())))
            .andExpect(jsonPath("$.[*].ctps").value(hasItem(DEFAULT_CTPS)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].descricaoAtividades").value(hasItem(DEFAULT_DESCRICAO_ATIVIDADES.toString())))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));

        // Check, that the count call also returns 1
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuncionarioShouldNotBeFound(String filter) throws Exception {
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFuncionario() throws Exception {
        // Get the funcionario
        restFuncionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuncionario() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionario
        Funcionario updatedFuncionario = funcionarioRepository.findById(funcionario.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFuncionario are not directly saved in db
        em.detach(updatedFuncionario);
        updatedFuncionario
            .nome(UPDATED_NOME)
            .salario(UPDATED_SALARIO)
            .ctps(UPDATED_CTPS)
            .cargo(UPDATED_CARGO)
            .descricaoAtividades(UPDATED_DESCRICAO_ATIVIDADES)
            .situacao(UPDATED_SITUACAO);
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(updatedFuncionario);

        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuncionarioToMatchAllProperties(updatedFuncionario);
    }

    @Test
    @Transactional
    void putNonExistingFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncionarioWithPatch() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionario using partial update
        Funcionario partialUpdatedFuncionario = new Funcionario();
        partialUpdatedFuncionario.setId(funcionario.getId());

        partialUpdatedFuncionario
            .nome(UPDATED_NOME)
            .salario(UPDATED_SALARIO)
            .descricaoAtividades(UPDATED_DESCRICAO_ATIVIDADES)
            .situacao(UPDATED_SITUACAO);

        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuncionarioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFuncionario, funcionario),
            getPersistedFuncionario(funcionario)
        );
    }

    @Test
    @Transactional
    void fullUpdateFuncionarioWithPatch() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the funcionario using partial update
        Funcionario partialUpdatedFuncionario = new Funcionario();
        partialUpdatedFuncionario.setId(funcionario.getId());

        partialUpdatedFuncionario
            .nome(UPDATED_NOME)
            .salario(UPDATED_SALARIO)
            .ctps(UPDATED_CTPS)
            .cargo(UPDATED_CARGO)
            .descricaoAtividades(UPDATED_DESCRICAO_ATIVIDADES)
            .situacao(UPDATED_SITUACAO);

        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuncionarioUpdatableFieldsEquals(partialUpdatedFuncionario, getPersistedFuncionario(partialUpdatedFuncionario));
    }

    @Test
    @Transactional
    void patchNonExistingFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        funcionario.setId(longCount.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(funcionarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncionario() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the funcionario
        restFuncionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return funcionarioRepository.count();
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

    protected Funcionario getPersistedFuncionario(Funcionario funcionario) {
        return funcionarioRepository.findById(funcionario.getId()).orElseThrow();
    }

    protected void assertPersistedFuncionarioToMatchAllProperties(Funcionario expectedFuncionario) {
        assertFuncionarioAllPropertiesEquals(expectedFuncionario, getPersistedFuncionario(expectedFuncionario));
    }

    protected void assertPersistedFuncionarioToMatchUpdatableProperties(Funcionario expectedFuncionario) {
        assertFuncionarioAllUpdatablePropertiesEquals(expectedFuncionario, getPersistedFuncionario(expectedFuncionario));
    }
}
