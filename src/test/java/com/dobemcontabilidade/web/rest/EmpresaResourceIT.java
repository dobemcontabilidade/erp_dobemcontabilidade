package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.domain.Pessoajuridica;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.repository.EmpresaRepository;
import com.dobemcontabilidade.service.EmpresaService;
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
 * Integration tests for the {@link EmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmpresaResourceIT {

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO_DO_NEGOCIO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO_DO_NEGOCIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_ABERTURA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ABERTURA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_URL_CONTRATO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_URL_CONTRATO_SOCIAL = "BBBBBBBBBB";

    private static final Double DEFAULT_CAPITAL_SOCIAL = 1D;
    private static final Double UPDATED_CAPITAL_SOCIAL = 2D;
    private static final Double SMALLER_CAPITAL_SOCIAL = 1D - 1D;

    private static final String DEFAULT_CNAE = "AAAAAAAAAA";
    private static final String UPDATED_CNAE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Mock
    private EmpresaRepository empresaRepositoryMock;

    @Mock
    private EmpresaService empresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    private Empresa insertedEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .descricaoDoNegocio(DEFAULT_DESCRICAO_DO_NEGOCIO)
            .dataAbertura(DEFAULT_DATA_ABERTURA)
            .urlContratoSocial(DEFAULT_URL_CONTRATO_SOCIAL)
            .capitalSocial(DEFAULT_CAPITAL_SOCIAL)
            .cnae(DEFAULT_CNAE);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        empresa.setPessoaJuridica(pessoajuridica);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        empresa.setTributacao(tributacao);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        empresa.setRamo(ramo);
        // Add required entity
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            enquadramento = EnquadramentoResourceIT.createEntity(em);
            em.persist(enquadramento);
            em.flush();
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        empresa.setEnquadramento(enquadramento);
        return empresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .descricaoDoNegocio(UPDATED_DESCRICAO_DO_NEGOCIO)
            .dataAbertura(UPDATED_DATA_ABERTURA)
            .urlContratoSocial(UPDATED_URL_CONTRATO_SOCIAL)
            .capitalSocial(UPDATED_CAPITAL_SOCIAL)
            .cnae(UPDATED_CNAE);
        // Add required entity
        Pessoajuridica pessoajuridica;
        if (TestUtil.findAll(em, Pessoajuridica.class).isEmpty()) {
            pessoajuridica = PessoajuridicaResourceIT.createUpdatedEntity(em);
            em.persist(pessoajuridica);
            em.flush();
        } else {
            pessoajuridica = TestUtil.findAll(em, Pessoajuridica.class).get(0);
        }
        empresa.setPessoaJuridica(pessoajuridica);
        // Add required entity
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            tributacao = TributacaoResourceIT.createUpdatedEntity(em);
            em.persist(tributacao);
            em.flush();
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        empresa.setTributacao(tributacao);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createUpdatedEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        empresa.setRamo(ramo);
        // Add required entity
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            enquadramento = EnquadramentoResourceIT.createUpdatedEntity(em);
            em.persist(enquadramento);
            em.flush();
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        empresa.setEnquadramento(enquadramento);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmpresa != null) {
            empresaRepository.delete(insertedEmpresa);
            insertedEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Empresa
        var returnedEmpresa = om.readValue(
            restEmpresaMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Empresa.class
        );

        // Validate the Empresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEmpresaUpdatableFieldsEquals(returnedEmpresa, getPersistedEmpresa(returnedEmpresa));

        insertedEmpresa = returnedEmpresa;
    }

    @Test
    @Transactional
    void createEmpresaWithExistingId() throws Exception {
        // Create the Empresa with an existing ID
        empresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresa)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRazaoSocialIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        empresa.setRazaoSocial(null);

        // Create the Empresa, which fails.

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCnaeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        empresa.setCnae(null);

        // Create the Empresa, which fails.

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresa)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmpresas() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].descricaoDoNegocio").value(hasItem(DEFAULT_DESCRICAO_DO_NEGOCIO.toString())))
            .andExpect(jsonPath("$.[*].dataAbertura").value(hasItem(DEFAULT_DATA_ABERTURA.toString())))
            .andExpect(jsonPath("$.[*].urlContratoSocial").value(hasItem(DEFAULT_URL_CONTRATO_SOCIAL)))
            .andExpect(jsonPath("$.[*].capitalSocial").value(hasItem(DEFAULT_CAPITAL_SOCIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].cnae").value(hasItem(DEFAULT_CNAE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(empresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(empresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(empresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(empresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmpresa() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.descricaoDoNegocio").value(DEFAULT_DESCRICAO_DO_NEGOCIO.toString()))
            .andExpect(jsonPath("$.dataAbertura").value(DEFAULT_DATA_ABERTURA.toString()))
            .andExpect(jsonPath("$.urlContratoSocial").value(DEFAULT_URL_CONTRATO_SOCIAL))
            .andExpect(jsonPath("$.capitalSocial").value(DEFAULT_CAPITAL_SOCIAL.doubleValue()))
            .andExpect(jsonPath("$.cnae").value(DEFAULT_CNAE));
    }

    @Test
    @Transactional
    void getEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        Long id = empresa.getId();

        defaultEmpresaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEmpresaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEmpresaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmpresasByRazaoSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razaoSocial equals to
        defaultEmpresaFiltering("razaoSocial.equals=" + DEFAULT_RAZAO_SOCIAL, "razaoSocial.equals=" + UPDATED_RAZAO_SOCIAL);
    }

    @Test
    @Transactional
    void getAllEmpresasByRazaoSocialIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razaoSocial in
        defaultEmpresaFiltering(
            "razaoSocial.in=" + DEFAULT_RAZAO_SOCIAL + "," + UPDATED_RAZAO_SOCIAL,
            "razaoSocial.in=" + UPDATED_RAZAO_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllEmpresasByRazaoSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razaoSocial is not null
        defaultEmpresaFiltering("razaoSocial.specified=true", "razaoSocial.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpresasByRazaoSocialContainsSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razaoSocial contains
        defaultEmpresaFiltering("razaoSocial.contains=" + DEFAULT_RAZAO_SOCIAL, "razaoSocial.contains=" + UPDATED_RAZAO_SOCIAL);
    }

    @Test
    @Transactional
    void getAllEmpresasByRazaoSocialNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where razaoSocial does not contain
        defaultEmpresaFiltering("razaoSocial.doesNotContain=" + UPDATED_RAZAO_SOCIAL, "razaoSocial.doesNotContain=" + DEFAULT_RAZAO_SOCIAL);
    }

    @Test
    @Transactional
    void getAllEmpresasByDataAberturaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where dataAbertura equals to
        defaultEmpresaFiltering("dataAbertura.equals=" + DEFAULT_DATA_ABERTURA, "dataAbertura.equals=" + UPDATED_DATA_ABERTURA);
    }

    @Test
    @Transactional
    void getAllEmpresasByDataAberturaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where dataAbertura in
        defaultEmpresaFiltering(
            "dataAbertura.in=" + DEFAULT_DATA_ABERTURA + "," + UPDATED_DATA_ABERTURA,
            "dataAbertura.in=" + UPDATED_DATA_ABERTURA
        );
    }

    @Test
    @Transactional
    void getAllEmpresasByDataAberturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where dataAbertura is not null
        defaultEmpresaFiltering("dataAbertura.specified=true", "dataAbertura.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpresasByUrlContratoSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where urlContratoSocial equals to
        defaultEmpresaFiltering(
            "urlContratoSocial.equals=" + DEFAULT_URL_CONTRATO_SOCIAL,
            "urlContratoSocial.equals=" + UPDATED_URL_CONTRATO_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllEmpresasByUrlContratoSocialIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where urlContratoSocial in
        defaultEmpresaFiltering(
            "urlContratoSocial.in=" + DEFAULT_URL_CONTRATO_SOCIAL + "," + UPDATED_URL_CONTRATO_SOCIAL,
            "urlContratoSocial.in=" + UPDATED_URL_CONTRATO_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllEmpresasByUrlContratoSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where urlContratoSocial is not null
        defaultEmpresaFiltering("urlContratoSocial.specified=true", "urlContratoSocial.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpresasByUrlContratoSocialContainsSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where urlContratoSocial contains
        defaultEmpresaFiltering(
            "urlContratoSocial.contains=" + DEFAULT_URL_CONTRATO_SOCIAL,
            "urlContratoSocial.contains=" + UPDATED_URL_CONTRATO_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllEmpresasByUrlContratoSocialNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where urlContratoSocial does not contain
        defaultEmpresaFiltering(
            "urlContratoSocial.doesNotContain=" + UPDATED_URL_CONTRATO_SOCIAL,
            "urlContratoSocial.doesNotContain=" + DEFAULT_URL_CONTRATO_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllEmpresasByCapitalSocialIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial equals to
        defaultEmpresaFiltering("capitalSocial.equals=" + DEFAULT_CAPITAL_SOCIAL, "capitalSocial.equals=" + UPDATED_CAPITAL_SOCIAL);
    }

    @Test
    @Transactional
    void getAllEmpresasByCapitalSocialIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial in
        defaultEmpresaFiltering(
            "capitalSocial.in=" + DEFAULT_CAPITAL_SOCIAL + "," + UPDATED_CAPITAL_SOCIAL,
            "capitalSocial.in=" + UPDATED_CAPITAL_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllEmpresasByCapitalSocialIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial is not null
        defaultEmpresaFiltering("capitalSocial.specified=true", "capitalSocial.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpresasByCapitalSocialIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial is greater than or equal to
        defaultEmpresaFiltering(
            "capitalSocial.greaterThanOrEqual=" + DEFAULT_CAPITAL_SOCIAL,
            "capitalSocial.greaterThanOrEqual=" + UPDATED_CAPITAL_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllEmpresasByCapitalSocialIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial is less than or equal to
        defaultEmpresaFiltering(
            "capitalSocial.lessThanOrEqual=" + DEFAULT_CAPITAL_SOCIAL,
            "capitalSocial.lessThanOrEqual=" + SMALLER_CAPITAL_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllEmpresasByCapitalSocialIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial is less than
        defaultEmpresaFiltering("capitalSocial.lessThan=" + UPDATED_CAPITAL_SOCIAL, "capitalSocial.lessThan=" + DEFAULT_CAPITAL_SOCIAL);
    }

    @Test
    @Transactional
    void getAllEmpresasByCapitalSocialIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where capitalSocial is greater than
        defaultEmpresaFiltering(
            "capitalSocial.greaterThan=" + SMALLER_CAPITAL_SOCIAL,
            "capitalSocial.greaterThan=" + DEFAULT_CAPITAL_SOCIAL
        );
    }

    @Test
    @Transactional
    void getAllEmpresasByCnaeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where cnae equals to
        defaultEmpresaFiltering("cnae.equals=" + DEFAULT_CNAE, "cnae.equals=" + UPDATED_CNAE);
    }

    @Test
    @Transactional
    void getAllEmpresasByCnaeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where cnae in
        defaultEmpresaFiltering("cnae.in=" + DEFAULT_CNAE + "," + UPDATED_CNAE, "cnae.in=" + UPDATED_CNAE);
    }

    @Test
    @Transactional
    void getAllEmpresasByCnaeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where cnae is not null
        defaultEmpresaFiltering("cnae.specified=true", "cnae.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpresasByCnaeContainsSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where cnae contains
        defaultEmpresaFiltering("cnae.contains=" + DEFAULT_CNAE, "cnae.contains=" + UPDATED_CNAE);
    }

    @Test
    @Transactional
    void getAllEmpresasByCnaeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where cnae does not contain
        defaultEmpresaFiltering("cnae.doesNotContain=" + UPDATED_CNAE, "cnae.doesNotContain=" + DEFAULT_CNAE);
    }

    @Test
    @Transactional
    void getAllEmpresasByPessoaJuridicaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Pessoajuridica pessoaJuridica = empresa.getPessoaJuridica();
        empresaRepository.saveAndFlush(empresa);
        Long pessoaJuridicaId = pessoaJuridica.getId();
        // Get all the empresaList where pessoaJuridica equals to pessoaJuridicaId
        defaultEmpresaShouldBeFound("pessoaJuridicaId.equals=" + pessoaJuridicaId);

        // Get all the empresaList where pessoaJuridica equals to (pessoaJuridicaId + 1)
        defaultEmpresaShouldNotBeFound("pessoaJuridicaId.equals=" + (pessoaJuridicaId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByTributacaoIsEqualToSomething() throws Exception {
        Tributacao tributacao;
        if (TestUtil.findAll(em, Tributacao.class).isEmpty()) {
            empresaRepository.saveAndFlush(empresa);
            tributacao = TributacaoResourceIT.createEntity(em);
        } else {
            tributacao = TestUtil.findAll(em, Tributacao.class).get(0);
        }
        em.persist(tributacao);
        em.flush();
        empresa.setTributacao(tributacao);
        empresaRepository.saveAndFlush(empresa);
        Long tributacaoId = tributacao.getId();
        // Get all the empresaList where tributacao equals to tributacaoId
        defaultEmpresaShouldBeFound("tributacaoId.equals=" + tributacaoId);

        // Get all the empresaList where tributacao equals to (tributacaoId + 1)
        defaultEmpresaShouldNotBeFound("tributacaoId.equals=" + (tributacaoId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByRamoIsEqualToSomething() throws Exception {
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            empresaRepository.saveAndFlush(empresa);
            ramo = RamoResourceIT.createEntity(em);
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        em.persist(ramo);
        em.flush();
        empresa.setRamo(ramo);
        empresaRepository.saveAndFlush(empresa);
        Long ramoId = ramo.getId();
        // Get all the empresaList where ramo equals to ramoId
        defaultEmpresaShouldBeFound("ramoId.equals=" + ramoId);

        // Get all the empresaList where ramo equals to (ramoId + 1)
        defaultEmpresaShouldNotBeFound("ramoId.equals=" + (ramoId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByEnquadramentoIsEqualToSomething() throws Exception {
        Enquadramento enquadramento;
        if (TestUtil.findAll(em, Enquadramento.class).isEmpty()) {
            empresaRepository.saveAndFlush(empresa);
            enquadramento = EnquadramentoResourceIT.createEntity(em);
        } else {
            enquadramento = TestUtil.findAll(em, Enquadramento.class).get(0);
        }
        em.persist(enquadramento);
        em.flush();
        empresa.setEnquadramento(enquadramento);
        empresaRepository.saveAndFlush(empresa);
        Long enquadramentoId = enquadramento.getId();
        // Get all the empresaList where enquadramento equals to enquadramentoId
        defaultEmpresaShouldBeFound("enquadramentoId.equals=" + enquadramentoId);

        // Get all the empresaList where enquadramento equals to (enquadramentoId + 1)
        defaultEmpresaShouldNotBeFound("enquadramentoId.equals=" + (enquadramentoId + 1));
    }

    private void defaultEmpresaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEmpresaShouldBeFound(shouldBeFound);
        defaultEmpresaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpresaShouldBeFound(String filter) throws Exception {
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].descricaoDoNegocio").value(hasItem(DEFAULT_DESCRICAO_DO_NEGOCIO.toString())))
            .andExpect(jsonPath("$.[*].dataAbertura").value(hasItem(DEFAULT_DATA_ABERTURA.toString())))
            .andExpect(jsonPath("$.[*].urlContratoSocial").value(hasItem(DEFAULT_URL_CONTRATO_SOCIAL)))
            .andExpect(jsonPath("$.[*].capitalSocial").value(hasItem(DEFAULT_CAPITAL_SOCIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].cnae").value(hasItem(DEFAULT_CNAE)));

        // Check, that the count call also returns 1
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpresaShouldNotBeFound(String filter) throws Exception {
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmpresa() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .descricaoDoNegocio(UPDATED_DESCRICAO_DO_NEGOCIO)
            .dataAbertura(UPDATED_DATA_ABERTURA)
            .urlContratoSocial(UPDATED_URL_CONTRATO_SOCIAL)
            .capitalSocial(UPDATED_CAPITAL_SOCIAL)
            .cnae(UPDATED_CNAE);

        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmpresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmpresaToMatchAllProperties(updatedEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empresa.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresa using partial update
        Empresa partialUpdatedEmpresa = new Empresa();
        partialUpdatedEmpresa.setId(empresa.getId());

        partialUpdatedEmpresa.razaoSocial(UPDATED_RAZAO_SOCIAL).capitalSocial(UPDATED_CAPITAL_SOCIAL).cnae(UPDATED_CNAE);

        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpresaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEmpresa, empresa), getPersistedEmpresa(empresa));
    }

    @Test
    @Transactional
    void fullUpdateEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empresa using partial update
        Empresa partialUpdatedEmpresa = new Empresa();
        partialUpdatedEmpresa.setId(empresa.getId());

        partialUpdatedEmpresa
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .descricaoDoNegocio(UPDATED_DESCRICAO_DO_NEGOCIO)
            .dataAbertura(UPDATED_DATA_ABERTURA)
            .urlContratoSocial(UPDATED_URL_CONTRATO_SOCIAL)
            .capitalSocial(UPDATED_CAPITAL_SOCIAL)
            .cnae(UPDATED_CNAE);

        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpresaUpdatableFieldsEquals(partialUpdatedEmpresa, getPersistedEmpresa(partialUpdatedEmpresa));
    }

    @Test
    @Transactional
    void patchNonExistingEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empresa.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(empresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpresa() throws Exception {
        // Initialize the database
        insertedEmpresa = empresaRepository.saveAndFlush(empresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the empresa
        restEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, empresa.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return empresaRepository.count();
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

    protected Empresa getPersistedEmpresa(Empresa empresa) {
        return empresaRepository.findById(empresa.getId()).orElseThrow();
    }

    protected void assertPersistedEmpresaToMatchAllProperties(Empresa expectedEmpresa) {
        assertEmpresaAllPropertiesEquals(expectedEmpresa, getPersistedEmpresa(expectedEmpresa));
    }

    protected void assertPersistedEmpresaToMatchUpdatableProperties(Empresa expectedEmpresa) {
        assertEmpresaAllUpdatablePropertiesEquals(expectedEmpresa, getPersistedEmpresa(expectedEmpresa));
    }
}
