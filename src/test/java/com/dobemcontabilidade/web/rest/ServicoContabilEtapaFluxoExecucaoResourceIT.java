package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucaoAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.EtapaFluxoExecucao;
import com.dobemcontabilidade.domain.ServicoContabil;
import com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucao;
import com.dobemcontabilidade.repository.ServicoContabilEtapaFluxoExecucaoRepository;
import com.dobemcontabilidade.service.ServicoContabilEtapaFluxoExecucaoService;
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
 * Integration tests for the {@link ServicoContabilEtapaFluxoExecucaoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ServicoContabilEtapaFluxoExecucaoResourceIT {

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    private static final Boolean DEFAULT_FEITO = false;
    private static final Boolean UPDATED_FEITO = true;

    private static final Integer DEFAULT_PRAZO = 1;
    private static final Integer UPDATED_PRAZO = 2;

    private static final String ENTITY_API_URL = "/api/servico-contabil-etapa-fluxo-execucaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServicoContabilEtapaFluxoExecucaoRepository servicoContabilEtapaFluxoExecucaoRepository;

    @Mock
    private ServicoContabilEtapaFluxoExecucaoRepository servicoContabilEtapaFluxoExecucaoRepositoryMock;

    @Mock
    private ServicoContabilEtapaFluxoExecucaoService servicoContabilEtapaFluxoExecucaoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicoContabilEtapaFluxoExecucaoMockMvc;

    private ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao;

    private ServicoContabilEtapaFluxoExecucao insertedServicoContabilEtapaFluxoExecucao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabilEtapaFluxoExecucao createEntity(EntityManager em) {
        ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao = new ServicoContabilEtapaFluxoExecucao()
            .ordem(DEFAULT_ORDEM)
            .feito(DEFAULT_FEITO)
            .prazo(DEFAULT_PRAZO);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        servicoContabilEtapaFluxoExecucao.setServicoContabil(servicoContabil);
        // Add required entity
        EtapaFluxoExecucao etapaFluxoExecucao;
        if (TestUtil.findAll(em, EtapaFluxoExecucao.class).isEmpty()) {
            etapaFluxoExecucao = EtapaFluxoExecucaoResourceIT.createEntity(em);
            em.persist(etapaFluxoExecucao);
            em.flush();
        } else {
            etapaFluxoExecucao = TestUtil.findAll(em, EtapaFluxoExecucao.class).get(0);
        }
        servicoContabilEtapaFluxoExecucao.setEtapaFluxoExecucao(etapaFluxoExecucao);
        return servicoContabilEtapaFluxoExecucao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabilEtapaFluxoExecucao createUpdatedEntity(EntityManager em) {
        ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao = new ServicoContabilEtapaFluxoExecucao()
            .ordem(UPDATED_ORDEM)
            .feito(UPDATED_FEITO)
            .prazo(UPDATED_PRAZO);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        servicoContabilEtapaFluxoExecucao.setServicoContabil(servicoContabil);
        // Add required entity
        EtapaFluxoExecucao etapaFluxoExecucao;
        if (TestUtil.findAll(em, EtapaFluxoExecucao.class).isEmpty()) {
            etapaFluxoExecucao = EtapaFluxoExecucaoResourceIT.createUpdatedEntity(em);
            em.persist(etapaFluxoExecucao);
            em.flush();
        } else {
            etapaFluxoExecucao = TestUtil.findAll(em, EtapaFluxoExecucao.class).get(0);
        }
        servicoContabilEtapaFluxoExecucao.setEtapaFluxoExecucao(etapaFluxoExecucao);
        return servicoContabilEtapaFluxoExecucao;
    }

    @BeforeEach
    public void initTest() {
        servicoContabilEtapaFluxoExecucao = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedServicoContabilEtapaFluxoExecucao != null) {
            servicoContabilEtapaFluxoExecucaoRepository.delete(insertedServicoContabilEtapaFluxoExecucao);
            insertedServicoContabilEtapaFluxoExecucao = null;
        }
    }

    @Test
    @Transactional
    void createServicoContabilEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServicoContabilEtapaFluxoExecucao
        var returnedServicoContabilEtapaFluxoExecucao = om.readValue(
            restServicoContabilEtapaFluxoExecucaoMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(servicoContabilEtapaFluxoExecucao))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServicoContabilEtapaFluxoExecucao.class
        );

        // Validate the ServicoContabilEtapaFluxoExecucao in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServicoContabilEtapaFluxoExecucaoUpdatableFieldsEquals(
            returnedServicoContabilEtapaFluxoExecucao,
            getPersistedServicoContabilEtapaFluxoExecucao(returnedServicoContabilEtapaFluxoExecucao)
        );

        insertedServicoContabilEtapaFluxoExecucao = returnedServicoContabilEtapaFluxoExecucao;
    }

    @Test
    @Transactional
    void createServicoContabilEtapaFluxoExecucaoWithExistingId() throws Exception {
        // Create the ServicoContabilEtapaFluxoExecucao with an existing ID
        servicoContabilEtapaFluxoExecucao.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServicoContabilEtapaFluxoExecucaos() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucaoRepository.saveAndFlush(
            servicoContabilEtapaFluxoExecucao
        );

        // Get all the servicoContabilEtapaFluxoExecucaoList
        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicoContabilEtapaFluxoExecucao.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].feito").value(hasItem(DEFAULT_FEITO.booleanValue())))
            .andExpect(jsonPath("$.[*].prazo").value(hasItem(DEFAULT_PRAZO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilEtapaFluxoExecucaosWithEagerRelationshipsIsEnabled() throws Exception {
        when(servicoContabilEtapaFluxoExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilEtapaFluxoExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(servicoContabilEtapaFluxoExecucaoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilEtapaFluxoExecucaosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(servicoContabilEtapaFluxoExecucaoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilEtapaFluxoExecucaoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(servicoContabilEtapaFluxoExecucaoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getServicoContabilEtapaFluxoExecucao() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucaoRepository.saveAndFlush(
            servicoContabilEtapaFluxoExecucao
        );

        // Get the servicoContabilEtapaFluxoExecucao
        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(get(ENTITY_API_URL_ID, servicoContabilEtapaFluxoExecucao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicoContabilEtapaFluxoExecucao.getId().intValue()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.feito").value(DEFAULT_FEITO.booleanValue()))
            .andExpect(jsonPath("$.prazo").value(DEFAULT_PRAZO));
    }

    @Test
    @Transactional
    void getNonExistingServicoContabilEtapaFluxoExecucao() throws Exception {
        // Get the servicoContabilEtapaFluxoExecucao
        restServicoContabilEtapaFluxoExecucaoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServicoContabilEtapaFluxoExecucao() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucaoRepository.saveAndFlush(
            servicoContabilEtapaFluxoExecucao
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilEtapaFluxoExecucao
        ServicoContabilEtapaFluxoExecucao updatedServicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucaoRepository
            .findById(servicoContabilEtapaFluxoExecucao.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedServicoContabilEtapaFluxoExecucao are not directly saved in db
        em.detach(updatedServicoContabilEtapaFluxoExecucao);
        updatedServicoContabilEtapaFluxoExecucao.ordem(UPDATED_ORDEM).feito(UPDATED_FEITO).prazo(UPDATED_PRAZO);

        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServicoContabilEtapaFluxoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServicoContabilEtapaFluxoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilEtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServicoContabilEtapaFluxoExecucaoToMatchAllProperties(updatedServicoContabilEtapaFluxoExecucao);
    }

    @Test
    @Transactional
    void putNonExistingServicoContabilEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicoContabilEtapaFluxoExecucao.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicoContabilEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicoContabilEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilEtapaFluxoExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabilEtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicoContabilEtapaFluxoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucaoRepository.saveAndFlush(
            servicoContabilEtapaFluxoExecucao
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilEtapaFluxoExecucao using partial update
        ServicoContabilEtapaFluxoExecucao partialUpdatedServicoContabilEtapaFluxoExecucao = new ServicoContabilEtapaFluxoExecucao();
        partialUpdatedServicoContabilEtapaFluxoExecucao.setId(servicoContabilEtapaFluxoExecucao.getId());

        partialUpdatedServicoContabilEtapaFluxoExecucao.prazo(UPDATED_PRAZO);

        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabilEtapaFluxoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabilEtapaFluxoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilEtapaFluxoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilEtapaFluxoExecucaoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServicoContabilEtapaFluxoExecucao, servicoContabilEtapaFluxoExecucao),
            getPersistedServicoContabilEtapaFluxoExecucao(servicoContabilEtapaFluxoExecucao)
        );
    }

    @Test
    @Transactional
    void fullUpdateServicoContabilEtapaFluxoExecucaoWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucaoRepository.saveAndFlush(
            servicoContabilEtapaFluxoExecucao
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilEtapaFluxoExecucao using partial update
        ServicoContabilEtapaFluxoExecucao partialUpdatedServicoContabilEtapaFluxoExecucao = new ServicoContabilEtapaFluxoExecucao();
        partialUpdatedServicoContabilEtapaFluxoExecucao.setId(servicoContabilEtapaFluxoExecucao.getId());

        partialUpdatedServicoContabilEtapaFluxoExecucao.ordem(UPDATED_ORDEM).feito(UPDATED_FEITO).prazo(UPDATED_PRAZO);

        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabilEtapaFluxoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabilEtapaFluxoExecucao))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilEtapaFluxoExecucao in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilEtapaFluxoExecucaoUpdatableFieldsEquals(
            partialUpdatedServicoContabilEtapaFluxoExecucao,
            getPersistedServicoContabilEtapaFluxoExecucao(partialUpdatedServicoContabilEtapaFluxoExecucao)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServicoContabilEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicoContabilEtapaFluxoExecucao.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicoContabilEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoExecucao))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicoContabilEtapaFluxoExecucao() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoExecucao.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoExecucao))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabilEtapaFluxoExecucao in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicoContabilEtapaFluxoExecucao() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoExecucao = servicoContabilEtapaFluxoExecucaoRepository.saveAndFlush(
            servicoContabilEtapaFluxoExecucao
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicoContabilEtapaFluxoExecucao
        restServicoContabilEtapaFluxoExecucaoMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicoContabilEtapaFluxoExecucao.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return servicoContabilEtapaFluxoExecucaoRepository.count();
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

    protected ServicoContabilEtapaFluxoExecucao getPersistedServicoContabilEtapaFluxoExecucao(
        ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao
    ) {
        return servicoContabilEtapaFluxoExecucaoRepository.findById(servicoContabilEtapaFluxoExecucao.getId()).orElseThrow();
    }

    protected void assertPersistedServicoContabilEtapaFluxoExecucaoToMatchAllProperties(
        ServicoContabilEtapaFluxoExecucao expectedServicoContabilEtapaFluxoExecucao
    ) {
        assertServicoContabilEtapaFluxoExecucaoAllPropertiesEquals(
            expectedServicoContabilEtapaFluxoExecucao,
            getPersistedServicoContabilEtapaFluxoExecucao(expectedServicoContabilEtapaFluxoExecucao)
        );
    }

    protected void assertPersistedServicoContabilEtapaFluxoExecucaoToMatchUpdatableProperties(
        ServicoContabilEtapaFluxoExecucao expectedServicoContabilEtapaFluxoExecucao
    ) {
        assertServicoContabilEtapaFluxoExecucaoAllUpdatablePropertiesEquals(
            expectedServicoContabilEtapaFluxoExecucao,
            getPersistedServicoContabilEtapaFluxoExecucao(expectedServicoContabilEtapaFluxoExecucao)
        );
    }
}
