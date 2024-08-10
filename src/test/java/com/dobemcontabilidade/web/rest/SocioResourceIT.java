package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.SocioAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.domain.Socio;
import com.dobemcontabilidade.domain.enumeration.FuncaoSocioEnum;
import com.dobemcontabilidade.repository.SocioRepository;
import com.dobemcontabilidade.service.SocioService;
import com.dobemcontabilidade.service.dto.SocioDTO;
import com.dobemcontabilidade.service.mapper.SocioMapper;
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
 * Integration tests for the {@link SocioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SocioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROLABORE = false;
    private static final Boolean UPDATED_PROLABORE = true;

    private static final Double DEFAULT_PERCENTUAL_SOCIEDADE = 1D;
    private static final Double UPDATED_PERCENTUAL_SOCIEDADE = 2D;
    private static final Double SMALLER_PERCENTUAL_SOCIEDADE = 1D - 1D;

    private static final Boolean DEFAULT_ADMINSTRADOR = false;
    private static final Boolean UPDATED_ADMINSTRADOR = true;

    private static final Boolean DEFAULT_DISTRIBUICAO_LUCRO = false;
    private static final Boolean UPDATED_DISTRIBUICAO_LUCRO = true;

    private static final Boolean DEFAULT_RESPONSAVEL_RECEITA = false;
    private static final Boolean UPDATED_RESPONSAVEL_RECEITA = true;

    private static final Double DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO = 1D;
    private static final Double UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO = 2D;
    private static final Double SMALLER_PERCENTUAL_DISTRIBUICAO_LUCRO = 1D - 1D;

    private static final FuncaoSocioEnum DEFAULT_FUNCAO_SOCIO = FuncaoSocioEnum.SOCIO;
    private static final FuncaoSocioEnum UPDATED_FUNCAO_SOCIO = FuncaoSocioEnum.SOCIO_ADMINISTRADOR;

    private static final String ENTITY_API_URL = "/api/socios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SocioRepository socioRepository;

    @Mock
    private SocioRepository socioRepositoryMock;

    @Autowired
    private SocioMapper socioMapper;

    @Mock
    private SocioService socioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocioMockMvc;

    private Socio socio;

    private Socio insertedSocio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Socio createEntity(EntityManager em) {
        Socio socio = new Socio()
            .nome(DEFAULT_NOME)
            .prolabore(DEFAULT_PROLABORE)
            .percentualSociedade(DEFAULT_PERCENTUAL_SOCIEDADE)
            .adminstrador(DEFAULT_ADMINSTRADOR)
            .distribuicaoLucro(DEFAULT_DISTRIBUICAO_LUCRO)
            .responsavelReceita(DEFAULT_RESPONSAVEL_RECEITA)
            .percentualDistribuicaoLucro(DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO)
            .funcaoSocio(DEFAULT_FUNCAO_SOCIO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        socio.setPessoa(pessoa);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        socio.setEmpresa(empresa);
        return socio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Socio createUpdatedEntity(EntityManager em) {
        Socio socio = new Socio()
            .nome(UPDATED_NOME)
            .prolabore(UPDATED_PROLABORE)
            .percentualSociedade(UPDATED_PERCENTUAL_SOCIEDADE)
            .adminstrador(UPDATED_ADMINSTRADOR)
            .distribuicaoLucro(UPDATED_DISTRIBUICAO_LUCRO)
            .responsavelReceita(UPDATED_RESPONSAVEL_RECEITA)
            .percentualDistribuicaoLucro(UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO)
            .funcaoSocio(UPDATED_FUNCAO_SOCIO);
        // Add required entity
        Pessoa pessoa;
        if (TestUtil.findAll(em, Pessoa.class).isEmpty()) {
            pessoa = PessoaResourceIT.createUpdatedEntity(em);
            em.persist(pessoa);
            em.flush();
        } else {
            pessoa = TestUtil.findAll(em, Pessoa.class).get(0);
        }
        socio.setPessoa(pessoa);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        socio.setEmpresa(empresa);
        return socio;
    }

    @BeforeEach
    public void initTest() {
        socio = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSocio != null) {
            socioRepository.delete(insertedSocio);
            insertedSocio = null;
        }
    }

    @Test
    @Transactional
    void createSocio() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);
        var returnedSocioDTO = om.readValue(
            restSocioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SocioDTO.class
        );

        // Validate the Socio in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSocio = socioMapper.toEntity(returnedSocioDTO);
        assertSocioUpdatableFieldsEquals(returnedSocio, getPersistedSocio(returnedSocio));

        insertedSocio = returnedSocio;
    }

    @Test
    @Transactional
    void createSocioWithExistingId() throws Exception {
        // Create the Socio with an existing ID
        socio.setId(1L);
        SocioDTO socioDTO = socioMapper.toDto(socio);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAdminstradorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        socio.setAdminstrador(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFuncaoSocioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        socio.setFuncaoSocio(null);

        // Create the Socio, which fails.
        SocioDTO socioDTO = socioMapper.toDto(socio);

        restSocioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocios() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList
        restSocioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].prolabore").value(hasItem(DEFAULT_PROLABORE.booleanValue())))
            .andExpect(jsonPath("$.[*].percentualSociedade").value(hasItem(DEFAULT_PERCENTUAL_SOCIEDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].adminstrador").value(hasItem(DEFAULT_ADMINSTRADOR.booleanValue())))
            .andExpect(jsonPath("$.[*].distribuicaoLucro").value(hasItem(DEFAULT_DISTRIBUICAO_LUCRO.booleanValue())))
            .andExpect(jsonPath("$.[*].responsavelReceita").value(hasItem(DEFAULT_RESPONSAVEL_RECEITA.booleanValue())))
            .andExpect(jsonPath("$.[*].percentualDistribuicaoLucro").value(hasItem(DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO.doubleValue())))
            .andExpect(jsonPath("$.[*].funcaoSocio").value(hasItem(DEFAULT_FUNCAO_SOCIO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSociosWithEagerRelationshipsIsEnabled() throws Exception {
        when(socioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSocioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(socioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSociosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(socioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSocioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(socioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSocio() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get the socio
        restSocioMockMvc
            .perform(get(ENTITY_API_URL_ID, socio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socio.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.prolabore").value(DEFAULT_PROLABORE.booleanValue()))
            .andExpect(jsonPath("$.percentualSociedade").value(DEFAULT_PERCENTUAL_SOCIEDADE.doubleValue()))
            .andExpect(jsonPath("$.adminstrador").value(DEFAULT_ADMINSTRADOR.booleanValue()))
            .andExpect(jsonPath("$.distribuicaoLucro").value(DEFAULT_DISTRIBUICAO_LUCRO.booleanValue()))
            .andExpect(jsonPath("$.responsavelReceita").value(DEFAULT_RESPONSAVEL_RECEITA.booleanValue()))
            .andExpect(jsonPath("$.percentualDistribuicaoLucro").value(DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO.doubleValue()))
            .andExpect(jsonPath("$.funcaoSocio").value(DEFAULT_FUNCAO_SOCIO.toString()));
    }

    @Test
    @Transactional
    void getSociosByIdFiltering() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        Long id = socio.getId();

        defaultSocioFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSocioFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSocioFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSociosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where nome equals to
        defaultSocioFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllSociosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where nome in
        defaultSocioFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllSociosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where nome is not null
        defaultSocioFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllSociosByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where nome contains
        defaultSocioFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllSociosByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where nome does not contain
        defaultSocioFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllSociosByProlaboreIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where prolabore equals to
        defaultSocioFiltering("prolabore.equals=" + DEFAULT_PROLABORE, "prolabore.equals=" + UPDATED_PROLABORE);
    }

    @Test
    @Transactional
    void getAllSociosByProlaboreIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where prolabore in
        defaultSocioFiltering("prolabore.in=" + DEFAULT_PROLABORE + "," + UPDATED_PROLABORE, "prolabore.in=" + UPDATED_PROLABORE);
    }

    @Test
    @Transactional
    void getAllSociosByProlaboreIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where prolabore is not null
        defaultSocioFiltering("prolabore.specified=true", "prolabore.specified=false");
    }

    @Test
    @Transactional
    void getAllSociosByPercentualSociedadeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualSociedade equals to
        defaultSocioFiltering(
            "percentualSociedade.equals=" + DEFAULT_PERCENTUAL_SOCIEDADE,
            "percentualSociedade.equals=" + UPDATED_PERCENTUAL_SOCIEDADE
        );
    }

    @Test
    @Transactional
    void getAllSociosByPercentualSociedadeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualSociedade in
        defaultSocioFiltering(
            "percentualSociedade.in=" + DEFAULT_PERCENTUAL_SOCIEDADE + "," + UPDATED_PERCENTUAL_SOCIEDADE,
            "percentualSociedade.in=" + UPDATED_PERCENTUAL_SOCIEDADE
        );
    }

    @Test
    @Transactional
    void getAllSociosByPercentualSociedadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualSociedade is not null
        defaultSocioFiltering("percentualSociedade.specified=true", "percentualSociedade.specified=false");
    }

    @Test
    @Transactional
    void getAllSociosByPercentualSociedadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualSociedade is greater than or equal to
        defaultSocioFiltering(
            "percentualSociedade.greaterThanOrEqual=" + DEFAULT_PERCENTUAL_SOCIEDADE,
            "percentualSociedade.greaterThanOrEqual=" + UPDATED_PERCENTUAL_SOCIEDADE
        );
    }

    @Test
    @Transactional
    void getAllSociosByPercentualSociedadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualSociedade is less than or equal to
        defaultSocioFiltering(
            "percentualSociedade.lessThanOrEqual=" + DEFAULT_PERCENTUAL_SOCIEDADE,
            "percentualSociedade.lessThanOrEqual=" + SMALLER_PERCENTUAL_SOCIEDADE
        );
    }

    @Test
    @Transactional
    void getAllSociosByPercentualSociedadeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualSociedade is less than
        defaultSocioFiltering(
            "percentualSociedade.lessThan=" + UPDATED_PERCENTUAL_SOCIEDADE,
            "percentualSociedade.lessThan=" + DEFAULT_PERCENTUAL_SOCIEDADE
        );
    }

    @Test
    @Transactional
    void getAllSociosByPercentualSociedadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualSociedade is greater than
        defaultSocioFiltering(
            "percentualSociedade.greaterThan=" + SMALLER_PERCENTUAL_SOCIEDADE,
            "percentualSociedade.greaterThan=" + DEFAULT_PERCENTUAL_SOCIEDADE
        );
    }

    @Test
    @Transactional
    void getAllSociosByAdminstradorIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where adminstrador equals to
        defaultSocioFiltering("adminstrador.equals=" + DEFAULT_ADMINSTRADOR, "adminstrador.equals=" + UPDATED_ADMINSTRADOR);
    }

    @Test
    @Transactional
    void getAllSociosByAdminstradorIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where adminstrador in
        defaultSocioFiltering(
            "adminstrador.in=" + DEFAULT_ADMINSTRADOR + "," + UPDATED_ADMINSTRADOR,
            "adminstrador.in=" + UPDATED_ADMINSTRADOR
        );
    }

    @Test
    @Transactional
    void getAllSociosByAdminstradorIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where adminstrador is not null
        defaultSocioFiltering("adminstrador.specified=true", "adminstrador.specified=false");
    }

    @Test
    @Transactional
    void getAllSociosByDistribuicaoLucroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where distribuicaoLucro equals to
        defaultSocioFiltering(
            "distribuicaoLucro.equals=" + DEFAULT_DISTRIBUICAO_LUCRO,
            "distribuicaoLucro.equals=" + UPDATED_DISTRIBUICAO_LUCRO
        );
    }

    @Test
    @Transactional
    void getAllSociosByDistribuicaoLucroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where distribuicaoLucro in
        defaultSocioFiltering(
            "distribuicaoLucro.in=" + DEFAULT_DISTRIBUICAO_LUCRO + "," + UPDATED_DISTRIBUICAO_LUCRO,
            "distribuicaoLucro.in=" + UPDATED_DISTRIBUICAO_LUCRO
        );
    }

    @Test
    @Transactional
    void getAllSociosByDistribuicaoLucroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where distribuicaoLucro is not null
        defaultSocioFiltering("distribuicaoLucro.specified=true", "distribuicaoLucro.specified=false");
    }

    @Test
    @Transactional
    void getAllSociosByResponsavelReceitaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where responsavelReceita equals to
        defaultSocioFiltering(
            "responsavelReceita.equals=" + DEFAULT_RESPONSAVEL_RECEITA,
            "responsavelReceita.equals=" + UPDATED_RESPONSAVEL_RECEITA
        );
    }

    @Test
    @Transactional
    void getAllSociosByResponsavelReceitaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where responsavelReceita in
        defaultSocioFiltering(
            "responsavelReceita.in=" + DEFAULT_RESPONSAVEL_RECEITA + "," + UPDATED_RESPONSAVEL_RECEITA,
            "responsavelReceita.in=" + UPDATED_RESPONSAVEL_RECEITA
        );
    }

    @Test
    @Transactional
    void getAllSociosByResponsavelReceitaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where responsavelReceita is not null
        defaultSocioFiltering("responsavelReceita.specified=true", "responsavelReceita.specified=false");
    }

    @Test
    @Transactional
    void getAllSociosByPercentualDistribuicaoLucroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualDistribuicaoLucro equals to
        defaultSocioFiltering(
            "percentualDistribuicaoLucro.equals=" + DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO,
            "percentualDistribuicaoLucro.equals=" + UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO
        );
    }

    @Test
    @Transactional
    void getAllSociosByPercentualDistribuicaoLucroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualDistribuicaoLucro in
        defaultSocioFiltering(
            "percentualDistribuicaoLucro.in=" + DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO + "," + UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO,
            "percentualDistribuicaoLucro.in=" + UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO
        );
    }

    @Test
    @Transactional
    void getAllSociosByPercentualDistribuicaoLucroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualDistribuicaoLucro is not null
        defaultSocioFiltering("percentualDistribuicaoLucro.specified=true", "percentualDistribuicaoLucro.specified=false");
    }

    @Test
    @Transactional
    void getAllSociosByPercentualDistribuicaoLucroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualDistribuicaoLucro is greater than or equal to
        defaultSocioFiltering(
            "percentualDistribuicaoLucro.greaterThanOrEqual=" + DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO,
            "percentualDistribuicaoLucro.greaterThanOrEqual=" + UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO
        );
    }

    @Test
    @Transactional
    void getAllSociosByPercentualDistribuicaoLucroIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualDistribuicaoLucro is less than or equal to
        defaultSocioFiltering(
            "percentualDistribuicaoLucro.lessThanOrEqual=" + DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO,
            "percentualDistribuicaoLucro.lessThanOrEqual=" + SMALLER_PERCENTUAL_DISTRIBUICAO_LUCRO
        );
    }

    @Test
    @Transactional
    void getAllSociosByPercentualDistribuicaoLucroIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualDistribuicaoLucro is less than
        defaultSocioFiltering(
            "percentualDistribuicaoLucro.lessThan=" + UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO,
            "percentualDistribuicaoLucro.lessThan=" + DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO
        );
    }

    @Test
    @Transactional
    void getAllSociosByPercentualDistribuicaoLucroIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where percentualDistribuicaoLucro is greater than
        defaultSocioFiltering(
            "percentualDistribuicaoLucro.greaterThan=" + SMALLER_PERCENTUAL_DISTRIBUICAO_LUCRO,
            "percentualDistribuicaoLucro.greaterThan=" + DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO
        );
    }

    @Test
    @Transactional
    void getAllSociosByFuncaoSocioIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where funcaoSocio equals to
        defaultSocioFiltering("funcaoSocio.equals=" + DEFAULT_FUNCAO_SOCIO, "funcaoSocio.equals=" + UPDATED_FUNCAO_SOCIO);
    }

    @Test
    @Transactional
    void getAllSociosByFuncaoSocioIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where funcaoSocio in
        defaultSocioFiltering(
            "funcaoSocio.in=" + DEFAULT_FUNCAO_SOCIO + "," + UPDATED_FUNCAO_SOCIO,
            "funcaoSocio.in=" + UPDATED_FUNCAO_SOCIO
        );
    }

    @Test
    @Transactional
    void getAllSociosByFuncaoSocioIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        // Get all the socioList where funcaoSocio is not null
        defaultSocioFiltering("funcaoSocio.specified=true", "funcaoSocio.specified=false");
    }

    @Test
    @Transactional
    void getAllSociosByPessoaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Pessoa pessoa = socio.getPessoa();
        socioRepository.saveAndFlush(socio);
        Long pessoaId = pessoa.getId();
        // Get all the socioList where pessoa equals to pessoaId
        defaultSocioShouldBeFound("pessoaId.equals=" + pessoaId);

        // Get all the socioList where pessoa equals to (pessoaId + 1)
        defaultSocioShouldNotBeFound("pessoaId.equals=" + (pessoaId + 1));
    }

    @Test
    @Transactional
    void getAllSociosByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            socioRepository.saveAndFlush(socio);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        socio.setEmpresa(empresa);
        socioRepository.saveAndFlush(socio);
        Long empresaId = empresa.getId();
        // Get all the socioList where empresa equals to empresaId
        defaultSocioShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the socioList where empresa equals to (empresaId + 1)
        defaultSocioShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    private void defaultSocioFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSocioShouldBeFound(shouldBeFound);
        defaultSocioShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocioShouldBeFound(String filter) throws Exception {
        restSocioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].prolabore").value(hasItem(DEFAULT_PROLABORE.booleanValue())))
            .andExpect(jsonPath("$.[*].percentualSociedade").value(hasItem(DEFAULT_PERCENTUAL_SOCIEDADE.doubleValue())))
            .andExpect(jsonPath("$.[*].adminstrador").value(hasItem(DEFAULT_ADMINSTRADOR.booleanValue())))
            .andExpect(jsonPath("$.[*].distribuicaoLucro").value(hasItem(DEFAULT_DISTRIBUICAO_LUCRO.booleanValue())))
            .andExpect(jsonPath("$.[*].responsavelReceita").value(hasItem(DEFAULT_RESPONSAVEL_RECEITA.booleanValue())))
            .andExpect(jsonPath("$.[*].percentualDistribuicaoLucro").value(hasItem(DEFAULT_PERCENTUAL_DISTRIBUICAO_LUCRO.doubleValue())))
            .andExpect(jsonPath("$.[*].funcaoSocio").value(hasItem(DEFAULT_FUNCAO_SOCIO.toString())));

        // Check, that the count call also returns 1
        restSocioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocioShouldNotBeFound(String filter) throws Exception {
        restSocioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocio() throws Exception {
        // Get the socio
        restSocioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSocio() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the socio
        Socio updatedSocio = socioRepository.findById(socio.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSocio are not directly saved in db
        em.detach(updatedSocio);
        updatedSocio
            .nome(UPDATED_NOME)
            .prolabore(UPDATED_PROLABORE)
            .percentualSociedade(UPDATED_PERCENTUAL_SOCIEDADE)
            .adminstrador(UPDATED_ADMINSTRADOR)
            .distribuicaoLucro(UPDATED_DISTRIBUICAO_LUCRO)
            .responsavelReceita(UPDATED_RESPONSAVEL_RECEITA)
            .percentualDistribuicaoLucro(UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO)
            .funcaoSocio(UPDATED_FUNCAO_SOCIO);
        SocioDTO socioDTO = socioMapper.toDto(updatedSocio);

        restSocioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socioDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSocioToMatchAllProperties(updatedSocio);
    }

    @Test
    @Transactional
    void putNonExistingSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socioDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(socioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(socioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocioWithPatch() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the socio using partial update
        Socio partialUpdatedSocio = new Socio();
        partialUpdatedSocio.setId(socio.getId());

        partialUpdatedSocio
            .prolabore(UPDATED_PROLABORE)
            .percentualSociedade(UPDATED_PERCENTUAL_SOCIEDADE)
            .adminstrador(UPDATED_ADMINSTRADOR)
            .responsavelReceita(UPDATED_RESPONSAVEL_RECEITA)
            .percentualDistribuicaoLucro(UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO)
            .funcaoSocio(UPDATED_FUNCAO_SOCIO);

        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSocio))
            )
            .andExpect(status().isOk());

        // Validate the Socio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSocioUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSocio, socio), getPersistedSocio(socio));
    }

    @Test
    @Transactional
    void fullUpdateSocioWithPatch() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the socio using partial update
        Socio partialUpdatedSocio = new Socio();
        partialUpdatedSocio.setId(socio.getId());

        partialUpdatedSocio
            .nome(UPDATED_NOME)
            .prolabore(UPDATED_PROLABORE)
            .percentualSociedade(UPDATED_PERCENTUAL_SOCIEDADE)
            .adminstrador(UPDATED_ADMINSTRADOR)
            .distribuicaoLucro(UPDATED_DISTRIBUICAO_LUCRO)
            .responsavelReceita(UPDATED_RESPONSAVEL_RECEITA)
            .percentualDistribuicaoLucro(UPDATED_PERCENTUAL_DISTRIBUICAO_LUCRO)
            .funcaoSocio(UPDATED_FUNCAO_SOCIO);

        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSocio))
            )
            .andExpect(status().isOk());

        // Validate the Socio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSocioUpdatableFieldsEquals(partialUpdatedSocio, getPersistedSocio(partialUpdatedSocio));
    }

    @Test
    @Transactional
    void patchNonExistingSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, socioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(socioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(socioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        socio.setId(longCount.incrementAndGet());

        // Create the Socio
        SocioDTO socioDTO = socioMapper.toDto(socio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(socioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Socio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocio() throws Exception {
        // Initialize the database
        insertedSocio = socioRepository.saveAndFlush(socio);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the socio
        restSocioMockMvc
            .perform(delete(ENTITY_API_URL_ID, socio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return socioRepository.count();
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

    protected Socio getPersistedSocio(Socio socio) {
        return socioRepository.findById(socio.getId()).orElseThrow();
    }

    protected void assertPersistedSocioToMatchAllProperties(Socio expectedSocio) {
        assertSocioAllPropertiesEquals(expectedSocio, getPersistedSocio(expectedSocio));
    }

    protected void assertPersistedSocioToMatchUpdatableProperties(Socio expectedSocio) {
        assertSocioAllUpdatablePropertiesEquals(expectedSocio, getPersistedSocio(expectedSocio));
    }
}
