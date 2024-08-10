package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.EnderecoEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.EnderecoEmpresa;
import com.dobemcontabilidade.repository.EnderecoEmpresaRepository;
import com.dobemcontabilidade.service.EnderecoEmpresaService;
import com.dobemcontabilidade.service.dto.EnderecoEmpresaDTO;
import com.dobemcontabilidade.service.mapper.EnderecoEmpresaMapper;
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
 * Integration tests for the {@link EnderecoEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EnderecoEmpresaResourceIT {

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINCIPAL = false;
    private static final Boolean UPDATED_PRINCIPAL = true;

    private static final Boolean DEFAULT_FILIAL = false;
    private static final Boolean UPDATED_FILIAL = true;

    private static final Boolean DEFAULT_ENDERECO_FISCAL = false;
    private static final Boolean UPDATED_ENDERECO_FISCAL = true;

    private static final String ENTITY_API_URL = "/api/endereco-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EnderecoEmpresaRepository enderecoEmpresaRepository;

    @Mock
    private EnderecoEmpresaRepository enderecoEmpresaRepositoryMock;

    @Autowired
    private EnderecoEmpresaMapper enderecoEmpresaMapper;

    @Mock
    private EnderecoEmpresaService enderecoEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnderecoEmpresaMockMvc;

    private EnderecoEmpresa enderecoEmpresa;

    private EnderecoEmpresa insertedEnderecoEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoEmpresa createEntity(EntityManager em) {
        EnderecoEmpresa enderecoEmpresa = new EnderecoEmpresa()
            .logradouro(DEFAULT_LOGRADOURO)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .bairro(DEFAULT_BAIRRO)
            .cep(DEFAULT_CEP)
            .principal(DEFAULT_PRINCIPAL)
            .filial(DEFAULT_FILIAL)
            .enderecoFiscal(DEFAULT_ENDERECO_FISCAL);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        enderecoEmpresa.setEmpresa(empresa);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        enderecoEmpresa.setCidade(cidade);
        return enderecoEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnderecoEmpresa createUpdatedEntity(EntityManager em) {
        EnderecoEmpresa enderecoEmpresa = new EnderecoEmpresa()
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL)
            .filial(UPDATED_FILIAL)
            .enderecoFiscal(UPDATED_ENDERECO_FISCAL);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        enderecoEmpresa.setEmpresa(empresa);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createUpdatedEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        enderecoEmpresa.setCidade(cidade);
        return enderecoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        enderecoEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEnderecoEmpresa != null) {
            enderecoEmpresaRepository.delete(insertedEnderecoEmpresa);
            insertedEnderecoEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EnderecoEmpresa
        EnderecoEmpresaDTO enderecoEmpresaDTO = enderecoEmpresaMapper.toDto(enderecoEmpresa);
        var returnedEnderecoEmpresaDTO = om.readValue(
            restEnderecoEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoEmpresaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EnderecoEmpresaDTO.class
        );

        // Validate the EnderecoEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEnderecoEmpresa = enderecoEmpresaMapper.toEntity(returnedEnderecoEmpresaDTO);
        assertEnderecoEmpresaUpdatableFieldsEquals(returnedEnderecoEmpresa, getPersistedEnderecoEmpresa(returnedEnderecoEmpresa));

        insertedEnderecoEmpresa = returnedEnderecoEmpresa;
    }

    @Test
    @Transactional
    void createEnderecoEmpresaWithExistingId() throws Exception {
        // Create the EnderecoEmpresa with an existing ID
        enderecoEmpresa.setId(1L);
        EnderecoEmpresaDTO enderecoEmpresaDTO = enderecoEmpresaMapper.toDto(enderecoEmpresa);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnderecoEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresas() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList
        restEnderecoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enderecoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())))
            .andExpect(jsonPath("$.[*].filial").value(hasItem(DEFAULT_FILIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].enderecoFiscal").value(hasItem(DEFAULT_ENDERECO_FISCAL.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEnderecoEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(enderecoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEnderecoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(enderecoEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEnderecoEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(enderecoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEnderecoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(enderecoEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEnderecoEmpresa() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get the enderecoEmpresa
        restEnderecoEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, enderecoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enderecoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.principal").value(DEFAULT_PRINCIPAL.booleanValue()))
            .andExpect(jsonPath("$.filial").value(DEFAULT_FILIAL.booleanValue()))
            .andExpect(jsonPath("$.enderecoFiscal").value(DEFAULT_ENDERECO_FISCAL.booleanValue()));
    }

    @Test
    @Transactional
    void getEnderecoEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        Long id = enderecoEmpresa.getId();

        defaultEnderecoEmpresaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEnderecoEmpresaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEnderecoEmpresaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByLogradouroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where logradouro equals to
        defaultEnderecoEmpresaFiltering("logradouro.equals=" + DEFAULT_LOGRADOURO, "logradouro.equals=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByLogradouroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where logradouro in
        defaultEnderecoEmpresaFiltering(
            "logradouro.in=" + DEFAULT_LOGRADOURO + "," + UPDATED_LOGRADOURO,
            "logradouro.in=" + UPDATED_LOGRADOURO
        );
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByLogradouroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where logradouro is not null
        defaultEnderecoEmpresaFiltering("logradouro.specified=true", "logradouro.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByLogradouroContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where logradouro contains
        defaultEnderecoEmpresaFiltering("logradouro.contains=" + DEFAULT_LOGRADOURO, "logradouro.contains=" + UPDATED_LOGRADOURO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByLogradouroNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where logradouro does not contain
        defaultEnderecoEmpresaFiltering(
            "logradouro.doesNotContain=" + UPDATED_LOGRADOURO,
            "logradouro.doesNotContain=" + DEFAULT_LOGRADOURO
        );
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where numero equals to
        defaultEnderecoEmpresaFiltering("numero.equals=" + DEFAULT_NUMERO, "numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where numero in
        defaultEnderecoEmpresaFiltering("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO, "numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where numero is not null
        defaultEnderecoEmpresaFiltering("numero.specified=true", "numero.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByNumeroContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where numero contains
        defaultEnderecoEmpresaFiltering("numero.contains=" + DEFAULT_NUMERO, "numero.contains=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByNumeroNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where numero does not contain
        defaultEnderecoEmpresaFiltering("numero.doesNotContain=" + UPDATED_NUMERO, "numero.doesNotContain=" + DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByComplementoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where complemento equals to
        defaultEnderecoEmpresaFiltering("complemento.equals=" + DEFAULT_COMPLEMENTO, "complemento.equals=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByComplementoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where complemento in
        defaultEnderecoEmpresaFiltering(
            "complemento.in=" + DEFAULT_COMPLEMENTO + "," + UPDATED_COMPLEMENTO,
            "complemento.in=" + UPDATED_COMPLEMENTO
        );
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByComplementoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where complemento is not null
        defaultEnderecoEmpresaFiltering("complemento.specified=true", "complemento.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByComplementoContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where complemento contains
        defaultEnderecoEmpresaFiltering("complemento.contains=" + DEFAULT_COMPLEMENTO, "complemento.contains=" + UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByComplementoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where complemento does not contain
        defaultEnderecoEmpresaFiltering(
            "complemento.doesNotContain=" + UPDATED_COMPLEMENTO,
            "complemento.doesNotContain=" + DEFAULT_COMPLEMENTO
        );
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByBairroIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where bairro equals to
        defaultEnderecoEmpresaFiltering("bairro.equals=" + DEFAULT_BAIRRO, "bairro.equals=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByBairroIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where bairro in
        defaultEnderecoEmpresaFiltering("bairro.in=" + DEFAULT_BAIRRO + "," + UPDATED_BAIRRO, "bairro.in=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByBairroIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where bairro is not null
        defaultEnderecoEmpresaFiltering("bairro.specified=true", "bairro.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByBairroContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where bairro contains
        defaultEnderecoEmpresaFiltering("bairro.contains=" + DEFAULT_BAIRRO, "bairro.contains=" + UPDATED_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByBairroNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where bairro does not contain
        defaultEnderecoEmpresaFiltering("bairro.doesNotContain=" + UPDATED_BAIRRO, "bairro.doesNotContain=" + DEFAULT_BAIRRO);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByCepIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where cep equals to
        defaultEnderecoEmpresaFiltering("cep.equals=" + DEFAULT_CEP, "cep.equals=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByCepIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where cep in
        defaultEnderecoEmpresaFiltering("cep.in=" + DEFAULT_CEP + "," + UPDATED_CEP, "cep.in=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByCepIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where cep is not null
        defaultEnderecoEmpresaFiltering("cep.specified=true", "cep.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByCepContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where cep contains
        defaultEnderecoEmpresaFiltering("cep.contains=" + DEFAULT_CEP, "cep.contains=" + UPDATED_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByCepNotContainsSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where cep does not contain
        defaultEnderecoEmpresaFiltering("cep.doesNotContain=" + UPDATED_CEP, "cep.doesNotContain=" + DEFAULT_CEP);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByPrincipalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where principal equals to
        defaultEnderecoEmpresaFiltering("principal.equals=" + DEFAULT_PRINCIPAL, "principal.equals=" + UPDATED_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByPrincipalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where principal in
        defaultEnderecoEmpresaFiltering("principal.in=" + DEFAULT_PRINCIPAL + "," + UPDATED_PRINCIPAL, "principal.in=" + UPDATED_PRINCIPAL);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByPrincipalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where principal is not null
        defaultEnderecoEmpresaFiltering("principal.specified=true", "principal.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByFilialIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where filial equals to
        defaultEnderecoEmpresaFiltering("filial.equals=" + DEFAULT_FILIAL, "filial.equals=" + UPDATED_FILIAL);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByFilialIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where filial in
        defaultEnderecoEmpresaFiltering("filial.in=" + DEFAULT_FILIAL + "," + UPDATED_FILIAL, "filial.in=" + UPDATED_FILIAL);
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByFilialIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where filial is not null
        defaultEnderecoEmpresaFiltering("filial.specified=true", "filial.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByEnderecoFiscalIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where enderecoFiscal equals to
        defaultEnderecoEmpresaFiltering(
            "enderecoFiscal.equals=" + DEFAULT_ENDERECO_FISCAL,
            "enderecoFiscal.equals=" + UPDATED_ENDERECO_FISCAL
        );
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByEnderecoFiscalIsInShouldWork() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where enderecoFiscal in
        defaultEnderecoEmpresaFiltering(
            "enderecoFiscal.in=" + DEFAULT_ENDERECO_FISCAL + "," + UPDATED_ENDERECO_FISCAL,
            "enderecoFiscal.in=" + UPDATED_ENDERECO_FISCAL
        );
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByEnderecoFiscalIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        // Get all the enderecoEmpresaList where enderecoFiscal is not null
        defaultEnderecoEmpresaFiltering("enderecoFiscal.specified=true", "enderecoFiscal.specified=false");
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        enderecoEmpresa.setEmpresa(empresa);
        enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);
        Long empresaId = empresa.getId();
        // Get all the enderecoEmpresaList where empresa equals to empresaId
        defaultEnderecoEmpresaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the enderecoEmpresaList where empresa equals to (empresaId + 1)
        defaultEnderecoEmpresaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllEnderecoEmpresasByCidadeIsEqualToSomething() throws Exception {
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);
            cidade = CidadeResourceIT.createEntity(em);
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        em.persist(cidade);
        em.flush();
        enderecoEmpresa.setCidade(cidade);
        enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);
        Long cidadeId = cidade.getId();
        // Get all the enderecoEmpresaList where cidade equals to cidadeId
        defaultEnderecoEmpresaShouldBeFound("cidadeId.equals=" + cidadeId);

        // Get all the enderecoEmpresaList where cidade equals to (cidadeId + 1)
        defaultEnderecoEmpresaShouldNotBeFound("cidadeId.equals=" + (cidadeId + 1));
    }

    private void defaultEnderecoEmpresaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEnderecoEmpresaShouldBeFound(shouldBeFound);
        defaultEnderecoEmpresaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnderecoEmpresaShouldBeFound(String filter) throws Exception {
        restEnderecoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enderecoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].principal").value(hasItem(DEFAULT_PRINCIPAL.booleanValue())))
            .andExpect(jsonPath("$.[*].filial").value(hasItem(DEFAULT_FILIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].enderecoFiscal").value(hasItem(DEFAULT_ENDERECO_FISCAL.booleanValue())));

        // Check, that the count call also returns 1
        restEnderecoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnderecoEmpresaShouldNotBeFound(String filter) throws Exception {
        restEnderecoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnderecoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEnderecoEmpresa() throws Exception {
        // Get the enderecoEmpresa
        restEnderecoEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnderecoEmpresa() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoEmpresa
        EnderecoEmpresa updatedEnderecoEmpresa = enderecoEmpresaRepository.findById(enderecoEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEnderecoEmpresa are not directly saved in db
        em.detach(updatedEnderecoEmpresa);
        updatedEnderecoEmpresa
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL)
            .filial(UPDATED_FILIAL)
            .enderecoFiscal(UPDATED_ENDERECO_FISCAL);
        EnderecoEmpresaDTO enderecoEmpresaDTO = enderecoEmpresaMapper.toDto(updatedEnderecoEmpresa);

        restEnderecoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enderecoEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enderecoEmpresaDTO))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEnderecoEmpresaToMatchAllProperties(updatedEnderecoEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // Create the EnderecoEmpresa
        EnderecoEmpresaDTO enderecoEmpresaDTO = enderecoEmpresaMapper.toDto(enderecoEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enderecoEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enderecoEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // Create the EnderecoEmpresa
        EnderecoEmpresaDTO enderecoEmpresaDTO = enderecoEmpresaMapper.toDto(enderecoEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(enderecoEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // Create the EnderecoEmpresa
        EnderecoEmpresaDTO enderecoEmpresaDTO = enderecoEmpresaMapper.toDto(enderecoEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(enderecoEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnderecoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoEmpresa using partial update
        EnderecoEmpresa partialUpdatedEnderecoEmpresa = new EnderecoEmpresa();
        partialUpdatedEnderecoEmpresa.setId(enderecoEmpresa.getId());

        partialUpdatedEnderecoEmpresa.numero(UPDATED_NUMERO).bairro(UPDATED_BAIRRO).cep(UPDATED_CEP);

        restEnderecoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnderecoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnderecoEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEnderecoEmpresa, enderecoEmpresa),
            getPersistedEnderecoEmpresa(enderecoEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateEnderecoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the enderecoEmpresa using partial update
        EnderecoEmpresa partialUpdatedEnderecoEmpresa = new EnderecoEmpresa();
        partialUpdatedEnderecoEmpresa.setId(enderecoEmpresa.getId());

        partialUpdatedEnderecoEmpresa
            .logradouro(UPDATED_LOGRADOURO)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .principal(UPDATED_PRINCIPAL)
            .filial(UPDATED_FILIAL)
            .enderecoFiscal(UPDATED_ENDERECO_FISCAL);

        restEnderecoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnderecoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEnderecoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the EnderecoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEnderecoEmpresaUpdatableFieldsEquals(
            partialUpdatedEnderecoEmpresa,
            getPersistedEnderecoEmpresa(partialUpdatedEnderecoEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // Create the EnderecoEmpresa
        EnderecoEmpresaDTO enderecoEmpresaDTO = enderecoEmpresaMapper.toDto(enderecoEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enderecoEmpresaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enderecoEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // Create the EnderecoEmpresa
        EnderecoEmpresaDTO enderecoEmpresaDTO = enderecoEmpresaMapper.toDto(enderecoEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(enderecoEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnderecoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        enderecoEmpresa.setId(longCount.incrementAndGet());

        // Create the EnderecoEmpresa
        EnderecoEmpresaDTO enderecoEmpresaDTO = enderecoEmpresaMapper.toDto(enderecoEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnderecoEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(enderecoEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnderecoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnderecoEmpresa() throws Exception {
        // Initialize the database
        insertedEnderecoEmpresa = enderecoEmpresaRepository.saveAndFlush(enderecoEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the enderecoEmpresa
        restEnderecoEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, enderecoEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return enderecoEmpresaRepository.count();
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

    protected EnderecoEmpresa getPersistedEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
        return enderecoEmpresaRepository.findById(enderecoEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedEnderecoEmpresaToMatchAllProperties(EnderecoEmpresa expectedEnderecoEmpresa) {
        assertEnderecoEmpresaAllPropertiesEquals(expectedEnderecoEmpresa, getPersistedEnderecoEmpresa(expectedEnderecoEmpresa));
    }

    protected void assertPersistedEnderecoEmpresaToMatchUpdatableProperties(EnderecoEmpresa expectedEnderecoEmpresa) {
        assertEnderecoEmpresaAllUpdatablePropertiesEquals(expectedEnderecoEmpresa, getPersistedEnderecoEmpresa(expectedEnderecoEmpresa));
    }
}
