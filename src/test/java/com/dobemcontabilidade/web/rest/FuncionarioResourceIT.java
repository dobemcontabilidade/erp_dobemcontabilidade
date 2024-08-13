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
import com.dobemcontabilidade.domain.Profissao;
import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.domain.enumeration.TipoFuncionarioEnum;
import com.dobemcontabilidade.repository.FuncionarioRepository;
import com.dobemcontabilidade.service.FuncionarioService;
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

    private static final Integer DEFAULT_NUMERO_PIS_NIS_PASEP = 1;
    private static final Integer UPDATED_NUMERO_PIS_NIS_PASEP = 2;
    private static final Integer SMALLER_NUMERO_PIS_NIS_PASEP = 1 - 1;

    private static final Boolean DEFAULT_REINTEGRADO = false;
    private static final Boolean UPDATED_REINTEGRADO = true;

    private static final Boolean DEFAULT_PRIMEIRO_EMPREGO = false;
    private static final Boolean UPDATED_PRIMEIRO_EMPREGO = true;

    private static final Boolean DEFAULT_MULTIPLO_VINCULOS = false;
    private static final Boolean UPDATED_MULTIPLO_VINCULOS = true;

    private static final String DEFAULT_DATA_OPCAO_FGTS = "AAAAAAAAAA";
    private static final String UPDATED_DATA_OPCAO_FGTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FILIACAO_SINDICAL = false;
    private static final Boolean UPDATED_FILIACAO_SINDICAL = true;

    private static final String DEFAULT_CNPJ_SINDICATO = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ_SINDICATO = "BBBBBBBBBB";

    private static final TipoFuncionarioEnum DEFAULT_TIPO_FUNCIONARIO_ENUM = TipoFuncionarioEnum.NORMAL;
    private static final TipoFuncionarioEnum UPDATED_TIPO_FUNCIONARIO_ENUM = TipoFuncionarioEnum.ESTAGIARIO;

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
            .numeroPisNisPasep(DEFAULT_NUMERO_PIS_NIS_PASEP)
            .reintegrado(DEFAULT_REINTEGRADO)
            .primeiroEmprego(DEFAULT_PRIMEIRO_EMPREGO)
            .multiploVinculos(DEFAULT_MULTIPLO_VINCULOS)
            .dataOpcaoFgts(DEFAULT_DATA_OPCAO_FGTS)
            .filiacaoSindical(DEFAULT_FILIACAO_SINDICAL)
            .cnpjSindicato(DEFAULT_CNPJ_SINDICATO)
            .tipoFuncionarioEnum(DEFAULT_TIPO_FUNCIONARIO_ENUM);
        // Add required entity
        UsuarioEmpresa usuarioEmpresa;
        if (TestUtil.findAll(em, UsuarioEmpresa.class).isEmpty()) {
            usuarioEmpresa = UsuarioEmpresaResourceIT.createEntity(em);
            em.persist(usuarioEmpresa);
            em.flush();
        } else {
            usuarioEmpresa = TestUtil.findAll(em, UsuarioEmpresa.class).get(0);
        }
        funcionario.setUsuarioEmpresa(usuarioEmpresa);
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
        // Add required entity
        Profissao profissao;
        if (TestUtil.findAll(em, Profissao.class).isEmpty()) {
            profissao = ProfissaoResourceIT.createEntity(em);
            em.persist(profissao);
            em.flush();
        } else {
            profissao = TestUtil.findAll(em, Profissao.class).get(0);
        }
        funcionario.setProfissao(profissao);
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
            .numeroPisNisPasep(UPDATED_NUMERO_PIS_NIS_PASEP)
            .reintegrado(UPDATED_REINTEGRADO)
            .primeiroEmprego(UPDATED_PRIMEIRO_EMPREGO)
            .multiploVinculos(UPDATED_MULTIPLO_VINCULOS)
            .dataOpcaoFgts(UPDATED_DATA_OPCAO_FGTS)
            .filiacaoSindical(UPDATED_FILIACAO_SINDICAL)
            .cnpjSindicato(UPDATED_CNPJ_SINDICATO)
            .tipoFuncionarioEnum(UPDATED_TIPO_FUNCIONARIO_ENUM);
        // Add required entity
        UsuarioEmpresa usuarioEmpresa;
        if (TestUtil.findAll(em, UsuarioEmpresa.class).isEmpty()) {
            usuarioEmpresa = UsuarioEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(usuarioEmpresa);
            em.flush();
        } else {
            usuarioEmpresa = TestUtil.findAll(em, UsuarioEmpresa.class).get(0);
        }
        funcionario.setUsuarioEmpresa(usuarioEmpresa);
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
        // Add required entity
        Profissao profissao;
        if (TestUtil.findAll(em, Profissao.class).isEmpty()) {
            profissao = ProfissaoResourceIT.createUpdatedEntity(em);
            em.persist(profissao);
            em.flush();
        } else {
            profissao = TestUtil.findAll(em, Profissao.class).get(0);
        }
        funcionario.setProfissao(profissao);
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
        var returnedFuncionario = om.readValue(
            restFuncionarioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionario)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Funcionario.class
        );

        // Validate the Funcionario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFuncionarioUpdatableFieldsEquals(returnedFuncionario, getPersistedFuncionario(returnedFuncionario));

        insertedFuncionario = returnedFuncionario;
    }

    @Test
    @Transactional
    void createFuncionarioWithExistingId() throws Exception {
        // Create the Funcionario with an existing ID
        funcionario.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionario)))
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
            .andExpect(jsonPath("$.[*].numeroPisNisPasep").value(hasItem(DEFAULT_NUMERO_PIS_NIS_PASEP)))
            .andExpect(jsonPath("$.[*].reintegrado").value(hasItem(DEFAULT_REINTEGRADO.booleanValue())))
            .andExpect(jsonPath("$.[*].primeiroEmprego").value(hasItem(DEFAULT_PRIMEIRO_EMPREGO.booleanValue())))
            .andExpect(jsonPath("$.[*].multiploVinculos").value(hasItem(DEFAULT_MULTIPLO_VINCULOS.booleanValue())))
            .andExpect(jsonPath("$.[*].dataOpcaoFgts").value(hasItem(DEFAULT_DATA_OPCAO_FGTS)))
            .andExpect(jsonPath("$.[*].filiacaoSindical").value(hasItem(DEFAULT_FILIACAO_SINDICAL.booleanValue())))
            .andExpect(jsonPath("$.[*].cnpjSindicato").value(hasItem(DEFAULT_CNPJ_SINDICATO)))
            .andExpect(jsonPath("$.[*].tipoFuncionarioEnum").value(hasItem(DEFAULT_TIPO_FUNCIONARIO_ENUM.toString())));
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
            .andExpect(jsonPath("$.numeroPisNisPasep").value(DEFAULT_NUMERO_PIS_NIS_PASEP))
            .andExpect(jsonPath("$.reintegrado").value(DEFAULT_REINTEGRADO.booleanValue()))
            .andExpect(jsonPath("$.primeiroEmprego").value(DEFAULT_PRIMEIRO_EMPREGO.booleanValue()))
            .andExpect(jsonPath("$.multiploVinculos").value(DEFAULT_MULTIPLO_VINCULOS.booleanValue()))
            .andExpect(jsonPath("$.dataOpcaoFgts").value(DEFAULT_DATA_OPCAO_FGTS))
            .andExpect(jsonPath("$.filiacaoSindical").value(DEFAULT_FILIACAO_SINDICAL.booleanValue()))
            .andExpect(jsonPath("$.cnpjSindicato").value(DEFAULT_CNPJ_SINDICATO))
            .andExpect(jsonPath("$.tipoFuncionarioEnum").value(DEFAULT_TIPO_FUNCIONARIO_ENUM.toString()));
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
    void getAllFuncionariosByNumeroPisNisPasepIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where numeroPisNisPasep equals to
        defaultFuncionarioFiltering(
            "numeroPisNisPasep.equals=" + DEFAULT_NUMERO_PIS_NIS_PASEP,
            "numeroPisNisPasep.equals=" + UPDATED_NUMERO_PIS_NIS_PASEP
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByNumeroPisNisPasepIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where numeroPisNisPasep in
        defaultFuncionarioFiltering(
            "numeroPisNisPasep.in=" + DEFAULT_NUMERO_PIS_NIS_PASEP + "," + UPDATED_NUMERO_PIS_NIS_PASEP,
            "numeroPisNisPasep.in=" + UPDATED_NUMERO_PIS_NIS_PASEP
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByNumeroPisNisPasepIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where numeroPisNisPasep is not null
        defaultFuncionarioFiltering("numeroPisNisPasep.specified=true", "numeroPisNisPasep.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByNumeroPisNisPasepIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where numeroPisNisPasep is greater than or equal to
        defaultFuncionarioFiltering(
            "numeroPisNisPasep.greaterThanOrEqual=" + DEFAULT_NUMERO_PIS_NIS_PASEP,
            "numeroPisNisPasep.greaterThanOrEqual=" + UPDATED_NUMERO_PIS_NIS_PASEP
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByNumeroPisNisPasepIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where numeroPisNisPasep is less than or equal to
        defaultFuncionarioFiltering(
            "numeroPisNisPasep.lessThanOrEqual=" + DEFAULT_NUMERO_PIS_NIS_PASEP,
            "numeroPisNisPasep.lessThanOrEqual=" + SMALLER_NUMERO_PIS_NIS_PASEP
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByNumeroPisNisPasepIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where numeroPisNisPasep is less than
        defaultFuncionarioFiltering(
            "numeroPisNisPasep.lessThan=" + UPDATED_NUMERO_PIS_NIS_PASEP,
            "numeroPisNisPasep.lessThan=" + DEFAULT_NUMERO_PIS_NIS_PASEP
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByNumeroPisNisPasepIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where numeroPisNisPasep is greater than
        defaultFuncionarioFiltering(
            "numeroPisNisPasep.greaterThan=" + SMALLER_NUMERO_PIS_NIS_PASEP,
            "numeroPisNisPasep.greaterThan=" + DEFAULT_NUMERO_PIS_NIS_PASEP
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByReintegradoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where reintegrado equals to
        defaultFuncionarioFiltering("reintegrado.equals=" + DEFAULT_REINTEGRADO, "reintegrado.equals=" + UPDATED_REINTEGRADO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByReintegradoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where reintegrado in
        defaultFuncionarioFiltering(
            "reintegrado.in=" + DEFAULT_REINTEGRADO + "," + UPDATED_REINTEGRADO,
            "reintegrado.in=" + UPDATED_REINTEGRADO
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByReintegradoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where reintegrado is not null
        defaultFuncionarioFiltering("reintegrado.specified=true", "reintegrado.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByPrimeiroEmpregoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where primeiroEmprego equals to
        defaultFuncionarioFiltering(
            "primeiroEmprego.equals=" + DEFAULT_PRIMEIRO_EMPREGO,
            "primeiroEmprego.equals=" + UPDATED_PRIMEIRO_EMPREGO
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByPrimeiroEmpregoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where primeiroEmprego in
        defaultFuncionarioFiltering(
            "primeiroEmprego.in=" + DEFAULT_PRIMEIRO_EMPREGO + "," + UPDATED_PRIMEIRO_EMPREGO,
            "primeiroEmprego.in=" + UPDATED_PRIMEIRO_EMPREGO
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByPrimeiroEmpregoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where primeiroEmprego is not null
        defaultFuncionarioFiltering("primeiroEmprego.specified=true", "primeiroEmprego.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByMultiploVinculosIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where multiploVinculos equals to
        defaultFuncionarioFiltering(
            "multiploVinculos.equals=" + DEFAULT_MULTIPLO_VINCULOS,
            "multiploVinculos.equals=" + UPDATED_MULTIPLO_VINCULOS
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByMultiploVinculosIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where multiploVinculos in
        defaultFuncionarioFiltering(
            "multiploVinculos.in=" + DEFAULT_MULTIPLO_VINCULOS + "," + UPDATED_MULTIPLO_VINCULOS,
            "multiploVinculos.in=" + UPDATED_MULTIPLO_VINCULOS
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByMultiploVinculosIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where multiploVinculos is not null
        defaultFuncionarioFiltering("multiploVinculos.specified=true", "multiploVinculos.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataOpcaoFgtsIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataOpcaoFgts equals to
        defaultFuncionarioFiltering("dataOpcaoFgts.equals=" + DEFAULT_DATA_OPCAO_FGTS, "dataOpcaoFgts.equals=" + UPDATED_DATA_OPCAO_FGTS);
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataOpcaoFgtsIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataOpcaoFgts in
        defaultFuncionarioFiltering(
            "dataOpcaoFgts.in=" + DEFAULT_DATA_OPCAO_FGTS + "," + UPDATED_DATA_OPCAO_FGTS,
            "dataOpcaoFgts.in=" + UPDATED_DATA_OPCAO_FGTS
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataOpcaoFgtsIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataOpcaoFgts is not null
        defaultFuncionarioFiltering("dataOpcaoFgts.specified=true", "dataOpcaoFgts.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataOpcaoFgtsContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataOpcaoFgts contains
        defaultFuncionarioFiltering(
            "dataOpcaoFgts.contains=" + DEFAULT_DATA_OPCAO_FGTS,
            "dataOpcaoFgts.contains=" + UPDATED_DATA_OPCAO_FGTS
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByDataOpcaoFgtsNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where dataOpcaoFgts does not contain
        defaultFuncionarioFiltering(
            "dataOpcaoFgts.doesNotContain=" + UPDATED_DATA_OPCAO_FGTS,
            "dataOpcaoFgts.doesNotContain=" + DEFAULT_DATA_OPCAO_FGTS
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByFiliacaoSindicalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where filiacaoSindical equals to
        defaultFuncionarioFiltering(
            "filiacaoSindical.equals=" + DEFAULT_FILIACAO_SINDICAL,
            "filiacaoSindical.equals=" + UPDATED_FILIACAO_SINDICAL
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByFiliacaoSindicalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where filiacaoSindical in
        defaultFuncionarioFiltering(
            "filiacaoSindical.in=" + DEFAULT_FILIACAO_SINDICAL + "," + UPDATED_FILIACAO_SINDICAL,
            "filiacaoSindical.in=" + UPDATED_FILIACAO_SINDICAL
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByFiliacaoSindicalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where filiacaoSindical is not null
        defaultFuncionarioFiltering("filiacaoSindical.specified=true", "filiacaoSindical.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByCnpjSindicatoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where cnpjSindicato equals to
        defaultFuncionarioFiltering("cnpjSindicato.equals=" + DEFAULT_CNPJ_SINDICATO, "cnpjSindicato.equals=" + UPDATED_CNPJ_SINDICATO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByCnpjSindicatoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where cnpjSindicato in
        defaultFuncionarioFiltering(
            "cnpjSindicato.in=" + DEFAULT_CNPJ_SINDICATO + "," + UPDATED_CNPJ_SINDICATO,
            "cnpjSindicato.in=" + UPDATED_CNPJ_SINDICATO
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByCnpjSindicatoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where cnpjSindicato is not null
        defaultFuncionarioFiltering("cnpjSindicato.specified=true", "cnpjSindicato.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByCnpjSindicatoContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where cnpjSindicato contains
        defaultFuncionarioFiltering("cnpjSindicato.contains=" + DEFAULT_CNPJ_SINDICATO, "cnpjSindicato.contains=" + UPDATED_CNPJ_SINDICATO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByCnpjSindicatoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where cnpjSindicato does not contain
        defaultFuncionarioFiltering(
            "cnpjSindicato.doesNotContain=" + UPDATED_CNPJ_SINDICATO,
            "cnpjSindicato.doesNotContain=" + DEFAULT_CNPJ_SINDICATO
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByTipoFuncionarioEnumIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where tipoFuncionarioEnum equals to
        defaultFuncionarioFiltering(
            "tipoFuncionarioEnum.equals=" + DEFAULT_TIPO_FUNCIONARIO_ENUM,
            "tipoFuncionarioEnum.equals=" + UPDATED_TIPO_FUNCIONARIO_ENUM
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByTipoFuncionarioEnumIsInShouldWork() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where tipoFuncionarioEnum in
        defaultFuncionarioFiltering(
            "tipoFuncionarioEnum.in=" + DEFAULT_TIPO_FUNCIONARIO_ENUM + "," + UPDATED_TIPO_FUNCIONARIO_ENUM,
            "tipoFuncionarioEnum.in=" + UPDATED_TIPO_FUNCIONARIO_ENUM
        );
    }

    @Test
    @Transactional
    void getAllFuncionariosByTipoFuncionarioEnumIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedFuncionario = funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where tipoFuncionarioEnum is not null
        defaultFuncionarioFiltering("tipoFuncionarioEnum.specified=true", "tipoFuncionarioEnum.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByUsuarioEmpresaIsEqualToSomething() throws Exception {
        // Get already existing entity
        UsuarioEmpresa usuarioEmpresa = funcionario.getUsuarioEmpresa();
        funcionarioRepository.saveAndFlush(funcionario);
        Long usuarioEmpresaId = usuarioEmpresa.getId();
        // Get all the funcionarioList where usuarioEmpresa equals to usuarioEmpresaId
        defaultFuncionarioShouldBeFound("usuarioEmpresaId.equals=" + usuarioEmpresaId);

        // Get all the funcionarioList where usuarioEmpresa equals to (usuarioEmpresaId + 1)
        defaultFuncionarioShouldNotBeFound("usuarioEmpresaId.equals=" + (usuarioEmpresaId + 1));
    }

    @Test
    @Transactional
    void getAllFuncionariosByPessoaIsEqualToSomething() throws Exception {
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            funcionarioRepository.saveAndFlush(funcionario);
            pessoa = PessoaResourceIT.createEntity(em);
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        em.persist(pessoa);
        em.flush();
        funcionario.setPessoa(pessoa);
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

    @Test
    @Transactional
    void getAllFuncionariosByProfissaoIsEqualToSomething() throws Exception {
        Profissao profissao;
        if (TestUtil.findAll(em, Profissao.class).isEmpty()) {
            funcionarioRepository.saveAndFlush(funcionario);
            profissao = ProfissaoResourceIT.createEntity(em);
        } else {
            profissao = TestUtil.findAll(em, Profissao.class).get(0);
        }
        em.persist(profissao);
        em.flush();
        funcionario.setProfissao(profissao);
        funcionarioRepository.saveAndFlush(funcionario);
        Long profissaoId = profissao.getId();
        // Get all the funcionarioList where profissao equals to profissaoId
        defaultFuncionarioShouldBeFound("profissaoId.equals=" + profissaoId);

        // Get all the funcionarioList where profissao equals to (profissaoId + 1)
        defaultFuncionarioShouldNotBeFound("profissaoId.equals=" + (profissaoId + 1));
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
            .andExpect(jsonPath("$.[*].numeroPisNisPasep").value(hasItem(DEFAULT_NUMERO_PIS_NIS_PASEP)))
            .andExpect(jsonPath("$.[*].reintegrado").value(hasItem(DEFAULT_REINTEGRADO.booleanValue())))
            .andExpect(jsonPath("$.[*].primeiroEmprego").value(hasItem(DEFAULT_PRIMEIRO_EMPREGO.booleanValue())))
            .andExpect(jsonPath("$.[*].multiploVinculos").value(hasItem(DEFAULT_MULTIPLO_VINCULOS.booleanValue())))
            .andExpect(jsonPath("$.[*].dataOpcaoFgts").value(hasItem(DEFAULT_DATA_OPCAO_FGTS)))
            .andExpect(jsonPath("$.[*].filiacaoSindical").value(hasItem(DEFAULT_FILIACAO_SINDICAL.booleanValue())))
            .andExpect(jsonPath("$.[*].cnpjSindicato").value(hasItem(DEFAULT_CNPJ_SINDICATO)))
            .andExpect(jsonPath("$.[*].tipoFuncionarioEnum").value(hasItem(DEFAULT_TIPO_FUNCIONARIO_ENUM.toString())));

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
            .numeroPisNisPasep(UPDATED_NUMERO_PIS_NIS_PASEP)
            .reintegrado(UPDATED_REINTEGRADO)
            .primeiroEmprego(UPDATED_PRIMEIRO_EMPREGO)
            .multiploVinculos(UPDATED_MULTIPLO_VINCULOS)
            .dataOpcaoFgts(UPDATED_DATA_OPCAO_FGTS)
            .filiacaoSindical(UPDATED_FILIACAO_SINDICAL)
            .cnpjSindicato(UPDATED_CNPJ_SINDICATO)
            .tipoFuncionarioEnum(UPDATED_TIPO_FUNCIONARIO_ENUM);

        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuncionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFuncionario))
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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionario))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(funcionario))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(funcionario)))
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
            .numeroPisNisPasep(UPDATED_NUMERO_PIS_NIS_PASEP)
            .primeiroEmprego(UPDATED_PRIMEIRO_EMPREGO)
            .filiacaoSindical(UPDATED_FILIACAO_SINDICAL)
            .cnpjSindicato(UPDATED_CNPJ_SINDICATO);

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
            .numeroPisNisPasep(UPDATED_NUMERO_PIS_NIS_PASEP)
            .reintegrado(UPDATED_REINTEGRADO)
            .primeiroEmprego(UPDATED_PRIMEIRO_EMPREGO)
            .multiploVinculos(UPDATED_MULTIPLO_VINCULOS)
            .dataOpcaoFgts(UPDATED_DATA_OPCAO_FGTS)
            .filiacaoSindical(UPDATED_FILIACAO_SINDICAL)
            .cnpjSindicato(UPDATED_CNPJ_SINDICATO)
            .tipoFuncionarioEnum(UPDATED_TIPO_FUNCIONARIO_ENUM);

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

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionario))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(funcionario))
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

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(funcionario)))
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
