package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.OrdemServicoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.OrdemServico;
import com.dobemcontabilidade.domain.enumeration.StatusDaOSEnum;
import com.dobemcontabilidade.repository.OrdemServicoRepository;
import com.dobemcontabilidade.service.OrdemServicoService;
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
 * Integration tests for the {@link OrdemServicoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OrdemServicoResourceIT {

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    private static final Instant DEFAULT_PRAZO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PRAZO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_CRIACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CRIACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_HORA_CANCELAMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_HORA_CANCELAMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StatusDaOSEnum DEFAULT_STATUS_DA_OS = StatusDaOSEnum.ABERTA;
    private static final StatusDaOSEnum UPDATED_STATUS_DA_OS = StatusDaOSEnum.EMATENDIMENTO;

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ordem-servicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Mock
    private OrdemServicoRepository ordemServicoRepositoryMock;

    @Mock
    private OrdemServicoService ordemServicoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdemServicoMockMvc;

    private OrdemServico ordemServico;

    private OrdemServico insertedOrdemServico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdemServico createEntity(EntityManager em) {
        OrdemServico ordemServico = new OrdemServico()
            .valor(DEFAULT_VALOR)
            .prazo(DEFAULT_PRAZO)
            .dataCriacao(DEFAULT_DATA_CRIACAO)
            .dataHoraCancelamento(DEFAULT_DATA_HORA_CANCELAMENTO)
            .statusDaOS(DEFAULT_STATUS_DA_OS)
            .descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        ordemServico.setEmpresa(empresa);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        ordemServico.setContador(contador);
        return ordemServico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdemServico createUpdatedEntity(EntityManager em) {
        OrdemServico ordemServico = new OrdemServico()
            .valor(UPDATED_VALOR)
            .prazo(UPDATED_PRAZO)
            .dataCriacao(UPDATED_DATA_CRIACAO)
            .dataHoraCancelamento(UPDATED_DATA_HORA_CANCELAMENTO)
            .statusDaOS(UPDATED_STATUS_DA_OS)
            .descricao(UPDATED_DESCRICAO);
        // Add required entity
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createUpdatedEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        ordemServico.setEmpresa(empresa);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        ordemServico.setContador(contador);
        return ordemServico;
    }

    @BeforeEach
    public void initTest() {
        ordemServico = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrdemServico != null) {
            ordemServicoRepository.delete(insertedOrdemServico);
            insertedOrdemServico = null;
        }
    }

    @Test
    @Transactional
    void createOrdemServico() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrdemServico
        var returnedOrdemServico = om.readValue(
            restOrdemServicoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordemServico)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrdemServico.class
        );

        // Validate the OrdemServico in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrdemServicoUpdatableFieldsEquals(returnedOrdemServico, getPersistedOrdemServico(returnedOrdemServico));

        insertedOrdemServico = returnedOrdemServico;
    }

    @Test
    @Transactional
    void createOrdemServicoWithExistingId() throws Exception {
        // Create the OrdemServico with an existing ID
        ordemServico.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdemServicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordemServico)))
            .andExpect(status().isBadRequest());

        // Validate the OrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdemServicos() throws Exception {
        // Initialize the database
        insertedOrdemServico = ordemServicoRepository.saveAndFlush(ordemServico);

        // Get all the ordemServicoList
        restOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordemServico.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].prazo").value(hasItem(DEFAULT_PRAZO.toString())))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())))
            .andExpect(jsonPath("$.[*].dataHoraCancelamento").value(hasItem(DEFAULT_DATA_HORA_CANCELAMENTO.toString())))
            .andExpect(jsonPath("$.[*].statusDaOS").value(hasItem(DEFAULT_STATUS_DA_OS.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrdemServicosWithEagerRelationshipsIsEnabled() throws Exception {
        when(ordemServicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrdemServicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(ordemServicoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllOrdemServicosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(ordemServicoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restOrdemServicoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(ordemServicoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getOrdemServico() throws Exception {
        // Initialize the database
        insertedOrdemServico = ordemServicoRepository.saveAndFlush(ordemServico);

        // Get the ordemServico
        restOrdemServicoMockMvc
            .perform(get(ENTITY_API_URL_ID, ordemServico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordemServico.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.prazo").value(DEFAULT_PRAZO.toString()))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()))
            .andExpect(jsonPath("$.dataHoraCancelamento").value(DEFAULT_DATA_HORA_CANCELAMENTO.toString()))
            .andExpect(jsonPath("$.statusDaOS").value(DEFAULT_STATUS_DA_OS.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingOrdemServico() throws Exception {
        // Get the ordemServico
        restOrdemServicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrdemServico() throws Exception {
        // Initialize the database
        insertedOrdemServico = ordemServicoRepository.saveAndFlush(ordemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordemServico
        OrdemServico updatedOrdemServico = ordemServicoRepository.findById(ordemServico.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrdemServico are not directly saved in db
        em.detach(updatedOrdemServico);
        updatedOrdemServico
            .valor(UPDATED_VALOR)
            .prazo(UPDATED_PRAZO)
            .dataCriacao(UPDATED_DATA_CRIACAO)
            .dataHoraCancelamento(UPDATED_DATA_HORA_CANCELAMENTO)
            .statusDaOS(UPDATED_STATUS_DA_OS)
            .descricao(UPDATED_DESCRICAO);

        restOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrdemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the OrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrdemServicoToMatchAllProperties(updatedOrdemServico);
    }

    @Test
    @Transactional
    void putNonExistingOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordemServico.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ordemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdemServicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ordemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdemServicoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordemServico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedOrdemServico = ordemServicoRepository.saveAndFlush(ordemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordemServico using partial update
        OrdemServico partialUpdatedOrdemServico = new OrdemServico();
        partialUpdatedOrdemServico.setId(ordemServico.getId());

        partialUpdatedOrdemServico.descricao(UPDATED_DESCRICAO);

        restOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the OrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrdemServicoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrdemServico, ordemServico),
            getPersistedOrdemServico(ordemServico)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrdemServicoWithPatch() throws Exception {
        // Initialize the database
        insertedOrdemServico = ordemServicoRepository.saveAndFlush(ordemServico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordemServico using partial update
        OrdemServico partialUpdatedOrdemServico = new OrdemServico();
        partialUpdatedOrdemServico.setId(ordemServico.getId());

        partialUpdatedOrdemServico
            .valor(UPDATED_VALOR)
            .prazo(UPDATED_PRAZO)
            .dataCriacao(UPDATED_DATA_CRIACAO)
            .dataHoraCancelamento(UPDATED_DATA_HORA_CANCELAMENTO)
            .statusDaOS(UPDATED_STATUS_DA_OS)
            .descricao(UPDATED_DESCRICAO);

        restOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrdemServico))
            )
            .andExpect(status().isOk());

        // Validate the OrdemServico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrdemServicoUpdatableFieldsEquals(partialUpdatedOrdemServico, getPersistedOrdemServico(partialUpdatedOrdemServico));
    }

    @Test
    @Transactional
    void patchNonExistingOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordemServico.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordemServico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ordemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdemServicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ordemServico))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdemServico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordemServico.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdemServicoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ordemServico)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdemServico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdemServico() throws Exception {
        // Initialize the database
        insertedOrdemServico = ordemServicoRepository.saveAndFlush(ordemServico);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ordemServico
        restOrdemServicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordemServico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ordemServicoRepository.count();
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

    protected OrdemServico getPersistedOrdemServico(OrdemServico ordemServico) {
        return ordemServicoRepository.findById(ordemServico.getId()).orElseThrow();
    }

    protected void assertPersistedOrdemServicoToMatchAllProperties(OrdemServico expectedOrdemServico) {
        assertOrdemServicoAllPropertiesEquals(expectedOrdemServico, getPersistedOrdemServico(expectedOrdemServico));
    }

    protected void assertPersistedOrdemServicoToMatchUpdatableProperties(OrdemServico expectedOrdemServico) {
        assertOrdemServicoAllUpdatablePropertiesEquals(expectedOrdemServico, getPersistedOrdemServico(expectedOrdemServico));
    }
}
