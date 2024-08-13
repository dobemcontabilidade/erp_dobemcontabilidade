package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModeloAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.EtapaFluxoModelo;
import com.dobemcontabilidade.domain.ServicoContabil;
import com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModelo;
import com.dobemcontabilidade.repository.ServicoContabilEtapaFluxoModeloRepository;
import com.dobemcontabilidade.service.ServicoContabilEtapaFluxoModeloService;
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
 * Integration tests for the {@link ServicoContabilEtapaFluxoModeloResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ServicoContabilEtapaFluxoModeloResourceIT {

    private static final Integer DEFAULT_ORDEM = 1;
    private static final Integer UPDATED_ORDEM = 2;

    private static final Integer DEFAULT_PRAZO = 1;
    private static final Integer UPDATED_PRAZO = 2;

    private static final String ENTITY_API_URL = "/api/servico-contabil-etapa-fluxo-modelos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServicoContabilEtapaFluxoModeloRepository servicoContabilEtapaFluxoModeloRepository;

    @Mock
    private ServicoContabilEtapaFluxoModeloRepository servicoContabilEtapaFluxoModeloRepositoryMock;

    @Mock
    private ServicoContabilEtapaFluxoModeloService servicoContabilEtapaFluxoModeloServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicoContabilEtapaFluxoModeloMockMvc;

    private ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo;

    private ServicoContabilEtapaFluxoModelo insertedServicoContabilEtapaFluxoModelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabilEtapaFluxoModelo createEntity(EntityManager em) {
        ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo = new ServicoContabilEtapaFluxoModelo()
            .ordem(DEFAULT_ORDEM)
            .prazo(DEFAULT_PRAZO);
        // Add required entity
        EtapaFluxoModelo etapaFluxoModelo;
        if (TestUtil.findAll(em, EtapaFluxoModelo.class).isEmpty()) {
            etapaFluxoModelo = EtapaFluxoModeloResourceIT.createEntity(em);
            em.persist(etapaFluxoModelo);
            em.flush();
        } else {
            etapaFluxoModelo = TestUtil.findAll(em, EtapaFluxoModelo.class).get(0);
        }
        servicoContabilEtapaFluxoModelo.setEtapaFluxoModelo(etapaFluxoModelo);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        servicoContabilEtapaFluxoModelo.setServicoContabil(servicoContabil);
        return servicoContabilEtapaFluxoModelo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabilEtapaFluxoModelo createUpdatedEntity(EntityManager em) {
        ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo = new ServicoContabilEtapaFluxoModelo()
            .ordem(UPDATED_ORDEM)
            .prazo(UPDATED_PRAZO);
        // Add required entity
        EtapaFluxoModelo etapaFluxoModelo;
        if (TestUtil.findAll(em, EtapaFluxoModelo.class).isEmpty()) {
            etapaFluxoModelo = EtapaFluxoModeloResourceIT.createUpdatedEntity(em);
            em.persist(etapaFluxoModelo);
            em.flush();
        } else {
            etapaFluxoModelo = TestUtil.findAll(em, EtapaFluxoModelo.class).get(0);
        }
        servicoContabilEtapaFluxoModelo.setEtapaFluxoModelo(etapaFluxoModelo);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        servicoContabilEtapaFluxoModelo.setServicoContabil(servicoContabil);
        return servicoContabilEtapaFluxoModelo;
    }

    @BeforeEach
    public void initTest() {
        servicoContabilEtapaFluxoModelo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedServicoContabilEtapaFluxoModelo != null) {
            servicoContabilEtapaFluxoModeloRepository.delete(insertedServicoContabilEtapaFluxoModelo);
            insertedServicoContabilEtapaFluxoModelo = null;
        }
    }

    @Test
    @Transactional
    void createServicoContabilEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServicoContabilEtapaFluxoModelo
        var returnedServicoContabilEtapaFluxoModelo = om.readValue(
            restServicoContabilEtapaFluxoModeloMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(servicoContabilEtapaFluxoModelo))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServicoContabilEtapaFluxoModelo.class
        );

        // Validate the ServicoContabilEtapaFluxoModelo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServicoContabilEtapaFluxoModeloUpdatableFieldsEquals(
            returnedServicoContabilEtapaFluxoModelo,
            getPersistedServicoContabilEtapaFluxoModelo(returnedServicoContabilEtapaFluxoModelo)
        );

        insertedServicoContabilEtapaFluxoModelo = returnedServicoContabilEtapaFluxoModelo;
    }

    @Test
    @Transactional
    void createServicoContabilEtapaFluxoModeloWithExistingId() throws Exception {
        // Create the ServicoContabilEtapaFluxoModelo with an existing ID
        servicoContabilEtapaFluxoModelo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilEtapaFluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServicoContabilEtapaFluxoModelos() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModeloRepository.saveAndFlush(servicoContabilEtapaFluxoModelo);

        // Get all the servicoContabilEtapaFluxoModeloList
        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicoContabilEtapaFluxoModelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordem").value(hasItem(DEFAULT_ORDEM)))
            .andExpect(jsonPath("$.[*].prazo").value(hasItem(DEFAULT_PRAZO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilEtapaFluxoModelosWithEagerRelationshipsIsEnabled() throws Exception {
        when(servicoContabilEtapaFluxoModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilEtapaFluxoModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(servicoContabilEtapaFluxoModeloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilEtapaFluxoModelosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(servicoContabilEtapaFluxoModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilEtapaFluxoModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(servicoContabilEtapaFluxoModeloRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getServicoContabilEtapaFluxoModelo() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModeloRepository.saveAndFlush(servicoContabilEtapaFluxoModelo);

        // Get the servicoContabilEtapaFluxoModelo
        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(get(ENTITY_API_URL_ID, servicoContabilEtapaFluxoModelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicoContabilEtapaFluxoModelo.getId().intValue()))
            .andExpect(jsonPath("$.ordem").value(DEFAULT_ORDEM))
            .andExpect(jsonPath("$.prazo").value(DEFAULT_PRAZO));
    }

    @Test
    @Transactional
    void getNonExistingServicoContabilEtapaFluxoModelo() throws Exception {
        // Get the servicoContabilEtapaFluxoModelo
        restServicoContabilEtapaFluxoModeloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServicoContabilEtapaFluxoModelo() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModeloRepository.saveAndFlush(servicoContabilEtapaFluxoModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilEtapaFluxoModelo
        ServicoContabilEtapaFluxoModelo updatedServicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModeloRepository
            .findById(servicoContabilEtapaFluxoModelo.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedServicoContabilEtapaFluxoModelo are not directly saved in db
        em.detach(updatedServicoContabilEtapaFluxoModelo);
        updatedServicoContabilEtapaFluxoModelo.ordem(UPDATED_ORDEM).prazo(UPDATED_PRAZO);

        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServicoContabilEtapaFluxoModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServicoContabilEtapaFluxoModelo))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilEtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServicoContabilEtapaFluxoModeloToMatchAllProperties(updatedServicoContabilEtapaFluxoModelo);
    }

    @Test
    @Transactional
    void putNonExistingServicoContabilEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicoContabilEtapaFluxoModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicoContabilEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicoContabilEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilEtapaFluxoModelo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabilEtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicoContabilEtapaFluxoModeloWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModeloRepository.saveAndFlush(servicoContabilEtapaFluxoModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilEtapaFluxoModelo using partial update
        ServicoContabilEtapaFluxoModelo partialUpdatedServicoContabilEtapaFluxoModelo = new ServicoContabilEtapaFluxoModelo();
        partialUpdatedServicoContabilEtapaFluxoModelo.setId(servicoContabilEtapaFluxoModelo.getId());

        partialUpdatedServicoContabilEtapaFluxoModelo.ordem(UPDATED_ORDEM);

        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabilEtapaFluxoModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabilEtapaFluxoModelo))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilEtapaFluxoModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilEtapaFluxoModeloUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServicoContabilEtapaFluxoModelo, servicoContabilEtapaFluxoModelo),
            getPersistedServicoContabilEtapaFluxoModelo(servicoContabilEtapaFluxoModelo)
        );
    }

    @Test
    @Transactional
    void fullUpdateServicoContabilEtapaFluxoModeloWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModeloRepository.saveAndFlush(servicoContabilEtapaFluxoModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilEtapaFluxoModelo using partial update
        ServicoContabilEtapaFluxoModelo partialUpdatedServicoContabilEtapaFluxoModelo = new ServicoContabilEtapaFluxoModelo();
        partialUpdatedServicoContabilEtapaFluxoModelo.setId(servicoContabilEtapaFluxoModelo.getId());

        partialUpdatedServicoContabilEtapaFluxoModelo.ordem(UPDATED_ORDEM).prazo(UPDATED_PRAZO);

        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabilEtapaFluxoModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabilEtapaFluxoModelo))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilEtapaFluxoModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilEtapaFluxoModeloUpdatableFieldsEquals(
            partialUpdatedServicoContabilEtapaFluxoModelo,
            getPersistedServicoContabilEtapaFluxoModelo(partialUpdatedServicoContabilEtapaFluxoModelo)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServicoContabilEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicoContabilEtapaFluxoModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicoContabilEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilEtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicoContabilEtapaFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilEtapaFluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilEtapaFluxoModelo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabilEtapaFluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicoContabilEtapaFluxoModelo() throws Exception {
        // Initialize the database
        insertedServicoContabilEtapaFluxoModelo = servicoContabilEtapaFluxoModeloRepository.saveAndFlush(servicoContabilEtapaFluxoModelo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicoContabilEtapaFluxoModelo
        restServicoContabilEtapaFluxoModeloMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicoContabilEtapaFluxoModelo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return servicoContabilEtapaFluxoModeloRepository.count();
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

    protected ServicoContabilEtapaFluxoModelo getPersistedServicoContabilEtapaFluxoModelo(
        ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo
    ) {
        return servicoContabilEtapaFluxoModeloRepository.findById(servicoContabilEtapaFluxoModelo.getId()).orElseThrow();
    }

    protected void assertPersistedServicoContabilEtapaFluxoModeloToMatchAllProperties(
        ServicoContabilEtapaFluxoModelo expectedServicoContabilEtapaFluxoModelo
    ) {
        assertServicoContabilEtapaFluxoModeloAllPropertiesEquals(
            expectedServicoContabilEtapaFluxoModelo,
            getPersistedServicoContabilEtapaFluxoModelo(expectedServicoContabilEtapaFluxoModelo)
        );
    }

    protected void assertPersistedServicoContabilEtapaFluxoModeloToMatchUpdatableProperties(
        ServicoContabilEtapaFluxoModelo expectedServicoContabilEtapaFluxoModelo
    ) {
        assertServicoContabilEtapaFluxoModeloAllUpdatablePropertiesEquals(
            expectedServicoContabilEtapaFluxoModelo,
            getPersistedServicoContabilEtapaFluxoModelo(expectedServicoContabilEtapaFluxoModelo)
        );
    }
}
