package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.GrupoAcessoEmpresa;
import com.dobemcontabilidade.repository.GrupoAcessoEmpresaRepository;
import com.dobemcontabilidade.service.GrupoAcessoEmpresaService;
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
 * Integration tests for the {@link GrupoAcessoEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GrupoAcessoEmpresaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/grupo-acesso-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GrupoAcessoEmpresaRepository grupoAcessoEmpresaRepository;

    @Mock
    private GrupoAcessoEmpresaRepository grupoAcessoEmpresaRepositoryMock;

    @Mock
    private GrupoAcessoEmpresaService grupoAcessoEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoAcessoEmpresaMockMvc;

    private GrupoAcessoEmpresa grupoAcessoEmpresa;

    private GrupoAcessoEmpresa insertedGrupoAcessoEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAcessoEmpresa createEntity(EntityManager em) {
        GrupoAcessoEmpresa grupoAcessoEmpresa = new GrupoAcessoEmpresa().nome(DEFAULT_NOME);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        grupoAcessoEmpresa.setAssinaturaEmpresa(assinaturaEmpresa);
        return grupoAcessoEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAcessoEmpresa createUpdatedEntity(EntityManager em) {
        GrupoAcessoEmpresa grupoAcessoEmpresa = new GrupoAcessoEmpresa().nome(UPDATED_NOME);
        // Add required entity
        AssinaturaEmpresa assinaturaEmpresa;
        if (TestUtil.findAll(em, AssinaturaEmpresa.class).isEmpty()) {
            assinaturaEmpresa = AssinaturaEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(assinaturaEmpresa);
            em.flush();
        } else {
            assinaturaEmpresa = TestUtil.findAll(em, AssinaturaEmpresa.class).get(0);
        }
        grupoAcessoEmpresa.setAssinaturaEmpresa(assinaturaEmpresa);
        return grupoAcessoEmpresa;
    }

    @BeforeEach
    public void initTest() {
        grupoAcessoEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGrupoAcessoEmpresa != null) {
            grupoAcessoEmpresaRepository.delete(insertedGrupoAcessoEmpresa);
            insertedGrupoAcessoEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GrupoAcessoEmpresa
        var returnedGrupoAcessoEmpresa = om.readValue(
            restGrupoAcessoEmpresaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoEmpresa)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GrupoAcessoEmpresa.class
        );

        // Validate the GrupoAcessoEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGrupoAcessoEmpresaUpdatableFieldsEquals(
            returnedGrupoAcessoEmpresa,
            getPersistedGrupoAcessoEmpresa(returnedGrupoAcessoEmpresa)
        );

        insertedGrupoAcessoEmpresa = returnedGrupoAcessoEmpresa;
    }

    @Test
    @Transactional
    void createGrupoAcessoEmpresaWithExistingId() throws Exception {
        // Create the GrupoAcessoEmpresa with an existing ID
        grupoAcessoEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoAcessoEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrupoAcessoEmpresas() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresa = grupoAcessoEmpresaRepository.saveAndFlush(grupoAcessoEmpresa);

        // Get all the grupoAcessoEmpresaList
        restGrupoAcessoEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoAcessoEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrupoAcessoEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(grupoAcessoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoAcessoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(grupoAcessoEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrupoAcessoEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(grupoAcessoEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoAcessoEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(grupoAcessoEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGrupoAcessoEmpresa() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresa = grupoAcessoEmpresaRepository.saveAndFlush(grupoAcessoEmpresa);

        // Get the grupoAcessoEmpresa
        restGrupoAcessoEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoAcessoEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoAcessoEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingGrupoAcessoEmpresa() throws Exception {
        // Get the grupoAcessoEmpresa
        restGrupoAcessoEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrupoAcessoEmpresa() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresa = grupoAcessoEmpresaRepository.saveAndFlush(grupoAcessoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoEmpresa
        GrupoAcessoEmpresa updatedGrupoAcessoEmpresa = grupoAcessoEmpresaRepository.findById(grupoAcessoEmpresa.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGrupoAcessoEmpresa are not directly saved in db
        em.detach(updatedGrupoAcessoEmpresa);
        updatedGrupoAcessoEmpresa.nome(UPDATED_NOME);

        restGrupoAcessoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoAcessoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGrupoAcessoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGrupoAcessoEmpresaToMatchAllProperties(updatedGrupoAcessoEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoAcessoEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoAcessoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresa = grupoAcessoEmpresaRepository.saveAndFlush(grupoAcessoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoEmpresa using partial update
        GrupoAcessoEmpresa partialUpdatedGrupoAcessoEmpresa = new GrupoAcessoEmpresa();
        partialUpdatedGrupoAcessoEmpresa.setId(grupoAcessoEmpresa.getId());

        restGrupoAcessoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAcessoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoAcessoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoAcessoEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGrupoAcessoEmpresa, grupoAcessoEmpresa),
            getPersistedGrupoAcessoEmpresa(grupoAcessoEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateGrupoAcessoEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresa = grupoAcessoEmpresaRepository.saveAndFlush(grupoAcessoEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoEmpresa using partial update
        GrupoAcessoEmpresa partialUpdatedGrupoAcessoEmpresa = new GrupoAcessoEmpresa();
        partialUpdatedGrupoAcessoEmpresa.setId(grupoAcessoEmpresa.getId());

        partialUpdatedGrupoAcessoEmpresa.nome(UPDATED_NOME);

        restGrupoAcessoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAcessoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoAcessoEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoAcessoEmpresaUpdatableFieldsEquals(
            partialUpdatedGrupoAcessoEmpresa,
            getPersistedGrupoAcessoEmpresa(partialUpdatedGrupoAcessoEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoAcessoEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoAcessoEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(grupoAcessoEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAcessoEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoAcessoEmpresa() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresa = grupoAcessoEmpresaRepository.saveAndFlush(grupoAcessoEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grupoAcessoEmpresa
        restGrupoAcessoEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoAcessoEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return grupoAcessoEmpresaRepository.count();
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

    protected GrupoAcessoEmpresa getPersistedGrupoAcessoEmpresa(GrupoAcessoEmpresa grupoAcessoEmpresa) {
        return grupoAcessoEmpresaRepository.findById(grupoAcessoEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedGrupoAcessoEmpresaToMatchAllProperties(GrupoAcessoEmpresa expectedGrupoAcessoEmpresa) {
        assertGrupoAcessoEmpresaAllPropertiesEquals(expectedGrupoAcessoEmpresa, getPersistedGrupoAcessoEmpresa(expectedGrupoAcessoEmpresa));
    }

    protected void assertPersistedGrupoAcessoEmpresaToMatchUpdatableProperties(GrupoAcessoEmpresa expectedGrupoAcessoEmpresa) {
        assertGrupoAcessoEmpresaAllUpdatablePropertiesEquals(
            expectedGrupoAcessoEmpresa,
            getPersistedGrupoAcessoEmpresa(expectedGrupoAcessoEmpresa)
        );
    }
}
