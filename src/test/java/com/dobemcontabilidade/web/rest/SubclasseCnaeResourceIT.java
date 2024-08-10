package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.SubclasseCnaeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.ClasseCnae;
import com.dobemcontabilidade.domain.SegmentoCnae;
import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.repository.SubclasseCnaeRepository;
import com.dobemcontabilidade.service.SubclasseCnaeService;
import com.dobemcontabilidade.service.dto.SubclasseCnaeDTO;
import com.dobemcontabilidade.service.mapper.SubclasseCnaeMapper;
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
 * Integration tests for the {@link SubclasseCnaeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SubclasseCnaeResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANEXO = 1;
    private static final Integer UPDATED_ANEXO = 2;
    private static final Integer SMALLER_ANEXO = 1 - 1;

    private static final Boolean DEFAULT_ATENDIDO_FREEMIUM = false;
    private static final Boolean UPDATED_ATENDIDO_FREEMIUM = true;

    private static final Boolean DEFAULT_ATENDIDO = false;
    private static final Boolean UPDATED_ATENDIDO = true;

    private static final Boolean DEFAULT_OPTANTE_SIMPLES = false;
    private static final Boolean UPDATED_OPTANTE_SIMPLES = true;

    private static final Boolean DEFAULT_ACEITA_MEI = false;
    private static final Boolean UPDATED_ACEITA_MEI = true;

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subclasse-cnaes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubclasseCnaeRepository subclasseCnaeRepository;

    @Mock
    private SubclasseCnaeRepository subclasseCnaeRepositoryMock;

    @Autowired
    private SubclasseCnaeMapper subclasseCnaeMapper;

    @Mock
    private SubclasseCnaeService subclasseCnaeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubclasseCnaeMockMvc;

    private SubclasseCnae subclasseCnae;

    private SubclasseCnae insertedSubclasseCnae;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubclasseCnae createEntity(EntityManager em) {
        SubclasseCnae subclasseCnae = new SubclasseCnae()
            .codigo(DEFAULT_CODIGO)
            .descricao(DEFAULT_DESCRICAO)
            .anexo(DEFAULT_ANEXO)
            .atendidoFreemium(DEFAULT_ATENDIDO_FREEMIUM)
            .atendido(DEFAULT_ATENDIDO)
            .optanteSimples(DEFAULT_OPTANTE_SIMPLES)
            .aceitaMEI(DEFAULT_ACEITA_MEI)
            .categoria(DEFAULT_CATEGORIA);
        // Add required entity
        ClasseCnae classeCnae;
        if (TestUtil.findAll(em, ClasseCnae.class).isEmpty()) {
            classeCnae = ClasseCnaeResourceIT.createEntity(em);
            em.persist(classeCnae);
            em.flush();
        } else {
            classeCnae = TestUtil.findAll(em, ClasseCnae.class).get(0);
        }
        subclasseCnae.setClasse(classeCnae);
        return subclasseCnae;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubclasseCnae createUpdatedEntity(EntityManager em) {
        SubclasseCnae subclasseCnae = new SubclasseCnae()
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .anexo(UPDATED_ANEXO)
            .atendidoFreemium(UPDATED_ATENDIDO_FREEMIUM)
            .atendido(UPDATED_ATENDIDO)
            .optanteSimples(UPDATED_OPTANTE_SIMPLES)
            .aceitaMEI(UPDATED_ACEITA_MEI)
            .categoria(UPDATED_CATEGORIA);
        // Add required entity
        ClasseCnae classeCnae;
        if (TestUtil.findAll(em, ClasseCnae.class).isEmpty()) {
            classeCnae = ClasseCnaeResourceIT.createUpdatedEntity(em);
            em.persist(classeCnae);
            em.flush();
        } else {
            classeCnae = TestUtil.findAll(em, ClasseCnae.class).get(0);
        }
        subclasseCnae.setClasse(classeCnae);
        return subclasseCnae;
    }

    @BeforeEach
    public void initTest() {
        subclasseCnae = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSubclasseCnae != null) {
            subclasseCnaeRepository.delete(insertedSubclasseCnae);
            insertedSubclasseCnae = null;
        }
    }

    @Test
    @Transactional
    void createSubclasseCnae() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SubclasseCnae
        SubclasseCnaeDTO subclasseCnaeDTO = subclasseCnaeMapper.toDto(subclasseCnae);
        var returnedSubclasseCnaeDTO = om.readValue(
            restSubclasseCnaeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subclasseCnaeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SubclasseCnaeDTO.class
        );

        // Validate the SubclasseCnae in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSubclasseCnae = subclasseCnaeMapper.toEntity(returnedSubclasseCnaeDTO);
        assertSubclasseCnaeUpdatableFieldsEquals(returnedSubclasseCnae, getPersistedSubclasseCnae(returnedSubclasseCnae));

        insertedSubclasseCnae = returnedSubclasseCnae;
    }

    @Test
    @Transactional
    void createSubclasseCnaeWithExistingId() throws Exception {
        // Create the SubclasseCnae with an existing ID
        subclasseCnae.setId(1L);
        SubclasseCnaeDTO subclasseCnaeDTO = subclasseCnaeMapper.toDto(subclasseCnae);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubclasseCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subclasseCnaeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubclasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodigoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        subclasseCnae.setCodigo(null);

        // Create the SubclasseCnae, which fails.
        SubclasseCnaeDTO subclasseCnaeDTO = subclasseCnaeMapper.toDto(subclasseCnae);

        restSubclasseCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subclasseCnaeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaes() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList
        restSubclasseCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subclasseCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(DEFAULT_ANEXO)))
            .andExpect(jsonPath("$.[*].atendidoFreemium").value(hasItem(DEFAULT_ATENDIDO_FREEMIUM.booleanValue())))
            .andExpect(jsonPath("$.[*].atendido").value(hasItem(DEFAULT_ATENDIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].optanteSimples").value(hasItem(DEFAULT_OPTANTE_SIMPLES.booleanValue())))
            .andExpect(jsonPath("$.[*].aceitaMEI").value(hasItem(DEFAULT_ACEITA_MEI.booleanValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubclasseCnaesWithEagerRelationshipsIsEnabled() throws Exception {
        when(subclasseCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubclasseCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(subclasseCnaeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSubclasseCnaesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(subclasseCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubclasseCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(subclasseCnaeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSubclasseCnae() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get the subclasseCnae
        restSubclasseCnaeMockMvc
            .perform(get(ENTITY_API_URL_ID, subclasseCnae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subclasseCnae.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.anexo").value(DEFAULT_ANEXO))
            .andExpect(jsonPath("$.atendidoFreemium").value(DEFAULT_ATENDIDO_FREEMIUM.booleanValue()))
            .andExpect(jsonPath("$.atendido").value(DEFAULT_ATENDIDO.booleanValue()))
            .andExpect(jsonPath("$.optanteSimples").value(DEFAULT_OPTANTE_SIMPLES.booleanValue()))
            .andExpect(jsonPath("$.aceitaMEI").value(DEFAULT_ACEITA_MEI.booleanValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA));
    }

    @Test
    @Transactional
    void getSubclasseCnaesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        Long id = subclasseCnae.getId();

        defaultSubclasseCnaeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSubclasseCnaeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSubclasseCnaeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where codigo equals to
        defaultSubclasseCnaeFiltering("codigo.equals=" + DEFAULT_CODIGO, "codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where codigo in
        defaultSubclasseCnaeFiltering("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO, "codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where codigo is not null
        defaultSubclasseCnaeFiltering("codigo.specified=true", "codigo.specified=false");
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByCodigoContainsSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where codigo contains
        defaultSubclasseCnaeFiltering("codigo.contains=" + DEFAULT_CODIGO, "codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where codigo does not contain
        defaultSubclasseCnaeFiltering("codigo.doesNotContain=" + UPDATED_CODIGO, "codigo.doesNotContain=" + DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where anexo equals to
        defaultSubclasseCnaeFiltering("anexo.equals=" + DEFAULT_ANEXO, "anexo.equals=" + UPDATED_ANEXO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAnexoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where anexo in
        defaultSubclasseCnaeFiltering("anexo.in=" + DEFAULT_ANEXO + "," + UPDATED_ANEXO, "anexo.in=" + UPDATED_ANEXO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAnexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where anexo is not null
        defaultSubclasseCnaeFiltering("anexo.specified=true", "anexo.specified=false");
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAnexoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where anexo is greater than or equal to
        defaultSubclasseCnaeFiltering("anexo.greaterThanOrEqual=" + DEFAULT_ANEXO, "anexo.greaterThanOrEqual=" + UPDATED_ANEXO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAnexoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where anexo is less than or equal to
        defaultSubclasseCnaeFiltering("anexo.lessThanOrEqual=" + DEFAULT_ANEXO, "anexo.lessThanOrEqual=" + SMALLER_ANEXO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAnexoIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where anexo is less than
        defaultSubclasseCnaeFiltering("anexo.lessThan=" + UPDATED_ANEXO, "anexo.lessThan=" + DEFAULT_ANEXO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAnexoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where anexo is greater than
        defaultSubclasseCnaeFiltering("anexo.greaterThan=" + SMALLER_ANEXO, "anexo.greaterThan=" + DEFAULT_ANEXO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAtendidoFreemiumIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where atendidoFreemium equals to
        defaultSubclasseCnaeFiltering(
            "atendidoFreemium.equals=" + DEFAULT_ATENDIDO_FREEMIUM,
            "atendidoFreemium.equals=" + UPDATED_ATENDIDO_FREEMIUM
        );
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAtendidoFreemiumIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where atendidoFreemium in
        defaultSubclasseCnaeFiltering(
            "atendidoFreemium.in=" + DEFAULT_ATENDIDO_FREEMIUM + "," + UPDATED_ATENDIDO_FREEMIUM,
            "atendidoFreemium.in=" + UPDATED_ATENDIDO_FREEMIUM
        );
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAtendidoFreemiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where atendidoFreemium is not null
        defaultSubclasseCnaeFiltering("atendidoFreemium.specified=true", "atendidoFreemium.specified=false");
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAtendidoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where atendido equals to
        defaultSubclasseCnaeFiltering("atendido.equals=" + DEFAULT_ATENDIDO, "atendido.equals=" + UPDATED_ATENDIDO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAtendidoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where atendido in
        defaultSubclasseCnaeFiltering("atendido.in=" + DEFAULT_ATENDIDO + "," + UPDATED_ATENDIDO, "atendido.in=" + UPDATED_ATENDIDO);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAtendidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where atendido is not null
        defaultSubclasseCnaeFiltering("atendido.specified=true", "atendido.specified=false");
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByOptanteSimplesIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where optanteSimples equals to
        defaultSubclasseCnaeFiltering(
            "optanteSimples.equals=" + DEFAULT_OPTANTE_SIMPLES,
            "optanteSimples.equals=" + UPDATED_OPTANTE_SIMPLES
        );
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByOptanteSimplesIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where optanteSimples in
        defaultSubclasseCnaeFiltering(
            "optanteSimples.in=" + DEFAULT_OPTANTE_SIMPLES + "," + UPDATED_OPTANTE_SIMPLES,
            "optanteSimples.in=" + UPDATED_OPTANTE_SIMPLES
        );
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByOptanteSimplesIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where optanteSimples is not null
        defaultSubclasseCnaeFiltering("optanteSimples.specified=true", "optanteSimples.specified=false");
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAceitaMEIIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where aceitaMEI equals to
        defaultSubclasseCnaeFiltering("aceitaMEI.equals=" + DEFAULT_ACEITA_MEI, "aceitaMEI.equals=" + UPDATED_ACEITA_MEI);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAceitaMEIIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where aceitaMEI in
        defaultSubclasseCnaeFiltering(
            "aceitaMEI.in=" + DEFAULT_ACEITA_MEI + "," + UPDATED_ACEITA_MEI,
            "aceitaMEI.in=" + UPDATED_ACEITA_MEI
        );
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByAceitaMEIIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where aceitaMEI is not null
        defaultSubclasseCnaeFiltering("aceitaMEI.specified=true", "aceitaMEI.specified=false");
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByCategoriaIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where categoria equals to
        defaultSubclasseCnaeFiltering("categoria.equals=" + DEFAULT_CATEGORIA, "categoria.equals=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByCategoriaIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where categoria in
        defaultSubclasseCnaeFiltering("categoria.in=" + DEFAULT_CATEGORIA + "," + UPDATED_CATEGORIA, "categoria.in=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByCategoriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where categoria is not null
        defaultSubclasseCnaeFiltering("categoria.specified=true", "categoria.specified=false");
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByCategoriaContainsSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where categoria contains
        defaultSubclasseCnaeFiltering("categoria.contains=" + DEFAULT_CATEGORIA, "categoria.contains=" + UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByCategoriaNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        // Get all the subclasseCnaeList where categoria does not contain
        defaultSubclasseCnaeFiltering("categoria.doesNotContain=" + UPDATED_CATEGORIA, "categoria.doesNotContain=" + DEFAULT_CATEGORIA);
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesByClasseIsEqualToSomething() throws Exception {
        ClasseCnae classe;
        if (TestUtil.findAll(em, ClasseCnae.class).isEmpty()) {
            subclasseCnaeRepository.saveAndFlush(subclasseCnae);
            classe = ClasseCnaeResourceIT.createEntity(em);
        } else {
            classe = TestUtil.findAll(em, ClasseCnae.class).get(0);
        }
        em.persist(classe);
        em.flush();
        subclasseCnae.setClasse(classe);
        subclasseCnaeRepository.saveAndFlush(subclasseCnae);
        Long classeId = classe.getId();
        // Get all the subclasseCnaeList where classe equals to classeId
        defaultSubclasseCnaeShouldBeFound("classeId.equals=" + classeId);

        // Get all the subclasseCnaeList where classe equals to (classeId + 1)
        defaultSubclasseCnaeShouldNotBeFound("classeId.equals=" + (classeId + 1));
    }

    @Test
    @Transactional
    void getAllSubclasseCnaesBySegmentoCnaeIsEqualToSomething() throws Exception {
        SegmentoCnae segmentoCnae;
        if (TestUtil.findAll(em, SegmentoCnae.class).isEmpty()) {
            subclasseCnaeRepository.saveAndFlush(subclasseCnae);
            segmentoCnae = SegmentoCnaeResourceIT.createEntity(em);
        } else {
            segmentoCnae = TestUtil.findAll(em, SegmentoCnae.class).get(0);
        }
        em.persist(segmentoCnae);
        em.flush();
        subclasseCnae.addSegmentoCnae(segmentoCnae);
        subclasseCnaeRepository.saveAndFlush(subclasseCnae);
        Long segmentoCnaeId = segmentoCnae.getId();
        // Get all the subclasseCnaeList where segmentoCnae equals to segmentoCnaeId
        defaultSubclasseCnaeShouldBeFound("segmentoCnaeId.equals=" + segmentoCnaeId);

        // Get all the subclasseCnaeList where segmentoCnae equals to (segmentoCnaeId + 1)
        defaultSubclasseCnaeShouldNotBeFound("segmentoCnaeId.equals=" + (segmentoCnaeId + 1));
    }

    private void defaultSubclasseCnaeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSubclasseCnaeShouldBeFound(shouldBeFound);
        defaultSubclasseCnaeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubclasseCnaeShouldBeFound(String filter) throws Exception {
        restSubclasseCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subclasseCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(DEFAULT_ANEXO)))
            .andExpect(jsonPath("$.[*].atendidoFreemium").value(hasItem(DEFAULT_ATENDIDO_FREEMIUM.booleanValue())))
            .andExpect(jsonPath("$.[*].atendido").value(hasItem(DEFAULT_ATENDIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].optanteSimples").value(hasItem(DEFAULT_OPTANTE_SIMPLES.booleanValue())))
            .andExpect(jsonPath("$.[*].aceitaMEI").value(hasItem(DEFAULT_ACEITA_MEI.booleanValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)));

        // Check, that the count call also returns 1
        restSubclasseCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubclasseCnaeShouldNotBeFound(String filter) throws Exception {
        restSubclasseCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubclasseCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSubclasseCnae() throws Exception {
        // Get the subclasseCnae
        restSubclasseCnaeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubclasseCnae() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subclasseCnae
        SubclasseCnae updatedSubclasseCnae = subclasseCnaeRepository.findById(subclasseCnae.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubclasseCnae are not directly saved in db
        em.detach(updatedSubclasseCnae);
        updatedSubclasseCnae
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .anexo(UPDATED_ANEXO)
            .atendidoFreemium(UPDATED_ATENDIDO_FREEMIUM)
            .atendido(UPDATED_ATENDIDO)
            .optanteSimples(UPDATED_OPTANTE_SIMPLES)
            .aceitaMEI(UPDATED_ACEITA_MEI)
            .categoria(UPDATED_CATEGORIA);
        SubclasseCnaeDTO subclasseCnaeDTO = subclasseCnaeMapper.toDto(updatedSubclasseCnae);

        restSubclasseCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subclasseCnaeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subclasseCnaeDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubclasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubclasseCnaeToMatchAllProperties(updatedSubclasseCnae);
    }

    @Test
    @Transactional
    void putNonExistingSubclasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subclasseCnae.setId(longCount.incrementAndGet());

        // Create the SubclasseCnae
        SubclasseCnaeDTO subclasseCnaeDTO = subclasseCnaeMapper.toDto(subclasseCnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubclasseCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subclasseCnaeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subclasseCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubclasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubclasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subclasseCnae.setId(longCount.incrementAndGet());

        // Create the SubclasseCnae
        SubclasseCnaeDTO subclasseCnaeDTO = subclasseCnaeMapper.toDto(subclasseCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubclasseCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subclasseCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubclasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubclasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subclasseCnae.setId(longCount.incrementAndGet());

        // Create the SubclasseCnae
        SubclasseCnaeDTO subclasseCnaeDTO = subclasseCnaeMapper.toDto(subclasseCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubclasseCnaeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subclasseCnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubclasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubclasseCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subclasseCnae using partial update
        SubclasseCnae partialUpdatedSubclasseCnae = new SubclasseCnae();
        partialUpdatedSubclasseCnae.setId(subclasseCnae.getId());

        partialUpdatedSubclasseCnae
            .anexo(UPDATED_ANEXO)
            .atendido(UPDATED_ATENDIDO)
            .optanteSimples(UPDATED_OPTANTE_SIMPLES)
            .aceitaMEI(UPDATED_ACEITA_MEI);

        restSubclasseCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubclasseCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubclasseCnae))
            )
            .andExpect(status().isOk());

        // Validate the SubclasseCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubclasseCnaeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubclasseCnae, subclasseCnae),
            getPersistedSubclasseCnae(subclasseCnae)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubclasseCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subclasseCnae using partial update
        SubclasseCnae partialUpdatedSubclasseCnae = new SubclasseCnae();
        partialUpdatedSubclasseCnae.setId(subclasseCnae.getId());

        partialUpdatedSubclasseCnae
            .codigo(UPDATED_CODIGO)
            .descricao(UPDATED_DESCRICAO)
            .anexo(UPDATED_ANEXO)
            .atendidoFreemium(UPDATED_ATENDIDO_FREEMIUM)
            .atendido(UPDATED_ATENDIDO)
            .optanteSimples(UPDATED_OPTANTE_SIMPLES)
            .aceitaMEI(UPDATED_ACEITA_MEI)
            .categoria(UPDATED_CATEGORIA);

        restSubclasseCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubclasseCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubclasseCnae))
            )
            .andExpect(status().isOk());

        // Validate the SubclasseCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubclasseCnaeUpdatableFieldsEquals(partialUpdatedSubclasseCnae, getPersistedSubclasseCnae(partialUpdatedSubclasseCnae));
    }

    @Test
    @Transactional
    void patchNonExistingSubclasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subclasseCnae.setId(longCount.incrementAndGet());

        // Create the SubclasseCnae
        SubclasseCnaeDTO subclasseCnaeDTO = subclasseCnaeMapper.toDto(subclasseCnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubclasseCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subclasseCnaeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subclasseCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubclasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubclasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subclasseCnae.setId(longCount.incrementAndGet());

        // Create the SubclasseCnae
        SubclasseCnaeDTO subclasseCnaeDTO = subclasseCnaeMapper.toDto(subclasseCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubclasseCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subclasseCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubclasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubclasseCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subclasseCnae.setId(longCount.incrementAndGet());

        // Create the SubclasseCnae
        SubclasseCnaeDTO subclasseCnaeDTO = subclasseCnaeMapper.toDto(subclasseCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubclasseCnaeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(subclasseCnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubclasseCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubclasseCnae() throws Exception {
        // Initialize the database
        insertedSubclasseCnae = subclasseCnaeRepository.saveAndFlush(subclasseCnae);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subclasseCnae
        restSubclasseCnaeMockMvc
            .perform(delete(ENTITY_API_URL_ID, subclasseCnae.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subclasseCnaeRepository.count();
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

    protected SubclasseCnae getPersistedSubclasseCnae(SubclasseCnae subclasseCnae) {
        return subclasseCnaeRepository.findById(subclasseCnae.getId()).orElseThrow();
    }

    protected void assertPersistedSubclasseCnaeToMatchAllProperties(SubclasseCnae expectedSubclasseCnae) {
        assertSubclasseCnaeAllPropertiesEquals(expectedSubclasseCnae, getPersistedSubclasseCnae(expectedSubclasseCnae));
    }

    protected void assertPersistedSubclasseCnaeToMatchUpdatableProperties(SubclasseCnae expectedSubclasseCnae) {
        assertSubclasseCnaeAllUpdatablePropertiesEquals(expectedSubclasseCnae, getPersistedSubclasseCnae(expectedSubclasseCnae));
    }
}
