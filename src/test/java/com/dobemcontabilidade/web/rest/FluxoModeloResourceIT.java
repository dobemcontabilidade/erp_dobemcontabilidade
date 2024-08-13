package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.FluxoModeloAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.FluxoModelo;
import com.dobemcontabilidade.repository.FluxoModeloRepository;
import com.dobemcontabilidade.service.FluxoModeloService;
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
 * Integration tests for the {@link FluxoModeloResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FluxoModeloResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fluxo-modelos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FluxoModeloRepository fluxoModeloRepository;

    @Mock
    private FluxoModeloRepository fluxoModeloRepositoryMock;

    @Mock
    private FluxoModeloService fluxoModeloServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFluxoModeloMockMvc;

    private FluxoModelo fluxoModelo;

    private FluxoModelo insertedFluxoModelo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FluxoModelo createEntity(EntityManager em) {
        FluxoModelo fluxoModelo = new FluxoModelo().nome(DEFAULT_NOME).descricao(DEFAULT_DESCRICAO);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        fluxoModelo.setCidade(cidade);
        return fluxoModelo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FluxoModelo createUpdatedEntity(EntityManager em) {
        FluxoModelo fluxoModelo = new FluxoModelo().nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);
        // Add required entity
        Cidade cidade;
        if (TestUtil.findAll(em, Cidade.class).isEmpty()) {
            cidade = CidadeResourceIT.createUpdatedEntity(em);
            em.persist(cidade);
            em.flush();
        } else {
            cidade = TestUtil.findAll(em, Cidade.class).get(0);
        }
        fluxoModelo.setCidade(cidade);
        return fluxoModelo;
    }

    @BeforeEach
    public void initTest() {
        fluxoModelo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFluxoModelo != null) {
            fluxoModeloRepository.delete(insertedFluxoModelo);
            insertedFluxoModelo = null;
        }
    }

    @Test
    @Transactional
    void createFluxoModelo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FluxoModelo
        var returnedFluxoModelo = om.readValue(
            restFluxoModeloMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fluxoModelo)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FluxoModelo.class
        );

        // Validate the FluxoModelo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFluxoModeloUpdatableFieldsEquals(returnedFluxoModelo, getPersistedFluxoModelo(returnedFluxoModelo));

        insertedFluxoModelo = returnedFluxoModelo;
    }

    @Test
    @Transactional
    void createFluxoModeloWithExistingId() throws Exception {
        // Create the FluxoModelo with an existing ID
        fluxoModelo.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFluxoModeloMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fluxoModelo)))
            .andExpect(status().isBadRequest());

        // Validate the FluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFluxoModelos() throws Exception {
        // Initialize the database
        insertedFluxoModelo = fluxoModeloRepository.saveAndFlush(fluxoModelo);

        // Get all the fluxoModeloList
        restFluxoModeloMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fluxoModelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFluxoModelosWithEagerRelationshipsIsEnabled() throws Exception {
        when(fluxoModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFluxoModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(fluxoModeloServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFluxoModelosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fluxoModeloServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFluxoModeloMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(fluxoModeloRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFluxoModelo() throws Exception {
        // Initialize the database
        insertedFluxoModelo = fluxoModeloRepository.saveAndFlush(fluxoModelo);

        // Get the fluxoModelo
        restFluxoModeloMockMvc
            .perform(get(ENTITY_API_URL_ID, fluxoModelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fluxoModelo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    void getNonExistingFluxoModelo() throws Exception {
        // Get the fluxoModelo
        restFluxoModeloMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFluxoModelo() throws Exception {
        // Initialize the database
        insertedFluxoModelo = fluxoModeloRepository.saveAndFlush(fluxoModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fluxoModelo
        FluxoModelo updatedFluxoModelo = fluxoModeloRepository.findById(fluxoModelo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFluxoModelo are not directly saved in db
        em.detach(updatedFluxoModelo);
        updatedFluxoModelo.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restFluxoModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFluxoModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFluxoModelo))
            )
            .andExpect(status().isOk());

        // Validate the FluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFluxoModeloToMatchAllProperties(updatedFluxoModelo);
    }

    @Test
    @Transactional
    void putNonExistingFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFluxoModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fluxoModelo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoModeloMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoModeloMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fluxoModelo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFluxoModeloWithPatch() throws Exception {
        // Initialize the database
        insertedFluxoModelo = fluxoModeloRepository.saveAndFlush(fluxoModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fluxoModelo using partial update
        FluxoModelo partialUpdatedFluxoModelo = new FluxoModelo();
        partialUpdatedFluxoModelo.setId(fluxoModelo.getId());

        partialUpdatedFluxoModelo.descricao(UPDATED_DESCRICAO);

        restFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFluxoModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFluxoModelo))
            )
            .andExpect(status().isOk());

        // Validate the FluxoModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFluxoModeloUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFluxoModelo, fluxoModelo),
            getPersistedFluxoModelo(fluxoModelo)
        );
    }

    @Test
    @Transactional
    void fullUpdateFluxoModeloWithPatch() throws Exception {
        // Initialize the database
        insertedFluxoModelo = fluxoModeloRepository.saveAndFlush(fluxoModelo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fluxoModelo using partial update
        FluxoModelo partialUpdatedFluxoModelo = new FluxoModelo();
        partialUpdatedFluxoModelo.setId(fluxoModelo.getId());

        partialUpdatedFluxoModelo.nome(UPDATED_NOME).descricao(UPDATED_DESCRICAO);

        restFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFluxoModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFluxoModelo))
            )
            .andExpect(status().isOk());

        // Validate the FluxoModelo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFluxoModeloUpdatableFieldsEquals(partialUpdatedFluxoModelo, getPersistedFluxoModelo(partialUpdatedFluxoModelo));
    }

    @Test
    @Transactional
    void patchNonExistingFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoModelo.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fluxoModelo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoModeloMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fluxoModelo))
            )
            .andExpect(status().isBadRequest());

        // Validate the FluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFluxoModelo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fluxoModelo.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFluxoModeloMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fluxoModelo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FluxoModelo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFluxoModelo() throws Exception {
        // Initialize the database
        insertedFluxoModelo = fluxoModeloRepository.saveAndFlush(fluxoModelo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fluxoModelo
        restFluxoModeloMockMvc
            .perform(delete(ENTITY_API_URL_ID, fluxoModelo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fluxoModeloRepository.count();
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

    protected FluxoModelo getPersistedFluxoModelo(FluxoModelo fluxoModelo) {
        return fluxoModeloRepository.findById(fluxoModelo.getId()).orElseThrow();
    }

    protected void assertPersistedFluxoModeloToMatchAllProperties(FluxoModelo expectedFluxoModelo) {
        assertFluxoModeloAllPropertiesEquals(expectedFluxoModelo, getPersistedFluxoModelo(expectedFluxoModelo));
    }

    protected void assertPersistedFluxoModeloToMatchUpdatableProperties(FluxoModelo expectedFluxoModelo) {
        assertFluxoModeloAllUpdatablePropertiesEquals(expectedFluxoModelo, getPersistedFluxoModelo(expectedFluxoModelo));
    }
}
