package com.dobemcontabilidade.web.rest;

import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContadorAsserts.*;
import static com.dobemcontabilidade.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dobemcontabilidade.IntegrationTest;
import com.dobemcontabilidade.domain.GrupoAcessoEmpresa;
import com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContador;
import com.dobemcontabilidade.domain.Permisao;
import com.dobemcontabilidade.domain.UsuarioContador;
import com.dobemcontabilidade.repository.GrupoAcessoEmpresaUsuarioContadorRepository;
import com.dobemcontabilidade.service.GrupoAcessoEmpresaUsuarioContadorService;
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
 * Integration tests for the {@link GrupoAcessoEmpresaUsuarioContadorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GrupoAcessoEmpresaUsuarioContadorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_EXPIRACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_EXPIRACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ILIMITADO = false;
    private static final Boolean UPDATED_ILIMITADO = true;

    private static final Boolean DEFAULT_DESABILITAR = false;
    private static final Boolean UPDATED_DESABILITAR = true;

    private static final String ENTITY_API_URL = "/api/grupo-acesso-empresa-usuario-contadors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GrupoAcessoEmpresaUsuarioContadorRepository grupoAcessoEmpresaUsuarioContadorRepository;

    @Mock
    private GrupoAcessoEmpresaUsuarioContadorRepository grupoAcessoEmpresaUsuarioContadorRepositoryMock;

    @Mock
    private GrupoAcessoEmpresaUsuarioContadorService grupoAcessoEmpresaUsuarioContadorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoAcessoEmpresaUsuarioContadorMockMvc;

    private GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador;

    private GrupoAcessoEmpresaUsuarioContador insertedGrupoAcessoEmpresaUsuarioContador;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAcessoEmpresaUsuarioContador createEntity(EntityManager em) {
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador = new GrupoAcessoEmpresaUsuarioContador()
            .nome(DEFAULT_NOME)
            .dataExpiracao(DEFAULT_DATA_EXPIRACAO)
            .ilimitado(DEFAULT_ILIMITADO)
            .desabilitar(DEFAULT_DESABILITAR);
        // Add required entity
        UsuarioContador usuarioContador;
        if (TestUtil.findAll(em, UsuarioContador.class).isEmpty()) {
            usuarioContador = UsuarioContadorResourceIT.createEntity(em);
            em.persist(usuarioContador);
            em.flush();
        } else {
            usuarioContador = TestUtil.findAll(em, UsuarioContador.class).get(0);
        }
        grupoAcessoEmpresaUsuarioContador.setUsuarioContador(usuarioContador);
        // Add required entity
        Permisao permisao;
        if (TestUtil.findAll(em, Permisao.class).isEmpty()) {
            permisao = PermisaoResourceIT.createEntity(em);
            em.persist(permisao);
            em.flush();
        } else {
            permisao = TestUtil.findAll(em, Permisao.class).get(0);
        }
        grupoAcessoEmpresaUsuarioContador.setPermisao(permisao);
        // Add required entity
        GrupoAcessoEmpresa grupoAcessoEmpresa;
        if (TestUtil.findAll(em, GrupoAcessoEmpresa.class).isEmpty()) {
            grupoAcessoEmpresa = GrupoAcessoEmpresaResourceIT.createEntity(em);
            em.persist(grupoAcessoEmpresa);
            em.flush();
        } else {
            grupoAcessoEmpresa = TestUtil.findAll(em, GrupoAcessoEmpresa.class).get(0);
        }
        grupoAcessoEmpresaUsuarioContador.setGrupoAcessoEmpresa(grupoAcessoEmpresa);
        return grupoAcessoEmpresaUsuarioContador;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoAcessoEmpresaUsuarioContador createUpdatedEntity(EntityManager em) {
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador = new GrupoAcessoEmpresaUsuarioContador()
            .nome(UPDATED_NOME)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);
        // Add required entity
        UsuarioContador usuarioContador;
        if (TestUtil.findAll(em, UsuarioContador.class).isEmpty()) {
            usuarioContador = UsuarioContadorResourceIT.createUpdatedEntity(em);
            em.persist(usuarioContador);
            em.flush();
        } else {
            usuarioContador = TestUtil.findAll(em, UsuarioContador.class).get(0);
        }
        grupoAcessoEmpresaUsuarioContador.setUsuarioContador(usuarioContador);
        // Add required entity
        Permisao permisao;
        if (TestUtil.findAll(em, Permisao.class).isEmpty()) {
            permisao = PermisaoResourceIT.createUpdatedEntity(em);
            em.persist(permisao);
            em.flush();
        } else {
            permisao = TestUtil.findAll(em, Permisao.class).get(0);
        }
        grupoAcessoEmpresaUsuarioContador.setPermisao(permisao);
        // Add required entity
        GrupoAcessoEmpresa grupoAcessoEmpresa;
        if (TestUtil.findAll(em, GrupoAcessoEmpresa.class).isEmpty()) {
            grupoAcessoEmpresa = GrupoAcessoEmpresaResourceIT.createUpdatedEntity(em);
            em.persist(grupoAcessoEmpresa);
            em.flush();
        } else {
            grupoAcessoEmpresa = TestUtil.findAll(em, GrupoAcessoEmpresa.class).get(0);
        }
        grupoAcessoEmpresaUsuarioContador.setGrupoAcessoEmpresa(grupoAcessoEmpresa);
        return grupoAcessoEmpresaUsuarioContador;
    }

    @BeforeEach
    public void initTest() {
        grupoAcessoEmpresaUsuarioContador = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGrupoAcessoEmpresaUsuarioContador != null) {
            grupoAcessoEmpresaUsuarioContadorRepository.delete(insertedGrupoAcessoEmpresaUsuarioContador);
            insertedGrupoAcessoEmpresaUsuarioContador = null;
        }
    }

    @Test
    @Transactional
    void createGrupoAcessoEmpresaUsuarioContador() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GrupoAcessoEmpresaUsuarioContador
        var returnedGrupoAcessoEmpresaUsuarioContador = om.readValue(
            restGrupoAcessoEmpresaUsuarioContadorMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(grupoAcessoEmpresaUsuarioContador))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GrupoAcessoEmpresaUsuarioContador.class
        );

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGrupoAcessoEmpresaUsuarioContadorUpdatableFieldsEquals(
            returnedGrupoAcessoEmpresaUsuarioContador,
            getPersistedGrupoAcessoEmpresaUsuarioContador(returnedGrupoAcessoEmpresaUsuarioContador)
        );

        insertedGrupoAcessoEmpresaUsuarioContador = returnedGrupoAcessoEmpresaUsuarioContador;
    }

    @Test
    @Transactional
    void createGrupoAcessoEmpresaUsuarioContadorWithExistingId() throws Exception {
        // Create the GrupoAcessoEmpresaUsuarioContador with an existing ID
        grupoAcessoEmpresaUsuarioContador.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoEmpresaUsuarioContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGrupoAcessoEmpresaUsuarioContadors() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContadorRepository.saveAndFlush(
            grupoAcessoEmpresaUsuarioContador
        );

        // Get all the grupoAcessoEmpresaUsuarioContadorList
        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoAcessoEmpresaUsuarioContador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataExpiracao").value(hasItem(DEFAULT_DATA_EXPIRACAO.toString())))
            .andExpect(jsonPath("$.[*].ilimitado").value(hasItem(DEFAULT_ILIMITADO.booleanValue())))
            .andExpect(jsonPath("$.[*].desabilitar").value(hasItem(DEFAULT_DESABILITAR.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrupoAcessoEmpresaUsuarioContadorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(grupoAcessoEmpresaUsuarioContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoAcessoEmpresaUsuarioContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(grupoAcessoEmpresaUsuarioContadorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGrupoAcessoEmpresaUsuarioContadorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(grupoAcessoEmpresaUsuarioContadorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGrupoAcessoEmpresaUsuarioContadorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(grupoAcessoEmpresaUsuarioContadorRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGrupoAcessoEmpresaUsuarioContador() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContadorRepository.saveAndFlush(
            grupoAcessoEmpresaUsuarioContador
        );

        // Get the grupoAcessoEmpresaUsuarioContador
        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoAcessoEmpresaUsuarioContador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoAcessoEmpresaUsuarioContador.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataExpiracao").value(DEFAULT_DATA_EXPIRACAO.toString()))
            .andExpect(jsonPath("$.ilimitado").value(DEFAULT_ILIMITADO.booleanValue()))
            .andExpect(jsonPath("$.desabilitar").value(DEFAULT_DESABILITAR.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingGrupoAcessoEmpresaUsuarioContador() throws Exception {
        // Get the grupoAcessoEmpresaUsuarioContador
        restGrupoAcessoEmpresaUsuarioContadorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrupoAcessoEmpresaUsuarioContador() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContadorRepository.saveAndFlush(
            grupoAcessoEmpresaUsuarioContador
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoEmpresaUsuarioContador
        GrupoAcessoEmpresaUsuarioContador updatedGrupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContadorRepository
            .findById(grupoAcessoEmpresaUsuarioContador.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedGrupoAcessoEmpresaUsuarioContador are not directly saved in db
        em.detach(updatedGrupoAcessoEmpresaUsuarioContador);
        updatedGrupoAcessoEmpresaUsuarioContador
            .nome(UPDATED_NOME)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);

        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGrupoAcessoEmpresaUsuarioContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGrupoAcessoEmpresaUsuarioContador))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGrupoAcessoEmpresaUsuarioContadorToMatchAllProperties(updatedGrupoAcessoEmpresaUsuarioContador);
    }

    @Test
    @Transactional
    void putNonExistingGrupoAcessoEmpresaUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresaUsuarioContador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoAcessoEmpresaUsuarioContador.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoEmpresaUsuarioContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoAcessoEmpresaUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresaUsuarioContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoAcessoEmpresaUsuarioContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoAcessoEmpresaUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresaUsuarioContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoAcessoEmpresaUsuarioContador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoAcessoEmpresaUsuarioContadorWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContadorRepository.saveAndFlush(
            grupoAcessoEmpresaUsuarioContador
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoEmpresaUsuarioContador using partial update
        GrupoAcessoEmpresaUsuarioContador partialUpdatedGrupoAcessoEmpresaUsuarioContador = new GrupoAcessoEmpresaUsuarioContador();
        partialUpdatedGrupoAcessoEmpresaUsuarioContador.setId(grupoAcessoEmpresaUsuarioContador.getId());

        partialUpdatedGrupoAcessoEmpresaUsuarioContador
            .nome(UPDATED_NOME)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .desabilitar(UPDATED_DESABILITAR);

        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAcessoEmpresaUsuarioContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoAcessoEmpresaUsuarioContador))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoAcessoEmpresaUsuarioContadorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGrupoAcessoEmpresaUsuarioContador, grupoAcessoEmpresaUsuarioContador),
            getPersistedGrupoAcessoEmpresaUsuarioContador(grupoAcessoEmpresaUsuarioContador)
        );
    }

    @Test
    @Transactional
    void fullUpdateGrupoAcessoEmpresaUsuarioContadorWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContadorRepository.saveAndFlush(
            grupoAcessoEmpresaUsuarioContador
        );

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoAcessoEmpresaUsuarioContador using partial update
        GrupoAcessoEmpresaUsuarioContador partialUpdatedGrupoAcessoEmpresaUsuarioContador = new GrupoAcessoEmpresaUsuarioContador();
        partialUpdatedGrupoAcessoEmpresaUsuarioContador.setId(grupoAcessoEmpresaUsuarioContador.getId());

        partialUpdatedGrupoAcessoEmpresaUsuarioContador
            .nome(UPDATED_NOME)
            .dataExpiracao(UPDATED_DATA_EXPIRACAO)
            .ilimitado(UPDATED_ILIMITADO)
            .desabilitar(UPDATED_DESABILITAR);

        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoAcessoEmpresaUsuarioContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoAcessoEmpresaUsuarioContador))
            )
            .andExpect(status().isOk());

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoAcessoEmpresaUsuarioContadorUpdatableFieldsEquals(
            partialUpdatedGrupoAcessoEmpresaUsuarioContador,
            getPersistedGrupoAcessoEmpresaUsuarioContador(partialUpdatedGrupoAcessoEmpresaUsuarioContador)
        );
    }

    @Test
    @Transactional
    void patchNonExistingGrupoAcessoEmpresaUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresaUsuarioContador.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoAcessoEmpresaUsuarioContador.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoEmpresaUsuarioContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoAcessoEmpresaUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresaUsuarioContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoEmpresaUsuarioContador))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoAcessoEmpresaUsuarioContador() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoAcessoEmpresaUsuarioContador.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoAcessoEmpresaUsuarioContador))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoAcessoEmpresaUsuarioContador in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoAcessoEmpresaUsuarioContador() throws Exception {
        // Initialize the database
        insertedGrupoAcessoEmpresaUsuarioContador = grupoAcessoEmpresaUsuarioContadorRepository.saveAndFlush(
            grupoAcessoEmpresaUsuarioContador
        );

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grupoAcessoEmpresaUsuarioContador
        restGrupoAcessoEmpresaUsuarioContadorMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoAcessoEmpresaUsuarioContador.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return grupoAcessoEmpresaUsuarioContadorRepository.count();
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

    protected GrupoAcessoEmpresaUsuarioContador getPersistedGrupoAcessoEmpresaUsuarioContador(
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador
    ) {
        return grupoAcessoEmpresaUsuarioContadorRepository.findById(grupoAcessoEmpresaUsuarioContador.getId()).orElseThrow();
    }

    protected void assertPersistedGrupoAcessoEmpresaUsuarioContadorToMatchAllProperties(
        GrupoAcessoEmpresaUsuarioContador expectedGrupoAcessoEmpresaUsuarioContador
    ) {
        assertGrupoAcessoEmpresaUsuarioContadorAllPropertiesEquals(
            expectedGrupoAcessoEmpresaUsuarioContador,
            getPersistedGrupoAcessoEmpresaUsuarioContador(expectedGrupoAcessoEmpresaUsuarioContador)
        );
    }

    protected void assertPersistedGrupoAcessoEmpresaUsuarioContadorToMatchUpdatableProperties(
        GrupoAcessoEmpresaUsuarioContador expectedGrupoAcessoEmpresaUsuarioContador
    ) {
        assertGrupoAcessoEmpresaUsuarioContadorAllUpdatablePropertiesEquals(
            expectedGrupoAcessoEmpresaUsuarioContador,
            getPersistedGrupoAcessoEmpresaUsuarioContador(expectedGrupoAcessoEmpresaUsuarioContador)
        );
    }
}
