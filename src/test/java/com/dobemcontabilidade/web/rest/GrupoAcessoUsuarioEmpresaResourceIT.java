package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresaAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.GrupoAcessoEmpresa;
import com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresa;
import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.repository.GrupoAcessoUsuarioEmpresaRepository;
import com.dobemcontabilidade.service.GrupoAcessoUsuarioEmpresaService;
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
 * Integration tests for the {@link GrupoAcessoUsuarioEmpresaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GrupoAcessoUsuarioEmpresaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_EXPIRACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EXPIRACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ILIMITADO = false;
    private static final Boolean UPDATED_ILIMITADO = true;

    private static final Boolean DEFAULT_DESABILITAR = false;
    private static final Boolean UPDATED_DESABILITAR = true;

    private static final String ENTITY_API_URL = "/api/grupo-acesso-usuario-empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GrupoAcessoUsuarioEmpresaRepository grupoAcessoUsuarioEmpresaRepository;

    @Mock
    private GrupoAcessoUsuarioEmpresaRepository grupoAcessoUsuarioEmpresaRepositoryMock;

    @Mock
    private GrupoAcessoUsuarioEmpresaService grupoAcessoUsuarioEmpresaServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoAcessoUsuarioEmpresaMockMvc;

    private GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa;

    private GrupoAcessoUsuarioEmpresa insertedGrupoAcessoUsuarioEmpresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAcessoUsuarioEmpresa createEntity(EntityManager em) {
        GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa = new GrupoAcessoUsuarioEmpresa()
            .nome(DEFAULT_NOME)
            .dataExpiracao(DEFAULT_DATA_EXPIRACAO)
            .ilimitado(DEFAULT_ILIMITADO)
            .desabilitar(DEFAULT_DESABILITAR);
        // Add required entity
        GrupoAcessoEmpresa grupoAcessoEmpresa;
        if (TestUtil.findAll(em, GrupoAcessoEmpresa.class).isEmpty()) {
            grupoAcessoEmpresa = GrupoAcessoEmpresaResourceIT.createEntity(em);
            em.persist(grupoAcessoEmpresa);
            em.flush();
        } else {
            grupoAcessoEmpresa = TestUtil.findAll(em, GrupoAcessoEmpresa.class).get(0);
        }
        grupoAcessoUsuarioEmpresa.setGrupoAcessoEmpresa(grupoAcessoEmpresa);
        // Add required entity
        UsuarioEmpresa usuarioEmpresa;
        if (TestUtil.findAll(em, UsuarioEmpresa.class).isEmpty()) {
            usuarioEmpresa = UsuarioEmpresaResourceIT.createEntity(em);
            em.persist(usuarioEmpresa);
            em.flush();
        } else {
            usuarioEmpresa = TestUtil.findAll(em, UsuarioEmpresa.class).get(0);
        }
        grupoAcessoUsuarioEmpresa.setUsuarioEmpresa(usuarioEmpresa);
        return grupoAcessoUsuarioEmpresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAcessoUsuarioEmpresa createUpdatedEntity(EntityManager em) {
        GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa = new GrupoAcessoUsuarioEmpresa()
            .nome(UPDATED_NOME)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);
        // Add required entity
        GrupoAcessoEmpresa grupoAcessoEmpresa;
        if (TestUtil.findAll(em, GrupoAcessoEmpresa.class).isEmpty()) {
            grupoAcessoEmpresa = GrupoAcessoEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(grupoAcessoEmpresa);
            em.flush();
        } else {
            grupoAcessoEmpresa = TestUtil.findAll(em, GrupoAcessoEmpresa.class).get(0);
        }
        grupoAcessoUsuarioEmpresa.setGrupoAcessoEmpresa(grupoAcessoEmpresa);
        // Add required entity
        UsuarioEmpresa usuarioEmpresa;
        if (TestUtil.findAll(em, UsuarioEmpresa.class).isEmpty()) {
            usuarioEmpresa = UsuarioEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(usuarioEmpresa);
            em.flush();
        } else {
            usuarioEmpresa = TestUtil.findAll(em, UsuarioEmpresa.class).get(0);
        }
        grupoAcessoUsuarioEmpresa.setUsuarioEmpresa(usuarioEmpresa);
        return grupoAcessoUsuarioEmpresa;
    }

    @BeforeEach
    public void initTest() {
        grupoAcessoUsuarioEmpresa = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGrupoAcessoUsuarioEmpresa != null) {
            grupoAcessoUsuarioEmpresaRepository.delete(insertedGrupoAcessoUsuarioEmpresa);
            insertedGrupoAcessoUsuarioEmpresa = null;
        }
    }

    @Test
    @Transactional
    void createGrupoAcessoUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GrupoAcessoUsuarioEmpresa
        var returnedGrupoAcessoUsuarioEmpresa = om.readValue(
            restGrupoAcessoUsuarioEmpresaMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoUsuarioEmpresa))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GrupoAcessoUsuarioEmpresa.class
        );

        // Validate the GrupoAcessoUsuarioEmpresa in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGrupoAcessoUsuarioEmpresaUpdatableFieldsEquals(
            returnedGrupoAcessoUsuarioEmpresa,
            getPersistedGrupoAcessoUsuarioEmpresa(returnedGrupoAcessoUsuarioEmpresa)
        );

        insertedGrupoAcessoUsuarioEmpresa = returnedGrupoAcessoUsuarioEmpresa;
    }

    @Test
    @Transactional
    void createGrupoAcessoUsuarioEmpresaWithExistingId() throws Exception {
        // Create the GrupoAcessoUsuarioEmpresa with an existing ID
        grupoAcessoUsuarioEmpresa.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoUsuarioEmpresa)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoUsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrupoAcessoUsuarioEmpresas() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresaRepository.saveAndFlush(grupoAcessoUsuarioEmpresa);

        // Get all the grupoAcessoUsuarioEmpresaList
        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoAcessoUsuarioEmpresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataExpiracao").value(hasItem(DEFAULT_DATA_EXPIRACAO.toString())))
            .andExpect(jsonPath("$.[*].ilimitado").value(hasItem(DEFAULT_ILIMITADO.booleanValue())))
            .andExpect(jsonPath("$.[*].desabilitar").value(hasItem(DEFAULT_DESABILITAR.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrupoAcessoUsuarioEmpresasWithEagerRelationshipsIsEnabled() throws Exception {
        when(grupoAcessoUsuarioEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoAcessoUsuarioEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(grupoAcessoUsuarioEmpresaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrupoAcessoUsuarioEmpresasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(grupoAcessoUsuarioEmpresaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoAcessoUsuarioEmpresaMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(grupoAcessoUsuarioEmpresaRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGrupoAcessoUsuarioEmpresa() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresaRepository.saveAndFlush(grupoAcessoUsuarioEmpresa);

        // Get the grupoAcessoUsuarioEmpresa
        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoAcessoUsuarioEmpresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoAcessoUsuarioEmpresa.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataExpiracao").value(DEFAULT_DATA_EXPIRACAO.toString()))
            .andExpect(jsonPath("$.ilimitado").value(DEFAULT_ILIMITADO.booleanValue()))
            .andExpect(jsonPath("$.desabilitar").value(DEFAULT_DESABILITAR.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingGrupoAcessoUsuarioEmpresa() throws Exception {
        // Get the grupoAcessoUsuarioEmpresa
        restGrupoAcessoUsuarioEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrupoAcessoUsuarioEmpresa() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresaRepository.saveAndFlush(grupoAcessoUsuarioEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoUsuarioEmpresa
        GrupoAcessoUsuarioEmpresa updatedGrupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresaRepository
            .findById(grupoAcessoUsuarioEmpresa.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedGrupoAcessoUsuarioEmpresa are not directly saved in db
        em.detach(updatedGrupoAcessoUsuarioEmpresa);
        updatedGrupoAcessoUsuarioEmpresa
            .nome(UPDATED_NOME)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);

        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoAcessoUsuarioEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGrupoAcessoUsuarioEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoUsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGrupoAcessoUsuarioEmpresaToMatchAllProperties(updatedGrupoAcessoUsuarioEmpresa);
    }

    @Test
    @Transactional
    void putNonExistingGrupoAcessoUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoAcessoUsuarioEmpresa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoUsuarioEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoUsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoAcessoUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoUsuarioEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoUsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoAcessoUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoUsuarioEmpresa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAcessoUsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoAcessoUsuarioEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresaRepository.saveAndFlush(grupoAcessoUsuarioEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoUsuarioEmpresa using partial update
        GrupoAcessoUsuarioEmpresa partialUpdatedGrupoAcessoUsuarioEmpresa = new GrupoAcessoUsuarioEmpresa();
        partialUpdatedGrupoAcessoUsuarioEmpresa.setId(grupoAcessoUsuarioEmpresa.getId());

        partialUpdatedGrupoAcessoUsuarioEmpresa.nome(UPDATED_NOME).dataExpiracao(UPDATED_DATA_EXPIRACAO);

        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAcessoUsuarioEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoAcessoUsuarioEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoUsuarioEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoAcessoUsuarioEmpresaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGrupoAcessoUsuarioEmpresa, grupoAcessoUsuarioEmpresa),
            getPersistedGrupoAcessoUsuarioEmpresa(grupoAcessoUsuarioEmpresa)
        );
    }

    @Test
    @Transactional
    void fullUpdateGrupoAcessoUsuarioEmpresaWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresaRepository.saveAndFlush(grupoAcessoUsuarioEmpresa);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoUsuarioEmpresa using partial update
        GrupoAcessoUsuarioEmpresa partialUpdatedGrupoAcessoUsuarioEmpresa = new GrupoAcessoUsuarioEmpresa();
        partialUpdatedGrupoAcessoUsuarioEmpresa.setId(grupoAcessoUsuarioEmpresa.getId());

        partialUpdatedGrupoAcessoUsuarioEmpresa
            .nome(UPDATED_NOME)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);

        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAcessoUsuarioEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoAcessoUsuarioEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoUsuarioEmpresa in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoAcessoUsuarioEmpresaUpdatableFieldsEquals(
            partialUpdatedGrupoAcessoUsuarioEmpresa,
            getPersistedGrupoAcessoUsuarioEmpresa(partialUpdatedGrupoAcessoUsuarioEmpresa)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGrupoAcessoUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioEmpresa.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoAcessoUsuarioEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoUsuarioEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoUsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoAcessoUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoUsuarioEmpresa))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoUsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoAcessoUsuarioEmpresa() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoUsuarioEmpresa.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(grupoAcessoUsuarioEmpresa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAcessoUsuarioEmpresa in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoAcessoUsuarioEmpresa() throws Exception {
        // Initialize the database
        insertedGrupoAcessoUsuarioEmpresa = grupoAcessoUsuarioEmpresaRepository.saveAndFlush(grupoAcessoUsuarioEmpresa);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grupoAcessoUsuarioEmpresa
        restGrupoAcessoUsuarioEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoAcessoUsuarioEmpresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return grupoAcessoUsuarioEmpresaRepository.count();
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

    protected GrupoAcessoUsuarioEmpresa getPersistedGrupoAcessoUsuarioEmpresa(GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa) {
        return grupoAcessoUsuarioEmpresaRepository.findById(grupoAcessoUsuarioEmpresa.getId()).orElseThrow();
    }

    protected void assertPersistedGrupoAcessoUsuarioEmpresaToMatchAllProperties(
        GrupoAcessoUsuarioEmpresa expectedGrupoAcessoUsuarioEmpresa
    ) {
        assertGrupoAcessoUsuarioEmpresaAllPropertiesEquals(
            expectedGrupoAcessoUsuarioEmpresa,
            getPersistedGrupoAcessoUsuarioEmpresa(expectedGrupoAcessoUsuarioEmpresa)
        );
    }

    protected void assertPersistedGrupoAcessoUsuarioEmpresaToMatchUpdatableProperties(
        GrupoAcessoUsuarioEmpresa expectedGrupoAcessoUsuarioEmpresa
    ) {
        assertGrupoAcessoUsuarioEmpresaAllUpdatablePropertiesEquals(
            expectedGrupoAcessoUsuarioEmpresa,
            getPersistedGrupoAcessoUsuarioEmpresa(expectedGrupoAcessoUsuarioEmpresa)
        );
    }
}
