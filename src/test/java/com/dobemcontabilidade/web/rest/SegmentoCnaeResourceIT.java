package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.SegmentoCnaeAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Ramo;
import com.dobemcontabilidade.domain.SegmentoCnae;
import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.domain.enumeration.TipoSegmentoEnum;
import com.dobemcontabilidade.repository.SegmentoCnaeRepository;
import com.dobemcontabilidade.service.SegmentoCnaeService;
import com.dobemcontabilidade.service.dto.SegmentoCnaeDTO;
import com.dobemcontabilidade.service.mapper.SegmentoCnaeMapper;
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
 * Integration tests for the {@link SegmentoCnaeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SegmentoCnaeResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEM = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEM = "BBBBBBBBBB";

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final TipoSegmentoEnum DEFAULT_TIPO = TipoSegmentoEnum.SERVICO;
    private static final TipoSegmentoEnum UPDATED_TIPO = TipoSegmentoEnum.COMERCIO;

    private static final String ENTITY_API_URL = "/api/segmento-cnaes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SegmentoCnaeRepository segmentoCnaeRepository;

    @Mock
    private SegmentoCnaeRepository segmentoCnaeRepositoryMock;

    @Autowired
    private SegmentoCnaeMapper segmentoCnaeMapper;

    @Mock
    private SegmentoCnaeService segmentoCnaeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSegmentoCnaeMockMvc;

    private SegmentoCnae segmentoCnae;

    private SegmentoCnae insertedSegmentoCnae;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SegmentoCnae createEntity(EntityManager em) {
        SegmentoCnae segmentoCnae = new SegmentoCnae()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .icon(DEFAULT_ICON)
            .imagem(DEFAULT_IMAGEM)
            .tags(DEFAULT_TAGS)
            .tipo(DEFAULT_TIPO);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        segmentoCnae.setRamo(ramo);
        return segmentoCnae;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SegmentoCnae createUpdatedEntity(EntityManager em) {
        SegmentoCnae segmentoCnae = new SegmentoCnae()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .icon(UPDATED_ICON)
            .imagem(UPDATED_IMAGEM)
            .tags(UPDATED_TAGS)
            .tipo(UPDATED_TIPO);
        // Add required entity
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            ramo = RamoResourceIT.createUpdatedEntity(em);
            em.persist(ramo);
            em.flush();
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        segmentoCnae.setRamo(ramo);
        return segmentoCnae;
    }

    @BeforeEach
    public void initTest() {
        segmentoCnae = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedSegmentoCnae != null) {
            segmentoCnaeRepository.delete(insertedSegmentoCnae);
            insertedSegmentoCnae = null;
        }
    }

    @Test
    @Transactional
    void createSegmentoCnae() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SegmentoCnae
        SegmentoCnaeDTO segmentoCnaeDTO = segmentoCnaeMapper.toDto(segmentoCnae);
        var returnedSegmentoCnaeDTO = om.readValue(
            restSegmentoCnaeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(segmentoCnaeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SegmentoCnaeDTO.class
        );

        // Validate the SegmentoCnae in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSegmentoCnae = segmentoCnaeMapper.toEntity(returnedSegmentoCnaeDTO);
        assertSegmentoCnaeUpdatableFieldsEquals(returnedSegmentoCnae, getPersistedSegmentoCnae(returnedSegmentoCnae));

        insertedSegmentoCnae = returnedSegmentoCnae;
    }

    @Test
    @Transactional
    void createSegmentoCnaeWithExistingId() throws Exception {
        // Create the SegmentoCnae with an existing ID
        segmentoCnae.setId(1L);
        SegmentoCnaeDTO segmentoCnaeDTO = segmentoCnaeMapper.toDto(segmentoCnae);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSegmentoCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(segmentoCnaeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SegmentoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        segmentoCnae.setTipo(null);

        // Create the SegmentoCnae, which fails.
        SegmentoCnaeDTO segmentoCnaeDTO = segmentoCnaeMapper.toDto(segmentoCnae);

        restSegmentoCnaeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(segmentoCnaeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaes() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList
        restSegmentoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segmentoCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSegmentoCnaesWithEagerRelationshipsIsEnabled() throws Exception {
        when(segmentoCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSegmentoCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(segmentoCnaeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSegmentoCnaesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(segmentoCnaeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSegmentoCnaeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(segmentoCnaeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSegmentoCnae() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get the segmentoCnae
        restSegmentoCnaeMockMvc
            .perform(get(ENTITY_API_URL_ID, segmentoCnae.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(segmentoCnae.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.imagem").value(DEFAULT_IMAGEM))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    void getSegmentoCnaesByIdFiltering() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        Long id = segmentoCnae.getId();

        defaultSegmentoCnaeFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSegmentoCnaeFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSegmentoCnaeFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where nome equals to
        defaultSegmentoCnaeFiltering("nome.equals=" + DEFAULT_NOME, "nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where nome in
        defaultSegmentoCnaeFiltering("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME, "nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where nome is not null
        defaultSegmentoCnaeFiltering("nome.specified=true", "nome.specified=false");
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByNomeContainsSomething() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where nome contains
        defaultSegmentoCnaeFiltering("nome.contains=" + DEFAULT_NOME, "nome.contains=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByNomeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where nome does not contain
        defaultSegmentoCnaeFiltering("nome.doesNotContain=" + UPDATED_NOME, "nome.doesNotContain=" + DEFAULT_NOME);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where icon equals to
        defaultSegmentoCnaeFiltering("icon.equals=" + DEFAULT_ICON, "icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByIconIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where icon in
        defaultSegmentoCnaeFiltering("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON, "icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where icon is not null
        defaultSegmentoCnaeFiltering("icon.specified=true", "icon.specified=false");
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByIconContainsSomething() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where icon contains
        defaultSegmentoCnaeFiltering("icon.contains=" + DEFAULT_ICON, "icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByIconNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where icon does not contain
        defaultSegmentoCnaeFiltering("icon.doesNotContain=" + UPDATED_ICON, "icon.doesNotContain=" + DEFAULT_ICON);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByImagemIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where imagem equals to
        defaultSegmentoCnaeFiltering("imagem.equals=" + DEFAULT_IMAGEM, "imagem.equals=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByImagemIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where imagem in
        defaultSegmentoCnaeFiltering("imagem.in=" + DEFAULT_IMAGEM + "," + UPDATED_IMAGEM, "imagem.in=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByImagemIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where imagem is not null
        defaultSegmentoCnaeFiltering("imagem.specified=true", "imagem.specified=false");
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByImagemContainsSomething() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where imagem contains
        defaultSegmentoCnaeFiltering("imagem.contains=" + DEFAULT_IMAGEM, "imagem.contains=" + UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByImagemNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where imagem does not contain
        defaultSegmentoCnaeFiltering("imagem.doesNotContain=" + UPDATED_IMAGEM, "imagem.doesNotContain=" + DEFAULT_IMAGEM);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where tipo equals to
        defaultSegmentoCnaeFiltering("tipo.equals=" + DEFAULT_TIPO, "tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where tipo in
        defaultSegmentoCnaeFiltering("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO, "tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        // Get all the segmentoCnaeList where tipo is not null
        defaultSegmentoCnaeFiltering("tipo.specified=true", "tipo.specified=false");
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesBySubclasseCnaeIsEqualToSomething() throws Exception {
        SubclasseCnae subclasseCnae;
        if (TestUtil.findAll(em, SubclasseCnae.class).isEmpty()) {
            segmentoCnaeRepository.saveAndFlush(segmentoCnae);
            subclasseCnae = SubclasseCnaeResourceIT.createEntity(em);
        } else {
            subclasseCnae = TestUtil.findAll(em, SubclasseCnae.class).get(0);
        }
        em.persist(subclasseCnae);
        em.flush();
        segmentoCnae.addSubclasseCnae(subclasseCnae);
        segmentoCnaeRepository.saveAndFlush(segmentoCnae);
        Long subclasseCnaeId = subclasseCnae.getId();
        // Get all the segmentoCnaeList where subclasseCnae equals to subclasseCnaeId
        defaultSegmentoCnaeShouldBeFound("subclasseCnaeId.equals=" + subclasseCnaeId);

        // Get all the segmentoCnaeList where subclasseCnae equals to (subclasseCnaeId + 1)
        defaultSegmentoCnaeShouldNotBeFound("subclasseCnaeId.equals=" + (subclasseCnaeId + 1));
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByRamoIsEqualToSomething() throws Exception {
        Ramo ramo;
        if (TestUtil.findAll(em, Ramo.class).isEmpty()) {
            segmentoCnaeRepository.saveAndFlush(segmentoCnae);
            ramo = RamoResourceIT.createEntity(em);
        } else {
            ramo = TestUtil.findAll(em, Ramo.class).get(0);
        }
        em.persist(ramo);
        em.flush();
        segmentoCnae.setRamo(ramo);
        segmentoCnaeRepository.saveAndFlush(segmentoCnae);
        Long ramoId = ramo.getId();
        // Get all the segmentoCnaeList where ramo equals to ramoId
        defaultSegmentoCnaeShouldBeFound("ramoId.equals=" + ramoId);

        // Get all the segmentoCnaeList where ramo equals to (ramoId + 1)
        defaultSegmentoCnaeShouldNotBeFound("ramoId.equals=" + (ramoId + 1));
    }

    @Test
    @Transactional
    void getAllSegmentoCnaesByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            segmentoCnaeRepository.saveAndFlush(segmentoCnae);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        segmentoCnae.addEmpresa(empresa);
        segmentoCnaeRepository.saveAndFlush(segmentoCnae);
        Long empresaId = empresa.getId();
        // Get all the segmentoCnaeList where empresa equals to empresaId
        defaultSegmentoCnaeShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the segmentoCnaeList where empresa equals to (empresaId + 1)
        defaultSegmentoCnaeShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    private void defaultSegmentoCnaeFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSegmentoCnaeShouldBeFound(shouldBeFound);
        defaultSegmentoCnaeShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSegmentoCnaeShouldBeFound(String filter) throws Exception {
        restSegmentoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segmentoCnae.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM)))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));

        // Check, that the count call also returns 1
        restSegmentoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSegmentoCnaeShouldNotBeFound(String filter) throws Exception {
        restSegmentoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSegmentoCnaeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSegmentoCnae() throws Exception {
        // Get the segmentoCnae
        restSegmentoCnaeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSegmentoCnae() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the segmentoCnae
        SegmentoCnae updatedSegmentoCnae = segmentoCnaeRepository.findById(segmentoCnae.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSegmentoCnae are not directly saved in db
        em.detach(updatedSegmentoCnae);
        updatedSegmentoCnae
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .icon(UPDATED_ICON)
            .imagem(UPDATED_IMAGEM)
            .tags(UPDATED_TAGS)
            .tipo(UPDATED_TIPO);
        SegmentoCnaeDTO segmentoCnaeDTO = segmentoCnaeMapper.toDto(updatedSegmentoCnae);

        restSegmentoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmentoCnaeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(segmentoCnaeDTO))
            )
            .andExpect(status().isOk());

        // Validate the SegmentoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSegmentoCnaeToMatchAllProperties(updatedSegmentoCnae);
    }

    @Test
    @Transactional
    void putNonExistingSegmentoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmentoCnae.setId(longCount.incrementAndGet());

        // Create the SegmentoCnae
        SegmentoCnaeDTO segmentoCnaeDTO = segmentoCnaeMapper.toDto(segmentoCnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, segmentoCnaeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(segmentoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSegmentoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmentoCnae.setId(longCount.incrementAndGet());

        // Create the SegmentoCnae
        SegmentoCnaeDTO segmentoCnaeDTO = segmentoCnaeMapper.toDto(segmentoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoCnaeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(segmentoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSegmentoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmentoCnae.setId(longCount.incrementAndGet());

        // Create the SegmentoCnae
        SegmentoCnaeDTO segmentoCnaeDTO = segmentoCnaeMapper.toDto(segmentoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoCnaeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(segmentoCnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SegmentoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSegmentoCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the segmentoCnae using partial update
        SegmentoCnae partialUpdatedSegmentoCnae = new SegmentoCnae();
        partialUpdatedSegmentoCnae.setId(segmentoCnae.getId());

        partialUpdatedSegmentoCnae.nome(UPDATED_NOME).imagem(UPDATED_IMAGEM);

        restSegmentoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmentoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSegmentoCnae))
            )
            .andExpect(status().isOk());

        // Validate the SegmentoCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSegmentoCnaeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSegmentoCnae, segmentoCnae),
            getPersistedSegmentoCnae(segmentoCnae)
        );
    }

    @Test
    @Transactional
    void fullUpdateSegmentoCnaeWithPatch() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the segmentoCnae using partial update
        SegmentoCnae partialUpdatedSegmentoCnae = new SegmentoCnae();
        partialUpdatedSegmentoCnae.setId(segmentoCnae.getId());

        partialUpdatedSegmentoCnae
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .icon(UPDATED_ICON)
            .imagem(UPDATED_IMAGEM)
            .tags(UPDATED_TAGS)
            .tipo(UPDATED_TIPO);

        restSegmentoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSegmentoCnae.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSegmentoCnae))
            )
            .andExpect(status().isOk());

        // Validate the SegmentoCnae in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSegmentoCnaeUpdatableFieldsEquals(partialUpdatedSegmentoCnae, getPersistedSegmentoCnae(partialUpdatedSegmentoCnae));
    }

    @Test
    @Transactional
    void patchNonExistingSegmentoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmentoCnae.setId(longCount.incrementAndGet());

        // Create the SegmentoCnae
        SegmentoCnaeDTO segmentoCnaeDTO = segmentoCnaeMapper.toDto(segmentoCnae);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, segmentoCnaeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(segmentoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSegmentoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmentoCnae.setId(longCount.incrementAndGet());

        // Create the SegmentoCnae
        SegmentoCnaeDTO segmentoCnaeDTO = segmentoCnaeMapper.toDto(segmentoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoCnaeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(segmentoCnaeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SegmentoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSegmentoCnae() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        segmentoCnae.setId(longCount.incrementAndGet());

        // Create the SegmentoCnae
        SegmentoCnaeDTO segmentoCnaeDTO = segmentoCnaeMapper.toDto(segmentoCnae);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSegmentoCnaeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(segmentoCnaeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SegmentoCnae in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSegmentoCnae() throws Exception {
        // Initialize the database
        insertedSegmentoCnae = segmentoCnaeRepository.saveAndFlush(segmentoCnae);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the segmentoCnae
        restSegmentoCnaeMockMvc
            .perform(delete(ENTITY_API_URL_ID, segmentoCnae.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return segmentoCnaeRepository.count();
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

    protected SegmentoCnae getPersistedSegmentoCnae(SegmentoCnae segmentoCnae) {
        return segmentoCnaeRepository.findById(segmentoCnae.getId()).orElseThrow();
    }

    protected void assertPersistedSegmentoCnaeToMatchAllProperties(SegmentoCnae expectedSegmentoCnae) {
        assertSegmentoCnaeAllPropertiesEquals(expectedSegmentoCnae, getPersistedSegmentoCnae(expectedSegmentoCnae));
    }

    protected void assertPersistedSegmentoCnaeToMatchUpdatableProperties(SegmentoCnae expectedSegmentoCnae) {
        assertSegmentoCnaeAllUpdatablePropertiesEquals(expectedSegmentoCnae, getPersistedSegmentoCnae(expectedSegmentoCnae));
    }
}
