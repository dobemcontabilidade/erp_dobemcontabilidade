package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AreaContabil;
import com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresa;
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.repository.AreaContabilAssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.AreaContabilAssinaturaEmpresaService;
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
 * Integration tests for the {@link AreaContabilAssinaturaEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AreaContabilAssinaturaEmpresaResourceIT {

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    private static final Instant DEFAULT_DATA_ATRIBUICAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ATRIBUICAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_REVOGACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_REVOGACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/area-contabil-assinatura-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AreaContabilAssinaturaEmpresaRepository areaContabilAssinaturaEmpresaRepository;

    @Mock
    private AreaContabilAssinaturaEmpresaRepository areaContabilAssinaturaEmpresaRepositoryMock;

    @Mock
    private AreaContabilAssinaturaEmpresaService areaContabilAssinaturaEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAreaContabilAssinaturaEmpresaMockMvc;

    private AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa;

    private AreaContabilAssinaturaEmpresa insertedAreaContabilAssinaturaEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaContabilAssinaturaEmpresa createEntity(EntityManager em) {
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa = new AreaContabilAssinaturaEmpresa()
            .ativo(DEFAULT_ATIVO)
            .dataAtribuicao(DEFAULT_DATA_ATRIBUICAO)
            .dataRevogacao(DEFAULT_DATA_REVOGACAO);
        // Add required entity
        AreaContabil areaContabil;
        if (TestUtil.findAll(em, AreaContabil.class).isEmpty()) {
            areaContabil = AreaContabilResourceIT.createEntity(em);
            em.persist(areaContabil);
            em.flush();
        } else {
            areaContabil = TestUtil.findAll(em, AreaContabil.class).get(0);
        }
        areaContabilAssinaturaEmpresa.setAreaContabil(areaContabil);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        areaContabilAssinaturaEmpresa.setAssinaturaEmpresa(assinaturaEmpresa);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        areaContabilAssinaturaEmpresa.setContador(contador);
        return areaContabilAssinaturaEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AreaContabilAssinaturaEmpresa createUpdatedEntity(EntityManager em) {
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa = new AreaContabilAssinaturaEmpresa()
            .ativo(UPDATED_ATIVO)
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO)
            .dataRevogacao(UPDATED_DATA_REVOGACAO);
        // Add required entity
        AreaContabil areaContabil;
        if (TestUtil.findAll(em, AreaContabil.class).isEmpty()) {
            areaContabil = AreaContabilResourceIT.createUpdatedEntity(em);
            em.persist(areaContabil);
            em.flush();
        } else {
            areaContabil = TestUtil.findAll(em, AreaContabil.class).get(0);
        }
        areaContabilAssinaturaEmpresa.setAreaContabil(areaContabil);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        areaContabilAssinaturaEmpresa.setAssinaturaEmpresa(assinaturaEmpresa);
        // Add required entity
        Contador contador;
        if (TestUtil.findAll(em, Contador.class).isEmpty()) {
            contador = ContadorResourceIT.createUpdatedEntity(em);
            em.persist(contador);
            em.flush();
        } else {
            contador = TestUtil.findAll(em, Contador.class).get(0);
        }
        areaContabilAssinaturaEmpresa.setContador(contador);
        return areaContabilAssinaturaEmpresa;
    }

    @BeforeEach
    public void initTest() {
        areaContabilAssinaturaEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAreaContabilAssinaturaEmpresa != null) {
            areaContabilAssinaturaEmpresaRepository.delete(insertedAreaContabilAssinaturaEmpresa);
            insertedAreaContabilAssinaturaEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createAreaContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AreaContabilAssinaturaEmpresa
        var returnedAreaContabilAssinaturaEmpresa = om.readValue(
            restAreaContabilAssinaturaEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(areaContabilAssinaturaEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AreaContabilAssinaturaEmpresa.class
        );

        // Validate the AreaContabilAssinaturaEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAreaContabilAssinaturaEmpresaUpdatableFieldsEquals(
            returnedAreaContabilAssinaturaEmpresa,
            getPersistedAreaContabilAssinaturaEmpresa(returnedAreaContabilAssinaturaEmpresa)
        );

        insertedAreaContabilAssinaturaEmpresa = returnedAreaContabilAssinaturaEmpresa;
    }

    @Test
    @Transactional
    void createAreaContabilAssinaturaEmpresaWithExistingId() throws Exception {
        // Create the AreaContabilAssinaturaEmpresa with an existing ID
        areaContabilAssinaturaEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAreaContabilAssinaturaEmpresas() throws Exception {
        // Initialize the database
        insertedAreaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresaRepository.saveAndFlush(areaContabilAssinaturaEmpresa);

        // Get all the areaContabilAssinaturaEmpresaList
        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(areaContabilAssinaturaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataAtribuicao").value(hasItem(DEFAULT_DATA_ATRIBUICAO.toString())))
            .andExpect(jsonPath("$.[*].dataRevogacao").value(hasItem(DEFAULT_DATA_REVOGACAO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaContabilAssinaturaEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(areaContabilAssinaturaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaContabilAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(areaContabilAssinaturaEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAreaContabilAssinaturaEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(areaContabilAssinaturaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAreaContabilAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(areaContabilAssinaturaEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAreaContabilAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedAreaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresaRepository.saveAndFlush(areaContabilAssinaturaEmpresa);

        // Get the areaContabilAssinaturaEmpresa
        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, areaContabilAssinaturaEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(areaContabilAssinaturaEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.dataAtribuicao").value(DEFAULT_DATA_ATRIBUICAO.toString()))
            .andExpect(jsonPath("$.dataRevogacao").value(DEFAULT_DATA_REVOGACAO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAreaContabilAssinaturaEmpresa() throws Exception {
        // Get the areaContabilAssinaturaEmpresa
        restAreaContabilAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAreaContabilAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedAreaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresaRepository.saveAndFlush(areaContabilAssinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabilAssinaturaEmpresa
        AreaContabilAssinaturaEmpresa updatedAreaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresaRepository
            .findById(areaContabilAssinaturaEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAreaContabilAssinaturaEmpresa are not directly saved in db
        em.detach(updatedAreaContabilAssinaturaEmpresa);
        updatedAreaContabilAssinaturaEmpresa
            .ativo(UPDATED_ATIVO)
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO)
            .dataRevogacao(UPDATED_DATA_REVOGACAO);

        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAreaContabilAssinaturaEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAreaContabilAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAreaContabilAssinaturaEmpresaToMatchAllProperties(updatedAreaContabilAssinaturaEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingAreaContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, areaContabilAssinaturaEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAreaContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(areaContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAreaContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(areaContabilAssinaturaEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAreaContabilAssinaturaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAreaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresaRepository.saveAndFlush(areaContabilAssinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabilAssinaturaEmpresa using partial update
        AreaContabilAssinaturaEmpresa partialUpdatedAreaContabilAssinaturaEmpresa = new AreaContabilAssinaturaEmpresa();
        partialUpdatedAreaContabilAssinaturaEmpresa.setId(areaContabilAssinaturaEmpresa.getId());

        partialUpdatedAreaContabilAssinaturaEmpresa.dataAtribuicao(UPDATED_DATA_ATRIBUICAO).dataRevogacao(UPDATED_DATA_REVOGACAO);

        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaContabilAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAreaContabilAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabilAssinaturaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAreaContabilAssinaturaEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAreaContabilAssinaturaEmpresa, areaContabilAssinaturaEmpresa),
            getPersistedAreaContabilAssinaturaEmpresa(areaContabilAssinaturaEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateAreaContabilAssinaturaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedAreaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresaRepository.saveAndFlush(areaContabilAssinaturaEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the areaContabilAssinaturaEmpresa using partial update
        AreaContabilAssinaturaEmpresa partialUpdatedAreaContabilAssinaturaEmpresa = new AreaContabilAssinaturaEmpresa();
        partialUpdatedAreaContabilAssinaturaEmpresa.setId(areaContabilAssinaturaEmpresa.getId());

        partialUpdatedAreaContabilAssinaturaEmpresa
            .ativo(UPDATED_ATIVO)
            .dataAtribuicao(UPDATED_DATA_ATRIBUICAO)
            .dataRevogacao(UPDATED_DATA_REVOGACAO);

        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAreaContabilAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAreaContabilAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the AreaContabilAssinaturaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAreaContabilAssinaturaEmpresaUpdatableFieldsEquals(
            partialUpdatedAreaContabilAssinaturaEmpresa,
            getPersistedAreaContabilAssinaturaEmpresa(partialUpdatedAreaContabilAssinaturaEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAreaContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, areaContabilAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(areaContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAreaContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(areaContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the AreaContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAreaContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        areaContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(areaContabilAssinaturaEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AreaContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAreaContabilAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedAreaContabilAssinaturaEmpresa = areaContabilAssinaturaEmpresaRepository.saveAndFlush(areaContabilAssinaturaEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the areaContabilAssinaturaEmpresa
        restAreaContabilAssinaturaEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, areaContabilAssinaturaEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return areaContabilAssinaturaEmpresaRepository.count();
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

    protected AreaContabilAssinaturaEmpresa getPersistedAreaContabilAssinaturaEmpresa(
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa
    ) {
        return areaContabilAssinaturaEmpresaRepository.findById(areaContabilAssinaturaEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedAreaContabilAssinaturaEmpresaToMatchAllProperties(
        AreaContabilAssinaturaEmpresa expectedAreaContabilAssinaturaEmpresa
    ) {
        assertAreaContabilAssinaturaEmpresaAllPropertiesEquals(
            expectedAreaContabilAssinaturaEmpresa,
            getPersistedAreaContabilAssinaturaEmpresa(expectedAreaContabilAssinaturaEmpresa)
        );
    }

    protected void assertPersistedAreaContabilAssinaturaEmpresaToMatchUpdatableProperties(
        AreaContabilAssinaturaEmpresa expectedAreaContabilAssinaturaEmpresa
    ) {
        assertAreaContabilAssinaturaEmpresaAllUpdatablePropertiesEquals(
            expectedAreaContabilAssinaturaEmpresa,
            getPersistedAreaContabilAssinaturaEmpresa(expectedAreaContabilAssinaturaEmpresa)
        );
    }
}
