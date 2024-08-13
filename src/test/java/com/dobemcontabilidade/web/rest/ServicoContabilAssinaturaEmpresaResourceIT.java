package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.ServicoContabil;
import com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa;
import com.dobemcontabilidade.repository.ServicoContabilAssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.ServicoContabilAssinaturaEmpresaService;
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
 * Integration tests for the {@link ServicoContabilAssinaturaEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ServicoContabilAssinaturaEmpresaResourceIT {

    private static final Instant DEFAULT_DATA_LEGAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_LEGAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_ADMIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ADMIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/servico-contabil-assinatura-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServicoContabilAssinaturaEmpresaRepository servicoContabilAssinaturaEmpresaRepository;

    @Mock
    private ServicoContabilAssinaturaEmpresaRepository servicoContabilAssinaturaEmpresaRepositoryMock;

    @Mock
    private ServicoContabilAssinaturaEmpresaService servicoContabilAssinaturaEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicoContabilAssinaturaEmpresaMockMvc;

    private ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa;

    private ServicoContabilAssinaturaEmpresa insertedServicoContabilAssinaturaEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabilAssinaturaEmpresa createEntity(EntityManager em) {
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa = new ServicoContabilAssinaturaEmpresa()
            .dataLegal(DEFAULT_DATA_LEGAL)
            .dataAdmin(DEFAULT_DATA_ADMIN);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        servicoContabilAssinaturaEmpresa.setServicoContabil(servicoContabil);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        servicoContabilAssinaturaEmpresa.setAssinaturaEmpresa(assinaturaEmpresa);
        return servicoContabilAssinaturaEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicoContabilAssinaturaEmpresa createUpdatedEntity(EntityManager em) {
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa = new ServicoContabilAssinaturaEmpresa()
            .dataLegal(UPDATED_DATA_LEGAL)
            .dataAdmin(UPDATED_DATA_ADMIN);
        // Add required entity
        ServicoContabil servicoContabil;
        if (TestUtil.findAll(em, ServicoContabil.class).isEmpty()) {
            servicoContabil = ServicoContabilResourceIT.createUpdatedEntity(em);
            em.persist(servicoContabil);
            em.flush();
        } else {
            servicoContabil = TestUtil.findAll(em, ServicoContabil.class).get(0);
        }
        servicoContabilAssinaturaEmpresa.setServicoContabil(servicoContabil);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        servicoContabilAssinaturaEmpresa.setAssinaturaEmpresa(assinaturaEmpresa);
        return servicoContabilAssinaturaEmpresa;
    }

    @BeforeEach
    public void initTest() {
        servicoContabilAssinaturaEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedServicoContabilAssinaturaEmpresa != null) {
            servicoContabilAssinaturaEmpresaRepository.delete(insertedServicoContabilAssinaturaEmpresa);
            insertedServicoContabilAssinaturaEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createServicoContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ServicoContabilAssinaturaEmpresa
        var returnedServicoContabilAssinaturaEmpresa = om.readValue(
            restServicoContabilAssinaturaEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(servicoContabilAssinaturaEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServicoContabilAssinaturaEmpresa.class
        );

        // Validate the ServicoContabilAssinaturaEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertServicoContabilAssinaturaEmpresaUpdatableFieldsEquals(
            returnedServicoContabilAssinaturaEmpresa,
            getPersistedServicoContabilAssinaturaEmpresa(returnedServicoContabilAssinaturaEmpresa)
        );

        insertedServicoContabilAssinaturaEmpresa = returnedServicoContabilAssinaturaEmpresa;
    }

    @Test
    @Transactional
    void createServicoContabilAssinaturaEmpresaWithExistingId() throws Exception {
        // Create the ServicoContabilAssinaturaEmpresa with an existing ID
        servicoContabilAssinaturaEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDataLegalIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        servicoContabilAssinaturaEmpresa.setDataLegal(null);

        // Create the ServicoContabilAssinaturaEmpresa, which fails.

        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDataAdminIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        servicoContabilAssinaturaEmpresa.setDataAdmin(null);

        // Create the ServicoContabilAssinaturaEmpresa, which fails.

        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServicoContabilAssinaturaEmpresas() throws Exception {
        // Initialize the database
        insertedServicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresaRepository.saveAndFlush(
            servicoContabilAssinaturaEmpresa
        );

        // Get all the servicoContabilAssinaturaEmpresaList
        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicoContabilAssinaturaEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataLegal").value(hasItem(DEFAULT_DATA_LEGAL.toString())))
            .andExpect(jsonPath("$.[*].dataAdmin").value(hasItem(DEFAULT_DATA_ADMIN.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilAssinaturaEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(servicoContabilAssinaturaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(servicoContabilAssinaturaEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicoContabilAssinaturaEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(servicoContabilAssinaturaEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicoContabilAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(servicoContabilAssinaturaEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getServicoContabilAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedServicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresaRepository.saveAndFlush(
            servicoContabilAssinaturaEmpresa
        );

        // Get the servicoContabilAssinaturaEmpresa
        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, servicoContabilAssinaturaEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicoContabilAssinaturaEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.dataLegal").value(DEFAULT_DATA_LEGAL.toString()))
            .andExpect(jsonPath("$.dataAdmin").value(DEFAULT_DATA_ADMIN.toString()));
    }

    @Test
    @Transactional
    void getNonExistingServicoContabilAssinaturaEmpresa() throws Exception {
        // Get the servicoContabilAssinaturaEmpresa
        restServicoContabilAssinaturaEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServicoContabilAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedServicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresaRepository.saveAndFlush(
            servicoContabilAssinaturaEmpresa
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilAssinaturaEmpresa
        ServicoContabilAssinaturaEmpresa updatedServicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresaRepository
            .findById(servicoContabilAssinaturaEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedServicoContabilAssinaturaEmpresa are not directly saved in db
        em.detach(updatedServicoContabilAssinaturaEmpresa);
        updatedServicoContabilAssinaturaEmpresa.dataLegal(UPDATED_DATA_LEGAL).dataAdmin(UPDATED_DATA_ADMIN);

        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedServicoContabilAssinaturaEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedServicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServicoContabilAssinaturaEmpresaToMatchAllProperties(updatedServicoContabilAssinaturaEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingServicoContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicoContabilAssinaturaEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicoContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(servicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicoContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(servicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicoContabilAssinaturaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresaRepository.saveAndFlush(
            servicoContabilAssinaturaEmpresa
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilAssinaturaEmpresa using partial update
        ServicoContabilAssinaturaEmpresa partialUpdatedServicoContabilAssinaturaEmpresa = new ServicoContabilAssinaturaEmpresa();
        partialUpdatedServicoContabilAssinaturaEmpresa.setId(servicoContabilAssinaturaEmpresa.getId());

        partialUpdatedServicoContabilAssinaturaEmpresa.dataAdmin(UPDATED_DATA_ADMIN);

        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabilAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilAssinaturaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilAssinaturaEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedServicoContabilAssinaturaEmpresa, servicoContabilAssinaturaEmpresa),
            getPersistedServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateServicoContabilAssinaturaEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedServicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresaRepository.saveAndFlush(
            servicoContabilAssinaturaEmpresa
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the servicoContabilAssinaturaEmpresa using partial update
        ServicoContabilAssinaturaEmpresa partialUpdatedServicoContabilAssinaturaEmpresa = new ServicoContabilAssinaturaEmpresa();
        partialUpdatedServicoContabilAssinaturaEmpresa.setId(servicoContabilAssinaturaEmpresa.getId());

        partialUpdatedServicoContabilAssinaturaEmpresa.dataLegal(UPDATED_DATA_LEGAL).dataAdmin(UPDATED_DATA_ADMIN);

        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicoContabilAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedServicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the ServicoContabilAssinaturaEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServicoContabilAssinaturaEmpresaUpdatableFieldsEquals(
            partialUpdatedServicoContabilAssinaturaEmpresa,
            getPersistedServicoContabilAssinaturaEmpresa(partialUpdatedServicoContabilAssinaturaEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingServicoContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicoContabilAssinaturaEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicoContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ServicoContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicoContabilAssinaturaEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        servicoContabilAssinaturaEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(servicoContabilAssinaturaEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ServicoContabilAssinaturaEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicoContabilAssinaturaEmpresa() throws Exception {
        // Initialize the database
        insertedServicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresaRepository.saveAndFlush(
            servicoContabilAssinaturaEmpresa
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the servicoContabilAssinaturaEmpresa
        restServicoContabilAssinaturaEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicoContabilAssinaturaEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return servicoContabilAssinaturaEmpresaRepository.count();
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

    protected ServicoContabilAssinaturaEmpresa getPersistedServicoContabilAssinaturaEmpresa(
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa
    ) {
        return servicoContabilAssinaturaEmpresaRepository.findById(servicoContabilAssinaturaEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedServicoContabilAssinaturaEmpresaToMatchAllProperties(
        ServicoContabilAssinaturaEmpresa expectedServicoContabilAssinaturaEmpresa
    ) {
        assertServicoContabilAssinaturaEmpresaAllPropertiesEquals(
            expectedServicoContabilAssinaturaEmpresa,
            getPersistedServicoContabilAssinaturaEmpresa(expectedServicoContabilAssinaturaEmpresa)
        );
    }

    protected void assertPersistedServicoContabilAssinaturaEmpresaToMatchUpdatableProperties(
        ServicoContabilAssinaturaEmpresa expectedServicoContabilAssinaturaEmpresa
    ) {
        assertServicoContabilAssinaturaEmpresaAllUpdatablePropertiesEquals(
            expectedServicoContabilAssinaturaEmpresa,
            getPersistedServicoContabilAssinaturaEmpresa(expectedServicoContabilAssinaturaEmpresa)
        );
    }
}
