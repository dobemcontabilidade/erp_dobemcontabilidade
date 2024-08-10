package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.TarefaEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Tarefa;
import com.dobemcontabilidade.domain.TarefaEmpresa;
import com.dobemcontabilidade.repository.TarefaEmpresaRepository;
import com.dobemcontabilidade.service.TarefaEmpresaService;
import com.dobemcontabilidade.service.dto.TarefaEmpresaDTO;
import com.dobemcontabilidade.service.mapper.TarefaEmpresaMapper;
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
 * Integration tests for the {@link TarefaEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TarefaEmpresaResourceIT {

    private static final Instant DEFAULT_DATA_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tarefa-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TarefaEmpresaRepository tarefaEmpresaRepository;

    @Mock
    private TarefaEmpresaRepository tarefaEmpresaRepositoryMock;

    @Autowired
    private TarefaEmpresaMapper tarefaEmpresaMapper;

    @Mock
    private TarefaEmpresaService tarefaEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTarefaEmpresaMockMvc;

    private TarefaEmpresa tarefaEmpresa;

    private TarefaEmpresa insertedTarefaEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaEmpresa createEntity(EntityManager em) {
        TarefaEmpresa tarefaEmpresa = new TarefaEmpresa().dataHora(DEFAULT_DATA_HORA);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        tarefaEmpresa.setEmpresa(empresa);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        tarefaEmpresa.setContador(contador);
        // Add required entity
        Tarefa tarefa;
        if (TestUtil.findAll(em, Tarefa.class).isEmpty()) {
            tarefa = TarefaResourceIT.createEntity(em);
            em.persist(tarefa);
            em.flush();
        } else {
            tarefa = TestUtil.findAll(em, Tarefa.class).get(0);
        }
        tarefaEmpresa.setTarefa(tarefa);
        return tarefaEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarefaEmpresa createUpdatedEntity(EntityManager em) {
        TarefaEmpresa tarefaEmpresa = new TarefaEmpresa().dataHora(UPDATED_DATA_HORA);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        tarefaEmpresa.setEmpresa(empresa);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        tarefaEmpresa.setContador(contador);
        // Add required entity
        Tarefa tarefa;
        if (TestUtil.findAll(em, Tarefa.class).isEmpty()) {
            tarefa = TarefaResourceIT.createUpdatedEntity(em);
            em.persist(tarefa);
            em.flush();
        } else {
            tarefa = TestUtil.findAll(em, Tarefa.class).get(0);
        }
        tarefaEmpresa.setTarefa(tarefa);
        return tarefaEmpresa;
    }

    @BeforeEach
    public void initTest() {
        tarefaEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTarefaEmpresa != null) {
            tarefaEmpresaRepository.delete(insertedTarefaEmpresa);
            insertedTarefaEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createTarefaEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TarefaEmpresa
        TarefaEmpresaDTO tarefaEmpresaDTO = tarefaEmpresaMapper.toDto(tarefaEmpresa);
        var returnedTarefaEmpresaDTO = om.readValue(
            restTarefaEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaEmpresaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TarefaEmpresaDTO.class
        );

        // Validate the TarefaEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTarefaEmpresa = tarefaEmpresaMapper.toEntity(returnedTarefaEmpresaDTO);
        assertTarefaEmpresaUpdatableFieldsEquals(returnedTarefaEmpresa, getPersistedTarefaEmpresa(returnedTarefaEmpresa));

        insertedTarefaEmpresa = returnedTarefaEmpresa;
    }

    @Test
    @Transactional
    void createTarefaEmpresaWithExistingId() throws Exception {
        // Create the TarefaEmpresa with an existing ID
        tarefaEmpresa.setId(1L);
        TarefaEmpresaDTO tarefaEmpresaDTO = tarefaEmpresaMapper.toDto(tarefaEmpresa);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarefaEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaEmpresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TarefaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTarefaEmpresas() throws Exception {
        // Initialize the database
        insertedTarefaEmpresa = tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);

        // Get all the tarefaEmpresaList
        restTarefaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTarefaEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(tarefaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTarefaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tarefaEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTarefaEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tarefaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTarefaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tarefaEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTarefaEmpresa() throws Exception {
        // Initialize the database
        insertedTarefaEmpresa = tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);

        // Get the tarefaEmpresa
        restTarefaEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, tarefaEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarefaEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.dataHora").value(DEFAULT_DATA_HORA.toString()));
    }

    @Test
    @Transactional
    void getTarefaEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        insertedTarefaEmpresa = tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);

        Long id = tarefaEmpresa.getId();

        defaultTarefaEmpresaFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTarefaEmpresaFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTarefaEmpresaFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTarefaEmpresasByDataHoraIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedTarefaEmpresa = tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);

        // Get all the tarefaEmpresaList where dataHora equals to
        defaultTarefaEmpresaFiltering("dataHora.equals=" + DEFAULT_DATA_HORA, "dataHora.equals=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllTarefaEmpresasByDataHoraIsInShouldWork() throws Exception {
        // Initialize the database
        insertedTarefaEmpresa = tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);

        // Get all the tarefaEmpresaList where dataHora in
        defaultTarefaEmpresaFiltering("dataHora.in=" + DEFAULT_DATA_HORA + "," + UPDATED_DATA_HORA, "dataHora.in=" + UPDATED_DATA_HORA);
    }

    @Test
    @Transactional
    void getAllTarefaEmpresasByDataHoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedTarefaEmpresa = tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);

        // Get all the tarefaEmpresaList where dataHora is not null
        defaultTarefaEmpresaFiltering("dataHora.specified=true", "dataHora.specified=false");
    }

    @Test
    @Transactional
    void getAllTarefaEmpresasByEmpresaIsEqualToSomething() throws Exception {
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);
            empresa = EmpresaResourceIT.createEntity(em);
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        tarefaEmpresa.setEmpresa(empresa);
        tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);
        Long empresaId = empresa.getId();
        // Get all the tarefaEmpresaList where empresa equals to empresaId
        defaultTarefaEmpresaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the tarefaEmpresaList where empresa equals to (empresaId + 1)
        defaultTarefaEmpresaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllTarefaEmpresasByContadorIsEqualToSomething() throws Exception {
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);
            contador = ContadorResourceIT.createEntity(em);
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        em.persist(contador);
        em.flush();
        tarefaEmpresa.setContador(contador);
        tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);
        Long contadorId = contador.getId();
        // Get all the tarefaEmpresaList where contador equals to contadorId
        defaultTarefaEmpresaShouldBeFound("contadorId.equals=" + contadorId);

        // Get all the tarefaEmpresaList where contador equals to (contadorId + 1)
        defaultTarefaEmpresaShouldNotBeFound("contadorId.equals=" + (contadorId + 1));
    }

    @Test
    @Transactional
    void getAllTarefaEmpresasByTarefaIsEqualToSomething() throws Exception {
        Tarefa tarefa;
        if (TestUtil.findAll(em, Tarefa.class).isEmpty()) {
            tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);
            tarefa = TarefaResourceIT.createEntity(em);
        } else {
            tarefa = TestUtil.findAll(em, Tarefa.class).get(0);
        }
        em.persist(tarefa);
        em.flush();
        tarefaEmpresa.setTarefa(tarefa);
        tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);
        Long tarefaId = tarefa.getId();
        // Get all the tarefaEmpresaList where tarefa equals to tarefaId
        defaultTarefaEmpresaShouldBeFound("tarefaId.equals=" + tarefaId);

        // Get all the tarefaEmpresaList where tarefa equals to (tarefaId + 1)
        defaultTarefaEmpresaShouldNotBeFound("tarefaId.equals=" + (tarefaId + 1));
    }

    private void defaultTarefaEmpresaFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultTarefaEmpresaShouldBeFound(shouldBeFound);
        defaultTarefaEmpresaShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTarefaEmpresaShouldBeFound(String filter) throws Exception {
        restTarefaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarefaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataHora").value(hasItem(DEFAULT_DATA_HORA.toString())));

        // Check, that the count call also returns 1
        restTarefaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTarefaEmpresaShouldNotBeFound(String filter) throws Exception {
        restTarefaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTarefaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTarefaEmpresa() throws Exception {
        // Get the tarefaEmpresa
        restTarefaEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTarefaEmpresa() throws Exception {
        // Initialize the database
        insertedTarefaEmpresa = tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaEmpresa
        TarefaEmpresa updatedTarefaEmpresa = tarefaEmpresaRepository.findById(tarefaEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTarefaEmpresa are not directly saved in db
        em.detach(updatedTarefaEmpresa);
        updatedTarefaEmpresa.dataHora(UPDATED_DATA_HORA);
        TarefaEmpresaDTO tarefaEmpresaDTO = tarefaEmpresaMapper.toDto(updatedTarefaEmpresa);

        restTarefaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarefaEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaEmpresaDTO))
            )
            .andExpect(status().isOk());

        // Validate the TarefaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTarefaEmpresaToMatchAllProperties(updatedTarefaEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingTarefaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresa.setId(longCount.incrementAndGet());

        // Create the TarefaEmpresa
        TarefaEmpresaDTO tarefaEmpresaDTO = tarefaEmpresaMapper.toDto(tarefaEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tarefaEmpresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTarefaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresa.setId(longCount.incrementAndGet());

        // Create the TarefaEmpresa
        TarefaEmpresaDTO tarefaEmpresaDTO = tarefaEmpresaMapper.toDto(tarefaEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tarefaEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTarefaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresa.setId(longCount.incrementAndGet());

        // Create the TarefaEmpresa
        TarefaEmpresaDTO tarefaEmpresaDTO = tarefaEmpresaMapper.toDto(tarefaEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tarefaEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTarefaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaEmpresa = tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaEmpresa using partial update
        TarefaEmpresa partialUpdatedTarefaEmpresa = new TarefaEmpresa();
        partialUpdatedTarefaEmpresa.setId(tarefaEmpresa.getId());

        partialUpdatedTarefaEmpresa.dataHora(UPDATED_DATA_HORA);

        restTarefaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the TarefaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTarefaEmpresa, tarefaEmpresa),
            getPersistedTarefaEmpresa(tarefaEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateTarefaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedTarefaEmpresa = tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tarefaEmpresa using partial update
        TarefaEmpresa partialUpdatedTarefaEmpresa = new TarefaEmpresa();
        partialUpdatedTarefaEmpresa.setId(tarefaEmpresa.getId());

        partialUpdatedTarefaEmpresa.dataHora(UPDATED_DATA_HORA);

        restTarefaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTarefaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTarefaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the TarefaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTarefaEmpresaUpdatableFieldsEquals(partialUpdatedTarefaEmpresa, getPersistedTarefaEmpresa(partialUpdatedTarefaEmpresa));
    }

    @Test
    @Transactional
    void patchNonExistingTarefaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresa.setId(longCount.incrementAndGet());

        // Create the TarefaEmpresa
        TarefaEmpresaDTO tarefaEmpresaDTO = tarefaEmpresaMapper.toDto(tarefaEmpresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarefaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tarefaEmpresaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTarefaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresa.setId(longCount.incrementAndGet());

        // Create the TarefaEmpresa
        TarefaEmpresaDTO tarefaEmpresaDTO = tarefaEmpresaMapper.toDto(tarefaEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tarefaEmpresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TarefaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTarefaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tarefaEmpresa.setId(longCount.incrementAndGet());

        // Create the TarefaEmpresa
        TarefaEmpresaDTO tarefaEmpresaDTO = tarefaEmpresaMapper.toDto(tarefaEmpresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTarefaEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tarefaEmpresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TarefaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTarefaEmpresa() throws Exception {
        // Initialize the database
        insertedTarefaEmpresa = tarefaEmpresaRepository.saveAndFlush(tarefaEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tarefaEmpresa
        restTarefaEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tarefaEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tarefaEmpresaRepository.count();
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

    protected TarefaEmpresa getPersistedTarefaEmpresa(TarefaEmpresa tarefaEmpresa) {
        return tarefaEmpresaRepository.findById(tarefaEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedTarefaEmpresaToMatchAllProperties(TarefaEmpresa expectedTarefaEmpresa) {
        assertTarefaEmpresaAllPropertiesEquals(expectedTarefaEmpresa, getPersistedTarefaEmpresa(expectedTarefaEmpresa));
    }

    protected void assertPersistedTarefaEmpresaToMatchUpdatableProperties(TarefaEmpresa expectedTarefaEmpresa) {
        assertTarefaEmpresaAllUpdatablePropertiesEquals(expectedTarefaEmpresa, getPersistedTarefaEmpresa(expectedTarefaEmpresa));
    }
}
