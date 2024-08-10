package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.DepartamentoFuncionarioAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.domain.DepartamentoFuncionario;
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.repository.DepartamentoFuncionarioRepository;
import com.dobemcontabilidade.service.DepartamentoFuncionarioService;
import com.dobemcontabilidade.service.dto.DepartamentoFuncionarioDTO;
import com.dobemcontabilidade.service.mapper.DepartamentoFuncionarioMapper;
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
 * Integration tests for the {@link DepartamentoFuncionarioResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DepartamentoFuncionarioResourceIT {

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/departamento-funcionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DepartamentoFuncionarioRepository departamentoFuncionarioRepository;

    @Mock
    private DepartamentoFuncionarioRepository departamentoFuncionarioRepositoryMock;

    @Autowired
    private DepartamentoFuncionarioMapper departamentoFuncionarioMapper;

    @Mock
    private DepartamentoFuncionarioService departamentoFuncionarioServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartamentoFuncionarioMockMvc;

    private DepartamentoFuncionario departamentoFuncionario;

    private DepartamentoFuncionario insertedDepartamentoFuncionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartamentoFuncionario createEntity(EntityManager em) {
        DepartamentoFuncionario departamentoFuncionario = new DepartamentoFuncionario().cargo(DEFAULT_CARGO);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        departamentoFuncionario.setFuncionario(funcionario);
        // Add required entity
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamento = DepartamentoResourceIT.createEntity(em);
            em.persist(departamento);
            em.flush();
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        departamentoFuncionario.setDepartamento(departamento);
        return departamentoFuncionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartamentoFuncionario createUpdatedEntity(EntityManager em) {
        DepartamentoFuncionario departamentoFuncionario = new DepartamentoFuncionario().cargo(UPDATED_CARGO);
        // Add required entity
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createUpdatedEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        departamentoFuncionario.setFuncionario(funcionario);
        // Add required entity
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamento = DepartamentoResourceIT.createUpdatedEntity(em);
            em.persist(departamento);
            em.flush();
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        departamentoFuncionario.setDepartamento(departamento);
        return departamentoFuncionario;
    }

    @BeforeEach
    public void initTest() {
        departamentoFuncionario = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDepartamentoFuncionario != null) {
            departamentoFuncionarioRepository.delete(insertedDepartamentoFuncionario);
            insertedDepartamentoFuncionario = null;
        }
    }

    @Test
    @Transactional
    void createDepartamentoFuncionario() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DepartamentoFuncionario
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = departamentoFuncionarioMapper.toDto(departamentoFuncionario);
        var returnedDepartamentoFuncionarioDTO = om.readValue(
            restDepartamentoFuncionarioMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamentoFuncionarioDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DepartamentoFuncionarioDTO.class
        );

        // Validate the DepartamentoFuncionario in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDepartamentoFuncionario = departamentoFuncionarioMapper.toEntity(returnedDepartamentoFuncionarioDTO);
        assertDepartamentoFuncionarioUpdatableFieldsEquals(
            returnedDepartamentoFuncionario,
            getPersistedDepartamentoFuncionario(returnedDepartamentoFuncionario)
        );

        insertedDepartamentoFuncionario = returnedDepartamentoFuncionario;
    }

    @Test
    @Transactional
    void createDepartamentoFuncionarioWithExistingId() throws Exception {
        // Create the DepartamentoFuncionario with an existing ID
        departamentoFuncionario.setId(1L);
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = departamentoFuncionarioMapper.toDto(departamentoFuncionario);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentoFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamentoFuncionarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCargoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        departamentoFuncionario.setCargo(null);

        // Create the DepartamentoFuncionario, which fails.
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = departamentoFuncionarioMapper.toDto(departamentoFuncionario);

        restDepartamentoFuncionarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamentoFuncionarioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDepartamentoFuncionarios() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        // Get all the departamentoFuncionarioList
        restDepartamentoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamentoFuncionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDepartamentoFuncionariosWithEagerRelationshipsIsEnabled() throws Exception {
        when(departamentoFuncionarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDepartamentoFuncionarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(departamentoFuncionarioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDepartamentoFuncionariosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(departamentoFuncionarioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDepartamentoFuncionarioMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(departamentoFuncionarioRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDepartamentoFuncionario() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        // Get the departamentoFuncionario
        restDepartamentoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, departamentoFuncionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departamentoFuncionario.getId().intValue()))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO));
    }

    @Test
    @Transactional
    void getDepartamentoFuncionariosByIdFiltering() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        Long id = departamentoFuncionario.getId();

        defaultDepartamentoFuncionarioFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDepartamentoFuncionarioFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDepartamentoFuncionarioFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDepartamentoFuncionariosByCargoIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        // Get all the departamentoFuncionarioList where cargo equals to
        defaultDepartamentoFuncionarioFiltering("cargo.equals=" + DEFAULT_CARGO, "cargo.equals=" + UPDATED_CARGO);
    }

    @Test
    @Transactional
    void getAllDepartamentoFuncionariosByCargoIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        // Get all the departamentoFuncionarioList where cargo in
        defaultDepartamentoFuncionarioFiltering("cargo.in=" + DEFAULT_CARGO + "," + UPDATED_CARGO, "cargo.in=" + UPDATED_CARGO);
    }

    @Test
    @Transactional
    void getAllDepartamentoFuncionariosByCargoIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        // Get all the departamentoFuncionarioList where cargo is not null
        defaultDepartamentoFuncionarioFiltering("cargo.specified=true", "cargo.specified=false");
    }

    @Test
    @Transactional
    void getAllDepartamentoFuncionariosByCargoContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        // Get all the departamentoFuncionarioList where cargo contains
        defaultDepartamentoFuncionarioFiltering("cargo.contains=" + DEFAULT_CARGO, "cargo.contains=" + UPDATED_CARGO);
    }

    @Test
    @Transactional
    void getAllDepartamentoFuncionariosByCargoNotContainsSomething() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        // Get all the departamentoFuncionarioList where cargo does not contain
        defaultDepartamentoFuncionarioFiltering("cargo.doesNotContain=" + UPDATED_CARGO, "cargo.doesNotContain=" + DEFAULT_CARGO);
    }

    @Test
    @Transactional
    void getAllDepartamentoFuncionariosByFuncionarioIsEqualToSomething() throws Exception {
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);
            funcionario = FuncionarioResourceIT.createEntity(em);
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        em.persist(funcionario);
        em.flush();
        departamentoFuncionario.setFuncionario(funcionario);
        departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);
        Long funcionarioId = funcionario.getId();
        // Get all the departamentoFuncionarioList where funcionario equals to funcionarioId
        defaultDepartamentoFuncionarioShouldBeFound("funcionarioId.equals=" + funcionarioId);

        // Get all the departamentoFuncionarioList where funcionario equals to (funcionarioId + 1)
        defaultDepartamentoFuncionarioShouldNotBeFound("funcionarioId.equals=" + (funcionarioId + 1));
    }

    @Test
    @Transactional
    void getAllDepartamentoFuncionariosByDepartamentoIsEqualToSomething() throws Exception {
        Departamento departamento;
        if (TestUtil.findAll(em, Departamento.class).isEmpty()) {
            departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);
            departamento = DepartamentoResourceIT.createEntity(em);
        } else {
            departamento = TestUtil.findAll(em, Departamento.class).get(0);
        }
        em.persist(departamento);
        em.flush();
        departamentoFuncionario.setDepartamento(departamento);
        departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);
        Long departamentoId = departamento.getId();
        // Get all the departamentoFuncionarioList where departamento equals to departamentoId
        defaultDepartamentoFuncionarioShouldBeFound("departamentoId.equals=" + departamentoId);

        // Get all the departamentoFuncionarioList where departamento equals to (departamentoId + 1)
        defaultDepartamentoFuncionarioShouldNotBeFound("departamentoId.equals=" + (departamentoId + 1));
    }

    private void defaultDepartamentoFuncionarioFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDepartamentoFuncionarioShouldBeFound(shouldBeFound);
        defaultDepartamentoFuncionarioShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartamentoFuncionarioShouldBeFound(String filter) throws Exception {
        restDepartamentoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamentoFuncionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)));

        // Check, that the count call also returns 1
        restDepartamentoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepartamentoFuncionarioShouldNotBeFound(String filter) throws Exception {
        restDepartamentoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartamentoFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDepartamentoFuncionario() throws Exception {
        // Get the departamentoFuncionario
        restDepartamentoFuncionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDepartamentoFuncionario() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamentoFuncionario
        DepartamentoFuncionario updatedDepartamentoFuncionario = departamentoFuncionarioRepository
            .findById(departamentoFuncionario.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedDepartamentoFuncionario are not directly saved in db
        em.detach(updatedDepartamentoFuncionario);
        updatedDepartamentoFuncionario.cargo(UPDATED_CARGO);
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = departamentoFuncionarioMapper.toDto(updatedDepartamentoFuncionario);

        restDepartamentoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departamentoFuncionarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamentoFuncionarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the DepartamentoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDepartamentoFuncionarioToMatchAllProperties(updatedDepartamentoFuncionario);
    }

    @Test
    @Transactional
    void putNonExistingDepartamentoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoFuncionario.setId(longCount.incrementAndGet());

        // Create the DepartamentoFuncionario
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = departamentoFuncionarioMapper.toDto(departamentoFuncionario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, departamentoFuncionarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamentoFuncionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDepartamentoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoFuncionario.setId(longCount.incrementAndGet());

        // Create the DepartamentoFuncionario
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = departamentoFuncionarioMapper.toDto(departamentoFuncionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(departamentoFuncionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDepartamentoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoFuncionario.setId(longCount.incrementAndGet());

        // Create the DepartamentoFuncionario
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = departamentoFuncionarioMapper.toDto(departamentoFuncionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoFuncionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(departamentoFuncionarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DepartamentoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDepartamentoFuncionarioWithPatch() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamentoFuncionario using partial update
        DepartamentoFuncionario partialUpdatedDepartamentoFuncionario = new DepartamentoFuncionario();
        partialUpdatedDepartamentoFuncionario.setId(departamentoFuncionario.getId());

        partialUpdatedDepartamentoFuncionario.cargo(UPDATED_CARGO);

        restDepartamentoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamentoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartamentoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the DepartamentoFuncionario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartamentoFuncionarioUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDepartamentoFuncionario, departamentoFuncionario),
            getPersistedDepartamentoFuncionario(departamentoFuncionario)
        );
    }

    @Test
    @Transactional
    void fullUpdateDepartamentoFuncionarioWithPatch() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the departamentoFuncionario using partial update
        DepartamentoFuncionario partialUpdatedDepartamentoFuncionario = new DepartamentoFuncionario();
        partialUpdatedDepartamentoFuncionario.setId(departamentoFuncionario.getId());

        partialUpdatedDepartamentoFuncionario.cargo(UPDATED_CARGO);

        restDepartamentoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDepartamentoFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDepartamentoFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the DepartamentoFuncionario in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepartamentoFuncionarioUpdatableFieldsEquals(
            partialUpdatedDepartamentoFuncionario,
            getPersistedDepartamentoFuncionario(partialUpdatedDepartamentoFuncionario)
        );
    }

    @Test
    @Transactional
    void patchNonExistingDepartamentoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoFuncionario.setId(longCount.incrementAndGet());

        // Create the DepartamentoFuncionario
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = departamentoFuncionarioMapper.toDto(departamentoFuncionario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, departamentoFuncionarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departamentoFuncionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDepartamentoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoFuncionario.setId(longCount.incrementAndGet());

        // Create the DepartamentoFuncionario
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = departamentoFuncionarioMapper.toDto(departamentoFuncionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(departamentoFuncionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DepartamentoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDepartamentoFuncionario() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        departamentoFuncionario.setId(longCount.incrementAndGet());

        // Create the DepartamentoFuncionario
        DepartamentoFuncionarioDTO departamentoFuncionarioDTO = departamentoFuncionarioMapper.toDto(departamentoFuncionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDepartamentoFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(departamentoFuncionarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DepartamentoFuncionario in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDepartamentoFuncionario() throws Exception {
        // Initialize the database
        insertedDepartamentoFuncionario = departamentoFuncionarioRepository.saveAndFlush(departamentoFuncionario);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the departamentoFuncionario
        restDepartamentoFuncionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, departamentoFuncionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return departamentoFuncionarioRepository.count();
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

    protected DepartamentoFuncionario getPersistedDepartamentoFuncionario(DepartamentoFuncionario departamentoFuncionario) {
        return departamentoFuncionarioRepository.findById(departamentoFuncionario.getId()).orElseThrow();
    }

    protected void assertPersistedDepartamentoFuncionarioToMatchAllProperties(DepartamentoFuncionario expectedDepartamentoFuncionario) {
        assertDepartamentoFuncionarioAllPropertiesEquals(
            expectedDepartamentoFuncionario,
            getPersistedDepartamentoFuncionario(expectedDepartamentoFuncionario)
        );
    }

    protected void assertPersistedDepartamentoFuncionarioToMatchUpdatableProperties(
        DepartamentoFuncionario expectedDepartamentoFuncionario
    ) {
        assertDepartamentoFuncionarioAllUpdatablePropertiesEquals(
            expectedDepartamentoFuncionario,
            getPersistedDepartamentoFuncionario(expectedDepartamentoFuncionario)
        );
    }
}
